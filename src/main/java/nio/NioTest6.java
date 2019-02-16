package nio;

import java.nio.ByteBuffer;

/**
 * @Description :
 * @Auther : hanxiaofan
 * @Date : 2019/2/16 21:49
 * @Product_Name : netty_fan
 */
public class NioTest6 {
    public static void main(String[] args) {
        //创建一个buffer
        ByteBuffer buffer = ByteBuffer.allocate(10);

        //放入元素
        for (int i = 0; i < buffer.capacity(); ++i) {
            buffer.put((byte) i);
        }

        buffer.position(2);
        buffer.limit(6);

        //分片buffer
        ByteBuffer sliceBuffer = buffer.slice();

        //操作sliceBuffer的数据也是操作buffer的数据
        for (int i = 0; i < sliceBuffer.capacity(); ++i) {
            byte b = sliceBuffer.get(i);
            b *= 2;
            sliceBuffer.put(i, b);
        }

        //将buffer重新截取
        buffer.position(0);
        buffer.limit(buffer.capacity());

        //获取全部数据
        while (buffer.hasRemaining()){
            System.out.println(buffer.get());
        }

    }
}
