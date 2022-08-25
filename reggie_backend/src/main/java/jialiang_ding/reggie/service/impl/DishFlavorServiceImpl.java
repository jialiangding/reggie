package jialiang_ding.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jialiang_ding.reggie.entity.DishFlavor;
import jialiang_ding.reggie.mapper.DishFlavorMapper;
import jialiang_ding.reggie.service.DishFlavorService;
import org.springframework.stereotype.Service;


@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements  DishFlavorService {
}
