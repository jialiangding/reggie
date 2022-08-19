package jialiang_ding.reggie.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import jialiang_ding.reggie.TestBase;
import jialiang_ding.reggie.entity.req.CategorySaveReq;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class CategoryControllerTest  extends TestBase {

    @Test
    void save() throws Exception {
        CategorySaveReq categorySaveReq=new CategorySaveReq();
        categorySaveReq.setName("qw");
        categorySaveReq.setSort(0);
        categorySaveReq.setType(2);

        MvcResult mvcResult = this.getMvc().perform(getPostRequest("/category", categorySaveReq)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        log.info(contentAsString);
    }
}