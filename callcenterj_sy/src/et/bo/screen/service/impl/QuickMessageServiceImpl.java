/*
 * @(#)CustinfoServiceImpl.java	 2008-03-19
 *
 * ��Ȩ���� ������׿Խ�Ƽ����޹�˾��
 */

package et.bo.screen.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import et.bo.screen.service.QuickMessageService;

import et.po.OperCustinfo;
import et.po.OperQuestion;
import et.po.ScreenQuickMessage;
import excellence.common.classtree.ClassTreeService;
import excellence.common.key.KeyService;
import excellence.common.page.PageInfo;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

/**
 * <p>
 * ÿ�տ�Ѷ
 * </p>
 * 
 * @version 2008-03-19
 * @author wwq
 */

public class QuickMessageServiceImpl implements QuickMessageService {

	BaseDAO dao = null;

	private int num = 0;

	private List questionList = null;

	public KeyService ks = null;

	private ClassTreeService cts = null;

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
	 * ���ݿͻ�ID��ѯ�����б�,���ظÿͻ��ġ����⡱��list�� ȡ�ò�ѯ�����б����ݡ� ���ص��б����ʱ���������
	 * 
	 * @return �ط����ݵ�list
	 */
	public List getQuestionList() {
		List<String> l = new ArrayList<String>();
		List qList = new ArrayList();
		for (int i = 0; i < questionList.size(); i++) {
			DynaBeanDTO dto = (DynaBeanDTO) questionList.get(i);
			l.add((String) dto.get("addtime"));
		}
		Collections.sort(l);
		for (int i = l.size() - 1; i >= 0; i--) {
			for (int j = 0; j < questionList.size(); j++) {
				DynaBeanDTO dto = (DynaBeanDTO) questionList.get(j);
				if (l.get(i).equals(dto.get("addtime"))) {
					qList.add(dto);
					break;
				}
			}
		}

		return qList;
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
	public List quickMessageQuery(IBaseDTO dto, PageInfo pi) {

		List list = new ArrayList();
		QuickMessageHelp h = new QuickMessageHelp();
		
		Object[] result = (Object[]) dao.findEntity(h.quickMessageQuery(dto, pi));
		num = dao.findEntitySize(h.quickMessageQuery(dto, pi));

		for (int i = 0, size = result.length; i < size; i++) {
			ScreenQuickMessage po = (ScreenQuickMessage) result[i];
			String msg = po.getMsgContent();
//			if(msg!=null&&msg.length()>25)
//			{
//				msg = msg.substring(0,25)+"....";
				po.setMsgContent(msg);
//			}
			list.add(this.quickMessageDynaBeanDTO(po));
		}
		return list;
	}

	/**
	 * ��ѯ�����б�,���ؼ�¼(ר)��list�� ȡ�ò�ѯ�б����ݡ�
	 * 
	 * @param dto
	 *            ���ݴ������
	 * @param pi
	 *            ҳ����Ϣ
	 * @return ���ݵ�list
	 */
	public List quickMessageAllQuery() {
		List list = new ArrayList();
		QuickMessageHelp h = new QuickMessageHelp();
		try
		{
			Object[] result = (Object[]) dao.findEntity(h.quickMessageAllQuery());
			for (int i = 0, size = result.length; i < size; i++) {
				ScreenQuickMessage po = (ScreenQuickMessage) result[i];
				list.add(this.quickMessageDynaBeanDTO(po));
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * ��ѯ�����б�,����ȫ����¼��list�� ȡ�ò�ѯ�����б����ݡ�
	 * 
	 * @param dto
	 *            ���ݴ������
	 * @param pi
	 *            ҳ����Ϣ
	 * @return ���ݵ�list
	 */
	public List custinfoAllQuery() {

		List list = new ArrayList();
		QuickMessageHelp h = new QuickMessageHelp();

		Object[] result = (Object[]) dao.findEntity(h.allQuery());

		for (int i = 0, size = result.length; i < size; i++) {
			ScreenQuickMessage po = (ScreenQuickMessage) result[i];
			list.add(this.quickMessageDynaBeanDTO(po));
		}
		return list;
	}

	/**
	 * ��ѯ������ po ת dto
	 * 
	 * @param po
	 * @return dto
	 */
	private DynaBeanDTO quickMessageDynaBeanDTO(ScreenQuickMessage po) {

		DynaBeanDTO dto = new DynaBeanDTO();

		dto.set("id", po.getId());
		dto.set("inputMan", po.getInputMan());
		dto.set("msgContent", po.getMsgContent());
		dto.set("msgTitle", po.getMsgTitle());
		dto.set("remark", po.getRemark());
		dto.set("createDate", po.getCreateDate());
		return dto;
	}

	/**
	 * ��ѯ�����б�������� ȡ�������ѯ�б��������
	 * 
	 * @return �õ�list������
	 */
	public int getQuickMessageSize() {

		return num;

	}

	/**
	 * ����IDȡ��һ�����ݵ�excellence.framework.base.dto.IBaseDTO���� ȡ��ĳ�����ݵ���ϸ��Ϣ��
	 * 
	 * @param id
	 *            ȡ��excellence.framework.base.dto.IBaseDTO�ı�ʶ
	 * @return ����������Ϣ��excellence.framework.base.dto.IBaseDTO����
	 */
	public IBaseDTO getQuickMessageInfo(String id) {

		ScreenQuickMessage po = (ScreenQuickMessage) dao.loadEntity(ScreenQuickMessage.class, id);
		IBaseDTO dto = new DynaBeanDTO();

		dto.set("id", po.getId());
		dto.set("inputMan", po.getInputMan());
		dto.set("msgContent", po.getMsgContent());
		dto.set("msgTitle", po.getMsgTitle());
		dto.set("remark", po.getRemark());
		dto.set("createDate", po.getCreateDate());

		return dto;

	}

	/**
	 * ���ݵ绰����ȡ��һ�����ݵ�excellence.framework.base.dto.IBaseDTO����
	 * ���ݵ绰�����ѯ�û�լ�磬�칫�绰���ֻ���ȡ��ĳ�ͻ�����ϸ��Ϣ��
	 * 
	 * @param id
	 *            ȡ��excellence.framework.base.dto.IBaseDTO�ı�ʶ
	 * @return ����������Ϣ��excellence.framework.base.dto.IBaseDTO����
	 */
	public IBaseDTO getopenwinInfo(String tel) {

		QuickMessageHelp h = new QuickMessageHelp();
		Object[] obj = (Object[]) dao.findEntity(h.openwinQuery(tel));

		OperCustinfo po = new OperCustinfo();
		if (obj.length > 0) { // ������ؽ���������0
			po = (OperCustinfo) obj[0]; // ��ȡ�õ�һ������ת��po
		}
		IBaseDTO dto = new DynaBeanDTO();

		dto.set("cust_id", po.getCustId());
		dto.set("cust_name", po.getCustName());
		dto.set("dict_sex", po.getDictSex());
		dto.set("cust_email", po.getCustEmail());
		dto.set("cust_addr", po.getCustAddr());
		dto.set("cust_pcode", po.getCustPcode());
		dto.set("cust_tel_home", po.getCustTelHome());
		dto.set("cust_tel_work", po.getCustTelWork());
		dto.set("cust_tel_mob", po.getCustTelMob());
		dto.set("cust_fax", po.getCustFax());
		dto.set("cust_voc", po.getDictCustVoc());
		dto.set("cust_scale", po.getDictCustScale());
		dto.set("cust_type", po.getDictCustType());
		dto.set("remark", po.getRemark());

		Set sets = po.getOperQuestions();
		Iterator it = sets.iterator();
		questionList = new ArrayList();
		while (it.hasNext()) {
			OperQuestion oq = (OperQuestion) it.next();
			DynaBeanDTO ddto = new DynaBeanDTO();

			ddto.set("id", oq.getId());
			ddto.set("dict_question_type1", oq.getDictQuestionType1());
			ddto.set("question_content", oq.getQuestionContent());

			String dict_is_answer_succeed = oq.getDictIsAnswerSucceed();
			if (dict_is_answer_succeed != null
					&& dict_is_answer_succeed.indexOf("SYS") != -1)
				dict_is_answer_succeed = cts
						.getLabelById(dict_is_answer_succeed);
			ddto.set("dict_is_answer_succeed", dict_is_answer_succeed);

			String d = new java.text.SimpleDateFormat("yy-MM-dd HH:mm")
					.format(oq.getAddtime());
			ddto.set("addtime", d);

			questionList.add(ddto);
		}

		return dto;
	}

	/**
	 * �޸����ݡ� �޸�ĳ����¼�����ݡ�
	 * 
	 * @param dto
	 *            Ҫ���µĵ�excellence.framework.base.dto.IBaseDTO����
	 * @return boolean
	 */
	public boolean updateQuickMessage(IBaseDTO dto) {

		try {
			dao.saveEntity(modifycoll(dto));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * �޸ķ����� dto ת po
	 * 
	 * @param dto
	 * @return po
	 */
	private ScreenQuickMessage modifycoll(IBaseDTO dto) {

		ScreenQuickMessage po = (ScreenQuickMessage) dao.loadEntity(ScreenQuickMessage.class, dto
				.get("id").toString());// ���������޸ı�
		
		po.setId((String)dto.get("id"));
		po.setMsgTitle((String)dto.get("msgTitle"));
		po.setInputMan((String)dto.get("inputMan"));
		po.setMsgContent((String)dto.get("msgContent"));
		po.setRemark((String)dto.get("remark"));

		return po;
	}

	/**
	 * ɾ�����ݡ� ɾ��ĳ����¼��
	 * 
	 * @param id
	 *            Ҫɾ�����ݵı�ʶ
	 */
	public void delQuickMessage(String id) {

		ScreenQuickMessage po = (ScreenQuickMessage) dao.loadEntity(ScreenQuickMessage.class, id);
		dao.removeEntity(po);

	}

	/**
	 * ���ɾ���� ����ֶ�"IS_DELETE"��Ϊ"1"ʱΪɾ����Ϊ"0"ʱδɾ����ʵ�����������ִ�е����޸�"IS_DELETE"�ֶεĲ�����
	 * 
	 * @param id
	 *            Ҫ���ɾ�����ݵı�ʶ
	 */
	public boolean isDelete(String id) {

		OperCustinfo po = (OperCustinfo) dao.loadEntity(OperCustinfo.class, id);// ���������޸ı�
		po.setIsDelete("1"); // "1"����ɾ������˼

		try {
			dao.saveEntity(po);
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	/**
	 * ������ݡ� �����ݿ������һ����¼��
	 * 
	 * @param dto
	 *            �����ݵ�excellence.framework.base.dto.IBaseDTO����
	 */
	public void addQuickMessage(IBaseDTO dto) {

		dao.saveEntity(createQuickMessage(dto));

	}

	/**
	 * ��ӷ����� dto ת po
	 * 
	 * @param dto
	 * @return po
	 */
	private ScreenQuickMessage createQuickMessage(IBaseDTO dto) {

		ScreenQuickMessage po = new ScreenQuickMessage();

		// ����ID
		//custId = ks.getNext("oper_custinfo");
		//po.setCustId(custId);
		// ���ɾ���ֶ�Ϊ"0"
		po.setId(ks.getNext("Screen_QM"));
		po.setMsgTitle((String)dto.get("msgTitle"));
		po.setInputMan((String)dto.get("inputMan"));
		po.setMsgContent((String)dto.get("msgContent"));
		po.setRemark((String)dto.get("remark"));
		po.setCreateDate(new java.util.Date());

		return po;
	}

	/**
	 * ����Ļ��ʾ
	 * @return
	 */
	public String screenOper(){			
		Object[] result = (Object[]) dao.findEntity(new QuickMessageHelp().quickMessageAllQuery());
		StringBuffer sb = new StringBuffer();
		for (int i = 0, size = result.length; i < size; i++) {
			ScreenQuickMessage sqm = (ScreenQuickMessage) result[i];
			sb.append(sqm.getMsgTitle());
			sb.append(" ��");
			sb.append(sqm.getMsgContent());
			sb.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");			
		}
		if(sb.length()>1){
			return sb.toString();
		}
		return null;
	}

	

}
