package com.pig.easy.bpm.generator.generator;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.ConstVal;
import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.FileType;
import com.baomidou.mybatisplus.generator.engine.AbstractTemplateEngine;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.pig.easy.bpm.generator.constant.GeneratorConstant;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.util.*;

/**
 * todo:
 *
 * @author : zhoulin.zhu
 * @date : 2021/2/20 16:40
 */
@Component
public class CustomFreemarkerTemplateEngine extends AbstractTemplateEngine {

    private Configuration configuration;

    public Map<String, String> preViewMap;

    public CustomFreemarkerTemplateEngine() {
        super();
        preViewMap = Collections.synchronizedMap(new HashMap<>());
    }

    @Autowired
    FreeMarkerConfigurationFactory freeMarkerConfigurationFactory;


    @Override
    public AbstractTemplateEngine batchOutput() {
        try {
            List<TableInfo> tableInfoList = getConfigBuilder().getTableInfoList();

            boolean preView = false;
            for (TableInfo tableInfo : tableInfoList) {
                Map<String, Object> objectMap = getObjectMap(tableInfo);
                Map<String, String> pathInfo = getConfigBuilder().getPathInfo();
                TemplateConfig template = getConfigBuilder().getTemplate();

                // 自定义内容
                InjectionConfig injectionConfig = getConfigBuilder().getInjectionConfig();
                if (null != injectionConfig) {
                    injectionConfig.initMap();
                    objectMap.put("cfg", injectionConfig.getMap());
                    try {
                        preView = Boolean.valueOf(((Map) objectMap.get("cfg")).get(GeneratorConstant.PRE_VIEW) + "");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    // 是否有勾选 需要生成模块
                    List<String> selectTemplatePathList = injectionConfig.getMap().get(GeneratorConstant.SELECT_TEMPLATE_PATH_LIST) != null ?
                            (List<String>)injectionConfig.getMap().get(GeneratorConstant.SELECT_TEMPLATE_PATH_LIST) : new ArrayList<>();

                    List<FileOutConfig> focList = injectionConfig.getFileOutConfigList();
                    if (CollectionUtils.isNotEmpty(focList)) {
                        for (FileOutConfig foc : focList) {
                            if(selectTemplatePathList.size() == 0
                                    || selectTemplatePathList.contains(foc.getTemplatePath()) ) {
                                if (preView) {
                                    writer(objectMap, foc.getTemplatePath(), foc.outputFile(tableInfo));
                                } else {
                                    if (isCreate(FileType.OTHER, foc.outputFile(tableInfo))) {
                                        writer(objectMap, foc.getTemplatePath(), foc.outputFile(tableInfo));
                                    }
                                }
                            }
                        }
                    }
                }
                // Mp.java
                String entityName = tableInfo.getEntityName();
                if (null != entityName && null != pathInfo.get(ConstVal.ENTITY_PATH)) {
                    String entityFile = String.format((pathInfo.get(ConstVal.ENTITY_PATH) + File.separator + "%s" + suffixJavaOrKt()), entityName);
                    if (preView) {
                        writer(objectMap, templateFilePath(template.getEntity(getConfigBuilder().getGlobalConfig().isKotlin())), entityFile);
                    } else {
                        if (isCreate(FileType.ENTITY, entityFile)) {
                            writer(objectMap, templateFilePath(template.getEntity(getConfigBuilder().getGlobalConfig().isKotlin())), entityFile);
                        }
                    }
                }
                // MpMapper.java
                if (null != tableInfo.getMapperName() && null != pathInfo.get(ConstVal.MAPPER_PATH)) {
                    String mapperFile = String.format((pathInfo.get(ConstVal.MAPPER_PATH) + File.separator + tableInfo.getMapperName() + suffixJavaOrKt()), entityName);
                    if (preView) {
                        writer(objectMap, templateFilePath(template.getMapper()), mapperFile);
                    } else {
                        if (isCreate(FileType.MAPPER, mapperFile)) {
                            writer(objectMap, templateFilePath(template.getMapper()), mapperFile);
                        }
                    }
                }
                // MpMapper.xml
                if (null != tableInfo.getXmlName() && null != pathInfo.get(ConstVal.XML_PATH)) {
                    String xmlFile = String.format((pathInfo.get(ConstVal.XML_PATH) + File.separator + tableInfo.getXmlName() + ConstVal.XML_SUFFIX), entityName);
                    if (preView) {
                        writer(objectMap, templateFilePath(template.getXml()), xmlFile);
                    } else {
                        if (isCreate(FileType.XML, xmlFile)) {
                            writer(objectMap, templateFilePath(template.getXml()), xmlFile);
                        }
                    }
                }
                // IMpService.java
                if (null != tableInfo.getServiceName() && null != pathInfo.get(ConstVal.SERVICE_PATH)) {
                    String serviceFile = String.format((pathInfo.get(ConstVal.SERVICE_PATH) + File.separator + tableInfo.getServiceName() + suffixJavaOrKt()), entityName);
                    if (preView) {
                        writer(objectMap, templateFilePath(template.getService()), serviceFile);
                    } else {
                        if (isCreate(FileType.SERVICE, serviceFile)) {
                            writer(objectMap, templateFilePath(template.getService()), serviceFile);
                        }
                    }
                }
                // MpServiceImpl.java
                if (null != tableInfo.getServiceImplName() && null != pathInfo.get(ConstVal.SERVICE_IMPL_PATH)) {
                    String implFile = String.format((pathInfo.get(ConstVal.SERVICE_IMPL_PATH) + File.separator + tableInfo.getServiceImplName() + suffixJavaOrKt()), entityName);

                    if (preView) {
                        writer(objectMap, templateFilePath(template.getServiceImpl()), implFile);
                    } else {
                        if (isCreate(FileType.SERVICE_IMPL, implFile)) {
                            writer(objectMap, templateFilePath(template.getServiceImpl()), implFile);
                        }
                    }
                }
                // MpController.java
                if (null != tableInfo.getControllerName() && null != pathInfo.get(ConstVal.CONTROLLER_PATH)) {
                    String controllerFile = String.format((pathInfo.get(ConstVal.CONTROLLER_PATH) + File.separator + tableInfo.getControllerName() + suffixJavaOrKt()), entityName);

                    if (preView) {
                        writer(objectMap, templateFilePath(template.getController()), controllerFile);
                    } else {
                        if (isCreate(FileType.CONTROLLER, controllerFile)) {
                            writer(objectMap, templateFilePath(template.getController()), controllerFile);
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error("无法创建文件，请检查配置信息！", e);
        }
        return this;
    }

    @Override
    public AbstractTemplateEngine mkdirs() {
        return super.mkdirs();
    }

    @Override
    public void open() {
        super.open();
    }

    @Override
    public CustomFreemarkerTemplateEngine init(ConfigBuilder configBuilder) {
        super.init(configBuilder);
        configuration = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
        configuration.setDefaultEncoding(ConstVal.UTF8);
        configuration.setClassForTemplateLoading(FreemarkerTemplateEngine.class, StringPool.SLASH);
        return this;
    }

    /**
     * 配置 freemarker configuration
     *
     * @return
     */
    private Configuration configuration() {
        Configuration configuration = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
        StringTemplateLoader templateLoader = new StringTemplateLoader();
        configuration.setTemplateLoader(templateLoader);
        configuration.setDefaultEncoding(ConstVal.UTF8);
        return configuration;
    }

    @Override
    public void writer(Map<String, Object> objectMap, String templatePath, String outputFile) throws Exception {

        System.out.println("模板:" + templatePath + ";  文件:" + outputFile);

        Map<String, Object> cfg = (Map<String, Object>) objectMap.get("cfg");
        Template template = null;
        // 如果数据库存在 则从数据库获取配置，如果不存在，则从 /resource/template 获取配置
        if(cfg.get(templatePath) != null){
            Configuration customConfiguration = configuration();
            StringTemplateLoader stringLoader = new StringTemplateLoader();
            // 注意 这里的template 不能包含 \ 字符
            String templateName = getFileName(outputFile);
            stringLoader.putTemplate(templateName , cfg.get(templatePath) + "" );
            customConfiguration.setTemplateLoader(stringLoader);
            template = customConfiguration.getTemplate(templateName , ConstVal.UTF8);
        } else {
            template = configuration.getTemplate(templatePath);
        }

        if (Boolean.valueOf((cfg).get(GeneratorConstant.PRE_VIEW) + "")) {
            StringWriter stringWriter = new StringWriter(2048);
            template.process(objectMap, stringWriter);
            preViewMap.put(getFileName(outputFile), stringWriter.toString());
            preViewMap.put(getFileName(outputFile)+"_path", outputFile);
            stringWriter.close();
        } else {
            try (FileOutputStream fileOutputStream = new FileOutputStream(outputFile)) {
                template.process(objectMap, new OutputStreamWriter(fileOutputStream, ConstVal.UTF8));
            }
            logger.debug("模板:" + templatePath + ";  文件:" + outputFile);
        }
    }

    @Override
    public String templateFilePath(String filePath) {
        return filePath + ".ftl";
    }

    private String getFileName(String outputFile) {

        if (StringUtils.isNotEmpty(outputFile)) {
            String regex = "\\\\";
            int length = outputFile.split(regex).length;
            String temp = length > 0 ? outputFile.split(regex)[length - 1] :outputFile.split(regex)[length];
            regex = "/";
            length = temp.split(regex).length;
            return length > 0 ? temp.split(regex)[length - 1] :temp.split(regex)[length];
        }
        return outputFile;
    }
}
