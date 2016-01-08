/*
 * @(#)QuestionHelp.java	 2008-03-19
 *
 * ��Ȩ���� ������׿Խ�Ƽ����޹�˾��
 */

package et.bo.question.service.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Order;

import et.po.OperQuestion;
import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;

/**
 * <p>�����������ѯ</p>
 * 
 * @version 2008-03-19
 * @author nie
 */
public class QuestionHelp extends MyQueryImpl{
	
	public MyQuery questionQuery(IBaseDTO dto, PageInfo pi){
		
		MyQuery mq = new MyQueryImpl();
		DetachedCriteria dc = DetachedCriteria.forClass(OperQuestion.class);

		//����ȷ����δɾ��������
		dc.add(Restrictions.eq("isDelete","0"));
		
		//����
		String str = (String)dto.get("bill_num");
		if(!str.equals("")){
			dc.add(Restrictions.like("billNum","%"+str+"%"));
		}
		//����
		str = (String)dto.get("question_content");
		if(!str.equals("")){
			dc.add(Restrictions.like("questionContent","%"+str+"%"));
		}
		//��
		str = (String)dto.get("answer_content");
		if(!str.equals("")){
			dc.add(Restrictions.like("answerContent","%"+str+"%"));
		}
		//��Ŀ
		str = (String)dto.get("dict_question_type1");
		if(!str.equals("")){
			dc.add(Restrictions.eq("dictQuestionType1",str));
		}
		//����1
		str = (String)dto.get("dict_question_type2");
		if(!str.equals("")){
			dc.add(Restrictions.eq("dictQuestionType2",str));
		}
		//����2
		str = (String)dto.get("dict_question_type3");
		if(!str.equals("")){
			dc.add(Restrictions.eq("dictQuestionType3",str));
		}
		//����3
		str = (String)dto.get("dict_question_type4");
		if(!str.equals("")){
			dc.add(Restrictions.eq("dictQuestionType4",str));
		}
		//���״̬
		str = (String)dto.get("dict_is_answer_succeed");
		if(!str.equals("")){
			dc.add(Restrictions.eq("dictIsAnswerSucceed",str));
		}
		//�����ʽ
		str = (String)dto.get("answer_man");
		if(!str.equals("")){
			dc.add(Restrictions.eq("answerMan",str));
		}
		//�Ƿ�ط�
		str = (String)dto.get("dict_is_callback");
		if(!str.equals("")){
			dc.add(Restrictions.eq("dictIsCallback",str));
		}
		//����
		dc.addOrder(Order.desc("addtime"));
		
		mq.setDetachedCriteria(dc);
		mq.setFirst(pi.getBegin());
		mq.setFetch(pi.getPageSize());
		
		return mq;
	}
}
