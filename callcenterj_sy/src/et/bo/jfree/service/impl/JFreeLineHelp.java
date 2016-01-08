/**
 * 
 */
package et.bo.jfree.service.impl;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.util.List;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 * @author zhangfeng
 * 
 */
public class JFreeLineHelp {

//	 生成柱状图数据集对象
	public static CategoryDataset createDataset(List<String> xChartValues ,
			List<String> yChartValues,List<String> barChartValues) {
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
				System.out.println(" ** 生成折线图数据集对象时，您传入的list数据集不匹配！** ");
				System.out.println("/***********************************************/");
			}
		} catch (Exception e) {
			System.out.println(" ** 生成折线图数据集对象时，您传入的list数据集里的对象类型不是String类型！** ");
		}
		
		return defaultcategorydataset;
	}
	/**
	 * 设置折线图的通用属性
	 * @param jfreechart
	 * @param jfco
	 * @return
	 */
	public static JFreeChart setLineShowAttribute(JFreeChart jfreechart,JFreeChartObject jfco) {
		//获得图表显示对象
//		 获得图表对象的引用，用于设置更多的自定义绘制属性
        CategoryPlot plot = jfreechart.getCategoryPlot();
    	//设置图表背景颜色
		if (!jfco.getChartBackGround().equals("")) {
			String backGroundValue = jfco.getChartBackGround();
			String[] str = backGroundValue.split(",");
			jfreechart.setBackgroundPaint(new Color(Integer.parseInt(str[0]),
					Integer.parseInt(str[1]), Integer.parseInt(str[2])));
		}
//		设置区域透明度
		if(jfco.getForegroundAlpha()!=0.0)
			plot.setForegroundAlpha(jfco.getForegroundAlpha());
	      
//		设置横坐标的显示位置
		/**
		 * 横坐标在下面显示，值为0:AxisLocation.BOTTOM_OR_LEFT;
		 * 横坐标在上面显示，值为1:AxisLocation.TOP_OR_LEFT;
		 * */
		switch (jfco.getXChartLocation()) {
		case 0:
			plot.setDomainAxisLocation(AxisLocation.BOTTOM_OR_LEFT);
			break;
		case 1:
			plot.setDomainAxisLocation(AxisLocation.TOP_OR_LEFT);
			break;
		}
//		设置纵坐标的显示位置
		/**
		 * 纵坐标在左面显示，值为0:AxisLocation.BOTTOM_OR_LEFT;
		 * 纵坐标在右面显示，值为1:AxisLocation.BOTTOM_OR_RIGHT;
		 * */
		switch (jfco.getYChartLocation()) {
		case 0:
			plot.setRangeAxisLocation(AxisLocation.BOTTOM_OR_LEFT);
			break;
		case 1:
			plot.setRangeAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);
			break;
		}
		
		//设置图表横格
		if(jfco.isXGridline()==true){
//			设置图表线
			plot.setDomainGridlinePaint(Color.BLACK);
			plot.setDomainGridlinesVisible(true);
		}
//		设置图表纵格
		if(jfco.isYGridline()==true){
			plot.setRangeGridlinePaint(Color.BLACK);
			plot.setRangeGridlinesVisible(true);
		}
			
//        GradientPaint bg = new GradientPaint(0, 50, new Color(248, 253, 255), 
//				0, 250, new Color(205, 237, 252));
//        plot.setBackgroundPaint(bg); 
//        plot.setDomainGridlinePaint(Color.BLACK);
//      背景表格线
//        plot.setDomainGridlinesVisible(true);
//        
//        plot.setRangeGridlinePaint(Color.RED);
//        plot.setRangeGridlinesVisible(true);

        // 设置横轴标题的字体
//		CategoryAxis domainAxis = plot.getDomainAxis();
//		domainAxis.setLabelFont(new Font("黑体", Font.BOLD, 15));
		
        // 设置纵轴标题文字的字体及其旋转方向
//		ValueAxis rangeAxis = plot.getRangeAxis();
//		rangeAxis.setLabelFont(new Font("黑体", Font.BOLD, 15));
//		rangeAxis.setLabelAngle(Math.PI/2);
	
		// 自定义图例的显示风格

        // 获取渲染对象
//        LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
		
		// 自定义线段的绘制颜色
//		Color color[] = new Color[jfco.getJfcd().getYChartValues().size()];
//		color[0] = new Color(99,99,0);
//		color[1] = new Color(255,169,66);
//		color[2] = new Color(33,255, 66);
//		color[3] = new Color(33,0,255);
//		color[4] = new Color(255,0,66);
//		int i=0;
//		for (i = 0; i < color.length; i++)
//		{
//			renderer.setSeriesPaint(i, color[i]);
//		}

		// 自定义线段的绘制风格
//		BasicStroke bs ;
//		for (i = 0; i < jfco.getJfcd().getYChartValues().size(); i++)
//		{
//			float dashes[] = {10.0f};
//			bs = new BasicStroke(2.0f, BasicStroke.CAP_ROUND, 
//						BasicStroke.JOIN_ROUND, 10.f, dashes, 0.0f);
////			if (i % 2 != 0)
////				renderer.setSeriesStroke(i, bs);
////			else
////				renderer.setSeriesStroke(i, new BasicStroke(2.0f));
//		}
      
		return jfreechart;
	}
	/**
	 * 设置3D折线图的属性
	 * @param jfreechart
	 * @param jfco
	 * @return
	 */
	public static JFreeChart set3DLineShowAttribute(JFreeChart jfreechart,JFreeChartObject jfco) {
		//获得图表显示对象
//		 获得图表对象的引用，用于设置更多的自定义绘制属性
        CategoryPlot plot = jfreechart.getCategoryPlot();
		return jfreechart;
	}
	/**
	 * 设置平面折线图的属性
	 * @param jfreechart
	 * @param jfco
	 * @return
	 */
	public static JFreeChart setCommonLineShowAttribute(JFreeChart jfreechart,JFreeChartObject jfco) {
		//获得图表显示对象
//		 获得图表对象的引用，用于设置更多的自定义绘制属性
        CategoryPlot plot = jfreechart.getCategoryPlot();
		return jfreechart;
	}

}
