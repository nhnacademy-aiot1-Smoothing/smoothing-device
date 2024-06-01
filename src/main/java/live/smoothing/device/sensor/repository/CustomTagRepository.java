package live.smoothing.device.sensor.repository;

import com.querydsl.core.QueryFactory;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import live.smoothing.device.sensor.dao.SensorTagDao;
import live.smoothing.device.sensor.dto.SensorTagsResponse;
import live.smoothing.device.sensor.dto.SensorTopicDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static live.smoothing.device.sensor.entity.QSensor.sensor;
import static live.smoothing.device.sensor.entity.QSensorTag.sensorTag;
import static live.smoothing.device.sensor.entity.QTag.tag;
import static live.smoothing.device.sensor.entity.QTopic.topic1;

@RequiredArgsConstructor
@Repository("customTagRepository")
public class CustomTagRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public List<SensorTagDao> getSensorTags(String userId, List<Integer> sensorIds) {
        return jpaQueryFactory
                .select(Projections.constructor(SensorTagDao.class, sensorTag.sensor.sensorId, tag.tagId, tag.tagName))
                .from(tag)
                .join(tag.sensorTags, sensorTag)
                .join(sensorTag.sensor, sensor)
                .where(tag.userId.eq(userId).and(sensor.sensorId.in(sensorIds)))
                .fetch();
    }

    public List<SensorTopicDto> getSensorTopicsByTagsAndType(String userId , List<String> tags, String type, Long size){
        return jpaQueryFactory
                .select(Projections.constructor(SensorTopicDto.class, sensor.sensorName, topic1.topic))
                .from(tag)
                .join(tag.sensorTags, sensorTag)
                .join(sensorTag.sensor, sensor)
                .join(sensor.topics, topic1)
                .where(tag.tagName.in(tags).and(topic1.topicType.topicType.eq(type)).and(tag.userId.eq(userId)))
                .groupBy(topic1.topic, sensor.sensorName)
                .having(topic1.topic.count().eq(size))
                .fetch();
    }

    public List<String> getTopicsByUserIdAndTags(String userId ,List<String> tags, Long size, String type) {
        return jpaQueryFactory
                .select(topic1.topic)
                .from(tag)
                .join(tag.sensorTags, sensorTag)
                .join(sensorTag.sensor, sensor)
                .join(sensor.topics, topic1)
                .where(tag.tagName.in(tags).and(topic1.topicType.topicType.eq(type)).and(tag.userId.eq(userId)))
                .groupBy(topic1.topic)
                .having(topic1.topic.count().eq(size))
                .fetch();
    }

}
