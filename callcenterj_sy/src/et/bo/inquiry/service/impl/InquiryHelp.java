/**
 * 	InquiryHelp.java   2008-4-2 ����09:36:38
 *	��Ȩ���� ������׿Խ�Ƽ����޹�˾�� 
 *	׿Խ�Ƽ� ����һ��Ȩ��
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
 * @author ���Ʒ�
 * 
 */
public class InquiryHelp {
	Logger log = Logger.getLogger(InquiryHelp.class);
	//���鿨���ͳ�������
	public static final Map<String, String> QUESTIONTYPE = new TreeMap<String, String>();
	static {
		QUESTIONTYPE.put("---", "��ѡ��");
		QUESTIONTYPE.put("001", "��ѡ��");
		QUESTIONTYPE.put("002", "��ѡ��");
		QUESTIONTYPE.put("003", "�ʴ���");
	}

	/**
	 * ���ݵ��鿨���ͳ������Ϸ��ض�̬Bean����
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
	 * �����������ʹ��뷵�ض�Ӧ������������������
	 * @param value �������ʹ���
	 * @return ����������������
	 */
	public static String getLabelByValue(String value) {
		return QUESTIONTYPE.get(value);
	}

	/**
	 * ���ݲ�ѯ��������ҳ������Ϣ����MyQuery����
	 * @param dto ��ѯ������Ϣ
	 * @param pi ҳ��������Ϣ
	 * @param rootNodeId �����������ĸ�ID
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
	 * �������߲���Ĳ�ѯ����
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
	 * �������߲���Ĳ�ѯ���� �ʾ�������Ϣ��
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
