/*======================================================================
 *    SYSTEM      : ���{�w�p�U����d�q�\���V�X�e���i�Ȋw������⏕���j
 *    Source name : KatuteiCancelAction.java
 *    Description : �̈�v�揑�m�������ʕ\���A�N�V�����N���X
 *
 *    Author      : DIS.jzx
 *    Date        : 2006/06/20
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2006/06/14    V1.0        DIS.jzx        �V�K�쐬
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.shinsei;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * �̈�v�揑�m�������ʂ𐶐�����B
 * ID RCSfile="$RCSfile: KakuteiCancelAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:02 $"
 */
public class KakuteiCancelAction extends BaseAction {

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

        //-----��ʑJ�ځi��^�����j-----
        updateFormBean(mapping,request,form);
        return forwardSuccess(mapping);
        
    }
    
}
