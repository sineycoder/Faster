package cn.siney.spring.Registrar;

import cn.siney.interf.FasterScan;
import cn.siney.spring.config.FasterConfig;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author siney
 * @createTime 2020-06-16
 **/
public class FasterImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        AnnotationAttributes annotationAttributes = AnnotationAttributes.fromMap(importingClassMetadata.getAnnotationAttributes(FasterScan.class.getName()));
        if(annotationAttributes != null){
            String[] packages = annotationAttributes.getStringArray("value");
            for(String p : packages){
                doScan(p);
            }
        }
    }

    private void doScan(String p) {

    }
}
