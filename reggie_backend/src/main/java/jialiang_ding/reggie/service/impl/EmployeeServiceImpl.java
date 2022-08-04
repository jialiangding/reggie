package jialiang_ding.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jialiang_ding.reggie.common.R;
import jialiang_ding.reggie.entity.Employee;
import jialiang_ding.reggie.mapper.EmployeeMapper;
import jialiang_ding.reggie.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.io.Serializable;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee>  implements  EmployeeService{

    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public R<Employee> login(String username, String password) {
        password= DigestUtils.md5DigestAsHex(password.getBytes());

        LambdaQueryWrapper<Employee>  queryWrapper=new LambdaQueryWrapper<>();
        LambdaQueryWrapper<Employee> eq = queryWrapper.eq(Employee::getUsername, username);
        Employee emp = employeeMapper.selectOne(queryWrapper);
        if(emp!=null){
            if(password.equals(emp.getPassword())){

                if(emp.getStatus().equals(0)){
                    return R.error("账号已被禁用");

                }else {
                    return  R.success(emp);
                }

            }else {
                return  R.error("密码错误");
            }
        }else {
            return  R.error("账号不存在或密码错误");
        }
    }
}
