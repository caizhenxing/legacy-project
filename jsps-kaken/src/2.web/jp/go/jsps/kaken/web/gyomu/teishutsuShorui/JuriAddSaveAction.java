/*======================================================================
 *    SYSTEM      : ���{�w�p�U����d�q�\���V�X�e���i�Ȋw������⏕���j
 *    Source name : JuriAddSaveAction.java
 *    Description : �󗝓o�^�����s�A�N�V�����N���X
 *
 *    Author      : DIS.jzx
 *    Date        : 2006/06/13
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2006/06/13    V1.0        DIS.jzx        �V�K�쐬
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.gyomu.teishutsuShorui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.vo.RyoikiKeikakushoInfo;
import jp.go.jsps.kaken.status.StatusCode;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * �󗝓o�^�����s�A�N�V�����N���X
 * ID RCSfile="$RCSfile: JuriAddSaveAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:55 $"
 */
public class JuriAddSaveAction extends BaseAction {

    /** �����@�֎�t��(��ID:03)�ȍ~�̏�ID */
    private static String[] JOKYO_ID = new String[]{
            StatusCode.STATUS_GAKUSIN_SHORITYU,             //�w�U��t��
            StatusCode.STATUS_GAKUSIN_JYURI,                //�w�U��
            StatusCode.STATUS_GAKUSIN_FUJYURI,              //�w�U�s��
            StatusCode.STATUS_SHINSAIN_WARIFURI_SHORIGO,    //�R��������U�菈����
            StatusCode.STATUS_WARIFURI_CHECK_KANRYO,        //����U��`�F�b�N����
            StatusCode.STATUS_1st_SHINSATYU,                //�ꎟ�R����
            StatusCode.STATUS_1st_SHINSA_KANRYO,            //�ꎟ�R���F����
            StatusCode.STATUS_2nd_SHINSA_KANRYO,            //�񎟐R������
    };
    public ActionForward doMainProcessing(
            ActionMapping mapping, 
            ActionForm form,
            HttpServletRequest request, 
            HttpServletResponse response, 
            UserContainer container)
            throws ApplicationException {

        //-----ActionErrors�̐錾�i��^�����j-----
        ActionErrors errors = new ActionErrors();
        
        JuriGaiyoForm juriGaiyoForm = (JuriGaiyoForm) form; // �t�H�[���擾

        //-------�� VO�Ƀf�[�^���Z�b�g����B
        String juriKekka = juriGaiyoForm.getJuriKekka();
        RyoikiKeikakushoInfo ryoikiInfo =new RyoikiKeikakushoInfo ();
        
        ryoikiInfo.setRyoikiSystemNo(juriGaiyoForm.getSystemNo());
        ryoikiInfo.setJokyoIds(JOKYO_ID);
        if (log.isDebugEnabled()) {
            log.debug("�󗝌��ʁ@�o�^��� ");
        }
        try{
        	//�󗝁A�s�󗝂̎��s
            getSystemServise(IServiceName.TEISHUTU_MAINTENANCE_SERVICE)
                .registTeisyutusyoJuri(container.getUserInfo(),ryoikiInfo,juriKekka); 
 
        }catch(NoDataFoundException e){
            if(e.getMessage().equals("�`���`�F�b�N")){
                    errors.add("�`���`�F�b�N���G���[�Ƃ���B",new ActionError(e.getErrorCode()));
            }else{
                errors.add("�Y���f�[�^�͂���܂���B",new ActionError("errors.5002"));
            }
        }catch (ApplicationException ex) {
            errors.add("�󗝓o�^�ŃG���[���������܂����B", new ActionError("errors.4002"));
        }
        
        //-----��ʑJ�ځi��^�����j-----
        if (!errors.isEmpty()) {
            saveErrors(request, errors);
            return forwardFailure(mapping);
        }

        return forwardSuccess(mapping);
    }
}