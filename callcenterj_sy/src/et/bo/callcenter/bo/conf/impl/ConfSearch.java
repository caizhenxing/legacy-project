/**
 * 沈阳卓越科技有限公司
 * 2008-6-10
 */
package et.bo.callcenter.bo.conf.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import et.po.EasyConfChannelState;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;

/**
 * @author zhang feng
 * 
 */
public class ConfSearch extends MyQueryImpl {

	/**
	 * 查询会议列表
	 * 
	 * @param roomno
	 *            会议编号
	 * @return
	 */
	public MyQuery confdeploy(String roomno) {
		MyQuery mq = new MyQueryImpl();
		DetachedCriteria dc = DetachedCriteria.forClass(EasyConfChannelState.class);
		if(roomno==null)
		{
			roomno="";
		}
		if (!"-1".equals(roomno.trim())) {
			dc.add(Restrictions.eq("roomno",Integer.parseInt(roomno)));
		}
		dc.add(Restrictions.eq("deleteMark", "Y"));
		dc.addOrder(Order.asc("channelno"));
		mq.setDetachedCriteria(dc);
		return mq;
	}
}
