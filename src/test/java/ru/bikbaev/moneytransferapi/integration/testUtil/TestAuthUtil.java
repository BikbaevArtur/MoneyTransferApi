package ru.bikbaev.moneytransferapi.integration.testUtil;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.bikbaev.moneytransferapi.dto.request.LoginRequest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@Component

public class TestAuthUtil {
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    public TestAuthUtil(MockMvc mockMvc, ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    public String loginAndGetToken(LoginRequest loginRequest) throws Exception {
        MvcResult result = mockMvc.perform(post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest))
        ).andReturn();

        String response = result.getResponse().getContentAsString();
        return objectMapper.readTree(response).get("token").asText();
    }
}
