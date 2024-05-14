package live.smoothing.device.sensor.service.impl;

import live.smoothing.device.sensor.dto.SensorTopicDto;
import live.smoothing.device.sensor.dto.SensorTopicResponse;
import live.smoothing.device.sensor.dto.TagRequest;
import live.smoothing.device.sensor.dto.TagResponse;
import live.smoothing.device.sensor.entity.Tag;
import live.smoothing.device.sensor.exception.TagAlreadyExistException;
import live.smoothing.device.sensor.exception.TagNotFoundException;
import live.smoothing.device.sensor.exception.TagOwnerException;
import live.smoothing.device.sensor.repository.TagRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TagServiceImplTest {

    @Mock
    private TagRepository tagRepository;

    @InjectMocks
    private TagServiceImpl tagService;

//    @Test
//    void getTagTopics() {
//        String userId = "testUserId";
//        List<String> tags = List.of("testTag1", "testTag2");
//        String type = "testType";
//
//        List<String> topics = List.of("testTopic1", "testTopic2");
//
//        when(tagRepository.getTopicsByUserIdAndTags(userId ,tags, (long) tags.size(), type))
//                .thenReturn(topics);
//
//        assertEquals(topics, tagService.getTagTopics(userId ,tags, type).getTopics());
//    }

//    @Test
//    void getSensorWithTopics() {
//        String userId = "testUserId";
//        List<String> tags = List.of("testTag1", "testTag2");
//        String type = "testType";
//        SensorTopicDto sensorTopicDto1 = mock(SensorTopicDto.class);
//        SensorTopicDto sensorTopicDto2 = mock(SensorTopicDto.class);
//
//        List<SensorTopicDto> topics = List.of(sensorTopicDto1, sensorTopicDto2);
//
//        when(tagRepository.getSensorTopicsByTagsAndType(userId ,tags, type, (long) tags.size()))
//                .thenReturn(topics);
//
//        assertEquals(topics, tagService.getSensorWithTopics(userId ,tags, type).getSensorWithTopics());
//    }

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
        TagRequest tagRequest = mock(TagRequest.class);

        when(tagRequest.getTagName()).thenReturn("testTagName");
        when(tagRepository.existsByUserIdAndTagName(userId, tagRequest.getTagName()))
                .thenReturn(false);

        tagService.addTag(userId, tagRequest);
        verify(tagRepository).save(tagRequest.toEntity(userId));
    }

    @Test
    void getTags() {
        String userId = "testUserId";
        TagResponse tagResponse = mock(TagResponse.class);

        List<TagResponse> tags = List.of(tagResponse);

        when(tagRepository.getByUserId(userId))
                .thenReturn(tags);

        assertEquals(tags, tagService.getTags(userId).getTags());
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
    void updateTag() {
        Integer tagId = 1;
        String userId = "testUserId";
        TagRequest tagRequest = mock(TagRequest.class);

        Tag tag = mock(Tag.class);

        when(tagRepository.findById(tagId))
                .thenReturn(java.util.Optional.of(tag));
        when(tag.getUserId()).thenReturn(userId);
        when(tagRequest.getTagName()).thenReturn("testTagName");

        tagService.updateTag(tagId, userId, tagRequest);
        verify(tag).updateTagName(tagRequest.getTagName());
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

}