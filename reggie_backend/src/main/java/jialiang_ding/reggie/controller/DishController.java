package jialiang_ding.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jialiang_ding.reggie.common.R;
import jialiang_ding.reggie.entity.Dish;
import jialiang_ding.reggie.entity.Employee;
import jialiang_ding.reggie.service.DishService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dish")
public class DishController {
    @Autowired
    private DishService dishService;


    @GetMapping("/page")
    public R<Page<Dish>> page(@RequestParam(value = "page",required = false,defaultValue = "1")Integer page,
                              @RequestParam(value = "pageSize",required = false,defaultValue = "10")Integer pageSize,
                              @RequestParam(value = "name",required = false,defaultValue = "") String name){
        Page page1=new Page(page,pageSize);

        LambdaQueryWrapper<Dish> queryWrapper=new LambdaQueryWrapper();
        queryWrapper.like(StringUtils.isNotEmpty(name),Dish::getName,name);
        queryWrapper.orderByDesc(Dish::getUpdateTime);
        queryWrapper.eq(Dish::getIsDelete,"0");
        dishService.page(page1,queryWrapper);
        return  R.success(page1);

    }
}
