/**
 * 
 * ����ʱ�䣺Nov 5, 200711:39:17 AM
 * �ļ�����InfoMeta.java
 * �����ߣ�zhaoyf
 * 
 */
package base.zyf.tools.auto.create.info;

import java.text.MessageFormat;

import org.apache.commons.lang.StringUtils;

/**
 * @author zhaoyf
 *
 */
public class InfoMeta {

	private static String enter="\r\n";
	private static String tab="\t";
	private static StringBuffer td=new StringBuffer();
	/**
	 * 
	 * {0}->viewClass
	 * {1}->inputClass
	 * {2}->disPlayName
	 * {3}->tag
	 */
	static
	{
		td.append(tab+tab);
		td.append("<td {0}>{2}</td>");
		td.append(enter);
		td.append(tab+tab);
		td.append("<td {1}>");
		td.append(enter);
		td.append("{3}");
		td.append(enter);
		td.append(tab+tab);
		td.append("</td>");
		td.append(enter);
	}
	
	
	public static final String dataType="document.getElementById(''{0}'').dataType = ''{1}'';";
	public static final String msg="document.getElementById(''{0}'').msg = ''{2}'';	";
	
	public static final String must="<span class=\"font_request\">*</span>";
	
	/**
	 * {0}->name
	 * {1}->valueDefault 
	 * {2}->style
	 * {3}->must
	 */
	public static final String INFO_TEXT="<input name=\"{0}\" value=\"<c:out value=''$'{'{1}.{0}}''/>\" {2}/> {3}";
	/**
	 * {0}->name
	 * {1}->valueDefault 
	 * {2}->selectSubSysCode
	 * {3}->selectModuleCode
	 * {4}->must
	 */
	public static final String INFO_SELECT="<ec:composite value=\"$'{'{1}.{0}}\" textName = \"temp.{0}\" valueName = \"{0}\" source = \"$'{'{1}.sysCodes[\"{2}\"][\"{3}\"]}\" /> {4}";
	/**
	 * {0}->name
	 * {1}->pattern
	 * {2}->valueDefault 
	 * {3}->must
	 */
	public static final String INFO_TIME="<input type=\"text\" value=\"<fmt:formatDate value=''$'{'{2}.{0}}'' pattern = ''{1}'' />\" id=\"input_text\" name=\"{0}\" readonly /><input type=\"button\" value=\"\" id=\"input_date\" onClick=\"DateComponent.setDay(this,document.getElementsByName(''{0}'')[0])\" /> {3}";
	/**
	 * {0}->code
	 * {1}->name
	 * {2}->valueDefault 
	 * {3}->must
	 */
	public static final String INFO_DEPT="<input type=\"hidden\" name=\"{0}\" id=\"deptId\" value=\"<c:out value=''$'{'{2}.{0}}''/>\"/>" +
											"<input type=\"text\" name=\"{1}\" id=\"deptName\" value=\"<c:out value=''$'{'{2}.{1}}''/>\"/> {3}" +
											"<input type=\"button\" id=\"select_fromtree\" onClick=\"TreeUtils.selectAndSetDepartment($(''deptId''),$(''deptName''),TreeUtils.treeTypeSingle)\" title=\"�������ѡ�����ڲ���\"/>";
	
	
	public static final String INFO_TYPE_TEXT="text";
	public static final String INFO_TYPE_SELECT="select";
	public static final String INFO_TYPE_TIME="time";
	public static final String INFO_TYPE_DEPT="dept";
	
	/**
	 * ������ʾ��
	 */
	private String displayName;
	
	/**
	 * code��������name
	 */
	private String code;
	/**
	 * ������
	 */
	private String name;
	
	/**
	 * ����
	 * ���磺�ı���������ʱ��ѡ�񣬲���ѡ��
	 */
	private String infoType;
	
	/**
	 * Ĭ��ֵ
	 */
	private String valueDefault;
	
	/**
	 * �������ʽ ����style="text-align:right" 
	 * ���Ǳ����
	 */
	private String style="";
	
	/**
	 * ʱ����ʾ��ʽ ����"yyyy-MM-dd"
	 */
	private String pattern="yyyy-MM-dd";
	
	/**
	 * ��ʾ��ʽ����ʽ
	 */


	private String viewClass=" class=\"attribute\" ";

	private String inputClass="";
	
	/**
	 * �������õ�����ϵͳ����
	 */
	private String selectSubSysCode;
	/**
	 * �������õ���ģ�����
	 */
	private String selectModuleCode;
	
	/**
	 * ��ѯ����ռ�Ŀ�ȣ�Ĭ��1��
	 * ʱ��ؼ�Ĭ��ռ2��
	 */
	private int width=1;
	/**
	 * �Ƿ��������
	 */
	private String isMust;

	/**
	 * ��֤��������"Required|Double"
	 */
	private String valid;
	/**
	 * ��֤��ʾ��Ϣ����"Ա����Ϊ������"
	 */
	private String validMsg;
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	

	public String getInfoType() {
		return infoType;
	}

	public void setInfoType(String infoType) {
		this.infoType = infoType;
	}

	public String getValueDefault() {
		return valueDefault;
	}

	public void setValueDefault(String valueDefault) {
		this.valueDefault = valueDefault;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		if(!StringUtils.isBlank(style))
		this.style = "style=\""+style+"\"";
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public String getViewClass() {
		return viewClass;
	}

	public void setViewClass(String viewClass) {
		this.viewClass = viewClass;
	}

	public String getInputClass() {
		return inputClass;
	}

	public void setInputClass(String inputClass) {
		if(StringUtils.isBlank(inputClass))
			inputClass="";
		this.inputClass = inputClass;
	}

	public String getSelectSubSysCode() {
		return selectSubSysCode;
	}

	public void setSelectSubSysCode(String selectSubSysCode) {
		this.selectSubSysCode = selectSubSysCode;
	}

	public String getSelectModuleCode() {
		return selectModuleCode;
	}

	public void setSelectModuleCode(String selectModuleCode) {
		this.selectModuleCode = selectModuleCode;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public String getIsMust() {
		return isMust;
	}

	public void setIsMust(String isMust) {
		this.isMust = isMust;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	
	public String getTag()
	{
		StringBuffer tag=new StringBuffer();
		String tags="";
		String span="";
		if(isMust.equals("true"))
			span=this.must;
		tag.append(tab+tab+tab);
		if(this.infoType.equals(this.INFO_TYPE_DEPT))
		{
			tag.append(this.INFO_DEPT);
			tags=MessageFormat.format(tag.toString(),new Object[]{code,name,valueDefault,span,"."});
		}
		if(this.infoType.equals(this.INFO_TYPE_SELECT))
		{
			tag.append(this.INFO_SELECT);
			tags=MessageFormat.format(tag.toString(),new Object[]{name,valueDefault,selectSubSysCode,selectModuleCode,span});
		}
		if(this.infoType.equals(this.INFO_TYPE_TEXT))
		{
			tag.append(this.INFO_TEXT);
			tags=MessageFormat.format(tag.toString(),new Object[]{name,valueDefault,style,span});
		}
		if(this.infoType.equals(this.INFO_TYPE_TIME))
		{
			tag.append(this.INFO_TIME);
			tags=MessageFormat.format(tag.toString(),new Object[]{name,pattern,valueDefault,span});
		}
		
		return MessageFormat.format(td.toString(), new Object[]{this.viewClass,this.inputClass,this.displayName,tags});
	}
	public String validate()
	{
		if(StringUtils.isBlank(this.valid))
			return "";
		StringBuffer validate=new StringBuffer();
		validate.append(this.dataType);
		validate.append(enter);
		validate.append(this.msg);
		validate.append(enter);
		String validName;
		if(StringUtils.isBlank(code))
			validName=name;
		else
			validName=code;
		return MessageFormat.format(validate.toString(),new Object[]{validName,valid,validMsg});
	}

	public String getValid() {
		return valid;
	}

	public void setValid(String valid) {
		this.valid = valid;
	}

	public String getValidMsg() {
		return validMsg;
	}

	public void setValidMsg(String validMsg) {
		this.validMsg = validMsg;
	}
}
