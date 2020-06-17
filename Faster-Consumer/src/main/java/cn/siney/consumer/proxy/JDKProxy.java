package cn.siney.consumer.proxy;

import cn.siney.consumer.client.JedisTemplate;
import cn.siney.protocol.URL;
import com.alibaba.fastjson.JSON;

import java.lang.reflect.Proxy;

public class JDKProxy implements ProxyFactory {

    @Override
    public <T> T createProxy(Class<T> clazz) {
        if(!clazz.isInterface()){
            throw new RuntimeException("这不是一个接口");
        }

        JedisTemplate jedisTemplate = JedisTemplate.getInstance();
        String interfaceName = clazz.getName();
        String providerInfo = jedisTemplate.get(interfaceName);
        if(providerInfo != null){
            URL url = JSON.parseObject(providerInfo, URL.class);
            return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[]{clazz},
                    new JDKInvocationHandler(url));
        }
        throw new RuntimeException("不存在该服务");
    }
}
