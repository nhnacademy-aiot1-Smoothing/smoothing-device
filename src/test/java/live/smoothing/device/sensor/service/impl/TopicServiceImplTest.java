package live.smoothing.device.sensor.service.impl;

import live.smoothing.device.adapter.RuleEngineAdapter;
import live.smoothing.device.broker.entity.Broker;
import live.smoothing.device.sensor.dto.*;
import live.smoothing.device.sensor.entity.Sensor;
import live.smoothing.device.sensor.entity.Topic;
import live.smoothing.device.sensor.entity.TopicType;
import live.smoothing.device.sensor.exception.SensorNotFoundException;
import live.smoothing.device.sensor.exception.TopicAlreadyExistException;
import live.smoothing.device.sensor.exception.TopicNotFoundException;
import live.smoothing.device.sensor.exception.TopicTypeNotExistException;
import live.smoothing.device.sensor.repository.SensorRepository;
import live.smoothing.device.sensor.repository.TopicRepository;
import live.smoothing.device.sensor.repository.TopicTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TopicServiceImplTest {

    @Mock
    private TopicRepository topicRepository;

    @Mock
    private TopicTypeRepository topicTypeRepository;

    @Mock
    private SensorRepository sensorRepository;

    @Mock
    private RuleEngineAdapter ruleEngineAdapter;

    @InjectMocks
    private TopicServiceImpl topicService;

    @Test
    void saveTopic_topicAlreadyExist_throwError() {
        TopicAddRequest topicAddRequest = mock(TopicAddRequest.class);

        when(topicAddRequest.getTopic()).thenReturn("testTopic");
        when(topicRepository.existsTopicByTopic(topicAddRequest.getTopic())).thenReturn(true);

        assertThrows(TopicAlreadyExistException.class, () -> topicService.saveTopic(topicAddRequest));
    }

    @Test
    void saveTopic_topicTypeNotExist_throwError() {
        TopicAddRequest topicAddRequest = mock(TopicAddRequest.class);

        when(topicAddRequest.getTopic()).thenReturn("testTopic");
        when(topicRepository.existsTopicByTopic(topicAddRequest.getTopic())).thenReturn(false);
        when(topicTypeRepository.findById(topicAddRequest.getTopicType())).thenReturn(Optional.empty());

        assertThrows(TopicTypeNotExistException.class, () -> topicService.saveTopic(topicAddRequest));
    }

    @Test
    void saveTopic() {
        TopicAddRequest topicAddRequest = mock(TopicAddRequest.class);
        Topic topic = mock(Topic.class);

        when(topicAddRequest.getTopic()).thenReturn("testTopic");
        when(topicRepository.existsTopicByTopic(topicAddRequest.getTopic())).thenReturn(false);
        when(sensorRepository.getReferenceById(topicAddRequest.getSensorId())).thenReturn(mock(Sensor.class));
        when(topicTypeRepository.findById(topicAddRequest.getTopicType())).thenReturn(Optional.of(mock(TopicType.class)));
        when(topicRepository.save(any(Topic.class))).thenReturn(topic);

        when(topic.getSensor()).thenReturn(mock(Sensor.class));
        when(topic.getSensor().getBroker()).thenReturn(mock(Broker.class));
        when(topic.getSensor().getBroker().getBrokerId()).thenReturn(1);
        when(topic.getTopic()).thenReturn("testTopic");

        topicService.saveTopic(topicAddRequest);
        verify(ruleEngineAdapter, times(1)).addTopic(any());
    }

    @Test
    void getTopics_sensorNotExist_throwError() {
        Integer sensorId = 1;

        when(sensorRepository.findById(sensorId)).thenReturn(Optional.empty());

        assertThrows(SensorNotFoundException.class, () -> topicService.getTopics(sensorId, null));
    }

    @Test
    void getTopics() {
        Integer sensorId = 1;

        Page topicResponses = mock(Page.class);
        Pageable pageable = mock(Pageable.class);
        List topicResponseList = mock(List.class);

        when(sensorRepository.findById(sensorId)).thenReturn(Optional.of(mock(Sensor.class)));
        when(topicRepository.getAllTopics(sensorId, pageable)).thenReturn(topicResponses);
        when(topicResponses.getContent()).thenReturn(topicResponseList);

        TopicResponseListResponse topicResponseListResponse = topicService.getTopics(sensorId, pageable);
        assertEquals(topicResponseList, topicResponseListResponse.getTopics());
    }

    @Test
    void updateTopic_topicNotFound_throwError() {
        Integer topicId = 1;
        TopicUpdateRequest topicUpdateRequest = mock(TopicUpdateRequest.class);

        when(topicRepository.findById(topicId)).thenReturn(Optional.empty());

        assertThrows(TopicNotFoundException.class, () -> topicService.updateTopic(topicId, topicUpdateRequest));
    }

    @Test
    void updateTopic_TopicTypeNotExist_throwError() {
        Integer topicId = 1;
        TopicUpdateRequest topicUpdateRequest = mock(TopicUpdateRequest.class);

        when(topicRepository.findById(topicId)).thenReturn(Optional.of(mock(Topic.class)));
        when(topicUpdateRequest.getTopicType()).thenReturn("testTopicType");
        when(topicTypeRepository.findById(topicUpdateRequest.getTopicType())).thenReturn(Optional.empty());

        assertThrows(TopicTypeNotExistException.class, () -> topicService.updateTopic(topicId, topicUpdateRequest));
    }

    @Test
    void updateTopic() {
        Integer topicId = 1;
        TopicUpdateRequest topicUpdateRequest = mock(TopicUpdateRequest.class);
        Topic topic = mock(Topic.class);
        TopicType topicType = mock(TopicType.class);

        when(topicRepository.findById(topicId)).thenReturn(Optional.of(topic));
        when(topicUpdateRequest.getTopicType()).thenReturn("testTopicType");
        when(topicTypeRepository.findById(topicUpdateRequest.getTopicType())).thenReturn(Optional.of(topicType));
        when(topic.getTopic()).thenReturn("oldTopic");
        when(topicUpdateRequest.getTopic()).thenReturn("newTopic");
        when(topic.getSensor()).thenReturn(mock(Sensor.class));
        when(topic.getSensor().getBroker()).thenReturn(mock(Broker.class));
        when(topic.getSensor().getBroker().getBrokerId()).thenReturn(1);
        when(topicRepository.save(topic)).thenReturn(topic);

        topicService.updateTopic(topicId, topicUpdateRequest);
        verify(ruleEngineAdapter, times(1)).deleteTopic(any(TopicRequest.class));
        verify(ruleEngineAdapter, times(1)).addTopic(any(TopicRequest.class));
        verify(topic, times(1)).updateTopic("newTopic");
        verify(topic, times(1)).updateTopicType(topicType);
    }

    @Test
    void deleteTopic_topicNotFound_throwError() {
        Integer topicId = 1;

        when(topicRepository.findById(topicId)).thenReturn(Optional.empty());

        assertThrows(TopicNotFoundException.class, () -> topicService.deleteTopic(topicId));
    }

    @Test
    void deleteTopic() {
        Integer topicId = 1;
        Topic topic = mock(Topic.class);

        when(topicRepository.findById(topicId)).thenReturn(Optional.of(topic));
        when(topic.getSensor()).thenReturn(mock(Sensor.class));
        when(topic.getSensor().getBroker()).thenReturn(mock(Broker.class));
        when(topic.getSensor().getBroker().getBrokerId()).thenReturn(1);

        topicService.deleteTopic(topicId);
        verify(ruleEngineAdapter, times(1)).deleteTopic(any(TopicRequest.class));
        verify(topicRepository, times(1)).delete(topic);
    }

    @Test
    void getTopicTypes() {
        List<TopicTypeResponse> topicTypeResponseList = mock(List.class);

        when(topicTypeRepository.getAllTopicTypes()).thenReturn(topicTypeResponseList);

        TopicTypeListResponse topicTypeListResponse = topicService.getTopicTypes();
        assertEquals(topicTypeResponseList, topicTypeListResponse.getTopicTypes());
    }

//    @Test
//    void getAllTopics() {
//        Topic topic1 = mock(Topic.class);
//        Topic topic2 = mock(Topic.class);
//        String type = "testType";
//
//        List<Topic> topics = List.of(topic1, topic2);
//
//        when(topic1.getTopic()).thenReturn("testTopic1");
//        when(topic2.getTopic()).thenReturn("testTopic2");
//
//        when(topicRepository.getTopicByTopicTypeTopicType(type)).thenReturn(topics);
//
//        TopicListResponse topicListResponse = topicService.getAllTopics(type);
//        assertEquals(List.of("testTopic1", "testTopic2"), topicListResponse.getTopics());
//    }
}