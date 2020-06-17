package cn.siney;

import cn.siney.provider.annotation.FasterScan;
import cn.siney.provider.server.FasterServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author siney
 * @Date 2020/6/17 14:52
 * @Version 1.0
 */
@Configuration
@FasterScan("cn.siney.service.impl")
public class Config {
    @Bean
    public FasterServer fasterServer(){
        return new FasterServer();
    }
}
