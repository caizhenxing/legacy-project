package et.bo.callcenter.message.event;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import et.bo.callcenter.ConstRuleI;
import et.bo.callcenter.base.CardInfo;
import et.bo.callcenter.base.CardState;
import et.bo.callcenter.base.ConnectInfo;
import et.bo.callcenter.message.BaseEvent;
/**
 * 
 * @author guxiaofeng
 *����ϯ�ֻ��������û�ͨ������������ϵͳ�����������ֹͣ�¼���
PTSTOP��<����˿�>��<��ϯ�˿�>��
���������ǰ����ϯ�˿���255�ַ�����
 */
public class Ptstop extends BaseEvent {
	private static Log log = LogFactory.getLog(Ptstop.class);
	@Override
	protected String execute(){
		//init
		String inPort = args[0];
		String operatorPort = args[1];		
		
//		1������ϯֹͣ��������
		CardInfo cardInfo1=CardInfo.getCardInfo(operatorPort);
		String ip = cardInfo1.getIp();
		String sCmd1 = cs.getSendMsg(ConstRuleI.CMD_OORING, 
				new String[]{"0"});
		server.command(ip, sCmd1);
//		1ˢ��CardState״̬
		baseClassHandle0();
		//
		//2����ˢ����ϯ������̡�
		this.eventHelper.refreshOperatorPanel();
		//
		return null;
	}
	private void baseClassHandle0(){
		//init
		String inPort=args[0];
		String operatorPort =args[1];
		//^^^^^^^^ConnectInfo handle		
		//����ConnectInfo��
		ConnectInfo ci =(ConnectInfo)ConnectInfo.getCurConn().get(inPort);
		String id = ci.getId();
		ci.setEndTime(new Date());
		
		//^^^^^^^^CardState handle
		CardState cardState1 =CardState.getCardState(inPort);
		cardState1.setNull();
		CardState.getOuterWaitingMap().put(inPort, cardState1);
		
		CardState cardState2 =CardState.getCardState(operatorPort);
		cardState2.setCurTime(new Date());
		cardState2.setLinkPort(null);
		cardState2.setPhoneNum(null);
		cardState2.setState(cardState2.INNER_SIGNIN);
		
//		cardState2.removeInnerActive(cardState2);
		//^^^^^^^^pc handle
		/*
		 * need to add below act&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
		 */
		this.pi.saveAll(id);
		this.pi.insertValue(id);
		
	}
}