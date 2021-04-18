package com.pig.easy.bpm.generator.generator;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.po.TableField;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.engine.AbstractTemplateEngine;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;
import com.pig.easy.bpm.generator.constant.GeneratorConstant;
import com.pig.easy.bpm.generator.dto.response.ColumnDTO;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * todo:
 *
 * @author : zhoulin.zhu
 * @date : 2021/2/20 17:24
 */

@Data
@Accessors(chain = true)
@Component
public class CustomAutoGenerator extends AutoGenerator {

    private static final Logger logger = LoggerFactory.getLogger(CustomAutoGenerator.class);

    /**
     * 自定义列信息map Map<tableName,Map<field,CodeColumnDTO>>
     */
    Map<String, Map<String, ColumnDTO>> tableMap = new HashMap<>();

    public CustomAutoGenerator(Map<String, Map<String, ColumnDTO>> tableMap) {
        this.tableMap = tableMap;
    }

    /**
     * 配置信息
     */
    protected ConfigBuilder config;
    /**
     * 注入配置
     */
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    protected InjectionConfig injectionConfig;
    /**
     * 数据源配置
     */
    private DataSourceConfig dataSource;
    /**
     * 数据库表配置
     */
    private StrategyConfig strategy;
    /**
     * 包 相关配置
     */
    private PackageConfig packageInfo;
    /**
     * 模板 相关配置
     */
    private TemplateConfig template;
    /**
     * 全局 相关配置
     */
    private GlobalConfig globalConfig;
    /**
     * 模板引擎
     */
    private AbstractTemplateEngine templateEngine;

    @Override
    protected List<TableInfo> getAllTableInfoList(ConfigBuilder config) {
        return super.getAllTableInfoList(config);
    }

    @Override
    protected ConfigBuilder pretreatmentConfigBuilder(ConfigBuilder config) {
        return super.pretreatmentConfigBuilder(config);
    }

    @Override
    public InjectionConfig getCfg() {
        return this.injectionConfig;
    }

    @Override
    public AutoGenerator setCfg(InjectionConfig injectionConfig) {
        this.injectionConfig = injectionConfig;
        return super.setCfg(injectionConfig);
    }

    @Override
    public void execute() {
        logger.debug("==========================准备生成文件...==========================");
        boolean preView = false;

        if (tableMap == null) {
            tableMap = new HashMap<>();
        }

        // 初始化配置
        if (null == config) {
            config = new ConfigBuilder(packageInfo, dataSource, strategy, template, globalConfig);
            if (null != injectionConfig) {
                injectionConfig.initMap();
                injectionConfig.setConfig(config);
                if (injectionConfig.getMap() != null) {
                    preView = Boolean.valueOf(injectionConfig.getMap().get(GeneratorConstant.PRE_VIEW) + "");
                }
            }

            // 可以在这里 注册 自定义列信息
            for (TableInfo tableInfo : config.getTableInfoList()) {
                List<TableField> fields = tableInfo.getFields();
                Map<String, ColumnDTO> fieldMap = tableMap.get(tableInfo.getName());
                if (fieldMap != null && fields != null) {
                    fields.stream().forEach(tableField -> {
                        Map<String, Object> customMap = tableField.getCustomMap() == null ? new HashMap<>() : tableField.getCustomMap();
                        List<String> validateList = new ArrayList<>();
                        ColumnDTO columnDTO = fieldMap.get(tableField.getName());
                        // 必填
                        if (columnDTO.getNotNull() == 1) {
                            switch (columnDTO.getFormType()) {
                                case "select":
                                case "radio":
                                case "checkbox":
                                case "date":
                                case "time":
                                case "datetime":
                                    validateList.add("{ required: true, messages: '请输入" + columnDTO.getFormLabel() + "', trigger: 'change' }");
                                    break;
                                default:
                                    validateList.add("{ required: true, messages: '请输入" + columnDTO.getFormLabel() + "', trigger: 'blur' }");
                                    break;
                            }
                        }
                        if (org.apache.commons.lang3.StringUtils.isNotEmpty(columnDTO.getValidateType())) {
                            switch (columnDTO.getFormType()) {
                                case "select":
                                case "radio":
                                case "checkbox":
                                case "date":
                                case "time":
                                case "datetime":
                                    validateList.add("{ required: true, validator: this." + columnDTO.getValidateType() + ", trigger: 'change' }");
                                    break;
                                default:
                                    validateList.add("{ required: true, validator: this." + columnDTO.getValidateType() + ", trigger: 'blur' }");
                                    break;
                            }
                        }
                        customMap.put("customFieldInfo", columnDTO);
                        if(validateList.size() > 0){
                            customMap.put("validate", validateList);
                        }

                        tableField.setCustomMap(customMap);
                    });
                }
            }
            // end

        }
        if (null == templateEngine) {
            // 为了兼容之前逻辑，采用 Velocity 引擎 【 默认 】
            templateEngine = new VelocityTemplateEngine();
        }

        if (preView) {
            templateEngine.init(this.pretreatmentConfigBuilder(config)).batchOutput();
        } else {
            // 模板引擎初始化执行文件输出
            templateEngine.init(this.pretreatmentConfigBuilder(config)).mkdirs().batchOutput().open();
        }

        logger.debug("==========================文件生成完成！！！==========================");

    }
}
