package live.smoothing.device.controller;

import live.smoothing.device.broker.dto.RuleEngineResponse;
import live.smoothing.device.broker.service.BrokerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/device")
public class DeviceController {
    private final BrokerService brokerService;

    @GetMapping("/initialization")
    public ResponseEntity<List<RuleEngineResponse>> getInitialization() {

        return ResponseEntity.ok(brokerService.getInitBrokers());
    }

}
