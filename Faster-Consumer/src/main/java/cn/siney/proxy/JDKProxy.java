package cn.siney.proxy;

import java.lang.reflect.Proxy;

public class JDKProxy implements ProxyFactory {

    @Override
    public <T> T createProxy(Class<T> clazz) {
        if(!clazz.isInterface()){
            throw new RuntimeException("这不是一个接口");
        }
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[]{clazz},
                new JDKInvocationHandler());
    }

}
