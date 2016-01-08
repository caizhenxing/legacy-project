/*
 * @(#)CustinfoServiceImpl.java	 2008-03-19
 *
 * ��Ȩ���� ������׿Խ�Ƽ����޹�˾��
 */

package et.bo.callcenter.userInfo.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import et.bo.callcenter.userInfo.service.UserInfoService;
import et.po.OperCustinfo;
import et.po.OperQuestion;
import excellence.common.classtree.ClassTreeService;
import excellence.common.key.KeyService;
import excellence.common.page.PageInfo;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

/**
 * <p>�ͻ�����</p>
 * 
 * @version 2008-03-19
 * @author nie
 */

public class UserInfoImpl implements UserInfoService {
	
	BaseDAO dao = null;
	private int num = 0;
	private String custId = null;
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
	 * ���ݿͻ�ID��ѯ�����б�,���ظÿͻ��ġ����⡱��list�� ȡ�ò�ѯ�����б����ݡ�
	 * ���ص��б����ʱ���������
	 * @return �ط����ݵ�list
	 */
	public List getQuestionList() {
		List<String> l = new ArrayList<String>();
		List qList = new ArrayList();
		for(int i = 0; i<questionList.size(); i++){
			DynaBeanDTO dto = (DynaBeanDTO)questionList.get(i);
			l.add((String)dto.get("addtime"));
		}
		Collections.sort(l);
		for(int i = l.size()-1; i>=0; i--){
			for(int j = 0; j<questionList.size(); j++ ){
				DynaBeanDTO dto = (DynaBeanDTO)questionList.get(j);
				if(l.get(i).equals( dto.get("addtime")) ){
					qList.add(dto);
					break;
				}
			}
		}
		
		return qList;
	}
	/**
	 * ��ѯ�����б�,���ؼ�¼��list��
	 * ȡ�ò�ѯ�����б����ݡ�
	 * @param dto ���ݴ������
	 * @param pi ҳ����Ϣ
	 * @return ���ݵ�list
	 */
	public List custinfoQuery(IBaseDTO dto, PageInfo pi) {
		
		List list = new ArrayList();
		UserInfoHelp h = new UserInfoHelp();
		
		Object[] result = (Object[]) dao.findEntity(h.custinfoQuery(dto, pi));
		num = dao.findEntitySize(h.custinfoQuery(dto, pi));
		
		for (int i = 0, size = result.length; i < size; i++) {
			OperCustinfo po = (OperCustinfo) result[i];
			list.add(custinfoToDynaBeanDTO(po));
		}
		return list;
	}
	/**
	 * ��ѯ�����б�,����ȫ����¼��list��
	 * ȡ�ò�ѯ�����б����ݡ�
	 * @param dto ���ݴ������
	 * @param pi ҳ����Ϣ
	 * @return ���ݵ�list
	 */
	public List custinfoAllQuery() {
		
		List list = new ArrayList();
		UserInfoHelp h = new UserInfoHelp();
		
		Object[] result = (Object[]) dao.findEntity(h.allQuery());
		
		for (int i = 0, size = result.length; i < size; i++) {
			OperCustinfo po = (OperCustinfo) result[i];
			list.add(custinfoToDynaBeanDTO(po));
		}
		return list;
	}
	/**
	 * ��ѯ������ po ת dto
	 * @param po
	 * @return dto
	 */
	private DynaBeanDTO custinfoToDynaBeanDTO(OperCustinfo po){
		
		DynaBeanDTO dto = new DynaBeanDTO();
		
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
		String cust_type = po.getDictCustType();
		if(cust_type != null && cust_type.length() > 15){
			cust_type = cts.getLabelById(cust_type);
		}
		dto.set("cust_type", cust_type);
		dto.set("remark", po.getRemark());

		return dto;
	}
	/**
	 * ��ѯ�����б��������
	 * ȡ�������ѯ�б��������
	 * @return �õ�list������
	 */
	public int getCustinfoSize() {
		
		return num;
	
	}
	/**
	 * ����IDȡ��һ�����ݵ�excellence.framework.base.dto.IBaseDTO����
	 * ȡ��ĳ�����ݵ���ϸ��Ϣ��
	 * @param id ȡ��excellence.framework.base.dto.IBaseDTO�ı�ʶ
	 * @return ����������Ϣ��excellence.framework.base.dto.IBaseDTO����
	 */
	public IBaseDTO getCustinfoInfo(String id) {
		
		OperCustinfo po = (OperCustinfo)dao.loadEntity(OperCustinfo.class,id);
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
		
		return dto;
		
	}
	/**
	 * ���ݵ绰����ȡ��һ�����ݵ�excellence.framework.base.dto.IBaseDTO����
	 * ���ݵ绰�����ѯ�û�լ�磬�칫�绰���ֻ���ȡ��ĳ�ͻ�����ϸ��Ϣ��
	 * @param id ȡ��excellence.framework.base.dto.IBaseDTO�ı�ʶ
	 * @return ����������Ϣ��excellence.framework.base.dto.IBaseDTO����
	 */
	public IBaseDTO getopenwinInfo(String tel) {
		
		UserInfoHelp h = new UserInfoHelp();
		Object[] obj = (Object[]) dao.findEntity(h.openwinQuery(tel));
		
		OperCustinfo po = new OperCustinfo();
		if(obj.length > 0){				//������ؽ���������0
			po = (OperCustinfo) obj[0];	//��ȡ�õ�һ������ת��po
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
		while(it.hasNext()){
			OperQuestion oq = (OperQuestion)it.next();
			DynaBeanDTO ddto = new DynaBeanDTO();
			
			ddto.set("id", oq.getId());
			ddto.set("dict_question_type1", oq.getDictQuestionType1());
			ddto.set("question_content", oq.getQuestionContent());
			
			String dict_is_answer_succeed = oq.getDictIsAnswerSucceed();
			if(dict_is_answer_succeed!=null&&dict_is_answer_succeed.indexOf("SYS")!=-1)
				dict_is_answer_succeed = cts.getLabelById(dict_is_answer_succeed);
			ddto.set("dict_is_answer_succeed", dict_is_answer_succeed);
			
			String d = new java.text.SimpleDateFormat("yy-MM-dd HH:mm").format(oq.getAddtime());
			ddto.set("addtime", d);
			
			questionList.add(ddto);
		}

		return dto;
	}
	/**
	 * �޸����ݡ�
	 * �޸�ĳ����¼�����ݡ�
	 * @param dto Ҫ���µĵ�excellence.framework.base.dto.IBaseDTO����
	 * @return boolean
	 */
	public boolean updateCustinfo(IBaseDTO dto) {
		
		try{
			dao.saveEntity(modifycoll(dto));
			return true;
		}catch(Exception e){
			return false;
		}
	}
	/**
	 * �޸ķ����� dto ת po
	 * @param dto
	 * @return po
	 */
	private OperCustinfo modifycoll(IBaseDTO dto){

		OperCustinfo po = (OperCustinfo)dao.loadEntity(OperCustinfo.class,dto.get("cust_id").toString());//���������޸ı�

//		po.setCustId(dto.get("cust_id").toString());
		po.setCustName(dto.get("cust_name").toString());
		po.setDictSex(dto.get("dict_sex").toString());
		po.setCustEmail(dto.get("cust_email").toString());
		po.setCustAddr(dto.get("cust_addr").toString());
		po.setCustPcode(dto.get("cust_pcode").toString());
		po.setCustTelHome(dto.get("cust_tel_home").toString());
		po.setCustTelWork(dto.get("cust_tel_work").toString());
		po.setCustTelMob(dto.get("cust_tel_mob").toString());
		po.setCustFax(dto.get("cust_fax").toString());
		po.setDictCustVoc(dto.get("cust_voc").toString());
		po.setDictCustScale(dto.get("cust_scale").toString());
		po.setDictCustType(dto.get("cust_type").toString());
		po.setRemark(dto.get("remark").toString());
		
		return po;
	}
	/**
	 * ɾ�����ݡ�
	 * ɾ��ĳ����¼��
	 * @param id Ҫɾ�����ݵı�ʶ
	 */
	public void delCustinfo(String id) {
		
		OperCustinfo cq = (OperCustinfo)dao.loadEntity(OperCustinfo.class,id);
		dao.removeEntity(cq);
		
	}
	/**
	 * ���ɾ����
	 * ����ֶ�"IS_DELETE"��Ϊ"1"ʱΪɾ����Ϊ"0"ʱδɾ����ʵ�����������ִ�е����޸�"IS_DELETE"�ֶεĲ�����
	 * @param id Ҫ���ɾ�����ݵı�ʶ
	 */
	public boolean isDelete(String id) {
		
		OperCustinfo po = (OperCustinfo)dao.loadEntity(OperCustinfo.class,id);//���������޸ı�
		po.setIsDelete("1");	//"1"����ɾ������˼
		
		try{
			dao.saveEntity(po);
			return true;
		}catch(Exception e){
			return false;
		}
		
	}
	/**
	 * ������ݡ�
	 * �����ݿ������һ����¼��
	 * @param dto �����ݵ�excellence.framework.base.dto.IBaseDTO����
	 */
	public void addCustinfo(IBaseDTO dto) {
		
		dao.saveEntity(createCustinfo(dto));
		
	}
	/**
	 * ��ӷ����� dto ת po
	 * @param dto
	 * @return po
	 */
	private OperCustinfo createCustinfo(IBaseDTO dto){
		
		OperCustinfo po = new OperCustinfo();
		
		//����ID
		custId = ks.getNext("oper_custinfo");
		po.setCustId(custId);
		//���ɾ���ֶ�Ϊ"0"
		po.setIsDelete("0");
		
		po.setCustName(dto.get("cust_name").toString());
		po.setDictSex(dto.get("dict_sex").toString());
		po.setCustEmail(dto.get("cust_email").toString());
		po.setCustAddr(dto.get("cust_addr").toString());
		po.setCustPcode(dto.get("cust_pcode").toString());
		po.setCustTelHome(dto.get("cust_tel_home").toString());
		po.setCustTelWork(dto.get("cust_tel_work").toString());
		po.setCustTelMob(dto.get("cust_tel_mob").toString());
		po.setCustFax(dto.get("cust_fax").toString());
		po.setDictCustVoc(dto.get("cust_voc").toString());
		po.setDictCustScale(dto.get("cust_scale").toString());
		po.setDictCustType(dto.get("cust_type").toString());
		po.setRemark(dto.get("remark").toString());
		
		return po;
	}
	/**
	 * ȡ���¿ͻ���¼��ID
	 * @return custID
	 */
	public String getCustId() {
		
		return custId;
		
	}

}
