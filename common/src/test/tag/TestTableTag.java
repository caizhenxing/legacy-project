/**
 * 沈阳卓越科技有限公司版权所有
 * 项目名称：common
 * 制作时间：Nov 21, 20072:46:46 PM
 * 包名：test.tag
 * 文件名：TestTableTag.java
 * 制作者：zhaoyf
 * @version 1.0
 */
package test.tag;

import java.io.StringWriter;
import java.text.MessageFormat;

import org.apache.commons.jexl.Expression;
import org.apache.commons.jexl.ExpressionFactory;
import org.apache.commons.jexl.JexlContext;
import org.apache.commons.jexl.JexlHelper;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

/**
 * 
 * @author zhaoyf
 * @version 1.0
 */
public class TestTableTag {

	private String linkScript="<div onclick=\"javascript:definedWin.openUrl(''{0}'', ''{1}'')\" class=\"font_link\">{2}</div>";
	
	private String qwareServer="/qware";
	private String changeScript(String popName,String link,String view,Object bean)
	{
		if(StringUtils.isBlank(link))
			return view;
		if(StringUtils.isBlank(view))
			return "&nbsp;";
		return MessageFormat.format(linkScript,new Object[]{popName,changeLink(link,bean),view});
	}
	private String changeLink(String link,Object bean)
	{
		try {
			Velocity.init();
			VelocityContext context=new VelocityContext();
			context.put("bean",bean);
			StringWriter out=new StringWriter();
			boolean a=Velocity.evaluate(context, out, "",qwareServer+link);
			String s=out.toString();
			return s;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
		
	}
	
	/**
	 * 功能描述
	 * @param args
	 * Nov 21, 2007 2:46:46 PM
	 * @version 1.0
	 * @author zhaoyf
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TestTableTag ttt=new TestTableTag();
		Bean b=new Bean();
		b.setId("ddddddddd");
		System.out.println(ttt.changeScript("popName", "/hr/EmpInfoAction.do?step=info&oid=${bean.id}", "vvvv", b));
	}

}
