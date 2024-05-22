package live.smoothing.device.broker.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BrokerErrorResponseTest {

    @Test
    void builder() {
        BrokerErrorResponse brokerErrorResponse = BrokerErrorResponse.builder()
                .createdAt(null)
                .solvedAt(null)
                .build();

        assertNotNull(brokerErrorResponse);
    }
}