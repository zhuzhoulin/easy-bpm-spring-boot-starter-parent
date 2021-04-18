package com.pig.easy.bpm.generator.generator;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * todo:
 *
 */
@Slf4j
@Component
public class CustomMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        boolean createTime = metaObject.hasSetter("createTime");
        if (createTime) {
            this.setFieldValByName("createTime", LocalDateTime.now(), metaObject);
        }
        boolean updateTime = metaObject.hasSetter("updateTime");
        if (updateTime) {
            this.setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {

        boolean createTime = metaObject.hasSetter("createTime");
        if (createTime) {
            this.setFieldValByName("createTime", LocalDateTime.now(), metaObject);
        }
        boolean updateTime = metaObject.hasSetter("updateTime");
        if (updateTime) {
            this.setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
        }
    }
}
