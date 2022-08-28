package jialiang_ding.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jialiang_ding.reggie.entity.Category;
import jialiang_ding.reggie.entity.Dish;
import jialiang_ding.reggie.entity.DishFlavor;
import jialiang_ding.reggie.entity.req.DishReq;
import jialiang_ding.reggie.mapper.DishFlavorMapper;
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
public class DishServcieImpl extends ServiceImpl<DishMapper, Dish> implements DishService {


    @Autowired
    private DishFlavorService dishFlavorService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private DishFlavorMapper dishFlavorMapper;

    @Override
    @Transactional
    public Long save(DishReq dishReq) {
        //更新逻辑
        if (dishReq.getId() != null) {
//            LambdaUpdateWrapper<Dish> dishLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
//            dishLambdaUpdateWrapper.set(Dish::getName, dishReq.getName());
//            dishLambdaUpdateWrapper.set(Dish::getCategoryId, dishReq.getCategoryId());
//            dishLambdaUpdateWrapper.set(Dish::getPrice, dishReq.getPrice());
//            dishLambdaUpdateWrapper.set(Dish::getImage, dishReq.getImage());
//            dishLambdaUpdateWrapper.set(Dish::getDescription, dishReq.getDescription());
//            //下面这行必须有 否则就批量更新了 如下 : UPDATE dish SET name=?,category_id=?,price=?,image=?,description=?
//            dishLambdaUpdateWrapper.eq(Dish::getId, dishReq.getId());
//            dishMapper.update(null, dishLambdaUpdateWrapper);
            this.updateById(dishReq);
            //更新DishFlavor表
            //查询 到dish关联的DishFlavor  isdelete置成1
            LambdaQueryWrapper<DishFlavor> dishFlavorLambdaQueryWrapper = new LambdaQueryWrapper<>();
            dishFlavorLambdaQueryWrapper.eq(DishFlavor::getDishId, dishReq.getId()).eq(DishFlavor::getIsDelete, "0");
//            List<DishFlavor> dishFlavorList = dishFlavorService.list(dishFlavorLambdaQueryWrapper);

//            for (DishFlavor dishFlavor : dishFlavorList) {
//                Long id = dishFlavor.getId();
//                LambdaUpdateWrapper<DishFlavor> dishFlavorLambdaUpdateWrapper1 = new LambdaUpdateWrapper<>();
//                dishFlavorLambdaUpdateWrapper1.set(DishFlavor::getIsDelete, "1");
//                dishFlavorLambdaUpdateWrapper1.eq(DishFlavor::getId, id);
//                int update = dishFlavorMapper.update(null, dishFlavorLambdaUpdateWrapper1);
//            }
            //查询 到dish关联的DishFlavor  isdelete置成1
            boolean remove = dishFlavorService.remove(dishFlavorLambdaQueryWrapper);


            //更新DishFlavor表
            List<DishFlavor> dishFlavorListupdate = dishReq.getFlavors();
//            for (DishFlavor dishFlavor : dishFlavorListupdate) {
//                if (dishFlavor.getId() != null) {
//                    DishFlavor dishFlavor1 = dishFlavorService.getById(dishFlavor.getId());
//                    dishFlavor1.setValue(dishFlavor.getValue());
//                    dishFlavor1.setName(dishFlavor.getName());
//                    dishFlavor1.setIsDelete(0);
//                    dishFlavorService.saveOrUpdate(dishFlavor1);
//                } else {
//                    DishFlavor dishFlavor1 = new DishFlavor();
//                    dishFlavor1.setDishId(dishReq.getId());
//                    dishFlavor1.setName(dishFlavor.getName());
//                    dishFlavor1.setValue(dishFlavor.getValue());
//                    dishFlavorService.save(dishFlavor1);
//                }
//            }
            List<DishFlavor> collect = dishFlavorListupdate.stream().map(dishFlavor -> {
                dishFlavor.setDishId(dishReq.getId());
                return dishFlavor;
            }).collect(Collectors.toList());
            dishFlavorService.saveBatch(collect);
            return dishReq.getId();


        } else {
            //更新逻辑
            Dish dish = new Dish();
            BeanUtils.copyProperties(dishReq, dish);

            this.save(dish);
            List<DishFlavor> dishFlavorList = dishReq.getFlavors();
            dishFlavorList.stream().map((item) -> {
                item.setDishId(dish.getId());
                return item;
            }).collect(Collectors.toList());
            dishFlavorService.saveBatch(dishFlavorList);
            return dish.getId();
        }


    }

    @Override
    public Page<DishReq> list(Integer page, Integer pageSize, String name) {
        Page<Dish> dishPage = new Page(page, pageSize);
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.like(StringUtils.isNotEmpty(name), Dish::getName, name);
        queryWrapper.orderByDesc(Dish::getUpdateTime);
        queryWrapper.eq(Dish::getIsDelete, "0");
        this.page(dishPage, queryWrapper);

        Page<DishReq> dishReqPage = new Page(page, pageSize);
        BeanUtils.copyProperties(dishPage, dishReqPage, "records");
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

    @Override
    public DishReq detail(Long dishId) {
        Dish dish = this.getById(dishId);
        DishReq dishReq = new DishReq();
        LambdaQueryWrapper<DishFlavor> dishFlavorLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishFlavorLambdaQueryWrapper.eq(DishFlavor::getDishId, dishId).eq(DishFlavor::getIsDelete, "0");
        List<DishFlavor> dishFlavorList = dishFlavorService.list(dishFlavorLambdaQueryWrapper);
        dishReq.setFlavors(dishFlavorList);
        dishReq.setCategoryName(dish.getName());
        BeanUtils.copyProperties(dish, dishReq);


        return dishReq;
    }
}
