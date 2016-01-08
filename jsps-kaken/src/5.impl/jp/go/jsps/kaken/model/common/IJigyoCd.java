/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Hashimoto
 *    Date        : 2005/04/09
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.model.common;

/**
 * ����CD���`����B
 */
public interface IJigyoCd {
	
	/** �w�p�n���i�����j */
	public static final String JIGYO_CD_GAKUSOU_HIKOUBO     = "00521";
	
	/** �w�p�n���i����j */
	public static final String JIGYO_CD_GAKUSOU_KOUBO       = "00522";
		
	/** ���ʐ��i���� */
	public static final String JIGYO_CD_TOKUSUI             = "00011";
	
	/** ��Ռ���(S) */
	public static final String JIGYO_CD_KIBAN_S             = "00031";
	
	/** ��Ռ���(A)(���) */
	public static final String JIGYO_CD_KIBAN_A_IPPAN       = "00041";
	
	/** ��Ռ���(A)(�C�O�w�p����) */
	public static final String JIGYO_CD_KIBAN_A_KAIGAI      = "00043";
	
	/** ��Ռ���(B)(���) */
	public static final String JIGYO_CD_KIBAN_B_IPPAN       = "00051";
	
	/** ��Ռ���(B)(�C�O�w�p����) */
	public static final String JIGYO_CD_KIBAN_B_KAIGAI      = "00053";
	
	/** ��Ռ���(C)(���) */
	public static final String JIGYO_CD_KIBAN_C_IPPAN       = "00061";
	
	/** ��Ռ���(C)(��撲��) */
	public static final String JIGYO_CD_KIBAN_C_KIKAKU      = "00062";
	
	/** �G�茤�� */
	public static final String JIGYO_CD_KIBAN_HOGA          = "00111";
	
	/** ��茤��(A) */
	public static final String JIGYO_CD_KIBAN_WAKATE_A      = "00121";
	
	/** ��茤��(B) */
	public static final String JIGYO_CD_KIBAN_WAKATE_B      = "00131";
    
//2007/02/03 �c�@�ǉ���������
    /** ��茤��(S) */
    public static final String JIGYO_CD_KIBAN_WAKATE_S      = "00120";
//2007/02/03 �c�@�ǉ������܂�
	
//2006/06/14 �c�@�C����������
    // 20050601 Start ����̈�
	/** ����̈� */
//	public static final String IJigyoCd.JIGYO_CD_TOKUTEI				= "00021";
    public static final String JIGYO_CD_TOKUTEI_KEIZOKU     = "00021";
    // Horikoshi End
//2006/06/14 �c�@�C�������܂�  
    
//2006/06/14 �ǉ���������
    /** ����̈挤���i�V�K�̈�j */
    public static final String JIGYO_CD_TOKUTEI_SINKI       = "00022";        
//�@�c�@�ǉ������܂�     
	
	/** ��茤���i�X�^�[�g�A�b�v�j */
// 2005/02/09   
	public static final String JIGYO_CD_WAKATESTART         = "00141";
// Syuu End	
	
// 2006/02/10 Start
	/** ���ʌ������i��i��Ռ���(A)�����j */
	public static final String JIGYO_CD_SHOKUSHINHI_KIBAN_A = "00152";

	/** ���ʌ������i��i��Ռ���(B)�����j */
	public static final String JIGYO_CD_SHOKUSHINHI_KIBAN_B = "00153";

	/** ���ʌ������i��i��Ռ���(C)�����j */
	public static final String JIGYO_CD_SHOKUSHINHI_KIBAN_C = "00154";

	/** ���ʌ������i��i��茤��(A)�����j */
	public static final String JIGYO_CD_SHOKUSHINHI_WAKATE_A = "00155";

	/** ���ʌ������i��i��茤��(B)�����j */
	public static final String JIGYO_CD_SHOKUSHINHI_WAKATE_B = "00156";
// Byou End       
}
