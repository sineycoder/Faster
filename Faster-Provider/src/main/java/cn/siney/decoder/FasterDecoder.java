package cn.siney.decoder;


import cn.siney.ObjectSerializerUtils;
import cn.siney.protocol.FasterInvocation;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class FasterDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("收到");
        if(in.readableBytes() >= 4){
            int len = in.readInt();//读取字节长度
            if(in.readableBytes() >= len){
                byte[] bytes = new byte[len];
                in.readBytes(bytes);
                out.add(ObjectSerializerUtils.unSerialize(bytes));
            }
        }

    }
}
