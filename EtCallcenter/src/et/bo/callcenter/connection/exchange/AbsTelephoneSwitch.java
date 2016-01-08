/**
 * 	@(#)AbsTelephoneSwitch.java   2007-1-29 上午09:38:37
 *	 。 
 *	 
 */
package et.bo.callcenter.connection.exchange;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import et.bo.callcenter.connection.voicemail.ManagerVoiceMailI;
import et.bo.callcenter.operation.AbsMissionBean;
import et.bo.callcenter.operation.ConferenceService;
import et.bo.callcenter.operation.EventBean;
import et.bo.callcenter.operation.InitInfo;
import et.bo.callcenter.operation.InitService;
import et.bo.callcenter.operation.LineService;
import et.bo.callcenter.operation.LinkService;
import et.bo.callcenter.operation.SysInfo;
import et.bo.callcenter.operation.VoiceFileChange;
import et.bo.callcenter.operation.EventBean.EventType;
import excellence.framework.base.container.SpringRunningContainer;

 /**
 * @author zhaoyifei
 * @version 2007-1-29
 * @see
 */
public abstract class AbsTelephoneSwitch implements TelephoneSwitchService{

	protected BlockingQueue<EventBean> q=new LinkedBlockingQueue<EventBean>();
	
	//protected BlockingQueue<AbsMissionBean> missions=new LinkedBlockingQueue<AbsMissionBean>(); 
	//protected Map<String,AbsMissionBean> domission=new HashMap<String,AbsMissionBean>();
	protected List<LineService> l=new ArrayList<LineService>();
	protected Map<LineService,LineService> waitLinks=new HashMap<LineService,LineService>();
	protected SysInfo si;
	protected VoiceFileChange vfs;
	protected ConferenceService cs;
	protected LinkService ls;

	private InitService is;
	
	protected ManagerVoiceMailI mvmi;
	public AbsTelephoneSwitch()
	{
		
	}
	public void start()
	{
		boolean isright=is.init(new InitInfo());
		if(!isright)
			is.free();
		si=is.getSysInfo();
//		for(int i=0,size=si.getLines().size();i<size;i++)
//		{
//			LineService ls=(LineService)SpringRunningContainer.getInstance().getBean("LineService");
//			ls.setLi(si.getLines().get(i));
//			l.add(i, ls);
//		}
		is.assist();
		
		
		this.listen();
		this.action();
		this.initSub();
		System.out.println("初始化完成");
	}
	private void listen()
	{
		for(int i=0,size=si.getLineCount();i<size;i++)
		{
			LineService ls=(LineService)SpringRunningContainer.getInstance().getBean("LineService");
			ls.setLi(si.getLines().get(i));
			l.add(i,ls);
		}
		Listen l=new Listen();
		l.start();
	}
	class Listen extends Thread
	{
		/* (non-Javadoc)
		 * @see java.lang.Thread#run()
		 */
		@Override
		public void run() {
			// TODO Auto-generated method stub
			while(true)
			{
				for(int i=0,size=l.size();i<size;i++)
				{
					Queue tq=l.get(i).listener();
					q.addAll(tq);
					tq.clear();
				}
				try {
					this.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
	protected abstract void operate(EventBean eb);
	class Operator extends Thread
	{

		/* (non-Javadoc)
		 * @see java.lang.Thread#run()
		 */
		@Override
		public void run() {
			// TODO Auto-generated method stub
			while(true)
			{
				EventBean eb=null;
				try {
					eb = q.take();
					System.out.println(eb.getLineNum()+"  "+eb.getEt());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				operate(eb);
			}
		}
		
	}
//	class Mission extends Thread
//	{
//
//		/* (non-Javadoc)
//		 * @see java.lang.Thread#run()
//		 */
//		@Override
//		public void run() {
//			// TODO Auto-generated method stub
//			while(true)
//			{
//				AbsMissionBean mb=null;
//				try {
//					mb = missions.take();
//					System.out.println(mb.getLineNum()+"  "+mb.getMn());
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				EventBean eb=new EventBean();
//				eb.setEt(EventType.L_MISSION);
//				eb.setLineNum(mb.getLineNum());
//				eb.setParam("mission",mb);
//			}
//		}
//		
//	}
	private void action()
	{
		Operator o=new Operator();
		o.start();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#finalize()
	 */
	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
		is.free();
	}
	/**
	 * @return the cs
	 */
	public ConferenceService getCs() {
		return cs;
	}
	/**
	 * @param cs the cs to set
	 */
	public void setCs(ConferenceService cs) {
		this.cs = cs;
	}
	/**
	 * @return the is
	 */
	public InitService getIs() {
		return is;
	}
	/**
	 * @param is the is to set
	 */
	public void setIs(InitService is) {
		this.is = is;
	}
	
	/**
	 * @return the ls
	 */
	public LinkService getLs() {
		return ls;
	}
	/**
	 * @param ls the ls to set
	 */
	public void setLs(LinkService ls) {
		this.ls = ls;
	}
	/**
	 * @return the vfs
	 */
	public VoiceFileChange getVfs() {
		return vfs;
	}
	/**
	 * @param vfs the vfs to set
	 */
	public void setVfs(VoiceFileChange vfs) {
		this.vfs = vfs;
	}
	protected abstract void initSub();
	
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
	
	
	public void voiceMail() {
		// TODO Auto-generated method stub
		System.out.println("检测是否有新录音");
		for(int i=0,size=l.size();i<size;i++)
		{
			LineService ls=l.get(i);
			boolean b=false;
			try
			{
			b=mvmi.hasNewVoice(Integer.toString(ls.getLi().getLineNum()));
			}catch(Exception e)
			{
				e.printStackTrace();
			}
			if(b)
			{
				AbsMissionBean mb=(AbsMissionBean)SpringRunningContainer.getInstance().getBean("VlwMissionBean");
				mb.setLineNum(ls.getLi().getLineNum());
				mb.setLs(ls);
				mb.setMn("");
				//missions.add(mb);
				EventBean eb=new EventBean();
				eb.setEt(EventType.L_MISSION);
				eb.setLineNum(mb.getLineNum());
				eb.setParam("mission", mb);
				q.add(eb);
			}
			
		}
		
	}
	
}
