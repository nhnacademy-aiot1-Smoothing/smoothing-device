package live.smoothing.device.sensor.service.impl;

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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TagServiceImplTest {

    @Mock
    private TagRepository tagRepository;

    @Mock
    private CustomTagRepository customTagRepository;

    @Mock
    private SensorTagRepository sensorTagRepository;

    @Mock
    private SensorRepository sensorRepository;

    @InjectMocks
    private TagServiceImpl tagService;

    @Test
    void getTagTopics() {
        String userId = "testUserId";
        List<String> tags = List.of("testTag1", "testTag2");
        String type = "testType";

        List<String> topics = List.of("testTopic1", "testTopic2");

        when(customTagRepository.getTopicsByUserIdAndTags(userId ,tags, (long) tags.size(), type))
                .thenReturn(topics);

        assertEquals(topics, ReflectionTestUtils.getField(tagService.getTagTopics(userId ,tags, type), "topics"));
    }

    @Test
    void getSensorWithTopics() {
        String userId = "testUserId";
        List<String> tags = List.of("testTag1", "testTag2");
        String type = "testType";
        SensorTopicDto sensorTopicDto1 = mock(SensorTopicDto.class);
        SensorTopicDto sensorTopicDto2 = mock(SensorTopicDto.class);

        List<SensorTopicDto> topics = List.of(sensorTopicDto1, sensorTopicDto2);

        when(customTagRepository.getSensorTopicsByTagsAndType(userId ,tags, type, (long) tags.size()))
                .thenReturn(topics);

        assertEquals(topics, ReflectionTestUtils.getField(tagService.getSensorWithTopics(userId ,tags, type), "sensorWithTopics"));
    }

    @Test
    void addTag_TagAlreadyExistException() {
        String userId = "testUserId";
        TagRequest tagRequest = mock(TagRequest.class);

        when(tagRequest.getTagName()).thenReturn("testTagName");
        when(tagRepository.existsByUserIdAndTagName(userId, tagRequest.getTagName()))
                .thenReturn(true);

        assertThrows(TagAlreadyExistException.class, () -> tagService.addTag(userId, tagRequest));
    }

    @Test
    void addTag() {
        String userId = "testUserId";
        TagRequest tagRequest = new TagRequest();

        ReflectionTestUtils.setField(tagRequest, "tagName", "testTagName");

        when(tagRepository.existsByUserIdAndTagName(userId, tagRequest.getTagName()))
                .thenReturn(false);

        tagService.addTag(userId, tagRequest);
    }

    @Test
    void getTags() {
        String userId = "testUserId";
        TagResponse tagResponse = mock(TagResponse.class);

        List<TagResponse> tags = List.of(tagResponse);

        when(tagRepository.getByUserId(userId))
                .thenReturn(tags);

        assertEquals(tags, ReflectionTestUtils.getField(tagService.getTags(userId), "tags"));
    }

    @Test
    void updateTag_TagNotFoundException() {
        Integer tagId = 1;
        String userId = "testUserId";
        TagRequest tagRequest = mock(TagRequest.class);

        when(tagRepository.findById(tagId))
                .thenReturn(java.util.Optional.empty());

        assertThrows(TagNotFoundException.class, () -> tagService.updateTag(tagId, userId, tagRequest));
    }

    @Test
    void updateTag_TagOwnerException() {
        Integer tagId = 1;
        String userId = "testUserId";
        TagRequest tagRequest = mock(TagRequest.class);
        TagResponse tagResponse = mock(TagResponse.class);

        Tag tag = mock(Tag.class);

        when(tagRepository.findById(tagId))
                .thenReturn(java.util.Optional.of(tag));
        when(tag.getUserId()).thenReturn("otherUserId");

        assertThrows(TagOwnerException.class, () -> tagService.updateTag(tagId, userId, tagRequest));
    }

    @Test
    void updateTag_TagAlreadyExistException() {
        Integer tagId = 1;
        String userId = "testUserId";
        TagRequest tagRequest = mock(TagRequest.class);
        TagResponse tagResponse = mock(TagResponse.class);

        Tag tag = mock(Tag.class);

        when(tag.getTagName()).thenReturn("testTagName2");
        when(tagRepository.findById(tagId))
                .thenReturn(java.util.Optional.of(tag));
        when(tag.getUserId()).thenReturn(userId);
        when(tagRequest.getTagName()).thenReturn("testTagName");

        when(tagRepository.existsByUserIdAndTagName(userId, tagRequest.getTagName()))
                .thenReturn(true);

        assertThrows(TagAlreadyExistException.class, () -> tagService.updateTag(tagId, userId, tagRequest));
    }

    @Test
    void updateTag() {
        Integer tagId = 1;
        String userId = "testUserId";
        TagRequest tagRequest = mock(TagRequest.class);

        Tag tag = new Tag();

        ReflectionTestUtils.setField(tag, "userId", userId);

        when(tagRepository.findById(tagId))
                .thenReturn(java.util.Optional.of(tag));
        when(tagRequest.getTagName()).thenReturn("testTagName");
        when(tagRepository.existsByUserIdAndTagName(userId, tagRequest.getTagName()))
                .thenReturn(false);

        tagService.updateTag(tagId, userId, tagRequest);
        verify(tagRepository).save(tag);
    }

    @Test
    void deleteTag_TagNotFoundException() {
        Integer tagId = 1;
        String userId = "testUserId";

        when(tagRepository.findById(tagId))
                .thenReturn(java.util.Optional.empty());

        assertThrows(TagNotFoundException.class, () -> tagService.deleteTag(userId, tagId));
    }

    @Test
    void deleteTag_TagOwnerException() {
        Integer tagId = 1;
        String userId = "testUserId";
        TagResponse tagResponse = mock(TagResponse.class);

        Tag tag = mock(Tag.class);

        when(tagRepository.findById(tagId))
                .thenReturn(java.util.Optional.of(tag));
        when(tag.getUserId()).thenReturn("otherUserId");

        assertThrows(TagOwnerException.class, () -> tagService.deleteTag(userId, tagId));
    }

    @Test
    void deleteTag() {
        Integer tagId = 1;
        String userId = "testUserId";
        TagResponse tagResponse = mock(TagResponse.class);

        Tag tag = mock(Tag.class);

        when(tagRepository.findById(tagId))
                .thenReturn(java.util.Optional.of(tag));
        when(tag.getUserId()).thenReturn(userId);

        tagService.deleteTag(userId, tagId);
        verify(tagRepository).delete(tag);
    }

    @Test
    void getSensorTags() {
        SensorTagDao sensorTagDao = new SensorTagDao(1, 1, "testTagName");

        when(customTagRepository.getSensorTags("testUserId", List.of(1)))
                .thenReturn(List.of(sensorTagDao));

        SensorTagsResponse sensorTagsResponse = tagService.getSensorTags("testUserId", List.of(1));

        assertTrue(ReflectionTestUtils.getField(((Map<Integer, List<TagResponse>>) ReflectionTestUtils.getField(sensorTagsResponse, "sensorTags")).get(1).get(0), "tagId").equals(1));
    }

    @Test
    void addSensorTag_alreadyExist_throwError(){
        String userId = "testUserId";
        SensorTagAddRequest sensorTagAddRequest = new SensorTagAddRequest();

        when(sensorTagRepository.existsByTagTagIdAndSensorSensorId(sensorTagAddRequest.getTagId(), sensorTagAddRequest.getSensorId()))
                .thenReturn(true);

        assertThrows(SensorTagAlreadyExistException.class, () -> tagService.addSensorTag(userId, sensorTagAddRequest));
    }

    @Test
    void addSensorTag(){
        String userId = "testUserId";
        SensorTagAddRequest sensorTagAddRequest = new SensorTagAddRequest();

        when(sensorTagRepository.existsByTagTagIdAndSensorSensorId(sensorTagAddRequest.getTagId(), sensorTagAddRequest.getSensorId()))
                .thenReturn(false);

        Tag tag = Tag.builder()
                .userId(userId)
                .build();

        when(tagRepository.findById(sensorTagAddRequest.getTagId()))
                .thenReturn(Optional.ofNullable(tag));

        when(sensorRepository.findById(sensorTagAddRequest.getSensorId()))
                .thenReturn(Optional.ofNullable(mock(Sensor.class)));

        tagService.addSensorTag(userId, sensorTagAddRequest);
        verify(sensorTagRepository).save(any());
    }

    @Test
    void removeSensorTag_notExistSensor_throwError(){
        String userId = "testUserId";
        Integer sensorId = 1;
        Integer tagId = 1;

        when(sensorTagRepository.findBySensorSensorIdAndTagTagId(sensorId, tagId))
                .thenReturn(null);

        assertThrows(TagNotFoundException.class, () -> tagService.removeSensorTag(userId, sensorId, tagId));
    }

    @Test
    void removeSensorTag_notOwner_throwError(){
        String userId = "testUserId";
        Integer sensorId = 1;
        Integer tagId = 1;

        Tag tag = Tag.builder()
                .userId("otherUserId")
                .build();

        SensorTag sensorTag = SensorTag.builder()
                .tag(tag)
                .build();

        when(sensorTagRepository.findBySensorSensorIdAndTagTagId(sensorId, tagId))
                .thenReturn(sensorTag);

        assertThrows(TagOwnerException.class, () -> tagService.removeSensorTag(userId, sensorId, tagId));
    }

    @Test
    void removeSensorTag(){
        String userId = "testUserId";
        Integer sensorId = 1;
        Integer tagId = 1;

        Tag tag = Tag.builder()
                .userId(userId)
                .build();

        SensorTag sensorTag = SensorTag.builder()
                .tag(tag)
                .build();

        when(sensorTagRepository.findBySensorSensorIdAndTagTagId(sensorId, tagId))
                .thenReturn(sensorTag);

        tagService.removeSensorTag(userId, sensorId, tagId);
        verify(sensorTagRepository).delete(sensorTag);
    }
}