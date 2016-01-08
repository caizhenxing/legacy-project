/**
 * 
 */
package et.bo.jfree.service.impl;

import java.util.List;
import java.util.Map;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.general.PieDataset;
import org.jfree.data.category.CategoryDataset;

import et.bo.jfree.service.JFreeService;

/**
 * @author zhangfeng 返回jsp页面需要的JFreeChart对象
 */
public class JFreeImpl implements JFreeService {
	// add by 梁云锋
	public final static String BAR = "bar";

	public final static String BAR3D = "bar3d";

	public final static String LINE = "line";

	public final static String LINE3D = "line3d";

	public final static String PIE = "pie";

	public final static String PIE3D = "pie3d";

	/**
	 * 得到饼状图数据集对象
	 * 
	 * @param jfco
	 * @return
	 * 
	 */
	private PieDataset getPieDataSet(JFreeChartObject jfco) {
		PieDataset pieDataset = JFreePieHelp.createDataset(jfco.getJfcd()
				.getPieChartTextValues(), jfco.getJfcd().getPieChartValues());
		jfco.getJfcd().setPieDataset(pieDataset);
		return pieDataset;
	}

	public JFreeChart create3DPieChart(JFreeChartObject jfco) {
		// TODO Auto-generated method stub
		JFreeChart jfreechart = ChartFactory.createPieChart3D(jfco
				.getChartTitle(), getPieDataSet(jfco), true, true, false);
		jfreechart = JFreePieHelp.setPieShowAttribute(jfreechart, jfco);
		jfreechart = JFreePieHelp.set3DPieShowAttribute(jfreechart, jfco);
		return jfreechart;
	}

	public JFreeChart createCommonPieChart(JFreeChartObject jfco) {
		// TODO Auto-generated method stub
		JFreeChart jfreechart = ChartFactory.createPieChart(jfco
				.getChartTitle(), getPieDataSet(jfco), true, true, false);
		jfreechart = JFreePieHelp.setPieShowAttribute(jfreechart, jfco);
		jfreechart = JFreePieHelp.setCommonPieShowAttribute(jfreechart, jfco);
		return jfreechart;
	}

	/**
	 * 得到柱状图数据集对象
	 * 
	 * @param jfco
	 * @return
	 */
	private CategoryDataset getBarDataSet(JFreeChartObject jfco) {
		CategoryDataset barDataset = JFreeBarHelp.createDataset(jfco.getJfcd()
				.getXChartValues(), jfco.getJfcd().getYChartValues(), jfco
				.getJfcd().getChartValues());
		jfco.getJfcd().setChartDataset(barDataset);
		return barDataset;
	}

	public JFreeChart create3DBarChart(JFreeChartObject jfco) {
		// TODO Auto-generated method stub
		JFreeChart jfreechart = ChartFactory.createBarChart3D(jfco
				.getChartTitle(), jfco.getXChartName(), jfco.getYChartName(),
				getBarDataSet(jfco), PlotOrientation.VERTICAL, true, true,
				false);
		jfreechart = JFreeBarHelp.setBarShowAttribute(jfreechart, jfco);
		jfreechart = JFreeBarHelp.set3DBarShowAttribute(jfreechart, jfco);
		return jfreechart;
	}

	public JFreeChart createCommonBarChart(JFreeChartObject jfco) {
		// TODO Auto-generated method stub
		JFreeChart jfreechart = ChartFactory.createBarChart(jfco
				.getChartTitle(), jfco.getXChartName(), jfco.getYChartName(),
				getBarDataSet(jfco), PlotOrientation.VERTICAL, true, true,
				false);
		jfreechart = JFreeBarHelp.setBarShowAttribute(jfreechart, jfco);
		jfreechart = JFreeBarHelp.setCommonBarShowAttribute(jfreechart, jfco);
		return jfreechart;
	}

	/**
	 * 得到线状图数据集对象
	 * 
	 * @param jfco
	 * @return
	 */
	private CategoryDataset getLineDataSet(JFreeChartObject jfco) {
		// System.out.println("come");
		// for (int i = 0; i < ((List)jfco.getJfcd().getChartValues()).size();
		// i++) {
		// System.out.println("%%############%%%%"+((List)jfco.getJfcd().getChartValues()).get(i));
		// }
		CategoryDataset lineDataset = JFreeLineHelp.createDataset(jfco
				.getJfcd().getXChartValues(), jfco.getJfcd().getYChartValues(),
				jfco.getJfcd().getChartValues());
		jfco.getJfcd().setChartDataset(lineDataset);
		return lineDataset;
	}

	public JFreeChart create3DLineChart(JFreeChartObject jfco) {
		// TODO Auto-generated method stub
		JFreeChart jfreechart = ChartFactory.createLineChart3D(jfco
				.getChartTitle(), jfco.getXChartName(), jfco.getYChartName(),
				getLineDataSet(jfco), PlotOrientation.VERTICAL, true, true,
				false);
		jfreechart = JFreeLineHelp.setLineShowAttribute(jfreechart, jfco);
		jfreechart = JFreeLineHelp.set3DLineShowAttribute(jfreechart, jfco);
		return jfreechart;
	}

	public JFreeChart createCommonLineChart(JFreeChartObject jfco) {
		// TODO Auto-generated method stub
		JFreeChart jfreechart = ChartFactory.createLineChart(jfco
				.getChartTitle(), jfco.getXChartName(), jfco.getYChartName(),
				getLineDataSet(jfco), PlotOrientation.VERTICAL, true, true,
				false);
		jfreechart = JFreeLineHelp.setLineShowAttribute(jfreechart, jfco);
		jfreechart = JFreeLineHelp.setCommonLineShowAttribute(jfreechart, jfco);
		return jfreechart;
	}

	public JFreeChart createJFreeChart(List<String> values,
			Map<String, Object> properties) {
		// TODO Auto-generated method stub
		String chartTitle = (String) properties.get("chartTitle");
		String chartType = (String) properties.get("chartType");
		JFreeChartObject jfco = new JFreeChartObject();
		jfco.setChartTitle(chartTitle);
		JFreeChart chart = null;
		if (BAR.equals(chartType)) {
			jfco.setXChartName(properties.get("xChartName").toString());
			jfco.setYChartName(properties.get("yChartName").toString());
			jfco.getJfcd().setXChartValues(
					(List) properties.get("xChartValues"));
			jfco.getJfcd().setYChartValues(
					(List) properties.get("yChartValues"));
			jfco.getJfcd().setChartValues((List) properties.get("chartValues"));
			chart = createCommonBarChart(jfco);
		} else if (BAR3D.equals(chartType)) {
			jfco.setXChartName(properties.get("xChartName").toString());
			jfco.setYChartName(properties.get("yChartName").toString());
			jfco.getJfcd().setXChartValues(
					(List) properties.get("xChartValues"));
			jfco.getJfcd().setYChartValues(
					(List) properties.get("yChartValues"));
			jfco.getJfcd().setChartValues((List) properties.get("chartValues"));
			chart = create3DBarChart(jfco);
		} else if (LINE.equals(chartType)) {
			jfco.setXChartName(properties.get("xChartName").toString());
			jfco.setYChartName(properties.get("yChartName").toString());
			jfco.getJfcd().setXChartValues(
					(List) properties.get("xChartValues"));
			jfco.getJfcd().setYChartValues(
					(List) properties.get("yChartValues"));
			jfco.getJfcd().setChartValues((List) properties.get("chartValues"));
			chart = createCommonLineChart(jfco);
		} else if (LINE3D.equals(chartType)) {
			jfco.setXChartName(properties.get("xChartName").toString());
			jfco.setYChartName(properties.get("yChartName").toString());
			jfco.getJfcd().setXChartValues(
					(List) properties.get("xChartValues"));
			jfco.getJfcd().setYChartValues(
					(List) properties.get("yChartValues"));
			jfco.getJfcd().setChartValues((List) properties.get("chartValues"));
			chart = create3DLineChart(jfco);
		} else if (PIE.equals(chartType)) {
			jfco.getJfcd().setPieChartTextValues(
					(List) properties.get("pieTextValues"));
			jfco.getJfcd().setPieChartValues(values);
			chart = createCommonPieChart(jfco);
		} else if (PIE3D.equals(chartType)) {
			jfco.getJfcd().setPieChartTextValues(
					(List) properties.get("pieTextValues"));
			jfco.getJfcd().setPieChartValues(values);
			chart = create3DPieChart(jfco);
		}
		return chart;
	}
}
