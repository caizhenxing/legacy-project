package et.bo.police.callcenter.message;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import et.bo.police.PoliceInfo;
import et.bo.police.callcenter.Constants;
import et.bo.police.callcenter.message.event.EventHelperService;
import et.bo.police.callcenter.server.CommandStr;
import et.bo.police.callcenter.server.Server;

public abstract class BaseEvent implements RuleI {
	protected String evtResult;
	protected String rule;
	protected String[] args;
	private final String PKG="et.bo.police.callcenter.message.event.";
	private static Log log = LogFactory.getLog(BaseEvent.class);
	
	//basic 注入
	protected PoliceInfo pi = null;
	protected Server server = null;
	protected CommandStr cs = null;
	protected EventHelperService eventHelper = null;
	//basic 注入 over
	
	/*
	 * see interface
	 */
	public RuleI action(String rule, String[] args) {
		//设置全局变量;
		this.setInfo(rule, args);
		//调用动态类
		this.invokeDyna();
		//执行业务逻辑
		this.execute();
		return this;
	}
	
	protected void setInfo(String rule, String[] args){
		this.rule =rule;
		this.args = args;
	}
	
	protected void invokeDyna(){
		StringBuffer sb = new StringBuffer(this.PKG);
		sb.append(formatRuleToClass(rule));
		RuleI ri=null;
		try{
			ri=(RuleI)Class.forName(sb.toString()).newInstance();

	     }catch(ClassNotFoundException ex){
	    	 evtResult=Constants.EVT_OPERATE_ERROR;
	         log.error("ClassNotFoundException");
	     }catch(InstantiationException ex){
	         evtResult=Constants.EVT_OPERATE_ERROR;
	         log.error("InstantiationException");
	     }catch (IllegalAccessException ex){
	         evtResult=Constants.EVT_OPERATE_ERROR;
	         log.error("IllegalAccessException");
	     }catch(Exception e){
	        e.printStackTrace();
	        log.error("unexpected exception");
	       }finally{
	    	   evtResult=Constants.EVT_OPERATE_OK;
	       }
	}
	
	//first character to upper.and other character is lower case.Such as "Ptring" like a class.
	private String formatRuleToClass(String rule){		
		String s0=rule.substring(0,1);
		String s1=rule.substring(1);
		s1.toLowerCase();
		StringBuffer sb=new StringBuffer(s0);
		sb.append(s1);
		return sb.toString();
	}
	abstract protected void execute();

	public CommandStr getCs() {
		return cs;
	}

	public void setCs(CommandStr cs) {
		this.cs = cs;
	}

	public PoliceInfo getPi() {
		return pi;
	}

	public void setPi(PoliceInfo pi) {
		this.pi = pi;
	}

	public Server getServer() {
		return server;
	}

	public void setServer(Server server) {
		this.server = server;
	}

	public void setEventHelper(EventHelperService eventHelper) {
		this.eventHelper = eventHelper;
	}

}
