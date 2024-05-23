package live.smoothing.device.sensor.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class TagListResponse {

    @JsonProperty("tags")
    private List<TagResponse> tags;
}
