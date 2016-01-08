/*======================================================================
 *    SYSTEM      : 日本学術振興会電子申請システム（科学研究費補助金）
 *    Source name : AddAction.java
 *    Description : 事業管理情報登録前アクションクラス
 *
 *    Author      : Admin
 *    Date        : 2003/11/14
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2003/11/14    V1.0        Admin          新規作成
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.gyomu.jigyoKanri;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.SystemException;
import jp.go.jsps.kaken.model.vo.GyomutantoInfo;
import jp.go.jsps.kaken.web.common.LabelValueManager;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;
import jp.go.jsps.kaken.web.struts.BaseForm;
import jp.go.jsps.kaken.util.DateUtil;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 * 事業管理情報登録前アクションクラス。
 * フォームに事業管理情報登録画面に必要なデータをセットする。
 * 事業管理情報新規登録画面を表示する。
 * 
 * ID RCSfile="$RCSfile: AddAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:04 $"
 */
public class AddAction extends BaseAction {

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

        //------新規登録フォーム情報の作成
        JigyoKanriForm addForm = new JigyoKanriForm();

        //------更新モード
        addForm.setAction(BaseForm.ADD_ACTION);

        // 業務担当者情報にログインした業務担当者IDをセット
        GyomutantoInfo gyomutantoInfo = new GyomutantoInfo();
        String gyomutantoId = (container.getUserInfo()).getGyomutantoInfo()
                .getGyomutantoId();
        gyomutantoInfo.setGyomutantoId(gyomutantoId);

        // ログインIDをキーとして業務担当者情報を取得
        gyomutantoInfo = getSystemServise(
                IServiceName.GYOMUTANTO_MAINTENANCE_SERVICE).select(
                container.getUserInfo(), gyomutantoInfo);

        //------取得した業務担当者情報をセット
        if (gyomutantoInfo != null) {
            // 業務担当課
            addForm.setTantokaName(gyomutantoInfo.getBukaName());
            // 業務担当係名
            addForm.setTantoKakari(gyomutantoInfo.getKakariName());
            // 問い合わせ先担当者名
            addForm.setToiawaseName(gyomutantoInfo.getNameKanjiSei() + " "
                    + gyomutantoInfo.getNameKanjiMei());

//2007/3/9  張志男　追加 ここから
            // 年度
            DateUtil dateUtil = new DateUtil();
            addForm.setNendo(dateUtil.getNendo());
//2007/3/9  張志男　追加 ここまで
        }
        else {
            throw new SystemException("該当する業務担当者が存在しません。業務担当者ID'"
                    + gyomutantoId + "'");
        }

        //------プルダウンデータセット
        // 事業リストの取得（担当する事業区分のみ）
        addForm.setJigyoNameList(LabelValueManager.getJigyoNameList(container
                .getUserInfo()));

        //------ラジオボタンデータセット
        // 評価用ファイル有無
        addForm.setFlgList(LabelValueManager.getFlgList());

        //------新規登録フォームにセットする。
        updateFormBean(mapping, request, addForm);

        //-----画面遷移（定型処理）-----
        if (!errors.isEmpty()) {
            saveErrors(request, errors);
            return forwardFailure(mapping);
        }
        return forwardSuccess(mapping);
    }
}