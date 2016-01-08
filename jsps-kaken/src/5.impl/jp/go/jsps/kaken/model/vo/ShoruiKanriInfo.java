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

import jp.go.jsps.kaken.util.FileResource;

/**
 * 書類管理情報を保持するクラス。
 * 
 * ID RCSfile="$RCSfile: ShoruiKanriInfo.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:11 $"
 */
public class ShoruiKanriInfo extends ShoruiKanriPk {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------
	static final long serialVersionUID = 8598716120596511071L;

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/** 事業ID */
	private String jigyoId;

	/** 事業名 */
	private String jigyoName;
	
	/** 年度 */
	private String nendo;
	
	/** 回数 */
	private String kaisu;

	/** 書類ファイル */
	private String shoruiFile;

	/** 書類名 */
	private String shoruiName;

	/** 削除フラグ */
	private String delFlg;

	/** 書類ファイル */
	private FileResource    shoruiFileRes;

	//...など

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * コンストラクタ。
	 */
	public ShoruiKanriInfo() {
		super();
	}
	
	
	/**
	 * 書類管理情報をリセットする。
	 */
	public void reset(){
		setShoruiFile("");//書類ファイル
		setShoruiName("");//書類名
		setSystemNo("");//システム番号
		setTaishoId("");//対象
		setDelFlg("");//削除フラグ
		setShoruiFileRes(null);
	}

	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------

	/**
	 * @return
	 */
	public String getDelFlg() {
		return delFlg;
	}

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
	 * @param string
	 */
	public void setDelFlg(String string) {
		delFlg = string;
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
	 * @return
	 */
	public String getJigyoName() {
		return jigyoName;
	}

	/**
	 * @return
	 */
	public String getNendo() {
		return nendo;
	}

	/**
	 * @param string
	 */
	public void setJigyoName(String string) {
		jigyoName = string;
	}

	/**
	 * @param string
	 */
	public void setNendo(String string) {
		nendo = string;
	}

	/**
	 * @return
	 */
	public FileResource getShoruiFileRes() {
		return shoruiFileRes;
	}

	/**
	 * @param resource
	 */
	public void setShoruiFileRes(FileResource resource) {
		shoruiFileRes = resource;
	}

	/**
	 * @return
	 */
	public String getJigyoId() {
		return jigyoId;
	}

	/**
	 * @return
	 */
	public String getKaisu() {
		return kaisu;
	}

	/**
	 * @param string
	 */
	public void setJigyoId(String string) {
		jigyoId = string;
	}

	/**
	 * @param string
	 */
	public void setKaisu(String string) {
		kaisu = string;
	}

}
