package live.smoothing.device.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import live.smoothing.device.sensor.dto.TagListResponse;
import live.smoothing.device.sensor.dto.TagRequest;
import live.smoothing.device.sensor.service.TagService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(TagController.class)
class TagControllerTest {

    @MockBean
    private TagService tagService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String X_USER_ID = "X-USER-ID";
    private static final String USER_ID = "testUserId";

    @Test
    void addTag() throws Exception {
        TagRequest tagRequest = new TagRequest();

        mockMvc.perform(post("/api/device/tags")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(X_USER_ID, USER_ID)
                        .content(objectMapper.writeValueAsString(tagRequest)))
                .andExpect(status().isCreated());

        verify(tagService).addTag(eq(USER_ID), any(TagRequest.class));
    }

    @Test
    void getTags() throws Exception {
        TagListResponse tagListResponse = new TagListResponse(null);

        when(tagService.getTags(eq(USER_ID))).thenReturn(tagListResponse);

        mockMvc.perform(get("/api/device/tags")
                        .header(X_USER_ID, USER_ID))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(tagListResponse)));
    }

    @Test
    void updateTag() throws Exception{
        int tagId = 1;
        TagRequest tagRequest = new TagRequest();

        mockMvc.perform(put("/api/device/tags/" + tagId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(X_USER_ID, USER_ID)
                        .content(objectMapper.writeValueAsString(tagRequest)))
                .andExpect(status().isOk());

        verify(tagService).updateTag(eq(tagId), eq(USER_ID), any(TagRequest.class));
    }

    @Test
    void deleteTag() {
        int tagId = 1;

        assertDoesNotThrow(() -> mockMvc.perform(delete("/api/device/tags/" + tagId)
                .header(X_USER_ID, USER_ID))
                .andExpect(status().isOk()));

        verify(tagService).deleteTag(eq(USER_ID), eq(tagId));
    }
}