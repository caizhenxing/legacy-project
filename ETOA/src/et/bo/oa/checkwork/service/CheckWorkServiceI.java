package et.bo.oa.checkwork.service;

import java.util.List;

import org.apache.struts.util.LabelValueBean;

import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;

/**
 * 
 * ���ڹ��� �ӿ�
 * 
 * 
 * @author zkhuali Inc :wjlovegirl 2006-05-09
 * 
 */
public interface CheckWorkServiceI {

	/**
	 * 
	 * ��ÿ��ڼ�¼
	 * 
	 * 
	 * @return
	 */
	public Object[] seletCheckList(IBaseDTO infoDTO,PageInfo pageInfo);
	
	/**
	 * <p> ��ò�ѯ��¼�� </p>
	 * @return����ѯ��¼��
	 */
	public int getCheckListSize();
	/**
	 * @describe ȡ�������б�LabelValueBean
	 * @param
	 * @return List<LabelValueBean>
	 * 
	 */
	public  List<LabelValueBean> getNameList();
	/**
	 * 
	 * ��óٵ�,���˿��ڼ�¼
	 * 
	 * 
	 * @return
	 */
	public List seletLaterOrEarlyCheckList(IBaseDTO infoDTO);
	/**
	 *   
		 * @describe �����ѯ
		 * @param  IBaseDTO infoDBaseDTO ����  
		 * @return List����  
		 *
	 */
	public List seletWaichuList(IBaseDTO infoDTO);
	/***
	 * 
		 * @describe ��ٲ�ѯ
		 * @param  IBaseDTO infoDTO  
		 * @return List ����  
		 *
	 */
	public List selecQingjiaList(IBaseDTO infoDTO);
	/**
	 * 
		 * @describe ����
		 * @param  IBaseDTO infoDTO  
		 * @return List ����  
		 *
	 */
	public List selectChuchaiList(IBaseDTO infoDTO);
    
}
