/*
 * @(#)CallbackServiceImpl.java	 2008-04-01
 *
 * 版权所有 沈阳市卓越科技有限公司。
 */
package et.bo.incommingInfo.service;

import java.util.List;

import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;
/**
 * <p>来电信息dao层</p>
 * 
 * @version 2008-06-13
 * @author wangwenquan
 */
public interface IncommingInfoService {
	/**
	 * @describe addIncommingInfo
	 * @param
	 * @return void
	 */ 
	public void addIncommingInfo(IBaseDTO dto);
	/**
	 * @describe updateIncommingInfoInfo
	 * @param
	 * @return void
	 */ 
	public boolean updateIncommingInfoInfo(IBaseDTO dto);
	/**
	 * @describe 删除来电信息
	 * @param
	 * @return void
	 */ 
	public void delIncommingInfo(String id);
	
	
	/**
	 * @describe 查询来电信息列表
	 * @param
	 * @return List
	 */ 
	public List incommingInfoList(IBaseDTO dto, PageInfo pi);
	
	
	/**
	 * @describe 取得查询条数
	 * @param
	 * @return int
	 */ 
	
	public int getIncommingInfoSize();
	
	
	/**
	 * @describe 根据Id取得信息
	 * @param
	 * @return dto
	 */ 
	public IBaseDTO getIncommingInfoDetail(String id);
	
	public List getExpertList();
	
	/**
	 * 获取screen的专题调查数据
	 */
	public List screenList();
}
