package live.smoothing.device.sensor.entity;

import lombok.*;

import javax.persistence.*;

/**
 * 센서 태그 엔티티 클래스
 *
 * @author 우혜승
 */
@Entity
@Getter
@Builder
@Table(name = "sensor_tags")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SensorTag {

    @Id
    @Column(name = "sensor_tag_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer sensorTagId;

    @JoinColumn(name = "tag_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Tag tag;

    @JoinColumn(name = "sensor_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Sensor sensor;
}
