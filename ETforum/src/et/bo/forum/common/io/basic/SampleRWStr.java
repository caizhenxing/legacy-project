package et.bo.forum.common.io.basic;

/**
 * <p>Title: ¼ûcommon.io.RW</p>
 * <p>Description: My com.guxf function</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: forecast</p>
 * @author ¹¼Ð¡·å
 * @version 1.0
 */

//import java.util.*;
import java.io.*;
//import com.guxf.io.basic.*;
//import java.util.Date;
//import java.sql.*;

public class SampleRWStr {

    private int buffereSize=1024*10;
    public SampleRWStr() {
    }
    public static void main(String[] args) {
        SampleRWStr rw = new SampleRWStr();

        String s=rw.read("c:/sqlnet.log");
        //System.out.println(s);

        long lB=System.currentTimeMillis();
        rw.write("c:/ha-rw.txt",s,true);
        long lE=System.currentTimeMillis();
        System.out.println((lE-lB));
    }
    //½«str write File(or append it to FileString absFileName,String str)
    public void write(String FileName,String str,boolean append){
        //String ls=(new com.guxf.io.basic.LineSeperator()).getLineSeperator();
        try {
            BufferedWriter bw=new BufferedWriter(new FileWriter(FileName,true));

            BufferedReader br=new BufferedReader(new StringReader(str));
            //DataInputStream dis=new DataInputStream(new InputStreamReader(br));
            /*
            //finally ,the c is null to write the file.
            char[] c=new char[buffereSize];
            int len;
            while((len=sr.read(c))!=-1) {
                bw.write(c);
                c=new char[buffereSize];
            }
            //line seperator is no easy to handle
            String sTemp=new String();
            while((sTemp=br.readLine())!=null) bw.write(sTemp+ls);
            */
            /*
            int i;
            while((i= br.read())!= -1) bw.write((char)i);
            */
            bw.write(str);
            bw.flush();
            bw.close();
            br.close();
        }
        catch (java.io.FileNotFoundException  fnf) {
            System.out.println("FileNotFoundException");
            fnf.printStackTrace();
        }
        catch (java.io.IOException ioe){
            System.out.println("IOException");
            ioe.printStackTrace();
        }
    }
    public String read(String FileName){
        StringBuffer sb=new StringBuffer();
        //String ls=(new com.guxf.io.basic.LineSeperator()).getLineSeperator();
        try{
            BufferedReader br = new BufferedReader(new FileReader(FileName));
            /*
            char[] c=new char[buffereSize];
            int len;
            while((len = br.read(c))!= -1){
                sb.append(c);
                c=new char[buffereSize];
            }

            String sTemp=new String();
            while((sTemp=br.readLine())!=null) sb.append(sTemp+ls);
            */
            int i;
            while((i= br.read())!= -1) sb.append((char)i);
            br.close();
        }catch (java.io.FileNotFoundException  fnf) {
            System.out.println("FileNotFoundException");
            fnf.printStackTrace();
        }
        catch (java.io.IOException ioe){
            System.out.println("IOException");
            ioe.printStackTrace();
        }
        return sb.toString();
    }
    public void setBuffereSize(int buffereSize) {
        this.buffereSize = buffereSize;
    }
    public int getBuffereSize() {
        return buffereSize;
    }
}
