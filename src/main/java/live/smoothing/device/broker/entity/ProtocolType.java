package live.smoothing.device.broker.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 프로토콜 타입 엔티티
 *
 * @author 우혜승
 */
@Getter
@Entity
@Table(name = "protocol_types")
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProtocolType {

    @Id
    @Column(name = "protocol_type")
    private String protocolType;

}