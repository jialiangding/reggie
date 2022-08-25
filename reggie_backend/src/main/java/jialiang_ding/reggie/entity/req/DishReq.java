package jialiang_ding.reggie.entity.req;


import jialiang_ding.reggie.entity.Dish;
import jialiang_ding.reggie.entity.DishFlavor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DishReq extends Dish {
    private List<DishFlavor> flavors=new ArrayList<>();
    private String categoryName;

    private Integer copies;

}
