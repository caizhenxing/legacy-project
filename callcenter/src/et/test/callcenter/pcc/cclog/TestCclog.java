package et.test.callcenter.pcc.cclog;

import java.util.Date;

import junit.framework.TestCase;

import org.apache.log4j.Logger;

import et.bo.callcenter.base.ConnectInfo;
import et.bo.pcc.cclog.service.CclogService;
import excellence.common.key.KeyService;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.container.SpringContainer;

public class TestCclog extends TestCase {
	static Logger log = Logger.getLogger(TestCclog.class.getName()); 
	public void testBoImpl() throws Exception {
	SpringContainer container = SpringContainer.getInstance();
	KeyService ks = (KeyService) container.getBean("KeyService");	
	CclogService cs = (CclogService) container.getBean("CclogService");
    ConnectInfo ci = new ConnectInfo();
//    StringBuffer sb = new StringBuffer();
//    ci.setCmd(sb);
    ci.setPhoneNum("22876322");
    ci.setId(ks.getNext("cclog"));
//    ci.setRecFile("CALLCENTER/REC/2006/10/18/165428_5.WAV");
//    Date   date1   =   new Date("17:29");   
//    Date   date2   =   new Date("17:32");   

//    Date o = TimeUtil.getNowTime();
//    Date b = TimeUtil.getNowTime();
//    ci.setBeginTime(beginTime);
//    ci.setOperateTime(o);
//    ci.setEndTime(b);
    try {
		cs.addCclog(ci);
	} catch (RuntimeException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    log.info("...............info................"+ci.getBeginTime());
	//	String pnum = "23996718";
//	String flag = bo.blacklist(pnum);
//	String message = "";
//	if(flag.equals("0")){
//		message = "在电话黑名单内";
//	}else{
//		message = "不在电话黑名单内";
//	}
//	log.info("返回值为: "+flag+" "+pnum+" "+message);
 }
}
