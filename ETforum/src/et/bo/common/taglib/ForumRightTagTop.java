package et.bo.common.taglib;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import et.bo.common.login.ForumRight;

/*
 * ������ϸ��Ϣ�б�ͷ
 */
public class ForumRightTagTop extends TagSupport {
    
    private String path =null;

    private String id = null;

    private String action = null;

    private String css = null;

    private String userkey = null;

    private String areaid = null;

    public String getAreaid() {
        String areaid = (String) pageContext.getAttribute("topicId",
                PageContext.SESSION_SCOPE);
        return areaid;
    }

    public String getUserkey() {
        String userkey = (String) pageContext.getAttribute("userkey",
                PageContext.SESSION_SCOPE);
        return userkey;
    }
    
    public String getId() {
        String id = (String) pageContext.getAttribute("postid",
                pageContext.REQUEST_SCOPE);
        return id;
    }

    public String getCss() {
        return css;
    }

    public void setCss(String css) {
        this.css = css;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public int doEndTag() throws JspException {
        // TODO Auto-generated method stub
        JspWriter out = pageContext.getOut();
        ForumRight fr = new ForumRight();
        List right = fr.returnRight(this.getUserkey(), "",
                this.getAreaid());
        try {

        	//../postOper/oper.do?method=addCollection&postid=<bean:write name='postid'/>"

        	if (right.contains(fr.NORMAL)||right.contains(fr.AUTHOR)) {
				out.print("<a href=\""+"../postOper/oper.do?method=addCollection&postid="+this.getId()+"\">�ղ�</a>");
			}
            if (right.contains(fr.MANAGER))
            // �������ö����Ƽ���ת����ɾ��������
            {
            	out.print("�Ƽ�");
            	out.print("ת��");
            	out.print("����");
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return super.doEndTag();
    }

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		//this. path = com.hl.love.url.UrlParseXml.getApacheUrl(path);
		this.path =path;
	}
}
