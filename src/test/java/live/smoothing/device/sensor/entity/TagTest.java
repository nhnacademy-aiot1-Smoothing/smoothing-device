package live.smoothing.device.sensor.entity;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TagTest {

    private final Tag tag = Tag.builder()
            .sensorTags(List.of())
            .build();

    @Test
    void getSensorTags() {
        assertNotNull(tag.getSensorTags());
    }
}