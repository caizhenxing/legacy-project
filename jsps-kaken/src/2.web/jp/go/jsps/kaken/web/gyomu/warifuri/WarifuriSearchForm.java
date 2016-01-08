/*======================================================================
 *    SYSTEM      : 日本学術振興会電子申請システム（科学研究費補助金）
 *    Source name : WarifuriSearchForm.java
 *    Description : 割り振り結果検索フォーム
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
package jp.go.jsps.kaken.web.gyomu.warifuri;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jp.go.jsps.kaken.model.common.*;
import jp.go.jsps.kaken.web.struts.BaseSearchForm;

import org.apache.struts.action.*;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

/**
 *  割り振り結果検索フォーム
 * 
 * ID RCSfile="$RCSfile: WarifuriSearchForm.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:21 $"
 */
public class WarifuriSearchForm extends BaseSearchForm {

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/** 申請番号 */
	private String uketukeNo;

	/** 事業コード */
	private String jigyoCd;

	/** 年度 */
	private String nendo;

	/** 回数 */
	private String kaisu;

	/** 申請者氏名（漢字等-姓） */
	private String nameKanjiSei;
	
	/** 申請者氏名（漢字等-名） */
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

	/** 整理番号 */
	private String seiriNo;	
	
//	2005/11/8 追加
	/** 利害関係者 */
	private String rigai;
	
	/** 割振り */
	private String warifuriFlg;
//	2005/11/8 追加完了
	
	/** 事業名選択リスト */
	private List jigyoNameList = new ArrayList();
	
	/** 事業選択値 */
	//チェックボックスのプロパティ名はvaluesで統一されてるので、変更すると値がセットされないので注意
	private List values = new ArrayList();
	//・・・・・・・・・・

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * コンストラクタ。
	 */
	public WarifuriSearchForm() {
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
		uketukeNo = "";          // 申請番号
		jigyoCd = "";            // 事業コード
		nendo = "";              // 年度
		kaisu = "";              // 回数
	    nameKanjiSei = "";       // 申請者氏名（漢字等-姓）
	    nameKanjiMei = "";       // 申請者氏名（漢字等-名）
	    nameKanaSei = "";        // 申請者氏名（フリガナ-姓）
	    nameKanaMei = "";        // 申請者氏名（フリガナ-名）
	    nameRoSei = "";          // 申請者氏名（ローマ字-姓）
	    nameRoMei = "";          // 申請者氏名（ローマ字-名）
	    shozokuCd = "";          // 所属機関コード
	    bunkaSaimokuCd = "";     // 細目番号
	    keiName = "";            // 系等の区分
        shozokuName = "";        // 審査員所属研究機関名
	    seiriNo = "";            // 整理番号
	    rigai = "";              // 利害関係者
	    warifuriFlg = "";        // 割振り
		values = new ArrayList();// 事業選択値
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
		
		//学術創成用と基盤研究等用の両種目をチェックボックスで選択していた場合はエラー（チェックボックスの選択は必須）
		List list = getValueList();
		
		if(list.size() == 0){
			errors.add(
				ActionErrors.GLOBAL_ERROR,
					new ActionError("errors.2002", new String[]{"研究種目名"}));
		}
		
		for(int i = 0; i < list.size(); i++){
			if(list.size() >= 2 && IJigyoCd.JIGYO_CD_GAKUSOU_HIKOUBO.equals((String)list.get(i))){
				errors.add(
					ActionErrors.GLOBAL_ERROR,
					new ActionError("errors.5062", new String[]{"研究種目名"}));	
			}
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
     * 事業名選択リストを取得
	 * @return 事業名選択リスト
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
     * 申請者氏名（漢字等-名）を取得
	 * @return 申請者氏名（漢字等-名）
	 */
	public String getNameKanjiMei() {
		return nameKanjiMei;
	}

    /**
     * 申請者氏名（漢字等-名）を設定
     * @param string 申請者氏名（漢字等-名）
     */
    public void setNameKanjiMei(String string) {
        nameKanjiMei = string;
    }

	/**
     * 申請者氏名（漢字等-姓）を取得
	 * @return 申請者氏名（漢字等-姓）
	 */
	public String getNameKanjiSei() {
		return nameKanjiSei;
	}

    /**
     * 申請者氏名（漢字等-姓）を設定
     * @param string 申請者氏名（漢字等-姓）
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
     * 申請番号を取得
	 * @return String 申請番号
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
     * 整理番号を取得
	 * @return 整理番号
	 */
	public String getSeiriNo(){
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
     * 事業選択値を取得
	 * @return 事業選択値
	 */
	public List getValueList() {
		return values;
	}

	/**
     * 事業選択値を設定
	 * @param list 事業選択値
	 */
	public void setValueList(List list) {
		values = list;
	}
	
	/**
     * 事業選択値を取得
     * @param key
	 * @return 事業選択値
	 */
	public Object getValues(int key) {
		return values.get(key);
	}

	/**
     * 事業選択値を設定
     * @param key
     * @param value
	 * @param string 事業選択値
	 */
	public void setValues(int key, Object value) {
		if(!values.contains(value)){
			values.add(value);
		}
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