package jialiang_ding.reggie.controller;

import jialiang_ding.reggie.common.R;
import jialiang_ding.reggie.entity.User;
import jialiang_ding.reggie.entity.req.UserLoginReq;
import jialiang_ding.reggie.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public R<User> login(@RequestBody UserLoginReq userLoginReq, HttpServletRequest request){
        User login = userService.login(userLoginReq);
        request.getSession().setAttribute("userid",login.getId());
        return R.success(login);

    }



    @PostMapping("/register")
    public R<Boolean> register(@RequestBody UserLoginReq userLoginReq){
        Boolean login = userService.register(userLoginReq);

        return R.success(login);

    }
}
