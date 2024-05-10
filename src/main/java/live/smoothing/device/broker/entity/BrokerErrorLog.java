package live.smoothing.device.broker.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 브로커 에러 로그 엔티티\
 *
 * @author 우혜승
 */
@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "broker_error_logs")
public class BrokerErrorLog {

    @Id
    @Column(name = "broker_error_log_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer brokerErrorLogId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "broker_id")
    private Broker broker;

    @Column(name = "broker_error_type")
    private String brokerErrorType;

    @Column(name = "broker_error_created_at")
    private LocalDateTime brokerErrorCreatedAt;

    @Column(name = "broker_error_solved_at")
    private LocalDateTime brokerErrorSolvedAt;
}
