package jialiang_ding.reggie.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jialiang_ding.reggie.common.R;
import jialiang_ding.reggie.entity.Setmeal;
import jialiang_ding.reggie.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("setmeal")
public class SetmealController {

    @Autowired
    private SetmealService setmealService;





    @GetMapping("/page")
    public R<Page<Setmeal>> page(@RequestParam(value = "page",required = false,defaultValue = "1")Integer page,
                                 @RequestParam(value = "pageSize",required = false,defaultValue = "10")Integer pageSize,
                                 @RequestParam(value = "name",required = false,defaultValue = "") String name) {


        Page<Setmeal> list = setmealService.list(page, pageSize, name);



        return  R.success(list);


    }








}
