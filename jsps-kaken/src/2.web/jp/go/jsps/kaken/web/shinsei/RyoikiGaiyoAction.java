/*======================================================================
 *    SYSTEM      : 日本学術振興会電子申請システム（科学研究費補助金）
 *    Source name : RyoikiGaiyoAction.java
 *    Description :  領域計画書作成画面の表示アクションクラス
 *
 *    Author      : DIS.miaom & DIS.dyh
 *    Date        : 2006/06/19
 *
 *    Revision history
 *    Date          Revision    Author                 Description
 *    2006/06/19    v1.0        DIS.miaom & DIS.dyh    新規作成
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.shinsei;

import java.util.HashMap;
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
import jp.go.jsps.kaken.util.StringUtil;
import jp.go.jsps.kaken.web.common.IConstants;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

/**
 * 領域計画書作成画面アクションクラス。
 * 領域計画書と領域計画書の一覧を取得する
 * 領域計画書作成画面を表示する。
 */
public class RyoikiGaiyoAction extends BaseAction{

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

        RyoikiGaiyoForm ryoikiGaiyoForm = (RyoikiGaiyoForm)form;
        String screenFlg = ryoikiGaiyoForm.getScreenFlg();
        String kariryoikiNo = ryoikiGaiyoForm.getKariryoikiNo();
        if(!StringUtil.isBlank(screenFlg) && "come".equals(screenFlg)){
            kariryoikiNo = "";
        }

        // サーバサービスの呼び出し（研究計画調書一覧データ取得）
        ISystemServise servise = getSystemServise(
                        IServiceName.TEISHUTU_MAINTENANCE_SERVICE);
        List resultList = servise.getRyoikiAndKenkyuList(
                container.getUserInfo(), kariryoikiNo);

        if(resultList == null || resultList.size() == 0
                || (List)resultList.get(0) == null
                || ((List)resultList.get(0)).size() == 0){
            errors.add("領域計画書一覧データ",new ActionError("errors.5002"));
        }

        //-----画面遷移（定型処理）-----
        if (!errors.isEmpty()) {
            saveErrors(request, errors);
            return forwardFailure(mapping);
        }

        // 他画面から遷移時(システム受付番号が未指定)、システム受付番号を設定
        if(StringUtil.isBlank(kariryoikiNo)){
            HashMap map = (HashMap)((List)resultList.get(0)).get(0);
            String ryoikiSystemNo0 = (String)map.get("RYOIKI_SYSTEM_NO");
            String kariryoikiNo0 = (String)map.get("KARIRYOIKI_NO");
            ryoikiGaiyoForm.setRyoikiSystemNo(ryoikiSystemNo0);
            ryoikiGaiyoForm.setKariryoikiNo(kariryoikiNo0);
        }

        // 検索結果をリクエスト属性にセット
        request.setAttribute(IConstants.RESULT_INFO, resultList);
        updateFormBean(mapping,request,ryoikiGaiyoForm);

        return forwardSuccess(mapping);
    }
}