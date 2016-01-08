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
package jp.go.jsps.kaken.model.common;

/**
 * サービス名を定数する。
 * 
 * ID RCSfile="$RCSfile: IServiceName.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:30 $"
 */
public interface IServiceName {
	
	/** サービス実装参照名 */
	public static final String APPLICATION_SERVICE = "APPLICATION.SERVICE";

	/** 業務担当者ログオンサービス */
	public static final String GYOMUTANTO_LOGON_SERVICE = "gyomutanto.logon.service";

	/** 申請者ログオンサービス */
	public static final String SHINSEISHA_LOGON_SERVICE = "shinseisha.logon.service";
 	
	/** 申請者情報管理サービス */
	public static final String SHINSEISHA_MAINTENANCE_SERVICE = "shinseisha.maintenance.service";
	
	/** 所属機関情報管理サービス */
	public static final String SHOZOKU_MAINTENANCE_SERVICE = "shozoku.maintenance.service";
	
	/** 事業情報管理サービス */
	public static final String JIGYOKANRI_MAINTENANCE_SERVICE = "jigyoKanri.maintenance.service";

	/** 業務担当者情報管理サービス */
	public static final String GYOMUTANTO_MAINTENANCE_SERVICE = "gyomutanto.maintenance.service";

	/** PDF変換サービス */
	public static final String CONVERT_SERVICE = "convert.service";
	
	/** 申請書情報管理サービス */
	public static final String SHINSEI_MAINTENANCE_SERVICE = "shinsei.maintenance.service";

	/** 各ID・パスワードの発行ルール管理サービス */
	public static final String RULE_MAINTENANCE_SERVICE = "rule.maintenance.service";
	
	/** 審査員情報管理サービス */
	public static final String SHINSAIN_MAINTENANCE_SERVICE = "shinsain.maintenance.service";
	
	/** 審査員割り振りサービス */
	public static final String SHINSAIN_WARIFURI_SERVICE = "shinsain.warifuri.service";
	
	/** 審査結果サービス */
	public static final String SHINSAKEKKA_MAINTENANCE_SERVICE = "shinsakekka.maintenance.service";
	
	/** 審査員割り振りメイン処理 */
	public static final String WARIFURI_EXECUTE_SERVICE = "warifuri.execute.service";
	
	/** システム管理者サービス */
	public static final String SYSTEM_MAINTENANCE_SERVICE = "system.maintenance.service";

	/** 審査状況確認サービス */
	public static final String SHINSAJOKYO_KAKUNIN_SERVICE = "shinsajokyo.kakunin.service";
	
	/** データ保管サービス */
	public static final String DATA_HOKAN_MAINTENANCE_SERVICE = "data.hokan.maintenance.service";
	
	/** ラベル管理サービス */
	public static final String LABEL_VALUE_MAINTENANCE_SERVICE = "label.value.maintenance.service";

	/** 学術システム研究センタ情報管理サービス */
	public static final String GAKUJUTU_MAINTENANCE_SERVICE = "gakujutu.maintenance.service";
	
	/** コード表表示サービス */
	public static final String CODE_MAINTENANCE_SERVICE = "code.maintenance.service";	
	
	/** 関連分野研究者情報管理サービス */
	public static final String KANRENBUNYA_MAINTENANCE_SERVICE = "kanrenbunya.maintenance.service";	
	
	/**パンチデータサービス */
	public static final String PUNCHDATA_MAINTENANCE_SERVICE = "punchdata.maintenance.service";
	
// 2005/03/25 追加 ここから--------------------------------------------
// 理由 部局担当者情報の追加による
	
	/**部局担当者情報管理サービス */
	public static final String BUKYOKUTANTO_MAINTENANCE_SERVICE = "bukyokutanto.maintenance.service";
	
// 追加 ここまで-------------------------------------------------------
	
// 2005/03/28 追加 ここから--------------------------------------------
// 理由 研究者マスタ情報の追加による
	
	/**研究者情報管理サービス */
	public static final String KENKYUSHA_MAINTENANCE_SERVICE = "kenkyusha.maintenance.service";
	
// 追加 ここまで-------------------------------------------------------

// 2005/03/30 追加 ここから--------------------------------------------
// 理由 チェックリスト情報の追加による
   
   /**チェックリスト管理サービス */
   public static final String CHECKLIST_MAINTENANCE_SERVICE = "checklist.maintenance.service";
	
//追加 ここまで-------------------------------------------------------

//2005/05/20 追加
	/** ご意見情報管理サービス */
	public static final String IKEN_MAINTENANCE_SERVICE = "iken.maintenance.service";
	
//2005/10/26 追加 ここから------------------------------------------------
    /** アンケート情報管理サービス */
    public static final String QUESTION_MAINTENANCE_SERVICE = "question.maintenance.service";

//追加 ここまで-----------------------------------------------------------
//2006/06/14 by jzx add start   
    /** 領域計画書（概要）情報管理 */
    public static final String TEISHUTU_MAINTENANCE_SERVICE = "teishutu.maintenance.service";
//2006/06/14 by jzx add end  
}
