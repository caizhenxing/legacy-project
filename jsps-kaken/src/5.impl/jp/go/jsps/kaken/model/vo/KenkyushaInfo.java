/*
 * 作成日: 2005/03/28
 *
 */
package jp.go.jsps.kaken.model.vo;

import java.util.Date;

/**
 * 研究者情報を保持するクラス.
 * 
 * @author yoshikawa_h
 *
 */
public class KenkyushaInfo extends KenkyushaPk {
	
	/**
     * <code>serialVersionUID</code> のコメント
     */
    private static final long serialVersionUID = -2256739159057758578L;

	/** 研究者番号 */
	private String kenkyuNo;
	
	/** 申請者氏名（漢字-姓） */
	private String nameKanjiSei;

	/** 申請者氏名（漢字-名） */
	private String nameKanjiMei;

	/** 申請者氏名（フリガナ-姓） */
	private String nameKanaSei;

	/** 申請者氏名（フリガナ-名） */
	private String nameKanaMei;
	
	/** 性別 */
	private String seibetsu;

	/** 生年月日 */
	private Date birthday;

	/** 学位 */
	private String gakui;
	
	/** 所属機関コード */
	private String shozokuCd;
	
	/** 所属機関名（和文） */
	private String shozokuNameKanji;
	
	/** 所属機関名（英文） */
	private String shozokuNameEigo;
	
	/** 所属機関名（略称） */
	private String shozokuRyakusho;
	
	/** 部局コード */
	private String bukyokuCd;
	
	/** 部局名 */
	private String bukyokuName;
	
	/** 部局略称 */
	private String bukyokuNameRyaku;
	
	/** 職名コード */
	private String shokushuCd;
	
	/** 職名 */
	private String shokushuName;
	
	/** 職名略称 */
	private String shokushuNameRyaku;

	/** 他の機関1（委嘱先マーク） */
	private String otherKikanFlg1;
	
	/** 他の機関番号1 */
	private String otherKikanCd1;

	/** 他の機関名1 */
	private String otherKikanName1;

	/** 他の機関2（委嘱先マーク） */
	private String otherKikanFlg2;

	/** 他の機関番号2 */
	private String otherKikanCd2;

	/** 他の機関名2 */
	private String otherKikanName2;

	/** 他の機関3（委嘱先マーク） */
	private String otherKikanFlg3;

	/** 他の機関番号3 */
	private String otherKikanCd3;

	/** 他の機関名3 */
	private String otherKikanName3;

	/** 他の機関4（委嘱先マーク） */
	private String otherKikanFlg4;

	/** 他の機関番号4 */
	private String otherKikanCd4;

	/** 他の機関名4 */
	private String otherKikanName4;

	
	
	
	/** データ更新日時 */
	private Date koshinDate;
	
	/** 備考 */
	private String biko;
	
	
	//2005/04/22 追加 ここから----------------------------------------------
	//削除フラグ追加
	
	/** 削除フラグ */
	private String delFlg;
	
	//2006/02/08 追加 ここから----------------------------------------------
	//応募資格追加
	
	/** 応募資格 */
	private String ouboShikaku;
	
	//追加 ここまで---------------------------------------------------------
	
	//・・・・・・・・・・

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * コンストラクタ。
	 */
	public KenkyushaInfo() {
		super();
	}
	 
	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------
	/**
	 * @return biko を戻します。
	 */
	public String getBiko() {
		return biko;
	}
	/**
	 * @param biko biko を設定。
	 */
	public void setBiko(String biko) {
		this.biko = biko;
	}
	/**
	 * @return birthday を戻します。
	 */
	public Date getBirthday() {
		return birthday;
	}
	/**
	 * @param birthday birthday を設定。
	 */
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	/**
	 * @return bukyokuCd を戻します。
	 */
	public String getBukyokuCd() {
		return bukyokuCd;
	}
	/**
	 * @param bukyokuCd bukyokuCd を設定。
	 */
	public void setBukyokuCd(String bukyokuCd) {
		this.bukyokuCd = bukyokuCd;
	}
	/**
	 * @return gakui を戻します。
	 */
	public String getGakui() {
		return gakui;
	}
	/**
	 * @param gakui gakui を設定。
	 */
	public void setGakui(String gakui) {
		this.gakui = gakui;
	}
	/**
	 * @return kenkyuNo を戻します。
	 */
	public String getKenkyuNo() {
		return kenkyuNo;
	}
	/**
	 * @param kenkyuNo kenkyuNo を設定。
	 */
	public void setKenkyuNo(String kenkyuNo) {
		this.kenkyuNo = kenkyuNo;
	}
	/**
	 * @return koshinDate を戻します。
	 */
	public Date getKoshinDate() {
		return koshinDate;
	}
	/**
	 * @param koshinDate koshinDate を設定。
	 */
	public void setKoshinDate(Date koshinDate) {
		this.koshinDate = koshinDate;
	}
	/**
	 * @return nameKanaMei を戻します。
	 */
	public String getNameKanaMei() {
		return nameKanaMei;
	}
	/**
	 * @param nameKanaMei nameKanaMei を設定。
	 */
	public void setNameKanaMei(String nameKanaMei) {
		this.nameKanaMei = nameKanaMei;
	}
	/**
	 * @return nameKanaSei を戻します。
	 */
	public String getNameKanaSei() {
		return nameKanaSei;
	}
	/**
	 * @param nameKanaSei nameKanaSei を設定。
	 */
	public void setNameKanaSei(String nameKanaSei) {
		this.nameKanaSei = nameKanaSei;
	}
	/**
	 * @return nameKanjiMei を戻します。
	 */
	public String getNameKanjiMei() {
		return nameKanjiMei;
	}
	/**
	 * @param nameKanjiMei nameKanjiMei を設定。
	 */
	public void setNameKanjiMei(String nameKanjiMei) {
		this.nameKanjiMei = nameKanjiMei;
	}
	/**
	 * @return nameKanjiSei を戻します。
	 */
	public String getNameKanjiSei() {
		return nameKanjiSei;
	}
	/**
	 * @param nameKanjiSei nameKanjiSei を設定。
	 */
	public void setNameKanjiSei(String nameKanjiSei) {
		this.nameKanjiSei = nameKanjiSei;
	}
	/**
	 * @return seibetsu を戻します。
	 */
	public String getSeibetsu() {
		return seibetsu;
	}
	/**
	 * @param seibetsu seibetsu を設定。
	 */
	public void setSeibetsu(String seibetsu) {
		this.seibetsu = seibetsu;
	}
	/**
	 * @return shokushuCd を戻します。
	 */
	public String getShokushuCd() {
		return shokushuCd;
	}
	/**
	 * @param shokushuCd shokushuCd を設定。
	 */
	public void setShokushuCd(String shokushuCd) {
		this.shokushuCd = shokushuCd;
	}
	/**
	 * @return shozokuCd を戻します。
	 */
	public String getShozokuCd() {
		return shozokuCd;
	}
	/**
	 * @param shozokuCd shozokuCd を設定。
	 */
	public void setShozokuCd(String shozokuCd) {
		this.shozokuCd = shozokuCd;
	}
	
	
	/**
	 * @return bukyokuName を戻します。
	 */
	public String getBukyokuName() {
		return bukyokuName;
	}
	/**
	 * @param bukyokuName bukyokuName を設定。
	 */
	public void setBukyokuName(String bukyokuName) {
		this.bukyokuName = bukyokuName;
	}
	/**
	 * @return bukyokuNameRyaku を戻します。
	 */
	public String getBukyokuNameRyaku() {
		return bukyokuNameRyaku;
	}
	/**
	 * @param bukyokuNameRyaku bukyokuNameRyaku を設定。
	 */
	public void setBukyokuNameRyaku(String bukyokuNameRyaku) {
		this.bukyokuNameRyaku = bukyokuNameRyaku;
	}
	/**
	 * @return shokushuName を戻します。
	 */
	public String getShokushuName() {
		return shokushuName;
	}
	/**
	 * @param shokushuName shokushuName を設定。
	 */
	public void setShokushuName(String shokushuName) {
		this.shokushuName = shokushuName;
	}
	/**
	 * @return shokushuNameRyaku を戻します。
	 */
	public String getShokushuNameRyaku() {
		return shokushuNameRyaku;
	}
	/**
	 * @param shokushuNameRyaku shokushuNameRyaku を設定。
	 */
	public void setShokushuNameRyaku(String shokushuNameRyaku) {
		this.shokushuNameRyaku = shokushuNameRyaku;
	}
	/**
	 * @return shozokuNameEig を戻します。
	 */
	public String getShozokuNameEigo() {
		return shozokuNameEigo;
	}
	/**
	 * @param shozokuNameEig shozokuNameEig を設定。
	 */
	public void setShozokuNameEigo(String shozokuNameEig) {
		this.shozokuNameEigo = shozokuNameEig;
	}
	/**
	 * @return shozokuNameKanji を戻します。
	 */
	public String getShozokuNameKanji() {
		return shozokuNameKanji;
	}
	/**
	 * @param shozokuNameKanji shozokuNameKanji を設定。
	 */
	public void setShozokuNameKanji(String shozokuNameKanji) {
		this.shozokuNameKanji = shozokuNameKanji;
	}
	/**
	 * @return shozokuRyakusho を戻します。
	 */
	public String getShozokuRyakusho() {
		return shozokuRyakusho;
	}
	/**
	 * @param shozokuRyakusho shozokuRyakusho を設定。
	 */
	public void setShozokuRyakusho(String shozokuRyakusho) {
		this.shozokuRyakusho = shozokuRyakusho;
	}
	
	//2005/04/22 追加 ここから----------------------------------------------
	//削除フラグ追加
		
	/**
	 * @return
	 */
	public String getDelFlg() {
		return delFlg;
	}

	/**
	 * @param string
	 */
	public void setDelFlg(String string) {
		delFlg = string;
	}
	//2006/02/08 追加 ここから----------------------------------------------
	//応募資格追加
	
	public String getOuboShikaku() {
		return ouboShikaku;
	}

	public void setOuboShikaku(String ouboShikaku) {
		this.ouboShikaku = ouboShikaku;
	}

	/**
     * otherKikanCd1を取得します。
     * 
     * @return otherKikanCd1
     */
    
    public String getOtherKikanCd1() {
    	return otherKikanCd1;
    }

	/**
     * otherKikanCd1を設定します。
     * 
     * @param otherKikanCd1 otherKikanCd1
     */
    
    public void setOtherKikanCd1(String otherKikanCd1) {
    	this.otherKikanCd1 = otherKikanCd1;
    }

	/**
     * otherKikanCd2を取得します。
     * 
     * @return otherKikanCd2
     */
    
    public String getOtherKikanCd2() {
    	return otherKikanCd2;
    }

	/**
     * otherKikanCd2を設定します。
     * 
     * @param otherKikanCd2 otherKikanCd2
     */
    
    public void setOtherKikanCd2(String otherKikanCd2) {
    	this.otherKikanCd2 = otherKikanCd2;
    }

	/**
     * otherKikanCd3を取得します。
     * 
     * @return otherKikanCd3
     */
    
    public String getOtherKikanCd3() {
    	return otherKikanCd3;
    }

	/**
     * otherKikanCd3を設定します。
     * 
     * @param otherKikanCd3 otherKikanCd3
     */
    
    public void setOtherKikanCd3(String otherKikanCd3) {
    	this.otherKikanCd3 = otherKikanCd3;
    }

	/**
     * otherKikanCd4を取得します。
     * 
     * @return otherKikanCd4
     */
    
    public String getOtherKikanCd4() {
    	return otherKikanCd4;
    }

	/**
     * otherKikanCd4を設定します。
     * 
     * @param otherKikanCd4 otherKikanCd4
     */
    
    public void setOtherKikanCd4(String otherKikanCd4) {
    	this.otherKikanCd4 = otherKikanCd4;
    }

	/**
     * otherKikanFlg1を取得します。
     * 
     * @return otherKikanFlg1
     */
    
    public String getOtherKikanFlg1() {
    	return otherKikanFlg1;
    }

	/**
     * otherKikanFlg1を設定します。
     * 
     * @param otherKikanFlg1 otherKikanFlg1
     */
    
    public void setOtherKikanFlg1(String otherKikanFlg1) {
    	this.otherKikanFlg1 = otherKikanFlg1;
    }

	/**
     * otherKikanFlg2を取得します。
     * 
     * @return otherKikanFlg2
     */
    
    public String getOtherKikanFlg2() {
    	return otherKikanFlg2;
    }

	/**
     * otherKikanFlg2を設定します。
     * 
     * @param otherKikanFlg2 otherKikanFlg2
     */
    
    public void setOtherKikanFlg2(String otherKikanFlg2) {
    	this.otherKikanFlg2 = otherKikanFlg2;
    }

	/**
     * otherKikanFlg3を取得します。
     * 
     * @return otherKikanFlg3
     */
    
    public String getOtherKikanFlg3() {
    	return otherKikanFlg3;
    }

	/**
     * otherKikanFlg3を設定します。
     * 
     * @param otherKikanFlg3 otherKikanFlg3
     */
    
    public void setOtherKikanFlg3(String otherKikanFlg3) {
    	this.otherKikanFlg3 = otherKikanFlg3;
    }

	/**
     * otherKikanFlg4を取得します。
     * 
     * @return otherKikanFlg4
     */
    
    public String getOtherKikanFlg4() {
    	return otherKikanFlg4;
    }

	/**
     * otherKikanFlg4を設定します。
     * 
     * @param otherKikanFlg4 otherKikanFlg4
     */
    
    public void setOtherKikanFlg4(String otherKikanFlg4) {
    	this.otherKikanFlg4 = otherKikanFlg4;
    }

	/**
     * otherKikanName1を取得します。
     * 
     * @return otherKikanName1
     */
    
    public String getOtherKikanName1() {
    	return otherKikanName1;
    }

	/**
     * otherKikanName1を設定します。
     * 
     * @param otherKikanName1 otherKikanName1
     */
    
    public void setOtherKikanName1(String otherKikanName1) {
    	this.otherKikanName1 = otherKikanName1;
    }

	/**
     * otherKikanName2を取得します。
     * 
     * @return otherKikanName2
     */
    
    public String getOtherKikanName2() {
    	return otherKikanName2;
    }

	/**
     * otherKikanName2を設定します。
     * 
     * @param otherKikanName2 otherKikanName2
     */
    
    public void setOtherKikanName2(String otherKikanName2) {
    	this.otherKikanName2 = otherKikanName2;
    }

	/**
     * otherKikanName3を取得します。
     * 
     * @return otherKikanName3
     */
    
    public String getOtherKikanName3() {
    	return otherKikanName3;
    }

	/**
     * otherKikanName3を設定します。
     * 
     * @param otherKikanName3 otherKikanName3
     */
    
    public void setOtherKikanName3(String otherKikanName3) {
    	this.otherKikanName3 = otherKikanName3;
    }

	/**
     * otherKikanName4を取得します。
     * 
     * @return otherKikanName4
     */
    
    public String getOtherKikanName4() {
    	return otherKikanName4;
    }

	/**
     * otherKikanName4を設定します。
     * 
     * @param otherKikanName4 otherKikanName4
     */
    
    public void setOtherKikanName4(String otherKikanName4) {
    	this.otherKikanName4 = otherKikanName4;
    }


	
}
