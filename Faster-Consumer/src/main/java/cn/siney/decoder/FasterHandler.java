package cn.siney.decoder;

import cn.siney.ObjectSerializerUtils;
import cn.siney.handler.FasterReceiveHandler;
import cn.siney.protocol.FasterInvocation;
import cn.siney.protocol.Protocol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.util.concurrent.locks.LockSupport;

public class FasterHandler extends SimpleChannelInboundHandler<Object> {

    private FasterInvocation invocation;
    private Thread invokeThread;

    public FasterHandler(FasterInvocation invocation){
        this.invocation = invocation;
        invokeThread = Thread.currentThread();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        if(invocation != null){
            System.out.println(invocation);
            byte[] bytes = ObjectSerializerUtils.serialize(invocation);
            Protocol protocol = new Protocol(bytes.length, bytes);
            ctx.writeAndFlush(protocol);
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println(Thread.currentThread().getName());
        System.out.println(invokeThread.getName());
        System.out.println("收到服务器 " + msg);
        FasterReceiveHandler.getInstance().cas(msg);
        LockSupport.unpark(invokeThread);
        ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
