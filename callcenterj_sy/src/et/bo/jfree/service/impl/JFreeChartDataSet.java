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
 * �������ݼ�
 */
public class JFreeChartDataSet {
	
	// ������״ͼ����״ͼ���������ݼ�
	private List<String> xChartValues;

	// ������״ͼ����״ͼ���������ݼ�
	private List<String> yChartValues;

	// ���ɵ���״ͼ����״ͼ��ʾ������Ϣ
	private List<String> ChartValues;
	
//	��״ͼ����״ͼ���ݼ�����
	private CategoryDataset chartDataset;
	
//	 ���ɵı�״ͼ��ʾ������Ϣ
	private List<String> pieChartTextValues;
	
	//���ɴ�����ɫ�ĸ�������б���Ϣ
	private List<Map<String,String>> pieChartTextColorValues;

	// ���ɵı�״ͼ��ʾ��ʵ��ֵ����Ϣ
	private List pieChartValues;
	
	//��״���ݼ�����
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
