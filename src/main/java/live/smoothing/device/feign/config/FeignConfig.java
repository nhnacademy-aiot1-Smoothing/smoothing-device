package live.smoothing.device.feign.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.codec.ErrorDecoder;
import live.smoothing.device.feign.decoder.FeignErrorDecoder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class FeignConfig {

    private final ObjectMapper objectMapper;

    @Bean
    public ErrorDecoder feignErrorDecoder() {
        return new FeignErrorDecoder(objectMapper);
    }

}
