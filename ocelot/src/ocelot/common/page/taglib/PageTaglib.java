package ocelot.common.page.taglib;


import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import ocelot.common.page.PageTurning;



 /**
 * @author 赵一非
 * @version 2007-1-15
 * @see
 */
public class PageTaglib extends TagSupport {

	String name=null;
	
	String style=null;
	
	static String table="<table border='0' width='100%' class='pagetable'>";
	static String table_="</table>";
	static String tr="<tr>";
	static String tr_="</tr>";
	static String td="<td align='center'>";
	static String td_="</td>";
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}
	public int doEndTag() throws JspException {
		// TODO Auto-generated method stub
		JspWriter out = pageContext.getOut();
        
        try {
        	PageTurning pt=getPt();
           
           if(style.equals("first"))
           {
        	   out.print(table);
        	   out.print(tr);
        	   out.print(td);
        	   out.print(pt.getShowSummary());
        	   out.print(td_);
        	   out.print(td);
        	   out.print(pt.getFirstPage());
        	   out.print(pt.getFirstWords());
        	   out.print(pt.getFirstA());
        	   out.print(td_);
        	   out.print(td);
        	   out.print(pt.getPreviousPage());
        	   out.print(pt.getPreviousWords());
        	   out.print(pt.getPreviousA());
        	   out.print(td_);
        	   out.print(td);
        	   out.print(pt.getNextPage());
        	   out.print(pt.getNextWords());
        	   out.print(pt.getNextA());
        	   out.print(td_);
        	   out.print(td);
        	   out.print(pt.getLastPage());
        	   out.print(pt.getLastWords());
        	   out.print(pt.getLastA());
        	   out.print(td_);
        	   out.print(tr_);
        	   out.print(table_);
           }
           if(style.equals("second"))
           {
        	   String script="\r\n<script>\r\n"+
    		   "function changepageto(pagechangea){\r\n"+
	    	"if(isNaN(pagechangea.value))\r\n"+
			"{\r\n"+
			"	pagechangea.value=\"\";\r\n"+
			"	alert(\"输入合法数字\");\r\n"+
			"}\r\n"+
			"pagechangeurlid.href+=pagechangea.value;\r\n"+
       		"}\r\n"+
       		"</script>\r\n";
        	   int last=pt.getPage().getLastPage();
        	   int current=pt.getPage().getShowPage();
        	   int begin =current;
        	   int end =current;
        	   while(end<begin+6)
        	   {
        		   if(current>last)
        		   {
        			   end=last;
        			   break;
        		   }
        		   if(begin>1)
        			   begin--;
        		   if(end<last)
        			   end++;
        		   if(begin==1&&end==last)
        			   break;
        	   }
        	   out.print(table);
        	   out.print(tr);
        	  
        	   out.print(td);
        	   out.print(pt.getShowSummary());
        	   out.print("  ");
        	   out.print(pt.getFirstPage());
        	   out.print("<<");
        	 
        	   out.print(pt.getFirstA());
        	   
        	   for(int i=begin;i<=end;i++)
        	   {
        		   
            	   out.print(pt.getThePage(i));
            	   if(i==current)
            		   out.print(" <font color='red'>");
            	   out.print(Integer.toString(i));
            	   out.print(".");
            	   if(i==current)
            		   out.print("</font> ");
            	   out.print(pt.getThePageEnd());
            	   
        	   }
        	   if(end!=last)
        		   out.print("...");
        	   out.print(pt.getLastPage());
        	 
        	   out.print(">>");
        	   out.print(pt.getLastA());
        	   out.print("   ");
        	   out.print("快速跳转");
        	   out.print("<input type='text' name='pagechangea'size='2' onchange='changepageto(pagechangea)' value='"+Integer.toString(current)+"'/>");
        	   out.print(" ");
        	   out.print(pt.getThePage("pagechangeurlid"));
        	   out.print("go");
        	   out.print(pt.getThePageEnd());
        	   out.print(td_);
        	   out.print(script);
        	   out.print(tr_);
        	   out.print(table_);
           }
           
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return super.doEndTag();
	}
	public PageTurning getPt() throws JspException {
		PageTurning pt=null;
		pt = (PageTurning)pageContext.getAttribute(name,
                PageContext.PAGE_SCOPE);
		if(pt==null)
		pt = (PageTurning)pageContext.getAttribute(name,
                PageContext.REQUEST_SCOPE);
		if(pt==null)
			pt = (PageTurning)pageContext.getAttribute(name,
	                PageContext.SESSION_SCOPE);
		if(pt==null)
			 throw new JspException("Cannot find pageTurning attribute " );
		return pt;
	}
	
}
