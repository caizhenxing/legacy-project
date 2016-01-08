/*======================================================================
 *    SYSTEM      : ���{�w�p�U����d�q�\���V�X�e���i�Ȋw������⏕���j
 *    Source name : OuboKyakkaAction.java
 *    Description : ������p����ʂ̕\��
 *
 *    Author      : �c�c
 *    Date        : 2006/06/16
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2006/06/16    v1.0        �c�c                        �V�K�쐬
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.shinsei;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.ShinseiDataPk;
import jp.go.jsps.kaken.model.vo.SimpleShinseiDataInfo;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

/**
 * ������p���O�A�N�V�����N���X�B
 * �p���Ώۉ�������擾�B�Z�b�V�����ɓo�^����B 
 * ������p����ʂ�\������B
 */
public class OuboKyakkaAction extends BaseAction {

    /* (�� Javadoc)
     * @see jp.go.jsps.kaken.web.struts.BaseAction#doMainProcessing(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, jp.go.jsps.kaken.web.common.UserContainer)
     */
    public ActionForward doMainProcessing(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response, UserContainer container)
            throws ApplicationException {

        //------�p���Ώې\�����V�X�e���ԍ��̎擾
        RyoikiGaiyoForm shoninForm = (RyoikiGaiyoForm)form;
        
        //------�p���Ώې\���V�X�e���ԍ��̎擾
        ShinseiDataPk pkInfo = new ShinseiDataPk();
        //------�L�[���
        String systemNo = shoninForm.getSystemNo();
        pkInfo.setSystemNo(systemNo);

        SimpleShinseiDataInfo shinseiInfo = null;
        
        //------�L�[�������ɐ\���f�[�^�擾  
        shinseiInfo = getSystemServise(IServiceName.SHINSEI_MAINTENANCE_SERVICE)
                .selectSimpleShinseiDataInfoForGaiyo(container.getUserInfo(), pkInfo);
        
        //------�p���Ώۏ������N�G�X�g�����ɃZ�b�g
        container.setSimpleShinseiDataInfo(shinseiInfo);
        
        return forwardSuccess(mapping);
    }
}