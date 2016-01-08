/*
 * @(#)DetailHelp.java	 2008-05-06
 *
 * 版权所有 沈阳市卓越科技有限公司。
 */

package et.bo.inquiry.detail.service.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import et.po.OperInquiryResult;
import excellence.common.page.PageInfo;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;


public class DetailHelp extends MyQueryImpl{
	
	public MyQuery detailQuery(IBaseDTO dto, PageInfo pi){
		
		MyQuery mq = new MyQueryImpl();
		DetachedCriteria dc = DetachedCriteria.forClass(OperInquiryResult.class);

		String beginTime = (String) dto.get("beginTime");
		String endTime = (String) dto.get("endTime");
		String rid = (String) dto.get("rid");
		String actor = (String) dto.get("actor");
		String questionType = (String) dto.get("questionType");
		String question = (String) dto.get("question");
		String answer = (String) dto.get("answer");
		String topicId = (String)dto.get("topicId");
		String qid = (String)dto.get("qid");
		
		if(qid != null && !qid.equals("")){
			dc.add(Restrictions.eq("questionId", qid));
		}
		if (!beginTime.equals("")) {
			dc.add(Restrictions.le("createTime", TimeUtil.getTimeByStr(beginTime, "yyyy-MM-dd")));
		}
		if (!endTime.equals("")) {
			dc.add(Restrictions.ge("createTime", TimeUtil.getTimeByStr(endTime, "yyyy-MM-dd")));
		}
		if (!rid.equals("")) {
			dc.add(Restrictions.like("rid", "%" + rid + "%"));
		}
		if (!actor.equals("")) {
			dc.add(Restrictions.like("actor", "%" + actor + "%"));
		}
		if (!questionType.equals("")) {
			dc.add(Restrictions.like("questionType", "%" + questionType + "%"));
		}
		if (!question.equals("")) {
			dc.add(Restrictions.like("question", "%" + question + "%"));
		}
		if (!answer.equals("")) {
			dc.add(Restrictions.like("answer", "%" + answer + "%"));
		}
		if (!topicId.equals("")) {
			dc.add(Restrictions.like("topicId", topicId));
		}
			
		mq.setDetachedCriteria(dc);
		mq.setFirst(pi.getBegin());
		mq.setFetch(pi.getPageSize());
		
		return mq;
	}
}
