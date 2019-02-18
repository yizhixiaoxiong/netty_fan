package nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

/**
 * @Description : MappedByteBuffer 内存映射文件
 *                在堆外内存中处理数据
 *
 * @Auther : hanxiaofan
 * @Date : 2019/2/18 23:23
 * @Product_Name : netty_fan
 */
public class NioTest9 {

    public static void main(String[] args) throws IOException {
        //执行了这段代码之后去文件夹中查看文件是否改变
        //结果是改变的
        operationsMapped("NioTest9.txt", "rw");

        mappedLock("NioTest9.txt", "rw");
    }

    /**
     *
     * Description: 内存映射文件，可在在堆外处理文件
     *
     * @param:  fileName    :   文件名
     *          mode        :   操作权限
     * @return:
     * @auther: hanxiaofan
     * @date: 2019/2/19 0:07
     */
    private static void operationsMapped(String fileName , String mode) throws IOException {
        //创建一个随机访问文件流
        RandomAccessFile file = new RandomAccessFile(fileName,mode);
        //获取通道
        FileChannel fileChannel = file.getChannel();
        //初始化内存映射文件，
        //参数分别是 : 可操作的权限; 起始位置; 映射文件长度。(将文件映射到内存当中)
        MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE,0, 5);

        //
        mappedByteBuffer.put(0, (byte) 'a');
        mappedByteBuffer.put(3, (byte) 'b');
        System.out.println("修改完毕");
        file.close();

    }

    private static void mappedLock(String fileName , String mode) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(fileName, mode);
        FileChannel fileChannel = randomAccessFile.getChannel();

        //获取文件锁
        //参数：起始位置; 锁多长; true 为共享锁 , false 为排他锁。
        FileLock fileLock = fileChannel.lock(0, 5, true);
        System.out.println("是否有效"+fileLock.isValid());
        //是否是共享锁，true是共享锁，false是排他锁
        System.out.println("锁的类型"+fileLock.isShared());

        fileLock.close();
        randomAccessFile.close();

    }
}
