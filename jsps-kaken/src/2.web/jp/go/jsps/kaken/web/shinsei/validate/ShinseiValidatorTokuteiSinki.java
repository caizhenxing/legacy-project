/*======================================================================
 *    SYSTEM      : ���{�w�p�U����d�q�\���V�X�e���i�Ȋw������⏕���j
 *    Source name : ShinseiValidatorTokuteiSinki.java
 *    Description : ���������(����̈挤��(�V�K�̈�))�̃`�F�b�N
 *
 *    Author      : �c�c
 *    Date        : 2006/06/21
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2006/06/14    v1.0        �c�c                        �V�K�쐬
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.shinsei.validate;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

import jp.go.jsps.kaken.model.IShinseiMaintenance;
import jp.go.jsps.kaken.model.vo.ShinseiDataInfo;
import jp.go.jsps.kaken.model.vo.shinsei.DaihyouInfo;
import jp.go.jsps.kaken.model.vo.shinsei.KenkyuKeihiInfo;
import jp.go.jsps.kaken.model.vo.shinsei.KenkyuKeihiSoukeiInfo;
import jp.go.jsps.kaken.model.vo.shinsei.KenkyuSoshikiKenkyushaInfo;
import jp.go.jsps.kaken.util.StringUtil;
import jp.go.jsps.kaken.web.struts.ActionMapping;

/**
 * ���������(����̈挤��(�V�K�̈�))�̃`�F�b�N
 */
public class ShinseiValidatorTokuteiSinki extends DefaultShinseiValidator {

    /**
     * �R���X�g���N�^
     * @param shinseiDataInfo
     * @param page
     */
    public ShinseiValidatorTokuteiSinki(ShinseiDataInfo shinseiDataInfo) {
        super(shinseiDataInfo);
    }


    /**
     * �`���`�F�b�N�i����̈�p�j
     * @see jp.go.jsps.kaken.web.shinsei.validate.IShinseiValidator#validate(jp.go.jsps.kaken.web.struts.ActionMapping, javax.servlet.http.HttpServletRequest)
     */
    public ActionErrors validate(
            ActionMapping mapping,
            HttpServletRequest request,
            int page,
            ActionErrors errors) {

        validateAndSetKeihiTotal(errors, request, page);    //�����o��̍��v���Z�o���A�ő�l�`�F�b�N���s��
        validateKenkyuSoshiki(errors, page);                //�����g�D�\�̌`���`�F�b�N���s��
        countKenkyushaNinzu(errors, page);                  //������
        validateKomokuNo(errors);                           //�������ڔԍ��̐��K�\���`�F�b�N

        return errors;
    }

    /**
     * �����g�D�\���A�S�����Ґ��A���@�ւ̌����Ґ����Z�o����B
     * �܂��A�����o��̍��v���\���f�[�^�̌����o��i1�N�ځj�Ɠ����l�ɂȂ邩���`�F�b�N����B
     * ����Ă����ꍇ�� ValidationException ���X���[����B
     * @param errors ���؃G���[���������ꍇ�́Aerrors�ɒǉ����Ă����B
     * @param page   �y�[�W�ԍ��i�����؃��x���j
     */
    private void countKenkyushaNinzu(ActionErrors errors, int page) {
        int keihiTotal = 0;                                                         //�����g�D�\�̌����o��v
        List kenkyuSoshikiList = shinseiDataInfo.getKenkyuSoshikiInfoList();        //�����g�D�\�̑�\�ҔN���\���f�[�^�ɂ��Z�b�g����

        //������\�҂̏����@�փR�[�h���擾����
        DaihyouInfo daihyouInfo = shinseiDataInfo.getDaihyouInfo();
        String daihyouKikanCd = daihyouInfo.getShozokuCd();

        //������\�҂̔N��A���@�ւ̌����Ґ����擾����
        int kenkyuNinzu  = kenkyuSoshikiList.size();
        int takikanNinzu = 0;
        int kyoryokushaNinzu = 0;

        for(int i=0; i<kenkyuNinzu; i++){
            KenkyuSoshikiKenkyushaInfo kenkyushainfo = (KenkyuSoshikiKenkyushaInfo)kenkyuSoshikiList.get(i);

            if(i==0){                                                       //��\�ҁi�C���f�b�N�X�l���u0�v�̂��́j
                daihyouInfo.setNenrei(kenkyushainfo.getNenrei());           //��\�҂̔N���\���f�[�^���ɃZ�b�g����
            }else if("2".equals(kenkyushainfo.getBuntanFlag())){            //���S��
                if( daihyouKikanCd != null &&                               //��\�҂̏����@�փR�[�h�ƈႤ�ꍇ�̓J�E���g�v���X�P
                   !daihyouKikanCd.equals(kenkyushainfo.getShozokuCd()))
                {
                    takikanNinzu++;
                }
            } else {                                                        //���͎�
                kyoryokushaNinzu++;
            }
            keihiTotal = keihiTotal + StringUtil.parseInt((String)kenkyushainfo.getKeihi());
        }
        shinseiDataInfo.setKenkyuNinzu(String.valueOf(kenkyuNinzu-kyoryokushaNinzu));   //�����Ґ�
        shinseiDataInfo.setTakikanNinzu(String.valueOf(takikanNinzu));                  //���@�ւ̌����Ґ�
        shinseiDataInfo.setKyoryokushaNinzu(String.valueOf(kyoryokushaNinzu));          //�������͎Ґ�
        
        //�{�o�^
        if(page >= 2){
            // ���S���̗L��:�u�L�v���u�����g�D�\�v�́u���S�ҁv�̋L��������A���A�u�����o��v�ɓ��͂����邱�Ɓi������΃G���[�j�B
            if(Integer.parseInt(shinseiDataInfo.getKenkyuNinzu()) == 1 &&
                "1".equals(shinseiDataInfo.getBuntankinFlg())){
                String property = "shinseiDataInfo.kenkyuSoshikiInfoList.keihi";
                ActionError error = new ActionError("errors.5055");
                errors.add(property, error);
//				<!-- ADD�@START 2007/07/09 BIS ���� -->	
				shinseiDataInfo.getErrorsMap().put(property,"���S���̗L��:�u�L�v���u�����g�D�\�v�́u���S�ҁv�̋L��������A���A�u�����o��v�ɓ��͂����邱�Ɓi������΃G��");
				shinseiDataInfo.setErrorsMap( shinseiDataInfo.getErrorsMap());
//				<!-- ADD�@END�@ 2007/07/09 BIS ���� -->	
            }
            //�����o��̍��v���\���f�[�^��1�N�ڌ����o��Ɠ����ɂȂ��Ă��邩�`�F�b�N����
            if(keihiTotal != 
               StringUtil.parseInt(shinseiDataInfo.getKenkyuKeihiSoukeiInfo().getKenkyuKeihi6()[0].getKeihi())) {
                String property = "shinseiDataInfo.kenkyuKeihiSoukeiInfo.kenkyuKeihi6";
                ActionError error = new ActionError("errors.5017");
                errors.add(property, error);
//				<!-- ADD�@START 2007/07/09 BIS ���� -->	
				shinseiDataInfo.getErrorsMap().put(property,"�����o��(1�N��)�������g�D�\�̌����o��v�ƈႢ�܂��B ");
				shinseiDataInfo.setErrorsMap( shinseiDataInfo.getErrorsMap());
//				<!-- ADD�@END�@ 2007/07/09 BIS ���� -->	
            }
        }
    }      
    
    /**
     * �����o��̑��v���Z�o���Đ\�����ɃZ�b�g����B
     * @param errors ���؃G���[���������ꍇ�́Aerrors�ɒǉ����Ă����B
     * @param page   �y�[�W�ԍ��i�����؃��x���j
     */
    private void validateAndSetKeihiTotal(
            ActionErrors errors,
            HttpServletRequest request,
            int page){
        
        // �����ǂ��I����
        if(IShinseiMaintenance.CHECK_ON.equals(request.getParameter("shinseiDataInfo.chouseiFlg"))) {
            shinseiDataInfo.setChouseiFlg(IShinseiMaintenance.CHECK_ON);//�I��
        } else {
            shinseiDataInfo.setChouseiFlg(IShinseiMaintenance.CHECK_OFF);//�I�t
        }             

        //�G���[���b�Z�[�W
        String name         = null;                                                 //���ږ�
        String value        = String.valueOf(IShinseiMaintenance.MAX_KENKYUKEIHI);  //�l
        String errKey       = "errors.maxValue";                                    //�L�[������
        String property     = "shinseiDataInfo.kenkyuKeihiSoukeiInfo.keihiTotal";   //�v���p�e�B��
//ADD�@START 2007-07-13 BIS ������
		//�s�ڃv���p�e�B
		String propertyRow = null;
//ADD�@END 2007-07-13 BIS ������

        KenkyuKeihiSoukeiInfo soukeiInfo = shinseiDataInfo.getKenkyuKeihiSoukeiInfo();
        KenkyuKeihiInfo[]     keihiInfo  = soukeiInfo.getKenkyuKeihi6();

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
        for(int i=0; i<IShinseiMaintenance.NENSU_TOKUTEI_SINNKI; i++){
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
//ADD�@START 2007-07-13 BIS ������
			//�s�ڂ�ݒ肷��
			propertyRow = property + (rowIndex - 1);
//ADD�@END 2007-07-13 BIS ������
            name = "�����o�� " + rowIndex + "�s��";
            //�P�N�x���v�̌`���`�F�b�N
            if(IShinseiMaintenance.MAX_KENKYUKEIHI < intKeihi){
                ActionError error = new ActionError(errKey,name,value);
                errors.add(property, error);
//ADD�@START 2007-07-13 BIS ������
				errors.add(propertyRow, error);
//ADD�@END 2007-07-13 BIS ������
            }

            //�`�F�b�N�͈ꎞ�ۑ����ɂ͍s��Ȃ�
            if(page >= 2){
                switch(i){
                    // �P�N��
                    case(0):
                        //(0�܂���10���~�ȏ�)�ȊO��
                        if(IShinseiMaintenance.MIN_KENKYUKEIHI > intKeihi && intKeihi != 0){
                            ActionError error = new ActionError(
                                    "errors.5031",
                                    name,
                                    String.valueOf(IShinseiMaintenance.MIN_KENKYUKEIHI)
                                    );
                            errors.add(property, error);
//ADD�@START 2007-07-13 BIS ������
    						errors.add(propertyRow, error);
//ADD�@END 2007-07-13 BIS ������
                        }
                        break;
                    //�Q�N��
                    case(1):
                        //(0�܂���10���~�ȏ�)�ȊO��
                        if(IShinseiMaintenance.MIN_KENKYUKEIHI > intKeihi && intKeihi != 0){
                            ActionError error = new ActionError(
                                    "errors.5031",
                                    name,
                                    String.valueOf(IShinseiMaintenance.MIN_KENKYUKEIHI)
                                    );
                            errors.add(property, error);
//ADD�@START 2007-07-13 BIS ������
    						errors.add(propertyRow, error);
//ADD�@END 2007-07-13 BIS ������
                        }
                        break;
                    //�R�N��
                    case(2):
                        //�O�N�x��0�~�̏ꍇ�ɂ�0�~
                        if("0".equals(keihiInfo[i-1].getKeihi()) && intKeihi != 0){
                            ActionError error = new ActionError(
                                    "errors.5032",
                                    name + " �O�N�x",
                                    "0�~",
                                    ""
                                    );
                            errors.add(property, error);
//ADD�@START 2007-07-13 BIS ������
    						errors.add(propertyRow, error);
//ADD�@END 2007-07-13 BIS ������
                        }
                        //(0�܂���10���~�ȏ�)�ȊO��
                        else if(IShinseiMaintenance.MIN_KENKYUKEIHI > intKeihi && intKeihi != 0){
                            ActionError error = new ActionError(
                                    "errors.5031",
                                    name,
                                    String.valueOf(IShinseiMaintenance.MIN_KENKYUKEIHI)
                                    );
                            errors.add(property, error);
//ADD�@START 2007-07-13 BIS ������
    						errors.add(propertyRow, error);
//ADD�@END 2007-07-13 BIS ������
                        }
                        break;
                    //�S�N��
                    case(3):
                        //�O�N�x��0�~�̏ꍇ�ɂ�0�~
                        if("0".equals(keihiInfo[i-1].getKeihi()) && intKeihi != 0){
                            ActionError error = new ActionError(
                                    "errors.5032",
                                    name + " �O�N�x",
                                    "0�~",
                                    ""
                                    );
                            errors.add(property, error);
//ADD�@START 2007-07-13 BIS ������
    						errors.add(propertyRow, error);
//ADD�@END 2007-07-13 BIS ������
                        }
                        //(0�܂���10���~�ȏ�)�ȊO��
                        else if(IShinseiMaintenance.MIN_KENKYUKEIHI > intKeihi && intKeihi != 0){
                            ActionError error = new ActionError(
                                    "errors.5031",
                                    name,
                                    String.valueOf(IShinseiMaintenance.MIN_KENKYUKEIHI)
                                    );
                            errors.add(property, error);
//ADD�@START 2007-07-13 BIS ������
    						errors.add(propertyRow, error);
//ADD�@END 2007-07-13 BIS ������
                        }
                        break;
                    //�T�N��
                    case(4):
                        //�O�N�x��0�~�̏ꍇ�ɂ�0�~
                        if("0".equals(keihiInfo[i-1].getKeihi()) && intKeihi != 0){
                            ActionError error = new ActionError(
                                    "errors.5032",
                                    name + " �O�N�x",
                                    "0�~",
                                    ""
                                    );
                            errors.add(property, error);
//ADD�@START 2007-07-13 BIS ������
    						errors.add(propertyRow, error);
//ADD�@END 2007-07-13 BIS ������
                        }
                        //(0�܂���10���~�ȏ�)�ȊO��
                        else if(IShinseiMaintenance.MIN_KENKYUKEIHI > intKeihi && intKeihi != 0){
                            ActionError error = new ActionError(
                                    "errors.5031",
                                    name,
                                    String.valueOf(IShinseiMaintenance.MIN_KENKYUKEIHI)
                                    );
                            errors.add(property, error);
//ADD�@START 2007-07-13 BIS ������
    						errors.add(propertyRow, error);
//ADD�@END 2007-07-13 BIS ������
                        }
                        break;
                    //�U�N��
                    case(5):
                        //�O�N�x��0�~�̏ꍇ�ɂ�0�~
                        if("0".equals(keihiInfo[i-1].getKeihi()) && intKeihi != 0){
                            ActionError error = new ActionError(
                                    "errors.5032",
                                    name + " �O�N�x",
                                    "0�~",
                                    ""
                                    );
                            errors.add(property, error);
//ADD�@START 2007-07-13 BIS ������
    						errors.add(propertyRow, error);
//ADD�@END 2007-07-13 BIS ������
                        }
                        //(0�܂���10���~�ȏ�)�ȊO��
                        else if(IShinseiMaintenance.MIN_KENKYUKEIHI > intKeihi && intKeihi != 0){
                            ActionError error = new ActionError(
                                    "errors.5031",
                                    name,
                                    String.valueOf(IShinseiMaintenance.MIN_KENKYUKEIHI)
                                    );
                            errors.add(property, error);
//ADD�@START 2007-07-13 BIS ������
    						errors.add(propertyRow, error);
//ADD�@END 2007-07-13 BIS ������
                        }
                        break;   
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

        //�͈͂̃`�F�b�N�͈ꎞ�ۑ����ɂ͍s��Ȃ�
        if(page == 2){
            if(IShinseiMaintenance.MAX_KENKYUKEIHI_GOKEI < intKeihiTotal){
                name = "�����o�� ���v";
                ActionError error = new ActionError(
                        "errors.5033",
                        name,
                        String.valueOf(IShinseiMaintenance.MAX_KENKYUKEIHI_GOKEI)
                        );
                errors.add(property, error);
            }
        }

        if(IShinseiMaintenance.MAX_KENKYUKEIHI < intKeihiTotal){
            name = "�����o�� ���v";
            ActionError error = new ActionError(errKey,name,value);
            errors.add(property, error);
        }
        if(IShinseiMaintenance.MAX_KENKYUKEIHI < intBihinhiTotal){
            name = "�ݔ����i�� ���v";
            ActionError error = new ActionError(errKey,name,value);
            errors.add(property, error);
        }
        if(IShinseiMaintenance.MAX_KENKYUKEIHI < intShomohinhiTotal){
            name = "���Օi�� ���v";
            ActionError error = new ActionError(errKey,name,value);
            errors.add(property, error);
        }
        if(IShinseiMaintenance.MAX_KENKYUKEIHI < intKokunairyohiTotal){
            name = "�������� ���v";
            ActionError error = new ActionError(errKey,name,value);
            errors.add(property, error);
        }
        if(IShinseiMaintenance.MAX_KENKYUKEIHI < intGaikokuryohiTotal){
            name = "�O������ ���v";
            ActionError error = new ActionError(errKey,name,value);
            errors.add(property, error);
        }
        if(IShinseiMaintenance.MAX_KENKYUKEIHI < intRyohiTotal){
            name = "���� ���v";
            ActionError error = new ActionError(errKey,name,value);
            errors.add(property, error);
        }
        if(IShinseiMaintenance.MAX_KENKYUKEIHI < intShakinTotal){
            name = "�Ӌ��� ���v";
            ActionError error = new ActionError(errKey,name,value);
            errors.add(property, error);
        }
        if(IShinseiMaintenance.MAX_KENKYUKEIHI < intSonotaTotal){
            name = "���̑� ���v";
            ActionError error = new ActionError(errKey,name,value);
            errors.add(property, error);
        }
    }

    /**
     * �������ڔԍ��̐��K�\���`�F�b�N
     * 
     * @param errors ���؃G���[���������ꍇ�́Aerrors�ɒǉ����Ă����B
     */
    private void validateKomokuNo(ActionErrors errors){
        
        //�������ڔԍ�
        String komokuNo = shinseiDataInfo.getRyouikiKoumokuNo();
        
        //�R�������̃`�F�b�N
        if(komokuNo != null && komokuNo.length() == 3){
            char komokuNoLetter = komokuNo.charAt(0);
            String komokuNoDigit = komokuNo.substring(1);

            //�A���t�@�x�b�g�P�����i�啶���j�łȂ���΂Ȃ�Ȃ�
            if (!StringUtil.isCapitalLetter(komokuNoLetter)) {
                ActionError error = new ActionError("errors.2001", "�������ڔԍ�");
                errors.add("shinseiDataInfo.ryouikiKoumokuNo", error);
                return;
            }

            //�P���ڂ��u�w�v�܂��u�x�v�̏ꍇ�C�Q�`�R���ڂ́C�O�O�̂�
            if (komokuNoLetter == 'X' || komokuNoLetter == 'Y') {
                if (!"00".equals(komokuNoDigit)) {
                    ActionError error = new ActionError("errors.2001", "�������ڔԍ�");
                    errors.add("shinseiDataInfo.ryouikiKoumokuNo", error);
                    return;
                }

            //�P���ڂ�����ȊO�̃A���t�@�x�b�g�̏ꍇ�C�Q�`�R���ڂ́C�O�P�`�X�X�̂�    
            } else {
                if (!StringUtil.isDigit(komokuNoDigit)
                        || StringUtil.parseInt(komokuNoDigit) < 1
                        || StringUtil.parseInt(komokuNoDigit) > 99) {
                    ActionError error = new ActionError("errors.2001", "�������ڔԍ�");
                    errors.add("shinseiDataInfo.ryouikiKoumokuNo", error);
                    return;
                }
            }
        }
    }
}