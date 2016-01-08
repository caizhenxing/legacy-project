package et.bo.callcenter.message.event;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import et.bo.callcenter.base.CardInfo;
import et.bo.callcenter.message.BaseEvent;
/**
 * 
 * @author guxiaofeng
 *当语音系统与服务器连接后，需要向服务器发送语音系统的硬件配置状态。
一次发送所有的卡的信息，格式是
物理端口#逻辑端口#端口类型#状态! 物理端口#逻辑端口#端口类型#状态!。。。。。。。。。。
PTTYPE:字符串；
物理端口-  语音系统的物理端口号。
逻辑端口-  语音系统的逻辑端口。
类型-  该端口的类型。0-内线端口；1-外线端口。
状态-  该端口的当前状态。0-未安装；1-损坏；2-关闭；3-正常。
 */
public class Pttype extends BaseEvent {
	private static Log log = LogFactory.getLog(Pttype.class);
//	private CardInfo cardInfo=null;
	private final String DELIM1="#";
	private final String DELIM2="!";
	@Override
	protected String execute() {
		//init		
		String sTmp =args[0];
		//将状态设置到map中，实时反映相关的当前的状态。
		CardInfo.initMap(parse(sTmp));
		/*
		 * 刷新座席对于工控机
		 */
		this.eventHelper.refreshIccOperatorState();
		return null;
	}
	
	/*
	 * s is:物理端口#逻辑端口#端口类型#状态!物理端口#逻辑端口#端口类型#状态!
	 * 多个类似的东东
	 */
	private List parse(String s){
		log.debug("into parase!");
		List list = new ArrayList();
		if(null==s)return list;
		//need validate;
		//如果验证合格进行如下：
		String[] sPort=s.split(this.DELIM2);
		for(int i=0;i<sPort.length;i++){
			String[] s1 =sPort[i].split(this.DELIM1);
			log.debug("guxf001");
			log.debug(sPort[i]);
			CardInfo ci = new CardInfo();
			log.debug(ci.getIp());
			ci.setPhysicsPort(s1[0]);
			ci.setLogicPort(s1[1]);
			ci.setPortType(s1[2]);
			ci.setState(s1[3]);
			list.add(ci);
		}
		//
		return list;
	}
	
//	public static void main(String[] args) {
//		new MyLog();
//		String s="0#0#1#3!1#0#1#3!2#0#1#3!3#0#1#3!4#0#1#3!5#0#1#3!6#1#0#3!7#2#0#3!8#0#0#0!9#0#0#0!10#0#0#0!11#0#0#0!12#0#0#0!13#0#0#0!14#0#0#0!15#0#0#0!";
//		Pttype p=new Pttype();
//		p.parse(s);
//	}
	
}
