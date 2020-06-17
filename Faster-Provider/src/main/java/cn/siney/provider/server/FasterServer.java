package cn.siney.provider.server;

import cn.siney.provider.config.ParamsConfig;
import cn.siney.provider.initializer.FasterServerInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class FasterServer implements ApplicationContextAware {

    private int port;
    private ServerBootstrap server;
    private static ApplicationContext applicationContext;

    public FasterServer(){
        ParamsConfig paramsConfig = ParamsConfig.getInstance();
        port = paramsConfig.getServerPort();
        new Thread(this::start).start();
    }

    private void start(){
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();
        try {
            server = new ServerBootstrap()
                    .group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)//设置线程队列等待连接的个数
                    .childOption(ChannelOption.SO_KEEPALIVE, true)//设置保持活动连接状态
                    .childHandler(new FasterServerInitializer());

            ChannelFuture fc = server.bind(port).sync();

            fc.channel().closeFuture().sync();
        }catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }

    public static ApplicationContext getContext(){
        return applicationContext;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        FasterServer.applicationContext = applicationContext;
    }
}
