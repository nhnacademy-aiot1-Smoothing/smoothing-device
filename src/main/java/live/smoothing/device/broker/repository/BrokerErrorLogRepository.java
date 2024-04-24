package live.smoothing.device.broker.repository;

import live.smoothing.device.broker.dto.BrokerErrorResponse;
import live.smoothing.device.broker.entity.BrokerErrorLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BrokerErrorLogRepository extends JpaRepository<BrokerErrorLog, Integer>{

    @Query("SELECT new live.smoothing.device.broker.dto.BrokerErrorResponse(b.brokerErrorType, b.broker.brokerName, b.brokerErrorCreatedAt, b.brokerErrorSolvedAt) FROM BrokerErrorLog b")
    List<BrokerErrorResponse> getAllErrors();
}
