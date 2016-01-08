package base.zyf.chart.action;

import java.util.LinkedList;
import java.util.List;

import base.zyf.chart.action.chart.Element;
import base.zyf.chart.action.chart.Title;

import com.opensymphony.xwork2.Action;

/**
 * So this is the class that gets serialized to JSON
 * @author tim
 */
public class Bar {
	protected Title title;
	protected List<Element> elements;
	
	public String ch() {
		title = new Title();
		title.setText("Hej");
		elements = new LinkedList<Element>();
		Element e = new Element();
		Object[] data = new Object[] {1,2,3,4,5,6,7,8,9,10};
		e.setValues(data);
		elements.add(e);
		return Action.SUCCESS;
	}	

	// Getters and Setters follow
	public Title getTitle() {
		return title;
	}
	public void setTitle(Title title) {
		this.title = title;
	}
	public List<Element> getElements() {
		return elements;
	}
	public void setElements(List<Element> elements) {
		this.elements = elements;
	}
}
