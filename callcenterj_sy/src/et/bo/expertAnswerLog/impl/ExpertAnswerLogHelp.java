package et.bo.expertAnswerLog.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import et.po.OperCustinfo;
import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;

public class ExpertAnswerLogHelp {
	public MyQuery operCustQuery(String telNum){
		
		MyQuery mq = new MyQueryImpl();
		String hql = "from OperCustinfo where custTelHome = '"+telNum+"' or custTelMob = '"+telNum+"' or custTelWork = '"+telNum+"'";
		mq.setHql(hql);
		
		return mq;
	}
}
