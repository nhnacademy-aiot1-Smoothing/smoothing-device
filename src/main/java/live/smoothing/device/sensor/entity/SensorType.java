package live.smoothing.device.sensor.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 센서 타입 엔티티 클래스
 *
 * @author 우혜승
 */
@Getter
@Entity
@Table(name = "sensor_types")
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SensorType {

    @Id
    @Column(name = "sensor_type")
    private String sensorType;

}
