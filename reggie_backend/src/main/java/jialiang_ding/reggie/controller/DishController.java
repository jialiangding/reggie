package jialiang_ding.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jialiang_ding.reggie.common.R;
import jialiang_ding.reggie.entity.Dish;
import jialiang_ding.reggie.entity.Employee;
import jialiang_ding.reggie.entity.req.DishReq;
import jialiang_ding.reggie.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dish")
@Slf4j
public class DishController {
    @Autowired
    private DishService dishService;




    @GetMapping("/page")
    public R<Page<DishReq>> page(@RequestParam(value = "page",required = false,defaultValue = "1")Integer page,
                              @RequestParam(value = "pageSize",required = false,defaultValue = "10")Integer pageSize,
                              @RequestParam(value = "name",required = false,defaultValue = "") String name){
        Page<DishReq> list = dishService.list(page, pageSize, name);


        return  R.success(list);

    }

    /**
     * 新增

     * @return 返回主键Id
     */

    @PostMapping
    public  R<Long> save(@RequestBody  DishReq dishReq){
        log.info(dishReq.toString());
        Long save = dishService.save(dishReq);
        return R.success(save);
    }


    @GetMapping("/{id}")
    public R<DishReq> detail(@PathVariable(value = "id") Long id){
        DishReq detail = this.dishService.detail(id);
        return R.success(detail);

    }


    @PutMapping
    public  R<Long> update(@RequestBody  DishReq dishReq){
        log.info(dishReq.toString());
        Long save = dishService.save(dishReq);
        return R.success(save);
    }



}
