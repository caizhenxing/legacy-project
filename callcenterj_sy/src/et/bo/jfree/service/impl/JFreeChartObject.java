/**
 * 
 */
package et.bo.jfree.service.impl;

import java.util.List;
import java.util.Map;

/**
 * @author zhangfeng
 * 设置属性的
 */
public class JFreeChartObject {
	
	/**
	 *  jfreechart图表公共属性
	 */
	// jfreechart图表对象标题
	private String chartTitle = "";
	// jfreechart图对象的背景颜色
	private String chartBackGround = "";
//	 设定哪个块颜色,key为块名,value为颜色类型111,111,111
	private Map<String,String> chartMapColor; 
	// 数据集对象所用到的元素
	private JFreeChartDataSet jfcd = new JFreeChartDataSet();
	
	/**
	 * jfreechart柱状图或线性图公共属性
	 */
	// jfreechart柱状图或线性图横坐标名称
	private String xChartName="";
	// jfreechart柱状图或线性图纵坐标名称
	private String yChartName="";
//	设置横坐标显示位置
	private int xChartLocation;
//	设置横坐标显示位置
	private int yChartLocation;
//	设置柱的透明度
	private float foregroundAlpha;
	
	/**
	 * jfreechart柱状图属性
	 */
//	设置每个柱子之间的距离
	private double itemMarginBar;
//	设置最高的一个 Item 与图片顶端的距离
	private double upperMarginBar;
//	设置最低的一个 Item 与图片底端的距离
	private double lowerMarginBar;
//	是否显示每个柱的数值
	private boolean showBarNumber=false;


	/**
	 * jfreechart线性图属性
	 */
	//设置图表横格
	private boolean xGridline=true;
//	设置图表纵格
	private boolean yGridline=true;
	
	
	/**
	 * jfreechart饼图属性
	 */
	// 饼图分离部分，设置分离显示饼图中表示"JAVA"的那部分圆弧
	private List<String> partPieName;
//	 设置饼图的绘制方向:顺时针或者逆时针
	private String orentionPie="";
//	 设置饼图轮廓线的绘制属性:颜色及线条粗细
	private String outlinePieColor="";
	private float outlinePieStroke;
//	 设置饼图的外观为椭圆形还是正圆形
	private boolean isCircularPie=true;
//	 设置前景透明度
	private float foregroundAlphaPie;
//	 设置背景透明度
	private float backgroundAlphaPie;
//	 设置饼图中第1段圆弧的绘制起始角度
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
