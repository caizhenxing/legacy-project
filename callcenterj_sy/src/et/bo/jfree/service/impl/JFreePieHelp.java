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

	// ���ͼ���ݼ���������
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
			System.out.println("��ͼ���ݼ�Ϊ�գ����쳣��");
		}
		return defaultpiedataset;
	}
	/**
	 * ���ñ�״ͼ��ͨ������
	 * @param jfreechart
	 * @param jfco
	 * @return
	 */
	// ���ñ�״ͼ������
	public static JFreeChart setPieShowAttribute(JFreeChart jfreechart,
			JFreeChartObject jfco) {
		// ���ͼ����ʾ����
		PiePlot plot = (PiePlot) jfreechart.getPlot();
		//����ͼ������ɫ
		if (!jfco.getChartBackGround().equals("")) {
			String backGroundValue = jfco.getChartBackGround();
			String[] str = backGroundValue.split(",");
			jfreechart.setBackgroundPaint(new Color(Integer.parseInt(str[0]),
					Integer.parseInt(str[1]), Integer.parseInt(str[2])));
		}
		
//		 ���ñ�ͼ�Ļ��Ʒ���:˳ʱ�������ʱ��
		if("anticlockwise".equals(jfco.getOrentionPie())){
			plot.setDirection(Rotation.ANTICLOCKWISE);
		}else{
			plot.setDirection(Rotation.CLOCKWISE);
		}
//		 ���ñ�ͼ�����ߵĻ�������:��ɫ��������ϸ
		if (!jfco.getOutlinePieColor().equals("")) {
			String outlineColor = jfco.getOutlinePieColor();
			String[] str = outlineColor.split(",");
			plot.setSectionOutlinePaint(new Color(Integer.parseInt(str[0]),
					Integer.parseInt(str[1]), Integer.parseInt(str[2])));
		}
		if(jfco.getOutlinePieStroke()!=0.0f){
			plot.setSectionOutlineStroke(new BasicStroke(jfco.getOutlinePieStroke()));
		}
//		 ���ñ�ͼ�����Ϊ��Բ�λ�����Բ��
		if(jfco.isCircularPie()==false) 
			plot.setCircular(false);
		else 
			plot.setCircular(true);

		// ����ǰ��͸����
		if(jfco.getForegroundAlphaPie()!=0.0f)
			plot.setForegroundAlpha(jfco.getForegroundAlphaPie());

		// ���ñ���͸����
		if(jfco.getBackgroundAlphaPie()!=0.0f)
			plot.setBackgroundAlpha(jfco.getBackgroundAlphaPie());

//		 ���õ�1��Բ���Ļ�����ʼ�Ƕ�
		if(jfco.getStartAnglePie()!=0.0D)
			plot.setStartAngle(jfco.getStartAnglePie());
		
		return jfreechart;
	}
	/**
	 * ����ƽ���ͼ������
	 * @param jfreechart
	 * @param jfco
	 * @return
	 */
	public static JFreeChart setCommonPieShowAttribute(JFreeChart jfreechart,
			JFreeChartObject jfco) {
		PiePlot plot = (PiePlot) jfreechart.getPlot();
//		 ��ͼ���벿�֣����÷�����ʾ��ͼ�б�ʾ"JAVA"���ǲ���Բ��
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
	 * ����3D��ͼ������
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
