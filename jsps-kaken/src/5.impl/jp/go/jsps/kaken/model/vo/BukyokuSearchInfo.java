/*
 * Created on 2005/04/05
 *
 */
package jp.go.jsps.kaken.model.vo;

/**
 * ���ǊǗ���񌟍��p�f�[�^�ێ��N���X
 *
 */
public class BukyokuSearchInfo extends SearchInfo {
	
	/** �����@�փR�[�h */
	private String shozokuCd;
	
	/**
	 * @return �����@�փR�[�h��Ԃ��܂��B
	 */
	public String getShozokuCd() {
		return shozokuCd;
	}

	/**
	 * @param shozokuCd �����@�փR�[�h��ݒ肵�܂��B
	 */
	public void setShozokuCd(String shozokuCd) {
		this.shozokuCd = shozokuCd;
	}

}
