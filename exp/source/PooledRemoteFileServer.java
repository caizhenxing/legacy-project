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
//setUpHandlers() 创建数目为 maxConnections 的大量 PooledConnectionHandler，而其它两个方法则与我们前面已经看到的相似：acceptConnections() 在 ServerSocket 上侦听传入的客户机连接，而 handleConnection 则在客户机连接一旦被建立后就实际处理它。


public class PooledRemoteFileServer {
	protected int maxConnections;
	protected int listenPort;
	protected ServerSocket serverSocket;

	public PooledRemoteFileServer(int aListenPort, int maxConnections) {
		listenPort = aListenPort;
		this.maxConnections = maxConnections;
	}
	public static void main(String[] args) {

		PooledRemoteFileServer server = new PooledRemoteFileServer(3000, 3);
		server.setUpHandlers();
		server.acceptConnections();

	}
	public void setUpHandlers() {

		for (int i = 0; i < maxConnections; i++) {
			PooledConnectionHandler currentHandler = new PooledConnectionHandler();
			new Thread(currentHandler, "Handler " + i).start();
		}

	}
	public void acceptConnections() {
	}
	protected void handleConnection(Socket connectionToHandle) {
		PooledConnectionHandler.processRequest(connectionToHandle);

	}
}

