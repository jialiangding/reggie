package jialiang_ding.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import jialiang_ding.reggie.common.R;
import jialiang_ding.reggie.entity.Employee;

public interface EmployeeService  extends IService<Employee> {




    public R<Employee> login(String username,String password);

}
