package cn.siney.provider.initializer;

import cn.siney.provider.decoder.FasterDecoder;
import cn.siney.provider.decoder.FasterHandler;
import cn.siney.provider.encoder.FasterEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

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
