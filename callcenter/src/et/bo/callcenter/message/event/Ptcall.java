package et.bo.callcenter.message.event;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import et.bo.callcenter.base.CardState;
import et.bo.callcenter.base.ConnectInfo;
import et.bo.callcenter.business.BusinessObject;
import et.bo.callcenter.message.BaseEvent;
import et.bo.common.testing.MyLog;
/**
 * @author guxiaofeng
 *当有外线呼入时，语音系统在收到警员号码和服务密码后向服务器发送该事件。
* PTCALL:<呼入端口>，<主叫号码>,<警号>，<密码>;
	呼入端口- 语音系统的外线端口号。
	主叫号码- 该呼入用户使用的话机或手机号码。
	警号-     该警员的编号。
	密码-     该警员的密码。
	注：语音系统采用一般呼入还是验证呼入由用户在语音系统中设置。
 */

public class Ptcall extends BaseEvent {
	private static Log log = LogFactory.getLog(Ptcall.class);
	private final int NUM=3;
	@Override
	protected String execute() {
		this.eventHelper.printArg(args);
		//init
		String inPort=args[0];
		String phoneNum =args[1];
		String fuzzNo=args[2];
		String password = args[3];

		//进行验证,yes;ok.no,if =3,if<3.
		ConnectInfo ci =(ConnectInfo)ConnectInfo.getCurConn().get(inPort);
		if(ci==null)return null;//exception exit.
		String id = ci.getId();
		int num =this.pi.getPcNum(id);
		num++;
		MyLog.info("this.pi.getPcNum(id)"+String.valueOf(num));
		String isPolice=pi.isPolice(fuzzNo, password);
		
		if(BusinessObject.SUCCEED.equals(isPolice)){
			baseClassHandle0(num);	
			this.eventHelper.callRe(inPort, "0");	
			return null;
		}else{
			if (num>=this.NUM){
				baseClassHandle1(num);
				this.eventHelper.callRe(inPort, "2");				
			}else{
				baseClassHandle2(num);
				this.eventHelper.callRe(inPort, "1");
			}
			return null;
		}
		//3
//		//1产生新的类ConnectInfo，产生流水,设置流水、呼入端口、主叫号码、开始时间
//		ConnectInfo ci=new ConnectInfo();
//		String id=ks.getNext(ConstantsI.CC_LOG);
//		ci.setId(id);
//		ci.setInPort(inPort);
//		ci.setPhoneNum(phoneNum);
//		ci.setBeginTime(new Date());
//		//2放入map中
//		ConnectInfo.getCurConn().put(inPort, ci);
//		//2.5数据库是否通？
//		if(BusinessObject.SUCCEED.equals((server.isDbConnect()))){
//		}else{
//			//?是否产生数据库记录
//			return;
//		}
//		//3验证黑名单,如果no，产生CALLNO:<呼入端口>；return
//		if(BusinessObject.SUCCEED.equals(pi.blacklist(phoneNum))){
//			//接着下一步。
//			//callOk();
//		}else{
//			//发送CALLNO命令。
//			callNo(inPort);
//			return;
//		}
//		//4根据<警号>，<密码>进行验证，如果no,产生CALLNO:<呼入端口>；return
//		//	否则产生CALLOK:<呼入端口>；
//		if(BusinessObject.SUCCEED.equals(pi.isPolice(fuzzNo, password))){
//			callOk(inPort);
//		}else{
//			callNo(inPort);
//		}
//		//如果成功，刷新座席面版
	}
	private void baseClassHandle0(int i){
		String inPort=args[0];
		String phoneNum =args[1];
		String fuzzNo=args[2];
		String password = args[3];
		//^^^^^^^^ConnectInfo handle		
		//产生新的类ConnectInfo，产生流水,设置流水、呼入端口、主叫号码、开始时间
		ConnectInfo ci =(ConnectInfo)ConnectInfo.getCurConn().get(inPort);
		String id = ci.getId();
		//^^^^^^^^CardState handle,nothing
		//^^^^^^^^pc handle
		this.pi.setPcFuzzNum(id, fuzzNo);
		this.pi.setPcNum(id, i);
		this.pi.setPcValidInfo(id, this.pi.VALID_OK);

	}
	private void baseClassHandle1(int i){
		log.debug("baseClassHandle1(int i) into");
//		init
		String inPort=args[0];
		String phoneNum =args[1];
		String fuzzNo=args[2];
		String password = args[3];
		//^^^^^^^^ConnectInfo handle		
		//产生新的类ConnectInfo，产生流水,设置流水、呼入端口、主叫号码、开始时间
		ConnectInfo ci =(ConnectInfo)ConnectInfo.getCurConn().get(inPort);
		String id = ci.getId();
		ci.setEndTime(new Date());
		//^^^^^^^^CardState handle
		CardState cardState1 =CardState.getCardState(inPort);
		cardState1.setNull();
		
		CardState.getOuterWaitingMap().remove(inPort);
		//^^^^^^^^pc handle
		
		this.pi.setPcFuzzNum(id, fuzzNo);
		this.pi.setPcNum(id, i);
		this.pi.setPcValidInfo(id, this.pi.VALID_FAIL);
	}
	private void baseClassHandle2(int i){
//		init
		String inPort		=args[0];
		String phoneNum 	=args[1];
		String fuzzNo		=args[2];
		String password 	= args[3];
		//^^^^^^^^ConnectInfo handle		
		//产生新的类ConnectInfo，产生流水,设置流水、呼入端口、主叫号码、开始时间
		ConnectInfo ci =(ConnectInfo)ConnectInfo.getCurConn().get(inPort);
		String id = ci.getId();
		ci.setEndTime(new Date());
		//^^^^^^^^CardState handle

		//^^^^^^^^pc handle
		this.pi.setPcFuzzNum(id, fuzzNo);
		this.pi.setPcNum(id, i);
		this.pi.setPcValidInfo(id, this.pi.VALID_FAIL);
	}
}
