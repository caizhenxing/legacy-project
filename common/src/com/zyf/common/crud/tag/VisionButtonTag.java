/**
 * 沈阳卓越科技有限公司版权所有
 * 制作时间：Oct 25, 20078:34:26 PM
 * 文件名：ComboCompositeTag.java
 * 制作者：wuym
 * 
 */
package com.zyf.common.crud.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.util.ExpressionEvaluationUtils;

/**
 * @author wuym
 *
 */
public class VisionButtonTag extends TagSupport {

	private String rwCtrlType = "";					
	private String permissionCode = "";
	private String wfPermissionCode = "";			//工作流PermissionCode
    private String name = "";                       //名称
    private String id = "";                         //id
    private String clazz = "";                      //CSS class
    private String onclick = "";                    //点击事件
    private String disabled = "false";              //disabled状态
    private String value = "";                      //按扭名称
    private String visible = "";                    //visible状态
    private String row = "";                        //可编辑列表的row
    private String holdObj = "";                    //可编辑列表的holdObj
    private String style = "";                      //style
    private String title = "";                      //title
	private String comInvorkeeClassFullName = "";	//设定值所属类绝对路径(服务器端处理)

	private String objectRwCtrlType = "";					
	private String objectPermissionCode = "";
	private String objectWfPermissionCode = "";		//工作流PermissionCode
    private String objectName = "";                 //名称
    private String objectId = "";                   //id
    private String objectClass = "";                //CSS class
    private String objectOnclick = "";              //点击事件
    private String objectDisabled = "";             //disabled状态
    private String objectValue = "";                //按扭名称
    private String objectVisible = "";              //visible状态
    private String objectRow = "";                  //可编辑列表的row
    private String objectHoldObj = "";              //可编辑列表的holdObj
    private String objectStyle = "";                //style
    private String objectTitle = "";                //title
	private String objectComInvorkeeClassFullName = "";		//设定值所属类绝对路径(服务器端处理)
	
	VisionStatusInfo statusInfo;
	String valueObjectHtml;

	private void objectInit() throws JspException {
		objectRwCtrlType=ExpressionEvaluationUtils.evaluateString("rwCtrlType", rwCtrlType, pageContext);
		objectPermissionCode=ExpressionEvaluationUtils.evaluateString("permissionCode", permissionCode, pageContext);
		objectWfPermissionCode=ExpressionEvaluationUtils.evaluateString("wfPermissionCode", wfPermissionCode, pageContext);
        objectName=ExpressionEvaluationUtils.evaluateString("name", name, pageContext);
        objectId=ExpressionEvaluationUtils.evaluateString("id", id, pageContext);
        objectClass=ExpressionEvaluationUtils.evaluateString("clazz", clazz, pageContext);
        objectOnclick=ExpressionEvaluationUtils.evaluateString("onclick", onclick, pageContext);
        objectDisabled=ExpressionEvaluationUtils.evaluateString("disabled", disabled, pageContext);
        objectValue=ExpressionEvaluationUtils.evaluateString("value", value, pageContext);
        objectVisible=ExpressionEvaluationUtils.evaluateString("visible", visible, pageContext);
        objectRow=ExpressionEvaluationUtils.evaluateString("row", row, pageContext);
        objectHoldObj=ExpressionEvaluationUtils.evaluateString("holdObj", holdObj, pageContext);
        objectStyle=ExpressionEvaluationUtils.evaluateString("style", style, pageContext);
        objectTitle=ExpressionEvaluationUtils.evaluateString("title", title, pageContext);
		objectComInvorkeeClassFullName=ExpressionEvaluationUtils.evaluateString("comInvorkeeClassFullName", comInvorkeeClassFullName, pageContext);
	}
	
	/**
	 * 生成标签主函数
	 */
	public int doStartTag() throws JspException {
		try {
			//实例化相关参数
			StringBuffer html = new StringBuffer();
//			valueObjectHtml = getValueObjectHtml();
			objectInit();
            ITagSecurityPolicy invorkee = (ITagSecurityPolicy) Class.forName(objectComInvorkeeClassFullName).newInstance();
            statusInfo = invorkee.compomentPermission(this.objectRwCtrlType,this.objectPermissionCode,this.objectWfPermissionCode,pageContext);
            getPage(html);
            
            //查看页
            if (statusInfo.getPageType() == ITagSecurityPolicy.VIEWPAGE || objectVisible.equals("false")) {
                html.append(" style=\"visibility:hidden;" + this.objectStyle + "\" />");
//                if (statusInfo.getVisiableStatus() == ITagSecurityPolicy.VISIBLE) {
//                } else if (statusInfo.getVisiableStatus() == ITagSecurityPolicy.UNVISIBLE) {
//                } else {
//                    throw new JspException("");
//                }
            }
            //或编辑页
            else if (statusInfo.getPageType() == ITagSecurityPolicy.EDITPAGE || objectVisible.equals("true")) {
                html.append(" style=\"" + this.objectStyle + "\" />");
//                if (statusInfo.getVisiableStatus() == ITagSecurityPolicy.UNVISIBLE) {
//                } else if (statusInfo.getVisiableStatus() == ITagSecurityPolicy.VISIBLE) {
//                }
            } else {
                throw new JspException("");
            }
            pageContext.getOut().print(html.toString());
        } catch (Exception e) {
            throw new JspTagException("SimpleTag: " + e.getMessage());
        }
        return SKIP_BODY;

	}
	
	/**
	 * 得到可编辑状态tag
	 * @param html
	 */
	public void getPage(StringBuffer html){
		try {
            
            html.append("<input type=\"button\" value=\"" + this.objectValue + "\" " + "class=\"" + this.objectClass + "\" ");
            this.getId(html);
            this.getName(html);
            this.getOnclick(html);
            this.getDisabled(html);
            this.getRow(html);
            this.getHoldObj(html);
            this.getTitle(html);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * id
	 * @param html
	 */
	private void getId(StringBuffer html){
		if(!this.objectId.equals("")){
			html.append(" id=\""+ this.objectId +"\" ");
		}
	}

    /**
     * name
     * @param html
     */
    private void getName(StringBuffer html){
        if(!this.objectName.equals("")){
            html.append(" name=\""+ this.objectName +"\" ");
        }
    }

	/**
	 * 按钮点击事件
	 * @param html
	 */
	private void getOnclick(StringBuffer html){
		if(!this.objectOnclick.equals("")){
			html.append(" onclick=\"" + this.objectOnclick + "\" ");
		}
	}
	
    /**
     * row
     * @param html
     */
    private void getRow(StringBuffer html){
        if(!this.objectRow.equals("")){
            html.append(" row=\"" + this.objectRow + "\" ");
        }
    }
    
    /**
     * holdObj
     * @param html
     */
    private void getHoldObj(StringBuffer html){
        if(!this.objectHoldObj.equals("")){
            html.append(" holdObj=\"" + this.objectHoldObj + "\" ");
        }
    }
    
    /**
     * title
     * @param html
     */
    private void getTitle(StringBuffer html){
        if(!this.objectTitle.equals("")){
            html.append(" title=\"" + this.objectTitle + "\" ");
        }
    }
    
    /**
     * 按钮不可编辑状态
     * @param html
     */
    private void getDisabled(StringBuffer html){
        if (objectDisabled.equals("false")) {
            
        } else if (statusInfo.getEditableStatus() == ITagSecurityPolicy.UNEDITABLE || objectDisabled.equals("true")) {
            html.append(" disabled ");
        }
    }

	public String getRwCtrlType() {
		return rwCtrlType;
	}

	public void setRwCtrlType(String rwCtrlType) {
		this.rwCtrlType = rwCtrlType;
	}

	public String getPermissionCode() {
		return permissionCode;
	}

	public void setPermissionCode(String permissionCode) {
		this.permissionCode = permissionCode;
	}

	public String getComInvorkeeClassFullName() {
		return comInvorkeeClassFullName;
	}

	public void setComInvorkeeClassFullName(String comInvorkeeClassFullName) {
		this.comInvorkeeClassFullName = comInvorkeeClassFullName;
	}

	public String getWfPermissionCode() {
		return wfPermissionCode;
	}

	public void setWfPermissionCode(String wfPermissionCode) {
		this.wfPermissionCode = wfPermissionCode;
	}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getObjectClass() {
        return objectClass;
    }

    public void setObjectClass(String objectClass) {
        this.objectClass = objectClass;
    }

    public String getObjectComInvorkeeClassFullName() {
        return objectComInvorkeeClassFullName;
    }

    public void setObjectComInvorkeeClassFullName(
            String objectComInvorkeeClassFullName) {
        this.objectComInvorkeeClassFullName = objectComInvorkeeClassFullName;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public String getObjectOnclick() {
        return objectOnclick;
    }

    public void setObjectOnclick(String objectOnclick) {
        this.objectOnclick = objectOnclick;
    }

    public String getObjectPermissionCode() {
        return objectPermissionCode;
    }

    public void setObjectPermissionCode(String objectPermissionCode) {
        this.objectPermissionCode = objectPermissionCode;
    }

    public String getObjectRwCtrlType() {
        return objectRwCtrlType;
    }

    public void setObjectRwCtrlType(String objectRwCtrlType) {
        this.objectRwCtrlType = objectRwCtrlType;
    }

    public String getObjectValue() {
        return objectValue;
    }

    public void setObjectValue(String objectValue) {
        this.objectValue = objectValue;
    }

    public String getObjectVisible() {
        return objectVisible;
    }

    public void setObjectVisible(String objectVisible) {
        this.objectVisible = objectVisible;
    }

    public String getObjectWfPermissionCode() {
        return objectWfPermissionCode;
    }

    public void setObjectWfPermissionCode(String objectWfPermissionCode) {
        this.objectWfPermissionCode = objectWfPermissionCode;
    }

    public String getOnclick() {
        return onclick;
    }

    public void setOnclick(String onclick) {
        this.onclick = onclick;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getVisible() {
        return visible;
    }

    public void setVisible(String visible) {
        this.visible = visible;
    }

    public String getDisabled() {
        return disabled;
    }

    public void setDisabled(String disabled) {
        this.disabled = disabled;
    }

    public String getObjectDisabled() {
        return objectDisabled;
    }

    public void setObjectDisabled(String objectDisabled) {
        this.objectDisabled = objectDisabled;
    }

    public String getHoldObj() {
        return holdObj;
    }

    public void setHoldObj(String holdObj) {
        this.holdObj = holdObj;
    }

    public String getObjectHoldObj() {
        return objectHoldObj;
    }

    public void setObjectHoldObj(String objectHoldObj) {
        this.objectHoldObj = objectHoldObj;
    }

    public String getObjectRow() {
        return objectRow;
    }

    public void setObjectRow(String objectRow) {
        this.objectRow = objectRow;
    }

    public String getObjectStyle() {
        return objectStyle;
    }

    public void setObjectStyle(String objectStyle) {
        this.objectStyle = objectStyle;
    }

    public String getObjectTitle() {
        return objectTitle;
    }

    public void setObjectTitle(String objectTitle) {
        this.objectTitle = objectTitle;
    }

    public String getRow() {
        return row;
    }

    public void setRow(String row) {
        this.row = row;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
	
}
