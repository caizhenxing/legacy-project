/*
 * 作成日: 2005/03/28
 *
 */
package jp.go.jsps.kaken.web.shozoku.shinseishaKanri;

import jp.go.jsps.kaken.web.struts.BaseSearchForm;
import jp.go.jsps.kaken.web.struts.BaseValidatorForm;

/**
 * @author yoshikawa_h
 *
 */
public class ShinseishaListForm extends BaseValidatorForm {
	
	BaseSearchForm base = new BaseSearchForm();
	
	/** 研究者番号 */
	//配列は１ページの最大表示件数分とる。
	private String[] kenkyuNo = new String[base.getPageSize()];
	
	
	//2005/04/20 追加 ここから-------------------------------------
	//理由 ラジオボタンのチェック判別用に追加
	private String selectRadioButton = new String();
	//追加 ここまで------------------------------------------------

	/**
	 * @return kenkyuNo を戻します。
	 */
	public String[] getKenkyuNo() {
		return kenkyuNo;
	}
	/**
	 * @param kenkyuNo kenkyuNo を設定。
	 */
	public void setKenkyuNo(String[] kenkyuNo) {
		this.kenkyuNo = kenkyuNo;
	}
	
	//2005/04/20 追加 ここから-------------------------------------
	//理由 ラジオボタンのチェック判別用に追加
	/**
	 * @return selectRadioButton を戻します。
	 */
	public String getSelectRadioButton() {
		return selectRadioButton;
	}

	/**
	 * @param selectRadioButton selectRadioButtonを設定。
	 */
	public void setSelectRadioButton(String selectRadioButton) {
		this.selectRadioButton = selectRadioButton;
	}
	//追加 ここまで------------------------------------------------
}
