package et.test;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.net.ServerSocket;
import java.net.Socket;

import et.bo.callcenter.serversocket.panel.OperAgentInfoService;
import excellence.framework.base.container.SpringContainer;

public class TestImpl {
	public static void main(String[] args)
	{
		/*
		ClassBaseTreeService cb = (ClassBaseTreeService)SpringContainer.getInstance().getBean("ClassBaseTreeService");
		cb.loadParamTree();
		List<IVRBean> ivrBeanList = cb.getListById("SYS_TREE_0000000422");
		System.out.println(ivrBeanList.size());
		for(int i=0; i<ivrBeanList.size(); i++)
		{
			IVRBean bean = ivrBeanList.get(i);
			System.out.println(bean.getContent()+bean.getFunctype());
		}
		*/
		/*
		ClassBaseTreeService service = (ClassBaseTreeService)SpringContainer.getInstance().getBean("ClassBaseTreeService");
		IVRBean bean = service.getIVRBeanById("SYS_TREE_0000000041");
		System.out.println(bean);
		service.getListById("SYS_TREE_0000000181");
		IVRBean bean1 = service.getIVRBeanById("SYS_TREE_0000000181");
		System.out.println(bean1.getIvrList().size()+":"+"size");
		System.out.println("***************************");
		*/
		//TestImpl ti = new TestImpl();
		//ti.startAcept();
		OperAgentInfoService oper = (OperAgentInfoService)SpringContainer.getInstance().getBean("OperAgentInfoService");
		oper.queryCcAgentInfo("111", "2008-01-01");
	}
	
	public void startAcept()
	{
		Socket socket = null;
		try
		{
			ServerSocket sSocket=new ServerSocket(12000);
			socket =sSocket.accept();
			StringBuffer sb = new StringBuffer();
			DataInputStream is= new DataInputStream(new BufferedInputStream(socket.getInputStream()));
			String inputLine = "";
			int i;
			//System.out.println("server prepare accept msg");
			while((i=is.read()) != 59) 
			{
                //System.out.println(i);
                if(i == -1)
                {
                    continue;
                }
                sb.append((char)i);
            }
			System.out.println("服务器收到信息为"+sb.toString());
			if(sb.toString().indexOf("NETTST")==-1)
			{
				System.out.println("服务器收到信息为"+sb.toString());
			}

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
