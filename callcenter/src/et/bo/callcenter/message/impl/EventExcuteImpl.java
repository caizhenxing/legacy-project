package et.bo.callcenter.message.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import et.bo.callcenter.ConstantsI;
import et.bo.callcenter.message.EventExcute;
import et.bo.callcenter.message.EventI;
import et.bo.callcenter.message.EventStrValid;
import et.bo.callcenter.message.RuleArg;
import et.bo.common.ConstantsCommonI;
import et.bo.common.testing.MyLog;

public class EventExcuteImpl implements EventExcute {
	protected String evtResult;
	private final String PKG="et.bo.callcenter.message.event.";
	private static Log log = LogFactory.getLog(EventExcuteImpl.class);
	/*
	 * 注入
	 */
	private EventStrValid esv=new EventStrValidImpl();

	public String excute(String s,String ip) {
		// 1传入的str的处理，略
		
		// 2命令解析,如果返回的参数是空说明是错误的命令
		RuleArg ra = esv.getRuleArg(s);
		if(ra==null)return null;
		
		// 3命令处理
		EventI ei=invokeDyna(ra.getCmd());
		//
		if (ra.getArgs()==null)ra.setArgs(new String[]{ip});
		else{
			String[] sArray = new String[ra.getArgs().length+1];
			for(int i=0;i<ra.getArgs().length;i++){
				sArray[i]=ra.getArgs()[i];
			}
			sArray[ra.getArgs().length]=ip;
			ra.setArgs(sArray);
			MyLog.info(s);
			for(int i=0;i<sArray.length;i++){
				MyLog.info(sArray[i]);
			}
		}
		
		evtResult=ei.action(ra.getCmd(), ra.getArgs());
		return evtResult;
	}
	public String getResult(){
		return evtResult;
	}
	public void setEsv(EventStrValid esv) {
		this.esv = esv;
	}
	private EventI invokeDyna(String rule){
		String sTmp=formatRuleToClass(rule);
		StringBuffer sb = new StringBuffer(this.PKG);
		sb.append(sTmp);
		EventI ei=null;
		try{
			log.debug(ConstantsCommonI.TEST_RETURN);
			log.debug(ConstantsCommonI.TEST_LINE);
			log.debug(sb.toString());
			log.debug(ConstantsCommonI.TEST_RETURN);
			log.debug(ConstantsCommonI.TEST_LINE);
			
			ei=(EventI)Class.forName(sb.toString()).newInstance();

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
	       return ei;
	}
//	first character to upper.and other character is lower case.Such as "Ptring" like a class.
	private String formatRuleToClass(String rule){
		String s0=rule.substring(0,1);
		String s1=rule.substring(1);
		s1=s1.toLowerCase();
		StringBuffer sb=new StringBuffer(s0);
		sb.append(s1);
		return sb.toString();
	}
}
