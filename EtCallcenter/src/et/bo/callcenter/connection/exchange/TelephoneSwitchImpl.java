/**
 * 	@(#)TelephoneSwitchImpl.java   2007-1-29 下午03:38:53
 *	 。 
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
		if(l.get(eb.getLineNum()).getLi().getMb()!=null)//如果端口有某个任务在执行，调用任务处理事件接口
		{
			AbsMissionBean mb=l.get(eb.getLineNum()).getLi().getMb();
			mb.operate(eb);
			return;
		}
		if(eb.getEt().equals(EventType.L_MISSION))//有任务
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
		if(eb.getEt().equals(EventType.T_RING))//外线振铃
		{
			LineService ls=l.get(eb.getLineNum());
			ls.offHook();//接电话
			ls.startPlayFile(welcomeFile);//播放欢迎音乐
		}
		if(eb.getEt().equals(EventType.T_SIG))//信号音，多种类型，具体看参数
		{
			//不做
		}
		if(eb.getEt().equals(EventType.T_POLARITY))//极性反转
		{
//			不做
		}
		if(eb.getEt().equals(EventType.T_DIAL))//外线拨号
		{
//			不做
		}
		if(eb.getEt().equals(EventType.T_CONNECT))//外线拨通
		{
//			不做
		}
		if(eb.getEt().equals(EventType.T_HANGUP))//外线拨出的电话挂机
		{
			LineService lis=l.get(eb.getLineNum());
			if(waitLinks.containsValue(lis))//如果有拨号电话，停止振铃。清除状态
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
			if(ls.contain(lis))//如果正在通话，清除连接
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
			 * 语音信箱－录音模块修改
			 * 注释一下
			 */
//			if(lis.getLi().getSubState().equals("record"))//如果正在录音，停止录音，清除状态
//			{
//				lis.stopRecordFile();
//				lis.getLi().setSubState("");
//				
//			}
			lis.hangUp();//外线挂机
		}
		if(eb.getEt().equals(EventType.U_OFFHOOK))//内线提机
		{
			LineService ls=l.get(eb.getLineNum());
			if(ls.getLi().getLineState().equals(LineState.U_OFFHOOK))//设置状态
			ls.startPlaySignal(DongjinParameter.SIG_DIALTONE);//播放拨号音
			
		}
		if(eb.getEt().equals(EventType.U_OFFHOOK_BYRING))//内线振铃提机
		{
			LineService lis=l.get(eb.getLineNum());
			lis.endTime();
			ls.setLink(lis, waitLinks.get(lis));//接通电话
			waitLinks.remove(lis);
		}
		if(eb.getEt().equals(EventType.U_HANGUP))//内线挂机
		{
			LineService lis=l.get(eb.getLineNum());
			if(waitLinks.containsValue(lis))//如果等待通话，则停止振铃，清除状态
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
			if(ls.contain(lis))//通话中，清除连接。
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
			 * 语音信箱－录音模块修改
			 * 注释一下
			 */
//			if(lis.getLi().getSubState().equals("record"))//录音中，停止录音，清除状态
//			{
//				lis.stopRecordFile();
//				lis.getLi().setSubState("");
//			}
		}
		if(eb.getEt().equals(EventType.L_RECEDTMF))//有dtmf
		{
			LineService ls=l.get(eb.getLineNum());
			ls.receiveDtmf();//收码
			if(!ls.getLi().getLineState().equals(LineState.L_FREE)&&!ls.getLi().getLineState().equals(LineState.L_LINK))
				parseDtmf(ls.getLi().getDtmf(),ls);//分析码
		}
		if(eb.getEt().equals(EventType.L_SENDDTMFEND))//发送完毕
		{
			//不做
		}
		if(eb.getEt().equals(EventType.L_RECEFSK))//有fsk
		{
			//不做
		}
		if(eb.getEt().equals(EventType.L_PLAYEND))//放音结束
		{
			
			LineService ls=l.get(eb.getLineNum());
			ls.stopPaly();//停止放音
			if(ls.getLi().getLineType().equals(LineType.OUT_LINE))
				ls.getLi().setLineState(LineState.T_OFFHOOK);//设置状态
			/**
			 * 语音信箱－录音模块修改
			 * 注释一下
			 */
//			if(ls.getLi().getSubState().equals("waitrecord"))//如果等待录音
//			{
//				String fileName="d:/zhaoyifei/"+TimeUtil.getNowSTime()+".record";
//				ls.startRecordFile(fileName);
//				mvmi.addVoice(Integer.toString(ls.getLineNum()),fileName);
//				ls.getLi().setSubState("record");//开始录音
//				
//				
//			}
		}
		
		if(eb.getEt().equals(EventType.U_HOOKFLASH))//拍叉
		{
			//不做
		}
		
		/**
		 * 语音信箱－录音模块修改
		 * 注释一下
		 */
		if(eb.getEt().equals(EventType.L_TIMEOUT))//超时
		{
			LineService lis=l.get(eb.getLineNum());
			LineService lis1=waitLinks.get(lis);
//			lis.feedPower();//停止振铃
			
//			lis1.startPlaySignal(DongjinParameter.SIG_STOP);//停止播回铃音
//			lis1.startPlayFile(recordFile);//播放等待录音音乐
//			lis1.getLi().setSubState("waitrecord");//设置状态
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
	 * 分析dtmf
	 * @param
	 * @version 2007-2-13
	 * @return
	 */
	private void parseDtmf(char[] dtmf,LineService ls1)
	{
		if(dtmf.length==0)//没有dtmf，返回
			return;
		if(dtmf[0]=='1')//拨1，找到外线连通
		{
			if(ls1.getLi().getLineType().equals(LineType.OUT_LINE))//如果是外线电话则不理会
			{
				//ls1.startPlaySignal(DongjinParameter.SIG_BUSY1);
				//
				return ;
			}
			boolean hasfree=false;
			//找一条空闲外线连接
			for(LineService lss:l)//内线电话就开始找空闲外线
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
			if(!hasfree)//如果没有空闲外线则播忙音
				ls1.startPlaySignal(DongjinParameter.SIG_BUSY1);
			return ;
		}
		if(tels.containsKey(new String(dtmf)))//如果内线有对应电话号码，则内线连接
		{
			if(tels.get(new String(dtmf)).getLi().getLineState().equals(LineState.L_FREE))//如果内线空闲，振铃，开始计时
			{
				//可以送来电
				tels.get(new String(dtmf)).feedRealRing();
				tels.get(new String(dtmf)).startTime(10*15);
				waitLinks.put(tels.get(new String(dtmf)),ls1 );
				if(ls1.getLi().getLineType().equals(LineType.IN_LINE))
				ls1.startPlaySignal(DongjinParameter.SIG_RINGBACK);
				else
					ls1.startPlayFile(welcomeFile);
				
			}
			else//否则播忙音
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
	 * 初始化电话号码
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
