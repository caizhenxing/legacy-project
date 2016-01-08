/**
 * 	@(#)InitDongjin.java   2007-1-26 ����09:16:56
 *	 �� 
 *	 
 */
package et.bo.callcenter.operation.dongjin;

import et.bo.callcenter.operation.InitInfo;
import et.bo.callcenter.operation.InitService;
import et.bo.callcenter.operation.LineInfo;
import et.bo.callcenter.operation.SysInfo;
import et.bo.callcenter.operation.LineInfo.LineState;
import et.bo.callcenter.operation.LineInfo.LineType;
import et.bo.callcenter.plant.dongjin_c161a.IDongjinConf;
import et.bo.callcenter.plant.dongjin_c161a.IDongjinInHook;
import et.bo.callcenter.plant.dongjin_c161a.IDongjinInit;
import et.bo.callcenter.plant.dongjin_c161a.IDongjinRecord;
import et.bo.callcenter.plant.dongjin_c161a.IDongjinVoice;
import et.bo.callcenter.plant.dongjin_c161a.impl.DongjinParameter;

 /**
 * @author zhaoyifei
 * @version 2007-1-26
 * @see
 */
public class InitDongjin implements InitService {

	private IDongjinInit idi=null;
	private IDongjinRecord idr=null;
	private IDongjinVoice idv=null;
	private IDongjinConf idc=null;
	private IDongjinInHook idih=null;
	SysInfo si=new SysInfo();
	/* (non-Javadoc)
	 * @see et.bo.callcenter.operation.InitService#assist()
	 */
	public void assist() {
		// TODO Auto-generated method stub
		AssistThread at=new AssistThread();
		at.start();
	}
	class AssistThread extends Thread
	{

		/* (non-Javadoc)
		 * @see java.lang.Thread#run()
		 */
		@Override
		public void run() {
			// TODO Auto-generated method stub
			while(true)
			{
				idi.pushPlay();
				idih.feedSigFunc();
				try {
					this.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
	/* (non-Javadoc)
	 * @see et.bo.callcenter.operation.InitService#free()
	 */
	public void free() {
		// TODO Auto-generated method stub
		idc.dConfDisableConfCard();
		idi.disableCard();
		idi.freeDRV();
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.operation.InitService#getSysInfo()
	 */
	public SysInfo getSysInfo() {
		// TODO Auto-generated method stub
		si.setId(Integer.toString(idi.newReadPass(0)));
		si.setSupportCalerId(idi.isSupportCallerID());
		return si;
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.operation.InitService#init(et.bo.callcenter.operation.InitInfo)
	 */
	public boolean init(InitInfo ip) {
		// TODO Auto-generated method stub
		int driverOpenFlag=idi.loadDRV();//����������
		if(driverOpenFlag!=0)//������ɹ�������
			return false;
		int totalLine=idi.checkValidCh();//�õ��˿�����
		si.setLineCount(totalLine);
		if(idi.enableCard(totalLine,ip.getLineBuf())!=0)//��ʼ����������������ɹ��ͷŲ�����
		{
			idi.freeDRV();
			return false;
		}
		idr.vRSetRefreshSize(ip.getRecordBuf());//��ʼ��¼��������
		idi.setBusyPara(ip.getBusyPa());//����æ������
		for(int i=0;i<totalLine;i++)//ȡ�����ж˿���Ϣ���������
		{
			LineInfo li=new LineInfo();
			int t=idi.checkChType(i);
			if(t==DongjinParameter.CHTYPE_EMPTY)
				li.setLineType(LineType.EMPTY_LINE);
			if(t==DongjinParameter.CHTYPE_RECORD)
				li.setLineType(LineType.RECORD_LINE);
			if(t==DongjinParameter.CHTYPE_TRUNK)
				li.setLineType(LineType.OUT_LINE);
			if(t==DongjinParameter.CHTYPE_USER)
				li.setLineType(LineType.IN_LINE);
			li.setCallId("");
			//tpl.setDtmf("".toCharArray());
			li.setLineState(LineState.L_FREE);
			li.setLineNum(i);
			si.getLines().add(i,li);
		}
		idv.sigInit(0);//��ʼ���ź���
		int r=idc.dConfEnableConfCard();//��ʼ������
		if(r!=0)//���ɹ��ͷŲ�����
		{
			free();
			return false;
		}
		//idih.feedPower(13);
		idi.setPackRate(ip.getPackRate());//����¼����ѹ������
		idi.setDialPara(ip.getRingback1(),ip.getRingback2(),ip.getBusylen(),ip.getRingtimes());
		//���ò��Ų���
		return true;
	}

	/**
	 * @return the idc
	 */
	public IDongjinConf getIdc() {
		return idc;
	}

	/**
	 * @param idc the idc to set
	 */
	public void setIdc(IDongjinConf idc) {
		this.idc = idc;
	}

	/**
	 * @return the idi
	 */
	public IDongjinInit getIdi() {
		return idi;
	}

	/**
	 * @param idi the idi to set
	 */
	public void setIdi(IDongjinInit idi) {
		this.idi = idi;
	}

	/**
	 * @return the idih
	 */
	public IDongjinInHook getIdih() {
		return idih;
	}

	/**
	 * @param idih the idih to set
	 */
	public void setIdih(IDongjinInHook idih) {
		this.idih = idih;
	}

	/**
	 * @return the idr
	 */
	public IDongjinRecord getIdr() {
		return idr;
	}

	/**
	 * @param idr the idr to set
	 */
	public void setIdr(IDongjinRecord idr) {
		this.idr = idr;
	}

	/**
	 * @return the idv
	 */
	public IDongjinVoice getIdv() {
		return idv;
	}

	/**
	 * @param idv the idv to set
	 */
	public void setIdv(IDongjinVoice idv) {
		this.idv = idv;
	}

}
