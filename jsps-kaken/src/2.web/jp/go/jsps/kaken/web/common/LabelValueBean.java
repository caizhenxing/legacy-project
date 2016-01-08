package jp.go.jsps.kaken.web.common;

import java.io.Serializable;

import jp.go.jsps.kaken.model.exceptions.SystemException;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * ���&lt;HTML:options&gt;�ɓn���AOPTION�^�O�̍��ڏ���ێ����Ă���N���X�B<br>
 * <pre>
 * �g�����F
 * Action���ŉ��L�̂悤��Collection�𐶐����Ascope�ɃZ�b�g���Ă����B
 * List monthList = new ArrayList();
 * monthList.add(new LabelValueBean("1��","1"));
 * monthList.add(new LabelValueBean("2��","2"));
 * monthList.add(new LabelValueBean("3��","3"));
 * request.setAttribute("monthList", monthList);
 *
 * JSP����
 * &lt;HTML:select property="month"&gt;
 *    &lt;HTML:options collection="monthList" property="value" labelProperty="label"/&gt;
 * &lt;/HTML:select property="month"&gt;
 *
 * �Ƃ���ƁA���L�̂悤�ȃ^�O���o�͂���B
 * &lt;select name="month"&gt;
 * &lt;option value="1"&gt;1��&lt;/option&gt;
 * &lt;option value="2"&gt;2��&lt;/option&gt;
 * &lt;option value="3"&gt;3��&lt;/option&gt;
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
	 * ���O
	 */
	private static Log log = LogFactory.getLog(LabelValueBean.class);

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/** OPTION�^�O��value */
	private String value;

	/** OPTION�^�O��label */
	private String label;

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * �f�t�H���g�R���X�g���N�^�B
	 */
	public LabelValueBean() {
	}

	/**
	 * value��label���w�肵��OPTION�^�O�̍��ڏ��𐶐�����B
	 * @param label OPTION�^�O��value
	 * @param value OPTION�^�O��value
	 */
	public LabelValueBean(String label,String value) {
		this.value = value;
		this.label = label;
	}

	//---------------------------------------------------------------------
	// Public methods
	//---------------------------------------------------------------------

	/* (�� Javadoc)
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
	/* (�� Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object o) {
		return EqualsBuilder.reflectionEquals(this, o);
	}

	/* (�� Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------

	/**
	 * OPTION�^�O��value��ݒ肷��B
	 * @param value OPTION�^�O��value 
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * OPTION�^�O��value���擾����B
	 * @return OPTION�^�O��value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * OPTION�^�O��label��ݒ肷��B
	 * @param label OPTION�^�O��label 
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * OPTION�^�O��label���擾����B
	 * @return OPTION�^�O��label
	 */
	public String getLabel() {
		return label;
	}

}
