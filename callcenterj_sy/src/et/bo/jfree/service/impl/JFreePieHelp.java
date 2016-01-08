package et.bo.jfree.service.impl;

import java.awt.BasicStroke;
import java.awt.Color;
import java.util.List;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;

/**
 * @author zhangfeng
 * 
 */
public class JFreePieHelp {

	// 向饼图数据集内添充对象
	public static PieDataset createDataset(List<String> pieChartTextValues,
			List pieChartIntValues) {
		int k = 0;
		DefaultPieDataset defaultpiedataset = new DefaultPieDataset();
		try{
			if (pieChartTextValues.size() > pieChartIntValues.size()) {
				k = pieChartIntValues.size();
			} else {
				k = pieChartTextValues.size();
			}
			//System.out.println(k);
			for (int i = 0; i < k; i++) {
				String bValueJ = pieChartIntValues.get(i).toString();
				defaultpiedataset.setValue(pieChartTextValues.get(i), bValueJ
						.matches("[\\d]+") == true ? Integer.parseInt(bValueJ)
						: Double.parseDouble(bValueJ));
			}
		}
		catch (Exception e) {
			System.out.println("饼图数据集为空，出异常！");
		}
		return defaultpiedataset;
	}
	/**
	 * 设置饼状图的通用属性
	 * @param jfreechart
	 * @param jfco
	 * @return
	 */
	// 设置饼状图的属性
	public static JFreeChart setPieShowAttribute(JFreeChart jfreechart,
			JFreeChartObject jfco) {
		// 获得图表显示对象
		PiePlot plot = (PiePlot) jfreechart.getPlot();
		//设置图表背景颜色
		if (!jfco.getChartBackGround().equals("")) {
			String backGroundValue = jfco.getChartBackGround();
			String[] str = backGroundValue.split(",");
			jfreechart.setBackgroundPaint(new Color(Integer.parseInt(str[0]),
					Integer.parseInt(str[1]), Integer.parseInt(str[2])));
		}
		
//		 设置饼图的绘制方向:顺时针或者逆时针
		if("anticlockwise".equals(jfco.getOrentionPie())){
			plot.setDirection(Rotation.ANTICLOCKWISE);
		}else{
			plot.setDirection(Rotation.CLOCKWISE);
		}
//		 设置饼图轮廓线的绘制属性:颜色及线条粗细
		if (!jfco.getOutlinePieColor().equals("")) {
			String outlineColor = jfco.getOutlinePieColor();
			String[] str = outlineColor.split(",");
			plot.setSectionOutlinePaint(new Color(Integer.parseInt(str[0]),
					Integer.parseInt(str[1]), Integer.parseInt(str[2])));
		}
		if(jfco.getOutlinePieStroke()!=0.0f){
			plot.setSectionOutlineStroke(new BasicStroke(jfco.getOutlinePieStroke()));
		}
//		 设置饼图的外观为椭圆形还是正圆形
		if(jfco.isCircularPie()==false) 
			plot.setCircular(false);
		else 
			plot.setCircular(true);

		// 设置前景透明度
		if(jfco.getForegroundAlphaPie()!=0.0f)
			plot.setForegroundAlpha(jfco.getForegroundAlphaPie());

		// 设置背景透明度
		if(jfco.getBackgroundAlphaPie()!=0.0f)
			plot.setBackgroundAlpha(jfco.getBackgroundAlphaPie());

//		 设置第1段圆弧的绘制起始角度
		if(jfco.getStartAnglePie()!=0.0D)
			plot.setStartAngle(jfco.getStartAnglePie());
		
		return jfreechart;
	}
	/**
	 * 设置平面饼图的属性
	 * @param jfreechart
	 * @param jfco
	 * @return
	 */
	public static JFreeChart setCommonPieShowAttribute(JFreeChart jfreechart,
			JFreeChartObject jfco) {
		PiePlot plot = (PiePlot) jfreechart.getPlot();
//		 饼图分离部分，设置分离显示饼图中表示"JAVA"的那部分圆弧
		if(jfco.getPartPieName()!=null){
			for (int i = 0; i < jfco.getPartPieName().size(); i++) {
				for (int j = 0; j < jfco.getJfcd().getPieChartTextValues().size(); j++) {
					if (jfco.getJfcd().getPieChartTextValues().get(j).equals(jfco.getPartPieName().get(i))) {
						plot.setExplodePercent(j, 0.15);
					}
				}
			}
		}
		return jfreechart;
	}
	/**
	 * 设置3D饼图的属性
	 * @param jfreechart
	 * @param jfco
	 * @return
	 */
	public static JFreeChart set3DPieShowAttribute(JFreeChart jfreechart,
			JFreeChartObject jfco) {
		PiePlot plot = (PiePlot) jfreechart.getPlot();
		return jfreechart;
	}
}
