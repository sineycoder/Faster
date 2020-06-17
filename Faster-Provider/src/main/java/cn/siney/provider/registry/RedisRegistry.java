package cn.siney.provider.registry;

import cn.siney.protocol.URL;
import cn.siney.provider.config.ParamsConfig;
import cn.siney.provider.server.JedisTemplate;
import com.alibaba.fastjson.JSON;
import org.springframework.util.ObjectUtils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Set;

/**
 * @Author siney
 * @Date 2020/6/17 13:30
 * @Version 1.0
 */
public class RedisRegistry {

    private static RedisRegistry instance = new RedisRegistry();
    private Set<String> keys;

    private JedisTemplate template;

    private RedisRegistry(){
        template = JedisTemplate.getInstance();
        keys = template.keys(ParamsConfig.REGISTRY + "*");
    }

    public static RedisRegistry getInstance(){
        return instance;
    }

    public void regist(String interfaceName){
        try {
            String hostname = InetAddress.getLocalHost().getHostAddress();
            int port = ParamsConfig.getInstance().getServerPort();
            URL url = new URL(hostname, port);
            String fullName = ParamsConfig.REGISTRY + interfaceName;
            if(!ObjectUtils.isEmpty(keys) && keys.contains(fullName)){
                keys.remove(fullName);
            }else{
                template.set(interfaceName, JSON.toJSONString(url));
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public void afterRegist(){
        if(!ObjectUtils.isEmpty(keys)){
            template.del(keys.toArray(new String[]{}));
        }
    }

}
