package live.smoothing.device.broker.exception;

import live.smoothing.common.exception.CommonException;
import org.springframework.http.HttpStatus;

/**
 * 브로커 에러를 찾을 수 없을 때 발생하는 예외
 *
 * @author 우혜승
 */
public class BrokerErrorNotFoundException extends CommonException {
    public BrokerErrorNotFoundException() {
        super(HttpStatus.NOT_FOUND, "해당 에러를 찾을 수 없습니다.");
    }
}
