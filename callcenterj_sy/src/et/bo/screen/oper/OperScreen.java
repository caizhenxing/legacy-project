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
	 * ��ʾ���һ���µ��ĸ��������绰�Ĵ����������������������ʾ����������
	 * @return
	 */
	/*public void getCallLogStatisByMonth(){
		try {
			ScreenService ss = (ScreenService)SpringRunningContainer.getInstance().getBean("ScreenService");
			XmlBuild xb = new XmlBuild();
			xb.modifyXMLNotNum("CallLogStatisMonth2D",ss.getCallLogStatisByMonth(),"12316���������»������","���","��������");			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}	
	}*/
	
	/**
	 * ��ʾ���ǵ�����ĸ��������绰�Ĵ����������������������ʾ����������
	 * @return
	 */	
	/*public void getCallLogStatisByDay(){		
		try {
			ScreenService ss = (ScreenService)SpringRunningContainer.getInstance().getBean("ScreenService");
			XmlBuild xb = new XmlBuild();
			xb.modifyXMLNotNum("CallLogStatisDay2D",ss.getCallLogStatisByDay(),"12316���������ջ������","���","��������");			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}*/
}
