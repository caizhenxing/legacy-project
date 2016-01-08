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

import java.util.ArrayList;
import java.util.List;

/**
 * 割り振り結果検索条件を保持するクラス。
 * 
 * ID RCSfile="$RCSfile: ShinsaJokyoSearchInfo.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:12 $"
 */
public class ShinsaJokyoSearchInfo extends SearchInfo{

	/**
     * <code>serialVersionUID</code> のコメント
     */
    private static final long serialVersionUID = -1427338328937100345L;

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/** 審査員番号 */
	private String shinsainNo;

	/** 審査員名（漢字-氏） */
	private String nameKanjiSei;

	/** 審査員名（漢字-名） */
	private String nameKanjiMei;

// 2006/07/03 dyh update start 理由：画面で審査員所属研究機関名を追加
	/** 審査員所属研究機関名 */
	private String shozokuName;
// 2006/07/03 dyh update end

//	/** 事業名 */
//	private String jigyoName;

	/** 年度 */
	private String nendo;

	/** 回数 */
	private String kaisu;

	/** １次審査入力方式 */
	private String shinsaType;

	/** 事業選択値リスト */
	private List values = new ArrayList();

	/** 審査員名（フリガナ-氏） */
	private String nameKanaSei;

	/** 審査員名（フリガナ-名） */
	private String nameKanaMei;

	/** 所属機関コード */
	private String shozokuCd;

	/** 系等の区分 */
	private String keiName;

	/** 審査状況 */
	private String shinsaJokyo;

	/** 事業ID */
	private String jigyoId;

	/** 事業区分 */
	private String jigyoKubun;
//最終ログイン日を追加
	/** 最終ログイン日 */
	private String loginDate;
	
	/** 最終ログインリスト */
	private List loginDateList = new ArrayList();

//利害関係者を追加
	/** 利害関係者リスト */
	private List rigaiKankeishaList = new ArrayList();	
	
	/** 利害関係者 */
	private String rigaiKankeisha;

//	表示方式を追加
	/** 表示方式 */
	private String hyojiHoshikiShinsaJokyo;
	
//整理番号を追加
	/** 整理番号（学創用） */
	private String seiriNo;

	/** 利害関係入力完了状況 */
	private String rigaiJokyo;

	//・・・・・・・・・・

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * コンストラクタ。
	 */
	public ShinsaJokyoSearchInfo() {
		super();
	}

	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------

//	/**
//	 * @return
//	 */
//	public String getJigyoName() {
//		return jigyoName;
//	}

	/**
     * 回数を取得
	 * @return 回数
	 */
	public String getKaisu() {
		return kaisu;
	}

    /**
     * 回数を設定
     * @param string 回数
     */
    public void setKaisu(String string) {
        kaisu = string;
    }

	/**
     * 審査員名（漢字-名）を取得
	 * @return 審査員名（漢字-名）
	 */
	public String getNameKanjiMei() {
		return nameKanjiMei;
	}

    /**
     * 審査員名（漢字-名）を設定
     * @param string 審査員名（漢字-名）
     */
    public void setNameKanjiMei(String string) {
        nameKanjiMei = string;
    }

	/**
     * 審査員名（漢字-氏）を取得
	 * @return 審査員名（漢字-氏）
	 */
	public String getNameKanjiSei() {
		return nameKanjiSei;
	}

    /**
     * 審査員名（漢字-氏）を設定
     * @param string 審査員名（漢字-氏）
     */
    public void setNameKanjiSei(String string) {
        nameKanjiSei = string;
    }

	/**
     * 年度を取得
	 * @return 年度
	 */
	public String getNendo() {
		return nendo;
	}

    /**
     * 年度を設定
     * @param string 年度
     */
    public void setNendo(String string) {
        nendo = string;
    }

	/**
     * 審査員番号を取得
	 * @return 審査員番号
	 */
	public String getShinsainNo() {
		return shinsainNo;
	}

    /**
     * 審査員番号を設定
     * @param string 審査員番号
     */
    public void setShinsainNo(String string) {
        shinsainNo = string;
    }

// 2006/07/03 dyh update start 理由：画面で審査員所属研究機関名を追加
	/**
     * 審査員所属研究機関名を取得
	 * @return 審査員所属研究機関名
	 */
	public String getShozokuName() {
		return shozokuName;
	}

    /**
     * 審査員所属研究機関名を設定
     * @param string 審査員所属研究機関名
     */
    public void setShozokuName(String string) {
        shozokuName = string;
    }
// 2006/07/03 dyh update end

//	/**
//	 * @param string
//	 */
//	public void setJigyoName(String string) {
//		jigyoName = string;
//	}

	/**
     * １次審査入力方式を取得
	 * @return １次審査入力方式
	 */
	public String getShinsaType() {
		return shinsaType;
	}

	/**
     * １次審査入力方式を設定
	 * @param string １次審査入力方式
	 */
	public void setShinsaType(String string) {
		shinsaType = string;
	}

	/**
     * 系等の区分を取得
	 * @return 系等の区分
	 */
	public String getKeiName() {
		return keiName;
	}

    /**
     * 系等の区分を設定
     * @param string 系等の区分
     */
    public void setKeiName(String string) {
        keiName = string;
    }

	/**
     * 審査員名（フリガナ-名）を取得
	 * @return 審査員名（フリガナ-名）
	 */
	public String getNameKanaMei() {
		return nameKanaMei;
	}

    /**
     * 審査員名（フリガナ-名）を設定
     * @param string 審査員名（フリガナ-名）
     */
    public void setNameKanaMei(String string) {
        nameKanaMei = string;
    }

	/**
     * 審査員名（フリガナ-氏）を取得
	 * @return 審査員名（フリガナ-氏）
	 */
	public String getNameKanaSei() {
		return nameKanaSei;
	}

    /**
     * 審査員名（フリガナ-氏）を設定
     * @param string 審査員名（フリガナ-氏）
     */
    public void setNameKanaSei(String string) {
        nameKanaSei = string;
    }

	/**
     * 審査状況を取得
	 * @return 審査状況
	 */
	public String getShinsaJokyo() {
		return shinsaJokyo;
	}

    /**
     * 審査状況を設定
     * @param string 審査状況
     */
    public void setShinsaJokyo(String string) {
        shinsaJokyo = string;
    }

	/**
     * 所属機関コードを取得
	 * @return 所属機関コード
	 */
	public String getShozokuCd() {
		return shozokuCd;
	}

    /**
     * 所属機関コードを設定
     * @param string 所属機関コード
     */
    public void setShozokuCd(String string) {
        shozokuCd = string;
    }

	/**
     * 事業選択値リストを取得
	 * @return 事業選択値リスト
	 */
	public List getValues() {
		return values;
	}

	/**
     * 事業選択値リストを設定
	 * @param list 事業選択値リスト
	 */
	public void setValues(List list) {
		values = list;
	}

	/**
     * 事業IDを取得
	 * @return 事業ID
	 */
	public String getJigyoId() {
		return jigyoId;
	}

    /**
     * 事業IDを設定
     * @param string 事業ID
     */
    public void setJigyoId(String string) {
        jigyoId = string;
    }

	/**
     * 事業区分を取得
	 * @return 事業区分
	 */
	public String getJigyoKubun() {
		return jigyoKubun;
	}

	/**
     * 事業区分を設定
	 * @param string 事業区分
	 */
	public void setJigyoKubun(String string) {
		jigyoKubun = string;
	}

//最終ログイン日追加　2005/10/24
	/**
     * 最終ログイン日を取得
     * @return 最終ログイン日
     */
    public String getLoginDate() {
        return loginDate;
    }

	/**
     * 最終ログイン日を設定
	 * @param string 最終ログイン日
	 */
	public void setLoginDate(String string) {
		loginDate = string;
	}
//	最終ログイン日追加	2005/10/25

	/**
     * 最終ログインリストを取得
     * @return 最終ログインリスト
     */
	public List getLoginDateList() {
		return loginDateList;
	}

	/**
     * 最終ログインリストを設定
	 * @param list 最終ログインリスト
	 */
	public void setLoginDateList(List list) {
		loginDateList = list;
	}
	
//	利害関係者追加		2005/10/25
	/**
     * 利害関係者リストを取得
	 * @return 利害関係者リスト
	 */
	public List getRigaiKankeishaList() {
		return rigaiKankeishaList;
	}
	
	/**
     * 利害関係者リストを設定
	 * @param list 利害関係者リスト
	 */
	public void setRigaiKankeishaList(List list) {
		rigaiKankeishaList = list;
	}

	/**
     * 利害関係者を取得
	 * @return 利害関係者
	 */
	public String getRigaiKankeisha() {
		return rigaiKankeisha;
	}

	/**
     * 利害関係者を設定
	 * @param string 利害関係者
	 */
	public void setRigaiKankeisha(String string) {
		rigaiKankeisha = string;
	}

//整理番号（学創用）追加	2005/11/2
	/**
     * 整理番号（学創用）を取得
	 * @return 整理番号（学創用）
	 */
	public String getSeiriNo() {
		return seiriNo;
	}

	/**
     * 整理番号（学創用）を設定
	 * @param string 整理番号（学創用）
	 */
	public void setSeiriNo(String string) {
		seiriNo = string;
	}

	/**
     * 表示方式を取得
	 * @return 表示方式
	 */
	public String getHyojiHoshikiShinsaJokyo() {
		return hyojiHoshikiShinsaJokyo;
	}
	
	/**
     * 表示方式を設定
	 * @param string 表示方式
	 */
	public void setHyojiHoshikiShinsaJokyo(String string) {
		hyojiHoshikiShinsaJokyo = string;
	}

	/**
     * rigaiJokyoを取得します。
     * 
     * @return rigaiJokyo
     */
    
    public String getRigaiJokyo() {
    	return rigaiJokyo;
    }

	/**
     * rigaiJokyoを設定します。
     * 
     * @param rigaiJokyo rigaiJokyo
     */
    
    public void setRigaiJokyo(String rigaiJokyo) {
    	this.rigaiJokyo = rigaiJokyo;
    }
}