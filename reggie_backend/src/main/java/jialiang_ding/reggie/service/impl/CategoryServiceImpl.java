package jialiang_ding.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jialiang_ding.reggie.entity.Category;
import jialiang_ding.reggie.entity.Dish;
import jialiang_ding.reggie.entity.Employee;
import jialiang_ding.reggie.entity.Setmeal;
import jialiang_ding.reggie.entity.req.CategorySaveReq;
import jialiang_ding.reggie.exception.BusinessRuntimeException;
import jialiang_ding.reggie.mapper.CategoryMapper;
import jialiang_ding.reggie.mapper.DishMapper;
import jialiang_ding.reggie.mapper.SetmealMapper;
import jialiang_ding.reggie.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper,Category> implements CategoryService {

    @Autowired
    private  CategoryMapper categoryMapper;

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private SetmealMapper setmealMapper;




    @Override
    public   Category save(CategorySaveReq categorySaveReq) {
        Category category=new Category();
        BeanUtils.copyProperties(categorySaveReq,category);
        LambdaQueryWrapper<Category> queryWrapper=new LambdaQueryWrapper<>();
        LambdaQueryWrapper<Category> eq = queryWrapper.eq(Category::getName, categorySaveReq.getName());
        Category category1 = this.categoryMapper.selectOne(eq);
        if(category1==null){
            this.categoryMapper.insert(category);
            return category;
        }
        throw new BusinessRuntimeException("分类名称重复");






    }

    @Override
    public Boolean delete(String categoryId) {

        //查询菜品是否关联了将要关联的分类 如果有不允许删除


        LambdaQueryWrapper<Setmeal> lambdaQueryWrapper2=new LambdaQueryWrapper<>();
        LambdaQueryWrapper<Setmeal> eq2= lambdaQueryWrapper2.eq(Setmeal::getCategoryId, categoryId).eq(Setmeal::getIsDelete,0);

        List<Setmeal> setmeals = setmealMapper.selectList(eq2);

        if(setmeals.size()>0){
            List<String> namelist=setmeals.stream().map(e->e.getName()).collect(Collectors.toList());
            throw  new BusinessRuntimeException("该分类被以下套餐使用了"+namelist.toString()+"无法删除");
        }


        LambdaQueryWrapper<Dish> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        LambdaQueryWrapper<Dish> eq = lambdaQueryWrapper.eq(Dish::getCategoryId, categoryId).eq(Dish::getIsDelete,0);

        List<Dish> dishes = dishMapper.selectList(eq);


        if(dishes.size()>0){
            List<String> nameslist= dishes.stream().map(e->e.getName()).collect(Collectors.toList());
            throw  new BusinessRuntimeException("该分类被以下菜品使用了"+nameslist.toString()+"无法删除");
        }

        Category category = categoryMapper.selectById(categoryId);
        category.setIsDelete(1);
        categoryMapper.updateById(category);


        return true;
    }
}
