package et.bo.callcenter.orderMenu.service;

import java.util.List;

import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;

/**
 * 
 * @author chen gang
 *
 */
public interface OrderMenuService {
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
	public List orderMenuSearch(IBaseDTO dto, PageInfo pi);
	
	/**
	 * �����û��㲥�����Ƶ�������ʷ��¼�б�
	 * @param telNum
	 * @return
	 */
//	public List businessMenuSearch(String telNum, PageInfo pi);
	
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
}
