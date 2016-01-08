package et.test.callcenter.server;

import et.bo.callcenter.base.OperatorState;
import et.bo.callcenter.server.Server;
import et.bo.common.testing.MyTesting;

public class ServerTesting extends MyTesting {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new ServerTesting().initServerTest();
	}
	public void initServerTest(){
		ServerTesting st =new ServerTesting();
		st.testing();
		Server s =(Server)st.springContainer.getBean("ServerService");
//		OperatorState os = (OperatorState)st.springContainer.getBean("OperatorStateService");		
		s.runServer();
//		try{
//			Thread.sleep(10000);
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//		OperatorState.setOperatorStateLogon("001", "192.168.1.3");
	}
	public void initServer(){
		ServerTesting st =new ServerTesting();
		st.testing();
		Server s =(Server)st.springContainer.getBean("ServerService");
//		OperatorState os = (OperatorState)st.springContainer.getBean("OperatorStateService");		
		s.runServer();
//		try{
//			Thread.sleep(10000);
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//		OperatorState.setOperatorStateLogon("001", "192.168.1.3");
	}
}
