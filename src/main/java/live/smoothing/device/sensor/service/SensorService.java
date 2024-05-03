package live.smoothing.device.sensor.service;

import live.smoothing.device.mq.dto.SensorErrorRequest;
import live.smoothing.device.sensor.dto.*;
import org.springframework.data.domain.Pageable;

/**
 * 센서 서비스<br>
 * 센서와 관련된 비즈니스 로직을 처리한다.
 *
 * @author 우혜승
 */
public interface SensorService {

    /**
     * 센서를 저장하는 메서드
     *
     * @param sensorRegisterRequest 저장할 센서 정보를 담은 요청 DTO
     */
    void saveSensor(SensorRegisterRequest sensorRegisterRequest);

    /**
     * 센서 목록을 조회하는 메서드
     *
     * @param brokerId 조회할 브로커의 아이디
     * @param pageable 페이지 정보
     * @return 조회된 센서 목록을 담은 응답 DTO
     */
    SensorListResponse getSensors(Integer brokerId, Pageable pageable);

    /**
     * 센서를 수정하는 메서드
     *
     * @param sensorId 수정할 센서의 아이디
     * @param sensorUpdateRequest 수정할 센서 정보를 담은 요청 DTO
     */
    void updateSensor(Integer sensorId, SensorUpdateRequest sensorUpdateRequest);

    /**
     * 센서를 삭제하는 메서드
     *
     * @param sensorId 삭제할 센서의 아이디
     */
    void deleteSensor(Integer sensorId);

    /**
     * 센서 에러 목록을 조회하는 메서드
     *
     * @param pageable 페이지 정보
     * @return 조회된 센서 에러 목록을 담은 응답 DTO
     */
    SensorErrorListResponse getSensorErrors(Pageable pageable);

    /**
     * 센서 에러를 삭제하는 메서드
     *
     * @param sensorErrorId 삭제할 센서 에러의 아이디
     */
    void deleteSensorError(Integer sensorErrorId);

    /**
     * 센서 타입 목록을 조회하는 메서드
     *
     * @return 조회된 센서 타입 목록을 담은 응답 DTO
     */
    SensorTypeListResponse getSensorTypes();

    /**
     * 센서 에러를 추가하는 메서드
     *
     * @param request 센서 에러 정보를 담은 요청 DTO
     */
    void addSensorError(SensorErrorRequest request);
}