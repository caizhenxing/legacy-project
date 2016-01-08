/*======================================================================
 *    SYSTEM      : 日本学術振興会電子申請システム（科学研究費補助金）
 *    Source name : ShinsaJokyoSearchForm.java
 *    Description : 審査状況検索フォーム
 *
 *    Author      : Admin
 *    Date        : 2003/11/14
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2003/11/14    V1.0        Admin          新規作成
 *    2006/07/03    V1.1        DIS.dyh        変更
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.gyomu.shinsaJokyo;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jp.go.jsps.kaken.web.struts.BaseSearchForm;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

/**
 *  審査状況検索フォーム
 * 
 * ID RCSfile="$RCSfile: ShinsaJokyoSearchForm.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:16 $"
 */
public class ShinsaJokyoSearchForm extends BaseSearchForm {

	/**
     * <code>serialVersionUID</code> のコメント
     */
    private static final long serialVersionUID = 5888050503516412637L;

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

	/** 事業名 */
	private String jigyoName;

	/** 年度 */
	private String nendo;

	/** 回数 */
	private String kaisu;

	/** 事業名選択リスト */
	private List jigyoNameList = new ArrayList();

	/** 事業名選択値リスト */
	private List values = new ArrayList();

	/** 審査員名（フリガナ-氏） */
	private String nameKanaSei;

	/** 審査員名（フリガナ-名） */
	private String nameKanaMei;

	/** 応募者所属研究機関番号 */
	private String shozokuCd;

	/** 系等の区分 */
	private String keiName;

	/** 審査状況リスト */
	private List shinsaJokyoList = new ArrayList();

	/** 審査状況 */
	private String shinsaJokyo;
	
//	最終ログイン日を追加
	/** 最終ログインリスト */
	private List loginDateList = new ArrayList();

	/** 最終ログイン日 */
    private String loginDate;

	/** システム番号 */
	private String systemNo;

	/** 事業区分 */
	private String jigyoKubun;

	/** 事業ID(再審査用) */
	private String jigyoId;
	
//利害関係者を追加
	/** 利害関係者リスト */
	private List rigaiKankeishaList = new ArrayList();
	
	/** 利害関係者 */
	private String rigaiKankeisha;
	
//表示方式を追加
	/** 表示方式 */
	private String hyojiHoshikiShinsaJokyo;

	/** 表示方式リスト */
	private List hyojiHoshikiListShinsaJokyo;	
	
//整理番号（学創用）を追加
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
	public ShinsaJokyoSearchForm() {
		super();
		init();
	}

	//---------------------------------------------------------------------
	// Public methods
	//---------------------------------------------------------------------

	/* 
	 * 初期化処理。
	 * (非 Javadoc)
	 * @see org.apache.struts.action.ActionForm#reset(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		//・・・
	}

	/**
	 * 初期化処理
	 */
	public void init() {
		shinsainNo = "";
		nameKanjiSei = "";
		nameKanjiMei = "";
		shozokuName = "";
		jigyoName = "";
		nendo = "";
		kaisu = "";
		values = new ArrayList();
		nameKanaSei = "";
		nameKanaMei = "";
		shozokuCd = "";
		keiName = ""; // 系等の区分
		shinsaJokyo = "";
		loginDate = "";// 最終ログイン日
		systemNo = "";
		jigyoKubun = "";
		jigyoId = "";
		hyojiHoshikiShinsaJokyo = "";
		seiriNo = "";
		rigaiJokyo = "";

	}

	/* 
	 * 入力チェック。
	 * (非 Javadoc)
	 * @see org.apache.struts.action.ActionForm#validate(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
	 */
	public ActionErrors validate(
		ActionMapping mapping,
		HttpServletRequest request) {

		//定型処理----- 
		ActionErrors errors = super.validate(mapping, request);
		//---------------------------------------------
		// 基本的なチェック(必須、形式等）はValidatorを使用する。
		//---------------------------------------------

		//基本入力チェックここまで
		if (!errors.isEmpty()) {
			return errors;
		}

		//定型処理----- 

		//追加処理----- 

		//---------------------------------------------
		//組み合わせチェック	
		//---------------------------------------------
		return errors;
	}

	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------

	/**
     * 事業名を取得
	 * @return String 事業名
	 */
	public String getJigyoName() {
		return jigyoName;
	}

    /**
     * 事業名を設定
     * @param string 事業名
     */
    public void setJigyoName(String string) {
        jigyoName = string;
    }

	/**
     * 事業名選択リストを取得
	 * @return List 事業名選択リスト
	 */
	public List getJigyoNameList() {
		return jigyoNameList;
	}

    /**
     * 事業名選択リストを設定
     * @param list 事業名選択リスト
     */
    public void setJigyoNameList(List list) {
        jigyoNameList = list;
    }

	/**
     * 回数を取得
	 * @return String 回数
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
	 * @return String 審査員名（漢字-名）
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
	 * @return String 審査員名（漢字-氏）
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
	 * @return String 年度
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
	 * @return String 審査員番号
	 */
	public String getShinsainNo() {
		return shinsainNo;
	}

    /**
     * 審査員番号
     * @param string 審査員番号
     */
    public void setShinsainNo(String string) {
        shinsainNo = string;
    }

// 2006/07/03 dyh update start 理由：画面で審査員所属研究機関名を追加
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
// 2006/07/03 dyh update end

	/**
     * 系等の区分を取得
	 * @return String 系等の区分
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
     * 応募者所属研究機関番号を取得
	 * @return 応募者所属研究機関番号
	 */
	public String getShozokuCd() {
		return shozokuCd;
	}

    /**
     * 応募者所属研究機関番号を設定
     * @param string 応募者所属研究機関番号
     */
    public void setShozokuCd(String string) {
        shozokuCd = string;
    }

    /**
     * 審査員名（フリガナ-名）を取得
     * @return String 審査員名（フリガナ-名）
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
     * 審査状況リストを取得
	 * @return List 審査状況リスト
	 */
	public List getShinsaJokyoList() {
		return shinsaJokyoList;
	}

	/**
     * 審査状況リストを設定
	 * @param list 審査状況リスト
	 */
	public void setShinsaJokyoList(List list) {
		shinsaJokyoList = list;
	}

	/**
     * 事業名選択値リストを取得
	 * @return List 事業名選択値リスト
	 */
	public List getValues() {
		return values;
	}

	/**
     * 事業名選択値リストを設定
	 * @param list 事業名選択値リスト
	 */
	public void setValues(List list) {
		values = list;
	}

	/**
     * 事業名選択値リストを取得
     * @param key
	 * @return Object 事業名選択値リスト
	 */
	public Object getValues(int key) {
		return values.get(key);
	}

	/**
     * 事業名選択値リストを設定
     * @param key
     * @param value
	 * @param string 事業名選択値リスト
	 */
	public void setValues(int key, Object value) {
        if (!values.contains(value)) {
            values.add(value);
        }
	}

	/**
     * 事業区分を取得
	 * @return String 事業区分
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
     * システム番号を取得
	 * @return String システム番号
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
     * 事業ID(再審査用)を取得
	 * @return String 事業ID(再審査用)
	 */
	public String getJigyoId() {
		return jigyoId;
	}

	/**
     * 事業ID(再審査用)を設定
	 * @param string 事業ID(再審査用)
	 */
	public void setJigyoId(String string) {
		jigyoId = string;
	}

//最終ログイン日追加	2005/10/24
	/**
     * 最終ログインリストを取得
	 * @return List 最終ログインリスト
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

	/**
     * 最終ログイン日を取得
	 * @return String 最終ログイン日
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

//利害関係者追加	2005/10/25
	/**
     * 利害関係者リストを取得
	 * @return List 利害関係者リスト
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
	 * @return String 利害関係者
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

//表示方式を追加
	/**
     * 表示方式を取得
	 * @return String 表示方式
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
     * 表示方式リストを取得
	 * @return List 表示方式リスト
	 */
	public List getHyojiHoshikiListShinsaJokyo() {
		return hyojiHoshikiListShinsaJokyo;
	}

	/**
     * 表示方式リストを設定
	 * @param list 表示方式リスト
	 */
	public void setHyojiHoshikiListShinsaJokyo(List list) {
		hyojiHoshikiListShinsaJokyo = list;
	}
		
//整理番号を追加
	/**
     * 整理番号（学創用）を取得
	 * @return String 整理番号（学創用）
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