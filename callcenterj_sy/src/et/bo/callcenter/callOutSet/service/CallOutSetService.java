package et.bo.callcenter.callOutSet.service;

import java.util.List;

import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;

/**
 * 
 * @author chen gang
 *
 */
public interface CallOutSetService {
	/**
	 * ���ҵ������Ϣ��¼
	 * @param dto
	 * @return
	 */
	public boolean addOrderMenu(IBaseDTO dto);
	
	/**
	 * ���Ҷ��ƺ�δ�˶��ļ�¼�б�
	 * @param telNum
	 * @return
	 */
	public List orderMenuSearch(String telNum, PageInfo pi);
	
	/**
	 * ���ؼ�¼�б�����
	 * @return
	 */
	public int getOrderMenuSize();
	
	/**
	 * �˶�����
	 * @param id
	 * @return
	 */
	public boolean delOrderMenu(String id);
	
	public List getUserList(String userType);
	
	/**
	 * ������ı����
	 * @param dto
	 * @return
	 */
	public boolean addGroupCallBack(IBaseDTO dto);
}
