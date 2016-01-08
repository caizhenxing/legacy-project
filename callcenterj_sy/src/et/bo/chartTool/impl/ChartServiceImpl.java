/**
 * 	@(#)ChartServiceImpl.java   2007-5-17 下午09:52:57
 *	版权所有 沈阳市卓越科技有限公司。 
 *	卓越科技 保留一切权利
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
 * @author 小白
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
				ci.getChartTitle(),  //图表标题
				ci.getChartX(),      //X轴坐标标题 
				ci.getChartY(),      //Y轴坐标标题
				dataset,             //绘制数据   
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
