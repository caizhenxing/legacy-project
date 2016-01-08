/*======================================================================
 *    SYSTEM      : 日本学術振興会電子申請システム（科学研究費補助金）
 *    Source name : kenkyuSosikiAction.java
 *    Description : 研究組織表アクション
 *
 *    Author      : 李義華
 *    Date        : 2006/06/16
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2006/06/16    V1.0        李義華     　　　　　　　新規作成
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.shinsei;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.dao.exceptions.DataAccessException;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.vo.shinsei.KenkyuSoshikiKenkyushaInfo;
import jp.go.jsps.kaken.status.StatusCode;
import jp.go.jsps.kaken.web.common.IConstants;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 研究組織表アクション
 * ID RCSfile="$RCSfile: KenkyuSosikiAction.java,v $"
 * Revision="$Revision: 1.10 $"
 * Date="$Date: 2007/07/21 06:08:03 $"
 */
public class KenkyuSosikiAction extends BaseAction{
	
    /**該当申請データ管理テーブル、JOKYO_ID=[21,23]の取得*/
    private static String[] JOKYO_ID = new String[]{
        StatusCode.STATUS_RYOUIKIDAIHYOU_KAKUNIN,     // 申請状況：「領域代表者確認中」
        StatusCode.STATUS_RYOUIKIDAIHYOU_KAKUTEIZUMI, // 申請状況：「領域代表者確定済み」
    };

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

        RyoikiGaiyoForm ryoikiGaiyoForm = (RyoikiGaiyoForm) form;
        KenkyuSoshikiKenkyushaInfo kenkyuSoshikiKenkyushaInfo = new KenkyuSoshikiKenkyushaInfo();

        // 前画面で仮領域番号
        kenkyuSoshikiKenkyushaInfo.setKariryoikiNo(ryoikiGaiyoForm.getRyoikikeikakushoInfo().getKariryoikiNo());
        
        kenkyuSoshikiKenkyushaInfo.setJokyoId(JOKYO_ID);
        
        List result = null;
//      <!-- UPDATE　START 2007/07/16 BIS 張楠 -->  
        //表示順のエラーメッセージをクリア
        ryoikiGaiyoForm.setErrorsList(null);
//      <!-- UPDATE　END 2007/07/16 BIS 張楠 -->  
        try{
        	 result =
    			getSystemServise(
    				IServiceName.SHINSEI_MAINTENANCE_SERVICE).searchKenkyuSosiki(
    				container.getUserInfo(),
    				kenkyuSoshikiKenkyushaInfo);
//        	<!-- ADD　START 2007/07/16 BIS 張楠 -->
        	 ryoikiGaiyoForm.getShinseiDataInfo().setKenkyuSoshikiInfoList(result);
//        	<!-- ADD　END 2007/07/16 BIS 張楠 -->
        }catch(NoDataFoundException ne){
            errors.add("該当データはありません", new ActionError("errors.5002"));
        }catch(DataAccessException e){
            errors.add("データ検索中にDBエラーが発生しました。",new ActionError("errors.4000"));
        }
        
        // -----画面遷移（定型処理）-----
        if (!errors.isEmpty()) {
            saveErrors(request, errors);
            return forwardFailure(mapping);
        }
        // 検索結果をフォームにセットする。
//      <!-- UPDATE　START 2007/07/16 BIS 張楠 -->        
        //request.setAttribute(IConstants.RESULT_INFO, result);
        updateFormBean(mapping, request, ryoikiGaiyoForm);
//      <!-- UPDATE　END 2007/07/16 BIS 張楠 -->
        return forwardSuccess(mapping);
    }

}
