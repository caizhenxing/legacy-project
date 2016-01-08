/*======================================================================
 *    SYSTEM      : ���{�w�p�U����d�q�\���V�X�e���i�Ȋw������⏕���j
 *    Source name : KenkyuKeihiSoukeiInfo.java
 *    Description : �����o��̑��v����ێ�����N���X�B
 *
 *    Author      : Admin
 *    Date        : 2003/07/16
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2003/07/16    V1.0        Admin          �V�K�쐬
 *====================================================================== 
 */
package jp.go.jsps.kaken.model.vo.shinsei;

import jp.go.jsps.kaken.model.vo.ShinseiDataPk;

/**
 * �����o��̑��v����ێ�����N���X�B
 * 
 * ID RCSfile="$RCSfile: KenkyuKeihiSoukeiInfo.java,v $"
 * Revision="$Revision: 1.3 $"
 * Date="$Date: 2007/07/20 01:28:09 $"
 */
public class KenkyuKeihiSoukeiInfo extends ShinseiDataPk{

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------
   
	/** �����o��i5���N���j */
	private KenkyuKeihiInfo[] kenkyuKeihi = makeKenkyuKeihiInfoArray(5);
    
//2006/07/03 �c�@�ǉ���������
    /** �����o��i6���N���j */
    private KenkyuKeihiInfo[] kenkyuKeihi6 = makeKenkyuKeihiInfoArray(6);
//2006/07/03�@�c�@�ǉ������܂�    
	
//  ADD START 2007-07-10 BIS ���u��
	/** ����z */
	private String naiyakuTotal;
// ADD END 2007-07-10 BIS ���u��
	
    /** ���v-�����o�� */
	private String keihiTotal;
	
	/** ���v-�ݔ����i�� */
	private String bihinhiTotal;
	
	/** ���v-���Օi�� */
	private String shomohinhiTotal;
	
	/** ���v-�������� */
	private String kokunairyohiTotal;
	
	/** ���v-�O������ */
	private String gaikokuryohiTotal;
	
	/** ���v-���� */
	private String ryohiTotal;
	
	/** ���v-�Ӌ��� */
	private String shakinTotal;
	
	/** ���v-���̑� */
	private String sonotaTotal;

// 20050803
    /** ��c�� */
	private String meetingCost;

    /** ����� */
	private String printingCost;
// Horikoshi

	//�E�E�E�E�E�E�E�E�E�E

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * �R���X�g���N�^�B
	 */
	public KenkyuKeihiSoukeiInfo() {
		super();
	}
	
	
	//---------------------------------------------------------------------
	// Methods
	//---------------------------------------------------------------------
	/**
	 * 
	 * @param arrayLength
	 * @return
	 */
	private KenkyuKeihiInfo[] makeKenkyuKeihiInfoArray(int arrayLength){
		KenkyuKeihiInfo[] infoArray = new KenkyuKeihiInfo[arrayLength];
		for(int i=0; i<arrayLength; i++){
			infoArray[i] = new KenkyuKeihiInfo();
		}
		return infoArray;
	}
	
	
	
	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------
	
	/**
     * ���v-�ݔ����i����擾
	 * @return ���v-�ݔ����i��
	 */
	public String getBihinhiTotal() {
		return bihinhiTotal;
	}

	/**
     * ���v-�O��������擾
	 * @return ���v-�O������
	 */
	public String getGaikokuryohiTotal() {
		return gaikokuryohiTotal;
	}

	/**
     * ���v-�����o����擾
	 * @return ���v-�����o��
	 */
	public String getKeihiTotal() {
		return keihiTotal;
	}

	/**
     * �����o��i5���N���j���擾
	 * @return �����o��i5���N���j
	 */
	public KenkyuKeihiInfo[] getKenkyuKeihi() {
		return kenkyuKeihi;
	}

	/**
     * ���v-����������擾
	 * @return ���v-��������
	 */
	public String getKokunairyohiTotal() {
		return kokunairyohiTotal;
	}

	/**
     * ���v-�Ӌ������擾
	 * @return ���v-�Ӌ���
	 */
	public String getShakinTotal() {
		return shakinTotal;
	}

	/**
     * ���v-���Օi����擾
	 * @return ���v-���Օi��
	 */
	public String getShomohinhiTotal() {
		return shomohinhiTotal;
	}

	/**
     * ���v-���̑����擾
	 * @return ���v-���̑�
	 */
	public String getSonotaTotal() {
		return sonotaTotal;
	}

	/**
     * ���v-�ݔ����i���ݒ�
	 * @param string ���v-�ݔ����i��
	 */
	public void setBihinhiTotal(String string) {
		bihinhiTotal = string;
	}

	/**
     * ���v-�O�������ݒ�
	 * @param string ���v-�O������
	 */
	public void setGaikokuryohiTotal(String string) {
		gaikokuryohiTotal = string;
	}

	/**
     * ���v-�����o���ݒ�
	 * @param string ���v-�����o��
	 */
	public void setKeihiTotal(String string) {
		keihiTotal = string;
	}

	/**
     * �����o��i5���N���j��ݒ�
	 * @param infos �����o��i5���N���j
	 */
	public void setKenkyuKeihi(KenkyuKeihiInfo[] infos) {
		kenkyuKeihi = infos;
	}

	/**
     * ���v-���������ݒ�
	 * @param string ���v-��������
	 */
	public void setKokunairyohiTotal(String string) {
		kokunairyohiTotal = string;
	}

	/**
     * ���v-�Ӌ�����ݒ�
	 * @param string ���v-�Ӌ���
	 */
	public void setShakinTotal(String string) {
		shakinTotal = string;
	}

	/**
     * ���v-���Օi���ݒ�
	 * @param string ���v-���Օi��
	 */
	public void setShomohinhiTotal(String string) {
		shomohinhiTotal = string;
	}

	/**
     * ���v-���̑���ݒ�
	 * @param string ���v-���̑�
	 */
	public void setSonotaTotal(String string) {
		sonotaTotal = string;
	}

	/**
     * ���v-������擾
	 * @return ���v-����
	 */
	public String getRyohiTotal() {
		return ryohiTotal;
	}

	/**
     * ���v-�����ݒ�
	 * @param string ���v-����
	 */
	public void setRyohiTotal(String string) {
		ryohiTotal = string;
	}

// 20050803
    /**
     * ��c����擾
     * @return ��c��
     */
	public String getMeetingCost() {
		return meetingCost;
	}

    /**
     * ��c���ݒ�
     * @param meetingCost ��c��
     */
	public void setMeetingCost(String meetingCost) {
		this.meetingCost = meetingCost;
	}

    /**
     * �������擾
     * @return �����
     */
	public String getPrintingCost() {
		return printingCost;
	}

    /**
     * ������ݒ�
     * @param printingCost �����
     */
	public void setPrintingCost(String printingCost) {
		this.printingCost = printingCost;
	}
// Horikoshi

    /**
     * �����o��i6���N���j���擾
     * @return Returns �����o��i6���N���j
     */
    public KenkyuKeihiInfo[] getKenkyuKeihi6() {
        return kenkyuKeihi6;
    }

    /**
     * �����o��i6���N���j��ݒ�
     * @param kenkyuKeihi6 �����o��i6���N���j
     */
    public void setKenkyuKeihi6(KenkyuKeihiInfo[] kenkyuKeihi6) {
        this.kenkyuKeihi6 = kenkyuKeihi6;
    }

//ADD START 2007-07-10 BIS ���u��
	public String getNaiyakuTotal() {
		return naiyakuTotal;
	}


	public void setNaiyakuTotal(String naiyakuTotal) {
		this.naiyakuTotal = naiyakuTotal;
	}
//ADD END 2007-07-10 BIS ���u��
}