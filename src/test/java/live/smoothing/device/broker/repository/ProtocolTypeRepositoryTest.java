package live.smoothing.device.broker.repository;

import live.smoothing.device.broker.entity.ProtocolType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("dev")
class ProtocolTypeRepositoryTest {

    @Autowired
    private ProtocolTypeRepository protocolTypeRepository;

    private ProtocolType protocolType;

    @BeforeEach
    void setUp() {
        protocolType = new ProtocolType("testProtocolType");
    }

    @Test
    void save() {
        ProtocolType result = protocolTypeRepository.save(protocolType);
        assertNotNull(result);
    }

    @Test
    void findById() {
        protocolTypeRepository.save(protocolType);
        Optional<ProtocolType> savedProtocolType = protocolTypeRepository.findById("testProtocolType");

        assertTrue(savedProtocolType.isPresent());
        assertEquals(protocolType.getProtocolType(), savedProtocolType.get().getProtocolType());
    }
}