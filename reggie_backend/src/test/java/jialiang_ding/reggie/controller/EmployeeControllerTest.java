package jialiang_ding.reggie.controller;

import jialiang_ding.reggie.TestBase;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.HashMap;
import java.util.Map;


@Slf4j
public class EmployeeControllerTest extends TestBase {

    @InjectMocks
    private  EmployeeController employeeController;

    @Test
    public void testHelloworld() {



    }

    @Test
    public void testSave() {
    }

    @Test
    public void testPage() throws Exception {
        Map<String, Object> keyMap = new HashMap<>();
        keyMap.put("page", 1L);
        keyMap.put("pageSize", 10L);
        MvcResult mvcResult = this.getMvc().perform(getGetRequest("/employee/page", keyMap))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
//        MockHttpServletResponse response = mvcResult.getResponse();
//        log.info(response.toString());
    }
}