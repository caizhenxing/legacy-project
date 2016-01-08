package et.bo.common.taglib;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.beanutils.BeanUtils;

import et.bo.common.login.ForumRight;

public class ForumRightTag extends TagSupport {
    
    private String path = null;

    private String action = null;

    private String userkey = null;

    private String areaid = null;

    private Object posts = null;

    private String beanName = null;

    private String propertyId = null;

    private String propertyAuthor = null;

    private String postid = null;

    private String postAuthor = null;

    private String css = null;

    private String id = null;

    public String getId() {
        String tempid = (String) pageContext.findAttribute(id);
        return tempid;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getCss() {
        return css;
    }

    public void setCss(String css) {
        this.css = css;
    }

    public String getPostAuthor() throws JspException {
        Object temp = getPosts();
        try {
            String author = (String) BeanUtils.getProperty(temp, this
                    .getPropertyAuthor());
            if (author == null)
                throw new JspException("no this property that's name is "
                        + this.getPropertyAuthor() + "in this bean ");
            return author;
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new JspException("no this property that's name is "
                    + this.getPropertyAuthor() + "in this bean ");

        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new JspException("no this property that's name is "
                    + this.getPropertyAuthor() + "in this bean ");

        } catch (NoSuchMethodException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new JspException("no this property that's name is "
                    + this.getPropertyAuthor() + "in this bean ");
        }
    }

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

    public int doEndTag() throws JspException {
        // TODO Auto-generated method stub
        JspWriter out = pageContext.getOut();
        ForumRight fr = new ForumRight();
        List right = fr.returnRight(this.getUserkey(), this
                .getPostAuthor(), this.getAreaid());
        try {
            if (right.contains(fr.FORBIDDEN))
            // �ղء�����
            {
            	
            }
            if (right.contains(fr.NORMAL))
            // �ظ�������
            {
            	out.print("����");
            }
            if (right.contains(fr.AUTHOR))
            // �༭
            {
            	out.print("�༩");
            }
            if (right.contains(fr.MANAGER))
            // �������ö����Ƽ���ת����ɾ��������
            {
            	out.print("�༩");
            	out.print("<a href=\""+"../postOper/oper.do?method=deletePosts&postsid="+this.getPostid()+"&areaid="+this.getAreaid()+"\">ɾ��</a>");
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return super.doEndTag();
    }

    public String getPropertyAuthor() {
        return propertyAuthor;
    }

    public void setPropertyAuthor(String propertyAuthor) {
        this.propertyAuthor = propertyAuthor;
    }

    public String getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(String propertyId) {
        this.propertyId = propertyId;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public String getPostid() throws JspException {
        Object temp = getPosts();
        try {
            String postids = (String) BeanUtils.getProperty(temp, this
                    .getPropertyId());
            if (postids == null)
                throw new JspException("no this property that's name is "
                        + this.getPropertyId() + "in this bean ");
            return postids;
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new JspException("no this property that's name is "
                    + this.getPropertyId() + "in this bean ");

        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new JspException("no this property that's name is "
                    + this.getPropertyId() + "in this bean ");

        } catch (NoSuchMethodException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new JspException("no this property that's name is "
                    + this.getPropertyId() + "in this bean ");
        }
    }
    
    //�õ�������Ϣ
    public Object getPosts() throws JspException {
        Object temp = pageContext.findAttribute(this.getBeanName());
        if (temp != null)
            posts = temp;
        if (temp == null)
            throw new JspException("no this bean that's name is "
                    + this.getBeanName() + "in any scope ");
        return temp;
    }

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		//this.path = com.hl.love.url.UrlParseXml.getApacheUrl(path);
		this.path = path;
	}
}
