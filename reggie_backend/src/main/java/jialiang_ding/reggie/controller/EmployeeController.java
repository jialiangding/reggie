package jialiang_ding.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import jialiang_ding.reggie.common.R;
import jialiang_ding.reggie.entity.Employee;
import jialiang_ding.reggie.entity.dto.LoginDto;
import jialiang_ding.reggie.exception.BusinessRuntimeException;
import jialiang_ding.reggie.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.util.DigestUtils;
import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
@RequestMapping("/employee")
public class EmployeeController {


    @Autowired
    private EmployeeService employeeService;


    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @Validated @RequestBody LoginDto loginDto){
        String password=loginDto.getPassword();
        String username=loginDto.getUsername();
        Employee login = employeeService.login(username, password);
        log.info("获取到的登录用户userid:  {}",login.getId().toString());
        request.getSession().setAttribute("employee",login.getId());
        Long curuserid = (Long)request.getSession().getAttribute("employee");
        log.info(curuserid.toString());
        return R.success(login);

    }



    @GetMapping("/hello")
    public R<String> helloworld(){
        return R.success("helloworld");
    }


    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request){
        request.getSession().removeAttribute("employee");
        return  R.success("success");

    }


//,@RequestBody  Employee employee
    @PostMapping
    public R<Employee> save( HttpServletRequest request,@RequestBody Employee employee){

//        log.info("新增员工:员工信息{}",employee.toString());
        Long curuserid = (Long)request.getSession().getAttribute("employee");
        log.info("id,{}",curuserid.toString());

        employee.setCreateUser(curuserid);
        employee.setUpdateUser(curuserid);
        log.info(employee.toString());
        Employee add = employeeService.add(employee);

        return R.success(add);


    }







}
