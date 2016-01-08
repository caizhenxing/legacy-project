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

package jp.go.jsps.kaken.model.vo.shinsei;

import jp.go.jsps.kaken.model.vo.ShinseiDataPk;

/**
 * 関連分野研究者情報を保持するクラス。
 * 
 * ID RCSfile="$RCSfile: KanrenBunyaKenkyushaInfo.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:30 $"
 */
public class KanrenBunyaKenkyushaInfo extends ShinseiDataPk{

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------
	
	/** 関連分野の研究者-氏名 */
	private String kanrenShimei;
	
	/** 関連分野の研究者-所属機関 */
	private String kanrenKikan;
	
	/** 関連分野の研究者-所属部局 */
	private String kanrenBukyoku;
	
	/** 関連分野の研究者-職名 */
	private String kanrenShoku;
	
	/** 関連分野の研究者-専門分野 */
	private String kanrenSenmon;
	
	/** 関連分野の研究者-勤務先電話番号 */
	private String kanrenTel;
	
	/** 関連分野の研究者-自宅電話番号 */
	private String kanrenJitakuTel;
	
	/** 関連分野の研究者-Email */
	private String kanrenMail;
	
	//・・・・・・・・・・

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * コンストラクタ。
	 */
	public KanrenBunyaKenkyushaInfo() {
		super();
	}

	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------
	
	/**
	 * @return
	 */
	public String getKanrenBukyoku() {
		return kanrenBukyoku;
	}

	/**
	 * @return
	 */
	public String getKanrenKikan() {
		return kanrenKikan;
	}

	/**
	 * @return
	 */
	public String getKanrenMail() {
		return kanrenMail;
	}

	/**
	 * @return
	 */
	public String getKanrenSenmon() {
		return kanrenSenmon;
	}

	/**
	 * @return
	 */
	public String getKanrenShimei() {
		return kanrenShimei;
	}

	/**
	 * @return
	 */
	public String getKanrenShoku() {
		return kanrenShoku;
	}

	/**
	 * @return
	 */
	public String getKanrenTel() {
		return kanrenTel;
	}

	/**
	 * @param string
	 */
	public void setKanrenBukyoku(String string) {
		kanrenBukyoku = string;
	}

	/**
	 * @param string
	 */
	public void setKanrenKikan(String string) {
		kanrenKikan = string;
	}

	/**
	 * @param string
	 */
	public void setKanrenMail(String string) {
		kanrenMail = string;
	}

	/**
	 * @param string
	 */
	public void setKanrenSenmon(String string) {
		kanrenSenmon = string;
	}

	/**
	 * @param string
	 */
	public void setKanrenShimei(String string) {
		kanrenShimei = string;
	}

	/**
	 * @param string
	 */
	public void setKanrenShoku(String string) {
		kanrenShoku = string;
	}

	/**
	 * @param string
	 */
	public void setKanrenTel(String string) {
		kanrenTel = string;
	}

	/**
	 * @return
	 */
	public String getKanrenJitakuTel() {
		return kanrenJitakuTel;
	}

	/**
	 * @param string
	 */
	public void setKanrenJitakuTel(String string) {
		kanrenJitakuTel = string;
	}

}
