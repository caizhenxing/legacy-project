package base.zyf.chart.action.chart;

public class Element {
	// set a default
	private String type = "pie";
	private String colour;
	private String text;
	// the data values
	Object[] values;
    
    // Getters and setters follow
    public Object[] getValues() {
		return values;
	}
	public void setValues(Object[] values) {
		this.values = values;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
