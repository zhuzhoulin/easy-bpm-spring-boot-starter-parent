package com.pig.easy.bpm.api.core.flowable;

import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.flowable.engine.impl.persistence.entity.HistoricActivityInstanceEntity;
import org.flowable.engine.impl.persistence.entity.HistoricActivityInstanceEntityManagerImpl;
import org.flowable.engine.impl.persistence.entity.data.HistoricActivityInstanceDataManager;

/**
 * todo: key 通过这个方法 接管  ACT_HI_ACTINST 表数据
 *
 * @author : pig
 * @date : 2020/7/28 17:34
 */
@Slf4j
public class CustomHistoricActivityInstanceEntityManager extends HistoricActivityInstanceEntityManagerImpl {

    @Override
    public void insert(HistoricActivityInstanceEntity entity) {

        log.info("##############################################{}#############",entity.getActivityId());
        log.info("##############################################{}#############",entity.getActivityName());

        super.insert(entity);
    }

    @Override
    public void insert(HistoricActivityInstanceEntity entity, boolean fireCreateEvent) {
        log.info("##############################################{}#############",entity.getActivityId());
        log.info("##############################################{}#############",entity.getActivityName());
        super.insert(entity, fireCreateEvent);
    }

    public CustomHistoricActivityInstanceEntityManager(ProcessEngineConfigurationImpl processEngineConfiguration, HistoricActivityInstanceDataManager historicActivityInstanceDataManager) {
        super(processEngineConfiguration, historicActivityInstanceDataManager);
    }
}
