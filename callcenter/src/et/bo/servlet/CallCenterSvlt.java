package et.bo.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import et.test.callcenter.server.SvltTesting;

public class CallCenterSvlt extends HttpServlet {

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		System.out.println("init cc #########################init cc");
		new InnerClass().start();
		System.err.println("init cc ###########forth#############init cc");
	}
	class InnerClass extends Thread{
		public void run()
        {
			new SvltTesting().initServer();
        }
	}
}
