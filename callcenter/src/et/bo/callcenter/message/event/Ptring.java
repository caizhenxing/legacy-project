package et.bo.callcenter.message.event;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import et.bo.callcenter.ConstantsI;
import et.bo.callcenter.base.CardState;
import et.bo.callcenter.base.ConnectInfo;
import et.bo.callcenter.business.BusinessObject;
import et.bo.callcenter.message.BaseEvent;
import excellence.common.key.KeyService;
import excellence.framework.base.container.SpringRunningContainer;
/**
 * 
 * @author guxiaofeng
 *当有外线呼入时，语音系统向服务器发送振铃事件。
 *PTRING:<呼入端口>,<主叫号码>；
 *呼入端口- 语音系统的外线端口号。
 *主叫号码- 该呼入用户使用的话机或手机号码。
 */
public class Ptring extends BaseEvent {
	//
	private static Log log = LogFactory.getLog(Ptring.class);
	private KeyService ks =
		(KeyService)SpringRunningContainer.getInstance().getBean("KeyService");;
	@Override
	protected String execute() {
		this.eventHelper.printArg(args);
		//init
		String inPort=args[0];
		String phoneNum =args[1];
		//
		baseClassHandle0();
		
//		2.5数据库是否通？
		if(BusinessObject.SUCCEED.equals((server.isDbConnect()))){
			log.debug("in to 2.5数据库是否通 yes");
		}else{
			//?是否产生数据库记录
			log.debug("in to 2.5数据库是否通 no");
			return null;
		}
		
		String tmp=pi.blacklist(phoneNum);
//		3验证黑名单,根据验证结果，对工控机的卡产生yes or no的命令。
		if(BusinessObject.SUCCEED.equals(pi.blacklist(phoneNum))){
			//接着下一步。进行警员验证。
			this.eventHelper.callRe(inPort, "0");
		}else{
			baseClassHandle1();
			//发送CALLNO命令。退出
			this.eventHelper.callRe(inPort, "2");
			baseClassHandle1();
		}
		return null;
	}
	/*
	 * 电话呼入进来的初始化
	 */
	private void baseClassHandle0(){
		//		init
		String inPort=args[0];
		String phoneNum =args[1];
		//^^^^^^^^ConnectInfo handle		
		//产生新的类ConnectInfo，产生流水,设置流水、呼入端口、主叫号码、开始时间
		ConnectInfo ci=new ConnectInfo();
		String id=ks.getNext(ConstantsI.CC_LOG);
		
		ci.setId(id);
		ci.setInPort(inPort);
		ci.setPhoneNum(phoneNum);
		ci.setBeginTime(new Date());
		ConnectInfo.getCurConn().put(inPort, ci);
		//^^^^^^^^CardState handle，肯定是外线模块。
		CardState cardState1 =CardState.getCardState(inPort);
		cardState1.setNull();
		cardState1.setCurTime(new Date());
		cardState1.setState(cardState1.OUTER_BUSY);
		cardState1.setPhoneNum(phoneNum);
		CardState.getOuterWaitingMap().put(inPort, cardState1);
		//^^^^^^^^pc handle
		this.pi.setPcInstance(id);
	}
	/*
	 * 黑名单处理以及退出。
	 */
	private void baseClassHandle1(){
		log.debug("Ptring into baseClassHandle1-");
		//init
		String inPort=args[0];
		String phoneNum =args[1];
//		^^^^^^^^ConnectInfo handle
		ConnectInfo ci =(ConnectInfo)ConnectInfo.getCurConn().get(inPort);
		String id = ci.getId();
		ci.setEndTime(new Date());
		
//		^^^^^^^^CardState handle，
		CardState cardState1 =CardState.getCardState(inPort);
		cardState1.setNull();
		CardState.getOuterWaitingMap().remove(inPort);
//		^^^^^^^^pc handle
		this.pi.setPcValidInfo(id, this.pi.VALID_BLACKLIST);
//		this.pi.savePc(id);
	}
	public KeyService getKs() {
		return ks;
	}
	public void setKs(KeyService ks) {
		this.ks = ks;
	}
}
