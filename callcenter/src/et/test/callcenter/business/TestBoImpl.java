package et.test.callcenter.business;

import org.apache.log4j.Logger;

import et.bo.callcenter.business.BusinessObject;
import excellence.framework.base.container.SpringContainer;
import junit.framework.TestCase;

public class TestBoImpl extends TestCase {
	static Logger log = Logger.getLogger(TestBoImpl.class.getName()); 
	public void testBoImpl() throws Exception {
	SpringContainer container = SpringContainer.getInstance();
//	KeyService ks = (KeyService) container.getBean("KeyService");	
	BusinessObject bo = (BusinessObject) container.getBean("BusinessObjectService");
    //���Ժ�����
	
	String pnum = "23996718";
	String flag = bo.blacklist(pnum);
	String message = "";
	if(flag.equals("0")){
		message = "�ڵ绰��������";
	}else{
		message = "���ڵ绰��������";
	}
	log.info("����ֵΪ: "+flag+" "+pnum+" "+message);
	
	String ip=bo.getPortLinkedIp("9");
//	System.out.println();
	log.info("ip��ַΪ��"+ip);
 }
}
