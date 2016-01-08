/*======================================================================
 *    SYSTEM      : 日本学術振興会電子申請システム（科学研究費補助金）
 *    Source name : SearchListAction.java
 *    Description : 提出書類一覧表示アクションクラス
 *
 *    Author      : Dis.lwj
 *    Date        : 2006/06/14
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2006/06/14    V1.0        DIS.lwj        新規作成
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.gyomu.teishutsuShorui;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IJigyoCd;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.vo.TeishutsuShoruiSearchInfo;
import jp.go.jsps.kaken.status.StatusCode;
import jp.go.jsps.kaken.util.StringUtil;
import jp.go.jsps.kaken.web.common.IConstants;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 提出書類一覧アクションクラス。
 * 提出書類一覧画面を表示する。
 */
public class SearchListAction extends BaseAction{
    
    /** (状況ID:03)以降の状況ID */
    private static String[] JIGYO_IDS = new String[]{
        StatusCode.STATUS_GAKUSIN_SHORITYU ,              //学振受付中
        StatusCode.STATUS_GAKUSIN_JYURI ,                 //学振受理
        StatusCode.STATUS_GAKUSIN_FUJYURI,                //学振不受理
        StatusCode.STATUS_SHINSAIN_WARIFURI_SHORIGO,      //審査員割り振り処理後
        StatusCode.STATUS_WARIFURI_CHECK_KANRYO,          //割り振りチェック完了
        StatusCode.STATUS_1st_SHINSATYU,                  //一次審査中
        StatusCode.STATUS_1st_SHINSA_KANRYO,              //一次審査：判定
        StatusCode.STATUS_2nd_SHINSA_KANRYO               //二次審査完了
    };
    
    /** 04(学振受付中),06(学振受理), 07(学振不受理) */
    private static String[] JIGYO_IDS1 = new String[]{
        StatusCode.STATUS_GAKUSIN_SHORITYU,               //学振受付中
        StatusCode.STATUS_GAKUSIN_JYURI ,                 //学振受理
        StatusCode.STATUS_GAKUSIN_FUJYURI,                //学振不受理
    };
    
    /** 02(申請書未確認),03(所属機関受付中), 05(所属機関却下) */
    private static String[] JIGYO_IDS2 = new String[]{
        StatusCode.STATUS_SHINSEISHO_MIKAKUNIN,           //申請書未確認
        StatusCode.STATUS_SHOZOKUKIKAN_UKETUKETYU,        //所属機関受付中
        StatusCode.STATUS_SHOZOKUKIKAN_KYAKKA,            //所属機関却下   
    };
    
    /** 状況IDが06(学振受理)のものを表示 */
    private static String[] JOKYO_ID_JYURIZUMI = new String[]{
            StatusCode.STATUS_GAKUSIN_JYURI,              //学振受理
    };

    /** 状況IDが04(学振受付中：受理前)のものを表示 */
    private static String[] JOKYO_ID_JYURIMAE = new String[]{
            StatusCode.STATUS_GAKUSIN_SHORITYU,           //学振受付中
    };
   
    public ActionForward doMainProcessing(
        ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response,
        UserContainer container)
        throws ApplicationException {
        
        //-----ActionErrorsの宣言（定型処理）-----
        ActionErrors errors = new ActionErrors();
            
        //------キャンセル時        
        if (isCancelled(request)) {
            return forwardCancel(mapping);
        }
        
        //-------▼ VOにデータをセットする。
        TeishutsuShoruiSearchInfo searchInfo = new TeishutsuShoruiSearchInfo();
        //フォーム情報取得
        TeishutsuShoruiSearchForm searchForm = (TeishutsuShoruiSearchForm)form;

        // 検索条件の事業CDを設定
        searchInfo.setJigyoCd(IJigyoCd.JIGYO_CD_TOKUTEI_SINKI);

        // 検索条件の所属コードを設定
        if(!StringUtil.isBlank(searchForm.getShozokuCd())){
            searchInfo.setShozokuCd(searchForm.getShozokuCd().trim());
        }

        // 検索条件の所属機関名を設定
        if(!StringUtil.isBlank(searchForm.getShozokuName())){
            searchInfo.setShozokuName(searchForm.getShozokuName().trim());
        }

        // 検索条件の受理状況を設定
        String jokyoKubun = searchForm.getJuriJokyo();
        
        searchInfo.setJokyoKubun(jokyoKubun);
        searchInfo.setSearchJokyoId(JIGYO_IDS);
        searchInfo.setDelFlg("0");
        
        if(jokyoKubun == null || jokyoKubun.equals("") ||jokyoKubun.equals("0")){
            searchInfo.setSearchJokyoId1(JIGYO_IDS1);
            searchInfo.setSearchJokyoId2(JIGYO_IDS2);
        }
        else if(jokyoKubun.equals(StatusCode.STATUS_GAKUSIN_SHORITYU)){
            //状況IDが04(学振受付中)のものを表示 
            searchInfo.setSearchJokyoId1(JOKYO_ID_JYURIMAE);
            searchInfo.setSearchJokyoId2(null);
        }
        else if(jokyoKubun.equals(StatusCode.STATUS_GAKUSIN_JYURI )){
            //状況IDが06(学振受理)のものを表示
            searchInfo.setSearchJokyoId1(JOKYO_ID_JYURIZUMI);
            searchInfo.setSearchJokyoId2(null);
        }
        else if(jokyoKubun.equals("03")){           
            searchInfo.setSearchJokyoId2(JIGYO_IDS2);
            searchInfo.setSearchJokyoId1(null);
        }
        
        //ページ制御
        searchInfo.setStartPosition(searchForm.getStartPosition());
        
        List result = null;
        try{
            //検索実行
            result = 
                getSystemServise(
                    IServiceName.TEISHUTU_MAINTENANCE_SERVICE).selectTeishutuShoruiList(
                    container.getUserInfo(),
                    searchInfo);
        }catch(NoDataFoundException e){
            errors.add("該当データはありません。",new ActionError("errors.5002"));
        }

        //検索条件をコンテナに格納
        container.setTeishutsuShoruiSearchInfo(searchInfo);

        //検索結果をセットする。
        request.setAttribute(IConstants.RESULT_INFO, result);
        
        //-----画面遷移（定型処理）-----
        if (!errors.isEmpty()) {
            saveErrors(request, errors);
            return forwardFailure(mapping);
        }
        
        return forwardSuccess(mapping);
    }
}