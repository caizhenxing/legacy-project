package et.bo.xml;

import java.util.HashMap;

import javax.sql.RowSet;

import et.bo.callcenter.cclog.service.TelQueryService;
import et.bo.screen.service.ScreenService;
import excellence.framework.base.container.SpringRunningContainer;
import excellence.framework.base.dao.BaseDAO;

public class CallLogStaticXmlDWR {
	
	private ScreenService ss = (ScreenService)SpringRunningContainer.getInstance().getBean("ScreenService");
	public void createXml(){
		ss.createXml();
	}
}
