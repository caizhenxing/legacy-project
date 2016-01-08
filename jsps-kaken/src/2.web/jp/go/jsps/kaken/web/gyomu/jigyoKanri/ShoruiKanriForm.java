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
package jp.go.jsps.kaken.web.gyomu.jigyoKanri;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jp.go.jsps.kaken.web.struts.BaseValidatorForm;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;


/**
 * ID RCSfile="$RCSfile: ShoruiKanriForm.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:04 $"
 */
public class ShoruiKanriForm extends BaseValidatorForm {
	

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------
	
	/** 事業ID */
	private String    jigyoId;
	
	/** 対象 */
	private String    taishoId;
	
	/** 書類ファイル */
	private String    shoruiFile;
	
	/** 書類名 */
	private String    shoruiName;
	
	/** システム番号 */
	private String    systemNo;
	
	/** 対象選択リスト */
	private List taishoIdList = new ArrayList();
	
	/** 書類ファイル(アップロードファイル) */
	private FormFile        shoruiUploadFile;
	
	//・・・・・・・・・・

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * コンストラクタ。
	 */
	public ShoruiKanriForm() {
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
		init();
	}

	/**
	 * 初期化処理
	 */
	public void init() {
		taishoId = "2";//初期表示「所属機関担当者向け」
		shoruiFile = "";
		shoruiName = "";
		systemNo = "";
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
			errors.add(
				ActionErrors.GLOBAL_ERROR,
				new ActionError("errors.2009"));
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
	public String getShoruiFile() {
		return shoruiFile;
	}

	/**
	 * @return
	 */
	public String getShoruiName() {
		return shoruiName;
	}

	/**
	 * @return
	 */
	public FormFile getShoruiUploadFile() {
		return shoruiUploadFile;
	}

	/**
	 * @return
	 */
	public String getTaishoId() {
		return taishoId;
	}

	/**
	 * @param string
	 */
	public void setShoruiFile(String string) {
		shoruiFile = string;
	}

	/**
	 * @param string
	 */
	public void setShoruiName(String string) {
		shoruiName = string;
	}

	/**
	 * @param file
	 */
	public void setShoruiUploadFile(FormFile file) {
		shoruiUploadFile = file;
	}

	/**
	 * @param string
	 */
	public void setTaishoId(String string) {
		taishoId = string;
	}

	/**
	 * @return
	 */
	public List getTaishoIdList() {
		return taishoIdList;
	}

	/**
	 * @param list
	 */
	public void setTaishoIdList(List list) {
		taishoIdList = list;
	}

	/**
	 * @return
	 */
	public String getJigyoId() {
		return jigyoId;
	}

	/**
	 * @param string
	 */
	public void setJigyoId(String string) {
		jigyoId = string;
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
	public void setSystemNo(String string) {
		systemNo = string;
	}

}