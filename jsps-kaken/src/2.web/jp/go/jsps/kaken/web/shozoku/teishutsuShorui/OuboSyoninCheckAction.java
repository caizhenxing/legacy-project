/*======================================================================
 *    SYSTEM      : 日本学術振興会電子申請システム（科学研究費補助金）
 *    Source name : OuboSyoninCheckAction.java
 *    Description : 応募書類承認確認画面
 *
 *    Author      : DIS.zhangjianping
 *    Date        : 2006/06/15
 *
 *    Revision history
 *    Date          Revision    Author                Description
 *    2006/06/15    V1.0        DIS.zhangjianping      新規作成
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.shozoku.teishutsuShorui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.vo.RyoikiKeikakushoInfo;
import jp.go.jsps.kaken.model.vo.RyoikiKeikakushoPk;
import jp.go.jsps.kaken.status.StatusCode;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 応募書類承認確認前アクションクラス。
 * 承認対象応募書類情報を取得。セッションに登録する。 
 * 応募書類承認画面を表示する。
 * 
 * ID RCSfile="$RCSfile: OuboSyoninCheckAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:45 $"
 */
public class OuboSyoninCheckAction extends BaseAction{
    
    /** 所属機関受付中(状況ID:03)以降の状況ID */
    private static String[] JOKYO_ID = new String[]{
            StatusCode.STATUS_GAKUSIN_SHORITYU,        //学振受付中
            StatusCode.STATUS_GAKUSIN_JYURI,          //学振受理
            StatusCode.STATUS_GAKUSIN_FUJYURI,        //学振不受理
            StatusCode.STATUS_SHINSAIN_WARIFURI_SHORIGO,      //審査員割り振り処理後
            StatusCode.STATUS_WARIFURI_CHECK_KANRYO,         //割り振りチェック完了
            StatusCode.STATUS_1st_SHINSATYU,            //一次審査中
            StatusCode.STATUS_1st_SHINSA_KANRYO,           //一次審査：判定
            StatusCode.STATUS_2nd_SHINSA_KANRYO,             //二次審査完了
            StatusCode.STATUS_RYOUIKISHOZOKU_UKETUKE  //領域代表者所属研究機関受付中
    };
    
    /* (非 Javadoc)
     * @see jp.go.jsps.kaken.web.struts.BaseAction#doMainProcessing(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, jp.go.jsps.kaken.web.common.UserContainer)
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

        //------応募書類承認情報システム番号の取得
        TeisyutuForm teisyutuForm = (TeisyutuForm)form;
        RyoikiKeikakushoPk ryoikikeikakushoPk = new RyoikiKeikakushoPk();
        
        //------キー情報
        String systemNo = teisyutuForm.getRyoikiSystemNo();
        ryoikikeikakushoPk.setRyoikiSystemNo(systemNo);
        
        RyoikiKeikakushoInfo ryoikiInfo = new RyoikiKeikakushoInfo();
        ryoikiInfo.setJokyoIds(JOKYO_ID);

        //------キー情報を元に申請データ取得  
        RyoikiKeikakushoInfo ryoikikeikakushoInfo = null;
        try{
            ryoikikeikakushoInfo = getSystemServise(IServiceName.TEISHUTU_MAINTENANCE_SERVICE).
                    searchOuboSyoruiInfo(container.getUserInfo(),ryoikikeikakushoPk,ryoikiInfo);
        } catch(NoDataFoundException ex){
            errors.add("データ検索中にDBエラーが発生しました。",new ActionError("errors.5002"));
        } catch(ApplicationException ex){
            errors.add("検索中にDBエラーが発生しました。",new ActionError("errors.4002"));
        }
        
        //------承認対象情報をリクエスト属性にセット
        container.setRyoikikeikakushoInfo(ryoikikeikakushoInfo);
        
        //-----画面遷移（定型処理）-----
        if (!errors.isEmpty()) {
            saveErrors(request, errors);
            return forwardFailure(mapping);
        }             
        return forwardSuccess(mapping);
    }
}

