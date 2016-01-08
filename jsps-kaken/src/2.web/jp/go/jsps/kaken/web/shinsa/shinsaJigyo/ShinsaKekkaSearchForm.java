/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2003/11/14
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.shinsa.shinsaJigyo;

import javax.servlet.http.HttpServletRequest;

import jp.go.jsps.kaken.web.struts.BaseSearchForm;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

/**
 * 審査結果検索フォーム
 * ID RCSfile="$RCSfile: ShinsaKekkaSearchForm.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:59 $"
 */
public class ShinsaKekkaSearchForm extends BaseSearchForm {

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/** 事業ID */
	private String jigyoId;
	
	/** 審査結果（点数） */
	private String kekkaTen;	

	//2006.06.08 iso 審査担当事業一覧での事業名表示方式修正
//	//2006/04/13 add start ly 
//	/** 事業名 */
//	private String jigyoName;
	
//2006/04/18 苗 追加ここから
	/**　事業区分 */
	private String jigyoKubun;
//2006/04/18 苗 追加ここまで
    
//2006/05/12 苗 追加ここから
    /**　審査状況 */
    private String shinsaJokyo;
//2006/05/12 苗 追加ここまで 
   
//2006/10/27 苗　追加ここから
    /** 利害関係 */
    private String rigai;
//2006/10/27　苗　追加ここまで        
    //・・・・・・・・・・
	
	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * コンストラクタ。
	 */
	public ShinsaKekkaSearchForm() {
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
		jigyoId = "";  // 事業ID
		kekkaTen = ""; // 審査結果（点数）
//		jigyoName="";
		jigyoKubun=""; // 事業区分
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
     * 審査結果（点数）を取得
     * @return 審査結果（点数）
     */
	public String getKekkaTen() {
		return kekkaTen;
	}

    /**
     * 審査結果（点数）を設定
     * @param string 審査結果（点数）
     */
	public void setKekkaTen(String string) {
		kekkaTen = string;
	}

//	public String getJigyoName() {
//		return jigyoName;
//	}
//
//	public void setJigyoName(String jigyoName) {
//		this.jigyoName = jigyoName;
//	}

    /**
     * 事業区分を取得
     * @return 事業区分
     */
	public String getJigyoKubun() {
		return jigyoKubun;
	}

    /**
     * 事業区分を設定
     * @param jigyoKubun 事業区分
     */
	public void setJigyoKubun(String jigyoKubun) {
		this.jigyoKubun = jigyoKubun;
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
     * @param shinsaJyokyo 審査状況
     */
    public void setShinsaJokyo(String shinsaJyokyo) {
        this.shinsaJokyo = shinsaJyokyo;
	}

    /**
     * @return Returns the rigai.
     */
    public String getRigai() {
        return rigai;
    }

    /**
     * @param rigai The rigai to set.
     */
    public void setRigai(String rigai) {
        this.rigai = rigai;
    }
}
