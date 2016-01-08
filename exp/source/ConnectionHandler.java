/*
 * 创建日期 2004-10-21
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

public class ConnectionHandler implements Runnable {
	protected Socket socketToHandle;
	public ConnectionHandler(Socket aSocketToHandle) {
		socketToHandle = aSocketToHandle;
	}
	public void run() {
		try {
			PrintWriter streamWriter = new PrintWriter(socketToHandle.getOutputStream());
			BufferedReader streamReader = new BufferedReader(new InputStreamReader(socketToHandle.getInputStream()));

			String fileToRead = streamReader.readLine();
			BufferedReader fileReader = new BufferedReader(new FileReader(fileToRead));

			String line = null;
			while ((line = fileReader.readLine()) != null)
				streamWriter.println(line);

			fileReader.close();
			streamWriter.close();
			streamReader.close();
		} catch (Exception e) {
			System.out.println("Error handling a client: " + e);
		}
	}
}

