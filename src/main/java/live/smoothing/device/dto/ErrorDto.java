package live.smoothing.device.dto;

import lombok.Getter;

@Getter
public class ErrorDto {
    private String timeStamp;
    private String status;
    private String errorMessage;
    private String path;
}
