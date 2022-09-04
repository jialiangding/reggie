package jialiang_ding.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sun.org.apache.xpath.internal.operations.Bool;
import jialiang_ding.reggie.entity.User;
import jialiang_ding.reggie.entity.req.UserLoginReq;

public interface UserService  extends IService<User> {

    public Boolean login(UserLoginReq loginReq);


    public Boolean register(UserLoginReq loginReq);

}
