package live.smoothing.device.controller;

import live.smoothing.device.broker.dto.BrokerInitResponse;
import live.smoothing.device.broker.entity.Broker;
import live.smoothing.device.broker.service.BrokerService;
import live.smoothing.device.sensor.entity.Topic;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/device")
public class DeviceController {
    private final BrokerService brokerService;

    @GetMapping("/initialization")
    public ResponseEntity<List<BrokerInitResponse>> getInitialization() {

        return ResponseEntity.ok(brokerService.getInitBrokers());
    }

}
