package zerocopy;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @Description :   NIO服务器
 * @demand :        NIO服务器,接收客户端的发送的数据字节数
 * @Auther : hanxiaofan
 * @Date : 2019/3/9 23:03
 * @Product_Name : netty_fan
 */
public class NewIOServer {
    public static void main(String[] args)throws Exception{
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        ServerSocket serverSocket = serverSocketChannel.socket();
        serverSocket.setReuseAddress(true);
        serverSocket.bind(new InetSocketAddress(8899));

        ByteBuffer byteBuffers = ByteBuffer.allocate(4096);

        while (true){
            SocketChannel socketChannel = serverSocketChannel.accept();
            serverSocketChannel.configureBlocking(true);
            int read = 0;
            while (-1 != read){
                read = socketChannel.read(byteBuffers);

                byteBuffers.rewind();
            }
        }
    }
}

