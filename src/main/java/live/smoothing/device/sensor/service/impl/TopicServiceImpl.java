package live.smoothing.device.sensor.service.impl;

import live.smoothing.device.sensor.dto.TopicAddRequest;
import live.smoothing.device.sensor.dto.TopicListResponse;
import live.smoothing.device.sensor.dto.TopicTypeListResponse;
import live.smoothing.device.sensor.dto.TopicUpdateRequest;
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
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service("topicService")
@RequiredArgsConstructor
public class TopicServiceImpl implements TopicService {
    private final TopicRepository topicRepository;
    private final TopicTypeRepository topicTypeRepository;
    private final SensorRepository sensorRepository;

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
        //todo mq
        topicRepository.save(topic);
    }

    @Override
    public TopicListResponse getTopics(Integer sensorId) {
        if(sensorRepository.findById(sensorId).isEmpty()) {
            throw new SensorNotFoundException();
        }
        return new TopicListResponse(topicRepository.getAllTopics(sensorId));
    }

    @Override
    @Transactional
    public void updateTopic(Integer topicId, TopicUpdateRequest topicUpdateRequest) {
        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new TopicNotFoundException());

        TopicType topicType = topicTypeRepository.findById(topicUpdateRequest.getTopicType())
                .orElseThrow(TopicTypeNotExistException::new);

        topic.updateTopic(topicUpdateRequest.getTopic());
        topic.updateTopicType(topicType);
        //todo mq
        topicRepository.save(topic);
    }

    @Override
    public void deleteTopic(Integer topicId) {
        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new TopicNotFoundException());
        //todo mq
        topicRepository.delete(topic);
    }

    @Override
    public TopicTypeListResponse getTopicTypes() {
        return new TopicTypeListResponse(topicTypeRepository.getAllTopicTypes());
    }
}
