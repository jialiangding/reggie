package jialiang_ding.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import jialiang_ding.reggie.common.R;
import jialiang_ding.reggie.entity.Employee;
import jialiang_ding.reggie.exception.BusinessRuntimeException;
import jialiang_ding.reggie.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee){
        String password=employee.getPassword();
        String username=employee.getUsername();
        R<Employee> login = employeeService.login(username, password);
        if(login.getCode()==1){
            request.getSession().setAttribute("employee",login.getData().getId());

            return  login;


        }
        return  login;
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



    @PostMapping("/save")
    public R<String> save(){

    throw new BusinessRuntimeException("sdsdsd");



    }







}
