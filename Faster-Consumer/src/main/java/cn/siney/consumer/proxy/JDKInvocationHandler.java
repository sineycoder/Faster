package cn.siney.consumer.proxy;

import cn.siney.consumer.client.FasterClient;
import cn.siney.consumer.handler.FasterReceiveHandler;
import cn.siney.protocol.FasterInvocation;
import cn.siney.protocol.URL;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class JDKInvocationHandler implements InvocationHandler {

    private URL url;

    public JDKInvocationHandler(URL url) {
        this.url = url;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String interfaceName = proxy.getClass().getInterfaces()[0].getName();
        FasterInvocation invocation = new FasterInvocation(interfaceName, method.getName(),
                method.getParameterTypes().length == 0?null:method.getParameterTypes(), args);//通过生成的invocation然后根据客户端发送给服务端

        new FasterClient(url.getIp(), url.getPort())
                .send(invocation);

        Object res = FasterReceiveHandler.getInstance().get();//会进行阻塞

        return res;

    }

}
