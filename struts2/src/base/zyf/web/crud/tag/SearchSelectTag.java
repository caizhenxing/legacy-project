/**
 * 
 * 制作时间：Oct 25, 20078:35:06 PM
 * 文件名：SearchSelectTag.java
 * 制作者：zhaoyf
 * 
 */
package base.zyf.web.crud.tag;

import java.io.IOException;
import java.text.MessageFormat;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.util.ExpressionEvaluationUtils;

/**
 * @author zhaoyf
 *
 */
public class SearchSelectTag extends TagSupport {

	private String name;
	private static String enter="\r\n";
	private String elName;
	public String getElName() {
		return elName;
	}

	public void setElName(String elName) {
		this.elName = elName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int doEndTag() throws JspException {
		// TODO Auto-generated method stub
		JspWriter out = pageContext.getOut();
		if(StringUtils.isBlank(name))
			name=(String)ExpressionEvaluationUtils.evaluate("elName",elName, pageContext);
		StringBuffer tr=new StringBuffer();
		tr.append("<input name=\"conditions[{1}{0}{1}].name\" type=\"hidden\" value=\"{0}\"/>"+enter);
		tr.append("<input name=\"conditions[{1}{0}{1}].operator\" type=\"hidden\" value=\"=\"/>"+enter);
		tr.append("<input name=\"conditions[{1}{0}{1}].type\" type=\"hidden\" value=\"java.lang.String\"/>"+enter);
		tr.append("<input name=\"conditions[{1}{0}{1}].createAlias\" type=\"hidden\"  value=\"false\" />"+enter);
		try {
			
			out.print(MessageFormat.format(tr.toString(), new Object[]{name, "'"}));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return super.doEndTag();
	}
	
}
