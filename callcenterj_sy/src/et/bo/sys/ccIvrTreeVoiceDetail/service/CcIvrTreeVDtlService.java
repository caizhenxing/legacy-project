package et.bo.sys.ccIvrTreeVoiceDetail.service;

import java.util.List;

import excellence.common.page.PageInfo;
import excellence.common.tools.LabelValueBean;
import excellence.framework.base.dto.IBaseDTO;

public interface CcIvrTreeVDtlService {
	/**
	 * @describe addCcIvrTreeInfo
	 * @param
	 * @return void
	 */ 
	public void addCcIvrTreeInfoText(IBaseDTO dto);
	/**
	 * @describe addCcIvrTreeInfo
	 * @param
	 * @return void
	 */ 
	public void addCcIvrTreeInfo(IBaseDTO dto);
	/**
	 * @describe �޸��г��۸�
	 * @param
	 * @return void
	 */ 
	public boolean updateCcIvrTreeInfo(IBaseDTO dto);
	/**
	 * @describe ɾ���г��۸�
	 * @param
	 * @return void
	 */ 
	public void delCcIvrTreeInfo(String id);
	
	
	/**
	 * @describe ��ѯ�г��۸��б�
	 * @param
	 * @return List
	 */ 
	public List operCcIvrTreeInfoList(IBaseDTO dto, PageInfo pi);
	
	
	/**
	 * @describe ȡ�ò�ѯ����
	 * @param
	 * @return int
	 */ 
	
	public int getCcIvrTreeInfoSize();
	
	
	/**
	 * @describe ����Idȡ����Ϣ
	 * @param
	 * @return dto(user����)
	 */ 
	public IBaseDTO getCcIvrTreevoiceDetail(String id);
	/**
	 * �õ�ivr id label List����ͼ��
	 * @return List LabelValueBeanList
	 */
	public List<LabelValueBean> getIvrLVList();
}
