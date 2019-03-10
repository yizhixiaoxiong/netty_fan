package zerocopy;

import java.io.*;
import java.net.Socket;

/**
 * @Description :   IO client
 * @demand :        统计发送给服务器的字节数
 * @Auther : hanxiaofan
 * @Date : 2019/3/9 23:03
 * @Product_Name : netty_fan
 */
public class OldIOClient {

    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("localhost", 8899);

        String fileName = "C:/Users/hanxiaofan/Desktop/algs4-data.zip";
        FileInputStream inputStream = new FileInputStream(fileName);

        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

        byte[] bytes = new byte[4096];
        long start = System.currentTimeMillis();
        int count = 0;
        int read ;

        while ((read = inputStream.read(bytes)) >= 0){
            count += read;
            dataOutputStream.write(bytes);//通过网络写入服务端

        }
        System.out.println("客户端写入的字节数为:"+count+"消耗时间为："+(System.currentTimeMillis() -start) + "ms");

        dataOutputStream.close();
        inputStream.close();
        socket.close();
    }
}
