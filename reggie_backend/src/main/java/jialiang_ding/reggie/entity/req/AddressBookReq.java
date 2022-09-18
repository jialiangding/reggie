package jialiang_ding.reggie.entity.req;

import lombok.Data;

import java.io.Serializable;

@Data
public class AddressBookReq  implements Serializable {
    private  Long id;
    private String consignee;
    private  String phone;
    private  String sex;
    private String detail;
    private  String label;


}
