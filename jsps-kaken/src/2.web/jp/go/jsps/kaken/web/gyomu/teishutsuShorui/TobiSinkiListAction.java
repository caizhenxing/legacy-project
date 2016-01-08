/*======================================================================
 *    SYSTEM      : 日本学術振興会電子申請システム（科学研究費補助金）
 *    Source name : TobiSinkiListAction
 *    Description : 飛び番号リスト画面アクションクラス
 *
 *    Author      : Dis.zhangt
 *    Date        : 2006/06/13
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2006/06/13    V1.0        DIS.zhangt       新規作成
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.gyomu.teishutsuShorui;

import java.util.HashMap;
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
 * 飛び番号リストアクション ID RCSfile="$RCSfile: TobiSinkiListAction.java,v $" Revision="$Revision: 1.1 $" Date="$Date: 2007/06/28 02:06:55 $"
 */
public class TobiSinkiListAction extends BaseAction {

    /**
     * Actionクラスの主要な機能を実装する。 戻り値として、次の遷移先をActionForward型で返する。
     */
    public ActionForward doMainProcessing(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response, UserContainer container)
            throws ApplicationException {

        // -----ActionErrorsの宣言（定型処理）-----
        ActionErrors errors = new ActionErrors();

        String[] jokyoIds = new String[] { StatusCode.STATUS_SAKUSEITHU, // 作成中:01
                StatusCode.STATUS_SHINSEISHO_MIKAKUNIN, // 申請書未確認:02
                StatusCode.STATUS_SHOZOKUKIKAN_KYAKKA, // 所属機関却下:05
        };
        // 検索条件の取得
        TeishutsuShoruiSearchInfo teishutsuShoruiSearchInfo = container
                .getTeishutsuShoruiSearchInfo();
        // 2006/08/01 zhangt add start
        List jigyoIdResult = null;
        try{
            //検索実行
            jigyoIdResult = 
                getSystemServise(
                    IServiceName.TEISHUTU_MAINTENANCE_SERVICE).selectTeishutuShoruiList(
                    container.getUserInfo(),
                    teishutsuShoruiSearchInfo);
        }catch(NoDataFoundException e){
            errors.add("該当データはありません。",new ActionError("errors.5002"));
        }
        String[] jigyoId = new String[jigyoIdResult.size()];
        for(int i=0;i<jigyoIdResult.size();i++){
            HashMap jigyoIdMap=(HashMap)jigyoIdResult.get(i);
            String allJigyoId = (String)jigyoIdMap.get("JIGYO_ID");
            jigyoId[i] = allJigyoId;
            teishutsuShoruiSearchInfo.setJigyoId(jigyoId);
        }
        // 2006/08/01 zhangt add end
        teishutsuShoruiSearchInfo.setRyoikiJokyoId(jokyoIds);
        teishutsuShoruiSearchInfo.setDelFlg("1");
        teishutsuShoruiSearchInfo.setSearchJokyoId1(null);
        teishutsuShoruiSearchInfo.setSearchJokyoId2(null);
        // サーバサービスの呼び出し（飛び番号リストデータ取得）
        List result = null;
        try {
            result = getSystemServise(IServiceName.TEISHUTU_MAINTENANCE_SERVICE)
                    .selectTeisyutusyoTobiSinkiList(container.getUserInfo(),
                            teishutsuShoruiSearchInfo);
        }
        catch (NoDataFoundException e) {
            errors.add("飛び番号リストデータ", new ActionError("errors.5002"));
        }

        // 検索結果をリクエスト属性にセット
        request.setAttribute(IConstants.RESULT_INFO, result);

        // -----画面遷移（定型処理）-----
        if (!errors.isEmpty()) {
            saveErrors(request, errors);
            return forwardFailure(mapping);
        }
        return forwardSuccess(mapping);
    }
}