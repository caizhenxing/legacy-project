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

package jp.go.jsps.kaken.model.pdf.webdoc;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import jp.go.jsps.kaken.model.vo.ValueObject;

/**
 * ページ毎のiodファイル情報情報を保持するクラス。
 * 
 * ID RCSfile="$RCSfile: PageInfo.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:57 $"
 */
public class PageInfo extends ValueObject{

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------
	static final long serialVersionUID = 6372462256401769823L;
	
	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------
	
	/** iodファイルパス情報 */
	private String templateFilePath;
	
	/** iodファイルの設定するフィールド情報 */
	private List fields = new ArrayList();
	
	//・・・・・・・・・・

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * コンストラクタ。
	 */
	public PageInfo() {
		super();
	}

	//---------------------------------------------------------------------
	// Public methods
	//---------------------------------------------------------------------

	/**
	 * フィールド情報をセットする。
	 * @param 	フィールド情報
	 * @return	
	 */
	public boolean addFieldInfo(FieldInfo fieldInfo) {
		return fields.add(fieldInfo);
	}
	
	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------

	/**
	 * @return
	 */
	public File getTemplateFile() {
		return new File(templateFilePath);
	}


	/**
	 * @return
	 */
	public String getTemplateFilePath() {
		return templateFilePath;
	}

	/**
	 * @param string
	 */
	public void setTemplateFilePath(String string) {
		templateFilePath = string;
	}

	/**
	 * @return
	 */
	public List getFields() {
		return fields;
	}

	/**
	 * @param list
	 */
	public void setFields(List list) {
		fields = list;
	}

}
