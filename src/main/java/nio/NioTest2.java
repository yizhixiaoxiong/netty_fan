package nio;

import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 *
 * @auther: hanxiaofan
 * @date: 2019/2/9 3:53
 * @Description :
 */
public class NioTest2 {
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

        //如果字节还有
        //判断是否有字节，另一个方法是hasRemaining()
        while (byteBuffer.remaining() > 0){
            byte b = byteBuffer.get();
            System.out.print((char)b);
        }
        fileInputStream.close();

    }
}
