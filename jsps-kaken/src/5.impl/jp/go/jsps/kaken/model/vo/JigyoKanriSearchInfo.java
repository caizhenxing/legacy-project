/*======================================================================
 *    SYSTEM      : 日本学術振興会電子申請システム（科学研究費補助金）
 *    Source name : JigyoKanriSearchInfo.java
 *    Description : 事業管理情報検索条件を保持するクラス
 *
 *    Author      : Admin
 *    Date        : 2003/07/16
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2003/07/16    v1.0        Admin          新規作成
 *====================================================================== 
 */
package jp.go.jsps.kaken.model.vo;

/**
 * 事業管理情報検索条件を保持するクラス。
 * 
 * ID RCSfile="$RCSfile: JigyoKanriSearchInfo.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:13 $"
 */
public class JigyoKanriSearchInfo extends SearchInfo{

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------
	//static final long serialVersionUID = -5037219847097453179L;
	
	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/** 事業ID */
	private JigyoKanriPk[]    jigyoPks;
	
	/** 事業区分 */
	private String[]    jigyoKubun;

    /** 事業コード(複数件可、カンマ区切り) */
    private String    jigyoCds;
	//・・・・・・・・・・

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

    /**
	 * コンストラクタ。
	 */
	public JigyoKanriSearchInfo() {
		super();
	}

	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------
	/**
     * 事業IDを取得
	 * @return JigyoKanriPk[] 事業ID
	 */
	public JigyoKanriPk[] getJigyoPks() {
		return jigyoPks;
	}

	/**
     * を設定
	 * @param pks
	 */
	public void setJigyoPks(JigyoKanriPk[] pks) {
		jigyoPks = pks;
	}

    /**
     * 事業区分を取得
     * @return String[] 事業区分
     */
	public String[] getJigyoKubun() {
		return jigyoKubun;
	}

    /**
     * 事業区分を設定
     * @param jigyoKubun 事業区分
     */
	public void setJigyoKubun(String[] jigyoKubun) {
		this.jigyoKubun = jigyoKubun;
	}

    /**
     * 事業コード(複数件可、カンマ区切り)を取得
     * @return String 事業コード(複数件可、カンマ区切り)
     */
    public String getJigyoCds() {
        return jigyoCds;
    }

    /**
     * 事業コード(複数件可、カンマ区切り)を設定
     * @param jigyoCds 事業コード(複数件可、カンマ区切り)
     */
    public void setJigyoCds(String jigyoCds) {
        this.jigyoCds = jigyoCds;
    }
}