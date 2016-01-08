/**
 * ����׿Խ�Ƽ����޹�˾
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
	 * ������ͨ��״ͼ
	 * 
	 * @return JFreeChart
	 */
	JFreeChart createCommonPieChart(JFreeChartObject jfco);
	
	/**
	 * ����3D��״ͼ
	 * 
	 * @return JFreeChart
	 */
	JFreeChart create3DPieChart(JFreeChartObject jfco);
	
	/**
	 * ������ͨ��״ͼ
	 * 
	 * @return JFreeChart
	 */
	JFreeChart createCommonBarChart(JFreeChartObject jfco);
	
	/**
	 * ����3D��״ͼ
	 * 
	 * @return JFreeChart
	 */
	JFreeChart create3DBarChart(JFreeChartObject jfco);
	
	/**
	 * ������ͨ��״ͼ
	 * 
	 * @return JFreeChart
	 */
	JFreeChart createCommonLineChart(JFreeChartObject jfco);
	
	/**
	 * ����3D��״ͼ
	 * 
	 * @return JFreeChart
	 */
	JFreeChart create3DLineChart(JFreeChartObject jfco);
	/**
	 * add by ���Ʒ�
	 * �õ�JFreeChart����
	 * ����properties�еĲ����趨JFreeChart������������
	 * @param values JFreeChart������Ҫ�����ݼ���
	 * @param properties ����JFreeChart����ĸ������Լ���
	 * @return
	 */
	JFreeChart createJFreeChart(List<String> values,Map<String,Object> properties);
}
