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
 *�������ߺ���ʱ������ϵͳ����������������¼���

*PTRING:<����˿�>,<���к���>��

*����˿�- ����ϵͳ�����߶˿ںš�
*���к���- �ú����û�ʹ�õĻ������ֻ����롣

 */
public class Ptring extends BaseEvent {
	private static Log log = LogFactory.getLog(Ptring.class);
	private KeyService ks = null;
	@Override
	protected void execute() {
		//init
		String inPort=args[0];
		String phoneNum =args[1];
		//1�����µ���ConnectInfo��������ˮ,������ˮ������˿ڡ����к��롢��ʼʱ��
		ConnectInfo ci=new ConnectInfo();
		String id=ks.getNext(Constants.CC_LOG);
		ci.setId(id);
		ci.setInPort(inPort);
		ci.setPhoneNum(phoneNum);
		ci.setBeginTime(new Date());
		//2����map��
		ConnectInfo.getCurConn().put(inPort, ci);	
		
//		2.5���ݿ��Ƿ�ͨ��
		if(PoliceInfo.SUCCEED.equals((pi.isDbConnect()))){
		}else{
			//?�Ƿ�������ݿ��¼
			return;
		}
//		3��֤������,������֤������Թ��ػ��Ŀ�����yes or no�����
		if(PoliceInfo.SUCCEED.equals(pi.blacklist(phoneNum))){
			//������һ����
			new Ptcall().callOk(inPort);
		}else{
			//����CALLNO���
			new Ptcall().callNo(inPort);
			return;
		}
		
	}
}
