package live.smoothing.device.controller;

import live.smoothing.device.sensor.dto.*;
import live.smoothing.device.sensor.service.SensorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 센서 컨트롤러<br>
 * 센서 관련 API를 제공하는 컨트롤러
 *
 * @author 우혜승
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/device/sensors")
public class SensorController {
    private final SensorService sensorService;

    /**
     * 센서 추가 APIS
     *
     * @param sensorRegisterRequest 센서 추가 요청
     * @return HTTP 상태코드
     */
    @PostMapping
    public ResponseEntity<Void> addSensor(@RequestBody SensorRegisterRequest sensorRegisterRequest) {
        sensorService.saveSensor(sensorRegisterRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 센서 조회 API
     *
     * @param brokerId 브로커 ID
     * @param pageable 페이징 정보
     * @return 센서 리스트 응답 HTTP 상태코드
     */
    @GetMapping("/{brokerId}")
    public ResponseEntity<SensorListResponse> getSensor(@PathVariable("brokerId") Integer brokerId,
                                                        Pageable pageable) {
        return ResponseEntity.ok(sensorService.getSensors(brokerId, pageable));
    }

    /**
     * 센서 수정 API
     *
     * @param sensorId 센서 ID
     * @param sensorUpdateRequest 센서 수정 요청
     * @return HTTP 상태코드
     */
    @PutMapping("/{sensorId}")
    public ResponseEntity<Void> updateSensor(@PathVariable("sensorId") Integer sensorId,
                                             @RequestBody SensorUpdateRequest sensorUpdateRequest) {
        sensorService.updateSensor(sensorId, sensorUpdateRequest);
        return ResponseEntity.ok().build();
    }

    /**
     * 센서 삭제 API
     *
     * @param sensorId 센서 ID
     * @return HTTP 상태코드
     */
    @DeleteMapping("/{sensorId}")
    public ResponseEntity<Void> deleteSensor(@PathVariable("sensorId") Integer sensorId) {
        sensorService.deleteSensor(sensorId);
        return ResponseEntity.ok().build();
    }

    /**
     * 센서 에러 조회 API
     *
     * @param pageable 페이징 정보
     * @return 센서 에러 리스트 응답 HTTP 상태코드
     */
    @GetMapping("/errors")
    public ResponseEntity<SensorErrorListResponse> getErrors(Pageable pageable) {
        return ResponseEntity.ok(sensorService.getSensorErrors(pageable));
    }

    /**
     * 센서 에러 삭제 API
     *
     * @param sensorErrorId 삭제할 센서 에러 ID
     * @return HTTP 상태코드
     */
    @DeleteMapping("/errors/{sensorErrorId}")
    public ResponseEntity<Void> deleteError(@PathVariable("sensorErrorId") Integer sensorErrorId) {
        sensorService.deleteSensorError(sensorErrorId);
        return ResponseEntity.ok().build();
    }

    /**
     * 센서 타입 목록 조회 API
     *
     * @return 센서 타입 리스트 응답 HTTP 상태코드
     */
    @GetMapping("/types")
    public ResponseEntity<SensorTypeListResponse> getSensorTypes() {
        return ResponseEntity.ok(sensorService.getSensorTypes());
    }
}
