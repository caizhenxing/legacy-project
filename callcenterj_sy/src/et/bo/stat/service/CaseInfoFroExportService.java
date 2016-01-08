/* 包    名：et.bo.stat.service
 * 文 件 名：CaseInfoFroExportService.java
 * 注释时间：2008-8-28 11:30:24
 * 版权所有：沈阳市卓越科技有限公司。
 */

package et.bo.stat.service;

import java.util.List;

import org.jfree.chart.JFreeChart;

import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;


/**
 * The Interface CaseInfoFroExportService.
 * 普通案例库专家受理量统计
 * @author Wang Lichun
 */
public interface CaseInfoFroExportService {
	
	/**
	 * 根据dto对象的参数值，执行统计，并返回根据统计结果生成的JFreeChart对象.
	 * 
	 * @param dto the IBaseDTO
	 * 
	 * @return the JFreeChart
	 */
	public JFreeChart statistic(IBaseDTO dto);
	
	/**
	 * 根据DTO对象的参数值，执行统计，并将统计结果进行处理，生成符合报表格式的列表.
	 * 
	 * @param dto the IBaseDTO
	 * 
	 * @return the list<DynaBeanDTO>
	 */
	public List<DynaBeanDTO> query(IBaseDTO dto);
}
