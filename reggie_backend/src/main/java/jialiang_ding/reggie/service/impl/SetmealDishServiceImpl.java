package jialiang_ding.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jialiang_ding.reggie.entity.DishFlavor;
import jialiang_ding.reggie.entity.SetmealDish;
import jialiang_ding.reggie.mapper.DishFlavorMapper;
import jialiang_ding.reggie.mapper.SetmealDishMapper;
import jialiang_ding.reggie.mapper.SetmealMapper;
import jialiang_ding.reggie.service.DishFlavorService;
import jialiang_ding.reggie.service.SetmealDishService;
import org.springframework.stereotype.Service;

@Service
public class SetmealDishServiceImpl extends ServiceImpl<SetmealDishMapper, SetmealDish> implements SetmealDishService {
}
