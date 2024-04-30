package live.smoothing.device.sensor.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

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

}
