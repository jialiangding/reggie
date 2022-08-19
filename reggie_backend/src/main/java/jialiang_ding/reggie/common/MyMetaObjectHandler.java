package jialiang_ding.reggie.common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import jialiang_ding.reggie.util.BaseContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("公共字段填充(insert)");
        // 创建时间
        metaObject.setValue("createTime", LocalDateTime.now());
        // 创建人
        metaObject.setValue("createUser",BaseContextUtil.getCurrentId());
        // 更新时间
        metaObject.setValue("updateTime", LocalDateTime.now());
        // 更新人
        metaObject.setValue("updateUser",BaseContextUtil.getCurrentId());


    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("公共字段填充(update)");
        // 更新时间
        metaObject.setValue("updateTime", LocalDateTime.now());
        // 更新人
        metaObject.setValue("updateUser", BaseContextUtil.getCurrentId());



    }
}
