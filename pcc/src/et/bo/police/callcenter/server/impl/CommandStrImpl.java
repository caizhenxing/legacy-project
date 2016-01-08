package et.bo.police.callcenter.server.impl;

import java.text.MessageFormat;

import ocelot.framework.base.container.SpringContainer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import et.bo.police.callcenter.ConstantRule;
import et.bo.police.callcenter.Constants;
import et.bo.police.callcenter.server.CommandStr;
import et.bo.police.callcenter.server.RuleInfo;
import et.bo.testing.MyLog;
import et.po.CcRule;

public class CommandStrImpl implements CommandStr{
	private RuleInfo ruleInfo;
	private String cmdResultBeforeSend;
	private static Log log = LogFactory.getLog(CommandStrImpl.class);
	/*
	 * BaseDAO
	 */
//	private static BaseDAO dao = null;
	public CommandStrImpl() {
		// TODO Auto-generated constructor stub
	}
	public String getSendMsg(String cmd,String[] args){
		String output=null;
		CcRule cr=ruleInfo.getCcRule(cmd+ConstantRule.CMD);
		//格式化的命令的格式。
		String cmdFormat =cr.getMsgFormat();
		//命令的参数的个数。
		int cmdArgNum =Integer.parseInt(cr.getArgNum());		
		if(cmdArgNum!=args.length){
			log.error("input args error!");
			this.cmdResultBeforeSend=Constants.CMD_SEND_ERROR;
			return output;
		}
		output = MessageFormat.format( cmdFormat, args );
		this.cmdResultBeforeSend=Constants.CMD_SEND_OK;
		return output;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new MyLog();
    	SpringContainer sc = SpringContainer.getInstance();
//    	PooledThreadServer pts =(PooledThreadServer)sc.getBean("PooledThreadServer");
    	
	}
	public String getCmdResultBeforeSend() {
		return cmdResultBeforeSend;
	}
	

}
