/**
 * className EnuNavItem 
 * 
 * �������� 2008-5-12
 * 
 * @version
 * 
 * ��Ȩ���� ������׿Խ�Ƽ����޹�˾��
 */
package et.bo.sys.tag;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import excellence.common.tree.base.service.TreeControlNodeService;
import excellence.common.tree.ext.view.impl.ViewTree;
import excellence.common.tree.ext.view.impl.ViewTreeControlNode;
import excellence.common.tree.ext.view.tag.TreeControlTag;
import excellence.common.util.Constants;
/**
 * ��ǩ������ʾ�� 
 * ��̳�TreeControlTag��дrender����
 * @version 	2008-05-06
 * @author ����Ȩ
 */
public class EnuNavItem extends TreeControlTag {
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//css��ʽid
	private String styleClass = null;
	//����ּ�����ʾ
	private String viewCols = "4";
	//���ˮƽ���з�ʽ
	private String horizontalAlign = "left";
	//�����ֱ���з�ʽ
	//private String vertical = "";
	private TreeControlNodeService root = null;
	//ͼƬ��css class����
	private String imgClass;
	
	private String skins;
    /**
     * Render this tree control.
     *
     * @exception JspException if a processing error occurs
     */
    public int doEndTag() throws JspException {
    	 this.curTdCount = 0;
    	 ViewTree treeControl = getTreeControl();
    	 //System.out.println(treeControl.getRegistry().keySet().size()+"::::::::::");
    	 //System.out.println("treeControl is :"+treeControl);
        JspWriter out = pageContext.getOut();
        try {
        	out.print(this.printJsFun());
        	out.print(this.printChangeImg());
            out.print
                ("<table border=\"0\" cellspacing=\"0\" cellpadding=\"1\"");
            if (style != null) {
                out.print(" class=\"");
                out.print(style);
                out.print("\"");
            }
            out.println(">");
            int level = 0;
            try
            {
            
            TreeControlNodeService node = treeControl.getRoot();
            this.root = node;
            //������ڵ� ������� ��ʾ������action�Ľڵ�
            render(out, node, level, treeControl.getWidth(), true);
            }
            catch(Exception e){e.printStackTrace();}
            out.println("</tr></table>");
        } catch (IOException e) {
            throw new JspException(e);
        }

        return (EVAL_PAGE);

    }
	
    //��render�õļ�¼��ǰ��ʾ�˵ڼ���td
    private int curTdCount = 0;
    //��render�õļ�¼һ����ʾ�˼���td
    private int trCols = Integer.parseInt(this.getViewCols());
	/**
     * Render the specified node, as controlled by the specified parameters.
     * ��ߵ��������������õ�
     * @param out The <code>JspWriter</code> to which we are writing
     * @param node The <code>TreeControlNode</code> we are currently
     *  rendering
     * @param level The indentation level of this node in the tree
     * @param width Total displayable width of the tree
     * @param last Is this the last node in a list?
     *
     * @exception IOException if an input/output error occurs
     */
    protected void render(JspWriter out, TreeControlNodeService nodeService,
                          int level, int width, boolean last)
        throws IOException {
    	 ViewTreeControlNode node = (ViewTreeControlNode)nodeService;
         //���ӽڵ�
    	 List<ViewTreeControlNode> nodeList =  node.getChildren();
         //System.out.println(node.getLabel()+":"+node.getChildren().size());
    	 if(nodeList.size()==0)
    	 {
    		 //
    		 if(node.getAction()!=null&&!"".equals(node.getAction().trim()))
			 {
				 //System.out.println(node.getIconByKey("agentLeaf")+":"+node.getIconByKey("agentLeafGray"));
    			 if(curTdCount==0)
    			 {
    				 out.println("<tr valign=\"middle\">");
    			 }
    			 //��Ҷ�ӽڵ�
    			 String aTarget = "";
    			 if(node.getTarget()!=null&&!"".equals(node.getTarget().trim()))
    			 {
    				 aTarget = " target = \""+node.getTarget()+"\" ";
    			 }
    			 String aStyleClass = "";
    			 if(this.getStyleClass()!=null&&!"".equals(this.getStyleClass().trim()))
    			 {
    				 aStyleClass = " class=\""+this.getStyleClass()+"\" ";
    			 }
    			 out.print("<td><a  href=\""+node.getAction()+"\" "+aTarget+" "+aStyleClass+">"+showImgOrLabel(node)+"</a></td>");
    			 if(curTdCount>0&&(curTdCount+1)%trCols==0)
    			 {
    				 out.println("<tr/><tr valign=\"middle\">");
    			 }
        			 //һ����Чд��
        			 curTdCount++;
    		 }
    		 //
    	 }
    	 else
    	 {
    		 for(int i=0; i<nodeList.size(); i++)
    		 {
    			render(out,nodeList.get(i),0,0,false); 
    		 }
    	 }
    }
    //��ϯ��������Ӧ�Ķ�ӦͼƬ��ʾͼƬ û����ʾlabel
    private String showImgOrLabel(ViewTreeControlNode node)
    {
    	String commonImg = node.getIconByKey("agentLeaf");
    	String grayImg = node.getIconByKey("agentLeafGray");
    	if(commonImg!=null&&grayImg!=null)
    	{
    		StringBuffer sb = new StringBuffer();
    		//<img src="style/xia/images/dl9.jpg" width="96" height="22" / class="btnstyle" />
    		String imgClass = "";
    		if(this.getImgClass()!=null)
    		{
    			imgClass = "class=\""+this.getImgClass()+"\"";
    		}
    		String skins = this.getSkins();
    		if(skins == null)
    		{
    			skins = "";
    		}
    		else 
    		{
    			skins = skins + "/";
    		}
    		sb.append("<img id=\""+node.getId()+"\" style=\"border:0px;\" "+imgClass+" src=\""+"/"+Constants.getProperty("project_name")+"/style/"+skins+"images/grantNav/"+commonImg+"\" "+imgMouseOverOutClickEvent(grayImg,commonImg)+"/>");
    		return sb.toString();
    	}
    	else
    	{
    		return node.getLabel();
    	}
    }
    private String imgMouseOverOutClickEvent(String overImg, String outImg)
    {
    	StringBuffer sb = new StringBuffer();
    	//sb.append("onmouseover=\"this.src='"+"/"+Constants.getProperty("project_name")+"/"+"images/grantNav/"+overImg+"'\" ");
    	//sb.append("onmouseout=\"this.src='"+"/"+Constants.getProperty("project_name")+"/"+"images/grantNav/"+outImg+"'\" ");
    	sb.append("onclick=\"clickChangeImg('"+this.getImgClass()+"',this.id)\"");
    	return sb.toString();
    }
    //��ӡjs����
    private String printJsFun()
    {
    	StringBuffer sb = new StringBuffer();
    	sb.append("<script language=\"javascript\">");
    	sb.append(" document.getElementsByClassName = function(clsName){    var retVal = new Array();    var elements = document.getElementsByTagName(\"*\");    for(var i = 0;i < elements.length;i++){        if(elements[i].className.indexOf(\" \") >= 0){            var classes = elements[i].className.split(\" \");            for(var j = 0;j < classes.length;j++){                if(classes[j] == clsName)                    retVal.push(elements[i]);            }        }        else if(elements[i].className == clsName)            retVal.push(elements[i]);    }    return retVal;} ");
    	sb.append("</script>");
    	return sb.toString();
    }
    //��ӡjs����
    private String printChangeImg()
    {
    	StringBuffer sb = new StringBuffer();
    	sb.append("<script language=\"javascript\">");
    	sb.append(" function clickChangeImg(imgClass,id) ");
    	sb.append(" { ");
    	sb.append(" setAllImgNonGray(imgClass); ");
    	sb.append(" var oImg = document.getElementById(id); ");
    	sb.append(" if(oImg) ");
    	sb.append(" { ");
    	sb.append("  	var src = oImg.src; ");
    	sb.append("     	if(src.indexOf(\"_gray\")==-1) ");
    	sb.append("     	{ " );
    	sb.append("     		var dot = src.lastIndexOf(\".\"); ");
    	sb.append("     		oImg.src = src.substring(0,dot)+\"_gray\"+src.substring(dot); ");
    	sb.append("     	} ");
    	sb.append("     } ");
    	sb.append("  } ");
    	 
    	sb.append("  function setAllImgNonGray(imgClass) ");
    	sb.append(" { ");
    	sb.append("  	var imgs = document.getElementsByClassName(imgClass); ");
    	sb.append("  	for(var i=0; i<imgs.length; i++) ");
    	sb.append(" 	{ ");
    	sb.append(" 		var imgSrc = imgs[i].src; ");
    	sb.append(" 	 	indexUnderline = imgSrc.indexOf(\"_gray\"); ");
    	sb.append(" 	 	indexDot = imgSrc.lastIndexOf(\".\"); ");
    	sb.append(" 	 	if(indexUnderline!=-1) ");
    	sb.append(" 	 	{ ");
    	sb.append(" 	 		imgs[i].src = imgSrc.substring(0,indexUnderline)+imgSrc.substring(indexDot);");
    	sb.append(" 	 	} ");
    	sb.append("  	} ");
    	sb.append("  } ");
    	sb.append("</script>");
    	return sb.toString();
    }
	public String getHorizontalAlign() {
		return horizontalAlign;
	}


	public void setHorizontalAlign(String horizontalAlign) {
		this.horizontalAlign = horizontalAlign;
	}



	public String getViewCols() {
		return viewCols;
	}


	public void setViewCols(String viewCols) {
		this.viewCols = viewCols;
	}


	public String getStyleClass() {
		return styleClass;
	}


	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}
	public String getImgClass() {
		return imgClass;
	}
	public void setImgClass(String imgClass) {
		this.imgClass = imgClass;
	}
	public String getSkins() {
		return skins;
	}
	public void setSkins(String skins) {
		this.skins = skins;
	}
    
    
}

