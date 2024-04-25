package live.smoothing.device.mq.producer;

public interface SensorMQ {
    void saveTopic(Integer brokerId ,String topic);
    void deleteTopic(Integer brokerId, String topic);
}
