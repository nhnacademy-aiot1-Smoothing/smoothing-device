package live.smoothing.device.controller;

import live.smoothing.device.broker.dto.RuleEngineResponse;
import live.smoothing.device.broker.service.BrokerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 디바이스 컨트롤러<br>
 * 디바이스 관련 API를 제공하는 컨트롤러
 *
 * @author 우혜승
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/device")
public class DeviceController {
    private final BrokerService brokerService;

    /**
     * 초기화 API
     *
     * @return 룰 엔진 초기화 응답 HTTP 상태코드
     */
    @GetMapping("/initialization")
    public ResponseEntity<List<RuleEngineResponse>> getInitialization() {

        return ResponseEntity.ok(brokerService.getInitBrokers());
    }

}
