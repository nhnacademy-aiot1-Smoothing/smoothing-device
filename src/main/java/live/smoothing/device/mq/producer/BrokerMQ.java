package live.smoothing.device.mq.producer;

public interface BrokerMQ {
    void saveBroker(String brokerIp, Integer brokerPort, Integer brokerId, String protocolType);
    void deleteBroker(Integer brokerId);
}
