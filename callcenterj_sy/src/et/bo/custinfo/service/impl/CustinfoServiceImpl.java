/*
 * @(#)CustinfoServiceImpl.java	 2008-03-19
 *
 * ��Ȩ���� ������׿Խ�Ƽ����޹�˾��
 */

package et.bo.custinfo.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.sql.RowSet;

import org.apache.log4j.Logger;

import et.bo.custinfo.service.CustinfoService;
import et.po.OperCustinfo;
import et.po.OperQuestion;
import et.po.SysUser;
import excellence.common.classtree.ClassTreeService;
import excellence.common.key.KeyService;
import excellence.common.page.PageInfo;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

/**
 * <p>
 * �ͻ�����
 * </p>
 * 
 * @version 2008-03-19
 * @author nie
 */

public class CustinfoServiceImpl implements CustinfoService {
	
	static Logger log = Logger.getLogger(CustinfoServiceImpl.class.getName());

	BaseDAO dao = null;

	private int num = 0;

	private int phonenum = 0;

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
	public List userQuery(String sql) {
		RowSet rs=dao.getRowSetByJDBCsql(sql);
		List<SysUser> list=new ArrayList<SysUser>();
		try {
			rs.beforeFirst();
			while (rs.next()) {
				SysUser su=new SysUser();
				su.setUserId(rs.getString("user_id"));
				list.add(su);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	/**
	 * ���ݿͻ�ID��ѯ�����б�,���ظÿͻ��ġ����⡱��list�� ȡ�ò�ѯ�����б����ݡ� ���ص��б����ʱ���������
	 * 
	 * @return �ط����ݵ�list
	 */
	public List getQuestionList() {
		return questionList;
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
	public List custinfoQuery(IBaseDTO dto, PageInfo pi) {

		List list = new ArrayList();
		CustinfoHelp h = new CustinfoHelp();
		
		Object[] result = (Object[]) dao.findEntity(h.custinfoQuery(dto, pi));
		num = dao.findEntitySize(h.custinfoQuery(dto, pi));

		for (int i = 0, size = result.length; i < size; i++) {
			OperCustinfo po = (OperCustinfo) result[i];

			DynaBeanDTO dbdto = new DynaBeanDTO();

			dbdto.set("cust_id", po.getCustId());
			dbdto.set("cust_name", po.getCustName());//����
			dbdto.set("cust_addr", po.getCustAddr());//��ַ			
			dbdto.set("cust_rid",po.getCustRid());//������
			dbdto.set("beginTime", TimeUtil.getTheTimeStr(po.getAddtime(), "yyyy-MM-dd HH:mm"));//����ʱ��
			Set sets = po.getOperQuestions();
			Iterator it = sets.iterator();
			/*
			 * �ڲ��Ķ�form-bean���������cust_tel_home������������Ϣ����beginTime����������ʱ�䡣
			 * �������û�������
			 * ��ʾ�б���Ŀ��˳��Ϊ�����硢��������ַ�������š�����ʱ�䡢����
			 */
			while (it.hasNext()) {
				OperQuestion oq = (OperQuestion) it.next();					
				dbdto.set("cust_tel_home", oq.getCustTel());//����	
			}

			list.add(dbdto);
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
	public List custinfoExpertAllQuery() {
		List list = new ArrayList();
		CustinfoHelp h = new CustinfoHelp();
		try
		{
			Object[] result = (Object[]) dao.findEntity(h.custinfoExpertAllQuery());
			for (int i = 0, size = result.length; i < size; i++) {
				OperCustinfo po = (OperCustinfo) result[i];
				list.add(custinfoToDynaBeanDTO(po));
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
		CustinfoHelp h = new CustinfoHelp();

		Object[] result = (Object[]) dao.findEntity(h.allQuery());

		for (int i = 0, size = result.length; i < size; i++) {
			OperCustinfo po = (OperCustinfo) result[i];
			list.add(custinfoToDynaBeanDTO(po));
		}
		return list;
	}

	/**
	 * ��ѯ������ po ת dto
	 * 
	 * @param po
	 * @return dto
	 */
	private DynaBeanDTO custinfoToDynaBeanDTO(OperCustinfo po) {

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
		dto.set("cust_rid", po.getCustRid());
		dto.set("cust_voc", po.getDictCustVoc());
		dto.set("cust_scale", po.getDictCustScale());
		dto.set("expert_type", po.getExpertType());
		
		String cust_type = po.getDictCustType();
		if (cust_type != null && cust_type.length() > 15) {
			try{
				cust_type = cts.getLabelById(cust_type);
			}catch(Exception e){
				System.err.println(e);
			}
		}

		dto.set("cust_type", cust_type);
		dto.set("remark", po.getRemark());

		return dto;
	}

	/**
	 * ��ѯ�����б�������� ȡ�������ѯ�б��������
	 * 
	 * @return �õ�list������
	 */
	public int getCustinfoSize() {

		return num;

	}

	/**
	 * ����IDȡ��һ�����ݵ�excellence.framework.base.dto.IBaseDTO���� ȡ��ĳ�����ݵ���ϸ��Ϣ��
	 * 
	 * @param id
	 *            ȡ��excellence.framework.base.dto.IBaseDTO�ı�ʶ
	 * @return ����������Ϣ��excellence.framework.base.dto.IBaseDTO����
	 */
	public IBaseDTO getCustinfoInfo(String id) {

		OperCustinfo po = (OperCustinfo) dao.loadEntity(OperCustinfo.class, id);
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
		String dictCusttype = po.getDictCustType();
		dto.set("cust_type", dictCusttype);
		dto.set("remark", po.getRemark());

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

		CustinfoHelp h = new CustinfoHelp();
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
			
			String dict_question_type1 = oq.getDictQuestionType1();
			ddto.set("dict_question_type1", dict_question_type1);
			
			String id = oq.getId();
			if(dict_question_type1 != null && dict_question_type1.equals("�۸���") || dict_question_type1.equals("���󷢲�") || dict_question_type1.equals("���ߵ���")){
				ddto.set("id", this.getTableId(dict_question_type1, id));//ȥ��ı����IDȥ����ͬ��������ת��ͬ��ID��ͬ
			}else{
				ddto.set("id", id);
			}
			
			ddto.set("cust_tel", oq.getCustTel());
			ddto.set("rid", oq.getRid());
			
			
			String question_content = oq.getQuestionContent();
			if(question_content != null && question_content.length() > 25){
				question_content = question_content.substring(0, 25) + "��";
			}
			ddto.set("question_content", question_content);

			ddto.set("addtime", TimeUtil.getTheTimeStr(oq.getAddtime(), "yyyy-MM-dd HH:mm"));

			questionList.add(ddto);
		}

		return dto;
	}
	private String getTableId(String dict_question_type1, String id){
		
		String idField = "";
		String table = "";
		if(dict_question_type1.equals("�۸���")){
			idField = "price_id";
			table = "oper_priceinfo";
			
		}else if(dict_question_type1.equals("���󷢲�")){
			idField = "sad_id";
			table = "oper_sadinfo";
			
		}else{
			return id;
		}
		
		try {
			String sql = "select "+ idField +" from "+ table +" where question_id ='"+ id +"'";
			RowSet rs = dao.getRowSetByJDBCsql(sql);
			if(rs.next()){
				id = rs.getString(1);
			}else{
				id = "false" + id;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return id;
	}
	

	/**
	 * �޸����ݡ� �޸�ĳ����¼�����ݡ�
	 * 
	 * @param dto
	 *            Ҫ���µĵ�excellence.framework.base.dto.IBaseDTO����
	 * @return boolean
	 */
	public boolean updateCustinfo(IBaseDTO dto) {

		try {
			dao.saveEntity(modifycoll(dto));
//			log.info("�ҵ���һ�ξ͸���һ���û���Ϣ��������");
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
	private OperCustinfo modifycoll(IBaseDTO dto) {

		OperCustinfo po = (OperCustinfo) dao.loadEntity(OperCustinfo.class, dto
				.get("cust_id").toString());// ���������޸ı�

		po.setCustName(dto.get("cust_name").toString());
		po.setDictSex(dto.get("dict_sex").toString());
		po.setCustEmail(dto.get("cust_email").toString());
		po.setCustAddr(dto.get("cust_addr").toString());
		po.setCustPcode(dto.get("cust_pcode").toString());
		po.setCustTelHome(dto.get("cust_tel_home").toString());
		po.setCustTelWork(dto.get("cust_tel_work").toString());
		po.setCustTelMob(dto.get("cust_tel_mob").toString());
		po.setCustFax(dto.get("cust_fax").toString());
		po.setCustRid(dto.get("cust_rid").toString());
		po.setDictCustVoc(dto.get("cust_voc").toString());
		po.setDictCustScale(dto.get("cust_scale").toString());
		po.setDictCustType(dto.get("cust_type").toString());
		po.setAddtime(TimeUtil.getNowTime());
		po.setRemark(dto.get("remark").toString());
		po.setModifyTime(new java.util.Date());
		return po;
	}

	/**
	 * ɾ�����ݡ� ɾ��ĳ����¼��
	 * 
	 * @param id
	 *            Ҫɾ�����ݵı�ʶ
	 */
	public void delCustinfo(String id) {

		OperCustinfo cq = (OperCustinfo) dao.loadEntity(OperCustinfo.class, id);
		//dao.removeEntity(cq);
		cq.setIsDelete("1");
		dao.saveEntity(cq);
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
	public void addCustinfo(IBaseDTO dto) {
		
//		System.out.println("����ʱ�ɹ����һ���µ����ݣ�");
		dao.saveEntity(createCustinfo(dto));
//		System.out.println("���û��������Ѿ��ɹ����!");
		
	}

	/**
	 * ��ӷ����� dto ת po
	 * 
	 * @param dto
	 * @return po
	 */
	private OperCustinfo createCustinfo(IBaseDTO dto) {

		OperCustinfo po = new OperCustinfo();

		// ����ID
		custId = ks.getNext("oper_custinfo");
		po.setCustId(custId);
		// ���ɾ���ֶ�Ϊ"0"
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
		po.setCustRid(dto.get("cust_rid").toString());
		po.setAddtime(TimeUtil.getNowTime());
		po.setRemark(dto.get("remark").toString());
		
		return po;
	}

	/**
	 * ȡ���¿ͻ���¼��ID
	 * 
	 * @return custID
	 */
	public String getCustId() {

		return custId;

	}

	public int getPhoneSize() {
		// TODO Auto-generated method stub
		return phonenum;
	}

	public List phoneQuery(IBaseDTO dto, PageInfo pi) {
		// TODO Auto-generated method stub
		List list = new ArrayList();
		CustinfoHelp h = new CustinfoHelp();

		Object[] result = (Object[]) dao.findEntity(h.phoneinfoQuery(dto, pi));
		phonenum = dao.findEntitySize(h.phoneinfoQuery(dto, pi));

		for (int i = 0, size = result.length; i < size; i++) {
			OperCustinfo po = (OperCustinfo) result[i];
			list.add(custinfoToDynaBeanDTO(po));
		}
		return list;
	}

}
