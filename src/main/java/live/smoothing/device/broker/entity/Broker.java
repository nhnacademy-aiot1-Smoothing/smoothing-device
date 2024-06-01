package live.smoothing.device.broker.entity;

import live.smoothing.device.sensor.entity.Sensor;
import lombok.*;

import javax.persistence.*;
import java.util.List;

/**
 * 브로커 엔티티
 *
 * @author 우혜승
 */
@Getter
@Entity
@Builder
@Table(name = "brokers")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@NamedEntityGraph(name = "Broker.sensors", attributeNodes = @NamedAttributeNode("sensors"))
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

    /**
     * 브로커 Ip를 수정한다.
     *
     * @param brokerIp 브로커 Ip
     */
    public void updateBrokerIp(String brokerIp) {
        this.brokerIp = brokerIp;
    }

    /**
     * 브로커 Port를 수정한다.
     *
     * @param brokerPort 브로커 Port
     */
    public void updateBrokerPort(Integer brokerPort) {
        this.brokerPort = brokerPort;
    }

    /**
     * 브로커 이름을 수정한다.
     *
     * @param brokerName 브로커 이름
     */
    public void updateBrokerName(String brokerName) {
        this.brokerName = brokerName;
    }

    /**
     * 프로토콜 타입을 수정한다.
     *
     * @param protocolType 프로토콜 타입
     */
    public void updateProtocolType(ProtocolType protocolType) {
        this.protocolType = protocolType;
    }
}