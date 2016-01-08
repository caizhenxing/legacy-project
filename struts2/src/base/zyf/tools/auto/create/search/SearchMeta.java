/**
 * 
 * ����ʱ�䣺Oct 27, 200711:45:00 AM
 * �ļ�����SearchMeta.java
 * �����ߣ�zhaoyf
 * 
 */
package base.zyf.tools.auto.create.search;

import java.text.MessageFormat;

/**
 * @author zhaoyf
 *
 */
public class SearchMeta {

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
	public static final String SEARCH_TYPE_TEXT="text";
	public static final String SEARCH_TYPE_SELECT="select";
	public static final String SEARCH_TYPE_TIME="time";
	public static final String SEARCH_TYPE_DEPT="dept";
	/**
	 * {0}->name
	 * {1}->oper
	 * {2}->type
	 * {3}->valueDefault 
	 * {4}->style
	 */
	public static final String SEARCH_TEXT="<search:text name=\"{0}\" oper=\"{1}\" type=\"{2}\" valueDefault=\"{3}\" {4}/>";
	/**
	 * {0}->name
	 */
	public static final String SEARCH_SELECT_A="<search:select name=\"{0}\"/>";
	/**
	 * {0}->name
	 * {1}->selectSubSysCode
	 * {2}->selectModuleCode
	 * {3}->"'"
	 * {4}->"{"
	 * {5}->"}"
	 */
	public static final String SEARCH_SELECT_B="<ec:composite value={3}${4}theForm.conditions[\"{0}\"].value{5}{3} valueName = \"conditions({0}).value\" textName = \"temp.conditions({0}).value\" source = {3}${4}theForm.sysCodes[\"{1}\"][\"{2}\"]{5}{3} />	";
	/**
	 * {0}->name
	 * {1}->pattern
	 * {2}->valueDefault 
	 */
	public static final String SEARCH_TIME="<search:time name=\"{0}\" pattern=\"{1}\" valueDefault=\"{2}\"/>";
	/**
	 * {0}->code
	 * {1}->name
	 * {2}->valueDefault 
	 */
	public static final String SEARCH_DEPT="<search:dept code=\"{0}\" name=\"{1}\" valueDefault=\"{2}\"/>";
	/**
	 * ������
	 */
	private String disPlayName="";
	/**
	 * ���ͣ����ı���������ʱ��ѡ���
	 */
	private String searchType;
	/**
	 * ����
	 */
	private String name="";
	/**
	 * ���ͣ�����java.lang.String
	 */
	private String type="java.lang.String";
	
	/**
	 * ���� ����"="
	 */
	private String oper="like";
	
	/**
	 * Ĭ��ֵ ����"${theForm}"
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
	 * ���Ż���Ա��code��
	 */
	private String code;
	
	//
	/**
	 * ��ʾ��ʽ����ʽ
	 */


	private String viewClass=" class=\"attribute\" ";

	private String inputClass="";
	
	//
	
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
	public String getDisPlayName() {
		return disPlayName;
	}

	public void setDisPlayName(String disPlayName) {
		if(disPlayName==null)
			return;;
		this.disPlayName = disPlayName;
	}

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		if(searchType==null)
			return;
		this.searchType = searchType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if(name==null)
			return;
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		if(type==null)
			return;
		this.type = type;
	}

	public String getOper() {
		return oper;
	}

	public void setOper(String oper) {
		if(oper==null)
			return;
		this.oper = oper;
	}

	public String getValueDefault() {
		return valueDefault;
	}

	public void setValueDefault(String valueDefault) {
		if(valueDefault==null)
			return;
		this.valueDefault = valueDefault;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		if(style==null)
			return;
		this.style = style;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		if(pattern==null)
			return;
		this.pattern = pattern;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		if(code==null)
			return;
		this.code = code;
	}

	public String getViewClass() {
		return viewClass;
	}

	public void setViewClass(String viewClass) {
		if(viewClass==null)
			return;
		this.viewClass = viewClass;
	}


	public String getInputClass() {
		return inputClass;
	}

	public void setInputClass(String inputClass) {
		if(inputClass==null)
			return;
		this.inputClass = inputClass;
	}
	
	public String getTag()
	{
		StringBuffer tag=new StringBuffer();
		String tags="";
		tag.append(tab+tab+tab);
		if(this.searchType.equals(this.SEARCH_TYPE_DEPT))
		{
			tag.append(this.SEARCH_DEPT);
			tags=MessageFormat.format(tag.toString(),new Object[]{code,name,valueDefault});
		}
		if(this.searchType.equals(this.SEARCH_TYPE_SELECT))
		{
			tag.append(this.SEARCH_SELECT_A);
			tag.append(this.SEARCH_SELECT_B);
			tags=MessageFormat.format(tag.toString(),new Object[]{name,selectSubSysCode,selectModuleCode,"'","{","}"});
		}
		if(this.searchType.equals(this.SEARCH_TYPE_TEXT))
		{
			tag.append(this.SEARCH_TEXT);
			tags=MessageFormat.format(tag.toString(),new Object[]{name,oper,type,valueDefault,style});
		}
		if(this.searchType.equals(this.SEARCH_TYPE_TIME))
		{
			tag.append(this.SEARCH_TIME);
			tags=MessageFormat.format(tag.toString(),new Object[]{name,pattern,valueDefault});
		}
		
		return MessageFormat.format(td.toString(), new Object[]{this.viewClass,this.inputClass,this.disPlayName,tags});
	}
	public static String getNullTag()
	{
		
		return MessageFormat.format(td.toString(), new Object[]{"","","&nbsp;","&nbsp;"});
	}
	public String getSelectSubSysCode() {
		return selectSubSysCode;
	}

	public void setSelectSubSysCode(String selectSubSysCode) {
		if(selectSubSysCode==null)
			return;
		this.selectSubSysCode = selectSubSysCode;
	}

	public String getSelectModuleCode() {
		return selectModuleCode;
	}

	public void setSelectModuleCode(String selectModuleCode) {
		if(selectModuleCode==null)
			return;
		this.selectModuleCode = selectModuleCode;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}
}
