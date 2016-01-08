/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2003/11/14
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.shinsa.shinsaJigyo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.IShinsaKekkaMaintenance;
import jp.go.jsps.kaken.model.ISystemServise;
import jp.go.jsps.kaken.model.common.IServiceName;

import jp.go.jsps.kaken.model.exceptions.ApplicationException;

import jp.go.jsps.kaken.model.vo.ShinsaKekkaInputInfo;

import jp.go.jsps.kaken.web.common.IConstants;
import jp.go.jsps.kaken.web.common.LabelValueManager;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 利害関係意見入力情報値オブジェクトを更新する。 
 * フォーム情報、更新情報をクリアする。
 */
public class RiekiSohanInputSaveAction extends BaseAction {

    /* (非 Javadoc)
     * @see jp.go.jsps.kaken.web.struts.BaseAction#doMainProcessing(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, jp.go.jsps.kaken.web.common.UserContainer)
     */
    public ActionForward doMainProcessing(ActionMapping mapping,
            ActionForm form, HttpServletRequest request,
            HttpServletResponse response, UserContainer container)
            throws ApplicationException {

        // -----ActionErrorsの宣言（定型処理）-----
        ActionErrors errors = new ActionErrors();

        // ------キャンセル時
        if (isCancelled(request)) {
            return forwardCancel(mapping);
        }

        // -----取得したトークンが無効であるとき
        if (!isTokenValid(request)) {
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
                    "error.transaction.token"));
            saveErrors(request, errors);
            return forwardTokenError(mapping);
        }

        // ------新規登録フォーム情報の取得
        ShinsaKekkaRigaiForm addForm = (ShinsaKekkaRigaiForm) form;

        // ------セッションより更新情報の取得
        ShinsaKekkaInputInfo addInfo = container.getShinsaKekkaInputInfo();

        // -------▼ VOにデータをセットする。
        addInfo.setComments(addForm.getComments());
        addInfo.setRigai(addForm.getRigai());
        // 　利害関係にチェックして「保存」ボタンを押した場合、審査結果情報をクリアとする
        if(IShinsaKekkaMaintenance.RIGAI_ON.equals(addForm.getRigai())){
        	//若手研究Sとしての妥当性　2007/5/11追加
        	addInfo.setWakates(null);
			//学術的重要性・妥当性
			addInfo.setJuyosei(null);        
			//研究計画
			addInfo.setKenkyuKeikaku(null);
			//独創性・革新性
			addInfo.setDokusosei(null);
			//波及効果・普遍性
			addInfo.setHakyukoka(null);
			//遂行能力・環境の適切性
			addInfo.setSuikonoryoku(null);
			//適切性-海外、萌芽
			addInfo.setTekisetsuKaigai(null);
			//総合評価（点数）
			addInfo.setKekkaTen("-");
			addInfo.setKekkaTenLabel("-");
			addInfo.setKekkaTenHogaLabel("-");
			//妥当性
			addInfo.setDato(null);
			//分担金
			addInfo.setBuntankin(null);
			//人権
			addInfo.setJinken(null);
			//その他のコメント
			addInfo.setOtherComment(null);
        }

        if (addForm.getRigai() != null && !addForm.getRigai().equals("")) {
            addInfo.setRigaiLabel(LabelValueManager.getlabelName(addForm.getRigaiList(), 
                                                                 addForm.getRigai()));
        }

        // DB登録
        ISystemServise service = getSystemServise(IServiceName.SHINSAKEKKA_MAINTENANCE_SERVICE);
        service.registRiekiSohan(container.getUserInfo(), addInfo);

        if (log.isDebugEnabled()) {
            log.debug("審査結果入力情報　登録情報 '" + addInfo);
        }

        // -----セッションの審査結果入力情報をリセット
        container.setShinsaKekkaInputInfo(addInfo);

        // -----フォームを削除
        removeFormBean(mapping, request);

        // 完了画面に入力情報を表示するためここでセット
        request.setAttribute(IConstants.RESULT_INFO, addInfo);

        // ------トークンの削除
        resetToken(request);

        // -----画面遷移（定型処理）-----

        if (!errors.isEmpty()) {
            saveErrors(request, errors);
            return forwardFailure(mapping);
        }
        return forwardSuccess(mapping);
    }
}