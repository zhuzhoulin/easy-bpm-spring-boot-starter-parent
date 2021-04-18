package com.pig.easy.bpm.auth.factory;

import com.pig.easy.bpm.auth.core.context.GlobalUserInfoContext;
import com.pig.easy.bpm.auth.dto.request.OperatorLogSaveOrUpdateDTO;
import com.pig.easy.bpm.auth.dto.request.SqlLogSaveOrUpdateDTO;
import com.pig.easy.bpm.auth.service.OperatorLogService;
import com.pig.easy.bpm.auth.service.SqlLogService;
import com.pig.easy.bpm.common.executor.GlobalExecutor;
import com.pig.easy.bpm.common.utils.spring.SpringUtils;

/**
 * todo:
 *
 * @author : zhoulin.zhu
 * @date : 2021/3/22 17:47
 */
public class AsyncLogFactory {

    public static void insertSqlLog(final SqlLogSaveOrUpdateDTO param) {

        GlobalExecutor.submitLoggerDataTask(new Runnable() {
            @Override
            public void run() {
                SpringUtils.getBean(SqlLogService.class).insertSqlLog(param);
            }
        });
    }

    public static void insertOperatorLog(final OperatorLogSaveOrUpdateDTO param) {

        GlobalExecutor.submitLoggerDataTask(new Runnable() {
            @Override
            public void run() {
                SpringUtils.getBean(OperatorLogService.class).insertOperatorLog(param);
            }
        });
    }

    public static void insertSqlLog(final String sqlType,final String sql,final long spendTime) {

        SqlLogSaveOrUpdateDTO param = new SqlLogSaveOrUpdateDTO();
        param.setLogType(sqlType);
        param.setLogSql(sql);
        param.setSpendTime(spendTime);
        if(GlobalUserInfoContext.getLoginInfo()!= null){
            param.setOperatorId(GlobalUserInfoContext.getLoginInfo().getUserId());
            param.setOperatorName(GlobalUserInfoContext.getLoginInfo().getRealName());
        }
        GlobalExecutor.submitLoggerDataTask(new Runnable() {
            @Override
            public void run() {
                SpringUtils.getBean(SqlLogService.class).insertSqlLog(param);
            }
        });
    }
}
