/*======================================================================
 *    SYSTEM      : 日本学術振興会電子申請システム（科学研究費補助金）
 *    Source name : DaihyouInfo.java
 *    Description : 研究代表者情報を保持するクラス。
 *
 *    Author      : Admin
 *    Date        : 2003/07/16
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2003/07/16    V1.0        Admin          新規作成
 *====================================================================== 
 */
package jp.go.jsps.kaken.model.vo.shinsei;

import jp.go.jsps.kaken.model.vo.ShinseiDataPk;

/**
 * 研究代表者情報を保持するクラス。
 * 
 * ID RCSfile="$RCSfile: DaihyouInfo.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:30 $"
 */
public class DaihyouInfo extends ShinseiDataPk{

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/** 申請者氏名（漢字等-姓） */
	private String nameKanjiSei;
	
	/** 申請者氏名（漢字等-名） */
	private String nameKanjiMei;
	
	/** 申請者氏名（カナ-姓） */
	private String nameKanaSei;
	
	/** 申請者氏名（カナ-名） */
	private String nameKanaMei;

	/** 申請者氏名（ローマ字-姓） */
	private String nameRoSei;
	
	/** 申請者氏名（ローマ字-名） */
	private String nameRoMei;
	
	/** 年齢 */
	private String nenrei;
	
	/** 申請者研究者番号 */
	private String kenkyuNo;
	
	/** 所属機関コード */
	private String shozokuCd;
	
	/** 所属機関名 */
	private String shozokuName;
	
	/** 所属機関名（略称） */
	private String shozokuNameRyaku;
		
	/** 部局コード */
	private String bukyokuCd;
	
	/** 部局名 */
	private String bukyokuName;
	
	/** 部局名（略称） */
	private String bukyokuNameRyaku;	
	
	/** 職名コード */
	private String shokushuCd;
	
	/** 職名（和文） */
	private String shokushuNameKanji;
	
	/** 職名（略称） */
	private String shokushuNameRyaku;
	
	/** 郵便番号 */
	private String zip;
	
	/** 住所 */
	private String address;
	
	/** TEL */
	private String tel;
	
	/** FAX */
	private String fax;
	
	/** E-mail */
	private String email;
    
//2006/06/30 苗　追加ここから
    /** URL */
    private String url;
//2006/06/30　苗　追加ここまで       
	
	/** 現在の専門 */
	private String senmon;
	
	/** 学位 */
	private String gakui;
	
	/** エフォート */
	private String effort;

	/** 役割分担 */
	private String buntan;
	
	
	//・・・・・・・・・・

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * コンストラクタ。
	 */
	public DaihyouInfo() {
		super();
	}

	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------

	/**
     * 住所を取得
	 * @return 住所
	 */
	public String getAddress() {
		return address;
	}

	/**
     * 部局コードを取得
	 * @return 部局コード
	 */
	public String getBukyokuCd() {
		return bukyokuCd;
	}

	/**
     * 部局名を取得
	 * @return 部局名
	 */
	public String getBukyokuName() {
		return bukyokuName;
	}

	/**
     * 部局名（略称）を取得
	 * @return 部局名（略称）
	 */
	public String getBukyokuNameRyaku() {
		return bukyokuNameRyaku;
	}

	/**
     * 役割分担を取得
	 * @return 役割分担
	 */
	public String getBuntan() {
		return buntan;
	}

	/**
     * E-mailを取得
	 * @return E-mail
	 */
	public String getEmail() {
		return email;
	}

	/**
     * FAXを取得
	 * @return FAX
	 */
	public String getFax() {
		return fax;
	}

	/**
     * 学位を取得
	 * @return 学位
	 */
	public String getGakui() {
		return gakui;
	}

	/**
     * 申請者研究者番号を取得
	 * @return 申請者研究者番号
	 */
	public String getKenkyuNo() {
		return kenkyuNo;
	}

	/**
     * 申請者氏名（カナ-名）を取得
	 * @return 申請者氏名（カナ-名）
	 */
	public String getNameKanaMei() {
		return nameKanaMei;
	}

	/**
     * 申請者氏名（カナ-姓）を取得
	 * @return 申請者氏名（カナ-姓）
	 */
	public String getNameKanaSei() {
		return nameKanaSei;
	}

	/**
     * 申請者氏名（漢字等-名）を取得
	 * @return 申請者氏名（漢字等-名）
	 */
	public String getNameKanjiMei() {
		return nameKanjiMei;
	}

	/**
     * 申請者氏名（漢字等-姓）を取得
	 * @return 申請者氏名（漢字等-姓）
	 */
	public String getNameKanjiSei() {
		return nameKanjiSei;
	}

	/**
     * 申請者氏名（ローマ字-名）を取得
	 * @return 申請者氏名（ローマ字-名）
	 */
	public String getNameRoMei() {
		return nameRoMei;
	}

	/**
     * 申請者氏名（ローマ字-姓）を取得
	 * @return 申請者氏名（ローマ字-姓）
	 */
	public String getNameRoSei() {
		return nameRoSei;
	}

	/**
     * 年齢を取得
	 * @return 年齢
	 */
	public String getNenrei() {
		return nenrei;
	}

	/**
     * 現在の専門を取得
	 * @return 現在の専門
	 */
	public String getSenmon() {
		return senmon;
	}

	/**
     * 職名コードを取得
	 * @return 職名コード
	 */
	public String getShokushuCd() {
		return shokushuCd;
	}

	/**
     * 職名（和文）を取得
	 * @return 職名（和文）
	 */
	public String getShokushuNameKanji() {
		return shokushuNameKanji;
	}

	/**
     * 職名（略称）を取得
	 * @return 職名（略称）
	 */
	public String getShokushuNameRyaku() {
		return shokushuNameRyaku;
	}

	/**
     * 所属機関コードを取得
	 * @return 所属機関コード
	 */
	public String getShozokuCd() {
		return shozokuCd;
	}

	/**
     * 所属機関名を取得
	 * @return 所属機関名
	 */
	public String getShozokuName() {
		return shozokuName;
	}

	/**
     * 所属機関名（略称）を取得
	 * @return 所属機関名（略称）
	 */
	public String getShozokuNameRyaku() {
		return shozokuNameRyaku;
	}

	/**
     * TELを取得
	 * @return TEL
	 */
	public String getTel() {
		return tel;
	}

	/**
     * 郵便番号を取得
	 * @return 郵便番号
	 */
	public String getZip() {
		return zip;
	}

	/**
     * 住所を設定
	 * @param string 住所
	 */
	public void setAddress(String string) {
		address = string;
	}

	/**
     * 部局コードを設定
	 * @param string 部局コード
	 */
	public void setBukyokuCd(String string) {
		bukyokuCd = string;
	}

	/**
     * 部局名を設定
	 * @param string 部局名
	 */
	public void setBukyokuName(String string) {
		bukyokuName = string;
	}

	/**
     * 部局名（略称）を設定
	 * @param string 部局名（略称）
	 */
	public void setBukyokuNameRyaku(String string) {
		bukyokuNameRyaku = string;
	}

	/**
     * 役割分担を設定
	 * @param string 役割分担
	 */
	public void setBuntan(String string) {
		buntan = string;
	}

	/**
     * E-mailを設定
	 * @param string E-mail
	 */
	public void setEmail(String string) {
		email = string;
	}

	/**
     * FAXを設定
	 * @param string FAX
	 */
	public void setFax(String string) {
		fax = string;
	}

	/**
     * 学位を設定
	 * @param string 学位
	 */
	public void setGakui(String string) {
		gakui = string;
	}

	/**
     * 申請者研究者番号を設定
	 * @param string 申請者研究者番号
	 */
	public void setKenkyuNo(String string) {
		kenkyuNo = string;
	}

	/**
     * 申請者氏名（カナ-名）を設定
	 * @param string 申請者氏名（カナ-名）
	 */
	public void setNameKanaMei(String string) {
		nameKanaMei = string;
	}

	/**
     * 申請者氏名（カナ-姓）を設定
	 * @param string 申請者氏名（カナ-姓）
	 */
	public void setNameKanaSei(String string) {
		nameKanaSei = string;
	}

	/**
     * 申請者氏名（漢字等-名）を設定
	 * @param string 申請者氏名（漢字等-名）
	 */
	public void setNameKanjiMei(String string) {
		nameKanjiMei = string;
	}

	/**
     * 申請者氏名（漢字等-姓）を設定
	 * @param string 申請者氏名（漢字等-姓）
	 */
	public void setNameKanjiSei(String string) {
		nameKanjiSei = string;
	}

	/**
     * 申請者氏名（ローマ字-名）を設定
	 * @param string 申請者氏名（ローマ字-名）
	 */
	public void setNameRoMei(String string) {
		nameRoMei = string;
	}

	/**
     * 申請者氏名（ローマ字-姓）を設定
	 * @param string 申請者氏名（ローマ字-姓）
	 */
	public void setNameRoSei(String string) {
		nameRoSei = string;
	}

	/**
     * 年齢を設定
	 * @param string 年齢
	 */
	public void setNenrei(String string) {
		nenrei = string;
	}

	/**
     * 現在の専門を設定
	 * @param string 現在の専門
	 */
	public void setSenmon(String string) {
		senmon = string;
	}

	/**
     * 職名コードを設定
	 * @param string 職名コード
	 */
	public void setShokushuCd(String string) {
		shokushuCd = string;
	}

	/**
     * 職名（和文）を設定
	 * @param string 職名（和文）
	 */
	public void setShokushuNameKanji(String string) {
		shokushuNameKanji = string;
	}

	/**
     * 職名（略称）を設定
	 * @param string 職名（略称）
	 */
	public void setShokushuNameRyaku(String string) {
		shokushuNameRyaku = string;
	}

	/**
     * 所属機関コードを設定
	 * @param string 所属機関コード
	 */
	public void setShozokuCd(String string) {
		shozokuCd = string;
	}

	/**
     * 所属機関名を設定
	 * @param string 所属機関名
	 */
	public void setShozokuName(String string) {
		shozokuName = string;
	}

	/**
     * 所属機関名（略称）を設定
	 * @param string 所属機関名（略称）
	 */
	public void setShozokuNameRyaku(String string) {
		shozokuNameRyaku = string;
	}

	/**
     * TELを設定
	 * @param string TEL
	 */
	public void setTel(String string) {
		tel = string;
	}

	/**
     * 郵便番号を設定
	 * @param string 郵便番号
	 */
	public void setZip(String string) {
		zip = string;
	}
	
	/**
     * エフォートを取得
	 * @return String エフォート
	 */
	public String getEffort() {
		return effort;
	}
	
	/**
     * エフォートを設定
	 * @param effort エフォート
	 */
	public void setEffort(String effort) {
		this.effort = effort;
	}

    /**
     * URLを取得
     * @return String URL
     */
    public String getUrl() {
        return url;
    }

    /**
     * URLを設定
     * @param url URL
     */
    public void setUrl(String url) {
        this.url = url;
    }
}