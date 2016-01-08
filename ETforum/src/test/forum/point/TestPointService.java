package test.forum.point;

import junit.framework.TestCase;

import org.apache.log4j.Logger;

import et.bo.forum.point.service.PointService;
import excellence.common.classtree.ClassTreeService;
import excellence.framework.base.container.SpringContainer;
import excellence.framework.base.dao.BaseDAO;

public class TestPointService extends TestCase {
	
    private BaseDAO dao = null;
	
	private ClassTreeService cts = null;
	static Logger log = Logger.getLogger(TestPointService.class.getName());
	public void testBoImpl() throws Exception {
		SpringContainer container = SpringContainer.getInstance();	
		PointService ps = (PointService) container.getBean("PointService");
//        发帖加分 
//        ps.addSendPoint("yepl");
//        回帖加分
//		ps.addAnswerPoint("yepl");
		//查询等级
		int i = 20000;
		System.out.println("*********************");
		String level = ps.getUserLevel(i);
		log.info("===="+level);
		System.out.println("等级为: "+level);
		System.out.println("*********************");
	 }
}
