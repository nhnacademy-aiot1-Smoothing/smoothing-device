package live.smoothing.device.sensor.service;

import live.smoothing.device.sensor.dto.TopicAddRequest;
import live.smoothing.device.sensor.dto.TopicListResponse;
import live.smoothing.device.sensor.dto.TopicTypeListResponse;
import live.smoothing.device.sensor.dto.TopicUpdateRequest;

public interface TopicService {

    void saveTopic(TopicAddRequest topicAddRequest);

    TopicListResponse getTopics(Integer sensorId);

    void updateTopic(Integer topicId ,TopicUpdateRequest topicUpdateRequest);

    void deleteTopic(Integer topicId);

    TopicTypeListResponse getTopicTypes();
}
