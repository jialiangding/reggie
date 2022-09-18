package jialiang_ding.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jialiang_ding.reggie.entity.AddressBook;
import jialiang_ding.reggie.entity.req.AddressBookReq;
import jialiang_ding.reggie.mapper.AddressBookMapper;
import jialiang_ding.reggie.service.AddressBookService;
import jialiang_ding.reggie.util.BaseContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressBookServiceImpl  extends ServiceImpl<AddressBookMapper, AddressBook>  implements AddressBookService {

    @Autowired
    private AddressBookMapper addressBookMapper;
    


    @Override
    public List<AddressBook> listByUserId(Long userId) {

        LambdaQueryWrapper<AddressBook> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        LambdaQueryWrapper<AddressBook> eq = lambdaQueryWrapper.eq(AddressBook::getUserId, userId).eq(AddressBook::getIsDelete, "0");
        List<AddressBook> list = this.list(eq);
        return list;
    }

    @Override
    public Long save(AddressBookReq addressBookReq) {
        Long currentUserId = BaseContextUtil.getCurrentId();
        AddressBook addressBook=new AddressBook();
        addressBook.setUserId(currentUserId);
        addressBook.setConsignee(addressBookReq.getConsignee());
        addressBook.setPhone(addressBookReq.getPhone());
        addressBook.setSex(addressBookReq.getSex());
        addressBook.setLabel(addressBookReq.getLabel());
        addressBook.setId(currentUserId);


        if(addressBookReq.getId()!=null){
            addressBook.setCreateUser(currentUserId);
            this.saveOrUpdate(addressBook);
            return addressBookReq.getId();
        }


        this.save(addressBook);
//        log.debug(addressBook.toString());
//        this.save(addressBook);
//        String id= String.valueOf(currentUserId);
        return addressBook.getId();
    }

    @Override
    public String setdefalut(AddressBook addressBook) {
        Long id = addressBook.getId();
        AddressBook byId = this.getById(id);
        LambdaQueryWrapper<AddressBook> lambdaQueryWrapper =new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(AddressBook::getUserId,BaseContextUtil.getCurrentId()).eq(AddressBook::getIsDefault,"1");
        List<AddressBook> list = this.list(lambdaQueryWrapper);
        List<AddressBook> collect = list.stream().map(item -> {
            item.setIsDefault(Integer.valueOf("0"));
            return item;
        }).collect(Collectors.toList());
       this.saveOrUpdateBatch(collect);
       byId.setIsDefault(Integer.valueOf("1"));
       this.saveOrUpdate(byId);
        return String.valueOf(byId.getId());
    }

    @Override
    public AddressBook getDetailById(String id) {
        AddressBook byId = this.getById(id);

        return byId;
    }
}
