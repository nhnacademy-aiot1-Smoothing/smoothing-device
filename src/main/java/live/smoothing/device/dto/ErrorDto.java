package live.smoothing.device.dto;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class ErrorDto {
    private String timeStamp;
    private Integer status;
    private String errorMessage;
    private String path;
}
