package com.pig.easy.bpm.api.config;

import com.pig.easy.bpm.api.core.flowable.BpmProcessDiagramGenerator;
import com.pig.easy.bpm.api.core.flowable.CustomHistoricActivityInstanceEntityManager;
import com.pig.easy.bpm.api.core.flowable.FlowableDeployCache;
import com.pig.easy.bpm.api.core.handler.CustomUserTaskParseHandler;
import com.pig.easy.bpm.api.core.listener.GlobalFlowableEventListener;
import com.pig.easy.bpm.api.utils.SnowIdGenerator;
import org.flowable.common.engine.api.delegate.event.FlowableEventListener;
import org.flowable.common.engine.impl.history.HistoryLevel;
import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.flowable.engine.parse.BpmnParseHandler;
import org.flowable.spring.ProcessEngineFactoryBean;
import org.flowable.spring.SpringProcessEngineConfiguration;
import org.flowable.spring.boot.EngineConfigurationConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author pigs
 */
@Configuration
public class FlowableConfig implements EngineConfigurationConfigurer<SpringProcessEngineConfiguration> {

    @Autowired
    private FlowableDeployCache customFlowableDeployCache;

   @Autowired
    private PlatformTransactionManager transactionManager;
   @Autowired
    private DataSource dataSource;

    @Autowired
    private GlobalFlowableEventListener globalFlowableEventListener;

    private static final String FONT_NAME = "宋体";

    @Override
    public void configure(SpringProcessEngineConfiguration springProcessEngineConfiguration) {

        springProcessEngineConfiguration.setDataSource(dataSource);
        springProcessEngineConfiguration.setAsyncExecutorActivate(false);
        springProcessEngineConfiguration.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_FALSE);
        springProcessEngineConfiguration.setDatabaseType(ProcessEngineConfiguration.DATABASE_TYPE_MYSQL);
        springProcessEngineConfiguration.setHistoryLevel(HistoryLevel.FULL);

        List<FlowableEventListener> flowableEventListenerList = new ArrayList<>();
        flowableEventListenerList.add(globalFlowableEventListener);
        springProcessEngineConfiguration.setEventListeners(flowableEventListenerList);

        List<BpmnParseHandler> bpmnParseHandlers = new ArrayList<>();
        bpmnParseHandlers.add(new CustomUserTaskParseHandler());
        springProcessEngineConfiguration.setCustomDefaultBpmnParseHandlers(bpmnParseHandlers);

        /* 流程图字体 */
        springProcessEngineConfiguration.setActivityFontName(FONT_NAME);
        springProcessEngineConfiguration.setAnnotationFontName(FONT_NAME);
        springProcessEngineConfiguration.setLabelFontName(FONT_NAME);

        springProcessEngineConfiguration.setIdGenerator(new SnowIdGenerator());
        springProcessEngineConfiguration.setProcessDefinitionCache(customFlowableDeployCache);
        springProcessEngineConfiguration.setTransactionManager(transactionManager);

        springProcessEngineConfiguration.setProcessDiagramGenerator(new BpmProcessDiagramGenerator());
        springProcessEngineConfiguration.setHistoricActivityInstanceEntityManager(new CustomHistoricActivityInstanceEntityManager(springProcessEngineConfiguration,springProcessEngineConfiguration.getHistoricActivityInstanceDataManager()));
    }


    /**
     * 流程引擎，与spring整合使用factoryBean
     */
    @Bean
    @Primary
    public ProcessEngineFactoryBean processEngine(ProcessEngineConfiguration processEngineConfiguration){
        ProcessEngineFactoryBean processEngineFactoryBean = new ProcessEngineFactoryBean();
        processEngineFactoryBean.setProcessEngineConfiguration((ProcessEngineConfigurationImpl) processEngineConfiguration);
        return processEngineFactoryBean;
    }

}
