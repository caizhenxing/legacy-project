/*======================================================================
 *    SYSTEM      : ���{�w�p�U����d�q�\���V�X�e���i�Ȋw������⏕���j
 *    Source name : ShoninSaveAction.java
 *    Description : ���F�Ώې\�������X�V����
 *
 *    Author      : Admin
 *    Date        : 2004/02/12
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2004/02/12    V1.0        Admin          �V�K�쐬
 *    2006/06/02    V1.1        DIS.wangXC     �C���i�������F�ΏۂɕύX���邩��j
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.shozoku.shinseiJohoKanri;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.ValidationException;
import jp.go.jsps.kaken.model.vo.ErrorInfo;
import jp.go.jsps.kaken.model.vo.ShinseiDataPk;
import jp.go.jsps.kaken.model.vo.SimpleShinseiDataInfo;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 * �\����񏳔F�A�N�V�����N���X�B
 * ���F�Ώې\�������X�V����B 
 * 
 * ID RCSfile="$RCSfile: ShoninSaveAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:39 $"
 */
public class ShoninSaveAction extends BaseAction {

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

		//## �폜���v���p�e�B(�Z�b�V�����ɕێ�)�@$!userContainer.shinseishaInfo.�v���p�e�B��

		//-----ActionErrors�̐錾�i��^�����j-----
		ActionErrors errors = new ActionErrors();

// 2006/06/05 WangXian update start	
		SimpleShinseiDataInfo[] dataInfo = container.getSimpleShinseiDataInfos();
        ShinseiDataPk[] pkInfo  = new ShinseiDataPk[dataInfo.length];
       
        for (int i = 0; i < dataInfo.length; i++) {
            pkInfo[i] = new ShinseiDataPk();
            pkInfo[i].setSystemNo(dataInfo[i].getSystemNo());
        }
        try {
            getSystemServise(IServiceName.SHINSEI_MAINTENANCE_SERVICE)
                    .recognizeMultiApplication(container.getUserInfo(), pkInfo, "shonin");
        }catch(ValidationException ex){
            List errorList = ex.getErrors();
            for(int i=0; i<errorList.size(); i++){
                ErrorInfo errInfo= (ErrorInfo)errorList.get(i);
                errors.add(errInfo.getProperty(),
                           new ActionError(errInfo.getErrorCode(), errInfo.getErrorArgs()));
            }
        }catch(ApplicationException ex){
            errors.add(ActionErrors.GLOBAL_ERROR,
                    new ActionError(ex.getErrorCode(), ex.getMessage()));
        }

// 2006/06/05 WangXian update end
		//-----��ʑJ�ځi��^�����j-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
        removeFormBean(mapping, request);
		return forwardSuccess(mapping);
	}
}