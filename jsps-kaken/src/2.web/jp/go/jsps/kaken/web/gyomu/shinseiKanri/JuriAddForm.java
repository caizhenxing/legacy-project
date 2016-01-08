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
package jp.go.jsps.kaken.web.gyomu.shinseiKanri;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import jp.go.jsps.kaken.web.struts.BaseValidatorForm;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

/**
 * ID RCSfile="$RCSfile: JuriAddForm.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:54 $"
 */
public class JuriAddForm extends BaseValidatorForm {

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/** システム受付番号 */
	private String systemNo;
    
    /** 事業コード */
    private String jigyoCd;

	/** 備考 */
	private String juriBiko;

	/** 受理結果 */
	private String juriKekka;
	
	/** 受理結果情報選択リスト */
	private List juriKekkaList = new ArrayList();	
	
	/** 受理整理番号 */
	private String juriSeiriNo;

	//・・・・・・・・・・

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * コンストラクタ。
	 */
	public JuriAddForm() {
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
		systemNo = "";
		juriBiko = "";
		juriKekka = "";
		juriSeiriNo = "";
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
	 * @return
	 */
	public String getJuriBiko() {
		return juriBiko;
	}

	/**
	 * @return
	 */
	public String getSystemNo() {
		return systemNo;
	}

	/**
	 * @param string
	 */
	public void setJuriBiko(String string) {
		juriBiko = string;
	}

	/**
	 * @param string
	 */
	public void setSystemNo(String string) {
		systemNo = string;
	}

	/**
	 * @return
	 */
	public String getJuriKekka() {
		return juriKekka;
	}

	/**
	 * @return
	 */
	public List getJuriKekkaList() {
		return juriKekkaList;
	}

	/**
	 * @param string
	 */
	public void setJuriKekka(String string) {
		juriKekka = string;
	}

	/**
	 * @param list
	 */
	public void setJuriKekkaList(List list) {
		juriKekkaList = list;
	}

	/**
	 * @return 整理番号を返す
	 */
	public String getJuriSeiriNo(){
		return juriSeiriNo;
	}
	
	/**
	 * @param str 整理番号をセットする
	 */
	public void setJuriSeiriNo(String str){
		juriSeiriNo = str;
	}

    /**
     * @return Returns the jigyoCd.
     */
    public String getJigyoCd() {
        return jigyoCd;
    }

    /**
     * @param jigyoCd The jigyoCd to set.
     */
    public void setJigyoCd(String jigyoCd) {
        this.jigyoCd = jigyoCd;
    }

}
