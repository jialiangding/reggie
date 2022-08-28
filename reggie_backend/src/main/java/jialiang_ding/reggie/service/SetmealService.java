package jialiang_ding.reggie.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jialiang_ding.reggie.entity.Setmeal;

public interface SetmealService  extends IService<Setmeal>{

    public Page<Setmeal> list(Integer page,Integer pageSize,String name);
}
