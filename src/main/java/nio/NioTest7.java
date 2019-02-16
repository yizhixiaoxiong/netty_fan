package nio;

import java.nio.ByteBuffer;

/**
 * @Description : 只读 Buffer
 * @Auther : hanxiaofan
 * @Date : 2019/2/16 22:11
 * @Product_Name : netty_fan
 */
public class NioTest7 {

    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(10);

        System.out.println(buffer.getClass());

        for (int i = 0 ; i < buffer.capacity() ; i++){
            buffer.put((byte)i);
        }

        ByteBuffer readOnlyBuffer = buffer.asReadOnlyBuffer();

        System.out.println(readOnlyBuffer.getClass());

    }
}
