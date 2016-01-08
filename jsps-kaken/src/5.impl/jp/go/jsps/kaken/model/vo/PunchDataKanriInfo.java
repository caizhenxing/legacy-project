/*
 * �쐬��: 2004/10/20
 *
 * ���̐������ꂽ�R�����g�̑}�������e���v���[�g��ύX���邽��
 * �E�B���h�E > �ݒ� > Java > �R�[�h���� > �R�[�h�ƃR�����g
 */
package jp.go.jsps.kaken.model.vo;

import java.util.Date;

/**
 * @author kainuma
 *
 * ���̐������ꂽ�R�����g�̑}�������e���v���[�g��ύX���邽��
 * �E�B���h�E > �ݒ� > Java > �R�[�h���� > �R�[�h�ƃR�����g
 */
public class PunchDataKanriInfo extends ValueObject{
	
	
	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/** �p���`�f�[�^��� */
	private String punchShubetu;
	
	/** �p���`�f�[�^���� */
	private String punchName;
	
	/** ���Ƌ敪 */
	private String jigyoKubun;
	
	/** �쐬���� */
	private Date sakuseiDate;
	
	/** �p���`�f�[�^�t�@�C���p�X */
	private String punchPath;
	


	
	//�E�E�E�E�E�E�E�E�E�E

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * �R���X�g���N�^�B
	 */
	public PunchDataKanriInfo() {
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
	public String getJigyoKubun() {
		return jigyoKubun;
	}

	/**
	 * @return
	 */
	public String getPunchName() {
		return punchName;
	}

	/**
	 * @return
	 */
	public String getPunchPath() {
		return punchPath;
	}

	/**
	 * @return
	 */
	public String getPunchShubetu() {
		return punchShubetu;
	}

	/**
	 * @return
	 */
	public Date getSakuseiDate() {
		return sakuseiDate;
	}

	/**
	 * @param string
	 */
	public void setJigyoKubun(String string) {
		jigyoKubun = string;
	}

	/**
	 * @param string
	 */
	public void setPunchName(String string) {
		punchName = string;
	}

	/**
	 * @param string
	 */
	public void setPunchPath(String string) {
		punchPath = string;
	}

	/**
	 * @param string
	 */
	public void setPunchShubetu(String string) {
		punchShubetu = string;
	}

	/**
	 * @param date
	 */
	public void setSakuseiDate(Date date) {
		sakuseiDate = date;
	}

}
