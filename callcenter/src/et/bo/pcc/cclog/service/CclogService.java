/**
 * @(#)CclogService.java 1.0 //
 * 
 *  ��  
 * 
 */
package et.bo.pcc.cclog.service;

import java.util.List;

import et.bo.callcenter.base.ConnectInfo;
import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;

/**
 * @describe ����������־�ӿ�
 * @author  Ҷ����
 * @version 1.0, 2006-10-08//
 * @see
 */
public interface CclogService {
    /**
	 * @describe ��Ӻ���������־
	 * @param
	 * @return void
	 */
	public void addCclog(ConnectInfo ci);
	/**
	 * @describe ��ѯ����������־
	 * @param
	 * @return List  Cclog����
	 */
	public List queryCclog(IBaseDTO dto, PageInfo pi);
    /**
	 * @describe �õ���־����
	 * @param
	 * @return int
	 * 
	 */    
    public int getCclogSize();
    /**
	 * @describe �õ���־��ϸ��Ϣ
	 * @param
	 * @return IBaseDTO ��־dto
	 */
    public IBaseDTO getCclogInfo(String id);
    
    public List listPhoneInfo(String id);
    IBaseDTO listFuzzInfo(String id);
    
    /**
     * �õ���־��Ϣ���б�
     * @param
     * @version Oct 30, 2006
     * @return
     */
    public List queryCcLogInfo(IBaseDTO dto, PageInfo pi,String begintime,String opernum);
    public int getCcLogInfoSize();
    
    public List getDepInfo(IBaseDTO dto);
}
