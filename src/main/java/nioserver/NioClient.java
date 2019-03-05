package nioserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Description :   Nio Client
 * @Auther : hanxiaofan
 * @Date : 2019/3/5 0:29
 * @Product_Name : netty_fan
 */
public class NioClient {

    public static void main(String[] args) throws IOException {

        try {
            SocketChannel socketChannel = SocketChannel.open();
            //非阻塞
            socketChannel.configureBlocking(false);
            Selector selector = Selector.open();
            //绑定到select并监听 OP_CONNECT事件
            socketChannel.register(selector, SelectionKey.OP_CONNECT);
            socketChannel.connect(new InetSocketAddress("127.0.0.1", 8899));

            while (true) {
                selector.select();
                Set<SelectionKey> selectionKeys = selector.selectedKeys();

                for (SelectionKey key : selectionKeys) {
                    if (key.isConnectable()) {   //连接
                        SocketChannel client = (SocketChannel) key.channel();
                        if (client.isConnectionPending()) {
                            //连接建立完成
                            client.finishConnect();
                            //发送消息
                            ByteBuffer writeBytes = ByteBuffer.allocate(1024);
                            writeBytes.put((LocalDateTime.now() + " :  连接成功!").getBytes());
                            writeBytes.flip();
                            //向服务器发送消息
                            client.write(writeBytes);

                            //单独开启一个线程来监听键盘
                            ExecutorService executorService = Executors.newSingleThreadExecutor(Executors.defaultThreadFactory());
                            executorService.submit(() -> {
                                try {
                                    while (true) {
                                        writeBytes.clear();
                                        InputStreamReader input = new InputStreamReader(System.in);
                                        BufferedReader reader = new BufferedReader(input);
                                        String sendMessage = reader.readLine();
                                        writeBytes.put(sendMessage.getBytes());
                                        writeBytes.flip();
                                        client.write(writeBytes);
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            });
                        }
                        //注册读取事件

                        client.register(selector, SelectionKey.OP_READ);
                    } else if (key.isReadable()) {
                        try {
                            SocketChannel client = (SocketChannel) key.channel();
                            ByteBuffer readBuffers = ByteBuffer.allocate(1024);
                            while (true){
                                int read = client.read(readBuffers);
                                if (read <= 0){
                                    break;
                                }
                                String readMessage = new String(readBuffers.array(), 0, read);
                                System.out.println(readMessage);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                selectionKeys.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
