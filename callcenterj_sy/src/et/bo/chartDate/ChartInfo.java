/**
 * 	@(#)ChartInfo.java   2007-5-17 ����09:54:08
 *	��Ȩ���� ������׿Խ�Ƽ����޹�˾�� 
 *	׿Խ�Ƽ� ����һ��Ȩ��
 */
package et.bo.chartDate;

/**
 * @describe
 * @author С��
 * @version 2007-5-17
 * @see
 */
public class ChartInfo {
	//ͼ�����
	String chartTitle = "";
	//��Ŀ����
	String[] itemTitle = null;
	//�ж�������һ������
	String[] category = null;
	int width = 500;
	int height = 375;
	String chartX = "";
	String chartY = "";
	String PlotOrientation = "VERTICAL";
	int[][] dateSet = null;
	public String[] getCategory() {
		return category;
	}
	public void setCategory(String[] category) {
		this.category = category;
	}
	public String getChartTitle() {
		return chartTitle;
	}
	public void setChartTitle(String chartTitle) {
		this.chartTitle = chartTitle;
	}
	
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public String[] getItemTitle() {
		return itemTitle;
	}
	public void setItemTitle(String[] itemTitle) {
		this.itemTitle = itemTitle;
	}
	public String getPlotOrientation() {
		return PlotOrientation;
	}
	public void setPlotOrientation(String plotOrientation) {
		PlotOrientation = plotOrientation;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int[][] getDateSet() {
		return dateSet;
	}
	public void setDateSet(int[][] dateSet) {
		this.dateSet = dateSet;
	}
	public String getChartX() {
		return chartX;
	}
	public void setChartX(String chartX) {
		this.chartX = chartX;
	}
	public String getChartY() {
		return chartY;
	}
	public void setChartY(String chartY) {
		this.chartY = chartY;
	}
	
	
	
}
