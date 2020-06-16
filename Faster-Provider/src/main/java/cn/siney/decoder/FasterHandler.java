package cn.siney.decoder;

import cn.siney.ObjectSerializerUtils;
import cn.siney.local.LocalRegistry;
import cn.siney.protocol.FasterInvocation;
import cn.siney.protocol.Protocol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.lang.reflect.Method;

public class FasterHandler extends SimpleChannelInboundHandler<FasterInvocation> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("服务端接收channel");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FasterInvocation msg) throws Exception {
        System.out.println("server received " + msg);
        LocalRegistry registry = LocalRegistry.getInstance();
        Object instance;
        Class<?> clazz;
        if((clazz = registry.getInterface(msg.getInterfaceName())) != null){//本地接口实现类的缓存
            if((instance = registry.getSingleton(msg.getInterfaceName())) == null){//接口实现类对象缓存
                synchronized (LocalRegistry.class){
                    if((instance = registry.getSingleton(msg.getInterfaceName())) == null){
                        instance = clazz.newInstance();
                        registry.putSingleObject(msg.getInterfaceName(), instance);
                    }
                }
            }else{
                System.out.println("获取缓存对象");
            }
            Method method = clazz.getMethod(msg.getMethodName(), msg.getParamTypes());
            Object result = method.invoke(instance, msg.getParams());
            System.out.println(result);
            byte[] serialize = ObjectSerializerUtils.serialize(result);
            Protocol protocol = new Protocol(serialize.length, serialize);
            ctx.writeAndFlush(protocol);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
