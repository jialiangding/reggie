package jialiang_ding.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jialiang_ding.reggie.entity.User;
import jialiang_ding.reggie.entity.req.UserLoginReq;
import jialiang_ding.reggie.exception.BusinessRuntimeException;
import jialiang_ding.reggie.mapper.UserMapper;
import jialiang_ding.reggie.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;


@Service
public class UserServiceImpl  extends ServiceImpl<UserMapper, User> implements UserService {





    @Override
    public Boolean login(UserLoginReq loginReq) {
        String password=loginReq.getPassword();
        String username=loginReq.getUsername();
        password= DigestUtils.md5DigestAsHex(password.getBytes());
        LambdaQueryWrapper<User> lambdaQueryWrapper=new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(User::getUsername,username);

        User one = this.getOne(lambdaQueryWrapper);

        if( one ==null || password.equals(one.getPassword())==false){
            throw  new BusinessRuntimeException("账号密码错误");
        }

        if(one.getStatus().equals(0)){
            throw  new BusinessRuntimeException("账号被禁用");
        }
        return true;

    }

    @Override
    public Boolean register(UserLoginReq loginReq) {
        LambdaQueryWrapper<User> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getUsername,loginReq.getUsername());
        User one = this.getOne(lambdaQueryWrapper);
        if(one!=null){
            throw new BusinessRuntimeException("账号已注册");
        }
        User user=new User();
        user.setUsername(loginReq.getUsername());
        user.setPassword(DigestUtils.md5DigestAsHex(loginReq.getPassword().getBytes()));
        this.save(user);
        return true;

    }
}
