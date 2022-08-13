package jialiang_ding.reggie.entity.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginDto {

    @NotBlank(message = "用户信息不能为空")
    private String username;

    @NotBlank(message = "密码不能为空")
    private String password;
}
