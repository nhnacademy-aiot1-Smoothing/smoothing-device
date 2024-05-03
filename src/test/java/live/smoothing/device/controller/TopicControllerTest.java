package live.smoothing.device.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import live.smoothing.device.sensor.dto.*;
import live.smoothing.device.sensor.service.TagService;
import live.smoothing.device.sensor.service.TopicService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.lang.reflect.Constructor;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(TopicController.class)
class TopicControllerTest {

    @MockBean
    private TopicService topicService;

    @MockBean
    private TagService tagService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void addTopic() throws Exception {
        Constructor<TopicAddRequest> constructor = TopicAddRequest.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        TopicAddRequest topicAddRequest = constructor.newInstance();

        mockMvc.perform(post("/api/device/topics")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(topicAddRequest)))
                .andExpect(status().isCreated());
    }

    @Test
    void getTopics() throws Exception {
        int sensorId = 1;
        TopicResponseListResponse topicResponseListResponse = new TopicResponseListResponse(List.of());

        when(topicService.getTopics(eq(sensorId), any())).thenReturn(topicResponseListResponse);

        mockMvc.perform(get("/api/device/topics/" + sensorId))
                .andExpect(status().isOk())
                .andExpect(content().bytes(objectMapper.writeValueAsBytes(topicResponseListResponse)));
    }

    @Test
    void updateTopic() throws Exception {
        int topicId = 1;
        Constructor<TopicAddRequest> constructor = TopicAddRequest.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        TopicAddRequest topicAddRequest = constructor.newInstance();

        mockMvc.perform(put("/api/device/topics/" + topicId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(topicAddRequest)))
                .andExpect(status().isOk());
    }

    @Test
    void deleteTopic() throws Exception {
        int topicId = 1;

        mockMvc.perform(delete("/api/device/topics/" + topicId))
                .andExpect(status().isOk());
    }

    @Test
    void getTopicTypes() throws Exception {
        TopicTypeListResponse topicTypeListResponse = new TopicTypeListResponse(List.of());

        when(topicService.getTopicTypes()).thenReturn(topicTypeListResponse);

        mockMvc.perform(get("/api/device/topics/types"))
                .andExpect(status().isOk())
                .andExpect(content().bytes(objectMapper.writeValueAsBytes(topicTypeListResponse)));
    }

    @Test
    void getTopicsByTagsAndType() throws Exception {
        String userId = "userId";
        List<String> tags = List.of();
        String type = "type";

        TopicListResponse topicListResponse = new TopicListResponse(List.of());

        when(tagService.getTagTopics(eq(userId), eq(tags), eq(type))).thenReturn(topicListResponse);

        mockMvc.perform(get("/api/device/topics")
                        .param("userId", userId)
                        .param("tags", "")
                        .param("type", type))
                .andExpect(status().isOk())
                .andExpect(content().bytes(objectMapper.writeValueAsBytes(topicListResponse)));
    }

    @Test
    void getAllTopicsByType() throws Exception {
        String type = "type";

        TopicListResponse topicListResponse = new TopicListResponse(List.of());

        when(topicService.getAllTopics(eq(type))).thenReturn(topicListResponse);

        mockMvc.perform(get("/api/device/topics/all")
                        .param("type", type))
                .andExpect(status().isOk())
                .andExpect(content().bytes(objectMapper.writeValueAsBytes(topicListResponse)));
    }

    @Test
    void getSensorWithTopics() throws Exception {
        String userId = "userId";
        List<String> tags = List.of();
        String type = "type";

        SensorTopicResponse sensorTopicResponse = new SensorTopicResponse(List.of());

        when(tagService.getSensorWithTopics(eq(userId), eq(tags), eq(type))).thenReturn(sensorTopicResponse);

        mockMvc.perform(get("/api/device/topics/sensors")
                        .param("userId", userId)
                        .param("tags", "")
                        .param("type", type))
                .andExpect(status().isOk())
                .andExpect(content().bytes(objectMapper.writeValueAsBytes(sensorTopicResponse)));
    }
}