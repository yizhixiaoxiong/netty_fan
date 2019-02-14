package nio;

import java.nio.IntBuffer;
import java.security.SecureRandom;

/**
 *
 * @auther: hanxiaofan
 * @date: 2019/2/9 3:55
 * @Description : java Nio 实例 可以用来引入Nio知识点
 */
public class NioTest1 {
    public static void main(String[] args) {
        //设置一个缓冲区，大小为10
        IntBuffer buffer = IntBuffer.allocate(10);

        for (int i = 0; i < buffer.capacity(); i++) {
            int randomNumber = new SecureRandom().nextInt(20);//生成一个随机数，比Random更随机
            //放入缓冲区
            buffer.put(randomNumber);
        }
        //反转。进行一个读写的切换
        buffer.flip();
        //如果之间不为空
        while (buffer.hasRemaining()) {
            //打印
            System.out.println(buffer.get());
        }
    }
}
