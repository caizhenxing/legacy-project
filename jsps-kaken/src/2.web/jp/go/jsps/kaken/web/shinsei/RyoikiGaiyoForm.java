/*======================================================================
 *    SYSTEM      : 日本学術振興会電子申請システム（科学研究費補助金）
 *    Source name : RyoikiGaiyoForm.java
 *    Description : 領域計画書作成・領域計画書提出確認用フォーム
 *
 *    Author      : 苗苗
 *    Date        : 2006/06/19
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2006/06/19    v1.0        苗苗                        新規作成
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.shinsei;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import jp.go.jsps.kaken.model.common.ApplicationSettings;
import jp.go.jsps.kaken.model.common.ISettingKeys;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.ErrorInfo;
import jp.go.jsps.kaken.model.vo.JigyoKanriInfo;
import jp.go.jsps.kaken.model.vo.RyoikiKeikakushoInfo;
import jp.go.jsps.kaken.model.vo.ShinseiDataInfo;
import jp.go.jsps.kaken.util.StringUtil;
import jp.go.jsps.kaken.web.struts.BaseValidatorForm;

/**
 * 領域計画書作成・領域計画書提出確認用フォーム
 */
public class RyoikiGaiyoForm extends BaseValidatorForm{
    
    // ---------------------------------------------------------------------
    // Static data
    // ---------------------------------------------------------------------

    /** ログ */
    protected static Log log = LogFactory.getLog(RyoikiGaiyoForm.class);

    /** 添付ファイル送信処理・申請登録処理同期化試行回数（1秒間隔） */
    private static int    TRY_COUNT_SYNCHRONIZE  = ApplicationSettings.getInt(ISettingKeys.TRY_COUNT_SYNCHRONIZE);
    
    // ---------------------------------------------------------------------
    // Instance data
    // ---------------------------------------------------------------------
//  <!-- ADD　START 2007/07/19 BIS 張楠 -->
    /** エラーメッセジー */
    private List errorsList =  new ArrayList(); ;
//  <!-- ADD　END　 2007/07/19 BIS 張楠 -->    
    
    /** 遷移画面フラグ */
    private String screenFlg;

    /** システム受付番号(領域) */
    private String ryoikiSystemNo;

    /** システム受付番号 */
    private String systemNo;

    /** 仮領域番号 */
    private String kariryoikiNo;
    
    /** 事業ID */
    private String jigyoId;

    /** 領域計画書（概要）情報 */
    private RyoikiKeikakushoInfo ryoikikeikakushoInfo = new RyoikiKeikakushoInfo();
    
    /** 申請データ情報 */
    private ShinseiDataInfo shinseiDataInfo = new ShinseiDataInfo();
    
    //2006/06/20 lwj add begin
    /** 事業管理データ情報 */
    private JigyoKanriInfo jigyoKanriInfo = new JigyoKanriInfo();
    //2006/06/20 lwj add end
    
    /** 審査希望部門リスト */
    private List kiboubumonList = new ArrayList(); 
    
    /** 事前調査リスト */
    private List jizenchousaList = new ArrayList();
    
    /** 研究の必要性リスト */
    private List kenkyuHitsuyouseiList = new ArrayList();
    
    /** 研究の必要性の値のリスト */
    private List values = new ArrayList();

// 2006/08/10 dyh delete start
//    /** 研究の必要性のチェックのリスト */
//    private List checkedList = new ArrayList();
// 2006/08/10 dyh delete end

    /** 15分類リスト */
    private List kanrenbunyaBunruiList = new ArrayList();
    
    /** 研究領域最終年度前年度の応募有無リスト */
    private List zenNendoOuboFlgList = new ArrayList();
    
    /** 添付ファールリスト */
    private List tenpuFileList = new ArrayList();
  
    /** 職名リスト */
    private List shokushuList = new ArrayList();
    
    /** アップロードファイルアクションとの同期化フラグ */
    private boolean uploadActionEnd = false;
    
    /** アップロードファイル */
    private FormFile uploadFile = null;

//  2006/06/24 劉佳　追加ここから
    /** 研究組織経費小計値 */
    private List keihi = new ArrayList();
    
    /** 研究組織経費小計値 */
    private List keihiTotal = new ArrayList();
    
    /** 公募研究経費小計値 */
    private List kenkyuSyokeiTotal = new ArrayList();
//  2006/06/24 劉佳　追加ここまで    
    
    // ---------------------------------------------------------------------
    // Constructors
    // ---------------------------------------------------------------------

    /**
     * コンストラクタ。
     */
    public RyoikiGaiyoForm() {
        super();
        init();
    }
    
    // ---------------------------------------------------------------------
    // Private methods
    // ---------------------------------------------------------------------
    /**
     * 初期化処理
     */
    public void init() {
        values = new ArrayList();
// 2006/08/10 dyh delete start
//        for(int i = 0; i < 5 ; i++){
//            checkedList.add(i,"00");
//        }
// 2006/08/10 dyh delete end
    }

    /* 
     * 初期化処理。
     * (非 Javadoc)
     * @see org.apache.struts.action.ActionForm#reset(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
     */
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
        //・・・
    }

    /*
     * 入力チェック。 (非 Javadoc)
     * 
     * @see org.apache.struts.action.ActionForm#validate(org.apache.struts.action.ActionMapping,javax.servlet.http.HttpServletRequest)
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

         // 定型処理-----
        ActionErrors errors = super.validate(mapping, request);

//ADD　START 2007-07-25 BIS 劉多良
        //「領域計画書入力」の「研究経費表」画面でエラーになった項目のエラーメッセージを追加する
        {
	        HashMap errorMap = new HashMap();
        	String property = null;
	        for(int i=2;i<7;i++){
	        	property = "ryoikikeikakushoInfo.kenkyuSyokei" + i;
	        	if(errors.get(property).hasNext()){
	        		errorMap.put(property,"研究経費小計 " + i + "年目 違います。 ");
	        	}
	        	property = "ryoikikeikakushoInfo.kenkyuUtiwake" + i;
	        	if(errors.get(property).hasNext()){
	        		errorMap.put(property,"研究経費内訳 " + i + "年目 違います。 ");
	        	}
	        }
	        ryoikikeikakushoInfo.setErrorsMap( errorMap);
        }
//ADD　END 2007-07-25 BIS 劉多良
        // ---------------------------------------------
        // 基本的なチェック(必須、形式等）はValidatorを使用する。
        
//      <!-- ADD　START 2007/07/26 BIS 張楠 -->
        //応募領域名全角文字のチェック
        String ryoikeName = ryoikikeikakushoInfo.getRyoikiName();
        if(!StringUtil.isBlank(ryoikeName)){
            ryoikeName = ryoikeName.replaceAll("[\r\n]", "");  //改行は削除する
            if(!StringUtil.isZenkaku(ryoikeName)){
                ActionError error = new ActionError("errors.mask_zenkaku","応募領域名");
                errors.add("ryoikikeikakushoInfo.ryoikiName", error);
            }
        }
//      <!-- ADD　END　 2007/07/26 BIS 張楠 -->

        
        //応募領域名（英訳名）のチェック
        String ryoikeNameEigo = ryoikikeikakushoInfo.getRyoikiNameEigo();
        if(!StringUtil.isBlank(ryoikeNameEigo)){
            ryoikeNameEigo = ryoikeNameEigo.replaceAll("[\r\n]", "");  //改行は削除する
            if(ryoikeNameEigo.length() > 200){
                ActionError error = new ActionError("errors.maxlength","応募領域名（英訳名）","200");
                errors.add("ryoikikeikakushoInfo.ryoikiNameEigo", error);
            }
        }
        
        //応募領域の研究概要のチェック
        String kenkyuGaiyou = ryoikikeikakushoInfo.getKenkyuGaiyou();
        if(!StringUtil.isBlank(kenkyuGaiyou)){
            kenkyuGaiyou = kenkyuGaiyou.replaceAll("[\r\n]", "");  //改行は削除する
            if(kenkyuGaiyou.length() > 400){
                ActionError error = new ActionError("errors.maxlength","応募領域の研究概要","400");
                errors.add("ryoikikeikakushoInfo.kenkyuGaiyou", error);
            }
        }

// 2006/08/07 dyh add start 理由：仕様変更
        //準備研究・事前調査の状況のチェック
        String jizenchousaFlg = ryoikikeikakushoInfo.getJizenchousaFlg();
        if(!StringUtil.isBlank(jizenchousaFlg) && !"3".equals(jizenchousaFlg)){
            if(!StringUtil.isBlank(ryoikikeikakushoInfo.getJizenchousaSonota())){
                errors.add("ryoikikeikakushoInfo.jizenchousaFlg",
                        new ActionError("errors.5068", new String[] {
                                "準備研究・事前調査の状況",
                                "「その他」以外",
                                "準備研究・事前調査の状況（その他）"}));
            }
        }
// 2006/08/07 dyh add end

        //過去の特定領域の応募状況のチェック
        String kakoOubojyoukyou = ryoikikeikakushoInfo.getKakoOubojyoukyou();
        if(!StringUtil.isBlank(kakoOubojyoukyou)){
            kakoOubojyoukyou = kakoOubojyoukyou.replaceAll("[\r\n]", "");  //改行は削除する
            if(kakoOubojyoukyou.length() > 100){
                ActionError error = new ActionError("errors.maxlength","過去の特定領域の応募状況","100");
                errors.add("ryoikikeikakushoInfo.kakoOubojyoukyou", error);
            }
        }

// 2006/08/07 dyh add start 理由：仕様変更
        //研究領域最終年度前年度の応募のチェック(研究領域最終年度前年度の応募が2:無の場合、空欄でなければエラー)
        if("2".equals(ryoikikeikakushoInfo.getZennendoOuboFlg())){
            if(!StringUtil.isBlank(ryoikikeikakushoInfo.getZennendoOuboNo())){
                errors.add("ryoikikeikakushoInfo.zennendoOuboNo",
                        new ActionError("errors.5068", new String[] {
                                "該当の有無",
                                "「無」",
                                "最終年度前年度にあたる領域番号"}));
            }
        }
// 2006/08/07 dyh add end

// 2006/08/10 dyh update start
        //研究の必要性のチェック
        if(page == 2 && (values == null || values.size() == 0)){
            ActionError error = new ActionError("errors.2002", "研究の必要性");
            errors.add("ryoikikeikakushoInfo.kenkyuHitsuyousei", error);
        }
//        List checkedList = this.getCheckedList();
//        boolean isChecked = false;
//        for(int i = 0 ; i< checkedList.size(); i++){
//            String checked = StringUtils.right((String)checkedList.get(i),1);
//            if(checked.equals("1")){
//                isChecked = true;
//            }
//        } 
//        
//        if (!isChecked && page == 2) {
//            valuesList = new ArrayList();
//            for (int i = 0; i < checkedList.size(); i++) {
//                if (StringUtils.right((String)checkedList.get(i),1).equals("1")) {
//                    valuesList.add(String.valueOf(StringUtils.left((String)checkedList.get(i),1)));
//                }
//            }
//            ActionError error = new ActionError("errors.2002", "研究の必要性");
//            errors.add("ryoikikeikakushoInfo.kenkyuHitsuyousei", error);
//        } else {
//            valuesList = new ArrayList();
//            for (int i = 0; i < checkedList.size(); i++) {
//                if (StringUtils.right((String)checkedList.get(i),1).equals("1")) {
//                    valuesList.add(String.valueOf(StringUtils.left((String)checkedList.get(i),1)));
//                }
//            }
//        }
// 2006/08/10 dyh update end

        //添付ファイル有無のチェック
        if("0".equals(ryoikikeikakushoInfo.getTenpuFileFlg())){
            if(uploadFile != null &&  uploadFile.getFileSize() != 0){
                ActionError error = new ActionError("errors.5063");
                errors.add("uploadFile", error); 
            }
        }
 
//		ADD　START 2007-07-19 BIS 劉多良
		//組み合わせのエラーを追加する
		addKumiaiError(errors);
//		ADD　END 2007-07-19 BIS 劉多良
        
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
    
    /**
     * アップロードファイルアクションとの同期化を行う。 アップロードフラグがtrueになるまで処理を中断する。
     * フラグがfalseの場合は1秒後に再度確認を行う。 TRY_COUNT_SYNCHRONIZEで設定されている回数分試行しても
     * 同じ場合はタイムアウトとみなす。
     * 
     * @return boolean タイムアウトが発生した場合false
     */
    private boolean synchronizedUploadFileAction() {

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
    
    /**
     * 添付ファイル情報出力。
     * @throws ApplicationException
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

    /**
     * 領域計画書（概要）情報を取得
     * @return RyoikiKeikakushoInfo 領域計画書（概要）情報を返す
     */
    public RyoikiKeikakushoInfo getRyoikikeikakushoInfo() {
        return ryoikikeikakushoInfo;
    }

    /**
     * 領域計画書（概要）情報を設定
     * @param ryoikikeikakushoInfo 領域計画書（概要）情報をセットする
     */
    public void setRyoikikeikakushoInfo(RyoikiKeikakushoInfo ryoikikeikakushoInfo) {
        this.ryoikikeikakushoInfo = ryoikikeikakushoInfo;
    }

    /**
     * 申請データ情報を取得
     * @return ShinseiDataInfo 申請データ情報を返す
     */
    public ShinseiDataInfo getShinseiDataInfo() {
        return shinseiDataInfo;
    }

    /**
     * 申請データ情報を設定
     * @param shinseiDataInfo 申請データ情報をセットする
     */
    public void setShinseiDataInfo(ShinseiDataInfo shinseiDataInfo) {
        this.shinseiDataInfo = shinseiDataInfo;
    }

    /**
     * 同期化フラグを取得
     * @return boolean アップロードファイルアクションとの同期化フラグを返す
     */
    public boolean isUploadActionEnd() {
        return uploadActionEnd;
    }

    /**
     * 同期化フラグを設定
     * @param uploadActionEnd アップロードファイルアクションとの同期化フラグをセットする
     */
    public void setUploadActionEnd(boolean uploadActionEnd) {
        this.uploadActionEnd = uploadActionEnd;
    }

    /**
     * アップロードファイルを取得
     * @return FormFile アップロードファイルを返す
     */
    public FormFile getUploadFile() {
        return uploadFile;
    }

    /**
     * アップロードファイルを設定
     * @param uploadFile アップロードファイルをセットする
     */
    public void setUploadFile(FormFile uploadFile) {
        this.uploadFile = uploadFile;
    }

    /**
     * 事業管理データ情報を取得
     * @return JigyoKanriInfo 事業管理データ情報
     */
    public JigyoKanriInfo getJigyoKanriInfo() {
        return jigyoKanriInfo;
    }

    /**
     * 事業管理データ情報を設定
     * @param jigyoKanriInfo 事業管理データ情報
     */
    public void setJigyoKanriInfo(JigyoKanriInfo jigyoKanriInfo) {
        this.jigyoKanriInfo = jigyoKanriInfo;
    }

    /**
     * 仮領域番号を取得
     * @return String 仮領域番号
     */
    public String getKariryoikiNo() {
        return kariryoikiNo;
    }

    /**
     * 仮領域番号を設定
     * @param kariryoikiNo 仮領域番号
     */
    public void setKariryoikiNo(String kariryoikiNo) {
        this.kariryoikiNo = kariryoikiNo;
    }

    /**
     * 事前調査リストを取得
     * @return List 事前調査リスト
     */
    public List getJizenchousaList() {
        return jizenchousaList;
    }

    /**
     * 事前調査リストを設定
     * @param jizenchousaList 事前調査リスト
     */
    public void setJizenchousaList(List jizenchousaList) {
        this.jizenchousaList = jizenchousaList;
    }

    /**
     * 15分類リストを取得
     * @return List 15分類リスト
     */
    public List getKanrenbunyaBunruiList() {
        return kanrenbunyaBunruiList;
    }

    /**
     * 15分類リストを設定
     * @param kanrenbunyaBunruiList 15分類リスト
     */
    public void setKanrenbunyaBunruiList(List kanrenbunyaBunruiList) {
        this.kanrenbunyaBunruiList = kanrenbunyaBunruiList;
    }

    /**
     * 研究の必要性リストを取得
     * @return List 研究の必要性リスト
     */
    public List getKenkyuHitsuyouseiList() {
        return kenkyuHitsuyouseiList;
    }

    /**
     * 研究の必要性リストを設定
     * @param kenkyuHitsuyouseiList 研究の必要性リスト
     */
    public void setKenkyuHitsuyouseiList(List kenkyuHitsuyouseiList) {
        this.kenkyuHitsuyouseiList = kenkyuHitsuyouseiList;
    }

    /**
     * 審査希望部門リストを取得
     * @return List 審査希望部門リスト
     */
    public List getKiboubumonList() {
        return kiboubumonList;
    }

    /**
     * 審査希望部門リストを設定
     * @param kiboubumonList 審査希望部門リスト
     */
    public void setKiboubumonList(List kiboubumonList) {
        this.kiboubumonList = kiboubumonList;
    }

    /**
     * 研究領域最終年度前年度の応募有無リストを取得
     * @return List 研究領域最終年度前年度の応募有無リスト
     */
    public List getZenNendoOuboFlgList() {
        return zenNendoOuboFlgList;
    }

    /**
     * 研究領域最終年度前年度の応募有無リストを設定
     * @param zenNendoOuboFlgList 研究領域最終年度前年度の応募有無リスト
     */
    public void setZenNendoOuboFlgList(List zenNendoOuboFlgList) {
        this.zenNendoOuboFlgList = zenNendoOuboFlgList;
    }

    /**
     * 研究の必要性の値のリストを取得
     * @return List 研究の必要性の値のリスト
     */
    public List getValuesList() {
        return values;
    }

    /**
     * 研究の必要性の値のリストを設定
     * @param values 研究の必要性の値のリスト
     */
    public void setValuesList(List values) {
        this.values = values;
    }

    /**
     * 研究の必要性の値のリストを取得
     * @param key
     * @return Object 研究の必要性の値のリスト
     */
    public Object getValues(int key) {
        return values.get(key);
    }

    /**
     * 研究の必要性の値のリストを設定
     * @param key
     * @param value
     */
    public void setValues(int key, Object value) {
        if(!values.contains(value)){
            values.add(value);
        }
    }
    
    /**
     * 職名リストを取得
     * @return List 職名リスト
     */
    public List getShokushuList() {
        return shokushuList;
    }

    /**
     * 職名リストを設定
     * @param shokushuList 職名リスト
     */
    public void setShokushuList(List shokushuList) {
        this.shokushuList = shokushuList;
    }

    /**
     * 添付ファールリストを取得
     * @return List 添付ファールリスト
     */
    public List getTenpuFileList() {
        return tenpuFileList;
    }

    /**
     * 添付ファールリストを設定
     * @param tenpuFileList 添付ファールリスト
     */
    public void setTenpuFileList(List tenpuFileList) {
        this.tenpuFileList = tenpuFileList;
    }

    /**
     * システム受付番号を取得
     * @return String システム受付番号
     */
    public String getSystemNo() {
        return systemNo;
    }

    /**
     * システム受付番号を設定
     * @param systemNo システム受付番号
     */
    public void setSystemNo(String systemNo) {
        this.systemNo = systemNo;
    }
    
    /**
     * システム受付番号(領域)を取得
     * @return String システム受付番号(領域)
     */
    public String getRyoikiSystemNo() {
        return ryoikiSystemNo;
    }

    /**
     * システム受付番号(領域)を設定
     * @param ryoikiSystemNo システム受付番号(領域)
     */
    public void setRyoikiSystemNo(String ryoikiSystemNo) {
        this.ryoikiSystemNo = ryoikiSystemNo;
    }

    /**
     * 研究組織経費小計値を取得
     * @return List 研究組織経費小計値
     */
    public List getKeihiTotal() {
        return keihiTotal;
    }

    /**
     * 研究組織経費小計値を設定
     * @param keihiTotal 研究組織経費小計値
     */
    public void setKeihiTotal(List keihiTotal) {
        this.keihiTotal = keihiTotal;
    }
    
    /**
     * 公募研究経費小計値を取得
     * @return List 公募研究経費小計値
     */
    public List getKenkyuSyokeiTotal() {
        return kenkyuSyokeiTotal;
    }

    /**
     * 公募研究経費小計値を設定
     * @param kenkyuSyokeiTotal 公募研究経費小計値
     */
    public void setKenkyuSyokeiTotal(List kenkyuSyokeiTotal) {
        this.kenkyuSyokeiTotal = kenkyuSyokeiTotal;
    }

    /**
     * 研究組織経費小計値を取得
     * @return List 研究組織経費小計値
     */
    public List getKeihi() {
        return keihi;
    }

    /**
     * 研究組織経費小計値を設定
     * @param keihi 研究組織経費小計値
     */
    public void setKeihi(List keihi) {
        this.keihi = keihi;
    }
   
    /**
     * 事業IDを取得
     * @return String 事業ID
     */
    public String getJigyoId() {
        return jigyoId;
    }

    /**
     * 事業IDを設定
     * @param jigyoId 事業ID
     */
    public void setJigyoId(String jigyoId) {
        this.jigyoId = jigyoId;
    }

    /**
     * 遷移画面フラグを取得
     * @return String 遷移画面フラグ
     */
    public String getScreenFlg() {
        return screenFlg;
    }

    /**
     * 遷移画面フラグを設定
     * @param screenFlg 遷移画面フラグ
     */
    public void setScreenFlg(String screenFlg) {
        this.screenFlg = screenFlg;
    }

//  <!-- ADD　START 2007/07/19 BIS 張楠 -->
	/**
	 * @return Returns the errorsList.
	 */
	public List getErrorsList() {
		return errorsList;
	}

	/**
	 * @param errorsList The errorsList to set.
	 */
	public void setErrorsList(List errorsList) {
		this.errorsList = errorsList;
	}
//  <!-- ADD　END 2007/07/19 BIS 張楠 -->
// 2006/08/10 dyh delete start
//    /**
//     * 研究の必要性のチェックのリストを取得
//     * @return checkedList 研究の必要性のチェックのリスト
//     */
//    public List getCheckedList() {
//        return checkedList;
//    }
//
//    /**
//     * 研究の必要性のチェックのリストを設定
//     * @param checkedList 研究の必要性のチェックのリスト
//     */
//    public void setCheckedList(List checkedList) {
//        this.checkedList = checkedList;
//    }
//
//    /**
//     * 研究の必要性のチェックのリストを取得
//     * @param key
//     * @return Object 研究の必要性の値のリスト
//     */
//    public Object getCheckedList(int key) {
//        return checkedList.get(key);
//    }
//
//    /**
//     * 研究の必要性のチェックのリストを設定
//     * @param key
//     * @param value
//     */
//    public void setCheckedList(int key, Object value) {
//        checkedList.set(key, value); 
//    }
// 2006/08/10 dyh delete end
	
//	ADD　START 2007-07-19 BIS 劉多良
	/**
	 * 
	 * @param errors
	 */
	private void addKumiaiError(ActionErrors errors){
		Iterator errorIterator = null;
		ActionError error = null;
		
		//準備研究・事前調査の状況（その他）は必須項目です
		errorIterator = errors.get("ryoikikeikakushoInfo.jizenchousaSonota");
		while(errorIterator.hasNext()){
			error = (ActionError)(errorIterator.next());
			if(error.getKey().equalsIgnoreCase("errors.required")){
				errors.add("ryoikikeikakushoInfo.jizenchousaSonota.kumiai",new ActionError("errors.required"));
			}
		}
		//準備研究・事前調査の状況で「その他」以外を選択した場合に準備研究・事前調査の状況（その他）は入力できません。 
		errorIterator = errors.get("ryoikikeikakushoInfo.jizenchousaFlg");
		while(errorIterator.hasNext()){
			error = (ActionError)(errorIterator.next());
			if(error.getKey().equalsIgnoreCase("errors.5068")){
				errors.add("ryoikikeikakushoInfo.jizenchousaSonota.kumiai",new ActionError("errors.5068"));
			}
		}
		
		//最終年度前年度にあたる領域番号は必須項目です
		errorIterator = errors.get("ryoikikeikakushoInfo.zennendoOuboNo");
		while(errorIterator.hasNext()){
			error = (ActionError)(errorIterator.next());
			if(error.getKey().equalsIgnoreCase("errors.5068")){
				errors.add("ryoikikeikakushoInfo.zennendoOuboNo.kumiai",new ActionError("errors.5068"));
			}else if(error.getKey().equalsIgnoreCase("errors.required")){
				errors.add("ryoikikeikakushoInfo.zennendoOuboNo.kumiai",new ActionError("errors.required"));
			}
		}
		//事務担当者職名（和文）は必須項目です
		errorIterator = errors.get("ryoikikeikakushoInfo.jimutantoShokushuNameKanji");
		while(errorIterator.hasNext()){
			error = (ActionError)(errorIterator.next());
			if(error.getKey().equalsIgnoreCase("errors.required")){
				errors.add("ryoikikeikakushoInfo.jimutantoShokushuNameKanji.kumiai",new ActionError("errors.required"));
			}
		}
	}
//	ADD　END 2007-07-19 BIS 劉多良
	
}