package jialiang_ding.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jialiang_ding.reggie.entity.Category;
import jialiang_ding.reggie.entity.Dish;
import jialiang_ding.reggie.mapper.CategoryMapper;
import jialiang_ding.reggie.mapper.DishMapper;
import jialiang_ding.reggie.service.DishService;
import org.springframework.stereotype.Service;


@Service
public class  DishServcieImpl extends ServiceImpl<DishMapper, Dish> implements DishService {
}
