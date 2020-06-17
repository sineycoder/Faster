package cn.siney.provider.annotation;

import cn.siney.provider.spring.Registrar.FasterImportBeanDefinitionRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author siney
 * @createTime 2020-06-16
 **/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(FasterImportBeanDefinitionRegistrar.class)
public @interface FasterScan {
    String[] value() default {};
}
