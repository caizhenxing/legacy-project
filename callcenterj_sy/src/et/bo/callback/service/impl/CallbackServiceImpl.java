/*
 * @(#)CallbackServiceImpl.java	 2008-04-01
 *
 * ��Ȩ���� ������׿Խ�Ƽ����޹�˾��
 */

package et.bo.callback.service.impl;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

import et.bo.callback.service.CallbackService;
import et.po.OperCallback;
import et.po.OperQuestion;
import excellence.common.key.KeyService;
import excellence.common.page.PageInfo;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

/**
 * <p>�طù���</p>
 * 
 * @version 2008-04-01
 * @author nie
 */

public class CallbackServiceImpl implements CallbackService {
	
	BaseDAO dao = null;
	private int num = 0;
	private String custId = null;
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
	
	/**
	 * ��ѯ�����б�,���ؼ�¼��list��
	 * ȡ�ò�ѯ�����б����ݡ�
	 * @param dto ���ݴ������
	 * @param pi ҳ����Ϣ
	 * @return ���ݵ�list
	 */
	public List callbackQuery(IBaseDTO dto, PageInfo pi) {
		
		List list = new ArrayList();
		CallbackHelp h = new CallbackHelp();
		
		Object[] result = (Object[]) dao.findEntity(h.custinfoQuery(dto, pi));
		num = dao.findEntitySize(h.custinfoQuery(dto, pi));
		
		for (int i = 0, size = result.length; i < size; i++) {
			OperCallback po = (OperCallback) result[i];
			list.add(custinfoToDynaBeanDTO(po));
		}
		return list;
	}
	
	/**
	 * ��ѯ������ po ת dto
	 * @param po
	 * @return dto
	 */
	private DynaBeanDTO custinfoToDynaBeanDTO(OperCallback po){
		
		DynaBeanDTO dto = new DynaBeanDTO();
		
		dto.set("id", po.getId());
		dto.set("callback_content", po.getCallbackContent());
		dto.set("remark", po.getRemark());
		String d = DateFormat.getDateInstance().format(po.getCallbackTime());
		dto.set("callback_time", d);
		
		return dto;
	}
	/**
	 * ��ѯ�����б��������
	 * ȡ�������ѯ�б��������
	 * @return �õ�list������
	 */
	public int getCallbackSize() {
		
		return num;
	
	}
	/**
	 * ����IDȡ��һ�����ݵ�excellence.framework.base.dto.IBaseDTO����
	 * ȡ��ĳ�����ݵ���ϸ��Ϣ��
	 * @param id ȡ��excellence.framework.base.dto.IBaseDTO�ı�ʶ
	 * @return ����������Ϣ��excellence.framework.base.dto.IBaseDTO����
	 */
	public IBaseDTO getCallbackInfo(String id) {
		
		OperCallback po = (OperCallback)dao.loadEntity(OperCallback.class,id);
		IBaseDTO dto = new DynaBeanDTO();
		
		dto.set("id", po.getId());
		dto.set("callback_content", po.getCallbackContent());
		dto.set("is_callback_succ", po.getIsCallbackSucc());
		dto.set("remark", po.getRemark());
		String d = DateFormat.getDateInstance().format(po.getCallbackTime());
		dto.set("callback_time", d);
		
		return dto;
		
	}

	/**
	 * �޸����ݡ�
	 * �޸�ĳ����¼�����ݡ�
	 * @param dto Ҫ���µĵ�excellence.framework.base.dto.IBaseDTO����
	 * @return boolean
	 */
	public boolean updateCallback(IBaseDTO dto) {
		
		OperCallback po = (OperCallback)dao.loadEntity(OperCallback.class,dto.get("id").toString());//���������޸ı�
		
		po.setCallbackTime(TimeUtil.getTimeByStr(dto.get("callback_time").toString(),"yyyy-MM-dd"));		//ʱ��
		po.setCallbackContent(dto.get("callback_content").toString());	//����
		po.setRemark(dto.get("remark").toString());			//��ע
		po.setIsCallbackSucc(dto.get("is_callback_succ").toString());
		
		try{
			dao.saveEntity(po);
			return true;
		}catch(Exception e){
			return false;
		}
	}
	/**
	 * ɾ�����ݡ�
	 * ɾ��ĳ����¼��
	 * @param id Ҫɾ�����ݵı�ʶ
	 */
	public void delCallback(String id) {
		
		OperCallback cq = (OperCallback)dao.loadEntity(OperCallback.class,id);
		dao.removeEntity(cq);
		
	}
	/**
	 * ���ɾ����
	 * ����ֶ�"IS_DELETE"��Ϊ"1"ʱΪɾ����Ϊ"0"ʱδɾ����ʵ�����������ִ�е����޸�"IS_DELETE"�ֶεĲ�����
	 * @param id Ҫ���ɾ�����ݵı�ʶ
	 */
	public boolean isDelete(String id) {
		
		OperCallback po = (OperCallback)dao.loadEntity(OperCallback.class,id);//���������޸ı�
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
	public void addCallback(IBaseDTO dto) {
		
		OperCallback po = new OperCallback();
		OperQuestion operQuestion = new OperQuestion();
		
		po.setId(ks.getNext("oper_callback"));	//����ID
		po.setIsDelete("0");					//����δɾ��
		
		String qid = dto.get("question_id").toString();
		if(qid != null && !qid.equals("")){
			operQuestion.setId(qid);
			po.setOperQuestion(operQuestion);		//����id
		}
		
		po.setCallbackTime(TimeUtil.getTimeByStr(dto.get("callback_time").toString(),"yyyy-MM-dd"));		//ʱ��
		po.setCallbackContent(dto.get("callback_content").toString());	//����
		po.setRemark(dto.get("remark").toString());			//��ע
		po.setIsCallbackSucc(dto.get("is_callback_succ").toString());	//�Ƿ�ɹ�
		
		dao.saveEntity(po);

	}

	/**
	 * ȡ���¿ͻ���¼��ID
	 * @return custID
	 */
	public String getCustId() {
		
		return custId;
		
	}
}
