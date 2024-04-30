package live.smoothing.device.sensor.entity;

import live.smoothing.device.broker.entity.Broker;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sensors")
@NamedEntityGraph(name = "Sensor.topics", attributeNodes = @NamedAttributeNode("topics"))
public class Sensor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sensor_id")
    private Integer sensorId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "broker_id")
    private Broker broker;

    @Column(name = "sensor_name")
    private String sensorName;

    @Column(name = "sensor_registered_at")
    private LocalDateTime sensorRegisteredAt;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sensor_type")
    private SensorType sensorType;

    @OneToMany(mappedBy = "sensor", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private Set<Topic> topics;

    @OneToMany(mappedBy = "sensor", fetch = FetchType.LAZY)
    private List<SensorTag> sensorTags;

    public void updateSensorType(SensorType sensorType) {
        this.sensorType = sensorType;
    }

    public void updateSensorName(String sensorName) {
        this.sensorName = sensorName;
    }
}