package et.bo.forum.common.io;
/**
 * <p>Title: </p>
 * <p>Description: My com.guxf function
 * ��IO class��ȣ�Reader and Write ���� using time һ�� ����RW�򵥣�and read ����
 * �����õķ������٣���װ���Reader and Writer ������̫�á���Ҫ������ı��Ĵ���
 *
 * ��jdk1.4������FileInputStream ��append������
 * ˵��sun ��û�з���inputStream and outputStream��
 * </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: forecast</p>
 * @author ��С��
 * @version 1.0
 */

/*
 �����ļ���д��ʽ���Ǵ�������:Writer and Reader,������һ���ȶ�����д�룬���Ǳ߶���д��
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
    fileName   ���ļ���
    str        ������
    append     ���ļ���ӵķ�ʽ
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
    д�����ļ�
    */
    public void write(String fileName,String str){
        this.write(fileName,str,false);
    }
    /*
    ���ļ�����ת�����ַ���
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
    ���ļ�����ת�����ַ�����
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
