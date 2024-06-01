package live.smoothing.device.broker.service.impl;

import live.smoothing.device.adapter.RuleEngineAdapter;
import live.smoothing.device.broker.dto.*;
import live.smoothing.device.broker.entity.Broker;
import live.smoothing.device.broker.entity.BrokerErrorLog;
import live.smoothing.device.broker.entity.ProtocolType;
import live.smoothing.device.broker.exception.AlreadyExistBroker;
import live.smoothing.device.broker.exception.BrokerErrorNotFoundException;
import live.smoothing.device.broker.exception.BrokerNotFoundException;
import live.smoothing.device.broker.exception.ProtocolTypeNotFoundException;
import live.smoothing.device.broker.repository.BrokerErrorLogRepository;
import live.smoothing.device.broker.repository.BrokerRepository;
import live.smoothing.device.broker.repository.ProtocolTypeRepository;
import live.smoothing.device.broker.service.BrokerService;
import live.smoothing.device.mq.dto.BrokerErrorRequest;
import live.smoothing.device.sensor.dto.TopicRequest;
import live.smoothing.device.sensor.entity.Topic;
import live.smoothing.device.sensor.repository.TopicRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 브로커 서비스
 * @see BrokerService 구현체
 *
 * @author 우혜승
 */
@Slf4j
@AllArgsConstructor
@Service("brokerService")
public class BrokerServiceImpl implements BrokerService {

    private final BrokerRepository brokerRepository;
    private final TopicRepository topicRepository;
    private final ProtocolTypeRepository protocolTypeRepository;
    private final BrokerErrorLogRepository brokerErrorLogRepository;
    private final RuleEngineAdapter ruleEngineAdapter;

    /**
     * @inheritDoc
     */
    @Override
    public List<RuleEngineResponse> getInitBrokers() {
        List<Broker> brokers = brokerRepository.getAllWith();
        List<RuleEngineResponse> responses = new LinkedList<>();
        for (Broker broker : brokers) {
            responses.add(RuleEngineResponse.builder()
                    .brokerId(broker.getBrokerId())
                    .brokerIp(broker.getBrokerIp())
                    .brokerPort(broker.getBrokerPort())
                    .protocolType(broker.getProtocolType().getProtocolType())
                    .topics(broker.getSensors().stream().flatMap(sensor -> sensor.getTopics().stream())
                            .map(Topic::getTopic)
                            .collect(Collectors.toSet()))
                    .build());
        }
        return responses;
    }

    /**
     * @inheritDoc
     */
    @Override
    @Transactional
    public void addBroker(BrokerAddRequest request) {
        ProtocolType protocolType = protocolTypeRepository.findById(request.getProtocolType())
                .orElseThrow(ProtocolTypeNotFoundException::new);

        if(brokerRepository.existsByBrokerIpAndBrokerPort(request.getBrokerIp(), request.getBrokerPort())) {
            throw new AlreadyExistBroker();
        }


        Broker broker = Broker.builder()
                .brokerName(request.getBrokerName())
                .brokerIp(request.getBrokerIp())
                .brokerPort(request.getBrokerPort())
                .protocolType(protocolType)
                .build();
        broker = brokerRepository.save(broker);

//        try {
            ruleEngineAdapter.addBroker(BrokerGenerateRequest.builder()
                    .brokerId(broker.getBrokerId())
                    .brokerIp(broker.getBrokerIp())
                    .brokerPort(broker.getBrokerPort())
                    .protocolType(protocolType.getProtocolType())
                    .build());
//        }catch (Exception e) {
//        log.error("Rule Engine Broker Add Error : {}", e.getMessage());
//        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public BrokerListResponse getBrokers(Pageable pageable) {
        Page<BrokerResponse> brokers = brokerRepository.getBrokers(pageable);
        return new BrokerListResponse(brokers.getContent(), brokers.getTotalPages());
    }

    /**
     * @inheritDoc
     */
    @Override
    @Transactional
    public void updateBroker(Integer brokerId, BrokerUpdateRequest brokerUpdateRequest) {
        Broker broker = brokerRepository.findById(brokerId)
                .orElseThrow(BrokerNotFoundException::new);
        ProtocolType protocolType = protocolTypeRepository.findById(brokerUpdateRequest.getProtocolType())
                .orElseThrow(ProtocolTypeNotFoundException::new);

        broker.updateBrokerIp(brokerUpdateRequest.getBrokerIp());
        broker.updateBrokerPort(brokerUpdateRequest.getBrokerPort());
        broker.updateBrokerName(brokerUpdateRequest.getBrokerName());
        broker.updateProtocolType(protocolType);
        broker = brokerRepository.save(broker);

        ruleEngineAdapter.deleteBroker(brokerId);
        ruleEngineAdapter.addBroker(BrokerGenerateRequest.builder()
                .brokerId(broker.getBrokerId())
                .brokerIp(broker.getBrokerIp())
                .brokerPort(broker.getBrokerPort())
                .protocolType(protocolType.getProtocolType())
                .build());
        List<Topic> topics = topicRepository.getTopicBySensorBrokerBrokerId(brokerId);
        for(Topic topic : topics) {
            ruleEngineAdapter.addTopic(new TopicRequest(brokerId, topic.getTopic()));
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    @Transactional
    public void deleteBroker(Integer brokerId) {
        Broker broker = brokerRepository.findById(brokerId)
                .orElseThrow(BrokerNotFoundException::new);
        brokerRepository.delete(broker);
        ruleEngineAdapter.deleteBroker(brokerId);
    }

    /**
     * @inheritDoc
     */
    @Override
    public BrokerErrorListResponse getErrors(Pageable pageable) {
        Page<BrokerErrorResponse> errors = brokerErrorLogRepository.getAllErrors(pageable);
        return new BrokerErrorListResponse(errors.getContent(), errors.getTotalPages());
    }

    /**
     * @inheritDoc
     */
    @Override
    public void deleteError(Integer brokerErrorId) {
        BrokerErrorLog brokerErrorLog = brokerErrorLogRepository.findById(brokerErrorId)
                .orElseThrow(BrokerErrorNotFoundException::new);
        brokerErrorLogRepository.delete(brokerErrorLog);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void addBrokerError(BrokerErrorRequest request) {
        BrokerErrorLog brokerErrorLog = BrokerErrorLog.builder()
                .broker(brokerRepository.getReferenceById(request.getBrokerId()))
                .brokerErrorType(request.getBrokerErrorType())
                .brokerErrorCreatedAt(LocalDateTime.parse(request.getCreatedAt()))
                .build();
        brokerErrorLogRepository.save(brokerErrorLog);
    }

    @Override
    public ProtocolTypeResponse getProtocolTypes() {
        return new ProtocolTypeResponse(protocolTypeRepository.findAll().stream().map(ProtocolType::getProtocolType).collect(Collectors.toList()));
    }
}