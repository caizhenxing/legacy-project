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

//	 ������״ͼ���ݼ�����
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
				System.out.println(" ** ��������ͼ���ݼ�����ʱ���������list���ݼ���ƥ�䣡** ");
				System.out.println("/***********************************************/");
			}
		} catch (Exception e) {
			System.out.println(" ** ��������ͼ���ݼ�����ʱ���������list���ݼ���Ķ������Ͳ���String���ͣ�** ");
		}
		
		return defaultcategorydataset;
	}
	/**
	 * ��������ͼ��ͨ������
	 * @param jfreechart
	 * @param jfco
	 * @return
	 */
	public static JFreeChart setLineShowAttribute(JFreeChart jfreechart,JFreeChartObject jfco) {
		//���ͼ����ʾ����
//		 ���ͼ���������ã��������ø�����Զ����������
        CategoryPlot plot = jfreechart.getCategoryPlot();
    	//����ͼ������ɫ
		if (!jfco.getChartBackGround().equals("")) {
			String backGroundValue = jfco.getChartBackGround();
			String[] str = backGroundValue.split(",");
			jfreechart.setBackgroundPaint(new Color(Integer.parseInt(str[0]),
					Integer.parseInt(str[1]), Integer.parseInt(str[2])));
		}
//		��������͸����
		if(jfco.getForegroundAlpha()!=0.0)
			plot.setForegroundAlpha(jfco.getForegroundAlpha());
	      
//		���ú��������ʾλ��
		/**
		 * ��������������ʾ��ֵΪ0:AxisLocation.BOTTOM_OR_LEFT;
		 * ��������������ʾ��ֵΪ1:AxisLocation.TOP_OR_LEFT;
		 * */
		switch (jfco.getXChartLocation()) {
		case 0:
			plot.setDomainAxisLocation(AxisLocation.BOTTOM_OR_LEFT);
			break;
		case 1:
			plot.setDomainAxisLocation(AxisLocation.TOP_OR_LEFT);
			break;
		}
//		�������������ʾλ��
		/**
		 * ��������������ʾ��ֵΪ0:AxisLocation.BOTTOM_OR_LEFT;
		 * ��������������ʾ��ֵΪ1:AxisLocation.BOTTOM_OR_RIGHT;
		 * */
		switch (jfco.getYChartLocation()) {
		case 0:
			plot.setRangeAxisLocation(AxisLocation.BOTTOM_OR_LEFT);
			break;
		case 1:
			plot.setRangeAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);
			break;
		}
		
		//����ͼ����
		if(jfco.isXGridline()==true){
//			����ͼ����
			plot.setDomainGridlinePaint(Color.BLACK);
			plot.setDomainGridlinesVisible(true);
		}
//		����ͼ���ݸ�
		if(jfco.isYGridline()==true){
			plot.setRangeGridlinePaint(Color.BLACK);
			plot.setRangeGridlinesVisible(true);
		}
			
//        GradientPaint bg = new GradientPaint(0, 50, new Color(248, 253, 255), 
//				0, 250, new Color(205, 237, 252));
//        plot.setBackgroundPaint(bg); 
//        plot.setDomainGridlinePaint(Color.BLACK);
//      ���������
//        plot.setDomainGridlinesVisible(true);
//        
//        plot.setRangeGridlinePaint(Color.RED);
//        plot.setRangeGridlinesVisible(true);

        // ���ú�����������
//		CategoryAxis domainAxis = plot.getDomainAxis();
//		domainAxis.setLabelFont(new Font("����", Font.BOLD, 15));
		
        // ��������������ֵ����弰����ת����
//		ValueAxis rangeAxis = plot.getRangeAxis();
//		rangeAxis.setLabelFont(new Font("����", Font.BOLD, 15));
//		rangeAxis.setLabelAngle(Math.PI/2);
	
		// �Զ���ͼ������ʾ���

        // ��ȡ��Ⱦ����
//        LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
		
		// �Զ����߶εĻ�����ɫ
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

		// �Զ����߶εĻ��Ʒ��
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
	 * ����3D����ͼ������
	 * @param jfreechart
	 * @param jfco
	 * @return
	 */
	public static JFreeChart set3DLineShowAttribute(JFreeChart jfreechart,JFreeChartObject jfco) {
		//���ͼ����ʾ����
//		 ���ͼ���������ã��������ø�����Զ����������
        CategoryPlot plot = jfreechart.getCategoryPlot();
		return jfreechart;
	}
	/**
	 * ����ƽ������ͼ������
	 * @param jfreechart
	 * @param jfco
	 * @return
	 */
	public static JFreeChart setCommonLineShowAttribute(JFreeChart jfreechart,JFreeChartObject jfco) {
		//���ͼ����ʾ����
//		 ���ͼ���������ã��������ø�����Զ����������
        CategoryPlot plot = jfreechart.getCategoryPlot();
		return jfreechart;
	}

}
