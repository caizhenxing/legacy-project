/**
 * 
 * 项目名称：struts2
 * 制作时间：May 20, 20092:02:12 PM
 * 包名：base.zyf.common.tree
 * 文件名：ModuleTreeView.java
 * 制作者：zhaoyifei
 * @version 1.0
 */
package base.zyf.common.tree;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import base.zyf.common.tree.module.ModuleTreeRight;
import base.zyf.common.util.Constants;
import base.zyf.web.condition.ContextInfo;

/**
 * 显示模块树
 * @author zhaoyifei
 * @version 1.0
 */
public class ModuleTreeViewTag extends TagSupport {


    /**
     * The default directory name for icon images.
     */
    static final String DEFAULT_IMAGES = "images/tree";
    
    /**
     * The names of tree state images that we need.
     */
    static final String IMAGE_HANDLE_DOWN_LAST =    "handledownlast.gif";
    static final String IMAGE_HANDLE_DOWN_MIDDLE =  "handledownmiddle.gif";
    static final String IMAGE_HANDLE_RIGHT_LAST =   "handlerightlast.gif";
    static final String IMAGE_HANDLE_RIGHT_MIDDLE = "handlerightmiddle.gif";
    static final String IMAGE_LINE_LAST =           "linelastnode.gif";
    static final String IMAGE_LINE_MIDDLE =         "linemiddlenode.gif";
    static final String IMAGE_LINE_VERTICAL =       "linevertical.gif";
    static final String IMAGE_LEAF =       "leaf.gif";

    // ------------------------------------------------------------- Properties


    /**
     * The hyperlink to be used for submitting requests to expand and
     * contract tree nodes.  The placeholder "<code>${name}</code>" will
     * be replaced by the <code>name</code> property of the current
     * tree node.
     */
    protected String action = null;

    public String getAction() {
        return (this.action);
    }

    public void setAction(String action) {
        this.action = action; 
    }

    /**
     * The name of the directory containing the images for our icons,
     * relative to the page including this tag.
     */
    protected String images = DEFAULT_IMAGES;

    public String getImages() {
        return (this.images);
    }

    public void setImages(String images) {
    	
        
        this.images = "/"+Constants.getProperty("publicResourceServer")+"/"+images;
    }
    
    protected String target;
    

    /**
     * The name of the scope in which to search for the <code>tree</code>
     * attribute.  Must be "page", "request", "session", or "application"
     * (or <code>null</code> for an ascending-visibility search).
     */
   


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
     * The CSS style <code>class</code> to be applied to the text
     * of selected nodes.
     */
    protected String styleSelected = null;

    public String getStyleSelected() {
        return (this.styleSelected);
    }

    public void setStyleSelected(String styleSelected) {
        this.styleSelected = styleSelected;
    }


    /**
     * The CSS style <code>class</code> to be applied to the text
     * of unselected nodes.
     */
    protected String styleUnselected = null;

    public String getStyleUnselected() {
        return (this.styleUnselected);
    }

    public void setStyleUnselected(String styleUnselected) {
        this.styleUnselected = styleUnselected;
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
    "function changeCl(id)"+
    "{"+
    "	var dd=document.getElementById(id);"+
    "	var tab=document.getElementById('treetab');"+
    "	for (var i=0,j=tab.rows.length;i <j;i++) "+
    "	{ "+
    "		tab.rows(i).className='"+this.getStyleUnselected()+"';"+
    "	}"+
    "	dd.className='"+this.getStyleSelected()+"';"+
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
                ("<table id='treetab' border=\"0\" cellspacing=\"0\" cellpadding=\"0\"");
            if (style != null) {
                out.print(" class=\"");
                out.print(style);
                out.print("\"");
            }
            out.println(">");
            int level = 0;
            render(out, root, level, true);
            out.println("</table>");
        } catch (IOException e) {
            throw new JspException(e);
        }

        return (EVAL_PAGE);

    }


    /**
     * Release all state information set by this tag.
     */
    public void release() {

        this.action = null;
        this.images = DEFAULT_IMAGES;
      
        this.style = null;
        this.styleSelected = null;
        this.styleUnselected = null;
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

        return (TreeViewI)ContextInfo.getContextUser().getModuleTree();

    }


    /**
     * Render the specified node, as controlled by the specified parameters.
     *
     * @param out The <code>JspWriter</code> to which we are writing
     * @param node The <code>TreeControlNode</code> we are currently
     *  rendering
     * @param level The indentation level of this node in the tree
     * @param width Total displayable width of the tree
     * @param last Is this the last node in a list?
     *
     * @exception IOException if an input/output error occurs
     */
    protected void render(JspWriter out, TreeViewI node,
                          int level, boolean last)
        throws IOException {
    	HttpServletResponse response =
            (HttpServletResponse) pageContext.getResponse();
    	
    	Map<String, ModuleTreeRight> moduleRights = ContextInfo.getContextUser().getModuleRights();
    	int width=getWidth(moduleRights);
    	if(node.getParent() != null)
    	{
    		
    		if(!moduleRights.containsKey(node.getId()))
    			return;
    	}else
    	{
    		if(!moduleRights.containsKey(node.getId()))
    			{
    				moduleRights.put(node.getId(), new ModuleTreeRight(true, false, node));
    			}
    		else
    		{
    			moduleRights.get(node.getId()).setExpand(true);
    		}
    		if ((node.getLabel() == null)) {
    		            // Render the children of this node
    		        	List<TreeNodeI> children = node.getChildren();
    		            int lastIndex = children.size() - 1;
    		            int newLevel = level + 1;
    		            for (int i = 0; i < children.size(); i++) {
    		                render(out, (TreeViewI)children.get(i), newLevel, i == lastIndex);
    		            }
    		            return;
    		        }
    	}
        
    
        // if the node is root node and the label value is
        // null, then do not render root node in the tree.
        
        
        
        // Render the beginning of this node
        out.println("  <tr id='"+node.getId()+"' valign=\"middle\">");

        // Create the appropriate number of indents
        for (int i = 0; i < level; i++) {
            int levels = level - i;
            TreeViewI parent = node;
            for (int j = 1; j <= levels; j++)
                parent = (TreeViewI)parent.getParent();
            if (parent.isLast())
                out.print("    <td></td>");
            else {
                out.print("    <td><img src=\"");
                out.print(images);
                out.print("/");
                out.print(IMAGE_LINE_VERTICAL);
                out.print("\" alt=\"\" border=\"0\"></td>");
            }
            out.println();
        }

        // Render the tree state image for this node

        // HACK to take into account special characters like = and &
        // in the node name, could remove this code if encode URL
        // and later request.getParameter() could deal with = and &
        // character in parameter values. 
        String encodedNodeName = URLEncoder.encode(node.getId());

        String action = replace(getAction(), "tree=$-{name}", "module="+encodedNodeName);
        action ="/"+Constants.getProperty("publicResourceServer")+action;
        out.print("    <td>");
        if ((action != null) && !node.isLast()) {
            out.print("<a href=\"");
            out.print(response.encodeURL(action));
            out.print("\">");
        }
        out.print("<img src=\"");
        out.print(images);
        out.print("/");
       
        
        if (node.isLast()) {
            if (node.isLast())
                out.print(IMAGE_LINE_LAST);
            else
                out.print(IMAGE_LINE_MIDDLE);
            out.print("\" alt=\"");
        } else if (moduleRights.get(node.getId()).isExpand()) {
            if (node.isLast())
                out.print(IMAGE_HANDLE_DOWN_LAST);
            else
                out.print(IMAGE_HANDLE_DOWN_MIDDLE);
            out.print("\" alt=\"close node");
        } else {
            if (node.isLast())
                out.print(IMAGE_HANDLE_RIGHT_LAST);
            else
                out.print(IMAGE_HANDLE_RIGHT_MIDDLE);
            out.print("\" alt=\"expand node");
        }
        out.print("\" border=\"0\">");
        if ((action != null) && !node.isLast())
            out.print("</a>");
        out.println("</td>");

        // Calculate the hyperlink for this node (if any)
        String hyperlink = null;
        if (node.getAction() != null)
            hyperlink = ((HttpServletResponse) pageContext.getResponse()).
                encodeURL("/"+Constants.getProperty("publicResourceServer")+node.getAction());

        // Render the icon for this node (if any)
        out.print("    <td colspan=\"");
        out.print(width - level + 2);
        out.print("\">");
        if (node.getIcon() != null || node.isLast()) {
            if (hyperlink != null) {
                out.print("<a href=\"");
                out.print(hyperlink);
                out.print("\"");
                if(target != null) {
                    out.print(" target=\"");
                    out.print(target);
                    out.print("\"");
                }
                // to refresh the tree in the same 'self' frame
                out.print(" onclick=\"");
                out.print("changeCl('" + node.getId() + "')");
                out.print("\"");
                out.print(">");
            }
            out.print("<img src=\"");
            out.print(images);
            out.print("/");
            if (node.getIcon() != null)
            {
            out.print(node.getIcon());
            }else
            {
            	out.print(IMAGE_LEAF);
            }
            
            out.print("\" alt=\"");
            out.print("\" border=\"0\">");
            if (hyperlink != null)
                out.print("</a>");
        }

        // Render the label for this node (if any)

        if (node.getLabel() != null) {
            String labelStyle = null;
            labelStyle = styleUnselected;
            if (hyperlink != null) {
                // Note the leading space so that the text has some space
                // between it and any preceding images
                out.print(" <a href=\"");
                out.print(hyperlink);
                out.print("\"");
                if(target != null) {
                    out.print(" target=\"");
                    out.print(target);
                    out.print("\"");
                }
                if (labelStyle != null) {
                    out.print(" class=\"");
                    out.print(labelStyle);
                    out.print("\"");
                }
                // to refresh the tree in the same 'self' frame
                out.print(" onclick=\"");
                out.print("changeCl('" + node.getId() + "')");
                out.print("\"");
                out.print(">");
            } else if (labelStyle != null) {
                out.print("<span class=\"");
                out.print(labelStyle);
                out.print("\">");
            }
            out.print(node.getLabel());
            if (hyperlink != null)
                out.print("</a>");
            else if (labelStyle != null)
                out.print("</span>");
        }
        out.println("</td>");

        // Render the end of this node
        out.println("  </tr>");

        // Render the children of this node
        if (moduleRights.get(node.getId()).isExpand())
    	{
            List<TreeNodeI> children = node.getChildren();
            int lastIndex = children.size() - 1;
            int newLevel = level + 1;
            for (int i = 0; i < children.size(); i++) {
                render(out, (TreeViewI)children.get(i), newLevel, i == lastIndex);
            }
        }

    }
    
    private int getWidth(Map<String, ModuleTreeRight> moduleRights)
    {
    	int widthp = 1;
    	for(ModuleTreeRight mtr: moduleRights.values())
    	{
    		if(mtr.isExpand())
    		{
    			widthp = widthp>mtr.getNode().getLayer() ? widthp : mtr.getNode().getLayer();
    		}
    	}
    	return widthp;
    }

    /**
     * Replace any occurrence of the specified placeholder in the specified
     * template string with the specified replacement value.
     *
     * @param template Pattern string possibly containing the placeholder
     * @param placeholder Placeholder expression to be replaced
     * @param value Replacement value for the placeholder
     */
    protected String replace(String template, String placeholder,
                             String value) {
       
        if (template == null)
            return (null);
        if ((placeholder == null) || (value == null))
            return (template);
        while (true) {
            int index = template.indexOf(placeholder);
            if (index < 0)
                break;
            StringBuffer temp = new StringBuffer(template.substring(0, index));
            temp.append(value);
            temp.append(template.substring(index + placeholder.length()));
            template = temp.toString();
        }
        return (template);

    }

	/**
	 * @return the target
	 */
	public String getTarget() {
		return target;
	}

	/**
	 * @param target the target to set
	 */
	public void setTarget(String target) {
		this.target = target;
	}


}