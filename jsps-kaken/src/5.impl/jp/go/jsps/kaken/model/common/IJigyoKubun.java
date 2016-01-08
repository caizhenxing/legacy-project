/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2004/08/04
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.model.common;

/**
 * ���Ƌ敪���`����B
 * 
 * ID RCSfile="$RCSfile: IJigyoKubun.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:30 $"
 */
public interface IJigyoKubun {
	
	/** �w�p�n���i�����j */
	public static final String JIGYO_KUBUN_GAKUSOU_HIKOUBO     = "1";
	
	/** �w�p�n���i����j */
	public static final String JIGYO_KUBUN_GAKUSOU_KOUBO       = "2";
		
	/** ���ʐ��i���� */
	public static final String JIGYO_KUBUN_TOKUSUI             = "3";
	
	/** ��Ռ��� */
	public static final String JIGYO_KUBUN_KIBAN               = "4";

	/** ����̈挤�� */
	public static final String JIGYO_KUBUN_TOKUTEI             = "5";

	/** ���X�^�[�g�A�b�v */
	public static final String JIGYO_KUBUN_WAKATESTART         = "6";
	
	/** ���ʌ������i�� */
	public static final String JIGYO_KUBUN_SHOKUSHINHI         = "7";

	/** ��Ղ̗l���� */
	public static final int KIBAN_YOSHIKISU	=	10;
//2006.05.09 Modify By Sai
/*	public static final String[] SHINSA_KANOU_JIGYO_KUBUN	= {
		JIGYO_KUBUN_KIBAN, 
		JIGYO_KUBUN_WAKATESTART
	};*/
}
