package et.bo.callcenter.bo.experter.impl;

import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;

public class OperExpertorSearch {
	public MyQuery experterSearch() {
		MyQuery mq = new MyQueryImpl();
		String hql = "from OperCustinfo a where a.dictCustType = '"+"SYS_TREE_0000000684"+"' order by a.custName";
		mq.setHql(hql);
		return mq;
	}
}
