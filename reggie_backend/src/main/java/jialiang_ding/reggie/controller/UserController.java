package jialiang_ding.reggie.controller;

import jialiang_ding.reggie.common.R;
import jialiang_ding.reggie.entity.req.UserLoginReq;
import jialiang_ding.reggie.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public R<Boolean> login(@RequestBody UserLoginReq userLoginReq){
        Boolean login = userService.login(userLoginReq);

        return R.success(login);

    }



    @PostMapping("/register")
    public R<Boolean> register(@RequestBody UserLoginReq userLoginReq){
        Boolean login = userService.login(userLoginReq);

        return R.success(login);

    }
}
