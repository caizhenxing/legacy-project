package et.bo.oa.main.service;

import java.util.List;

import et.bo.sys.login.UserInfo;
import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;

/**
 * <p> ��ҳService </p>
 * 
 * @author zkhuali Inc :wjlovegirl 2006-05-15
 * 
 */
public interface MainSerivce {

	/**
	 * <p> �����б� </p>
	 * @return
	 */
	public List newList();
	
	/**
	 * <p> ���������б� </p>
	 * @return
	 */
	public List waitingWork();
	
	/**
	 * <p> �����б� </p>
	 * @return
	 */
	public List afficheList();
	
	/**
	 * <p> �����ƻ��б� </p>
	 * @return
	 */
	public List workList();
	
	/**
	 * <p> �ڲ��ʼ��б� </p>
	 * @return
	 */
	public List inemailList(String username);
	
	/**
	 * �ʼ���ϸ�б�
	 * @param
	 * @version Sep 15, 2006
	 * @return
	 */
	public List emaliListIndex(IBaseDTO dto,PageInfo pi);
	public int getEmailIndexSize();
	
	/**
	 * �ʼ���ϸ��Ϣ
	 */
	public List getEmailInfo(String id);
	

	
}
