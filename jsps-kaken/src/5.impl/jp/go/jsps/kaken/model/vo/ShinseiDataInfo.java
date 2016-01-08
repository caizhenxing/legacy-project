/*======================================================================
 *    SYSTEM      : ���{�w�p�U����d�q�\���V�X�e���i�Ȋw������⏕���j
 *    Source name : ShinseiDataInfo.java
 *    Description : �\���f�[�^����ێ�����N���X�B
 *
 *    Author      : Admin
 *    Date        : 2003/07/16
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2003/07/16    v1.0        Admin          �V�K�쐬
 *====================================================================== 
 */
package jp.go.jsps.kaken.model.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.struts.action.ActionErrors;

import jp.go.jsps.kaken.model.vo.shinsei.DaihyouInfo;
import jp.go.jsps.kaken.model.vo.shinsei.KadaiInfo;
import jp.go.jsps.kaken.model.vo.shinsei.KanrenBunyaKenkyushaInfo;
import jp.go.jsps.kaken.model.vo.shinsei.KenkyuKeihiSoukeiInfo;

/**
 * �\���f�[�^����ێ�����N���X�B
 * 
 * ID RCSfile=$RCSfile: ShinseiDataInfo.java,v $
 * Revision=$Revision: 1.2 $
 * Date=$Date: 2007/07/09 02:55:34 $
 */
public class ShinseiDataInfo extends ShinseiDataPk {

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/** �\���ԍ� */
	private String uketukeNo;

	/** ����ID */
	private String jigyoId;
	
	/** �N�x */
	private String nendo;
	
	/** �� */
	private String kaisu;
	
	/** ���Ɩ� */
	private String jigyoName;
	
	/** �\����ID */
	private String shinseishaId;

	/** �\�����쐬�� */
	private Date sakuseiDate;

	/** �������ԏ��F�� */
	private Date shoninDate;
    
    //add start ly 2006/06/28
    /** �������ԏ��F��(mark) */
    private String shoninDateMark;
    //add end ly 2006/06/28

	/** �w�U�󗝓� */
	private Date jyuriDate;
	
	/** �w�U�󗝓�Flg */
	private String jyuriDateFlg;

	//�E�E�E�E�E�E�E�E�E�E
	
	/** ������\�ҏ��@*/
	private DaihyouInfo daihyouInfo = new DaihyouInfo();

	/** �����ۑ���@*/
	private KadaiInfo kadaiInfo = new KadaiInfo();

	/** �����o���� */
	private KenkyuKeihiSoukeiInfo kenkyuKeihiSoukeiInfo = new KenkyuKeihiSoukeiInfo();

	/** �����g�D�̌`�Ԕԍ� */
	private String soshikiKeitaiNo;
	
	/** �����g�D�̌`�� */
	private String soshikiKeitai;
	
	/** ���S���̗L�� */
	private String buntankinFlg;
	
	/** �����x���Ҍٗp�o�� */
	private String koyohi;
	
	/** �����Ґ� */
	private String kenkyuNinzu;
	
	//2005/03/30 �ǉ� ----------------------------��������
	//���R �����g�D�\�Ɍ������͎҂��ǉ����ꂽ����
	/** �������͎Ґ� */
	private String kyoryokushaNinzu;

	/** �����Ґ�(�J�E���g�p) */
	private int kenkyuNinzuInt = 1;

	/** �������͎Ґ�(�J�E���g�p) */
	private int kyoryokushaNinzuInt = 0;
	//2005/03/30 �ǉ� ----------------------------�����܂�
	
	/** ���@�ւ̕��S�Ґ� */
	private String takikanNinzu;
	
	/** �����g�D���i������\�ҋy�ѕ��S�҃��X�g�j */
	private List kenkyuSoshikiInfoList = new ArrayList();
	
	/** �V�K�p���敪 */
	private String shinseiKubun;
	
	/** �p�����̌����ۑ�ԍ� */
	private String kadaiNoKeizoku;
	
	/** �����v��ŏI�N�x�O�N�x�̉��� */
	private String shinseiFlgNo;
	
	/** �\���̗L�� */
	private String shinseiFlg;
	
	/** �ŏI�N�x�ۑ�ԍ� */
	private String kadaiNoSaisyu;
	
	/** �J����]�̗L���ԍ� */
	private String kaijikiboFlgNo;
	
	/** �J����]�̗L�� */
	private String kaijiKiboFlg;
	
	/** �C�O����R�[�h */
	private String kaigaibunyaCd;
	
	/** �C�O���얼�� */
	private String kaigaibunyaName;
	
	/** �C�O���얼�̗��� */
	private String kaigaibunyaNameRyaku;
	
	//�E�E�E�E�E�E�E�E�E�E
	
	/** �֘A���쌤���ҏ�� */
	private KanrenBunyaKenkyushaInfo[] kanrenBunyaKenkyushaInfo = makeKanrenBunyaKenkyushaInfoArray(3);
	
	//�E�E�E�E�E�E�E�E�E�E

	/** XML�̊i�[�p�X */
	private String xmlPath;

	/** PDF�̊i�[�p�X */
	private String pdfPath;

	/** �󗝌��� */
	private String juriKekka;

	/** �󗝌��ʔ��l */
	private String juriBiko;
	
	/** �󗝐����ԍ� */
	private String seiriNo;

	/** ���E���̊i�[�p�X */
	private String suisenshoPath;

	/** �P���R������(ABC) */
	private String kekka1Abc;
	
	/** �P���R������(�_��) */
	private String kekka1Ten;
	
	/** �P���R�����ʁi�_�����j */
	private String kekka1TenSorted;	

	/** �P���R�����l */
	private String shinsa1Biko;

	/** �Q���R������ */
	private String kekka2;

	/** ���o��i�w�U���́j */
	private String souKehi;

	/** ���N�x�o��i�w�U���́j */
	private String shonenKehi;

	/** �Ɩ��S���ҋL���� */
	private String shinsa2Biko;

	/** �폜�t���O */
	private String delFlg;

	//----- �ȉ��͎��ƊǗ��e�[�u����� -----
	/** ����CD */
	private String jigyoCd;
	
	/** ���ƔN�x�i����Q�P�^�j*/
	private String nendoSeireki;
	
// 2006/02/13 �ǉ�Start
	/** �w�U��t���ԁi�I���j */
	private Date uketukekikanEnd;
// 2006/02/13 Nae
	
	//----- �Y�t�t�@�C�� -----
	/** �Y�t�t�@�C�����z�� */
	private TenpuFileInfo[] tenpuFileInfos;
	
	//2005/04/19 �ǉ� --------------------------��������
	//���R ��茤���̏ꍇ�A�a������񂪕K�v�Ȃ���
    /** �a���� */
	private Date birthDay;
	//2005/04/19 �ǉ� --------------------------�����܂�
	
	//2006/02/08 �ǉ� --------------------------��������
	//���R ���ʌ������i��i��茤�������j�̏ꍇ�A�����Җ�����ؓ���񂪕K�v�Ȃ���
    /** �����Җ�����ؓ� */
	private Date meiboDate;

    /** �����Җ�����ؓ�(�a��) */
	private String meiboDateWareki;
    //Nae �ǉ� --------------------------�����܂�
	
    // 2006/02/09 Start
	// ���R�͐\�����i�t���O�A�̗p�N�����A�Ζ����Ԑ��A���ʌ�������������z�A���i�擾�N�����A���i�擾�O�@�֖��A��x���J�n���A��x���I������ǉ�����
	/** �\�����i�t���O */
	private String ouboShikaku;
	
	/** �̗p�N���� */
	private Date saiyoDate;
	
	/** �Ζ����Ԑ� */
	private String kinmuHour;

	/** ���ʌ�������������z */
	private String naiyakugaku;
	
	/** ���i�擾�N���� */
	private Date sikakuDate;
	
	/** ���i�擾�O�@�֖� */
	private String syuTokumaeKikan;
	
	/** ��x���J�n�� */
	private Date ikukyuStartDate;
	
	/** ��x���I���� */
	private Date ikukyuEndDate;
	// Nae End

	/** �R����]�̈於 */
	private String shinsaRyoikiName;

	/** �R����]�̈�R�[�h */
	private String shinsaRyoikiCd;

	// 20060525 Start ����̈�ǉ��̂��ߍ��ڂ�ǉ�
	/** �v�挤���E���匤���E�I�������̈�敪 */
	private String kenkyuKubun;

	/** �啝�ȕύX�𔺂������ۑ� */
	private String changeFlg = "0";

	/** �̈�ԍ� */
	private String ryouikiNo;

	/** �̈旪�̖� */
	private String ryouikiRyakuName;

	/** �������ڔԍ� */
	private String ryouikiKoumokuNo;

	/** ������ */
	private String chouseiFlg = "0";
	// Horikoshi End

//	 20050725
    /** �L��(�R�[�h����A���t�@�x�b�g�Ȃ�) **/
	private String kigou;

    /** �L�[���[�h(����) **/
	private String keyName;

    /** �זڕ\�ȊO�̃L�[���[�h(����) **/
	private String keyOtherName;
// Horikoshi

    /** �\����ID */
    private String jokyoId;

    // 2006/06/27 LY add 
    /** �\����ID�z�� */
    private String[] jokyoIds;
    
    /** �Đ\���t���O */
    private String saishinseiFlg;
    
//  2006/06/20 by jzx add start
    /** �̈��\�Ҋm��� */
    private Date ryoikiKakuteiDate;
    
    /** �̈��\�Ҋm���Flg */
    private String ryoikiKakuteiDateFlg;
//  2006/06/20 by jzx add end 
    
//2006/06/30 �c�@�ǉ���������
    /** URL */
    private String url;
//2006/06/30�@�c�@�ǉ������܂�
    
//2007/02/02 �c�@�ǉ���������
    /** ���ʌ����������ۑ�ԍ�-�N�x */
    private String shoreihiNoNendo;
    
    /** ���ʌ����������ۑ�ԍ�-�����ԍ� */
    private String shoreihiNoSeiri = "";
//2007/02/02�@�c�@�ǉ������܂�    
 
//  <!-- ADD�@START 2007/07/06 BIS ���� -->    
//  H19���S�d�q���y�ѐ��x����.
//  ���͂��������g�D�ɃG���[������ꍇ�A�u��������́v��ʂŃG���[���b�Z�[�W��\�����A�u�����g�D�\�v��ʂŃG���[�ɂȂ鍀�ڂ̔w�i�F��ύX����B	
    /** �G���[���b�Z�W�[ */
    private HashMap errorsMap;
//	<!-- ADD�@END�@ 2007/07/06 BIS ���� -->    
	//�E�E�E�E�E�E�E�E�E�E

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------
    
    /**
	 * �R���X�g���N�^�B
	 */
	public ShinseiDataInfo() {
		super();
	}
		
	//---------------------------------------------------------------------
	// Methods
	//---------------------------------------------------------------------
	/**
	 * �֘A���쌤���ҏ���������
	 * @param arrayLength
	 * @return
	 */
	private KanrenBunyaKenkyushaInfo[] makeKanrenBunyaKenkyushaInfoArray(int arrayLength){
		KanrenBunyaKenkyushaInfo[] infoArray = new KanrenBunyaKenkyushaInfo[arrayLength];
		for(int i=0; i<arrayLength; i++){
			infoArray[i] = new KanrenBunyaKenkyushaInfo();
		}
		return infoArray;
	}	

	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------
	
	/**
     * ���S���̗L�����擾
	 * @return ���S���̗L��
	 */
	public String getBuntankinFlg() {
		return buntankinFlg;
	}

    /**
     * ���S���̗L����ݒ�
     * @param string ���S���̗L��
     */
    public void setBuntankinFlg(String string) {
        buntankinFlg = string;
    }

	/**
     * ������\�ҏ����擾
	 * @return ������\�ҏ��
	 */
	public DaihyouInfo getDaihyouInfo() {
		return daihyouInfo;
	}

    /**
     * ������\�ҏ���ݒ�
     * @param info ������\�ҏ��
     */
    public void setDaihyouInfo(DaihyouInfo info) {
        daihyouInfo = info;
    }

	/**
     * �폜�t���O���擾
	 * @return �폜�t���O
	 */
	public String getDelFlg() {
		return delFlg;
	}

    /**
     * �폜�t���O��ݒ�
     * @param string �폜�t���O
     */
    public void setDelFlg(String string) {
        delFlg = string;
    }

	/**
     * ����CD���擾
	 * @return ����CD
	 */
	public String getJigyoCd() {
		return jigyoCd;
	}

    /**
     * ����CD��ݒ�
     * @param string ����CD
     */
    public void setJigyoCd(String string) {
        jigyoCd = string;
    }

	/**
     * ����ID���擾
	 * @return ����ID
	 */
	public String getJigyoId() {
		return jigyoId;
	}

    /**
     * ����ID��ݒ�
     * @param string ����ID
     */
    public void setJigyoId(String string) {
        jigyoId = string;
    }

	/**
     * ���Ɩ����擾
	 * @return ���Ɩ�
	 */
	public String getJigyoName() {
		return jigyoName;
	}

    /**
     * ���Ɩ���ݒ�
     * @param string ���Ɩ�
     */
    public void setJigyoName(String string) {
        jigyoName = string;
    }

	/**
     * �\����ID���擾
	 * @return �\����ID
	 */
	public String getJokyoId() {
		return jokyoId;
	}

    /**
     * �\����ID��ݒ�
     * @param string �\����ID
     */
    public void setJokyoId(String string) {
        jokyoId = string;
    }

	/**
     * �󗝌��ʔ��l���擾
	 * @return �󗝌��ʔ��l
	 */
	public String getJuriBiko() {
		return juriBiko;
	}

    /**
     * �󗝌��ʔ��l��ݒ�
     * @param string �󗝌��ʔ��l
     */
    public void setJuriBiko(String string) {
        juriBiko = string;
    }

	/**
     * �󗝌��ʂ��擾
	 * @return �󗝌���
	 */
	public String getJuriKekka() {
		return juriKekka;
	}

    /**
     * �󗝌��ʂ�ݒ�
     * @param string �󗝌���
     */
    public void setJuriKekka(String string) {
        juriKekka = string;
    }

	/**
     * �w�U�󗝓����擾
	 * @return �w�U�󗝓�
	 */
	public Date getJyuriDate() {
		return jyuriDate;
	}

    /**
     * �w�U�󗝓���ݒ�
     * @param date �w�U�󗝓�
     */
    public void setJyuriDate(Date date) {
        jyuriDate = date;
    }

	/**
     * �����ۑ�����擾
	 * @return �����ۑ���
	 */
	public KadaiInfo getKadaiInfo() {
		return kadaiInfo;
	}

    /**
     * �����ۑ����ݒ�
     * @param info �����ۑ���
     */
    public void setKadaiInfo(KadaiInfo info) {
        kadaiInfo = info;
    }

	/**
     * �p�����̌����ۑ�ԍ����擾
	 * @return �p�����̌����ۑ�ԍ�
	 */
	public String getKadaiNoKeizoku() {
		return kadaiNoKeizoku;
	}

    /**
     * �p�����̌����ۑ�ԍ���ݒ�
     * @param string �p�����̌����ۑ�ԍ�
     */
    public void setKadaiNoKeizoku(String string) {
        kadaiNoKeizoku = string;
    }

	/**
     * �ŏI�N�x�ۑ�ԍ����擾
	 * @return �ŏI�N�x�ۑ�ԍ�
	 */
	public String getKadaiNoSaisyu() {
		return kadaiNoSaisyu;
	}

    /**
     * �ŏI�N�x�ۑ�ԍ���ݒ�
     * @param string �ŏI�N�x�ۑ�ԍ�
     */
    public void setKadaiNoSaisyu(String string) {
        kadaiNoSaisyu = string;
    }

	/**
     * �J����]�̗L�����擾
	 * @return �J����]�̗L��
	 */
	public String getKaijiKiboFlg() {
		return kaijiKiboFlg;
	}

    /**
     * �J����]�̗L����ݒ�
     * @param string �J����]�̗L��
     */
    public void setKaijiKiboFlg(String string) {
        kaijiKiboFlg = string;
    }

	/**
     * �J����]�̗L���ԍ����擾
	 * @return �J����]�̗L���ԍ�
	 */
	public String getKaijikiboFlgNo() {
		return kaijikiboFlgNo;
	}

    /**
     * �J����]�̗L���ԍ���ݒ�
     * @param string �J����]�̗L���ԍ�
     */
    public void setKaijikiboFlgNo(String string) {
        kaijikiboFlgNo = string;
    }

	/**
     * �񐔂��擾
	 * @return ��
	 */
	public String getKaisu() {
		return kaisu;
	}

    /**
     * �񐔂�ݒ�
     * @param string ��
     */
    public void setKaisu(String string) {
        kaisu = string;
    }

	/**
     * �֘A���쌤���ҏ����擾
	 * @return �֘A���쌤���ҏ��
	 */
	public KanrenBunyaKenkyushaInfo[] getKanrenBunyaKenkyushaInfo() {
		return kanrenBunyaKenkyushaInfo;
	}

    /**
     * �֘A���쌤���ҏ���ݒ�
     * @param infos �֘A���쌤���ҏ��
     */
    public void setKanrenBunyaKenkyushaInfo(KanrenBunyaKenkyushaInfo[] infos) {
        kanrenBunyaKenkyushaInfo = infos;
    }

	/**
     * �P���R������(ABC)���擾
	 * @return �P���R������(ABC)
	 */
	public String getKekka1Abc() {
		return kekka1Abc;
	}

    /**
     * �P���R������(ABC)��ݒ�
     * @param string �P���R������(ABC)
     */
    public void setKekka1Abc(String string) {
        kekka1Abc = string;
    }

	/**
     * �P���R������(�_��)���擾
	 * @return �P���R������(�_��)
	 */
	public String getKekka1Ten() {
		return kekka1Ten;
	}

    /**
     * �P���R������(�_��)��ݒ�
     * @param string �P���R������(�_��)
     */
    public void setKekka1Ten(String string) {
        kekka1Ten = string;
    }

	/**
     * �Q���R�����ʂ��擾
	 * @return �Q���R������
	 */
	public String getKekka2() {
		return kekka2;
	}

    /**
     * �Q���R�����ʂ�ݒ�
     * @param string �Q���R������
     */
    public void setKekka2(String string) {
        kekka2 = string;
    }

	/**
     * �����o������擾
	 * @return �����o����
	 */
	public KenkyuKeihiSoukeiInfo getKenkyuKeihiSoukeiInfo() {
		return kenkyuKeihiSoukeiInfo;
	}

	/**
     * �����Ґ����擾
	 * @return �����Ґ�
	 */
	public String getKenkyuNinzu() {
		return kenkyuNinzu;
	}

    /**
     * �����Ґ���ݒ�
     * @param string �����Ґ�
     */
    public void setKenkyuNinzu(String string) {
        kenkyuNinzu = string;
    }

	/**
     * �����x���Ҍٗp�o����擾
	 * @return �����x���Ҍٗp�o��
	 */
	public String getKoyohi() {
		return koyohi;
	}

    /**
     * �����x���Ҍٗp�o���ݒ�
     * @param string �����x���Ҍٗp�o��
     */
    public void setKoyohi(String string) {
        koyohi = string;
    }

	/**
     * �N�x���擾
	 * @return �N�x
	 */
	public String getNendo() {
		return nendo;
	}

    /**
     * �N�x��ݒ�
     * @param string �N�x
     */
    public void setNendo(String string) {
        nendo = string;
    }

	/**
     * ���ƔN�x�i����Q�P�^�j���擾
	 * @return ���ƔN�x�i����Q�P�^�j
	 */
	public String getNendoSeireki() {
		return nendoSeireki;
	}

    /**
     * ���ƔN�x�i����Q�P�^�j��ݒ�
     * @param string ���ƔN�x�i����Q�P�^�j
     */
    public void setNendoSeireki(String string) {
        nendoSeireki = string;
    }

	/**
     * PDF�̊i�[�p�X���擾
	 * @return PDF�̊i�[�p�X
	 */
	public String getPdfPath() {
		return pdfPath;
	}

    /**
     * PDF�̊i�[�p�X��ݒ�
     * @param string PDF�̊i�[�p�X
     */
    public void setPdfPath(String string) {
        pdfPath = string;
    }

	/**
     * �Đ\���t���O���擾
	 * @return �Đ\���t���O
	 */
	public String getSaishinseiFlg() {
		return saishinseiFlg;
	}

    /**
     * �Đ\���t���O��ݒ�
     * @param string �Đ\���t���O
     */
    public void setSaishinseiFlg(String string) {
        saishinseiFlg = string;
    }

	/**
     * �\�����쐬�����擾
	 * @return �\�����쐬��
	 */
	public Date getSakuseiDate() {
		return sakuseiDate;
	}

    /**
     * �\�����쐬����ݒ�
     * @param date �\�����쐬��
     */
    public void setSakuseiDate(Date date) {
        sakuseiDate = date;
    }

	/**
     * �P���R�����l���擾
	 * @return �P���R�����l
	 */
	public String getShinsa1Biko() {
		return shinsa1Biko;
	}

    /**
     * �P���R�����l��ݒ�
     * @param string �P���R�����l
     */
    public void setShinsa1Biko(String string) {
        shinsa1Biko = string;
    }

	/**
     * �Ɩ��S���ҋL�������擾
	 * @return �Ɩ��S���ҋL����
	 */
	public String getShinsa2Biko() {
		return shinsa2Biko;
	}

    /**
     * �Ɩ��S���ҋL������ݒ�
     * @param string �Ɩ��S���ҋL����
     */
    public void setShinsa2Biko(String string) {
        shinsa2Biko = string;
    }

	/**
     * �\���̗L�����擾
	 * @return �\���̗L��
	 */
	public String getShinseiFlg() {
		return shinseiFlg;
	}

    /**
     * �\���̗L����ݒ�
     * @param string �\���̗L��
     */
    public void setShinseiFlg(String string) {
        shinseiFlg = string;
    }

	/**
     * �����v��ŏI�N�x�O�N�x�̉�����擾
	 * @return �����v��ŏI�N�x�O�N�x�̉���
	 */
	public String getShinseiFlgNo() {
		return shinseiFlgNo;
	}

    /**
     * �����v��ŏI�N�x�O�N�x�̉����ݒ�
     * @param string �����v��ŏI�N�x�O�N�x�̉���
     */
    public void setShinseiFlgNo(String string) {
        shinseiFlgNo = string;
    }

	/**
     * �V�K�p���敪���擾
	 * @return �V�K�p���敪
	 */
	public String getShinseiKubun() {
		return shinseiKubun;
	}

    /**
     * �V�K�p���敪��ݒ�
     * @param string �V�K�p���敪
     */
    public void setShinseiKubun(String string) {
        shinseiKubun = string;
    }

	/**
     * �\����ID���擾
	 * @return �\����ID
	 */
	public String getShinseishaId() {
		return shinseishaId;
	}

    /**
     * �\����ID��ݒ�
     * @param string �\����ID
     */
    public void setShinseishaId(String string) {
        shinseishaId = string;
    }

	/**
     * ���N�x�o��i�w�U���́j���擾
	 * @return ���N�x�o��i�w�U���́j
	 */
	public String getShonenKehi() {
		return shonenKehi;
	}

    /**
     * ���N�x�o��i�w�U���́j��ݒ�
     * @param string ���N�x�o��i�w�U���́j
     */
    public void setShonenKehi(String string) {
        shonenKehi = string;
    }

	/**
     * �������ԏ��F�����擾
	 * @return �������ԏ��F��
	 */
	public Date getShoninDate() {
		return shoninDate;
	}

    /**
     * �������ԏ��F����ݒ�
     * @param date �������ԏ��F��
     */
    public void setShoninDate(Date date) {
        shoninDate = date;
    }

	/**
     * �����g�D�̌`�Ԃ��擾
	 * @return �����g�D�̌`��
	 */
	public String getSoshikiKeitai() {
		return soshikiKeitai;
	}

	/**
     * �����g�D�̌`�Ԕԍ����擾
	 * @return �����g�D�̌`�Ԕԍ�
	 */
	public String getSoshikiKeitaiNo() {
		return soshikiKeitaiNo;
	}

    /**
     * �����g�D�̌`�Ԕԍ���ݒ�
     * @param string �����g�D�̌`�Ԕԍ�
     */
    public void setSoshikiKeitaiNo(String string) {
        soshikiKeitaiNo = string;
    }

	/**
     * ���o��i�w�U���́j���擾
	 * @return ���o��i�w�U���́j
	 */
	public String getSouKehi() {
		return souKehi;
	}

    /**
     * ���o��i�w�U���́j��ݒ�
     * @param string ���o��i�w�U���́j
     */
    public void setSouKehi(String string) {
        souKehi = string;
    }

	/**
     * ���E���̊i�[�p�X���擾
	 * @return ���E���̊i�[�p�X
	 */
	public String getSuisenshoPath() {
		return suisenshoPath;
	}

    /**
     * ���E���̊i�[�p�X��ݒ�
     * @param string ���E���̊i�[�p�X
     */
    public void setSuisenshoPath(String string) {
        suisenshoPath = string;
    }

	/**
     * ���@�ւ̕��S�Ґ����擾
	 * @return ���@�ւ̕��S�Ґ�
	 */
	public String getTakikanNinzu() {
		return takikanNinzu;
	}

    /**
     * ���@�ւ̕��S�Ґ���ݒ�
     * @param string ���@�ւ̕��S�Ґ�
     */
    public void setTakikanNinzu(String string) {
        takikanNinzu = string;
    }

	/**
     * �Y�t�t�@�C�����z����擾
	 * @return �Y�t�t�@�C�����z��
	 */
	public TenpuFileInfo[] getTenpuFileInfos() {
		return tenpuFileInfos;
	}

    /**
     * �Y�t�t�@�C�����z���ݒ�
     * @param infos �Y�t�t�@�C�����z��
     */
    public void setTenpuFileInfos(TenpuFileInfo[] infos) {
        tenpuFileInfos = infos;
    }

	/**
     * �\���ԍ����擾
	 * @return �\���ԍ�
	 */
	public String getUketukeNo() {
		return uketukeNo;
	}

    /**
     * �\���ԍ���ݒ�
     * @param string �\���ԍ�
     */
    public void setUketukeNo(String string) {
        uketukeNo = string;
    }

	/**
     * XML�̊i�[�p�X���擾
	 * @return XML�̊i�[�p�X
	 */
	public String getXmlPath() {
		return xmlPath;
	}

    /**
     * XML�̊i�[�p�X��ݒ�
     * @param string XML�̊i�[�p�X
     */
    public void setXmlPath(String string) {
        xmlPath = string;
    }

	/**
     * �����o�����ݒ�
	 * @param info �����o����
	 */
	public void setKenkyuKeihiSoukeiInfo(KenkyuKeihiSoukeiInfo info) {
		kenkyuKeihiSoukeiInfo = info;
	}

	/**
     * �����g�D�̌`�Ԃ�ݒ�
	 * @param string �����g�D�̌`��
	 */
	public void setSoshikiKeitai(String string) {
		soshikiKeitai = string;
	}

	/**
     * �����g�D���i������\�ҋy�ѕ��S�҃��X�g�j���擾
	 * @return �����g�D���i������\�ҋy�ѕ��S�҃��X�g�j
	 */
	public List getKenkyuSoshikiInfoList() {
		return kenkyuSoshikiInfoList;
	}

	/**
     * �����g�D���i������\�ҋy�ѕ��S�҃��X�g�j��ݒ�
	 * @param list �����g�D���i������\�ҋy�ѕ��S�҃��X�g�j
	 */
	public void setKenkyuSoshikiInfoList(List list) {
		kenkyuSoshikiInfoList = list;
	}

	/**
     * �P���R�����ʁi�_�����j���擾
	 * @return �P���R�����ʁi�_�����j
	 */
	public String getKekka1TenSorted() {
		return kekka1TenSorted;
	}

	/**
     * �P���R�����ʁi�_�����j��ݒ�
	 * @param string �P���R�����ʁi�_�����j
	 */
	public void setKekka1TenSorted(String string) {
		kekka1TenSorted = string;
	}

	/**
     * �C�O����R�[�h���擾
	 * @return �C�O����R�[�h
	 */
	public String getKaigaibunyaCd() {
		return kaigaibunyaCd;
	}

    /**
     * �C�O����R�[�h��ݒ�
     * @param string �C�O����R�[�h
     */
    public void setKaigaibunyaCd(String string) {
        kaigaibunyaCd = string;
    }

	/**
     * �C�O���얼�̂��擾
	 * @return �C�O���얼��
	 */
	public String getKaigaibunyaName() {
		return kaigaibunyaName;
	}

    /**
     * �C�O���얼�̂�ݒ�
     * @param string �C�O���얼��
     */
    public void setKaigaibunyaName(String string) {
        kaigaibunyaName = string;
    }

	/**
     * �C�O���얼�̗��̂��擾
	 * @return �C�O���얼�̗���
	 */
	public String getKaigaibunyaNameRyaku() {
		return kaigaibunyaNameRyaku;
	}

	/**
     * �C�O���얼�̗��̂�ݒ�
	 * @param string �C�O���얼�̗���
	 */
	public void setKaigaibunyaNameRyaku(String string) {
		kaigaibunyaNameRyaku = string;
	}

	//2005/03/30 �ǉ� ----------------------------��������
	//���R �����g�D�\�Ɍ������͎҂��ǉ����ꂽ����
	/**
     * �������͎Ґ����擾
	 * @return Returns �������͎Ґ�
	 */
	public String getKyoryokushaNinzu() {
		return kyoryokushaNinzu;
	}

	/**
     * �������͎Ґ���ݒ�
	 * @param kyoryokushaNinzu �������͎Ґ�
	 */
	public void setKyoryokushaNinzu(String kyoryokushaNinzu) {
		this.kyoryokushaNinzu = kyoryokushaNinzu;
	}

	/**
     * �����Ґ�(�J�E���g�p)���擾
	 * @return Returns �����Ґ�(�J�E���g�p)
	 */
	public int getKenkyuNinzuInt() {
		return kenkyuNinzuInt;
	}

	/**
     * �����Ґ�(�J�E���g�p)��ݒ�
	 * @param kenkyuNinzuInt �����Ґ�(�J�E���g�p)
	 */
	public void setKenkyuNinzuInt(int kenkyuNinzuInt) {
		this.kenkyuNinzuInt = kenkyuNinzuInt;
	}

	/**
     * �������͎Ґ�(�J�E���g�p)���擾
	 * @return Returns �������͎Ґ�(�J�E���g�p)
	 */
	public int getKyoryokushaNinzuInt() {
		return kyoryokushaNinzuInt;
	}

	/**
     * �������͎Ґ�(�J�E���g�p)��ݒ�
	 * @param kyoryokushaNinzuInt �������͎Ґ�(�J�E���g�p)
	 */
	public void setKyoryokushaNinzuInt(int kyoryokushaNinzuInt) {
		this.kyoryokushaNinzuInt = kyoryokushaNinzuInt;
	}
	//2005/03/30 �ǉ� ----------------------------�����܂�

	/**
     * �a�������擾
	 * @return Returns �a����
	 */
	public Date getBirthDay() {
		return birthDay;
	}

	/**
     * �a������ݒ�
	 * @param birthDay �a����
	 */
	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}

// 210050526 Start ����̈�p�̍��ڒǉ��̂��߃v���p�e�B���쐬

/* �ϐ� */
    /**
     * �v�挤���E���匤���E�I�������̈�敪���擾
     * @return�@�v�挤���E���匤���E�I�������̈�敪
     */
	public String getKenkyuKubun() {
        return kenkyuKubun;
    }

    /**
     * �v�挤���E���匤���E�I�������̈�敪��ݒ�
     * @param kenkyuKubun �v�挤���E���匤���E�I�������̈�敪
     */
	public void setKenkyuKubun(String kenkyuKubun){
        this.kenkyuKubun = kenkyuKubun;
    }

    /**
     * �啝�ȕύX�𔺂������ۑ���擾
     * @return�@�啝�ȕύX�𔺂������ۑ�
     */
	public String getChangeFlg() {
        return changeFlg;
    }

    /**
     * �啝�ȕύX�𔺂������ۑ��ݒ�
     * @param changeFlg �啝�ȕύX�𔺂������ۑ�
     */
	public void setChangeFlg(String changeFlg){
        this.changeFlg = changeFlg;
    }

    /**
     * �̈�ԍ����擾
     * @return �̈�ԍ�
     */
	public String getRyouikiNo(){
        return ryouikiNo;
    }

    /**
     * �̈�ԍ���ݒ�
     * @param string �̈�ԍ�
     */
	public void setRyouikiNo(String string) {
        ryouikiNo = string;
    }

    /**
     * �̈旪�̖����擾
     * @return �̈旪�̖�
     */
	public String getRyouikiRyakuName() {
        return ryouikiRyakuName;
    }

    /**
     * �̈旪�̖���ݒ�
     * @param string �̈旪�̖�
     */
	public void setRyouikiRyakuName(String string){
        ryouikiRyakuName = string;
    }

    /**
     * �������ڔԍ����擾
     * @return �������ڔԍ�
     */
	public String getRyouikiKoumokuNo() {
        return ryouikiKoumokuNo;
    }

    /**
     * �������ڔԍ���ݒ�
     * @param string �������ڔԍ�
     */
	public void setRyouikiKoumokuNo(String string){
        ryouikiKoumokuNo = string;
    }

    /**
     * �����ǂ��擾
     * @return ������
     */
	public String getChouseiFlg() {
        return chouseiFlg;
    }

    /**
     * �����ǂ�ݒ�
     * @param string ������
     */
	public void setChouseiFlg(String string){
        chouseiFlg = string;
    }
// Horikoshi End

//	 20050725 �L�[���[�h�ǉ��̂���
    /**
     * �L�����擾
     * @return �L��
     */
	public String getKigou() {
		return kigou;
	}

    /**
     * �L����ݒ�
     * @param kigou �L��
     */
	public void setKigou(String kigou) {
		this.kigou = kigou;
	}

    /**
     * �L�[���[�h(����)���擾
     * @return �L�[���[�h(����)
     */
	public String getKeyName() {
		return keyName;
	}

    /**
     * �L�[���[�h(����)��ݒ�
     * @param keyName �L�[���[�h(����)
     */
	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

    /**
     * �זڕ\�ȊO�̃L�[���[�h(����)���擾
     * @return �זڕ\�ȊO�̃L�[���[�h(����)
     */
	public String getKeyOtherName() {
		return keyOtherName;
	}

    /**
     * �זڕ\�ȊO�̃L�[���[�h(����)��ݒ�
     * @param keyOtherName �זڕ\�ȊO�̃L�[���[�h(����)
     */
	public void setKeyOtherName(String keyOtherName) {
		this.keyOtherName = keyOtherName;
	}
// Horikoshi

	/**
     * �󗝐����ԍ����擾
	 * @return �����ԍ�
	 */
	public String getSeiriNo(){
		return seiriNo;
	}
	
	/**
     * �󗝐����ԍ���ݒ�
	 * @param str �����ԍ�
	 */
	public void setSeiriNo(String str){
		seiriNo = str;
	}

	/**
     * �����Җ�����ؓ����擾
	 * @return Returns �����Җ�����ؓ�
	 */
	public Date getMeiboDate() {
		return meiboDate;
	}

	/**
     * �����Җ�����ؓ���ݒ�
	 * @param meiboDate �����Җ�����ؓ�
	 */
	public void setMeiboDate(Date meiboDate) {
		this.meiboDate = meiboDate;
	}

	/**
     * �����Җ�����ؓ�(�a��)���擾
	 * @return Returns �����Җ�����ؓ�(�a��)
	 */
	public String getMeiboDateWareki() {
		return meiboDateWareki;
	}

	/**
     * �����Җ�����ؓ�(�a��)��ݒ�
	 * @param meiboDateWareki �����Җ�����ؓ�(�a��)
	 */
	public void setMeiboDateWareki(String meiboDateWareki) {
		this.meiboDateWareki = meiboDateWareki;
	}

	/**
     * ��x���I�������擾
	 * @return Returns ��x���I����
	 */
	public Date getIkukyuEndDate() {
		return ikukyuEndDate;
	}

	/**
     * ��x���I������ݒ�
	 * @param ikukyuEndDate ��x���I����
	 */
	public void setIkukyuEndDate(Date ikukyuEndDate) {
		this.ikukyuEndDate = ikukyuEndDate;
	}

	/**
     * ��x���J�n�����擾
	 * @return Returns ��x���J�n��
	 */
	public Date getIkukyuStartDate() {
		return ikukyuStartDate;
	}

	/**
     * ��x���J�n����ݒ�
	 * @param ikukyuStartDate ��x���J�n��
	 */
	public void setIkukyuStartDate(Date ikukyuStartDate) {
		this.ikukyuStartDate = ikukyuStartDate;
	}

	/**
     * �Ζ����Ԑ����擾
	 * @return Returns �Ζ����Ԑ�
	 */
	public String getKinmuHour() {
		return kinmuHour;
	}

	/**
     * �Ζ����Ԑ���ݒ�
	 * @param kinmuHour �Ζ����Ԑ�
	 */
	public void setKinmuHour(String kinmuHour) {
		this.kinmuHour = kinmuHour;
	}

	/**
     * ���ʌ�������������z���擾
	 * @return Returns ���ʌ�������������z
	 */
	public String getNaiyakugaku() {
		return naiyakugaku;
	}

	/**
     * ���ʌ�������������z��ݒ�
	 * @param naiyakugaku ���ʌ�������������z
	 */
	public void setNaiyakugaku(String naiyakugaku) {
		this.naiyakugaku = naiyakugaku;
	}

	/**
     * �\�����i�t���O���擾
	 * @return Returns �\�����i�t���O
	 */
	public String getOuboShikaku() {
		return ouboShikaku;
	}

	/**
     * �\�����i�t���O��ݒ�
	 * @param ouboShikaku �\�����i�t���O
	 */
	public void setOuboShikaku(String ouboShikaku) {
		this.ouboShikaku = ouboShikaku;
	}

	/**
     * �̗p�N�������擾
	 * @return Returns �̗p�N����
	 */
	public Date getSaiyoDate() {
		return saiyoDate;
	}

	/**
     * �̗p�N������ݒ�
	 * @param saiyoDate �̗p�N����
	 */
	public void setSaiyoDate(Date saiyoDate) {
		this.saiyoDate = saiyoDate;
	}

	/**
     * ���i�擾�N�������擾
	 * @return Returns ���i�擾�N����
	 */
	public Date getSikakuDate() {
		return sikakuDate;
	}

	/**
     * ���i�擾�N������ݒ�
	 * @param sikakuDate ���i�擾�N����
	 */
	public void setSikakuDate(Date sikakuDate) {
		this.sikakuDate = sikakuDate;
	}

	/**
     * ���i�擾�O�@�֖����擾
	 * @return Returns ���i�擾�O�@�֖�
	 */
	public String getSyuTokumaeKikan() {
		return syuTokumaeKikan;
	}

	/**
     * ���i�擾�O�@�֖���ݒ�
	 * @param syuTokumaeKikan ���i�擾�O�@�֖�
	 */
	public void setSyuTokumaeKikan(String syuTokumaeKikan) {
		this.syuTokumaeKikan = syuTokumaeKikan;
	}

	/**
     * �w�U��t���ԁi�I���j���擾
	 * @return Returns �w�U��t���ԁi�I���j
	 */
	public Date getUketukekikanEnd() {
		return uketukekikanEnd;
	}

	/**
     * �w�U��t���ԁi�I���j��ݒ�
	 * @param uketukekikanEnd �w�U��t���ԁi�I���j
	 */
	public void setUketukekikanEnd(Date uketukekikanEnd) {
		this.uketukekikanEnd = uketukekikanEnd;
	}

	/**
     * �R����]�̈�R�[�h���擾
	 * @return Returns �R����]�̈�R�[�h
	 */
	public String getShinsaRyoikiCd() {
		return shinsaRyoikiCd;
	}

	/**
     * �R����]�̈�R�[�h��ݒ�
	 * @param shinsaRyoikiCd �R����]�̈�R�[�h
	 */
	public void setShinsaRyoikiCd(String shinsaRyoikiCd) {
		this.shinsaRyoikiCd = shinsaRyoikiCd;
	}

	/**
     * �R����]�̈於���擾
	 * @return Returns �R����]�̈於
	 */
	public String getShinsaRyoikiName() {
		return shinsaRyoikiName;
	}

	/**
     * �R����]�̈於��ݒ�
	 * @param shinsaryoikiName �R����]�̈於
	 */
	public void setShinsaRyoikiName(String shinsaryoikiName) {
		this.shinsaRyoikiName = shinsaryoikiName;
	}

    /**
     * �̈��\�Ҋm������擾
     * @return Returns �̈��\�Ҋm���
     */
    public Date getRyoikiKakuteiDate() {
        return ryoikiKakuteiDate;
    }

    /**
     * �̈��\�Ҋm�����ݒ�
     * @param ryoikiKakuteiDate �̈��\�Ҋm���
     */
    public void setRyoikiKakuteiDate(Date ryoikiKakuteiDate) {
        this.ryoikiKakuteiDate = ryoikiKakuteiDate;
    }

    /**
     * �\����ID�z����擾
     * @return Returns �\����ID�z��
     */
    public String[] getJokyoIds() {
        return jokyoIds;
    }

    /**
     * �\����ID�z���ݒ�
     * @param jokyoIds �\����ID�z��
     */
    public void setJokyoIds(String[] jokyoIds) {
        this.jokyoIds = jokyoIds;
    }

    /**
     * �������ԏ��F��(mark)���擾
     * @return Returns �������ԏ��F��(mark)
     */
    public String getShoninDateMark() {
        return shoninDateMark;
    }

    /**
     * �������ԏ��F��(mark)��ݒ�
     * @param shoninDateMark �������ԏ��F��(mark)
     */
    public void setShoninDateMark(String shoninDateMark) {
        this.shoninDateMark = shoninDateMark;
    }

    /**
     * �w�U�󗝓�Flg���擾
     * @return Returns �w�U�󗝓�Flg
     */
    public String getJyuriDateFlg() {
        return jyuriDateFlg;
    }

    /**
     * �w�U�󗝓�Flg��ݒ�
     * @param jyuriDateFlg �w�U�󗝓�Flg
     */
    public void setJyuriDateFlg(String jyuriDateFlg) {
        this.jyuriDateFlg = jyuriDateFlg;
    }

    /**
     * �̈��\�Ҋm���Flg���擾
     * @return Returns �̈��\�Ҋm���Flg
     */
    public String getRyoikiKakuteiDateFlg() {
        return ryoikiKakuteiDateFlg;
    }

    /**
     * �̈��\�Ҋm���Flg��ݒ�
     * @param ryoikiKakuteiDateFlg �̈��\�Ҋm���Flg
     */
    public void setRyoikiKakuteiDateFlg(String ryoikiKakuteiDateFlg) {
        this.ryoikiKakuteiDateFlg = ryoikiKakuteiDateFlg;
    }

    /**
     * URL���擾
     * @return Returns URL
     */
    public String getUrl() {
        return url;
    }

    /**
     * URL��ݒ�
     * @param url URL
     */
    public void setUrl(String url) {
        this.url = url;
    }
    

    /**
     * ���ʌ����������ۑ�ԍ�-�N�x�ݒ�
     * @param  shureihiNo �ݒ�l
     */
    public void setShoreihiNoNendo(String shoreihiNoNendo) {
        this.shoreihiNoNendo = shoreihiNoNendo;
    }
    
    /**
     * ���ʌ����������ۑ�ԍ�-�N�x�擾
     * @return ���ʌ����������ۑ�ԍ�-�N�x�̒l
     */
    public String getShoreihiNoNendo() {
        return shoreihiNoNendo;
    }
    

    /**
     * ���ʌ����������ۑ�ԍ�-�����ԍ��ݒ�
     * @param  shoreihiNoSeiri �ݒ�l
     */
    public void setShoreihiNoSeiri(String shoreihiNoSeiri) {
        this.shoreihiNoSeiri = shoreihiNoSeiri;
    }
    
    /**
     * ���ʌ����������ۑ�ԍ�-�����ԍ��擾
     * @return ���ʌ����������ۑ�ԍ�-�����ԍ��̒l
     */
    public String getShoreihiNoSeiri() {
        return shoreihiNoSeiri;
    }
//  <!-- ADD�@START 2007/07/06 BIS ���� -->
//  H19���S�d�q���y�ѐ��x����.
//  ���͂��������g�D�ɃG���[������ꍇ�A�u��������́v��ʂŃG���[���b�Z�[�W��\�����A�u�����g�D�\�v��ʂŃG���[�ɂȂ鍀�ڂ̔w�i�F��ύX����B	
	/**
	 * @return Returns the errorsMap.
	 */
	public HashMap getErrorsMap() {
		return errorsMap;
	}

	/**
	 * @param errorsMap The errorsMap to set.
	 */
	public void setErrorsMap(HashMap errorsMap) {
		this.errorsMap = errorsMap;
	}
//	<!-- ADD�@END�@ 2007/07/06 BIS ���� -->	
}