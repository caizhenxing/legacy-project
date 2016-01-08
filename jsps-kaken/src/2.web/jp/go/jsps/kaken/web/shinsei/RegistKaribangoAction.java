/*======================================================================
 *    SYSTEM      : 日本学術振興会電子申請システム（科学研究費補助金）
 *    Source name : RegistKaribangoAction.java
 *    Description : 仮領域番号発行情報登録アクション
 *
 *    Author      : DIS.gongXB
 *    Date        : 2006/06/16
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2006/06/16    V1.0        DIS.gongXB     新規作成
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.shinsei;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.ISystemServise;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.JigyoKanriInfo;
import jp.go.jsps.kaken.model.vo.JigyoKanriPk;
import jp.go.jsps.kaken.model.vo.ShinseiDataInfo;
import jp.go.jsps.kaken.web.common.IConstants;
import jp.go.jsps.kaken.web.common.LabelValueManager;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 仮領域番号発行情報登録アクションクラス
 */
public class RegistKaribangoAction extends BaseAction {

    /**
     * Actionクラスの主要な機能を実装する。
     * 戻り値として、次の遷移先をActionForward型で返する。
     */
    public ActionForward doMainProcessing(
            ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response,
            UserContainer container)
            throws ApplicationException {
        
        //-----ActionErrorsの宣言（定型処理）-----
        ActionErrors errors = new ActionErrors();
        
        

        JigyoKanriPk pkInfo=new JigyoKanriPk();
        ShinseiDataInfo shinseiDataInfo=((ShinseiForm)form).getShinseiDataInfo();

        //------キー情報-----
        String jigyoId = shinseiDataInfo.getJigyoId();
        pkInfo.setJigyoId(jigyoId);    
        ISystemServise servise = getSystemServise(IServiceName.JIGYOKANRI_MAINTENANCE_SERVICE);
        JigyoKanriInfo jigyoKanriInfo=servise.select(container.getUserInfo(),pkInfo);
        jigyoKanriInfo.setNendoSeireki("20"+jigyoId.substring(0,2));
              
        //-----画面遷移（定型処理）-----
        if (!errors.isEmpty()) {
            saveErrors(request, errors);
            return forwardFailure(mapping);
        }

        //検索結果をフォームにセットする。
        request.setAttribute(IConstants.RESULT_INFO, jigyoKanriInfo);
//ADD START 2007/07/13 BIS 趙一非
       
        RyoikiGaiyoForm ryoikiGaiyoForm =new RyoikiGaiyoForm();
        ryoikiGaiyoForm.getRyoikikeikakushoInfo().setZennendoOuboFlg(request.getParameter("ryoikikeikakushoInfo.zennendoOuboFlg"));
        ryoikiGaiyoForm.getRyoikikeikakushoInfo().setZennendoOuboNo(request.getParameter("ryoikikeikakushoInfo.zennendoOuboNo"));
       //研究領域最終年度前年度の応募有無リスト
        ryoikiGaiyoForm.setZenNendoOuboFlgList(LabelValueManager.getBuntankinList());
        ryoikiGaiyoForm.setJigyoId(jigyoId);
        request.setAttribute("ryoikiGaiyoForm",ryoikiGaiyoForm);
        
        //ADD END 2007/07/13 BIS 趙一非
        return forwardSuccess(mapping);
    }
}