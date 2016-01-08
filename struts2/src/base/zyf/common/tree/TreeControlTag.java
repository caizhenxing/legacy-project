/*
 * Copyright 2001,2004 The Apache Software Foundation.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
//package com.cw.common;
package base.zyf.common.tree;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import base.zyf.common.tree.module.ModuleTreeRight;
import base.zyf.common.util.Constants;
import base.zyf.web.condition.ContextInfo;



/**
 * <p>JSP custom tag that renders a tree control represented by the
 * <code>TreeControl</code> and <code>TreeControlNode</code> classes.
 * This tag has the following user-settable attributes:</p>
 * <ul>
 * <li><strong>action</strong> - Hyperlink to which expand/contract actions
 *     should be sent, with a string "<code>${node}</code> marking where
 *     the node name of the affected node should be included.</li>
 * <li><strong>images</strong> - Name of the directory containing the images
 *     for our icons, relative to the page including this tag.  If not
 *     specified, defaults to "images".</li>
 * <li><strong>scope</strong> - Attribute scope in which the <code>tree</code>
 *     attribute is to be found (page, request, session, application).  If
 *     not specified, the attribute is searched for in all scopes.</li>
 * <li><strong>style</strong> - CSS style <code>class</code> to be applied
 *     to be applied to the entire rendered output of the tree control.
 *     If not specified, no style class is applied.</li>
 * <li><strong>styleSelected</strong> - CSS style <code>class</code> to be
 *     applied to the text of any element that is currently selected.  If not
 *     specified, no additional style class is applied.</li>
 * <li><strong>styleUnselected</strong> - CSS style <code>class</code> to be
 *     applied to the text of any element that is not currently selected.
 *     If not specified, no additional style class is applied.</li>
 * <li><strong>tree</strong> - Attribute name under which the
 *     <code>TreeControl</code> bean of the tree we are rendering
 *     is stored, in the scope specified by the <code>scope</code>
 *     attribute.  This attribute is required.</li>
 * </ul>
 *
 * <strong>FIXME</strong> - Internationalize the exception messages!
 *
 * @author Craig R. McClanahan
 * @version $Revision: 1.1 $ $Date: 2008/03/11 00:58:09 $
 */

public class TreeControlTag extends TagSupport {


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
    "</script>";

    /**
     * Render this tree control.
     *
     * @exception JspException if a processing error occurs
     */
    public int doEndTag() throws JspException {

    	TreeViewI root = getTreeRoot();
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
            if(!root.isLast())
            {
            	out.print("<tr><td ><img id=\"img_"+root.getId()+"\" src=\""+ this.getImages()+ClOSE_IMG +"\" onclick=\"changeExp('"+root.getId()+"')\" title='点击此处关闭节点'/></td><td style=\"cursor:hand\" title='点击此处显示详细' onclick=\"viewDetail('"+root.getId()+"')\">"+ root.getName() +"</td></tr>");
            	printChildren(out, root.getChildren(), root.getId(), true);
            }else
            {
            	 out.print("<tr><td ><img src=\""+ this.getImages()+NONE_IMG +"\"/></td><td style=\"cursor:hand\" title='点击此处显示详细' onclick=\"viewDetail('"+root.getId()+"')\">"+ root.getName() +"</td></tr>");
            }
            out.println("</table>");
        } catch (IOException e) {
            throw new JspException(e);
        }

        return (EVAL_PAGE);

    }

    private void printChildren(JspWriter out, List<TreeViewI> l,String id, boolean exp) throws IOException
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
    		if(!tvi.isLast())
            {
            	out.print("<tr><td ><img id=\"img_"+tvi.getId()+"\" src=\""+ this.getImages()+ClOSE_IMG +"\" onclick=\"changeExp('"+tvi.getId()+"')\" title='点击此处关闭节点'/></td><td style=\"cursor:hand\" title='点击此处显示详细' onclick=\"viewDetail('"+tvi.getId()+"')\">"+ tvi.getName() +"</td></tr>");
            	printChildren(out, tvi.getChildren(), tvi.getId(), false);
            }else
            {
            	 out.print("<tr><td ><img src=\""+ this.getImages()+NONE_IMG +"\"/></td><td style=\"cursor:hand\" title='点击此处显示详细' onclick=\"viewDetail('"+tvi.getId()+"')\">"+ tvi.getName() +"</td></tr>");
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


    
}
