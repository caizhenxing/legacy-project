/*
 * �쐬���F 2005/05/23
 *
 * TODO ���̐������ꂽ�t�@�C���̃e���v���[�g��ύX����ɂ͎����Q�ƁB
 * �E�B���h�E �� �ݒ� �� Java �� �R�[�h�E�X�^�C�� �� �R�[�h�E�e���v���[�g
 */
package jp.go.jsps.kaken.model.vo;

 

/**
 * @author user1
 *
 * TODO ���̐������ꂽ�^�R�����g�̃e���v���[�g��ύX����ɂ͎����Q�ƁB
 * �E�B���h�E �� �ݒ� �� Java �� �R�[�h�E�X�^�C�� �� �R�[�h�E�e���v���[�g
 */
public class IkenSearchInfo extends SearchInfo {

	/** �\���҃t���O */
	private String shinseisya ;
	
	/** �����@�֒S���� */
	private String syozoku ;
	
	/** ���ǒS���� */
	private String bukyoku ;
	
	/** �R�����@*/
	private String shinsyain ;
	
	/** �쐬��(�J�n) */
	private String     sakuseiDateFrom;
	
	/** �쐬��(�I��) */
	private String     sakuseiDateTo;
	
	/** �\������ */
	private String dispmode ;
	
	/**
	 * 
	 */
	public IkenSearchInfo() {
		super();
		// TODO �������������R���X�g���N�^�[�E�X�^�u
	}

	/**
	 * �\���҃t���O�̎擾
	 * @return
	 */
	public String getShinseisya(){
		return shinseisya;
	}
	
	/**
	 * �\���҃t���O�̐ݒ�
	 * @param n
	 */
	public void setShinseisya(String n){
		shinseisya = n;
	}

	/**
	 * �����@�֒S���҃t���O�̎擾
	 * @return
	 */
	public String getSyozoku(){
		return syozoku;
	}
	
	/**
	 * �����@�֒S���҃t���O�̐ݒ�
	 * @param n
	 */
	public void setSyozoku(String n){
		syozoku = n;
	}
	
	/**
	 * ���ǒS���҃t���O�̎擾
	 * @return
	 */
	public String getBukyoku(){
		return bukyoku;
	}
	
	/**
	 * ���ǒS���҃t���O�̐ݒ�
	 * @param n
	 */
	public void setBukyoku(String n){
		bukyoku = n;
	}

	/**
	 * �\���҃t���O�̎擾
	 * @return
	 */
	public String getShinsyain(){
		return shinsyain;
	}
	
	/**
	 * �\���҃t���O�̐ݒ�
	 * @param n
	 */
	public void setShinsyain(String n){
		shinsyain = n;
	}
	
	/**
	 * ���e���J�n
	 * @return
	 */
	public String getSakuseiDateFrom() {
		return sakuseiDateFrom;
	}

	/**
	 * ���e���J�n
	 * @return
	 */
	public void setSakuseiDateFrom(String s) {
		sakuseiDateFrom = s;
	}

	/**
	 * ���e���I��
	 * @return
	 */
	public String getSakuseiDateTo() {
		return sakuseiDateTo;
	}
	
	/**
	 * ���e���I��
	 * @return
	 */
	public void setSakuseiDateTo(String s ) {
		sakuseiDateTo = s;
	}

	/**
	 * �\���҃t���O�̎擾
	 * @return
	 */
	public String getDispmode(){
		return dispmode;
	}
	
	/**
	 * �\���҃t���O�̐ݒ�
	 * @param n
	 */
	public void setDispmode(String n){
		dispmode = n;
	}
}
