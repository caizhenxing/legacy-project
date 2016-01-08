/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : 張楠
 *    Date        : 2007/7/16
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.shinsei.kenkyusoshiki;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.dao.exceptions.DataAccessException;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.vo.shinsei.KenkyuSoshikiKenkyushaInfo;
import jp.go.jsps.kaken.status.StatusCode;
import jp.go.jsps.kaken.util.StringUtil;
import jp.go.jsps.kaken.web.common.IConstants;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.shinsei.RyoikiGaiyoForm;
import jp.go.jsps.kaken.web.struts.BaseAction;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 研究組織表アクションクラス。
 * 研究組織表一覧画面を表示する。
 * 
 * ID RCSfile="$RCSfile: SortingKenkyuSoshikiAction.java,v $"
 * Revision="$Revision: 1.15 $"
 * Date="$Date: 2007/07/19 02:11:57 $"
 */
public class SortingKenkyuSoshikiAction extends BaseAction {
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
        //      前画面で仮領域番号
        kenkyuSoshikiKenkyushaInfo.setKariryoikiNo(ryoikiGaiyoForm.getRyoikikeikakushoInfo().getKariryoikiNo());
        kenkyuSoshikiKenkyushaInfo.setJokyoId(JOKYO_ID);
        
        List result = ryoikiGaiyoForm.getShinseiDataInfo().getKenkyuSoshikiInfoList();

        try{
        	//表示順のチェック
        	ryoikiGaiyoForm.setErrorsList(null);
        	List errorsList = new ArrayList();
        	if(result != null && result.size()>0){
        		for (int i=0;i < result.size();i++){
        			String hyojijun = request.getParameter("shinseiDataInfo.kenkyuSoshikiInfoList["+i+"].HYOJIJUN");
        			((HashMap)(result).get(i)).put("HYOJIJUN",hyojijun);
                	if (hyojijun != null && !"".equals(hyojijun)){
                    	String property = "shinseiDataInfo.kenkyuSoshikiInfoList["+i+"].HYOJIJUN";
                    	String meg = "表示順 " + (i+1) + " 行目";
                        if (hyojijun.length()>3) {
    						ActionError error = new ActionError("errors.length",meg);
    						errors.add(property, error);
    						errorsList.add("表示順 " + (i+1) + "行目は3桁で入力してください。");
                        }
                        if (!StringUtil.isDigit(hyojijun)) {
    						ActionError error = new ActionError("errors.numeric",meg);
    						errors.add(property, error);
    						errorsList.add("表示順 " + (i+1) + "行目は半角数字で入力してください。");
                        }
                	}
        		}
        	}
        	
            // -----画面遷移（定型処理）-----
            if (!errors.isEmpty()) {
                saveErrors(request, errors);
                ryoikiGaiyoForm.setErrorsList(errorsList);
                return forwardFailure(mapping);
            }

        	if(result != null && result.size()>0){
                for (int i=0;i < result.size();i++){
                	String hyojijun = (String)((HashMap)(result).get(i)).get("HYOJIJUN");
                	//String hyojijun = request.getParameter("shinseiDataInfo.kenkyuSoshikiInfoList["+i+"].HYOJIJUN");
                	if (hyojijun != null){
                    	if( hyojijun.length() == 2){
                    		hyojijun =  "0"+hyojijun;
                    	}else if( hyojijun.length() == 1){
                    		hyojijun =  "00"+hyojijun;
                    	}else if( hyojijun.length() == 0){
                    		hyojijun =  "000";
                    	}
                    	kenkyuSoshikiKenkyushaInfo.setHyojijun(hyojijun);
                		kenkyuSoshikiKenkyushaInfo.setSystemNo((String)((HashMap)(result).get(i)).get("SYSTEM_NO"));
                		
                    	getSystemServise(
                				IServiceName.SHINSEI_MAINTENANCE_SERVICE).updateHyojijun(
                				container.getUserInfo(),
                				kenkyuSoshikiKenkyushaInfo);
                	}
                }
        	}

        	 result =
    			getSystemServise(
    				IServiceName.SHINSEI_MAINTENANCE_SERVICE).searchKenkyuSosiki(
    				container.getUserInfo(),
    				kenkyuSoshikiKenkyushaInfo);
        	 
        	 ryoikiGaiyoForm.getShinseiDataInfo().setKenkyuSoshikiInfoList(result);
        	 
        }catch(NoDataFoundException e){
            errors.add("該当データはありません", new ActionError("errors.5002"));
        }catch(DataAccessException e){
            errors.add("データ検索中にDBエラーが発生しました。",new ActionError("errors.4000"));
        }


        // 検索結果をフォームにセットする。
        request.setAttribute(IConstants.RESULT_INFO, ryoikiGaiyoForm);

        return forwardSuccess(mapping);
    }
}
