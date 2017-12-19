package com.wxf.utils;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Locale;
import java.util.Map;

/**
 * Created by TYZ027 on 2017/7/31.
 */
public class FreeMarkertUtil {
    private static Configuration config = new Configuration();

    /**
     * @param templateName 模板名字
     * @param root 模板根 用于在模板内输出结果集
     * @param out 输出对象 具体输出到哪里
     */
    public static void processTemplate(String templateName, Map<?,?> root, Writer out){
        try{
            //获得模板
            Template template=config.getTemplate(templateName,"utf-8");
            //生成文件（这里是我们是生成html）
            template.process(root, out);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }finally{
            try {
                out.close();
                out=null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * 初始化模板配置
     * @param servletContext javax.servlet.ServletContext
     * @param templateDir 模板位置
     */
    public static void initConfig(ServletContext servletContext, String templateDir){
        config.setLocale(Locale.CHINA);
        config.setDefaultEncoding("utf-8");
        config.setEncoding(Locale.CHINA, "utf-8");
        config.setServletContextForTemplateLoading(servletContext, templateDir);
        config.setObjectWrapper(new DefaultObjectWrapper());
    }
    public static void analysisTemplate(String templateName,
             String templateEncoding, Map<?, ?> root) {
                 try {
                         /**
                           * 创建Configuration对象
                           */
                         Configuration config = new Configuration();
                         /**
                           * 指定模板路径
                           */
                         File file = new File("src/test/java/ftl");
                         /**
                           * 设置要解析的模板所在的目录，并加载模板文件
                           */
                         config.setDirectoryForTemplateLoading(file);
                         /**
                           * 设置包装器，并将对象包装为数据模型
                          */
                         config.setObjectWrapper(new DefaultObjectWrapper());

                         /**
                         * 获取模板,并设置编码方式，这个编码必须要与页面中的编码格式一致
                        */
                        Template template = config.getTemplate(templateName,
                                         templateEncoding);
                         /**
             * 合并数据模型与模板
                           */
                        Writer out = new OutputStreamWriter(System.out);
                         template.process(root, out);
                         out.flush();
                         out.close();
                     } catch (IOException e) {
                         e.printStackTrace();
                    } catch (TemplateException e) {
                         e.printStackTrace();
                     }
            }
}
