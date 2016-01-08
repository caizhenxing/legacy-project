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
 * ��JFreeChartDataSet.java������Դ���������
 */
public class JFreeBarHelp {

//	 ������״ͼ���ݼ�����
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
				System.out.println(" ** ������״ͼ���ݼ�����ʱ���������list���ݼ���ƥ�䣡** ");
				System.out.println("/***********************************************/");
			}
		} catch (Exception e) {
			System.out.println(" ** ������״ͼ���ݼ�����ʱ���������list���ݼ���Ķ������Ͳ���String���ͣ�** ");
		}
		
		return defaultcategorydataset;
	}
	/**
	 * ������״ͼ��ͨ������
	 * @param jfreechart
	 * @param jfco
	 * @return
	 */
	public static JFreeChart setBarShowAttribute(JFreeChart jfreechart,JFreeChartObject jfco) {
		//���ͼ����ʾ����
		CategoryPlot categoryplot = jfreechart.getCategoryPlot();
		CategoryAxis categoryaxis = categoryplot.getDomainAxis();
//		�����ϵ� Lable 45����б
//		categoryaxis.setCategoryLabelPositions(CategoryLabelPositions
//				.createUpRotationLabelPositions(0.39269908169872414D));
		CategoryItemRenderer categoryitemrenderer = categoryplot.getRenderer();
		categoryitemrenderer.setItemLabelsVisible(true);
//		����ͼ������ɫ
		if (!jfco.getChartBackGround().equals("")) {
			String backGroundValue = jfco.getChartBackGround();
			String[] str = backGroundValue.split(",");
			jfreechart.setBackgroundPaint(new Color(Integer.parseInt(str[0]),
					Integer.parseInt(str[1]), Integer.parseInt(str[2])));
		}   
		ValueAxis rangeAxis = categoryplot.getRangeAxis();
//		������ߵ�һ�� Item ��ͼƬ���˵ľ���
		if(jfco.getUpperMarginBar()!=0.0)
			rangeAxis.setUpperMargin(jfco.getUpperMarginBar());
//		������͵�һ�� Item ��ͼƬ�׶˵ľ���
		if(jfco.getLowerMarginBar()!=0.0)
			rangeAxis.setLowerMargin(jfco.getLowerMarginBar());
		categoryplot.setRangeAxis(rangeAxis);
//		��������͸����
		if(jfco.getForegroundAlpha()!=0.0)
			categoryplot.setForegroundAlpha(jfco.getForegroundAlpha());
//		���ú��������ʾλ��
		/**
		 * ��������������ʾ��ֵΪ0:AxisLocation.BOTTOM_OR_LEFT;
		 * ��������������ʾ��ֵΪ1:AxisLocation.TOP_OR_LEFT;
		 * */
		switch (jfco.getXChartLocation()) {
		case 0:
			categoryplot.setDomainAxisLocation(AxisLocation.BOTTOM_OR_LEFT);
			break;
		case 1:
			categoryplot.setDomainAxisLocation(AxisLocation.TOP_OR_LEFT);
			break;
		}
//		�������������ʾλ��
		/**
		 * ��������������ʾ��ֵΪ0:AxisLocation.BOTTOM_OR_LEFT;
		 * ��������������ʾ��ֵΪ1:AxisLocation.BOTTOM_OR_RIGHT;
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
	 * ����3D��״ͼ������
	 * @param jfreechart
	 * @param jfco
	 * @return
	 */
	public static JFreeChart set3DBarShowAttribute(JFreeChart jfreechart,JFreeChartObject jfco) {
		//���ͼ����ʾ����
		CategoryPlot categoryplot = jfreechart.getCategoryPlot();
		CategoryItemRenderer categoryitemrenderer = categoryplot.getRenderer();
		BarRenderer3D barrenderer = (BarRenderer3D) categoryitemrenderer;
//		����ÿ������֮��ľ���
		if(jfco.getItemMarginBar()!=0.0D)
			barrenderer.setItemMargin(jfco.getItemMarginBar());
		
//		��ʾÿ��������ֵ�����޸ĸ���ֵ����������
		if(jfco.isShowBarNumber()==true){
			barrenderer.setItemLabelGenerator(new StandardCategoryItemLabelGenerator());
			barrenderer.setItemLabelsVisible(true);
		}
//		barrenderer.setBaseOutlinePaint(Color.BLACK);
////		���� Wall ����ɫ
//		barrenderer.setWallPaint(Color.gray);
////		����ÿ��ˮ�������������ɫ
//		barrenderer.setSeriesPaint(0, new Color(0, 0, 255));
//		barrenderer.setSeriesPaint(1, new Color(0, 100, 255));
//		barrenderer.setSeriesPaint(2, Color.GREEN);

//		categoryplot.setRenderer(barrenderer);
		return jfreechart;
	}
	/**
	 * ����ƽ����״ͼ������
	 * @param jfreechart
	 * @param jfco
	 * @return
	 */
	public static JFreeChart setCommonBarShowAttribute(JFreeChart jfreechart,JFreeChartObject jfco) {
		//���ͼ����ʾ����
		CategoryPlot categoryplot = jfreechart.getCategoryPlot();
		CategoryItemRenderer categoryitemrenderer = categoryplot.getRenderer();
		BarRenderer barrenderer = (BarRenderer) categoryitemrenderer;
//		����ÿ������֮��ľ���
		if(jfco.getItemMarginBar()!=0.0D)
			barrenderer.setItemMargin(jfco.getItemMarginBar());
//		��ʾÿ��������ֵ�����޸ĸ���ֵ����������
		if(jfco.isShowBarNumber()==true){
			barrenderer.setItemLabelGenerator(new StandardCategoryItemLabelGenerator());
			barrenderer.setItemLabelsVisible(true);
		}		
//		barrenderer.setBaseOutlinePaint(Color.BLACK);
////		����ÿ��ˮ�������������ɫ
//		barrenderer.setSeriesPaint(0, new Color(0, 0, 255));
//		barrenderer.setSeriesPaint(1, new Color(0, 100, 255));
//		barrenderer.setSeriesPaint(2, Color.GREEN);

//		categoryplot.setRenderer(barrenderer);
		
		return jfreechart;
	}

}
