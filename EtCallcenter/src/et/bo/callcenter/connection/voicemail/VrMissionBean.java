/**
 * 	@(#)VrMissionBean.java   2007-3-14 上午10:30:14
 *	 。 
 *	 
 */
package et.bo.callcenter.connection.voicemail;

import et.bo.callcenter.operation.AbsMissionBean;
import et.bo.callcenter.operation.EventBean;
import et.bo.callcenter.operation.LineInfo;
import et.bo.callcenter.operation.LineService;
import et.bo.callcenter.operation.EventBean.EventType;
import et.bo.callcenter.operation.LineInfo.LineState;
import et.bo.callcenter.operation.LineInfo.LineType;
import et.bo.callcenter.plant.dongjin_c161a.impl.DongjinParameter;
import excellence.common.util.time.TimeUtil;

 /**
 * @author zhaoyifei
 * @version 2007-3-14
 * @see
 */
public class VrMissionBean extends AbsMissionBean {

	
	private LineService recordLine;
	private String recordFile=null;
	private ManagerVoiceMailI mvmi=null;
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

	/**
	 * @return the recordFile
	 */
	public String getRecordFile() {
		return recordFile;
	}

	/**
	 * @param recordFile the recordFile to set
	 */
	public void setRecordFile(String recordFile) {
		this.recordFile = recordFile;
	}

	

	

	/* (non-Javadoc)
	 * @see et.bo.callcenter.operation.AbsMissionBean#isState(et.bo.callcenter.operation.LineInfo)
	 */
	@Override
	public boolean isState(LineInfo li) {
		// TODO Auto-generated method stub
		return true;
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.operation.AbsMissionBean#operate(et.bo.callcenter.operation.EventBean)
	 */
	@Override
	public void operate(EventBean eb) {
		// TODO Auto-generated method stub
		recordLine=(LineService)params.get("recordLine");
		LineService lis=(LineService)params.get("ringLs");
		if(eb.getEt().equals(EventType.L_MISSION))
		{
			
			lis.feedPower();//停止振铃
			lis.getLi().setLineState(LineState.L_FREE);
			recordLine.startPlaySignal(DongjinParameter.SIG_STOP);//停止播回铃音
			recordLine.startPlayFile(recordFile);//播放等待录音音乐
			recordLine.getLi().setSubState("waitrecord");//设置状态
		}
		if(eb.getEt().equals(EventType.L_PLAYEND))//放音结束
		{
			if(ls.getLi().getSubState().equals("waitrecord"))//如果等待录音
			{
				String fileName="d:/zhaoyifei/"+TimeUtil.getNowSTime()+".record";
				ls.startRecordFile(fileName);
				mvmi.addVoice(Integer.toString(lis.getLineNum()),fileName);
				ls.getLi().setSubState("record");//开始录音
				ls.getLi().setLineState(LineState.L_RECORD);
			}
		}
		if(eb.getEt().equals(EventType.U_HANGUP))//内线挂机
		{
			if(recordLine.getLi().getSubState().equals("record"))//录音中，停止录音，清除状态
			{
				recordLine.stopRecordFile();
				recordLine.getLi().setSubState("");
			}
			recordLine.getLi().setMb(null);
		}
		if(eb.getEt().equals(EventType.T_HANGUP))//外线拨出的电话挂机
		{
			if(recordLine.getLi().getSubState().equals("record"))//如果正在录音，停止录音，清除状态
			{
				recordLine.stopRecordFile();
				recordLine.getLi().setSubState("");
			}
			recordLine.hangUp();//外线挂机
			recordLine.getLi().setMb(null);
		}
	}

}
