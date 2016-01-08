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
package jp.go.jsps.kaken.web.shinsa.shinsaJigyo;

import java.util.*;

import javax.servlet.http.*;

import jp.go.jsps.kaken.model.common.*;
import jp.go.jsps.kaken.model.IShinsaKekkaMaintenance;
import jp.go.jsps.kaken.web.struts.*;
import jp.go.jsps.kaken.util.*;

import org.apache.struts.action.*;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.*;

/**
 * 審査結果フォーム
 * ID RCSfile="$RCSfile: ShinsaKekkaForm.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:59 $"
 */
public class ShinsaKekkaForm extends BaseValidatorForm {

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/**
     * <code>serialVersionUID</code> のコメント
     */
    private static final long serialVersionUID = -8088977827710070187L;

	/** システム番号 */
	private String systemNo;

	/** 審査員番号 */
	private String shinsainNo;

	/** 事業区分 */
	private String jigyoKubun;
		
	/** 事業ID */
	private String jigyoId;

	/** 事業コード */
	private String jigyoCd;
	
	/** 総合評価（ABC） */
	private String kekkaAbc;
	
	/** 総合評価（点数） */
	private String kekkaTen;
	
	/** 総合評価（点数） 萌芽 */
	private String kekkaTenHoga;

	/** コメント1 */
	private String comment1;

	/** コメント2 */
	private String comment2;
	
	/** コメント3 */
	private String comment3;
	
	/** コメント4 */
	private String comment4;
	
	/** コメント5 */
	private String comment5;
	
	/** コメント6 */
	private String comment6;

	/** 研究内容 */
	private String kenkyuNaiyo;

	/** 研究計画 */
	private String kenkyuKeikaku;

	/** 適切性-海外 */
	private String tekisetsuKaigai;

	/** 適切性-研究(1) */
	private String tekisetsuKenkyu1;

	/** 適切性 */
	private String tekisetsu;

	/** 妥当性 */
	private String dato;

	/** 研究代表者 */
	private String shinseisha;

	/** 研究分担者 */
	private String kenkyuBuntansha;

	/** ヒトゲノム */
	private String hitogenomu;

	/** 特定胚 */
	private String tokutei;

	/** ヒトES細胞 */
	private String hitoEs;

	/** 遺伝子組換え実験 */
	private String kumikae;

	/** 遺伝子治療臨床研究 */
	private String chiryo;

	/** 疫学研究 */
	private String ekigaku;

	/** コメント */
	private String comments;
	
	/** 利害関係 */
	private String rigai;
	
	/** 若手（S）としての妥当性	 */
	private String wakates;

	/** 学術的重要性・妥当性 */
	private String juyosei;

	/** 独創性・革新性 */
	private String dokusosei;

	/** 波及効果・普遍性 */
	private String hakyukoka;

	/** 遂行能力・環境の適切性 */
	private String suikonoryoku;

	/** 人権の保護・法令等の遵守 */
	private String jinken;
	
	/** 分担金 */
	private String buntankin;

	/** その他コメント */
	private String otherComment;
	
	/** 添付ファイル */
	private String tenpuPath;

	/** 添付ファイルフラグ */
	private String tenpuFlg;
	
	/** 添付ファイル(アップロードファイル) */
	private FormFile tenpuUploadFile;
	
	/** 評価用ファイルフラグ */	
	private String hyokaFileFlg;
	
	/** 総合評価（ABC）選択リスト */
	private List kekkaAbcList = new ArrayList();

	/** 総合評価（点数）選択リスト */
	private List kekkaTenList = new ArrayList();
	
	/** 総合評価（点数）萌芽選択リスト */
	private List kekkaTenHogaList = new ArrayList();

	/** 研究内容選択リスト */
	private List kenkyuNaiyoList = new ArrayList();
	
	/** 研究計画選択リスト */
	private List kenkyuKeikakuList = new ArrayList();
	
	/** 適切性-海外選択リスト */
	private List tekisetsuKaigaiList = new ArrayList();
	
	/** 適切性-研究（1）選択リスト */
	private List tekisetsuKenkyu1List = new ArrayList();

	/** 適切性選択リスト */
	private List tekisetsuList = new ArrayList();
			
	/** 妥当性選択リスト */
	private List datoList = new ArrayList();

	/** 研究代表者選択リスト */
	private List shinseishaList = new ArrayList();
		
	/** 研究分担者選択リスト */
	private List kenkyuBuntanshaList = new ArrayList();

	/** ヒトゲノム選択リスト */
	private List hitogenomuList = new ArrayList();
		
	/** 特定胚選択リスト */
	private List tokuteiList = new ArrayList();

	/** ヒトES細胞選択リスト */
	private List hitoEsList = new ArrayList();

	/** 遺伝子組換え実験選択リスト */
	private List kumikaeList = new ArrayList();
		
	/** 遺伝子治療臨床研究選択リスト */
	private List chiryoList = new ArrayList();

	/** 疫学研究選択リスト */
	private List ekigakuList = new ArrayList();
	
	/** 利害関係リスト */
	private List rigaiList = new ArrayList();
		
	/** 学術的重要性・妥当性リスト */
	private List juyoseiList = new ArrayList();

	/** 独創性・革新性リスト */
	private List dokusoseiList = new ArrayList();
		
	/** 波及効果・普遍性リスト */
	private List hakyukokaList = new ArrayList();

	/** 遂行能力・環境の適切性リスト */
	private List suikonoryokuList = new ArrayList();

	/** 人権の保護・法令等の遵守リスト */
	private List jinkenList = new ArrayList();
		
	/** 分担金リスト */
	private List buntankinList = new ArrayList();


	//・・・・・・・・・・

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * コンストラクタ。
	 */
	public ShinsaKekkaForm() {
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
		systemNo = "";         // システム番号
        shinsainNo = "";
        jigyoId = "";
        jigyoCd = "";
        kekkaAbc = "0";        // プルダウン初期値設定
        kekkaTen = "";         // ラジオボタン初期値設定
        comment1 = "";         // コメント1
        comment2 = "";         // コメント2
        comment3 = "";         // コメント3
        comment4 = "";         // コメント4
        comment5 = "";         // コメント5
        comment6 = "";         // コメント6
        kenkyuNaiyo = "";      // 研究内容ラジオ初期値設定
        kenkyuKeikaku = "";    // 研究計画ラジオ初期値設定
        tekisetsuKaigai = "";  // 適切性-海外ラジオ初期値設定
        tekisetsuKenkyu1 = ""; // 適切性-研究(1)ラジオ初期値設定
        tekisetsu = "";        // 適切性ラジオ初期値設定
        dato = "";             // 妥当性ラジオ初期値設定
        shinseisha = "";       // 研究代表者ラジオ初期値設定
        kenkyuBuntansha = "";  // ラジオボタン初期値設定
        hitogenomu = "";       // ヒトゲノムラジオ初期値設定
        tokutei = "";          // 特定胚ラジオ初期値設定
        hitoEs = "";           // ヒトES細胞ラジオ初期値設定
        kumikae = "";          // 遺伝子組換え実験ラジオ初期値設定
        chiryo = "";           // ラジオボタン初期値設定
        ekigaku = "";          // ラジオボタン初期値設定
        comments = "";
        rigai = "";
        juyosei = "";
        dokusosei = "";
        hakyukoka = "";
        suikonoryoku = "";
        jinken = "";
        buntankin = "";
        otherComment = "";
        tenpuPath = "";
        tenpuUploadFile = null;
        hyokaFileFlg = "";	
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

//		if(getShinseisha() != null && !"".equals(getShinseisha())){
//			
//			//研究種目が「若手研究(A)」「若手研究(B)」「基盤研究(C)一般」「基盤研究(C)企画」「萌芽研究」の場合
//			if("00121".equals(getJigyoCd())
//				|| "00131".equals(getJigyoCd())
//				|| "00061".equals(getJigyoCd())
//				|| "00062".equals(getJigyoCd())
//				|| "00111".equals(getJigyoCd())){
//				
//					//関係者等が「1：研究代表者」の場合は、「総合評点」「研究内容」「研究計画」は「-」を選択する					
//					if("1".equals(getShinseisha())) {
//					
//						if(!"-".equals(getKekkaTen()) 
//							|| !"-".equals(getKenkyuNaiyo())
//							|| !"-".equals(getKenkyuKeikaku())
//						){
//							errors.add(
//								ActionErrors.GLOBAL_ERROR,
//								new ActionError("errors.5026"));	
//						}
//					}
//			
//			//上記（↑）以外の研究種目の場合
//			}else{
//				//関係者等が「1：研究代表者」「2：研究分担者」「3：関係者」の場合は、「総合評点」「研究内容」「研究計画」は「-」を選択する
//				if("1".equals(getShinseisha()) 
//					|| "2".equals(getShinseisha()) 
//					|| "3".equals(getShinseisha())){
//					
//					if(!"-".equals(getKekkaTen()) 
//						|| !"-".equals(getKenkyuNaiyo())
//						|| !"-".equals(getKenkyuKeikaku())
//					){
//						errors.add(
//							ActionErrors.GLOBAL_ERROR,
//							new ActionError("errors.5025"));	
//					}
//				}
//			}
//		}

		//update2004/12/21 制度改正対応
		//研究分担者・関係者が「1:研究分担者」又は「2:関係者」を選択した場合は、「総合評点」「研究内容」「研究計画」は「-」を選択する
		/*
		if(getKenkyuBuntansha() != null && !"".equals(getKenkyuBuntansha())){
			//「基盤研究(S)」「基盤研究(A)一般」「基盤研究(B)一般」「基盤研究(A)海外学術調査」「基盤研究(B)海外学術調査」の場合のみ対象とする。
			if("00031".equals(getJigyoCd())
				|| "00041".equals(getJigyoCd())
				|| "00051".equals(getJigyoCd())				
				|| "00043".equals(getJigyoCd())				
				|| "00053".equals(getJigyoCd()))
			{	
				if("1".equals(getKenkyuBuntansha()) || "2".equals(getKenkyuBuntansha())){
					if(!"-".equals(getKekkaTen()) 
						|| !"-".equals(getKenkyuNaiyo())
						|| !"-".equals(getKenkyuKeikaku())
					){
						errors.add(
							ActionErrors.GLOBAL_ERROR,
							new ActionError("errors.5025"));	
					}
				}
			}

			//研究分担者・関係者が「2:関係者」を選択した場合はコメント欄は必須。全共通。
			if("2".equals(getKenkyuBuntansha())){
				//コメントが未入力の場合
				if(getComments() == null || "".equals(getComments()) 
				){
					errors.add(
						ActionErrors.GLOBAL_ERROR,
						new ActionError("errors.5026"));	
				}
			}
		}
*/

		//update2004/12/21 制度改正対応
		//「総合評点」で「5」「1」のいずれかを選択した場合はコメント欄は必須
/*
		if(getKekkaTen() != null && !"".equals(getKekkaTen())){
			//「基盤研究(A)海外学術調査」「基盤研究(B)海外学術調査」以外を対象とする
			if(!"00043".equals(getJigyoCd()) && !"00053".equals(getJigyoCd())
				&& ("5".equals(getKekkaTen()) || "1".equals(getKekkaTen())))
			{
				//コメントが未入力の場合
				if(getComments() == null || "".equals(getComments()) 
				){
					errors.add(
						ActionErrors.GLOBAL_ERROR,
						new ActionError("errors.5027"));	
				}
			}
		}
*/

//		//「ヒトゲノム・遺伝子解析研究」「特定胚」「ヒトES細胞」「遺伝子組換え実験」「遺伝子治療臨床研究」「疫学研究」で
//		//「1:当該年度」「2:次年度以降」のいずれかを選択した場合はコメント欄は必須
//		if(getHitogenomu() != null && !"".equals(getHitogenomu())){
//			if("1".equals(getHitogenomu()) || "2".equals(getHitogenomu())
//				|| "1".equals(getTokutei()) || "2".equals(getTokutei())
//				|| "1".equals(getHitoEs()) || "2".equals(getHitoEs())
//				|| "1".equals(getKumikae()) || "2".equals(getKumikae())			
//				|| "1".equals(getChiryo()) || "2".equals(getChiryo())	
//				|| "1".equals(getEkigaku()) || "2".equals(getEkigaku())
//			){
//				//コメントが未入力の場合
//				if(getComments() == null || "".equals(getComments()) 
//				){
//					errors.add(
//						ActionErrors.GLOBAL_ERROR,
//						new ActionError("errors.5028"));	
//				}
//			}
//		}			
		
//2006/10/28 苗　修正ここから
//		if(IShinsaKekkaMaintenance.RIGAI_OFF.equals(getRigai()) && 
//			IShinsaKekkaMaintenance.SOGO_HYOTEN.equals(getKekkaTen())){
        if(IShinsaKekkaMaintenance.SOGO_HYOTEN.equals(getKekkaTen())){
//2006/10/28　苗　修正ここまで            
				errors.add(
				ActionErrors.GLOBAL_ERROR,
				new ActionError("errors.2002", new String[]{"総合評点"}));	
		}
		
		
		
//		//「企画調査」「若手研究」で分担金の妥当性に「×」を選択した場合エラー
		if(IJigyoCd.JIGYO_CD_KIBAN_C_KIKAKU.equals(getJigyoCd())){
				if(IShinsaKekkaMaintenance.RIGAI_OFF.equals(getRigai()) && 
					IShinsaKekkaMaintenance.TEKISETU_BUNTANKIN.equals(getBuntankin())){
					errors.add(
					ActionErrors.GLOBAL_ERROR,
					new ActionError("errors.notselect", new String[]{"基盤研究(C)企画調査"}) );
				}
		}else{
			if(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_A.equals(getJigyoCd()) || 
				IJigyoCd.JIGYO_CD_KIBAN_WAKATE_B.equals(getJigyoCd())){
				if(IShinsaKekkaMaintenance.RIGAI_OFF.equals(getRigai()) && 
					IShinsaKekkaMaintenance.TEKISETU_BUNTANKIN.equals(getBuntankin())){
						errors.add(
						ActionErrors.GLOBAL_ERROR,
						new ActionError("errors.notselect", new String[]{"若手研究"}) );
				}
			}	
		}

		//その他の評価項目で「×」を選択した場合で、「コメント」欄に入力がない場合は、エラー
		//基盤のみ実行。
		if(IJigyoCd.JIGYO_CD_KIBAN_C_KIKAKU.equals(getJigyoCd())
			|| IJigyoCd.JIGYO_CD_KIBAN_WAKATE_A.equals(getJigyoCd()) 
			|| IJigyoCd.JIGYO_CD_KIBAN_WAKATE_B.equals(getJigyoCd())
//2006/04/10 追加ここから
			|| IJigyoCd.JIGYO_CD_WAKATESTART.equals(getJigyoCd())
//苗　追加ここまで		
		
		){
//2006/10/28　苗　修正ここから			
//            if(StringUtil.isBlank(getOtherComment()) && 
//				IShinsaKekkaMaintenance.RIGAI_OFF.equals(getRigai()) &&
//				(IShinsaKekkaMaintenance.TEKISETU_KEIHI.equals(getDato()) ||
//				 IShinsaKekkaMaintenance.TEKISETU_JINKEN.equals(getJinken()))){
            if(StringUtil.isBlank(getOtherComment()) && 
                    (IShinsaKekkaMaintenance.TEKISETU_KEIHI.equals(getDato()) ||
                     IShinsaKekkaMaintenance.TEKISETU_JINKEN.equals(getJinken()))){ 
//2006/10/28　苗　修正ここまで                
					errors.add(
					ActionErrors.GLOBAL_ERROR,
					new ActionError("errors.noComments") );
			}
		}else{
//2006/10/28　苗　修正ここから            
//			if(StringUtil.isBlank(getOtherComment()) && 
//				IShinsaKekkaMaintenance.RIGAI_OFF.equals(getRigai()) &&
//				(IShinsaKekkaMaintenance.TEKISETU_KEIHI.equals(getDato()) ||
//				 IShinsaKekkaMaintenance.TEKISETU_JINKEN.equals(getJinken())||
//				 IShinsaKekkaMaintenance.TEKISETU_BUNTANKIN.equals(getBuntankin()))){
            if(StringUtil.isBlank(getOtherComment()) &&
                    (IShinsaKekkaMaintenance.TEKISETU_KEIHI.equals(getDato()) ||
                     IShinsaKekkaMaintenance.TEKISETU_JINKEN.equals(getJinken())||
                     IShinsaKekkaMaintenance.TEKISETU_BUNTANKIN.equals(getBuntankin()))){
//2006/10/28　苗　修正ここまで                
					errors.add(
					ActionErrors.GLOBAL_ERROR,
					new ActionError("errors.noComments") );		
			}
		}
	
//		//2005.11.01 kainuma
//		//その他の評価項目で「×」を選択した場合で、「コメント」欄に入力がない場合は、エラー
//		//基盤のみ実行。
//		if(getDato() != null && !"".equals(getDato()) && 
//		   getJinken() != null && !"".equals(getJinken()) && 
//		   getBuntankin() != null && !"".equals(getBuntankin())){
//		
//			if("3".equals(getDato()) || 
//				"3".equals(getJinken()) || 
//				  "3".equals(getBuntankin()))
//		    {
//			if(otherComment == null || otherComment.equals(""))
//				{
//			 errors.add(
//			 ActionErrors.GLOBAL_ERROR,
//			 new ActionError("errors.noComments") );
//			   	 
//				}
//		    }	
//		 }
		
		//基本入力チェックここまで
		if (!errors.isEmpty()) {
			//評価用ファイルフラグが1の場合は、ファイル再選択エラーを表示
			//利害関係以外 2005/11/15
			//if("1".equals(getHyokaFileFlg())){
			if("1".equals(getHyokaFileFlg()) && !"1".equals(this.rigai)){
				errors.add(
					ActionErrors.GLOBAL_ERROR,
					new ActionError("errors.2009"));
			}
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
     * システム番号を取得
     * @return システム番号
     */
    public String getSystemNo() {
        return systemNo;
    }
    
    /**
     * システム番号を設定
     * @param string システム番号
     */
    public void setSystemNo(String string) {
        systemNo = string;
    }

    /**
     * 審査員番号を取得
     * @return 審査員番号
     */
    public String getShinsainNo() {
        return shinsainNo;
    }

    /**
     * 審査員番号を設定
     * @param string 審査員番号
     */
    public void setShinsainNo(String string) {
        shinsainNo = string;
    }

    /**
     * 事業区分を取得
     * @return 事業区分
     */
    public String getJigyoKubun() {
        return jigyoKubun;
    }

    /**
     * 事業区分を設定
     * @param string 事業区分
     */
    public void setJigyoKubun(String string) {
        jigyoKubun = string;
    }

    /**
     * 事業IDを取得
     * @return 事業ID
     */
    public String getJigyoId() {
        return jigyoId;
    }

    /**
     * 事業IDを設定
     * @param string 事業ID
     */
    public void setJigyoId(String string) {
        jigyoId = string;
    }

    /**
     * 事業コードを取得
     * @return 事業コード
     */
    public String getJigyoCd() {
        return jigyoCd;
    }

    /**
     * 事業コードを設定
     * @param string 事業コード
     */
    public void setJigyoCd(String string) {
        jigyoCd = string;
    }

    /**
     * 総合評価（ABC）を取得
     * @return 総合評価（ABC）
     */
    public String getKekkaAbc() {
        return kekkaAbc;
    }

    /**
     * 総合評価（ABC）を設定
     * @param string 総合評価（ABC）
     */
    public void setKekkaAbc(String string) {
        kekkaAbc = string;
    }

    /**
     * 総合評価（点数）を取得
     * @return 総合評価（点数）
     */
    public String getKekkaTen() {
        return kekkaTen;
    }

    /**
     * 総合評価（点数）を設定
     * @param string 総合評価（点数）
     */
    public void setKekkaTen(String string) {
        kekkaTen = string;
    }

    /**
     * 総合評価（点数） 萌芽を取得
     * @return 総合評価（点数） 萌芽
     */
    public String getKekkaTenHoga() {
        return kekkaTenHoga;
    }

    /**
     * 総合評価（点数） 萌芽を設定
     * @param string 総合評価（点数） 萌芽
     */
    public void setKekkaTenHoga(String string) {
        kekkaTenHoga = string;
    }

    /**
     * コメント1を取得
     * @return コメント1
     */
    public String getComment1() {
        return comment1;
    }

    /**
     * コメント1を設定
     * @param string コメント1
     */
    public void setComment1(String string) {
        comment1 = string;
    }

    /**
     * コメント2を取得
     * @return コメント2
     */
    public String getComment2() {
        return comment2;
    }

    /**
     * コメント2を設定
     * @param string コメント2
     */
    public void setComment2(String string) {
        comment2 = string;
    }

    /**
     * コメント3を取得
     * @return コメント3
     */
    public String getComment3() {
        return comment3;
    }

    /**
     * コメント3を設定
     * @param string コメント3
     */
    public void setComment3(String string) {
        comment3 = string;
    }

    /**
     * コメント4を取得
     * @return コメント4
     */
    public String getComment4() {
        return comment4;
    }

    /**
     * コメント4を設定
     * @param string コメント4
     */
    public void setComment4(String string) {
        comment4 = string;
    }

    /**
     * コメント5を取得
     * @return コメント5
     */
    public String getComment5() {
        return comment5;
    }

    /**
     * コメント5を設定
     * @param string コメント5
     */
    public void setComment5(String string) {
        comment5 = string;
    }

    /**
     * コメント6を取得
     * @return コメント6
     */
    public String getComment6() {
        return comment6;
    }

    /**
     * コメント6を設定
     * @param string コメント6
     */
    public void setComment6(String string) {
        comment6 = string;
    }

    /**
     * 研究内容を取得
     * @return 研究内容
     */
    public String getKenkyuNaiyo() {
        return kenkyuNaiyo;
    }

    /**
     * 研究内容を設定
     * @param string 研究内容
     */
    public void setKenkyuNaiyo(String string) {
        kenkyuNaiyo = string;
    }

    /**
     * 研究計画を取得
     * @return 研究計画
     */
    public String getKenkyuKeikaku() {
        return kenkyuKeikaku;
    }

    /**
     * 研究計画を設定
     * @param string 研究計画
     */
    public void setKenkyuKeikaku(String string) {
        kenkyuKeikaku = string;
    }

    /**
     * 適切性-海外を取得
     * @return 適切性-海外
     */
    public String getTekisetsuKaigai() {
        return tekisetsuKaigai;
    }

    /**
     * 適切性-海外を設定
     * @param string 適切性-海外
     */
    public void setTekisetsuKaigai(String string) {
        tekisetsuKaigai = string;
    }

    /**
     * 適切性-研究(1)を取得
     * @return 適切性-研究(1)
     */
    public String getTekisetsuKenkyu1() {
        return tekisetsuKenkyu1;
    }

    /**
     * 適切性-研究(1)を設定
     * @param string 適切性-研究(1)
     */
    public void setTekisetsuKenkyu1(String string) {
        tekisetsuKenkyu1 = string;
    }

    /**
     * 適切性を取得
     * @return 適切性
     */
    public String getTekisetsu() {
        return tekisetsu;
    }

    /**
     * 適切性を設定
     * @param string 適切性
     */
    public void setTekisetsu(String string) {
        tekisetsu = string;
    }

    /**
     * 妥当性を取得
     * @return 妥当性
     */
    public String getDato() {
        return dato;
    }

    /**
     * 妥当性を設定
     * @param string 妥当性
     */
    public void setDato(String string) {
        dato = string;
    }

    /**
     * 研究代表者を取得
     * @return 研究代表者
     */
    public String getShinseisha() {
        return shinseisha;
    }

    /**
     * 研究代表者を設定
     * @param string 研究代表者
     */
    public void setShinseisha(String string) {
        shinseisha = string;
    }

    /**
     * 研究分担者を取得
     * @return 研究分担者
     */
    public String getKenkyuBuntansha() {
        return kenkyuBuntansha;
    }

    /**
     * 研究分担者を設定
     * @param string 研究分担者
     */
    public void setKenkyuBuntansha(String string) {
        kenkyuBuntansha = string;
    }

    /**
     * ヒトゲノムを取得
     * @return ヒトゲノム
     */
    public String getHitogenomu() {
        return hitogenomu;
    }

    /**
     * ヒトゲノムを設定
     * @param string ヒトゲノム
     */
    public void setHitogenomu(String string) {
        hitogenomu = string;
    }

    /**
     * 特定胚を取得
     * @return 特定胚
     */
    public String getTokutei() {
        return tokutei;
    }

    /**
     * 特定胚を設定
     * @param string 特定胚
     */
    public void setTokutei(String string) {
        tokutei = string;
    }

    /**
     * ヒトES細胞を取得
     * @return ヒトES細胞
     */
    public String getHitoEs() {
        return hitoEs;
    }

    /**
     * ヒトES細胞を設定
     * @param string ヒトES細胞
     */
    public void setHitoEs(String string) {
        hitoEs = string;
    }

    /**
     * 遺伝子組換え実験を取得
     * @return 遺伝子組換え実験
     */
    public String getKumikae() {
        return kumikae;
    }

    /**
     * 遺伝子組換え実験を設定
     * @param string 遺伝子組換え実験
     */
    public void setKumikae(String string) {
        kumikae = string;
    }
    
    /**
     * 遺伝子治療臨床研究を取得
     * @return 遺伝子治療臨床研究
     */
    public String getChiryo() {
        return chiryo;
    }

    /**
     * 遺伝子治療臨床研究を設定
     * @param string 遺伝子治療臨床研究
     */
    public void setChiryo(String string) {
        chiryo = string;
    }

    /**
     * 疫学研究を取得
     * @return 疫学研究
     */
    public String getEkigaku() {
        return ekigaku;
    }

    /**
     * 疫学研究を設定
     * @param string 疫学研究
     */
    public void setEkigaku(String string) {
        ekigaku = string;
    }

    /**
     * コメントを取得
     * @return コメント
     */
    public String getComments() {
        return comments;
    }

    /**
     * コメントを設定
     * @param string コメント
     */
    public void setComments(String string) {
        comments = string;
    }

    /**
     * 利害関係を取得
     * @return 利害関係
     */
    public String getRigai() {
        return rigai;
    }

    /**
     * 利害関係を設定
     * @param string 利害関係
     */
    public void setRigai(String string) {
        rigai = string;
    }

    /**
     * 学術的重要性・妥当性を取得
     * @return 学術的重要性・妥当性
     */
    public String getJuyosei() {
        return juyosei;
    }

    /**
     * 学術的重要性・妥当性を設定
     * @param string 学術的重要性・妥当性
     */
    public void setJuyosei(String string) {
        juyosei = string;
    }

    /**
     * 独創性・革新性を取得
     * @return 独創性・革新性
     */
    public String getDokusosei() {
        return dokusosei;
    }

    /**
     * 独創性・革新性を設定
     * @param string 独創性・革新性
     */
    public void setDokusosei(String string) {
        dokusosei = string;
    }

    /**
     * 波及効果・普遍性を取得
     * @return 波及効果・普遍性
     */
    public String getHakyukoka() {
        return hakyukoka;
    }

    /**
     * 波及効果・普遍性を設定
     * @param string 波及効果・普遍性
     */
    public void setHakyukoka(String string) {
        hakyukoka = string;
    }

    /**
     * 遂行能力・環境の適切性を取得
     * @return 遂行能力・環境の適切性
     */
    public String getSuikonoryoku() {
        return suikonoryoku;
    }

    /**
     * 遂行能力・環境の適切性を設定
     * @param string 遂行能力・環境の適切性
     */
    public void setSuikonoryoku(String string) {
        suikonoryoku = string;
    }

    /**
     * 人権の保護・法令等の遵守を取得
     * @return 人権の保護・法令等の遵守
     */
    public String getJinken() {
        return jinken;
    }

    /**
     * 人権の保護・法令等の遵守を設定
     * @param string 人権の保護・法令等の遵守
     */
    public void setJinken(String string) {
        jinken = string;
    }

    /**
     * 分担金を取得
     * @return 分担金
     */
    public String getBuntankin() {
        return buntankin;
    }

    /**
     * 分担金を設定
     * @param string 分担金
     */
    public void setBuntankin(String string) {
        buntankin = string;
    }

    /**
     * その他コメントを取得
     * @return その他コメント
     */
    public String getOtherComment() {
        return otherComment;
    }

    /**
     * その他コメントを設定
     * @param string その他コメント
     */
    public void setOtherComment(String string) {
        otherComment = string;
    }

    /**
     * 添付ファイルを取得
     * @return 添付ファイル
     */
	public String getTenpuPath() {
		return tenpuPath;
	}

    /**
     * 添付ファイルを設定
     * @param string 添付ファイル
     */
    public void setTenpuPath(String string) {
        tenpuPath = string;
    }

    /**
     * 添付ファイルフラグを取得
     * @return 添付ファイルフラグ
     */
    public String getTenpuFlg() {
        return tenpuFlg;
    }

    /**
     * 添付ファイルフラグを設定
     * @param string 添付ファイルフラグ
     */
    public void setTenpuFlg(String string) {
        tenpuFlg = string;
    }

    /**
     * 添付ファイル(アップロードファイル)を取得
     * @return 添付ファイル(アップロードファイル)
     */
	public FormFile getTenpuUploadFile() {
		return tenpuUploadFile;
	}

    /**
     * 添付ファイル(アップロードファイル)を設定
     * @param file 添付ファイル(アップロードファイル)
     */
	public void setTenpuUploadFile(FormFile file) {
		tenpuUploadFile = file;
	}

    /**
     * 評価用ファイルフラグを取得
     * @return 評価用ファイルフラグ
     */
    public String getHyokaFileFlg() {
        return hyokaFileFlg;
    }

    /**
     * 評価用ファイルフラグを設定
     * @param string 評価用ファイルフラグ
     */
    public void setHyokaFileFlg(String string) {
        hyokaFileFlg = string;
    }

    /**
     * 総合評価（ABC）選択リストを取得
     * @return 総合評価（ABC）選択リスト
     */
	public List getKekkaAbcList() {
		return kekkaAbcList;
	}

    /**
     * 総合評価（ABC）選択リストを設定
     * @param list 総合評価（ABC）選択リスト
     */
	public void setKekkaAbcList(List list) {
		kekkaAbcList = list;
	}

    /**
     * 総合評価（点数）選択リストを取得
     * @return 総合評価（点数）選択リスト
     */
    public List getKekkaTenList() {
        return kekkaTenList;
    }

    /**
     * 総合評価（点数）選択リストを設定
     * @param list 総合評価（点数）選択リスト
     */
    public void setKekkaTenList(List list) {
        kekkaTenList = list;
    }

    /**
     * 総合評価（点数）萌芽選択リストを取得
     * @return 総合評価（点数）萌芽選択リスト
     */
    public List getKekkaTenHogaList() {
        return kekkaTenHogaList;
    }

    /**
     * 総合評価（点数）萌芽選択リストを設定
     * @param list 総合評価（点数）萌芽選択リスト
     */
    public void setKekkaTenHogaList(List list) {
        kekkaTenHogaList = list;
    }

    /**
     * 研究内容選択リストを取得
     * @return 研究内容選択リスト
     */
    public List getKenkyuNaiyoList() {
        return kenkyuNaiyoList;
    }

    /**
     * 研究内容選択リストを設定
     * @param list 研究内容選択リスト
     */
    public void setKenkyuNaiyoList(List list) {
        kenkyuNaiyoList = list;
    }

    /**
     * 研究計画選択リストを取得
     * @return 研究計画選択リスト
     */
    public List getKenkyuKeikakuList() {
        return kenkyuKeikakuList;
    }

    /**
     * 研究計画選択リストを設定
     * @param list 研究計画選択リスト
     */
    public void setKenkyuKeikakuList(List list) {
        kenkyuKeikakuList = list;
    }

    /**
     * 適切性-海外選択リストを取得
     * @return 適切性-海外選択リスト
     */
    public List getTekisetsuKaigaiList() {
        return tekisetsuKaigaiList;
    }

    /**
     * 適切性-海外選択リストを設定
     * @param list 適切性-海外選択リスト
     */
    public void setTekisetsuKaigaiList(List list) {
        tekisetsuKaigaiList = list;
    }

    /**
     * 適切性-研究（1）選択リストを取得
     * @return 適切性-研究（1）選択リスト
     */
    public List getTekisetsuKenkyu1List() {
        return tekisetsuKenkyu1List;
    }

    /**
     * 適切性-研究（1）選択リストを設定
     * @param list 適切性-研究（1）選択リスト
     */
    public void setTekisetsuKenkyu1List(List list) {
        tekisetsuKenkyu1List = list;
    }

    /**
     * 適切性選択リストを取得
     * @return 適切性選択リスト
     */
    public List getTekisetsuList() {
        return tekisetsuList;
    }

    /**
     * 適切性選択リストを設定
     * @param list 適切性選択リスト
     */
    public void setTekisetsuList(List list) {
        tekisetsuList = list;
    }

    /**
     * 妥当性選択リストを取得
     * @return 妥当性選択リスト
     */
    public List getDatoList() {
        return datoList;
    }

    /**
     * 妥当性選択リストを設定
     * @param list 妥当性選択リスト
     */
    public void setDatoList(List list) {
        datoList = list;
    }

    /**
     * 研究代表者選択リストを取得
     * @return 研究代表者選択リスト
     */
    public List getShinseishaList() {
        return shinseishaList;
    }

    /**
     * 研究代表者選択リストを設定
     * @param list 研究代表者選択リスト
     */
    public void setShinseishaList(List list) {
        shinseishaList = list;
    }

    /**
     * 研究分担者選択リストを取得
     * @return 研究分担者選択リスト
     */
    public List getKenkyuBuntanshaList() {
        return kenkyuBuntanshaList;
    }

    /**
     * 研究分担者選択リストを設定
     * @param list 研究分担者選択リスト
     */
    public void setKenkyuBuntanshaList(List list) {
        kenkyuBuntanshaList = list;
    }

    /**
     * ヒトゲノム選択リストを取得
     * @return ヒトゲノム選択リスト
     */
    public List getHitogenomuList() {
        return hitogenomuList;
    }

    /**
     * ヒトゲノム選択リストを設定
     * @param list ヒトゲノム選択リスト
     */
    public void setHitogenomuList(List list) {
        hitogenomuList = list;
    }

    /**
     * 特定胚選択リストを取得
     * @return 特定胚選択リスト
     */
    public List getTokuteiList() {
        return tokuteiList;
    }

    /**
     * 特定胚選択リストを設定
     * @param list 特定胚選択リスト
     */
    public void setTokuteiList(List list) {
        tokuteiList = list;
    }

    /**
     * ヒトES細胞選択リストを取得
     * @return ヒトES細胞選択リスト
     */
    public List getHitoEsList() {
        return hitoEsList;
    }

    /**
     * ヒトES細胞選択リストを設定
     * @param list ヒトES細胞選択リスト
     */
    public void setHitoEsList(List list) {
        hitoEsList = list;
    }

    /**
     * 遺伝子組換え実験選択リストを取得
     * @return 遺伝子組換え実験選択リスト
     */
    public List getKumikaeList() {
        return kumikaeList;
    }

    /**
     * 遺伝子組換え実験選択リストを設定
     * @param list 遺伝子組換え実験選択リスト
     */
    public void setKumikaeList(List list) {
        kumikaeList = list;
    }

    /**
     * 遺伝子治療臨床研究選択リストを取得
     * @return 遺伝子治療臨床研究選択リスト
     */
	public List getChiryoList() {
		return chiryoList;
	}

    /**
     * 遺伝子治療臨床研究選択リストを設定
     * @param list 遺伝子治療臨床研究選択リスト
     */
    public void setChiryoList(List list) {
        chiryoList = list;
    }

    /**
     * 疫学研究選択リストを取得
     * @return 疫学研究選択リスト
     */
	public List getEkigakuList() {
		return ekigakuList;
	}

    /**
     * 疫学研究選択リストを設定
     * @param list 疫学研究選択リスト
     */
	public void setEkigakuList(List list) {
		ekigakuList = list;
	}

    /**
     * 利害関係リストを取得
     * @return 利害関係リスト
     */
    public List getRigaiList() {
        return rigaiList;
    }

    /**
     * 利害関係リストを設定
     * @param list 利害関係リスト
     */
    public void setRigaiList(List list) {
        rigaiList = list;
    }

    /**
     * 学術的重要性・妥当性リストを取得
     * @return 学術的重要性・妥当性リスト
     */
    public List getJuyoseiList() {
        return juyoseiList;
    }

    /**
     * 学術的重要性・妥当性リストを設定
     * @param list 学術的重要性・妥当性リスト
     */
    public void setJuyoseiList(List list) {
        juyoseiList = list;
    }

    /**
     * 独創性・革新性リストを取得
     * @return 独創性・革新性リスト
     */
    public List getDokusoseiList() {
        return dokusoseiList;
    }

    /**
     * 独創性・革新性リストを設定
     * @param list 独創性・革新性リスト
     */
    public void setDokusoseiList(List list) {
        dokusoseiList = list;
    }

    /**
     * 波及効果・普遍性リストを取得
     * @return 波及効果・普遍性リスト
     */
    public List getHakyukokaList() {
        return hakyukokaList;
    }

    /**
     * 波及効果・普遍性リストを設定
     * @param list 波及効果・普遍性リスト
     */
    public void setHakyukokaList(List list) {
        hakyukokaList = list;
    }

    /**
     * 遂行能力・環境の適切性リストを取得
     * @return 遂行能力・環境の適切性リスト
     */
    public List getSuikonoryokuList() {
        return suikonoryokuList;
    }

    /**
     * 遂行能力・環境の適切性リストを設定
     * @param list 遂行能力・環境の適切性リスト
     */
    public void setSuikonoryokuList(List list) {
        suikonoryokuList = list;
    }

    /**
     * 人権の保護・法令等の遵守リストを取得
     * @return 人権の保護・法令等の遵守リスト
     */
    public List getJinkenList() {
        return jinkenList;
    }

    /**
     * 人権の保護・法令等の遵守リストを設定
     * @param list 人権の保護・法令等の遵守リスト
     */
    public void setJinkenList(List list) {
        jinkenList = list;
    }

    /**
     * 分担金リストを取得
     * @return 分担金リスト
     */
	public List getBuntankinList() {
		return buntankinList;
	}

    /**
     * 分担金リストを設定
     * @param list 分担金リスト
     */
	public void setBuntankinList(List list) {
		buntankinList = list;
	}

	/**
     * 若手Sとしての妥当性を取得します。
     * 
     * @return wakates
     */
    
    public String getWakates() {
    	return wakates;
    }

	/**
     * 若手Sとしての妥当性を設定します。
     * 
     * @param wakates wakates
     */
    
    public void setWakates(String wakates) {
    	this.wakates = wakates;
    }
}
