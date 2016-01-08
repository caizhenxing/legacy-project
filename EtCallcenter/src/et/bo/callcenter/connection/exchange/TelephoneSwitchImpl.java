/**
 * 	@(#)TelephoneSwitchImpl.java   2007-1-29 ����03:38:53
 *	 �� 
 *	 
 */
package et.bo.callcenter.connection.exchange;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import et.bo.callcenter.operation.AbsMissionBean;
import et.bo.callcenter.operation.EventBean;
import et.bo.callcenter.operation.LineService;
import et.bo.callcenter.operation.LinkInfo;
import et.bo.callcenter.operation.EventBean.EventType;
import et.bo.callcenter.operation.LineInfo.LineState;
import et.bo.callcenter.operation.LineInfo.LineType;
import et.bo.callcenter.operation.LinkInfo.LinkType;
import et.bo.callcenter.plant.dongjin_c161a.impl.DongjinParameter;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.container.SpringRunningContainer;

 /**
 * @author zhaoyifei
 * @version 2007-1-29
 * @see
 */
public class TelephoneSwitchImpl extends AbsTelephoneSwitch {
	Map<String,LineService> tels=new HashMap<String,LineService>();

	String welcomeFile;
	String recordFile;
	String recordNum;
	public String getRecordFile() {
		return recordFile;
	}
	public void setRecordFile(String recordFile) {
		this.recordFile = recordFile;
	}
	public TelephoneSwitchImpl()
	{
		super();
		
	}
	/**
	 * @return the welcomeFile
	 */
	public String getWelcomeFile() {
		return welcomeFile;
	}
	/**
	 * @param welcomeFile the welcomeFile to set
	 */
	public void setWelcomeFile(String welcomeFile) {
		this.welcomeFile = welcomeFile;
	}
	/* (non-Javadoc)
	 * @see et.bo.callcenter.connection.exchange.AbsTelephoneSwitch#operate(et.bo.callcenter.operation.EventBean)
	 */
	@Override
	protected void operate(EventBean eb) {
		// TODO Auto-generated method stub
		if(l.get(eb.getLineNum()).getLi().getMb()!=null)//����˿���ĳ��������ִ�У������������¼��ӿ�
		{
			AbsMissionBean mb=l.get(eb.getLineNum()).getLi().getMb();
			mb.operate(eb);
			return;
		}
		if(eb.getEt().equals(EventType.L_MISSION))//������
		{
			AbsMissionBean mb=(AbsMissionBean)eb.getParam("mission");
			LineService ls=l.get(eb.getLineNum());
			if(mb.isState(ls.getLi()))
			{
				mb.setLs(ls);
				mb.operate(eb);
				ls.getLi().setMb(mb);
			}
		}
		if(eb.getEt().equals(EventType.T_RING))//��������
		{
			LineService ls=l.get(eb.getLineNum());
			ls.offHook();//�ӵ绰
			ls.startPlayFile(welcomeFile);//���Ż�ӭ����
		}
		if(eb.getEt().equals(EventType.T_SIG))//�ź������������ͣ����忴����
		{
			//����
		}
		if(eb.getEt().equals(EventType.T_POLARITY))//���Է�ת
		{
//			����
		}
		if(eb.getEt().equals(EventType.T_DIAL))//���߲���
		{
//			����
		}
		if(eb.getEt().equals(EventType.T_CONNECT))//���߲�ͨ
		{
//			����
		}
		if(eb.getEt().equals(EventType.T_HANGUP))//���߲����ĵ绰�һ�
		{
			LineService lis=l.get(eb.getLineNum());
			if(waitLinks.containsValue(lis))//����в��ŵ绰��ֹͣ���塣���״̬
			{
				LineService lsi=null;
				Iterator<LineService> i=waitLinks.keySet().iterator();
				while(i.hasNext())
				{
					lsi=i.next();
					if(waitLinks.get(lsi).equals(lis))
					{
						break;
					}
				}
				lsi.feedPower();
				lsi.endTime();
				lsi.getLi().setLineState(LineState.L_FREE);
				waitLinks.remove(lsi);
			}
			if(ls.contain(lis))//�������ͨ�����������
			{
				LinkInfo li=ls.clearLink(lis);
				LineService ls1=li.getLs1();
				LineService ls2=li.getLs2();
				LineService ls3=li.getLs3();
				if(!LineState.L_FREE.equals(ls1.getLi().getLineState()))
				{
					if(LineType.OUT_LINE.equals(ls1.getLi().getLineType()))
					ls1.hangUp();
					
				}
				if(!LineState.L_FREE.equals(ls2.getLi().getLineState()))
				{
					if(LineType.OUT_LINE.equals(ls2.getLi().getLineType()))
						ls2.hangUp();
						
				}
				if(li.getLt().equals(LinkType.L_THREE))
				if(!LineState.L_FREE.equals(ls3.getLi().getLineState()))
				{
					if(LineType.OUT_LINE.equals(ls3.getLi().getLineType()))
						ls3.hangUp();
						
				}
			}
			/**
			 * �������䣭¼��ģ���޸�
			 * ע��һ��
			 */
//			if(lis.getLi().getSubState().equals("record"))//�������¼����ֹͣ¼�������״̬
//			{
//				lis.stopRecordFile();
//				lis.getLi().setSubState("");
//				
//			}
			lis.hangUp();//���߹һ�
		}
		if(eb.getEt().equals(EventType.U_OFFHOOK))//�������
		{
			LineService ls=l.get(eb.getLineNum());
			if(ls.getLi().getLineState().equals(LineState.U_OFFHOOK))//����״̬
			ls.startPlaySignal(DongjinParameter.SIG_DIALTONE);//���Ų�����
			
		}
		if(eb.getEt().equals(EventType.U_OFFHOOK_BYRING))//�����������
		{
			LineService lis=l.get(eb.getLineNum());
			lis.endTime();
			ls.setLink(lis, waitLinks.get(lis));//��ͨ�绰
			waitLinks.remove(lis);
		}
		if(eb.getEt().equals(EventType.U_HANGUP))//���߹һ�
		{
			LineService lis=l.get(eb.getLineNum());
			if(waitLinks.containsValue(lis))//����ȴ�ͨ������ֹͣ���壬���״̬
			{
				LineService lsi=null;
				Iterator<LineService> i=waitLinks.keySet().iterator();
				while(i.hasNext())
				{
					lsi=i.next();
					if(waitLinks.get(lsi).equals(lis))
					{
						break;
					}
				}
				lsi.feedPower();
				lsi.endTime();
				lsi.getLi().setLineState(LineState.L_FREE);
				waitLinks.remove(lsi);
			}
			if(ls.contain(lis))//ͨ���У�������ӡ�
			{
				LinkInfo li=ls.clearLink(lis);
				LineService ls1=li.getLs1();
				LineService ls2=li.getLs2();
				LineService ls3=li.getLs3();
				if(!LineState.L_FREE.equals(ls1.getLi().getLineState()))
				{
					if(LineType.OUT_LINE.equals(ls1.getLi().getLineType()))
					ls1.hangUp();
					
				}
				if(!LineState.L_FREE.equals(ls2.getLi().getLineState()))
				{
					if(LineType.OUT_LINE.equals(ls2.getLi().getLineType()))
						ls2.hangUp();
						
				}
				if(li.getLt().equals(LinkType.L_THREE))
				if(!LineState.L_FREE.equals(ls3.getLi().getLineState()))
				{
					if(LineType.OUT_LINE.equals(ls3.getLi().getLineType()))
						ls3.hangUp();
						
				}
			}
			/**
			 * �������䣭¼��ģ���޸�
			 * ע��һ��
			 */
//			if(lis.getLi().getSubState().equals("record"))//¼���У�ֹͣ¼�������״̬
//			{
//				lis.stopRecordFile();
//				lis.getLi().setSubState("");
//			}
		}
		if(eb.getEt().equals(EventType.L_RECEDTMF))//��dtmf
		{
			LineService ls=l.get(eb.getLineNum());
			ls.receiveDtmf();//����
			if(!ls.getLi().getLineState().equals(LineState.L_FREE)&&!ls.getLi().getLineState().equals(LineState.L_LINK))
				parseDtmf(ls.getLi().getDtmf(),ls);//������
		}
		if(eb.getEt().equals(EventType.L_SENDDTMFEND))//�������
		{
			//����
		}
		if(eb.getEt().equals(EventType.L_RECEFSK))//��fsk
		{
			//����
		}
		if(eb.getEt().equals(EventType.L_PLAYEND))//��������
		{
			
			LineService ls=l.get(eb.getLineNum());
			ls.stopPaly();//ֹͣ����
			if(ls.getLi().getLineType().equals(LineType.OUT_LINE))
				ls.getLi().setLineState(LineState.T_OFFHOOK);//����״̬
			/**
			 * �������䣭¼��ģ���޸�
			 * ע��һ��
			 */
//			if(ls.getLi().getSubState().equals("waitrecord"))//����ȴ�¼��
//			{
//				String fileName="d:/zhaoyifei/"+TimeUtil.getNowSTime()+".record";
//				ls.startRecordFile(fileName);
//				mvmi.addVoice(Integer.toString(ls.getLineNum()),fileName);
//				ls.getLi().setSubState("record");//��ʼ¼��
//				
//				
//			}
		}
		
		if(eb.getEt().equals(EventType.U_HOOKFLASH))//�Ĳ�
		{
			//����
		}
		
		/**
		 * �������䣭¼��ģ���޸�
		 * ע��һ��
		 */
		if(eb.getEt().equals(EventType.L_TIMEOUT))//��ʱ
		{
			LineService lis=l.get(eb.getLineNum());
			LineService lis1=waitLinks.get(lis);
//			lis.feedPower();//ֹͣ����
			
//			lis1.startPlaySignal(DongjinParameter.SIG_STOP);//ֹͣ��������
//			lis1.startPlayFile(recordFile);//���ŵȴ�¼������
//			lis1.getLi().setSubState("waitrecord");//����״̬
			AbsMissionBean mb=(AbsMissionBean)SpringRunningContainer.getInstance().getBean("VrMissionBean");
			
			mb.setLs(lis);
			mb.setParam("recordLine",lis1);
			mb.setParam("ringLs",lis);
			EventBean eb1=new EventBean();
			eb1.setEt(EventType.L_MISSION);
			eb1.setLineNum(lis1.getLineNum());
			eb1.setParam("mission", mb);
			
			q.add(eb1);
		}
	}
	/**
	 * ����dtmf
	 * @param
	 * @version 2007-2-13
	 * @return
	 */
	private void parseDtmf(char[] dtmf,LineService ls1)
	{
		if(dtmf.length==0)//û��dtmf������
			return;
		if(dtmf[0]=='1')//��1���ҵ�������ͨ
		{
			if(ls1.getLi().getLineType().equals(LineType.OUT_LINE))//��������ߵ绰�����
			{
				//ls1.startPlaySignal(DongjinParameter.SIG_BUSY1);
				//
				return ;
			}
			boolean hasfree=false;
			//��һ��������������
			for(LineService lss:l)//���ߵ绰�Ϳ�ʼ�ҿ�������
			{
				if(lss.getLi().getLineType().equals(LineType.OUT_LINE)&&lss.getLi().getLineState().equals(LineState.L_FREE))
				{
					lss.offHook();
					ls.setLink(ls1, lss);
					//ls1.startPlaySignal(DongjinParameter.SIG_DIALTONE);
					hasfree=true;
					break;
				}
				
			}
			if(!hasfree)//���û�п���������æ��
				ls1.startPlaySignal(DongjinParameter.SIG_BUSY1);
			return ;
		}
		if(tels.containsKey(new String(dtmf)))//��������ж�Ӧ�绰���룬����������
		{
			if(tels.get(new String(dtmf)).getLi().getLineState().equals(LineState.L_FREE))//������߿��У����壬��ʼ��ʱ
			{
				//����������
				tels.get(new String(dtmf)).feedRealRing();
				tels.get(new String(dtmf)).startTime(10*15);
				waitLinks.put(tels.get(new String(dtmf)),ls1 );
				if(ls1.getLi().getLineType().equals(LineType.IN_LINE))
				ls1.startPlaySignal(DongjinParameter.SIG_RINGBACK);
				else
					ls1.startPlayFile(welcomeFile);
				
			}
			else//����æ��
			{
				if(ls1.getLi().getLineType().equals(LineType.IN_LINE))
				ls1.startPlaySignal(DongjinParameter.SIG_BUSY1);
				else
				{
					ls1.clearDtmf();
					ls1.startPlayFile(welcomeFile);
				}
			}
			ls1.clearDtmf();
		}
	}
	/**
	 * ��ʼ���绰����
	 * @param
	 * @version 2007-2-13
	 * @return
	 */
	@Override
	protected void initSub() {
		// TODO Auto-generated method stub
		for(LineService lss:l)
		{
			StringBuilder sb=new StringBuilder();
			sb.append("8");
			if(lss.getLi().getLineNum()<10)
				sb.append("0");
			sb.append(Integer.toString(lss.getLi().getLineNum()));
			
			tels.put(sb.toString(),lss);
		}
	}
	
}
