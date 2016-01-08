/*======================================================================
 *    SYSTEM      : ���{�w�p�U����d�q�\���V�X�e���i�Ȋw������⏕���j
 *    Source name : KeikakuTyosyoListAction.java
 *    Description : �����v�撲���ꗗ�A�N�V����
 *
 *    Author      : DIS.dyh
 *    Date        : 2006/06/14
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2006/06/14    V1.0        DIS.dyh        �V�K�쐬
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.shozoku.teishutsuShorui;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import jp.go.jsps.kaken.model.ISystemServise;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.vo.ShinseiSearchInfo;
import jp.go.jsps.kaken.status.StatusCode;
import jp.go.jsps.kaken.util.StringUtil;
import jp.go.jsps.kaken.web.common.IConstants;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

/**
 * �����v�撲���ꗗ�A�N�V����
 * @author DIS.dyh
 * ID RCSfile="$RCSfile: KeikakuTyosyoListAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:45 $"
 */
public class KeikakuTyosyoListAction extends BaseAction {

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

        // ����������ݒ�
        TeisyutuForm teisyotuForm = (TeisyutuForm)form;
        ShinseiSearchInfo searchInfo = new ShinseiSearchInfo();

        List resultList = null;
        if(StringUtil.isBlank(teisyotuForm.getKariryoikiNo())){
            errors.add("�����v�撲���ꗗ�f�[�^",new ActionError("errors.5040",
                    new String[]{"�����v�撲���ꗗ"}));
        }else{
            searchInfo.setRyouikiNo(teisyotuForm.getKariryoikiNo());
            String[] jokyoIds = new String[]{
                    StatusCode.STATUS_GAKUSIN_SHORITYU,         //�w�U��t��:04
                    StatusCode.STATUS_GAKUSIN_JYURI,            //�w�U��:06
                    StatusCode.STATUS_GAKUSIN_FUJYURI,          //�w�U�s��:07
                    StatusCode.STATUS_SHINSAIN_WARIFURI_SHORIGO,//�R��������U�菈����:08
                    StatusCode.STATUS_WARIFURI_CHECK_KANRYO,    //����U��`�F�b�N����:09
                    StatusCode.STATUS_1st_SHINSATYU,            //�ꎟ�R����:10
                    StatusCode.STATUS_1st_SHINSA_KANRYO,        //�ꎟ�R���F����:11
                    StatusCode.STATUS_2nd_SHINSA_KANRYO,        //�񎟐R������:12
                    StatusCode.STATUS_RYOUIKISHOZOKU_UKETUKE};  //�̈��\�ҏ��������@�֎�t��:24
            searchInfo.setJokyoId(jokyoIds);
            
            // �T�[�o�T�[�r�X�̌Ăяo���i�����v�撲���ꗗ�f�[�^�擾�j
            ISystemServise servise = getSystemServise(
                            IServiceName.SHINSEI_MAINTENANCE_SERVICE);
            try{
                resultList = servise.getKeikakuTyosyoList(container.getUserInfo(),
                        searchInfo);
            }catch(NoDataFoundException e){
                errors.add("�����v�撲���ꗗ�f�[�^",new ActionError("errors.5002"));
            }
        }

        //-----��ʑJ�ځi��^�����j-----
        if (!errors.isEmpty()) {
            saveErrors(request, errors);
            return forwardFailure(mapping);
        }

        // �������ʂ����N�G�X�g�����ɃZ�b�g
        request.setAttribute(IConstants.RESULT_INFO, resultList);
        return forwardSuccess(mapping);
    }
}