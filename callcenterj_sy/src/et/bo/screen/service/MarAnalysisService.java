package et.bo.screen.service;

import java.util.List;

import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

public interface MarAnalysisService {
	
	/**
	 * 根据id取得一条金农市场分析信息详细
	 * @param id
	 * @return
	 */
	public IBaseDTO getMarAnalysisInfo(String id);
	
	/**
	 * 根据查询条件返回金农市场分析信息列表
	 * @param dto
	 * @param pi
	 * @return
	 */
	public List marketAnalysisInfoQuery(IBaseDTO dto, PageInfo pi);
	/**
	 * 根据查询条件返回金农市场分析信息列表 按时间倒序 显示头几条
	 * @param dto
	 * @param pi
	 * @return
	 */
	public List marketAnalysisInfoQuery(int size);
	public List marketAnalysisInfoQuery2();
	
	public int getMarAnalysisInfoSize();
	
	/**
	 * 添加金农市场分析信息
	 * @param dto
	 * @return
	 */
	public boolean addMarketAnalysis(IBaseDTO dto);
	
	/**
	 * 修改金农市场分析信息
	 * @param dto
	 * @return
	 */
	public boolean updateMarketAnalysis(IBaseDTO dto);
	
	/**
	 * 删除金农市场分析信息
	 * @param dto
	 * @return
	 */
	public boolean deleteMarketAnalysis(IBaseDTO dto);
	
	/**
	 * 根据查询条件返回金农市场分析信息列表
	 * @param dto
	 * @param pi
	 * @return
	 */
	public List marketAnalysisInfoQuery2(IBaseDTO dto, PageInfo pi);
	
	/**
	 * 根据id取得一条金农市场分析信息详细
	 * @param id
	 * @return
	 */
	public IBaseDTO getMarAnalysisInfo2(String id);
	
	/**
	 * 添加金农市场分析信息
	 * @param dto
	 * @return
	 */
	public boolean addMarketAnalysis2(IBaseDTO dto);
	
	/**
	 * 修改金农市场分析信息
	 * @param dto
	 * @return
	 */
	public boolean updateMarketAnalysis2(IBaseDTO dto);
	
	/**
	 * 删除金农市场分析信息
	 * @param dto
	 * @return
	 */
	public boolean deleteMarketAnalysis2(IBaseDTO dto);
}
