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
//        �����ӷ� 
//        ps.addSendPoint("yepl");
//        �����ӷ�
//		ps.addAnswerPoint("yepl");
		//��ѯ�ȼ�
		int i = 20000;
		System.out.println("*********************");
		String level = ps.getUserLevel(i);
		log.info("===="+level);
		System.out.println("�ȼ�Ϊ: "+level);
		System.out.println("*********************");
	 }
}
