package netty.five.websocket;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * WebSocket初始化器
 */
public class WebSocketChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        //基于http的hander
        pipeline.addLast(new HttpServerCodec());
        pipeline.addLast(new ChunkedWriteHandler());
        //对httpMessage进行聚合。基于netty的http的请求很常用
        pipeline.addLast(new HttpObjectAggregator(8192));

        //专门用于websocket的处理器，参数代表uri地址。
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));

        //自定义处理器
        pipeline.addLast(new TextWebSocketFrameHandler());
    }
}
