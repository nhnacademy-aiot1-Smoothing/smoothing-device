package live.smoothing.device.sensor.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@Builder
@Table(name = "topics")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    public void updateTopic(String topic) {
        this.topic = topic;
    }

    public void updateTopicType(TopicType topicType) {
        this.topicType = topicType;
    }
}