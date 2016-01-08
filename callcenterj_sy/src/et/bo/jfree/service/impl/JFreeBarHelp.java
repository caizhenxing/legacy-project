/**
 * 
 */
package et.bo.jfree.service.impl;

import java.awt.Color;
import java.util.List;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 * @author zhangfeng
 * 把JFreeChartDataSet.java里的属性传入这里来
 */
public class JFreeBarHelp {

//	 生成柱状图数据集对象
	public static CategoryDataset createDataset(List<String> xChartValues ,
			List<String> yChartValues,List<String> barChartValues) {
//		System.out.println("xChartValues "+xChartValues.size()+" yChartValues "+
//				yChartValues.size()+" barChartValues "+barChartValues.size());
		DefaultCategoryDataset defaultcategorydataset = new DefaultCategoryDataset();
		try {
			if(barChartValues.size() == (xChartValues.size()*yChartValues.size())){
				int k=0;
				for (int i = 0; i < xChartValues.size(); i++) {
					for (int j = 0; j < yChartValues.size(); j++) {
						defaultcategorydataset.addValue(Double.parseDouble(barChartValues.get(k).toString()), 
								yChartValues.get(j), xChartValues.get(i));
						k++;
					}
				}
			}else{
				System.out.println("/***********************************************/");
				System.out.println(" ** 生成柱状图数据集对象时，您传入的list数据集不匹配！** ");
				System.out.println("/***********************************************/");
			}
		} catch (Exception e) {
			System.out.println(" ** 生成柱状图数据集对象时，您传入的list数据集里的对象类型不是String类型！** ");
		}
		
		return defaultcategorydataset;
	}
	/**
	 * 设置柱状图的通用属性
	 * @param jfreechart
	 * @param jfco
	 * @return
	 */
	public static JFreeChart setBarShowAttribute(JFreeChart jfreechart,JFreeChartObject jfco) {
		//获得图表显示对象
		CategoryPlot categoryplot = jfreechart.getCategoryPlot();
		CategoryAxis categoryaxis = categoryplot.getDomainAxis();
//		横轴上的 Lable 45度倾斜
//		categoryaxis.setCategoryLabelPositions(CategoryLabelPositions
//				.createUpRotationLabelPositions(0.39269908169872414D));
		CategoryItemRenderer categoryitemrenderer = categoryplot.getRenderer();
		categoryitemrenderer.setItemLabelsVisible(true);
//		设置图表背景颜色
		if (!jfco.getChartBackGround().equals("")) {
			String backGroundValue = jfco.getChartBackGround();
			String[] str = backGroundValue.split(",");
			jfreechart.setBackgroundPaint(new Color(Integer.parseInt(str[0]),
					Integer.parseInt(str[1]), Integer.parseInt(str[2])));
		}   
		ValueAxis rangeAxis = categoryplot.getRangeAxis();
//		设置最高的一个 Item 与图片顶端的距离
		if(jfco.getUpperMarginBar()!=0.0)
			rangeAxis.setUpperMargin(jfco.getUpperMarginBar());
//		设置最低的一个 Item 与图片底端的距离
		if(jfco.getLowerMarginBar()!=0.0)
			rangeAxis.setLowerMargin(jfco.getLowerMarginBar());
		categoryplot.setRangeAxis(rangeAxis);
//		设置柱的透明度
		if(jfco.getForegroundAlpha()!=0.0)
			categoryplot.setForegroundAlpha(jfco.getForegroundAlpha());
//		设置横坐标的显示位置
		/**
		 * 横坐标在下面显示，值为0:AxisLocation.BOTTOM_OR_LEFT;
		 * 横坐标在上面显示，值为1:AxisLocation.TOP_OR_LEFT;
		 * */
		switch (jfco.getXChartLocation()) {
		case 0:
			categoryplot.setDomainAxisLocation(AxisLocation.BOTTOM_OR_LEFT);
			break;
		case 1:
			categoryplot.setDomainAxisLocation(AxisLocation.TOP_OR_LEFT);
			break;
		}
//		设置纵坐标的显示位置
		/**
		 * 纵坐标在左面显示，值为0:AxisLocation.BOTTOM_OR_LEFT;
		 * 纵坐标在右面显示，值为1:AxisLocation.BOTTOM_OR_RIGHT;
		 * */
		switch (jfco.getYChartLocation()) {
		case 0:
			categoryplot.setRangeAxisLocation(AxisLocation.BOTTOM_OR_LEFT);
			break;
		case 1:
			categoryplot.setRangeAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);
			break;
		}
		return jfreechart;
	}
	/**
	 * 设置3D柱状图的属性
	 * @param jfreechart
	 * @param jfco
	 * @return
	 */
	public static JFreeChart set3DBarShowAttribute(JFreeChart jfreechart,JFreeChartObject jfco) {
		//获得图表显示对象
		CategoryPlot categoryplot = jfreechart.getCategoryPlot();
		CategoryItemRenderer categoryitemrenderer = categoryplot.getRenderer();
		BarRenderer3D barrenderer = (BarRenderer3D) categoryitemrenderer;
//		设置每个柱子之间的距离
		if(jfco.getItemMarginBar()!=0.0D)
			barrenderer.setItemMargin(jfco.getItemMarginBar());
		
//		显示每个柱的数值，并修改该数值的字体属性
		if(jfco.isShowBarNumber()==true){
			barrenderer.setItemLabelGenerator(new StandardCategoryItemLabelGenerator());
			barrenderer.setItemLabelsVisible(true);
		}
//		barrenderer.setBaseOutlinePaint(Color.BLACK);
////		设置 Wall 的颜色
//		barrenderer.setWallPaint(Color.gray);
////		设置每种水果代表的柱的颜色
//		barrenderer.setSeriesPaint(0, new Color(0, 0, 255));
//		barrenderer.setSeriesPaint(1, new Color(0, 100, 255));
//		barrenderer.setSeriesPaint(2, Color.GREEN);

//		categoryplot.setRenderer(barrenderer);
		return jfreechart;
	}
	/**
	 * 设置平面柱状图的属性
	 * @param jfreechart
	 * @param jfco
	 * @return
	 */
	public static JFreeChart setCommonBarShowAttribute(JFreeChart jfreechart,JFreeChartObject jfco) {
		//获得图表显示对象
		CategoryPlot categoryplot = jfreechart.getCategoryPlot();
		CategoryItemRenderer categoryitemrenderer = categoryplot.getRenderer();
		BarRenderer barrenderer = (BarRenderer) categoryitemrenderer;
//		设置每个柱子之间的距离
		if(jfco.getItemMarginBar()!=0.0D)
			barrenderer.setItemMargin(jfco.getItemMarginBar());
//		显示每个柱的数值，并修改该数值的字体属性
		if(jfco.isShowBarNumber()==true){
			barrenderer.setItemLabelGenerator(new StandardCategoryItemLabelGenerator());
			barrenderer.setItemLabelsVisible(true);
		}		
//		barrenderer.setBaseOutlinePaint(Color.BLACK);
////		设置每种水果代表的柱的颜色
//		barrenderer.setSeriesPaint(0, new Color(0, 0, 255));
//		barrenderer.setSeriesPaint(1, new Color(0, 100, 255));
//		barrenderer.setSeriesPaint(2, Color.GREEN);

//		categoryplot.setRenderer(barrenderer);
		
		return jfreechart;
	}

}
