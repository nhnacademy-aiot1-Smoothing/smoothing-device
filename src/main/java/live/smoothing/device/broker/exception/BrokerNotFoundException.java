package live.smoothing.device.broker.exception;

import live.smoothing.common.exception.CommonException;
import org.springframework.http.HttpStatus;

public class BrokerNotFoundException extends CommonException {
    public BrokerNotFoundException() {
        super(HttpStatus.NOT_FOUND, "브로커를 찾을 수 없습니다.");
    }
}
