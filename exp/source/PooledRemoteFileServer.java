/*
 * �������� 2004-10-22
 *
 * �����������ļ�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */

/**
 * @author Administrator
 *
 * ��������������ע�͵�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
import java.io.*;
import java.net.*;
import java.util.*;
//setUpHandlers() ������ĿΪ maxConnections �Ĵ��� PooledConnectionHandler������������������������ǰ���Ѿ����������ƣ�acceptConnections() �� ServerSocket ����������Ŀͻ������ӣ��� handleConnection ���ڿͻ�������һ�����������ʵ�ʴ�������


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

