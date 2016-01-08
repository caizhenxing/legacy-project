package et.bo.police.callcenter.message.event;

import java.util.Date;

import ocelot.common.key.KeyService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import et.bo.police.PoliceInfo;
import et.bo.police.callcenter.ConnectInfo;
import et.bo.police.callcenter.Constants;
import et.bo.police.callcenter.message.BaseEvent;
/**
 * 
 * @author guxiaofeng
 *当有外线呼入时，语音系统向服务器发送振铃事件。

*PTRING:<呼入端口>,<主叫号码>；

*呼入端口- 语音系统的外线端口号。
*主叫号码- 该呼入用户使用的话机或手机号码。

 */
public class Ptring extends BaseEvent {
	private static Log log = LogFactory.getLog(Ptring.class);
	private KeyService ks = null;
	@Override
	protected void execute() {
		//init
		String inPort=args[0];
		String phoneNum =args[1];
		//1产生新的类ConnectInfo，产生流水,设置流水、呼入端口、主叫号码、开始时间
		ConnectInfo ci=new ConnectInfo();
		String id=ks.getNext(Constants.CC_LOG);
		ci.setId(id);
		ci.setInPort(inPort);
		ci.setPhoneNum(phoneNum);
		ci.setBeginTime(new Date());
		//2放入map中
		ConnectInfo.getCurConn().put(inPort, ci);	
		
//		2.5数据库是否通？
		if(PoliceInfo.SUCCEED.equals((pi.isDbConnect()))){
		}else{
			//?是否产生数据库记录
			return;
		}
//		3验证黑名单,根据验证结果，对工控机的卡产生yes or no的命令。
		if(PoliceInfo.SUCCEED.equals(pi.blacklist(phoneNum))){
			//接着下一步。
			new Ptcall().callOk(inPort);
		}else{
			//发送CALLNO命令。
			new Ptcall().callNo(inPort);
			return;
		}
		
	}
}
