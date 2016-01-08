/*======================================================================
 *    SYSTEM      : ���{�w�p�U����d�q�\���V�X�e���i�Ȋw������⏕���j
 *    Source name : OuboKyakkaSaveAction.java
 *    Description : ���发�ދp���m�F���s�A�N�V�����N���X
 *
 *    Author      : DIS.zhangjianping
 *    Date        : 2006/06/15
 *
 *    Revision history
 *    Date          Revision    Author                Description
 *    2006/06/15    V1.0        DIS.zhangjianping      �V�K�쐬
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.shozoku.teishutsuShorui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.vo.RyoikiKeikakushoInfo;
import jp.go.jsps.kaken.model.vo.RyoikiKeikakushoPk;
import jp.go.jsps.kaken.status.StatusCode;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * ���发�ދp���m�F���s�A�N�V�����N���X�B
 * �p���Ώۉ��发�ޏ����X�V�B
 * 
 * ID RCSfile="$RCSfile: OuboKyakkaSaveAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:45 $"
 */
public class OuboKyakkaSaveAction extends BaseAction {
    
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
            StatusCode.STATUS_RYOUIKISHOZOKU_UKETUKE        //�̈��\�ҏ��������@�֎�t��
    };
    
    /* (�� Javadoc)
     * @see jp.go.jsps.kaken.web.struts.BaseAction#doMainProcessing(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, jp.go.jsps.kaken.web.common.UserContainer)
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

        //------���发�ދp�����V�X�e���ԍ��̎擾
        TeisyutuForm teisyutuForm = (TeisyutuForm)form;
        RyoikiKeikakushoPk ryoikikeikakushoPk = new RyoikiKeikakushoPk();
        
        //------�L�[���
        String systemNo = teisyutuForm.getRyoikiSystemNo();
        ryoikikeikakushoPk.setRyoikiSystemNo(systemNo);

        RyoikiKeikakushoInfo ryoikiInfo = new RyoikiKeikakushoInfo();
        ryoikiInfo.setJokyoIds(JOKYO_ID);
        
        //------�L�[�������ɐ\���f�[�^�擾  
        RyoikiKeikakushoInfo ryoikikeikakushoInfo = null;
        try{
            getSystemServise(IServiceName.TEISHUTU_MAINTENANCE_SERVICE).
                    rejectOuboSyorui(container.getUserInfo(),ryoikikeikakushoPk, ryoikiInfo);
        } catch(NoDataFoundException ex){
            errors.add("�f�[�^��������DB�G���[���������܂����B",new ActionError("errors.5002"));
        } catch(ApplicationException ex){
            errors.add(ex.getMessage(),new ActionError(ex.getErrorCode(),ex.getMessage()));
        }
    
        //------�p���Ώۏ������N�G�X�g�����ɃZ�b�g
        container.setRyoikikeikakushoInfo(ryoikikeikakushoInfo);
        
        //-----��ʑJ�ځi��^�����j-----
        if (!errors.isEmpty()) {
            saveErrors(request, errors);
            return forwardFailure(mapping);
        }              
        return forwardSuccess(mapping);
    }
}