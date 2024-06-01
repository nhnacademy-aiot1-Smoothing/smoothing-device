package live.smoothing.device.broker.exception;

import live.smoothing.common.exception.CommonException;
import org.springframework.http.HttpStatus;

/**
 * 이미 존재하는 브로커 예외
 *
 * @author 우혜승
 */
public class AlreadyExistBroker extends CommonException {
    public AlreadyExistBroker() {
        super(HttpStatus.BAD_REQUEST, "이미 존재하는 브로커입니다.");
    }
}
