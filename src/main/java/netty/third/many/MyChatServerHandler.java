package netty.third.many;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

public class MyChatServerHandler extends SimpleChannelInboundHandler<String> {

    //channelGroup用来保存一个个的channel对象。
    //所有与客户端建立连接的channel对象的Group(Group)
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    /**
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        //根据上下文获取当前发送的channel对象
        Channel channel = ctx.channel();

        channelGroup.forEach(ch ->{
            if(channel != ch){//如果当前发送的不是本用户
                ch.writeAndFlush("【"+ch.remoteAddress()+"】："+msg+"\n");
            }else{
                ch.writeAndFlush("【自己】："+msg+"\n");
            }
        } );
    }

    /**
     * handler增加的时候执行
     * @param ctx 上下文对象
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        //通过上下文获取Channel对象，也就是通过上下文获取通道
        //然后通过管道组来发布消息
        Channel channel = ctx.channel();
        //进行广播，将消息写出并清纯缓冲区
        channelGroup.writeAndFlush("【服务器】-"+channel.remoteAddress()+"加入\n");

        //将当前连接的客户端的channel放入channelGroup中
        channelGroup.add(channel);
    }

    /**
     * 连接断开，也就是客户端退出，原理加入
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        //通过上下文获取Channel对象，也就是通过上下文获取通道
        //然后通过管道来发布消息
        Channel channel = ctx.channel();

        //进行广播，将消息写出并清纯缓冲区
        channelGroup.writeAndFlush("【服务器】-"+channel.remoteAddress()+"断开\n");

        //移除channel。但是在Netty,会有一种检测机制，自动将channel移除掉。
        //channelGroup.remove(channel);
    }

    /**
     * 表示连接连接到服务的时候，会触发的方法
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        //服务器向客户端发送的消息
        System.out.println(channel.remoteAddress() + "上线了");
    }

    /**
     * 表示连接断开服务的时候，会触发的方法
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println(channel.remoteAddress() + "下线了");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
