/*======================================================================
 *    SYSTEM      : ���{�w�p�U����d�q�\���V�X�e���i�Ȋw������⏕���j
 *    Source name : TeisyutuListAction.java
 *    Description : ��o�m�F�i����̈挤���i�V�K�̈�j�j�A�N�V����
 *
 *    Author      : DIS.gongXB
 *    Date        : 2006/06/14
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2006/06/14    V1.0        DIS.gongXB     �V�K�쐬
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.shozoku.teishutsuShorui;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.ISystemServise;
import jp.go.jsps.kaken.model.common.IJigyoCd;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.vo.RyoikiKeikakushoInfo;
import jp.go.jsps.kaken.status.StatusCode;
import jp.go.jsps.kaken.web.common.IConstants;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * ��o�m�F�i����̈挤���i�V�K�̈�j�j�A�N�V����
 * ID RCSfile="$RCSfile: TeisyutuListAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:45 $"
 */
public class TeisyutuListAction extends BaseAction {
    
    /** �����@�֎�t��(��ID:03)�ȍ~�̏�ID */
    private static String[] JOKYO_ID = new String[]{
            StatusCode.STATUS_GAKUSIN_SHORITYU,        //�w�U��t��
            StatusCode.STATUS_GAKUSIN_JYURI,          //�w�U��
            StatusCode.STATUS_GAKUSIN_FUJYURI,        //�w�U�s��
            StatusCode.STATUS_SHINSAIN_WARIFURI_SHORIGO,      //�R��������U�菈����
            StatusCode.STATUS_WARIFURI_CHECK_KANRYO,         //����U��`�F�b�N����
            StatusCode.STATUS_1st_SHINSATYU,            //�ꎟ�R����
            StatusCode.STATUS_1st_SHINSA_KANRYO,           //�ꎟ�R���F����
            StatusCode.STATUS_2nd_SHINSA_KANRYO,             //�񎟐R������
            StatusCode.STATUS_RYOUIKISHOZOKU_UKETUKE  //�̈��\�ҏ��������@�֎�t��
    };
    
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

        RyoikiKeikakushoInfo ryoikiInfo = new RyoikiKeikakushoInfo();
        ryoikiInfo.setShozokuCd(container.getUserInfo().getShozokuInfo().getShozokuCd());
        
        ryoikiInfo.setJigyoCd(IJigyoCd.JIGYO_CD_TOKUTEI_SINKI);        
        ryoikiInfo.setJokyoIds(JOKYO_ID);
        
        List    result=null;
        ISystemServise servise = getSystemServise(IServiceName.TEISHUTU_MAINTENANCE_SERVICE);
        try{
            result=servise.searchTeisyutuKakuninList(container.getUserInfo(),ryoikiInfo);
        }catch(NoDataFoundException e){
            result=new ArrayList();
        }
        
        //      -----��ʑJ�ځi��^�����j-----
        if (!errors.isEmpty()) {
            saveErrors(request, errors);
            return forwardFailure(mapping);
        }
        //�������ʂ��t�H�[���ɃZ�b�g����B
        request.setAttribute(IConstants.RESULT_INFO, result);

        return forwardSuccess(mapping);
    }
}