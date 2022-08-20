package jialiang_ding.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jialiang_ding.reggie.entity.Setmeal;
import jialiang_ding.reggie.mapper.SetmealMapper;
import jialiang_ding.reggie.service.SetmealService;
import org.springframework.stereotype.Service;

@Service

public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal>  implements SetmealService {
}
