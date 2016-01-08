/*======================================================================
 *    SYSTEM      : 日本学術振興会電子申請システム（科学研究費補助金）
 *    Source name : KeikakuTyosyoListAction.java
 *    Description : 研究計画調書一覧アクション
 *
 *    Author      : DIS.dyh
 *    Date        : 2006/06/14
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2006/06/14    V1.0        DIS.dyh        新規作成
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.shozoku.teishutsuShorui;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import jp.go.jsps.kaken.model.ISystemServise;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.vo.ShinseiSearchInfo;
import jp.go.jsps.kaken.status.StatusCode;
import jp.go.jsps.kaken.util.StringUtil;
import jp.go.jsps.kaken.web.common.IConstants;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

/**
 * 研究計画調書一覧アクション
 * @author DIS.dyh
 * ID RCSfile="$RCSfile: KeikakuTyosyoListAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:45 $"
 */
public class KeikakuTyosyoListAction extends BaseAction {

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

        // 検索条件を設定
        TeisyutuForm teisyotuForm = (TeisyutuForm)form;
        ShinseiSearchInfo searchInfo = new ShinseiSearchInfo();

        List resultList = null;
        if(StringUtil.isBlank(teisyotuForm.getKariryoikiNo())){
            errors.add("研究計画調書一覧データ",new ActionError("errors.5040",
                    new String[]{"研究計画調書一覧"}));
        }else{
            searchInfo.setRyouikiNo(teisyotuForm.getKariryoikiNo());
            String[] jokyoIds = new String[]{
                    StatusCode.STATUS_GAKUSIN_SHORITYU,         //学振受付中:04
                    StatusCode.STATUS_GAKUSIN_JYURI,            //学振受理:06
                    StatusCode.STATUS_GAKUSIN_FUJYURI,          //学振不受理:07
                    StatusCode.STATUS_SHINSAIN_WARIFURI_SHORIGO,//審査員割り振り処理後:08
                    StatusCode.STATUS_WARIFURI_CHECK_KANRYO,    //割り振りチェック完了:09
                    StatusCode.STATUS_1st_SHINSATYU,            //一次審査中:10
                    StatusCode.STATUS_1st_SHINSA_KANRYO,        //一次審査：判定:11
                    StatusCode.STATUS_2nd_SHINSA_KANRYO,        //二次審査完了:12
                    StatusCode.STATUS_RYOUIKISHOZOKU_UKETUKE};  //領域代表者所属研究機関受付中:24
            searchInfo.setJokyoId(jokyoIds);
            
            // サーバサービスの呼び出し（研究計画調書一覧データ取得）
            ISystemServise servise = getSystemServise(
                            IServiceName.SHINSEI_MAINTENANCE_SERVICE);
            try{
                resultList = servise.getKeikakuTyosyoList(container.getUserInfo(),
                        searchInfo);
            }catch(NoDataFoundException e){
                errors.add("研究計画調書一覧データ",new ActionError("errors.5002"));
            }
        }

        //-----画面遷移（定型処理）-----
        if (!errors.isEmpty()) {
            saveErrors(request, errors);
            return forwardFailure(mapping);
        }

        // 検索結果をリクエスト属性にセット
        request.setAttribute(IConstants.RESULT_INFO, resultList);
        return forwardSuccess(mapping);
    }
}