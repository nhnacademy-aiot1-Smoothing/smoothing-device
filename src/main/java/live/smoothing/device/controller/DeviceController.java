package live.smoothing.device.controller;

import live.smoothing.device.broker.dto.BrokerResponse;
import live.smoothing.device.broker.entity.Broker;
import live.smoothing.device.broker.service.BrokerService;
import live.smoothing.device.sensor.entity.Sensor;
import live.smoothing.device.sensor.entity.Topic;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/device")
public class DeviceController {
    private final BrokerService brokerService;

    @GetMapping("/initialization")
    public ResponseEntity<List<BrokerResponse>> getInitialization() {
        List<Broker> brokers = brokerService.getBrokers();
        List<BrokerResponse> responses = new LinkedList<>();
        for(Broker broker : brokers) {
            responses.add(BrokerResponse.builder()
                    .id(broker.getBrokerId())
                    .ip(broker.getBrokerIp())
                    .port(broker.getBrokerPort())
                    .protocolType(broker.getProtocolType().getProtocolType())
                    .topics(broker.getSensors().stream().flatMap(sensor -> sensor.getTopics().stream())
                            .map(Topic::getTopic)
                            .collect(Collectors.toList()))
                    .build());
        }
        return ResponseEntity.ok(responses);
    }
}
