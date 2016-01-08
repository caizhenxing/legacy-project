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

/**
 * 割り振り結果情報を保持するクラス。
 * 
 * ID RCSfile="$RCSfile: WarifuriInfo.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:12 $"
 */
public class WarifuriInfo extends WarifuriPk{

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/** 年度 */
	private String nendo;

	/** 回数 */
	private String kaisu;

	/** 事業名 */
	private String jigyoName;

	/** 申請番号 */
	private String uketukeNo;
	
	/** 系等の区分 */
	private String keiNameRyaku;

	/** 研究課題名（和文） */
	private String kadaiNameKanji;

	/** 申請者氏名（漢字等-姓） */
	private String nameKanjiSei;

	/** 申請者氏名（漢字等-名） */
	private String nameKanjiMei;

	/** 所属機関名（略称） */
	private String shozokuNameRyaku;

	/** 部局名（略称） */
	private String bukyokuNameRyaku;

	/** 職名（略称） */
	private String shokushuNameRyaku;

	/** 審査員番号 */
	private String shinsainNo;

	/** 審査員名-姓 */
	private String shinsainNameKanjiSei;

	/** 審査員名-名 */
	private String shinsainNameKanjiMei;

	/** 審査員所属機関 */
	private String shinsainShozokuName;

	/** 審査員部局名 */
	private String shinsainBukyokuName;

	/** 審査員職名 */
	private String shinsainShokuName;

//2005/10/17追加
	/** 整理番号 */
	private String seiriNo;

	//・・・・・・・・・・

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * コンストラクタ。
	 */
	public WarifuriInfo() {
		super();
	}

	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------
	/**
	 * @return
	 */
	public String getBukyokuNameRyaku() {
		return bukyokuNameRyaku;
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
	public String getKadaiNameKanji() {
		return kadaiNameKanji;
	}

	/**
	 * @return
	 */
	public String getKaisu() {
		return kaisu;
	}

	/**
	 * @return
	 */
	public String getNameKanjiMei() {
		return nameKanjiMei;
	}

	/**
	 * @return
	 */
	public String getNameKanjiSei() {
		return nameKanjiSei;
	}

	/**
	 * @return
	 */
	public String getNendo() {
		return nendo;
	}

	/**
	 * @return
	 */
	public String getShinsainBukyokuName() {
		return shinsainBukyokuName;
	}

	/**
	 * @return
	 */
	public String getShinsainNameKanjiMei() {
		return shinsainNameKanjiMei;
	}

	/**
	 * @return
	 */
	public String getShinsainNameKanjiSei() {
		return shinsainNameKanjiSei;
	}

	/**
	 * @return
	 */
	public String getShinsainNo() {
		return shinsainNo;
	}

	/**
	 * @return
	 */
	public String getShinsainShokuName() {
		return shinsainShokuName;
	}

	/**
	 * @return
	 */
	public String getShinsainShozokuName() {
		return shinsainShozokuName;
	}

	/**
	 * @return
	 */
	public String getShokushuNameRyaku() {
		return shokushuNameRyaku;
	}

	/**
	 * @return
	 */
	public String getShozokuNameRyaku() {
		return shozokuNameRyaku;
	}

	/**
	 * @return
	 */
	public String getUketukeNo() {
		return uketukeNo;
	}

//2005/10/17　整理番号追加
	/**
	 * @return
	 */
	public String getSeiriNo() {
		return seiriNo;
	}

	/**
	 * @param string
	 */
	public void setBukyokuNameRyaku(String string) {
		bukyokuNameRyaku = string;
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
	public void setKadaiNameKanji(String string) {
		kadaiNameKanji = string;
	}

	/**
	 * @param string
	 */
	public void setKaisu(String string) {
		kaisu = string;
	}

	/**
	 * @param string
	 */
	public void setNameKanjiMei(String string) {
		nameKanjiMei = string;
	}

	/**
	 * @param string
	 */
	public void setNameKanjiSei(String string) {
		nameKanjiSei = string;
	}

	/**
	 * @param string
	 */
	public void setNendo(String string) {
		nendo = string;
	}

	/**
	 * @param string
	 */
	public void setShinsainBukyokuName(String string) {
		shinsainBukyokuName = string;
	}

	/**
	 * @param string
	 */
	public void setShinsainNameKanjiMei(String string) {
		shinsainNameKanjiMei = string;
	}

	/**
	 * @param string
	 */
	public void setShinsainNameKanjiSei(String string) {
		shinsainNameKanjiSei = string;
	}

	/**
	 * @param string
	 */
	public void setShinsainNo(String string) {
		shinsainNo = string;
	}

	/**
	 * @param string
	 */
	public void setShinsainShokuName(String string) {
		shinsainShokuName = string;
	}

	/**
	 * @param string
	 */
	public void setShinsainShozokuName(String string) {
		shinsainShozokuName = string;
	}

	/**
	 * @param string
	 */
	public void setShokushuNameRyaku(String string) {
		shokushuNameRyaku = string;
	}

	/**
	 * @param string
	 */
	public void setShozokuNameRyaku(String string) {
		shozokuNameRyaku = string;
	}

	/**
	 * @param string
	 */
	public void setUketukeNo(String string) {
		uketukeNo = string;
	}

	/**
	 * @return
	 */
	public String getKeiNameRyaku() {
		return keiNameRyaku;
	}

	/**
	 * @param string
	 */
	public void setKeiNameRyaku(String string) {
		keiNameRyaku = string;
	}
	
//2005/10/17 整理番号追加
	/**
	 * @param string
	 */
	public void setSeiriNo(String string) {
		seiriNo = string;
	}

}
