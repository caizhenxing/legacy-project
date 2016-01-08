package et.test;

import java.util.Timer;

import ocelot.framework.base.container.SpringContainer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class test {
	
	 private static Log log = LogFactory.getLog(test.class);
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		log.info("========================");
		log.info("========================");
		log.info("init");
		log.info("========================");
		log.info("========================");
		SpringContainer s=SpringContainer.getInstance();
		Timer timer=(Timer)s.getBean("timerFactory");
		
		/*log.error("*(************");
		Timer timer=(Timer)s.getBean("timerFactory");
		//Scheduler sd=(Scheduler)s.getBean("quartz");
		log.error("*(************");
			try {
				log.error("-------------"+System.currentTimeMillis());
				Thread.sleep(6000000);
				
				log.error("-------------"+System.currentTimeMillis());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			
	}

}
