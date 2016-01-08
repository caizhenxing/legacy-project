package et.bo.forum.common.io;
/**
 * <p>Title: </p>
 * <p>Description: 详细见 class :RW</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: forecast</p>
 * @author 辜小峰
 * @version 1.0
 */

//import java.io.*;
import et.bo.forum.common.io.basic.*;
public class IO {

    private int buffereSize=1024*5;
    public IO() {
    }
    public static void main(String[] args) {
        IO io = new IO();
        //String s=io.read("c:/sqlnet.log");
		String s = "hello guxiaofeng!";
        //System.out.println(s);
        long lB=System.currentTimeMillis();
        io.write("c:/ha-io",s,true);
		io.write("haha",s,true);
        long lE=System.currentTimeMillis();
        System.out.println((lE-lB));
    }
    //将str write File(or append it to FileString absFileName,String str)
    public void write(String fileName,String str,boolean append){
        try {
             /*
            FileOutputStream out = new FileOutputStream(FileName,append);//--------java 1.4才有
            BufferedOutputStream bufOut = new BufferedOutputStream(out);
            DataOutputStream dos=new DataOutputStream(bufOut);
             */
            OutFile dos=new OutFile(fileName,append);
            dos.writeBytes(str);
            dos.close();
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
    public String read(String fileName){
        StringBuffer sb=new StringBuffer();
        try{
        /*
         FileInputStream in=new FileInputStream(FileName);
         BufferedInputStream bufIn=new BufferedInputStream(in,buffereSize);
         DataInputStream dis=new DataInputStream(bufIn);
        */
            InFile dis=new InFile(fileName);
            int i;
            while((i=dis.read())!=-1)sb.append((char)i);
            dis.close();
        }catch (java.io.FileNotFoundException  fnf) {
            System.out.println("FileNotFoundException");
            fnf.printStackTrace();
        }catch(java.io.EOFException eofe){
            eofe.getMessage();
            eofe.printStackTrace();
        }catch (java.io.IOException ioe){
            ioe.getMessage();
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