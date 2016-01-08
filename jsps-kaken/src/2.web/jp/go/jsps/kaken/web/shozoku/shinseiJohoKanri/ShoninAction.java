/*======================================================================
 *    SYSTEM      : 日本学術振興会電子申請システム（科学研究費補助金）
 *    Source name : ShoninAction.java
 *    Description : 研究計画調書承認画面を表示する
 *
 *    Author      : Admin
 *    Date        : 2004/02/12
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2004/02/12    V1.0        Admin          新規作成
 *    2006/06/05    V1.1        DIS.wangXC     修正（研究計画調書承認画面を追加するから）
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.shozoku.shinseiJohoKanri;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.vo.ShinseiDataPk;
import jp.go.jsps.kaken.model.vo.SimpleShinseiDataInfo;
import jp.go.jsps.kaken.util.StringUtil;
import jp.go.jsps.kaken.web.common.IConstants;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 申請情報承認前アクションクラス。
 * 承認対象申請情報を取得。セッションに登録する。 
 * 研究計画調書承認画面を表示する。
 * 
 * ID RCSfile="$RCSfile: ShoninAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:39 $"
 */
public class ShoninAction extends BaseAction {

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

		//## 削除情報プロパティ(セッションに保持)　$!userContainer.shinseishaInfo.プロパティ名

		//-----ActionErrorsの宣言（定型処理）-----
		ActionErrors errors = new ActionErrors();

		//------承認対象申請情報システム番号の取得
		ShinseiDataForm shoninForm = (ShinseiDataForm)form;

// 2006/06/05 WangXiancheng update start
        if (isCancelled(request)) {
            removeFormBean(mapping,request);
            return forwardCancel(mapping);
        }
//		//------承認対象申請システム番号の取得
//		ShinseiDataPk pkInfo = new ShinseiDataPk();
//		//------キー情報
//		String systemNo = shoninForm.getSystemNo();
//		pkInfo.setSystemNo(systemNo);
//        
//　　　　　//------キー情報を元に申請データ取得	
//		SimpleShinseiDataInfo shinseiInfo = getSystemServise(
//                IServiceName.SHINSEI_MAINTENANCE_SERVICE)
//                .selectSimpleShinseiDataInfo(container.getUserInfo(),pkInfo);
//		
//		//------承認対象情報をリクエスト属性にセット
//		container.setSimpleShinseiDataInfo(shinseiInfo);

        //------承認対象申請情報システム番号の取得
        String[] sysNos = shoninForm.getTantoSystemNo();
        ArrayList selectNos = new ArrayList();
        for (int i = 0; i < sysNos.length; i ++) {
           if (! StringUtil.isBlank(sysNos[i])) {
               selectNos.add(sysNos[i]);
           }
        } 
        if(selectNos.size()==0) {
            errors.add(ActionErrors.GLOBAL_ERROR,
                    new ActionError("errors.requiredSelect", "承認する応募情報"));
            saveErrors(request, errors);
            return forwardFailure(mapping);
        }
        //------承認対象申請システム番号の取得
        ShinseiDataPk[] pkInfo  = new ShinseiDataPk[selectNos.size()];
        for (int i = 0; i < selectNos.size(); i ++) {
            pkInfo[i] = new ShinseiDataPk();
            pkInfo[i].setSystemNo((String)selectNos.get(i));
        }       
		//------キー情報を元に申請データ取得
		SimpleShinseiDataInfo[] shinseiInfo = null;
		try {
            shinseiInfo = getSystemServise(IServiceName.SHINSEI_MAINTENANCE_SERVICE)
                    .selectSimpleShinseiDataInfos(container.getUserInfo(), pkInfo);
            // ------承認対象情報をリクエスト属性にセット
            container.setSimpleShinseiDataInfos(shinseiInfo);
        }
        catch (NoDataFoundException e) {
			errors.add(ActionErrors.GLOBAL_ERROR,
                    new ActionError(e.getErrorCode(), e.getMessage()));
		} catch (ApplicationException e) {
			errors.add(ActionErrors.GLOBAL_ERROR,
                    new ActionError(e.getErrorCode(), e.getMessage()));
		}
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		ArrayList list =new ArrayList();
        for(int i=0;i<shinseiInfo.length;i++){
           list.add(shinseiInfo[i]); 
        }        
        request.getSession().setAttribute(IConstants.RESULT_INFO,list);		
		//-----画面遷移（定型処理）-----
		//if (!errors.isEmpty()) {
		//	saveErrors(request, errors);
		//	return forwardFailure(mapping);
		//}
// 2006/06/05 WangXiancheng update end

		//トークンをセッションに保存する。
		saveToken(request);

		return forwardSuccess(mapping);
	}
}