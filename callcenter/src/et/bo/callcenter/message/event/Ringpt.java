package et.bo.callcenter.message.event;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import et.bo.callcenter.ConstRuleI;
import et.bo.callcenter.base.CardInfo;
import et.bo.callcenter.base.CardState;
import et.bo.callcenter.base.OperatorState;
import et.bo.callcenter.message.BaseEvent;
/**
 * 
 * @author guxiaofeng
 *������ϵͳ��ĳ����ϯ�ֻ�����ʱ������������������¼���
RINGPT:<����˿�>,<��ϯ�˿�>,<��ϯ�߼���>,<���к���>,
 */

public class Ringpt extends BaseEvent {
	
	private static Log log = LogFactory.getLog(Ringpt.class);
	@Override
	protected String execute() {
		//init
		String inPort = args[0];
		String operatorPort = args[1];
		String logicPort = args[2];
		String phoneNum = args[3];
		
		CardInfo cardInfo1=CardInfo.getCardInfo(operatorPort);
		String ip = cardInfo1.getIp();

		//1������ϯ���з������������
		String sCmd1 = cs.getSendMsg(ConstRuleI.CMD_OORING, 
				new String[]{"1"});
		//
		//ͨ����ϯ�˿ڷ�����ϯip��
		CardInfo ci=CardInfo.getCardInfo(operatorPort);
		ip=ci.getIp();
		System.out.println(sCmd1);
		System.out.println(ip);
		server.command(ip, sCmd1);
		
		//2
		CardState cardState1 =CardState.getCardState(inPort);
		cardState1.setCurTime(new Date());
		cardState1.setState(cardState1.OUTER_BUSY);
		
		//
		CardState cardState2 =CardState.getCardState(operatorPort);
		cardState1.setCurTime(new Date());
		////add at 31
		CardInfo cardInfo2=CardInfo.getCardInfo(operatorPort);
		String ip2 = cardInfo2.getIp();
		OperatorState os11 = OperatorState.getOperatorState(ip2);
		String on = os11.getOperatorNum();
		cardState2.setOperatorNum(on);
		CardState.setInnerActiveMap(cardState2);	
		////add at 31
		cardState1.setState(cardState2.OUTER_BUSY);
		
		//3����ˢ����ϯ������̡�
		this.eventHelper.refreshOperatorPanel();
		return null;
	}
	
}
