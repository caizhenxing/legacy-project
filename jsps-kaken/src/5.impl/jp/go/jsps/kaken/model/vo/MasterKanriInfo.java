/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2004/02/16
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */

package jp.go.jsps.kaken.model.vo;

import java.util.Date;
import java.util.List;


/**
 * �}�X�^�Ǘ��f�[�^����ێ�����N���X�B
 * 
 * ID RCSfile=$RCSfile: MasterKanriInfo.java,v $
 * Revision=$Revision: 1.1 $
 * Date=$Date: 2007/06/28 02:07:13 $
 */
public class MasterKanriInfo extends ValueObject{

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/**
     * <code>serialVersionUID</code> �̃R�����g
     */
    private static final long serialVersionUID = 5457083506946073237L;

	/** �}�X�^��� */
	private String masterShubetu;

	/** �}�X�^���� */
	private String masterName;
	
	/** ��荞�ݓ��� */
	private Date importDate;
	
	/** ���� */
	private String kensu;
	
	/** ��荞�݃e�[�u���� */
	private String importTable;
	
	/** �V�K�E�X�V�t���O */
	private String importFlg;
	
	/** ������ */
	private String importMsg;
	
	/** CSV�t�@�C���p�X */
	private String csvPath;

	/** �捞�G���[���b�Z�[�W */
	private List importErrMsg;
	
	/** �}�X�^�X�V���t */
	private Date updateDate;
	
	//�E�E�E�E�E�E�E�E�E�E

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * �R���X�g���N�^�B
	 */
	public MasterKanriInfo() {
		super();
	}
	
	
	//---------------------------------------------------------------------
	// Methods
	//---------------------------------------------------------------------
	
	
	
	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------
	

	/**
	 * @return
	 */
	public String getMasterShubetu() {
		return masterShubetu;
	}

	/**
	 * @return
	 */
	public String getMasterName() {
		return masterName;
	}

	/**
	 * @return
	 */
	public Date getImportDate() {
		return importDate;
	}

	/**
	 * @return
	 */
	public String getKensu() {
		return kensu;
	}

	/**
	 * @return
	 */
	public String getImportTable() {
		return importTable;
	}

	/**
	 * @return
	 */
	public String getImportFlg() {
		return importFlg;
	}

	/**
	 * @return
	 */
	public String getImportMsg() {
		return importMsg;
	}

	/**
	 * @return
	 */
	public List getImportErrMsg() {
		return importErrMsg;
	}


	/**
	 * @param string
	 */
	public void setMasterShubetu(String string) {
		masterShubetu = string;
	}

	/**
	 * @param string
	 */
	public void setMasterName(String string) {
		masterName = string;
	}

	/**
	 * @param string
	 */
	public void setImportDate(Date date) {
		importDate = date;
	}

	/**
	 * @param string
	 */
	public void setKensu(String string) {
		kensu = string;
	}

	/**
	 * @param string
	 */
	public void setImportTable(String string) {
		importTable = string;
	}

	/**
	 * @param string
	 */
	public void setImportFlg(String string) {
		importFlg = string;
	}

	/**
	 * @param string
	 */
	public void setImportMsg(String string) {
		importMsg = string;
	}

	/**
	 * @param string
	 */
	public void setImportErrMsg(List list) {
		importErrMsg = list;
	}

	/**
	 * @return
	 */
	public String getCsvPath() {
		return csvPath;
	}

	/**
	 * @param string
	 */
	public void setCsvPath(String string) {
		csvPath = string;
	}


	/**
     * updateDate���擾���܂��B
     * 
     * @return updateDate
     */
    
    public Date getUpdateDate() {
    	return updateDate;
    }


	/**
     * updateDate��ݒ肵�܂��B
     * 
     * @param updateDate updateDate
     */
    
    public void setUpdateDate(Date updateDate) {
    	this.updateDate = updateDate;
    }

}
