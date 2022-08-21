package jialiang_ding.reggie.service.impl;

import jialiang_ding.reggie.TestBase;
import jialiang_ding.reggie.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

class CategoryServiceImplTest extends TestBase {
    @Autowired
    private CategoryService categoryService;



    @Test
    void save() {

    }

    @Test
    void delete() {
        categoryService.delete("1397844303408574465");
    }
}