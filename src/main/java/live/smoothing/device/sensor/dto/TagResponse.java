package live.smoothing.device.sensor.dto;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagResponse {
    private Integer tagId;
    private String tagName;
}
