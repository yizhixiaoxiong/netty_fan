package nioserver;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * @Description :   Nio 服务器
 * 开放一个端口  模拟3个客户端,当一个客户端发送消息的时候
 * ,服务器会将消息分发给已经登陆的客户端
 * @Auther : hanxiaofan
 * @Date : 2019/3/4 21:52
 * @Product_Name : netty_fan
 */
public class NioServer {

    //此map是记录客户端信息; key : 每个channel的唯一标识符  value : key对应的Channel
    private static Map<String, SocketChannel> clientMap = new HashMap();

    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        ServerSocket serverSocket = serverSocketChannel.socket();
        InetSocketAddress address = new InetSocketAddress(8899);
        serverSocket.bind(address);

        Selector selector = Selector.open();

        //将channle对象注册到Selector上
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            //这个方法会一直阻塞,直到有一个channel进来
            selector.select();

            Set<SelectionKey> selectionKeys = selector.selectedKeys();

            selectionKeys.forEach(selectionKey -> {
                final SocketChannel client;
                try {
                    if (selectionKey.isAcceptable()) {   //如果有连接
                        //获取这个key的Channle
                        //此时获取的Channel为上面注册的Channel,上面注册的为ServerSocketChannel，所以强转。
                        ServerSocketChannel server = (ServerSocketChannel) selectionKey.channel();
                        client = server.accept();
                        client.configureBlocking(false);    //非阻塞

                        //客户端的Channel注册到Selector  关注事件 OP_READ
                        client.register(selector, SelectionKey.OP_READ);

                        String key = "【" + UUID.randomUUID().toString() + "】";
                        //将client的信息放入map中
                        clientMap.put(key, client);

                    } else if (selectionKey.isReadable()) { //倘若客户端带数据,可读
                        //将数据分发给各个客户端
                        client = (SocketChannel) selectionKey.channel();
                        ByteBuffer readBuffers = ByteBuffer.allocate(2048);
                        int read = client.read(readBuffers);
                        if (read > 0) {
                            readBuffers.flip();
                            //设置编码字符集
                            Charset charset = Charset.forName("utf-8");
                            //获取数据
                            String readMessage = String.valueOf(charset.decode(readBuffers).array());
                            System.out.println("客户端 : 【" + client + "】" + readMessage);

                            //功能
                            //服务端保存客户端发送的消息，然后分发给客户端
                            //获取唯一标识符
                            String sendKey = null;
                            for (Map.Entry<String, SocketChannel> entry : clientMap.entrySet()) {
                                if (client == entry.getValue()) {
                                    sendKey = entry.getKey();
                                    break;
                                }
                            }
                            //将数据打包写入服务器
                            ByteBuffer writeBytes = ByteBuffer.allocate(1024);
                            for (Map.Entry<String, SocketChannel> entry : clientMap.entrySet()) {
                                writeBytes.clear();
                                //获取所有客户端的Channel对象
                                SocketChannel value = entry.getValue();
                                writeBytes.put((sendKey + " : " + readMessage).getBytes());
                                //翻转,如果不翻转则会在write中跟着put的末尾位置继续写
                                writeBytes.flip();
                                while (writeBytes.hasRemaining()) {
                                    value.write(writeBytes);
                                }
                            }
                            String a = new String(writeBytes.array(), 0,
                                    writeBytes.array().length);
                            System.out.println(a+"一次");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            selectionKeys.clear();
        }
    }
}
