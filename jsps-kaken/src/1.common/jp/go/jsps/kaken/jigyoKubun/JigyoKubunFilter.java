/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : takano
 *    Date        : 2004/01/20
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.jigyoKubun;

import java.util.HashSet;
import java.util.Set;

import jp.go.jsps.kaken.model.common.IJigyoCd;
import jp.go.jsps.kaken.model.common.IJigyoKubun;
import jp.go.jsps.kaken.model.role.UserRole;
import jp.go.jsps.kaken.model.vo.GyomutantoInfo;
import jp.go.jsps.kaken.model.vo.UserInfo;

/**
 * �R���Ώێ��Ƌ敪�ݒ�B
 * ID RCSfile="$RCSfile: JigyoKubunFilter.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:51 $"
 */
public class JigyoKubunFilter {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------
	/** �R���ΏۂƂȂ鎖�Ƌ敪�̔z�� */
	private static String[] SHINSA_TAISHO_JIGYO_KUBUN = {IJigyoKubun.JIGYO_KUBUN_GAKUSOU_HIKOUBO		//�w�n�i�����j
															 ,IJigyoKubun.JIGYO_KUBUN_KIBAN				//���
//2006/03/09 �V��ڒǉ�
															 ,IJigyoKubun.JIGYO_KUBUN_WAKATESTART		//��茤���X�^�[�g�A�b�v
															 ,IJigyoKubun.JIGYO_KUBUN_SHOKUSHINHI		//���ʌ������i��
// 20050705 ����̈�͐R���ΏۂƂ��Ȃ����ߍ폜
//															 ,IJigyoKubun.JIGYO_KUBUN_TOKUTEI			//����̈�	2005.06.09 iso �ǉ�
// Horikoshi
															 };
    
//2006/05/09 �ǉ���������
    /** ��Ռn�̎��Ƌ敪�̔z�� */
    public static String[] KIBAN_JIGYO_KUBUN         = {
            IJigyoKubun.JIGYO_KUBUN_KIBAN, 
            IJigyoKubun.JIGYO_KUBUN_WAKATESTART,
            IJigyoKubun.JIGYO_KUBUN_SHOKUSHINHI       };
//�c�@�ǉ������܂�   
	//---------------------------------------------------------------------
	// Static initialize
	//---------------------------------------------------------------------

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------


	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------
	/**
	 * �R���X�g���N�^
	 */
	public JigyoKubunFilter(){

	}


	//---------------------------------------------------------------------
	// Private Methods
	//---------------------------------------------------------------------
	/**
	 * �S�����Ƌ敪�̂����R���Ώێ��Ƌ敪�݂̂�Ԃ��B<br>
	 * �R���Ώە��̎��Ƌ敪�͈ȉ��̂Ƃ���B<br>
	 * <li>1�F�w�p�n��������i�����j</li>
	 * <li>4�F��Ռ���</li>
	 * <li>5�F����̈�</li>
	 * �S�����Ƌ敪�Ɋ܂܂�Ă�����̂̒��ɁA�R���Ώۂ̎��Ƌ敪�����������ꍇ�́A
	 * ���Set�I�u�W�F�N�g���Ԃ�B<br>
	 * @param  userInfo
	 * @return �R���Ώێ��Ƌ敪
	 */
	public static Set getShinsaTaishoJigyoKubun(UserInfo userInfo)
	{
		//�R���Ώێ��Ƌ敪��Set�I�u�W�F�N�g��V�K�ɍ쐬
		//���V�K�ɍ쐬���Ȃ��ƁA����Set�I�u�W�F�N�g���X�V���Ă��܂����ߒ��ӁB
		Set shinsaTaishoSet = new HashSet();

		//�Ɩ��S���҂Ȃ�Ύ������S�����鎖�Ƌ敪�̂�
		if(userInfo.getRole().equals(UserRole.GYOMUTANTO)){
			GyomutantoInfo gyomutantoInfo = userInfo.getGyomutantoInfo(); 		
					
			//�S�����Ƌ敪�̂����A�R���S�����̎��Ƌ敪��Ԃ�
			for(int i = 0; i < SHINSA_TAISHO_JIGYO_KUBUN.length;i++){
				if(gyomutantoInfo.getTantoJigyoKubun().contains(SHINSA_TAISHO_JIGYO_KUBUN[i])){
					//�R���Ώێ��Ƌ敪��Set�I�u�W�F�N�g�ɒǉ�
					shinsaTaishoSet.add(SHINSA_TAISHO_JIGYO_KUBUN[i]);			
				}			 
			}
		}else{
			//����ȊO�̒S���҂̏ꍇ�́A�R���Ώێ��Ƌ敪�����̂܂ܕԂ��B		
			for(int i = 0; i < SHINSA_TAISHO_JIGYO_KUBUN.length;i++){
				//�R���Ώێ��Ƌ敪��Set�I�u�W�F�N�g�ɒǉ�
				shinsaTaishoSet.add(SHINSA_TAISHO_JIGYO_KUBUN[i]);						 
			}		
		}
		
		return shinsaTaishoSet;
	}
    
    
//2006/04/24 �ǉ���������    
    /**
     * �R�����̎��Ƌ敪����\�����̎��Ƌ敪�݂̂�Ԃ��B<br>
     * �R�����̎��Ƌ敪�̎��Ƌ敪�͈ȉ��̂Ƃ���B<br>
     * <li>1�F�w�p�n��������i�����j</li>
     * <li>4�F��Ռ����A���X�^�[�g�A�b�v</li>
     * �R���Ώێ��Ƌ敪�Ɋ܂܂�Ă�����̂̒��ɁA�\�����̎��Ƌ敪�����������ꍇ�́A
     * ���Set�I�u�W�F�N�g���Ԃ�B<br>
     * @param  shinsainJigyoKubun
     * @return �\�����̎��Ƌ敪
     */
    public static Set Convert2ShinseishoJigyoKubun(String shinsainJigyoKubun){
        
        //�\�����̎��Ƌ敪
        Set shinseishoJigyoKubunSet = new HashSet();
        
        //�R�����̎��Ƌ敪�́F�w�p�n���i�����j�̏ꍇ
        if(shinsainJigyoKubun.equals(IJigyoKubun.JIGYO_KUBUN_GAKUSOU_HIKOUBO)){
            //�\�����̎��Ƌ敪�F�w�p�n���i�����j��ݒ肷��
            shinseishoJigyoKubunSet.add(IJigyoKubun.JIGYO_KUBUN_GAKUSOU_HIKOUBO);
        //�R�����̎��Ƌ敪�́F��Ռ����̏ꍇ
        } else if (shinsainJigyoKubun.equals(IJigyoKubun.JIGYO_KUBUN_KIBAN)){
            //�\�����̎��Ƌ敪�F��Ռ�����ݒ肷��
            shinseishoJigyoKubunSet.add(IJigyoKubun.JIGYO_KUBUN_KIBAN);
            //�\�����̎��Ƌ敪�F���X�^�[�g�A�b�v��ݒ肷��
            shinseishoJigyoKubunSet.add(IJigyoKubun.JIGYO_KUBUN_WAKATESTART);
            //�\�����̎��Ƌ敪�F���ʌ������i���ݒ肷��
            shinseishoJigyoKubunSet.add(IJigyoKubun.JIGYO_KUBUN_SHOKUSHINHI);
        //���̑��̏ꍇ    
        } else {
            shinseishoJigyoKubunSet = null;
        }
        
        return shinseishoJigyoKubunSet;
    }
    
    
    /**
     * �\�����̎��Ƌ敪����R�����̎��Ƌ敪�݂̂�Ԃ��B<br>
     * �\�����̎��Ƌ敪�̎��Ƌ敪�͈ȉ��̂Ƃ���B<br>
     * <li>1�F�w�p�n��������i�����j</li>
     * <li>4�F��Ռ���</li>
     * <li>6�F���X�^�[�g�A�b�v</li>
     * <li>7�F���ʌ������i��</li>
     * �R���Ώێ��Ƌ敪�Ɋ܂܂�Ă�����̂̒��ɁA�\�����̎��Ƌ敪�����������ꍇ�́A
     * ���Set�I�u�W�F�N�g���Ԃ�B<br>
     * @param  shinseishoJigyoKubun
     * @return �R�����̎��Ƌ敪
     */
    public static Set Convert2ShinsainJigyoKubun(String shinseishoJigyoKubun) {
        
        //�R�����̎��Ƌ敪
        Set shinsainJigyoKubunSet = new HashSet();
        
        //�\�����̎��Ƌ敪�́F�w�p�n���i�����j�̏ꍇ
        if(shinseishoJigyoKubun.equals(IJigyoKubun.JIGYO_KUBUN_GAKUSOU_HIKOUBO)){
            //�R�����̎��Ƌ敪�F�w�p�n���i�����j��ݒ肷��
            shinsainJigyoKubunSet.add(IJigyoKubun.JIGYO_KUBUN_GAKUSOU_HIKOUBO);
        //�\�����̎��Ƌ敪�́F��Ռ����̏ꍇ   
        } else if (shinseishoJigyoKubun.equals(IJigyoKubun.JIGYO_KUBUN_KIBAN)){
            //�R�����̎��Ƌ敪�F��Ռ�����ݒ肷��
            shinsainJigyoKubunSet.add(IJigyoKubun.JIGYO_KUBUN_KIBAN);
        //�\�����̎��Ƌ敪�́F���X�^�[�g�A�b�v�̏ꍇ  
        } else if (shinseishoJigyoKubun.equals(IJigyoKubun.JIGYO_KUBUN_WAKATESTART)){
            //�R�����̎��Ƌ敪�F��Ռ�����ݒ肷��
            shinsainJigyoKubunSet.add(IJigyoKubun.JIGYO_KUBUN_KIBAN);
        //�\�����̎��Ƌ敪�́F���ʌ������i��̏ꍇ    
        } else if (shinseishoJigyoKubun.equals(IJigyoKubun.JIGYO_KUBUN_SHOKUSHINHI)){
            //�R�����̎��Ƌ敪�F��Ռ�����ݒ肷��
            shinsainJigyoKubunSet.add(IJigyoKubun.JIGYO_KUBUN_KIBAN);
        //���̑��̏ꍇ    
        } else {
            shinsainJigyoKubunSet = null;
        }
        
        return shinsainJigyoKubunSet;
    }
//�c�@�ǉ������܂�    
    
//2006/05/10 �ǉ���������
    /**
     * ���F�^�C�v�̎��Ƌ敪���擾����B<br>
     * ���F�^�C�v�̎��Ƌ敪�͈ȉ��̂Ƃ���B<br>
     * <li>1�F�w�p�n��������i�����j</li>
     * <li>2�F�w�p�n���i����j</li>
     * <li>3�F���ʐ��i����</li>
     * @return ���F�^�C�v�̎��Ƌ敪�i�[
     */
    public static Set getShoninTaishoJigyoKubun(){
        //���F�^�C�v�̎��Ƌ敪�i�[�̐錾
        Set shoninTaishoSet = new HashSet();
        
        //���Ƌ敪:�w�p�n���i�����j
        shoninTaishoSet.add(IJigyoKubun.JIGYO_KUBUN_GAKUSOU_HIKOUBO);
        //���Ƌ敪:�w�p�n���i����j
        shoninTaishoSet.add(IJigyoKubun.JIGYO_KUBUN_GAKUSOU_KOUBO);
        //���Ƌ敪:���ʐ��i����
        shoninTaishoSet.add(IJigyoKubun.JIGYO_KUBUN_TOKUSUI);
        
        return shoninTaishoSet;
    }
    
    /**
     * �`�F�b�N���X�g�^�C�v�̎��Ƌ敪���擾����B<br>
     * �`�F�b�N���X�g�^�C�v�̎��Ƌ敪�͈ȉ��̂Ƃ���B<br>
     * <li>4�F��Ռ���</li>
     * <li>5�F����̈挤��</li>
     * <li>6�F���X�^�[�g�A�b�v</li>
     * <li>7�F���ʌ������i��</li>
     * @return �`�F�b�N���X�g�^�C�v�̎��Ƌ敪�i�[
     */
    public static Set getCheckListTaishoJigyoKubun (){
        //�`�F�b�N���X�g�^�C�v�̎��Ƌ敪�i�[�̐錾
        Set checklistTaishoSet = new HashSet();
        
//      2006/06/29 ZJP �폜�������� 
//        //���Ƌ敪:��Ռ���
//        checklistTaishoSet.add(IJigyoKubun.JIGYO_KUBUN_KIBAN);
//        //���Ƌ敪:����̈挤��
//        checklistTaishoSet.add(IJigyoKubun.JIGYO_KUBUN_TOKUTEI);
//      ZJP�@�폜�����܂�         
        
        //���Ƌ敪:���X�^�[�g�A�b�v
        checklistTaishoSet.add(IJigyoKubun.JIGYO_KUBUN_WAKATESTART);
        //���Ƌ敪:���ʌ������i��
        checklistTaishoSet.add(IJigyoKubun.JIGYO_KUBUN_SHOKUSHINHI);
        
        return checklistTaishoSet;
    }
//�c�@�ǉ������܂�    
    
//  2006/06/29 ZJP �ǉ��������� 
    /**
     * ���F�^�C�v�̎���CD���擾����B<br>
     * ���F�^�C�v�̎���CD�͈ȉ��̂Ƃ���B<br>
     * <li>00521�F�w�p�n���i�����j</li>
     * <li>00522�F�w�p�n���i����j</li>
     * <li>00011�F���ʐ��i����</li>
     * <li>00031�F��Ռ���(S)</li>
     * <li>00041�F��Ռ���(A)(���)</li>
     * <li>00051�F��Ռ���(B)(���)</li>
     * <li>00043�F��Ռ���(A)(�C�O�w�p����)</li>
     * <li>00053�F��Ռ���(B)(�C�O�w�p����)</li>
     * @return ���F�^�C�v�̎���CD�i�[
     */
    public static Set getShoninTaishoJigyoCd(){
        //���F�^�C�v�̎���CD�i�[�̐錾
        Set shoninTaishoSet = new HashSet();
        //�w�p�n���i�����j
        shoninTaishoSet.add(IJigyoCd.JIGYO_CD_GAKUSOU_HIKOUBO);
        //�w�p�n���i����j
        shoninTaishoSet.add(IJigyoCd.JIGYO_CD_GAKUSOU_KOUBO);
        //���ʐ��i����
        shoninTaishoSet.add(IJigyoCd.JIGYO_CD_TOKUSUI);
        //��Ռ���(S)
        shoninTaishoSet.add(IJigyoCd.JIGYO_CD_KIBAN_S);
        //��Ռ���(A)(���)
        shoninTaishoSet.add(IJigyoCd.JIGYO_CD_KIBAN_A_IPPAN);
        //��Ռ���(B)(���)
        shoninTaishoSet.add(IJigyoCd.JIGYO_CD_KIBAN_B_IPPAN);
        //��Ռ���(A)(�C�O�w�p����)
        shoninTaishoSet.add(IJigyoCd.JIGYO_CD_KIBAN_A_KAIGAI);
        //��Ռ���(B)(�C�O�w�p����)
        shoninTaishoSet.add(IJigyoCd.JIGYO_CD_KIBAN_B_KAIGAI);
//2007/03/13 �����F�@�ǉ��@��������
        //��茤��(S)
        shoninTaishoSet.add(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_S);
        //��茤��(�X�^�[�g�A�b�v)
        shoninTaishoSet.add(IJigyoCd.JIGYO_CD_WAKATESTART);
        //���ʌ������i��i��Ռ���(A)�����j
        shoninTaishoSet.add(IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_A);
        //���ʌ������i��i��Ռ���(B)�����j
        shoninTaishoSet.add(IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_B);
        //���ʌ������i��i��Ռ���(C)�����j
        shoninTaishoSet.add(IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_C);
        //���ʌ������i��i��茤��(A)�����j
        shoninTaishoSet.add(IJigyoCd.JIGYO_CD_SHOKUSHINHI_WAKATE_A);
        //���ʌ������i��i��茤��(B)�����j
        shoninTaishoSet.add(IJigyoCd.JIGYO_CD_SHOKUSHINHI_WAKATE_B);
//2007/03/13 �����F�@�ǉ��@�����܂�
        
        return shoninTaishoSet;
    }
    
    /**
     * �`�F�b�N���X�g�^�C�v�̎���CD���擾����B<br>
     * �`�F�b�N���X�g�^�C�v�̎���CD�͈ȉ��̂Ƃ���B<br>
     * <li>00061�F��Ռ���(C)(���)</li>
     * <li>00062�F��Ռ���(C)(��撲��)</li>
     * <li>00021�F����̈�i�p���̈�j</li>
     * <li>00111�F�G�茤��</li>
     * <li>00121�F��茤��(A)</li>
     * <li>00131�F��茤��(B)</li>
     * <li>00120�F��茤��(S)</li>
     * <li>00141�F��茤��(�X�^�[�g�A�b�v)</li>
     * <li>00152�F���ʌ������i��i��Ռ���(A)�����j</li>
     * <li>00153�F���ʌ������i��i��Ռ���(B)�����j</li>
     * <li>00154�F���ʌ������i��i��Ռ���(C)�����j</li>
     * <li>00155�F���ʌ������i��i��茤��(A)�����j</li>
     * <li>00156�F���ʌ������i��i��茤��(B)�����j</li>
     * @return �`�F�b�N���X�g�^�C�v�̎���CD�i�[
     */
    public static Set getCheckListTaishoJigyoCd(){
        //���F�^�C�v�̎���CD�i�[�̐錾
        Set shoninTaishoSet = new HashSet();
        //��Ռ���(C)(���)
        shoninTaishoSet.add(IJigyoCd.JIGYO_CD_KIBAN_C_IPPAN);
        //��Ռ���(C)(��撲��)
        shoninTaishoSet.add(IJigyoCd.JIGYO_CD_KIBAN_C_KIKAKU);
        //����̈�i�p���̈�j
        shoninTaishoSet.add(IJigyoCd.JIGYO_CD_TOKUTEI_KEIZOKU);
        //�G�茤��
        shoninTaishoSet.add(IJigyoCd.JIGYO_CD_KIBAN_HOGA);
        //��茤��(A)
        shoninTaishoSet.add(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_A);
        //��茤��(B)
        shoninTaishoSet.add(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_B);
//        //��茤��(�X�^�[�g�A�b�v)
//        shoninTaishoSet.add(IJigyoCd.JIGYO_CD_WAKATESTART);
//        //���ʌ������i��i��Ռ���(A)�����j
//        shoninTaishoSet.add(IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_A);
//        //���ʌ������i��i��Ռ���(B)�����j
//        shoninTaishoSet.add(IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_B);
//        //���ʌ������i��i��Ռ���(C)�����j
//        shoninTaishoSet.add(IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_C);
//        //���ʌ������i��i��茤��(A)�����j
//        shoninTaishoSet.add(IJigyoCd.JIGYO_CD_SHOKUSHINHI_WAKATE_A);
//        //���ʌ������i��i��茤��(B)�����j
//        shoninTaishoSet.add(IJigyoCd.JIGYO_CD_SHOKUSHINHI_WAKATE_B);
       
        return shoninTaishoSet;
    }

    /**
     * �`�F�b�N���X�g�^�C�v�̎���CD���擾����B<br>
     * �`�F�b�N���X�g�^�C�v�̎���CD�͈ȉ��̂Ƃ���B<br>
     * <li>00061�F��Ռ���(C)(���)</li>
     * <li>00062�F��Ռ���(C)(��撲��)</li>
     * <li>00021�F�G�茤��</li>
     * <li>00061�F��茤��(A)</li>
     * <li>00061�F��茤��(B)</li>
     * <li>00021�F����̈�i�p���̈�j</li>
     * <li>00141�F��茤���i�X�^�[�g�A�b�v�j</li>
     * <li>00152�F���ʌ������i��i��Ռ���(A)�����j</li>
     * <li>00153�F���ʌ������i��i��Ռ���(B)�����j</li>
     * <li>00154�F���ʌ������i��i��Ռ���(C)�����j</li>
     * <li>00155�F���ʌ������i��i��茤��(A)�����j</li>
     * <li>00156�F���ʌ������i��i��茤��(B)�����j</li>
     * @return �`�F�b�N���X�g�^�C�v�̎���CD�i�[
     */
    public static Set getCheckListKakuteiJigyoCd(){
        // �m��^�C�v�̎���CD�i�[�̐錾
        Set kakuteiTypeSet = new HashSet();
        // ��Ռ���(C)(���)
        kakuteiTypeSet.add(IJigyoCd.JIGYO_CD_KIBAN_C_IPPAN);
        // ��Ռ���(C)(��撲��)
        kakuteiTypeSet.add(IJigyoCd.JIGYO_CD_KIBAN_C_KIKAKU);
        // �G�茤��
        kakuteiTypeSet.add(IJigyoCd.JIGYO_CD_KIBAN_HOGA);
        // ��茤��(A)
        kakuteiTypeSet.add(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_A);
        // ��茤��(B)
        kakuteiTypeSet.add(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_B);
        // ����̈�i�p���̈�j
        kakuteiTypeSet.add(IJigyoCd.JIGYO_CD_TOKUTEI_KEIZOKU);
// 2006/08/21 dyh update start
//        //���ʐ��i����
//        kakuteiTypeSet.add(IJigyoCd.JIGYO_CD_TOKUSUI);
//2007/03/13 �����F�@�폜�@��������
//        // ��茤���i�X�^�[�g�A�b�v�j
//        kakuteiTypeSet.add(IJigyoCd.JIGYO_CD_WAKATESTART);
//        // ���ʌ������i��i��Ռ���(A)�����j
//        kakuteiTypeSet.add(IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_A);
//        // ���ʌ������i��i��Ռ���(B)�����j
//        kakuteiTypeSet.add(IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_B);
//        // ���ʌ������i��i��Ռ���(C)�����j
//        kakuteiTypeSet.add(IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_C);
//        // ���ʌ������i��i��茤��(A)�����j
//        kakuteiTypeSet.add(IJigyoCd.JIGYO_CD_SHOKUSHINHI_WAKATE_A);
//        // ���ʌ������i��i��茤��(B)�����j
//        kakuteiTypeSet.add(IJigyoCd.JIGYO_CD_SHOKUSHINHI_WAKATE_B);
//2007/03/13 �����F�@�폜�@�����܂�
// 2006/08/21 dyh update start

        return kakuteiTypeSet;
    }
//  ZJP�@�ǉ������܂� 
}