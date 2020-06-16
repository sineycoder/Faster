package cn.siney.spring.config;

import cn.siney.interf.FasterScan;
import cn.siney.spring.Registrar.FasterImportBeanDefinitionRegistrar;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author siney
 * @createTime 2020-06-16
 **/
@Configuration
@FasterScan("aaa")
public class FasterConfig {

}
