package excellence.framework.base.action;

import java.util.Timer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import excellence.framework.base.container.SpringRunningContainer;


 /**
 * @author zhaoyifei
 * @version 2007-1-15
 * @see
 */
public class TimeTaskServlet extends HttpServlet {

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		Timer timer=(Timer)SpringRunningContainer.getInstance().getBean("timerFactory");
	}

}
