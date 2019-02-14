package netty.second.socket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.UUID;

public class MyServerHandler extends SimpleChannelInboundHandler<String> {

    /**
     * 重写方法,表示服务端收到了客户端消息
     *
     * @param ctx 上下文对象
     * @param msg 所接受到的客户端发送过来的
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        //远程客户端地址和端口号打印出来
        System.out.println(ctx.channel().remoteAddress() + ","+msg);
        //服务端写出数据并将清除缓冲区
        ctx.channel().writeAndFlush("from server:"+ UUID.randomUUID());
    }

    /**
     * 出现异常的操作
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //打印异常信息
        cause.printStackTrace();
        //关闭连接
        ctx.close();
    }
}
