package jialiang_ding.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jialiang_ding.reggie.common.R;
import jialiang_ding.reggie.entity.Category;
import jialiang_ding.reggie.entity.Dish;
import jialiang_ding.reggie.entity.req.CategorySaveReq;
import jialiang_ding.reggie.exception.BusinessRuntimeException;
import jialiang_ding.reggie.service.CategoryService;
import jialiang_ding.reggie.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/category")
@Slf4j
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private DishService dishService;


    @GetMapping("/page")
    public  R<Page> page(@RequestParam(value = "page",required = false,defaultValue = "1")Integer page,
                                   @RequestParam(value = "pageSize",required = false,defaultValue = "10")Integer pageSize
                                ){
        Page page1=new Page(page,pageSize);
        LambdaQueryWrapper<Category> queryWrapper=new LambdaQueryWrapper();
        queryWrapper.eq(Category::getIsDelete,"0");
        queryWrapper.orderByDesc(Category::getSort);
        //执行查询
        categoryService.page(page1, queryWrapper);
        return  R.success(page1);
    }
    @PutMapping
    public  R<Category> update(@RequestBody Category category){
        boolean b = categoryService.updateById(category);
        if(b==false){
            throw new BusinessRuntimeException("更新失败");
        }
        return R.success(category);
    }

    /**
     * 删除套餐
     * @param ids
     * @return
     */
    @DeleteMapping
    public  R<Category> delete(@RequestParam(value = "ids",required = true)String ids){
        //查询菜品是否关联了将要关联的分类 如果有不允许删除
        LambdaQueryWrapper<Dish> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        LambdaQueryWrapper<Dish> eq = lambdaQueryWrapper.eq(Dish::getCategoryId, ids).ne(Dish::getIsDelete,0);

        Map<String, Object> map = dishService.getMap(eq);

        if(map!=null){
            log.info(map.toString());
        }
        int count = dishService.count(eq);
        if(count>0){
            throw new  BusinessRuntimeException("该分类已经关联了菜品");
        }


        Category category = categoryService.getById(ids);
        category.setIsDelete(1);
        boolean b = categoryService.updateById(category);


        return R.success(category);


    }


    @PostMapping
    public R<Category> save(@RequestBody CategorySaveReq categorySaveReq){

        Category save = this.categoryService.save(categorySaveReq);
        return  R.success(save);

    }








}
