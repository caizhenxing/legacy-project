/**
 * className TreePropertyService 
 * 
 * 创建日期 2008-1-4
 * 
 * @version
 * 
 * 版权所有 沈阳市卓越科技有限公司。
 */
package et.bo.sys.leaf.leafTag;

import java.io.IOException;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import et.po.SysLeafRight;

/**
 * 叶子节点按钮权限标签
 *
 * @version 	jan 01 2008 
 * @author 王文权
 */
public class LeafRightBtnTag extends TagSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/*
	 * 按钮类型 button reset
	 */
	private String type;
	/*
	 * 按钮id
	 */
	private String styleId;
	/*
	 * 按钮class
	 */
	private String styleClass;
	/*
	 * 按钮名字
	 */
	private String name;
	/*
	 * 按钮值 
	 */
	private String value;
	
	/*
	 * 单击事件
	 */
	private String onclick;
	/*
	 * 在 request session 里的名字
	 */
	private String scopeName;
	/**
     * 设定搜索范围
     */
    private String scope = null;
    
    /*
     * 为一名标签收索的
     */
    private String nickName = null;
    
    /*
     * 按钮宽度
     */
    private String width;
    /*
     * 按钮高度
     */
    private String height;
    
    /*
     * 按钮样式
     */
    private String style;
    
    
    public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStyleClass() {
		return styleClass;
	}

	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getScope() {
        return (this.scope);
    }

    public void setScope(String scope) {
        if (!"page".equals(scope) &&
            !"request".equals(scope) &&
            !"session".equals(scope) &&
            !"application".equals(scope))
            throw new IllegalArgumentException("Invalid scope '" +
                                               scope + "'");
        this.scope = scope;
    }
	public String getName() {
		return name;
	}

	
	public String getScopeName() {
		return scopeName;
	}

	public void setScopeName(String scopeName) {
		this.scopeName = scopeName;
	}

	public void setName(String name) {
		this.name = name;
	}


	public String getOnclick() {
		return onclick;
	}


	public void setOnclick(String onclick) {
		this.onclick = onclick;
	}


	public String getStyleId() {
		return styleId;
	}


	public void setStyleId(String styleId) {
		this.styleId = styleId;
	}


	public String getValue() {
		return value;
	}


	public void setValue(String value) {
		this.value = value;
	}


	/**
     * Render this tree control.
     *
     * @exception JspException if a processing error occurs
     */
    public int doEndTag() throws JspException {
    	JspWriter out = pageContext.getOut();
    	try
    	{
    		Map rights = this.getLeafRightMap();
    		SysLeafRight slr = (SysLeafRight)rights.get(this.getNickName());
			if(this.getType()==null)
			{
				type = "button";
			}
			out.print("<input type=\""+this.getType()+"\" ");
			if(this.getStyleId()!=null)
				out.print(" id=\""+this.getStyleId()+"\"");
			if(this.getName()!=null)
				out.print(" name=\""+this.getName()+"\"");
    		if(slr != null)
    		{
    
    			if(this.getOnclick()!=null)
    				out.print(" onclick=\""+this.onclick+"\"");
    			
    		}
    		else
    		{
    			out.print(" disabled=true");
    		}
    		if(this.getValue()!=null)
				out.print(" value=\""+this.value+"\"");
			if(this.getWidth()!=null)
				out.print(" width=\""+this.width+"\"");
			if(this.getHeight()!=null)
				out.print(" height=\""+this.height+"\"");
			if(this.getStyle()!=null)
				out.print(" style=\""+this.style+"\"");
			if(this.getStyleClass()!=null)
				out.print(" class=\""+styleClass+"\"");
			out.print(" />");
    		out.print("");
    	} 
    	catch (IOException e) {
            throw new JspException(e);
        }

        return (EVAL_PAGE);

    }

    /**
     * Return the <code>TreeControl</code> instance for the tree control that
     * we are rendering.
     *
     * @exception JspException if no TreeControl instance can be found
     */
    protected Map getLeafRightMap() throws JspException {

    	Object leafRightMap = null;
    	//System.out.println("tag tree is :"+tree);
        if (scope == null)
        	leafRightMap = pageContext.findAttribute(scopeName);
        else if ("page".equals(scope))
        	leafRightMap =
                pageContext.getAttribute(scopeName, PageContext.PAGE_SCOPE);
        else if ("request".equals(scope))
        	leafRightMap =
                pageContext.getAttribute(scopeName, PageContext.REQUEST_SCOPE);
        else if ("session".equals(scope))
        	leafRightMap =
                pageContext.getAttribute(scopeName, PageContext.SESSION_SCOPE);
        else if ("application".equals(scope))
        	leafRightMap =
                pageContext.getAttribute(scopeName, PageContext.APPLICATION_SCOPE);
        if (leafRightMap == null)
            throw new JspException("Cannot find tree control attribute '" +
            		scopeName + "'");
        else if (!(leafRightMap instanceof Map))
            throw new JspException("Invalid tree Service attribute '" +
            		scopeName + "'");
        else
            return ((Map) leafRightMap);

    }

    /**
     * Release all state information set by this tag.
     */
    public void release() 
    {
    	this.styleId = null;
    	this.name = null;
    	this.onclick = null;
    	this.value = null;
    }
}
