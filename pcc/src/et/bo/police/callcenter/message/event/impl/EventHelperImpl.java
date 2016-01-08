package et.bo.police.callcenter.message.event.impl;

import java.util.List;

import et.bo.police.PoliceInfo;
import et.bo.police.callcenter.CardState;
import et.bo.police.callcenter.ConstantRule;
import et.bo.police.callcenter.message.event.EventHelperService;
import et.bo.police.callcenter.server.CommandStr;
import et.bo.police.callcenter.server.Server;

public class EventHelperImpl implements EventHelperService{
	protected CommandStr commandStr = null;
	protected Server server = null;
	protected PoliceInfo policeInfo = null;
	
	/*
	 * ˢ����ϯ����״̬
	 * 
	 */
	public void refreshOperatorPanel(){
		String s = CardState.transToOperator();
		server.command(policeInfo.getCardPcIp(), s);
	}
	/*
	 * ˢ�¹��ػ�����ϯ״̬
	 * ���ػ���icc
	 */
	public void refreshIccOperatorState(){
		List l = CardState.transToCard();
		for(int i=0;i<l.size();i++){
			String[] s = (String[])l.get(i);			
			String str=commandStr.getSendMsg(ConstantRule.CMD_SETPRT, s);
			server.command(policeInfo.getCardPcIp(), str);
		}
	}
	/*
	 * �洢cclog��policeCallin��Police_callin_info
	 */
	public void saveInfo(String id){
		
	}
}
