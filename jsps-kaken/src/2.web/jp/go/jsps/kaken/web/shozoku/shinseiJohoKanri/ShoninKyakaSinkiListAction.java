/*======================================================================
 *    SYSTEM      : ���{�w�p�U����d�q�\���V�X�e���i�Ȋw������⏕���j
 *    Source name : ShoninKyakaSinkiListAction.java
 *    Description : �m�F�E�p���Ώۉ�����ꗗ��ʂ�\������
 *
 *    Author      : DIS LY
 *    Date        : 2006/06/21
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2006/06/21    V1.0        LY              �V�K�쐬
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.shozoku.shinseiJohoKanri;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.ISystemServise;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.vo.ErrorInfo;
import jp.go.jsps.kaken.model.vo.ShinseiSearchInfo;
import jp.go.jsps.kaken.model.vo.ShozokuInfo;
import jp.go.jsps.kaken.model.vo.UserInfo;
import jp.go.jsps.kaken.status.StatusCode;
import jp.go.jsps.kaken.util.Page;
import jp.go.jsps.kaken.web.common.IConstants;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * �m�F�E�p���Ώۉ�����ꗗ�A�N�V�����N���X�B
 * �m�F�E�p���Ώۉ�����ꗗ��ʂ�\������B
 */
public class ShoninKyakaSinkiListAction extends BaseAction {

    public ActionForward doMainProcessing(ActionMapping mapping,
            ActionForm form, HttpServletRequest request,
            HttpServletResponse response, UserContainer container)
            throws ApplicationException {

        ShinseiSearchForm searchForm = (ShinseiSearchForm) form;

        //------�L�����Z������-----------------
        String forwardStr = (String) request.getParameter("goBack");
        if ("goBack".equals(forwardStr)) {
            searchForm.setStartPosition(0);
            return mapping.findForward(forwardStr);
        }
        //�����@�֏��
        UserInfo userInfo = container.getUserInfo();
        ShozokuInfo shozokuInfo = userInfo.getShozokuInfo();
        if (shozokuInfo == null) {
            throw new ApplicationException("�����@�֏����擾�ł��܂���ł����B", new ErrorInfo(
                    "errors.application"));
        }
        //�����������Z�b�g����
        ShinseiSearchInfo searchInfo = new ShinseiSearchInfo();
        searchInfo.setShozokuCd(shozokuInfo.getShozokuCd());//�����@�փR�[�h
        searchInfo.setJokyoId(new String[] {
                StatusCode.STATUS_SHOZOKUKIKAN_UKETUKETYU });//�\����ID("03")
        // ���ݕۗ�
        searchInfo.setOrder(ShinseiSearchInfo.ORDER_BY_SYSTEM_NO);//�V�X�e����t�ԍ��̏���("SYSTEM_NO")        

        // ����̈挤���i�V�K�̈�j:00022
        searchInfo.setJigyoCd(searchForm.getJigyoCd());

        //�y�[�W����
        searchInfo.setStartPosition(searchForm.getStartPosition());
        searchInfo.setPageSize(searchForm.getPageSize());
        searchInfo.setMaxSize(searchForm.getMaxSize());
        //�T�[�o�T�[�r�X�̌Ăяo���i�����󋵈ꗗ�y�[�W���擾�j
        ISystemServise servise = getSystemServise(IServiceName.SHINSEI_MAINTENANCE_SERVICE);
        Page page = null;
        try {
            page = servise.searchConfirmInfo(userInfo, searchInfo);
        }
        catch (NoDataFoundException e) {
            //0���̃y�[�W�I�u�W�F�N�g�𐶐�
            page = Page.EMPTY_PAGE;
        }
        //�������ʂ����N�G�X�g�����ɃZ�b�g
        request.setAttribute(IConstants.RESULT_INFO, page);

        saveToken(request);
        //-----��ʑJ�ځi��^�����j-----
        
        return forwardSuccess(mapping);
    }
}