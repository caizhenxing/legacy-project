
package base.zyf.chart.action.chart;
/**
 * 
 * openflashchart
 * ��ʾ����
 * All attributes are optional.
 * text: string, the title
 * style: string, the CSS style
 * @author zhaoyifei
 * @version 1.0
 */
public class Title {
	/**
	 * ��ʾ����
	 */
	protected String text;
	/**
	 * ��ʽ
	 */
	private String style;
	/**
	 * @return the style
	 */
	public String getStyle() {
		return style;
	}
	/**
	 * @param style the style to set
	 */
	public void setStyle(String style) {
		this.style = style;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
}
