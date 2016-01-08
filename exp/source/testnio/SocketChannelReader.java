package testnio;

import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.net.InetSocketAddress;
import java.io.IOException;

public class SocketChannelReader{
    
    private Charset charset=Charset.forName("UTF-16");//����UTF-8�ַ���
    private SocketChannel channel;

    public void getHTMLContent(){
    try{
       connect();
       sendRequest();
       readResponse();
    }catch(IOException e){
       System.err.println(e.toString());
    }finally{
     if(channel!=null){
     try{
      channel.close();
  }catch(IOException e){}
     }
 }
    }
    private void connect()throws IOException{//���ӵ�CSDN
 InetSocketAddress socketAddress=
     new InetSocketAddress("www.csdn.net",80);
 channel=SocketChannel.open(socketAddress);
 //ʹ�ù�������open����һ��channel���������ӵ�ָ����ַ��
 //�൱��SocketChannel.open().connect(socketAddress);����
}

private void sendRequest()throws IOException{
 channel.write(charset.encode("GET "
        +"/document"
        +"\r\n\r\n"));//����GET����CSDN���ĵ�����
 //ʹ��channel.write����������ҪCharByte���͵Ĳ�����ʹ��
 //Charset.encode(String)����ת���ַ�����
    }

    private void readResponse()throws IOException{//��ȡӦ��
 ByteBuffer buffer=ByteBuffer.allocate(1024);//����1024�ֽڵĻ���
 while(channel.read(buffer)!=-1){
     buffer.flip();//flip�����ڶ��������ֽڲ���֮ǰ���á�
     System.out.println(charset.decode(buffer));
//ʹ��Charset.decode�������ֽ�ת��Ϊ�ַ���
     buffer.clear();//��ջ���
 }
    }

    public static void main(String [] args){
 new SocketChannelReader().getHTMLContent();
    }

}
