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
 * 審査結果情報（審査結果入力画面用）を保持するクラス。
 * 
 * ID RCSfile="$RCSfile: ShinsaKekkaInputInfo.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:13 $"
 */
public class ShinsaKekkaInputInfo extends ShinsaKekkaPk{
	
	/**
     * <code>serialVersionUID</code> のコメント
     */
    private static final long serialVersionUID = -3393199654452788443L;

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------
	/** 申請番号 */
	private String uketukeNo;

	/** 年度 */
	private String nendo;
	
	/** 回数 */
	private String kaisu;

	/** 申請者名（漢字－姓） */
	private String nameKanjiSei;
	
	/** 申請者名（漢字－名） */
	private String nameKanjiMei;

	/** 研究課題名 */
	private String kadaiNameKanji;

	/** 審査員名（漢字－姓） */
	private String shinsainNameKanjiSei;
	
	/** 審査員名（漢字－名） */
	private String shinsainNameKanjiMei;
	
	/** 申請者所属機関名 */
	private String shozokuName;
	
	/** 申請者部局名 */
	private String bukyokuName;
	
	/** 申請者職名 */
	private String shokushuName;

	/** 推薦の観点 */
	private String kanten;

	/** 系等の区分 */
	private String keiName;

	/** 事業ID */
	private String jigyoId;

	/** 事業コード */
	private String jigyoCd;
		
	/** 事業名 */
	private String jigyoName;
	
	/** 細目番号 */
	private String bunkaSaimokuCd;	

	/** 細目名 */
	private String saimokuName;	

	/** 総合評価（ABC） */
	private String kekkaAbc;	

	/** 総合評価（ABC）表示ラベル */
	private String kekkaAbcLabel;
	
	/** 総合評価（点数） */
	private String kekkaTen;	

	/** 総合評価（点数）表示ラベル */
	private String kekkaTenLabel;

	/** 総合評価（萌芽）表示ラベル */
	private String kekkaTenHogaLabel;
		
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

	/** 研究内容 表示ラベル */
	private String kenkyuNaiyoLabel;
	
	/** 研究計画 */
	private String kenkyuKeikaku;

	/** 研究計画 表示ラベル */
	private String kenkyuKeikakuLabel;

	/** 適切性-海外 */
	private String tekisetsuKaigai;

	/** 適切性-海外 表示ラベル */
	private String tekisetsuKaigaiLabel;
	
	/** 適切性-研究(1) */
	private String tekisetsuKenkyu1;

	/** 適切性-研究(1) 表示ラベル */
	private String tekisetsuKenkyu1Label;
	
	/** 適切性 */
	private String tekisetsu;

	/** 適切性 表示ラベル */
	private String tekisetsuLabel;
	
	/** 妥当性 */
	private String dato;

	/** 妥当性 表示ラベル */
	private String datoLabel;
	
	/** 研究代表者 */
	private String shinseisha;

	/** 研究代表者 表示ラベル */
	private String shinseishaLabel;

	/** 研究分担者 */
	private String kenkyuBuntansha;

	/** 研究分担者 表示ラベル */
	private String kenkyuBuntanshaLabel;
	
	/** ヒトゲノム */
	private String hitogenomu;

	/** ヒトゲノム 表示ラベル */
	private String hitogenomuLabel;
	
	/** 特定胚 */
	private String tokutei;

	/** 特定胚 表示ラベル */
	private String tokuteiLabel;

	/** ヒトES細胞 */
	private String hitoEs;

	/** ヒトES細胞 表示ラベル */
	private String hitoEsLabel;
	
	/** 遺伝子組換え実験 */
	private String kumikae;

	/** 遺伝子組換え実験 表示ラベル */
	private String kumikaeLabel;
	
	/** 遺伝子治療臨床研究 */
	private String chiryo;

	/** 遺伝子治療臨床研究 表示ラベル */
	private String chiryoLabel;
	
	/** 疫学研究 */
	private String ekigaku;

	/** 疫学研究 表示ラベル */
	private String ekigakuLabel;

	/** コメント */
	private String comments;
	
	/** 添付ファイル格納パス */
	private String tenpuPath;

	/** 添付ファイル格納フラグ */
	private String tenpuFlg;

	/** 添付ファイル名 */
	private String tenpuName;
	
	/** 評価用ファイル有無 */
	private String hyokaFileFlg;
	
	/** 評価用ファイル */
	private FileResource   hyokaFileRes;

	/** 研究組織の形態番号 */
	private String soshikiKeitaiNo;	

	/** 研究計画最終年度前年度の応募 */
	private String shinseiFlgNo;	
	
	/** 分担金の有無 */
	private String buntankinFlg;	
	
	/** 分割番号 */
	private String bunkatsuNo;	

	/** 海外分野コード */
	private String kaigaibunyaCd;	
	
	/** 海外分野名 */
	private String kaigaibunyaName;	
	
	/** 利害関係 */
	private String rigai;
	
	/** 利害関係 表示ラベル */
	private String rigaiLabel;

	/** 学術的重要性・妥当性 */
	private String juyosei;
	
	/** 学術的重要性・妥当性 表示ラベル */
	private String juyoseiLabel;

	/** 独創性・革新性 */
	private String dokusosei;
	
	/** 独創性・革新性  表示ラベル */
	private String dokusoseiLabel;

	/** 波及効果・普遍性 */
	private String hakyukoka;
	
	/** 波及効果・普遍性  表示ラベル */
	private String hakyukokaLabel;

	/** 遂行能力・環境の適切性 */
	private String suikonoryoku;
	
	/** 遂行能力・環境の適切性  表示ラベル */
	private String suikonoryokuLabel;
	
	/** 人権の保護・法令等の遵守 */
	private String jinken;
	
	/** 人権の保護・法令等の遵守  表示ラベル */
	private String jinkenLabel;
	
	/** 分担金 */
	private String buntankin;
	
	/** 分担金  表示ラベル */
	private String buntankinLabel;

	/** その他コメント */
	private String otherComment;
	
//2006/04/10 追加ここから
	/** 分野番号 */
	private String shinsaryoikiCd;
	
	/** 分野名 */
	private String shinsaryoikiName;
//苗　追加ここまで	
	
	/** 若手研究（S）としての妥当性 */
	private String wakates;

	/** 若手研究（S）としての妥当性  表示ラベル */
	private String wakatesLabel;
	
	
	//・・・・・・・・・・

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * コンストラクタ。
	 */
	public ShinsaKekkaInputInfo() {
		super();
	}

	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------
	/**
	 * @return
	 */
	public String getBukyokuName() {
		return bukyokuName;
	}

	/**
	 * @return
	 */
	public String getComments() {
		return comments;
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
	public String getJigyoName() {
		return jigyoName;
	}
	/**
	 * @return
	 */
	public String getShinsainNameKanjiMei() {
		return shinsainNameKanjiMei;
	}

	/**
	 * @return
	 */
	public String getShinsainNameKanjiSei() {
		return shinsainNameKanjiSei;
	}

	/**
	 * @return
	 */
	public String getShozokuName() {
		return shozokuName;
	}

	/**
	 * @return
	 */
	public String getTenpuPath() {
		return tenpuPath;
	}

	/**
	 * @return
	 */
	public String getUketukeNo() {
		return uketukeNo;
	}

	/**
	 * @param string
	 */
	public void setBukyokuName(String string) {
		bukyokuName = string;
	}

	/**
	 * @param string
	 */
	public void setComments(String string) {
		comments = string;
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
	public void setJigyoName(String string) {
		jigyoName = string;
	}

	/**
	 * @param string
	 */
	public void setShinsainNameKanjiMei(String string) {
		shinsainNameKanjiMei = string;
	}

	/**
	 * @param string
	 */
	public void setShinsainNameKanjiSei(String string) {
		shinsainNameKanjiSei = string;
	}

	/**
	 * @param string
	 */
	public void setShozokuName(String string) {
		shozokuName = string;
	}

	/**
	 * @param string
	 */
	public void setTenpuPath(String string) {
		tenpuPath = string;
	}

	/**
	 * @param string
	 */
	public void setUketukeNo(String string) {
		uketukeNo = string;
	}

	/**
	 * @return
	 */
	public String getBunkaSaimokuCd() {
		return bunkaSaimokuCd;
	}

	/**
	 * @return
	 */
	public String getChiryo() {
		return chiryo;
	}

	/**
	 * @return
	 */
	public String getComment1() {
		return comment1;
	}

	/**
	 * @return
	 */
	public String getComment2() {
		return comment2;
	}

	/**
	 * @return
	 */
	public String getComment3() {
		return comment3;
	}

	/**
	 * @return
	 */
	public String getComment4() {
		return comment4;
	}

	/**
	 * @return
	 */
	public String getComment5() {
		return comment5;
	}

	/**
	 * @return
	 */
	public String getComment6() {
		return comment6;
	}

	/**
	 * @return
	 */
	public String getDato() {
		return dato;
	}

	/**
	 * @return
	 */
	public String getEkigaku() {
		return ekigaku;
	}

	/**
	 * @return
	 */
	public String getHitoEs() {
		return hitoEs;
	}

	/**
	 * @return
	 */
	public String getHitogenomu() {
		return hitogenomu;
	}

	/**
	 * @return
	 */
	public String getKenkyuBuntansha() {
		return kenkyuBuntansha;
	}

	/**
	 * @return
	 */
	public String getKenkyuKeikaku() {
		return kenkyuKeikaku;
	}

	/**
	 * @return
	 */
	public String getKenkyuNaiyo() {
		return kenkyuNaiyo;
	}

	/**
	 * @return
	 */
	public String getKumikae() {
		return kumikae;
	}

	/**
	 * @return
	 */
	public String getShinseisha() {
		return shinseisha;
	}

	/**
	 * @return
	 */
	public String getShokushuName() {
		return shokushuName;
	}

	/**
	 * @return
	 */
	public String getTekisetsu() {
		return tekisetsu;
	}

	/**
	 * @return
	 */
	public String getTekisetsuKaigai() {
		return tekisetsuKaigai;
	}

	/**
	 * @return
	 */
	public String getTekisetsuKenkyu1() {
		return tekisetsuKenkyu1;
	}

	/**
	 * @return
	 */
	public String getTokutei() {
		return tokutei;
	}

	/**
	 * @param string
	 */
	public void setBunkaSaimokuCd(String string) {
		bunkaSaimokuCd = string;
	}

	/**
	 * @param string
	 */
	public void setChiryo(String string) {
		chiryo = string;
	}

	/**
	 * @param string
	 */
	public void setComment1(String string) {
		comment1 = string;
	}

	/**
	 * @param string
	 */
	public void setComment2(String string) {
		comment2 = string;
	}

	/**
	 * @param string
	 */
	public void setComment3(String string) {
		comment3 = string;
	}

	/**
	 * @param string
	 */
	public void setComment4(String string) {
		comment4 = string;
	}

	/**
	 * @param string
	 */
	public void setComment5(String string) {
		comment5 = string;
	}

	/**
	 * @param string
	 */
	public void setComment6(String string) {
		comment6 = string;
	}

	/**
	 * @param string
	 */
	public void setDato(String string) {
		dato = string;
	}

	/**
	 * @param string
	 */
	public void setEkigaku(String string) {
		ekigaku = string;
	}

	/**
	 * @param string
	 */
	public void setHitoEs(String string) {
		hitoEs = string;
	}

	/**
	 * @param string
	 */
	public void setHitogenomu(String string) {
		hitogenomu = string;
	}

	/**
	 * @param string
	 */
	public void setKenkyuBuntansha(String string) {
		kenkyuBuntansha = string;
	}

	/**
	 * @param string
	 */
	public void setKenkyuKeikaku(String string) {
		kenkyuKeikaku = string;
	}

	/**
	 * @param string
	 */
	public void setKenkyuNaiyo(String string) {
		kenkyuNaiyo = string;
	}

	/**
	 * @param string
	 */
	public void setKumikae(String string) {
		kumikae = string;
	}

	/**
	 * @param string
	 */
	public void setShinseisha(String string) {
		shinseisha = string;
	}

	/**
	 * @param string
	 */
	public void setShokushuName(String string) {
		shokushuName = string;
	}

	/**
	 * @param string
	 */
	public void setTekisetsu(String string) {
		tekisetsu = string;
	}

	/**
	 * @param string
	 */
	public void setTekisetsuKaigai(String string) {
		tekisetsuKaigai = string;
	}

	/**
	 * @param string
	 */
	public void setTekisetsuKenkyu1(String string) {
		tekisetsuKenkyu1 = string;
	}

	/**
	 * @param string
	 */
	public void setTokutei(String string) {
		tokutei = string;
	}

	/**
	 * @return
	 */
	public String getHyokaFileFlg() {
		return hyokaFileFlg;
	}

	/**
	 * @return
	 */
	public String getKadaiNameKanji() {
		return kadaiNameKanji;
	}

	/**
	 * @return
	 */
	public String getKaisu() {
		return kaisu;
	}

	/**
	 * @return
	 */
	public String getNameKanjiMei() {
		return nameKanjiMei;
	}

	/**
	 * @return
	 */
	public String getNameKanjiSei() {
		return nameKanjiSei;
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
	public void setHyokaFileFlg(String string) {
		hyokaFileFlg = string;
	}

	/**
	 * @param string
	 */
	public void setKadaiNameKanji(String string) {
		kadaiNameKanji = string;
	}

	/**
	 * @param string
	 */
	public void setKaisu(String string) {
		kaisu = string;
	}

	/**
	 * @param string
	 */
	public void setNameKanjiMei(String string) {
		nameKanjiMei = string;
	}

	/**
	 * @param string
	 */
	public void setNameKanjiSei(String string) {
		nameKanjiSei = string;
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
	public FileResource getHyokaFileRes() {
		return hyokaFileRes;
	}

	/**
	 * @param resource
	 */
	public void setHyokaFileRes(FileResource resource) {
		hyokaFileRes = resource;
	}

	/**
	 * @return
	 */
	public String getKanten() {
		return kanten;
	}

	/**
	 * @return
	 */
	public String getKeiName() {
		return keiName;
	}

	/**
	 * @param string
	 */
	public void setKanten(String string) {
		kanten = string;
	}

	/**
	 * @param string
	 */
	public void setKeiName(String string) {
		keiName = string;
	}

	/**
	 * @return
	 */
	public String getSaimokuName() {
		return saimokuName;
	}

	/**
	 * @param string
	 */
	public void setSaimokuName(String string) {
		saimokuName = string;
	}

	/**
	 * @return
	 */
	public String getKekkaAbc() {
		return kekkaAbc;
	}
	
		/**
		 * @return
		 */
		public String getKekkaTen() {
			return kekkaTen;
		}
	
	/**
	 * @param string
	 */
	public void setKekkaAbc(String string) {
		kekkaAbc = string;
	}

	/**
	 * @param string
	 */
	public void setKekkaTen(String string) {
		kekkaTen = string;
	}

	/**
	 * @return
	 */
	public String getKekkaAbcLabel() {
		return kekkaAbcLabel;
	}

	/**
	 * @return
	 */
	public String getKekkaTenLabel() {
		return kekkaTenLabel;
	}

	/**
	 * @param string
	 */
	public void setKekkaAbcLabel(String string) {
		kekkaAbcLabel = string;
	}

	/**
	 * @param string
	 */
	public void setKekkaTenLabel(String string) {
		kekkaTenLabel = string;
	}

	/**
	 * @return
	 */
	public String getTenpuFlg() {
		return tenpuFlg;
	}

	/**
	 * @param string
	 */
	public void setTenpuFlg(String string) {
		tenpuFlg = string;
	}

	/**
	 * @return
	 */
	public String getTenpuName() {
		return tenpuName;
	}

	/**
	 * @param string
	 */
	public void setTenpuName(String string) {
		tenpuName = string;
	}

	/**
	 * @return
	 */
	public String getChiryoLabel() {
		return chiryoLabel;
	}

	/**
	 * @return
	 */
	public String getDatoLabel() {
		return datoLabel;
	}

	/**
	 * @return
	 */
	public String getEkigakuLabel() {
		return ekigakuLabel;
	}

	/**
	 * @return
	 */
	public String getHitoEsLabel() {
		return hitoEsLabel;
	}

	/**
	 * @return
	 */
	public String getHitogenomuLabel() {
		return hitogenomuLabel;
	}

	/**
	 * @return
	 */
	public String getKenkyuBuntanshaLabel() {
		return kenkyuBuntanshaLabel;
	}

	/**
	 * @return
	 */
	public String getKenkyuKeikakuLabel() {
		return kenkyuKeikakuLabel;
	}

	/**
	 * @return
	 */
	public String getKenkyuNaiyoLabel() {
		return kenkyuNaiyoLabel;
	}

	/**
	 * @return
	 */
	public String getKumikaeLabel() {
		return kumikaeLabel;
	}

	/**
	 * @return
	 */
	public String getShinseishaLabel() {
		return shinseishaLabel;
	}

	/**
	 * @return
	 */
	public String getTekisetsuKaigaiLabel() {
		return tekisetsuKaigaiLabel;
	}

	/**
	 * @return
	 */
	public String getTekisetsuKenkyu1Label() {
		return tekisetsuKenkyu1Label;
	}

	/**
	 * @return
	 */
	public String getTekisetsuLabel() {
		return tekisetsuLabel;
	}

	/**
	 * @return
	 */
	public String getTokuteiLabel() {
		return tokuteiLabel;
	}

	/**
	 * @param string
	 */
	public void setChiryoLabel(String string) {
		chiryoLabel = string;
	}

	/**
	 * @param string
	 */
	public void setDatoLabel(String string) {
		datoLabel = string;
	}

	/**
	 * @param string
	 */
	public void setEkigakuLabel(String string) {
		ekigakuLabel = string;
	}

	/**
	 * @param string
	 */
	public void setHitoEsLabel(String string) {
		hitoEsLabel = string;
	}

	/**
	 * @param string
	 */
	public void setHitogenomuLabel(String string) {
		hitogenomuLabel = string;
	}

	/**
	 * @param string
	 */
	public void setKenkyuBuntanshaLabel(String string) {
		kenkyuBuntanshaLabel = string;
	}

	/**
	 * @param string
	 */
	public void setKenkyuKeikakuLabel(String string) {
		kenkyuKeikakuLabel = string;
	}

	/**
	 * @param string
	 */
	public void setKenkyuNaiyoLabel(String string) {
		kenkyuNaiyoLabel = string;
	}

	/**
	 * @param string
	 */
	public void setKumikaeLabel(String string) {
		kumikaeLabel = string;
	}

	/**
	 * @param string
	 */
	public void setShinseishaLabel(String string) {
		shinseishaLabel = string;
	}

	/**
	 * @param string
	 */
	public void setTekisetsuKaigaiLabel(String string) {
		tekisetsuKaigaiLabel = string;
	}

	/**
	 * @param string
	 */
	public void setTekisetsuKenkyu1Label(String string) {
		tekisetsuKenkyu1Label = string;
	}

	/**
	 * @param string
	 */
	public void setTekisetsuLabel(String string) {
		tekisetsuLabel = string;
	}

	/**
	 * @param string
	 */
	public void setTokuteiLabel(String string) {
		tokuteiLabel = string;
	}

	/**
	 * @return
	 */
	public String getJigyoCd() {
		return jigyoCd;
	}

	/**
	 * @param string
	 */
	public void setJigyoCd(String string) {
		jigyoCd = string;
	}

	/**
	 * @return
	 */
	public String getSoshikiKeitaiNo() {
		return soshikiKeitaiNo;
	}

	/**
	 * @param string
	 */
	public void setSoshikiKeitaiNo(String string) {
		soshikiKeitaiNo = string;
	}

	/**
	 * @return
	 */
	public String getBunkatsuNo() {
		return bunkatsuNo;
	}

	/**
	 * @return
	 */
	public String getBuntankinFlg() {
		return buntankinFlg;
	}

	/**
	 * @return
	 */
	public String getShinseiFlgNo() {
		return shinseiFlgNo;
	}

	/**
	 * @param string
	 */
	public void setBunkatsuNo(String string) {
		bunkatsuNo = string;
	}

	/**
	 * @param string
	 */
	public void setBuntankinFlg(String string) {
		buntankinFlg = string;
	}

	/**
	 * @param string
	 */
	public void setShinseiFlgNo(String string) {
		shinseiFlgNo = string;
	}

	/**
	 * @return
	 */
	public String getKaigaibunyaCd() {
		return kaigaibunyaCd;
	}

	/**
	 * @return
	 */
	public String getKaigaibunyaName() {
		return kaigaibunyaName;
	}

	/**
	 * @param string
	 */
	public void setKaigaibunyaCd(String string) {
		kaigaibunyaCd = string;
	}

	/**
	 * @param string
	 */
	public void setKaigaibunyaName(String string) {
		kaigaibunyaName = string;
	}

	/**
	 * @return
	 */
	public String getBuntankin() {
		return buntankin;
	}

	/**
	 * @return
	 */
	public String getDokusosei() {
		return dokusosei;
	}

	/**
	 * @return
	 */
	public String getHakyukoka() {
		return hakyukoka;
	}

	/**
	 * @return
	 */
	public String getJinken() {
		return jinken;
	}

	/**
	 * @return
	 */
	public String getJuyosei() {
		return juyosei;
	}

	/**
	 * @return
	 */
	public String getOtherComment() {
		return otherComment;
	}

	/**
	 * @return
	 */
	public String getRigai() {
		return rigai;
	}

	/**
	 * @return
	 */
	public String getSuikonoryoku() {
		return suikonoryoku;
	}

	/**
	 * @param string
	 */
	public void setBuntankin(String string) {
		buntankin = string;
	}

	/**
	 * @param string
	 */
	public void setDokusosei(String string) {
		dokusosei = string;
	}

	/**
	 * @param string
	 */
	public void setHakyukoka(String string) {
		hakyukoka = string;
	}

	/**
	 * @param string
	 */
	public void setJinken(String string) {
		jinken = string;
	}

	/**
	 * @param string
	 */
	public void setJuyosei(String string) {
		juyosei = string;
	}

	/**
	 * @param string
	 */
	public void setOtherComment(String string) {
		otherComment = string;
	}

	/**
	 * @param string
	 */
	public void setRigai(String string) {
		rigai = string;
	}

	/**
	 * @param string
	 */
	public void setSuikonoryoku(String string) {
		suikonoryoku = string;
	}

	/**
	 * @return
	 */
	public String getBuntankinLabel() {
		return buntankinLabel;
	}

	/**
	 * @return
	 */
	public String getDokusoseiLabel() {
		return dokusoseiLabel;
	}

	/**
	 * @return
	 */
	public String getHakyukokaLabel() {
		return hakyukokaLabel;
	}

	/**
	 * @return
	 */
	public String getJinkenLabel() {
		return jinkenLabel;
	}

	/**
	 * @return
	 */
	public String getJuyoseiLabel() {
		return juyoseiLabel;
	}

	/**
	 * @return
	 */
	public String getRigaiLabel() {
		return rigaiLabel;
	}

	/**
	 * @return
	 */
	public String getSuikonoryokuLabel() {
		return suikonoryokuLabel;
	}

	/**
	 * @param string
	 */
	public void setBuntankinLabel(String string) {
		buntankinLabel = string;
	}

	/**
	 * @param string
	 */
	public void setDokusoseiLabel(String string) {
		dokusoseiLabel = string;
	}

	/**
	 * @param string
	 */
	public void setHakyukokaLabel(String string) {
		hakyukokaLabel = string;
	}

	/**
	 * @param string
	 */
	public void setJinkenLabel(String string) {
		jinkenLabel = string;
	}

	/**
	 * @param string
	 */
	public void setJuyoseiLabel(String string) {
		juyoseiLabel = string;
	}

	/**
	 * @param string
	 */
	public void setRigaiLabel(String string) {
		rigaiLabel = string;
	}

	/**
	 * @param string
	 */
	public void setSuikonoryokuLabel(String string) {
		suikonoryokuLabel = string;
	}

	/**
	 * @return
	 */
	public String getKekkaTenHogaLabel() {
		return kekkaTenHogaLabel;
	}

	/**
	 * @param string
	 */
	public void setKekkaTenHogaLabel(String string) {
		kekkaTenHogaLabel = string;
	}

	/**
	 * @return Returns the shinsaryoikiCd.
	 */
	public String getShinsaryoikiCd() {
		return shinsaryoikiCd;
	}

	/**
	 * @param shinsaryoikiCd The shinsaryoikiCd to set.
	 */
	public void setShinsaryoikiCd(String shinsaryoikiCd) {
		this.shinsaryoikiCd = shinsaryoikiCd;
	}

	/**
	 * @return Returns the shinsaryoikiName.
	 */
	public String getShinsaryoikiName() {
		return shinsaryoikiName;
	}

	/**
	 * @param shinsaryoikiName The shinsaryoikiName to set.
	 */
	public void setShinsaryoikiName(String shinsaryoikiName) {
		this.shinsaryoikiName = shinsaryoikiName;
	}

	/**
     * wakatesを取得します。
     * 
     * @return wakates
     */
    
    public String getWakates() {
    	return wakates;
    }

	/**
     * wakatesを設定します。
     * 
     * @param wakates wakates
     */
    
    public void setWakates(String wakates) {
    	this.wakates = wakates;
    }

	/**
     * wakatesLabelを取得します。
     * 
     * @return wakatesLabel
     */
    
    public String getWakatesLabel() {
    	return wakatesLabel;
    }

	/**
     * wakatesLabelを設定します。
     * 
     * @param wakatesLabel wakatesLabel
     */
    
    public void setWakatesLabel(String wakatesLabel) {
    	this.wakatesLabel = wakatesLabel;
    }

}
