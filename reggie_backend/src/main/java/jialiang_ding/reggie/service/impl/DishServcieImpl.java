package jialiang_ding.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jialiang_ding.reggie.entity.Category;
import jialiang_ding.reggie.entity.Dish;
import jialiang_ding.reggie.entity.DishFlavor;
import jialiang_ding.reggie.entity.req.DishReq;
import jialiang_ding.reggie.mapper.DishMapper;
import jialiang_ding.reggie.service.CategoryService;
import jialiang_ding.reggie.service.DishFlavorService;
import jialiang_ding.reggie.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Slf4j
public class  DishServcieImpl extends ServiceImpl<DishMapper, Dish> implements DishService {


    @Autowired
    private DishFlavorService  dishFlavorService;
    
    @Autowired
    private CategoryService categoryService;



    @Override
    @Transactional
    public Long save(DishReq dishReq) {
        Dish dish=new Dish();
        BeanUtils.copyProperties(dishReq,dish);

        this.save(dish);
        List<DishFlavor> dishFlavorList = dishReq.getFlavors();
//        dishFlavorList.forEach(dishFlavor1->{
//            dishFlavor1.setDishId(dish.getId());
//            dishFlavorService.save(dishFlavor1);
//        });
        dishFlavorList.stream().map((item)-> {
                    item.setDishId(dish.getId());
                    return item;
                }).collect(Collectors.toList());
        dishFlavorService.saveBatch(dishFlavorList);
        return dish.getId();
    }

    @Override
    public Page<DishReq> list(Integer page, Integer pageSize, String name) {
        Page<Dish> dishPage=new Page(page,pageSize);
        LambdaQueryWrapper<Dish> queryWrapper=new LambdaQueryWrapper();
        queryWrapper.like(StringUtils.isNotEmpty(name),Dish::getName,name);
        queryWrapper.orderByDesc(Dish::getUpdateTime);
        queryWrapper.eq(Dish::getIsDelete,"0");
        this.page(dishPage,queryWrapper);

        Page< DishReq>  dishReqPage=new Page(page,pageSize);
        BeanUtils.copyProperties(dishPage,dishReqPage,"records");
        //records 就是list 拿到dishPage的records 然后塞到dishReqPage的records里去
        List<Dish> records = dishPage.getRecords();
        List<DishReq> dishReqList = records.stream().map(r -> {
            DishReq dishReq = new DishReq();
            Long categoryId = r.getCategoryId();
            Category category = categoryService.getById(categoryId);
            BeanUtils.copyProperties(r, dishReq);
            String categoryName = category.getName();
            dishReq.setCategoryName(categoryName);
            return dishReq;
        }).collect(Collectors.toList());
        dishReqPage.setRecords(dishReqList);

        return dishReqPage;
    }
}
