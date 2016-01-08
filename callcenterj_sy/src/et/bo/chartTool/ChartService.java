package et.bo.chartTool;

import org.jfree.chart.JFreeChart;

import et.bo.chartDate.ChartInfo;

public interface ChartService {
	
	public JFreeChart getBarChart(ChartInfo ci);
	
	public JFreeChart getPieChart(ChartInfo ci);

}
