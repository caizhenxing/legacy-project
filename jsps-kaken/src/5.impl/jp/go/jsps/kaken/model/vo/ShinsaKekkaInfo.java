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

import java.io.File;
import java.util.*;

/**
 * �R�����ʏ���ێ�����N���X�B
 * 
 * ID RCSfile="$RCSfile: ShinsaKekkaInfo.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:15 $"
 */
public class ShinsaKekkaInfo extends ShinsaKekkaPk{

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------
	/** �\���ԍ� */
	private String uketukeNo;

	/** �V�[�P���X�ԍ� */
	private String seqNo;

	/** �R���敪 */
	private String shinsaKubun;
			
	/** �R�������i�����|���j */
	private String shinsainNameKanjiSei;
	
	/** �R�������i�����|���j */
	private String shinsainNameKanjiMei;

	/** �R�������i�t���K�i�|���j */
	private String nameKanaSei;
	
	/** �R�������i�t���K�i�|���j */
	private String nameKanaMei;
	
	/** �R���������@�֖� */
	private String shozokuName;
	
	/** �R�������ǖ� */
	private String bukyokuName;
	
	/** �R�����E�� */
	private String shokushuName;

	/** ����ID */
	private String jigyoId;
	
	/** ���Ɩ� */
	private String jigyoName;

	/** �זڔԍ� */
	private String bunkaSaimokuCd;	

	/** �}�� */
	private String edaNo;	

	/** �`�F�b�N�f�W�b�g */
	private String checkDigit;	

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

	/** �����v�� */
	private String kenkyuKeikaku;

	/** �K�ؐ�-�C�O */
	private String tekisetsuKaigai;

	/** �K�ؐ�-����(1) */
	private String tekisetsuKenkyu1;

	/** �K�ؐ� */
	private String tekisetsu;

	/** �Ó��� */
	private String dato;

	/** ������\�� */
	private String shinseisha;

	/** �������S�� */
	private String kenkyuBuntansha;

	/** �q�g�Q�m�� */
	private String hitogenomu;

	/** ������ */
	private String tokutei;

	/** �q�gES�זE */
	private String hitoEs;

	/** ��`�q�g�������� */
	private String kumikae;

	/** ��`�q���×Տ����� */
	private String chiryo;

	/** �u�w���� */
	private String ekigaku;

	/** �R�����g */
	private String comments;
	
	
	
	/** ���Q�֌W */
	private String rigai;
	
	/** �㗝�t���O */
	private String dairi;
	
	/** ���(S)�Ƃ��Ă̑Ó��� */
	private String wakates;

	/** �w�p�I�d�v���E�Ó��� */
	private String juyosei;

	/** �Ƒn���E�v�V�� */
	private String dokusosei;

	/** �g�y���ʁE���Ր� */
	private String hakyukoka;

	/** ���s�\�́E���̓K�ؐ� */
	private String suikonoryoku;

	/** �l���̕ی�E�@�ߓ��̏��� */
	private String jinken;
	
	/** ���S�� */
	private String buntankin;

	/** ���̑��R�����g */
	private String otherComment;
	
	/** ����U��X�V�� */
	private Date koshinDate;
	
	/** �Y�t�t�@�C���i�[�p�X */
	private String tenpuPath;
	
	/** ���S���z�� */
	private String shinsaJokyo;
	
	/** ���l */
	private String biko;

	/** �Y�t�t�@�C���� */
	private String tenpuName;

	/** �Y�t�t�@�C���t���O */
	private String tenpuFlg;	
	
	/** ���ƃR�[�h */
	private String jigyoCd;
	
	//�E�E�E�E�E�E�E�E�E�E

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * �R���X�g���N�^�B
	 */
	public ShinsaKekkaInfo() {
		super();
	}

	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------
	/**
	 * @return
	 */
	public String getBiko() {
		return biko;
	}

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
	public void setBiko(String string) {
		biko = string;
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
	public String getTenpuName() {
		if(tenpuName == null && tenpuPath != null){
			tenpuName = new File(tenpuPath).getName();
		}
		return tenpuName;
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
	public String getCheckDigit() {
		return checkDigit;
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
	public String getEdaNo() {
		return edaNo;
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
	public String getNameKanaMei() {
		return nameKanaMei;
	}

	/**
	 * @return
	 */
	public String getNameKanaSei() {
		return nameKanaSei;
	}

	/**
	 * @return
	 */
	public String getShinsaJokyo() {
		return shinsaJokyo;
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
	public String getKekkaAbc() {
		return kekkaAbc;
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
	public void setCheckDigit(String string) {
		checkDigit = string;
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
	public void setEdaNo(String string) {
		edaNo = string;
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
	public void setNameKanaMei(String string) {
		nameKanaMei = string;
	}

	/**
	 * @param string
	 */
	public void setNameKanaSei(String string) {
		nameKanaSei = string;
	}

	/**
	 * @param string
	 */
	public void setShinsaJokyo(String string) {
		shinsaJokyo = string;
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
	public void setKekkaAbc(String string) {
		kekkaAbc = string;
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
	public void setTenpuName(String string) {
		tenpuName = string;
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
	public String getSeqNo() {
		return seqNo;
	}

	/**
	 * @return
	 */
	public String getShinsaKubun() {
		return shinsaKubun;
	}

	/**
	 * @param string
	 */
	public void setSeqNo(String string) {
		seqNo = string;
	}

	/**
	 * @param string
	 */
	public void setShinsaKubun(String string) {
		shinsaKubun = string;
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
	 * @param string
	 */
	public void setKekkaAbcLabel(String string) {
		kekkaAbcLabel = string;
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
	public void setKekkaTenLabel(String string) {
		kekkaTenLabel = string;
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
	public String getDairi() {
		return dairi;
	}

	/**
	 * @param string
	 */
	public void setDairi(String string) {
		dairi = string;
	}

	/**
	 * @return
	 */
	public Date getKoshinDate() {
		return koshinDate;
	}

	/**
	 * @param date
	 */
	public void setKoshinDate(Date date) {
		koshinDate = date;
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
		
}
