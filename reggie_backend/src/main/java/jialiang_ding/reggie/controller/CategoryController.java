package jialiang_ding.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jialiang_ding.reggie.common.R;
import jialiang_ding.reggie.entity.Category;
import jialiang_ding.reggie.entity.Employee;
import jialiang_ding.reggie.entity.req.CategorySaveReq;
import jialiang_ding.reggie.exception.BusinessRuntimeException;
import jialiang_ding.reggie.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
@Slf4j
public class CategoryController {

    @Autowired
    private CategoryService categoryService;


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


    @DeleteMapping
    public  R<Category> delete(@RequestParam(value = "ids",required = true)String ids){

        Category category = categoryService.getById(ids);
        category.setIsDelete(1);
        boolean b = categoryService.updateById(category);
        log.info("结果"+String.valueOf(b));
//        if(b==false){
//            throw new BusinessRuntimeException("更新失败");
//        }
        return R.success(category);


    }


    @PostMapping
    public R<Category> save(@RequestBody CategorySaveReq categorySaveReq){

        Category save = this.categoryService.save(categorySaveReq);


        return  R.success(save);

    }








}
