/*
 * @(#)CallbackServiceImpl.java	 2008-04-01
 *
 * 版权所有 沈阳市卓越科技有限公司。
 */
package et.bo.incommingInfo.service.impl;

import java.util.Calendar;
import java.util.Date;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import et.po.CcIvrTreevoiceDetail;
import et.po.OperQuestion;
import excellence.common.page.PageInfo;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;

/**
 * <p>
 * 来电信息查询帮助
 * </p>
 * 
 * @version 2008-06-13
 * @author wangwenquan
 */
public class IncommingInfoHelp extends MyQueryImpl {

	private String addoneDate(String endTime) {
		Date date = new Date();
		Calendar ca = Calendar.getInstance();
		ca.setTime(TimeUtil.getTimeByStr(endTime, "yyyy-MM-dd"));
		ca.add(ca.DATE, 1);
		date = ca.getTime();
		endTime = TimeUtil.getTheTimeStr(date, "yyyy-MM-dd HH:mm:ss");
		return endTime;
	}

	private String addHMS(String stime) {
		String hmsTime = "";
		hmsTime = stime + " 00:00:00";
		return hmsTime;
	}

	// /**
	// * @describe 取得端口对照表所有记录
	// */
	/*
	 * 排序查询的
	 */
	public MyQuery operIncommingInfoQuery(IBaseDTO dto, PageInfo pi) {
		MyQuery mq = new MyQueryImpl();
		// DetachedCriteria dc=DetachedCriteria.forClass(OperQuestion.class);

		// String hql = "select a.telNum,b.addtime, b.dictQuestionType1,
		// c.respondent,b.questionContent,b.answerContent ,a.id,b.id,c.id"
		// + " from CcMain a, OperQuestion b, CcTalk c"
		// + " where b.ringBegintime = a.ringBegintime and a.id = c.ccMain.id";
//		select count(*),oc.cust_addr from cc_talk ct,oper_question oq,cc_main cm,oper_custinfo oc
//		where ct.cclog_id = cm.id and oq.ring_begintime = cm.ring_begintime 
//		and ct.respondent = oq.answer_agent and oq.cust_id = oc.cust_id  and oc.cust_addr like '%'+@qAddress+'%'
//		and ct.TOUCH_BEGINTIME >= @begintime and ct.TOUCH_BEGINTIME <= @endtime
		String hql = "from OperQuestion oq where oq.isDelete=0 ";
		String tel_num = (String) dto.get("tel_num");
		if (tel_num != null && !"".equals(tel_num.trim())) {
			hql = hql + " and oq.custTel like '" + tel_num.trim() + "%'";
			// dc.add(Restrictions.like("custTel", "'%"+tel_num+"%'"));
		}
		String respondent = (String) dto.get("respondent");
		if (respondent != null && !"".equals(respondent.trim())) {
			hql = hql + " and oq.answerAgent like '" + respondent.trim() + "%'";
			// dc.add(Restrictions.like("custTel", "'%"+tel_num+"%'"));
		}
		String questionContent = (String) dto.get("questionContent");
		if (questionContent != null && !"".equals(questionContent.trim())) {
			hql = hql + " and oq.questionContent like '%" + questionContent.trim()
					+ "%'";
			// dc.add(Restrictions.like("custTel", "'%"+tel_num+"%'"));
		}
		String addtimeBegin = (String) dto.get("addtimeBegin");
		if (addtimeBegin == null) {
			addtimeBegin = "";
		}
		String addtimeEnd = (String) dto.get("addtimeEnd");
		if (addtimeEnd == null) {
			addtimeEnd = "";
		}
		if (!"".equals(addtimeBegin.trim()) && !"".equals(addtimeEnd.trim())) {
			hql = hql + " and  Convert(varchar(10),oq.addtime,120) between '"
					+ addtimeBegin.trim() + "' and '" + addtimeEnd.trim() + "'";
		} else if (!"".equals(addtimeBegin.trim())) {
			hql = hql + " and  Convert(varchar(10),oq.addtime,120) like '"
					+ addtimeBegin.trim() + "%'";
		} else if (!"".equals(addtimeEnd.trim())) {
			hql = hql + " and  Convert(varchar(10),oq.addtime,120) like '"
					+ addtimeEnd.trim() + "%'";
		}

		String dictQuestionType1 = (String) dto.get("dictQuestionType1");
		if (dictQuestionType1 != null && !"".equals(dictQuestionType1.trim())) {
			hql = hql + " and oq.dictQuestionType1 like '"
					+ dictQuestionType1.trim() + "%'";
		}
		String dictIsAnswerSucceed = (String) dto.get("dictIsAnswerSucceed");
		if (dictIsAnswerSucceed != null
				&& !"".equals(dictIsAnswerSucceed.trim())) {
			hql = hql + " and oq.dictIsAnswerSucceed like '"
					+ dictIsAnswerSucceed.trim() + "%'";
		}
		String answerMan = (String) dto.get("answerMan");
		if (answerMan != null && !"".equals(answerMan.trim())) {
			hql = hql + " and oq.answerMan like '" + answerMan.trim() + "%'";
		}
		String dictIsCallback = (String) dto.get("dictIsCallback");
		if (dictIsCallback != null && !"".equals(dictIsCallback.trim())) {
			hql = hql + " and oq.dictIsCallback like '" + dictIsCallback.trim()
					+ "%'";
		}
		String billNum = (String) dto.get("billNum");
		if (billNum != null && !"".equals(billNum.trim())) {
			hql = hql + " and oq.billNum like '" + billNum.trim() + "%'";
		}
		String expertName = (String) dto.get("expertName");
		if (expertName != null && !"".equals(expertName.trim())) {
			hql = hql + " and oq.expertName like '" + expertName.trim() + "%'";
		}

		// 加排序
		hql = hql + " order by oq.addtime desc";

		//System.out.println(hql+" .......hql");

		mq.setHql(hql);
		// mq.setDetachedCriteria(dc);
		mq.setFirst(pi.getBegin());
		mq.setFetch(pi.getPageSize());
		return mq;
	}
	/*
	 * 排序查询的
	 */
	public MyQuery operIncommingInfoQueryForSize(IBaseDTO dto, PageInfo pi) {
		MyQuery mq = new MyQueryImpl();
		// DetachedCriteria dc=DetachedCriteria.forClass(OperQuestion.class);

		// String hql = "select a.telNum,b.addtime, b.dictQuestionType1,
		// c.respondent,b.questionContent,b.answerContent ,a.id,b.id,c.id"
		// + " from CcMain a, OperQuestion b, CcTalk c"
		// + " where b.ringBegintime = a.ringBegintime and a.id = c.ccMain.id";
//		select count(*),oc.cust_addr from cc_talk ct,oper_question oq,cc_main cm,oper_custinfo oc
//		where ct.cclog_id = cm.id and oq.ring_begintime = cm.ring_begintime 
//		and ct.respondent = oq.answer_agent and oq.cust_id = oc.cust_id  and oc.cust_addr like '%'+@qAddress+'%'
//		and ct.TOUCH_BEGINTIME >= @begintime and ct.TOUCH_BEGINTIME <= @endtime
		String hql = "from OperQuestion oq where oq.isDelete=0 ";
		String tel_num = (String) dto.get("tel_num");
		if (tel_num != null && !"".equals(tel_num.trim())) {
			hql = hql + " and oq.custTel like '" + tel_num.trim() + "%'";
			// dc.add(Restrictions.like("custTel", "'%"+tel_num+"%'"));
		}
		String respondent = (String) dto.get("respondent");
		if (respondent != null && !"".equals(respondent.trim())) {
			hql = hql + " and oq.answerAgent like '" + respondent.trim() + "%'";
			// dc.add(Restrictions.like("custTel", "'%"+tel_num+"%'"));
		}
		String questionContent = (String) dto.get("questionContent");
		if (questionContent != null && !"".equals(questionContent.trim())) {
			hql = hql + " and oq.questionContent like '%" + questionContent.trim()
					+ "%'";
			// dc.add(Restrictions.like("custTel", "'%"+tel_num+"%'"));
		}
		String addtimeBegin = (String) dto.get("addtimeBegin");
		if (addtimeBegin == null) {
			addtimeBegin = "";
		}
		String addtimeEnd = (String) dto.get("addtimeEnd");
		if (addtimeEnd == null) {
			addtimeEnd = "";
		}
		if (!"".equals(addtimeBegin.trim()) && !"".equals(addtimeEnd.trim())) {
			hql = hql + " and  Convert(varchar(10),oq.addtime,120) between '"
					+ addtimeBegin.trim() + "' and '" + addtimeEnd.trim() + "'";
		} else if (!"".equals(addtimeBegin.trim())) {
			hql = hql + " and  Convert(varchar(10),oq.addtime,120) like '"
					+ addtimeBegin.trim() + "%'";
		} else if (!"".equals(addtimeEnd.trim())) {
			hql = hql + " and  Convert(varchar(10),oq.addtime,120) like '"
					+ addtimeEnd.trim() + "%'";
		}

		String dictQuestionType1 = (String) dto.get("dictQuestionType1");
		if (dictQuestionType1 != null && !"".equals(dictQuestionType1.trim())) {
			hql = hql + " and oq.dictQuestionType1 like '"
					+ dictQuestionType1.trim() + "%'";
		}
		String dictIsAnswerSucceed = (String) dto.get("dictIsAnswerSucceed");
		if (dictIsAnswerSucceed != null
				&& !"".equals(dictIsAnswerSucceed.trim())) {
			hql = hql + " and oq.dictIsAnswerSucceed like '"
					+ dictIsAnswerSucceed.trim() + "%'";
		}
		String answerMan = (String) dto.get("answerMan");
		if (answerMan != null && !"".equals(answerMan.trim())) {
			hql = hql + " and oq.answerMan like '" + answerMan.trim() + "%'";
		}
		String dictIsCallback = (String) dto.get("dictIsCallback");
		if (dictIsCallback != null && !"".equals(dictIsCallback.trim())) {
			hql = hql + " and oq.dictIsCallback like '" + dictIsCallback.trim()
					+ "%'";
		}
		String billNum = (String) dto.get("billNum");
		if (billNum != null && !"".equals(billNum.trim())) {
			hql = hql + " and oq.billNum like '" + billNum.trim() + "%'";
		}
		String expertName = (String) dto.get("expertName");
		if (expertName != null && !"".equals(expertName.trim())) {
			hql = hql + " and oq.expertName like '" + expertName.trim() + "%'";
		}


		//System.out.println(hql+" .......hql");
		System.out.println(hql);
		mq.setHql(hql);
		// mq.setDetachedCriteria(dc);
		return mq;
	}
	public MyQuery operIncommingInfoDtlQuery(String id) {
		MyQuery mq = new MyQueryImpl();
		DetachedCriteria dc = DetachedCriteria.forClass(OperQuestion.class);

		// String hql = "select a.telNum,b.addtime, b.dictQuestionType1,
		// c.respondent,b.questionContent,b.answerContent ,a.id,b.id,c.id"
		// + " from CcMain a, OperQuestion b, CcTalk c"
		// + " where b.ringBegintime = a.ringBegintime and a.id = c.ccMain.id
		// and b.id = '"+id+"'";

		// mq.setHql(hql);
		dc.add(Restrictions.eq("id", id));
		mq.setDetachedCriteria(dc);
		return mq;
	}

	/**
	 * 统计总页数的
	 */
	public MyQuery operIncommingInfoSizeQuery(IBaseDTO dto, PageInfo pi) {
		MyQuery mq = new MyQueryImpl();
		DetachedCriteria dc = DetachedCriteria
				.forClass(CcIvrTreevoiceDetail.class);

		String hql = "select a.telNum,b.addtime, b.dictQuestionType1, c.respondent,b.questionContent,b.answerContent ,a.id,b.id,c.id"
				+ " from CcMain a, OperQuestion b, CcTalk c"
				+ " where b.ringBegintime = a.ringBegintime and a.id = c.ccMain.id";
		String tel_num = (String) dto.get("tel_num");
		if (tel_num != null && !"".equals(tel_num.trim())) {
			hql = hql + " and a.telNum like '" + tel_num.trim() + "%'";
		}
		String addtimeBegin = (String) dto.get("addtimeBegin");
		if (addtimeBegin == null) {
			addtimeBegin = "";
		}
		String addtimeEnd = (String) dto.get("addtimeEnd");
		if (addtimeEnd == null) {
			addtimeEnd = "";
		}
		if (!"".equals(addtimeBegin.trim()) && !"".equals(addtimeEnd.trim())) {
			hql = hql + " and  Convert(varchar(10),b.addtime,120) between '"
					+ addtimeBegin.trim() + "' and '" + addtimeEnd.trim() + "'";
		} else if (!"".equals(addtimeBegin.trim())) {
			hql = hql + " and  Convert(varchar(10),b.addtime,120) like '"
					+ addtimeBegin.trim() + "%'";
		} else if (!"".equals(addtimeEnd.trim())) {
			hql = hql + " and  Convert(varchar(10),b.addtime,120) like '"
					+ addtimeEnd.trim() + "%'";
		}
		mq.setHql(hql);

		mq.setFirst(pi.getBegin());

		mq.setFetch(pi.getPageSize());
		return mq;
	}

}
