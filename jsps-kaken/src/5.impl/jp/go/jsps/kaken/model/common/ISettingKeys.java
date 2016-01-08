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
 * アプリケーション設定ファイルキーを定義する。
 * 
 * ID RCSfile="$RCSfile: ISettingKeys.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:30 $"
 */
public interface ISettingKeys {
	
	/** 共通設定ファイルのリソースバンドル名　*/
	public static final String BUNDLE_NAME = "ApplicationSettings";
	
	/** 変数パラメータ名 */
	public static final String VARIABLE_PARAM = "VARIABLE_PARAM";
	
	/** 変数の値 */
	public static final String VARIABLE_VALUE = "VARIABLE_VALUE";
	
	//------------
	
	/** PDFオートコンバータ INフォルダ(変換したいファイルをこのフォルダに格納)。　*/
	public static final String PDF_IN_FOLDER = "PDF_IN_FOLDER";

	/** PDFオートコンバータ OUTフォルダ(変換したファイルをこのフォルダに自動で出力)。　*/
	public static final String PDF_OUT_FOLDER = "PDF_OUT_FOLDER";

	/** PDFオートコンバータ STATUSフォルダ(変換したステータスをこのフォルダに自動で出力)。 */
	public static final String PDF_STATUS_FOLDER = "PDF_STATUS_FOLDER";

	/** PDFオートコンバータ ERRフォルダ(変換に失敗した場合、変換元ファイルをこのフォルダに保存)。　*/
	public static final String PDF_ERR_FOLDER = "PDF_ERR_FOLDER";

	/** PDFオートコンバータ PDF変換ファイル監視間隔(s) */
	public static final String PDF_REFRESH_SECONDS = "PDF_REFRESH_SECONDS";

	/** PDF出力(WEBDOC)設定情報ファイル(事業区分と帳票テンプレートの関連ファイル) */
	public static final String PDF_REPORT_SETTING_FILE_PATH = "PDF_REPORT_SETTING_FILE_PATH";

	/** PDF出力(WEBDOC)作業フォルダ */
	public static final String PDF_WORK_FOLDER = "PDF_WORK_FOLDER";

	/** 
	 * WORDファイル→PDF変換タイムアウト(秒) 
	 * 0の場合はタイムアウトしない。
	 */
	public static final String PDF_TIMEOUT = "PDF_TIMEOUT";
	
	/** PDF変換サーブレットURL */	
	public static final String PDF_CONV_SERVLET_URL = "PDF_CONV_SERVLET_URL";

	//2006.07.03 iso PDF変換サーバ振り分け処理のため追加
	/** PDF変換サーブレットURLの重み付け */	
	public static final String PDF_CONV_SERVLET_WEIGHTS = "PDF_CONV_SERVLET_WEIGHTS";

	
	//2006.07.03 iso 添付ファイル変換サーバ振り分け処理のため追加
	public static final String ANNEX_CONV_SERVLET_URL = "ANNEX_CONV_SERVLET_URL";

	public static final String ANNEX_CONV_SERVLET_WEIGHTS = "ANNEX_CONV_SERVLET_WEIGHTS";
	
	
	/** 申請書XMLファイル格納フォルダ(本フォルダ配下に「事業ID\システム受付番号\xml」と続く) */
	public static final String SHINSEI_XML_FOLDER = "SHINSEI_XML_FOLDER";
    
    /** XMLテンプレートファイル(Shift_JIS) */
    public static final String SHINSEI_XML_TEMPLATE = "SHINSEI_XML_TEMPLATE";

	/** 申請書PDFファイル格納フォルダ(本フォルダ配下に「事業ID\システム受付番号\pdf」と続く) */
	public static final String SHINSEI_PDF_FOLDER = "SHINSEI_PDF_FOLDER";

    //2006.09.25 iso iso タイトルに「概要」をつけたPDF作成のため
	/** 領域計画書PDFファイル(概要つき)格納フォルダ(本フォルダ配下に「事業ID」と続く) */
	public static final String RG_PDF_FOLDER = "RG_PDF_FOLDER";

// 2006/06/27 dyh add start
    /** 領域計画書表紙PDFファイル格納フォルダ(本フォルダ配下に「事業ID\仮領域番号\pdf」と続く) */
    public static final String PDF_DOMAINCOVER = "PDF_DOMAINCOVER";
// 2006/06/27 dyh add end

	/** 申請書添付ファイル格納フォルダ(本フォルダ配下に「事業ID\システム受付番号\word」と続く) */
	public static final String SHINSEI_ANNEX_FOLDER = "SHINSEI_ANNEX_FOLDER";

	/** 申請書添付PDFファイル格納フォルダ(本フォルダ配下に「事業ID\システム受付番号\word」と続く) */
	public static final String SHINSEI_ANNEX_PDF_FOLDER = "SHINSEI_ANNEX_PDF_FOLDER";

	/** 申請書添付エラーPDFファイル格納フォルダ(結合不能のエラーPDFをこのフォルダに格納する) */
	public static final String SHINSEI_ANNEX_ERR_FOLDER = "SHINSEI_ANNEX_ERR_FOLDER";
	
	/** 申請書推薦書ファイル格納フォルダ（{0}=事業ID,{1}=システム受付番号） */
	public static final String SHINSEI_SUISEN_FOLDER = "SHINSEI_SUISEN_FOLDER";

	/** 申請書PDFファイル（パスワードロック無し版）格納フォルダ（{0}=事業ID,{1}=事業ごとの命名規則）*/
	public static final String SHINSEI_PDF_NO_PASSWORD = "SHINSEI_PDF_NO_PASSWORD";

	/** 申請内容入力用ファイル（Windows用）(本フォルダ配下に「事業ID\win\事業ID.doc」と続く) */
	public static final String SHINSEI_TENPUWIN_FOLDER = "SHINSEI_TENPUWIN_FOLDER";

	/** 申請内容入力用ファイル（Mac用）(本フォルダ配下に「事業ID\win\事業ID.doc」と続く) */
	public static final String SHINSEI_TENPUMAC_FOLDER = "SHINSEI_TENPUMAC_FOLDER";

	/** 評価用ファイル(本フォルダ配下に「事業ID\\事業ID.doc」と続く) */
	public static final String SHINSEI_HYOKA_FOLDER = "SHINSEI_HYOKA_FOLDER";
	
	/** 書類フォルダ(本フォルダ配下に「事業ID\\対象番号\\システム受付番号.pdf」と続く) */
	public static final String SHINSEI_SHORUI_FOLDER = "SHINSEI_SHORUI_FOLDER";
	
	/** 審査結果ファイル（事業ID\\システム受付番号\\shinsa\\審査員番号.doc）*/
	public static final String SHINSEI_KEKKA_FOLDER = "SHINSEI_KEKKA_FOLDER";
	
	//2005/04/13 追加 ここから-------------------------------------------------------------------
	//PDF表紙ファイルパス取得のため
	
	/** PDF表紙ファイル */
	public static final String PDF_COVER = "PDF_COVER";
	
	//追加 ここまで------------------------------------------------------------------------------
	
	//--------- <メール関係設定情報> ---------------
	/** メールサーバアドレス */
	public static final String SMTP_SERVER_ADDRESS = "SMTP_SERVER_ADDRESS"; 
	/** 差出人（統一して１つ） */
	public static final String FROM_ADDRESS = "FROM_ADDRESS";
	/** アラート通知用メールアドレス */
	public static final String TO_ADDRESS_FOR_ALERT = "TO_ADDRESS_FOR_ALERT";
	
	/** メール内容（申請者が申請書確認を完了したとき）「件名」 */
	public static final String SUBJECT_SHINSEISHO_KAKUNIN_KANRYO = "SUBJECT_SHINSEISHO_KAKUNIN_KANRYO";
	/** メール内容（申請者が申請書確認を完了したとき）「本文」 */
	public static final String CONTENT_SHINSEISHO_KAKUNIN_KANRYO = "CONTENT_SHINSEISHO_KAKUNIN_KANRYO";
    
//2006/06/27 追加　李義華　ここから    
    /** メール内容（応募者が仮領域番号発行情報を登録したとき）「件名」 */
    public static final String SUBJECT_KARIRYOIKINO_KAKUNIN_IRAI = "SUBJECT_KARIRYOIKINO_KAKUNIN_IRAI";
    /** メール内容（応募者が仮領域番号発行情報を登録したとき）「本文」 */
    public static final String CONTENT_KARIRYOIKINO_KAKUNIN_IRAI = "CONTENT_KARIRYOIKINO_KAKUNIN_IRAI";
//2006/06/27 追加　李義華　ここまで
 
//2006/06/29  追加　張建平　ここから  
    /** メール内容（領域代表者が研究計画調書を却下したとき）「件名」 */
    public static final String SUBJECT_RYOIKIDAIHYOSHA_KYAKKA = "SUBJECT_RYOIKIDAIHYOSHA_KYAKKA";
    /** メール内容（領域代表者が研究計画調書を却下したとき）「本文」 */
    public static final String CONTENT_RYOIKIDAIHYOSHA_KYAKKA = "CONTENT_RYOIKIDAIHYOSHA_KYAKKA";
    
    /** 確認申請書締め切り期限までの日付 */
    public static final String DATE_BY_KAKUNIN_TOKUSOKU = "DATE_BY_KAKUNIN_TOKUSOKU";
    /**メール内容（所属機関担当者への確認通知）「件名」 */
    public static final String SUBJECT_SHINSEISHO_KAKUNIN_TOKUSOKU = "SUBJECT_SHINSEISHO_KAKUNIN_TOKUSOKU";
    /**メール内容（所属機関担当者への確認通知）「本文」 */
    public static final String CONTENT_SHINSEISHO_KAKUNIN_TOKUSOKU = "CONTENT_SHINSEISHO_KAKUNIN_TOKUSOKU";
    
    /** 承認申請書締め切り期限までの日付 */
    public static final String DATE_BY_SHONIN_TOKUSOKU = "DATE_BY_KAKUNIN_TOKUSOKU";
    /**メール内容（所属機関担当者への承認通知）「件名」 */
    public static final String SUBJECT_RYOIKIGAIYO_SHONIN_TOKUSOKU = "SUBJECT_RYOIKIGAIYO_SHONIN_TOKUSOKU";
    /**メール内容（所属機関担当者への承認通知）「本文」 */
    public static final String CONTENT_RYOIKIGAIYO_SHONIN_TOKUSOKU = "CONTENT_RYOIKIGAIYO_SHONIN_TOKUSOKU";
//2006/06/29  追加　張建平　ここまで   
    
	/** メール内容（所属機関が申請書を承認したとき）「件名」 */
	public static final String SUBJECT_SHINSEISHO_SHOZOKUKIKAN_SHONIN = "SUBJECT_SHINSEISHO_SHOZOKUKIKAN_SHONIN";
	/** メール内容（所属機関が申請書を承認したとき）「本文」 */
	public static final String CONTENT_SHINSEISHO_SHOZOKUKIKAN_SHONIN = "CONTENT_SHINSEISHO_SHOZOKUKIKAN_SHONIN";
	/** メール内容（所属機関がチェックリストを確定したとき）「件名」 */
	public static final String SUBJECT_CHECKLIST_KAKUTEI = "SUBJECT_CHECKLIST_KAKUTEI";
	/** メール内容（所属機関がチェックリストを確定したとき）「本文」 */
	public static final String CONTENT_CHECKLIST_KAKUTEI = "CONTENT_CHECKLIST_KAKUTEI";

	/** 審査催促期限までの日付 */
	public static final String DATE_BY_SHINSA_KIGEN = "DATE_BY_SHINSA_KIGEN";
	/**メール内容（審査員への審査催促）「件名」 */
	public static final String SUBJECT_SHINSEISHO_SHINSA_SAISOKU = "SUBJECT_SHINSEISHO_SHINSA_SAISOKU";
	/**メール内容（審査員への審査催促）「本文」 */
	public static final String CONTENT_SHINSEISHO_SHINSA_SAISOKU = "CONTENT_SHINSEISHO_SHINSA_SAISOKU";

	/** 未承認申請書締め切り期限までの日付 */
	public static final String DATE_BY_SHONIN_KIGEN = "DATE_BY_SHONIN_KIGEN";
	/**メール内容（所属機関担当者への未承認確認通知）「件名」 */
	public static final String SUBJECT_SHINSEISHO_SHONIN_TSUCHI = "SUBJECT_SHINSEISHO_SHONIN_TSUCHI";
	/**メール内容（所属機関担当者への未承認確認通知）「本文」 */
	public static final String CONTENT_SHINSEISHO_SHONIN_TSUCHI = "CONTENT_SHINSEISHO_SHONIN_TSUCHI";
	
	/** メール内容（審査員が審査完了したとき）「件名」 */
	public static final String SUBJECT_SHINSAKEKKA_JURI_TSUCHI = "SUBJECT_SHINSAKEKKA_JURI_TSUCHI";
	/** メール内容（審査員が審査完了したとき）「本文」 */
	public static final String CONTENT_SHINSAKEKKA_JURI_TSUCHI = "CONTENT_SHINSAKEKKA_JURI_TSUCHI";
	
	
	//--------- <学振問い合わせ先情報> ---------------	
	/** 学振問い合わせ先郵便番号 */
	public static final String GAKUSHIN_TOIAWASE_YUBIN = "GAKUSHIN_TOIAWASE_YUBIN";
	/** 学振問い合わせ先住所 */	
	public static final String GAKUSHIN_TOIAWASE_JUSHO = "GAKUSHIN_TOIAWASE_JUSHO";

	//----------------------------------------------
	/** データソースタイプ */
	public static final String DB_DATA_SOURCE_TYPE = "DB_DATA_SOURCE_TYPE";
	/** データベースURL */
	public static final String DB_URL = "DB_URL";
	/** データベースユーザ名 */
	public static final String DB_USER = "DB_USER";
	/** データベースパスワード */
	public static final String DB_PASSWORD = "DB_PASSWORD";
	/** MINコネクション数の設定 */
	public static final String DB_MIN_LIMIT = "DB_MIN_LIMIT";
	/** MAXコネクション数 */
	public static final String DB_MAX_LIMIT = "DB_MAX_LIMIT";
	/** 初期コンテキストファクトリ */
	public static final String DB_INITIAL_CONTEXT_FACTORY = "DB_INITIAL_CONTEXT_FACTORY";
	/** データソースサービスプロバイダ */
	public static final String DB_PROVIDER_URL = "DB_PROVIDER_URL";
	/** データソース名 */
	public static final String DB_DATA_SOURCE_NAME = "DB_DATA_SOURCE_NAME";
	
	
	//-----------------------------------------------
	/** 業務サーバURL */
	public static final String GYOMU_SERVLET_URL = "GYOMU_SERVLET_URL";
	
	
	//--------- <申請処理> ---------------
	/** システム受付番号リトライ回数 */		
	public static final String SYSTEM_NO_MAX_RETRY_COUNT = "SYSTEM_NO_MAX_RETRY_COUNT";
	/** 重複申請チェックフラグ */
	public static final String CHECK_DUPLICACATION_FLAG = "CHECK_DUPLICACATION_FLAG";
	/** 添付ファイル送信処理・申請登録処理同期化試行回数（1秒間隔）*/
	public static final String TRY_COUNT_SYNCHRONIZE    = "TRY_COUNT_SYNCHRONIZE";
	//2005/04/18 追加 ここから-------------------------------------------------------------------
	//チェックデジットチェックをフラグで制御するため
	/** チェックデジットフラグ */
	public static final String CHECK_DIGIT_FLAG = "CHECK_DIGIT_FLAG";
	//追加 ここまで------------------------------------------------------------------------------
	
	//--------- <審査依頼通知書出力処理> ---------------
	/** 審査依頼通知書ファイル格納フォルダ */		
	public static final String IRAI_WORK_FOLDER = "IRAI_WORK_FOLDER";
	/** 審査依頼書フォーマットパス */
	public static final String IRAI_FORMAT_PATH = "IRAI_FORMAT_PATH";
	/** 審査依頼書フォーマット名 */
	public static final String IRAI_FORMAT_FILE_NAME = "IRAI_FORMAT_FILE_NAME";
	//--------- <審査依頼通知書（審査員管理用）出力処理> ---------------
	/** 審査依頼通知書（審査員管理用）ファイル格納フォルダ */		
	public static final String IRAI_WORK_FOLDER2 = "IRAI_WORK_FOLDER2";
	/** 審査依頼書（審査員管理用）フォーマットパス */
	public static final String IRAI_FORMAT_PATH2 = "IRAI_FORMAT_PATH2";
	/** 審査依頼書（審査員管理用）フォーマット名 */
	public static final String IRAI_FORMAT_FILE_NAME2 = "IRAI_FORMAT_FILE_NAME2";
	//--------- <IDパスワード通知書出力処理> ---------------
	/** IDパスワード通知書ファイル格納フォルダ */		
	public static final String SHINSEISHA_WORK_FOLDER = "SHINSEISHA_WORK_FOLDER";
	/** IDパスワード通知書フォーマットパス */
	public static final String SHINSEISHA_FORMAT_PATH = "SHINSEISHA_FORMAT_PATH";
	/** IDパスワード通知書フォーマット名 */
	public static final String SHINSEISHA_FORMAT_FILE_NAME = "SHINSEISHA_FORMAT_FILE_NAME";
	

	//--------- <評価結果出力処理> ---------------
	/** 評価結果ファイル格納フォルダ */		
	public static final String HYOKA_WORK_FOLDER = "HYOKA_WORK_FOLDER";
	/** 評価結果フォーマットパス */
	public static final String HYOKA_FORMAT_PATH = "HYOKA_FORMAT_PATH";
	/** 評価結果フォーマット名 */
	public static final String HYOKA_FORMAT_FILE_NAME = "HYOKA_FORMAT_FILE_NAME";
	
	
	//--------- <割り振りチェック依頼通知書出力処理> ---------------
	/** チェック依頼通知書ファイル格納フォルダ */		
	public static final String CHECKIRAI_WORK_FOLDER = "CHECKIRAI_WORK_FOLDER";
	/** チェック依頼書フォーマットパス */
	public static final String CHECKIRAI_FORMAT_PATH = "CHECKIRAI_FORMAT_PATH";
	/** チェック依頼書フォーマット名 */
	public static final String CHECKIRAI_FORMAT_FILE_NAME = "CHECKIRAI_FORMAT_FILE_NAME";
	
	//--------- <応募書類の提出書出力設定処理> ---------------
	/** 応募書類の提出書ファイル格納フォルダ */		
	public static final String OUBO_WORK_FOLDER = "OUBO_WORK_FOLDER";
	/** 応募書類の提出書フォーマットパス */
	public static final String OUBO_FORMAT_PATH = "OUBO_FORMAT_PATH";
	/** 応募書類の提出書フォーマット名 */
	public static final String OUBO_FORMAT_FILE_NAME_KIBAN = "OUBO_FORMAT_FILE_NAME_KIBAN";
	public static final String OUBO_FORMAT_FILE_NAME_TOKUTEI = "OUBO_FORMAT_FILE_NAME_TOKUTEI";
    public static final String OUBO_FORMAT_FILE_NAME_TOKUTEI_SHINKI = "OUBO_FORMAT_FILE_NAME_TOKUTEI_SHINKI";
	public static final String OUBO_FORMAT_FILE_NAME_WAKATESTART = "OUBO_FORMAT_FILE_NAME_WAKATESTART";
	public static final String OUBO_FORMAT_FILE_NAME_SHOKUSHINHI = "OUBO_FORMAT_FILE_NAME_SHOKUSHINHI";
	
	//--------- <マスタ取込> ---------------
	/** DBエクスポートコマンド */		
	public static final String EXPORT_COMMAND = "EXPORT_COMMAND";
	/** CSV取り込み先 */
	public static final String CSV_TORIKOMI_LOCATION = "CSV_TORIKOMI_LOCATION";	
	
	//---------<パンチデータ>---------------
	/** パン手データ保管先 */
	public static final String PUNCHDATA_HOKAN_LOCATION = "PUNCHDATA_HOKAN_LOCATION";
	
	//--------- <データ保管設定> ---------------
	/** データ保管サーバDBリンク名 */
	public static final String HOKAN_SERVER_DB_LINK = "HOKAN_SERVER_DB_LINK";
	/** データ保管サーバUNC */
	public static final String HOKAN_SERVER_UNC = "HOKAN_SERVER_UNC";
	/** UNCに変換するドライブレター */
	public static final String DRIVE_LETTER_CONVERTED_TO_UNC = "DRIVE_LETTER_CONVERTED_TO_UNC";
	/** データ保管サーバにコピーするディレクトリ */
	public static final String HOKAN_TARGET_DIRECTORY = "HOKAN_TARGET_DIRECTORY";
	
	//--------- <CertWorker CSVフォーマット設定> ---------------
	/** profile name */
	public static final String PROFILE_NAME = "PROFILE_NAME";
	/** subject DN */
	public static final String SUBJECT_DN = "SUBJECT_DN";	
	/** subjectAltName */
	public static final String SUBJECT_ALT_NAME = "SUBJECT_ALT_NAME";
	/** pubkey algo */
	public static final String PUBKEY_ALGO = "PUBKEY_ALGO";
	/** key length */
	public static final String KEY_LENGTH = "KEY_LENGTH";
	/** p12 flag */
	public static final String P12_FLAG = "P12_FLAG";	
		
    //--------- <Velocity設定> ---------------
    /** Velocity表示中に例外が発生したときに表示するページURL(コンテキストルートからの相対パス) */
    public static final String VM_ERROR_PAGE = "VM_ERROR_PAGE";   
    
	//--------- <一覧設定> ---------------
	/** 一覧画面におけるページサイズ数 */
	public static final String PAGE_SIZE = "PAGE_SIZE";   
	/** #該当件数のMAX値 */
	public static final String MAX_RECORD_COUNT = "MAX_RECORD_COUNT";   	
	
	
	//--------- <メモリチェック設定> ---------------
	/** 使用メモリチェック比率（パーセンテージ）*/
	public static final String MAX_MEMORY_USED_RATE = "MAX_MEMORY_USED_RATE";
	/**Sorryページとして表示する画面(コンテキストルートからの相対パス)*/
	public static final String MAX_MEMORY_ERROR_PAGE = "MAX_MEMORY_ERROR_PAGE";
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
