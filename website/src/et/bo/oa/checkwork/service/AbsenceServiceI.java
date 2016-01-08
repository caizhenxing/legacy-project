/**
 * 	@(#)BookBorrowService.java   Sep 1, 2006 11:04:29 AM
 *	 。 
 *	 
 */
package et.bo.oa.checkwork.service;

import java.util.List;

import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;


/**
 * <p> 缺勤管理 接口 </p>
 * 
 * @author zkhuali Inc :wjlovegirl 2006-05-10
 * 
 */
public interface AbsenceServiceI {

	/**
	 * <p> 添加缺勤记录 </p>
	 * 
	 * @param infoDTO
	 */
	public void addAbsence(IBaseDTO infoDTO);

	/**
	 * <p> 添加补签记录 </p>
	 * 
	 * @param infoDTO
	 */
	public void addResign(IBaseDTO infoDTO);

	/**
	 * <p> 获得缺勤记录 </p>
	 * 
	 * @param infoDTO
	 * @return
	 */
	public Object[] selectAbsenseList(IBaseDTO infoDTO,PageInfo pi);

	/**
	 * <p> 获得用户列表 </p>
	 * @param page: 调用页参数
	 * @return：用户信息（id，name）
	 */
	public Object[] getUserList(String page,IBaseDTO infoDTO);
	
	/**
	 * <p> 获得记录数 </p>
	 * @return
	 */
	public int getAbsenceSize();
	
	/**
	 * <p> 获得小时数 </p>
	 * @return
	 */
	public List gethour();
	/**
	 * @describe 获得外出状态列表
	 * @param
	 * @return
	 * 
	 */
	public List getOutStateList();
	/**
	 * @describe 获得所有人外出状态列表
	 * @param
	 * @return
	 * 
	 */
	public List getAllOutStateList();
}
