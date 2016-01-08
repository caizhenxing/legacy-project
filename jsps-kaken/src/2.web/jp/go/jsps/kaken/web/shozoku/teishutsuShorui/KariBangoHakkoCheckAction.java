/*======================================================================
 *    SYSTEM      : 日本学術振興会電子申請システム（科学研究費補助金）
 *    Source name : KariBangoHakkoCheckAction.java
 *    Description : 仮領域番号発行確認前アクションクラス
 *
 *    Author      : liuyi
 *    Date        : 2006/06/14
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *   2006/06/14      V1.0        DIS.liuyi        新規作成
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.shozoku.teishutsuShorui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.RyoikiKeikakushoInfo;
import jp.go.jsps.kaken.model.vo.RyoikiKeikakushoPk;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

/**
 * 仮領域番号発行確認前アクションクラス。
 * 確認対象仮領域情報を取得。セッションに登録する。 
 * 仮領域番号発行確認画面を表示する。
 * 
 * ID RCSfile="$RCSfile: KariBangoHakkoCheckAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:45 $"
 */
public class KariBangoHakkoCheckAction extends BaseAction{

    /**
     * Actionクラスの主要な機能を実装する。
     * 戻り値として、次の遷移先をActionForward型で返する。
     */
    public ActionForward doMainProcessing(ActionMapping mapping,
            ActionForm form, HttpServletRequest request,
            HttpServletResponse response, UserContainer container)
            throws ApplicationException {

        //------確認対象仮領域情報システム番号の取得
        TeisyutuForm teisyutuForm = (TeisyutuForm)form;

        //------確認対象システム番号の取得
        RyoikiKeikakushoPk ryoikiPk = new RyoikiKeikakushoPk();
        //------キー情報  
        ryoikiPk.setRyoikiSystemNo(teisyutuForm.getRyoikiSystemNo());
        
        //------キー情報を元に申請データ取得
        RyoikiKeikakushoInfo ryoikiInfo = getSystemServise(
                IServiceName.TEISHUTU_MAINTENANCE_SERVICE).selectRyoikiInfo(
                container.getUserInfo(), ryoikiPk);
        
        //------確認対象情報をリクエスト属性にセット
        container.setRyoikikeikakushoInfo(ryoikiInfo);
        
        //-----画面遷移（定型処理）-----
        return forwardSuccess(mapping);
    }
}