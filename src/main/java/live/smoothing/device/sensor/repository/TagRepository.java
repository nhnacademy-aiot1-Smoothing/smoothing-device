package live.smoothing.device.sensor.repository;

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
     * 사용자 아이디와 태그 이름으로 존재 여부 확인
     *
     * @param userId 사용자 아이디
     * @param tagName 태그 이름
     * @return 존재 여부
     */
    boolean existsByUserIdAndTagName(String userId, String tagName);

    /**
     * 사용자 아이디로 태그 리스트 조회
     *
     * @param userId 사용자 아이디
     * @return 태그 리스트 응답
     */
    @Query("SELECT new live.smoothing.device.sensor.dto.TagResponse(t.tagId, t.tagName) FROM Tag t WHERE t.userId = :userId")
    List<TagResponse> getByUserId(String userId);
}
