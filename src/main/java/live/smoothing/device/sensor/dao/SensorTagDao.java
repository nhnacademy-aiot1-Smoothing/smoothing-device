package live.smoothing.device.sensor.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SensorTagDao {
    private Integer sensorId;
    private Integer tagId;
    private String tagName;
}
