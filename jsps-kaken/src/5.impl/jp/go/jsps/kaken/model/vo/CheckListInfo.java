/*
 * Created on 2005/04/12
 *
 */
package jp.go.jsps.kaken.model.vo;

/**
 * �`�F�b�N���X�g�p�f�[�^�ێ��N���X
 * 
 * @author masuo_t
 *
 */
public class CheckListInfo extends CheckListPk {

	/** ����CD */	
	private String jigyoCd;

	//2005/04/21 �ǉ� ��������--------------------------------------------------
	//���R ���������ɏ����@�֖��Ǝ󗝏󋵂��ǉ����ꂽ����
	
	/** �󗝏� */
	private String jyuriJokyo;
	
	/** �����@�֖� */
	private String shozokuName;
	
	//�ǉ� �����܂�-------------------------------------------------------------
	
	//2005/05/25 �ǉ� ��������--------------------------------------------------
	//���R PDF�\���p�̃f�[�^�ێ��̂���

	/** ���Ɩ� */
	private String jigyoName;
	
	/** ��(������) */
	private String kaisu;
	
	/** �V�K���� */
	private int shinkiCount;
	
	/** �p������ */
	private int keizokuCount;
	
	/** ���Ƌ敪 */
	private String jigyoKbn;

	
	//�ǉ� �����܂�-------------------------------------------------------------

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

	//2005/04/21 �ǉ� ��������--------------------------------------------------
	//���R ���������ɏ����@�֖��Ǝ󗝏󋵂��ǉ����ꂽ����

	/**
	 * @return
	 */
	public String getJyuriJokyo() {
		return jyuriJokyo;
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
	public void setJyuriJokyo(String string) {
		jyuriJokyo = string;
	}

	/**
	 * @param string
	 */
	public void setShozokuName(String string) {
		shozokuName = string;
	}
	//�ǉ� �����܂�-------------------------------------------------------------
	
	//2005/05/25 �ǉ� ��������--------------------------------------------------
	//���R PDF�\���p�̃f�[�^�ێ��̂���
	/**
	 * @return
	 */
	public String getJigyoName() {
		return jigyoName;
	}

	/**
	 * @return
	 */
	public String getKaisu() {
		return kaisu;
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
	public void setKaisu(String string) {
		kaisu = string;
	}
	//�ǉ� �����܂�-------------------------------------------------------------

	/**
	 * @return �V�K������Ԃ�
	 */
	public int getShinkiCount(){
		return shinkiCount;
	}

	/**
	 * @param cnt �V�K������ݒ肷��
	 */
	public void setShinkiCount(int cnt){
		shinkiCount = cnt;
	}
	
	/**
	 * @return �p��������Ԃ�
	 */
	public int getKeizokuCount(){
		return keizokuCount;
	}
	
	/**
	 * @param cnt �p���������擾����
	 */
	public void setKeizokuCount(int cnt){
		keizokuCount = cnt;
	}

	/**
	 * @return ���Ƌ敪��Ԃ�
	 */
	public String getJigyoKbn(){
		return jigyoKbn;
	}
	
	/**
	 * @param str ���Ƌ敪���擾����
	 */
	public void setJigyoKbn(String str){
		jigyoKbn = str;
	}
}
