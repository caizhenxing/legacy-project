package et.test;


import java.util.List;

import ocelot.flow.msg.FlowMsgService;
import ocelot.flow.msg.MsgBean;
import ocelot.flow.msg.impl.FlowMsgServiceMapImpl;

import et.workflow.jbpm.impl.MsgBeanJbpm;

public class TestT extends Thread {

	FlowMsgService fms=new FlowMsgServiceMapImpl();
	@Override
	public void run() {
		// TODO Auto-generated method stub
		MsgBean mb=new MsgBean();
		MsgBeanJbpm mbj=new MsgBeanJbpm();
		mbj.setId("1");
		mbj.setMessageInfo("1");
		mbj.setMessageType("2");
		mbj.setActor("22");
		mb.setDest("me");
		mb.setMsg(mbj.getMsg());
		fms.sendMsg(mb);
		List<MsgBean> l=fms.receiveMsg("me",false);
		
		System.out.println(l.size());
	}
	public static void main(String[] arg0)
	{
		TestT tt1=null;
		for(int i=0;i<15;i++)
		{
			tt1=new TestT();
			tt1.start();
		}
		List<MsgBean> l=tt1.fms.receiveMsg("me",true);
		
		System.out.println("total"+l.size());
	}
}
