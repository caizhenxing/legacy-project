/**
 * className TreePropertyService 
 * 
 * �������� 2008-1-4
 * 
 * @version
 * 
 * ��Ȩ���� ������׿Խ�Ƽ����޹�˾��
 */
package et.bo.sys.leaf.leafTag;

import java.io.IOException;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;

import et.po.SysLeafRight;

/**
 * Ҷ�ӽڵ㰴ťȨ�ޱ�ǩ
 *
 * @version 	jan 01 2008 
 * @author ����Ȩ
 */
public class LeafRightImgTag extends BodyTagSupport {

	/**
	 * <img alt="��ϸ" src="../style/chun/images/detail.gif"
							onclick="popUp('1OPER_FOCUSINFO_0000000183','focusPursue.do?method=toFocusPursueLoad&type=detail&id=OPER_FOCUSINFO_0000000183',1010,585)"
							width="16" height="16" border="0" />
	 */
	private static final long serialVersionUID = 1L;
	
	/*
	 * ��ťid
	 */
	private String styleId;
	/*
	 * ��ť����
	 */
	private String name;
	/*
	 * ��ʾ 
	 */
	private String alt;
	/*
	 * ͼƬλ��
	 */
	private String src;
	
	/*
	 * �����¼�
	 */
	private String onclick;
	/*
	 * �� request session �������
	 */
	private String scopeName;
	/**
     * �趨������Χ
     */
    private String scope = null;
    
    /*
     * Ϊһ����ǩ������
     */
    private String nickName = null;
    
    /*
     * ��ť���
     */
    private String width;
    /*
     * ��ť�߶�
     */
    private String height;
    
    /*
     * ��ť��ʽ
     */
    private String style;
    
    /**
     * border ��ť�߿�
     * @return
     */
    private String border;
    
    
    public String getBorder() {
		return border;
	}

	public void setBorder(String border) {
		this.border = border;
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
    		if(slr != null)
    		{
    			out.print("<img ");
    			if(this.getStyleId()!=null)
    				out.print(" id=\""+this.getStyleId()+"\"");
    			if(this.getName()!=null)
    				out.print(" name=\""+this.getName()+"\"");
    			if(this.getOnclick()!=null)
    				out.print(" onclick=\""+this.onclick+"\"");
    			if(this.getAlt()!=null)
    				out.print(" alt=\""+this.alt+"\"");
    			if(this.getSrc()!=null)
    				out.print(" src=\""+this.src+"\"");
    			if(this.getWidth()!=null)
    				out.print(" width=\""+this.width+"\"");
    			if(this.getHeight()!=null)
    				out.print(" height=\""+this.height+"\"");
    			if(this.getStyle()!=null)
    				out.print(" style=\""+this.style+"\"");
       			if(this.getBorder()!=null)
    				out.print(" border=\""+this.border+"\"");
    			out.print(" />");
    		}
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

    }

	public String getAlt() {
		return alt;
	}

	public void setAlt(String alt) {
		this.alt = alt;
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}
}
