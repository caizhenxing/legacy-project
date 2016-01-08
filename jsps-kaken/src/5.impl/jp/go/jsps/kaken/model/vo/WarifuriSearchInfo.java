/*======================================================================
 *    SYSTEM      : 日本学術振興会電子申請システム（科学研究費補助金）
 *    Source name : WarifuriSearchInfo.java
 *    Description : 割り振り結果検索条件を保持するクラス
 *
 *    Author      : Admin
 *    Date        : 2003/07/16
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2003/07/16    V1.0        Admin          新規作成
 *    2006/07/03    V1.1        DIS.dyh        変更
 *====================================================================== 
 */
package jp.go.jsps.kaken.model.vo;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 割り振り結果検索条件を保持するクラス。
 * 
 * ID RCSfile="$RCSfile: WarifuriSearchInfo.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:14 $"
 */
public class WarifuriSearchInfo extends SearchInfo{

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------
	
	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------
	/** システム番号 */
	private String systemNo;

	/** 申請番号 */
	private String uketukeNo;

	/** 事業区分 */
	private String jigyoKubun;
	
	/** 事業コード */
	private String jigyoCd;

	/** 年度 */
	private String nendo;

	/** 回数 */
	private String kaisu;

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

	/** 所属機関コード */
	private String shozokuCd;	
	
	/** 細目番号 */
	private String bunkaSaimokuCd;
	
	/** 系等の区分 */
	private String keiName;

// 2006/07/03 dyh add start 理由：画面で審査員所属研究機関名を追加
    /** 審査員所属研究機関名 */
    private String shozokuName;
// 2006/07/03 dyh add end

//2005/10/17追加
	/** 整理番号 */
	private String seiriNo;
	
	/** 事業選択値 */
	private List jigyoCdValueList = new ArrayList();

	/** 担当事業区分（複数） */
	private Set tantoJigyoKubun;

	/** 審査員番号 */
	private String shinsainNo;

//	2005/11/8 追加
	/** 利害関係者 */
	private String rigai;
	
	/** 割振り */
	private String warifuriFlg;
//	2005/11/8 追加完了
	
	//・・・・・・・・・・

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------
	/**
	 * コンストラクタ。
	 */
	public WarifuriSearchInfo() {
		super();
	}

	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------
	/**
     * 細目番号を取得
	 * @return 細目番号
	 */
	public String getBunkaSaimokuCd() {
		return bunkaSaimokuCd;
	}

    /**
     * 細目番号を設定
     * @param string 細目番号
     */
    public void setBunkaSaimokuCd(String string) {
        bunkaSaimokuCd = string;
    }

	/**
     * 事業コードを取得
	 * @return 事業コード
	 */
	public String getJigyoCd() {
		return jigyoCd;
	}

    /**
     * 事業コードを設定
     * @param string 事業コード
     */
    public void setJigyoCd(String string) {
        jigyoCd = string;
    }

	/**
     * 事業選択値を取得
	 * @return 事業選択値
	 */
	public List getJigyoCdValueList() {
		return jigyoCdValueList;
	}

    /**
     * 事業選択値を設定
     * @param list 事業選択値
     */
    public void setJigyoCdValueList(List list) {
        jigyoCdValueList = list;
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

// 2006/07/03 dyh add start 理由：画面で審査員所属研究機関名を追加
    /**
     * 審査員所属研究機関名を取得
     * @return String 審査員所属研究機関名
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
// 2006/07/03 dyh add end

	/**
     * 申請者氏名（フリガナ-名）を取得
	 * @return 申請者氏名（フリガナ-名）
	 */
	public String getNameKanaMei() {
		return nameKanaMei;
	}

    /**
     * 申請者氏名（フリガナ-名）を設定
     * @param string 申請者氏名（フリガナ-名）
     */
    public void setNameKanaMei(String string) {
        nameKanaMei = string;
    }

	/**
     * 申請者氏名（フリガナ-姓）を取得
	 * @return 申請者氏名（フリガナ-姓）
	 */
	public String getNameKanaSei() {
		return nameKanaSei;
	}

    /**
     * 申請者氏名（フリガナ-姓）を設定
     * @param string 申請者氏名（フリガナ-姓）
     */
    public void setNameKanaSei(String string) {
        nameKanaSei = string;
    }

	/**
     * 申請者氏名（漢字-名）を取得
	 * @return 申請者氏名（漢字-名）
	 */
	public String getNameKanjiMei() {
		return nameKanjiMei;
	}

    /**
     * 申請者氏名（漢字-名）を設定
     * @param string 申請者氏名（漢字-名）
     */
    public void setNameKanjiMei(String string) {
        nameKanjiMei = string;
    }

	/**
     * 申請者氏名（漢字-姓）を取得
	 * @return 申請者氏名（漢字-姓）
	 */
	public String getNameKanjiSei() {
		return nameKanjiSei;
	}

    /**
     * 申請者氏名（漢字-姓）を設定
     * @param string 申請者氏名（漢字-姓）
     */
    public void setNameKanjiSei(String string) {
        nameKanjiSei = string;
    }

	/**
     * 申請者氏名（ローマ字-名）を取得
	 * @return 申請者氏名（ローマ字-名）
	 */
	public String getNameRoMei() {
		return nameRoMei;
	}

    /**
     * 申請者氏名（ローマ字-名）を設定
     * @param string 申請者氏名（ローマ字-名）
     */
    public void setNameRoMei(String string) {
        nameRoMei = string;
    }

	/**
     * 申請者氏名（ローマ字-姓）を取得
	 * @return 申請者氏名（ローマ字-姓）
	 */
	public String getNameRoSei() {
		return nameRoSei;
	}

    /**
     * 申請者氏名（ローマ字-姓）を設定
     * @param string 申請者氏名（ローマ字-姓）
     */
    public void setNameRoSei(String string) {
        nameRoSei = string;
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
     * システム番号を取得
	 * @return システム番号
	 */
	public String getSystemNo() {
		return systemNo;
	}

    /**
     * システム番号を設定
     * @param string システム番号
     */
    public void setSystemNo(String string) {
        systemNo = string;
    }

	/**
     * 担当事業区分（複数）を取得
	 * @return 担当事業区分（複数）
	 */
	public Set getTantoJigyoKubun() {
		return tantoJigyoKubun;
	}

	/**
     * 申請番号を取得
	 * @return 申請番号
	 */
	public String getUketukeNo() {
		return uketukeNo;
	}

    /**
     * 申請番号を設定
     * @param string 申請番号
     */
    public void setUketukeNo(String string) {
        uketukeNo = string;
    }

	/**
     * 担当事業区分（複数）を設定
	 * @param set 担当事業区分（複数）
	 */
	public void setTantoJigyoKubun(Set set) {
		tantoJigyoKubun = set;
	}

//2005/10/17　整理番号追加
	/**
     * 整理番号を取得
	 * @return 整理番号
	 */
	public String getSeiriNo() {
		return seiriNo;
	}

	/**
     * 整理番号を設定
	 * @param string 整理番号
	 */
	public void setSeiriNo(String string) {
		seiriNo = string;
	}

	/**
	 * 利害関係者を取得します。
	 * @return 利害関係者
	 */
	public String getRigai() {
		return rigai;
	}

	/**
	 * 利害関係者を設定します。
	 * @param rigai 利害関係者
	 */
	public void setRigai(String rigai) {
		this.rigai = rigai;
	}

	/**
	 * 割振りを取得します。
	 * @return 割振り
	 */
	public String getWarifuriFlg() {
		return warifuriFlg;
	}

	/**
	 * 割振りを設定します。
	 * @param warifuriFlg 割振り
	 */
	public void setWarifuriFlg(String warifuriFlg) {
		this.warifuriFlg = warifuriFlg;
	}
}