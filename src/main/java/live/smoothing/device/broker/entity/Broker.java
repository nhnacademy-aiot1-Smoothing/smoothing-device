package live.smoothing.device.broker.entity;

import live.smoothing.device.sensor.entity.Sensor;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Entity
@Builder
@Table(name = "brokers")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@NamedEntityGraph(name = "Broker.sensors", attributeNodes = @NamedAttributeNode("sensors"))
//@NamedEntityGraph(name = "Broker.sensors.topics", attributeNodes = @NamedAttributeNode(value = "sensors", subgraph = "sensors.topics"), subgraphs = @NamedSubgraph(name = "sensors.topics", attributeNodes = @NamedAttributeNode("topics")))
public class Broker {

    @Id
    @Column(name = "broker_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer brokerId;

    @Column(name = "broker_ip", nullable = false)
    private String brokerIp;

    @Column(name = "broker_port", nullable = false)
    private Integer brokerPort;

    @Column(name = "broker_name", nullable = false)
    private String brokerName;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "protocol_type")
    private ProtocolType protocolType;

    @OneToMany(mappedBy = "broker", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Sensor> sensors;

    public void updateBrokerIp(String brokerIp) {
        this.brokerIp = brokerIp;
    }

    public void updateBrokerPort(Integer brokerPort) {
        this.brokerPort = brokerPort;
    }

    public void updateBrokerName(String brokerName) {
        this.brokerName = brokerName;
    }

    public void updateProtocolType(ProtocolType protocolType) {
        this.protocolType = protocolType;
    }
}