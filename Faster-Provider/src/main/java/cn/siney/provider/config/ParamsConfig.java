package cn.siney.provider.config;

import cn.siney.PropertiesUtils;
import org.springframework.util.ObjectUtils;

import java.util.Properties;

public class ParamsConfig {

    public static final String REGISTRY = "faster:provider:";

    private int serverPort = 8888;

    private int redisPort = 6379;

    private String redisHostname = "localhost";

    private static ParamsConfig instance = new ParamsConfig();

    private ParamsConfig(){
        Properties properties = PropertiesUtils.analyzeProperties("faster.properties");
        if(!ObjectUtils.isEmpty(properties) && properties.size() > 0){
            String serverPort = properties.getProperty("faster.port");
            String redisPort = properties.getProperty("faster.redis.port");
            String redisHostname = properties.getProperty("faster.redis.hostname");
            if(!ObjectUtils.isEmpty(serverPort))this.serverPort = Integer.parseInt(serverPort);
            if(!ObjectUtils.isEmpty(redisPort))this.redisPort = Integer.parseInt(redisPort);
            if(!ObjectUtils.isEmpty(redisHostname))this.redisHostname = redisHostname;
        }
    }

    public static ParamsConfig getInstance(){
        return instance;
    }

    public int getServerPort() {
        return serverPort;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    public int getRedisPort() {
        return redisPort;
    }

    public void setRedisPort(int redisPort) {
        this.redisPort = redisPort;
    }

    public String getRedisHostname() {
        return redisHostname;
    }

    public void setRedisHostname(String redisHostname) {
        this.redisHostname = redisHostname;
    }

    public static void setInstance(ParamsConfig instance) {
        ParamsConfig.instance = instance;
    }
}
