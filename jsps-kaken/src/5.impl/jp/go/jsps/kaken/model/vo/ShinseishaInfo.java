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

import java.util.Date;

/**
 * 申請者情報を保持するクラス。
 * 
 * ID RCSfile="$RCSfile: ShinseishaInfo.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:12 $"
 */
public class ShinseishaInfo extends ShinseishaPk{

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/** 申請者ID */
	private String shinseishaId;

	/** 所属機関コード */
	private String shozokuCd;
	
	/** 所属機関名（和文） */
	private String shozokuName;

	/** 所属機関名（英文） */
	private String shozokuNameEigo;

	/** 所属機関名（略称） */
	private String shozokuNameRyaku;

	/** パスワード */
	private String password;
	
	/** 申請者氏名（漢字-姓） */
	private String nameKanjiSei;

	/** 申請者氏名（漢字-名） */
	private String nameKanjiMei;

	/** 申請者氏名（フリガナ-姓） */
	private String nameKanaSei;

	/** 申請者氏名（フリガナ-名） */
	private String nameKanaMei;
	
	/** 申請者氏名（ローマ字-姓） */
	private String nameRoSei;

	/** 申請者氏名（ローマ字-名） */
	private String nameRoMei;

	/** 部局コード */
	private String bukyokuCd;
	
	/** 部局名 */
	private String bukyokuName;

	/** 部局名（略称） */
	private String bukyokuNameRyaku;
	
	/** 部局種別 */
	private String bukyokuShubetuCd;
	
	/** 部局種別名 */
	private String bukyokuShubetuName;

	/** 職名コード */
	private String shokushuCd;

	/** 職名（和文） */
	private String shokushuNameKanji;

	/** 職名（略称） */
	private String shokushuNameRyaku;

	/** 科研研究者番号 */
	private String kenkyuNo;

	/** 非公募応募可フラグ */
	private String hikoboFlg;
	
	/** 備考 */
	private String biko;
	
	/** 削除フラグ*/
	private String delFlg;
	
	/** 有効期限 */
	private Date yukoDate; 
	
//	2005/03/28 追加 ここから---------------------------------
//	理由 生年月日を表示するため
	/** 生年月日 */
	private Date birthday;
//  追加 ここまで--------------------------------------------
	
//	2005/03/31 追加 ここから---------------------------------
//	理由 「発行者ID」「発行日」項目追加
	/** 発行者ID */
	private String hakkoshaId;
	
	/** 発行日 */
	private Date hakkoDate;
//	2006/02/09  追加 
	private String ouboshikaku;
//  追加 ここまで--------------------------------------------
    
    //宮　2006/06/16 ここから
    private String nenrei;
    //宮　ここまで
    
//  2006/06/20 苗　追加ここから
    /** 領域計画書（概要）フラグ */
    private boolean ryoikiGaiyoFlg;
//2006/06/20　苗　追加ここまで       
	
	//・・・・・・・・・・

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

    /**
	 * コンストラクタ。
	 */
	public ShinseishaInfo() {
		super();
	}

	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------


	/**
	 * @return
	 */
	public String getBiko() {
		return biko;
	}

	/**
	 * @return
	 */
	public String getBukyokuCd() {
		return bukyokuCd;
	}

	/**
	 * @return
	 */
	public String getBukyokuName() {
		return bukyokuName;
	}

	/**
	 * @return
	 */
	public String getBukyokuShubetuCd() {
		return bukyokuShubetuCd;
	}

	/**
	 * @return
	 */
	public String getBukyokuShubetuName() {
		return bukyokuShubetuName;
	}

	/**
	 * @return
	 */
	public String getDelFlg() {
		return delFlg;
	}

	/**
	 * @return
	 */
	public String getKenkyuNo() {
		return kenkyuNo;
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
	public String getNameRoMei() {
		return nameRoMei;
	}

	/**
	 * @return
	 */
	public String getNameRoSei() {
		return nameRoSei;
	}

	/**
	 * @return
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @return
	 */
	public String getShinseishaId() {
		return shinseishaId;
	}

	/**
	 * @return
	 */
	public String getShozokuCd() {
		return shozokuCd;
	}

	/**
	 * @return
	 */
	public String getShozokuName() {
		return shozokuName;
	}

	/**
	 * @return
	 */
	public String getShozokuNameEigo() {
		return shozokuNameEigo;
	}

	/**
	 * @return
	 */
	public Date getYukoDate() {
		return yukoDate;
	}

	/**
	 * @param string
	 */
	public void setBiko(String string) {
		biko = string;
	}

	/**
	 * @param string
	 */
	public void setBukyokuCd(String string) {
		bukyokuCd = string;
	}

	/**
	 * @param string
	 */
	public void setBukyokuName(String string) {
		bukyokuName = string;
	}

	/**
	 * @param string
	 */
	public void setBukyokuShubetuCd(String string) {
		bukyokuShubetuCd = string;
	}

	/**
	 * @param string
	 */
	public void setBukyokuShubetuName(String string) {
		bukyokuShubetuName = string;
	}

	/**
	 * @param string
	 */
	public void setDelFlg(String string) {
		delFlg = string;
	}

	/**
	 * @param string
	 */
	public void setKenkyuNo(String string) {
		kenkyuNo = string;
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
	public void setNameRoMei(String string) {
		nameRoMei = string;
	}

	/**
	 * @param string
	 */
	public void setNameRoSei(String string) {
		nameRoSei = string;
	}

	/**
	 * @param string
	 */
	public void setPassword(String string) {
		password = string;
	}

	/**
	 * @param string
	 */
	public void setShinseishaId(String string) {
		shinseishaId = string;
	}

	/**
	 * @param string
	 */
	public void setShozokuCd(String string) {
		shozokuCd = string;
	}

	/**
	 * @param string
	 */
	public void setShozokuName(String string) {
		shozokuName = string;
	}

	/**
	 * @param string
	 */
	public void setShozokuNameEigo(String string) {
		shozokuNameEigo = string;
	}

	/**
	 * @param date
	 */
	public void setYukoDate(Date date) {
		yukoDate = date;
	}

	/**
	 * @return
	 */
	public String getShozokuNameRyaku() {
		return shozokuNameRyaku;
	}

	/**
	 * @param string
	 */
	public void setShozokuNameRyaku(String string) {
		shozokuNameRyaku = string;
	}

	/**
	 * @return
	 */
	public String getBukyokuNameRyaku() {
		return bukyokuNameRyaku;
	}

	/**
	 * @return
	 */
	public String getHikoboFlg() {
		return hikoboFlg;
	}

	/**
	 * @return
	 */
	public String getNameKanaMei() {
		return nameKanaMei;
	}

	/**
	 * @return
	 */
	public String getNameKanaSei() {
		return nameKanaSei;
	}

	/**
	 * @return
	 */
	public String getShokushuCd() {
		return shokushuCd;
	}

	/**
	 * @return
	 */
	public String getShokushuNameKanji() {
		return shokushuNameKanji;
	}

	/**
	 * @return
	 */
	public String getShokushuNameRyaku() {
		return shokushuNameRyaku;
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
	public void setHikoboFlg(String string) {
		hikoboFlg = string;
	}

	/**
	 * @param string
	 */
	public void setNameKanaMei(String string) {
		nameKanaMei = string;
	}

	/**
	 * @param string
	 */
	public void setNameKanaSei(String string) {
		nameKanaSei = string;
	}

	/**
	 * @param string
	 */
	public void setShokushuCd(String string) {
		shokushuCd = string;
	}

	/**
	 * @param string
	 */
	public void setShokushuNameKanji(String string) {
		shokushuNameKanji = string;
	}

	/**
	 * @param string
	 */
	public void setShokushuNameRyaku(String string) {
		shokushuNameRyaku = string;
	}

//	2005/03/28 追加 ここから---------------------------------
//	理由 生年月日を表示するため
	/**
	 * @return
	 */
	public Date getBirthday() {
		return birthday;
	}

	/**
	 * @param date
	 */
	public void setBirthday(Date date) {
		birthday = date;
	}
//	追加 ここまで--------------------------------------------
	
//	2005/03/31 追加 ここから---------------------------------
//	理由 「発行者ID」「発行日」追加
	/**
	 * @return hakkoDate を戻します。
	 */
	public Date getHakkoDate() {
		return hakkoDate;
	}
	
	/**
	 * @param hakkoDate hakkoDate を設定。
	 */
	public void setHakkoDate(Date hakkoDate) {
		this.hakkoDate = hakkoDate;
	}
	/**
	 * @return hakkoshaID を戻します。
	 */
	public String getHakkoshaId() {
		return hakkoshaId;
	}
	/**
	 * @param hakkoshaID hakkoshaID を設定。
	 */
	public void setHakkoshaId(String hakkoshaID) {
		this.hakkoshaId = hakkoshaID;
	}
//追加 ここまで--------------------------------------------

	public String getOuboshikaku() {
		return ouboshikaku;
	}

	public void setOuboshikaku(String ouboshikaku) {
		this.ouboshikaku = ouboshikaku;
	}
    
    /**
     * @return nenrei を戻します。
     */
    public String getNenrei( )
    {
        return nenrei;
    }

    /**
     * @param nenrei 設定する nenrei。
     */
    public void setNenrei(String nenrei)
    {
        this.nenrei = nenrei;
    }

    /**
     * @return ryoikiGaiyoFlgを戻します
     */
    public boolean isRyoikiGaiyoFlg() {
        return ryoikiGaiyoFlg;
    }

    /**
     * @param ryoikiGaiyoFlgを設定する
     */
    public void setRyoikiGaiyoFlg(boolean ryoikiGaiyoFlg) {
        this.ryoikiGaiyoFlg = ryoikiGaiyoFlg;
    }

}
