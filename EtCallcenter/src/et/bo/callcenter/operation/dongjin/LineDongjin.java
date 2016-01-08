/**
 * 	@(#)LineDongjin.java   2007-1-23 下午02:00:57
 *	 。 
 *	 
 */
package et.bo.callcenter.operation.dongjin;

import java.util.List;

import et.bo.callcenter.operation.EventBean;
import et.bo.callcenter.operation.EventBean.EventType;
import et.bo.callcenter.operation.EventBean.SigType;
import et.bo.callcenter.operation.LineInfo.LineState;
import et.bo.callcenter.operation.LineInfo.LineType;
import et.bo.callcenter.operation.impl.AbsLine;
import et.bo.callcenter.plant.dongjin_c161a.IDongjinDtmf;
import et.bo.callcenter.plant.dongjin_c161a.IDongjinFsk;
import et.bo.callcenter.plant.dongjin_c161a.IDongjinInHook;
import et.bo.callcenter.plant.dongjin_c161a.IDongjinOutHook;
import et.bo.callcenter.plant.dongjin_c161a.IDongjinPlay;
import et.bo.callcenter.plant.dongjin_c161a.IDongjinRecord;
import et.bo.callcenter.plant.dongjin_c161a.IDongjinVoice;
import et.bo.callcenter.plant.dongjin_c161a.impl.DongjinParameter;

 /**
 * @author zhaoyifei
 * @version 2007-1-23
 * @see
 */
public class LineDongjin extends AbsLine {

	private boolean polarity;
	private boolean isrecord;
	private Object signal;
	private boolean isring;
	private String file;
	private int lineNum;
	private IDongjinInHook idih=null;
	private IDongjinOutHook idoh=null;
	private IDongjinFsk idf=null;
	private IDongjinDtmf idd=null;
	private IDongjinPlay idp=null;
	private IDongjinVoice idv=null;
	private IDongjinRecord idr=null;
	public LineDongjin(){
		super();
		signal=new Object();
	}
	/**
	 * @return the idd
	 */
	public IDongjinDtmf getIdd() {
		return idd;
	}

	/**
	 * @param idd the idd to set
	 */
	public void setIdd(IDongjinDtmf idd) {
		this.idd = idd;
	}

	/**
	 * @return the idf
	 */
	public IDongjinFsk getIdf() {
		return idf;
	}

	/**
	 * @param idf the idf to set
	 */
	public void setIdf(IDongjinFsk idf) {
		this.idf = idf;
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
	 * @return the idoh
	 */
	public IDongjinOutHook getIdoh() {
		return idoh;
	}

	/**
	 * @param idoh the idoh to set
	 */
	public void setIdoh(IDongjinOutHook idoh) {
		this.idoh = idoh;
	}

	/**
	 * @return the idp
	 */
	public IDongjinPlay getIdp() {
		return idp;
	}

	/**
	 * @param idp the idp to set
	 */
	public void setIdp(IDongjinPlay idp) {
		this.idp = idp;
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

	public LineDongjin(int i, LineType lt, int logic) {
		super(i, lt, logic);
		signal=new Object();
		polarity=idd.checkPolarity(i);
		// TODO Auto-generated constructor stub
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
	

	/* (non-Javadoc)
	 * @see et.bo.callcenter.operation.impl.AbsLine#feedPower(int)
	 */
	@Override
	protected void feedPower(int i) {
		// TODO Auto-generated method stub
		this.isring=false;
		idih.feedPower(i);
		
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.operation.impl.AbsLine#feedRealRing(int)
	 */
	@Override
	protected void feedRealRing(int i) {
		// TODO Auto-generated method stub
		li.setLineState(LineState.U_RING);
		idih.feedRealRing(i);
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.operation.impl.AbsLine#feedRing(int)
	 */
	@Override
	protected void feedRing(int i,int ring,int interval) {
		// TODO Auto-generated method stub
		if(isring)
			return;
		li.setLineState(LineState.U_RING);
		
		Ring r=new Ring();
		r.ring=ring;
		r.line=i;
		r.interval=interval;
		isring=true;
		r.start();
	}
	class Ring extends Thread
	{
		int ring;
		int interval;
		int line;
		/* (non-Javadoc)
		 * @see java.lang.Thread#run()
		 */
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
			while(isring)
			{
				idih.feedRing(line);
				this.sleep(ring);
				idih.feedPower(line);
				this.sleep(interval);
			}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	/* (non-Javadoc)
	 * @see et.bo.callcenter.operation.impl.AbsLine#hangUp(int)
	 */
	@Override
	protected void hangUp(int i) {
		// TODO Auto-generated method stub
		clearDtmf();
		li.setLineState(LineState.L_FREE);
		idd.stopSigCheck(i);
		idoh.hangUp(i);
		idv.sigResetCheck(i);
		
	}

	

	/* (non-Javadoc)
	 * @see et.bo.callcenter.operation.impl.AbsLine#newSendDtmfBuf(int, java.lang.String)
	 */
	@Override
	protected void newSendDtmfBuf(int i, String dtmf) {
		// TODO Auto-generated method stub
		li.setLineState(LineState.L_SENDDTMF);
		idd.setSendPara(50, 50);
		idd.newSendDtmfBuf(i,dtmf);
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.operation.impl.AbsLine#offHook(int)
	 */
	@Override
	protected void offHook(int i) {
		// TODO Auto-generated method stub
		
		idoh.offHook(i);
		idd.initDtmfBuf(i);
		idd.startSigCheck(i);
		if(li.getLineState().equals(LineState.T_RING))
			li.setLineState(LineState.T_OFFHOOK);
		else
			li.setLineState(LineState.T_OFFHOOK);
		
		
	}
	
	


	/* (non-Javadoc)
	 * @see et.bo.callcenter.operation.impl.AbsLine#startIndexPlayFile(int, java.util.List)
	 */
	@Override
	protected boolean startIndexPlayFile(int num, List<String> files) {
		// TODO Auto-generated method stub
		idp.rsetIndexPlayFile(num);
		for(int i=0;i<files.size();i++)
		{
			idp.addIndexPlayFile(num,files.get(i));
		}
		li.setLineState(LineState.L_PLAYINDEX);
		return idp.startIndexPlayFile(num);
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.operation.impl.AbsLine#startPlayFile(int, java.lang.String)
	 */
	@Override
	protected boolean startPlayFile(int i, String file) {
		// TODO Auto-generated method stub
		li.setLineState(LineState.L_PLAY);
		return idp.startPlayFile(i, file,0);
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.operation.impl.AbsLine#startPlaySignal(int, int)
	 */
	@Override
	protected void startPlaySignal(int i, int type) {
		// TODO Auto-generated method stub
		idih.startPlaySignal(i, type);
		li.setLineState(LineState.L_PLAY_SIG);
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.operation.impl.AbsLine#startRecordFile(int, java.lang.String)
	 */
	@Override
	protected boolean startRecordFile(int i, String file) {
		// TODO Auto-generated method stub
		isrecord=true;
		this.file=file;
		this.lineNum=i;
		li.setLineState(LineState.L_RECORD);
		Record r=new Record();
		r.start();
		return true;
	}
	private void record()
	{
		idr.startRecordFileNew(lineNum, file, 1024*8*3, -1);
		try {
			synchronized(signal)
			{
				signal.wait(2999);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		
		}
	}
	class Record extends Thread{

		/* (non-Javadoc)
		 * @see java.lang.Thread#run()
		 */
		@Override
		public void run() {
			// TODO Auto-generated method stub
			
			while(isrecord)
			{
				record();
			}
			idr.stopRecordFile(lineNum);
		}
		
	}
	/* (non-Javadoc)
	 * @see et.bo.callcenter.operation.impl.AbsLine#stopIndexPlayFile(int)
	 */
	@Override
	protected void stopIndexPlayFile(int i) {
		// TODO Auto-generated method stub
		idp.stopIndexPlayFile(i);
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.operation.impl.AbsLine#stopPlayFile(int)
	 */
	@Override
	protected void stopPlayFile(int i) {
		// TODO Auto-generated method stub
		idp.stopPlay(i);
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.operation.impl.AbsLine#stopRecordFile(int)
	 */
	@Override
	protected void stopRecordFile(int i) {
		// TODO Auto-generated method stub
		isrecord=false;
		synchronized(signal)
		{
		signal.notify();
		}
	}

	@Override
	protected void dial(int i, String dail, String pre) {
		// TODO Auto-generated method stub
		idv.sigStartDial(i,dail, pre,0);
		Dial d=new Dial();
		d.line=i;
		d.start();
	}
	class Dial extends Thread
	{
		 int line;
		
		/* (non-Javadoc)
		 * @see java.lang.Thread#run()
		 */
		@Override
		public void run() {
			// TODO Auto-generated method stub
			idd.startSigCheck(line);
			int sig;
			while(true)
			{
				try {
					this.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				sig=idv.sigCheckDial(line);
				if(sig==DongjinParameter.S_NORESULT)
					continue;
				break;
			}
			EventBean eb=new EventBean();
			if(sig==DongjinParameter.S_BUSY)
			{
				eb.setEt(EventType.T_DIAL);
				eb.setSt(SigType.S_BUSY);
				eb.setLineNum(line);
			}
			if(sig==DongjinParameter.S_NOBODY)
			{
				eb.setEt(EventType.T_DIAL);
				eb.setSt(SigType.S_NOBODY);
				eb.setLineNum(line);
			}
			if(sig==DongjinParameter.S_NODIALTONE)
			{
				eb.setEt(EventType.T_DIAL);
				eb.setSt(SigType.S_NODIALTONE);
				eb.setLineNum(line);
			}
			if(sig==DongjinParameter.S_NOSIGNAL)
			{
				eb.setEt(EventType.T_DIAL);
				eb.setSt(SigType.S_NOSIGNAL);
				eb.setLineNum(line);
			}
			if(sig==DongjinParameter.S_CONNECT)
			{
				eb.setEt(EventType.T_CONNECT);
				
				eb.setLineNum(line);
			}
			ebs.add(eb);
			idd.stopSigCheck(line);
		}
		
	}
	@Override
	protected void listen(int i) {
		// TODO Auto-generated method stub
		LineState ls=li.getLineState();
		if(ls.equals(LineState.L_PLAY))
		{
			//检测放音是否结束,如果结束触发放音结束事件
			boolean end=idp.checkPlayEnd(i);
			if(end)
			{
				EventBean eb=new EventBean();
				eb.setEt(EventType.L_PLAYEND);
				eb.setLineNum(i);
				ebs.add(eb);
				
			}
		}
		if(ls.equals(LineState.L_PLAYINDEX))
		{
			//检测放音是否结束,如果结束触发放音结束事件
			boolean end=idp.checkIndexPlayFile(i);
			if(end)
			{
				EventBean eb=new EventBean();
				eb.setEt(EventType.L_PLAYEND);
				eb.setLineNum(i);
				ebs.add(eb);
				
			}
			
		}
	
		if(!ls.equals(LineState.L_FREE))
		{
			//检测有dtmf码如果有，停止放音，触发收码事件
			boolean dtmf=idd.dtmfHit(i);
			if(dtmf)
			{
				this.stopPaly();
				EventBean eb=new EventBean();
				eb.setEt(EventType.L_RECEDTMF);
				eb.setLineNum(i);
				ebs.add(eb);
			}
		}
		
		if(ls.equals(LineState.L_SENDDTMF))
		{
			//检测是否发送完毕
			boolean dtmf=idd.newCheckSendEnd(i);
			if(dtmf)
			{
				EventBean eb=new EventBean();
				eb.setEt(EventType.L_SENDDTMFEND);
				eb.setLineNum(i);
				ebs.add(eb);
			}
		}
		if(ls.equals(LineState.U_RING))
		{
			//检测内线摘机，如果摘机停止振铃
			boolean offhook=idih.offHookDetect(i);
			if(offhook)
			{
				idih.feedPower(li.getLineNum());
				EventBean eb=new EventBean();
				eb.setEt(EventType.U_OFFHOOK_BYRING);
				eb.setLineNum(i);
				ebs.add(eb);
				li.setLineState(LineState.U_OFFHOOK);
				idih.startHangUpDetect(i);
			}
		}
		if(!ls.equals(LineState.L_FREE)&&!ls.equals(LineState.U_RING)&&li.getLineType().equals(LineType.IN_LINE))
		{
			//内线非挂机状态,检测拍叉和挂机状态
			int a=idih.hangUpDetect(i);
			if(a==DongjinParameter.HANG_UP_FLAG_PRESS_R)
			{
				EventBean eb=new EventBean();
				eb.setEt(EventType.U_HOOKFLASH);
				eb.setLineNum(i);
				ebs.add(eb);
			}
			if(a==DongjinParameter.HANG_UP_FLAG_TRUE)
			{
				
				EventBean eb=new EventBean();
				eb.setEt(EventType.U_HANGUP);
				eb.setLineNum(i);
				ebs.add(eb);
				li.setLineState(LineState.L_FREE);
				clearDtmf();
				this.stopPaly();
				
			}
			
		}
		if(ls.equals(LineState.L_FREE))
		if(idoh.ringDetect(i))
		{
			//挂机状态，检测内线摘机，外线振铃
			if(li.getLineType().equals(LineType.OUT_LINE))
			{
				//外线振铃，触发振铃事件
				EventBean eb=new EventBean();
				eb.setEt(EventType.T_RING);
				eb.setLineNum(i);
				ebs.add(eb);
				clearDtmf();
				
			}
			if(li.getLineType().equals(LineType.IN_LINE))
			{
				//内线摘机，初始化dtmf缓冲，触发振铃事件
				idd.initDtmfBuf(i);
				EventBean eb=new EventBean();
				eb.setEt(EventType.U_OFFHOOK);
				eb.setLineNum(i);
				ebs.add(eb);
				li.setLineState(LineState.U_OFFHOOK);
				idih.startHangUpDetect(i);
				clearDtmf();
			}
		}
		//if(ls.equals(LineState.T_CONNECT)||ls.equals(LineState.T_OFFHOOK)||ls.equals(LineState.L_LINK))
		if(!ls.equals(LineState.L_FREE)&&li.getLineType().equals(LineType.OUT_LINE))
		{
			//外线非挂机状态，检测极性反转事件
			boolean p=idd.checkPolarity(i);
			if(p!=polarity)
			{
				EventBean eb=new EventBean();
				eb.setEt(EventType.T_POLARITY);
				eb.setLineNum(i);
				ebs.add(eb);
				polarity=p;
			}
			//检测信号音
			int iv=idv.sigCheckBusy(i);
			if(iv==1)
			{
				EventBean eb=new EventBean();
				eb.setEt(EventType.T_HANGUP);
				eb.setLineNum(i);
				ebs.add(eb);
				li.setLineState(LineState.L_FREE);
			}
		}

	}
	
	
	
	/**
	 * 内部工具
	 * @param
	 * @version 2007-1-25
	 * @return
	 */
	private char numToDtmf(int ch)
	{
		char c;

		switch(ch)
	    {
			case 10:
				c = '0';
				break;
			case 11:
				c = '*';
				break;
			case 12:
				c = '#';
				break;
	        case 13:
	        case 14:
	        case 15:
	            c=(char) (ch-13+'a');
	            break;
	        case 0:
	            c='d';
	            break;
			default:
				c = (char) (ch + '0');//转换成ASCII码
		}
		return c;
	}

	public void receiveDtmf() {
		// TODO Auto-generated method stub
		
		int n=idd.getDtmfCode(li.getLineNum());
		if(n==-1)
			return;
		li.addDtmf(numToDtmf(n));
	}

	public void receiveFsk() {
		// TODO Auto-generated method stub
		idf.resetCallerIDBuffer(li.getLineNum());
		li.setCallId(idf.getCallerIDStr(li.getLineNum()));
	}

	public void clearDtmf() {
		// TODO Auto-generated method stub
		idd.initDtmfBuf(li.getLineNum());
		li.clearDtmf();
	}

	public void stopPaly() {
		// TODO Auto-generated method stub
		idp.stopPlay(li.getLineNum());
		idih.startPlaySignal(li.getLineNum(),DongjinParameter.SIG_STOP);
		idp.stopIndexPlayFile(li.getLineNum());
		idp.stopPlayFile(li.getLineNum());
		
	}
	
}
