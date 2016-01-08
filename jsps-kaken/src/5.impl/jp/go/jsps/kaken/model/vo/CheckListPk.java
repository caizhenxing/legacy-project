/*
 * Created on 2005/04/12
 */
package jp.go.jsps.kaken.model.vo;

/**
 * チェックリスト用データ（主キー）保持クラス
 * 
 * @author masuo_t
 *
 */
public class CheckListPk extends ValueObject {

	/** 所属CD */
	private String shozokuCd;
	
	/** 事業ID */
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
