package jialiang_ding.reggie.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import jialiang_ding.reggie.entity.Dish;
import jialiang_ding.reggie.entity.req.DishReq;
import org.springframework.stereotype.Service;


public interface DishService extends IService<Dish> {
    public  Long save(DishReq dishReq);

    public Page<DishReq>   list(Integer page,Integer pageSize,String name);

    public  DishReq detail(Long  dishId);





}
