package jialiang_ding.reggie.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jialiang_ding.reggie.common.R;
import jialiang_ding.reggie.entity.AddressBook;
import jialiang_ding.reggie.entity.req.AddressBookReq;
import jialiang_ding.reggie.service.AddressBookService;
import jialiang_ding.reggie.util.BaseContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/addressBook")
public class AddressBookController {

    @Autowired
    private AddressBookService addressBookService;




    @GetMapping("/list")
    public R<List<AddressBook>> list(String userid){


        List<AddressBook> addressBooks = addressBookService.listByUserId(BaseContextUtil.getCurrentId());
        return  R.success(addressBooks);
    }


    @PostMapping
    public R<Long> add(@RequestBody AddressBookReq addressBookReq ){
        Long save = addressBookService.save(addressBookReq);
        return  R.success(save);
    }


    @PutMapping("/default")
    public R<String> setdefault(@RequestBody AddressBook addressBook){

        String setdefalut = addressBookService.setdefalut(addressBook);

        return  R.success(setdefalut);
    }


    @GetMapping("/{id}")
    public R<AddressBook> setdefault(@PathVariable("id") String id){
        AddressBook detailById = addressBookService.getDetailById(id);
        return  R.success(detailById);
    }
}
