package jialiang_ding.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jialiang_ding.reggie.entity.Employee;
import jialiang_ding.reggie.mapper.EmployeeMapper;
import jialiang_ding.reggie.service.EmployeeService;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee>  implements  EmployeeService{


}
