package live.smoothing.device.adapter;

import live.smoothing.device.broker.dto.BrokerRequest;
import live.smoothing.device.sensor.dto.TopicRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "rule-engine", path = "/api/ruleengine")
public interface RuleEngineAdapter {
    @PostMapping("/broker")
    ResponseEntity<Void> addBroker(@RequestBody BrokerRequest brokerRequest);

    @DeleteMapping("/broker/{brokerId}")
    ResponseEntity<Void> deleteBroker(@RequestParam("brokerId") Integer brokerId);

    @PostMapping("/topic")
    ResponseEntity<Void> addTopic(@RequestBody TopicRequest topicRequest);

    @DeleteMapping("/topic")
    ResponseEntity<Void> deleteTopic(@RequestBody TopicRequest topicRequest);
}
