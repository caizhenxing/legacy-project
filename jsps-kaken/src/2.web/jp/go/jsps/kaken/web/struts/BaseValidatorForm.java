/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2003/11/14
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.struts;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;

/**
 * ���؃t�H�[���̊�{�ƂȂ�N���X�B
 * 
 * ID RCSfile="$RCSfile: BaseValidatorForm.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:28 $"
 */
public class BaseValidatorForm extends ValidatorForm {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------
	/** 
	 * ���O
	 */
	protected static Log log = LogFactory.getLog(BaseValidatorForm.class);

	/**
	 * �V�K�o�^�A�N�V�����B 
	 */
	public static final String ADD_ACTION = "add";

	/**
	 * �C���A�N�V�����B
	 */
	public static final String EDIT_ACTION = "edit";

	/**
	 * �폜�A�N�V�����B
	 */
	public static final String DELETE_ACTION = "delete";

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/** ������� */
	private Map dto = new HashMap();
	
	/** �A�N�V�����B */
	private String action;

	/** �J�ڌ��y�[�W*/
	private String source;
	
	/** �J�ڐ�y�[�W*/
	private String destination;

	/** �g�ݍ��킹�`�F�b�N�Ŏg�p����_�~�[�t�B�[���h*/
	private String combination;
			
	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * �R���X�g���N�^�B
	 */
	public BaseValidatorForm() {
		super();
	}

	//---------------------------------------------------------------------
	// Public methods
	//---------------------------------------------------------------------

	/* 
	 * �����������B
	 * (�� Javadoc)
	 * @see org.apache.struts.action.ActionForm#reset(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		//super.reset(mapping, request);
//		action=ADD_ACTION;
		dto.clear();
	}

	/* 
	 * ���̓`�F�b�N�B
	 * (�� Javadoc)
	 * @see org.apache.struts.action.ActionForm#validate(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
	 */
	public ActionErrors validate(
		ActionMapping mapping,
		HttpServletRequest request) {

		//�X�[�p�[�N���X�̏������Ăяo���B 
		ActionErrors errors = super.validate(mapping, request);
		return errors;
	}

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

	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------


	/**
	 * DTO�}�b�v���擾����B
	 * @return	Dto�}�b�v	
	 */
	public Map getDto() {
		return dto;
	}

	/**
	 * DTO�}�b�v�̃L�[�ɊY������l���擾����B
	 * @param key	�L�[
	 * @return		�l
	 */
	public Object getDto(String key) {
		return dto.get(key);
	}

	/**
	 * DTO�}�b�v���Z�b�g����B
	 * @param map	DTO�}�b�v
	 */
	public void setDto(Map map) {
		dto = map;
	}

	/**
	 * DTO�}�b�v�ɒl���Z�b�g����
	 * @param key		�L�[�l
	 * @param value		�l
	 */
	public void setDto(String key, Object value) {
		dto.put(key,value);
	}

	/**
	 * �A�N�V�����������擾����B
	 * @return	�A�N�V������
	 */
	public String getAction() {
		return action;
	}

	/**
	 * �A�N�V�����������Z�b�g����B
	 * @param string	�A�N�V������
	 */
	public void setAction(String string) {
		action = string;
	}
	/**
	 * @return
	 */
	public String getDestination() {
		return destination;
	}

	/**
	 * @return
	 */
	public String getSource() {
		return source;
	}

	/**
	 * @param string
	 */
	public void setDestination(String string) {
		destination = string;
	}

	/**
	 * @param string
	 */
	public void setSource(String string) {
		source = string;
	}

	/**
	 * @return
	 */
	public String getCombination() {
		return combination;
	}

	/**
	 * @param string
	 */
	public void setCombination(String string) {
		combination = string;
	}

}
