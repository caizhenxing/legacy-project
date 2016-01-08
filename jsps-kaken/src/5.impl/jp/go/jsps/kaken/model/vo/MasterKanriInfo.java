/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2004/02/16
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */

package jp.go.jsps.kaken.model.vo;

import java.util.Date;
import java.util.List;


/**
 * マスタ管理データ情報を保持するクラス。
 * 
 * ID RCSfile=$RCSfile: MasterKanriInfo.java,v $
 * Revision=$Revision: 1.1 $
 * Date=$Date: 2007/06/28 02:07:13 $
 */
public class MasterKanriInfo extends ValueObject{

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/**
     * <code>serialVersionUID</code> のコメント
     */
    private static final long serialVersionUID = 5457083506946073237L;

	/** マスタ種別 */
	private String masterShubetu;

	/** マスタ名称 */
	private String masterName;
	
	/** 取り込み日時 */
	private Date importDate;
	
	/** 件数 */
	private String kensu;
	
	/** 取り込みテーブル名 */
	private String importTable;
	
	/** 新規・更新フラグ */
	private String importFlg;
	
	/** 処理状況 */
	private String importMsg;
	
	/** CSVファイルパス */
	private String csvPath;

	/** 取込エラーメッセージ */
	private List importErrMsg;
	
	/** マスタ更新日付 */
	private Date updateDate;
	
	//・・・・・・・・・・

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * コンストラクタ。
	 */
	public MasterKanriInfo() {
		super();
	}
	
	
	//---------------------------------------------------------------------
	// Methods
	//---------------------------------------------------------------------
	
	
	
	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------
	

	/**
	 * @return
	 */
	public String getMasterShubetu() {
		return masterShubetu;
	}

	/**
	 * @return
	 */
	public String getMasterName() {
		return masterName;
	}

	/**
	 * @return
	 */
	public Date getImportDate() {
		return importDate;
	}

	/**
	 * @return
	 */
	public String getKensu() {
		return kensu;
	}

	/**
	 * @return
	 */
	public String getImportTable() {
		return importTable;
	}

	/**
	 * @return
	 */
	public String getImportFlg() {
		return importFlg;
	}

	/**
	 * @return
	 */
	public String getImportMsg() {
		return importMsg;
	}

	/**
	 * @return
	 */
	public List getImportErrMsg() {
		return importErrMsg;
	}


	/**
	 * @param string
	 */
	public void setMasterShubetu(String string) {
		masterShubetu = string;
	}

	/**
	 * @param string
	 */
	public void setMasterName(String string) {
		masterName = string;
	}

	/**
	 * @param string
	 */
	public void setImportDate(Date date) {
		importDate = date;
	}

	/**
	 * @param string
	 */
	public void setKensu(String string) {
		kensu = string;
	}

	/**
	 * @param string
	 */
	public void setImportTable(String string) {
		importTable = string;
	}

	/**
	 * @param string
	 */
	public void setImportFlg(String string) {
		importFlg = string;
	}

	/**
	 * @param string
	 */
	public void setImportMsg(String string) {
		importMsg = string;
	}

	/**
	 * @param string
	 */
	public void setImportErrMsg(List list) {
		importErrMsg = list;
	}

	/**
	 * @return
	 */
	public String getCsvPath() {
		return csvPath;
	}

	/**
	 * @param string
	 */
	public void setCsvPath(String string) {
		csvPath = string;
	}


	/**
     * updateDateを取得します。
     * 
     * @return updateDate
     */
    
    public Date getUpdateDate() {
    	return updateDate;
    }


	/**
     * updateDateを設定します。
     * 
     * @param updateDate updateDate
     */
    
    public void setUpdateDate(Date updateDate) {
    	this.updateDate = updateDate;
    }

}
