package live.smoothing.device.broker.exception;

import live.smoothing.common.exception.CommonException;
import org.springframework.http.HttpStatus;

/**
 * 브로커를 찾을 수 없을 때 발생하는 예외
 *
 * @author 우혜승
 */
public class BrokerNotFoundException extends CommonException {
    public BrokerNotFoundException() {
        super(HttpStatus.NOT_FOUND, "브로커를 찾을 수 없습니다.");
    }
}
