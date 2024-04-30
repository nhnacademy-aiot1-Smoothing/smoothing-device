package live.smoothing.device.broker.service;

import live.smoothing.device.broker.dto.*;
import live.smoothing.device.mq.dto.BrokerErrorRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 브로커 서비스
 * <p>
 * 브로커 관련 비즈니스 로직을 처리하는 서비스
 *
 * @author 우혜승
 */
public interface BrokerService {

    /**
     * Rule Engine Service를 초기화하기 위한 브로커 정보 조회
     * @return 브로커 정보 리스트
     */
    List<RuleEngineResponse> getInitBrokers();

    /**
     * 브로커 추가
     *
     * @param request 브로커 추가 요청
     */
    void addBroker(BrokerAddRequest request);

    /**
     * 브로커 조회
     * @param pageable 페이징 정보
     * @return 브로커 리스트 응답
     */
    BrokerListResponse getBrokers(Pageable pageable);

    /**
     * 브로커 수정
     * @param brokerId 브로커 ID
     * @param brokerUpdateRequest 브로커 수정 요청
     */
    void updateBroker(Integer brokerId, BrokerUpdateRequest brokerUpdateRequest);

    /**
     * 브로커 삭제
     * @param brokerId 삭제할 브로커 ID
     */
    void deleteBroker(Integer brokerId);

    /**
     * 브로커 에러 조회
     * @param pageable 페이징 정보
     * @return 브로커 에러 리스트 응답
     */
    BrokerErrorListResponse getErrors(Pageable pageable);

    /**
     * 브로커 에러 삭제
     * @param brokerErrorId 삭제할 브로커 에러 ID
     */
    void deleteError(Integer brokerErrorId);

    /**
     * 브로커 에러 추가
     * @param request 브로커 에러 추가 요청
     */
    void addBrokerError(BrokerErrorRequest request);

}
