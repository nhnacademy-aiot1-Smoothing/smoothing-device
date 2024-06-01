package live.smoothing.device.sensor.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class SensorIdListRequest {
    private List<Integer> sensorIds;
}
