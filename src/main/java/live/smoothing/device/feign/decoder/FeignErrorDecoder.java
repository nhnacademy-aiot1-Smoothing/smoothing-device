package live.smoothing.device.feign.decoder;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import live.smoothing.common.dto.ErrorResponse;
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
            System.out.println(objectMapper.readValue(response.body().asInputStream(), ErrorDto.class));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new CommonException(HttpStatus.valueOf(response.status()), response.reason());
    }
}