package cn.siney.consumer.decoder;

import cn.siney.ObjectSerializerUtils;
import cn.siney.consumer.handler.FasterReceiveHandler;
import cn.siney.entity.Error;
import cn.siney.protocol.FasterInvocation;
import cn.siney.protocol.Protocol;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

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
        if(msg instanceof Error){
            Error error = (Error) msg;
            throw new RuntimeException(error.getMsg());
        }
//        System.out.println(Thread.currentThread().getName());
//        System.out.println(invokeThread.getName());
        System.out.println("收到服务器 " + msg);
        FasterReceiveHandler.getInstance().cas(msg);
        LockSupport.unpark(invokeThread);
        ctx.close();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent e = (IdleStateEvent) evt;
            if (e.state() == IdleState.READER_IDLE) {
                throw new RuntimeException("读超时");
            } else if (e.state() == IdleState.WRITER_IDLE) {
                throw new RuntimeException("写入超时");
            }
            else {
                throw new RuntimeException("超时");
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        close(ctx);
    }

    private void close(ChannelHandlerContext ctx){
        FasterReceiveHandler.getInstance().cas(null);
        LockSupport.unpark(invokeThread);
        ctx.close();
    }

}
