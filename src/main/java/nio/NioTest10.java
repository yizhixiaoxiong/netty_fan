package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * @Description : Nio 中处理buffer数组
 * @Auther : hanxiaofan
 * @Date : 2019/2/19 0:44
 * @Product_Name : netty_fan
 */
public class NioTest10 {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress socketAddress = new InetSocketAddress(8899);
        serverSocketChannel.socket().bind(socketAddress);

        int messageLength = 2 + 3 + 4;

        ByteBuffer[] buffers = new ByteBuffer[3];

        buffers[0] = ByteBuffer.allocate(2);
        buffers[1] = ByteBuffer.allocate(3);
        buffers[2] = ByteBuffer.allocate(4);


        //监听
        SocketChannel socketChannel = serverSocketChannel.accept();


        while (true) {
            int bytesRead = 0;
            while (bytesRead < messageLength) {
                long read = socketChannel.read(buffers);

                bytesRead += read;

                System.out.println("读取字节 byteRead : " + bytesRead);

                Arrays.asList(buffers).stream().
                        map(buffer -> "position   " + buffer.position() + "  limit " + buffer.limit()).
                        forEach(System.out::println);

            }

            //翻转
            Arrays.asList(buffers).forEach(buffer -> buffer.flip());

            //开始读取

            int bytesWrite = 0;
            while (bytesWrite < messageLength){
                long write = socketChannel.write(buffers);
                bytesWrite += write;
            }

            Arrays.asList(buffers).forEach(buffer -> buffer.clear());

            System.out.println("bytesRead : " + bytesRead + "  bytesWrite : " +bytesWrite + " " +
                    "  messageLength " + messageLength );
        }

    }
}
