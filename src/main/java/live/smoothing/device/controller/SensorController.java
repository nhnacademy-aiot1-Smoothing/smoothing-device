package live.smoothing.device.controller;

import live.smoothing.device.sensor.dto.*;
import live.smoothing.device.sensor.service.SensorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/device/sensors")
public class SensorController {
    private final SensorService sensorService;

    @PostMapping
    public ResponseEntity<Void> addSensor(@RequestBody SensorRegisterRequest sensorRegisterRequest) {
        sensorService.saveSensor(sensorRegisterRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{brokerId}")
    public ResponseEntity<SensorListResponse> getSensor(@PathVariable("brokerId") Integer brokerId) {
        return ResponseEntity.ok(sensorService.getSensors(brokerId));
    }

    @PutMapping("/{sensorId}")
    public ResponseEntity<Void> updateSensor(@PathVariable("sensorId") Integer sensorId,
                                             @RequestBody SensorUpdateRequest sensorUpdateRequest) {
        sensorService.updateSensor(sensorId, sensorUpdateRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{sensorId}")
    public ResponseEntity<Void> deleteSensor(@PathVariable("sensorId") Integer sensorId) {
        sensorService.deleteSensor(sensorId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/errors")
    public ResponseEntity<SensorErrorListResponse> getErrors() {
        return ResponseEntity.ok(sensorService.getSensorErrors());
    }

    @DeleteMapping("/errors/{sensorErrorId}")
    public ResponseEntity<Void> deleteError(@PathVariable("sensorErrorId") Integer sensorErrorId) {
        sensorService.deleteSensorError(sensorErrorId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/types")
    public ResponseEntity<SensorTypeListResponse> getSensorTypes() {
        return ResponseEntity.ok(sensorService.getSensorTypes());
    }
}
