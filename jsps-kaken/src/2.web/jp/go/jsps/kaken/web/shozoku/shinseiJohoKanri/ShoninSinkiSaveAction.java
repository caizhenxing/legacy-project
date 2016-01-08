/*======================================================================
 *    SYSTEM      : 日本学術振興会電子申請システム（科学研究費補助金）
 *    Source name : ShoninSinkiSaveAction.java
 *    Description : 確認対象申請情報を更新する
 *
 *    Author      : DIS LY
 *    Date        : 2006/06/21
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2006/06/21    V1.0        ly             新規作成
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.shozoku.shinseiJohoKanri;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.ValidationException;
import jp.go.jsps.kaken.model.vo.ShinseiDataPk;
import jp.go.jsps.kaken.model.vo.SimpleShinseiDataInfo;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;
import jp.go.jsps.kaken.model.vo.ErrorInfo;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 申請情報確認アクションクラス。
 * 確認対象申請情報を更新する。 
 * 
 * ID RCSfile="$RCSfile: ShoninSinkiSaveAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:38 $"
 */
public class ShoninSinkiSaveAction extends BaseAction {

    /* (非 Javadoc)
     * @see jp.go.jsps.kaken.web.struts.BaseAction#doMainProcessing(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, jp.go.jsps.kaken.web.common.UserContainer)
     */
    public ActionForward doMainProcessing(ActionMapping mapping,
            ActionForm form, HttpServletRequest request,
            HttpServletResponse response, UserContainer container)
            throws ApplicationException {

        // -----ActionErrorsの宣言（定型処理）-----
        ActionErrors errors = new ActionErrors();

        // ------承認対象申請情報システム番号の取得
        SimpleShinseiDataInfo[] dataInfo = container
                .getSimpleShinseiDataInfos();
        ShinseiDataPk[] pkInfo = new ShinseiDataPk[dataInfo.length];
        for (int i = 0; i < dataInfo.length; i++) {
            pkInfo[i] = new ShinseiDataPk();
            pkInfo[i].setSystemNo(dataInfo[i].getSystemNo());
            
        }
        try {
            getSystemServise(IServiceName.SHINSEI_MAINTENANCE_SERVICE)
                    .recognizeMultiApplication(container.getUserInfo(), pkInfo, "kakunin");
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
        // -----画面遷移（定型処理）-----
        if (!errors.isEmpty()) {
            saveErrors(request, errors);
            return forwardFailure(mapping);
        }
        removeFormBean(mapping, request);
        return forwardSuccess(mapping);
    }
}