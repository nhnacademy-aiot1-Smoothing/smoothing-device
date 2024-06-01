package live.smoothing.device.broker.repository;

import live.smoothing.device.broker.dto.BrokerErrorResponse;
import live.smoothing.device.broker.entity.BrokerErrorLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * 브로커 에러 로그 레포지토리
 *
 * @author 우혜승
 */
public interface BrokerErrorLogRepository extends JpaRepository<BrokerErrorLog, Integer> {

    /**
     * 모든 에러 로그 조회
     *
     * @param pageable 페이징 정보
     * @return 에러 로그 페이지
     */
    @Query("SELECT new live.smoothing.device.broker.dto.BrokerErrorResponse(be.brokerErrorLogId, be.brokerErrorType, be.broker.brokerName, be.brokerErrorCreatedAt, be.brokerErrorSolvedAt, b.brokerIp, b.brokerPort) " +
            "FROM BrokerErrorLog be " +
            "JOIN be.broker b")
    Page<BrokerErrorResponse> getAllErrors(Pageable pageable);
}
