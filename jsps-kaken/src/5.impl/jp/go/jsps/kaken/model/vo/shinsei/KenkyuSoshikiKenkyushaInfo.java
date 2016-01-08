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

package jp.go.jsps.kaken.model.vo.shinsei;

import java.util.ArrayList;
import java.util.List;


/**
 * ������\�ҋy�ѕ��S�ҁi�����g�D�\�j��ێ�����N���X�B
 * 
 * ID RCSfile="$RCSfile: KenkyuSoshikiKenkyushaInfo.java,v $"
 * Revision="$Revision: 1.4 $"
 * Date="$Date: 2007/07/20 01:31:07 $"
 */
public class KenkyuSoshikiKenkyushaInfo extends KenkyuSoshikiKenkyushaPk{

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------
	//<!-- ADD�@START 2007/07/20 BIS ���� -->
	/** �\���� */
	private String hyojijun;
	//<!-- ADD�@END 2007/07/20 BIS ���� -->
	/** ����ID */
	private String jigyoID;
	
	/** ��\�ҕ��S�ҕ� */
	private String buntanFlag;
	
	/** �����Ҕԍ� */
	private String kenkyuNo;
	
	/** �����i����-���j */
	private String nameKanjiSei;
	
	/** �����i����-���j */
	private String nameKanjiMei;
	
	/** �����i�t���K�i-���j */
	private String nameKanaSei;
	
	/** �����i�t���K�i-���j */
	private String nameKanaMei;
	
	/** �����@�֖��i�R�[�h�j */
	private String shozokuCd;
	
	/** �����@�֖��i�a���j */
	private String shozokuName;
	
	/** ���ǖ��i�R�[�h�j */
	private String bukyokuCd;
	
	/** ���ǖ��i�a���j */
	private String bukyokuName;
	
	/** �E���R�[�h */
	private String shokushuCd;
	
	/** �E���i�a���j */
	private String shokushuNameKanji;
	
	/** ���݂̐�� */
	private String senmon;
	
	/** �w�� */
	private String gakui;
	
	/** �������S */
	private String buntan;
	
	/** �����o�� */
	private String keihi;
	
	/** �G�t�H�[�g */
	private String effort;
	
	/** �N�� */
	private String nenrei;
    
	//  2006/06/20 �����@�ǉ� ��������--------------------------------------------
    /** �\���� */
    private String[] jokyoId;
    //  2006/06/20 �����@�ǉ� �����܂�--------------------------------------------
    
    // 2006/6/16 �ǉ��@���`�؁@��������
    /** ���̈�ԍ� */
    private String kariryoikiNo;
    // 2006/6/16 �ǉ��@���`�؁@�����܂�
	//�E�E�E�E�E�E�E�E�E�E

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * �R���X�g���N�^�B
	 */
	public KenkyuSoshikiKenkyushaInfo() {
		super();
	}

	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------
	
	/**
	 * @return
	 */
	public String getBukyokuCd() {
		return bukyokuCd;
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
	public String getBuntan() {
		return buntan;
	}

	/**
	 * @return
	 */
	public String getBuntanFlag() {
		return buntanFlag;
	}

	/**
	 * @return
	 */
	public String getEffort() {
		return effort;
	}

	/**
	 * @return
	 */
	public String getGakui() {
		return gakui;
	}

	/**
	 * @return
	 */
	public String getJigyoID() {
		return jigyoID;
	}

	/**
	 * @return
	 */
	public String getKeihi() {
		return keihi;
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
	public String getSenmon() {
		return senmon;
	}

	/**
	 * @return
	 */
	public String getShokushuCd() {
		return shokushuCd;
	}

	/**
	 * @return
	 */
	public String getShokushuNameKanji() {
		return shokushuNameKanji;
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
	public String getShozokuName() {
		return shozokuName;
	}

	/**
	 * @param string
	 */
	public void setBukyokuCd(String string) {
		bukyokuCd = string;
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
	public void setBuntan(String string) {
		buntan = string;
	}

	/**
	 * @param string
	 */
	public void setBuntanFlag(String string) {
		buntanFlag = string;
	}

	/**
	 * @param string
	 */
	public void setEffort(String string) {
		effort = string;
	}

	/**
	 * @param string
	 */
	public void setGakui(String string) {
		gakui = string;
	}

	/**
	 * @param string
	 */
	public void setJigyoID(String string) {
		jigyoID = string;
	}

	/**
	 * @param string
	 */
	public void setKeihi(String string) {
		keihi = string;
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
	public void setSenmon(String string) {
		senmon = string;
	}

	/**
	 * @param string
	 */
	public void setShokushuCd(String string) {
		shokushuCd = string;
	}

	/**
	 * @param string
	 */
	public void setShokushuNameKanji(String string) {
		shokushuNameKanji = string;
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
	public void setShozokuName(String string) {
		shozokuName = string;
	}

	/**
	 * @return
	 */
	public String getNenrei() {
		return nenrei;
	}

	/**
	 * @param string
	 */
	public void setNenrei(String string) {
		nenrei = string;
	}

	//  2006/06/20 �����@�ǉ� ��������--------------------------------------------
    /**
     * @return
     */
    public String[] getJokyoId() {
        return jokyoId;
    }

    /**
     * @param string
     */
    public void setJokyoId(String[] jokyoId) {
        this.jokyoId = jokyoId;
    }
    //  2006/06/20 �����@�ǉ� �����܂�--------------------------------------------
    // 2006/06/16 �ǉ��@���`�؁@��������
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
	// 2006/06/16 �ǉ��@���`�؁@�����܂�
	//<!-- ADD�@START 2007/07/20 BIS ���� -->
	/**
	 * @return Returns the hyojijun.
	 */
	public String getHyojijun() {
		return hyojijun;
	}

	/**
	 * @param hyojijun The hyojijun to set.
	 */
	public void setHyojijun(String hyojijun) {
		this.hyojijun = hyojijun;
	}
	//<!-- ADD�@END 2007/07/20 BIS ���� -->
}
