package jialiang_ding.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import jialiang_ding.reggie.entity.AddressBook;
import jialiang_ding.reggie.entity.req.AddressBookReq;

import java.util.List;

public interface AddressBookService  extends IService<AddressBook> {


    List<AddressBook> listByUserId(Long userId);

    String save(AddressBookReq addressBookReq);
}
