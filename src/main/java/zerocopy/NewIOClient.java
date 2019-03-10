package zerocopy;

import java.io.FileInputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

/**
 * @Description :   NIO客户端
 * @demand :        统计发送给服务器的字节数
 * @Auther : hanxiaofan
 * @Date : 2019/3/9 23:03
 * @Product_Name : netty_fan
 */
public class NewIOClient {
    public static void main(String[] args) throws Exception{
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("127.0.0.1", 8899));
        socketChannel.configureBlocking(true);

        String fileName = "C:/Users/hanxiaofan/Desktop/algs4-data.zip";
        FileChannel fileChannel = new FileInputStream(fileName).getChannel();

        long start = System.currentTimeMillis();
        fileChannel.transferTo(0, fileChannel.size(),socketChannel);

        System.out.println("发送字节数为："+fileChannel.size()+"消耗时间为："+(System.currentTimeMillis() - start)+
                "ms");

        fileChannel.close();
        socketChannel.close();
    }
}
