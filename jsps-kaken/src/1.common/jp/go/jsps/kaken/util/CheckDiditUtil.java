package jp.go.jsps.kaken.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * �`�F�b�N�f�W�b�g���f�[�^�A�N�Z�X�N���X�B
 * ID RCSfile="$RCSfile: CheckDiditUtil.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:44 $"
 */
public class CheckDiditUtil {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/** ���O */
	protected static final Log log = LogFactory.getLog(CheckDiditUtil.class);
	
	/** �`�F�b�N�f�W�b�g�̌`���i�����j */
	public static final String FORM_NUM  = "form_num";

	/** �`�F�b�N�f�W�b�g�̌`���i�A���t�@�x�b�g�j */
	public static final String FORM_ALP  = "form_alp";

	//---------------------------------------------------------------------
	// Static Methods
	//---------------------------------------------------------------------

	/**
	 * �`�F�b�N�f�W�b�g���擾����B�`�F�b�N�f�W�b�g�̐������@�̓��W�����X10�̃E�G�C�g3�Ƃ���B
	 * �`�F�b�N�f�W�b�g�̌`���́A�����Ŏ擾����B
	 * @param data 				���W�����X10�ŎZ�o����f�[�^
	 * @return	�`�F�b�N�f�W�b�g			
	 */
	public static String getCheckDigit(String data) {
		
		return getCheckDigit(data, CheckDiditUtil.FORM_NUM);

	}
	
	/**
	 * �`�F�b�N�f�W�b�g���擾����B�`�F�b�N�f�W�b�g�̐������@�̓��W�����X10�̃E�G�C�g3�Ƃ���B
	 * @param data 			���W�����X10�ŎZ�o����f�[�^
	 * @param form 			�`�F�b�N�f�W�b�g�̌`���B
	 *                          CheckDiditUtil.FORM_NUM�i�����j�܂���CheckDiditUtil.FORM_ALP�i�A���t�@�x�b�g�j�B
	 * @return	�`�F�b�N�f�W�b�g			
	 */
	public static String getCheckDigit(String data , String form) {
			
		//�`�F�b�N�f�W�b�g
		int digit = 0;
			
		//�E�G�C�g
		int[] weight = {3,1};
		

		//������𕶎����̐������������AString�^�̔z��ŕԂ�
		int count = data.length();
		String[] result = new String[count];
		
		for (int i = 0; i < count; i++){
			result[i] = data.substring(i,i+1);						
		}	
	
		//���a
		int sum = 0;
		//�E�G�C�g�̑I���J�E���g
		int weightCount = 0;
		
		//�f�[�^�̖����̌�����E�G�C�g��3/1/3/1�Ƃ����Ă������a�����߂�B
		for(int i = result.length-1; i >= 0; i--){
			//���ɐ��l�Ƃ��ĔF���ł��Ȃ��ꍇ�͏������΂�
			if(StringUtil.isDigit(result[i])){
				sum = sum + (Integer.parseInt(result[i]) * weight[weightCount]);
			}
			weightCount++;
			//�E�G�C�g�J�E���g�����Z�b�g
			if(weightCount == 2){
				weightCount = 0;
			}
		}
		
		//���a��10�Ŋ��肻�̗]������߂�B
		int surplus = sum%10;		
		
		//10���]����������l���`�F�b�N�f�W�b�g
		if(surplus == 0){
			//�]�肪0�̏ꍇ�̓`�F�b�N�f�W�b�g�u0�v			
		}else{
			digit = 10-surplus;
		}
		
		String strDigit = null;
		
		if((CheckDiditUtil.FORM_NUM).equals(form)){
			//�����`��
			strDigit =  Integer.toString(digit);			
		}else if((CheckDiditUtil.FORM_ALP).equals(form)){
			//�A���t�@�x�b�g�`��
			strDigit = convertCheckDigit(digit);			
		}else{
			//����ȊO�̏ꍇ�́A���̂܂ܕԂ�
			strDigit = Integer.toString(digit);						
		}
		
		return strDigit;
	}	

	/**
	 * ���p����1�`9�܂ł̃`�F�b�N�f�W�b�g���A���t�@�x�b�g�ɕϊ�����B
	 * �����œn���ꂽ�`�F�b�N�f�W�b�g�����p����1�`9�ł͂Ȃ��ꍇ�́A�����𕶎���ɕϊ����ĕԂ��B
	 * @param data 				���p����1�`9�܂ł̃`�F�b�N�f�W�b�g
	 * @return	�A���t�@�x�b�g�ɕϊ����ꂽ�`�F�b�N�f�W�b�g			
	 */
	public static String convertCheckDigit(int data) {
		String digit = null;	

		String[] alp = {"A", "B", "C", "E", "F", "H", "J", "K", "L", "M"}; 
		
		try{
			digit = alp[data];
		}catch(ArrayIndexOutOfBoundsException e){
			digit = new Integer(data).toString();
		}
		
		return digit;
	}

}
