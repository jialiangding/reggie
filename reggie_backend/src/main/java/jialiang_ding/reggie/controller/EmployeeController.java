package jialiang_ding.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jialiang_ding.reggie.common.R;
import jialiang_ding.reggie.entity.Category;
import jialiang_ding.reggie.entity.Employee;
import jialiang_ding.reggie.entity.req.LoginReq;
import jialiang_ding.reggie.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
@RequestMapping("/employee")
public class EmployeeController {


    @Autowired
    private EmployeeService employeeService;


    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @Validated @RequestBody LoginReq loginDto){
        String password=loginDto.getPassword();
        String username=loginDto.getUsername();
        Employee login = employeeService.login(username, password);
        log.info("获取到的登录用户userid:  {}",login.getId().toString());
        request.getSession().setAttribute("employee",login.getId());

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
//
//        Long curuserid = (Long)request.getSession().getAttribute("employee");
//        log.info("id,{}",curuserid.toString());
//
//        employee.setCreateUser(curuserid);
//        employee.setUpdateUser(curuserid);
//        log.info(employee.toString());
        Employee add = employeeService.add(employee);

        return R.success(add);
    }


    @PutMapping
    public R<Employee> update( HttpServletRequest request,@RequestBody Employee employee){

//        log.info("新增员工:员工信息{}",employee.toString());
        Long curuserid = (Long)request.getSession().getAttribute("employee");
        log.info("id,{}",curuserid.toString());

        employee.setCreateUser(curuserid);
        employee.setUpdateUser(curuserid);
        Employee update = employeeService.update(employee);
        return  R.success(update);
    }


    @GetMapping("/page")
    public R<Page> page(@RequestParam(value = "page",required = false,defaultValue = "1")Integer page,
                        @RequestParam(value = "pageSize",required = false,defaultValue = "10")Integer pageSize,
                        @RequestParam(value = "name",required = false,defaultValue = "") String name){
        //构造分页构造器
        Page page1=new Page(page,pageSize);
        //构造条件构造器
        LambdaQueryWrapper<Employee> queryWrapper=new LambdaQueryWrapper();
        //添加过滤条件
        queryWrapper.like(StringUtils.isNotEmpty(name),Employee::getName,name);
        queryWrapper.orderByDesc(Employee::getUpdateTime);
        queryWrapper.eq(Employee::getIsDelete,"0");

        //执行查询
        employeeService.page(page1, queryWrapper);
        return  R.success(page1);

    }

    @GetMapping("/{id}")
    public  R<Employee> getDetail(@PathVariable("id")  String id){
        Employee datail = employeeService.datail(id);


        return  R.success(datail);
    }







}
