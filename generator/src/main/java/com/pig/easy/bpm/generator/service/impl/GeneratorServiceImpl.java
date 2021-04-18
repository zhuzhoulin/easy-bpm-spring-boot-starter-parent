package com.pig.easy.bpm.generator.service.impl;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.core.toolkit.IOUtils;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.pig.easy.bpm.common.entityError.EntityError;
import com.pig.easy.bpm.common.utils.BeanUtils;
import com.pig.easy.bpm.common.utils.CommonUtils;
import com.pig.easy.bpm.common.utils.Result;
import com.pig.easy.bpm.common.utils.StringUtils;
import com.pig.easy.bpm.generator.constant.GeneratorConstant;
import com.pig.easy.bpm.generator.dto.request.ColumnQueryDTO;
import com.pig.easy.bpm.generator.dto.request.TableStrategyConfigQueryDTO;
import com.pig.easy.bpm.generator.dto.request.TableStrategyConfigSaveOrUpdateDTO;
import com.pig.easy.bpm.generator.dto.request.TemplateQueryDTO;
import com.pig.easy.bpm.generator.dto.response.ColumnDTO;
import com.pig.easy.bpm.generator.dto.response.DbConfigDTO;
import com.pig.easy.bpm.generator.dto.response.TableStrategyConfigDTO;
import com.pig.easy.bpm.generator.dto.response.TemplateDTO;
import com.pig.easy.bpm.generator.generator.CustomAutoGenerator;
import com.pig.easy.bpm.generator.generator.CustomDbQuery;
import com.pig.easy.bpm.generator.generator.CustomFreemarkerTemplateEngine;
import com.pig.easy.bpm.generator.service.*;
import com.pig.easy.bpm.generator.utils.DbUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * todo:
 *
 * @author : zhoulin.zhu
 * @date : 2021/2/28 16:28
 */
@Slf4j
@Service
public class GeneratorServiceImpl implements GeneratorService {



    @Autowired
    CodeTableService codeTableService;
    @Autowired
    DbConfigService codeDbConfigService;
    @Autowired
    ColumnService codeColumnService;
    @Autowired
    TableStrategyConfigService tableStrategyConfigService;
    @Autowired
    TemplateService templateService;

    @Override
    public Result<Map<String, Object>> initAndExecute(Long dbId, Long templateGroupId,List<String> selectTemplateList,boolean download, boolean preView, String... tableNames) {
        return generatorCode(dbId,templateGroupId,selectTemplateList,null,download,preView,tableNames);
    }

    @Override
    public Result<Map<String, Object>> generatorCode(Long dbId, Long templateGroupId,List<String> selectTemplateList, TableStrategyConfigSaveOrUpdateDTO tableStrategyConfig, boolean download, boolean preView, String... tableNames) {

        if (log.isDebugEnabled()) {
            log.debug("==========================准备生成文件...==========================");
        }
        Result<DbConfigDTO> result1 = codeDbConfigService.getDbConfigById(dbId);
        if (result1.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return Result.responseError(result1.getEntityError());
        }

        DbConfigDTO codeDbConfigDTO = result1.getData();
        Result<Integer> result = codeTableService.syncTableIntoColunmTable(dbId, tableNames, true);
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return Result.responseError(result.getEntityError());
        }

        Map<String, Object> templateMap = new HashMap<>();
        List<TemplateDTO> templateList = new ArrayList<>();
        if(templateGroupId == null){
            templateGroupId = 1L;
        }
        if (CommonUtils.evalInt(templateGroupId) > 0) {
            TemplateQueryDTO templateQueryDTO = new TemplateQueryDTO();
            templateQueryDTO.setTemplateGroupId(templateGroupId);
            templateQueryDTO.setValidState(1);

            Result<List<TemplateDTO>> result4 = templateService.getListByCondition(templateQueryDTO);
            if (result4.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
                return Result.responseError(result4.getEntityError());
            }
            templateList = result4.getData();
            templateMap = templateList.stream().collect(Collectors.toMap(TemplateDTO::getTemplatePath, TemplateDTO::getTemplateContent, (oldData, newData) -> newData));
        }

        TableStrategyConfigDTO tableStrategyConfigDTO = null;

        if(tableStrategyConfig == null ){
            TableStrategyConfigQueryDTO tableStrategyConfigQueryDTO = new TableStrategyConfigQueryDTO();
            tableStrategyConfigQueryDTO.setDbId(dbId);
            tableStrategyConfigQueryDTO.setValidState(1);
            Result<List<TableStrategyConfigDTO>> result3 = tableStrategyConfigService.getListByCondition(tableStrategyConfigQueryDTO);

            if (result3.getEntityError().getCode() == EntityError.SUCCESS.getCode()
                    && result3.getData().size() > 0) {
                tableStrategyConfigDTO = result3.getData().get(0);
            }
        } else {
            tableStrategyConfigDTO = BeanUtils.switchToDTO(tableStrategyConfig,TableStrategyConfigDTO.class);
        }

        List<ColumnDTO> listHideField = new ArrayList<>();
        List<ColumnDTO> queryFields = new ArrayList<>();
        List<ColumnDTO> addHideField = new ArrayList<>();
        List<ColumnDTO> updateHideFields = new ArrayList<>();
        ColumnQueryDTO param = null;
        Result<List<ColumnDTO>> result2 = null;
        /* Map<tableName,Map<field,CodeColumnDTO>> */
        Map<String, Map<String, ColumnDTO>> tableMap = new HashMap<>();
        Map<String, ColumnDTO> fieldMap = null;
        for (String tableName : tableNames) {
            fieldMap = new HashMap<>();
            param = new ColumnQueryDTO();
            param.setDbId(dbId);
            param.setValidState(1);
            param.setTableName(tableName);
            result2 = codeColumnService.getListByCondition(param);
            if (result2.getEntityError().getCode() == EntityError.SUCCESS.getCode()) {
                fieldMap = result2.getData().stream().collect(Collectors.toMap(ColumnDTO::getColumnName, a -> a, (oldValue, newValue) -> newValue));
                result2.getData().stream().forEach(columnDTO -> {
                    if (columnDTO.getQueryFlag() == 1) {
                        queryFields.add(columnDTO);
                    }
                    if (columnDTO.getListFlag() == 0) {
                        listHideField.add(columnDTO);
                    }
                    if (columnDTO.getInsertFlag() == 0) {
                        addHideField.add(columnDTO);
                    }
                    if (columnDTO.getUpdateFlag() == 0) {
                        updateHideFields.add(columnDTO);
                    }
                });
            }
            //多个表单时 查询列表可能有BUG
            tableMap.put(tableName, fieldMap);
            templateMap.put("queryFields", queryFields);
            templateMap.put("listHideFields", listHideField);
            templateMap.put("addHideFields", addHideField);
            templateMap.put("updateHideFields", updateHideFields);
        }

        CustomFreemarkerTemplateEngine customFreemarkerTemplateEngine = new CustomFreemarkerTemplateEngine();
        DataSourceConfig dataSourceConfig = getDataSourceConfig(codeDbConfigDTO);

        if(download) {
            tableStrategyConfigDTO.setProjectPath(null);
            tableStrategyConfigDTO.setVuePath(null);
        }
        new CustomAutoGenerator(tableMap)
                .setTemplateEngine(customFreemarkerTemplateEngine)
                .setPackageInfo(getPackageConfig(tableStrategyConfigDTO))
                .setStrategy(getStrategyConfig(tableStrategyConfigDTO, tableNames))
                .setCfg(getInjectionConfig(tableStrategyConfigDTO,selectTemplateList, templateList, preView, templateMap))
                .setTemplate(getTemplateConfig())
                .setGlobalConfig(getGlobalConfig(tableStrategyConfigDTO))
                .setDataSource(dataSourceConfig).execute();

        if (log.isDebugEnabled()) {
            log.debug("==========================文件生成完成！！！==========================");
        }

        Map<String, Object> resultMap = new HashMap<>(16);
        Map<String, String> filePathMap = new HashMap<>(16);

        if (preView) {
            for (Map.Entry<String, String> entry : customFreemarkerTemplateEngine.preViewMap.entrySet()){
                if(entry.getKey().endsWith(GeneratorConstant.PATH)){
                    filePathMap.put(entry.getKey(),entry.getValue());
                } else {
                    resultMap.put(entry.getKey(),entry.getValue());
                }
            }

            Connection conn = DbUtils.getConn(dataSourceConfig.getDriverName(), dataSourceConfig.getUrl(), dataSourceConfig.getUsername(), dataSourceConfig.getPassword());
            CustomDbQuery customDbQuery = DbUtils.getCustomDbQuery(dataSourceConfig.getDbType());
            String queryCreateTableSql = customDbQuery.queryCreateTableSql();

            StringBuffer createTableBuffer = new StringBuffer();
            if (StringUtils.isNotEmpty(queryCreateTableSql)) {
                for (String tableName : tableNames) {
                    Map<String, Object> stringObjectMap = DbUtils.executeQuery(conn, String.format(queryCreateTableSql, tableName));
                    createTableBuffer.append(stringObjectMap.getOrDefault(GeneratorConstant.CREATE_TABLE, "") + "").append("\n\r");
                }
            }
            resultMap.put(GeneratorConstant.DDL, createTableBuffer.toString());
        }

        if(download) {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ZipOutputStream zip = new ZipOutputStream(outputStream);
            for (Map.Entry<String, String> entry : filePathMap.entrySet()) {
                try {
                    zip.putNextEntry(new ZipEntry(entry.getValue()));
                    org.apache.commons.io.IOUtils.write(resultMap.getOrDefault(entry.getKey().replaceAll(GeneratorConstant.PATH, ""), "").toString(), zip, ConstVal.UTF8);
                    zip.flush();
                    zip.closeEntry();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            org.apache.commons.io.IOUtils.closeQuietly(zip);
            resultMap.put(GeneratorConstant.ZIP, outputStream.toByteArray());
            IOUtils.closeQuietly(outputStream);
        }
        return Result.responseSuccess(resultMap);

    }


    private DataSourceConfig getDataSourceConfig(DbConfigDTO codeDbConfigDTO) {
        return DbUtils.buildDataSourceConfig(codeDbConfigDTO.getDbDriverName(), codeDbConfigDTO.getDbUrl(), codeDbConfigDTO.getDbUsername(), codeDbConfigDTO.getDbPassword());
    }

    private PackageConfig getPackageConfig(TableStrategyConfigDTO tableStrategyConfigDTO) {
        PackageConfig packageConfig = new PackageConfig();
        String parentPackage = StringUtils.isEmpty(tableStrategyConfigDTO.getParentPackage()) ? GeneratorConstant.DEFAYLT_PARENT_PACKAGE : tableStrategyConfigDTO.getParentPackage();
        //提取公共父级包名
        packageConfig.setParent(parentPackage);
        //设置controller信息
        packageConfig.setController("controller");
        //设置ModuleName信息
        if (StringUtils.isNotEmpty(packageConfig.getModuleName())) {
            packageConfig.setModuleName(packageConfig.getModuleName());
        }
        packageConfig.setMapper("mapper");
        packageConfig.setServiceImpl("service.impl");
        packageConfig.setService("service");
        //设置实体类信息
        packageConfig.setEntity("entity");
        packageConfig.setXml("mapper");
        return packageConfig;
    }

    private GlobalConfig getGlobalConfig(TableStrategyConfigDTO tableStrategyConfigDTO) {

        String codePath = tableStrategyConfigDTO.getProjectPath();
        String outputDir = codePath + File.separator + String.join(File.separator, Arrays.asList("src", "main", "java"));

        GlobalConfig globalConfig = new GlobalConfig();
        //设置是否支持AR模式
        globalConfig.setActiveRecord(true)
                //设置生成代码的作者
                .setAuthor(StringUtils.defaultIfEmpty(tableStrategyConfigDTO.getAuthor(), GeneratorConstant.DEFAYLT_AUTHOR))
                //设置输出代码的位置
                .setOutputDir(outputDir)
                .setOpen(false)
                /*XML 设置映射结果 ResultMap*/
                .setBaseResultMap(true)
                // 是否在xml中添加二级缓存配置,默认false
                .setEnableCache(false)
                /*XML 设置表列 ColumnList*/
                .setBaseColumnList(true)
                /*自定义文件命名，注意 %s 会自动填充表实体属性！*/
                .setControllerName(GeneratorConstant.CONTROLLER_NAME)
                .setServiceName(GeneratorConstant.SERVICE_NAME)
                .setServiceImplName(GeneratorConstant.SERVICE_IMPL_NAME)
                .setEntityName(GeneratorConstant.ENTITY_NAME)
                .setMapperName(GeneratorConstant.MAPPER_NAME)
                .setXmlName(GeneratorConstant.XML_NAME)
                /*每次生成，是否覆盖之前的文件（慎重考虑啊）*/
                .setFileOverride(CommonUtils.evalInt(tableStrategyConfigDTO.getOverride()) == 1)
        ;

        return globalConfig;
    }

    private TemplateConfig getTemplateConfig() {
        TemplateConfig templateConfig = new TemplateConfig();
        templateConfig.setController(null);
        templateConfig.setEntity(null);
        templateConfig.setXml(null);
        templateConfig.setMapper(null);
        templateConfig.setService(null);
        templateConfig.setServiceImpl(null);
        return templateConfig;
    }

    private InjectionConfig getInjectionConfig(TableStrategyConfigDTO tableStrategyConfigDTO,List<String> selectTemplateList, List<TemplateDTO> templateList, boolean preView, Map<String, Object> initMap) {

        List<String> selectTemplatePathList = selectTemplateList == null ? new ArrayList<>() : selectTemplateList;
        String codePath = StringUtils.isNotEmpty(tableStrategyConfigDTO.getProjectPath()) ? tableStrategyConfigDTO.getProjectPath() : GeneratorConstant.CODE_PATH;

        String outputDir = codePath + File.separator + GeneratorConstant.SRC_MAIN_JAVA;
        String vuePath = StringUtils.isNotEmpty(tableStrategyConfigDTO.getVuePath()) ? tableStrategyConfigDTO.getVuePath() : GeneratorConstant.VUE_PATH;
        String contentPath = tableStrategyConfigDTO.getContextPath();
        String parentPackage = StringUtils.isEmpty(tableStrategyConfigDTO.getParentPackage()) ? GeneratorConstant.DEFAYLT_PARENT_PACKAGE : tableStrategyConfigDTO.getParentPackage();
        String dtoResponse = parentPackage + ".dto.response";
        String dtoRequest = parentPackage + ".dto.request";
        String voRequest = parentPackage + ".vo.request";

        Map<String, Object> map = new HashMap<String, Object>();
        map.put(GeneratorConstant.PRE_VIEW, preView);
        map.put(GeneratorConstant.SELECT_TEMPLATE_PATH_LIST, selectTemplatePathList);
        map.put("dtoResponse", dtoResponse);
        map.put("dtoRequest", dtoRequest);
        map.put("voRequest", voRequest);
        map.put("contentPath", contentPath);
        map.put("codePath", codePath);
        map.put("outputDir", outputDir);
        map.put("vuePath", vuePath);
        map.put("parentPackage", parentPackage);
        //集成注入设置
        InjectionConfig cfg = new InjectionConfig() {

            // 获取 ${cfg}
            @Override
            public void initMap() {
                if (initMap != null) {
                    map.putAll(initMap);
                }
                this.setMap(map);
            }
        };

        // 可以也放数据库配置
        List<FileOutConfig> focList = new ArrayList<>();
        for (TemplateDTO template : templateList) {
            focList.add(new FileOutConfig(template.getTemplatePath()) {
                @Override
                public String outputFile(TableInfo tableInfo) {
                    String outputPath = null;
                    // 0 前端 1 后端
                    if (CommonUtils.evalInt(template.getTemplateType()) == 0) {
                        outputPath = vuePath + File.separator + (template.getOutputFileName().contains("%s") ?
                                String.format(template.getOutputFileName(), com.baomidou.mybatisplus.core.toolkit.StringUtils.firstToLowerCase(tableInfo.getEntityName())) : template.getOutputFileName());
                    } else {
                        // 区分 mapper.xml
                        if (template.getOutputFileName().endsWith("xml")) {
                            outputPath = codePath + File.separator + String.join(File.separator, Arrays.asList("src", "main", "resources"))
                                    + File.separator + (template.getOutputFileName().contains("%s") ? String.format(template.getOutputFileName(), tableInfo.getEntityName()) : template.getOutputFileName());
                        } else {
                            outputPath = joinPath(outputDir ,parentPackage)  + File.separator
                                    + (template.getOutputFileName().contains("%s") ? String.format(template.getOutputFileName(), tableInfo.getEntityName()) : template.getOutputFileName());
                        }
                    }
                    return outputPath;
                }
            });
        }

        if (templateList.size() == 0) {
            focList.add(new FileOutConfig("/templates/entity.java.ftl") {
                @Override
                public String outputFile(TableInfo tableInfo) {
                    return joinPath(outputDir ,parentPackage)  + File.separator + String.join(File.separator, Arrays.asList("entity"))
                            + File.separator + tableInfo.getEntityName() + "DO.java";
                }
            });
            focList.add(new FileOutConfig("/templates/mapper.xml.ftl") {
                @Override
                public String outputFile(TableInfo tableInfo) {
                    return codePath + File.separator + String.join(File.separator, Arrays.asList("src", "main", "resources", "mapper"))
                            + "/" + tableInfo.getMapperName() + ".xml";
                }
            });
            focList.add(new FileOutConfig("/templates/queryVO.java.ftl") {
                @Override
                public String outputFile(TableInfo tableInfo) {
                    return joinPath(outputDir ,parentPackage)  + File.separator + String.join(File.separator, Arrays.asList("vo", "request"))
                            + File.separator + tableInfo.getEntityName() + "QueryVO.java";
                }
            });
            focList.add(new FileOutConfig("/templates/saveOrUpdateVO.java.ftl") {
                @Override
                public String outputFile(TableInfo tableInfo) {
                    return joinPath(outputDir ,parentPackage)  + File.separator + String.join(File.separator, Arrays.asList("vo", "request"))
                            + File.separator + tableInfo.getEntityName() + "SaveOrUpdateVO.java";
                }
            });
            focList.add(new FileOutConfig("/templates/queryDTO.java.ftl") {
                @Override
                public String outputFile(TableInfo tableInfo) {
                    return joinPath(outputDir ,parentPackage)  + File.separator + String.join(File.separator, Arrays.asList("dto", "request"))
                            + File.separator + tableInfo.getEntityName() + "QueryDTO.java";
                }
            });
            focList.add(new FileOutConfig("/templates/saveOrUpdateDTO.java.ftl") {
                @Override
                public String outputFile(TableInfo tableInfo) {
                    return joinPath(outputDir ,parentPackage)  + File.separator + String.join(File.separator, Arrays.asList("dto", "request"))
                            + File.separator + tableInfo.getEntityName() + "SaveOrUpdateDTO.java";
                }
            });
            focList.add(new FileOutConfig("/templates/dto.java.ftl") {
                @Override
                public String outputFile(TableInfo tableInfo) {
                    return joinPath(outputDir ,parentPackage)  + File.separator + String.join(File.separator, Arrays.asList("dto", "response"))
                            + File.separator + tableInfo.getEntityName() + "DTO.java";
                }
            });
            focList.add(new FileOutConfig("/templates/downloadDTO.java.ftl") {
                @Override
                public String outputFile(TableInfo tableInfo) {
                    return joinPath(outputDir ,parentPackage) + File.separator + String.join(File.separator, Arrays.asList("dto", "response"))
                            + File.separator + tableInfo.getEntityName() + "ExportDTO.java";
                }
            });
            focList.add(new FileOutConfig("/templates/api.ftl") {
                @Override
                public String outputFile(TableInfo tableInfo) {
                    return vuePath + File.separator + "api" + File.separator + tableInfo.getEntityPath() + ".js";
                }
            });
            focList.add(new FileOutConfig("/templates/index.ftl") {
                @Override
                public String outputFile(TableInfo tableInfo) {
                    return vuePath + File.separator + "views" + File.separator + "generator" + File.separator + tableInfo.getEntityPath() + File.separator + "index.vue";
                }
            });
        }
        cfg.setFileOutConfigList(focList);

        return cfg;
    }

    private String joinPath(String parentDir, String packageName) {
        if (com.baomidou.mybatisplus.core.toolkit.StringUtils.isEmpty(parentDir)) {
            parentDir = System.getProperty(ConstVal.JAVA_TMPDIR);
        }
        if (!com.baomidou.mybatisplus.core.toolkit.StringUtils.endsWith(parentDir, File.separator)) {
            parentDir += File.separator;
        }
        packageName = packageName.replaceAll("\\.", StringPool.BACK_SLASH + File.separator);
        return parentDir + packageName;
    }

    private StrategyConfig getStrategyConfig(TableStrategyConfigDTO tableStrategyConfigDTO, String... tableNames) {

        // 自定义需要填充的字段 数据库中的字段
        List<TableFill> tableFillList = new ArrayList<>();
        tableFillList.add(new TableFill(GeneratorConstant.CREATE_TIME, FieldFill.INSERT));
        tableFillList.add(new TableFill(GeneratorConstant.UPDATE_TIME, FieldFill.INSERT_UPDATE));

        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig
                //全局大写命名是否开启
                .setCapitalMode(true)
                //【实体】是否为lombok模型
                .setEntityLombokModel(true)
                //表名生成策略 下划线转驼峰
                .setNaming(NamingStrategy.underline_to_camel)
                //自动填充设置
                .setTableFillList(tableFillList)
                .setVersionFieldName("version")
                .setEntityBooleanColumnRemoveIsPrefix(true)
                /*【实体】是否生成字段常量（默认 false）public static final String ID = "test_id";*/
                .setEntityColumnConstant(false)
                /*【实体】是否为构建者模型（默认 false）public User setName(String name) {this.name = name; return this;}*/
                .setEntityBuilderModel(true)
                /*【实体】是否为lombok模型（默认 false）*/
                .setEntityLombokModel(true)
        ;

        if (tableStrategyConfigDTO != null) {
            if (tableNames != null && tableNames.length > 0) {
                strategyConfig.setInclude(tableNames);
            }
            if (StringUtils.isNotEmpty(tableStrategyConfigDTO.getTablePrefix())) {
                strategyConfig.setTablePrefix(tableStrategyConfigDTO.getTablePrefix());
            }
            if (StringUtils.isNotEmpty(tableStrategyConfigDTO.getFieldPrefix())) {
                strategyConfig.setFieldPrefix(tableStrategyConfigDTO.getFieldPrefix());
            }
            if (StringUtils.isNotEmpty(tableStrategyConfigDTO.getSuperEntityClass())) {
                strategyConfig.setSuperEntityColumns(tableStrategyConfigDTO.getSuperEntityClass());
            }
            if (StringUtils.isNotEmpty(tableStrategyConfigDTO.getSuperMapperClass())) {
                strategyConfig.setSuperMapperClass(tableStrategyConfigDTO.getSuperMapperClass());
            }
            if (StringUtils.isNotEmpty(tableStrategyConfigDTO.getSuperServiceImplClass())) {
                strategyConfig.setSuperServiceImplClass(tableStrategyConfigDTO.getSuperServiceImplClass());
            }
            if (StringUtils.isNotEmpty(tableStrategyConfigDTO.getSuperServiceClass())) {
                strategyConfig.setSuperServiceClass(tableStrategyConfigDTO.getSuperServiceClass());
            }
            if (StringUtils.isNotEmpty(tableStrategyConfigDTO.getSuperControllerClass())) {
                strategyConfig.setSuperControllerClass(tableStrategyConfigDTO.getSuperControllerClass());
            }
        }

        return strategyConfig;
    }
}
