/*======================================================================
 *    SYSTEM      : 日本学術振興会電子申請システム（科学研究費補助金）
 *    Source name : OuboDownloadAction.java
 *    Description : 応募書類の提出書出力(特定領域研究（新規領域）)アクション
 *
 *    Author      : DIS.liuYi
 *    Date        : 2006/06/14
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2006/06/14    V1.0        DIS.liuYi      新規作成
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.shozoku.teishutsuShorui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.RyoikiKeikakushoInfo;
import jp.go.jsps.kaken.status.StatusCode;
import jp.go.jsps.kaken.util.DownloadFileUtil;
import jp.go.jsps.kaken.util.FileResource;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 応募書類の提出書出力(特定領域研究（新規領域）)アクション
 * ID RCSfile="$RCSfile: OuboDownloadAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:45 $"
 */
public class OuboDownloadAction extends BaseAction {

    
    /** 所属機関受付中(状況ID:03)以降の状況ID */
    private static String[] JIGYO_IDS = new String[]{
            StatusCode.STATUS_GAKUSIN_SHORITYU,          //学振受付中
            StatusCode.STATUS_GAKUSIN_JYURI,             //学振受理
            StatusCode.STATUS_GAKUSIN_FUJYURI,           //学振不受理
            StatusCode.STATUS_SHINSAIN_WARIFURI_SHORIGO, //審査員割り振り処理後
            StatusCode.STATUS_WARIFURI_CHECK_KANRYO,     //割り振りチェック完了
            StatusCode.STATUS_1st_SHINSATYU,             //一次審査中
            StatusCode.STATUS_1st_SHINSA_KANRYO,         //一次審査：判定
            StatusCode.STATUS_2nd_SHINSA_KANRYO,         //二次審査完了
            StatusCode.STATUS_RYOUIKISHOZOKU_UKETUKE     //領域代表者所属研究機関受付中
    };

    /** 領域状況ID */
    private static String[] RYOIKI_JOKYO_IDS=new String[]{
             StatusCode.STATUS_GAKUSIN_SHORITYU,         //所属機関受付中
             StatusCode.STATUS_GAKUSIN_JYURI,            //所属機関却下
             StatusCode.STATUS_GAKUSIN_FUJYURI           //学振不受理
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

        //-------▼ VOにデータをセットする。
        RyoikiKeikakushoInfo ryoikiInfo = new RyoikiKeikakushoInfo();
        ryoikiInfo.setShozokuCd(container.getUserInfo().getShozokuInfo().getShozokuCd());

        //状況IDが所属機関受付中以降のものについて表示するよう修正
        ryoikiInfo.setJokyoIds(JIGYO_IDS);
        ryoikiInfo.setRyoikiJokyoIds(RYOIKI_JOKYO_IDS);

        //-----ビジネスロジック実行-----
        FileResource fileRes =
                getSystemServise(
                    IServiceName.TEISHUTU_MAINTENANCE_SERVICE).createOuboTeishutusho(
                    container.getUserInfo(),
                    ryoikiInfo);

        //-----ファイルのダウンロード
        DownloadFileUtil.downloadFile(response, fileRes);   

        return forwardSuccess(mapping);
    }
}