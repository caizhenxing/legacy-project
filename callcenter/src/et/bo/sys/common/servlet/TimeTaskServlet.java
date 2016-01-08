package et.bo.sys.common.servlet;

import java.util.Timer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import excellence.framework.base.container.SpringRunningContainer;

public class TimeTaskServlet extends HttpServlet {

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		Timer timer=(Timer)SpringRunningContainer.getInstance().getBean("timerFactory");
	}

}
