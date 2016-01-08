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
package jp.go.jsps.kaken.web.shinsei;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import jp.go.jsps.kaken.model.common.ApplicationSettings;
import jp.go.jsps.kaken.model.common.IJigyoCd;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.common.ISettingKeys;
import jp.go.jsps.kaken.model.common.SystemServiceFactory;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.impl.ShinseishaMaintenance;
import jp.go.jsps.kaken.model.vo.ErrorInfo;
import jp.go.jsps.kaken.model.vo.ShinseiDataInfo;
import jp.go.jsps.kaken.model.vo.ShinseishaSearchInfo;
import jp.go.jsps.kaken.model.vo.shinsei.DaihyouInfo;
import jp.go.jsps.kaken.model.vo.shinsei.KenkyuSoshikiKenkyushaInfo;
import jp.go.jsps.kaken.util.Page;
import jp.go.jsps.kaken.util.StringUtil;
import jp.go.jsps.kaken.web.common.IConstants;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.shinsei.validate.DefaultShinseiValidator;
import jp.go.jsps.kaken.web.shinsei.validate.IShinseiValidator;
import jp.go.jsps.kaken.web.shinsei.validate.ShinseiValidatorFactory;
import jp.go.jsps.kaken.web.shinsei.validate.ShinseiValidatorGakusou;
import jp.go.jsps.kaken.web.shinsei.validate.ShinseiValidatorKibanAIppan;
import jp.go.jsps.kaken.web.shinsei.validate.ShinseiValidatorKibanAKaigai;
import jp.go.jsps.kaken.web.shinsei.validate.ShinseiValidatorKibanBIppan;
import jp.go.jsps.kaken.web.shinsei.validate.ShinseiValidatorKibanBKaigai;
import jp.go.jsps.kaken.web.shinsei.validate.ShinseiValidatorKibanCIppan;
import jp.go.jsps.kaken.web.shinsei.validate.ShinseiValidatorKibanCKikaku;
import jp.go.jsps.kaken.web.shinsei.validate.ShinseiValidatorKibanHoga;
import jp.go.jsps.kaken.web.shinsei.validate.ShinseiValidatorKibanS;
import jp.go.jsps.kaken.web.shinsei.validate.ShinseiValidatorKibanWakateA;
import jp.go.jsps.kaken.web.shinsei.validate.ShinseiValidatorKibanWakateB;
import jp.go.jsps.kaken.web.shinsei.validate.ShinseiValidatorKibanWakateS;
import jp.go.jsps.kaken.web.shinsei.validate.ShinseiValidatorShokushinhiKibanA;
import jp.go.jsps.kaken.web.shinsei.validate.ShinseiValidatorShokushinhiKibanB;
import jp.go.jsps.kaken.web.shinsei.validate.ShinseiValidatorShokushinhiKibanC;
import jp.go.jsps.kaken.web.shinsei.validate.ShinseiValidatorShokushinhiWakateA;
import jp.go.jsps.kaken.web.shinsei.validate.ShinseiValidatorShokushinhiWakateB;
import jp.go.jsps.kaken.web.shinsei.validate.ShinseiValidatorTokusui;
import jp.go.jsps.kaken.web.shinsei.validate.ShinseiValidatorTokutei;
import jp.go.jsps.kaken.web.shinsei.validate.ShinseiValidatorTokuteiSinki;
import jp.go.jsps.kaken.web.shinsei.validate.ShinseiValidatorWakatestart;
import jp.go.jsps.kaken.web.struts.BaseValidatorForm;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

/**
 * ID RCSfile="$RCSfile: ShinseiForm.java,v $" Revision="$Revision: 1.34 $"
 * Date="$Date: 2007/07/25 08:53:00 $"
 */
public class ShinseiForm extends BaseValidatorForm {

	// ---------------------------------------------------------------------
	// Static data
	// ---------------------------------------------------------------------

	/**
     * <code>serialVersionUID</code> のコメント
     */
    private static final long serialVersionUID = -8594326959248568097L;

	/** ログ */
	protected static Log log = LogFactory.getLog(ShinseiForm.class);

	/** 添付ファイル送信処理・申請登録処理同期化試行回数（1秒間隔） */
	private static int    TRY_COUNT_SYNCHRONIZE  = ApplicationSettings.getInt(ISettingKeys.TRY_COUNT_SYNCHRONIZE);

	// ---------------------------------------------------------------------
	// Instance data
	// ---------------------------------------------------------------------
	
    /** 仮領域番号 */
    private String kariryoikiNo;
	
	/** 申請データ情報 */
	private ShinseiDataInfo shinseiDataInfo = new ShinseiDataInfo();

	/** 系統の区分リスト */
	private List keitouList = new ArrayList();

	/** 推薦の観点リスト */
	private List suisenList = new ArrayList();

	/** 職リスト */
	private List shokushuList = new ArrayList();

	/** 新規・継続区分リスト */
	private List shinkiKeibetuList = new ArrayList();

	/** 前年度の応募リスト */
	private List zennendoList = new ArrayList();

	/** 分担金の有無リスト */
	private List buntankinList = new ArrayList();

	/** 開示希望の有無リスト */
	private List kaijiKiboList = new ArrayList();

	// 2005/04/08 追加 ここから----------------------------
	/** 研究対象の類型 */
	private List kenkyuTaishoList = new ArrayList();

	/** 審査希望分野 */
	private List shinsaKiboBunyaList = new ArrayList();
	//2006/02/15
	/** 審査希望領域 */
	private List shinsaKiboRyoikiList = new ArrayList();

	// 追加 ここまで--------------------------------------

	/** アップロードファイルアクションとの同期化フラグ */
	private boolean uploadActionEnd = false;

	/** アップロードファイル */
	private FormFile uploadFile = null;

	/** 様式種別（事業コードの3,4ケタ） */
	private String yoshikiShubetu = null;

	/** リストインデックス */
	private int listIndex = -1;

	/** 追加する研究組織の研究者の分担フラグ */
	private String addBuntanFlg = null;

	// ・・・・・・・・・・

	// 20050526 Start
	/** 研究領域リスト */
	private List kenkyuKubunList = new ArrayList();

	// Horikoshi End

	// 2006/02/08 Start
	/** 資格取得年月日の年 */
	private String sikakuDateYear = null;

	/** 資格取得年月日の月 */
	private String sikakuDateMonth = null;

	/** 資格取得年月日の日 */
	private String sikakuDateDay = null;

	/** 資格取得前機関名 */
	private String syutokumaeKikan = null;

	/** 育休等開始日の年 */
	private String ikukyuStartDateYear = null;

	/** 育休等開始日の月 */
	private String ikukyuStartDateMonth = null;

	/** 育休等開始日の日 */
	private String ikukyuStartDateDay = null;

	/** 育休等終了日の年 */
	private String ikukyuEndDateYear = null;

	/** 育休等終了日の月 */
	private String ikukyuEndDateMonth = null;

	/** 育休等終了日の日 */
	private String ikukyuEndDateDay = null;
	
	/** 資格取得年リスト */
	private List sikakuDateList = new ArrayList();
	
	/** 育休等開始年リスト */
	private List ikukyuStartDateList = new ArrayList();
	
	/** 採用年月日の年 */
    private String saiyoDateYear;
    
    /** 採用年月日の月 */
    private String saiyoDateMonth;
    
    /** 採用年月日の日 */
    private String saiyoDateDay;

	// Byou End
	// ---------------------------------------------------------------------
	// Constructors
	// ---------------------------------------------------------------------

	/**
	 * @return Returns the sikakuDateList.
	 */
	public List getSikakuDateList() {
		return sikakuDateList;
	}

	/**
	 * @param sikakuDateList The sikakuDateList to set.
	 */
	public void setSikakuDateList(List sikakuDateList) {
		this.sikakuDateList = sikakuDateList;
	}

	/**
	 * コンストラクタ。
	 */
	public ShinseiForm() {
		super();
		init();
	}

	// ---------------------------------------------------------------------
	// Private methods
	// ---------------------------------------------------------------------

	/**
	 * アップロードファイルアクションとの同期化を行う。 アップロードフラグがtrueになるまで処理を中断する。
	 * フラグがfalseの場合は1秒後に再度確認を行う。 TRY_COUNT_SYNCHRONIZEで設定されている回数分試行しても
	 * 同じ場合はタイムアウトとみなす。
	 * 
	 * @return タイムアウトが発生した場合false
	 */
	private boolean synchronizedUploadFileAction() {

		// 2005.08.08 iso 同期化ログの追加
		log.info("ファイルアップロード同期化開始");

		long sleepTime = 1000;
		try {
			for (int i = 0; i < TRY_COUNT_SYNCHRONIZE; i++) {
				if (isUploadActionEnd()) {
					return true; // アップロードアクションが完了していた場合はreturnする。
				} else {
					try {
						Thread.sleep(sleepTime);
					} catch (InterruptedException e) {
					}
				}
			}
			return false;
		} finally {
			setUploadActionEnd(false); // フラグの初期化
		}
	}

	/*
	 * 初期化処理。 (非 Javadoc)
	 * 
	 * @see org.apache.struts.action.ActionForm#reset(org.apache.struts.action.ActionMapping,
	 *      javax.servlet.http.HttpServletRequest)
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		// ・・・
	}

	/**
	 * 初期化処理
	 */
	public void init() {
	}

	/*
	 * 入力チェック。 (非 Javadoc)
	 * 
	 * @see org.apache.struts.action.ActionForm#validate(org.apache.struts.action.ActionMapping,
	 *      javax.servlet.http.HttpServletRequest)
	 */
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {

		// ActionMappingパラメータに"synchronized"が指定されていた場合のみ
		if ("synchronized".equals(mapping.getParameter())) {
			// アップロードファイルアクションとの同期化
			if (!synchronizedUploadFileAction()) {
				log.error("UploadFileActionとの同期化においてタイムアウトが発生しました。");
				ActionErrors errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
						"errors.timeout"));
				return errors;
			}
		}
		
// ADD START 2007-07-10 BIS 王志安
		if ((IJigyoCd.JIGYO_CD_TOKUSUI.equals(shinseiDataInfo.getJigyoCd())
			|| IJigyoCd.JIGYO_CD_TOKUTEI_KEIZOKU.equals(shinseiDataInfo.getJigyoCd())) 
			&& page == 2 && "2".equals(shinseiDataInfo.getShinseiKubun())
			&& (shinseiDataInfo.getKenkyuKeihiSoukeiInfo().getNaiyakuTotal() == null
				|| "".equals(shinseiDataInfo.getKenkyuKeihiSoukeiInfo().getNaiyakuTotal()) 
				|| "0".equals(shinseiDataInfo.getKenkyuKeihiSoukeiInfo().getNaiyakuTotal()))) {
			
			ActionErrors errors = new ActionErrors();
			// [継続課題情報取得]ボタンをクリックで、内約額を確認してください。
			errors.add("shinseiForm.confirmNaiyakugaku", new ActionError(
					"errors.9038"));
			return errors;
		}
// ADD END 2007-07-10 BIS 王志安
		
//ADD　START 2007/07/11 BIS 金京浩  
        if ("kenkyusoshiki".equals(mapping.getParameter())) {
			ActionErrors errors = super.validate(mapping, request);
			//研究組織表リストをアクションフォームから取得する
			List kenkyuSoshikiIfo = this.getShinseiDataInfo().getKenkyuSoshikiInfoList();
			   
			boolean bunfanFlagCheck = false;
			boolean renkeiFlagCheck = false;
			
			for (int i = 1; kenkyuSoshikiIfo != null && i < kenkyuSoshikiIfo.size(); i++) {
				KenkyuSoshikiKenkyushaInfo kenkyuSoshikiKenkyushaInfo =
					            (KenkyuSoshikiKenkyushaInfo) kenkyuSoshikiIfo.get(i);

				String buntanFlag = kenkyuSoshikiKenkyushaInfo.getBuntanFlag() ;				
				
				if("5".equals(buntanFlag)){
					continue;
				}
				
				//申請者検索条件オブジェクトの実例化
				ShinseishaSearchInfo searchInfo = new ShinseishaSearchInfo();
				//科研研究者番号の設定
				searchInfo.setKenkyuNo(kenkyuSoshikiKenkyushaInfo.getKenkyuNo());
				//セッションから使用する情報を取得する
				UserContainer container = (UserContainer) request.getSession()
				                .getAttribute(IConstants.USER_CONTAINER_KEY);
				if (container == null) {
					container = new UserContainer();
					HttpSession session = request.getSession(true);
					session.setAttribute(IConstants.USER_CONTAINER_KEY, container);
				}

				try {
					if (searchInfo.getKenkyuNo() != null
							&& !"".equals(searchInfo.getKenkyuNo())) {
						Page result = new ShinseishaMaintenance().getKenkyushaInfoByKenkyuNo(container.getUserInfo(), searchInfo);
						Map mapTemp = (Map) result.getList().get(0);
						String nyuuryokuSei = StringUtil
								.toOomojiDigit(kenkyuSoshikiKenkyushaInfo.getNameKanaSei());
						String nyuuryokuMei = StringUtil
								.toOomojiDigit(kenkyuSoshikiKenkyushaInfo.getNameKanaMei());
						String mapSei = StringUtil
								.toOomojiDigit((String) mapTemp.get("NAME_KANA_SEI"));
						String mapMei = StringUtil
								.toOomojiDigit((String) mapTemp.get("NAME_KANA_MEI"));

						// 研究者マスタのフリガナ姓・フリガナ名に、全角スペースが１文字登録されている場合は、その項目の比較チェックは行わない。
						// フリガナ姓名が、どちらも全角スペース１文字ということはない。
						// 全角スペース１文字だけでなく、NULLのときも同様の動作とする。
						boolean flagTemp = false;
						if (mapSei == null || "".equals(mapSei) || mapSei.indexOf('　') >= 0) {
							flagTemp = !flagTemp;
						}
						if (mapMei == null || "".equals(mapMei) || mapMei.indexOf('　') >= 0) {
							flagTemp = !flagTemp;
						}
						if (flagTemp) {
							continue;
						}

						// 研究者番号をキーとして、「画面入力値のフリガナ姓」が「研究者マスタのフリガナ姓」に含まれるかをチェックする。
						// 研究者番号をキーとして、「研究者マスタのフリガナ姓」が「画面入力値のフリガナ姓」に含まれるかをチェックする。
						// (1),(2)の比較結果のいずれか（もしくは両方）が「○」のとき、「フリガナ姓」は一致していると判定する。
						if (nyuuryokuSei != null && mapSei != null && !"".equals(nyuuryokuSei)
								&& (mapSei.indexOf(nyuuryokuSei) >= 0 || nyuuryokuSei.indexOf(mapSei) >= 0)) {
							continue;
						}
						// 「フリガナ姓」の比較』と同様の仕組みで、「フリガナ名」についても判定する。
						if (nyuuryokuMei != null && mapMei != null && !"".equals(nyuuryokuMei)
								&& (mapMei.indexOf(nyuuryokuMei) >= 0 || nyuuryokuMei.indexOf(mapMei) >= 0)) {
							continue;
						}
						
						if ("2".equals(buntanFlag)){
							bunfanFlagCheck = true;
						}else if("4".equals(buntanFlag)){
							renkeiFlagCheck = true ; 
						}
						
						String property = "shinseiDataInfo.kenkyuSoshikiInfoList["+ i + "].";
						
						ActionError error = new ActionError("errors.5074");
						
						errors.add(property + "nameKanaSei", error);
						
						errors.add(property + "nameKanaMei", error);
						
						errors.add(property + "kenkyuNo", error);

					}
				} catch (ApplicationException e) {
					if ("2".equals(buntanFlag)){
						bunfanFlagCheck = true;
					}else if("4".equals(buntanFlag)){
						renkeiFlagCheck = true ; 
					}
					String property = "shinseiDataInfo.kenkyuSoshikiInfoList["+ i + "].";
					ActionError error = new ActionError("errors.5074");
					errors.add(property + "nameKanaSei", error);
					errors.add(property + "nameKanaMei", error);
					errors.add(property + "kenkyuNo", error);
				}
			}
			if (bunfanFlagCheck) {
				String property = "shinseiDataInfo.kenkyuKeihiSoukeiInfo.NameKanaSeiORNameKanaMei.Bunfan";
				ActionError error = new ActionError("errors.5074" , "分担者");
				errors.add(property, error);
			}
			if (renkeiFlagCheck) {
				String property = "shinseiDataInfo.kenkyuKeihiSoukeiInfo.NameKanaSeiORNameKanaMei.Renkei";
				ActionError error = new ActionError("errors.5074" , "連携研究者");
				errors.add(property, error);
			}
			return errors;

		}
// ADD END 2007/07/11 BIS 金京浩
		
		
        
// 2007/02/06 劉長宇 追加 ここから
        if(IJigyoCd.JIGYO_CD_WAKATESTART.equals(this.getShinseiDataInfo().getJigyoCd())){
            //特別研究員奨励費課題番号-年度が2桁未満時自動0詰め.
            if (!StringUtil.isBlank(this.getShinseiDataInfo().getShoreihiNoNendo())) {
                this.getShinseiDataInfo().setShoreihiNoNendo(StringUtil.fillLZero(
                        StringUtil.toHankakuDigit(this.getShinseiDataInfo().getShoreihiNoNendo()), 2));
            }
            //特別研究員奨励費課題番号－整理番号が5桁未満時自動0詰め.
            if (!StringUtil.isBlank(this.getShinseiDataInfo().getShoreihiNoSeiri())) {
                this.getShinseiDataInfo().setShoreihiNoSeiri(StringUtil.fillLZero(
                        StringUtil.toHankakuDigit(this.getShinseiDataInfo().getShoreihiNoSeiri()), 5));
            }
        }
//2007/02/06 劉長宇　追加　ここまで

		// 2005/8/29 職名称の全角/半角空白を除去する
		String shokushu = this.getShinseiDataInfo().getDaihyouInfo()
				.getShokushuNameKanji();
		if (StringUtil.isSpaceString(shokushu)) {
			this.getShinseiDataInfo().getDaihyouInfo().setShokushuNameKanji("");
		}
		
		//2006/11/16 分割番号に「-」を入力したら、スペースに変更
		if ("-".equals(this.getShinseiDataInfo().getKadaiInfo().getBunkatsuNo())){
			this.getShinseiDataInfo().getKadaiInfo().setBunkatsuNo("");
		}

		// 定型処理-----
		ActionErrors errors = super.validate(mapping, request);

//		ADD　START 2007-07-19 BIS 劉多良
		//組み合わせのエラーを追加する
		addKumiaiError(errors);
//		ADD　END 2007-07-19 BIS 劉多良
		
		// ---------------------------------------------
		// 基本的なチェック(必須、形式等）はValidatorを使用する。

		// 研究費のチェックについては各Validatorクラスを使用する。
		// //研究経費の合計を算出しつつ、最大値チェックを行う
		// validateAndSetKeihiTotal(errors, page);

		// 2005.04.08 hashimoto 様式ごとのValidateの動作実験
		IShinseiValidator shinseiValidator = ShinseiValidatorFactory
				.getShinseiValidator(shinseiDataInfo);
		errors = shinseiValidator.validate(
				(jp.go.jsps.kaken.web.struts.ActionMapping) mapping, request,
				page, errors);

//全事業に対して、代表者の年齢をセットとなる 2007/3/19
//// 2006/02/13 Update Start
//		// 特別研究促進費と基盤若手は組織票が無いためチェックを行わない
//		if (!(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_A.equals(this.getShinseiDataInfo().getJigyoCd()) 
//			 || IJigyoCd.JIGYO_CD_KIBAN_WAKATE_B.equals(this.getShinseiDataInfo().getJigyoCd())
//			 || IJigyoCd.JIGYO_CD_SHOKUSHINHI_WAKATE_A.equals(this.getShinseiDataInfo().getJigyoCd())
//			 || IJigyoCd.JIGYO_CD_SHOKUSHINHI_WAKATE_B.equals(this.getShinseiDataInfo().getJigyoCd())
////2007/02/15 苗　追加ここから
//             || IJigyoCd.JIGYO_CD_KIBAN_WAKATE_S.equals(this.getShinseiDataInfo().getJigyoCd())
////2007/02/15　苗　追加ここまで
////2007/03/19 苗　追加ここから
//             || IJigyoCd.JIGYO_CD_WAKATESTART.equals(this.getShinseiDataInfo().getJigyoCd())
////2007/03/19　苗　追加ここまで             
//        )) {
//// Byou End
//
//			// 2005/06/02 削除
//			// ここから----------------------------------------------------------
//			// 各ShinseiValidaterクラスでvalidate処理を行うため削除
//			// 研究組織表の形式チェックを行う。
//			// validateKenkyuSoshiki(errors, page);
//			//
//			// countKenkyushaNinzu(errors, page);
//			// 削除
//			// ここまで---------------------------------------------------------------------
//		} else {
			DaihyouInfo daihyo = this.getShinseiDataInfo().getDaihyouInfo();
			if (daihyo != null) {
				List soshiki = this.getShinseiDataInfo().getKenkyuSoshikiInfoList();
				if (soshiki != null && soshiki.size() > 0) {
					daihyo.setNenrei(((KenkyuSoshikiKenkyushaInfo) soshiki.get(0)).getNenrei());
				}
			}
//		}
		
        // 基本入力チェックここまで
		if (!errors.isEmpty()) {		
			return errors;
		}

		// 定型処理-----

		// 追加処理-----

		// ---------------------------------------------
		// 組み合わせチェック
		// ---------------------------------------------

		return errors;

	}

	// 2005.08.08 iso 添付ファイル情報出力追加
	/*
	 * 添付ファイル情報出力。
	 */
	public void outputFileInfo() throws ApplicationException {

		log.info("ユーザID=" + shinseiDataInfo.getShinseishaId());
		if (uploadFile == null) {
			log.info("添付ファイルなし");
		} else {
			try {
				log.info("ファイル名=" + uploadFile.getFileName());
				log.info("ファイルサイズ=" + uploadFile.getFileData().length);
			} catch (IOException e) {
				throw new ApplicationException("添付ファイルの取得に失敗しました。",
						new ErrorInfo("errors.7000"), e);
			}
		}
	}

	// 2005.10.25 iso エラーログ出力機能
	/*
	 * エラーメッセージのログ出力。
	 */
	public void outputErrors(ActionErrors errors) {

		log.info("申請者ID=" + shinseiDataInfo.getShinseishaId());

		for (Iterator iter = errors.get(); iter.hasNext();) {
			ActionError value = (ActionError) iter.next();
			String out = value.getKey();
			Object[] values = value.getValues();
			for (int j = 0; j < values.length; j++) {
				out += " " + values[j].toString();
			}
			log.info(out);
		}
	}

	// ---------------------------------------------------------------------
	// Properties
	// ---------------------------------------------------------------------

	/**
	 * @return
	 */
	public List getKeitouList() {
		return keitouList;
	}

	/**
	 * @return
	 */
	public ShinseiDataInfo getShinseiDataInfo() {
		return shinseiDataInfo;
	}

	/**
	 * @return
	 */
	public List getShokushuList() {
		return shokushuList;
	}

	/**
	 * @return
	 */
	public List getSuisenList() {
		return suisenList;
	}

	/**
	 * @return
	 */
	public boolean isUploadActionEnd() {
		return uploadActionEnd;
	}

	/**
	 * @return
	 */
	public FormFile getUploadFile() {
		return uploadFile;
	}

	/**
	 * @return
	 */
	public String getYoshikiShubetu() {
		return yoshikiShubetu;
	}

	/**
	 * @param list
	 */
	public void setKeitouList(List list) {
		keitouList = list;
	}

	/**
	 * @param info
	 */
	public void setShinseiDataInfo(ShinseiDataInfo info) {
		shinseiDataInfo = info;
	}

	/**
	 * @param list
	 */
	public void setShokushuList(List list) {
		shokushuList = list;
	}

	/**
	 * @param list
	 */
	public void setSuisenList(List list) {
		suisenList = list;
	}

	/**
	 * @param b
	 */
	public void setUploadActionEnd(boolean b) {
		uploadActionEnd = b;
	}

	/**
	 * @param file
	 */
	public void setUploadFile(FormFile file) {
		uploadFile = file;
	}

	/**
	 * @param string
	 */
	public void setYoshikiShubetu(String string) {
		yoshikiShubetu = string;
	}

	/**
	 * @return
	 */
	public List getBuntankinList() {
		return buntankinList;
	}

	/**
	 * @return
	 */
	public List getShinkiKeibetuList() {
		return shinkiKeibetuList;
	}

	/**
	 * @return
	 */
	public List getZennendoList() {
		return zennendoList;
	}

	/**
	 * @param list
	 */
	public void setBuntankinList(List list) {
		buntankinList = list;
	}

	/**
	 * @param list
	 */
	public void setShinkiKeibetuList(List list) {
		shinkiKeibetuList = list;
	}

	/**
	 * @param list
	 */
	public void setZennendoList(List list) {
		zennendoList = list;
	}

	/**
	 * @return
	 */
	public int getListIndex() {
		return listIndex;
	}

	/**
	 * @param i
	 */
	public void setListIndex(int i) {
		listIndex = i;
	}

	public List getKaijiKiboList() {
		return kaijiKiboList;
	}

	public void setKaijiKiboList(List kaijiKiboList) {
		this.kaijiKiboList = kaijiKiboList;
	}

	/**
	 * @return Returns the addBuntanFlg.
	 */
	public String getAddBuntanFlg() {
		return addBuntanFlg;
	}

	/**
	 * @param addBuntanFlg
	 *            The addBuntanFlg to set.
	 */
	public void setAddBuntanFlg(String addBuntanFlg) {
		this.addBuntanFlg = addBuntanFlg;
	}

	/**
	 * @return kenkyuTaishoList を戻します。
	 */
	public List getKenkyuTaishoList() {
		return kenkyuTaishoList;
	}

	/**
	 * @param kenkyuTaishoList
	 *            kenkyuTaishoList を設定。
	 */
	public void setKenkyuTaishoList(List kenkyuTaishoList) {
		this.kenkyuTaishoList = kenkyuTaishoList;
	}

	/**
	 * @return shinsaKiboBunyaList を戻します。
	 */
	public List getShinsaKiboBunyaList() {
		return shinsaKiboBunyaList;
	}

	/**
	 * @param shinsaKiboBunyaList
	 *            shinsaKiboBunyaList を設定。
	 */
	public void setShinsaKiboBunyaList(List shinsaKiboBunyaList) {
		this.shinsaKiboBunyaList = shinsaKiboBunyaList;
	}

	// 20050526 Start
	public List getKenkyuKubunList() {
		return kenkyuKubunList;
	}

	public void setKenkyuKubunList(List List) {
		kenkyuKubunList = List;
	}

	// Horikoshi End

	/**
	 * @return Returns the ikukyuEndDateDay.
	 */
	public String getIkukyuEndDateDay() {
		return ikukyuEndDateDay;
	}

	/**
	 * @param ikukyuEndDateDay
	 *            The ikukyuEndDateDay to set.
	 */
	public void setIkukyuEndDateDay(String ikukyuEndDateDay) {
		this.ikukyuEndDateDay = ikukyuEndDateDay;
	}

	/**
	 * @return Returns the ikukyuEndDateMonth.
	 */
	public String getIkukyuEndDateMonth() {
		return ikukyuEndDateMonth;
	}

	/**
	 * @param ikukyuEndDateMonth
	 *            The ikukyuEndDateMonth to set.
	 */
	public void setIkukyuEndDateMonth(String ikukyuEndDateMonth) {
		this.ikukyuEndDateMonth = ikukyuEndDateMonth;
	}

	/**
	 * @return Returns the ikukyuEndDateYear.
	 */
	public String getIkukyuEndDateYear() {
		return ikukyuEndDateYear;
	}

	/**
	 * @param ikukyuEndDateYear
	 *            The ikukyuEndDateYear to set.
	 */
	public void setIkukyuEndDateYear(String ikukyuEndDateYear) {
		this.ikukyuEndDateYear = ikukyuEndDateYear;
	}

	/**
	 * @return Returns the ikukyuStartDateDay.
	 */
	public String getIkukyuStartDateDay() {
		return ikukyuStartDateDay;
	}

	/**
	 * @param ikukyuStartDateDay
	 *            The ikukyuStartDateDay to set.
	 */
	public void setIkukyuStartDateDay(String ikukyuStartDateDay) {
		this.ikukyuStartDateDay = ikukyuStartDateDay;
	}

	/**
	 * @return Returns the ikukyuStartDateMonth.
	 */
	public String getIkukyuStartDateMonth() {
		return ikukyuStartDateMonth;
	}

	/**
	 * @param ikukyuStartDateMonth
	 *            The ikukyuStartDateMonth to set.
	 */
	public void setIkukyuStartDateMonth(String ikukyuStartDateMonth) {
		this.ikukyuStartDateMonth = ikukyuStartDateMonth;
	}

	/**
	 * @return Returns the ikukyuStartDateYear.
	 */
	public String getIkukyuStartDateYear() {
		return ikukyuStartDateYear;
	}

	/**
	 * @param ikukyuStartDateYear
	 *            The ikukyuStartDateYear to set.
	 */
	public void setIkukyuStartDateYear(String ikukyuStartDateYear) {
		this.ikukyuStartDateYear = ikukyuStartDateYear;
	}

	/**
	 * @return Returns the sikakuDateDay.
	 */
	public String getSikakuDateDay() {
		return sikakuDateDay;
	}

	/**
	 * @param sikakuDateDay
	 *            The sikakuDateDay to set.
	 */
	public void setSikakuDateDay(String sikakuDateDay) {
		this.sikakuDateDay = sikakuDateDay;
	}

	/**
	 * @return Returns the sikakuDateMonth.
	 */
	public String getSikakuDateMonth() {
		return sikakuDateMonth;
	}

	/**
	 * @param sikakuDateMonth
	 *            The sikakuDateMonth to set.
	 */
	public void setSikakuDateMonth(String sikakuDateMonth) {
		this.sikakuDateMonth = sikakuDateMonth;
	}

	/**
	 * @return Returns the sikakuDateYear.
	 */
	public String getSikakuDateYear() {
		return sikakuDateYear;
	}

	/**
	 * @param sikakuDateYear The sikakuDateYear to set.
	 */
	public void setSikakuDateYear(String sikakuDateYear) {
		this.sikakuDateYear = sikakuDateYear;
	}

	/**
	 * @return Returns the syutokumaeKikan.
	 */
	public String getSyutokumaeKikan() {
		return syutokumaeKikan;
	}

	/**
	 * @param syutokumaeKikan The syutokumaeKikan to set.
	 */
	public void setSyutokumaeKikan(String syutokumaeKikan) {
		this.syutokumaeKikan = syutokumaeKikan;
	}

	/**
	 * @return Returns the ikukyuStartDateList.
	 */
	public List getIkukyuStartDateList() {
		return ikukyuStartDateList;
	}

	/**
	 * @param ikukyuStartDateList The ikukyuStartDateList to set.
	 */
	public void setIkukyuStartDateList(List ikukyuStartDateList) {
		this.ikukyuStartDateList = ikukyuStartDateList;
	}

	/**
	 * @return Returns the saiyoDateDay.
	 */
	public String getSaiyoDateDay() {
		return saiyoDateDay;
	}

	/**
	 * @param saiyoDateDay The saiyoDateDay to set.
	 */
	public void setSaiyoDateDay(String saiyoDateDay) {
		this.saiyoDateDay = saiyoDateDay;
	}

	/**
	 * @return Returns the saiyoDateMonth.
	 */
	public String getSaiyoDateMonth() {
		return saiyoDateMonth;
	}

	/**
	 * @param saiyoDateMonth The saiyoDateMonth to set.
	 */
	public void setSaiyoDateMonth(String saiyoDateMonth) {
		this.saiyoDateMonth = saiyoDateMonth;
	}

	/**
	 * @return Returns the saiyoDateYear.
	 */
	public String getSaiyoDateYear() {
		return saiyoDateYear;
	}

	/**
	 * @param saiyoDateYear The saiyoDateYear to set.
	 */
	public void setSaiyoDateYear(String saiyoDateYear) {
		this.saiyoDateYear = saiyoDateYear;
	}

	/**
	 * @return Returns the shinsaKiboRyoikiList.
	 */
	public List getShinsaKiboRyoikiList() {
		return shinsaKiboRyoikiList;
	}

	/**
	 * @param saiyoDateYear The shinsaKiboRyoikiList to set.
	 */
	public void setShinsaKiboRyoikiList(List shinsaKiboRyoikiList) {
		this.shinsaKiboRyoikiList = shinsaKiboRyoikiList;
	}

	/**
	 * @return Returns the kariryoikiNo.
	 */
	public String getKariryoikiNo() {
		return kariryoikiNo;
	}

	/**
	 * @param kariryoikiNo The kariryoikiNo to set.
	 */
	public void setKariryoikiNo(String kariryoikiNo) {
		this.kariryoikiNo = kariryoikiNo;
	}
	
//	ADD　START 2007-07-19 BIS 劉多良
	/**
	 * 
	 * @param errors
	 */
	private void addKumiaiError(ActionErrors errors){
		Iterator errorIterator = null;
		ActionError error = null;
		//新規・継続区分と研究課題番号の組み合わせが
		errorIterator = errors.get("shinseiDataInfo.kadaiNoKeizoku");
		while(errorIterator.hasNext()){
			error = (ActionError)(errorIterator.next());
			if(error.getKey().equalsIgnoreCase("errors.5019")){
				errors.add("shinseiDataInfo.kadaiNoKeizoku.kumiai",new ActionError("errors.5019"));
			}
		}
		//前年度の応募の組み合わせが
		errorIterator = errors.get("shinseiDataInfo.kadaiNoSaisyu");
		while(errorIterator.hasNext()){
			error = (ActionError)(errorIterator.next());
			if(error.getKey().equalsIgnoreCase("errors.5020")){
				errors.add("shinseiDataInfo.kadaiNoSaisyu.kumiai",new ActionError("errors.5020"));
			}else if(error.getKey().equalsIgnoreCase("errors.5047")){
				errors.add("shinseiDataInfo.kadaiNoSaisyu.kumiai",new ActionError("errors.5047"));
			}
		}
		//新規・継続区分が継続の場合には研究計画最終年度前年度の応募に「該当する」を選択できません。
		errorIterator = errors.get("shinseiDataInfo.shinseiFlgNo");
		while(errorIterator.hasNext()){
			error = (ActionError)(errorIterator.next());
			String errorKey=error.getKey();
			if(errorKey.equalsIgnoreCase("errors.5056")){
				errors.add("shinseiDataInfo.shinseiFlgNo.kumiai",new ActionError("errors.5056"));
			}else if(errorKey.equalsIgnoreCase("errors.5054")){
				errors.add("shinseiDataInfo.shinseiFlgNo.kumiai",new ActionError("errors.5054"));
			}
		}
	}
//	ADD　END 2007-07-19 BIS 劉多良
	
}