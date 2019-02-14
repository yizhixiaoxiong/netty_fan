package netty.forth.xintiao;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

public class MyServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        //IdleStateHandler空闲检测
        // 心跳检查客户端在规定时间内是否进行了read，write。如果没有进行操作，则触发
        pipeline.addLast(new IdleStateHandler(5,7,10,TimeUnit.SECONDS));
        pipeline.addLast(new MyServerHandler());
    }
}
