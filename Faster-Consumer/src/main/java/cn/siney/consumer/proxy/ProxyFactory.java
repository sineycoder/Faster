package cn.siney.consumer.proxy;

public interface ProxyFactory {

    <T> T createProxy(Class<T> clazz);

}
