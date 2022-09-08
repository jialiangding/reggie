package jialiang_ding.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import jialiang_ding.reggie.entity.User;
import jialiang_ding.reggie.entity.req.UserLoginReq;

public interface UserService  extends IService<User> {

    public User login(UserLoginReq loginReq);


    public Boolean register(UserLoginReq loginReq);

}
