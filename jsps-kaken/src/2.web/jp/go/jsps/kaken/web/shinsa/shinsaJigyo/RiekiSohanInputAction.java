/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : jinbaogang
 *    Date        : 2006/10/25
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.shinsa.shinsaJigyo;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.IShinsaKekkaMaintenance;
import jp.go.jsps.kaken.model.common.ILabelKubun;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.SystemException;
import jp.go.jsps.kaken.model.vo.ShinsaKekkaInputInfo;
import jp.go.jsps.kaken.model.vo.ShinsaKekkaPk;
import jp.go.jsps.kaken.util.StringUtil;
import jp.go.jsps.kaken.web.common.LabelValueManager;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 利害関係意見表示。
 * 
 * ID RCSfile="$RCSfile: RiekiSohanInputAction.java,v $"
 * Revision="$Revision: 1.1 $" 
 * Date="$Date: 2007/06/28 02:06:59 $"
 */
public class RiekiSohanInputAction extends BaseAction {

    /* (非 Javadoc)
     * @see jp.go.jsps.kaken.web.struts.BaseAction#doMainProcessing(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, jp.go.jsps.kaken.web.common.UserContainer)
     */
    public ActionForward doMainProcessing(ActionMapping mapping,
            ActionForm form, HttpServletRequest request,
            HttpServletResponse response, UserContainer container)
            throws ApplicationException {

        // -----ActionErrorsの宣言（定型処理）-----
        ActionErrors errors = new ActionErrors();

        // ------キー情報
        ShinsaKekkaPk pkInfo = new ShinsaKekkaPk();
        pkInfo.setSystemNo(((ShinsaKekkaRigaiForm) form).getSystemNo()); // システム受付番号
        pkInfo.setShinsainNo(container.getUserInfo().getShinsainInfo()
                .getShinsainNo()); // 審査員番号
        pkInfo.setJigyoKubun(container.getUserInfo().getShinsainInfo()
                .getJigyoKubun()); // 事業区分

        // ------キー情報を元に更新データ取得
        ShinsaKekkaInputInfo selectInfo = getSystemServise(
                IServiceName.SHINSAKEKKA_MAINTENANCE_SERVICE)
                .selectShinsaKekkaForRiekiSohan(container.getUserInfo(), pkInfo);

        // ------更新対象審査結果情報より、登録フォーム情報の更新
        ShinsaKekkaRigaiForm inputForm = new ShinsaKekkaRigaiForm();

        try {
            PropertyUtils.copyProperties(inputForm, selectInfo);
        } catch (Exception e) {
            log.error(e);
            throw new SystemException("プロパティの設定に失敗しました。", e);
        }
        
        String[] labelNames = { ILabelKubun.RIGAI };
        HashMap labelMap = (HashMap) LabelValueManager.getLabelMap(labelNames);
        inputForm.setRigaiList((List) labelMap.get(ILabelKubun.RIGAI));
        
        //利害関係ラバルを設定
        if (!StringUtil.isBlank(inputForm.getRigai())) {
            selectInfo.setRigaiLabel(LabelValueManager.getlabelName(inputForm.getRigaiList(), 
                    inputForm.getRigai()));
        } else {
            selectInfo.setRigaiLabel(LabelValueManager.getlabelName(inputForm.getRigaiList(), 
                    IShinsaKekkaMaintenance.RIGAI_OFF));
        }
        
        // ------表示対象情報をセッションに登録。
        container.setShinsaKekkaInputInfo(selectInfo);

        // ------新規登録フォームにセットする。
        updateFormBean(mapping, request, inputForm);

        // -----画面遷移（定型処理）-----
        if (!errors.isEmpty()) {
            saveErrors(request, errors);
            return forwardFailure(mapping);
        }
        saveToken(request);

        return forwardSuccess(mapping);
    }
}