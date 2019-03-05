package nioserver;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

/**
 * @Description :   编码与解码测试
 * @demand :        使用内存映射文件的方式来
 *                  复制文件内容，源文件中包含 英文，数字和中文。
 * @Auther : hanxiaofan
 * @Date : 2019/3/5 18:46
 * @Product_Name : netty_fan
 *
 * Question:(1) 传入的编码方式是"IOS-8859-1"的时候,源文件的中文也会被copy的复制文件中，这是为什么？
 *          答：ISO-8859-1编码格式是单字节,不会丢失字节。虽然在解码的时候会出现乱码,但是在重新编码之后字节数量不变,
 *              而生成的文件也是UTF-8的格式,字节组合和源文件是一样的编码格式,所以还是会出现中文。
 *          (2)
 */
public class EncodingTest {
    public static void main(String[] args) {
        try {
            copyFile("decodeFile.txt", "encodeFile.txt", "iso-8859-1");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Description: 按照指定的编码格式复制文件
     *
     * @param:
     * @return:
     * @auther: hanxiaofan
     * @date: 2019/3/5 19:22
     */
    private static void copyFile(String sourceFile, String copyFile, String charsetName) throws Exception {
        //只读源文件
        RandomAccessFile inputFile = new RandomAccessFile(sourceFile, "r");
        RandomAccessFile outputFile = new RandomAccessFile(copyFile, "rw");

        FileChannel input = inputFile.getChannel();
        FileChannel output = outputFile.getChannel();

        long length = new File(sourceFile).length();
        MappedByteBuffer mappedByteBuffer = input.map(FileChannel.MapMode.READ_ONLY, 0, length);
        if(charsetName != null){
            ByteBuffer byteBuffer = encoding(mappedByteBuffer, charsetName);
            output.write(byteBuffer);
        }else {
            output.write(mappedByteBuffer);
        }


        inputFile.close();
        outputFile.close();
    }


    /**
     * Description: 按照指定格式编解码
     *
     * @param:
     * @return:
     * @auther: hanxiaofan
     * @date: 2019/3/5 19:18
     */
    private static ByteBuffer encoding(ByteBuffer buffer, String charsetName) throws CharacterCodingException {
        //构建编码格式
        Charset charset = Charset.forName(charsetName);
        //编码器
        CharsetDecoder decode = charset.newDecoder();
        //解码器
        CharsetEncoder encode = charset.newEncoder();
        //先按照charsetName 解码成CharBuffer
        CharBuffer charBuffer = decode.decode(buffer);
        //按照指定格式编码
        return encode.encode(charBuffer);
    }
}
