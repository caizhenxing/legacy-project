package et.test.callcenter.pcc.callinFirewall;

import org.apache.log4j.Logger;

import et.bo.pcc.callinFirewall.service.CallinFirewallService;
import excellence.framework.base.container.SpringContainer;
import excellence.framework.base.dto.IBaseDTO;

public class CallinFirewallTest {
	
	static Logger log = Logger.getLogger(CallinFirewallTest.class.getName());

	public static void main(String[] args) {
		
//		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
//		CallinFirewallService callinFirewallService = (CallinFirewallService)context.getBean("callinFirewallService");
//		boolean flag = callinFirewallService.IfInBlacklist("111");
//		System.out.println(flag);
		
		SpringContainer s=SpringContainer.getInstance();
//		CallinFirewallService callinFirewallService = (CallinFirewallService)context.getBean("callinFirewallService");
		System.out.println("+++++++++++++++++++++++++++++++");
		CallinFirewallService callinFirewallService=(CallinFirewallService)s.getBean("CallinFirewallService");
		boolean flag = callinFirewallService.IfInBlacklist("2255555555");
//		IBaseDTO dto = callinFirewallService.getRuleInfo("POLICECALLINFIREWALL_0000000082");
//		
		String ss = "";
//		System.out.println("+++++++++++++++++++++++++++++++");
		if(flag==true){ss="未通过";}else{ss="通过验证";}
		log.info("电话号码  "+ss);
//		log.info(dto.get("id").toString());
	}
}
