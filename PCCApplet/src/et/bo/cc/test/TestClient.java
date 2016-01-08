package et.bo.cc.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;

import excellence.common.util.file.FileUtil;

public class TestClient {

	int port = 12000;

	String host = "192.168.1.202";

	private Socket socket = null;

	public TestClient() {
		connection();
		NetListener nl = new NetListener();
		nl.start();
		SendListener sl = new SendListener();
		sl.start();
	}

	private void connection() {
		try {
			socket = new Socket(host, port);
			socket.setSoTimeout(3000);
			System.out.println("success");
		} catch (Exception ex) {
			

		}
	}

	String ring = "OORING";

	String seri = "OOSERI";

	String disp = "OODISP";

	String vali = "OOVALI";

	String estate = "ESTATE";

	class NetListener extends Thread {
		public void run() {
			//netConnectListener();
			accept();
		}
	}
	class NetListener1 extends Thread {
		public void run() {
			//netConnectListener();
			while(true)
			{
				try {
					this.sleep(3000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				sendMsg("NETTST:;");
			}
		}
	}

	class SendListener extends Thread {
		public void run() {
			while (true) {
				StringBuilder sb = new StringBuilder();
				try {
					int i;
					while ((i = System.in.read()) != 59)
						sb.append((char) i);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String s = sb.toString();
				sendMsg(s + ";");
				//System.out.println(s);
				writeFile(">" + s + ";" + "<");
			}
		}
	}

	private void sendMsg(String msg) {
		synchronized (socket) {
			if (!socket.isConnected()) {
				connection();

			}
			OutputStream os;
			try {
				os = socket.getOutputStream();
				os.write(msg.getBytes());
				os.flush();
			} catch (IOException ex) {
				connection();

			}
		}
	}

	private void accept() {
		Queue<String> cms = new LinkedList<String>();
		while (true) {
			if (!(socket.isConnected() || !socket.isClosed())) {
				connection();

			}
			StringBuilder sb = new StringBuilder();
			InputStream is = null;
			try {
				synchronized (socket) {
					is = socket.getInputStream();
				}

				int i;
				while ((i = is.read()) != 59 & i != -1) {
					sb.append((char) i);
				}
				if (i == -1)
					continue;
			} catch (IOException e) {
				e.printStackTrace();
				connection();
			}
			String mm = sb.toString();
			if (!"".equals(mm))
				cms.add(mm + ";");

			while (!cms.isEmpty()) {
				String cm = cms.poll();
				if (!cm.equals("NETTST:;"))
					writeFile("<" + cm + ">");
			}

		}
	}

	public static void main(String[] arg0) {

		TestClient tc = new TestClient();
		//tc.accept();

	}

	private void writeFile(String s) {
		FileUtil fu = new FileUtil();
		fu.writeToFile("d:/appletlog.txt", s, true);
		System.out.println(s);
	}
}
