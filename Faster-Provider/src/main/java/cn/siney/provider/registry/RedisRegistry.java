package cn.siney.provider.registry;

import cn.siney.protocol.URL;
import cn.siney.provider.config.ParamsConfig;
import cn.siney.provider.server.JedisTemplate;
import com.alibaba.fastjson.JSON;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @Author siney
 * @Date 2020/6/17 13:30
 * @Version 1.0
 */
public class RedisRegistry {

    private static RedisRegistry instance = new RedisRegistry();

    private JedisTemplate template;

    private RedisRegistry(){
        template = JedisTemplate.getInstance();
    }

    public static RedisRegistry getInstance(){
        return instance;
    }

    public void regist(String interfaceName){
        try {
            String hostname = InetAddress.getLocalHost().getHostAddress();
            int port = ParamsConfig.getInstance().getServerPort();
            URL url = new URL(hostname, port);
            template.set(interfaceName, JSON.toJSONString(url));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

}
