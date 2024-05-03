package live.smoothing.device.adapter;

import live.smoothing.device.broker.dto.BrokerGenerateRequest;
import live.smoothing.device.sensor.dto.TopicRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Rule Engine Service 와 통신하기 위한 Feign Client
 *
 * @author 우혜승
 */
@FeignClient(value = "rule-engine", path = "/api/ruleengine")
public interface RuleEngineAdapter {

    /**
     * 브로커 추가
     * @param brokerGenerateRequest 브로커 생성 요청
     */
    @PostMapping("/broker")
    void addBroker(@RequestBody BrokerGenerateRequest brokerGenerateRequest);

    /**
     * 브로커 삭제
     * @param brokerId 브로커 ID
     */
    @DeleteMapping("/broker/{brokerId}")
    void deleteBroker(@RequestParam("brokerId") Integer brokerId);

    /**
     * 토픽 추가
     * @param topicRequest 토픽 생성 요청
     */
    @PostMapping("/topic")
    void addTopic(@RequestBody TopicRequest topicRequest);

    /**
     * 토픽 삭제
     * @param topicRequest 토픽 삭제 요청
     */
    @DeleteMapping("/topic")
    void deleteTopic(@RequestBody TopicRequest topicRequest);
}
