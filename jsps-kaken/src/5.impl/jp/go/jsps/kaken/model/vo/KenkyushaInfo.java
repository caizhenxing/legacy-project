/*
 * �쐬��: 2005/03/28
 *
 */
package jp.go.jsps.kaken.model.vo;

import java.util.Date;

/**
 * �����ҏ���ێ�����N���X.
 * 
 * @author yoshikawa_h
 *
 */
public class KenkyushaInfo extends KenkyushaPk {
	
	/**
     * <code>serialVersionUID</code> �̃R�����g
     */
    private static final long serialVersionUID = -2256739159057758578L;

	/** �����Ҕԍ� */
	private String kenkyuNo;
	
	/** �\���Ҏ����i����-���j */
	private String nameKanjiSei;

	/** �\���Ҏ����i����-���j */
	private String nameKanjiMei;

	/** �\���Ҏ����i�t���K�i-���j */
	private String nameKanaSei;

	/** �\���Ҏ����i�t���K�i-���j */
	private String nameKanaMei;
	
	/** ���� */
	private String seibetsu;

	/** ���N���� */
	private Date birthday;

	/** �w�� */
	private String gakui;
	
	/** �����@�փR�[�h */
	private String shozokuCd;
	
	/** �����@�֖��i�a���j */
	private String shozokuNameKanji;
	
	/** �����@�֖��i�p���j */
	private String shozokuNameEigo;
	
	/** �����@�֖��i���́j */
	private String shozokuRyakusho;
	
	/** ���ǃR�[�h */
	private String bukyokuCd;
	
	/** ���ǖ� */
	private String bukyokuName;
	
	/** ���Ǘ��� */
	private String bukyokuNameRyaku;
	
	/** �E���R�[�h */
	private String shokushuCd;
	
	/** �E�� */
	private String shokushuName;
	
	/** �E������ */
	private String shokushuNameRyaku;

	/** ���̋@��1�i�Ϗ���}�[�N�j */
	private String otherKikanFlg1;
	
	/** ���̋@�֔ԍ�1 */
	private String otherKikanCd1;

	/** ���̋@�֖�1 */
	private String otherKikanName1;

	/** ���̋@��2�i�Ϗ���}�[�N�j */
	private String otherKikanFlg2;

	/** ���̋@�֔ԍ�2 */
	private String otherKikanCd2;

	/** ���̋@�֖�2 */
	private String otherKikanName2;

	/** ���̋@��3�i�Ϗ���}�[�N�j */
	private String otherKikanFlg3;

	/** ���̋@�֔ԍ�3 */
	private String otherKikanCd3;

	/** ���̋@�֖�3 */
	private String otherKikanName3;

	/** ���̋@��4�i�Ϗ���}�[�N�j */
	private String otherKikanFlg4;

	/** ���̋@�֔ԍ�4 */
	private String otherKikanCd4;

	/** ���̋@�֖�4 */
	private String otherKikanName4;

	
	
	
	/** �f�[�^�X�V���� */
	private Date koshinDate;
	
	/** ���l */
	private String biko;
	
	
	//2005/04/22 �ǉ� ��������----------------------------------------------
	//�폜�t���O�ǉ�
	
	/** �폜�t���O */
	private String delFlg;
	
	//2006/02/08 �ǉ� ��������----------------------------------------------
	//���厑�i�ǉ�
	
	/** ���厑�i */
	private String ouboShikaku;
	
	//�ǉ� �����܂�---------------------------------------------------------
	
	//�E�E�E�E�E�E�E�E�E�E

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * �R���X�g���N�^�B
	 */
	public KenkyushaInfo() {
		super();
	}
	 
	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------
	/**
	 * @return biko ��߂��܂��B
	 */
	public String getBiko() {
		return biko;
	}
	/**
	 * @param biko biko ��ݒ�B
	 */
	public void setBiko(String biko) {
		this.biko = biko;
	}
	/**
	 * @return birthday ��߂��܂��B
	 */
	public Date getBirthday() {
		return birthday;
	}
	/**
	 * @param birthday birthday ��ݒ�B
	 */
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	/**
	 * @return bukyokuCd ��߂��܂��B
	 */
	public String getBukyokuCd() {
		return bukyokuCd;
	}
	/**
	 * @param bukyokuCd bukyokuCd ��ݒ�B
	 */
	public void setBukyokuCd(String bukyokuCd) {
		this.bukyokuCd = bukyokuCd;
	}
	/**
	 * @return gakui ��߂��܂��B
	 */
	public String getGakui() {
		return gakui;
	}
	/**
	 * @param gakui gakui ��ݒ�B
	 */
	public void setGakui(String gakui) {
		this.gakui = gakui;
	}
	/**
	 * @return kenkyuNo ��߂��܂��B
	 */
	public String getKenkyuNo() {
		return kenkyuNo;
	}
	/**
	 * @param kenkyuNo kenkyuNo ��ݒ�B
	 */
	public void setKenkyuNo(String kenkyuNo) {
		this.kenkyuNo = kenkyuNo;
	}
	/**
	 * @return koshinDate ��߂��܂��B
	 */
	public Date getKoshinDate() {
		return koshinDate;
	}
	/**
	 * @param koshinDate koshinDate ��ݒ�B
	 */
	public void setKoshinDate(Date koshinDate) {
		this.koshinDate = koshinDate;
	}
	/**
	 * @return nameKanaMei ��߂��܂��B
	 */
	public String getNameKanaMei() {
		return nameKanaMei;
	}
	/**
	 * @param nameKanaMei nameKanaMei ��ݒ�B
	 */
	public void setNameKanaMei(String nameKanaMei) {
		this.nameKanaMei = nameKanaMei;
	}
	/**
	 * @return nameKanaSei ��߂��܂��B
	 */
	public String getNameKanaSei() {
		return nameKanaSei;
	}
	/**
	 * @param nameKanaSei nameKanaSei ��ݒ�B
	 */
	public void setNameKanaSei(String nameKanaSei) {
		this.nameKanaSei = nameKanaSei;
	}
	/**
	 * @return nameKanjiMei ��߂��܂��B
	 */
	public String getNameKanjiMei() {
		return nameKanjiMei;
	}
	/**
	 * @param nameKanjiMei nameKanjiMei ��ݒ�B
	 */
	public void setNameKanjiMei(String nameKanjiMei) {
		this.nameKanjiMei = nameKanjiMei;
	}
	/**
	 * @return nameKanjiSei ��߂��܂��B
	 */
	public String getNameKanjiSei() {
		return nameKanjiSei;
	}
	/**
	 * @param nameKanjiSei nameKanjiSei ��ݒ�B
	 */
	public void setNameKanjiSei(String nameKanjiSei) {
		this.nameKanjiSei = nameKanjiSei;
	}
	/**
	 * @return seibetsu ��߂��܂��B
	 */
	public String getSeibetsu() {
		return seibetsu;
	}
	/**
	 * @param seibetsu seibetsu ��ݒ�B
	 */
	public void setSeibetsu(String seibetsu) {
		this.seibetsu = seibetsu;
	}
	/**
	 * @return shokushuCd ��߂��܂��B
	 */
	public String getShokushuCd() {
		return shokushuCd;
	}
	/**
	 * @param shokushuCd shokushuCd ��ݒ�B
	 */
	public void setShokushuCd(String shokushuCd) {
		this.shokushuCd = shokushuCd;
	}
	/**
	 * @return shozokuCd ��߂��܂��B
	 */
	public String getShozokuCd() {
		return shozokuCd;
	}
	/**
	 * @param shozokuCd shozokuCd ��ݒ�B
	 */
	public void setShozokuCd(String shozokuCd) {
		this.shozokuCd = shozokuCd;
	}
	
	
	/**
	 * @return bukyokuName ��߂��܂��B
	 */
	public String getBukyokuName() {
		return bukyokuName;
	}
	/**
	 * @param bukyokuName bukyokuName ��ݒ�B
	 */
	public void setBukyokuName(String bukyokuName) {
		this.bukyokuName = bukyokuName;
	}
	/**
	 * @return bukyokuNameRyaku ��߂��܂��B
	 */
	public String getBukyokuNameRyaku() {
		return bukyokuNameRyaku;
	}
	/**
	 * @param bukyokuNameRyaku bukyokuNameRyaku ��ݒ�B
	 */
	public void setBukyokuNameRyaku(String bukyokuNameRyaku) {
		this.bukyokuNameRyaku = bukyokuNameRyaku;
	}
	/**
	 * @return shokushuName ��߂��܂��B
	 */
	public String getShokushuName() {
		return shokushuName;
	}
	/**
	 * @param shokushuName shokushuName ��ݒ�B
	 */
	public void setShokushuName(String shokushuName) {
		this.shokushuName = shokushuName;
	}
	/**
	 * @return shokushuNameRyaku ��߂��܂��B
	 */
	public String getShokushuNameRyaku() {
		return shokushuNameRyaku;
	}
	/**
	 * @param shokushuNameRyaku shokushuNameRyaku ��ݒ�B
	 */
	public void setShokushuNameRyaku(String shokushuNameRyaku) {
		this.shokushuNameRyaku = shokushuNameRyaku;
	}
	/**
	 * @return shozokuNameEig ��߂��܂��B
	 */
	public String getShozokuNameEigo() {
		return shozokuNameEigo;
	}
	/**
	 * @param shozokuNameEig shozokuNameEig ��ݒ�B
	 */
	public void setShozokuNameEigo(String shozokuNameEig) {
		this.shozokuNameEigo = shozokuNameEig;
	}
	/**
	 * @return shozokuNameKanji ��߂��܂��B
	 */
	public String getShozokuNameKanji() {
		return shozokuNameKanji;
	}
	/**
	 * @param shozokuNameKanji shozokuNameKanji ��ݒ�B
	 */
	public void setShozokuNameKanji(String shozokuNameKanji) {
		this.shozokuNameKanji = shozokuNameKanji;
	}
	/**
	 * @return shozokuRyakusho ��߂��܂��B
	 */
	public String getShozokuRyakusho() {
		return shozokuRyakusho;
	}
	/**
	 * @param shozokuRyakusho shozokuRyakusho ��ݒ�B
	 */
	public void setShozokuRyakusho(String shozokuRyakusho) {
		this.shozokuRyakusho = shozokuRyakusho;
	}
	
	//2005/04/22 �ǉ� ��������----------------------------------------------
	//�폜�t���O�ǉ�
		
	/**
	 * @return
	 */
	public String getDelFlg() {
		return delFlg;
	}

	/**
	 * @param string
	 */
	public void setDelFlg(String string) {
		delFlg = string;
	}
	//2006/02/08 �ǉ� ��������----------------------------------------------
	//���厑�i�ǉ�
	
	public String getOuboShikaku() {
		return ouboShikaku;
	}

	public void setOuboShikaku(String ouboShikaku) {
		this.ouboShikaku = ouboShikaku;
	}

	/**
     * otherKikanCd1���擾���܂��B
     * 
     * @return otherKikanCd1
     */
    
    public String getOtherKikanCd1() {
    	return otherKikanCd1;
    }

	/**
     * otherKikanCd1��ݒ肵�܂��B
     * 
     * @param otherKikanCd1 otherKikanCd1
     */
    
    public void setOtherKikanCd1(String otherKikanCd1) {
    	this.otherKikanCd1 = otherKikanCd1;
    }

	/**
     * otherKikanCd2���擾���܂��B
     * 
     * @return otherKikanCd2
     */
    
    public String getOtherKikanCd2() {
    	return otherKikanCd2;
    }

	/**
     * otherKikanCd2��ݒ肵�܂��B
     * 
     * @param otherKikanCd2 otherKikanCd2
     */
    
    public void setOtherKikanCd2(String otherKikanCd2) {
    	this.otherKikanCd2 = otherKikanCd2;
    }

	/**
     * otherKikanCd3���擾���܂��B
     * 
     * @return otherKikanCd3
     */
    
    public String getOtherKikanCd3() {
    	return otherKikanCd3;
    }

	/**
     * otherKikanCd3��ݒ肵�܂��B
     * 
     * @param otherKikanCd3 otherKikanCd3
     */
    
    public void setOtherKikanCd3(String otherKikanCd3) {
    	this.otherKikanCd3 = otherKikanCd3;
    }

	/**
     * otherKikanCd4���擾���܂��B
     * 
     * @return otherKikanCd4
     */
    
    public String getOtherKikanCd4() {
    	return otherKikanCd4;
    }

	/**
     * otherKikanCd4��ݒ肵�܂��B
     * 
     * @param otherKikanCd4 otherKikanCd4
     */
    
    public void setOtherKikanCd4(String otherKikanCd4) {
    	this.otherKikanCd4 = otherKikanCd4;
    }

	/**
     * otherKikanFlg1���擾���܂��B
     * 
     * @return otherKikanFlg1
     */
    
    public String getOtherKikanFlg1() {
    	return otherKikanFlg1;
    }

	/**
     * otherKikanFlg1��ݒ肵�܂��B
     * 
     * @param otherKikanFlg1 otherKikanFlg1
     */
    
    public void setOtherKikanFlg1(String otherKikanFlg1) {
    	this.otherKikanFlg1 = otherKikanFlg1;
    }

	/**
     * otherKikanFlg2���擾���܂��B
     * 
     * @return otherKikanFlg2
     */
    
    public String getOtherKikanFlg2() {
    	return otherKikanFlg2;
    }

	/**
     * otherKikanFlg2��ݒ肵�܂��B
     * 
     * @param otherKikanFlg2 otherKikanFlg2
     */
    
    public void setOtherKikanFlg2(String otherKikanFlg2) {
    	this.otherKikanFlg2 = otherKikanFlg2;
    }

	/**
     * otherKikanFlg3���擾���܂��B
     * 
     * @return otherKikanFlg3
     */
    
    public String getOtherKikanFlg3() {
    	return otherKikanFlg3;
    }

	/**
     * otherKikanFlg3��ݒ肵�܂��B
     * 
     * @param otherKikanFlg3 otherKikanFlg3
     */
    
    public void setOtherKikanFlg3(String otherKikanFlg3) {
    	this.otherKikanFlg3 = otherKikanFlg3;
    }

	/**
     * otherKikanFlg4���擾���܂��B
     * 
     * @return otherKikanFlg4
     */
    
    public String getOtherKikanFlg4() {
    	return otherKikanFlg4;
    }

	/**
     * otherKikanFlg4��ݒ肵�܂��B
     * 
     * @param otherKikanFlg4 otherKikanFlg4
     */
    
    public void setOtherKikanFlg4(String otherKikanFlg4) {
    	this.otherKikanFlg4 = otherKikanFlg4;
    }

	/**
     * otherKikanName1���擾���܂��B
     * 
     * @return otherKikanName1
     */
    
    public String getOtherKikanName1() {
    	return otherKikanName1;
    }

	/**
     * otherKikanName1��ݒ肵�܂��B
     * 
     * @param otherKikanName1 otherKikanName1
     */
    
    public void setOtherKikanName1(String otherKikanName1) {
    	this.otherKikanName1 = otherKikanName1;
    }

	/**
     * otherKikanName2���擾���܂��B
     * 
     * @return otherKikanName2
     */
    
    public String getOtherKikanName2() {
    	return otherKikanName2;
    }

	/**
     * otherKikanName2��ݒ肵�܂��B
     * 
     * @param otherKikanName2 otherKikanName2
     */
    
    public void setOtherKikanName2(String otherKikanName2) {
    	this.otherKikanName2 = otherKikanName2;
    }

	/**
     * otherKikanName3���擾���܂��B
     * 
     * @return otherKikanName3
     */
    
    public String getOtherKikanName3() {
    	return otherKikanName3;
    }

	/**
     * otherKikanName3��ݒ肵�܂��B
     * 
     * @param otherKikanName3 otherKikanName3
     */
    
    public void setOtherKikanName3(String otherKikanName3) {
    	this.otherKikanName3 = otherKikanName3;
    }

	/**
     * otherKikanName4���擾���܂��B
     * 
     * @return otherKikanName4
     */
    
    public String getOtherKikanName4() {
    	return otherKikanName4;
    }

	/**
     * otherKikanName4��ݒ肵�܂��B
     * 
     * @param otherKikanName4 otherKikanName4
     */
    
    public void setOtherKikanName4(String otherKikanName4) {
    	this.otherKikanName4 = otherKikanName4;
    }


	
}
