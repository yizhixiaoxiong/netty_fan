package zerocopy;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Description :   IO服务端
 * @demand :        IO服务器,接收客户端的发送的数据字节数
 * @Auther : hanxiaofan
 * @Date : 2019/3/9 23:02
 * @Product_Name : netty_fan
 */
public class OldIOServer {
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(8899);
        InetAddress inetAddress = null;
        while (true) {
            Socket socket = serverSocket.accept();
            //获取数据流
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            long start = System.currentTimeMillis();
            int count = 0;
            inetAddress = socket.getInetAddress();
            try {
                byte[] bytes = new byte[4096];
                while (true) {
                    int readCount = dataInputStream.read(bytes,0,bytes.length);
                    count += readCount;
                    if (-1 == readCount) {
                        break;
                    }
                }
                System.out.println("客户端【"+inetAddress.getHostAddress()+"】传递字节数为："+count+"个,消耗时间为:"+(System.currentTimeMillis() - start));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

//    private static void oldServer(int port){
//
//    }
}
