package cn.siney.initializer;

import cn.siney.decoder.FasterDecoder;
import cn.siney.decoder.FasterHandler;
import cn.siney.encoder.FasterEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.internal.logging.InternalLogLevel;

public class FasterServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {

//        ch.pipeline().addLast("logging", new LoggingHandler(LogLevel.INFO));
        //Inbound
        ch.pipeline().addLast(new FasterDecoder());
        //outbound
        ch.pipeline().addLast(new FasterEncoder());
        ch.pipeline().addLast(new FasterHandler());

    }

}
