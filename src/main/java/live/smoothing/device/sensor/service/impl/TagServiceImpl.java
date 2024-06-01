package live.smoothing.device.sensor.service.impl;

import live.smoothing.device.aop.annotation.Flagging;
import live.smoothing.device.sensor.dao.SensorTagDao;
import live.smoothing.device.sensor.dto.*;
import live.smoothing.device.sensor.entity.Sensor;
import live.smoothing.device.sensor.entity.SensorTag;
import live.smoothing.device.sensor.entity.Tag;
import live.smoothing.device.sensor.exception.SensorTagAlreadyExistException;
import live.smoothing.device.sensor.exception.TagAlreadyExistException;
import live.smoothing.device.sensor.exception.TagNotFoundException;
import live.smoothing.device.sensor.exception.TagOwnerException;
import live.smoothing.device.sensor.repository.CustomTagRepository;
import live.smoothing.device.sensor.repository.SensorRepository;
import live.smoothing.device.sensor.repository.SensorTagRepository;
import live.smoothing.device.sensor.repository.TagRepository;
import live.smoothing.device.sensor.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import static java.util.stream.Collectors.*;

/**
 * 태그 서비스 구현체<br>
 * 태그와 관련된 비즈니스 로직을 처리한다.
 *
 * @author 우혜승
 */
@Service("tagService")
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private final CustomTagRepository customTagRepository;
    private final SensorTagRepository sensorTagRepository;
    private final SensorRepository sensorRepository;

    /**
     * @inheritDoc
     */
    @Override
    public TopicListResponse getTagTopics(String userId ,List<String> tags, String type) {
        return new TopicListResponse(customTagRepository.getTopicsByUserIdAndTags(userId ,tags, (long) tags.size(), type));
    }

    /**
     * @inheritDoc
     */
    @Override
    public SensorTopicResponse getSensorWithTopics(String userId ,List<String> tags, String type) {
        return new SensorTopicResponse(customTagRepository.getSensorTopicsByTagsAndType(userId ,tags, type, (long) tags.size()));
    }

    /**
     * @inheritDoc
     */
    @Override
    public void addTag(String userId, TagRequest tagRequest) {
        if(tagRepository.existsByUserIdAndTagName(userId, tagRequest.getTagName())) {
            throw new TagAlreadyExistException();
        }
        tagRepository.save(tagRequest.toEntity(userId));
    }

    /**
     * @inheritDoc
     */
    @Override
    public TagListResponse getTags(String userId) {
        return new TagListResponse(tagRepository.getByUserId(userId));
    }

    /**
     * @inheritDoc
     */
    @Override
    public void updateTag(Integer tagId, String userId, TagRequest tagRequest) {
        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(TagNotFoundException::new);
        if(!tag.getUserId().equals(userId)) {
            throw new TagOwnerException();
        }
        tag.updateTagName(tagRequest.getTagName());
        tagRepository.save(tag);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void deleteTag(String userId, Integer tagId) {
        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(TagNotFoundException::new);
        if(!tag.getUserId().equals(userId)) {
            throw new TagOwnerException();
        }
        tagRepository.delete(tag);
    }

    @Override
    public SensorTagsResponse getSensorTags(String userId, List<Integer> sensorIds) {
        return new SensorTagsResponse(customTagRepository.getSensorTags(userId, sensorIds).stream().collect(groupingBy(SensorTagDao::getSensorId,mapping(sensorTagDao -> new TagResponse(sensorTagDao.getTagId(), sensorTagDao.getTagName()),toList()))));
    }

    @Override
    public void addSensorTag(String userId, SensorTagAddRequest sensorTagAddRequest) {
        if(sensorTagRepository.existsByTagTagIdAndSensorSensorId(sensorTagAddRequest.getTagId(), sensorTagAddRequest.getSensorId())) {
            throw new SensorTagAlreadyExistException();
        }
        SensorTag sensorTag = SensorTag.builder()
                .tag(tagRepository.getReferenceById(sensorTagAddRequest.getTagId()))
                .sensor(sensorRepository.getReferenceById(sensorTagAddRequest.getSensorId()))
                .build();
        sensorTagRepository.save(sensorTag);
    }

    @Override
    public void removeSensorTag(String userId, Integer sensorId, Integer tagId) {

        SensorTag sensorTag = sensorTagRepository.findBySensorSensorIdAndTagTagId(sensorId, tagId);

        if(Objects.isNull(sensorTag)) {
            throw new TagNotFoundException();
        }

        if(!sensorTag.getTag().getUserId().equals(userId)) {
            throw new TagOwnerException();
        }
        sensorTagRepository.delete(sensorTag);
    }
}
