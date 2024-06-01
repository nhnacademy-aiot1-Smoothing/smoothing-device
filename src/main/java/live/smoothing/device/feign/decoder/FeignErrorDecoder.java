package live.smoothing.device.feign.decoder;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import live.smoothing.common.exception.CommonException;
import live.smoothing.device.dto.ErrorDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.io.IOException;

@RequiredArgsConstructor
public class FeignErrorDecoder implements ErrorDecoder {

    private final ObjectMapper objectMapper;

    @Override
    public Exception decode(String s, Response response) {
        try {
            return new CommonException(HttpStatus.valueOf(response.status()), objectMapper.readValue(response.body().asInputStream(), ErrorDto.class).getErrorMessage());
        } catch (IOException e) {
            throw new CommonException(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류 발생");
        }
    }
}