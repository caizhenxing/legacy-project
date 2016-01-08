package et.bo.common.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import excellence.common.page.PageInfo;
import excellence.common.page.PageTurning;

public class FloorNumTag extends TagSupport {

	private String num=null;
	private String page="";
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = ((Integer)pageContext.getAttribute(num,
                PageContext.PAGE_SCOPE)).toString();
		if(this.num==null)
		this.num = ((Integer)pageContext.getAttribute(num,
                PageContext.REQUEST_SCOPE)).toString();
		if(this.num==null)
			this.num = ((Integer)pageContext.getAttribute(num,
	                PageContext.SESSION_SCOPE)).toString();
		
			//System.out.println("----------|"+this.num);
	}
	
	 public int doEndTag() throws JspException {
	        // TODO Auto-generated method stub
	        JspWriter out = pageContext.getOut();
	        
	        try {
	           int size=getFloorNum();
	           
	           if(size==0)
	        	   out.print("Â¥Ö÷");
	           else
	        	   out.print("µÚ"+Integer.toString(size)+"Â¥");
	        	   
	        } catch (IOException ioe) {
	            ioe.printStackTrace();
	        }
	        return super.doEndTag();
	    }
	 private int getFloorNum()
	 {
		 //StringBuffer floorNum=new  StringBuffer();
		 PageTurning  pt = (PageTurning) pageContext.getAttribute(page,
	                PageContext.SESSION_SCOPE);
         if (pt.getPage()==null) {
            
        }
		 PageInfo pi=pt.getPage();
		 int page=pi.getBegin();
		 page+=Integer.parseInt(num);
		 
	        return page;
	 }
	public String getPage() {
		return page;
	}
	public void setPage(String page) {
		this.page = page;
	}
}
