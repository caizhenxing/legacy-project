/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2003/11/20
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.model.vo;

import java.io.Serializable;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * �l�I�u�W�F�N�g�ł��邱�Ƃ������N���X�B
 * 
 * ID RCSfile="$RCSfile: ValueObject.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:38 $"
 */
public class ValueObject implements Serializable {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------
	//static final long serialVersionUID = -2592182194435673611L;

	/**  ���O  */
	protected static Log log = LogFactory.getLog(ValueObject.class);

	//---------------------------------------------------------------------
	// Public Methods
	//---------------------------------------------------------------------

	/* (�� Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuffer results = new StringBuffer();
		results.append(getClassAsString(getClass().getSuperclass()));
		results.append(getClassAsString(getClass()));
		return results.toString();
	}

	/**
	 * ��`���ꂽ���𕶎���Ƃ��ĕ\������B
	 * @param clazz	�@�f�o�b�N�p�������\�����邽�߂̕�����	
	 * @return	�f�o�b�O�p���b�Z�[�W
	 */
	public StringBuffer getClassAsString(Class clazz) {
		StringBuffer results = new StringBuffer();
		results.append(getClass().getName() + "\n");

		Field[] fields = clazz.getDeclaredFields();
		try {
			AccessibleObject.setAccessible(fields, true);
			for (int i = 0; i < fields.length; i++) {
				results.append(
					"\t"
						+ fields[i].getName()
						+ "="
						+ fields[i].get(this)
						+ "\n");
			}
		} catch (Exception e) {
			log.error(e);
		}

		return results;
	}
}
