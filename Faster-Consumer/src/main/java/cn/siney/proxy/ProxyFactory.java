package cn.siney.proxy;

public interface ProxyFactory {

    <T> T createProxy(Class<T> clazz);

}
