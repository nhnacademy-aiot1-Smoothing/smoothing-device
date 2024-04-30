package live.smoothing.device.sensor.service.impl;

import live.smoothing.device.adapter.RuleEngineAdapter;
import live.smoothing.device.sensor.dto.*;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service("topicService")
@RequiredArgsConstructor
public class TopicServiceImpl implements TopicService {
    private final TopicRepository topicRepository;
    private final TopicTypeRepository topicTypeRepository;
    private final SensorRepository sensorRepository;
    private final RuleEngineAdapter ruleEngineAdapter;

    @Override
    @Transactional
    public void saveTopic(TopicAddRequest topicAddRequest) {
        if(topicRepository.existsTopicByTopic(topicAddRequest.getTopic())) {
            throw new TopicAlreadyExistException();
        }
        TopicType topicType = topicTypeRepository.findById(topicAddRequest.getTopicType())
                .orElseThrow(TopicTypeNotExistException::new);
        Topic topic = Topic.builder()
                .topic(topicAddRequest.getTopic())
                .topicType(topicType)
                .sensor(sensorRepository.getReferenceById(topicAddRequest.getSensorId()))
                .build();

        topic = topicRepository.save(topic);

        ruleEngineAdapter.addTopic(new TopicRequest(topic.getSensor().getBroker().getBrokerId(), topic.getTopic()));
    }

    @Override
    public TopicListResponse getTopics(Integer sensorId, Pageable pageable) {
        if(sensorRepository.findById(sensorId).isEmpty()) {
            throw new SensorNotFoundException();
        }
        Page<TopicResponse> topicResponses = topicRepository.getAllTopics(sensorId, pageable);
        return new TopicListResponse(topicResponses.getContent());
    }

    @Override
    @Transactional
    public void updateTopic(Integer topicId, TopicUpdateRequest topicUpdateRequest) {
        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new TopicNotFoundException());

        TopicType topicType = topicTypeRepository.findById(topicUpdateRequest.getTopicType())
                .orElseThrow(TopicTypeNotExistException::new);

        String oldTopic = topic.getTopic();

        topic.updateTopic(topicUpdateRequest.getTopic());
        topic.updateTopicType(topicType);
        topic = topicRepository.save(topic);

        ruleEngineAdapter.deleteTopic(new TopicRequest(topic.getSensor().getBroker().getBrokerId(), oldTopic));
        ruleEngineAdapter.addTopic(new TopicRequest(topic.getSensor().getBroker().getBrokerId(), topic.getTopic()));
    }

    @Override
    @Transactional
    public void deleteTopic(Integer topicId) {
        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new TopicNotFoundException());
        topicRepository.delete(topic);

        ruleEngineAdapter.deleteTopic(new TopicRequest(topic.getSensor().getBroker().getBrokerId(), topic.getTopic()));
    }

    @Override
    public TopicTypeListResponse getTopicTypes() {
        return new TopicTypeListResponse(topicTypeRepository.getAllTopicTypes());
    }
}
