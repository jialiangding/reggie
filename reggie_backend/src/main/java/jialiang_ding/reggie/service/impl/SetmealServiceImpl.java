package jialiang_ding.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jialiang_ding.reggie.entity.Category;
import jialiang_ding.reggie.entity.Setmeal;
import jialiang_ding.reggie.entity.SetmealDish;
import jialiang_ding.reggie.entity.dto.SetmealDto;
import jialiang_ding.reggie.mapper.SetmealMapper;
import jialiang_ding.reggie.service.CategoryService;
import jialiang_ding.reggie.service.SetmealDishService;
import jialiang_ding.reggie.service.SetmealService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SetmealServiceImpl  extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {

    @Autowired
     private SetmealDishService setmealDishService;



    @Autowired
    private SetmealService  setmealService;



    @Autowired
    private CategoryService categoryService;


    @Override
    public Page<SetmealDto> list(Integer page, Integer pageSize, String name) {
        Page page1=new Page(page,pageSize);
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper=new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.like(Setmeal::getName,name).eq(Setmeal::getIsDelete,"0").orderByDesc(Setmeal::getCreateTime);

        this.page(page1, setmealLambdaQueryWrapper);

        Page<SetmealDto> setmealDtoPage=new Page<>(page,pageSize);
        BeanUtils.copyProperties(page1,setmealDtoPage,"records");


        List<Setmeal> list = this.setmealService.list(setmealLambdaQueryWrapper);

        List<SetmealDto> collect = list.stream().map(setmeal -> {
            SetmealDto setmealDto=new SetmealDto();
            BeanUtils.copyProperties(setmeal, setmealDto);
            Category category = categoryService.getById(setmeal.getCategoryId());
            setmealDto.setCategoryName(category.getName());
            return setmealDto;
        }).collect(Collectors.toList());

        setmealDtoPage.setRecords(collect);







        return setmealDtoPage;

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
            setmealDish.setName(setmealDishe.getName());
            setmealDish.setPrice(setmealDishe.getPrice());
            return setmealDish;
        }).collect(Collectors.toList());
        boolean b = setmealDishService.saveBatch(collect);
        return setmealDto.getId();
    }

    @Override
    public SetmealDto detail(Long setmealId) {
        Setmeal setmeal = this.getById(setmealId);
        SetmealDto setmealDto=new SetmealDto();
        BeanUtils.copyProperties(setmeal,setmealDto);
        LambdaQueryWrapper<SetmealDish> setmealDishLambdaQueryWrapper=new LambdaQueryWrapper<>();
        setmealDishLambdaQueryWrapper.eq(SetmealDish::getSetmealId, setmealId).eq(SetmealDish::getIsDelete, "0");
        List<SetmealDish> setmealDishList= setmealDishService.list(setmealDishLambdaQueryWrapper);
        setmealDto.setSetmealDishes(setmealDishList);
        return setmealDto;
    }

    @Override
    public Long updateBySetmealDto(SetmealDto setmealDto) {

        setmealService.updateById(setmealDto);
      //删除原来的，给新增 的赋id

        LambdaQueryWrapper<SetmealDish> setmealDishLambdaQueryWrapper=new LambdaQueryWrapper<>();
        setmealDishLambdaQueryWrapper.eq(SetmealDish::getSetmealId,setmealDto.getId());

        setmealDishService.remove(setmealDishLambdaQueryWrapper);

        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        List<SetmealDish> collect = setmealDishes.stream().map(setmealDish -> {
            setmealDish.setSetmealId(setmealDto.getId());
            return setmealDish;

        }).collect(Collectors.toList());
        setmealDishService.saveBatch(collect);

        return setmealDto.getId();
    }
}
