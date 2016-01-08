package et.bo.callcenter.message;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import et.bo.callcenter.business.BusinessObject;
import et.bo.callcenter.server.Server;
import et.bo.common.testing.MyLog2;
import excellence.framework.base.container.SpringRunningContainer;

public abstract class BaseEvent implements EventI {
	protected String evtResult;
	protected String rule;
	protected String[] args;
	
	private static Log log = LogFactory.getLog(BaseEvent.class);
	
	//basic 注入
	protected BusinessObject pi = 
		(BusinessObject)SpringRunningContainer.getInstance().getBean("BusinessObjectService");
	protected Server server = 
		(Server)SpringRunningContainer.getInstance().getBean("ServerService");
	protected CommandStr cs = 
		(CommandStr)SpringRunningContainer.getInstance().getBean("CommandStrService");
	protected EventHelperService eventHelper = 
		(EventHelperService)SpringRunningContainer.getInstance().getBean("EventHelperService");

	//basic 注入 over
	/*
	 * see interface
	 */
	public String action(String rule, String[] args) {
		try{
//			设置全局变量;
			this.setInfo(rule, args);
			//执行业务逻辑
			evtResult=execute();
			return evtResult;
		}catch(Exception e){
			e.printStackTrace();
			StringBuffer sb=new StringBuffer();
			if(args!=null){
				for(int i=0;i<args.length;i++){
					sb.append("args "+i+" is :"+args[i]+"------");
				}
			}
			new MyLog2().info("rule is:"+rule +"args is "+sb.toString());
			return null;
		}finally{
			//do sth
			;
		}		
	}
	
	protected void setInfo(String rule, String[] args){
		this.rule =rule;
		this.args = args;
	}
	abstract protected String execute();

	public CommandStr getCs() {
		return cs;
	}

	public void setCs(CommandStr cs) {
		this.cs = cs;
	}

	public BusinessObject getPi() {
		return pi;
	}

	public void setPi(BusinessObject pi) {
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
	public String getResult(){
		return evtResult;
	}
}
