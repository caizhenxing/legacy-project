/**
 * 
 * 项目名称：struts2
 * 制作时间：Apr 29, 20094:30:34 PM
 * 包名：base.zyf.hibernate
 * 文件名：CodeWrapper.java
 * 制作者：赵一非
 * @version 1.0
 */
package base.zyf.hibernate.usertype;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;



/**
 * 代码封装对象
 * @author 赵一非
 * @version 1.0
 */
public class CodeWrapper implements Serializable {
	/** 代码 code */
	protected String code;
	
	/** 代码 name */
	protected String name;
	
	/** 代码描述 */
	protected String description;
	 
    public CodeWrapper() {
        super();
        this.code="";
		this.name="";
    }
    public CodeWrapper(String code) {
    	 this.code = code;
    	 this.name="";
    }
    public CodeWrapper(String code, String name) {
        this.code = code;
        this.name = name;
    }

	public CodeWrapper(String code, String name, String description) {
		this.code = code;
		this.name = name;
		this.description = description;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}


	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	private boolean isModify=false;
	public boolean isModify() {
		return isModify;
	}
	public void setModify(boolean isModify) {
		this.isModify = isModify;
	}
	
	
	/**
	 * 描述：
	 */
	private static final long serialVersionUID = 2268012381907524861L;

	public String toString() {
		// TODO Auto-generated method stub
		return this.getCode();
	}
	public void setCode(String code) {
		// TODO Auto-generated method stub
		this.setModify(!code.equals(this.code));
		this.code = code;
	}
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if(obj==null)
			return false;
		if(CodeWrapper.class.isAssignableFrom(obj.getClass()))
		{
			if(StringUtils.isBlank(((CodeWrapper)obj).getCode()))
				return StringUtils.isBlank(this.getCode());
			return ((CodeWrapper)obj).getCode().equals(this.getCode());
				
		}
		return false;
	}
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		CodeWrapper cwp=new CodeWrapper();
		cwp.setCode(this.getCode());
		cwp.setDescription(this.getDescription());
		cwp.setName(this.name);
		return cwp;
	}
	
}
