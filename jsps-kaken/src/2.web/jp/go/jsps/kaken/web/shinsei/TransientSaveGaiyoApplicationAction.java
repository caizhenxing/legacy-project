/*======================================================================
 *    SYSTEM      : 日本学術振興会電子申請システム（科学研究費補助金）
 *    Source name : RegistGaiyoApplicationAction
 *    Description : 領域計画書一時保存
 *
 *    Author      : 苗
 *    Date        : 2006/06/28
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2006/06/28    v1.0        苗苗　　　　               新規作成　　　　　　 　 
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.shinsei;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import jp.go.jsps.kaken.model.ISystemServise;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.ValidationException;
import jp.go.jsps.kaken.model.vo.ErrorInfo;
import jp.go.jsps.kaken.model.vo.RyoikiKeikakushoInfo;
import jp.go.jsps.kaken.model.vo.RyoikiKeikakushoPk;
import jp.go.jsps.kaken.util.FileResource;
import jp.go.jsps.kaken.web.common.LabelValueBean;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

/**
 * 領域計画書一時保存アクションクラス。
 * 領域計画書情報をデータベースに登録する。
 * 処理が正常に終了した場合、一時保存完了画面を返す。
 */
public class TransientSaveGaiyoApplicationAction extends BaseAction{

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

        //------キャンセル時      
        if (isCancelled(request)) {
            return forwardCancel(mapping);
        }

        //-----取得したトークンが無効であるとき
        if (!isTokenValid(request)) {
            errors.add(ActionErrors.GLOBAL_ERROR,
                       new ActionError("error.transaction.token"));
            saveErrors(request, errors);
            return forwardTokenError(mapping);
        }
        
        //-----領域計画書入力フォームの取得
        RyoikiGaiyoForm ryoikiGaiyoForm = (RyoikiGaiyoForm)form;

        //-----仮保存メソッドを呼び出す
        try{
            transientSave(container, ryoikiGaiyoForm);
        }catch(ValidationException e){
            List errorList = e.getErrors();
            for(int i=0; i<errorList.size(); i++){
                ErrorInfo errInfo = (ErrorInfo)errorList.get(i);
                errors.add(errInfo.getProperty(),
                           new ActionError(errInfo.getErrorCode(), errInfo.getErrorArgs()));
            }
        }

        //-----画面遷移（定型処理）-----
        if (!errors.isEmpty()) {
            saveErrors(request, errors);
            return forwardFailure(mapping);
        }

        // -----一時保存完了画面にて、SYSTEMNOが必要なためリクエストにセット
        RyoikiKeikakushoPk ryoikikeikakushoPk = new RyoikiKeikakushoPk(ryoikiGaiyoForm
                .getRyoikikeikakushoInfo().getRyoikiSystemNo());
        // -----SYSTEMNOをリクエスト属性にセット
        request.setAttribute("ryoikikeikakushoPk", ryoikikeikakushoPk);

        //------トークンの削除 
        resetToken(request);
        
        //------フォーム情報の削除
//        removeFormBean(mapping,request);        

        return forwardSuccess(mapping);
    }
    
    
    /**
     * 領域計画書を仮保存する。
     * @param container ログイン申請者情報
     * @param ryoikiGaiyoForms      入力フォームデータ
     * @throws ValidationException  データチェックエラーが発生した場合
     * @throws ApplicationException 仮保存に失敗した場合
     */
    private void transientSave(UserContainer container, RyoikiGaiyoForm ryoikiGaiyoForm)
            throws ValidationException, ApplicationException {
        //添付ファイル
        FileResource annexFileRes = null;
        try{
            FormFile file = ryoikiGaiyoForm.getUploadFile();
            if(file != null &&
               file.getFileData() != null && 
               file.getFileData().length != 0)
            {
                annexFileRes = new FileResource();
                annexFileRes.setPath(file.getFileName());   //ファイル名
                annexFileRes.setBinary(file.getFileData()); //ファイルサイズ
            }
        }catch(IOException e){
            throw new ApplicationException(
                "添付ファイルの取得に失敗しました。",
                new ErrorInfo("errors.7001"),
                e);
        }
        
        //領域計画書（概要）情報の取得
        RyoikiKeikakushoInfo ryoikikeikakushoInfo = ryoikiGaiyoForm.getRyoikikeikakushoInfo();
        
        //研究の必要性の値のリストを取得する
        List kenkyuHitsuyouseiValues = ryoikiGaiyoForm.getValuesList();
        
        //研究の必要性を初期値設定する
        ryoikikeikakushoInfo.setKenkyuHitsuyousei1("0");
        ryoikikeikakushoInfo.setKenkyuHitsuyousei2("0");
        ryoikikeikakushoInfo.setKenkyuHitsuyousei3("0");
        ryoikikeikakushoInfo.setKenkyuHitsuyousei4("0");
        ryoikikeikakushoInfo.setKenkyuHitsuyousei5("0");
        
        //研究の必要性：チェック済みの場合は「1」を設定する
        if (kenkyuHitsuyouseiValues.contains("1")) {
            ryoikikeikakushoInfo.setKenkyuHitsuyousei1("1");
        } 
        if (kenkyuHitsuyouseiValues.contains("2")) {
            ryoikikeikakushoInfo.setKenkyuHitsuyousei2("1");
        } 
        if (kenkyuHitsuyouseiValues.contains("3")) {
            ryoikikeikakushoInfo.setKenkyuHitsuyousei3("1");
        }
        if (kenkyuHitsuyouseiValues.contains("4")) {
            ryoikikeikakushoInfo.setKenkyuHitsuyousei4("1");
        }  
        if (kenkyuHitsuyouseiValues.contains("5")) {
            ryoikikeikakushoInfo.setKenkyuHitsuyousei5("1");
        }
        
        //関連分野１５分類（分類）リスト
        List kanrenbunyaBunruiList = ryoikiGaiyoForm.getKanrenbunyaBunruiList();
        //関連分野１５分類（番号）
        String kanrenbunyaBunruiNo = ryoikikeikakushoInfo.getKanrenbunyaBunruiNo();        
        
        //関連分野15分類（分類名）を設定する
        for(int i = 0 ; i < kanrenbunyaBunruiList.size() ; i++){
            LabelValueBean kanrenbunyaBunrui = (LabelValueBean) kanrenbunyaBunruiList.get(i);
            if(kanrenbunyaBunruiNo.equals(kanrenbunyaBunrui.getValue())){
                ryoikikeikakushoInfo.setKanrenbunyaBunruiName(kanrenbunyaBunrui.getLabel());
            }
        }
        
        //審査希望部門（系等）リスト
        List kiboubumonList = ryoikiGaiyoForm.getKiboubumonList();
        //審査希望部門（系等）コード
        String  kiboubumonNo = ryoikikeikakushoInfo.getKiboubumonCd();
        //審査希望部門（系等）名を設定する
        for(int i = 0 ; i < kiboubumonList.size() ; i++){
            LabelValueBean kiboubumon = (LabelValueBean) kiboubumonList.get(i);
            if(kiboubumonNo.equals(kiboubumon.getValue())){
                ryoikikeikakushoInfo.setKiboubumonName(kiboubumon.getLabel());
            }
        }

        ryoikiGaiyoForm.outputFileInfo();

        //-----新規か更新か分岐（受付番号がセットされているかどうか）
        String uketukeNo = ryoikikeikakushoInfo.getUketukeNo();
        if(uketukeNo == null || uketukeNo.length() == 0){
            //サーバサービスの呼び出し（一時保存）
            ISystemServise servise = getSystemServise(
                                IServiceName.SHINSEI_MAINTENANCE_SERVICE);
            RyoikiKeikakushoInfo newInfo = servise.transientGaiyoApplicationNew(
                                            container.getUserInfo(),
                                            ryoikikeikakushoInfo,
                                            annexFileRes);
            //フォームに登録された申請データをセットする
            ryoikiGaiyoForm.setRyoikikeikakushoInfo(newInfo);
        } else {
            ISystemServise servise = getSystemServise(
                                IServiceName.SHINSEI_MAINTENANCE_SERVICE);
            servise.transientGaiyoApplicationUpdate(
                                            container.getUserInfo(),
                                            ryoikikeikakushoInfo,
                                            annexFileRes);
        }
    }
}
