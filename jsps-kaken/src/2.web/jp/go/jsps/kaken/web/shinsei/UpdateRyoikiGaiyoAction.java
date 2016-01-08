/*======================================================================
 *    SYSTEM      : 日本学術振興会電子申請システム（科学研究費補助金）
 *    Source name : RyoikiGaiyoApplicationAction.java
 *    Description : 応募情報修正画面(領域計画書概要修正)
 *
 *    Author      : 苗
 *    Date        : 2006/06/28
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2006/06/28    v1.1        苗苗　　　　　　　　　　 　 
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.shinsei;

import java.util.ArrayList;
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
import jp.go.jsps.kaken.util.StringUtil;
import jp.go.jsps.kaken.web.common.LabelValueManager;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

/**
 * 領域計画書修正前アクションクラス。
 * フォームに申請書修正画面に必要なデータをセットする。
 * 領域計画書修正画面を表示する。
 */

public class UpdateRyoikiGaiyoAction extends BaseAction{

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
        
        if(!StringUtil.isBlank(rgForm.getJigyoId()) && !StringUtil.isBlank(rgForm.getRyoikiSystemNo())){
            rgForm.getRyoikikeikakushoInfo().setJigyoId(rgForm.getJigyoId());
            rgForm.getRyoikikeikakushoInfo().setRyoikiSystemNo(rgForm.getRyoikiSystemNo());
        }
        
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
        //領域計画書（概要）情報の部局CDと応募者情報の部局CDが異なる場合
        if(!shinseishaInfo.getBukyokuCd().equals(ryoikikeikakushoInfo.getBukyokuCd())){
            //部局CD、部局名はセッションの応募者情報から設定する
            ryoikikeikakushoInfo.setBukyokuCd(shinseishaInfo.getBukyokuCd());
            ryoikikeikakushoInfo.setBukyokuName(shinseishaInfo.getBukyokuName());
        }
        
        //領域計画書（概要）情報の職名CDと応募者情報の職名CDが異なる場合
        if(!shinseishaInfo.getShokushuCd().equals(ryoikikeikakushoInfo.getShokushuCd())){
            //職名CD、職名（和文）はセッションの応募者情報から設定する
            ryoikikeikakushoInfo.setShokushuCd(shinseishaInfo.getShokushuCd());
            ryoikikeikakushoInfo.setShokushuNameKanji(shinseishaInfo.getShokushuNameKanji());
        }
        
        ryoikiGaiyoForm.setRyoikikeikakushoInfo(ryoikikeikakushoInfo);
        ryoikiGaiyoForm.setKiboubumonList(kiboubumonList);
        ryoikiGaiyoForm.setJizenchousaList(jizenchousaList);
        ryoikiGaiyoForm.setKenkyuHitsuyouseiList(kenkyuHitsuyouseiList);
        ryoikiGaiyoForm.setKanrenbunyaBunruiList(kanrenbunyaBunruiList);
        ryoikiGaiyoForm.setShokushuList(shokushuList);
        ryoikiGaiyoForm.setTenpuFileList(tenpuFileList);        
        
        //研究の必要性の設定
        List kenkyuHitsuyouseiValues = new ArrayList();
        if ("1".equals(ryoikikeikakushoInfo.getKenkyuHitsuyousei1())){
            kenkyuHitsuyouseiValues.add("1");
        } 
        if ("1".equals(ryoikikeikakushoInfo.getKenkyuHitsuyousei2())){
            kenkyuHitsuyouseiValues.add("2");
        }
        if ("1".equals(ryoikikeikakushoInfo.getKenkyuHitsuyousei3())){
            kenkyuHitsuyouseiValues.add("3");
        }
        if ("1".equals(ryoikikeikakushoInfo.getKenkyuHitsuyousei4())){
            kenkyuHitsuyouseiValues.add("4");
        }
        if ("1".equals(ryoikikeikakushoInfo.getKenkyuHitsuyousei5())){
            kenkyuHitsuyouseiValues.add("5");
        }
        ryoikiGaiyoForm.setValuesList(kenkyuHitsuyouseiValues);

        return ryoikiGaiyoForm;
    }
}
