/**
 * 	@(#)TestBank.java   2007-1-8 ÉÏÎç09:05:43
 *	 ¡£ 
 *	 
 */
package et.bo.callcenter.plant.dongjin_c161a.test;

import java.util.Timer;
import java.util.TimerTask;

import excellence.framework.base.container.SpringContainer;
import excellence.framework.base.container.SpringRunningContainer;

 /**
 * @author zhaoyifei
 * @version 2007-1-8
 * @see
 */
public class TestBank {

	/*IAcceptMessage iam;
	class Tt extends TimerTask
	{
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			iam.accept();
		}
		
	}*/
	//Tt t=new Tt();
	/**
	 * @param
	 * @version 2007-1-8
	 * @return
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SpringContainer sc=SpringContainer.getInstance();
		//TestBank tb=new TestBank();
		
		IInitDongjin iid=(IInitDongjin)SpringRunningContainer.getInstance().getBean("IInitDongjin");
		try {
			iid.init();
//			iid.connect(14,13);
//			iid.getInitInfo();
//			while(true)
//			{
//				iid.trunkRingAndHook();
//			}
//			iid.ring(13);
//			while(true)
			iid.playIndex();
//			iid.recordAndPlay(14);
//			iid.receiveAndSendDtmf(13,12);
//			iid.checkSig(2);
//			iid.receiveFsk(2);
//			iid.changewavFile();
//			iid.recordAndPlayMemory(14);
//			iid.memoryPlay(14);
//			iid.conf();
//			iid.linkthree(12,14,15);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}finally
		{
			iid.exit();
		}
		//tb.iam=(IAcceptMessage)SpringRunningContainer.getInstance().getBean("IAcceptMessage");
		
		//Timer timer=new Timer();
		
		//timer.schedule(tb.t, 100);
		
	}

}