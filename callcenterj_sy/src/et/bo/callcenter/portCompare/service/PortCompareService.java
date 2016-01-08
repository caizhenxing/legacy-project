/**
 * @(#)PortCompareService.java 1.0 //
 * 
 * 版权所有 沈阳市卓越科技有限公司。 卓越科技 保留一切权利
 * 
 */
package et.bo.callcenter.portCompare.service;

import java.util.HashMap;
import java.util.List;

import excellence.framework.base.dto.IBaseDTO;

/**
 * @describe 端口对照接口
 * @author 叶浦亮
 * @version 1.0, 2006-10-13//
 * @see
 */
public interface PortCompareService {
	/**
	 * 得到ip根据端口
	 * 
	 * @param port
	 * @return
	 */
	public String getIpByPort(String port);

	/**
	 * @describe 取得端口与Ip的对应Map
	 * @param
	 * @return Map
	 */
	public HashMap getIpByPort();

	/**
	 * @describe 刷新取得端口与Ip的对应Map
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
	 * @return boolean true为有此端口
	 */
	public boolean isHaveSamePort(String port);

	/**
	 * @describe 判断是否有相同的Ip
	 * @param
	 * @return boolean true为有此Ip
	 */
	public boolean isHaveSameIp(String ip);
	/**
	 * 通过ip得端口号
	 * @param ip
	 * @return port 端口
	 */
	public String getPortByIp(String ip);
	/**
	 * 得到内线端口的HashMap
	 * @return HashMap
	 */
	public HashMap getInnerPortMap();

}