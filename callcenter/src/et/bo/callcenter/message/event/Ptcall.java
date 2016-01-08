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
	private final int NUM=3;
	@Override
	protected String execute() {
		this.eventHelper.printArg(args);
		//init
		String inPort=args[0];
		String phoneNum =args[1];
		String fuzzNo=args[2];
		String password = args[3];

		//������֤,yes;ok.no,if =3,if<3.
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
//		//1�����µ���ConnectInfo��������ˮ,������ˮ������˿ڡ����к��롢��ʼʱ��
//		ConnectInfo ci=new ConnectInfo();
//		String id=ks.getNext(ConstantsI.CC_LOG);
//		ci.setId(id);
//		ci.setInPort(inPort);
//		ci.setPhoneNum(phoneNum);
//		ci.setBeginTime(new Date());
//		//2����map��
//		ConnectInfo.getCurConn().put(inPort, ci);
//		//2.5���ݿ��Ƿ�ͨ��
//		if(BusinessObject.SUCCEED.equals((server.isDbConnect()))){
//		}else{
//			//?�Ƿ�������ݿ��¼
//			return;
//		}
//		//3��֤������,���no������CALLNO:<����˿�>��return
//		if(BusinessObject.SUCCEED.equals(pi.blacklist(phoneNum))){
//			//������һ����
//			//callOk();
//		}else{
//			//����CALLNO���
//			callNo(inPort);
//			return;
//		}
//		//4����<����>��<����>������֤�����no,����CALLNO:<����˿�>��return
//		//	�������CALLOK:<����˿�>��
//		if(BusinessObject.SUCCEED.equals(pi.isPolice(fuzzNo, password))){
//			callOk(inPort);
//		}else{
//			callNo(inPort);
//		}
//		//����ɹ���ˢ����ϯ���
	}
	private void baseClassHandle0(int i){
		String inPort=args[0];
		String phoneNum =args[1];
		String fuzzNo=args[2];
		String password = args[3];
		//^^^^^^^^ConnectInfo handle		
		//�����µ���ConnectInfo��������ˮ,������ˮ������˿ڡ����к��롢��ʼʱ��
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
		//�����µ���ConnectInfo��������ˮ,������ˮ������˿ڡ����к��롢��ʼʱ��
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
		//�����µ���ConnectInfo��������ˮ,������ˮ������˿ڡ����к��롢��ʼʱ��
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
