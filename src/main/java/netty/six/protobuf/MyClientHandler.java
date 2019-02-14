package netty.six.protobuf;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Random;

public class MyClientHandler extends SimpleChannelInboundHandler<MyDataInfo.MyMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyDataInfo.MyMessage msg) throws Exception {

    }

    /**
     * 连接建立之后调用的方法
     * 建立连接之后发送一个消息
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        int randomInt = new Random().nextInt(3);
        MyDataInfo.MyMessage myMessage = null;

        if(randomInt == 0){
            myMessage = MyDataInfo.MyMessage.newBuilder()
                    .setDataType(MyDataInfo.MyMessage.DataType.PersonType)      //设置Type
                    .setPerson(MyDataInfo.Person.newBuilder().setName("张三")
                            .setAge(20).setAddress("北京").build()).build();
        }else if(randomInt == 1){
            myMessage = MyDataInfo.MyMessage.newBuilder()
                    .setDataType(MyDataInfo.MyMessage.DataType.DogType)
                    .setDog(MyDataInfo.Dog.newBuilder().setName("狗")
                            .setAge(20).build()).build();
        }else{
            myMessage = MyDataInfo.MyMessage.newBuilder()
                    .setDataType(MyDataInfo.MyMessage.DataType.CarType)
                    .setCat(MyDataInfo.Cat.newBuilder().setName("猫")
                    .setAge(20).build()).build();
        }

        ctx.channel().writeAndFlush(myMessage);
    }














}
