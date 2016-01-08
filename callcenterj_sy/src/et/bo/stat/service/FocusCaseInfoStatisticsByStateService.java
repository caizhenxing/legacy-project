/**
 * 	@(#) TelService.java 2008-4-11 下午01:09:36
 *	版权所有 沈阳市卓越科技有限公司。 
 *	卓越科技 保留一切权利
 */
package et.bo.stat.service;

import java.util.List;

import org.jfree.chart.JFreeChart;

import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

/**
 * <p>焦点案例库下显示全部座席员和每一座席员受理的各审核状态下的案例数量 显示 受理工号 审核时间action</p>
 * 
 * @version 2008-03-29
 * @author wangwenquan
 */
public interface FocusCaseInfoStatisticsByStateService {
	/**
	 * 获得座席员列表
	 * @param sql
	 * @return List
	 */	
	public List userQuery(String sql);
	/**
	 * 根据dto对象的参数值，执行统计，并返回根据统计结果生成的JFreeChart对象
	 * 显示全部座席员和每一座席员受理的各审核状态下的案例数量 显示 受理工号 审核时间
	 * @param dto
	 * @return
	 */
	public JFreeChart statistic(IBaseDTO dto);
	/**
	 * 根据DTO对象的参数值，执行统计，并将统计结果进行处理，生成符合报表格式的列表
	 * 显示全部座席员和每一座席员受理的各审核状态下的案例数量 显示 受理工号 审核时间
	 * @param dto
	 * @return List
	 */
	public List<DynaBeanDTO> query(IBaseDTO dto);
}
