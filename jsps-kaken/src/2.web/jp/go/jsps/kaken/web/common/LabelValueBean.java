package jp.go.jsps.kaken.web.common;

import java.io.Serializable;

import jp.go.jsps.kaken.model.exceptions.SystemException;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 主に&lt;HTML:options&gt;に渡す、OPTIONタグの項目情報を保持しているクラス。<br>
 * <pre>
 * 使い方：
 * Action側で下記のようにCollectionを生成し、scopeにセットしておく。
 * List monthList = new ArrayList();
 * monthList.add(new LabelValueBean("1月","1"));
 * monthList.add(new LabelValueBean("2月","2"));
 * monthList.add(new LabelValueBean("3月","3"));
 * request.setAttribute("monthList", monthList);
 *
 * JSP側で
 * &lt;HTML:select property="month"&gt;
 *    &lt;HTML:options collection="monthList" property="value" labelProperty="label"/&gt;
 * &lt;/HTML:select property="month"&gt;
 *
 * とすると、下記のようなタグを出力する。
 * &lt;select name="month"&gt;
 * &lt;option value="1"&gt;1月&lt;/option&gt;
 * &lt;option value="2"&gt;2月&lt;/option&gt;
 * &lt;option value="3"&gt;3月&lt;/option&gt;
 * &lt;/select&gt;
 * </pre>
 * 
 * ID RCSfile="$RCSfile: LabelValueBean.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:49 $" 
 */

public class LabelValueBean implements Serializable{
		
	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------
	/** 
	 * ログ
	 */
	private static Log log = LogFactory.getLog(LabelValueBean.class);

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/** OPTIONタグのvalue */
	private String value;

	/** OPTIONタグのlabel */
	private String label;

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * デフォルトコンストラクタ。
	 */
	public LabelValueBean() {
	}

	/**
	 * valueとlabelを指定してOPTIONタグの項目情報を生成する。
	 * @param label OPTIONタグのvalue
	 * @param value OPTIONタグのvalue
	 */
	public LabelValueBean(String label,String value) {
		this.value = value;
		this.label = label;
	}

	//---------------------------------------------------------------------
	// Public methods
	//---------------------------------------------------------------------

	/* (非 Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		try {
			return BeanUtils.describe(this).toString();
		} catch (Exception e) {
			if (log.isErrorEnabled()) {
				log.error(this.getClass().getName() + " toString error ", e);
			}
			throw new SystemException(
				this.getClass().getName() + " toString error ",
				e);
		}
	}
	/* (非 Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object o) {
		return EqualsBuilder.reflectionEquals(this, o);
	}

	/* (非 Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------

	/**
	 * OPTIONタグのvalueを設定する。
	 * @param value OPTIONタグのvalue 
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * OPTIONタグのvalueを取得する。
	 * @return OPTIONタグのvalue
	 */
	public String getValue() {
		return value;
	}

	/**
	 * OPTIONタグのlabelを設定する。
	 * @param label OPTIONタグのlabel 
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * OPTIONタグのlabelを取得する。
	 * @return OPTIONタグのlabel
	 */
	public String getLabel() {
		return label;
	}

}
