package cn.siney.client;

import cn.siney.decoder.FasterDecoder;
import cn.siney.decoder.FasterHandler;
import cn.siney.encoder.FasterEncoder;
import cn.siney.protocol.FasterInvocation;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class FasterClient {

    private String hostname;
    private int port;
    private Bootstrap bootstrap;

    public FasterClient(String hostname, int port){
        this.hostname = hostname;
        this.port = port;
    }

    public void send(FasterInvocation invocation){
        NioEventLoopGroup group = new NioEventLoopGroup();
        try {
            FasterHandler handler = new FasterHandler(invocation);
            bootstrap = new Bootstrap()
                    .group(group)
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            //ch.pipeline().addLast("logging", new LoggingHandler(LogLevel.INFO));
                            //outbound
                            ch.pipeline().addLast(new FasterEncoder());//顺序不要反了

                            //inbound
                            ch.pipeline().addLast(new FasterDecoder());
                            ch.pipeline().addLast(handler);
                        }
                    });

            ChannelFuture cf = bootstrap.connect(hostname, port).sync();
            cf.channel().closeFuture().sync();
        }catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            group.shutdownGracefully();
        }
    }

}
