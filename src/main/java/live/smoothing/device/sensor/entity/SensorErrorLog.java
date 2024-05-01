package live.smoothing.device.sensor.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 센서 에러 로그 엔티티 클래스
 *
 * @author 우혜승
 */
@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sensor_error_logs")
public class SensorErrorLog {

    @Id
    @Column(name = "sensor_error_log_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer sensorErrorLogId;

    @Column(name = "sensor_error_type")
    private String sensorErrorType;

    @Column(name = "sensor_error_created_at")
    private LocalDateTime sensorErrorCreatedAt;

    @Column(name = "sensor_error_value")
    private Double sensorErrorValue;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinColumn(name="sensor_id")
    private Sensor sensor;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinColumn(name="topic_id")
    private Topic topic;

}
