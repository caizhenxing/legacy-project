/*======================================================================
 *    SYSTEM      : ���{�w�p�U����d�q�\���V�X�e���i�Ȋw������⏕���j
 *    Source name : CsvOutAction
 *    Description : CSV�o��(������ꗗ)�A�N�V�����N���X
 *
 *    Author      : dis.liuyi
 *    Date        : 2006/07/03
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2006/07/03    v1.0        dis.liuyi      �V�K�쐬
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.shozoku.shinseiJohoKanri;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.ShinseiSearchInfo;
import jp.go.jsps.kaken.util.DownloadFileUtil;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * CSV�o��(������ꗗ)�A�N�V�����N���X
 */
public class CsvOutAction extends BaseAction {
    
    /**
     * CSV�t�@�C�����̐ړ����B
     */
    public static final String filename = "OUBODATA";  //TODO

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
        
        //���������̎擾
        ShinseiSearchInfo searchInfo = container.getShinseiSearchInfo();
        
//2007/03/23  ���u�j�@�폜��������
        //searchInfo.clrOrder();
//2007/03/23�@���u�j�@�폜�����܂�
       
        //�������s
        List result =
            getSystemServise(
                IServiceName.SHINSEI_MAINTENANCE_SERVICE).searchShozokuCsvData(
                container.getUserInfo(),
                searchInfo);
        
        DownloadFileUtil.downloadCSV(request, response, result, filename);

        //-----��ʑJ�ځi��^�����j-----
        if (!errors.isEmpty()) {
            saveErrors(request, errors);
            return forwardFailure(mapping);
        }
        return forwardSuccess(mapping);
    }
}