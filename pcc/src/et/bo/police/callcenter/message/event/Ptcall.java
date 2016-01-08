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
 *�������ߺ���ʱ������ϵͳ���յ���Ա����ͷ������������������͸��¼���
* PTCALL:<����˿�>��<���к���>,<����>��<����>;
	����˿�- ����ϵͳ�����߶˿ںš�
	���к���- �ú����û�ʹ�õĻ������ֻ����롣
	����-     �þ�Ա�ı�š�
	����-     �þ�Ա�����롣
	ע������ϵͳ����һ����뻹����֤�������û�������ϵͳ�����á�
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
		
		//1�����µ���ConnectInfo��������ˮ,������ˮ������˿ڡ����к��롢��ʼʱ��
		ConnectInfo ci=new ConnectInfo();
		String id=ks.getNext(Constants.CC_LOG);
		ci.setId(id);
		ci.setInPort(inPort);
		ci.setPhoneNum(phoneNum);
		ci.setBeginTime(new Date());
		//2����map��
		ConnectInfo.getCurConn().put(inPort, ci);
		//2.5���ݿ��Ƿ�ͨ��
		if(PoliceInfo.SUCCEED.equals((pi.isDbConnect()))){
		}else{
			//?�Ƿ�������ݿ��¼
			return;
		}
		//3��֤������,���no������CALLNO:<����˿�>��return
		if(PoliceInfo.SUCCEED.equals(pi.blacklist(phoneNum))){
			//������һ����
			//callOk();
		}else{
			//����CALLNO���
			callNo(inPort);
			return;
		}
		//4����<����>��<����>������֤�����no,����CALLNO:<����˿�>��return
		//	�������CALLOK:<����˿�>��
		if(PoliceInfo.SUCCEED.equals(pi.isPolice(fuzzNo, password))){
			callOk(inPort);
		}else{
			callNo(inPort);
		}
		//����ɹ���ˢ����ϯ���
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
