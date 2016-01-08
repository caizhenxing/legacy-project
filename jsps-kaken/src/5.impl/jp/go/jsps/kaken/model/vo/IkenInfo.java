/*======================================================================
 *    SYSTEM      : 
 *    Source name : IkenInfo.java
 *    Description : �ӌ��E�v�]����ێ�����N���X
 *
 *    Author      : Admin
 *    Date        : 2005/05/20
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *�@�@2005/05/20    1.0         Xiang Emin     �V�K�쐬
 *
 *====================================================================== 
 */

package jp.go.jsps.kaken.model.vo;

import java.io.Serializable;


/**
 * @author user1
 *
 * TODO ���̐������ꂽ�^�R�����g�̃e���v���[�g��ύX����ɂ͎����Q�ƁB
 * �E�B���h�E �� �ݒ� �� Java �� �R�[�h�E�X�^�C�� �� �R�[�h�E�e���v���[�g
 */
public class IkenInfo implements Serializable {

	/** �V�X�e����t�ԍ� */
	private String system_no = "";
	
	/**�@���e�� */
	private String sakusei_date = "";
	
	/** �Ώێ҂h�c */
	private int taisho_id = 0;

	/** �ΏێҖ��� */
	private String taisho_nm = "";
	
	/** ���ӌ����e */
	private String iken = "";
	
	/** ���l */
	private String biko = "";
	
	/**
	 * @return �V�X�e����t�ԍ�
	 */
	public String getSystem_no(){
		return system_no;
	}
	
	/**
	 * @param s �V�X�e����t�ԍ�
	 */
	public void setSystem_no(String s){
		system_no = s;
	}
	
	/**
	 * @return ���e��
	 */
	public String getSakusei_date(){
		return sakusei_date;
	}
	
	/**
	 * @param s ���e��
	 */
	public void setSakusei_date(String s){
		sakusei_date = s;
	}
	
	/**
	 * @return �Ώێ҂h�c
	 */
	public int getTaisho_id(){
		return taisho_id;
	}
	
	/**
	 * @param s �Ώێ҂h�c
	 */
	public void setTaisho_id(int id){
		taisho_id = id;
	}

	/**
	 * @return �ΏێҖ���
	 */
	public String getTaisho_nm(){
		return taisho_nm;
	}
	
	/**
	 * @param s �ΏێҖ���
	 */
	public void setTaisho_nm(String nm){
		taisho_nm = nm;
	}
	
	/**
	 * @return �ӌ����e
	 */
	public String getIken(){
		return iken;
	}
	
	/**
	 * @param s �ӌ����e
	 */
	public void setIken(String s){
		iken = s;
	}
	
	/**
	 * @return ���l
	 */
	public String getBiko(){
		return biko;
	}
	
	/**
	 * @param s ���l
	 */
	public void setBiko(String s){
		biko = s;
	}

}
