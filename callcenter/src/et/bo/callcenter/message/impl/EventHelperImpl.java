package et.bo.callcenter.message.impl;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import et.bo.callcenter.ConstOperI;
import et.bo.callcenter.ConstRuleI;
import et.bo.callcenter.base.CardState;
import et.bo.callcenter.base.OperatorState;
import et.bo.callcenter.business.BusinessObject;
import et.bo.callcenter.message.CommandStr;
import et.bo.callcenter.message.EventHelperService;
import et.bo.callcenter.server.Server;
import et.bo.callcenter.server.socket.ServerSocketHelper;
import et.bo.pcc.operatorStatistic.service.OperatorStatisticService;

public class EventHelperImpl implements EventHelperService{
	private static Log log = LogFactory.getLog(EventHelperImpl.class);
//	basic 注入
	protected CommandStr commandStr = null;
	protected Server server = null;
	protected BusinessObject bo = null;
	protected OperatorStatisticService oss=null;
	
//	basic 注入 over
	
	//------------------常量------------------
	public final String DRIVE="D:\\WEB_SERVER\\";
	//此在unix下面要该成\.
	public final String PATH = "CALLCENTER\\REC\\";
	public final String FILE_EXT = ".WAV";
//	------------------常量------------------
	
	public static void main(String[] args) {
//		EventHelperImpl ehi = new EventHelperImpl();
//		System.out.println(ehi.getFileName("15"));
	}
	/*
	 * 刷新座席面版的状态
	 * 
	 */
	public void refreshOperatorPanel(){
		List l = CardState.transToOperator();
		StringBuffer sb=new StringBuffer();
		for(int i=0;i<ConstOperI.OPERATOR_PANEL_NUM;i++){
			String s=(String)l.get(i);
			sb.append(s);
		}
		String s=sb.toString();
//		for(int i=0;i<OperatorState.getOperatorStateIpList().size();i++){
//			String ip=(String)CardInfo.getInnerIpList().get(i);
//			server.command(ip, s);
//		}
		Iterator i =OperatorState.getOperatorStateMap().keySet().iterator();
		OperatorState os=null;
		
		while(i.hasNext()){
			String on=(String)i.next();			
			OperatorState os1=
				(OperatorState)OperatorState.getOperatorStateMap().get(on);
			String ip=os1.getIp();
			String str=commandStr.getSendMsg(ConstRuleI.CMD_OODISP, new String[]{s});
			server.command(ip, str);
			System.out.println("%%%%%%%%%%%%%%%%%%%%%");
			System.out.println(str);
		}
	}
	public void setBo(BusinessObject bo) {
		this.bo = bo;
		ServerSocketHelper.icc_ip=bo.getCardPcIp();
	}

	public void setCommandStr(CommandStr commandStr) {
		this.commandStr = commandStr;
	}

	public void setServer(Server server) {
		this.server = server;
	}

	/*
	 * 刷新工控机的座席状态
	 * 工控机：icc
	 */
	public void refreshIccOperatorState(){
		List l = CardState.transToCard();
		System.err.println("list size is:"+l.size());
		for(int i=0;i<l.size();i++){
			String[] s = (String[])l.get(i);			
			String str=commandStr.getSendMsg(ConstRuleI.CMD_SETPRT, s);
			server.command(bo.getCardPcIp(), str);			
		}
	}
	/*
	 * 刷新工控机的座席员 :physicsPort and OperatorNum
	 * 工控机：icc 
	 */
	public void refreshIccOperator(String physicsPort,String OperatorNum){
		String str=commandStr.getSendMsg(ConstRuleI.CMD_SETJJY, new String[]{physicsPort,OperatorNum});
		System.out.println("str is:\\\\\\"+str);
		server.command(bo.getCardPcIp(), str);
	}
	/*
	 * 存储cclog、policeCallin、Police_callin_info
	 */
	public void saveInfo(String id){
		;
	}

	/*
	 * 全部刷新，以上2个命令
	 */
	public void refreshIccAndOperator(){		
		this.refreshIccOperatorState();
		this.refreshOperatorPanel();
	}
	/*
	 * 给工控机的命令结果，
	 * 当服务器在收到语音系统的呼入事件后，进行黑名单和警号以及密码响应。
		CALLRE:<呼入端口>,<信号>；
		信号为0－允许呼入，服务器向语音系统发送成功信息。，。
		信号为1－失败重新验证
		信号为2－禁止呼入，服务器向语音系统发送失败信息。
	 */
	public void callRe(String inPort,String result){
		String[] s = new String[]{inPort,result};
		String ip=bo.getCardPcIp();
//		String ss=commandStr.getSendMsg(ConstRuleI.CMD_CALLRE,s);
		server.
			command(bo.getCardPcIp(),
					commandStr.getSendMsg(ConstRuleI.CMD_CALLRE,s)
				);
	}
	public void printArg(String s[]){
		if(s==null)return;
		log.debug("print argument!");
		for(int i=0;i<s.length;i++){		
			log.debug(s[i]);
		}
		log.debug("print argument over!");
	}

	public void setOss(OperatorStatisticService oss) {
		this.oss = oss;
	}

	public void oocall(String op,String s) {
		// TODO Auto-generated method stub
//		if(s.equals("1"))
//			oss.addAnswerPhone(op);
//			if(s.equals("0"))
//				oss.addDisconnectPhone(op);
	}

	public void ooseat(String op,String s) {
		// TODO Auto-generated method stub
		if(s.equals("1"))
			oss.addSetting(op);
			if(s.equals("0"))
				oss.addOutSetting(op);
	}

	public void oosign(String op,String s) {
		// TODO Auto-generated method stub
		if(s.equals("1"))
			oss.addSignIn(op);
			if(s.equals("0"))
				oss.addSignOut(op);
	}
	
}
