/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2004/01/27
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.gyomu.shinsainKanri;

import javax.servlet.http.HttpServletRequest;

import jp.go.jsps.kaken.web.struts.BaseSearchForm;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

/**
 * 審査員情報検索フォーム
 * 
 * ID RCSfile="$RCSfile: ShinsainSearchForm.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:40 $"
 */
public class ShinsainSearchForm extends BaseSearchForm {

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/** 審査員番号 */
	private String shinsainNo;

	/** 審査員氏名（氏） */
	private String nameKanjiSei;

	/** 審査員氏名（名） */
	private String nameKanjiMei;

//	/** 所属機関名 */
//	private String shozokuName;

	/** 分科細目コード */
	private String bunkaSaimokuCd;

//	/** キーワード */
//	private String keyword;

	/** 所属機関名（コード）*/
	private String shozokuCd;

	/** 担当事業区分 */
	private String jigyoKubun;

	//・・・・・・・・・・

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * コンストラクタ。
	 */
	public ShinsainSearchForm() {
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
		shinsainNo     = "";
		nameKanjiSei        = "";
		nameKanjiMei        = "";
//		shozokuName    = "";
		bunkaSaimokuCd = "";
//		keyword        = "";
		shozokuCd        = "";
		jigyoKubun        = "";
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

/*									
		//入力をスペース(全角半角共)で分割した値に対してチェックを行う
		//分科細目コード条件チェック
		if(getBunkaSaimokuCd() != null && !getBunkaSaimokuCd().equals("")) {
			ArrayList list = this.separateString(getBunkaSaimokuCd());
			
			int cnt = list.size();
			for(int i=0; i<cnt; i++){
				String cnd = (String)list.get(i);


				if(cnd.length() > 4){
					errors.add(
					ActionErrors.GLOBAL_ERROR,
					new ActionError("errors.maxlength", "分科細目コード 第" + (i+1) + "条件", "4"));
				}
				if(cnd.length() != 4){
					errors.add(
					ActionErrors.GLOBAL_ERROR,
					new ActionError("errors.length", "分科細目コード 第" + (i+1) + "条件", "4"));
				}
		
				int lng = cnd.length();

				for(int j=0; j<lng; j++){
					if(!StringUtil.isDigit(cnd.charAt(j))){
						errors.add(
						ActionErrors.GLOBAL_ERROR,
						new ActionError("errors.integer", "分科細目コード 第" + (i+1) + "条件"));
						break;
					}
				}
				
			}
		}

		//キーワード条件チェック
		if(getKeyword() != null && !getKeyword().equals("")) {
			ArrayList list = this.separateString(getKeyword());

			int cnt = list.size();
			for(int i=0; i<cnt; i++){
				String cnd = (String)list.get(i);
				
				if(cnd.length() > 20){
					errors.add(
					ActionErrors.GLOBAL_ERROR,
					new ActionError("errors.maxlength", "キーワード 第" + (i+1) + "条件", "20"));
				}
				
			}
		}

		//基本入力チェックここまで
		if (!errors.isEmpty()) {
			return errors;
		}

		//定型処理----- 

		//追加処理----- 

		//---------------------------------------------
		//組み合わせチェック	
		//---------------------------------------------
*/
		return errors;
	}

	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------

	/**
	 * @return
	 */
	public String getShinsainNo() {
		return shinsainNo;
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

//	/**
//	 * @return
//	 */
//	public String getShozokuName() {
//		return shozokuName;
//	}

	/**
	 * @return
	 */
	public String getBunkaSaimokuCd() {
		return bunkaSaimokuCd;
	}

//	/**
//	 * @return
//	 */
//	public String getKeyword() {
//		return keyword;
//	}

	/**
	 * @param string
	 */
	public void setShinsainNo(String string) {
		shinsainNo = string;
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

//	/**
//	 * @param string
//	 */
//	public void setShozokuName(String string) {
//		shozokuName = string;
//	}

	/**
	 * @param string
	 */
	public void setBunkaSaimokuCd(String string) {
		bunkaSaimokuCd = string;
	}

//	/**
//	 * @param string
//	 */
//	public void setKeyword(String string) {
//		keyword = string;
//	}

//	/* (非 Javadoc)
//	 * @see jp.go.jsps.kaken.model.IShinsainMaintenance#separateString(java.lang.String)
//	 */
//	private ArrayList separateString(String str){
//		str = str.trim();
//		ArrayList arrayl = new ArrayList();
//			
//		while(true){
//			int idx_low = str.indexOf(" ");										//半角スペースのインデックス
//			int idx_up = str.indexOf("　");										//全角スペースのインデックス
//				
//			//半角スペース、全角スペースともに該当なし
//			if(idx_low == -1 && idx_up == -1){
//				if(!str.equals("")){
//					arrayl.add(str);
//				}
//				break;
//			}
//			//全角スペースが該当なし
//			else if(idx_up == -1){
//				String condi = str.substring(0, idx_low);
//				if(!condi.equals("")){
//					arrayl.add(condi);
//				}
//				str = str.substring(idx_low+1);
//			}
//			//半角スペースが該当なし
//			else if(idx_low == -1){
//				String condi = str.substring(0, idx_up);
//				if(!condi.equals("")){
//					arrayl.add(condi);
//				}
//				str = str.substring(idx_up+1);
//			}
//			//半角スペース、全角スペースとも該当あり
//			else{
//				//半角スペースが先に該当する
//				if(idx_low < idx_up){
//					String condi = str.substring(0, idx_low);
//					if(!condi.equals("")){
//						arrayl.add(condi);
//					}
//					str = str.substring(idx_low+1);
//				}
//				//全角スペースが先に該当する
//				else{
//					String condi = str.substring(0, idx_up);
//					if(!condi.equals("")){
//						arrayl.add(condi);
//					}
//					str = str.substring(idx_up+1);
//				}
//			}
//		}
//			
//		return arrayl;
//	}

/**
 * @return
 */
public String getShozokuCd() {
	return shozokuCd;
}

/**
 * @param string
 */
public void setShozokuCd(String string) {
	shozokuCd = string;
}

	/**
	 * @return
	 */
	public String getJigyoKubun() {
		return jigyoKubun;
	}

	/**
	 * @param string
	 */
	public void setJigyoKubun(String string) {
		jigyoKubun = string;
	}

}
