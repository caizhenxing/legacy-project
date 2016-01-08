/**
 * 
 */
package et.bo.sys.bak.service;

import java.util.List;

import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;

/**
 * @author zhangfeng
 *
 */
public interface BakService {
	
	/**
	 * 用于进行各种数据库的备份
	 *
	 */
	public void backup();
	
	/**
	 * 设置必须字段属性
	 *
	 */
	public void backupSet();
	
	/**
	 * 立即备份
	 *
	 */
	public void startBakupImmediate();
	
	/**
	 * @describe 添加新数据库的备份设置
	 * @param
	 * @return void
	 */ 
	public void addBak(IBaseDTO dto);
	/**
	 * @describe 修改数据库的备份设置
	 * @param
	 * @return void
	 */ 
	public boolean updateBak(IBaseDTO dto);
	/**
	 * @describe 删除数据库的备份设置
	 * @param
	 * @return void
	 */ 
	public void delBak(String id);
	
	
	/**
	 * @describe 查询数据库的备份设置列表
	 * @param
	 * @return List
	 */ 
	public List bakQuery(IBaseDTO dto, PageInfo pi);
	
	
	/**
	 * @describe 取得查询条数
	 * @param
	 * @return int
	 */ 
	
	public int getBakSize();
	
	
	/**
	 * @describe 根据Id取得信息
	 * @param
	 * @return dto(user类型)
	 */ 
	public IBaseDTO getBakInfo(String id);
	

}
