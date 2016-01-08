/**
 * 	@(#)SearchImpl.java   Oct 30, 2006 5:54:17 PM
 *	 �� 
 *	 
 */
package et.bo.pcc.phonesearch.service;

import java.util.List;

import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;

/**
 * @author zhang
 * @version Oct 30, 2006
 * @see
 */
public interface SearchService {
	
	 /**
	 * @describe ��Ӳ�ѯ��Ϣ����
	 * @param
	 * @return void
	 */
	public void addSearchInfo(IBaseDTO dto);
	
	/**
	 * �޸���Ϣ
	 * @param
	 * @version Oct 30, 2006
	 * @return
	 */
	public void upSearchInfo(IBaseDTO dto);
	
	/**
	 * ɾ����Ϣ
	 * @param
	 * @version Oct 30, 2006
	 * @return
	 */
	public void delSearchInfo(IBaseDTO dto);
	
	/**
	 * @describe ��ѯ����������־
	 * @param
	 * @return List  Cclog����
	 */
	public List phoneSearch(IBaseDTO dto, PageInfo pi);
    /**
	 * @describe �õ���־����
	 * @param
	 * @return int
	 * 
	 */    
    public int getPhoneSearchSize();
    /**
	 * @describe �õ���־��ϸ��Ϣ
	 * @param
	 * @return IBaseDTO ��־dto
	 */
    public IBaseDTO getPhoneSearch(String id);
    
}
