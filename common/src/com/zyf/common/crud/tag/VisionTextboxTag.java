/**
 * 沈阳卓越科技有限公司版权所有
 * 制作时间：Oct 25, 20078:34:26 PM
 * 文件名：TextboxTag.java
 * 制作者：wuym
 * 
 */
package com.zyf.common.crud.tag;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.zyf.utils.DateUtils;

import javax.servlet.jsp.tagext.TagSupport;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.util.ExpressionEvaluationUtils;
import org.springframework.web.util.HtmlUtils;

/**
 * @author wuym
 * 
 */
public class VisionTextboxTag extends TagSupport {
	private String rwCtrlType = "";					//rwCtrlType
	private String permissionCode = "";				//permissionCode
	private String wfPermissionCode = "";			//工作流PermissionCode
	private String id = "";							//文本ID
	private String name = "";						//文本框名
	private String value = "";						//文本框值
	private String bisname = "";					//效验提示名称
	private String maxlength = "";					//文本框最大输入长度
	private String style = "";						//style属性值
	private String clazz = "";						//class属性值
	private String readonly = "";					//只读
	private String required = "false";				//是否为必填项*
	private String mustInputHintName = "";			//为必填项* 提示
	private String mustInputHintAction = "";		//为必填项* 事件
	private String dataType = "1";					//0数字/1字符/2日期框/3日期加时间框
	private String haveDateButton = "true";			//只读状态下是否显示日期button,true:显示,false:不显示,默认true
	private String link = "";						//是否为链接
	private String longTextType = "false";			//是否为长文本框
	private String rows = "1";						//文本域行数
	private String signatureButtonAction = "";		//签字按钮js函数
	private String selButtonAction = "";			//查看按钮js函数
	private String canDelete = "false";				//橡皮擦
	private String deleteAction = "";				//橡皮擦事件
	private String onchange = "";					//文本值改变事件
	private String searchButtonAction = "";			//搜索按钮js函数
	private String comInvorkeeClassFullName = "";	//设定值所属类绝对路径(服务器端处理)

	private String objectRwCtrlType = "";					//rwCtrlType
	private String objectPermissionCode = "";				//permissionCode
	private String objectWfPermissionCode = "";				//工作流PermissionCode
	private String objectId = "";							//文本ID
	private String objectName = "";							//文本框对象生成名
	private String objectValue = "";						//文本框对象生成值
	private String objectBisname = "";						//效验提示名称
	private String objectMaxlength = "";					//文本框最大输入长度
	private String objectStyle = "";						//style属性值
	private String objectClazz = "";						//class属性值
	private String objectReadonly = "";						//只读
	private String objectRequired = "false";				//是否为必填项*
	private String objectMustInputHintName = "";			//为必填项* 提示
	private String objectMustInputHintAction = "";			//为必填项* 事件
	private String objectDataType = "1";					//数字/字符/日期框
	private String objectHaveDateButton = "true";			//只读状态下是否显示日期button,true:显示,false:不显示,默认true
	private String objectLink = "";							//是否为链接
	private String objectLongTextType = "false";			//是否为长文本框
	private String objectRows = "1";						//文本域行数
	private String objectSignatureButtonAction = "";		//签字按钮js函数
	private String objectSelButtonAction = "";				//查看按钮js函数
	private String objectCanDelete = "false";				//橡皮擦
	private String objectDeleteAction = "";					//橡皮擦事件
	private String objectOnchange = "";						//文本值改变事件
	private String objectSearchButtonAction = "";			//搜索按钮js函数
	private String objectComInvorkeeClassFullName = "";		//设定值所属类绝对路径(服务器端处理)

	VisionStatusInfo statusInfo;
	boolean wfRequried = false;

	/**
	 * 初始化解析所有传入值对象值
	 * @throws JspException
	 */
	private void objectInit() throws JspException {
		objectRwCtrlType=ExpressionEvaluationUtils.evaluateString("rwCtrlType", rwCtrlType, pageContext);
		objectPermissionCode=ExpressionEvaluationUtils.evaluateString("permissionCode", permissionCode, pageContext);
		objectWfPermissionCode=ExpressionEvaluationUtils.evaluateString("wfPermissionCode", wfPermissionCode, pageContext);
		objectId=ExpressionEvaluationUtils.evaluateString("id", id, pageContext);
		objectName=ExpressionEvaluationUtils.evaluateString("name", name, pageContext);
		try{
			Object objectDoubleValue = ExpressionEvaluationUtils.evaluate("value", value, pageContext);
			if(Double.class.isAssignableFrom(ExpressionEvaluationUtils.evaluate("value", value, pageContext).getClass())){
				objectValue=(String)(new DecimalFormat("#.#").format(objectDoubleValue));
			}
			else{
				objectValue=ExpressionEvaluationUtils.evaluateString("value", value, pageContext);
			}
		}catch(Exception e){objectValue="";}
		objectBisname=ExpressionEvaluationUtils.evaluateString("bisname", bisname, pageContext);
		objectMaxlength=ExpressionEvaluationUtils.evaluateString("maxlength", maxlength, pageContext);
		objectStyle=ExpressionEvaluationUtils.evaluateString("style", style, pageContext);
		objectClazz=ExpressionEvaluationUtils.evaluateString("clazz", clazz, pageContext);
		objectReadonly=ExpressionEvaluationUtils.evaluateString("readonly", readonly, pageContext);
		objectRequired=ExpressionEvaluationUtils.evaluateString("required", required, pageContext);
		objectMustInputHintName=ExpressionEvaluationUtils.evaluateString("mustInputHintName", mustInputHintName, pageContext);
		objectMustInputHintAction=ExpressionEvaluationUtils.evaluateString("mustInputHintAction", mustInputHintAction, pageContext);
		objectDataType=ExpressionEvaluationUtils.evaluateString("dataType", dataType, pageContext);
		objectHaveDateButton=ExpressionEvaluationUtils.evaluateString("haveDateButton", haveDateButton, pageContext);
		objectLink=ExpressionEvaluationUtils.evaluateString("link", link, pageContext);
		objectLongTextType=ExpressionEvaluationUtils.evaluateString("longTextType", longTextType, pageContext);
		objectRows=ExpressionEvaluationUtils.evaluateString("rows", rows, pageContext);
		objectSignatureButtonAction=ExpressionEvaluationUtils.evaluateString("signatureButtonAction", signatureButtonAction, pageContext);
		objectSelButtonAction=ExpressionEvaluationUtils.evaluateString("selButtonAction", selButtonAction, pageContext);
		objectCanDelete=ExpressionEvaluationUtils.evaluateString("canDelete", canDelete, pageContext);
		objectDeleteAction=ExpressionEvaluationUtils.evaluateString("deleteAction", deleteAction, pageContext);
		objectOnchange=ExpressionEvaluationUtils.evaluateString("onchange", onchange, pageContext);
		objectSearchButtonAction=ExpressionEvaluationUtils.evaluateString("searchButtonAction", searchButtonAction, pageContext);
		objectComInvorkeeClassFullName=ExpressionEvaluationUtils.evaluateString("comInvorkeeClassFullName", comInvorkeeClassFullName, pageContext);
		

		try{
			if(Date.class.isAssignableFrom(ExpressionEvaluationUtils.evaluate("value", value, pageContext).getClass())){
				this.objectValue = getTextDateOrDatetimeValue();
			}
		}catch(Exception e){}

		//日期框值需格式化
		if (objectDataType.equals("2") || objectDataType.equals("3") || objectDataType.equals("4")) {
			this.objectValue = getDateOrDatetimeValue();
		}
		
		//转义html字符
		this.objectValue = this.objectValue.replaceAll("&", "&amp;");
		this.objectValue = this.objectValue.replaceAll("<", "&lt;");
		this.objectValue = this.objectValue.replaceAll(">", "&gt;");
		//this.objectValue = this.objectValue.replaceAll("'", "&apos;");
		this.objectValue = this.objectValue.replaceAll("\"", "&quot;");
	}
	
	/**
	 * 生成标签主函数
	 */
	public int doStartTag() throws JspException {
		try {
			//实例化相关参数
			StringBuffer html = new StringBuffer();
			objectInit();
			ITagSecurityPolicy invorkee = (ITagSecurityPolicy) Class.forName(objectComInvorkeeClassFullName).newInstance();
			statusInfo = invorkee.compomentPermission(this.objectRwCtrlType,this.objectPermissionCode,this.objectWfPermissionCode,pageContext);
			wfRequried = invorkee.workFlowIsNeedData(this.objectWfPermissionCode);
			//查看页
			if (statusInfo.getPageType() == ITagSecurityPolicy.VIEWPAGE) {
				//是否可见
				if (statusInfo.getVisiableStatus() == ITagSecurityPolicy.VISIBLE) {
					if/*(this.objectLongTextType.equals("true")){
						objectReadonly = "true";
						objectSignatureButtonAction = "";
						objectSelButtonAction = "";
						objectCanDelete = "false";
						objectOnchange = "";
						objectSearchButtonAction = "";
						getLongText(html);
					}
					else if*/(objectSignatureButtonAction.trim().length() > 0 || objectDataType == "7"){
						html.append("<u>");
						html.append(objectValue);
						html.append("</u>");
					}
					else if(!this.objectLink.equals("")){
						html.append("<a href=\""+ this.objectLink +"\" style=\""+ this.objectStyle +"\" class=\""+ this.objectClazz +"\">");
						html.append(objectValue);
						html.append("</a>");
					}else{
						html.append(objectValue+" &nbsp;");
					}
				} else if (statusInfo.getVisiableStatus() == ITagSecurityPolicy.UNVISIBLE) {
					html.append(" &nbsp;");
				} else {
					throw new JspException("");
				}
			}
			//或编辑页
			else if (statusInfo.getPageType() == ITagSecurityPolicy.EDITPAGE) {
				//是否可见
				if (statusInfo.getVisiableStatus() == ITagSecurityPolicy.UNVISIBLE) {
					html.append("-");
				} else if (statusInfo.getVisiableStatus() == ITagSecurityPolicy.VISIBLE) {
					//是否有隐含域
//					if (!dateType && ((selButtonAction != null && selButtonAction.trim().length() > 0)
//						|| (searchButtonAction != null && searchButtonAction.trim().length() > 0))){
//						getHiddenFieldInit(html);
//						html.append(getHiddenField());
//					}else{
						getHiddenInit(html);
//					}
					
						//是否为长文本框或文件选择框
					if (this.objectLongTextType.equals("true")) {
						getLongText(html);
					}
					else if(objectDataType == "7"){
						if(objectValue.equals("")){
							html.append("<input style=\"background:;text-decoration:none;BORDER: 0px; WIDTH: 0px;\" type=\"text\" name=\""+objectName+"\" id=\""+objectId+"\" value=\""+objectValue+"\" readonly/>");
						}else{
							html.append("<u>");
							html.append(objectValue);
							html.append("</u>");
						}
					}
					else {
						getTextbox(html);
					}
				}
			} else {
				throw new JspException("");
			}
			pageContext.getOut().print(html.toString());
		} catch (Exception e) {
			throw new JspTagException("SimpleTag: " + e.getMessage());
		}
		return SKIP_BODY;
	}

	public int doEndTag() {
		return EVAL_PAGE;
	}
	
	/**
	 * 得到长文本框
	 * @param html
	 */
	private void getLongText(StringBuffer html){
		//加入保存临时值DIV
		if(!objectOnchange.equals("")){
			html.append("<div value=\""+ objectValue +"\" id=\""+ objectName +"TempValueDivId\"/>");
		}
		html.append("<textarea id=\"textArea\"" +
				" name=\""+ this.objectName +"\"");
		
		//文本域行数
		html.append(" rows=\""+ this.objectRows +"\"");

		//工作流表单编辑项必须入力属性
		getWfRequried(html);
		
		//文本域最大输入数
		if(!this.objectMaxlength.equals("")){
			if(!this.objectBisname.equals("")){
				//去除输入限制
//				html.append(" onkeydown=\"this.value = this.value.substring(0,"+ this.objectMaxlength +");\"");
//			}else{
				html.append(" onkeydown=\"if(this.value.length > "+ this.objectMaxlength +
						"){Validator.clearValidateInfo();Validator.warnMessage('"+ this.objectBisname +"长度不能大于"+ this.objectMaxlength +"');}\"");
			}
		}

		//自定义属性bisname提示信息名称
		getBisname(html);
		
		//最大长度提示
		isMaxlengthHint(html);

		//必输项提示
		if (objectRequired.equals("true")) {
			html.append(" bisRequired=\"true\"");
		}
		
		//文本为数字框或长文本框
		if(objectDataType.equals("0")){
			html.append(" class=\"font_money\"");
		}else{
			html.append(" class=\"input_long\"");
		}
		getOnchange(html,this.objectName);
		
		//长文本框无滚动条
		if(this.objectDataType.equals("6")){
			html.append(" style=\"overflow:hidden\"");
		}else if(!objectStyle.equals("")){
			html.append(" style=\""+ this.objectStyle +"\"");
		}
		
		//getOnchange(html);
		if(objectReadonly.equals("true")){
			html.append(getDisableAttrStr(statusInfo));
		}
		else if (statusInfo.getEditableStatus() == ITagSecurityPolicy.UNEDITABLE) {
			html.append(" readonly");
		}
		html.append(">" + this.objectValue + "</textarea>");
		isRequired(html);

		if(!this.objectDataType.equals("8")){
			remarkButton(html);
		}
	}
	
	/**
	 * 得到文本框
	 * @param html
	 */
	private void getTextbox(StringBuffer html){
		//加入保存临时值DIV
		if(!objectOnchange.equals("")){
			html.append("<div value=\""+ objectValue +"\" id=\""+ objectName +"TempValueDivId\"/>");
		}

		//是否为文件选择类型
		if(this.objectDataType.equals("5")){
			html.append("<input type=\"file\"");
		}else{
			html.append("<input type=\"text\"");
		}
		
		//工作流表单编辑项必须入力属性
		getWfRequried(html);
		
		html.append(" name=\"" + this.objectName + "\"");
		html.append(" value=\"" + this.objectValue + "\"");
		
		//长文本框样式
		if(objectDataType.equals("6")){
			html.append(" class=\"readonly\"");
		}
		
		//文本为数字框
		if(objectDataType.equals("0")){
			html.append(" class=\"font_money\"");
		}
		
		//自定义属性bisname提示信息名称
		getBisname(html);
		
		//最大长度提示
		isMaxlengthHint(html);

		//必输项提示
		if (objectRequired.equals("true")) {
			html.append(" bisRequired=\"true\"");
		}
		
		//文本框最大输入数
//		if(!this.objectMaxlength.equals("")){
//			html.append(" maxlength=\""+ this.objectMaxlength +"\"");
//		}
		
		getOnchange(html,this.objectName);
		
		if (objectDataType.equals("2") || objectDataType.equals("3") || objectDataType.equals("4")) {
			html.append(" id=\"input_text\"");
		}
//		if ((selButtonAction != null && selButtonAction.trim().length() > 0)
//				|| (searchButtonAction != null && searchButtonAction.trim().length() > 0)) {
//			html.append(" id=\"" + this.id + "\"");
//		}
		if (!this.objectId.equals("")) {
			html.append(" id=\"" + this.objectId + "\"");
		}
		if (objectStyle != null && objectStyle.trim().length() > 0) {
			html.append(" style=\"" + this.objectStyle + "\"");
		}
		String disableAttrStr = getDisableAttrStr(statusInfo);
		if (disableAttrStr.equals("")) {
			if (objectDataType.equals("2") || objectDataType.equals("3") || objectDataType.equals("4")){
				html.append(" class=\"input_text\"");
			}
			else if (objectClazz != null && objectClazz.trim().length() > 0) {
				html.append(" class=\"" + this.objectClazz + "\"");
			} else if(objectDataType.equals("0")){
				html.append(" class=\"font_money\"");
			}
		} else {
			html.append(disableAttrStr);
		}
		html.append("/>");
		//可见状态
		if (statusInfo.getVisiableStatus() == ITagSecurityPolicy.VISIBLE) {
			//日期框
			if (!(objectHaveDateButton.equals("false") && objectReadonly.equals("true")) && 
					(objectDataType.equals("2") || objectDataType.equals("3") || objectDataType.equals("4"))) {
				html.append("<input id=\"input_date\" type=\"button\" name=\"button\"");
				getDisabled(html);
				html.append(" onclick=\""
								+ "DateComponent.setDay(this,document.getElementsByName('"
								+ this.objectName + "')[0]"+ 
								(objectDataType.equals("2")?"":(objectDataType.equals("3")?",''":",document.getElementsByName('"
								+ this.objectName + "')[0]")) +")"
								+ "\"/>\n");
			}
			isRequired(html);

			if(this.objectDataType.equals("6")){
				remarkButton(html);
			}

			//签字按钮
			if(statusInfo.getEditableStatus() != ITagSecurityPolicy.UNEDITABLE){
				if (objectSignatureButtonAction.trim().length() > 0) {
					html.append("<input id=\"signature_query\" type=\"button\" name=\"signaturebutton_"+this.objectName+"\"");
					getDisabled(html);
					html.append(" onclick=\"" + objectSignatureButtonAction
							+ "\"/>\n");
				}
			}
			
			//日历无树形选择按钮
			if (!(objectDataType.equals("2") || objectDataType.equals("3") || objectDataType.equals("4"))) {
				if (objectSelButtonAction.trim().length() > 0) {
					html.append("<input id=\"select_fromtree\" type=\"button\" name=\"selbutton_"+this.objectName+"\"");
					getDisabled(html);
					html.append(" onclick=\"" + objectSelButtonAction
							+ "\"/>\n");
				}
			}
			
			//日历无搜索按钮
			if (!(objectDataType.equals("2") || objectDataType.equals("3") || objectDataType.equals("4"))) {
				if (objectSearchButtonAction.trim().length() > 0) {
					html.append("<input id=\"edit_query\" type=\"button\" name=\"edibutton_"+this.objectName+"\"");
					getDisabled(html);
					html.append(" onclick=\"" + objectSearchButtonAction
							+ "\"/>\n");
				}
			}

			//是否有橡皮擦
			if (objectCanDelete.equals("true") && ((objectSelButtonAction.trim().length() > 0)
					|| (objectSearchButtonAction.trim().length() > 0))
					|| objectCanDelete.equals("true") && (objectDataType.equals("2") || objectDataType.equals("3") || objectDataType.equals("4"))) {
				html.append("<input id=\"opera_clear\" type=\"button\" name=\"delbutton_"+this.objectName+"\" title=\"点击清除\"");
				getDisabled(html);
				
				if(!this.objectDeleteAction.equals("")){
					//手动实现橡皮擦事件
					html.append(" onclick=\"" + this.objectDeleteAction + "\"");
				}else{
					html.append(" onclick=\"FormUtils.cleanValues($('" + this.objectName + "'));\"");
				}
				
				html.append("/>\n");
			}
			
		}
	}

	/**
	 * 隐藏域,用于保存初始值
	 * @param html
	 */
	private void getHiddenInit(StringBuffer html){
		html.append("<input type=\"hidden\" id=\"hiddenValueDefault"+ this.objectId +"\"" +
				" name=\"hiddenValueDefault"+ this.objectName +"\"" +
				" value=\"" + this.objectValue + "\"/>\n");
	}

	/**
	 * 文本域值改变事件
	 * @param html
	 */
	private void getOnchange(StringBuffer html,String evaledTextName){
		if(!this.objectOnchange.equals("")){
			html.append(" onpropertychange=\"if(!(this.value==document.getElementById('"+ evaledTextName +"TempValueDivId').value)){" + this.objectOnchange + "" +
					"document.getElementById('"+ evaledTextName +"TempValueDivId').value=this.value;}\"");
		}
	}

	//自定义属性bisname提示信息名称,必输入项提示
	private void getBisname(StringBuffer html){
		if(!this.objectBisname.equals("")){
			html.append(" bisname=\""+ this.objectBisname +"\"");
		}
	}

	//必输项
	private void isRequired(StringBuffer html){
		if (objectRequired.equals("true")) {
			html.append(" <span class=\"font_request\">*</span>\n");
		}
	}

	//最大长度提示信息
	private void isMaxlengthHint(StringBuffer html){
		if (!objectMaxlength.equals("")) {
			html.append(" maxlength=\""+ this.objectMaxlength +"\"");
		}
	}

	/**
	 * 工作流表单编辑项必须入力属性
	 * @param html
	 */
	private void getWfRequried(StringBuffer html){
		if(wfRequried){
			html.append(" wfRequried=\"true\"");
		}
	}
	
	/**
	 * 文本域值改变事件
	 * @param html
	 */
//	private void getOnchange(StringBuffer html){
//		if(!this.getOnchange().equals("")){
//			html.append(" onpropertychange=\"" + this.onchange + "\"");
//		}
//	}

	/**
	 * 结果隐藏域,保存KEY值
	 * @param html
	 */
//	private void getHiddenFieldInit(StringBuffer html){
//		html.append("<input type=\"hidden\" id=\""+ this.hiddenFieldId +"HiddenValueDefault\"" +
//				" name=\""+ this.hiddenFieldName +"HiddenValueDefault\"" +
//				" value=\"" + this.hiddenFieldValue + "\"/>\n");
//	}
	
	/**
	 * 功能按钮不可用状态
	 * @param html
	 */
	private void getDisabled(StringBuffer html){
		if (statusInfo.getEditableStatus() == ITagSecurityPolicy.UNEDITABLE || objectReadonly.equals("true")) {
			html.append(" disabled");
		}
	}
	
	/**
	 * 根据是否有按钮加入隐含域
	 * @return
	 */
	private String getHiddenField() {
//			return "<input id=\"" + this.hiddenFieldId
//					+ "\" type=\"hidden\" name=\"" + this.hiddenFieldName
//					+ "\" value=\"" + this.hiddenFieldValue + "\" readonly/>\n";
		return "";
	}

	//只读状态
	private String getDisableAttrStr(VisionStatusInfo comStatusInfo) {
		if ((comStatusInfo.getEditableStatus() == ITagSecurityPolicy.UNEDITABLE)
				|| (objectDataType.equals("2") || objectDataType.equals("3") || objectDataType.equals("4"))
				|| objectSignatureButtonAction.trim().length() > 0
				|| (objectSelButtonAction.trim().length() > 0)
				//|| (canDelete)
				|| (objectSearchButtonAction.trim().length() > 0)
				|| objectReadonly.equals("true")) {
			if (objectDataType.equals("2") 
					|| objectDataType.equals("3") || objectDataType.equals("4") || objectDataType.equals("0")
					|| this.objectDataType.equals("6")) {
				return " readonly";
			} else {
				return " class=\"readonly\" readonly";
			}
		}
		return "";
	}

	//长文本编辑框弹出备注按钮
	private void remarkButton(StringBuffer html){

		html.append("<span title=''>");
		html.append("<input type=\"button\"" +
				" id=\"edit_longText\"" +
				" onClick=\"definedWin.openLongTextWin(document.getElementsByName('"+ this.objectName +"')[0],'',");
		if (statusInfo.getEditableStatus() == ITagSecurityPolicy.UNEDITABLE || objectReadonly.equals("true")) {
			html.append("true");
		}else{
			html.append("false");
		}
		//去除长度输入限制
//		if(!this.objectMaxlength.equals("")){
//			html.append(","+this.objectMaxlength);
//		}
		html.append(");\" /></span>");
	}

	//得到纯日期/日期加时间/纯时间
	private String getDateOrDatetimeValue(){
		try{
			if(objectDataType.equals("2")){
				return getDate(objectValue);
			}
			else if(objectDataType.equals("3")) {
				return getDatetime(objectValue);
			}
			else if(objectDataType.equals("4")) {
				return getTime(objectValue);
			}else {
				return objectValue;
			}
		}catch(Exception pe){
			return objectValue;
		}
	}
	//得到纯日期/日期加时间/纯时间
	private String getTextDateOrDatetimeValue(){
		try{
			if(objectDataType.equals("9")){
				return getDate(objectValue);
			}
			else if(objectDataType.equals("10")) {
				return getDatetime(objectValue);
			}
			else if(objectDataType.equals("11")){
				return getTime(objectValue);
			}else{
				return objectValue;
			}
		}catch(Exception pe){
			return objectValue;
		}
	}
	//得到纯日期类型
	private String getDate(String datetime) throws Exception{
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(DateUtils.parse(datetime));
//		return datetime.equals("")?"":(datetime.substring(0,datetime.length()<10?9:datetime.length()-1));
	}

	//得到日期加时间类型
	private String getDatetime(String datetime) throws Exception{
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(DateUtils.parse(datetime));
	}

	//得到纯时间类型
	private String getTime(String datetime) throws Exception{
		SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:ss");
		return sdf.format(DateUtils.parse(datetime));
	}

	protected String evaluateAndEscapeHtml(
		String attributeName, 
		String attributeValue, 
		PageContext pageContext) 
		throws JspException {
		String escapedHtml = HtmlUtils.htmlEscape(ExpressionEvaluationUtils.evaluateString(attributeName, attributeValue, pageContext));
		return StringUtils.trimToEmpty(escapedHtml);
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

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getClazz() {
		return clazz;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	public String getRequired() {
		return required;
	}

	public void setRequired(String required) {
		this.required = required;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getLongTextType() {
		return longTextType;
	}

	public void setLongTextType(String longTextType) {
		this.longTextType = longTextType;
	}

	public String getSelButtonAction() {
		return selButtonAction;
	}

	public void setSelButtonAction(String selButtonAction) {
		this.selButtonAction = selButtonAction;
	}

	public String getCanDelete() {
		return canDelete;
	}

	public void setCanDelete(String canDelete) {
		this.canDelete = canDelete;
	}

	public String getOnchange() {
		return onchange;
	}

	public void setOnchange(String onchange) {
		this.onchange = onchange;
	}

	public String getSearchButtonAction() {
		return searchButtonAction;
	}

	public void setSearchButtonAction(String searchButtonAction) {
		this.searchButtonAction = searchButtonAction;
	}

	public String getComInvorkeeClassFullName() {
		return comInvorkeeClassFullName;
	}

	public void setComInvorkeeClassFullName(String comInvorkeeClassFullName) {
		this.comInvorkeeClassFullName = comInvorkeeClassFullName;
	}

	public String getRows() {
		return rows;
	}

	public void setRows(String rows) {
		this.rows = rows;
	}

	public String getMaxlength() {
		return maxlength;
	}

	public void setMaxlength(String maxlength) {
		this.maxlength = maxlength;
	}

	public String getReadonly() {
		return readonly;
	}

	public void setReadonly(String readonly) {
		this.readonly = readonly;
	}

	public String getHaveDateButton() {
		return haveDateButton;
	}

	public void setHaveDateButton(String haveDateButton) {
		this.haveDateButton = haveDateButton;
	}

	public String getDeleteAction() {
		return deleteAction;
	}

	public void setDeleteAction(String deleteAction) {
		this.deleteAction = deleteAction;
	}

	public String getMustInputHintAction() {
		return mustInputHintAction;
	}

	public void setMustInputHintAction(String mustInputHintAction) {
		this.mustInputHintAction = mustInputHintAction;
	}

	public String getMustInputHintName() {
		return mustInputHintName;
	}

	public void setMustInputHintName(String mustInputHintName) {
		this.mustInputHintName = mustInputHintName;
	}

	public String getBisname() {
		return bisname;
	}

	public void setBisname(String bisname) {
		this.bisname = bisname;
	}

	public String getSignatureButtonAction() {
		return signatureButtonAction;
	}

	public void setSignatureButtonAction(String signatureButtonAction) {
		this.signatureButtonAction = signatureButtonAction;
	}

	public String getWfPermissionCode() {
		return wfPermissionCode;
	}

	public void setWfPermissionCode(String wfPermissionCode) {
		this.wfPermissionCode = wfPermissionCode;
	}

}
