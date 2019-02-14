package com.fan.protobuf;

/**
 * 这个实例的主要作用：
 * protobuf的主要作用是将对象转换成字节数组在网络上传输，然后再RPC框架中进行数据的
 * 交互。即使是两个不一样的系统，也不会影响传输。
 */
public class TestProtobuf {
    public static void main(String[] args) throws Exception{
        //构建一个对象
        DataInfo.Student student = DataInfo.Student.newBuilder()
                .setName("张三").setAge(20).setAddress("北京").build();
        //转化成字节数组，以便于传输
        byte[] student2ByteArray = student.toByteArray();

        //将字节数组转化对象
        DataInfo.Student student2 = DataInfo.Student.parseFrom(student2ByteArray);

        System.out.println(student2.getName());
        System.out.println(student2.getAge());
        System.out.println(student2.getAddress());
    }
}
