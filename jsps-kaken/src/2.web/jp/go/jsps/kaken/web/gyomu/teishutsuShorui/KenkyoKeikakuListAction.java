/*======================================================================
 *    SYSTEM      : ���{�w�p�U����d�q�\���V�X�e���i�Ȋw������⏕���j
 *    Source name : KeikakuTyosyoListAction.java
 *    Description : �����v�撲���ꗗ�A�N�V����
 *
 *    Author      : DIS.zhangt
 *    Date        : 2006/06/15
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2006/06/15    V1.0        DIS.zhangt     �V�K�쐬
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.gyomu.teishutsuShorui;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.vo.ShinseiSearchInfo;
import jp.go.jsps.kaken.status.StatusCode;
import jp.go.jsps.kaken.util.StringUtil;
import jp.go.jsps.kaken.web.common.IConstants;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * �����v�撲���ꗗ�A�N�V����
 * @author DIS.zhangt
 * ID RCSfile="$RCSfile: KenkyoKeikakuListAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:55 $"
 */
public class KenkyoKeikakuListAction extends BaseAction {

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
        TeishutsuShoruiSearchForm searchForm = (TeishutsuShoruiSearchForm) form;
        ShinseiSearchInfo searchInfo = new ShinseiSearchInfo();
        
        //��ʂŃf�[�^��n����Ȃ��̏ꍇ
        if(StringUtil.isBlank(searchForm.getKariryoikiNo())){
            //�G���[���b�Z�[�W
            errors.add("�����v�撲���ꗗ�f�[�^",new ActionError("errors.5002"));
            saveErrors(request, errors);
            return forwardFailure(mapping);
        }
        searchInfo.setRyouikiNo(searchForm.getKariryoikiNo());
        String[] jokyoIds = new String[] { 
                StatusCode.STATUS_GAKUSIN_SHORITYU,          // �w�U��t��:04
                StatusCode.STATUS_GAKUSIN_JYURI,             // �w�U��:06
//2006/07/24 zhangt add start ���R�F�d�l�ύX
                StatusCode.STATUS_GAKUSIN_FUJYURI,             // �w�U�s��:07
//2006/07/24 zhangt add end  
                StatusCode.STATUS_SHINSAIN_WARIFURI_SHORIGO, // �R��������U�菈����:08
                StatusCode.STATUS_WARIFURI_CHECK_KANRYO,     // ����U��`�F�b�N����:09
                StatusCode.STATUS_1st_SHINSATYU,             // �ꎟ�R����:10
                StatusCode.STATUS_1st_SHINSA_KANRYO,         // �ꎟ�R���F����:11
                StatusCode.STATUS_2nd_SHINSA_KANRYO          // �񎟐R������:12;
        };
        searchInfo.setJokyoId(jokyoIds);
        
        // �T�[�o�T�[�r�X�̌Ăяo���i�����v�撲���ꗗ�f�[�^�擾�j
        List resultList = null;
        try{
            resultList = getSystemServise(IServiceName.SHINSEI_MAINTENANCE_SERVICE)
                    .getKeikakuTyosyoList(container.getUserInfo(), searchInfo);
        }catch(NoDataFoundException e){
            resultList = new ArrayList();
        }catch (ApplicationException ex) {
            errors.add("�����v�撲���ꗗ�G���[���������܂����B", new ActionError("errors.4002"));
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