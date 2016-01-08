package et.bo.police.socket.impl;
import java.io.*;
import java.net.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
/**
 * 
 * @author guxiaofeng
 *
 */
public class StaticConnectionHandler {

    protected static Map longLink = new ConcurrentHashMap(); 
    public StaticConnectionHandler() {
    }
    private static void handleConnection(Socket connection,String content) {
    	try {
            PrintWriter streamWriter = new PrintWriter(connection.getOutputStream());
            BufferedReader streamReader =
                new BufferedReader(new InputStreamReader(connection.getInputStream()));
            streamWriter.println(content);            
            streamWriter.close();
            streamReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Could not find requested file on the server.");
        } catch (IOException e) {
            System.out.println("Error handling a client: " + e);
        }
    }
    /*
     * �з��������͵����ݣ����ҵ�ip��Ӧ�ĳ����Ӷ�Ӧ��socket��Ȼ����д���
     */
    public static void processResponse(String ip,String content) {
    	synchronized (longLink) {
    		Socket s=(Socket)longLink.get(ip);
    		StaticConnectionHandler.handleConnection(s,content);
        }
    }
}
