package jialiang_ding.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jialiang_ding.reggie.entity.Setmeal;
import jialiang_ding.reggie.mapper.SetmealMapper;
import jialiang_ding.reggie.service.SetmealService;
import org.springframework.stereotype.Service;

@Service
public class SetmealServiceImpl  extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {
    @Override
    public Page<Setmeal> list(Integer page, Integer pageSize, String name) {
        Page page1=new Page(page,pageSize);
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper=new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.like(Setmeal::getName,name).eq(Setmeal::getIsDelete,"0").orderByDesc(Setmeal::getCreateTime);
        return   this.page(page1, setmealLambdaQueryWrapper);

    }
}
