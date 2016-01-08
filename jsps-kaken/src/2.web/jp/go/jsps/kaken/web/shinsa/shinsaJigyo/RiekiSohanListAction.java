/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2006/10/24
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.shinsa.shinsaJigyo;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.impl.ShinsaKekkaMaintenance;

import jp.go.jsps.kaken.model.vo.SearchInfo;

import jp.go.jsps.kaken.web.common.IConstants;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * ���Q�֌W���͏󋵈ꗗ�����A�N�V�����N���X�B
 * ���Q�֌W���͏󋵈ꗗ��ʂ�\������B
 * 
 * ID RCSfile="$RCSfile: RiekiSohanListAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:59 $"
 */
public class RiekiSohanListAction extends BaseAction {

    /* (�� Javadoc)
     * @see jp.go.jsps.kaken.web.struts.BaseAction#doMainProcessing(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, jp.go.jsps.kaken.web.common.UserContainer)
     */
    public ActionForward doMainProcessing(ActionMapping mapping,
            ActionForm form, HttpServletRequest request,
            HttpServletResponse response, UserContainer container)
            throws ApplicationException {

        //-----ActionErrors�̐錾�i��^�����j-----
        ActionErrors errors = new ActionErrors();

        //------�L�[���
        String jigyoId = ((ShinsaKekkaSearchForm) form).getJigyoId(); //����ID
        String jigyoKubun = ((ShinsaKekkaSearchForm) form).getJigyoKubun(); //���Ƌ敪
        String rigai = ((ShinsaKekkaSearchForm) form).getRigai(); //���Q�֌W
        if (rigai == null || rigai.equals("")) {
            rigai = "";
            ((ShinsaKekkaSearchForm) form).setRigai(rigai);
        }

        container.getUserInfo().getShinsainInfo().setJigyoKubun(jigyoKubun);
        SearchInfo searchInfo = new SearchInfo();

        //�y�[�W����
        searchInfo.setPageSize(0);
        searchInfo.setMaxSize(0);
        searchInfo.setStartPosition(0);

        //------�L�[�������ɍX�V�f�[�^�擾	
        Map result = getSystemServise(
                IServiceName.SHINSAKEKKA_MAINTENANCE_SERVICE)
                .selectShinsaKekkaTantoList(container.getUserInfo(),
                                            jigyoId,
                                            null,
                                            ShinsaKekkaMaintenance.RIEKISOHAN_FLAG,
                                            rigai,
                                            searchInfo);

        // �������ʂ����N�G�X�g�����ɃZ�b�g
        request.setAttribute(IConstants.RESULT_INFO, result);

        // -----��ʑJ�ځi��^�����j-----
        if (!errors.isEmpty()) {
            saveErrors(request, errors);
            return forwardFailure(mapping);
        }
        return forwardSuccess(mapping);
    }
}