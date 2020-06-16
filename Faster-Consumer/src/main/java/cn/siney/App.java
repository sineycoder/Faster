package cn.siney;

import cn.siney.client.FasterClient;
import cn.siney.proxy.JDKProxy;
import cn.siney.service.Service;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args ){
        Service proxy = new JDKProxy().createProxy(Service.class);
        String res = proxy.sayHello("nihao");
        System.out.println(res);
    }
}
