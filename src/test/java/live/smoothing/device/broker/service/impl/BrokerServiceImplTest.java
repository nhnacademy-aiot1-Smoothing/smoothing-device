package live.smoothing.device.broker.service.impl;

import live.smoothing.device.broker.dto.*;
import live.smoothing.device.broker.entity.Broker;
import live.smoothing.device.broker.entity.BrokerErrorLog;
import live.smoothing.device.broker.entity.ProtocolType;
import live.smoothing.device.broker.exception.AlreadyExistBroker;
import live.smoothing.device.broker.exception.BrokerErrorNotFoundException;
import live.smoothing.device.broker.exception.BrokerNotFoundException;
import live.smoothing.device.broker.exception.ProtocolTypeNotFoundException;
import live.smoothing.device.mq.dto.BrokerErrorRequest;
import live.smoothing.device.sensor.dto.TopicRequest;
import live.smoothing.device.sensor.entity.Sensor;
import live.smoothing.device.sensor.entity.Topic;
import live.smoothing.device.sensor.entity.TopicType;
import org.junit.jupiter.api.Test;

import live.smoothing.device.adapter.RuleEngineAdapter;
import live.smoothing.device.broker.repository.BrokerErrorLogRepository;
import live.smoothing.device.broker.repository.BrokerRepository;
import live.smoothing.device.broker.repository.ProtocolTypeRepository;
import live.smoothing.device.sensor.repository.TopicRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BrokerServiceImplTest {

    @Mock
    private BrokerRepository brokerRepository;
    @Mock
    private TopicRepository topicRepository;
    @Mock
    private ProtocolTypeRepository protocolTypeRepository;
    @Mock
    private BrokerErrorLogRepository brokerErrorLogRepository;
    @Mock
    private RuleEngineAdapter ruleEngineAdapter;

    @InjectMocks
    private BrokerServiceImpl brokerService;

    @Test
    void getBrokersWithTopics() {
        List<Broker> brokers = List.of(
                Broker.builder()
                        .brokerId(1)
                        .brokerIp("123.456.789.0")
                        .brokerPort(1234)
                        .protocolType(new ProtocolType("MQTT"))
                        .brokerName("test1")
                        .sensors(List.of(
                                        Sensor.builder()
                                                .sensorId(1)
                                                .topics(Set.of(
                                                        Topic.builder()
                                                                .topicId(1)
                                                                .topic("testTopic1")
                                                                .build()
                                                ))
                                                .sensorName("testSensor1")
                                                .build()
                                )
                        )
                        .build(),
                Broker.builder()
                        .brokerId(2)
                        .brokerIp("123.456.789.1")
                        .brokerPort(1235)
                        .sensors(
                                List.of(
                                        Sensor.builder()
                                                .sensorId(2)
                                                .topics(Set.of(
                                                        Topic.builder()
                                                                .topicId(2)
                                                                .topic("testTopic2")
                                                                .build()
                                                ))
                                                .sensorName("testSensor2")
                                                .build()
                                )
                        )
                        .protocolType(new ProtocolType("MQTT"))
                        .brokerName("test2")
                        .build()

        );
        when(brokerRepository.getAllWith()).thenReturn(brokers);
        List<RuleEngineResponse> result = brokerService.getBrokersWithTopics();
        assertAll(
                () -> assertEquals(2, result.size()),
                () -> assertTrue(result.stream().anyMatch(response -> Objects.equals(ReflectionTestUtils.getField(response, "brokerIp"), "123.456.789.0"))),
                () -> assertTrue(result.stream().anyMatch(response -> Objects.equals(ReflectionTestUtils.getField(response, "brokerIp"), "123.456.789.1")))
        );
    }

    @Test
    void addBroker_brokerType_notExist_throwError() {
        BrokerAddRequest brokerAddRequest = new BrokerAddRequest();
        ReflectionTestUtils.setField(brokerAddRequest, "protocolType", "testProtocolType");

        assertThrows(ProtocolTypeNotFoundException.class, () -> brokerService.addBroker(brokerAddRequest));
        verify(protocolTypeRepository, times(1)).findById(brokerAddRequest.getProtocolType());
    }

    @Test
    void addBroker_broker_alreadyExist_throwError() {
        BrokerAddRequest brokerAddRequest = new BrokerAddRequest();
        ReflectionTestUtils.setField(brokerAddRequest, "brokerIp", "123.456.789.0");
        ReflectionTestUtils.setField(brokerAddRequest, "brokerPort", 1234);
        ReflectionTestUtils.setField(brokerAddRequest, "brokerName", "testBroker");
        ReflectionTestUtils.setField(brokerAddRequest, "protocolType", "testProtocolType");

        when(protocolTypeRepository.findById(brokerAddRequest.getProtocolType())).thenReturn(Optional.of(new ProtocolType(brokerAddRequest.getProtocolType())));
        when(brokerRepository.existsByBrokerIpAndBrokerPort(brokerAddRequest.getBrokerIp(), brokerAddRequest.getBrokerPort())).thenReturn(true);

        assertThrows(AlreadyExistBroker.class, () -> brokerService.addBroker(brokerAddRequest));
        verify(brokerRepository, times(0)).save(any(Broker.class));
    }

    @Test
    void addBroker(){
        BrokerAddRequest brokerAddRequest = new BrokerAddRequest();
        ReflectionTestUtils.setField(brokerAddRequest, "brokerIp", "123.456.789.0");
        ReflectionTestUtils.setField(brokerAddRequest, "brokerPort", 1234);
        ReflectionTestUtils.setField(brokerAddRequest, "brokerName", "testBroker");
        ReflectionTestUtils.setField(brokerAddRequest, "protocolType", "testProtocolType");

        Broker broker = Broker.builder()
                .brokerId(1)
                .brokerIp("123.456.789.0")
                .brokerPort(1234)
                .protocolType(new ProtocolType(brokerAddRequest.getProtocolType()))
                .build();

        when(protocolTypeRepository.findById(brokerAddRequest.getProtocolType())).thenReturn(Optional.of(new ProtocolType(brokerAddRequest.getProtocolType())));
        when(brokerRepository.existsByBrokerIpAndBrokerPort(brokerAddRequest.getBrokerIp(), brokerAddRequest.getBrokerPort())).thenReturn(false);
        when(brokerRepository.save(any(Broker.class))).thenReturn(broker);

        brokerService.addBroker(brokerAddRequest);

        verify(brokerRepository, times(1)).save(any(Broker.class));
        verify(brokerRepository, times(1)).existsByBrokerIpAndBrokerPort(brokerAddRequest.getBrokerIp(), brokerAddRequest.getBrokerPort());
        verify(ruleEngineAdapter, times(1)).addBroker(any(BrokerGenerateRequest.class));
    }

    @Test
    void getBrokers() {
        Pageable pageable = mock(Pageable.class);
        Page<BrokerResponse> brokerResponsePage = mock(Page.class);
        BrokerResponse brokerResponse = BrokerResponse.builder()
                .brokerId(1)
                .brokerIp("123.456.789.0")
                .brokerPort(1234)
                .protocolType("testProtocolType")
                .brokerName("testBroker")
                .build();
        when(brokerRepository.getBrokers(pageable)).thenReturn(brokerResponsePage);
        when(brokerResponsePage.getContent()).thenReturn(List.of(brokerResponse));

        BrokerListResponse result = brokerService.getBrokers(pageable);

        assertNotNull(result);

        verify(brokerRepository, times(1)).getBrokers(pageable);
    }

    @Test
    void updateBroker_broker_notExist_throwError() {
        Integer brokerId = 1;
        BrokerUpdateRequest brokerUpdateRequest = new BrokerUpdateRequest();

        when(brokerRepository.findById(brokerId)).thenReturn(Optional.empty());

        assertThrows(BrokerNotFoundException.class, () -> brokerService.updateBroker(brokerId, brokerUpdateRequest));
        verify(brokerRepository, times(1)).findById(brokerId);
    }

    @Test
    void updateBroker_protocolType_notExist_throwError(){
        Integer brokerId = 1;
        BrokerUpdateRequest brokerUpdateRequest = new BrokerUpdateRequest();
        ReflectionTestUtils.setField(brokerUpdateRequest, "protocolType", "testProtocolType");

        Broker broker = Broker.builder()
                .build();

        when(brokerRepository.findById(brokerId)).thenReturn(Optional.of(broker));
        when(protocolTypeRepository.findById(brokerUpdateRequest.getProtocolType())).thenReturn(Optional.empty());

        assertThrows(ProtocolTypeNotFoundException.class, () -> brokerService.updateBroker(brokerId, brokerUpdateRequest));
        verify(protocolTypeRepository, times(1)).findById(brokerUpdateRequest.getProtocolType());
    }

    @Test
    void updateBroker_alreadyExistBroker_throwError(){
        Integer brokerId = 1;
        BrokerUpdateRequest brokerUpdateRequest = new BrokerUpdateRequest();
        ReflectionTestUtils.setField(brokerUpdateRequest, "brokerIp", "123.456.789.0");
        ReflectionTestUtils.setField(brokerUpdateRequest, "brokerPort", 1234);
        ReflectionTestUtils.setField(brokerUpdateRequest, "brokerName", "testBroker");
        ReflectionTestUtils.setField(brokerUpdateRequest, "protocolType", "testProtocolType");

        Broker broker = Broker.builder()
                .brokerId(1)
                .brokerIp("123.456.789.0")
                .brokerPort(1234)
                .protocolType(new ProtocolType("testProtocolType"))
                .build();

        when(brokerRepository.findById(brokerId)).thenReturn(Optional.of(broker));
        when(protocolTypeRepository.findById(brokerUpdateRequest.getProtocolType())).thenReturn(Optional.of(new ProtocolType(brokerUpdateRequest.getProtocolType())));
        when(brokerRepository.existsByBrokerIpAndBrokerPort(brokerUpdateRequest.getBrokerIp(), brokerUpdateRequest.getBrokerPort())).thenReturn(true);

        assertThrows(AlreadyExistBroker.class, () -> brokerService.updateBroker(brokerId, brokerUpdateRequest));
        verify(brokerRepository, times(0)).save(any(Broker.class));
    }

    @Test
    void updateBroker(){
        Integer brokerId = 1;
        BrokerUpdateRequest brokerUpdateRequest = new BrokerUpdateRequest();
        ReflectionTestUtils.setField(brokerUpdateRequest, "brokerIp", "123.456.789.0");
        ReflectionTestUtils.setField(brokerUpdateRequest, "brokerPort", 1234);
        ReflectionTestUtils.setField(brokerUpdateRequest, "brokerName", "testBroker");
        ReflectionTestUtils.setField(brokerUpdateRequest, "protocolType", "testProtocolType");

        Broker broker = Broker.builder()
                .brokerId(1)
                .brokerIp("123.456.789.0")
                .brokerPort(1234)
                .protocolType(new ProtocolType("testProtocolType"))
                .build();

        TopicType topicType = new TopicType("testTopicType");
        List<Topic> topics = List.of(
                Topic.builder()
                        .topic("topic1")
                        .topicId(1)
                        .topicType(topicType)
                        .topicRegisteredAt(LocalDateTime.now())
                        .build(),
                Topic.builder()
                        .topic("topic2")
                        .topicId(2)
                        .topicType(topicType)
                        .topicRegisteredAt(LocalDateTime.now())
                        .build()
        );

        when(brokerRepository.findById(brokerId)).thenReturn(Optional.of(broker));
        when(protocolTypeRepository.findById(brokerUpdateRequest.getProtocolType())).thenReturn(Optional.of(new ProtocolType(brokerUpdateRequest.getProtocolType())));
        when(brokerRepository.save(any(Broker.class))).thenReturn(broker);
        when(topicRepository.getTopicBySensorBrokerBrokerId(brokerId)).thenReturn(topics);
        when(brokerRepository.existsByBrokerIpAndBrokerPort(brokerUpdateRequest.getBrokerIp(), brokerUpdateRequest.getBrokerPort())).thenReturn(false);

        brokerService.updateBroker(brokerId, brokerUpdateRequest);

        verify(brokerRepository, times(1)).save(any(Broker.class));
        verify(ruleEngineAdapter, times(1)).deleteBroker(brokerId);
        verify(ruleEngineAdapter, times(1)).addBroker(any(BrokerGenerateRequest.class));
        verify(ruleEngineAdapter).deleteBroker(brokerId);
        verify(ruleEngineAdapter).addBroker(any(BrokerGenerateRequest.class));
        verify(topicRepository).getTopicBySensorBrokerBrokerId(brokerId);
        verify(ruleEngineAdapter, times(topics.size())).addTopic(any(TopicRequest.class));
    }

    @Test
    void deleteBroker_notExist_broker_throwError() {
        Integer brokerId = 1;

        when(brokerRepository.findById(brokerId)).thenThrow(BrokerNotFoundException.class);

        assertThrows(BrokerNotFoundException.class, () -> brokerService.deleteBroker(brokerId));
        verify(brokerRepository, times(1)).findById(brokerId);
    }

    @Test
    void deleteBroker() {
        Integer brokerId = 1;
        Broker broker = Broker.builder()
                .brokerId(brokerId)
                .brokerIp("123.456.789.0")
                .brokerPort(1234)
                .protocolType(new ProtocolType("testProtocolType"))
                .build();

        when(brokerRepository.findById(brokerId)).thenReturn(Optional.of(broker));

        brokerService.deleteBroker(brokerId);

        verify(brokerRepository, times(1)).delete(broker);
        verify(ruleEngineAdapter, times(1)).deleteBroker(brokerId);
    }

    @Test
    void getErrors() {
        Pageable pageable = Pageable.ofSize(10);
        Page<BrokerErrorResponse> errors = mock(Page.class);

        BrokerErrorResponse brokerErrorResponse = BrokerErrorResponse.builder()
                .brokerErrorType("testBrokerErrorType")
                .brokerName("testBroker")
                .createdAt(LocalDateTime.now())
                .solvedAt(LocalDateTime.now())
                .build();

        when(brokerErrorLogRepository.getAllErrors(pageable)).thenReturn(errors);
        when(errors.getContent()).thenReturn(List.of(brokerErrorResponse));

        BrokerErrorListResponse result = brokerService.getErrors(pageable);

        assertNotNull(result);

        verify(brokerErrorLogRepository, times(1)).getAllErrors(pageable);
    }

    @Test
    void deleteError_notExist_brokerError_throwError() {
        Integer brokerErrorId = 1;

        when(brokerErrorLogRepository.findById(brokerErrorId)).thenReturn(Optional.empty());

        assertThrows(BrokerErrorNotFoundException.class, () -> brokerService.deleteError(brokerErrorId));
        verify(brokerErrorLogRepository, times(1)).findById(brokerErrorId);
    }

    @Test
    void deleteError() {
        Integer brokerErrorId = 1;
        BrokerErrorLog brokerErrorLog = BrokerErrorLog.builder()
                .brokerErrorLogId(brokerErrorId)
                .brokerErrorType("testBrokerErrorType")
                .brokerErrorCreatedAt(LocalDateTime.now())
                .brokerErrorSolvedAt(LocalDateTime.now())
                .build();

        when(brokerErrorLogRepository.findById(brokerErrorId)).thenReturn(Optional.of(brokerErrorLog));

        brokerService.deleteError(brokerErrorId);

        verify(brokerErrorLogRepository, times(1)).delete(brokerErrorLog);
    }

    @Test
    void addBrokerError() {
        BrokerErrorRequest request = new BrokerErrorRequest();
        ReflectionTestUtils.setField(request, "brokerId", 1);
        ReflectionTestUtils.setField(request, "brokerErrorType", "testBrokerErrorType");
        ReflectionTestUtils.setField(request, "createdAt", LocalDateTime.now().toString());

        when(brokerRepository.getReferenceById(request.getBrokerId())).thenReturn(Broker.builder().build());

        brokerService.addBrokerError(request);
        verify(brokerErrorLogRepository, times(1)).save(any(BrokerErrorLog.class));
    }

    @Test
    void getProtocolTypes() {
        List<ProtocolType> protocolTypes = List.of(new ProtocolType("testProtocolType1"), new ProtocolType("testProtocolType2"));

        when(protocolTypeRepository.findAll()).thenReturn(protocolTypes);

        ProtocolTypeResponse result = brokerService.getProtocolTypes();
        Object protocolTypesResult = ReflectionTestUtils.getField(result, "protocolTypes");

        assertInstanceOf(List.class, protocolTypesResult);
        assertEquals(protocolTypes.size(), ((List<?>) protocolTypesResult).size());
        assertTrue(((List<?>) protocolTypesResult).containsAll(List.of("testProtocolType1", "testProtocolType2")));
    }
}