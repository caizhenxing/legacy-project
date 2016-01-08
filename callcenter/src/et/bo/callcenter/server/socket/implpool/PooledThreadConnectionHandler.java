package et.bo.callcenter.server.socket.implpool;
import java.io.*;
import java.net.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

	public class PooledThreadConnectionHandler implements Runnable{
		protected Socket connection;
	    protected static List pool = new LinkedList();
	    private static Log log = LogFactory.getLog(PooledThreadConnectionHandler.class);
	    public PooledThreadConnectionHandler() {
	    }
    public static void processRequest(Socket requestToHandle) {
    	synchronized (pool){
            pool.add(pool.size(), requestToHandle);
            log.debug("#####"+pool.size()+"#####");
            pool.notifyAll();
        }
    }
    private String handleConnection() {
    	String content=null;
    	try {
            PrintWriter streamWriter = new PrintWriter(connection.getOutputStream());
            BufferedReader streamReader =
                new BufferedReader(new InputStreamReader(connection.getInputStream()));
            
            content = streamReader.readLine();
            
//            System.out.println(content);
            log.debug("receiveing begin............\r\n");
            log.debug(content);
            log.debug("receiveing end........");
            streamWriter.println(content+"\r\n");
//            streamWriter.println("hello\r\n");
            streamWriter.flush();
            
            streamReader.close();
            streamWriter.close();
//            connection.close();
            return content;
        } catch (FileNotFoundException e) {
//            System.out.println("Could not find requested file on the server.");
            log.error("Could not find requested file on the server.");
            return content;
        } catch (IOException e) {
//            System.out.println("Error handling a client: " + e);
            log.error("Error handling a client: " + e);
            return content;
        }
    }
    public void run() {
    	while (true) {
            synchronized (pool) {
                 while (pool.isEmpty()) {
                      try {
//                    	  System.out.print("is waiting pool ...\r\n");
                    	  log.debug("--- waiting pool .................................\r\n");
                          pool.wait();
                      } catch (InterruptedException e){
                    	  e.printStackTrace();
                           return;
                      }catch(Exception e){
                    	  e.printStackTrace();
                      }
                  }
//                 System.out.print("will doing sth and pool is removing.....!\r\n");
                 log.debug("will doing sth and pool is removing.....!\r\n");
                  connection = (Socket) pool.remove(0);
            }
//            System.out.print("doing sth!\r\n");
            log.debug("doing sth!\r\n");
            handleConnection();
       }
    }
}