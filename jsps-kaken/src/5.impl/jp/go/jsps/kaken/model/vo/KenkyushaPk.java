/*
 * �쐬��: 2005/03/28
 *
 */
package jp.go.jsps.kaken.model.vo;

/**
 * �����ҏ��i��L�[�j��ێ�����N���X�B
 * 
 * @author yoshikawa_h
 *
 */
public class KenkyushaPk extends ValueObject{
	
	/** �����Ҕԍ� */
	private String kenkyuNo;
	
	/** �����@�փR�[�h */
	private String ShozokuCd;

	/**
	 * �R���X�g���N�^�B
	 */
	public KenkyushaPk() {
		super();
	}
	
	/**
	 * @return kenkyuNo ��߂��܂��B
	 */
	public String getKenkyuNo() {
		return kenkyuNo;
	}
	/**
	 * @param kenkyuNo kenkyuNo ��ݒ�B
	 */
	public void setKenkyuNo(String kenkyuNo) {
		this.kenkyuNo = kenkyuNo;
	}
	/**
	 * @return shozokuCd ��߂��܂��B
	 */
	public String getShozokuCd() {
		return ShozokuCd;
	}
	/**
	 * @param shozokuCd shozokuCd ��ݒ�B
	 */
	public void setShozokuCd(String shozokuCd) {
		ShozokuCd = shozokuCd;
	}
}
