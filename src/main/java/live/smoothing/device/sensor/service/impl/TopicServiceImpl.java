package live.smoothing.device.sensor.service.impl;

import feign.FeignException;
import live.smoothing.device.adapter.RuleEngineAdapter;
import live.smoothing.device.sensor.dto.TopicResponseListResponse;
import live.smoothing.device.sensor.dto.*;
import live.smoothing.device.sensor.entity.Sensor;
import live.smoothing.device.sensor.entity.Topic;
import live.smoothing.device.sensor.entity.TopicType;
import live.smoothing.device.sensor.exception.TopicAlreadyExistException;
import live.smoothing.device.sensor.exception.SensorNotFoundException;
import live.smoothing.device.sensor.exception.TopicNotFoundException;
import live.smoothing.device.sensor.exception.TopicTypeNotExistException;
import live.smoothing.device.sensor.repository.SensorRepository;
import live.smoothing.device.sensor.repository.TopicRepository;
import live.smoothing.device.sensor.repository.TopicTypeRepository;
import live.smoothing.device.sensor.service.TopicService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * 토픽 서비스 구현체<br>
 * 토픽과 관련된 비즈니스 로직을 처리한다.
 *
 * @author 우혜승
 */
@Slf4j
@Service("topicService")
@RequiredArgsConstructor
public class TopicServiceImpl implements TopicService {
    private final TopicRepository topicRepository;
    private final TopicTypeRepository topicTypeRepository;
    private final SensorRepository sensorRepository;
    private final RuleEngineAdapter ruleEngineAdapter;

    /**
     * @inheritDoc
     */
    @Override
    @Transactional
    public synchronized void saveTopic(TopicAddRequest topicAddRequest) {
        if (topicRepository.existsTopicByTopicAndSensorSensorId(topicAddRequest.getTopic(), topicAddRequest.getSensorId())) {
            throw new TopicAlreadyExistException();
        }
        TopicType topicType = topicTypeRepository.findById(topicAddRequest.getTopicType())
                .orElseThrow(TopicTypeNotExistException::new);
        Sensor sensor = sensorRepository.findById(topicAddRequest.getSensorId())
                .orElseThrow(SensorNotFoundException::new);
        Topic topic = Topic.builder()
                .topic(topicAddRequest.getTopic())
                .topicType(topicType)
                .sensor(sensor)
                .build();

        topic = topicRepository.save(topic);

        try {
            ruleEngineAdapter.addTopic(new TopicRequest(topic.getSensor().getBroker().getBrokerId(), topic.getTopic()));
        } catch (FeignException e) {
            log.error("RuleEngine 맛탱이감");
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public TopicResponseListResponse getTopics(Integer sensorId, Pageable pageable) {
        if (sensorRepository.findById(sensorId).isEmpty()) {
            throw new SensorNotFoundException();
        }
        Page<TopicResponse> topicResponses = topicRepository.getAllTopics(sensorId, pageable);
        return new TopicResponseListResponse(topicResponses.getContent(), topicResponses.getTotalPages());
    }

    /**
     * @inheritDoc
     */
    @Override
    @Transactional
    public void updateTopic(Integer topicId, TopicUpdateRequest topicUpdateRequest) {
        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(TopicNotFoundException::new);

        TopicType topicType = topicTypeRepository.findById(topicUpdateRequest.getTopicType())
                .orElseThrow(TopicTypeNotExistException::new);

        if(topicRepository.existsTopicByTopic(topicUpdateRequest.getTopic()) && !topic.getTopic().equals(topicUpdateRequest.getTopic())) {
            throw new TopicAlreadyExistException();
        }

        String oldTopic = topic.getTopic();

        topic.updateTopic(topicUpdateRequest.getTopic());
        topic.updateTopicType(topicType);
        topic = topicRepository.save(topic);

        try {
            ruleEngineAdapter.deleteTopic(new TopicRequest(topic.getSensor().getBroker().getBrokerId(), oldTopic));
            ruleEngineAdapter.addTopic(new TopicRequest(topic.getSensor().getBroker().getBrokerId(), topic.getTopic()));
        } catch (FeignException e) {
            log.error("RuleEngine 맛탱이감");
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    @Transactional
    public synchronized void deleteTopic(Integer topicId) {
        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new TopicNotFoundException());
        topicRepository.delete(topic);

        try {
            ruleEngineAdapter.deleteTopic(new TopicRequest(topic.getSensor().getBroker().getBrokerId(), topic.getTopic()));
        } catch (FeignException e) {
            log.error("RuleEngine 맛탱이감");
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public TopicTypeListResponse getTopicTypes() {
        return new TopicTypeListResponse(topicTypeRepository.getAllTopicTypes());
    }

    /**
     * @inheritDoc
     */
    @Override
    public TopicListResponse getAllTopics(String type) {
        return new TopicListResponse(topicRepository.getTopicByTopicType(type));
    }

    @Override
    public SensorTopicResponse getAllSensorWithTopicsByType(String type) {
        return new SensorTopicResponse(topicRepository.getSensorTopicsByTopicType(type));
    }
}
