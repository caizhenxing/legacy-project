package et.bo.forum.common.io;
/**
 * <p>Title: </p>
 * <p>Description: My com.guxf function
 * 与IO class相比，Reader and Write 方法 using time 一样 但是RW简单，and read 方法
 * 可以用的方法极少！封装后的Reader and Writer 并不是太好。主要是针对文本的处理
 *
 * 在jdk1.4中增加FileInputStream 的append方法，
 * 说明sun 并没有放弃inputStream and outputStream。
 * </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: forecast</p>
 * @author 辜小峰
 * @version 1.0
 */

/*
 现在文件读写方式都是打开两个流:Writer and Reader,而不是一起先读出后写入，而是边读编写。
 */
import java.util.*;
import java.io.*;
//import com.guxf.io.basic.*;
//import java.util.Date;
//import java.sql.*;

public class RW {

    public RW() {
    }
    public static void main(String[] args) {
        RW rw = new RW();
/*
        String s=rw.read("c:/sqlnet.log");
        long lB=System.currentTimeMillis();
        rw.write("c:/ha-rw.txt",s,true);
        long lE=System.currentTimeMillis();
        System.out.println((lE-lB));
        */
        String fileName1="f:/source1.txt";
        String fileName2="f:/source2.txt";
        //List list=rw.readCharSet(fileName1);
        //System.out.println(list.toString());
        rw.write("f:/ldy.txt","helloworld!\r\n",true);
        rw.write("f:/ldy.txt","helloworld1!",true);

    }
    /*
    fileName   ：文件名
    str        ：内容
    append     ：文件添加的方式
    */
    public void write(String fileName,String str,boolean append){
        try {
            BufferedWriter bw=new BufferedWriter(new FileWriter(fileName,append));
            bw.write(str);
            bw.close();
        }catch (java.io.FileNotFoundException  fnf) {
            System.out.println("FileNotFoundException");
            fnf.getMessage();
            fnf.printStackTrace();
        }catch (java.io.IOException ioe){
            System.out.println("IOException");
            ioe.getMessage();
            ioe.printStackTrace();
        }
    }
    /*
    写入新文件
    */
    public void write(String fileName,String str){
        this.write(fileName,str,false);
    }
    /*
    将文件内容转换成字符串
    */
    public String read(String FileName){
        StringBuffer sb=new StringBuffer();
        try{
            BufferedReader br = new BufferedReader(new FileReader(FileName));
            int i;
            while((i= br.read())!= -1) sb.append((char)i);
            
            br.close();
        }catch (java.io.FileNotFoundException  fnf) {
            System.out.println("FileNotFoundException");
            fnf.getMessage();
            fnf.printStackTrace();
        }catch (java.io.IOException ioe){
            System.out.println("IOException");
            ioe.getMessage();
            ioe.printStackTrace();
        }
        return sb.toString();
    }
    /*
    将文件内容转换成字符集合
    */
    public List readCharSet(String FileName){
        StringBuffer sb=new StringBuffer();
        List list=new ArrayList();
        try{
            BufferedReader br = new BufferedReader(new FileReader(FileName));
            int i;
            while((i= br.read())!= -1) list.add(new Character((char)i));
            br.close();
        }catch (java.io.FileNotFoundException  fnf) {
            System.out.println("FileNotFoundException");
            fnf.getMessage();
            fnf.printStackTrace();
        }catch (java.io.IOException ioe){
            System.out.println("IOException");
            ioe.getMessage();
            ioe.printStackTrace();
        }
        return list;
    }
}
