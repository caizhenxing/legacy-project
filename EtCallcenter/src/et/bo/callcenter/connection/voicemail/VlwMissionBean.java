/**
 * 	@(#)VlwMissionBean.java   2007-2-28 下午03:34:14
 *	 。 
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
		if(eb.getEt().equals(EventType.L_MISSION))//有任务
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
		if(eb.getEt().equals(EventType.U_OFFHOOK_BYRING))//内线振铃提机
		{
			ls.endTime();
			ls.feedPower();
			play();
		}
		if(eb.getEt().equals(EventType.L_RECEDTMF))//有dtmf
		{
			ls.stopPaly();
			parseDtmf(ls.getLi().getDtmf());//分析码
			
		}
		if(eb.getEt().equals(EventType.U_HANGUP))//内线挂机
		{
			ls.stopPaly();
			ls.getLi().setLineState(LineState.L_FREE);
			ls.getLi().setMb(null);
		}
		if(eb.getEt().equals(EventType.L_TIMEOUT))//超时未接听
		{
			ls.feedPower();
			ls.getLi().setLineState(LineState.L_FREE);
			ls.getLi().setMb(null);
		}
		if(eb.getEt().equals(EventType.L_PLAYEND))//放音完毕
		{
			ls.stopPaly();
			ls.getLi().setLineState(LineState.U_OFFHOOK);
			mvmi.subVoiceSize(Integer.toString(lineNum));
		}
	}
	/**
	 * 分析dtmf
	 * @param
	 * @version 2007-2-13
	 * @return
	 */
	private void parseDtmf(char[] dtmf)
	{
		if(dtmf.length==0)//没有dtmf，返回
			return;
		if(dtmf[0]=='*')//拨*，删除录音，收听下一段录音
		{
			mvmi.delVoice(Integer.toString(lineNum), voice);
			i--;
			
		}
		if(dtmf[0]=='#')//拨*，收听下一段录音
		{
			i--;
			
		}
		play();
	}
	
	/**
	 * 返回录音文件列表
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
