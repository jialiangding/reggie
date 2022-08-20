package jialiang_ding.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jialiang_ding.reggie.entity.Category;
import jialiang_ding.reggie.entity.Dish;
import jialiang_ding.reggie.entity.Employee;
import jialiang_ding.reggie.entity.req.CategorySaveReq;
import jialiang_ding.reggie.exception.BusinessRuntimeException;
import jialiang_ding.reggie.mapper.CategoryMapper;
import jialiang_ding.reggie.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper,Category> implements CategoryService {

    @Autowired
    private  CategoryMapper categoryMapper;


    @Override
    public Category save(CategorySaveReq categorySaveReq) {
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

        LambdaQueryWrapper<Dish> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        LambdaQueryWrapper<Dish> eq = lambdaQueryWrapper.eq(Dish::getCategoryId, categoryId).ne(Dish::getIsDelete,0);


        Category category = this.getById(categoryId);
        category.setIsDelete(1);
        boolean b = this.updateById(category);
        return b;
    }
}
