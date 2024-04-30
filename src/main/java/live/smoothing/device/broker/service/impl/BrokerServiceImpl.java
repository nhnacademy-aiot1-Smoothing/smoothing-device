package live.smoothing.device.broker.service.impl;

import live.smoothing.device.adapter.RuleEngineAdapter;
import live.smoothing.device.broker.dto.*;
import live.smoothing.device.broker.entity.Broker;
import live.smoothing.device.broker.entity.BrokerErrorLog;
import live.smoothing.device.broker.entity.ProtocolType;
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
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service("brokerService")
public class BrokerServiceImpl implements BrokerService {

    private final BrokerRepository brokerRepository;
    private final TopicRepository topicRepository;
    private final ProtocolTypeRepository protocolTypeRepository;
    private final BrokerErrorLogRepository brokerErrorLogRepository;
    private final RuleEngineAdapter ruleEngineAdapter;

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

    @Override
    @Transactional
    public void addBroker(BrokerAddRequest request) {
        ProtocolType protocolType = protocolTypeRepository.findById(request.getProtocolType())
                .orElseThrow(ProtocolTypeNotFoundException::new);

        Broker broker = Broker.builder()
                .brokerName(request.getBrokerName())
                .brokerIp(request.getBrokerIp())
                .brokerPort(request.getBrokerPort())
                .protocolType(protocolType)
                .build();
        broker = brokerRepository.save(broker);

        ruleEngineAdapter.addBroker(BrokerRequest.builder()
                .brokerId(broker.getBrokerId())
                .brokerIp(broker.getBrokerIp())
                .brokerPort(broker.getBrokerPort())
                .protocolType(protocolType.getProtocolType())
                .build());
    }

    @Override
    public BrokerListResponse getBrokers() {
        return new BrokerListResponse(brokerRepository.getBrokers());
    }

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
        ruleEngineAdapter.addBroker(BrokerRequest.builder()
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

    @Override
    public void deleteBroker(Integer brokerId) {
        Broker broker = brokerRepository.findById(brokerId)
                .orElseThrow(BrokerNotFoundException::new);
        brokerRepository.delete(broker);
        ruleEngineAdapter.deleteBroker(brokerId);
    }

    @Override
    public BrokerErrorListResponse getErrors() {
        return new BrokerErrorListResponse(brokerErrorLogRepository.getAllErrors());
    }

    @Override
    public void deleteError(Integer brokerErrorId) {
        BrokerErrorLog brokerErrorLog = brokerErrorLogRepository.findById(brokerErrorId)
                .orElseThrow(BrokerErrorNotFoundException::new);
        brokerErrorLogRepository.delete(brokerErrorLog);
    }

    @Override
    public void addBrokerError(BrokerErrorRequest request) {
        BrokerErrorLog brokerErrorLog = BrokerErrorLog.builder()
                .broker(brokerRepository.getReferenceById(request.getBrokerId()))
                .brokerErrorType(request.getBrokerErrorType())
                .brokerErrorCreatedAt(request.getCreatedAt())
                .build();
        brokerErrorLogRepository.save(brokerErrorLog);
    }
}