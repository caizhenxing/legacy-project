/*======================================================================
 *    SYSTEM      : 日本学術振興会電子申請システム（科学研究費補助金）
 *    Source name : JuriSaveTokuteiAction.java
 *    Description : 受理登録確認(特定)を行う。
 *
 *    Author      : Admin
 *    Date        : 2005/04/14
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2005/04/14    v1.0        Admin          新規作成
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.gyomu.checkList;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IJigyoKubun;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.CheckListSearchInfo;
import jp.go.jsps.kaken.util.Page;
import jp.go.jsps.kaken.web.common.IConstants;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import jp.go.jsps.kaken.web.common.*;

/**
 * 受理登録確認(特定)を行う。
 * 
 * @author masuo_t
 */
public class JuriTokuteiAction extends BaseAction {

// 2006/07/21 dyh delete start 理由：使用しない
//    /** 状況IDが04(学振受付中)のものを表示 */
//    private static String[] JIGYO_IDS = new String[]{
//        StatusCode.STATUS_GAKUSIN_SHORITYU        //学振受付中
//    };
// 2006/07/21 dyh delete end

    public ActionForward doMainProcessing(
            ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response,
            UserContainer container)
            throws ApplicationException {

        CheckListForm checkListForm = (CheckListForm) form;                //検索条件の取得
        JuriCheckListForm juriForm = new JuriCheckListForm();            //受理フォーム生成
        CheckListSearchInfo searchInfo = new CheckListSearchInfo();        //-------▼ VOにデータをセットする。

        //検索条件の設定
        if (checkListForm.getJigyoId() != null
                && !checkListForm.getJigyoId().equals("")) {
            searchInfo.setJigyoCd(checkListForm.getJigyoId().substring(2, 7));
            searchInfo.setJigyoId(checkListForm.getJigyoId());
            juriForm.setJigyoCD(checkListForm.getJigyoId().substring(2, 7));
            juriForm.setJigyoID(checkListForm.getJigyoId());
        }
        if (checkListForm.getShozokuCd() != null
                && !checkListForm.getShozokuCd().equals("")) {
            searchInfo.setShozokuCd(checkListForm.getShozokuCd());
            juriForm.setShozokuCD(checkListForm.getShozokuCd());
        }
        if(checkListForm.getKaisu() != null
                && !checkListForm.getKaisu().equals("")){
            searchInfo.setKaisu(checkListForm.getKaisu());
            juriForm.setKaisu(checkListForm.getKaisu());
        }

// 20050719
        // 状況IDが04または07のものを表示
        if(checkListForm.getJokyoId() != null
                && !checkListForm.getJokyoId().equals("")){
            String[] JokyoStr = new String[]{checkListForm.getJokyoId()};
            searchInfo.setSearchJokyoId(JokyoStr);                    //状況IDを検索条件にセット
            juriForm.setJokyoID(checkListForm.getJokyoId());        //状況IDを受理フォームにセット
        }
//        //状況IDが04(学振受付中)のものを表示
//        searchInfo.setSearchJokyoId(JIGYO_IDS);
// Horikoshi
        searchInfo.setJigyoKubun(IJigyoKubun.JIGYO_KUBUN_TOKUTEI);        //検索条件に特定領域の事業区分を追加

        // 検索実行
        Page result = getSystemServise(
                IServiceName.CHECKLIST_MAINTENANCE_SERVICE).selectCheckList(
                container.getUserInfo(), searchInfo, true);

        //検索結果をセットする
        juriForm.setCheckListPage(result);                                    //検索結果
        juriForm.setJuriFujuri(LabelValueManager.getJuriFujuriList());        //リスト項目
        juriForm.setJuriKekka(checkListForm.getJokyoId());                    //リスト表示
        request.setAttribute(IConstants.RESULT_INFO, result);                //

        //受理備考を検索結果から取得して表示
        HashMap dataMap = (HashMap)result.getList().get(0);
        Object juriBiko = dataMap.get("JURI_BIKO");
        if(juriBiko != null){
            juriForm.setJuriBiko(juriBiko.toString());
        }

        //TODO：setAttribute()の使用方法
        request.getSession().setAttribute("juriCheckListForm", juriForm);    //受理フォーム
//        updateFormBean(mapping, request, juriForm);

        //トークンをセッションに保存する。
        saveToken(request);
        return forwardSuccess(mapping);
    }
}