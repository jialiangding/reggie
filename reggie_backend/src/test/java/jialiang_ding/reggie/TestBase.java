package jialiang_ding.reggie;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Map;
import java.util.UUID;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ReggieApplication.class)
public class TestBase {

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    private MockMvc mvc;

    public MockMvc getMvc() {
        return mvc;
    }

    protected void setup(Object controller) {
        MockitoAnnotations.initMocks(this);
        mvc= MockMvcBuilders.standaloneSetup(controller).build();
    }



    public static MockHttpServletRequestBuilder getGetRequest(String url, Map<String, Object> keyMap) {

        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, Object> entry : keyMap.entrySet()) {
            builder.append(entry.getKey())
                    .append('=')
                    .append(entry.getValue())
                    .append('&');
        }
        builder.deleteCharAt(builder.length() - 1);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(url.concat("?").concat(builder.toString()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        return request;

    }


    /**
     * web项目上下文
     */
    public static MockHttpServletRequestBuilder getPostRequest(String url, Object body) throws JsonProcessingException {

        ObjectMapper mapper = Jackson2ObjectMapperBuilder.json().build();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        //writeValueAsString 将传入的对象序列化为json，返回给调用者
        String content = mapper.writeValueAsString(body);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content);
        return request;

    }

}
