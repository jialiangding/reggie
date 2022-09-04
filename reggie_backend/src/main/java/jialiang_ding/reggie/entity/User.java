package jialiang_ding.reggie.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class User  implements Serializable {
    private  Long id;
    private  String username;
    private  String password;
    private  String sex;
    private String idNumber;
    private  String avatar;
    private Integer status;
}
