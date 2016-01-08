/**
 * 	@(#)VrMissionBean.java   2007-3-14 ����10:30:14
 *	 �� 
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
			
			lis.feedPower();//ֹͣ����
			lis.getLi().setLineState(LineState.L_FREE);
			recordLine.startPlaySignal(DongjinParameter.SIG_STOP);//ֹͣ��������
			recordLine.startPlayFile(recordFile);//���ŵȴ�¼������
			recordLine.getLi().setSubState("waitrecord");//����״̬
		}
		if(eb.getEt().equals(EventType.L_PLAYEND))//��������
		{
			if(ls.getLi().getSubState().equals("waitrecord"))//����ȴ�¼��
			{
				String fileName="d:/zhaoyifei/"+TimeUtil.getNowSTime()+".record";
				ls.startRecordFile(fileName);
				mvmi.addVoice(Integer.toString(lis.getLineNum()),fileName);
				ls.getLi().setSubState("record");//��ʼ¼��
				ls.getLi().setLineState(LineState.L_RECORD);
			}
		}
		if(eb.getEt().equals(EventType.U_HANGUP))//���߹һ�
		{
			if(recordLine.getLi().getSubState().equals("record"))//¼���У�ֹͣ¼�������״̬
			{
				recordLine.stopRecordFile();
				recordLine.getLi().setSubState("");
			}
			recordLine.getLi().setMb(null);
		}
		if(eb.getEt().equals(EventType.T_HANGUP))//���߲����ĵ绰�һ�
		{
			if(recordLine.getLi().getSubState().equals("record"))//�������¼����ֹͣ¼�������״̬
			{
				recordLine.stopRecordFile();
				recordLine.getLi().setSubState("");
			}
			recordLine.hangUp();//���߹һ�
			recordLine.getLi().setMb(null);
		}
	}

}
