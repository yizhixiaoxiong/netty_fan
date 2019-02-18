package nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Description : 使用DirectByteBuffer 处理文件
 * @Auther : hanxiaofan
 * @Date : 2019/2/17 22:58
 * @Product_Name : netty_fan
 */
public class NioTest8 {
    public static void main(String[] args) throws IOException {
        operationNioDirect("NioTest2.txt", "NioTest3.txt", 512);

    }

    private static void operationNioDirect(String inputFile , String outputFile , int len) throws IOException {
        FileInputStream inputStream = new FileInputStream(inputFile);
        FileOutputStream outputStream = new FileOutputStream(outputFile);

        FileChannel inputChannel = inputStream.getChannel();
        FileChannel outputChannel = outputStream.getChannel();

        ByteBuffer buffer = ByteBuffer.allocateDirect(len);

        while (true){
            buffer.clear();

            int read = inputChannel.read(buffer);

            if (-1 == read){
                break;
            }

            buffer.flip();

            outputChannel.write(buffer);
        }

        inputStream.close();
        outputChannel.close();
    }
}
