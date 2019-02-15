package nio;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Auther : hanxiaofan
 * @Date : 2019/2/15 20:07
 * @Product_Name : netty_fan
 *
 * Description : 从一个文件读取文件内容到另一个文件
 */
public class NioTest4 {
    public static void main(String[] args) throws IOException {
//        operationIO("input.txt", "output.txt",512);
//
//        operationNIO("input.txt", "output.txt", 4);

        operationNIO("input.txt", "output.txt", 512);

    }




    /**
     *
     * Description: IO操作
     *
     * @param:  inputFile   : 源文件
     *          outputFile  : 输出文件
     *          len         : 字节长度
     * @return:
     * @auther: hanxiaofan
     * @date: 2019/2/15 20:28
     */
    private static void operationIO(String inputFile , String outputFile , int len) throws IOException {
        System.out.println("正在使用IO操作文件");

        FileInputStream inputStream = new FileInputStream(inputFile);
        FileOutputStream outputStream = new FileOutputStream(outputFile);

        byte[] bs = new byte[len];


        while (true){
            int read = inputStream.read(bs);

            System.out.println("read:" + read);

            if (-1 == read){
                break;
            }

            outputStream.write(read);

        }

        System.out.println("正在关闭流------");

        inputStream.close();
        outputStream.close();

        System.out.println("关闭完毕，完成操作");

    }

    /**
     *
     * Description: NIO操作
     *
     * @param:  inputFile   : 源文件
     *          outputFile  : 输出文件
     *          len         : buffer 容量
     * @return: 
     * @auther: hanxiaofan
     * @date: 2019/2/15 20:26
     */
    private static void operationNIO(String inputFile , String outputFile,int len) throws IOException {

        System.out.println("正在使用NIO 操作文件");

        FileInputStream inputStream = new FileInputStream(inputFile);
        FileOutputStream outputStream = new FileOutputStream(outputFile);

        FileChannel inputChannel = inputStream.getChannel();
        FileChannel outputChannel = outputStream.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(len);

        while (true){
            //如果删除当前行，则无限制的输出，原因是如果去除当前行，position和limit相等，
            //则会让下一行read方法返回0。
            buffer.clear();

            int read = inputChannel.read(buffer);

            System.out.println("read:" + read);

            if (-1 == read){
                break;
            }

            buffer.flip();
            outputChannel.write(buffer);

        }

        System.out.println("正在关闭流------");

        inputStream.close();
        outputStream.close();

        System.out.println("关闭完毕，完成操作");
    }

}
