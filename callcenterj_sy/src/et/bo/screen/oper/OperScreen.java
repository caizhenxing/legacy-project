/**
 * 
 */
package et.bo.screen.oper;

import et.bo.screen.service.QuickMessageService;
import et.bo.xml.XmlBuild;
import et.bo.screen.service.ScreenService;

import excellence.framework.base.container.SpringRunningContainer;


/**
 * @author zhangfeng
 *
 */
public class OperScreen {	
	public String getQuickMessage(){
		QuickMessageService qms = (QuickMessageService)SpringRunningContainer.getInstance().getBean("quickMessageService");
		return qms.screenOper();
	}
	
	/**
	 * 显示最近一个月的哪个类别接听电话的次数，横轴是类别，纵轴是显示的数据内容
	 * @return
	 */
	/*public void getCallLogStatisByMonth(){
		try {
			ScreenService ss = (ScreenService)SpringRunningContainer.getInstance().getBean("ScreenService");
			XmlBuild xb = new XmlBuild();
			xb.modifyXMLNotNum("CallLogStatisMonth2D",ss.getCallLogStatisByMonth(),"12316呼叫中心月话务分析","类别","接听次数");			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}	
	}*/
	
	/**
	 * 显示的是当天的哪个类别接听电话的次数，横轴是类别，纵轴是显示的数据内容
	 * @return
	 */	
	/*public void getCallLogStatisByDay(){		
		try {
			ScreenService ss = (ScreenService)SpringRunningContainer.getInstance().getBean("ScreenService");
			XmlBuild xb = new XmlBuild();
			xb.modifyXMLNotNum("CallLogStatisDay2D",ss.getCallLogStatisByDay(),"12316呼叫中心日话务分析","类别","接听次数");			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}*/
}
