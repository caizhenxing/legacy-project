/**
 * 
 * 项目名称：struts2
 * 制作时间：Jun 3, 200910:40:22 AM
 * 包名：base.zyf.common.tree
 * 文件名：TreeGrantTag.java
 * 制作者：zhaoyifei
 * @version 1.0
 */
package base.zyf.common.tree;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import base.zyf.common.util.Constants;

import com.cc.sys.db.SysGroup;
import com.cc.sys.db.SysRightGroup;

/**
 * 用于授权，每个节点前都有checkbox
 * @author zhaoyifei
 * @version 1.0
 */
public class TreeGrantTag extends TagSupport {

    /**
     * The default directory name for icon images.
     */
    static final String DEFAULT_IMAGES = "image/tree/depart/";
    
    static final String OPEN_IMG = "folder_open.gif";
    
    static final String ClOSE_IMG = "folder_close.gif";
    
    static final String NONE_IMG = "pic_dept.gif";

    /**
     * The name of the directory containing the images for our icons,
     * relative to the page including this tag.
     */
    protected String images = DEFAULT_IMAGES;

    public String getImages() {
    	if(this.images.indexOf("/")!=0)
    		this.images = "/"+Constants.getProperty("publicResourceServer")+"/"+images;
        return (this.images);
    }

    public void setImages(String images) {
    	this.images = "/"+Constants.getProperty("publicResourceServer")+"/"+images;
    }
   
   


    /**
     * The CSS style <code>class</code> to be applied to the entire tree.
     */
    protected String style = null;

    public String getStyle() {
        return (this.style);
    }

    public void setStyle(String style) {
        this.style = style;
    }






    /**
     * The name of the attribute (in the specified scope) under which our
     * <code>TreeControl</code> instance is stored.
     */
    protected String tree = null;

    public String getTree() {
        return (this.tree);
    }

    public void setTree(String tree) {
        this.tree = tree;
    }


    // --------------------------------------------------------- Public Methods
    private String script = "<script type=\"text/javascript\">"+
    "function changeExp(id)"+
    "{"+
    "	var img=document.getElementById('img_'+id);"+
    "	var tr=document.getElementById('tr_'+id);"+
    "	if(tr.style.display == 'block')"+
    "	{"+
    "		tr.style.display = 'none';"+
    "		img.src = '"+this.getImages()+ClOSE_IMG+"';"+
    "		img.title = '点击此处展开节点';"+
    "	}else"+
    "	{"+
    "		tr.style.display = 'block';"+
    "		img.src = '"+this.getImages()+OPEN_IMG+"';"+
    "		img.title = '点击此处关闭节点';"+
    "	"+
    "	}"+
    "}"+
    "function clickcheck(id)"+
    "{"+
    "	var a = document.getElementById(id);"+
    "	var arr  = new Array();"+
    "	i = 0;"+
    "	var str = id;"+
    "	while(str.lastIndexOf('_') != -1)"+
    "	{"+
    "		arr[i] = str.substring(0,str.lastIndexOf('_'));"+
    "		str = str.substring(0,str.lastIndexOf('_'));"+
    "		i++;"+
    "	}"+
    "	var as = document.getElementsByName(\"temp_all_checkbox\");"+
    "	if(a.checked)"+
    "	{"+
    "		for(var i=0;i<as.length;i++)"+
    "		{"+
    "			temp = as[i];"+
    "			if(temp.id.indexOf(a.id) != -1)"+
    "			{"+
    "				temp.checked = true;"+
    "			}"+
    "			for(var ii=0;ii<arr.length;ii++)"+
    "			{"+
    "				if(temp.id == arr[ii])"+
    "				{"+
    "					temp.checked = true;"+
    "				}"+
    "			}"+
    "		}"+
    "	}else"+
    "	{"+
    "		for(var i=0;i<as.length;i++)"+
    "		{"+
    "		temp = as[i];"+
    "			if(temp.id.indexOf(a.id) != -1)"+
    "				temp.checked = false;"+
    "		}"+
    "	}"+
    "}"+
    "</script>";

    /**
     * Render this tree control.
     *
     * @exception JspException if a processing error occurs
     */
    public int doEndTag() throws JspException {

    	TreeViewI root = getTreeRoot();
    	this.loadRight();
        JspWriter out = pageContext.getOut();
        try {
        	out.print(script);
            out.print
                ("<table id='tree' border=\"0\" cellspacing=\"0\" cellpadding=\"0\"");
            if (style != null) {
                out.print(" class=\"");
                out.print(style);
                out.print("\"");
            }
            out.println(">");
            String checked = "";
            if(rights.contains(root.getId()))
            	checked = "checked";
            if(!root.isLast())
            {
            	out.print("<tr><td ><img id=\"img_"+root.getId()+"\" src=\""+ this.getImages()+ClOSE_IMG +"\" onclick=\"changeExp('"+root.getId()+"')\" title='点击此处关闭节点'/></td><td><input type='checkbox' id='"+root.getId()+"' onclick=\"clickcheck('"+root.getId()+"')\" name='temp_all_checkbox' title='授权' "+checked+"  value=\""+root.getId()+"\"/>"+ root.getName() +"</td></tr>");
            	printChildren(out, root.getChildren(), root.getId(), true,root.getId());
            }else
            {
            	 out.print("<tr><td ><img src=\""+ this.getImages()+NONE_IMG +"\"/></td><td><input type='checkbox' id='"+root.getId()+"' onclick=\"clickcheck('"+root.getId()+"')\" name='temp_all_checkbox' title='授权'  "+checked+"  value=\""+root.getId()+"\"/>"+ root.getName() +"</td></tr>");
            }
            out.println("</table>");
            this.rights.clear();
        } catch (IOException e) {
        	this.rights.clear();
            throw new JspException(e);
        }

        return (EVAL_PAGE);

    }

    private void printChildren(JspWriter out, List<TreeViewI> l,String id, boolean exp, String checkId) throws IOException
    {
    	if(l.size() == 0)
    		return;
    	if(exp)
    	{
    		out.println("<tr id=\"tr_" + id + "\" style='display:block'><td></td><td>");
    	}else
    	{
    		out.println("<tr id=\"tr_" + id + "\" style='display:none'><td></td><td>");
    	}
    	out.println("<table>");
    	
    	for(TreeViewI tvi:l)
    	{
    		String tempc = checkId+"_"+tvi.getId();
    		String checked = "";
            if(rights.contains(tvi.getId()))
            	checked = "checked";
    		if(!tvi.isLast())
            {
    			
            	out.print("<tr><td ><img id=\"img_"+tvi.getId()+"\" src=\""+ this.getImages()+ClOSE_IMG +"\" onclick=\"changeExp('"+tvi.getId()+"')\" title='点击此处关闭节点'/></td><td><input type='checkbox' id='"+tempc+"' onclick=\"clickcheck('"+tempc+"')\" name='temp_all_checkbox' title='授权'  "+checked+" value=\""+tvi.getId()+"\"/>"+ tvi.getName() +"</td></tr>");
            	printChildren(out, tvi.getChildren(), tvi.getId(), false, tempc);
            }else
            {
            	 out.print("<tr><td ><img src=\""+ this.getImages()+NONE_IMG +"\"/></td><td><input type='checkbox' id='"+tempc+"' onclick=\"clickcheck('"+tempc+"')\" name='temp_all_checkbox' title='授权'   "+checked+" value=\""+tvi.getId()+"\"/>"+ tvi.getName() +"</td></tr>");
            }
    	}
    	out.println("</table>");
    	out.println("</td></tr>");
    }

    /**
     * Release all state information set by this tag.
     */
    public void release() {

       
        this.images = DEFAULT_IMAGES;
      
        this.style = null;
      
        this.tree = null;
        this.rights.clear();
    }


    // ------------------------------------------------------ Protected Methods


    /**
     * Return the <code>TreeControl</code> instance for the tree control that
     * we are rendering.
     *
     * @exception JspException if no TreeControl instance can be found
     */
    protected TreeViewI getTreeRoot() throws JspException {
    	TreeViewI tvi;
            tvi = (TreeViewI)pageContext.findAttribute(tree);
        if (tvi == null)
            tvi =
            	(TreeViewI)pageContext.getAttribute(tree, PageContext.PAGE_SCOPE);
        else if (tvi == null)
            tvi =
            	(TreeViewI)pageContext.getAttribute(tree, PageContext.REQUEST_SCOPE);
        else if (tvi == null)
            tvi =
            	(TreeViewI)pageContext.getAttribute(tree, PageContext.SESSION_SCOPE);
        else if (tvi == null)
            tvi =
            	(TreeViewI)pageContext.getAttribute(tree, PageContext.APPLICATION_SCOPE);
        return tvi;

    }
    List<String> rights = new ArrayList<String>();
    private void loadRight()
    {
    	SysGroup sg;
    	sg = (SysGroup)pageContext.findAttribute("entityObject");
        if (sg == null)
        	sg =
            	(SysGroup)pageContext.getAttribute("entityObject", PageContext.PAGE_SCOPE);
        else if (sg == null)
        	sg =
            	(SysGroup)pageContext.getAttribute("entityObject", PageContext.REQUEST_SCOPE);
        else if (sg == null)
        	sg =
            	(SysGroup)pageContext.getAttribute("entityObject", PageContext.SESSION_SCOPE);
        else if (sg == null)
        	sg =
            	(SysGroup)pageContext.getAttribute("entityObject", PageContext.APPLICATION_SCOPE);
        if(sg != null)
        {
        	Iterator i = sg.getSysRightGroups().iterator();
        	while(i.hasNext())
        	{
        		SysRightGroup srg = (SysRightGroup)i.next();
        		rights.add(srg.getModId());
        	}
        }
    }

}
