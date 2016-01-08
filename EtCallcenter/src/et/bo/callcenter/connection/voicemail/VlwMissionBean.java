/**
 * 	@(#)VlwMissionBean.java   2007-2-28 ����03:34:14
 *	 �� 
 *	 
 */
package et.bo.callcenter.connection.voicemail;

import java.util.ArrayList;
import java.util.List;

import et.bo.callcenter.operation.AbsMissionBean;
import et.bo.callcenter.operation.EventBean;
import et.bo.callcenter.operation.LineInfo;
import et.bo.callcenter.operation.LineService;
import et.bo.callcenter.operation.EventBean.EventType;
import et.bo.callcenter.operation.LineInfo.LineState;
import et.bo.callcenter.operation.LineInfo.LineType;
import et.bo.callcenter.plant.dongjin_c161a.impl.DongjinParameter;

 /**
 * @author zhaoyifei
 * @version 2007-2-28
 * @see
 */
public class VlwMissionBean extends AbsMissionBean {

	private ManagerVoiceMailI mvmi=null;
	private int i;
	private String voice=null;
	/* (non-Javadoc)
	 * @see et.bo.callcenter.operation.AbsMissionBean#isState(et.bo.callcenter.operation.LineInfo)
	 */
	@Override
	public boolean isState(LineInfo li) {
		// TODO Auto-generated method stub
		if(li.getLineState().equals(LineState.L_FREE))
			return true;
		return false;
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.operation.AbsMissionBean#operate(et.bo.callcenter.operation.EventBean)
	 */
	@Override
	public void operate(EventBean eb) {
		// TODO Auto-generated method stub
		if(eb.getEt().equals(EventType.L_MISSION))//������
		{
			if(!ls.getLi().getLineState().equals(LineState.L_FREE))
			{
				ls.getLi().setMb(null);
				return;
			}
			ls.getLi().setMb(this);
			ls.sendDtmf("11400");
			ls.feedRealRing(1000, 2000);
			ls.startTime(150);
			
		}
		if(eb.getEt().equals(EventType.U_OFFHOOK_BYRING))//�����������
		{
			ls.endTime();
			ls.feedPower();
			play();
		}
		if(eb.getEt().equals(EventType.L_RECEDTMF))//��dtmf
		{
			ls.stopPaly();
			parseDtmf(ls.getLi().getDtmf());//������
			
		}
		if(eb.getEt().equals(EventType.U_HANGUP))//���߹һ�
		{
			ls.stopPaly();
			ls.getLi().setLineState(LineState.L_FREE);
			ls.getLi().setMb(null);
		}
		if(eb.getEt().equals(EventType.L_TIMEOUT))//��ʱδ����
		{
			ls.feedPower();
			ls.getLi().setLineState(LineState.L_FREE);
			ls.getLi().setMb(null);
		}
		if(eb.getEt().equals(EventType.L_PLAYEND))//�������
		{
			ls.stopPaly();
			ls.getLi().setLineState(LineState.U_OFFHOOK);
			mvmi.subVoiceSize(Integer.toString(lineNum));
		}
	}
	/**
	 * ����dtmf
	 * @param
	 * @version 2007-2-13
	 * @return
	 */
	private void parseDtmf(char[] dtmf)
	{
		if(dtmf.length==0)//û��dtmf������
			return;
		if(dtmf[0]=='*')//��*��ɾ��¼����������һ��¼��
		{
			mvmi.delVoice(Integer.toString(lineNum), voice);
			i--;
			
		}
		if(dtmf[0]=='#')//��*��������һ��¼��
		{
			i--;
			
		}
		play();
	}
	
	/**
	 * ����¼���ļ��б�
	 * @param
	 * @version 2007-3-1
	 * @return
	 */
	private List<String> getRecords(String num)
	{
		return null;
		
	}

	/**
	 * @return the mvmi
	 */
	public ManagerVoiceMailI getMvmi() {
		return mvmi;
	}

	/**
	 * @param mvmi the mvmi to set
	 */
	public void setMvmi(ManagerVoiceMailI mvmi) {
		this.mvmi = mvmi;
	}
	private boolean play()
	{
		int size=mvmi.getNewVoice(Integer.toString(ls.getLi().getLineNum()));
		if(size==0)
			return false;
		i=mvmi.getVoiceList(Integer.toString(ls.getLi().getLineNum())).size()-size;
		voice=mvmi.getVoiceList(Integer.toString(ls.getLi().getLineNum())).get(i);
		List<String> files=new ArrayList<String>();
		files.add(voice);
		files.add(mvmi.getClewVoice());
		return ls.startIndexPlayFile(files);
	}
}
