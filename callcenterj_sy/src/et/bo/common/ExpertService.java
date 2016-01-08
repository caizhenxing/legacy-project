package et.bo.common;

import java.util.Map;

import excellence.framework.base.container.SpringRunningContainer;


public class ExpertService {
	
	private ExpertDAOImpl pd = (ExpertDAOImpl)SpringRunningContainer.getInstance().getBean("ExpertDAOImpl");
	//����ͨ����¼
	private ThreeCallService tcs = (ThreeCallService)SpringRunningContainer.getInstance().getBean("ThreeCallService");
	
	public Map getExpert(String expertType){
//		System.out.println(expertType);
		return pd.getExpert(expertType);
	}
	
	public void insertThreeCall(String agcid){
		tcs.insertThreeCall(agcid);
	}
	
}
