/**
 * 	@(#)InitDongjin.java   2007-1-4 上午11:11:53
 *	 。 
 *	 
 */
package et.bo.callcenter.plant.dongjin_c161a.test.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import et.bo.callcenter.plant.dongjin_c161a.IDongjinConf;
import et.bo.callcenter.plant.dongjin_c161a.IDongjinDtmf;
import et.bo.callcenter.plant.dongjin_c161a.IDongjinFsk;
import et.bo.callcenter.plant.dongjin_c161a.IDongjinInHook;
import et.bo.callcenter.plant.dongjin_c161a.IDongjinInit;
import et.bo.callcenter.plant.dongjin_c161a.IDongjinLink;
import et.bo.callcenter.plant.dongjin_c161a.IDongjinOutHook;
import et.bo.callcenter.plant.dongjin_c161a.IDongjinPlay;
import et.bo.callcenter.plant.dongjin_c161a.IDongjinRecord;
import et.bo.callcenter.plant.dongjin_c161a.IDongjinRecordChange;
import et.bo.callcenter.plant.dongjin_c161a.IDongjinVoice;
import et.bo.callcenter.plant.dongjin_c161a.impl.DongjinParameter;
import et.bo.callcenter.plant.dongjin_c161a.impl.TcIniType;
import et.bo.callcenter.plant.dongjin_c161a.impl.TcIniTypeMore;
import et.bo.callcenter.plant.dongjin_c161a.test.IInitDongjin;
import et.bo.callcenter.plant.dongjin_c161a.test.impl.TelephoneLine.TeleLineState;
import et.bo.callcenter.plant.dongjin_c161a.test.impl.TelephoneLine.TeleLineType;
 /**
 * @author zhaoyifei
 * @version 2007-1-4
 * @see
 */
public class InitDongjin implements IInitDongjin {

	public static List<TelephoneLine> tl=new CopyOnWriteArrayList<TelephoneLine>();
	private IDongjinInit idi=null;
	private IDongjinVoice idv=null;
	private IDongjinInHook idih=null;
	private IDongjinLink idl=null;
	private IDongjinOutHook idoh=null;
	private IDongjinFsk idf=null;
	private IDongjinDtmf idd=null;
	private IDongjinPlay idp=null;
	private IDongjinRecord idr=null;
	private IDongjinRecordChange idrc=null;
	private IDongjinConf idc=null;
	public IDongjinConf getIdc() {
		return idc;
	}
	public void setIdc(IDongjinConf idc) {
		this.idc = idc;
	}
	public IDongjinRecord getIdr() {
		return idr;
	}
	public void setIdr(IDongjinRecord idr) {
		this.idr = idr;
	}
	public IDongjinDtmf getIdd() {
		return idd;
	}
	public void setIdd(IDongjinDtmf idd) {
		this.idd = idd;
	}
	public IDongjinFsk getIdf() {
		return idf;
	}
	public void setIdf(IDongjinFsk idf) {
		this.idf = idf;
	}
	public IDongjinPlay getIdp() {
		return idp;
	}
	public void setIdp(IDongjinPlay idp) {
		this.idp = idp;
	}
	public IDongjinOutHook getIdoh() {
		return idoh;
	}
	public void setIdoh(IDongjinOutHook idoh) {
		this.idoh = idoh;
	}
	public IDongjinVoice getIdv() {
		return idv;
	}
	public void setIdv(IDongjinVoice idv) {
		this.idv = idv;
	}
	public IDongjinInHook getIdih() {
		return idih;
	}
	public void setIdih(IDongjinInHook idih) {
		this.idih = idih;
	}
	public IDongjinLink getIdl() {
		return idl;
	}
	public void setIdl(IDongjinLink idl) {
		this.idl = idl;
	}
	public IDongjinRecordChange getIdrc() {
		return idrc;
	}
	public void setIdrc(IDongjinRecordChange idrc) {
		this.idrc = idrc;
	}
	/**
	 * 是否初始化成功
	 */
	private int driverOpenFlag; 
	/**
	 * 电话路
	 */
	private int totalLine;
	public IDongjinInit getIdi() {
		return idi;
	}
	public void setIdi(IDongjinInit idi) {
		this.idi = idi;
	}
	
	/* (non-Javadoc)
	 * @see et.bo.callcenter.plant.dongjin_c161a.test.IInitDongjin#init()
	 */
	public void init()throws Exception
	{
		driverOpenFlag=idi.loadDRV();
		if(driverOpenFlag!=0)
			throw new Exception("");
		totalLine=idi.checkValidCh();
		if(idi.enableCard(totalLine,1024*8*3)!=0)
		{
			idi.freeDRV();
			throw new Exception("");
		}
		idr.vRSetRefreshSize(1024*8);
		idi.setBusyPara(700);
		for(int i=0;i<totalLine;i++)
		{
			TelephoneLine tpl=new TelephoneLine();
			int t=idi.checkChType(i);
			if(t==DongjinParameter.CHTYPE_EMPTY)
				tpl.setType(TeleLineType.EMPTY_LINE);
			if(t==DongjinParameter.CHTYPE_RECORD)
				tpl.setType(TeleLineType.RECORD_LINE);
			if(t==DongjinParameter.CHTYPE_TRUNK)
				tpl.setType(TeleLineType.OUT_LINE);
			if(t==DongjinParameter.CHTYPE_USER)
				tpl.setType(TeleLineType.IN_LINE);
			tpl.setCallId("");
			//tpl.setDtmf("".toCharArray());
			tpl.setState(TeleLineState.CH_FREE);
			tpl.setLine(i);
			tl.add(i,tpl);
		}
		idv.sigInit(0);
		int r=idc.dConfEnableConfCard();
		if(r!=0)
		{
			exit();
			return;
		}
		//idih.feedPower(13);
		idi.setPackRate(0);
		idi.setDialPara(1000,4000,350,15);
	}
	/* (non-Javadoc)
	 * @see et.bo.callcenter.plant.dongjin_c161a.test.IInitDongjin#exit()
	 */
	public void exit()
	{
		idc.dConfDisableConfCard();
		idi.disableCard();
		idi.freeDRV();
		
	}
	public void getInitInfo()
	{
		int i=idi.checkValidCh();
		TcIniType tit=idi.getSysInfo();
		TcIniTypeMore titm=idi.getSysInfoMore();
		boolean support=idi.isSupportCallerID();
		int cardno=idi.newReadPass(0);
	}
	public void connect(int i,int j)
	{
		try {
			/*while(!idih.offHookDetect(i))
				ring(13);
			while(!idih.offHookDetect(j))
			ring(14);*/
			if(idih.offHookDetect(j)&&idih.offHookDetect(i))
			idl.setLink(i,j);
			while(idih.offHookDetect(j)&&idih.offHookDetect(i))
				;
			idl.clearLink(i,j);
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			idih.feedPower(i);
			idih.feedPower(j);
			idl.clearLink(i,j);
		}
	}
	public void link(int i,int j)
	{
		if(idih.offHookDetect(j)&&idih.offHookDetect(i))
			idl.linkOneToAnother(i,j);
			while(idih.offHookDetect(j)&&idih.offHookDetect(i))
				;
			idl.clearOneFromAnother(i,j);
	}
	public void linkthree(int i,int j,int k)
	{
		while(true)
		{
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		if(idih.offHookDetect(j)&&idih.offHookDetect(i)&&idih.offHookDetect(k))
			idl.linkThree(i,j,k);
			while(idih.offHookDetect(j)&&idih.offHookDetect(i)&&idih.offHookDetect(k))
				;
			idl.clearThree(i,j,k);
		}
	}
	public void ring(int i)
	{
			int times=7;
			while(times-->0)
			{
			idih.feedRing(i);
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			idih.feedPower(i);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
		
	}
	public void trunkRingAndHook()
	{
		String filename="d:\\zhaoyifei\\aa.bbb";
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int i=0;i<10;i++)
		{
			if(idoh.ringDetect(i))
			{
				idoh.offHook(i);
				System.out.println(i);
				idr.dadjustVocVol(i,DongjinParameter.VOL_ADJUST_PLAY,-12);
				idp.startPlayFile(i, filename,0);
				while(true)
				{
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					idi.pushPlay();
					if(idp.checkPlayEnd(i))
					{
						idp.stopPlayFile(i);
						//idoh.hangUp(i);
						break;
					}
				}
				
				idoh.hangUp(i);
			}
		}
	}
	public void playIndex()
	{
		String filename="C:\\TC08A32\\voc\\";
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int i=0;i<16;i++)
		{
			
			if(idoh.ringDetect(i))
			{
				idoh.offHook(i);
				System.out.println(i);
				idp.rsetIndexPlayFile(i);
				idp.addIndexPlayFile(i, filename+"Bank.005");
				idp.addIndexPlayFile(i, filename+"Bank.006");
				idp.addIndexPlayFile(i, filename+"Bank.007");
				idp.addIndexPlayFile(i, filename+"Bank.008");
				idp.startIndexPlayFile(i);
				while(true)
				{
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					idi.pushPlay();
					if(idp.checkIndexPlayFile(i))
					{
						idp.stopIndexPlayFile(i);
						//idoh.hangUp(i);
						break;
					}
				}
				//idp.startPlayFile(i, filename,0);
				//if(idp.checkPlayEnd(i))
				//idp.stopPlayFile(i);
				
				idoh.hangUp(i);
			}
		}
	}
	public void recordAndPlay(int i) {
		// TODO Auto-generated method stub
		while(true)
		{
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		if(idoh.ringDetect(i))
		{
		idr.startRecordFile(i, "d:\\aa.bb",1024*8*5);
		while(true)
		{
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			idi.pushPlay();
			if(idr.checkRecordEnd(i))
			{
				idr.stopRecordFile(i);
				break;
			}
			if(!idoh.ringDetect(i))
			{
				idr.stopRecordFile(i);
				break;
			}
		}
		
		idp.startPlayFile(i, "d:\\aa.bb",0);
		if(idp.checkPlayEnd(i))
		idp.stopPlayFile(i);
		break;
		}
	}
	}
	public void receiveAndSendDtmf(int i,int to)
	{
		StringBuilder sb=new StringBuilder();
		int size=12;
		int count=0;
		while(true)
		{
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(idoh.ringDetect(i))
			{
				idd.initDtmfBuf(i);
				while(true)
				{
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if(idd.dtmfHit(i))
					{
					int a=idd.getDtmfCode(i);
					if(a!=-1)
					{
					sb.append(numToDtmf(a));
					count++;
					}
					}
					if(count==size)
						break;
				}
				break;
			}
		}
		System.out.println(sb.toString());
		idd.sendDtmfBuf(to,sb.toString());
		while(true)
		{
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			idi.pushPlay();
			if(idd.checkSendEnd(to))
				break;
		}
		idd.setSendPara(50, 50);
		idd.newSendDtmfBuf(to,sb.toString());
		while(true)
		{
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			idi.pushPlay();
			if(idd.newCheckSendEnd(to))
				break;
		}
	}
	public char numToDtmf(int ch)
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
	public void checkSig(int i)
	{
		String filename="C:\\TC08A32\\voc\\bank.001";
		idd.startSigCheck(i);
		idoh.offHook(i);
		int a=idv.sigCheckDial(i);
		boolean polarity;
		///if(a==DongjinParameter.S_NODIALTONE)
		idv.sigStartDial(i,"1043","", 0);
		
		//System.out.println("1043".toCharArray());
		while(true)
		{
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//idi.pushPlay();
			a=idv.sigCheckDial(i);
			idih.feedSigFunc();
			if(a==DongjinParameter.S_NORESULT)
				continue;
			
			
			break;
		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		polarity=idd.checkPolarity(i);
		if(a==DongjinParameter.S_CONNECT)
		{
			idp.startPlayFile(i, filename,0);
			
		}
		while(true)
		{
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			idi.pushPlay();
			if(idp.checkPlayEnd(i))
			{
				idp.stopPlayFile(i);
				//idoh.hangUp(i);
				break;
			}
		}
		while(true)
		{
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			a=idv.sigCheckBusy(i);
			
			idih.feedSigFunc();
			int si=idd.checkSilence(i,31*5);

			if(polarity!=idd.checkPolarity(i))
			{
				idoh.hangUp(i);
				break;
			}
			if(a==1)
			{
				int siz=idv.sigGetCadenceCount(i,DongjinParameter.SIG_BUSY1);
				idv.sigResetCheck(i);
				idoh.hangUp(i);
				break;
			}
			
			
		}
		
		
		idd.stopSigCheck(i);
 	}
	public void inline(int i)
	{
		idih.feedRealRing(i);
		while(!idih.offHookDetect(i))
		{
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			idih.feedSigFunc();
		}
		idih.feedPower(i);
		idih.readGenerateSigBuf("d:\\zhaoyifei\\aaa.wav");//这个不管用
		idih.startPlaySignal(i,DongjinParameter.SIG_DIALTONE);
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		idih.startPlaySignal(i,DongjinParameter.SIG_STOP);
		idih.startHangUpDetect(i);
		while(true)
		{
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			int state=idih.hangUpDetect(i);
			switch(state)
			{
			case 0:
			case 2:
				break;
			case 3:
				System.out.println("拍叉");
			case 1:
				break;
			}
			if(state==1)
				break;
		}
	}
	public void receiveFsk(int i)
	{
		StringBuilder sb=new StringBuilder();
		idf.resetCallerIDBuffer(i);
		idd.initDtmfBuf(i);
		while(true)
		{
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(idd.dtmfHit(i))
			{
			int a=idd.getDtmfCode(i);
			if(a!=-1)
			{
				sb.append(numToDtmf(a));
			}
			System.out.println("dtmf: "+sb.toString());
			}
			String num=idf.getCallerIDStr(i);
			if(num.equals("4"))
			{
				System.out.println("error");
				break;
			}
			if(num.equals("0")||num.equals("1")||num.equals("2"))
				continue;
			System.out.println(num);
			break;
		}
		
	}
	public void changewavFile()
	{
		idrc.pcmtoWave("d:\\zhaoyifei\\aa.bb","d:\\zhaoyifei\\aa.wav");
		idrc.pcmtoAd("d:\\zhaoyifei\\aa.bb", "d:\\zhaoyifei\\aa.ad");
		idrc.ad6ktoPcm("d:\\zhaoyifei\\aa.ad", "d:\\zhaoyifei\\aa.bc");
		idrc.wavetoPcm("d:\\zhaoyifei\\aa.wav", "d:\\zhaoyifei\\aa.bd");
	}
	public void recordAndPlayMemory(int i)
	{
		String s;
		while(true)
		{
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		if(idoh.ringDetect(i))
		{
		idr.vRStartRecord(i);
		while(true)
		{
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			s=idr.vRGetRecordData(i);
			if(!s.equals("0"))
			{
				idr.vRStopRecord(i);
				break;
			}
			if(!idoh.ringDetect(i))
			{
				idr.stopRecordFile(i);
				break;
			}
		}
		int len=s.length();
		idp.startPlay(i,s,0, s.length());
		while(true)
		{
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			idi.pushPlay();
		if(idp.checkPlayEnd(i))
		idp.stopPlay(i);
		break;
		}
		}
	}
	}
	public void memoryPlay(int i)
	{
		while(true)
		{
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(idoh.ringDetect(i))
			{
				break;
			}
		}
		String s=null;
		int size=0;
		try {
			FileInputStream is=new FileInputStream(new File("C:\\TC08A32\\voc\\bank.001"));
			size=is.available();
			byte[] temp=new byte[size];
			is.read(temp);
			s=new String(temp);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		idp.startPlay(i,s,0,size);
		while(true)
		{
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			idi.pushPlay();
		if(idp.checkPlayEnd(i))
		idp.stopPlay(i);
		break;
		}
	}
	
	public void conf()
	{
		List<Integer> l=new ArrayList<Integer>();
		int confSize=idc.dConfGetResNumber();
		boolean flag=false;
		while(true)
		{
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			idi.pushPlay();
			for(int i=0;i<16;i++)
			{
				if(idoh.ringDetect(i)&&!l.contains(i))
				{
					if(i>13)
					idc.addChnl(1,i, -6,0);
					else
						idc.addListenChnl(1,i);
					l.add(i);
				}
				if(!idoh.ringDetect(i)&&l.contains(i))
				{
					l.remove(new Integer(i));
					if(i>13)
					idc.subChnl(1,i);
					else
						idc.subListenChnk(1,i);
				}
					
			}
			if(l.size()==0)
			{
				if(flag)
				{
					if(idr.checkRecordEnd(13))
					{
						idr.stopRecordFile(13);
					}
					idc.dConfSubRecListenChnl(1,13);	
					break;
				}
			}
			else
			{
				if(!flag)
				{
				idr.startRecordFile(13,"d:\\zhaoyifei\\aa.bbb", 1024*8*20);
				idc.dConfAddRecListenChnl(1,13);
				}
				flag=true;
			}
		}
		
	}
	
}
