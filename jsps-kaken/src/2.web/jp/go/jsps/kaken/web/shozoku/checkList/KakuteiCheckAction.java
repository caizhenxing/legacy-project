/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2005/03/31
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.shozoku.checkList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.SystemException;
import jp.go.jsps.kaken.model.vo.CheckListSearchInfo;
import jp.go.jsps.kaken.model.vo.ErrorInfo;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.List;
import java.util.ArrayList;
import jp.go.jsps.kaken.model.vo.UserInfo;

/**
 * チェックリスト確定確認アクションクラス。
 */
public class KakuteiCheckAction extends BaseAction {

	public ActionForward doMainProcessing(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response,
			UserContainer container)
			throws ApplicationException {

		//-------▼ VOにデータをセットする。
		CheckListSearchInfo checkInfo = new CheckListSearchInfo();
		checkInfo.setShozokuCd(container.getUserInfo().getShozokuInfo().getShozokuCd());
// 20050606 Start
//update start dyh 2006/2/8
		CheckListForm listForm = (CheckListForm)form;
		checkInfo.setJigyoKubun(listForm.getJigyoKbn().trim());//事業区分
//		checkInfo.setJigyoKubun(IJigyoKubun.JIGYO_KUBUN_KIBAN);
//update end dyh 2006/2/8
		ActionErrors errors = new ActionErrors();
// Horikoshi End
		CheckListForm checkForm = (CheckListForm)form;	
		try {
			PropertyUtils.copyProperties(checkInfo, checkForm);
		} catch (Exception e) {
			log.error(e);
			throw new SystemException("プロパティの設定に失敗しました。", e);
		}

		//有効期限チェック
		boolean inPeriod = getSystemServise(
			IServiceName.CHECKLIST_MAINTENANCE_SERVICE).checkLimitDate(
			container.getUserInfo(),
			checkInfo);
			
		//有効期限チェック結果をフォームに格納
		checkForm.setPeriod(inPeriod);

// 20050714 研究者マスタの存在チェック
		List lstResult = new ArrayList();
		try{
			lstResult = blnKenkyushaExistCheck(container.getUserInfo(), checkInfo);
			for(int n=0; n<lstResult.size(); n++){
				ActionError error = new ActionError("errors.5051",lstResult.get(n));
				errors.add("kenkyuExists", error);
			}
		}catch(ApplicationException ex){
			throw new ApplicationException("研究者の検索中にDBエラーが発生しました。",new ErrorInfo("errors.4004"),ex);
		}
		//結果をフォームに格納
		if(lstResult.isEmpty()){
			checkForm.setKenkyushaExist(true);
		}
		else{
			checkForm.setKenkyushaExist(false);
		}
// Horikoshi

		//トークンをセッションに保存する。
		saveToken(request);

// 20050722
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
// Horikoshi

		//チェックリスト出力確認画面へ
		return forwardSuccess(mapping);
	}

	List blnKenkyushaExistCheck(
			UserInfo userInfo,
			CheckListSearchInfo checkInfo
			)
	throws
			ApplicationException
	{

		List lstErrorInfo = new ArrayList();
//delete start dyh 2006/2/9 使用しない
//		List lstKenkyusha = new ArrayList();		//研究者NO、所属研究機関コード格納リスト
//delete end dyh 2006/2/9
		try{
			lstErrorInfo = getSystemServise(
					IServiceName.CHECKLIST_MAINTENANCE_SERVICE).chkKenkyushaExist(
					userInfo,
					checkInfo,
					null
					);
		}catch(ApplicationException ex){
			throw new ApplicationException("研究者の検索中にDBエラーが発生しました。",new ErrorInfo("errors.4004"),ex);
		}
		finally{
//			lstKenkyusha.clear();
		}

		return lstErrorInfo;
	}
}
