/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2004/02/17
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.gyomu.hyoka;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jp.go.jsps.kaken.web.struts.BaseSearchForm;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

/**
 * 評価一覧検索フォーム
 * 
 * ID RCSfile="$RCSfile: HyokaSearchForm.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:19 $"
 */
public class HyokaSearchForm extends BaseSearchForm {

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

//	/** 事業名 */
//	private List values = new ArrayList();

	/** 年度 */
	private String nendo;

	/** 回数 */
	private String kaisu;

	/** 分野・系別 */
	private String bunya;

	/** 申請者氏名（氏） */
	private String nameKanjiSei;

	/** 申請者氏名（名） */
	private String nameKanjiMei;

	/** 申請者氏名（ローマ字-氏） */
	private String nameRoSei;

	/** 申請者氏名（ローマ字-名） */
	private String nameRoMei;

//	/** 申請者番号 */
//	private String shinseiNo;

//	/** 評価（From） */
//	private String hyokaFrom;
//
//	/** 評価（To） */
//	private String hyokaTo;

	/** 申請番号 */
	private String uketukeNo;

	/** 表示方式 */
	private String hyojiHoshiki;

	/** 事業名リスト */
	private List jigyoList;

	/** 表示方式リスト */
	private List hyojiHoshikiList;

//	/** 事業フラグ */
//	private String jigyoFlg;

	/** 事業コード */
	private String jigyoCd;

	/** 系等の区分 */
	private String keiName;

	/** 申請者氏名（フリガナ） */
	private String nameKanaSei;

	/** 申請者氏名（フリガナ） */
	private String nameKanaMei;

	/** 所属機関コード */
	private String shozokuCd;

	/** 細目番号 */
	private String bunkasaimokuCd;

	/** 事業区分 */
	private String jigyoKubun;

	/** 表示方式（基盤用） */
	private String hyojiHoshikiKiban;

	/** 表示方式リスト（基盤用） */
	private List hyojiHoshikiListKiban;

	/** 評価（点）（高） */
	private String hyokaHigh;

	/** 評価（点）（低） */
	private String hyokaLow;

	/** 海外分野 */
	private String kaigaibunyaName;
	
// 2005/10/18	整理番号追加
   /** 整理番号 */
   private String seiriNo;


	//・・・・・・・・・・

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * コンストラクタ。
	 */
	public HyokaSearchForm() {
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
		nendo = "";
		kaisu = "";
		bunya = "";
		nameKanjiSei = "";
		nameKanjiMei = "";
		nameRoSei = "";
		nameRoMei = "";
		uketukeNo = "";
		hyojiHoshiki = "";
		jigyoCd = "";
		keiName = "";
		nameKanaSei = "";
		nameKanaMei = "";
		shozokuCd = "";
		bunkasaimokuCd = "";
		jigyoList = new ArrayList();
		hyojiHoshikiList = new ArrayList();
		jigyoKubun = "";
		hyojiHoshikiKiban = "";
		hyojiHoshikiListKiban = new ArrayList();
		hyokaHigh = "";
		hyokaLow = "";
		kaigaibunyaName = "";
		seiriNo = "";
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

//		//事業名チェック（集会とその他（共同研究、セミナー）が同時に指定されてないか？
//		List jigyo = this.getValueList();
//		int cnt = jigyo.size();
//		int jigyo_err = 0;
//		if(cnt != 0){
//			//事業名が指定されている（要素が0でない）時
//			int jigyo_flg0 = 0;													//基礎となるフラグ(第1要素)で判定
//			for(int i=0; i<cnt; i++){
//				String no = (String)jigyo.get(i);
//				int jigyo_flg = 0;
//				
//				if(no.substring(0,2).equals("01")){
//					//集会(01)の場合はフラグを1にする
//					jigyo_flg = 1;
//				}
//
//				if(i == 0){
//					//第1要素の場合、基礎フラグに代入する
//					jigyo_flg0 = jigyo_flg;
//				}else{
//					//第2要素以降は、基礎フラグと自己フラグが同じかを判定する
//					if(jigyo_flg0 != jigyo_flg){
//						//基礎フラグと自己フラグが異なる場合はエラーを出力する
//						errors.add(
//						ActionErrors.GLOBAL_ERROR,
//						new ActionError("errors.5009"));
//						
//						jigyo_err = 1;
//						
//						break;
//					}
//				}
//			}
//		}
//		
//		//集会選択時の年度必須チェック
//		if(cnt != 0 && jigyo_err == 0){
//			if(((String)jigyo.get(0)).substring(0,2).equals("01") && this.getNendo().equals("")){
//				//集会を選択し、年度が未選択の場合
//				errors.add(
//				ActionErrors.GLOBAL_ERROR,
//				new ActionError("errors.5011"));
//			}
//		}
//		
//		//評価の入力チェック
//		//評価（From）
//		String base = "ABCF";
//		String hf = this.getHyokaFrom();
//		if(hf != null && !hf.equals("")){
//			for(int i=0; i<hf.length(); i++){
//				//入力値一文字毎に"ABCF"のいずれかであるかをチェックする
//				if(base.indexOf(hf.charAt(i)) == -1){
//					//入力値が"ABCF"に含まれなかった場合はエラーを出力する
//					errors.add(
//					ActionErrors.GLOBAL_ERROR,
//					new ActionError("errors.5010", "評価"));
//					return errors;						
////					break;					
//				}
//			}
//		}
//		//評価（To）
//		String ht = this.getHyokaTo();
//		if(ht != null && !ht.equals("")){
//			for(int i=0; i<ht.length(); i++){
//				//入力値一文字毎に"ABCF"のいずれかであるかをチェックする
//				if(base.indexOf(ht.charAt(i)) == -1){
//					//入力値が"ABCF"に含まれなかった場合はエラーを出力する
//					errors.add(
//					ActionErrors.GLOBAL_ERROR,
//					new ActionError("errors.5010", "評価"));
//					return errors;						
////					break;					
//				}
//			}
//		}


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
	public String getBunkasaimokuCd() {
		return bunkasaimokuCd;
	}

	/**
	 * @return
	 */
	public String getBunya() {
		return bunya;
	}

/**
 * @return
 */
public String getHyojiHoshiki() {
	return hyojiHoshiki;
}

	/**
	 * @return
	 */
	public List getHyojiHoshikiList() {
		return hyojiHoshikiList;
	}

/**
 * @return
 */
public String getJigyoCd() {
	return jigyoCd;
}

	/**
	 * @return
	 */
	public List getJigyoList() {
		return jigyoList;
	}

	/**
	 * @return
	 */
	public String getKaisu() {
		return kaisu;
	}

	/**
	 * @return
	 */
	public String getKeiName() {
		return keiName;
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
	public String getNendo() {
		return nendo;
	}

	/**
	 * @return
	 */
	public String geUketukeNo() {
		return uketukeNo;
	}

	/**
	 * @return
	 */
	public String getShozokuCd() {
		return shozokuCd;
	}

	/**
	 * @param string
	 */
	public void setBunkasaimokuCd(String string) {
		bunkasaimokuCd = string;
	}

	/**
	 * @param string
	 */
	public void setBunya(String string) {
		bunya = string;
	}

/**
 * @param string
 */
public void setHyojiHoshiki(String string) {
	hyojiHoshiki = string;
}

	/**
	 * @param list
	 */
	public void setHyojiHoshikiList(List list) {
		hyojiHoshikiList = list;
	}

/**
 * @param string
 */
public void setJigyoCd(String string) {
	jigyoCd = string;
}

	/**
	 * @param list
	 */
	public void setJigyoList(List list) {
		jigyoList = list;
	}

	/**
	 * @param string
	 */
	public void setKaisu(String string) {
		kaisu = string;
	}

	/**
	 * @param string
	 */
	public void setKeiName(String string) {
		keiName = string;
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
	public void setNendo(String string) {
		nendo = string;
	}

	/**
	 * @param string
	 */
	public void setUketukeNo(String string) {
		uketukeNo = string;
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
 * @return
 */
public String getUketukeNo() {
	return uketukeNo;
}

	/**
	 * @param string
	 */
	public void setJigyoKubun(String string) {
		jigyoKubun = string;
	}

	/**
	 * @return
	 */
	public String getHyojiHoshikiKiban() {
		return hyojiHoshikiKiban;
	}

	/**
	 * @return
	 */
	public List getHyojiHoshikiListKiban() {
		return hyojiHoshikiListKiban;
	}

	/**
	 * @param string
	 */
	public void setHyojiHoshikiKiban(String string) {
		hyojiHoshikiKiban = string;
	}

	/**
	 * @param list
	 */
	public void setHyojiHoshikiListKiban(List list) {
		hyojiHoshikiListKiban = list;
	}

	/**
	 * @return
	 */
	public String getHyokaHigh() {
		return hyokaHigh;
	}

	/**
	 * @return
	 */
	public String getHyokaLow() {
		return hyokaLow;
	}

	/**
	 * @param string
	 */
	public void setHyokaHigh(String string) {
		hyokaHigh = string;
	}

	/**
	 * @param string
	 */
	public void setHyokaLow(String string) {
		hyokaLow = string;
	}

	/**
	 * @return
	 */
	public String getKaigaibunyaName() {
		return kaigaibunyaName;
	}

	/**
	 * @param string
	 */
	public void setKaigaibunyaName(String string) {
		kaigaibunyaName = string;
	}

// 2005/10/18	整理番号追加
	/**
	 * @return
	 */
	public String getSeiriNo() {
		return seiriNo;
	}
	/**
	 * @param string
	 */
	public void setSeiriNo(String string) {
		seiriNo = string;
	}
}
