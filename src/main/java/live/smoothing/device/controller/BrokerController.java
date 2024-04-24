package live.smoothing.device.controller;

import live.smoothing.device.broker.dto.BrokerErrorListResponse;
import live.smoothing.device.broker.dto.BrokerListResponse;
import live.smoothing.device.broker.dto.BrokerUpdateRequest;
import live.smoothing.device.broker.service.BrokerService;
import live.smoothing.device.broker.dto.BrokerAddRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/device/brokers")
public class BrokerController {
    private final BrokerService brokerService;

    @PostMapping
    public ResponseEntity<Void> addBroker(@RequestBody BrokerAddRequest brokerAddRequest) {
        brokerService.addBroker(brokerAddRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<BrokerListResponse> getBrokers() {
        return ResponseEntity.ok(brokerService.getBrokers());
    }

    @PutMapping("/{brokerId}")
    public ResponseEntity<Void> updateBroker(@PathVariable("brokerId") Integer brokerId,
                                             @RequestBody BrokerUpdateRequest brokerUpdateRequest) {
        brokerService.updateBroker(brokerId, brokerUpdateRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{brokerId}")
    public ResponseEntity<Void> deleteBroker(@PathVariable("brokerId") Integer brokerId) {
        brokerService.deleteBroker(brokerId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/errors")
    public ResponseEntity<BrokerErrorListResponse> getErrors() {
        return ResponseEntity.ok(brokerService.getErrors());
    }

    @DeleteMapping("/errors/{brokerErrorId}")
    public ResponseEntity<Void> deleteError(@PathVariable("brokerErrorId") Integer brokerErrorId) {
        brokerService.deleteError(brokerErrorId);
        return ResponseEntity.ok().build();
    }

}
