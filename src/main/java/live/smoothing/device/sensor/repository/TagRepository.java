package live.smoothing.device.sensor.repository;

import live.smoothing.device.sensor.dao.SensorTagDao;
import live.smoothing.device.sensor.dto.SensorTopicDto;
import live.smoothing.device.sensor.dto.TagResponse;
import live.smoothing.device.sensor.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 태그 레포지토리
 *
 * @author 우혜승
 */
public interface TagRepository extends JpaRepository<Tag, Integer> {

    /**
     * 사용자 아이디와 태그에 모두 해당하는 토픽 조회
     *
     * @param tags 태그 리스트
     * @param size 태그 리스트의 크기
     * @return 사용자 아이디와 태그에 모두 해당하는 토픽 조회
     */
    @Query("SELECT to.topic FROM Tag t " +
            "JOIN t.sensorTags st " +
            "JOIN st.sensor s " +
            "JOIN s.topics to " +
            "WHERE t.tagName IN :tags AND to.topicType.topicType = :type AND t.userId = :userId " +
            "GROUP BY to.topic HAVING COUNT(to.topic) = :size")
    List<String> getTopicsByUserIdAndTags(String userId ,List<String> tags, Long size, String type);

    @Query("SELECT new live.smoothing.device.sensor.dto.SensorTopicDto(s.sensorName, to.topic) FROM Tag t " +
            "JOIN t.sensorTags st " +
            "JOIN st.sensor s " +
            "JOIN s.topics to " +
            "WHERE t.tagName IN :tags AND to.topicType.topicType = :type AND t.userId = :userId " +
            "GROUP BY to.topic, s.sensorName HAVING COUNT(to.topic) = :size")
    List<SensorTopicDto> getSensorTopicsByTagsAndType(String userId ,List<String> tags, String type, Long size);

    boolean existsByUserIdAndTagName(String userId, String tagName);

    @Query("SELECT new live.smoothing.device.sensor.dto.TagResponse(t.tagId, t.tagName) FROM Tag t WHERE t.userId = :userId")
    List<TagResponse> getByUserId(String userId);

    @Query("SELECT new live.smoothing.device.sensor.dao.SensorTagDao(s.sensorId, t.tagId, t.tagName) " +
            "FROM Tag t " +
            "JOIN t.sensorTags st " +
            "JOIN st.sensor s " +
            "WHERE t.userId = :userId AND s.sensorId IN :sensorIds")
    List<SensorTagDao> getSensorTags(String userId, List<Integer> sensorIds);
}
