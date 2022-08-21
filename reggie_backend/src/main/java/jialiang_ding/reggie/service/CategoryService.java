package jialiang_ding.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import jialiang_ding.reggie.entity.Category;
import jialiang_ding.reggie.entity.Employee;
import jialiang_ding.reggie.entity.req.CategorySaveReq;


public interface CategoryService extends IService<Category> {
   public Category save(CategorySaveReq categorySaveReq);
   public Boolean delete(String categoryId);

}
