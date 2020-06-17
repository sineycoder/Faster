package cn.siney;

import cn.siney.consumer.proxy.JDKProxy;
import cn.siney.service.ServiceTwo;

/**
 * @Author siney
 * @Date 2020/6/17 14:37
 * @Version 1.0
 */
public class Client {
    public static void main(String[] args) {
        JDKProxy jdkProxy = new JDKProxy();
        ServiceTwo proxy = jdkProxy.createProxy(ServiceTwo.class);
        String res = proxy.sayHi("kakak");
        System.out.println(res);
    }
}
