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

public class RemoteFileClient {
	protected BufferedReader socketReader;
	protected PrintWriter socketWriter;
	protected String hostIp;
	protected int hostPort;

	public RemoteFileClient(String aHostIp, int aHostPort) {
		hostIp = aHostIp;
		hostPort = aHostPort;
	}
	public String getFile(String fileNameToGet) {
		StringBuffer fileLines = new StringBuffer();

		try {
			socketWriter.println(fileNameToGet);
			socketWriter.flush();

			String line = null;
			while ((line = socketReader.readLine()) != null)
				fileLines.append(line + "\n");
		} catch (IOException e) {
			System.out.println("Error reading from file: " + fileNameToGet);
		}

		return fileLines.toString();
	}
	public static void main(String[] args) {
		RemoteFileClient remoteFileClient = new RemoteFileClient("127.0.0.1", 3000);
		remoteFileClient.setUpConnection();
		String fileContents = remoteFileClient.getFile("f:/Java 面试中的陷阱[转载].txt");
		remoteFileClient.tearDownConnection();

		System.out.println(fileContents);
	}
	public void setUpConnection() {
		try {
			Socket client = new Socket(hostIp, hostPort);

			socketReader = new BufferedReader(new InputStreamReader(client.getInputStream()));
			socketWriter = new PrintWriter(client.getOutputStream());

		} catch (UnknownHostException e) {
			System.out.println("Error setting up socket connection: unknown host at " + hostIp + ":" + hostPort);
		} catch (IOException e) {
			System.out.println("Error setting up socket connection: " + e);
		}
	}
	public void tearDownConnection() {
		try {
			socketWriter.close();
			socketReader.close();
		} catch (IOException e) {
			System.out.println("Error tearing down socket connection: " + e);
		}
	}
}

