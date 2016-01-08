/**
 * 	@(#)AcceptMessage.java   2007-1-5 ÉÏÎç09:53:10
 *	 ¡£ 
 *	 
 */
package et.bo.callcenter.plant.dongjin_c161a.test.impl;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.apache.commons.lang.ArrayUtils;

import et.bo.callcenter.plant.dongjin_c161a.IDongjinDtmf;
import et.bo.callcenter.plant.dongjin_c161a.IDongjinFsk;
import et.bo.callcenter.plant.dongjin_c161a.IDongjinInHook;
import et.bo.callcenter.plant.dongjin_c161a.IDongjinInit;
import et.bo.callcenter.plant.dongjin_c161a.IDongjinOutHook;
import et.bo.callcenter.plant.dongjin_c161a.IDongjinPlay;
import et.bo.callcenter.plant.dongjin_c161a.IDongjinVoice;
import et.bo.callcenter.plant.dongjin_c161a.impl.DongjinParameter;
import et.bo.callcenter.plant.dongjin_c161a.test.IAcceptMessage;
import et.bo.callcenter.plant.dongjin_c161a.test.impl.TelephoneLine.TeleLineState;
import et.bo.callcenter.plant.dongjin_c161a.test.impl.TelephoneLine.TeleLineType;

 /**
 * @author zhaoyifei
 * @version 2007-1-5
 * @see
 */
public class AcceptMessage implements IAcceptMessage {

	private IDongjinInit idi=null;
	private IDongjinInHook idih=null;
	private IDongjinOutHook idoh=null;
	private IDongjinFsk idf=null;
	private IDongjinDtmf idd=null;
	private IDongjinPlay idp=null;
	private IDongjinVoice idv=null;
	private String voicePath;
	private BlockingQueue<MessageBean> mq=new ArrayBlockingQueue<MessageBean>(20);
	List<TelephoneLine> l=null;
	/**
	 * 
	 */
	public AcceptMessage() {
		// TODO Auto-generated constructor stub
		
		
	}
	/* (non-Javadoc)
	 * @see et.bo.callcenter.plant.dongjin_c161a.test.IAcceptMessage#accept()
	 */
	public void accept()
	{
		try {
			String filename;
			idi.pushPlay();
			idih.feedSigFunc();
			l=InitDongjin.tl;
			
			for(int i=0,size=l.size();i<size;i++)
			{
				
				switch(l.get(i).getState())
				{
					case CH_FREE:
					{
						//System.out.print(i);
						if(idoh.ringDetect(i))
						{
							if(l.get(i).getType().equals(TeleLineType.IN_LINE))
								l.get(i).setState(TeleLineState.CH_OFFHOOK);
							else
							{
								l.get(i).setState(TeleLineState.CH_RECEIVEID);
								idf.resetCallerIDBuffer(i);
								l.get(i).setTimeElapse(0);
							}
						}
						break;
					}
					case CH_RECEIVEID:
					{
						System.out.println("line: "+i+" state: "+l.get(i).getState());
						boolean offHook=false;
						if(l.get(i).getTimeElapse()>2000&&idoh.ringDetect(i))
							offHook=true;
						if(l.get(i).getTimeElapse()>7000)
							offHook=true;
						String a=idf.getCallerIDStr(i);
						if(a.equals("0")||a.equals("1")||a.equals("2")||a.equals("4"))
							;
						else
						{
							l.get(i).setCallId(a);
							offHook=true;
						}
						if(offHook)
						{
							idoh.offHook(i);
							idd.startSigCheck(i);
							l.get(i).setState(TeleLineState.CH_OFFHOOK);
						}
						l.get(i).setTimeElapse(l.get(i).getTimeElapse()+50);
					}
					case CH_WAITSECONDRING:
					{
						System.out.println("line: "+i+" state: "+l.get(i).getState());
						if(idoh.ringDetect(i))
						{
							idoh.offHook(i);
							idd.startSigCheck(i);
							l.get(i).setState(TeleLineState.CH_OFFHOOK);
						}
						break;
					}
					case CH_OFFHOOK:
					{
						System.out.println("line: "+i+" state: "+l.get(i).getState());
						filename=voicePath+"bank.001";
						idd.initDtmfBuf(i);
						idp.startPlayFile(i, filename,0);
						l.get(i).setState(TeleLineState.CH_WELCOME);
						break;
					}
					case CH_WELCOME:
					{
						System.out.println("line: "+i+" state: "+l.get(i).getState());
						int code=idd.getDtmfCode(i);
						if(code!=-1)
						{
							idp.stopPlayFile(i);
							l.get(i).setDtmf(numToDtmf(code),0);
							l.get(i).setDtmf((char)0,1);
							l.get(i).setState(TeleLineState.CH_ACCOUNT1);
						}
						if(idp.checkPlayEnd(i))
						{
							System.out.println("line: "+i+" state: "+l.get(i).getState());
							idp.stopPlayFile(i);
							filename=voicePath+"bank.002";
							idp.startPlayFile(i, filename,0);
							l.get(i).setState(TeleLineState.CH_ACCOUNT);
						}
						break;
					}
					case CH_ACCOUNT:
					{
						System.out.println("line: "+i+" state: "+l.get(i).getState());
						int code=idd.getDtmfCode(i);
						if(code!=-1)
						{
							idp.stopPlayFile(i);
							l.get(i).setDtmf(numToDtmf(code),0);
							l.get(i).setDtmf((char)0,1);
							l.get(i).setState(TeleLineState.CH_ACCOUNT1);
						}
						if(idp.checkPlayEnd(i))
						{
							idp.stopPlayFile(i);
							
							l.get(i).setState(TeleLineState.CH_ACCOUNT1);
						}
						break;
					}
					case CH_ACCOUNT1:
					{
						System.out.println("line: "+i+" state: "+l.get(i).getState());
						int len=ArrayUtils.indexOf(l.get(i).getDtmf(), '0');
						if(len==-1)
							len=0;
						int code;
						while((code=idd.getDtmfCode(i))!=-1)
							l.get(i).setDtmf(numToDtmf(code),len++);
						l.get(i).setDtmf((char)0,len);
						filename=voicePath+"bank.003";
						idp.startPlayFile(i, filename,0);
						l.get(i).setState(TeleLineState.CH_PASSWORD);
						l.get(i).setDtmf(new char[32]);
						break;
					}
					case CH_PASSWORD:
					{
						System.out.println("line: "+i+" state: "+l.get(i).getState());
						int code=idd.getDtmfCode(i);
						if(code!=-1)
						{
							idp.stopPlayFile(i);
							l.get(i).setDtmf(numToDtmf(code),0);
							l.get(i).setDtmf((char)0,1);
							l.get(i).setState(TeleLineState.CH_PASSWORD1);
						}
						if(idp.checkPlayEnd(i))
						{
							idp.stopPlayFile(i);
							
							l.get(i).setState(TeleLineState.CH_PASSWORD1);
						}
						break;
					}
					case CH_PASSWORD1:
					{
						System.out.println("line: "+i+" state: "+l.get(i).getState());
						int len=l.get(i).getDtmf().length;
						int code;
						while((code=idd.getDtmfCode(i))!=-1)
							l.get(i).setDtmf(numToDtmf(code),len++);
						l.get(i).setDtmf((char)0,len);
						filename=voicePath+"bank.004";
						idp.startPlayFile(i, filename,0);
						l.get(i).setState(TeleLineState.CH_SELECT);
						l.get(i).setDtmf(new char[32]);
						break;
					}
					case CH_SELECT:
					{
						System.out.println("line: "+i+" state: "+l.get(i).getState());
						int code=idd.getDtmfCode(i);
						if(code!=-1)
						{
							l.get(i).setDtmf(numToDtmf(code),0);
							l.get(i).setDtmf((char)0,1);
							switch(l.get(i).getDtmf(0))
							{
								case '1':
								{
									idp.stopPlayFile(i);
									idp.rsetIndexPlayFile(i);
									filename=voicePath+"bank.005";
									idp.addIndexPlayFile(i, filename);
									
									filename=voicePath+"d5";
									idp.addIndexPlayFile(i, filename);
									
									filename=voicePath+"d12";
									idp.addIndexPlayFile(i, filename);
									
									filename=voicePath+"d8";
									idp.addIndexPlayFile(i, filename);
									
									filename=voicePath+"d11";
									idp.addIndexPlayFile(i, filename);
									
									filename=voicePath+"d9";
									idp.addIndexPlayFile(i, filename);
									
									filename=voicePath+"d11";
									idp.addIndexPlayFile(i, filename);
									
									filename=voicePath+"d10";
									idp.addIndexPlayFile(i, filename);
									
									filename=voicePath+"d6";
									idp.addIndexPlayFile(i, filename);
									
									filename=voicePath+"d15";
									idp.addIndexPlayFile(i, filename);
									
									filename=voicePath+"d8";
									idp.addIndexPlayFile(i, filename);
									
									filename=voicePath+"bank.008";
									idp.addIndexPlayFile(i, filename);
									idp.startIndexPlayFile(i);
									break;
								}
								case '2':
								{
									idp.stopPlayFile(i);
									idp.rsetIndexPlayFile(i);
									
									filename=voicePath+"bank.006";
									idp.addIndexPlayFile(i, filename);
									
									filename=voicePath+"d0";
									idp.addIndexPlayFile(i, filename);
									
									filename=voicePath+"d15";
									idp.addIndexPlayFile(i, filename);
									
									filename=voicePath+"d4";
									idp.addIndexPlayFile(i, filename);
									
									filename=voicePath+"d8";
									idp.addIndexPlayFile(i, filename);
									
									filename=voicePath+"bank.008";
									idp.addIndexPlayFile(i, filename);
									
									idp.startIndexPlayFile(i);
									break;
								}
								case '3':
								{
									idp.stopPlayFile(i);
									idp.rsetIndexPlayFile(i);
									
									filename=voicePath+"bank.007";
									idp.addIndexPlayFile(i, filename);
									
									filename=voicePath+"d3";
									idp.addIndexPlayFile(i, filename);
									
									filename=voicePath+"d13";
									idp.addIndexPlayFile(i, filename);
									
									filename=voicePath+"d7";
									idp.addIndexPlayFile(i, filename);
									
									filename=voicePath+"d12";
									idp.addIndexPlayFile(i, filename);
									
									filename=voicePath+"bank.008";
									idp.addIndexPlayFile(i, filename);
									
									idp.startIndexPlayFile(i);
									break;
								}
								default:
									break;
							}
						}
						break;
					}
					case CH_PLAYRESULT:
					{
						System.out.println("line: "+i+" state: "+l.get(i).getState());
						if(idp.checkIndexPlayFile(i))
						{
							idp.stopPlayFile(i);
							if(l.get(i).getType().equals(TeleLineType.OUT_LINE))
								resetChannel(l.get(i));
							else
							{
								idih.startPlaySignal(i,DongjinParameter.SIG_BUSY1);
								l.get(i).setState(TeleLineState.CH_WAITUSERONHOOK);
							}
								
						}
						break;
					}
					case CH_WAITUSERONHOOK:
					{
						System.out.println("line: "+i+" state: "+l.get(i).getState());
						if(idoh.ringDetect(i))
						{
							idih.startPlaySignal(i, DongjinParameter.SIG_STOP);
							resetChannel(l.get(i));
						}
						break;
					}
					
				}//end switch
				if(l.get(i).getType().equals(TeleLineType.OUT_LINE)&&!l.get(i).getState().equals(TeleLineState.CH_FREE))
				{
					if(idv.sigCheckBusy(i)==1)
					{
						switch(l.get(i).getState())
						{
							case CH_WELCOME:
							case CH_ACCOUNT:
							case CH_PASSWORD:
							case CH_SELECT:
								idp.stopPlayFile(i);
								break;
							case CH_PLAYRESULT:
								idp.stopIndexPlayFile(i);
								break;
						}
						resetChannel(l.get(i));
					}
				}
				else if(l.get(i).getType().equals(TeleLineType.IN_LINE)&&!l.get(i).getState().equals(TeleLineState.CH_FREE))
				{
					if(!idoh.ringDetect(i))
					{
						switch(l.get(i).getState())
						{
						case CH_WELCOME:
						case CH_ACCOUNT:
						case CH_PASSWORD:
						case CH_SELECT:
							idp.stopPlayFile(i);
							break;
						case CH_PLAYRESULT:
							idp.stopIndexPlayFile(i);
							break;
						}
					}
					resetChannel(l.get(i));
				}
			}//end for
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/* (non-Javadoc)
	 * @see et.bo.callcenter.plant.dongjin_c161a.test.IAcceptMessage#numToDtmf(int)
	 */
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
				c = (char) (ch + '0');//×ª»»³ÉASCIIÂë
		}
		return c;
	}
	private void resetChannel(TelephoneLine t)
	{
		if(t.getType().equals(TeleLineType.OUT_LINE))
		{
			idd.startSigCheck(t.getLine());
			idoh.hangUp(t.getLine());
			idv.sigResetCheck(t.getLine());
		}
		t.setCallId("");
		t.setDtmf(new char[32]);
		t.setState(TeleLineState.CH_FREE);
	}
	/* (non-Javadoc)
	 * @see et.bo.callcenter.plant.dongjin_c161a.test.IAcceptMessage#getVoicePath()
	 */
	public void getVoicePath()
	{
		
	}
	public IDongjinInit getIdi() {
		return idi;
	}
	public void setIdi(IDongjinInit idi) {
		this.idi = idi;
	}
	public IDongjinInHook getIdih() {
		return idih;
	}
	public void setIdih(IDongjinInHook idih) {
		this.idih = idih;
	}
	public IDongjinOutHook getIdoh() {
		return idoh;
	}
	public void setIdoh(IDongjinOutHook idoh) {
		this.idoh = idoh;
	}
	public IDongjinFsk getIdf() {
		return idf;
	}
	public void setIdf(IDongjinFsk idf) {
		this.idf = idf;
	}
	public IDongjinDtmf getIdd() {
		return idd;
	}
	public void setIdd(IDongjinDtmf idd) {
		this.idd = idd;
	}
	public IDongjinPlay getIdp() {
		return idp;
	}
	public void setIdp(IDongjinPlay idp) {
		this.idp = idp;
	}
	public IDongjinVoice getIdv() {
		return idv;
	}
	public void setIdv(IDongjinVoice idv) {
		this.idv = idv;
	}
	public void setVoicePath(String voicePath) {
		this.voicePath = voicePath;
	}
}
