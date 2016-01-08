/*======================================================================
 *    SYSTEM      : 日本学術振興会電子申請システム（科学研究費補助金）
 *    Source name : RegistKaribangoAction.java
 *    Description : 仮領域番号発行情報登録アクション
 *
 *    Author      : BIS 趙一非
 *    Date        : 2007/07/21
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2006/06/16    V1.0        BIS 趙一非     新規作成
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.shinsei;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.ISystemServise;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.ErrorInfo;
import jp.go.jsps.kaken.model.vo.JigyoKanriInfo;
import jp.go.jsps.kaken.model.vo.JigyoKanriPk;
import jp.go.jsps.kaken.model.vo.RyoikiKeikakushoInfo;
import jp.go.jsps.kaken.web.common.IConstants;
import jp.go.jsps.kaken.web.common.LabelValueManager;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author zhaoyf
 *
 */
public class RegistKaribangoCheckAction extends BaseAction {

	public ActionForward doMainProcessing(
            ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response,
            UserContainer container)
            throws ApplicationException {
        
        //-----ActionErrorsの宣言（定型処理）-----
        ActionErrors errors = new ActionErrors();
        RyoikiGaiyoForm ryoikiGaiyoForm =(RyoikiGaiyoForm)form;
        JigyoKanriPk pkInfo=new JigyoKanriPk();
        
        

        //------キー情報-----
        String jigyoId = ryoikiGaiyoForm.getJigyoId();
        pkInfo.setJigyoId(jigyoId);    
        ISystemServise servise = getSystemServise(IServiceName.JIGYOKANRI_MAINTENANCE_SERVICE);
        JigyoKanriInfo jigyoKanriInfo=servise.select(container.getUserInfo(),pkInfo);
        jigyoKanriInfo.setNendoSeireki("20"+jigyoId.substring(0,2));
        
        RyoikiKeikakushoInfo ryoikiKeikakushoInfo;
		try {
			ryoikiKeikakushoInfo = servise.ryoikiKeikakushoInfo(container.getUserInfo(),ryoikiGaiyoForm.getRyoikikeikakushoInfo());
			ryoikiGaiyoForm.setRyoikikeikakushoInfo(ryoikiKeikakushoInfo);
		} catch (ApplicationException e) {
			// TODO 自動生成された catch ブロック
			
				errors.add("ryoikikeikakushoInfo.zennendoOuboNo",
						   new ActionError(e.getErrorCode(), e.getErrorArgs()));
				ryoikiGaiyoForm.setZenNendoOuboFlgList(LabelValueManager.getBuntankinList());
				request.setAttribute(IConstants.RESULT_INFO, jigyoKanriInfo);
		        request.setAttribute("ryoikiGaiyoForm",ryoikiGaiyoForm);
			//throw e;
		}
        finally
        {
        //-----画面遷移（定型処理）-----
        if (!errors.isEmpty()) {
            saveErrors(request, errors);
            return forwardFailure(mapping);
        }

        //検索結果をフォームにセットする。
        
        
        }
        request.setAttribute(IConstants.RESULT_INFO, jigyoKanriInfo);
        request.setAttribute("ryoikiGaiyoForm",ryoikiGaiyoForm);
        return forwardSuccess(mapping);
        
    }

}
