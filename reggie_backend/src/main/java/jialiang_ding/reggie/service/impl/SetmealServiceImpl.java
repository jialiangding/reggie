package jialiang_ding.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jialiang_ding.reggie.entity.Setmeal;
import jialiang_ding.reggie.entity.SetmealDish;
import jialiang_ding.reggie.entity.dto.SetmealDto;
import jialiang_ding.reggie.mapper.SetmealMapper;
import jialiang_ding.reggie.service.SetmealDishService;
import jialiang_ding.reggie.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SetmealServiceImpl  extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {

    @Autowired
     private SetmealDishService setmealDishService;








    @Override
    public Page<Setmeal> list(Integer page, Integer pageSize, String name) {
        Page page1=new Page(page,pageSize);
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper=new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.like(Setmeal::getName,name).eq(Setmeal::getIsDelete,"0").orderByDesc(Setmeal::getCreateTime);
        return   this.page(page1, setmealLambdaQueryWrapper);

    }

    @Override
    @Transactional
    public Long add(SetmealDto setmealDto) {
        this.save(setmealDto);
        SetmealDish setmealDish=new SetmealDish();

        List<SetmealDish> setmealDishes =setmealDto.getSetmealDishes();
        List<SetmealDish> collect = setmealDishes.stream().map(setmealDishe -> {
            setmealDish.setCopies(setmealDishe.getCopies());
            setmealDish.setDishId(setmealDishe.getDishId());
            setmealDish.setSetmealId(setmealDto.getId());
            setmealDish.setSort("0");
            return setmealDish;


        }).collect(Collectors.toList());
        boolean b = setmealDishService.saveBatch(collect);



        return setmealDto.getId();
    }
}
