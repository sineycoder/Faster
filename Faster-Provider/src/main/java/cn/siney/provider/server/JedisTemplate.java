package cn.siney.provider.server;

import cn.siney.provider.config.ParamsConfig;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Set;

public class JedisTemplate {

    private int port;
    private String hostname;
    private JedisPool jedisPool;
    private Set<String> keys;

    private static JedisTemplate instance = new JedisTemplate();

    public static JedisTemplate getInstance(){
        return instance;
    }

    private JedisTemplate(){
        ParamsConfig paramsConfig = ParamsConfig.getInstance();
        hostname = paramsConfig.getRedisHostname();
        port = paramsConfig.getRedisPort();
        createJedisPool();
    }

    private void createJedisPool() {
        JedisPoolConfig conf = new JedisPoolConfig();
        conf.setMaxTotal(1024);
        conf.setMaxIdle(100);
        conf.setMaxWaitMillis(100);
        conf.setTestOnBorrow(false);//jedis 第一次启动时，会报错
        conf.setTestOnReturn(true);
        jedisPool = new JedisPool(conf, hostname, port, 5000);
    }

    public Set<String> keys(String pattern) {
        return jedisPool.getResource().keys(pattern);
    }

    public void del(String... key){
        jedisPool.getResource().del(key);
    }

    public void set(String key, String value){
        jedisPool.getResource().set(ParamsConfig.REGISTRY + key, value);
    }

    public String get(String key){
        return jedisPool.getResource().get(ParamsConfig.REGISTRY + key);
    }

}
