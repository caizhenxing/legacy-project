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
 * �R�����ʏ��i�R�����ʓ��͉�ʗp�j��ێ�����N���X�B
 * 
 * ID RCSfile="$RCSfile: ShinsaKekkaInputInfo.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:13 $"
 */
public class ShinsaKekkaInputInfo extends ShinsaKekkaPk{
	
	/**
     * <code>serialVersionUID</code> �̃R�����g
     */
    private static final long serialVersionUID = -3393199654452788443L;

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------
	/** �\���ԍ� */
	private String uketukeNo;

	/** �N�x */
	private String nendo;
	
	/** �� */
	private String kaisu;

	/** �\���Җ��i�����|���j */
	private String nameKanjiSei;
	
	/** �\���Җ��i�����|���j */
	private String nameKanjiMei;

	/** �����ۑ薼 */
	private String kadaiNameKanji;

	/** �R�������i�����|���j */
	private String shinsainNameKanjiSei;
	
	/** �R�������i�����|���j */
	private String shinsainNameKanjiMei;
	
	/** �\���ҏ����@�֖� */
	private String shozokuName;
	
	/** �\���ҕ��ǖ� */
	private String bukyokuName;
	
	/** �\���ҐE�� */
	private String shokushuName;

	/** ���E�̊ϓ_ */
	private String kanten;

	/** �n���̋敪 */
	private String keiName;

	/** ����ID */
	private String jigyoId;

	/** ���ƃR�[�h */
	private String jigyoCd;
		
	/** ���Ɩ� */
	private String jigyoName;
	
	/** �זڔԍ� */
	private String bunkaSaimokuCd;	

	/** �זږ� */
	private String saimokuName;	

	/** �����]���iABC�j */
	private String kekkaAbc;	

	/** �����]���iABC�j�\�����x�� */
	private String kekkaAbcLabel;
	
	/** �����]���i�_���j */
	private String kekkaTen;	

	/** �����]���i�_���j�\�����x�� */
	private String kekkaTenLabel;

	/** �����]���i�G��j�\�����x�� */
	private String kekkaTenHogaLabel;
		
	/** �R�����g1 */
	private String comment1;

	/** �R�����g2 */
	private String comment2;

	/** �R�����g3 */
	private String comment3;

	/** �R�����g4 */
	private String comment4;

	/** �R�����g5 */
	private String comment5;

	/** �R�����g6 */
	private String comment6;

	/** �������e */
	private String kenkyuNaiyo;

	/** �������e �\�����x�� */
	private String kenkyuNaiyoLabel;
	
	/** �����v�� */
	private String kenkyuKeikaku;

	/** �����v�� �\�����x�� */
	private String kenkyuKeikakuLabel;

	/** �K�ؐ�-�C�O */
	private String tekisetsuKaigai;

	/** �K�ؐ�-�C�O �\�����x�� */
	private String tekisetsuKaigaiLabel;
	
	/** �K�ؐ�-����(1) */
	private String tekisetsuKenkyu1;

	/** �K�ؐ�-����(1) �\�����x�� */
	private String tekisetsuKenkyu1Label;
	
	/** �K�ؐ� */
	private String tekisetsu;

	/** �K�ؐ� �\�����x�� */
	private String tekisetsuLabel;
	
	/** �Ó��� */
	private String dato;

	/** �Ó��� �\�����x�� */
	private String datoLabel;
	
	/** ������\�� */
	private String shinseisha;

	/** ������\�� �\�����x�� */
	private String shinseishaLabel;

	/** �������S�� */
	private String kenkyuBuntansha;

	/** �������S�� �\�����x�� */
	private String kenkyuBuntanshaLabel;
	
	/** �q�g�Q�m�� */
	private String hitogenomu;

	/** �q�g�Q�m�� �\�����x�� */
	private String hitogenomuLabel;
	
	/** ������ */
	private String tokutei;

	/** ������ �\�����x�� */
	private String tokuteiLabel;

	/** �q�gES�זE */
	private String hitoEs;

	/** �q�gES�זE �\�����x�� */
	private String hitoEsLabel;
	
	/** ��`�q�g�������� */
	private String kumikae;

	/** ��`�q�g�������� �\�����x�� */
	private String kumikaeLabel;
	
	/** ��`�q���×Տ����� */
	private String chiryo;

	/** ��`�q���×Տ����� �\�����x�� */
	private String chiryoLabel;
	
	/** �u�w���� */
	private String ekigaku;

	/** �u�w���� �\�����x�� */
	private String ekigakuLabel;

	/** �R�����g */
	private String comments;
	
	/** �Y�t�t�@�C���i�[�p�X */
	private String tenpuPath;

	/** �Y�t�t�@�C���i�[�t���O */
	private String tenpuFlg;

	/** �Y�t�t�@�C���� */
	private String tenpuName;
	
	/** �]���p�t�@�C���L�� */
	private String hyokaFileFlg;
	
	/** �]���p�t�@�C�� */
	private FileResource   hyokaFileRes;

	/** �����g�D�̌`�Ԕԍ� */
	private String soshikiKeitaiNo;	

	/** �����v��ŏI�N�x�O�N�x�̉��� */
	private String shinseiFlgNo;	
	
	/** ���S���̗L�� */
	private String buntankinFlg;	
	
	/** �����ԍ� */
	private String bunkatsuNo;	

	/** �C�O����R�[�h */
	private String kaigaibunyaCd;	
	
	/** �C�O���얼 */
	private String kaigaibunyaName;	
	
	/** ���Q�֌W */
	private String rigai;
	
	/** ���Q�֌W �\�����x�� */
	private String rigaiLabel;

	/** �w�p�I�d�v���E�Ó��� */
	private String juyosei;
	
	/** �w�p�I�d�v���E�Ó��� �\�����x�� */
	private String juyoseiLabel;

	/** �Ƒn���E�v�V�� */
	private String dokusosei;
	
	/** �Ƒn���E�v�V��  �\�����x�� */
	private String dokusoseiLabel;

	/** �g�y���ʁE���Ր� */
	private String hakyukoka;
	
	/** �g�y���ʁE���Ր�  �\�����x�� */
	private String hakyukokaLabel;

	/** ���s�\�́E���̓K�ؐ� */
	private String suikonoryoku;
	
	/** ���s�\�́E���̓K�ؐ�  �\�����x�� */
	private String suikonoryokuLabel;
	
	/** �l���̕ی�E�@�ߓ��̏��� */
	private String jinken;
	
	/** �l���̕ی�E�@�ߓ��̏���  �\�����x�� */
	private String jinkenLabel;
	
	/** ���S�� */
	private String buntankin;
	
	/** ���S��  �\�����x�� */
	private String buntankinLabel;

	/** ���̑��R�����g */
	private String otherComment;
	
//2006/04/10 �ǉ���������
	/** ����ԍ� */
	private String shinsaryoikiCd;
	
	/** ���얼 */
	private String shinsaryoikiName;
//�c�@�ǉ������܂�	
	
	/** ��茤���iS�j�Ƃ��Ă̑Ó��� */
	private String wakates;

	/** ��茤���iS�j�Ƃ��Ă̑Ó���  �\�����x�� */
	private String wakatesLabel;
	
	
	//�E�E�E�E�E�E�E�E�E�E

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * �R���X�g���N�^�B
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
     * wakates���擾���܂��B
     * 
     * @return wakates
     */
    
    public String getWakates() {
    	return wakates;
    }

	/**
     * wakates��ݒ肵�܂��B
     * 
     * @param wakates wakates
     */
    
    public void setWakates(String wakates) {
    	this.wakates = wakates;
    }

	/**
     * wakatesLabel���擾���܂��B
     * 
     * @return wakatesLabel
     */
    
    public String getWakatesLabel() {
    	return wakatesLabel;
    }

	/**
     * wakatesLabel��ݒ肵�܂��B
     * 
     * @param wakatesLabel wakatesLabel
     */
    
    public void setWakatesLabel(String wakatesLabel) {
    	this.wakatesLabel = wakatesLabel;
    }

}
