/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : jinbaogang
 *    Date        : 2006/10/25
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.shinsa.shinsaJigyo;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.IShinsaKekkaMaintenance;
import jp.go.jsps.kaken.model.common.ILabelKubun;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.SystemException;
import jp.go.jsps.kaken.model.vo.ShinsaKekkaInputInfo;
import jp.go.jsps.kaken.model.vo.ShinsaKekkaPk;
import jp.go.jsps.kaken.util.StringUtil;
import jp.go.jsps.kaken.web.common.LabelValueManager;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * ���Q�֌W�ӌ��\���B
 * 
 * ID RCSfile="$RCSfile: RiekiSohanInputAction.java,v $"
 * Revision="$Revision: 1.1 $" 
 * Date="$Date: 2007/06/28 02:06:59 $"
 */
public class RiekiSohanInputAction extends BaseAction {

    /* (�� Javadoc)
     * @see jp.go.jsps.kaken.web.struts.BaseAction#doMainProcessing(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, jp.go.jsps.kaken.web.common.UserContainer)
     */
    public ActionForward doMainProcessing(ActionMapping mapping,
            ActionForm form, HttpServletRequest request,
            HttpServletResponse response, UserContainer container)
            throws ApplicationException {

        // -----ActionErrors�̐錾�i��^�����j-----
        ActionErrors errors = new ActionErrors();

        // ------�L�[���
        ShinsaKekkaPk pkInfo = new ShinsaKekkaPk();
        pkInfo.setSystemNo(((ShinsaKekkaRigaiForm) form).getSystemNo()); // �V�X�e����t�ԍ�
        pkInfo.setShinsainNo(container.getUserInfo().getShinsainInfo()
                .getShinsainNo()); // �R�����ԍ�
        pkInfo.setJigyoKubun(container.getUserInfo().getShinsainInfo()
                .getJigyoKubun()); // ���Ƌ敪

        // ------�L�[�������ɍX�V�f�[�^�擾
        ShinsaKekkaInputInfo selectInfo = getSystemServise(
                IServiceName.SHINSAKEKKA_MAINTENANCE_SERVICE)
                .selectShinsaKekkaForRiekiSohan(container.getUserInfo(), pkInfo);

        // ------�X�V�ΏېR�����ʏ����A�o�^�t�H�[�����̍X�V
        ShinsaKekkaRigaiForm inputForm = new ShinsaKekkaRigaiForm();

        try {
            PropertyUtils.copyProperties(inputForm, selectInfo);
        } catch (Exception e) {
            log.error(e);
            throw new SystemException("�v���p�e�B�̐ݒ�Ɏ��s���܂����B", e);
        }
        
        String[] labelNames = { ILabelKubun.RIGAI };
        HashMap labelMap = (HashMap) LabelValueManager.getLabelMap(labelNames);
        inputForm.setRigaiList((List) labelMap.get(ILabelKubun.RIGAI));
        
        //���Q�֌W���o����ݒ�
        if (!StringUtil.isBlank(inputForm.getRigai())) {
            selectInfo.setRigaiLabel(LabelValueManager.getlabelName(inputForm.getRigaiList(), 
                    inputForm.getRigai()));
        } else {
            selectInfo.setRigaiLabel(LabelValueManager.getlabelName(inputForm.getRigaiList(), 
                    IShinsaKekkaMaintenance.RIGAI_OFF));
        }
        
        // ------�\���Ώۏ����Z�b�V�����ɓo�^�B
        container.setShinsaKekkaInputInfo(selectInfo);

        // ------�V�K�o�^�t�H�[���ɃZ�b�g����B
        updateFormBean(mapping, request, inputForm);

        // -----��ʑJ�ځi��^�����j-----
        if (!errors.isEmpty()) {
            saveErrors(request, errors);
            return forwardFailure(mapping);
        }
        saveToken(request);

        return forwardSuccess(mapping);
    }
}