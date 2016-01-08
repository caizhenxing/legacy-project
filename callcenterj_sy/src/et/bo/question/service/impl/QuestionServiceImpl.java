/*
 * @(#)QusetionServiceImpl.java	 2008-03-19
 *
 * ��Ȩ���� ������׿Խ�Ƽ����޹�˾��
 */

package et.bo.question.service.impl;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.sql.RowSet;

import et.bo.question.service.QuestionService;
import et.po.OperCallback;
import et.po.OperCustinfo;
import et.po.OperQuestion;
import excellence.common.classtree.ClassTreeService;
import excellence.common.key.KeyService;
import excellence.common.page.PageInfo;
import excellence.common.tools.LabelValueBean;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

/**
 * <p>
 * �������
 * </p>
 * 
 * @version 2008-03-19
 * @author nie
 */

public class QuestionServiceImpl implements QuestionService {

	private String questionId = null;
	
	BaseDAO dao = null;
	private List callbackList = null;
	private int num = 0;
	private ClassTreeService cts = null;
	public KeyService ks = null;

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
	
	public ClassTreeService getCts() {
		return cts;
	}
	public void setCts(ClassTreeService cts) {
		this.cts = cts;
	}
	/**
	 * ��������ID��ѯ�����б�,���ء��طá���list�� ȡ�ò�ѯ�����б����ݡ�
	 * 
	 * @return �ط����ݵ�list
	 */
	public List getCallbackList() {
		List<String> l = new ArrayList<String>();
		List cList = new ArrayList();
		for(int i = 0; i<callbackList.size(); i++){
			DynaBeanDTO dto = (DynaBeanDTO)callbackList.get(i);
			l.add((String)dto.get("callback_time"));
		}
		Collections.sort(l);
		for(int i = l.size()-1; i>=0; i--){
			for(int j = 0; j<callbackList.size(); j++ ){
				DynaBeanDTO dto = (DynaBeanDTO)callbackList.get(j);
				if(l.get(i).equals( dto.get("callback_time")) ){
					cList.add(dto);
					break;
				}
			}
		}
		return cList;
	}

	/**
	 * ��ѯ�����б�,���ؼ�¼��list�� ȡ�ò�ѯ�����б����ݡ�
	 * 
	 * @param dto
	 *            ���ݴ������
	 * @param pi
	 *            ҳ����Ϣ
	 * @return ���ݵ�list
	 */
	public List questionQuery(IBaseDTO dto, PageInfo pi) {
		List list = new ArrayList();
		QuestionHelp h = new QuestionHelp();
		Object[] result = (Object[]) dao.findEntity(h.questionQuery(dto, pi));
		num = dao.findEntitySize(h.questionQuery(dto, pi));

		for (int i = 0, size = result.length; i < size; i++) {
			OperQuestion cq = (OperQuestion) result[i];
			list.add(questionToDynaBeanDTO(cq));
		}

		return list;
	}

	/**
	 * ��ѯ���С�024007009���ķ����¼�����б�,���ؼ�¼��list��
	 * ȡ�ò�ѯ�����б����ݡ�
	 * @param dto ���ݴ������
	 * @param pi ҳ����Ϣ
	 * @return ���ݵ�list
	 */
	public List questionSpecialQuery(String content){
		List list = new ArrayList();
		String sql = "select a.cust_tel,a.addtime,b.cust_name,b.cust_addr from (select distinct cust_tel,addtime,question_content from dbo.oper_question "+
					"where question_content like '%"+content+"%') a,oper_custinfo b "+
					" where a.cust_tel in (cust_tel_home,cust_tel_work,cust_tel_mob)";
		RowSet rs = dao.getRowSetByJDBCsql(sql);
		try {
			while(rs.next()){
				String cust_tel = rs.getString("cust_tel");
				String addtime = rs.getString("addtime");
				String cust_name = rs.getString("cust_name");
				String cust_addr = rs.getString("cust_addr");
				
				List<String> lit = new ArrayList<String>();
				lit.add(cust_tel);
				lit.add(cust_name);
				lit.add(cust_addr);
				lit.add(addtime);
				list.add(lit);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * ��ѯ������ po ת dto
	 * 
	 * @param po
	 * @return dto
	 */
	private DynaBeanDTO questionToDynaBeanDTO(OperQuestion po) {

		DynaBeanDTO dto = new DynaBeanDTO();
		
		dto.set("id", po.getId());
		String question = po.getQuestionContent();
		if(question != null && question.length() > 15){
			question = question.substring(0, 10) + "��";			
		}
		
		dto.set("question_content", question);
		dto.set("answer_content", po.getAnswerContent());
		dto.set("dict_question_type1", po.getDictQuestionType1());
		
		String dict_is_answer_succeed = po.getDictIsAnswerSucceed();
		if(dict_is_answer_succeed != null && dict_is_answer_succeed.length() > 6){
			dict_is_answer_succeed = cts.getLabelById(dict_is_answer_succeed);
		}
		dto.set("dict_is_answer_succeed", dict_is_answer_succeed);
		String date = TimeUtil.getTheTimeStr(po.getAddtime(), "yyyy-MM-dd");
		dto.set("addtime", date);
		
		dto.set("answer_agent", po.getAnswerAgent());
		dto.set("telNum", po.getCustTel());
		if(po.getExpertName() != null)
			dto.set("expert_name", po.getExpertName());
		else
			dto.set("expert_name", "");

		return dto;
	}

	/**
	 * ��ѯ�����б�������� ȡ�������ѯ�б��������
	 * 
	 * @return �õ�list������
	 */
	public int getQuestionSize() {

		return num;

	}

	/**
	 * ����IDȡ��һ�����ݵ�excellence.framework.base.dto.IBaseDTO���� ȡ��ĳ�����ݵ���ϸ��Ϣ��
	 * 
	 * @param id ȡ��excellence.framework.base.dto.IBaseDTO�ı�ʶ
	 * @return ����������Ϣ��excellence.framework.base.dto.IBaseDTO����
	 */
	public IBaseDTO getQuestionInfo(String id) {

		OperQuestion po = (OperQuestion) dao.loadEntity(OperQuestion.class, id);
		IBaseDTO dto = new DynaBeanDTO();
		
		dto.set("id", po.getId());
		dto.set("question_content", po.getQuestionContent());
		dto.set("answer_content", po.getAnswerContent());
		dto.set("dict_question_type1", po.getDictQuestionType1());
		dto.set("dict_question_type2", po.getDictQuestionType2());
		dto.set("dict_question_type3", po.getDictQuestionType3());
		dto.set("dict_question_type4", po.getDictQuestionType4());
		dto.set("bill_num", po.getBillNum());
		dto.set("dict_is_answer_succeed", po.getDictIsAnswerSucceed());
		dto.set("answer_man", po.getAnswerMan());
		dto.set("dict_is_callback", po.getDictIsCallback());
		
		dto.set("callback_man", po.getCallbackMan());
		dto.set("callback_phone", po.getCallbackPhone());
		dto.set("is_callback_succ", po.getIsCallbackSucc());
		
		String date = TimeUtil.getTheTimeStr(po.getCallbackTime(), "yyyy-MM-dd");
		dto.set("callback_time", date);
		
		Set sets = po.getOperCallbacks();
		Iterator it = sets.iterator();
		callbackList = new ArrayList();
		while(it.hasNext()){
			OperCallback oc = (OperCallback)it.next();
			DynaBeanDTO cdto = new DynaBeanDTO();
			cdto.set("id", oc.getId());
			cdto.set("callback_content", oc.getCallbackContent());
			cdto.set("remark", oc.getRemark());
			String d = DateFormat.getDateInstance().format(oc.getCallbackTime());
			cdto.set("callback_time", d);
			callbackList.add(cdto);
		}
		
		return dto;

	}
	/**
	 * �޸Ļط����ݡ� 
	 * 
	 * @param dto Ҫ���µĵ�excellence.framework.base.dto.IBaseDTO����
	 * @return boolean
	 */
	public boolean updateCallback(IBaseDTO dto) {
		
		OperQuestion po = (OperQuestion) dao.loadEntity(OperQuestion.class, dto.get("question_id").toString());// ���������޸ı�
		
		po.setCallbackMan(dto.get("callback_man").toString());
		po.setCallbackPhone(dto.get("callback_phone").toString());
		po.setIsCallbackSucc(dto.get("is_callback_succ").toString());
		
		dao.saveEntity(po);
		return false;

	}

	/**
	 * �޸����ݡ� �޸�ĳ����¼�����ݡ�
	 * 
	 * @param dto Ҫ���µĵ�excellence.framework.base.dto.IBaseDTO����
	 * @return boolean
	 */
	public boolean updateQuestion(IBaseDTO dto) {

		dao.saveEntity(modifycoll(dto));
		return false;

	}

	/**
	 * �޸ķ����� dto ת po
	 * 
	 * @param dto
	 * @return po
	 */
	private OperQuestion modifycoll(IBaseDTO dto) {

		OperQuestion po = (OperQuestion) dao.loadEntity(OperQuestion.class, dto.get("id").toString());// ���������޸ı�

		po.setQuestionContent(dto.get("question_content").toString());
		po.setAnswerContent(dto.get("answer_content").toString());
		po.setDictQuestionType1(dto.get("dict_question_type1").toString());
		po.setDictQuestionType2(dto.get("dict_question_type2").toString());
		po.setDictQuestionType3(dto.get("dict_question_type3").toString());
		po.setDictQuestionType4(dto.get("dict_question_type4").toString());
		po.setBillNum(dto.get("bill_num").toString());
		po.setDictIsAnswerSucceed(dto.get("dict_is_answer_succeed").toString());
		po.setAnswerMan(dto.get("answer_man").toString());
		po.setDictIsCallback(dto.get("dict_is_callback").toString());
		po.setCallbackTime(TimeUtil.getTimeByStr(dto.get("callback_time").toString(), "yyyy-MM-dd"));

		return po;
	}

	/**
	 * ɾ�����ݡ� ɾ��ĳ����¼��
	 * 
	 * @param id
	 *            Ҫɾ�����ݵı�ʶ
	 */
	public void delQuestion(String id) {

		OperQuestion po = (OperQuestion) dao.loadEntity(OperQuestion.class, id);
		dao.removeEntity(po);

	}
	
	/**
	 * ���ɾ����
	 * ����ֶ�"IS_DELETE"��Ϊ"1"ʱΪɾ����Ϊ"0"ʱδɾ����ʵ�����������ִ�е����޸�"IS_DELETE"�ֶεĲ�����
	 * @param id Ҫ���ɾ�����ݵı�ʶ
	 */
	public boolean isDelete(String id) {
		
		OperQuestion po = (OperQuestion)dao.loadEntity(OperQuestion.class,id);//���������޸ı�
		po.setIsDelete("1");	//"1"����ɾ������˼
		
		try{
			dao.saveEntity(po);
			return true;
		}catch(Exception e){
			return false;
		}
		
	}
	
	/**
	 * ������ݡ� �����ݿ������һ����¼��
	 * 
	 * @param dto
	 *            �����ݵ�excellence.framework.base.dto.IBaseDTO����
	 */
	public void addQuestion(IBaseDTO dto) {

		dao.saveEntity(createQuestion2(dto));

	}
	
	/**
	 * ������ݡ� �����ݿ������һ����¼��
	 * ���ҷ�������ID
	 * @param dto
	 *            �����ݵ�excellence.framework.base.dto.IBaseDTO����
	 */
	public String addQuestionAndGetId(IBaseDTO dto, String ring_begintime, String userId, String isout, String cust_tel) {

		dao.saveEntity(createQuestion(dto, ring_begintime, userId, isout, cust_tel));
		return questionId;

	}

	/**
	 * ��ӷ����� dto ת po
	 * 
	 * @param dto
	 * @return po
	 */
	private OperQuestion createQuestion(IBaseDTO dto, String ring_begintime, String userId, String isout, String cust_tel) {

		OperQuestion po = new OperQuestion();
		
		//����id
		questionId = ks.getNext("oper_question");
		po.setId(questionId);
		//���ɾ���ֶ�Ϊ"0"
		po.setIsDelete("0");
		//��ӿͻ�ID
		String cust_id = (String)dto.get("cust_id");
		if(cust_id != null && !cust_id.equals("")){
			OperCustinfo ocf = new OperCustinfo();
			ocf.setCustId(cust_id);
			po.setOperCustinfo(ocf);
		}
		po.setCustTel(cust_tel);
		po.setRid(userId);
		po.setQuestionContent(dto.get("question_content").toString());
		po.setAnswerContent(dto.get("answer_content").toString());
		po.setDictQuestionType1(dto.get("dict_question_type1").toString());
		po.setDictQuestionType2(dto.get("dict_question_type2").toString());
		po.setDictQuestionType3(dto.get("dict_question_type3").toString());
		po.setDictQuestionType4(dto.get("dict_question_type4").toString());
		po.setBillNum(dto.get("bill_num").toString());
		po.setDictIsAnswerSucceed(dto.get("dict_is_answer_succeed").toString());
		po.setAnswerMan(dto.get("answer_man").toString());
		po.setDictIsCallback(dto.get("dict_is_callback").toString());
		po.setCallbackTime(TimeUtil.getTimeByStr(dto.get("callback_time").toString(), "yyyy-MM-dd"));
		po.setAddtime(new Date());
		po.setRingBegintime(TimeUtil.getTimeByStr(ring_begintime, "yyyy-MM-dd HH:mm:ss"));
		po.setAnswerAgent(userId);
		po.setIsOut(isout);
		// ��¼ר������
		po.setExpertName(dto.get("expert_name").toString());
		
		return po;
	}
	
	private OperQuestion createQuestion2(IBaseDTO dto) {

		OperQuestion po = new OperQuestion();
		
		//����id
		questionId = ks.getNext("oper_question");
		po.setId(questionId);
		//���ɾ���ֶ�Ϊ"0"
		po.setIsDelete("0");
		//��ӿͻ�ID
		String cust_id = (String)dto.get("cust_id");
		if(cust_id != null && !cust_id.equals("")){
			OperCustinfo ocf = new OperCustinfo();
			ocf.setCustId(cust_id);
			po.setOperCustinfo(ocf);
		}

		po.setQuestionContent(dto.get("question_content").toString());
		po.setAnswerContent(dto.get("answer_content").toString());
		po.setDictQuestionType1(dto.get("dict_question_type1").toString());
		po.setDictQuestionType2(dto.get("dict_question_type2").toString());
		po.setDictQuestionType3(dto.get("dict_question_type3").toString());
		po.setDictQuestionType4(dto.get("dict_question_type4").toString());
		po.setBillNum(dto.get("bill_num").toString());
		po.setDictIsAnswerSucceed(dto.get("dict_is_answer_succeed").toString());
		po.setAnswerMan(dto.get("answer_man").toString());
		po.setDictIsCallback(dto.get("dict_is_callback").toString());
		po.setCallbackTime(TimeUtil.getTimeByStr(dto.get("callback_time").toString(), "yyyy-MM-dd"));
		po.setAddtime(new Date());
		
//		po.setExpertName(dto.get("caseExpert").toString());
		
		return po;
	}
	/**
	 * �õ�ר�����
	 */
	public List<LabelValueBean> getExpertsList()
	{
		return cts.getLabelVaList("zhuanjialeibie");
	}
}
