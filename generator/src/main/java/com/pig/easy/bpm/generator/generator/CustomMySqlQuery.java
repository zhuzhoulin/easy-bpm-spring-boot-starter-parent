package com.pig.easy.bpm.generator.generator;

import com.baomidou.mybatisplus.generator.config.querys.MySqlQuery;

/**
 * todo:
 *
 * @author : zhoulin.zhu
 * @date : 2021/2/23 9:21
 */
public class CustomMySqlQuery extends MySqlQuery {

    @Override
    public String[] fieldCustom() {
        return new String[]{"FIELD","TYPE","COLLATION","NULL","KEY","DEFAULT","EXTRA","PRIVILEGES","COMMENT"};
    }
}
