/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : �c
 *    Date        : 2007/02/03
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2007/02/03    1.0         �c�@�@�@�@�@�@�@�@�@�@�@�@�V�K
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.shinsei.validate;

import javax.servlet.http.HttpServletRequest;

import jp.go.jsps.kaken.model.vo.ShinseiDataInfo;
import jp.go.jsps.kaken.model.vo.shinsei.KenkyuKeihiInfo;
import jp.go.jsps.kaken.model.vo.shinsei.KenkyuKeihiSoukeiInfo;
import jp.go.jsps.kaken.util.StringUtil;
import jp.go.jsps.kaken.web.struts.ActionMapping;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

/**
 * �`���`�F�b�N�N���X�i���S�p�j
 * ID RCSfile="$RCSfile: ShinseiValidatorKibanWakateS.java,v $"
 * Revision="$Revision: 1.2 $"
 * Date="$Date: 2007/07/11 06:51:41 $"
 */
public class ShinseiValidatorKibanWakateS extends DefaultShinseiValidator{
    /** ����������N�����͂��邩 */
    private int NENSU = 5;
    
    /** �����o��V�X�e��MAX�l */
    private int MAX_KENKYUKEIHI = 9999999;
    
    /** �e�N�x�̌����o��(���~) */
    private int MIN_KENKYUKEIHI = 100;

    /**
     * �R���X�g���N�^
     * @param shinseiDataInfo
     * @param page
     */
    public ShinseiValidatorKibanWakateS(ShinseiDataInfo shinseiDataInfo) {
        super(shinseiDataInfo);
    }

    /**
     * �`���`�F�b�N�i���S�p�j
     * @see jp.go.jsps.kaken.web.shinsei.validate.IShinseiValidator#validate(jp.go.jsps.kaken.web.struts.ActionMapping, javax.servlet.http.HttpServletRequest)
     */
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request, int page, ActionErrors errors) {

        //�����o��̍��v���Z�o���A�ő�l�`�F�b�N���s��
        validateAndSetKeihiTotal(errors, page);
        
        return errors;
    }
    
    
    /**
     * �����o��̑��v���Z�o���Đ\�����ɃZ�b�g����B
     * @param errors ���؃G���[���������ꍇ�́Aerrors�ɒǉ����Ă����B
     * @param page   �y�[�W�ԍ��i�����؃��x���j
     */
    private void validateAndSetKeihiTotal(ActionErrors errors, int page) {
        
        //�G���[���b�Z�[�W
        String name         = null;                                              //���ږ�
        String value        = String.valueOf(MAX_KENKYUKEIHI);                   //�l
        String errKey       = "errors.maxValue";                                 //�L�[������
        String property     = "shinseidataInfo.kenkyuKeihiSoukeiInfo.keihiTotal";//�v���p�e�B��
//ADD�@START 2007-07-11 BIS ������
		//�s�ڃv���p�e�B
		String propertyRow = null;
//ADD�@END 2007-07-11 BIS ������
        
        KenkyuKeihiSoukeiInfo soukeiInfo = shinseiDataInfo.getKenkyuKeihiSoukeiInfo();
        KenkyuKeihiInfo[]     keihiInfo  = soukeiInfo.getKenkyuKeihi();
        
        //�����o��̑��v���Z�o����
        int intKeihiTotal          = 0;
        int intBihinhiTotal        = 0;
        int intShomohinhiTotal     = 0;
        int intKokunairyohiTotal   = 0;
        int intGaikokuryohiTotal   = 0;
        int intRyohiTotal          = 0;
        int intShakinTotal         = 0;
        int intSonotaTotal         = 0;
        
        //�N�x���ƂɌJ��Ԃ�
        for(int i=0; i<NENSU; i++){
            //�P�N�x
            int intBihinhi        = StringUtil.parseInt(keihiInfo[i].getBihinhi());
            int intShomohinhi     = StringUtil.parseInt(keihiInfo[i].getShomohinhi());
            int intKokunairyohi   = StringUtil.parseInt(keihiInfo[i].getKokunairyohi());
            int intGaikokuryohi   = StringUtil.parseInt(keihiInfo[i].getGaikokuryohi());
            int intRyohi          = StringUtil.parseInt(keihiInfo[i].getRyohi());
            int intShakin         = StringUtil.parseInt(keihiInfo[i].getShakin());
            int intSonota         = StringUtil.parseInt(keihiInfo[i].getSonota());
            //�P�N�x���v
            int intKeihi          = intBihinhi
                                   + intShomohinhi
                                   + intKokunairyohi
                                   + intGaikokuryohi
                                   + intRyohi
                                   + intShakin
                                   + intSonota
                                   ;
            //�P�N�x���v���I�u�W�F�N�g�ɃZ�b�g����
            keihiInfo[i].setKeihi(String.valueOf(intKeihi)); 

            int rowIndex = i+1;
//ADD�@START 2007-07-11 BIS ������
			//�s�ڂ�ݒ肷��
			propertyRow = property + (rowIndex - 1);
//ADD�@END 2007-07-11 BIS ������
            name = "�����o�� " + rowIndex + "�s��";
            //�P�N�x���v�̌`���`�F�b�N
            if(MAX_KENKYUKEIHI < intKeihi){
                ActionError error = new ActionError(errKey,name,value);
                errors.add(property, error);
//ADD�@START 2007-07-11 BIS ������
				errors.add(propertyRow, error);
//ADD�@END 2007-07-11 BIS ������
            }

            // �`�F�b�N�͈ꎞ�ۑ����ɂ͍s��Ȃ�
            if (page >= 2) {
                //1�N�ڂ͐V�K�Ɠ������[���A10���~�ȏ�łȂ���΂Ȃ�Ȃ�
                if (i == 0) {
                    if (MIN_KENKYUKEIHI > intKeihi) {
                        ActionError error = new ActionError("errors.5031",
                                name, String.valueOf(MIN_KENKYUKEIHI));
                        errors.add(property, error);
//ADD�@START 2007-07-11 BIS ������
        				errors.add(propertyRow, error);
//ADD�@END 2007-07-11 BIS ������
                    }

                //2�N�ڎ�
                } else if (i == 1) {
                    // 10���~�ȏ�łȂ���΂Ȃ�Ȃ�
                    if (MIN_KENKYUKEIHI > intKeihi) {
                        ActionError error = new ActionError("errors.5031",
                                name, String.valueOf(MIN_KENKYUKEIHI));
                        errors.add(property, error);
//ADD�@START 2007-07-11 BIS ������
        				errors.add(propertyRow, error);
//ADD�@END 2007-07-11 BIS ������
                    }
                //3�N�ځA4�N�ڎ�
                } else if (i == 2 || i == 3) {
                    //10���~�ȏ�łȂ���΂Ȃ�Ȃ�
                    if (MIN_KENKYUKEIHI > intKeihi) {
                        ActionError error = new ActionError("errors.5031",
                                name, String.valueOf(MIN_KENKYUKEIHI));
                        errors.add(property, error);
//ADD�@START 2007-07-11 BIS ������
        				errors.add(propertyRow, error);
//ADD�@END 2007-07-11 BIS ������
                    }
                //��L�ȊO�̔N�ڎ�
                } else {
                    //10���~�ȏ�łȂ���΂Ȃ�Ȃ�
                    if (MIN_KENKYUKEIHI > intKeihi) {
                        ActionError error = new ActionError("errors.5031",
                                name, String.valueOf(MIN_KENKYUKEIHI));
                        errors.add(property, error);
//ADD�@START 2007-07-11 BIS ������
        				errors.add(propertyRow, error);
//ADD�@END 2007-07-11 BIS ������
                    }
                }
            }

            //���v
            intKeihiTotal          = intKeihiTotal        + intKeihi;
            intBihinhiTotal        = intBihinhiTotal      + StringUtil.parseInt(keihiInfo[i].getBihinhi());
            intShomohinhiTotal     = intShomohinhiTotal   + StringUtil.parseInt(keihiInfo[i].getShomohinhi());
            intKokunairyohiTotal   = intKokunairyohiTotal + StringUtil.parseInt(keihiInfo[i].getKokunairyohi());
            intGaikokuryohiTotal   = intGaikokuryohiTotal + StringUtil.parseInt(keihiInfo[i].getGaikokuryohi());
            intRyohiTotal          = intRyohiTotal        + StringUtil.parseInt(keihiInfo[i].getRyohi());
            intShakinTotal         = intShakinTotal       + StringUtil.parseInt(keihiInfo[i].getShakin());
            intSonotaTotal         = intSonotaTotal       + StringUtil.parseInt(keihiInfo[i].getSonota());
        }

        //���v���Z�b�g����
        soukeiInfo.setKeihiTotal(String.valueOf(intKeihiTotal));
        soukeiInfo.setBihinhiTotal(String.valueOf(intBihinhiTotal));
        soukeiInfo.setShomohinhiTotal(String.valueOf(intShomohinhiTotal));
        soukeiInfo.setKokunairyohiTotal(String.valueOf(intKokunairyohiTotal));
        soukeiInfo.setGaikokuryohiTotal(String.valueOf(intGaikokuryohiTotal));
        soukeiInfo.setRyohiTotal(String.valueOf(intRyohiTotal));
        soukeiInfo.setShakinTotal(String.valueOf(intShakinTotal));
        soukeiInfo.setSonotaTotal(String.valueOf(intSonotaTotal));
        
        
        //-----���v�̌`���`�F�b�N-----        
        if(MAX_KENKYUKEIHI < intKeihiTotal){
            name = "�����o�� ���v";
            ActionError error = new ActionError(errKey,name,value);
            errors.add(property, error);
        }
        if(MAX_KENKYUKEIHI < intBihinhiTotal){
            name = "�ݔ����i�� ���v";
            ActionError error = new ActionError(errKey,name,value);
            errors.add(property, error);
        }
        if(MAX_KENKYUKEIHI < intShomohinhiTotal){
            name = "���Օi�� ���v";
            ActionError error = new ActionError(errKey,name,value);
            errors.add(property, error);
        }
        if(MAX_KENKYUKEIHI < intKokunairyohiTotal){
            name = "�������� ���v";
            ActionError error = new ActionError(errKey,name,value);
            errors.add(property, error);
        }
        if(MAX_KENKYUKEIHI < intGaikokuryohiTotal){
            name = "�O������ ���v";
            ActionError error = new ActionError(errKey,name,value);
            errors.add(property, error);
        }
        if(MAX_KENKYUKEIHI < intRyohiTotal){
            name = "���� ���v";
            ActionError error = new ActionError(errKey,name,value);
            errors.add(property, error);
        }
        if(MAX_KENKYUKEIHI < intShakinTotal){
            name = "�Ӌ��� ���v";
            ActionError error = new ActionError(errKey,name,value);
            errors.add(property, error);
        }       
        if(MAX_KENKYUKEIHI < intSonotaTotal){
            name = "���̑� ���v";
            ActionError error = new ActionError(errKey,name,value);
            errors.add(property, error);
        }       
        
    }
}