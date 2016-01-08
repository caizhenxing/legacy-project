package et.bo.callcenter.server.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import et.bo.callcenter.ConstantsI;
import et.bo.callcenter.business.nouse.EventAction;

public class EventActionImpl implements EventAction {
	private String evtResult;
	private String[] evtInfo;
	private final String PKG="et.bo.callcenter.server.event.";
	private final String EVENT="Event";
	private static Log log = LogFactory.getLog(EventActionImpl.class);
	/*
	 * (non-Javadoc)
	 * @see et.bo.callcenter.server.EventAction#action(java.lang.String, java.lang.String[])
	 * 动态类加载,规则是"Event"+FormatedRule,FormatedRule是经过格式化的rule，第一个字母大写，后面的小写。
	 */
	public EventAction action(String rule, String[] args) {
		StringBuffer sb = new StringBuffer(this.PKG);
		sb.append(this.EVENT);
		sb.append(formatRule(rule));
		EventAction eai=null;
		try{
			eai=(EventAction)Class.forName(sb.toString()).newInstance();

	     }catch(ClassNotFoundException ex){
	    	 evtResult=ConstantsI.EVT_OPERATE_ERROR;
	         log.error("ClassNotFoundException");
	     }catch(InstantiationException ex){
	         evtResult=ConstantsI.EVT_OPERATE_ERROR;
	         log.error("InstantiationException");
	     }catch (IllegalAccessException ex){
	         evtResult=ConstantsI.EVT_OPERATE_ERROR;
	         log.error("IllegalAccessException");
	     }catch(Exception e){
	        e.printStackTrace();
	        log.error("unexpected exception");
	       }finally{
	    	   evtResult=ConstantsI.EVT_OPERATE_OK;
	       }
	       return eai;
	}
	/*
	 * 第一个字母大写，其余小写。
	 */
	private String formatRule(String rule){
		
		String s0=rule.substring(0,1);
		String s1=rule.substring(1);
		s1.toLowerCase();
		StringBuffer sb=new StringBuffer(s0);
		sb.append(s1);
		return sb.toString();
	}
}
