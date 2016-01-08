/**
 * 
 * 制作时间：Oct 25, 20078:33:54 PM
 * 文件名：SearchTimeTag.java
 * 制作者：zhaoyf
 * 
 */
package base.zyf.web.crud.tag;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.util.ExpressionEvaluationUtils;

import base.zyf.tools.Tools;
import base.zyf.web.condition.Condition;

/**
 * @author zhaoyf
 *
 */
public class SearchTimeTag extends TagSupport {

	private static String enter="\r\n";
	private String name;
	private String elName;
	private String valueDefault;
	private String oper;
	private String pattern;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	public String getValueDefault() {
		return valueDefault;
	}
	public void setValueDefault(String valueDefault) {
		this.valueDefault = valueDefault;
	}
	public String getOper() {
		return oper;
	}
	public void setOper(String oper) {
		this.oper = oper;
	}
	
	public String getPattern() {
		return pattern;
	}
	public void setPattern(String pattern) {
		this.pattern = pattern;
	}
	public int doEndTag() throws JspException {
		// TODO Auto-generated method stub
		JspWriter out = pageContext.getOut();
		Map<String,Condition> conditions = (Map<String,Condition>)ExpressionEvaluationUtils.evaluate("valueDefault","${conditions}", pageContext);
		if(StringUtils.isBlank(name))
			name=(String)ExpressionEvaluationUtils.evaluate("elName",elName, pageContext);
		
		Date date1 = null;
		Date date2 = null;
		Condition c1 = conditions.get(name+"ge");
		Condition c2 = conditions.get(name+"le");
		if(c1 != null)
		{
			date1 = (Date)c1.getValue();
		}
		if(c2 != null)
		{
			date2 = (Date)c2.getValue();
		}
			
		StringBuffer tr=new StringBuffer();
		tr.append("<input name=\"conditions[{3}{0}{1}{3}].name\" type=\"hidden\" value=\"{0}\"/>"+enter);
		tr.append("<input name=\"conditions[{3}{0}{1}{3}].operator\" type=\"hidden\" value=\"{1}\"/>"+enter);
		tr.append("<input name=\"conditions[{3}{0}{1}{3}].type\" type=\"hidden\" value=\"java.util.Date\"/>"+enter);
		tr.append("<input name=\"conditions[{3}{0}{1}{3}].value\"  type=\"text\" id=\"input_text\" value=\"{2}\" class=\"readonly\" readonly=\"readonly\" style=\"text-align:left\"/>");
		if("yyyy-MM-dd".equals(this.pattern))
			tr.append("<input type=\"button\" id=\"input_date\" name=\"powerWorkTime_button\" onclick=\"DateComponent.setDay(this, $({3}conditions[\\{3}{0}{1}\\{3}].value{3}));\" />"+enter);
		if("yyyy-MM-dd hh:mm:ss".equals(this.pattern))
				tr.append("<input type=\"button\" id=\"input_date\" name=\"powerWorkTime_button\" onclick=\"DateComponent.setDay(this, $({3}conditions[\\{3}{0}{1}\\{3}].value{3}),{3}{3});\" />"+enter);
		if(" hh:mm:ss".equals(this.pattern))
				tr.append("<input type=\"button\" id=\"input_date\" name=\"powerWorkTime_button\" onclick=\"DateComponent.setDay(this,{3}{3},$({3}conditions[\\{3}{0}{1}\\{3}].value{3}));\" />"+enter);
		try {
			out.print(MessageFormat.format(tr.toString(), new Object[]{name,"ge",Tools.getTheTime(date1, pattern),"'"}));
			out.print(" 至 ");
			out.print(MessageFormat.format(tr.toString(), new Object[]{name,"le",Tools.getTheTime(date2, pattern),"'"}));
			out.print(" <input type=\"button\" id=\"opera_clear\" onclick=\"FormUtils.cleanValues($('conditions[\\'"+name+">="+"\\'].value'), $('conditions[\\'"+name+">="+"\\'].value')); FormUtils.cleanValues($('conditions[\\'"+name+"<="+"\\'].value'), $('conditions[\\'"+name+"<="+"\\'].value'))\"/>");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return super.doEndTag();
	}
	public String getElName() {
		return elName;
	}
	public void setElName(String elName) {
		this.elName = elName;
	}
	
}
