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

import java.util.*;

/**
 * �\������������ێ�����N���X�B
 * 
 * ID RCSfile="$RCSfile: ShinseiSearchInfo.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:12 $"
 */
public class ShinseiSearchInfo extends SearchInfo{

	/**
	 * 
	 */
	private static final long serialVersionUID = -800367715758497474L;

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------
	/** ���񏇏� */
	private static final String[] ORDER_SPEC          = new String[]{" ASC", " DESC"};
	
	/** ���񏇏��F�����i�f�t�H���g�j */
	public static final int   ORDER_SPEC_ASC          = 0;
	
	/** ���񏇏��F�~���@*/
	public static final int   ORDER_SPEC_DESC         = 1;
	
	/** ���񍀖ځF�V�X�e����t�ԍ��@*/
	public static final String ORDER_BY_SYSTEM_NO      = "SYSTEM_NO";
	
	/** ���񍀖ځF�\���ԍ� */
	public static final String ORDER_BY_UKETUKE_NO     = "UKETUKE_NO";

	/** ���񍀖ځF�N�x */
	public static final String ORDER_BY_NENDO          = "NENDO";
	
	/** ���񍀖ځF�� */
	public static final String ORDER_BY_KAISU          = "KAISU";
	
	/** ���񍀖ځF����ID */
	public static final String ORDER_BY_JIGYO_ID       = "JIGYO_ID";
	
	/** ���񍀖ځF�\����ID */
	public static final String ORDER_BY_SHINSEISHA_ID  = "SHINSEISHA_ID";
	
	/** ���񍀖ځF�����Ҕԍ� */
	public static final String ORDER_BY_KENKYU_NO      = "KENKYU_NO";
	
	/** ���񍀖ځF�\���Җ��i�����|���j */
	public static final String ORDER_BY_SHINSEISHA_NAME_KANJI_SEI  = "NAME_KANJI_SEI";
	
	/** ���񍀖ځF�\���Җ��i�����|���j */
	public static final String ORDER_BY_SHINSEISHA_NAME_KANJI_MEI  = "NAME_KANJI_MEI";
	
	/** ���񍀖ځF�\���Җ��i�J�i�|���j */
	public static final String ORDER_BY_SHINSEISHA_NAME_KANA_SEI   = "NAME_KANA_SEI";
	
	/** ���񍀖ځF�\���Җ��i�J�i�|���j */
	public static final String ORDER_BY_SHINSEISHA_NAME_KANA_MEI   = "NAME_KANA_MEI";

	/** ���񍀖ځF�����@��CD */
	public static final String ORDER_BY_SHOZOKU_CD     = "SHOZOKU_CD";
	
	/** ���񍀖ځF1���R������(ABC) */
	public static final String ORDER_BY_KEKKA1_ABC     = "KEKKA1_ABC";
	
	/** ���񍀖ځF1���R������(�_��) */
	public static final String ORDER_BY_KEKKA1_TEN     = "KEKKA1_TEN";
	
	/** ���񍀖ځF2���R������ */
	public static final String ORDER_BY_KEKKA2         = "KEKKA2";
	
	/** ���񍀖ځF�n���̋敪�ԍ� */
	public static final String ORDER_BY_KEI_NAME_NO    = "KEI_NAME_NO";
	
	/** ���񍀖ځF���E�̊ϓ_�ԍ� */
	public static final String ORDER_BY_KANTEN_NO      = "KANTEN_NO";

	/** ���񍀖ځF�����ԍ� */
	public static final String ORDER_BY_SEIRI_NO      = "JURI_SEIRI_NO";
	
	/** ���񍀖ځF�V�[�P���X�i�����g�D�\�p�j */
	public static final String ORDER_BY_SEQ_NO      	= "SEQ_NO";
	
	/** �폜�t���O�u0�v */
	public static final String NOT_DELETE_FLG  = "0";	

	/** �폜�t���O�u1�v */
	public static final String DELETE_FLG      = "1";	
	
	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------
	/** �e�[�u�����ړ����i�f�t�H���g�󕶎��j */
	private String tablePrefix;
	
	/** ����ݒ� */
	private String order;
	
	/** �V�X�e����t�ԍ� */
	private String systemNo;
	
	/** �\���ԍ� */
	private String uketukeNo;
	
	/** ����ID */
	private String jigyoId;
	
	/** ���ƃR�[�h */
	private String jigyoCd;
	
	/** ���ƃR�[�h�i�����j*/
	private Collection tantoJigyoCd;
	
	/** ���Ɩ� */
	private String jigyoName;
	
	/** �N�x */
	private String nendo;
	
	/** �� */
	private String kaisu;
	
	/** �\����ID */
	private String shinseishaId;
	
	/** �\���Җ��i�����F���j�@*/
	private String nameKanjiSei;
	
	/** �\���Җ��i�����F���j */
	private String nameKanjiMei;
	
	/** �\���Җ��i�J�i�F���j�@*/
	private String nameKanaSei;
	
	/** �\���Җ��i�J�i�F���j */
	private String nameKanaMei;
	
	/** �\���Җ��i���[�}���F���j */
	private String nameRoSei;
	
	/** �\���Җ��i���[�}���F���j */
	private String nameRoMei;
	
	/** �����@�փR�[�h */
	private String shozokuCd;
    
//  2006/06/02 jzx�@add start
    /** ���������@�֖�(����) */
    private String shozokNm;
//  2006/06/02 jzx�@add end
	
	/** �����Ҕԍ� */
	private String kenkyuNo;
	
	/** ���Ƌ敪 */
	private Collection jigyoKubun;
	
	/** �n���̋敪�ԍ� */
	private String keiNameNo;

	/** �n���̋敪�܂��͌n���̋敪�i���́j */
	private String keiName;
	
	/** �֘A����̌����Ҏ���1�`3�̂����ꂩ */
	private String kanrenShimei;
	
	/** ���E�̊ϓ_�ԍ� */
	private String kantenNo;
	
	/** ���E�̊ϓ_ */
	private String kanten;
	
	/** ���E�̊ϓ_���� */
	private String kantenRyaku;
	
	/** �\���� */
	private String[] jokyoId;
	
	/** �Đ\���t���O */
	private String[] saishinseiFlg;
	
	/** �זڔԍ�1�܂��͍זڔԍ�2 */
	private String bunkasaimokuCd;
	
	/** 2���R������ */
	private int[] kekka2;
	
	/** �쐬���iFrom�jyyyy/MM/dd�^ */
	private String sakuseiDateFrom;
	
	/** �쐬���iTo�jyyyy/MM/dd�^ */
	private String sakuseiDateTo;
	
	/** �����@�֏��F���iFrom�jyyyy/MM/dd�^ */
	private String shoninDateFrom;
	
	/** �����@�֏��F���iTo�jyyyy/MM/dd�^ */
	private String shoninDateTo;
	
	/** �g�ݍ��킹�X�e�[�^�X�󋵁i�\���󋵂ƍĐ\���t���O�̑g�ݍ��킹�j */
	private CombinedStatusSearchInfo statusSearchInfo;

	/** ���ǃR�[�h */
	private String bukyokuCd;

	/** �폜�t���O */
	private String[] delFlg = new String[]{NOT_DELETE_FLG} ;		//������
	
	/** �󗝐����ԍ� */
	private String seiriNo;

	/** �V�K�E�p���敪 */
	private String shinseiKubun;
	
	/** �����ԍ� */
	private String bunkatsuNo;
	
	/** �����v��ŏI�N�x�O�N�x�̉��� */
	private String shinseiFlgNo;

	/** �v�挤���E���匤���E�I�������̈�敪 */
	private String kenkyuKubun;
	
	/** ������ */
	private String chouseiFlg;
	
	/** �̈�ԍ� */
	private String ryouikiNo;

	/** �������ڔԍ� */
	private String ryouikiKoumokuNo;

	/** ���S���̗L�� */
	private String buntankinFlg;
	
	/** �J����]�̗L�� */
	private String kaijiKiboFlg;
	
	
	//�E�E�E�E�E�E�E�E�E�E

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * �R���X�g���N�^�B
	 */
	public ShinseiSearchInfo() {
		super();
	}


	//---------------------------------------------------------------------
	// Public Methods
	//---------------------------------------------------------------------
	/**
	 * ��������̃e�[�u���ړ������Z�b�g����B
	 * �ړ����̃s���I�h�͕K�v�����B
	 * ��j�����ɁuA�v���Z�b�g�����ꍇ�B
	 *     SHINSEIDATAKANRI -> A.SHINSEIDATAKANRI �ƂȂ�B
	 * 
	 * ���ӁFsetOrder()���Ăяo���O�Ɏ��s���邱�ƁB
	 * @param prefix
	 */
	public void setTablePrefix(String prefix){
		tablePrefix = prefix + ".";
	}
	
	/**
	 * �w�肳�ꂽ�J�������������ɃZ�b�g����B
	 * ���ɐ���������w�肳��Ă����ꍇ�́A���̎��̃L�[�Ƃ��Đ�������ɒǉ�����B
	 * �J�������͖{�N���X�̃p�u���b�N�t�B�[���h[ORDER_BY_*]���Q�Ƃ��邱�ƁB
	 * @param columnName
	 */
	public void setOrder(String columnName){
		setOrder(columnName, ORDER_SPEC_ASC);
	}
	
	/**
	 * �w�肳�ꂽ�J���������w�菇�̐�������ɃZ�b�g����B
	 * ���ɐ���������w�肳��Ă����ꍇ�́A���̎��̃L�[�Ƃ��Đ�������ɒǉ�����B
	 * �J�������͖{�N���X�̃p�u���b�N�t�B�[���h[ORDER_BY_*]���Q�Ƃ��邱�ƁB
	 * �����A�~���̃Z�b�g�l�͖{�N���X�̃p�u���b�N�t�B�[���h[ORDER_SPEC_*]���Q�Ƃ��邱�ƁB
	 * @param columnName
	 */
	public void setOrder(String columnName, int orderType){
		
		//�e�[�u���ړ������Z�b�g����Ă���ꍇ�͒ǉ�
		if(tablePrefix != null && tablePrefix.length() != 0){
			columnName = tablePrefix + columnName;
		}
		
		//�������������
		String orderBy = columnName + ORDER_SPEC[orderType];
		
		//�ŏ��̃p�����[�^�̏ꍇ
		if(order == null || order.length() == 0){
			order = orderBy;
		//�Q�Ԗڈȍ~
		}else{
			order = order + ", " + orderBy;
		}
	}
	
	/**
	 * �ݒ肳��Ă��鐮�񏇂��N���A����B
	 */
	public void clrOrder(){
		this.order = null;
	}
	
	
	
	
	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------
	
	/**
	 * @return
	 */
	public String getBunkasaimokuCd() {
		return bunkasaimokuCd;
	}

	/**
	 * @return
	 */
	public String getJigyoCd() {
		return jigyoCd;
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
	public String[] getJokyoId() {
		return jokyoId;
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
	public String getKanrenShimei() {
		return kanrenShimei;
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
	public String getKantenNo() {
		return kantenNo;
	}

	/**
	 * @return
	 */
	public String getKantenRyaku() {
		return kantenRyaku;
	}

	/**
	 * @return
	 */
	public String getKeiName() {
		return keiName;
	}

	/**
	 * @return
	 */
	public String getKeiNameNo() {
		return keiNameNo;
	}

	/**
	 * @return
	 */
	public int[] getKekka2() {
		return kekka2;
	}

	/**
	 * @return
	 */
	public String getKenkyuNo() {
		return kenkyuNo;
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
	public String getNameRoMei() {
		return nameRoMei;
	}

	/**
	 * @return
	 */
	public String getNameRoSei() {
		return nameRoSei;
	}

	/**
	 * @return
	 */
	public String getNendo() {
		return nendo;
	}

	/**
	 * @return
	 */
	public String getOrder() {
		return order;
	}

	/**
	 * @return
	 */
	public String getSakuseiDateFrom() {
		return sakuseiDateFrom;
	}

	/**
	 * @return
	 */
	public String getSakuseiDateTo() {
		return sakuseiDateTo;
	}

	/**
	 * @return
	 */
	public String getShinseishaId() {
		return shinseishaId;
	}

	/**
	 * @return
	 */
	public String getShoninDateFrom() {
		return shoninDateFrom;
	}

	/**
	 * @return
	 */
	public String getShoninDateTo() {
		return shoninDateTo;
	}

	/**
	 * @return
	 */
	public String getShozokuCd() {
		return shozokuCd;
	}

	/**
	 * @return
	 */
	public String getSystemNo() {
		return systemNo;
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
	public void setBunkasaimokuCd(String string) {
		bunkasaimokuCd = string;
	}

	/**
	 * @param string
	 */
	public void setJigyoCd(String string) {
		jigyoCd = string;
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
	 * @param strings
	 */
	public void setJokyoId(String[] strings) {
		jokyoId = strings;
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
	public void setKanrenShimei(String string) {
		kanrenShimei = string;
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
	public void setKantenNo(String string) {
		kantenNo = string;
	}

	/**
	 * @param string
	 */
	public void setKantenRyaku(String string) {
		kantenRyaku = string;
	}

	/**
	 * @param string
	 */
	public void setKeiName(String string) {
		keiName = string;
	}

	/**
	 * @param string
	 */
	public void setKeiNameNo(String string) {
		keiNameNo = string;
	}

	/**
	 * @param is
	 */
	public void setKekka2(int[] is) {
		kekka2 = is;
	}

	/**
	 * @param string
	 */
	public void setKenkyuNo(String string) {
		kenkyuNo = string;
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
	public void setNameRoMei(String string) {
		nameRoMei = string;
	}

	/**
	 * @param string
	 */
	public void setNameRoSei(String string) {
		nameRoSei = string;
	}

	/**
	 * @param string
	 */
	public void setNendo(String string) {
		nendo = string;
	}

	/**
	 * @param string
	 */
	public void setSakuseiDateFrom(String string) {
		sakuseiDateFrom = string;
	}

	/**
	 * @param string
	 */
	public void setSakuseiDateTo(String string) {
		sakuseiDateTo = string;
	}

	/**
	 * @param string
	 */
	public void setShinseishaId(String string) {
		shinseishaId = string;
	}

	/**
	 * @param string
	 */
	public void setShoninDateFrom(String string) {
		shoninDateFrom = string;
	}

	/**
	 * @param string
	 */
	public void setShoninDateTo(String string) {
		shoninDateTo = string;
	}

	/**
	 * @param string
	 */
	public void setShozokuCd(String string) {
		shozokuCd = string;
	}

	/**
	 * @param string
	 */
	public void setSystemNo(String string) {
		systemNo = string;
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
	public Collection getJigyoKubun() {
		return jigyoKubun;
	}

	/**
	 * @param collection
	 */
	public void setJigyoKubun(Collection collection) {
		jigyoKubun = collection;
	}

	/**
	 * @return
	 */
	public String[] getSaishinseiFlg() {
		return saishinseiFlg;
	}

	/**
	 * @param strings
	 */
	public void setSaishinseiFlg(String[] strings) {
		saishinseiFlg = strings;
	}

	/**
	 * @return
	 */
	public CombinedStatusSearchInfo getStatusSearchInfo() {
		return statusSearchInfo;
	}

	/**
	 * @param info
	 */
	public void setStatusSearchInfo(CombinedStatusSearchInfo info) {
		statusSearchInfo = info;
	}

	/**
	 * @return
	 */
	public Collection getTantoJigyoCd() {
		return tantoJigyoCd;
	}

	/**
	 * @param collection
	 */
	public void setTantoJigyoCd(Collection collection) {
		tantoJigyoCd = collection;
	}

	/**
	 * @return
	 */
	public String getBukyokuCd() {
		return bukyokuCd;
	}

	/**
	 * @param string
	 */
	public void setBukyokuCd(String string) {
		bukyokuCd = string;
	}
	
	/**
	 * @return
	 */
	public String[] getDelFlg() {
		return delFlg;
	}

	/**
	 * @param strings
	 */
	public void setDelFlg(String[] strings) {
		delFlg = strings;
	}

	/**
	 * @return �����ԍ���Ԃ�
	 */
	public String getSeiriNo(){
		return seiriNo;
	}
	
	/**
	 * @param str �����ԍ����Z�b�g����
	 */
	public void setSeiriNo(String str){
		seiriNo = str;
	}


	/**
	 * @return bunkatsuNo ��߂��܂��B
	 */
	public String getBunkatsuNo() {
		return bunkatsuNo;
	}


	/**
	 * @param bunkatsuNo �ݒ肷�� bunkatsuNo�B
	 */
	public void setBunkatsuNo(String bunkatsuNo) {
		this.bunkatsuNo = bunkatsuNo;
	}


	/**
	 * @return buntankinFlg ��߂��܂��B
	 */
	public String getBuntankinFlg() {
		return buntankinFlg;
	}


	/**
	 * @param buntankinFlg �ݒ肷�� buntankinFlg�B
	 */
	public void setBuntankinFlg(String buntankinFlg) {
		this.buntankinFlg = buntankinFlg;
	}


	/**
	 * @return chouseiFlg ��߂��܂��B
	 */
	public String getChouseiFlg() {
		return chouseiFlg;
	}


	/**
	 * @param chouseiFlg �ݒ肷�� chouseiFlg�B
	 */
	public void setChouseiFlg(String chouseiFlg) {
		this.chouseiFlg = chouseiFlg;
	}


	/**
	 * @return kaijiKiboFlg ��߂��܂��B
	 */
	public String getKaijiKiboFlg() {
		return kaijiKiboFlg;
	}


	/**
	 * @param kaijiKiboFlg �ݒ肷�� kaijiKiboFlg�B
	 */
	public void setKaijiKiboFlg(String kaijiKiboFlg) {
		this.kaijiKiboFlg = kaijiKiboFlg;
	}


	/**
	 * @return kenkyuKubun ��߂��܂��B
	 */
	public String getKenkyuKubun() {
		return kenkyuKubun;
	}


	/**
	 * @param kenkyuKubun �ݒ肷�� kenkyuKubun�B
	 */
	public void setKenkyuKubun(String kenkyuKubun) {
		this.kenkyuKubun = kenkyuKubun;
	}


	/**
	 * @return ryouikiNo ��߂��܂��B
	 */
	public String getRyouikiNo() {
		return ryouikiNo;
	}


	/**
	 * @param ryouikiNo �ݒ肷�� ryouikiNo�B
	 */
	public void setRyouikiNo(String ryouikiNo) {
		this.ryouikiNo = ryouikiNo;
	}


	/**
	 * @return shinseiFlgNo ��߂��܂��B
	 */
	public String getShinseiFlgNo() {
		return shinseiFlgNo;
	}


	/**
	 * @param shinseiFlgNo �ݒ肷�� shinseiFlgNo�B
	 */
	public void setShinseiFlgNo(String shinseiFlgNo) {
		this.shinseiFlgNo = shinseiFlgNo;
	}


	/**
	 * @return shinseiKubun ��߂��܂��B
	 */
	public String getShinseiKubun() {
		return shinseiKubun;
	}


	/**
	 * @param shinseiKubun �ݒ肷�� �B
	 */
	public void setShinseiKubun(String shinseiKubun) {
		this.shinseiKubun = shinseiKubun;
	}


	/**
	 * @return ryouikiKoumokuNo ��߂��܂��B
	 */
	public String getRyouikiKoumokuNo() {
		return ryouikiKoumokuNo;
	}


	/**
	 * @param ryouikiKoumokuNo �ݒ肷�� ryouikiKoumokuNo�B
	 */
	public void setRyouikiKoumokuNo(String ryouikiKoumokuNo) {
		this.ryouikiKoumokuNo = ryouikiKoumokuNo;
	}


    /**
     * @return Returns the shozokNm.
     */
    public String getShozokNm() {
        return shozokNm;
    }


    /**
     * @param shozokNm The shozokNm to set.
     */
    public void setShozokNm(String shozokNm) {
        this.shozokNm = shozokNm;
    }
	
}
