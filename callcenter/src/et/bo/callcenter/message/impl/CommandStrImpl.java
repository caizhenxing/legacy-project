package et.bo.callcenter.message.impl;

import java.text.MessageFormat;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import et.bo.callcenter.ConstRuleI;
import et.bo.callcenter.ConstantsI;
import et.bo.callcenter.message.CommandStr;
import et.bo.callcenter.message.RuleInfo;
import et.po.CcRule;
import excellence.framework.base.container.SpringContainer;

public class CommandStrImpl implements CommandStr{
	
	private String cmdResultBeforeSend;
	private static Log log = LogFactory.getLog(CommandStrImpl.class);
	/*
	 * 注入
	 */
	private RuleInfo ruleInfo=null;
	public void setRuleInfo(RuleInfo ruleInfo) {
		this.ruleInfo = ruleInfo;
	}
	/*
	 * BaseDAO
	 */
//	private static BaseDAO dao = null;
	public CommandStrImpl() {
		// TODO Auto-generated constructor stub
	}
	public String getSendMsg(String cmd,String[] args){
		String output=null;
		StringBuffer sb = new StringBuffer();
		sb.append(cmd);
		sb.append("_");
		sb.append(ConstRuleI.CMD);
		log.debug("&&&&&&");
		log.debug(sb.toString());
		CcRule cr=ruleInfo.getCcRule(cmd+"_"+ConstRuleI.CMD);
		//格式化的命令的格式。
		String cmdFormat =cr.getMsgFormat();
		//命令的参数的个数。
		int cmdArgNum =Integer.parseInt(cr.getArgNum());		
		if(cmdArgNum!=args.length){
			log.error("input args error!");
			this.cmdResultBeforeSend=ConstantsI.CMD_SEND_ERROR;
			return output;
		}
		output = MessageFormat.format( cmdFormat, args );
		this.cmdResultBeforeSend=ConstantsI.CMD_SEND_OK;
		log.info(output);
		
		return output;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
    	SpringContainer sc = SpringContainer.getInstance();
    	CommandStr pts =(CommandStr)sc.getBean("CommandStrService");
    	String s=pts.getSendMsg(ConstRuleI.CMD_OOVALI, new String[]{"0"});
    	log.debug(s);
//		String s=MessageFormat.format( "OOVALI:{0};", new String[]{"0"} );
//		System.out.println(s);
	}
	public String getCmdResultBeforeSend() {
		return cmdResultBeforeSend;
	}
	

}
