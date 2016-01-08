package et.bo.xml;

import java.util.HashMap;

import javax.sql.RowSet;

import et.bo.callcenter.cclog.service.TelQueryService;
import excellence.framework.base.container.SpringRunningContainer;
import excellence.framework.base.dao.BaseDAO;

public class TelStaticXmlDWR {
	
	private TelQueryService tq = (TelQueryService)SpringRunningContainer.getInstance().getBean("telQueryService");
	public void createXml(){
		tq.createXml();
	}
}
