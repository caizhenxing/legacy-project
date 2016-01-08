/*
 * @(#)CallbackServiceImpl.java	 2008-04-01
 *
 * ��Ȩ���� ������׿Խ�Ƽ����޹�˾��
 */
package et.bo.incommingInfo.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.sql.RowSet;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;

import et.bo.incommingInfo.IncommingInfo;
import et.bo.incommingInfo.service.IncommingInfoService;
import et.po.OperInquiryResult;
import et.po.OperInquiryinfo;
import et.po.OperPriceinfo;
import et.po.OperQuestion;
import et.po.OperSadinfo;
import excellence.common.classtree.ClassTreeService;
import excellence.common.key.KeyService;
import excellence.common.page.PageInfo;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;

/**
 * <p>
 * ������Ϣdao��
 * </p>
 * 
 * @version 2008-06-13
 * @author wangwenquan
 */
public class IncommingInfoServiceImpl implements IncommingInfoService {

	private BaseDAO dao = null;

	private KeyService ks = null;

	private ClassTreeService cts = null;

	private int num = 0;

	public static HashMap hashmap = new HashMap();

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

	// ɾ�����ﲻ��Ҫ
	public void addIncommingInfo(IBaseDTO dto) {
		// TODO Auto-generated method stub
		// do something
	}

	// ɾ�����ﲻ��Ҫ
	private IncommingInfo createIncommingInfoDetail(IBaseDTO dto) {
		IncommingInfo info = new IncommingInfo();
		info.setAddtimeBegin((String) dto.get("addtimeBegin"));
		info.setAddtimeEnd((String) dto.get("addtimeEnd"));
		info.setAnswerContent((String) dto.get("answerContent"));
		info.setDictQuestionType1((String) dto.get("dictQuestionType1"));
		info.setMainId((String) dto.get("mainId"));
		info.setQuestionContent((String) dto.get("questionContent"));
		info.setQuestionId((String) dto.get("questionId"));
		info.setRespondent((String) dto.get("respondent"));
		info.setTalkId((String) dto.get("talkId"));
		info.setTel_num((String) dto.get("tel_num"));

		return info;
	}

	// ɾ�����ﲻ��Ҫ
	public void delIncommingInfo(String id) {
		// TODO Auto-generated method stub
		// do something
	}

	/**
	 * ȡ������Ϣ��ϸ��Ϣ
	 */
	public IBaseDTO getIncommingInfoDetail(String id) {
		// TODO Auto-generated method stub
		IncommingInfoHelp help = new IncommingInfoHelp();
		OperQuestion oq = (OperQuestion) dao.loadEntity(OperQuestion.class, id);
		IBaseDTO dto = new DynaBeanDTO();
		dto.set("tel_num", oq.getCustTel());
		dto.set("addtime", oq.getAddtime());
		String dictQuestionType = oq.getDictQuestionType1();
		dto.set("dictQuestionType1", dictQuestionType);
		dto.set("respondent", oq.getAnswerAgent());
		dto.set("questionContent", oq.getQuestionContent());
		dto.set("answerContent", oq.getAnswerContent());
		// ����Ǽ۸��ͣ���ʾ�ĸ�ʽ��
		// ���۸��͡���ʾ��ʽΪ����Ʒ����+�۸�����+�۸�+�����������ע�����磺�����չ���5.80Ԫ/���ǰ���½��������չ���0.72Ԫ/�ƽ�ȡ�
		if (dictQuestionType.equals("�۸���")) {

			dto.set("questionContent", "�۸���");
			dto.set("answerContent", getQuestionById(id));
		}
		// ����ǹ��󷢲�����ʾ�ĸ�ʽ��
		// �����󷢲�����ʾ��ʽΪ����ϵ��ַ+��ϵ��+��������+��Ʒ����+��Ʒ������+��ϵ�绰��+��ע�����磺�������������Ԥ������2��֣���ϵ�绰1333356920���������٣��۸����顣
		if (dictQuestionType.equals("���󷢲�")) {

			dto.set("questionContent", "���󷢲�");
			dto.set("answerContent", getSadById(id));
		}
		if(dictQuestionType.equals("���ߵ���"))
		{
			OperInquiryResult inquiryResult = getOperInquiryResultByQId(oq.getId());
			if(inquiryResult!=null)
			{
				String title = "";
				OperInquiryinfo info = getOperInquiryinfoByResultId(inquiryResult.getTopicId());
				if(info!=null)
				{
					title = info.getTopic();
				}
				dto.set("questionContent", oq.getExpertName()+"����"+title);
				
			}
		}
		dto.set("id", oq.getId());

		dto.set("dictIsAnswerSucceed", oq.getDictIsAnswerSucceed());
		dto.set("answerMan", oq.getAnswerMan());
		dto.set("dictIsCallback", oq.getDictIsCallback());
		dto.set("billNum", oq.getBillNum());
		dto.set("expertName", oq.getExpertName());

		return dto;
	}

	/**
	 * ���ݼ۸�͹�������ݲ�ѯ��������װ�ɶ�Ӧ�ĸ�ʽ��ʾ
	 * 
	 * @param id
	 *            �浽question�е�id����Ϣ
	 * @return ָ����ʽ���ַ���
	 */
	private String getQuestionById(String id) {
		String result = "";
		MyQuery mq = new MyQueryImpl();
		String hql = "from OperPriceinfo op where op.questionId = '" + id + "'";
		mq.setHql(hql);
		Object[] o = (Object[]) dao.findEntity(mq);
		for (int i = 0; i < o.length; i++) {
			OperPriceinfo oq = (OperPriceinfo) o[i];
			String dictPriceType = cts.getLabelById(oq.getDictPriceType());
			result += oq.getCustAddr() + oq.getProductName() + dictPriceType
					+ oq.getProductPrice() + "Ԫ/��" + oq.getRemark()+" ; ";
		}
		return result;
	}
	
	/**
	 * ���ݼ۸�͹�������ݲ�ѯ��������װ�ɶ�Ӧ�ĸ�ʽ��ʾ
	 * 
	 * @param id
	 *            �浽question�е�id����Ϣ
	 * @return ָ����ʽ���ַ���
	 */
	private String getSadById(String id) {
		String result = "";
		MyQuery mq = new MyQueryImpl();
		String hql = "from OperSadinfo op where op.questionId = '" + id + "'";
		mq.setHql(hql);
		Object[] o = (Object[]) dao.findEntity(mq);
		for (int i = 0; i < o.length; i++) {
			OperSadinfo oq = (OperSadinfo) o[i];
			String dictSadType = cts.getLabelById(oq.getDictSadType());
			result = oq.getCustAddr() + oq.getCustName() + dictSadType
					+ oq.getProductName() + oq.getProductCount() + oq.getRemark();
		}
		return result;
	}
	
	

	public int getIncommingInfoSize() {
		// TODO Auto-generated method stub
		return num;
	}

	/**
	 * ����������Ϣ�б�
	 */
	public boolean updateIncommingInfoInfo(IBaseDTO dto) {
		String id = dto.get("id").toString();
		OperQuestion question = (OperQuestion) dao.loadEntity(
				OperQuestion.class, id);
		if (question != null) {
			question
					.setDictQuestionType1((String) dto.get("dictQuestionType1"));
			question.setQuestionContent((String) dto.get("questionContent"));
			question.setAnswerContent((String) dto.get("answerContent"));

			question.setDictIsAnswerSucceed((String) dto
					.get("dictIsAnswerSucceed"));
			question.setAnswerMan((String) dto.get("answerMan"));
			question.setDictIsCallback((String) dto.get("dictIsCallback"));
			question.setExpertName((String) dto.get("expertName"));
			question.setBillNum((String) dto.get("billNum"));

			dao.saveEntity(question);
		}
		// }
		return true;
	}

	/**
	 * ��ȡscreen��ר���������
	 */
	public List screenList() {
		// TODO Auto-generated method stub
		List l = new ArrayList();
		MyQuery mq=new MyQueryImpl();
		DetachedCriteria dc=DetachedCriteria.forClass(OperQuestion.class);
		
//		dc.add(Restrictions.eq("dictCaseType", "putong"));
		dc.addOrder(Order.desc("ringBegintime"));
		
		mq.setDetachedCriteria(dc);
		mq.setFirst(0);
		mq.setFetch(50);
		
		Object[] result = (Object[]) dao.findEntity(mq);
		
		for (int i = 0, size = result.length; i < size; i++) {
			OperQuestion oq = (OperQuestion) result[i];
			DynaBeanDTO dbd = new DynaBeanDTO();
			
			String ringBegintime = "";//����ʱ��
			Object obj = oq.getRingBegintime();
			if(obj!=null) {
				ringBegintime = obj.toString();
				ringBegintime = ringBegintime.substring(0,ringBegintime.indexOf("."));
			}
			dbd.set("ringBegintime", ringBegintime);
			//��ѯ��Ŀ
			String dictQuestionType = oq.getDictQuestionType1();
			dbd.set("dictQuestionType1", dictQuestionType);
			//��ѯ����
			String question = oq.getQuestionContent();
			if (question.length() > 24) {
				question = question.substring(0, 24) + "...";
			}
			dbd.set("questionContent", question);
			//���״̬
			if (oq.getAnswerMan() != null) {
				dbd.set("answerMan", cts.getLabelById(oq.getAnswerMan()));
			} else {
				dbd.set("answerMan", "");
			}
			String telnum = oq.getCustTel();
			if(!"".equals(telnum)){
				String sql = "select top 1 cust_name,cust_addr from oper_custinfo where "+
				"'"+telnum+"' in (cust_tel_home,cust_tel_work,cust_tel_mob) order by addtime desc";
				RowSet rs=dao.getRowSetByJDBCsql(sql);
				try {
					while(rs.next()){
						String cust_name = rs.getString("cust_name").trim();//�û���
						String cust_addr = rs.getString("cust_addr").trim();//�û���ַ
						dbd.set("cust_name", cust_name);
						dbd.set("cust_addr", cust_addr);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			l.add(dbd);
		}
		return l;
	}	
	
	/**
	 * ȡ��������Ϣ�б�
	 */
	public List<DynaBeanDTO> incommingInfoList(IBaseDTO dto, PageInfo pi) {
		// TODO Auto-generated method stub
		List<DynaBeanDTO> list = new ArrayList<DynaBeanDTO>();
		IncommingInfoHelp sh = new IncommingInfoHelp();
		Object[] result = (Object[]) dao.findEntity(sh.operIncommingInfoQuery(
				dto, pi));
		try {
			num = dao.findEntitySize(sh.operIncommingInfoQueryForSize(dto, pi));
			System.out.println("size is:"+num);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

		for (int i = 0, size = result.length; i < size; i++) {
			DynaBeanDTO dbd = new DynaBeanDTO();
			OperQuestion oq = (OperQuestion) result[i];
			dbd.set("id", oq.getId());
			dbd.set("tel_num", oq.getCustTel());
			dbd.set("addtime", TimeUtil.getTheTimeStr(oq.getAddtime(),
					"yyyy-MM-dd HH:mm:ss"));
			String dictQuestionType = oq.getDictQuestionType1();
			dbd.set("dictQuestionType1", dictQuestionType);
			dbd.set("respondent", oq.getAnswerAgent());
			String question = oq.getQuestionContent();
			if (question.length() > 12) {
				question = question.substring(0, 12) + "...";
			}
			
			dbd.set("questionContent", question);
			if (dictQuestionType.equals("�۸���")) {
				dbd.set("questionContent", "�۸���");
			}
			if (dictQuestionType.equals("���󷢲�")) {
				dbd.set("questionContent", "���󷢲�");
			}
			if(dictQuestionType.equals("���ߵ���"))
			{
				OperInquiryResult inquiryResult = getOperInquiryResultByQId(oq.getId());
				if(inquiryResult!=null)
				{
					String title = "";
					OperInquiryinfo info = getOperInquiryinfoByResultId(inquiryResult.getTopicId());
					if(info!=null)
					{
						title = info.getTopic();
					}
					dbd.set("questionContent", oq.getExpertName()+"����"+title);
					
				}
			}
			dbd.set("answerContent", oq.getAnswerContent());

			if (oq.getDictIsAnswerSucceed() != null) {
				dbd.set("dictIsAnswerSucceed", cts.getLabelById(oq
						.getDictIsAnswerSucceed()));
			} else {
				dbd.set("dictIsAnswerSucceed", "");
			}

			if (oq.getAnswerMan() != null) {
				dbd.set("answerMan", cts.getLabelById(oq.getAnswerMan()));
			} else {
				dbd.set("answerMan", "");
			}

			dbd.set("dictIsCallback", oq.getDictIsCallback());
			dbd.set("expertName", oq.getExpertName());

			list.add(dbd);
		}
		return list;
	}
	private OperInquiryResult getOperInquiryResultByQId(String questionId)
	{
		OperInquiryResult o = null;
		String hql = "from OperInquiryResult a where a.questionId='"+questionId+"'";
		MyQuery mq = new MyQueryImpl();
		mq.setHql(hql);
		Object[] os = dao.findEntity(mq);
		if(os.length>0)
		{
			o = (OperInquiryResult)os[0];
		}
		return o;
	}
	private OperInquiryinfo getOperInquiryinfoByResultId(String id)
	{
		OperInquiryinfo o = null;
		String hql = "from OperInquiryinfo a where a.id='"+id+"'";
		MyQuery mq = new MyQueryImpl();
		mq.setHql(hql);
		Object[] os = dao.findEntity(mq);
		if(os.length>0)
		{
			o = (OperInquiryinfo)os[0];
		}
		return o;
	}
	public ClassTreeService getCts() {
		return cts;
	}

	public void setCts(ClassTreeService cts) {
		this.cts = cts;
	}

	public List getExpertList() {
		return cts.getLabelVaList("zhuanjialeibie");
	}

}
