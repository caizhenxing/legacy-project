/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2003/12/08
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
 package jp.go.jsps.kaken.model.vo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * �������邽�߂̏���ێ�����N���X�B
 * 
 * ID RCSfile="$RCSfile: SearchInfo.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:38 $"
 */
public class SearchInfo extends ValueObject {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------
	//static final long serialVersionUID = 2911234343007665744L;

	/** ���O */
	protected static Log log = LogFactory.getLog(SearchInfo.class);
	
	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------
	
	/** 1 �y�[�W������̕\������(���ׂẴf�[�^���P�y�[�W�ɕ\������ꍇ��0) */
	private int pageSize = 0;

	/** �擾�J�n�s�ԍ�(1�s�ڂ���擾����ꍇ��0) */
	private int startPosition = 0;

	/** �f�[�^����MAX�l�iMAX�l��݂��Ȃ��ꍇ��0�j */
	private int maxSize = 0;
	
	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * �R���X�g���N�^�B
	 */
	public SearchInfo() {
		super();
	}

	//---------------------------------------------------------------------
	// Public Methods
	//---------------------------------------------------------------------

	/**
	 * �擾�J�n�s�ԍ����擾����B
	 * @return	�擾�J�n�s�ԍ�
	 */
	public int getStartPosition() {
		//�J�n�ʒu�����̏ꍇ��0�ɂ���B
		if (startPosition < 0) {
			return 0;
		}
		return startPosition;
	}
	
	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------
	
	/**
	 * @return
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * @param i
	 */
	public void setPageSize(int i) {
		pageSize = i;
	}

	/**
	 * @param i
	 */
	public void setStartPosition(int i) {
		startPosition = i;
	}
	/**
	 * @return
	 */
	public int getMaxSize() {
		return maxSize;
	}

	/**
	 * @param i
	 */
	public void setMaxSize(int i) {
		maxSize = i;
	}

}
