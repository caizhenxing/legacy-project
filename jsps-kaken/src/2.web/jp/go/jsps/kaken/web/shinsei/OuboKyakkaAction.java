/*======================================================================
 *    SYSTEM      : 日本学術振興会電子申請システム（科学研究費補助金）
 *    Source name : OuboKyakkaAction.java
 *    Description : 応募情報却下画面の表示
 *
 *    Author      : 苗苗
 *    Date        : 2006/06/16
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2006/06/16    v1.0        苗苗                        新規作成
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
 * 応募情報却下前アクションクラス。
 * 却下対象応募情報を取得。セッションに登録する。 
 * 応募情報却下画面を表示する。
 */
public class OuboKyakkaAction extends BaseAction {

    /* (非 Javadoc)
     * @see jp.go.jsps.kaken.web.struts.BaseAction#doMainProcessing(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, jp.go.jsps.kaken.web.common.UserContainer)
     */
    public ActionForward doMainProcessing(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response, UserContainer container)
            throws ApplicationException {

        //------却下対象申請情報システム番号の取得
        RyoikiGaiyoForm shoninForm = (RyoikiGaiyoForm)form;
        
        //------却下対象申請システム番号の取得
        ShinseiDataPk pkInfo = new ShinseiDataPk();
        //------キー情報
        String systemNo = shoninForm.getSystemNo();
        pkInfo.setSystemNo(systemNo);

        SimpleShinseiDataInfo shinseiInfo = null;
        
        //------キー情報を元に申請データ取得  
        shinseiInfo = getSystemServise(IServiceName.SHINSEI_MAINTENANCE_SERVICE)
                .selectSimpleShinseiDataInfoForGaiyo(container.getUserInfo(), pkInfo);
        
        //------却下対象情報をリクエスト属性にセット
        container.setSimpleShinseiDataInfo(shinseiInfo);
        
        return forwardSuccess(mapping);
    }
}