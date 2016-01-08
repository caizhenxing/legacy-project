/*======================================================================
 *    SYSTEM      : 日本学術振興会電子申請システム（科学研究費補助金）
 *    Source name : IkkatsuJuriAction.java
 *    Description : 一括受理(提出書類一覧)画面を表示するアクションクラス
 *
 *    Author      : Dis.mcj
 *    Date        : 2006/06/14
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2006/06/14    V1.0        DIS.mcj        新規作成
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.gyomu.teishutsuShorui;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.vo.TeishutsuShoruiSearchInfo;
import jp.go.jsps.kaken.status.StatusCode;
import jp.go.jsps.kaken.web.common.IConstants;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 一括受理(提出書類一覧)画面を表示するアクションクラス
 */
public class IkkatsuJuriAction extends BaseAction {

    /** 状況IDが04(学振受付中)のものを表示 */
    private static String[] JIGYO_IDS = new String[] { 
        StatusCode.STATUS_GAKUSIN_SHORITYU // 学振受付中
    };

    public ActionForward doMainProcessing(
            ActionMapping mapping, 
            ActionForm form,
            HttpServletRequest request, 
            HttpServletResponse response, 
            UserContainer container)
            throws ApplicationException {

        // -----ActionErrorsの宣言（定型処理）-----
        ActionErrors errors = new ActionErrors();

        // 検索条件の取得
        TeishutsuShoruiSearchInfo searchInfo = container.getTeishutsuShoruiSearchInfo();

        searchInfo.setRyoikiJokyoId(JIGYO_IDS);
        searchInfo.setDelFlg("0");
        List result = null;
        try {
            // 検索実行
            result = getSystemServise(IServiceName.TEISHUTU_MAINTENANCE_SERVICE)
                    .selectTeishutuShoruiList(container.getUserInfo(), searchInfo);

            // ------承認対象情報をリクエスト属性にセット
            container.setTeishutsuShoruiSearchInfo(searchInfo);
            container.setShoruiKanriList(result);          
        } catch (NoDataFoundException e) {
            errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("errors.5045","一括受理対象"));
        } catch (ApplicationException e) {
            errors.add("検索処理でエラーが発生しました。",new ActionError("errors.4004"));
        } 

        // 検索結果をセットする。
        request.setAttribute(IConstants.RESULT_INFO, result);

        // トークンをセッションに保存する。
        saveToken(request);
        //-----画面遷移（定型処理）-----
        if (!errors.isEmpty()) {
            saveErrors(request, errors);
            return forwardFailure(mapping);
        }
        return forwardSuccess(mapping);
    }
}