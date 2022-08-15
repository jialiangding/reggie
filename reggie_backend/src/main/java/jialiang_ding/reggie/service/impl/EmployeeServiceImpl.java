package jialiang_ding.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jialiang_ding.reggie.common.R;
import jialiang_ding.reggie.entity.Employee;
import jialiang_ding.reggie.exception.BusinessRuntimeException;
import jialiang_ding.reggie.mapper.EmployeeMapper;
import jialiang_ding.reggie.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.io.Serializable;
import java.time.LocalDateTime;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee>  implements  EmployeeService{

    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public Employee login(String username, String password) {
        password= DigestUtils.md5DigestAsHex(password.getBytes());

        LambdaQueryWrapper<Employee>  queryWrapper=new LambdaQueryWrapper<>();
        LambdaQueryWrapper<Employee> eq = queryWrapper.eq(Employee::getUsername, username);
        Employee emp = employeeMapper.selectOne(queryWrapper);
        if(emp!=null){
            if(password.equals(emp.getPassword())){

                if(emp.getStatus().equals(0)){
                    log.debug("账号已被禁用");
                    throw new BusinessRuntimeException("账号已被禁用");

                }else {
                    return emp;
                }

            }else {
                log.debug("密码错误");
                throw new BusinessRuntimeException("密码错误");
            }
        }else {
            log.debug("账号不存在或密码错误");
            throw new BusinessRuntimeException("账号不存在或密码错误");
        }
    }

    @Override
    public Employee add(Employee employee) {

        LambdaQueryWrapper<Employee>  queryWrapper=new LambdaQueryWrapper<>();
        LambdaQueryWrapper<Employee> eq = queryWrapper.eq(Employee::getUsername, employee.getUsername());
        Employee emp = employeeMapper.selectOne(queryWrapper);
        if(emp!=null){
            throw  new BusinessRuntimeException("账号已被他人注册");
        }
        //设置初始密码
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());

        this.save(employee);
        return employee;
    }

    @Override
    public Employee update(Employee employee) {


        Employee emp = employeeMapper.selectById(employee.getId());
        if(emp==null){
            log.debug("员工更新失败");
            throw new BusinessRuntimeException("员工更新失败");
        }
        employeeMapper.updateById(employee);

        return employee;
    }
}
