package et.bo.common;

import java.util.HashMap;
import java.util.Map;

import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;

/**
 * 省市县级联选择
 * 
 * @author zhangfeng
 * 
 */
public class ExpertDAOImpl {

	private BaseDAO dao = null;

	public BaseDAO getDao() {
		return dao;
	}

	public void setDao(BaseDAO dao) {
		this.dao = dao;
	}

	public Map getExpert(String expertType) {
		Map map = new HashMap();
		MyQuery mq = new MyQueryImpl();
		String hql = "select custName from OperCustinfo a where a.expertType = '"+expertType+"' order by a.custName asc";
		mq.setHql(hql);
		Object[] o = dao.findEntity(mq);
		for (int i = 0; i < o.length; i++) {
			String custName = (String)o[i];
			if(custName != null){
				map.put(custName, custName);
			}
		}
		
		return map;
	}

}
