/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2003/07/16
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.model.vo;

import jp.go.jsps.kaken.util.FileResource;

/**
 * ���ފǗ�����ێ�����N���X�B
 * 
 * ID RCSfile="$RCSfile: ShoruiKanriInfo.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:11 $"
 */
public class ShoruiKanriInfo extends ShoruiKanriPk {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------
	static final long serialVersionUID = 8598716120596511071L;

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/** ����ID */
	private String jigyoId;

	/** ���Ɩ� */
	private String jigyoName;
	
	/** �N�x */
	private String nendo;
	
	/** �� */
	private String kaisu;

	/** ���ރt�@�C�� */
	private String shoruiFile;

	/** ���ޖ� */
	private String shoruiName;

	/** �폜�t���O */
	private String delFlg;

	/** ���ރt�@�C�� */
	private FileResource    shoruiFileRes;

	//...�Ȃ�

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * �R���X�g���N�^�B
	 */
	public ShoruiKanriInfo() {
		super();
	}
	
	
	/**
	 * ���ފǗ��������Z�b�g����B
	 */
	public void reset(){
		setShoruiFile("");//���ރt�@�C��
		setShoruiName("");//���ޖ�
		setSystemNo("");//�V�X�e���ԍ�
		setTaishoId("");//�Ώ�
		setDelFlg("");//�폜�t���O
		setShoruiFileRes(null);
	}

	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------

	/**
	 * @return
	 */
	public String getDelFlg() {
		return delFlg;
	}

	/**
	 * @return
	 */
	public String getShoruiFile() {
		return shoruiFile;
	}

	/**
	 * @return
	 */
	public String getShoruiName() {
		return shoruiName;
	}

	/**
	 * @param string
	 */
	public void setDelFlg(String string) {
		delFlg = string;
	}

	/**
	 * @param string
	 */
	public void setShoruiFile(String string) {
		shoruiFile = string;
	}

	/**
	 * @param string
	 */
	public void setShoruiName(String string) {
		shoruiName = string;
	}

	/**
	 * @return
	 */
	public String getJigyoName() {
		return jigyoName;
	}

	/**
	 * @return
	 */
	public String getNendo() {
		return nendo;
	}

	/**
	 * @param string
	 */
	public void setJigyoName(String string) {
		jigyoName = string;
	}

	/**
	 * @param string
	 */
	public void setNendo(String string) {
		nendo = string;
	}

	/**
	 * @return
	 */
	public FileResource getShoruiFileRes() {
		return shoruiFileRes;
	}

	/**
	 * @param resource
	 */
	public void setShoruiFileRes(FileResource resource) {
		shoruiFileRes = resource;
	}

	/**
	 * @return
	 */
	public String getJigyoId() {
		return jigyoId;
	}

	/**
	 * @return
	 */
	public String getKaisu() {
		return kaisu;
	}

	/**
	 * @param string
	 */
	public void setJigyoId(String string) {
		jigyoId = string;
	}

	/**
	 * @param string
	 */
	public void setKaisu(String string) {
		kaisu = string;
	}

}
