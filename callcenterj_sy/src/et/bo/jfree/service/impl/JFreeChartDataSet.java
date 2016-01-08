/**
 * 
 */
package et.bo.jfree.service.impl;

import java.util.List;
import java.util.Map;

import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.PieDataset;

/**
 * @author zhangfeng
 * 设置数据集
 */
public class JFreeChartDataSet {
	
	// 生成柱状图及线状图横座标数据集
	private List<String> xChartValues;

	// 生成柱状图及线状图纵座标数据集
	private List<String> yChartValues;

	// 生成的柱状图及线状图显示内容信息
	private List<String> ChartValues;
	
//	柱状图及线状图数据集对象
	private CategoryDataset chartDataset;
	
//	 生成的饼状图显示内容信息
	private List<String> pieChartTextValues;
	
	//生成带有颜色的各个块的列表信息
	private List<Map<String,String>> pieChartTextColorValues;

	// 生成的饼状图显示的实际值的信息
	private List pieChartValues;
	
	//饼状数据集对象
	private PieDataset pieDataset;
	
	public PieDataset getPieDataset() {
		return pieDataset;
	}
	public void setPieDataset(PieDataset pieDataset) {
		this.pieDataset = pieDataset;
	}

	public List<String> getXChartValues() {
		return xChartValues;
	}
	public void setXChartValues(List<String> chartValues) {
		xChartValues = chartValues;
	}
	public List<String> getYChartValues() {
		return yChartValues;
	}
	public void setYChartValues(List<String> chartValues) {
		yChartValues = chartValues;
	}
	public List<Map<String, String>> getPieChartTextColorValues() {
		return pieChartTextColorValues;
	}
	public void setPieChartTextColorValues(
			List<Map<String, String>> pieChartTextColorValues) {
		this.pieChartTextColorValues = pieChartTextColorValues;
	}
	public List<String> getPieChartTextValues() {
		return pieChartTextValues;
	}
	public void setPieChartTextValues(List<String> pieChartTextValues) {
		this.pieChartTextValues = pieChartTextValues;
	}
	public List getPieChartValues() {
		return pieChartValues;
	}
	public void setPieChartValues(List pieChartValues) {
		this.pieChartValues = pieChartValues;
	}
	public CategoryDataset getChartDataset() {
		return chartDataset;
	}
	public void setChartDataset(CategoryDataset chartDataset) {
		this.chartDataset = chartDataset;
	}
	public List<String> getChartValues() {
		return ChartValues;
	}
	public void setChartValues(List<String> chartValues) {
		ChartValues = chartValues;
	}

}
