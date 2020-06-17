package cn.siney.provider.decoder;

import cn.siney.ObjectSerializerUtils;
import cn.siney.entity.Error;
import cn.siney.protocol.FasterInvocation;
import cn.siney.protocol.Protocol;
import cn.siney.provider.server.FasterServer;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class FasterHandler extends SimpleChannelInboundHandler<Object> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("服务端接收channel");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object m) throws InterruptedException {
        System.out.println("server received " + m);
        Protocol protocol = null;
        Error error = null;
        try {
            FasterInvocation msg = (FasterInvocation) m;
            ApplicationContext context = FasterServer.getContext();
            Object instance = context.getBean(msg.getInterfaceName());
            Class<?> clazz = instance.getClass();
            Method method = clazz.getMethod(msg.getMethodName(), msg.getParamTypes());
            Object result = method.invoke(instance, msg.getParams());
            System.out.println(result);
            if(result == null)result = "null";//如果返回结果为null，返回字符串null
            byte[] serialize = ObjectSerializerUtils.serialize(result);
            protocol = new Protocol(serialize.length, serialize);
        }catch (NoSuchMethodException e) {
            error = new Error("没有这个方法 " + e.getMessage());
        } catch (IllegalAccessException e) {
            error = new Error("不合法访问 " + e.getMessage());
        } catch (InvocationTargetException e) {
            error = new Error("执行函数失败，可能参数不正确 " + e.getMessage());
        } catch (Exception e){
            error = new Error(e.getMessage());
        }finally {
            if(error != null){
                byte[] serialize = ObjectSerializerUtils.serialize(error);
                protocol = new Protocol(serialize.length, serialize);
            }
            ctx.writeAndFlush(protocol);
        }
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("断开了");
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
