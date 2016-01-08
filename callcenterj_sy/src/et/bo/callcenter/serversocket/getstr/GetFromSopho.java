/**
 * ����׿Խ�Ƽ����޹�˾
 * 2008-4-28
 */
package et.bo.callcenter.serversocket.getstr;

import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import et.bo.callcenter.serversocket.panel.RefreshPanelService;
import et.bo.callcenter.serversocket.panel.bean.AgentStateBean;
import et.bo.common.mylog.MyLogPbx;
import excellence.common.util.time.TimeUtil;

/**
 * @author zhang feng
 * @author wangwenquan
 * 
 */
public class GetFromSopho {

	ExecutorService es = Executors.newFixedThreadPool(20);

	/**
	 * �Ƿ������嶯��
	 * 1)
	 * @param pbxPort
	 *            ��������Ӧ�Ķ˿ں�
	 * @return OK ��ʾ��ժ��״̬���� true ��ʾû���¼����� false
	 */
	public static boolean isRing(String pbxPort) {
		boolean flag = false;
		GetFromSopho gfs = new GetFromSopho();
	
		String state = gfs.isRingState(pbxPort);
		//String state = "isRing";
		MyLogPbx.info("����isRing("+pbxPort+") ���؂���:"+state+":"+new Date());
		if ("isRing".equals(state)) {
			flag = true;
		} else {
			MyLogPbx.info(pbxPort+":ȡ������״̬����:" + state);
		}
		
		return flag;
	}
	//�����õĲ���ʱɾ��############################################
	private static class RingRunnable implements Runnable
	{
		String pbxPort = null;
		public RingRunnable(String pbxPort)
		{
			this.pbxPort = pbxPort;
		}
		public void run()
		{
			AgentStateBean bean = RefreshPanelService.agentStateMap
			.get(pbxPort);
			while(true)
			{
				if("1".equals(bean.getAlertingCall()))
				{
					//System.out.println(bean.getAgentNum()+":alertingCall."+bean.getAlertingCall()+":offHook."+bean.getOffhook());
					//System.out.println("RingRunnable �߳��˳�");
					break;
				}
				/*
				try {
					Thread.sleep(150);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				*/
			}
		}
	}
	/**
	 * �����̵߳ȴ�����״̬����
	 * 
	 * @param pbxPort
	 * @return isRing ������ null ����״̬
	 */
	private String isRingState(String pbxPort) {
		GetState gs = new GetState(pbxPort,15000);
		gs.setPbxPort(pbxPort);
//		System.out.println(pbxPort + ">>>>>>..........");
		gs.setIsWaitForRing(true);
		gs.setIsWartForOffHook(false);
		gs.setIsWaitForOnHook(false);
		Future f = es.submit(gs);
		try {
//			System.out.println(f.get() + "................>>>>>>>>>");
			return f.get().toString();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ��������״̬��ժ��״̬�Ƿ���������������������Ӧ�ĵ�������
	 * 2)
	 * @param pbxPort
	 *            ��������Ӧ�Ķ˿ں�
	 * @return OK ��ʾ��ժ��״̬���� true ��ʾû���¼����� false
	 */
	public static boolean getStateRingToOffHook(String pbxPort) {
		boolean flag = false;

		//String state = "isOffHook";
		GetFromSopho gfs = new GetFromSopho();
		String state = gfs.isOffHookState(pbxPort);
		MyLogPbx.info("����ժ��getStateRingToOffHook("+pbxPort+") ���؂���:"+state+":"+new Date());
		if ("isOffHook".equals(state)) {
			flag = true;
		} else {
			MyLogPbx.info(pbxPort+":ȡ������->ժ��״̬����:" + state);
		}
		return flag;
	}
//	�����õĲ���ʱɾ��############################################
	private static class RingToOffHookRunnable implements Runnable
	{
		String pbxPort = null;
		public RingToOffHookRunnable(String pbxPort)
		{
			this.pbxPort = pbxPort;
		}
		public void run()
		{
			AgentStateBean bean = RefreshPanelService.agentStateMap
			.get(pbxPort);
			while(true)
			{
				if("1".equals(bean.getOffhook()))
				{
					//System.out.println(bean.getAgentNum()+":alertingCall."+bean.getAlertingCall()+":offHook."+bean.getOffhook());
					//System.out.println("RingToOffHookRunnable �߳��˳�");
					break;
				}
				/*
				try {
					Thread.sleep(150);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				*/
			}
		}
	}
	/**
	 * ����ժ��״̬���һ�״̬�Ƿ���������������������Ӧ�Ĺһ�״̬
	 * 
	 * @param pbxPort
	 *            ��������Ӧ�Ķ˿ں�
	 * @return OK ��ʾ��ժ��״̬���� true ��ʾû���¼����� false
	 */
	public static boolean getStateOffHookToOnHook(String pbxPort) {
		boolean flag = false;
		GetFromSopho gfs = new GetFromSopho();
		String state = gfs.isOnHookState(pbxPort);
		MyLogPbx.info("���ùһ�getStateOffHookToOnHook("+pbxPort+") ���؂���:"+state+":"+new Date());
		if ("isOnHook".equals(state)) {
			flag = true;
		} else {
			MyLogPbx.info(pbxPort+":ȡ��ժ��->�һ�״̬����:" + state);
		}
		return flag;
	}

	/**
	 * �����̵߳ȴ��һ�״̬����
	 * 
	 * @param pbxPort
	 * @return isOffHook �һ��� null ����״̬
	 */
	private String isOnHookState(String pbxPort) {
		GetState gs = new GetState(pbxPort,600000);
		gs.setPbxPort(pbxPort);
		gs.setIsWaitForRing(false);
		gs.setIsWartForOffHook(false);
		gs.setIsWaitForOnHook(true);
		Future f = es.submit(gs);
		try {
			return f.get().toString();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * �����̵߳ȴ�ժ��״̬����
	 * 
	 * @param pbxPort
	 * @return isOffHook ժ���� null ����״̬
	 */
	private String isOffHookState(String pbxPort) {
		GetState gs = new GetState(pbxPort,30000);
		gs.setPbxPort(pbxPort);
		gs.setIsWaitForRing(false);
		gs.setIsWartForOffHook(true);
		gs.setIsWaitForOnHook(false);
		Future f = es.submit(gs);
		try {
			return f.get().toString();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	// ���״̬��ȡ�÷���ֵ
	
	private class GetState implements Callable {
		private String pbxPort = null;
		private long threadSleepTime = 150;
		private long milTimeout = -1;
		public void setPbxPort(String pbxPort) {
			this.pbxPort = pbxPort;
			
		}
		public GetState(String pbxPort, long milTimeout) {
			this.pbxPort = pbxPort;
			this.milTimeout = milTimeout;
		}
		private boolean isWaitForRing;

		private boolean isWaitForOffHook;

		private boolean isWaitForOnHook;

		public void setIsWaitForRing(boolean isWaitForRing) {
			this.isWaitForRing = isWaitForRing;
		}

		public void setIsWartForOffHook(boolean isWaitForOffHook) {
			this.isWaitForOffHook = isWaitForOffHook;
		}

		public void setIsWaitForOnHook(boolean isWaitForOnHook) {
			this.isWaitForOnHook = isWaitForOnHook;
		}

		public Object call() throws Exception {
			// TODO Auto-generated method stub
			int curCount = 0;
			while (true) {
				AgentStateBean bean = RefreshPanelService.agentStateMap.get(pbxPort);
				Date alertingDate = bean.getAlertingTime();
				Date offhookDate = bean.getOffhookTime();
				Date onhookDate = bean.getOnhookTime();
				if (bean == null) {
					throw new NullPointerException(
							"*RefreshPanelService.agentStateMap.get(" + pbxPort
									+ ")���ص�AgentStateBeanΪNULL����*");
				}
				if (isWaitForRing) {
					//System.out.println(bean.getAgentNum()+":***********:"+bean.getAlertingCall());
					if ("1".equals(bean.getAlertingCall())) {
						//System.out.println(bean.getAgentNum()+"isWaitForRing>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<"+TimeUtil.getTheTimeStr(new Date()));
						return "isRing";
					}
				}
				if (isWaitForOffHook) {
					if ("1".equals(bean.getOffhook())) {
						return "isOffHook";
					}
				}
				if (isWaitForOnHook) {
					if ("1".equals(bean.getOnhook())) {
						return "isOnHook";
					}
				}
				//timeout != -1 �趨��ʱ
				if(milTimeout!=-1)
				{
					if(curCount*threadSleepTime>milTimeout)
					{
						return "timeoutError";
					}
					curCount++;
				}
				
				//System.out.println("############################");
				Thread.sleep(threadSleepTime);
			}
		}

	}
	
	public static void main(String[] args) {
		GetFromSopho.isRing("8902");
	}

}
