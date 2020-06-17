package cn.siney.provider.spring.Registrar;

import cn.siney.provider.annotation.FasterScan;
import cn.siney.provider.annotation.FasterService;
import cn.siney.provider.registry.RedisRegistry;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @author siney
 * @createTime 2020-06-16
 **/
public class FasterImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar, ResourceLoaderAware {

    private ResourceLoader resourceLoader;

    private Map<String, String> bean;

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        Set<String> packages = scanPackages(importingClassMetadata);

        Map<String, String> bean = doScan(packages);

        register(bean, registry);

        this.bean = bean;
    }

    /**
     * 注册进BeanDefinitionMap，由spring管理
     * @param bean 接口名，实例化类名
     * @param registry 注册仓库
     */
    private void register(Map<String, String> bean, BeanDefinitionRegistry registry) {
        bean.forEach((key, value) -> {
            BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(value);
            builder.setScope(BeanDefinition.SCOPE_SINGLETON);
            AbstractBeanDefinition beanDefinition = builder.getBeanDefinition();
            registry.registerBeanDefinition(key, beanDefinition);
            RedisRegistry.getInstance().regist(key);
        });
    }

    private Map<String, String> doScan(Set<String> packages) {
        Map<String, String> bean = new LinkedHashMap<>();
        try {
            ResourcePatternResolver resolver = ResourcePatternUtils.getResourcePatternResolver(resourceLoader);
            CachingMetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(resourceLoader);
            for(String path : packages){
                String packagePath = "classpath*:" + path.replace(".", "/") + "/**/*.class";
                Resource[] resources = resolver.getResources(packagePath);
                for(Resource resource : resources){
                    MetadataReader metadataReader = metadataReaderFactory.getMetadataReader(resource);
                    ClassMetadata classMetadata = metadataReader.getClassMetadata();
                    AnnotationMetadata annotationMetadata = metadataReader.getAnnotationMetadata();
                    if(annotationMetadata.hasAnnotation(FasterService.class.getName())
                    && classMetadata.getInterfaceNames().length == 1){
                        String interfaceName = classMetadata.getInterfaceNames()[0];
                        String className = classMetadata.getClassName();
                        String name = (String) Objects.requireNonNull(annotationMetadata.getAnnotationAttributes(FasterService.class.getName())).get("value");
                        if(StringUtils.hasText(name)){
                            bean.put(name, className);
                        }else{
                            bean.put(interfaceName, className);
                        }
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return bean;
    }

    private Set<String> scanPackages(AnnotationMetadata importingClassMetadata) {
        AnnotationAttributes annotationAttributes = AnnotationAttributes
                .fromMap(importingClassMetadata.getAnnotationAttributes(FasterScan.class.getName()));
        String[] packages = annotationAttributes.getStringArray("value");
        Set<String> packagesSet = new LinkedHashSet<>(Arrays.asList(packages));
        return packagesSet;
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }
}
