package live.smoothing.device.sensor.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
public class TagResponse {
    @JsonProperty("tagId")
    private Integer tagId;
    @JsonProperty("tagName")
    private String tagName;
}
