package live.smoothing.device.sensor.repository;

import live.smoothing.device.sensor.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Integer> {

    @Query("SELECT to.topic FROM Tag t " +
            "JOIN t.sensorTags st " +
            "JOIN st.sensor s " +
            "JOIN s.topics to " +
            "WHERE t.tagName IN :tags AND t.userId = :userId " +
            "GROUP BY to.topic HAVING COUNT(to.topic) = :size")
    List<String> getTopicsByUserIdAndTags(String userId, List<String> tags, Long size);

}
