package jialiang_ding.reggie.entity.dto;

import jialiang_ding.reggie.entity.Setmeal;
import jialiang_ding.reggie.entity.SetmealDish;
import lombok.Data;

import java.util.List;

@Data
public class SetmealDto extends Setmeal {



    private List<SetmealDish> setmealDishes;


}
