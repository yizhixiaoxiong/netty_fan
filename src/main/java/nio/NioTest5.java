package nio;

import java.nio.ByteBuffer;

/**
 * @Description : ByteBuffer 类型化的put和get方法
 * @Auther : hanxiaofan
 * @Date : 2019/2/16 21:27
 * @Product_Name : netty_fan
 */
public class NioTest5 {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(64);

        buffer.putInt(10);
        buffer.putLong(50000L);
        buffer.putChar('你');
        buffer.putDouble(3.14d);
        buffer.putShort((short) 1);

        buffer.flip();

        //如果类型不对应，则会在运行时出现异常
        System.out.println(buffer.getInt());
        System.out.println(buffer.getLong());
        System.out.println(buffer.getChar());
        System.out.println(buffer.getDouble());
        System.out.println(buffer.getShort());


    }
}
