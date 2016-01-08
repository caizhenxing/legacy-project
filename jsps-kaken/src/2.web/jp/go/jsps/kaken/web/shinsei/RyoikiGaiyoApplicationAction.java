/*======================================================================
 *    SYSTEM      : 日本学術振興会電子申請システム（科学研究費補助金）
 *    Source name : RyoikiGaiyoApplicationAction.java
 *    Description : 領域計画書入力
 *
 *    Author      : 苗
 *    Date        : 2006/06/20
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2006/06/20    v1.0        苗苗                        新規作成　 
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.shinsei;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import jp.go.jsps.kaken.model.ISystemServise;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.JigyoKanriPk;
import jp.go.jsps.kaken.model.vo.RyoikiKeikakushoInfo;
import jp.go.jsps.kaken.model.vo.ShinseishaInfo;
import jp.go.jsps.kaken.web.common.LabelValueManager;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

/**
 * 領域計画書入力前アクションクラス。
 * フォームに申請書入力録画面に必要なデータをセットする。
 * 領域計画書入力画面を表示する。
 */
public class RyoikiGaiyoApplicationAction extends BaseAction{
    
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
        
        //-----領域概要フォームの取得
        RyoikiGaiyoForm ryoikiGaiyoForm = getInputNewForm(container, (RyoikiGaiyoForm)form);
       
        //研究領域最終年度前年度の応募有無リスト
        ryoikiGaiyoForm.setZenNendoOuboFlgList(LabelValueManager.getBuntankinList());
        
        //研究組織経費の研究項目年目小計値の初期値を設定する
// 2006/08/25 dyh delete start 原因：仕様変更
        //ryoikiGaiyoForm.getRyoikikeikakushoInfo().setKenkyuSyokei1("");
// 2006/08/25 dyh delete end
        ryoikiGaiyoForm.getRyoikikeikakushoInfo().setKenkyuSyokei2("");
        ryoikiGaiyoForm.getRyoikikeikakushoInfo().setKenkyuSyokei3("");
        ryoikiGaiyoForm.getRyoikikeikakushoInfo().setKenkyuSyokei4("");
        ryoikiGaiyoForm.getRyoikikeikakushoInfo().setKenkyuSyokei5("");
        ryoikiGaiyoForm.getRyoikikeikakushoInfo().setKenkyuSyokei6("");
        
        ryoikiGaiyoForm.setRyoikiSystemNo(ryoikiGaiyoForm.getRyoikikeikakushoInfo().getRyoikiSystemNo());
        ryoikiGaiyoForm.setKariryoikiNo(ryoikiGaiyoForm.getRyoikikeikakushoInfo().getKariryoikiNo());
        
        //-----領域概要フォームにセットする。
        updateFormBean(mapping, request, ryoikiGaiyoForm);

        //-----画面遷移（定型処理）-----
        if (!errors.isEmpty()) {
            saveErrors(request, errors);
            return forwardFailure(mapping);
        }
        
        //トークンをセッションに保存する。
        saveToken(request);
        
        return forwardSuccess(mapping);
    }
    
    /**
     * 領域計画書入力用フォーム取得メソッド。
     * @param container 申請者情報
     * @param rgForm 領域計画書入力フォーム
     * @return 新規用領域計画書入力フォーム
     * @throws ApplicationException
     */
    private RyoikiGaiyoForm getInputNewForm(UserContainer container, RyoikiGaiyoForm rgForm)
        throws ApplicationException {
        
        rgForm.getRyoikikeikakushoInfo().setJigyoId(rgForm.getJigyoId());
        rgForm.getRyoikikeikakushoInfo().setRyoikiSystemNo(rgForm.getRyoikiSystemNo());
        
        //事業IDを取得する
        String jigyoId = rgForm.getRyoikikeikakushoInfo().getJigyoId();
        String ryoikiSystemNo = rgForm.getRyoikikeikakushoInfo().getRyoikiSystemNo();
        
        //事業管理主キーオブジェクトの生成
        JigyoKanriPk jigyoKanriPk = new JigyoKanriPk(jigyoId);
        
        //DBよりレコードを取得
        ISystemServise servise = getSystemServise(
                        IServiceName.SHINSEI_MAINTENANCE_SERVICE);
        Map resultMap = servise.selectRyoikiKeikakushoInfoForInput(container.getUserInfo(), jigyoKanriPk, ryoikiSystemNo);
            
        //領域計画書（概要）情報、各プルダウンメニューリスト取得
        RyoikiKeikakushoInfo ryoikikeikakushoInfo = (RyoikiKeikakushoInfo)resultMap.get(ISystemServise.KEY_RYOIKIKEIKAKUSHO_INFO);
        //審査希望部門リスト
        List kiboubumonList = (List)resultMap.get(ISystemServise.KEY_KIBOUBUMON_LIST);
        //事前調査リスト
        List jizenchousaList = (List)resultMap.get(ISystemServise.KEY_JIZENCHOUSA_LIST);
        //研究の必要性リスト
        List kenkyuHitsuyouseiList = (List)resultMap.get(ISystemServise.KEY_KENKYUHITSUYOUSEI_LIST);
        //15分類リスト
        List kanrenbunyaBunruiList = (List)resultMap.get(ISystemServise.KEY_KANRENBUNYABUNRUI_LIST);
        //職名リスト
        List shokushuList = (List)resultMap.get(ISystemServise.KEY_SHOKUSHU_LIST);
        //参考資料ファイル選択リスト
        List tenpuFileList = (List)resultMap.get(ISystemServise.KEY_RYOIKITENPUFLAG_LIST);
           
        //領域計画書（概要）情報入力フォームの生成
        RyoikiGaiyoForm ryoikiGaiyoForm = new RyoikiGaiyoForm();
        
        ShinseishaInfo shinseishaInfo = container.getUserInfo().getShinseishaInfo();
        //部局CD、部局名、職名CD、職名（和文）はセッションの応募者情報を初期表示
        ryoikikeikakushoInfo.setBukyokuCd(shinseishaInfo.getBukyokuCd());
        ryoikikeikakushoInfo.setBukyokuName(shinseishaInfo.getBukyokuName());
        ryoikikeikakushoInfo.setShokushuCd(shinseishaInfo.getShokushuCd());
        ryoikikeikakushoInfo.setShokushuNameKanji(shinseishaInfo.getShokushuNameKanji());
        
        ryoikiGaiyoForm.setRyoikikeikakushoInfo(ryoikikeikakushoInfo);
        ryoikiGaiyoForm.setKiboubumonList(kiboubumonList);
        ryoikiGaiyoForm.setJizenchousaList(jizenchousaList);
        ryoikiGaiyoForm.setKenkyuHitsuyouseiList(kenkyuHitsuyouseiList);
        ryoikiGaiyoForm.setKanrenbunyaBunruiList(kanrenbunyaBunruiList);
        ryoikiGaiyoForm.setShokushuList(shokushuList);
        ryoikiGaiyoForm.setTenpuFileList(tenpuFileList);

        return ryoikiGaiyoForm;
    }
}