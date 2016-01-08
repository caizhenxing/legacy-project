package et.workflow.common.servlet;


import java.util.Timer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import ocelot.framework.base.container.SpringContainer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class TimerServlet extends HttpServlet {
	 private static Log log = LogFactory.getLog(TimerServlet.class);
	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		
//		long lsj=60*60*1000;//1Сʱ=60minites*60seconds*1000ms
		log.info("========================");
		log.info("========================");
		log.info("init");
		log.info("========================");
		log.info("========================");
		SpringContainer s=SpringContainer.getInstance();
		Timer timer=(Timer)s.getBean("timerFactory");
		
		super.init();
	}

	
}
