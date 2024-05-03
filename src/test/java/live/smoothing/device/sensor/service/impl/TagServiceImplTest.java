package live.smoothing.device.sensor.service.impl;

import live.smoothing.device.sensor.dto.SensorTopicDto;
import live.smoothing.device.sensor.dto.SensorTopicResponse;
import live.smoothing.device.sensor.repository.TagRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TagServiceImplTest {

    @Mock
    private TagRepository tagRepository;

    @InjectMocks
    private TagServiceImpl tagService;

    @Test
    void getTagTopics() {
        String userId = "testUserId";
        List<String> tags = List.of("testTag1", "testTag2");
        String type = "testType";

        List<String> topics = List.of("testTopic1", "testTopic2");

        when(tagRepository.getTopicsByUserIdAndTags(userId ,tags, (long) tags.size(), type))
                .thenReturn(topics);

        assertEquals(topics, tagService.getTagTopics(userId ,tags, type).getTopics());
    }

    @Test
    void getSensorWithTopics() {
        String userId = "testUserId";
        List<String> tags = List.of("testTag1", "testTag2");
        String type = "testType";
        SensorTopicDto sensorTopicDto1 = mock(SensorTopicDto.class);
        SensorTopicDto sensorTopicDto2 = mock(SensorTopicDto.class);

        List<SensorTopicDto> topics = List.of(sensorTopicDto1, sensorTopicDto2);

        when(tagRepository.getSensorTopicsByTagsAndType(userId ,tags, type, (long) tags.size()))
                .thenReturn(topics);

        assertEquals(topics, tagService.getSensorWithTopics(userId ,tags, type).getSensorWithTopics());
    }
}