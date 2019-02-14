package nio;

import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Auther : hanxiaofan
 * @Date : 2019/2/9 14:41
 * @Product_Name : netty_fan
 */
public class TestNio {
    public static void main(String[] args) throws Exception{
        //一个输入流
        FileInputStream fileInputStream = new FileInputStream("NioTest2.txt");
        //通过输入流获取FileChannel对象
        FileChannel fileChannel = fileInputStream.getChannel();
        //创建一个buffer对象。用于读写
        ByteBuffer byteBuffer = ByteBuffer.allocate(512);
        //读取到byteBuffer 中
        fileChannel.read(byteBuffer);
        //翻转，切换读写
        byteBuffer.flip();

        while (byteBuffer.hasRemaining()){
            byte b = byteBuffer.get();
            System.out.print((char)b);
        }

        fileChannel.close();
        fileInputStream.close();;
    }
}
