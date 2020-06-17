package cn.siney;

import cn.siney.consumer.proxy.JDKProxy;
import cn.siney.service.ServiceThree;
import cn.siney.service.ServiceTwo;

/**
 * @Author siney
 * @Date 2020/6/17 14:37
 * @Version 1.0
 */
public class Client {
    public static void main(String[] args) {
        JDKProxy jdkProxy = new JDKProxy();
        ServiceThree proxy = jdkProxy.createProxy(ServiceThree.class);
        String res = proxy.order(5);
        System.out.println(res);
    }
}
