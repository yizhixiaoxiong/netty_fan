package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * @Description : Selector 分析与实例
 * @Auther : hanxiaofan
 * @Date : 2019/2/25 21:57
 * @Product_Name : netty_fan
 */
public class NioTest12 {

    public static void main(String[] args) throws IOException {
        int[] ports = new int[5];

        ports[0] = 5000;
        ports[1] = 5001;
        ports[2] = 5002;
        ports[3] = 5003;
        ports[4] = 5004;

        //open a selector
        Selector selector = Selector.open();

        System.out.println(selector.getClass());


        for (int i = 0; i < ports.length; i++) {

            //打开通道
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            //System.out.println("ServerSocketChannel类型  ：  " + serverSocketChannel.getClass());

            //更改模式
            //true ： blocking mode  ; false : non-blocking mode
            serverSocketChannel.configureBlocking(false);
            //套接字地址
            InetSocketAddress address = new InetSocketAddress(ports[i]);

            ServerSocket serverSocket = serverSocketChannel.socket();
            //绑定
            serverSocket.bind(address);

            //将选择器注册到通道上，第二个参数必须是OP_ACCEPT.
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            System.out.println("准备工作完成！监听端口：" + ports[i]);

        }

        //连接
        while (true) {
            //返回值不为0,则说明有连接进入
            int selectNum = selector.select();
            System.out.println("键的数量：" + selectNum);
            //获取选择器selector键的集合
            Set<SelectionKey> selectionKeys = selector.selectedKeys();

            System.out.println("键的集合" + selectionKeys);

            Iterator<SelectionKey> iter = selectionKeys.iterator();

            while (iter.hasNext()) {
                //获取迭代器中的元素
                SelectionKey selectionKey = iter.next();

                if (selectionKey.isAcceptable()) {
                    //获取键中的通道
                    ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();

                    //接受与此频道套接字的连接
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);

                    //将选择器注册到连接上
                    socketChannel.register(selector, SelectionKey.OP_READ);

                    iter.remove();

                    System.out.println("监听详情 ：" + socketChannel);
                } else if (selectionKey.isReadable()) {   //可读的
                    //获取选择器上的通道
                    SocketChannel socketChannel = (SocketChannel) selectionKey.channel();

                    int readBytes = 0;

                    ByteBuffer byteBuffer = ByteBuffer.allocate(512);

                    while (true) {
                        byteBuffer.clear();
                        int read = socketChannel.read(byteBuffer);
                        readBytes += read;

                        if (read <= 0) {
                            break;
                        }

                        byteBuffer.flip();

                        socketChannel.write(byteBuffer);
                    }

                    iter.remove();

                    System.out.println("读取字节数：" + readBytes + ", 来自于 ：" + socketChannel);
                }

            }
        }

    }
}
