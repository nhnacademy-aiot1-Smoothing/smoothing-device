package live.smoothing.device.broker.repository;

import live.smoothing.device.broker.dto.BrokerErrorResponse;
import live.smoothing.device.broker.entity.Broker;
import live.smoothing.device.broker.entity.BrokerErrorLog;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class BrokerErrorLogRepositoryTest {

    @Autowired
    private BrokerErrorLogRepository brokerErrorLogRepository;

    @Autowired
    private TestEntityManager entityManager;

    private Broker broker = Broker.builder()
            .brokerIp("testBrokerIp")
            .brokerPort(1234)
            .brokerName("testBrokerName")
            .build();

    private BrokerErrorLog brokerErrorLog = BrokerErrorLog.builder()
            .brokerErrorType("testBrokerErrorType")
            .brokerErrorSolvedAt(LocalDateTime.now())
            .brokerErrorCreatedAt(LocalDateTime.now())
            .broker(broker)
            .build();

    @BeforeEach
    void setUp() {
        entityManager.persist(broker);
        entityManager.flush();
        entityManager.persist(brokerErrorLog);
    }

    @Test
    void find() {
        BrokerErrorLog result = brokerErrorLogRepository.findById(brokerErrorLog.getBrokerErrorLogId()).orElse(null);

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(brokerErrorLog.getBrokerErrorType(), result.getBrokerErrorType()),
                () -> assertEquals(brokerErrorLog.getBrokerErrorCreatedAt(), result.getBrokerErrorCreatedAt()),
                () -> assertEquals(brokerErrorLog.getBrokerErrorSolvedAt(), result.getBrokerErrorSolvedAt()),
                () -> assertEquals(brokerErrorLog.getBroker().getBrokerName(), result.getBroker().getBrokerName()),
                () -> assertNotNull(result.getBrokerErrorLogId())
        );
    }

    @Test
    void getAllErrors() {
        Page<BrokerErrorResponse> result = brokerErrorLogRepository.getAllErrors(Pageable.ofSize(10));

        assertEquals(1, result.getTotalElements());
    }
}