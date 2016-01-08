/**
 * 沈阳卓越科技有限公司
 */
package et.bo.jfree.service;

import java.util.List;
import java.util.Map;

import org.jfree.chart.JFreeChart;

import et.bo.jfree.service.impl.JFreeChartObject;

/**
 * @author zhangfeng
 *
 */
public interface JFreeService {
	
	/**
	 * 创建普通饼状图
	 * 
	 * @return JFreeChart
	 */
	JFreeChart createCommonPieChart(JFreeChartObject jfco);
	
	/**
	 * 闯将3D饼状图
	 * 
	 * @return JFreeChart
	 */
	JFreeChart create3DPieChart(JFreeChartObject jfco);
	
	/**
	 * 创建普通柱状图
	 * 
	 * @return JFreeChart
	 */
	JFreeChart createCommonBarChart(JFreeChartObject jfco);
	
	/**
	 * 闯将3D柱状图
	 * 
	 * @return JFreeChart
	 */
	JFreeChart create3DBarChart(JFreeChartObject jfco);
	
	/**
	 * 创建普通线状图
	 * 
	 * @return JFreeChart
	 */
	JFreeChart createCommonLineChart(JFreeChartObject jfco);
	
	/**
	 * 闯将3D线状图
	 * 
	 * @return JFreeChart
	 */
	JFreeChart create3DLineChart(JFreeChartObject jfco);
	/**
	 * add by 梁云锋
	 * 得到JFreeChart对象
	 * 根据properties中的参数设定JFreeChart对象的相关属性
	 * @param values JFreeChart对象需要的数据集合
	 * @param properties 设置JFreeChart对象的各种属性集合
	 * @return
	 */
	JFreeChart createJFreeChart(List<String> values,Map<String,Object> properties);
}
