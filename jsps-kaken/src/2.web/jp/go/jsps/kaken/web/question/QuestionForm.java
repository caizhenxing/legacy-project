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
package jp.go.jsps.kaken.web.question;



import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jp.go.jsps.kaken.web.struts.BaseValidatorForm;


import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;


/**
 */
public class QuestionForm extends BaseValidatorForm {

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/** アンケート受付番号 */
	private String uketukeNo;

	/** 研究機関名 */
	private String shozokuName;
	
	/** ①電子申請システム全般についてQ1 */
	private String benri1;
	
	/** ①電子申請システム全般についてQ1 */
	private String a1;
	
	/** ①電子申請システム全般についてQ1 */
	private String a2;
	
	/** ①電子申請システム全般についてQ2 */
	private String rikai1;	
	
	/** ①電子申請システム全般についてQ2 */
	private String a3;
	
	/** ②ご利用環境について(OS)Q */
	private String os;
	
	/** ②ご利用環境について(OS)Q */
	private String kankyoosSonota;
	
	/** ②ご利用環境について(WEB)Q */
	private String web;
	
	/** ②ご利用環境について(WEB)Q */
	private String kankyowebSonota;
	
	/** ③電子申請のご案内ページQ1 */
	private String rikai2;
	
	/** ③電子申請のご案内ページQ1 */
	private String rikai3;
	
	/** ③電子申請のご案内ページQ1 */
	private String rikai4;
	
	/** ③電子申請のご案内ページQ1 */
	private String rikai5;
	
	/** ③電子申請のご案内ページQ1 */
	private String rikai6;
	
	/** ③電子申請のご案内ページQ1 */
	private String rikai7;
	
	/** ③電子申請のご案内ページQ1 */
	private String rikai8;
	
	/** ③電子申請のご案内ページQ1 */
	private String rikai9;
	
	/** ③電子申請のご案内ページQ1 */
	private String rikai10;
	
	/** ③電子申請のご案内ページQ1 */
	private String rikai11;
	
	/** ③電子申請のご案内ページQ2 */
	private String a4;
	
	/** ④操作手引についてQ1 */
	private String yonda1;
	
	/** ④操作手引についてQ2 */
	private String rikai12;	

	/** ④操作手引についてQ2 */
	private String a5;
	
	/** ④操作手引についてQ3 */
	private String yonda2;
	
	/** ④操作手引についてQ4 */
	private String rikai13;	
		
	/** ④操作手引についてQ4 */
	private String a6;
	
	/** ④操作手引についてQ5 */
	private String a7;

	/** ⑤電子申請システムについてQ1 */
	private String keisiki;
		
	/** ⑤電子申請システムについてQ1 */
	private String oubokeisikiSonota;
	
	/** ⑤電子申請システムについてQ2 */
	private String riyoutime;
	
	/** ⑤電子申請システムについてQ3 */
	private String benri2;
	
	/** ⑤電子申請システムについてQ3 */
	private String a8;
	
	/** ⑤電子申請システムについてQ4 */
	private String a9;
	
	/** ⑤電子申請システムについてQ5 */
	private String a10;

	/** ⑥コールセンターについてQ1 */
	private String callriyou;
	
	/** ⑥コールセンターについてQ2 */
	private String callrikai;
	
	/** ⑥コールセンターについてQ2 */
	private String a11;
	
	/** ⑥コールセンターについてQ3 */
	private String a12;
		
	/** ⑦Q3 */
	private String toiawase1;
	
	/** ⑦Q4 */
	private String a13;
	
	/** ⑦Q5 */
	private String toiawase2;
	
	/** ⑦Q6 */
	private String a14;
	
	//審査員向け　2005.11.21追加
	
	/** 審査員名（姓） */
	private String SHINSAIN_NAME_SEI;
	
	/** 審査員名（名） */
	private String SHINSAIN_NAME_MEI;

	/** 審査員専用ページについてQ1 */
	private String rikai14;
	
	/** 審査員専用ページについてQ1 */
	private String a15;     

	/** リストラジオボタン */
	private List benri1List = new ArrayList();
	
	private List rikai1List = new ArrayList();
	
	private List rikai2List = new ArrayList();
	
	private List rikai3List = new ArrayList();
	
	private List yonda1List = new ArrayList();
	
	private List benri2List = new ArrayList();
	
	private List callriyouList = new ArrayList();
	
	private List callrikaiList = new ArrayList();
	
	/** リストプルダウン */
	private List osList = new ArrayList();
	
	private List webList = new ArrayList();
	
	private List keisikiList = new ArrayList();
	
	private List riyoutimeList = new ArrayList();
	
	private List toiawase1List = new ArrayList();

	//2005.11.10 iso チェックボックス対応
	/** 応募者からの問い合わせの内容 */
	private List ouboToiValues = new ArrayList();

	/** 応募者からの問い合わせの内容リスト */
	private List ouboToiList = new ArrayList();
	
	/** 部局担当者からの問い合わせの内容 */
	private List bukyokuToiValues = new ArrayList();
	
	/** 部局担当者からの問い合わせの内容リスト */
	private List bukyokuToiList = new ArrayList();
	
	//・・・・・・・・・・

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * コンストラクタ。
	 */
	public QuestionForm() {
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
		uketukeNo= "";
		shozokuName= "";
		benri1= "";
		a1= "";
		a2= "";
		rikai1= "";
		a3= "";
		os= "";
		kankyoosSonota= "";
		web= "";
		kankyowebSonota= "";
		rikai2= "";
		rikai3= "";
		rikai4= "";
		rikai5= "";
		rikai6= "";
		rikai7= "";
		rikai8= "";
		rikai9= "";
		rikai10= "";
		rikai11= "";
		a4= "";
		yonda1= "";
		rikai12= "";
		a5= "";
		yonda2= "";
		rikai13= "";
		a6= "";
		a7= "";
		keisiki= "";
		oubokeisikiSonota= "";
		riyoutime= "0";
		benri2= "";
		a8= "";
		a9= "";
		a10= "";
		toiawase1= "0";
		toiawase2= "0";
		os= "0";
		web= "0";
		keisiki= "0";
		ouboToiValues = new ArrayList();
		bukyokuToiValues = new ArrayList();
	//審査員向け　2005.11.21追加
		SHINSAIN_NAME_SEI= "";
		SHINSAIN_NAME_MEI= "";
		rikai14= "";
		a15= "";
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
		//update2004/12/21 制度改正対応

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
	public String getA10() {
		return a10;
	}

	/**
	 * @return
	 */
	public String getA9() {
		return a9;
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
	public String getUketukeNo() {
		return uketukeNo;
	}

	/**
	 * @param string
	 */
	public void setA10(String string) {
		a10 = string;
	}

	/**
	 * @param string
	 */
	public void setA9(String string) {
		a9 = string;
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
	public void setUketukeNo(String string) {
		uketukeNo = string;
	}


	/**
	 * @return
	 */
	public String getA1() {
		return a1;
	}

	/**
	 * @return
	 */
	public String getA2() {
		return a2;
	}

	/**
	 * @return
	 */
	public String getA3() {
		return a3;
	}

	/**
	 * @return
	 */
	public String getA4() {
		return a4;
	}

	/**
	 * @return
	 */
	public String getA5() {
		return a5;
	}

	/**
	 * @return
	 */
	public String getA6() {
		return a6;
	}

	/**
	 * @return
	 */
	public String getA7() {
		return a7;
	}

	/**
	 * @return
	 */
	public String getA8() {
		return a8;
	}

	/**
	 * @return
	 */
	public String getKankyoosSonota() {
		return kankyoosSonota;
	}

	/**
	 * @return
	 */
	public String getKankyowebSonota() {
		return kankyowebSonota;
	}

	/**
	 * @param string
	 */
	public void setA1(String string) {
		a1 = string;
	}

	/**
	 * @param string
	 */
	public void setA2(String string) {
		a2 = string;
	}

	/**
	 * @param string
	 */
	public void setA3(String string) {
		a3 = string;
	}

	/**
	 * @param string
	 */
	public void setA4(String string) {
		a4 = string;
	}

	/**
	 * @param string
	 */
	public void setA5(String string) {
		a5 = string;
	}

	/**
	 * @param string
	 */
	public void setA6(String string) {
		a6 = string;
	}

	/**
	 * @param string
	 */
	public void setA7(String string) {
		a7 = string;
	}

	/**
	 * @param string
	 */
	public void setA8(String string) {
		a8 = string;
	}

	/**
	 * @param string
	 */
	public void setKankyoosSonota(String string) {
		kankyoosSonota = string;
	}

	/**
	 * @param string
	 */
	public void setKankyowebSonota(String string) {
		kankyowebSonota = string;
	}

	/**
	 * @return
	 */
	public String getOubokeisikiSonota() {
		return oubokeisikiSonota;
	}

	/**
	 * @param string
	 */
	public void setOubokeisikiSonota(String string) {
		oubokeisikiSonota = string;
	}

	/**
	 * @return
	 */
	public String getBenri1() {
		return benri1;
	}

	/**
	 * @return
	 */
	public List getBenri1List() {
		return benri1List;
	}

	/**
	 * @param string
	 */
	public void setBenri1(String string) {
		benri1 = string;
	}

	/**
	 * @param list
	 */
	public void setBenri1List(List list) {
		benri1List = list;
	}

	/**
	 * @return
	 */
	public String getBenri2() {
		return benri2;
	}

	/**
	 * @return
	 */
	public String getRikai1() {
		return rikai1;
	}

	/**
	 * @return
	 */
	public String getRikai10() {
		return rikai10;
	}

	/**
	 * @return
	 */
	public String getRikai11() {
		return rikai11;
	}

	/**
	 * @return
	 */
	public String getRikai12() {
		return rikai12;
	}

	/**
	 * @return
	 */
	public String getRikai13() {
		return rikai13;
	}

	/**
	 * @return
	 */
	public String getRikai2() {
		return rikai2;
	}

	/**
	 * @return
	 */
	public String getRikai3() {
		return rikai3;
	}

	/**
	 * @return
	 */
	public String getRikai4() {
		return rikai4;
	}

	/**
	 * @return
	 */
	public String getRikai5() {
		return rikai5;
	}

	/**
	 * @return
	 */
	public String getRikai6() {
		return rikai6;
	}

	/**
	 * @return
	 */
	public String getRikai7() {
		return rikai7;
	}

	/**
	 * @return
	 */
	public String getRikai8() {
		return rikai8;
	}

	/**
	 * @return
	 */
	public String getRikai9() {
		return rikai9;
	}


	/**
	 * @return
	 */
	public String getYonda2() {
		return yonda2;
	}

	/**
	 * @param string
	 */
	public void setBenri2(String string) {
		benri2 = string;
	}

	/**
	 * @param string
	 */
	public void setRikai1(String string) {
		rikai1 = string;
	}

	/**
	 * @param string
	 */
	public void setRikai10(String string) {
		rikai10 = string;
	}

	/**
	 * @param string
	 */
	public void setRikai11(String string) {
		rikai11 = string;
	}

	/**
	 * @param string
	 */
	public void setRikai12(String string) {
		rikai12 = string;
	}

	/**
	 * @param string
	 */
	public void setRikai13(String string) {
		rikai13 = string;
	}

	/**
	 * @param string
	 */
	public void setRikai2(String string) {
		rikai2 = string;
	}

	/**
	 * @param string
	 */
	public void setRikai3(String string) {
		rikai3 = string;
	}

	/**
	 * @param string
	 */
	public void setRikai4(String string) {
		rikai4 = string;
	}

	/**
	 * @param string
	 */
	public void setRikai5(String string) {
		rikai5 = string;
	}

	/**
	 * @param string
	 */
	public void setRikai6(String string) {
		rikai6 = string;
	}

	/**
	 * @param string
	 */
	public void setRikai7(String string) {
		rikai7 = string;
	}

	/**
	 * @param string
	 */
	public void setRikai8(String string) {
		rikai8 = string;
	}

	/**
	 * @param string
	 */
	public void setRikai9(String string) {
		rikai9 = string;
	}


	/**
	 * @param string
	 */
	public void setYonda2(String string) {
		yonda2 = string;
	}

	/**
	 * @return
	 */
	public List getBenri2List() {
		return benri2List;
	}

	/**
	 * @return
	 */
	public List getRikai1List() {
		return rikai1List;
	}

	/**
	 * @return
	 */
	public List getRikai2List() {
		return rikai2List;
	}

	/**
	 * @return
	 */
	public List getRikai3List() {
		return rikai3List;
	}

	/**
	 * @param list
	 */
	public void setBenri2List(List list) {
		benri2List = list;
	}

	/**
	 * @param list
	 */
	public void setRikai1List(List list) {
		rikai1List = list;
	}

	/**
	 * @param list
	 */
	public void setRikai2List(List list) {
		rikai2List = list;
	}

	/**
	 * @param list
	 */
	public void setRikai3List(List list) {
		rikai3List = list;
	}

	/**
	 * @return
	 */
	public String getOs() {
		return os;
	}

	/**
	 * @return
	 */
	public String getWeb() {
		return web;
	}

	/**
	 * @param string
	 */
	public void setOs(String string) {
		os = string;
	}

	/**
	 * @param string
	 */
	public void setWeb(String string) {
		web = string;
	}

	/**
	 * @return
	 */
	public List getOsList() {
		return osList;
	}

	/**
	 * @return
	 */
	public List getWebList() {
		return webList;
	}

	/**
	 * @param list
	 */
	public void setOsList(List list) {
		osList = list;
	}

	/**
	 * @param list
	 */
	public void setWebList(List list) {
		webList = list;
	}

	/**
	 * @return
	 */
	public String getKeisiki() {
		return keisiki;
	}

	/**
	 * @return
	 */
	public List getKeisikiList() {
		return keisikiList;
	}

	/**
	 * @return
	 */
	public String getRiyoutime() {
		return riyoutime;
	}

	/**
	 * @return
	 */
	public List getRiyoutimeList() {
		return riyoutimeList;
	}

	/**
	 * @return
	 */
	public String getToiawase1() {
		return toiawase1;
	}

	/**
	 * @return
	 */
	public List getToiawase1List() {
		return toiawase1List;
	}

	/**
	 * @return
	 */
	public String getToiawase2() {
		return toiawase2;
	}

	/**
	 * @param string
	 */
	public void setKeisiki(String string) {
		keisiki = string;
	}

	/**
	 * @param list
	 */
	public void setKeisikiList(List list) {
		keisikiList = list;
	}

	/**
	 * @param string
	 */
	public void setRiyoutime(String string) {
		riyoutime = string;
	}

	/**
	 * @param list
	 */
	public void setRiyoutimeList(List list) {
		riyoutimeList = list;
	}

	/**
	 * @param string
	 */
	public void setToiawase1(String string) {
		toiawase1 = string;
	}

	/**
	 * @param list
	 */
	public void setToiawase1List(List list) {
		toiawase1List = list;
	}

	/**
	 * @param string
	 */
	public void setToiawase2(String string) {
		toiawase2 = string;
	}

	/**
	 * @return
	 */
	public String getA11() {
		return a11;
	}

	/**
	 * @return
	 */
	public String getA12() {
		return a12;
	}

	/**
	 * @return
	 */
	public String getA13() {
		return a13;
	}

	/**
	 * @return
	 */
	public String getA14() {
		return a14;
	}

	/**
	 * @return
	 */
	public String getCallrikai() {
		return callrikai;
	}

	/**
	 * @return
	 */
	public List getCallrikaiList() {
		return callrikaiList;
	}

	/**
	 * @return
	 */
	public String getCallriyou() {
		return callriyou;
	}

	/**
	 * @return
	 */
	public List getCallriyouList() {
		return callriyouList;
	}

	/**
	 * @param string
	 */
	public void setA11(String string) {
		a11 = string;
	}

	/**
	 * @param string
	 */
	public void setA12(String string) {
		a12 = string;
	}

	/**
	 * @param string
	 */
	public void setA13(String string) {
		a13 = string;
	}

	/**
	 * @param string
	 */
	public void setA14(String string) {
		a14 = string;
	}

	/**
	 * @param string
	 */
	public void setCallrikai(String string) {
		callrikai = string;
	}

	/**
	 * @param list
	 */
	public void setCallrikaiList(List list) {
		callrikaiList = list;
	}

	/**
	 * @param string
	 */
	public void setCallriyou(String string) {
		callriyou = string;
	}

	/**
	 * @param list
	 */
	public void setCallriyouList(List list) {
		callriyouList = list;
	}

	/**
	 * @return
	 */
	public String getYonda1() {
		return yonda1;
	}

	/**
	 * @param string
	 */
	public void setYonda1(String string) {
		yonda1 = string;
	}



	/**
	 * @return
	 */
	public List getYonda1List() {
		return yonda1List;
	}

	/**
	 * @param list
	 */
	public void setYonda1List(List list) {
		yonda1List = list;
	}

	/**
	 * @return ouboToiList を戻します。
	 */
	public List getOuboToiList() {
		return ouboToiList;
	}

	/**
	 * @param ouboToiList 設定する ouboToiList。
	 */
	public void setOuboToiList(List ouboToiList) {
		this.ouboToiList = ouboToiList;
	}

	/**
	 * @return ouboToiValues を戻します。
	 */
	public List getOuboToiValues() {
		return ouboToiValues;
	}

	/**
	 * @param ouboToiValues 設定する ouboToiValues。
	 */
	public void setOuboToiValues(List ouboToiValues) {
		this.ouboToiValues = ouboToiValues;
	}

	/**
	 * @return
	 */
	public Object getOuboToiValue(int key) {
		return ouboToiValues.get(key);
	}

	/**
	 * @param string
	 */
	public void setOuboToiValue(int key, Object value) {
		ouboToiValues.add(value);
	}

	/**
	 * @return
	 */
	public List getBukyokuToiList() {
		return bukyokuToiList;
	}

	/**
	 * @return
	 */
	public List getBukyokuToiValues() {
		return bukyokuToiValues;
	}

	/**
	 * @param bukyokuToiList 設定する bukyokuToiList。
	 */
	public void setBukyokuToiList(List bukyokuToiList) {
		this.bukyokuToiList = bukyokuToiList;
	}

	/**
	 * @param bukyokuToiValues 設定する bukyokuToiValues。
	 */
	public void setBukyokuToiValues(List bukyokuToiValues) {
		this.bukyokuToiValues = bukyokuToiValues;
	}

	/**
	 * @return
	 */
	public Object getBukyokuToiValue(int key) {
		return bukyokuToiValues.get(key);
	}

	/**
	 * @param string
	 */
	public void setBukyokuToiValue(int key, Object value) {
		bukyokuToiValues.add(value);
	}

	/**
	 * @return
	 */
	public String getA15() {
		return a15;
	}

	/**
	 * @return
	 */
	public String getRikai14() {
		return rikai14;
	}

	/**
	 * @return
	 */
	public String getSHINSAIN_NAME_MEI() {
		return SHINSAIN_NAME_MEI;
	}

	/**
	 * @return
	 */
	public String getSHINSAIN_NAME_SEI() {
		return SHINSAIN_NAME_SEI;
	}

	/**
	 * @param string
	 */
	public void setA15(String string) {
		a15 = string;
	}

	/**
	 * @param string
	 */
	public void setRikai14(String string) {
		rikai14 = string;
	}

	/**
	 * @param string
	 */
	public void setSHINSAIN_NAME_MEI(String string) {
		SHINSAIN_NAME_MEI = string;
	}

	/**
	 * @param string
	 */
	public void setSHINSAIN_NAME_SEI(String string) {
		SHINSAIN_NAME_SEI = string;
	}

}
