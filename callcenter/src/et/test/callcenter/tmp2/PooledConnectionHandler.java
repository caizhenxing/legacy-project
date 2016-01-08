package et.test.callcenter.tmp2;
import java.io.*;
import java.net.*;
import java.util.LinkedList;
import java.util.List;

public class PooledConnectionHandler implements Runnable{
	protected Socket connection;
    protected static List pool = new LinkedList();
    public PooledConnectionHandler() {
    }
    public void handleConnection() {
    	try {
            PrintWriter streamWriter = new PrintWriter(connection.getOutputStream());
            BufferedReader streamReader =
                new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String fileToRead = streamReader.readLine();
            BufferedReader fileReader = new BufferedReader(new FileReader(fileToRead));

            String line = null;
//            while ((line = fileReader.readLine()) != null)
//                streamWriter.println(line);
            streamWriter.println("hi");
            streamWriter.close();
            fileReader.close();
            streamReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Could not find requested file on the server.");
        } catch (IOException e) {
            System.out.println("Error handling a client: " + e);
        }
    }
    public static void processRequest(Socket requestToHandle) {
    	synchronized (pool) {
            pool.add(pool.size(), requestToHandle);
            System.out.println(pool.size());
    		//pool.add(requestToHandle);
            pool.notifyAll();
        }
    }
    public void run() {
    	while (true) {
            synchronized (pool) {
                 while (pool.isEmpty()) {
                      try {
                    	  System.out.print("is waiting pool ...\r\n");
                           pool.wait();
                      } catch (InterruptedException e){
                    	  e.printStackTrace();
                           return;
                      }catch(Exception e){
                    	  e.printStackTrace();
                      }
                  }
                 System.out.print("will doing sth and pool is removing.....!\r\n");
                  connection = (Socket) pool.remove(0);
            }
            System.out.print("doing sth!\r\n");
            handleConnection();
       }
    }
}
