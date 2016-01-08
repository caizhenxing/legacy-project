/**
 * 
 */
package et.bo.jfree.service.impl;

import java.util.List;
import java.util.Map;

/**
 * @author zhangfeng
 * �������Ե�
 */
public class JFreeChartObject {
	
	/**
	 *  jfreechartͼ��������
	 */
	// jfreechartͼ��������
	private String chartTitle = "";
	// jfreechartͼ����ı�����ɫ
	private String chartBackGround = "";
//	 �趨�ĸ�����ɫ,keyΪ����,valueΪ��ɫ����111,111,111
	private Map<String,String> chartMapColor; 
	// ���ݼ��������õ���Ԫ��
	private JFreeChartDataSet jfcd = new JFreeChartDataSet();
	
	/**
	 * jfreechart��״ͼ������ͼ��������
	 */
	// jfreechart��״ͼ������ͼ����������
	private String xChartName="";
	// jfreechart��״ͼ������ͼ����������
	private String yChartName="";
//	���ú�������ʾλ��
	private int xChartLocation;
//	���ú�������ʾλ��
	private int yChartLocation;
//	��������͸����
	private float foregroundAlpha;
	
	/**
	 * jfreechart��״ͼ����
	 */
//	����ÿ������֮��ľ���
	private double itemMarginBar;
//	������ߵ�һ�� Item ��ͼƬ���˵ľ���
	private double upperMarginBar;
//	������͵�һ�� Item ��ͼƬ�׶˵ľ���
	private double lowerMarginBar;
//	�Ƿ���ʾÿ��������ֵ
	private boolean showBarNumber=false;


	/**
	 * jfreechart����ͼ����
	 */
	//����ͼ����
	private boolean xGridline=true;
//	����ͼ���ݸ�
	private boolean yGridline=true;
	
	
	/**
	 * jfreechart��ͼ����
	 */
	// ��ͼ���벿�֣����÷�����ʾ��ͼ�б�ʾ"JAVA"���ǲ���Բ��
	private List<String> partPieName;
//	 ���ñ�ͼ�Ļ��Ʒ���:˳ʱ�������ʱ��
	private String orentionPie="";
//	 ���ñ�ͼ�����ߵĻ�������:��ɫ��������ϸ
	private String outlinePieColor="";
	private float outlinePieStroke;
//	 ���ñ�ͼ�����Ϊ��Բ�λ�����Բ��
	private boolean isCircularPie=true;
//	 ����ǰ��͸����
	private float foregroundAlphaPie;
//	 ���ñ���͸����
	private float backgroundAlphaPie;
//	 ���ñ�ͼ�е�1��Բ���Ļ�����ʼ�Ƕ�
	private double startAnglePie;


	public JFreeChartDataSet getJfcd() {
		return jfcd;
	}

	public void setJfcd(JFreeChartDataSet jfcd) {
		this.jfcd = jfcd;
	}

	public String getChartTitle() {
		return chartTitle;
	}

	public void setChartTitle(String chartTitle) {
		this.chartTitle = chartTitle;
	}

	public String getChartBackGround() {
		return chartBackGround;
	}

	public void setChartBackGround(String chartBackGround) {
		this.chartBackGround = chartBackGround;
	}

	public Map<String, String> getChartMapColor() {
		return chartMapColor;
	}

	public void setChartMapColor(Map<String, String> chartMapColor) {
		this.chartMapColor = chartMapColor;
	}

	public String getXChartName() {
		return xChartName;
	}

	public void setXChartName(String chartName) {
		xChartName = chartName;
	}

	public String getYChartName() {
		return yChartName;
	}

	public void setYChartName(String chartName) {
		yChartName = chartName;
	}

	public List<String> getPartPieName() {
		return partPieName;
	}

	public void setPartPieName(List<String> partPieName) {
		this.partPieName = partPieName;
	}

	public String getOrentionPie() {
		return orentionPie;
	}

	public void setOrentionPie(String orentionPie) {
		this.orentionPie = orentionPie;
	}

	public String getOutlinePieColor() {
		return outlinePieColor;
	}

	public void setOutlinePieColor(String outlinePieColor) {
		this.outlinePieColor = outlinePieColor;
	}

	public float getOutlinePieStroke() {
		return outlinePieStroke;
	}

	public void setOutlinePieStroke(float outlinePieStroke) {
		this.outlinePieStroke = outlinePieStroke;
	}

	public float getBackgroundAlphaPie() {
		return backgroundAlphaPie;
	}

	public void setBackgroundAlphaPie(float backgroundAlphaPie) {
		this.backgroundAlphaPie = backgroundAlphaPie;
	}

	public float getForegroundAlphaPie() {
		return foregroundAlphaPie;
	}

	public void setForegroundAlphaPie(float foregroundAlphaPie) {
		this.foregroundAlphaPie = foregroundAlphaPie;
	}

	public boolean isCircularPie() {
		return isCircularPie;
	}

	public void setCircularPie(boolean isCircularPie) {
		this.isCircularPie = isCircularPie;
	}

	public double getStartAnglePie() {
		return startAnglePie;
	}

	public void setStartAnglePie(double startAnglePie) {
		this.startAnglePie = startAnglePie;
	}

	public double getItemMarginBar() {
		return itemMarginBar;
	}

	public void setItemMarginBar(double itemMarginBar) {
		this.itemMarginBar = itemMarginBar;
	}

	public float getForegroundAlpha() {
		return foregroundAlpha;
	}

	public void setForegroundAlpha(float foregroundAlpha) {
		this.foregroundAlpha = foregroundAlpha;
	}

	public double getLowerMarginBar() {
		return lowerMarginBar;
	}

	public void setLowerMarginBar(double lowerMarginBar) {
		this.lowerMarginBar = lowerMarginBar;
	}

	public boolean isShowBarNumber() {
		return showBarNumber;
	}

	public void setShowBarNumber(boolean showBarNumber) {
		this.showBarNumber = showBarNumber;
	}

	public double getUpperMarginBar() {
		return upperMarginBar;
	}

	public void setUpperMarginBar(double upperMarginBar) {
		this.upperMarginBar = upperMarginBar;
	}

	public int getXChartLocation() {
		return xChartLocation;
	}

	public void setXChartLocation(int chartLocation) {
		xChartLocation = chartLocation;
	}

	public int getYChartLocation() {
		return yChartLocation;
	}

	public void setYChartLocation(int chartLocation) {
		yChartLocation = chartLocation;
	}

	public boolean isXGridline() {
		return xGridline;
	}

	public void setXGridline(boolean gridline) {
		xGridline = gridline;
	}

	public boolean isYGridline() {
		return yGridline;
	}

	public void setYGridline(boolean gridline) {
		yGridline = gridline;
	}

}
