/*
 * �������� 2004-10-21
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

public class RemoteFileServer {
	protected int listenPort = 3000;

	public void acceptConnections() {
		try {
			ServerSocket server = new ServerSocket(listenPort);
			Socket incomingConnection = null;
			while (true) {
				incomingConnection = server.accept();
				handleConnection(incomingConnection);
			}
		} catch (BindException e) {
			System.out.println("Unable to bind to port " + listenPort);
		} catch (IOException e) {
			System.out.println("Unable to instantiate a ServerSocket on port: " + listenPort);
		}

	}
	public void handleConnection(Socket incomingConnection) {

		try {
			OutputStream outputToSocket = incomingConnection.getOutputStream();
			InputStream inputFromSocket = incomingConnection.getInputStream();

			BufferedReader streamReader =
				new BufferedReader(new InputStreamReader(inputFromSocket));

			FileReader fileReader = new FileReader(new File(streamReader.readLine()));

			BufferedReader bufferedFileReader = new BufferedReader(fileReader);
			PrintWriter streamWriter =
				new PrintWriter(incomingConnection.getOutputStream());
			String line = null;
			while ((line = bufferedFileReader.readLine()) != null) {
				streamWriter.println(line);
			}

			fileReader.close();
			streamWriter.close();
			streamReader.close();
		} catch (Exception e) {
			System.out.println("Error handling a client: " + e);
		}
	}
	public static void main(String[] args) {
		RemoteFileServer server = new RemoteFileServer();
		server.acceptConnections();
	}

}

