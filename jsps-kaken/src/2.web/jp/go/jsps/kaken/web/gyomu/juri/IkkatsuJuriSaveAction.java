/*======================================================================
 *    SYSTEM      : 日本学術振興会電子申請システム（科学研究費補助金）
 *    Source name : IkkatsuJuriSaveAction.java
 *    Description : 一括受理処理アクション
 *
 *    Author      : yoshikawa_h
 *    Date        : 2005/04/21
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2005/04/21    V1.0                       新規作成
 *    2005/06/02    V1.1        DIS.dyh  　　           修正（受理登録対象応募情報一覧画面ラジオを追加するから）
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.gyomu.juri;

import java.util.List;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.ISystemServise;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.util.StringUtil;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 一括受理処理アクション
 * @author yoshikawa_h
 */
public class IkkatsuJuriSaveAction extends BaseAction {
	
	public ActionForward doMainProcessing(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response,
		UserContainer container)
		throws ApplicationException {

		//-----ActionErrorsの宣言（定型処理）-----
		ActionErrors errors = new ActionErrors();
        
        if (isCancelled(request)) {
//         removeFormBean(mapping,request);
           return forwardCancel(mapping);
        }

//update start dyh 2006/06/02
//        //------事業情報保持クラス
//        JigyoInfo jigyoInfo = new JigyoInfo();
//        
//        //-------▼ VOにデータをセットする。
//        ShinseiSearchInfo searchInfo = new ShinseiSearchInfo();
//
//        //-----一括受理登録対象
//        //自分が担当する事業コードのみ
//        if(container.getUserInfo().getRole().equals(UserRole.GYOMUTANTO)){
//            GyomutantoInfo gyomutantoInfo = container.getUserInfo().getGyomutantoInfo(); 
//            searchInfo.setTantoJigyoCd(gyomutantoInfo.getTantoJigyoCd());
//        }
//        
//        //区分が基盤以外
//        ArrayList array = new ArrayList();
//        array.add("1");
//        array.add("2");
//        array.add("3");
//        searchInfo.setJigyoKubun(array);
//        
//        //ステータスが受理前
//        CombinedStatusSearchInfo statusInfo = new CombinedStatusSearchInfo();
//        String[] saishinseiArray = new String[]{StatusCode.SAISHINSEI_FLG_DEFAULT,
//                                                         StatusCode.SAISHINSEI_FLG_SAISHINSEIZUMI};     //再申請フラグ（初期値、再申請済み）
//        statusInfo.addOrQuery(StatusCode.STATUS_GAKUSIN_SHORITYU, saishinseiArray);                     //「学振処理中」:04
//        searchInfo.setStatusSearchInfo(statusInfo);
//
//        ISystemServise servise = getSystemServise(
//                IServiceName.SHINSEI_MAINTENANCE_SERVICE);
//        
//        Page juriTaisho = null;
//        try {
//            juriTaisho =
//                servise.searchApplication(container.getUserInfo(),searchInfo);
//        }
//        catch(ApplicationException e) {
//            //------該当担当者なし→通常ありえないので空表示
//        }
//        
//        if(juriTaisho == null){
//            return forwardFailure(mapping);
//        }
//        //------システム受付番号のpk配列
//        ArrayList pks = new ArrayList();
//        
//        //------該当件数分、繰り返し
//        for(int i = 0; i < juriTaisho.getSize(); i++){
//            
//            //------各申請情報を取得
//            HashMap shinseishaDataMap = (HashMap)juriTaisho.getList().get(i);
//            
//            //システム受付番号を配列に格納
//            pks.add(shinseishaDataMap.get("SYSTEM_NO"));
//        }
//
//        //一括受理
//        List lstErrors = new ArrayList();
//        lstErrors = servise.registShinseiJuriAll(container.getUserInfo(),pks);

		ISystemServise servise = getSystemServise(
				IServiceName.SHINSEI_MAINTENANCE_SERVICE);

        JuriListForm listForm = (JuriListForm)form;
        List pks = new ArrayList();
        for (int i = 0; i < listForm.getSystemNos().length; i++) {
            if (!StringUtil.isBlank(listForm.getSystemNos()[i]) ) {
                pks.add(listForm.getSystemNos()[i]);
            }
        }
		//一括受理
		List lstErrors = new ArrayList();
		lstErrors = servise.registShinseiJuriAll(container.getUserInfo(), pks);
//update end dyh 2006/06/02
		for(int count=0; count<lstErrors.size();count++){
			ActionError error = new ActionError("errors.5051", lstErrors.get(count).toString());
			errors.add("一括受理でエラーが発生しました。", error);
		}

		//-----画面遷移（定型処理）-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}

		//------トークンの削除
		resetToken(request);
		//------フォーム情報の削除
		removeFormBean(mapping,request);
        //------セッションより新規登録情報の削除
        container.setShinseishaInfo(null);

		return forwardSuccess(mapping);
	}
}