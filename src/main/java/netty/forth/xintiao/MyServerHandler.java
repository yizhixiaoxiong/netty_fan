package netty.forth.xintiao;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

public class MyServerHandler extends ChannelInboundHandlerAdapter {

    /**
     *  这个方法会触发ChannelPipeline绑定的下一个事件
     * @param ctx 上下文对象
     * @param evt 事件对象
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if(evt instanceof IdleStateEvent){  //如果事件对象属于空闲状态
            //转化成真正类型
            IdleStateEvent event = (IdleStateEvent) evt;
            String enentType = null;

            switch (event.state()){
                case READER_IDLE:
                    enentType = "读空闲";
                    break;
                case WRITER_IDLE:
            enentType = "写空闲";
            break;
            case ALL_IDLE:
                enentType = "读写都空闲";
                break;
        }
            System.out.println("【"+ctx.channel().remoteAddress()+"】超时事件:"+enentType);
        }
    }
}
