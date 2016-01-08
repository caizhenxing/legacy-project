/*======================================================================
 *    SYSTEM      : ���{�w�p�U����d�q�\���V�X�e���i�Ȋw������⏕���j
 *    Source name : ShoninSinkiAction.java
 *    Description : �����v�撲���m�F��ʂ�\������
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

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.vo.ShinseiDataPk;
import jp.go.jsps.kaken.model.vo.SimpleShinseiDataInfo;
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
 * �\����񏳔F�O�A�N�V�����N���X�B
 * �m�F�Ώې\�������擾�B�Z�b�V�����ɓo�^����B 
 * �����v�撲���m�F��ʂ�\������B
 * 
 * ID RCSfile="$RCSfile: ShoninSinkiAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:38 $"
 */
public class ShoninSinkiAction extends BaseAction {

    /* (�� Javadoc)
     * @see jp.go.jsps.kaken.web.struts.BaseAction#doMainProcessing(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, jp.go.jsps.kaken.web.common.UserContainer)
     */
    public ActionForward doMainProcessing(ActionMapping mapping,
            ActionForm form, HttpServletRequest request,
            HttpServletResponse response, UserContainer container)
            throws ApplicationException {

        //-----ActionErrors�̐錾�i��^�����j-----
        ActionErrors errors = new ActionErrors();

        //------�m�F�Ώې\�����V�X�e���ԍ��̎擾
        ShinseiDataForm shoninForm = (ShinseiDataForm) form;

        if (isCancelled(request)) {
            removeFormBean(mapping, request);

            return forwardCancel(mapping);

        }
        //------���F�Ώې\�����V�X�e���ԍ��̎擾
        String[] sysNos = shoninForm.getTantoSystemNo();
        ArrayList selectNos = new ArrayList();
        for (int i = 0; i < sysNos.length; i++) {
            if (!StringUtil.isBlank(sysNos[i])) {
                selectNos.add(sysNos[i]);
            }
        }
        if (selectNos.size() == 0) {
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
                    "errors.requiredSelect", "�m�F���鉞����"));
            saveErrors(request, errors);
            return forwardFailure(mapping);

        }
        //------���F�Ώې\���V�X�e���ԍ��̎擾
        ShinseiDataPk[] pkInfo = new ShinseiDataPk[selectNos.size()];
        for (int i = 0; i < selectNos.size(); i++) {
            pkInfo[i] = new ShinseiDataPk();
            pkInfo[i].setSystemNo((String) selectNos.get(i));
        }
        //------�L�[�������ɐ\���f�[�^�擾
        SimpleShinseiDataInfo[] shinseiInfo = null;
        try {
            shinseiInfo = getSystemServise(
                    IServiceName.SHINSEI_MAINTENANCE_SERVICE)
                    .selectSimpleShinseiDataInfos(container.getUserInfo(),
                            pkInfo);
            // ------���F�Ώۏ������N�G�X�g�����ɃZ�b�g
            container.setSimpleShinseiDataInfos(shinseiInfo);
        }
        catch (NoDataFoundException e) {
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(e
                    .getErrorCode(), e.getMessage()));
        }
        catch (ApplicationException e) {
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(e
                    .getErrorCode(), e.getMessage()));
        }
        if (!errors.isEmpty()) {
            saveErrors(request, errors);
            return forwardFailure(mapping);
        }
        ArrayList list = new ArrayList();
        for (int i = 0; i < shinseiInfo.length; i++) {
            list.add(shinseiInfo[i]);
        }
        request.getSession().setAttribute(IConstants.RESULT_INFO, list);
        //-----��ʑJ�ځi��^�����j-----

        //�g�[�N�����Z�b�V�����ɕۑ�����B
        saveToken(request);

        return forwardSuccess(mapping);
    }
}
