/*
 * Created on 2005/04/12
 */
package jp.go.jsps.kaken.model.vo;

/**
 * �`�F�b�N���X�g�p�f�[�^�i��L�[�j�ێ��N���X
 * 
 * @author masuo_t
 *
 */
public class CheckListPk extends ValueObject {

	/** ����CD */
	private String shozokuCd;
	
	/** ����ID */
	private String jigyoId;

	/**
	 * @return
	 */
	public String getJigyoId() {
		return jigyoId;
	}

	/**
	 * @return
	 */
	public String getShozokuCd() {
		return shozokuCd;
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
	public void setShozokuCd(String string) {
		shozokuCd = string;
	}

}
