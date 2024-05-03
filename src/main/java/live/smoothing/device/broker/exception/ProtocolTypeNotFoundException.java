package live.smoothing.device.broker.exception;

import live.smoothing.common.exception.CommonException;
import org.springframework.http.HttpStatus;

/**
 * 프로토콜 타입을 찾을 수 없을 때 발생하는 예외
 *
 * @author 우혜승
 */
public class ProtocolTypeNotFoundException extends CommonException {
    public ProtocolTypeNotFoundException() {
        super(HttpStatus.NOT_FOUND, "존재하지 않는 프로토콜 타입입니다.");
    }
}
