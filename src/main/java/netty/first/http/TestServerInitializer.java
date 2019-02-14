package netty.first.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * 初始化器材
 */
public class TestServerInitializer extends ChannelInitializer<SocketChannel> {
    //连接一旦被注册之后就会创建此方法

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        //一个管道
        ChannelPipeline pipeline = ch.pipeline();
        //向管道添加处理器，最终会走向我们自定义的处理器
        pipeline.addLast("httpServerCodec",new HttpServerCodec());
        pipeline.addLast("testHttpServerHandler",new TestHttpServerHandler());

    }
}
