package com.server.tools;

import com.server.model.FactClassDescriptionInfo;
import com.server.model.FactFieldDescriptionInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 8/9/18
 * Time: 9:32 PM
 */

@Component
public class FactLoader {

    private static Logger logger = LoggerFactory.getLogger(FactLoader.class);

    private static final String MODELPATH = "com/server/";

    private String classPath;

    public void setClassPath(String classPath) {
        this.classPath = classPath;
    }

    @Autowired
    private ApplicationContext applicationContext;

    public List<FactClassDescriptionInfo> entityInfo() {
        List<FactClassDescriptionInfo> factClassDescriptionInfoList = new ArrayList<>();
        PathMatchingResourcePatternResolver pathMatchingResourcePatternResolver = new PathMatchingResourcePatternResolver();
        try {
            Resource[] metaInfResources = pathMatchingResourcePatternResolver.getResources(MODELPATH + this.classPath);
            Arrays.stream(metaInfResources).forEach(resource -> {
                FactClassDescriptionInfo factClassDescriptionInfo = new FactClassDescriptionInfo();
                List<FactFieldDescriptionInfo> factFieldDescriptionInfoList = new ArrayList<>();

                String className = resource.getFilename().substring(0, resource.getFilename().indexOf("."));
                className = className.substring(0, 1).toLowerCase() + className.substring(1, className.length());
                try {
                    Object object = applicationContext.getBean(className);
                    Annotation[] annotationClass = object.getClass().getAnnotations();
                    Arrays.stream(annotationClass).forEach(annotation -> {
                        if (annotation instanceof EntityAttributeInfo.ClassAnnotation) {
                            factClassDescriptionInfo.setClassName(((EntityAttributeInfo.ClassAnnotation) annotation).name());
                            factClassDescriptionInfo.setDescription(((EntityAttributeInfo.ClassAnnotation) annotation).desc());
                        }
                    });
                    if (null != factClassDescriptionInfo.getDescription()) {
                        Field[] fields = object.getClass().getDeclaredFields();
                        Arrays.stream(fields).forEach(field -> {
                            FactFieldDescriptionInfo factFieldDescriptionInfo = new FactFieldDescriptionInfo();
                            Annotation annotationField = field.getAnnotation(EntityAttributeInfo.FieldAnnotation.class);
                            factFieldDescriptionInfo.setFieldName(((EntityAttributeInfo.FieldAnnotation) annotationField).name());
                            factFieldDescriptionInfo.setDescription(((EntityAttributeInfo.FieldAnnotation) annotationField).desc());
                            factFieldDescriptionInfo.setType(((EntityAttributeInfo.FieldAnnotation) annotationField).type());
                            factFieldDescriptionInfoList.add(factFieldDescriptionInfo);
                        });
                        factClassDescriptionInfo.setFactFieldDescriptionInfoList(factFieldDescriptionInfoList);
                        factClassDescriptionInfoList.add(factClassDescriptionInfo);
                    }
                } catch (NoSuchBeanDefinitionException ex) {
                    logger.error(ex.toString());
                }
            });
        } catch (IOException e) {
            logger.error(e.toString());
        }
        return factClassDescriptionInfoList;
    }

}
