package live.smoothing.device.sensor.dto;

import live.smoothing.device.sensor.entity.Tag;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TagRequest {

    private String tagName;

    public Tag toEntity(String userId) {
        return Tag.builder()
                .userId(userId)
                .tagName(tagName)
                .build();
    }
}
