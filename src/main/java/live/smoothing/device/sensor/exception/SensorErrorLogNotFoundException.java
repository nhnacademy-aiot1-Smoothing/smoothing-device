package live.smoothing.device.sensor.exception;

import live.smoothing.common.exception.CommonException;
import org.springframework.http.HttpStatus;

public class SensorErrorLogNotFoundException extends CommonException {
    public SensorErrorLogNotFoundException() {
        super(HttpStatus.NOT_FOUND, "센서 에러 로그를 찾을 수 없습니다.");
    }
}
