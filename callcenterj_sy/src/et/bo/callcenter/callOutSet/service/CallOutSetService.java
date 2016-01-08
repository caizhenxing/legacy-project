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
	public List orderMenuSearch(String telNum, PageInfo pi);
	
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
	
	public List getUserList(String userType);
	
	/**
	 * 添加由文本外呼
	 * @param dto
	 * @return
	 */
	public boolean addGroupCallBack(IBaseDTO dto);
}
