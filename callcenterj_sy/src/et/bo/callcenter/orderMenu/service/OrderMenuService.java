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
	 * 添加业务定制信息记录
	 * @param dto
	 * @return
	 */
	public boolean addOrderMenu(IBaseDTO dto);
	
	/**
	 * 查找定制后还未退订的记录列表
	 * @param telNum
	 * @return
	 */
	public List orderMenuSearch(IBaseDTO dto, PageInfo pi);
	
	/**
	 * 查找用户点播、定制的所有历史记录列表
	 * @param telNum
	 * @return
	 */
//	public List businessMenuSearch(String telNum, PageInfo pi);
	
	/**
	 * 返回记录列表数量
	 * @return
	 */
	public int getOrderMenuSize();
	
	/**
	 * 退订操作
	 * @param id
	 * @return
	 */
	public boolean delOrderMenu(String id);
}
