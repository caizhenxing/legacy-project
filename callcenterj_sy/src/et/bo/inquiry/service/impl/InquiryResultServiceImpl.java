/**
 * 	@(#) InquiryResultServiceImpl.java 2008-4-9 ����02:06:11
 *	��Ȩ���� ������׿Խ�Ƽ����޹�˾�� 
 *	׿Խ�Ƽ� ����һ��Ȩ��
 */
package et.bo.inquiry.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.apache.log4j.Logger;

import et.bo.inquiry.service.InquiryResultService;
import et.po.OperInquiryResult;
import et.po.OperInquiryinfo;
import excellence.common.classtree.ClassTreeService;
import excellence.common.key.KeyService;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

/**
 * et.bo.inquiry.service.InquiryResultService��ʵ����
 * @author ���Ʒ�
 * 
 */
public class InquiryResultServiceImpl implements InquiryResultService {
	static Logger log = Logger.getLogger(InquiryServiceImpl.class);
	private String inquiry_num = null;
	private String topic = null;
	private BaseDAO dao;

	private KeyService ks;

	private ClassTreeService classTree;

	public ClassTreeService getClassTree() {
		return classTree;
	}

	public void setClassTree(ClassTreeService classTree) {
		this.classTree = classTree;
	}

	public BaseDAO getDao() {
		return dao;
	}

	public void setDao(BaseDAO dao) {
		this.dao = dao;
	}

	public KeyService getKs() {
		return ks;
	}

	public void setKs(KeyService ks) {
		this.ks = ks;
	}
	public String getInquiryNum(){
		return inquiry_num;
	}
	public String getTopic(){
		return topic;
	}
	public void save(List answerList) {

		inquiry_num = ks.getNext("inquiry_num");
		
		for(int i = 0; i < answerList.size(); i++){
			
			Map valueMap = (Map)answerList.get(i);
			
			String[] answer = (String[])valueMap.get("answer");
			if(answer != null){
				for(int j = 0; j < answer.length; j++){
					
					OperInquiryResult po = new et.po.OperInquiryResult();
					
					po.setId(ks.getNext("oper_inquiry_result"));
					
					po.setTopicId((String)valueMap.get("topic_id"));
					po.setRid((String)valueMap.get("rid"));
					po.setCreateTime(new Date());
					po.setCardId((String)valueMap.get("card_id"));
					po.setQuestionType((String)valueMap.get("question_type"));
					po.setQuestion((String)valueMap.get("question"));
					po.setAnswer(answer[j]);
					po.setNum(inquiry_num);
					
					dao.saveEntity(po);
				}
			}
		}
	}

	public List<DynaBeanDTO> getResultInfo(String id) {

		OperInquiryinfo inquiry = (OperInquiryinfo) dao.loadEntity(
				OperInquiryinfo.class, id);
		TreeSet<OperInquiryResult> results = new TreeSet<OperInquiryResult>();
		List<DynaBeanDTO> list = new ArrayList<DynaBeanDTO>();
		Iterator<OperInquiryResult> i = results.iterator();
		while (i.hasNext()) {
			OperInquiryResult tmp = i.next();
			DynaBeanDTO result = new DynaBeanDTO();
			result.set("topic", inquiry.getTopic());
			result.set("actor", tmp.getActor());// ������Ҫ������ͻ���IDת��������
			result.set("rname", tmp.getRid());// ������Ҫ��������ϯԱIDת��Ϊ����
			result.set("time", TimeUtil.getTheTimeStr(tmp.getCreateTime(),
					"yyyy-MM-dd"));
			result.set("question", tmp.getQuestion());
			result.set("answer", tmp.getAnswer());
			list.add(result);
		}
		return list;
	}

}
