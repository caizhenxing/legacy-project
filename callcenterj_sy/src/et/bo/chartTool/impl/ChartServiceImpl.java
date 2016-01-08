/**
 * 	@(#)ChartServiceImpl.java   2007-5-17 ����09:52:57
 *	��Ȩ���� ������׿Խ�Ƽ����޹�˾�� 
 *	׿Խ�Ƽ� ����һ��Ȩ��
 */
package et.bo.chartTool.impl;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import et.bo.chartDate.ChartInfo;
import et.bo.chartTool.ChartService;

/**
 * @describe
 * @author С��
 * @version 2007-5-17
 * @see
 */
public class ChartServiceImpl implements ChartService {

	/* (non-Javadoc)
	 * @see et.bo.chartTool.ChartService#getBarChart()
	 */
	public JFreeChart getBarChart(ChartInfo ci) {
		// TODO Auto-generated method stub
		DefaultCategoryDataset dataset=new DefaultCategoryDataset();
		for(int i=0;i<ci.getItemTitle().length;i++){
			for(int j=0;j<ci.getCategory().length;j++){
				dataset.setValue((Number)ci.getDateSet()[i][j], ci.getItemTitle()[i], ci.getCategory()[j]);
			}
		}
		JFreeChart chart = ChartFactory.createBarChart(
				ci.getChartTitle(),  //ͼ�����
				ci.getChartX(),      //X��������� 
				ci.getChartY(),      //Y���������
				dataset,             //��������   
				PlotOrientation.VERTICAL,
				true, 
				true, 
				false
			);
		return chart;
	}

	/* (non-Javadoc)
	 * @see et.bo.chartTool.ChartService#getPieChart()
	 */
	public JFreeChart getPieChart(ChartInfo ci) {
		// TODO Auto-generated method stub
		return null;
	}

}
