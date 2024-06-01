package live.smoothing.device.sensor.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

/**
 * 센서 태그 엔티티 클래스
 *
 * @author 우혜승
 */
@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tags")
public class Tag {

    @Id
    @Column(name = "tag_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tagId;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "tag_name")
    private String tagName;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tag")
    private List<SensorTag> sensorTags;

    public void updateTagName(String tagName) {
        this.tagName = tagName;
    }
}
