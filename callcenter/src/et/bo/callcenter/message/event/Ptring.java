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
 *�������ߺ���ʱ������ϵͳ����������������¼���
 *PTRING:<����˿�>,<���к���>��
 *����˿�- ����ϵͳ�����߶˿ںš�
 *���к���- �ú����û�ʹ�õĻ������ֻ����롣
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
		
//		2.5���ݿ��Ƿ�ͨ��
		if(BusinessObject.SUCCEED.equals((server.isDbConnect()))){
			log.debug("in to 2.5���ݿ��Ƿ�ͨ yes");
		}else{
			//?�Ƿ�������ݿ��¼
			log.debug("in to 2.5���ݿ��Ƿ�ͨ no");
			return null;
		}
		
		String tmp=pi.blacklist(phoneNum);
//		3��֤������,������֤������Թ��ػ��Ŀ�����yes or no�����
		if(BusinessObject.SUCCEED.equals(pi.blacklist(phoneNum))){
			//������һ�������о�Ա��֤��
			this.eventHelper.callRe(inPort, "0");
		}else{
			baseClassHandle1();
			//����CALLNO����˳�
			this.eventHelper.callRe(inPort, "2");
			baseClassHandle1();
		}
		return null;
	}
	/*
	 * �绰��������ĳ�ʼ��
	 */
	private void baseClassHandle0(){
		//		init
		String inPort=args[0];
		String phoneNum =args[1];
		//^^^^^^^^ConnectInfo handle		
		//�����µ���ConnectInfo��������ˮ,������ˮ������˿ڡ����к��롢��ʼʱ��
		ConnectInfo ci=new ConnectInfo();
		String id=ks.getNext(ConstantsI.CC_LOG);
		
		ci.setId(id);
		ci.setInPort(inPort);
		ci.setPhoneNum(phoneNum);
		ci.setBeginTime(new Date());
		ConnectInfo.getCurConn().put(inPort, ci);
		//^^^^^^^^CardState handle���϶�������ģ�顣
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
	 * �����������Լ��˳���
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
		
//		^^^^^^^^CardState handle��
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
