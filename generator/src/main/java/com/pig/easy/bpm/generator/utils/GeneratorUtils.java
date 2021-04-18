package com.pig.easy.bpm.generator.utils;

import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.NullCacheStorage;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

/**
 * todo:
 *
 * @author : zhoulin.zhu
 * @date : 2021/2/5 14:08
 */
public class GeneratorUtils {

    public static final Map<String, String> MYSQL_TYPE_TO_JAVA_TYPE_MAP = new ConcurrentHashMap<>();
    public static final Map<String, Class> MYSQL_TYPE_TO_JAVA_TYPE_CLASS_MAP = new ConcurrentHashMap<>();

    static {
        MYSQL_TYPE_TO_JAVA_TYPE_MAP.put("CHAR", "String");
        MYSQL_TYPE_TO_JAVA_TYPE_MAP.put("VARCHAR", "String");
        MYSQL_TYPE_TO_JAVA_TYPE_MAP.put("NUMERIC", "java.math.BigDecimal");
        MYSQL_TYPE_TO_JAVA_TYPE_MAP.put("DECIMAL", "java.math.BigDecimal");
        MYSQL_TYPE_TO_JAVA_TYPE_MAP.put("TINYINT", "byte");
        MYSQL_TYPE_TO_JAVA_TYPE_MAP.put("SMALLINT", "short");
        MYSQL_TYPE_TO_JAVA_TYPE_MAP.put("INTEGER", "int");
        MYSQL_TYPE_TO_JAVA_TYPE_MAP.put("BIGINT", "long");
        MYSQL_TYPE_TO_JAVA_TYPE_MAP.put("REAL", "float");
        MYSQL_TYPE_TO_JAVA_TYPE_MAP.put("FLOAT", "double");
        MYSQL_TYPE_TO_JAVA_TYPE_MAP.put("DOUBLE", "double");
        MYSQL_TYPE_TO_JAVA_TYPE_MAP.put("DATE", "java.sql.Date");
        MYSQL_TYPE_TO_JAVA_TYPE_MAP.put("TIMESTAMP", "java.sql.Timestamp");
        MYSQL_TYPE_TO_JAVA_TYPE_MAP.put("DATETIME", "java.sql.Date");
        MYSQL_TYPE_TO_JAVA_TYPE_MAP.put("CLOB", "String");
        MYSQL_TYPE_TO_JAVA_TYPE_MAP.put("BLOB", "String");

        MYSQL_TYPE_TO_JAVA_TYPE_CLASS_MAP.put("CHAR", java.lang.String.class);
        MYSQL_TYPE_TO_JAVA_TYPE_CLASS_MAP.put("VARCHAR", java.lang.String.class);
        MYSQL_TYPE_TO_JAVA_TYPE_CLASS_MAP.put("NUMERIC", java.math.BigDecimal.class);
        MYSQL_TYPE_TO_JAVA_TYPE_CLASS_MAP.put("DECIMAL", java.math.BigDecimal.class);
        MYSQL_TYPE_TO_JAVA_TYPE_CLASS_MAP.put("TINYINT", Byte.TYPE);
        MYSQL_TYPE_TO_JAVA_TYPE_CLASS_MAP.put("SMALLINT", Short.TYPE);
        MYSQL_TYPE_TO_JAVA_TYPE_CLASS_MAP.put("INTEGER", Integer.TYPE);
        MYSQL_TYPE_TO_JAVA_TYPE_CLASS_MAP.put("BIGINT", Long.TYPE);
        MYSQL_TYPE_TO_JAVA_TYPE_CLASS_MAP.put("REAL", Float.TYPE);
        MYSQL_TYPE_TO_JAVA_TYPE_CLASS_MAP.put("FLOAT", Double.TYPE);
        MYSQL_TYPE_TO_JAVA_TYPE_CLASS_MAP.put("DOUBLE", Double.TYPE);
        MYSQL_TYPE_TO_JAVA_TYPE_CLASS_MAP.put("DATE", java.sql.Date.class);
        MYSQL_TYPE_TO_JAVA_TYPE_CLASS_MAP.put("TIMESTAMP", java.sql.Time.class);
        MYSQL_TYPE_TO_JAVA_TYPE_CLASS_MAP.put("DATETIME", java.sql.Date.class);
        MYSQL_TYPE_TO_JAVA_TYPE_CLASS_MAP.put("CLOB", java.lang.String.class);
        MYSQL_TYPE_TO_JAVA_TYPE_CLASS_MAP.put("BLOB", java.lang.String.class);
    }

    private static final String TIMESTAMP = "Timestamp";

    private static final String BIGDECIMAL = "BigDecimal";

    public static final String PK = "PRI";

    public static final String EXTRA = "auto_increment";

    private static final String ENCODE = "UTF-8";

    private static final Configuration CONFIGURATION = new Configuration(Configuration.getVersion());

    private GeneratorUtils() {
        CONFIGURATION.setTemplateLoader(new ClassTemplateLoader(FreeMarkerTemplateUtils.class, "/templates"));
        CONFIGURATION.setDefaultEncoding(ENCODE);
        CONFIGURATION.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        CONFIGURATION.setCacheStorage(NullCacheStorage.INSTANCE);
    }

    public static Template getTemplate(String templateName) throws IOException {
        try {
            return CONFIGURATION.getTemplate(templateName);
        } catch (IOException e) {
            throw e;
        }
    }

    public static void clearCache() {
        CONFIGURATION.clearTemplateCache();
    }

    /**
     * 获取后端代码模板名称
     *
     * @return List
     */
    private static List<String> getTemplateNames() {
        List<String> templateNames = new ArrayList<>();
        templateNames.add("Entity");
        templateNames.add("Dto");
        templateNames.add("Mapper");
        templateNames.add("Controller");
        templateNames.add("QueryCriteria");
        templateNames.add("Service");
        templateNames.add("ServiceImpl");
        templateNames.add("Repository");
        return templateNames;
    }

    /**
     * 获取前端代码模板名称
     *
     * @return List
     */
    private static List<String> getFrontTemplateNames() {
        List<String> templateNames = new ArrayList<>();
        templateNames.add("index");
        templateNames.add("api");
        return templateNames;
    }


    private static Pattern linePattern = Pattern.compile("_(\\w)");
    private static Pattern humpPattern = Pattern.compile("[A-Z]");
    private static final char DEFAULT_SEPARATOR = '_';


    public static String toUnderlineName(String s) {
        if (s == null) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        boolean upperCase = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            boolean nextUpperCase = true;

            if (i < (s.length() - 1)) {
                nextUpperCase = Character.isUpperCase(s.charAt(i + 1));
            }

            if ((i >= 0) && Character.isUpperCase(c)) {
                if (!upperCase || !nextUpperCase) {
                    if (i > 0) {
                        sb.append(DEFAULT_SEPARATOR);
                    }
                }
                upperCase = true;
            } else {
                upperCase = false;
            }

            sb.append(Character.toLowerCase(c));
        }

        return sb.toString();
    }

    public static String toCamelCase(String s) {
        if (s == null) {
            return null;
        }

        s = s.toLowerCase();

        StringBuilder sb = new StringBuilder(s.length());
        boolean upperCase = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c == DEFAULT_SEPARATOR) {
                upperCase = true;
            } else if (upperCase) {
                sb.append(Character.toUpperCase(c));
                upperCase = false;
            } else {
                sb.append(c);
            }
        }

        return sb.toString();
    }

    public static String toCapitalizeCamelCase(String s) {
        if (s == null) {
            return null;
        }
        s = toCamelCase(s);
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

    public static String process(String content, Map<String, Object> businessData) {
        String formatContent = "";
        try {
            Configuration configuration = new Configuration(Configuration.getVersion());
            StringTemplateLoader stringLoader = new StringTemplateLoader();
            stringLoader.putTemplate("template", content);
            configuration.setTemplateLoader(stringLoader);
            freemarker.template.Template temp = null;
            temp = configuration.getTemplate("template", ENCODE);
            StringWriter stringWriter = new StringWriter(2048);
            temp.process(businessData, stringWriter);
            formatContent = stringWriter.toString();
            stringWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }

        return formatContent;
    }


    public static String process(Template template, Map<String, Object> businessData) {
        String formatContent = "";
        try {
            StringWriter stringWriter = new StringWriter(2048);
            template.process(businessData, stringWriter);
            formatContent = stringWriter.toString();
            stringWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
        return formatContent;
    }

    private static void genFile(File file, Template template, Map<String, Object> map) throws IOException {
        // 生成目标文件
        Writer writer = null;
        try {
            touch(file);
            writer = new FileWriter(file);
            template.process(map, writer);
        } catch (TemplateException | IOException e) {
            throw new RuntimeException(e);
        } finally {
            assert writer != null;
            writer.close();
        }
    }

    public static void touch(File file) {
        long currentTime = System.currentTimeMillis();
        if (!file.exists()) {
            System.err.println("file not found:" + file.getName());
            System.err.println("Create a new file:" + file.getName());
            try {
                if (file.createNewFile()) {
                    System.out.println("Succeeded!");
                } else {
                    System.err.println("Create file failed!");
                }
            } catch (IOException e) {
                System.err.println("Create file failed!");
                e.printStackTrace();
            }
        }
        boolean result = file.setLastModified(currentTime);
        if (!result) {
            System.err.println("touch failed: " + file.getName());
        }
    }

    public static void main(String[] args) {


    }
}
