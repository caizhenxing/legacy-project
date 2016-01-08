/**
 * 	@(#)StationService.java   Sep 2, 2006 2:37:38 PM
 *	 。 
 *	 
 */
package et.bo.sys.station.service;

import java.util.List;

import excellence.common.page.PageInfo;
import excellence.common.tree.TreeControlI;
import excellence.framework.base.dto.IBaseDTO;

/**
 * 岗位信息维护
 * @author zhang
 * @version Sep 2, 2006
 * @see
 */
public interface StationService {
	
	/**
	 * 岗位信息添加
	 * @param dto 类型 IBaseDTO 岗位信息
	 * @version Aug 30, 2006
	 * @return
	 */
	
	public boolean addStationBox(IBaseDTO dto);
	
	/**
	 * 岗位信息修改
	 * @param dto 类型 IBaseDTO 岗位信息
	 * @version Aug 30, 2006
	 * @return
	 */
	
	public boolean updateStationBox(IBaseDTO dto);
	
	/**
	 * 岗位信息删除
	 * @param dto 类型 IBaseDTO 岗位信息
	 * @version Aug 30, 2006
	 * @return
	 */
	
	public boolean delStationBox(IBaseDTO dto);
	
	/**
	 * 查询岗位信息
	 * @param dto 类型 IBaseDTO 岗位信息
	 * @param pageInfo 类型 PageInfo 分页信息
	 * @param mailboxType 类型 String 邮箱类型
	 * @return 类型 List 返回邮件列表信息
	 */
	public List StationIndex(IBaseDTO dto,PageInfo pageInfo);
	public int getStationSize();
	
	/**
	 * 查询指定岗位详细信息
	 * @param id 类型 String 岗位编号
	 * @version Sep 4, 2006
	 * @return
	 */
	public IBaseDTO getStationInfo(String id);
	
	/**
	 * 查询岗位类别详细信息
	 * @param station_class 类型 String 岗位类别
	 * @return 类型 List 返回邮件列表信息
	 */
	public List StationList(String station_class);
	
	/**
	 * 加载部门树
	 * @param
	 * @version Sep 4, 2006
	 * @return
	 */
	public TreeControlI loadDepartments();
}
