/*======================================================================
 *    SYSTEM      : 日本学術振興会電子申請システム（科学研究費補助金）
 *    Source name : JuriCancelSaveAction.java
 *    Description : 受理解除を実行アクションクラス
 *
 *    Author      : DIS.jzx
 *    Date        : 2006/06/13
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2006/06/13    V1.0        DIS.jzx        新規作成
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.gyomu.teishutsuShorui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.vo.RyoikiKeikakushoInfo;
import jp.go.jsps.kaken.status.StatusCode;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 受理解除を実行アクションクラス
 * ID RCSfile="$RCSfile: JuriCancelSaveAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:55 $"
 */
public class JuriCancelSaveAction extends BaseAction {

    /** 所属機関受付中(状況ID:03)以降の状況ID */
    private static String[] JOKYO_ID = new String[]{
            StatusCode.STATUS_GAKUSIN_SHORITYU,             //学振受付中
            StatusCode.STATUS_GAKUSIN_JYURI,                //学振受理
            StatusCode.STATUS_GAKUSIN_FUJYURI,              //学振不受理
            StatusCode.STATUS_SHINSAIN_WARIFURI_SHORIGO,    //審査員割り振り処理後
            StatusCode.STATUS_WARIFURI_CHECK_KANRYO,        //割り振りチェック完了
            StatusCode.STATUS_1st_SHINSATYU,                //一次審査中
            StatusCode.STATUS_1st_SHINSA_KANRYO,            //一次審査：判定
            StatusCode.STATUS_2nd_SHINSA_KANRYO,            //二次審査完了
    };
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
        // 検索条件の取得
        JuriGaiyoForm juriGaiyoForm = (JuriGaiyoForm) form;

        //-------▼ VOにデータをセットする。
        RyoikiKeikakushoInfo ryoikiInfo =new RyoikiKeikakushoInfo();
        ryoikiInfo.setRyoikiSystemNo(juriGaiyoForm.getSystemNo());
        ryoikiInfo.setJokyoIds(JOKYO_ID);
        if (log.isDebugEnabled()) {
            log.debug("受理結果　解除情報 ");
        }

        try{
            getSystemServise(IServiceName.TEISHUTU_MAINTENANCE_SERVICE)
                .cancelTeisyutusyoJuri(container.getUserInfo(),ryoikiInfo); 
        }catch(NoDataFoundException e){
            errors.add("該当データはありません。",new ActionError("errors.5002"));
        }catch (ApplicationException ex) {
            errors.add("受理解除でエラーが発生しました。", new ActionError("errors.4002"));
        }

        // -----画面遷移（定型処理）-----
        if (!errors.isEmpty()) {
            saveErrors(request, errors);
            return forwardFailure(mapping);
        }

        return forwardSuccess(mapping);
    }
}