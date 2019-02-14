package netty.first.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 第一个netty项目
 * 执行流程：
 *  先进入TestServerInitializer初始化器，在初始化器中设置了自定义的服务处理器，在处理器中编写响应和处理逻辑
 */
public class TestServer {
    public static void main(String[] args) throws Exception{
        //NioEventLoopGroup 相当于一个死循环

        //bossGroup 相当于接收连接
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        //workerGroup 相当于处理连接
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            //启动服务端
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            //使用方法链的方式
            serverBootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new TestServerInitializer());
            //绑定端口
            ChannelFuture channelFuture = serverBootstrap.bind(8899).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }
}
