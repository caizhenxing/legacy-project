/* 包    名：et.bo.stat.service
 * 文 件 名：FocusCaseInfoUserService.java
 * 注释时间：2008-8-28 13:52:14
 * 版权所有：沈阳市卓越科技有限公司。
 */

package et.bo.stat.service;

import java.util.List;

import org.jfree.chart.JFreeChart;

import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;


/**
 * The Interface FocusCaseInfoUserService.
 * 焦点案例库统计之坐席受理量统计
 * @author Wang Lichun
 */
public interface FocusCaseInfoUserService {
	
	/**
	 * 根据dto对象的参数值，执行统计，并返回根据统计结果生成的JFreeChart对象.
	 * 
	 * @param dto the dto
	 * 
	 * @return the j free chart
	 */
	public JFreeChart statistic(IBaseDTO dto);
	
	/**
	 * 根据DTO对象的参数值，执行统计，并将统计结果进行处理，生成符合报表格式的列表.
	 * 
	 * @param dto the dto
	 * 
	 * @return the list< dyna bean dt o>
	 */
	public List<DynaBeanDTO> query(IBaseDTO dto);
}
