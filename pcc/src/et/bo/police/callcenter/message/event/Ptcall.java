package et.bo.police.callcenter.message.event;

import java.util.Date;

import ocelot.common.key.KeyService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import et.bo.police.PoliceInfo;
import et.bo.police.callcenter.ConnectInfo;
import et.bo.police.callcenter.ConstantRule;
import et.bo.police.callcenter.Constants;
import et.bo.police.callcenter.message.BaseEvent;
/**
 * 
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
	private KeyService ks = null;
	
	@Override
	protected void execute() {
		//init
		String inPort=args[0];
		String phoneNum =args[1];
		String fuzzNo=args[2];
		String password = args[3];
		
		//1产生新的类ConnectInfo，产生流水,设置流水、呼入端口、主叫号码、开始时间
		ConnectInfo ci=new ConnectInfo();
		String id=ks.getNext(Constants.CC_LOG);
		ci.setId(id);
		ci.setInPort(inPort);
		ci.setPhoneNum(phoneNum);
		ci.setBeginTime(new Date());
		//2放入map中
		ConnectInfo.getCurConn().put(inPort, ci);
		//2.5数据库是否通？
		if(PoliceInfo.SUCCEED.equals((pi.isDbConnect()))){
		}else{
			//?是否产生数据库记录
			return;
		}
		//3验证黑名单,如果no，产生CALLNO:<呼入端口>；return
		if(PoliceInfo.SUCCEED.equals(pi.blacklist(phoneNum))){
			//接着下一步。
			//callOk();
		}else{
			//发送CALLNO命令。
			callNo(inPort);
			return;
		}
		//4根据<警号>，<密码>进行验证，如果no,产生CALLNO:<呼入端口>；return
		//	否则产生CALLOK:<呼入端口>；
		if(PoliceInfo.SUCCEED.equals(pi.isPolice(fuzzNo, password))){
			callOk(inPort);
		}else{
			callNo(inPort);
		}
		//如果成功，刷新座席面版
	}
	public void callOk(String inPort){
		String[] s = new String[]{inPort};
		server.
			command(pi.getCardPcIp(), 
				cs.getSendMsg(ConstantRule.CMD_CALLOK,s)
				);
	}
	public void callNo(String inPort){
		String[] s = new String[]{inPort};
		server.
		command(pi.getCardPcIp(), 
				cs.getSendMsg(ConstantRule.CMD_CALLNO,s)
				);
	}

}
