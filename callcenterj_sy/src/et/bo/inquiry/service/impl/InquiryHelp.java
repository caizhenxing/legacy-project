/**
 * 	InquiryHelp.java   2008-4-2 上午09:36:38
 *	版权所有 沈阳市卓越科技有限公司。 
 *	卓越科技 保留一切权利
 */
package et.bo.inquiry.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import et.po.OperInquiryinfo;
import excellence.common.page.PageInfo;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;

/**
 * @author 梁云锋
 * 
 */
public class InquiryHelp {
	Logger log = Logger.getLogger(InquiryHelp.class);
	//调查卡题型常量集合
	public static final Map<String, String> QUESTIONTYPE = new TreeMap<String, String>();
	static {
		QUESTIONTYPE.put("---", "请选择");
		QUESTIONTYPE.put("001", "单选题");
		QUESTIONTYPE.put("002", "多选题");
		QUESTIONTYPE.put("003", "问答题");
	}

	/**
	 * 根据调查卡题型常量集合返回动态Bean集合
	 * @return
	 */
	public static List<DynaBeanDTO> getQTList() {
		List<DynaBeanDTO> list = new ArrayList<DynaBeanDTO>();
		Set<Entry<String, String>> set = QUESTIONTYPE.entrySet();
		for (Iterator<Entry<String, String>> i = set.iterator(); i.hasNext();) {
			DynaBeanDTO dto = new DynaBeanDTO();
			Entry<String, String> entry = i.next();
			dto.set("value", entry.getKey());
			dto.set("label", entry.getValue());
			list.add(dto);
		}
		return list;
	}

	/**
	 * 根据问题类型代码返回对应的问题类型文字描述
	 * @param value 问题类型代码
	 * @return 问题类型文字描述
	 */
	public static String getLabelByValue(String value) {
		return QUESTIONTYPE.get(value);
	}

	/**
	 * 根据查询条件、分页配置信息创建MyQuery对象
	 * @param dto 查询条件信息
	 * @param pi 页面配置信息
	 * @param rootNodeId 主题类别参数的跟ID
	 * @return
	 */
	public MyQuery query(IBaseDTO dto, PageInfo pi, String rootNodeId) {
		MyQuery mq = new MyQueryImpl();
		DetachedCriteria dc = DetachedCriteria.forClass(OperInquiryinfo.class);
		
		String type = (String) dto.get("inquiryType");
		String topic = (String) dto.get("topic");
		String organiztion = (String) dto.get("organiztion");
		String organizers = (String) dto.get("organizers");
		String beginTime = (String) dto.get("beginTime");
		String endTime = (String) dto.get("endTime");
		
		if (!type.equals("")) {
			dc.add(Restrictions.eq("dictInquiryType", type));
		}
		if (!topic.equals("")) {
			dc.add(Restrictions.like("topic", "%" + topic + "%"));
		}
		if (!organiztion.equals("")) {
			dc.add(Restrictions.like("organiztion", "%" + organiztion + "%"));
		}
		if (!organizers.equals("")) {
			dc.add(Restrictions.like("organizers", "%" + organizers + "%"));
		}
		if (!beginTime.equals("")) {
			dc.add(Restrictions.ge("beginTime", TimeUtil.getTimeByStr(beginTime, "yyyy-MM-dd")));
		}
		if (!endTime.equals("")) {
			dc.add(Restrictions.le("endTime", TimeUtil.getTimeByStr(endTime, "yyyy-MM-dd")));
		}
		String state = (String) dto.get("state");
		if(!state.equals("")){
			dc.add(Restrictions.eq("state", state));
		}
		String reportState = (String) dto.get("reportState");
		if(!reportState.equals("")){
			dc.add(Restrictions.eq("reportState", reportState));
		}
		dc.addOrder(Order.desc("endTime"));
		
		mq.setDetachedCriteria(dc);
		mq.setFirst(pi.getBegin());
		mq.setFetch(pi.getPageSize());
		return mq;
	}
	
	public MyQuery queryLast() {
		
		MyQuery mq = new MyQueryImpl();
		DetachedCriteria dc = DetachedCriteria.forClass(OperInquiryinfo.class);
		
		dc.add(Restrictions.isNotNull("reportTopic"));
		dc.add(Restrictions.isNotNull("reportContent"));
		dc.addOrder(Order.desc("endTime"));		
		mq.setDetachedCriteria(dc);
		mq.setFirst(1);
		return mq;
	}

	/**
	 * 生成热线参与的查询对象
	 * @return
	 */
	public MyQuery filter() {
		MyQuery mq = new MyQueryImpl();
		DetachedCriteria dc = DetachedCriteria.forClass(OperInquiryinfo.class);
		Date date = new Date();
		dc.add(Restrictions.ge("endTime", date));
		dc.add(Restrictions.le("beginTime", date));
		dc.addOrder(Order.desc("id"));
		mq.setDetachedCriteria(dc);
		return mq;
	}
	/**
	 * 生成热线参与的查询对象 问卷主题信息表
	 * add wwq
	 * @return
	 */
	public MyQuery myFilter() {
		MyQuery mq = new MyQueryImpl();
		DetachedCriteria dc = DetachedCriteria.forClass(OperInquiryinfo.class);
		Date date = new Date();
		dc.add(Restrictions.ge("endTime", date));
		dc.add(Restrictions.le("beginTime", date));
		dc.addOrder(Order.desc("id"));
		mq.setDetachedCriteria(dc);
		return mq;
	}
}
