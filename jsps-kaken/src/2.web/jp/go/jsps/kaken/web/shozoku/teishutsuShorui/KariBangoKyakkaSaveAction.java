/*======================================================================
 *    SYSTEM      : ���{�w�p�U����d�q�\���V�X�e���i�Ȋw������⏕���j
 *    Source name : KariBangoKyakkaSaveAction.java
 *    Description : ���̈�ԍ����s�p���m�F���s�A�N�V����
 *
 *    Author      : DIS.lY
 *    Date        : 2006/06/14
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2006/06/14    V1.0        DIS.lY         �V�K�쐬
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.shozoku.teishutsuShorui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.vo.RyoikiKeikakushoPk;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * ���̈�ԍ����s�p���m�F���s�A�N�V����
 * ID RCSfile="$RCSfile: KariBangoKyakkaSaveAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:45 $"
 */
public class KariBangoKyakkaSaveAction extends BaseAction {

    /**
     * Action�N���X�̎�v�ȋ@�\����������B
     * �߂�l�Ƃ��āA���̑J�ڐ��ActionForward�^�ŕԂ���B
     */
    public ActionForward doMainProcessing(
            ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response,
            UserContainer container)
            throws ApplicationException {

        //-----ActionErrors�̐錾�i��^�����j-----
        ActionErrors errors = new ActionErrors();

        //------�p���Ώۉ��̈���V�X�e���ԍ��̎擾
        TeisyutuForm teisyutuForm = (TeisyutuForm)form;

        //------�p���ΏۃV�X�e���ԍ��̎擾
        RyoikiKeikakushoPk ryoikiPk = new RyoikiKeikakushoPk();
        //------�L�[���  
        ryoikiPk.setRyoikiSystemNo(teisyutuForm.getRyoikiSystemNo());
        
        //------�L�[�������ɐ\�������X�V    kakunin
        try{
            getSystemServise(IServiceName.TEISHUTU_MAINTENANCE_SERVICE).rejectKariBangoHakko(
                    container.getUserInfo(), ryoikiPk);
        }catch (NoDataFoundException e) {
            errors.add("�f�[�^��������DB�G���[���������܂����B",new ActionError("errors.4004"));
        }
        catch (ApplicationException ex) {
            
            errors.add(ex.getMessage(),new ActionError(ex.getErrorCode(),ex.getMessage()));
            
        }
        //      -----��ʑJ�ځi��^�����j-----
        if (!errors.isEmpty()) {
            saveErrors(request, errors);
            return forwardFailure(mapping);
        }
        return forwardSuccess(mapping);
    }
}