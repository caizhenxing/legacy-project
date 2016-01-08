/**
 * @(#)PortCompareService.java 1.0 //
 * 
 *  。  
 * 
 */
package et.bo.pcc.portCompare.service;

import java.util.HashMap;
import java.util.List;

import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.query.MyQuery;

/**
 * @describe 端口对照接口
 * @author  叶浦亮
 * @version 1.0, 2006-10-13//
 * @see
 */
public interface PortCompareService {
	/**
	 * @describe 取得端口与Ip的对应Map
	 * @param
	 * @return Map
	 */    
	public HashMap getIpByPort();
	/**
	 * @describe  刷新取得端口与Ip的对应Map
	 * @param
	 * @return Map
	 */  
	public HashMap flushGetIpByPort();
	/**
	 * @describe 添加对照记录
	 * @param
	 * @return void
	 */ 
	public void addPortCompare(IBaseDTO dto);
	/**
	 * @describe 修改对照记录
	 * @param
	 * @return void
	 */ 
	public boolean updatePortCompare(IBaseDTO dto);
	/**
	 * @describe 删除对照记录
	 * @param
	 * @return void
	 */ 
	public void delPortCompare(String id);
	/**
	 * @describe 查询端口对照列表
	 * @param
	 * @return List
	 */ 
	public List portCompareQuery();
	/**
	 * @describe 取得查询条数
	 * @param
	 * @return int
	 */ 
	public int getPortCompareSize();
	/**
	 * @describe 根据Id取得信息
	 * @param
	 * @return dto(PortCompare类型)
	 */ 
	public IBaseDTO getPortCompareInfo(String id);
	/**
	 * @describe 判断是否有相同的Port
	 * @param
	 * @return boolean  true为有此端口
	 */ 
	public boolean isHaveSamePort(String port);
	/**
	 * @describe 判断是否有相同的Ip
	 * @param
	 * @return boolean  true为有此Ip
	 */ 
	public boolean isHaveSameIp(String ip);
}
