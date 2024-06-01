package live.smoothing.device.sensor.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 토픽 엔티티 클래스
 *
 * @author 우혜승
 */
@Getter
@Entity
@Builder
@Table(name = "topics")
@AllArgsConstructor
@NoArgsConstructor
public class Topic {

    @Id
    @Column(name = "topic_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer topicId;

    @Column(name = "topic")
    private String topic;

    @Column(name = "topic_registered_at")
    private LocalDateTime topicRegisteredAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sensor_id")
    private Sensor sensor;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "topic_type")
    private TopicType topicType;

    /**
     * 토픽 정보를 수정
     * @param topic 수정할 토픽
     */
    public void updateTopic(String topic) {
        this.topic = topic;
    }

    /**
     * 토픽 타입을 수정
     * @param topicType 수정할 토픽 타입
     */
    public void updateTopicType(TopicType topicType) {
        this.topicType = topicType;
    }
}