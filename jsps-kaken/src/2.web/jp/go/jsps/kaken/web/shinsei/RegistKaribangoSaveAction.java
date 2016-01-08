/*======================================================================
 *    SYSTEM      : 日本学術振興会電子申請システム（科学研究費補助金）
 *    Source name : RegistKaribangoAction.java
 *    Description : 仮領域番号発行情報登録を行うアクション
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

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.vo.JigyoKanriPk;
import jp.go.jsps.kaken.model.vo.ShinseiDataInfo;
import jp.go.jsps.kaken.model.vo.UserInfo;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 仮領域番号発行情報登録を行うアクション
 * 仮領域番号発行情報登録完了画面を表示する
 */
public class RegistKaribangoSaveAction extends BaseAction {

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
        //UPDATE START 2007/07/23 BIS 趙一非
//        ShinseiDataInfo shinseiDataInfo=((ShinseiForm)form).getShinseiDataInfo();
//
//        // キー情報
//        String jigyoId = shinseiDataInfo.getJigyoId();
        RyoikiGaiyoForm ryoikiGaiyoForm =(RyoikiGaiyoForm)form;
       //------キー情報-----
        String jigyoId = ryoikiGaiyoForm.getJigyoId();
        pkInfo.setZennendoOuboFlg(ryoikiGaiyoForm.getRyoikikeikakushoInfo().getZennendoOuboFlg());;
        pkInfo.setZennendoOuboNo(ryoikiGaiyoForm.getRyoikikeikakushoInfo().getZennendoOuboNo());
        pkInfo.setZennendoOuboRyoikiRyaku(ryoikiGaiyoForm.getRyoikikeikakushoInfo().getZennendoOuboRyoikiRyaku());
        pkInfo.setZennendoOuboSettei(ryoikiGaiyoForm.getRyoikikeikakushoInfo().getZennendoOuboSettei());
//      UPDATE END 2007/07/23 BIS 趙一非
        pkInfo.setJigyoId(jigyoId); 
      
        UserInfo userInfo=container.getUserInfo();
        
        try{
            getSystemServise(IServiceName.TEISHUTU_MAINTENANCE_SERVICE)
               .registKariBangoHakkoInfo(userInfo,pkInfo);
        }catch (NoDataFoundException ex) {
            errors.add("該当データはありません", new ActionError("errors.5002"));
        }
        
        //-----画面遷移（定型処理）-----
        if (!errors.isEmpty()) {
            saveErrors(request, errors);
            return forwardFailure(mapping);
        }
        return forwardSuccess(mapping);
    }
}