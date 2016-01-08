/*
 * 创建日期 2004-10-22
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */

/**
 * @author Administrator
 *
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
import java.io.*;
import java.net.*;
import java.util.*;

public class PooledConnectionHandler implements Runnable {
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
			while ((line = fileReader.readLine()) != null)
				streamWriter.println(line);

			fileReader.close();
			streamWriter.close();
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
			pool.notifyAll();
		}

	}
	public void run() {
		while (true) {
					 synchronized (pool) {
						  while (pool.isEmpty()) {
							   try {
									pool.wait();
							   } catch (InterruptedException e) {
									return;
							   }
						   }
						   connection = (Socket) pool.remove(0);
					 }
					 handleConnection();
				}

	}
}
