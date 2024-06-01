package live.smoothing.device.controller;

import live.smoothing.device.broker.dto.*;
import live.smoothing.device.broker.service.BrokerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 브로커 컨트롤러<br>
 * 브로커 관련 API를 제공하는 컨트롤러
 *
 * @author 우혜승
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/device/brokers")
public class BrokerController {
    private final BrokerService brokerService;

    /**
     * 브로커 추가 API
     *
     * @param brokerAddRequest 브로커 추가 요청
     * @return HTTP 상태코드
     */
    @PostMapping
    public ResponseEntity<Void> addBroker(@RequestBody BrokerAddRequest brokerAddRequest) {
        brokerService.addBroker(brokerAddRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 브로커 조회 API
     *
     * @param pageable 페이징 정보
     * @return 브로커 리스트 응답 HTTP 상태코드
     */
    @GetMapping
    public ResponseEntity<BrokerListResponse> getBrokers(Pageable pageable) {
        return ResponseEntity.ok(brokerService.getBrokers(pageable));
    }

    /**
     * 브로커 수정 API
     *
     * @param brokerId 브로커 ID
     * @param brokerUpdateRequest 브로커 수정 요청
     * @return HTTP 상태코드
     */
    @PutMapping("/{brokerId}")
    public ResponseEntity<Void> updateBroker(@PathVariable("brokerId") Integer brokerId,
                                             @RequestBody BrokerUpdateRequest brokerUpdateRequest) {
        brokerService.updateBroker(brokerId, brokerUpdateRequest);
        return ResponseEntity.ok().build();
    }

    /**
     * 브로커 삭제 API
     *
     * @param brokerId 삭제할 브로커 ID
     * @return HTTP 상태코드
     */
    @DeleteMapping("/{brokerId}")
    public ResponseEntity<Void> deleteBroker(@PathVariable("brokerId") Integer brokerId) {
        brokerService.deleteBroker(brokerId);
        return ResponseEntity.ok().build();
    }

    /**
     * 브로커 에러 조회 API
     *
     * @param pageable 페이징 정보
     * @return 브로커 에러 리스트 응답 HTTP 상태코드
     */
    @GetMapping("/errors")
    public ResponseEntity<BrokerErrorListResponse> getErrors(Pageable pageable) {
        return ResponseEntity.ok(brokerService.getErrors(pageable));
    }

    /**
     * 브로커 에러 삭제 API
     *
     * @param brokerErrorId 삭제할 브로커 에러 ID
     * @return HTTP 상태코드
     */
    @DeleteMapping("/errors/{brokerErrorId}")
    public ResponseEntity<Void> deleteError(@PathVariable("brokerErrorId") Integer brokerErrorId) {
        brokerService.deleteError(brokerErrorId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/protocols")
    public ResponseEntity<ProtocolTypeResponse> getProtocolTypes() {
        return ResponseEntity.ok(brokerService.getProtocolTypes());
    }

}
