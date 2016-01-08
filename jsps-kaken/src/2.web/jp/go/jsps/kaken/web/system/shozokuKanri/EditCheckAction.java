/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2003/11/14
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.system.shozokuKanri;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.ISystemServise;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.SystemException;
import jp.go.jsps.kaken.model.vo.ShozokuInfo;
import jp.go.jsps.kaken.model.vo.ShozokuPk;
import jp.go.jsps.kaken.util.DateUtil;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 * 更新された所属機関情報の入力チェックを行う。
 * 所属機関修正情報値オブジェクトを作成する。
 * 修正確認画面を表示する。 
 * 
 * ID RCSfile="$RCSfile: EditCheckAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:03 $"
 */
public class EditCheckAction extends BaseAction {

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

		//------修正登録フォーム情報の取得
		ShozokuForm editForm = (ShozokuForm) form;
		
		ShozokuPk pkInfo = new ShozokuPk();
		
		pkInfo.setShozokuTantoId(container.getShozokuInfo().getShozokuTantoId());
		
		//------更新対象情報の取得
		ISystemServise servise = getSystemServise(
						IServiceName.SHOZOKU_MAINTENANCE_SERVICE);
		ShozokuInfo editInfo = servise.select(container.getUserInfo(),pkInfo);
		
		// 2005/04/21 追加 ここから-----------------------------------------------------------
		// 理由 部局担当者人数が元データより少ないときエラー表示
		
		int addBukyokuNum = 0;
		//元データのの部局担当者人数が空でない
		if(editInfo.getBukyokuNum() != null && !editInfo.getBukyokuNum().equals("")){
			//部局担当者の入力値が空でない
			if(editForm.getBukyokuNum() != null && !editForm.getBukyokuNum().equals("")){
				
				addBukyokuNum = Integer.parseInt(editForm.getBukyokuNum()) - Integer.parseInt(editInfo.getBukyokuNum());
				//部局担当者人数の入力値が元データより少ない値のときエラー
				if(addBukyokuNum < 0){
					errors.add("errors.5034", new ActionError("errors.5034", editInfo.getBukyokuNum()));
					//エラーを保存。
					saveErrors(request, errors);
					
					//---入力内容に不備があるので再入力
					return forwardInput(mapping);
				}
			}else{
				errors.add("errors.5034", new ActionError("errors.5034", editInfo.getBukyokuNum()));
				//エラーを保存。
				saveErrors(request, errors);
				
				//---入力内容に不備があるので再入力
				return forwardInput(mapping);
			}
				
		}
		
		// 追加 ここまで----------------------------------------------------------------------------
		
		// 2005/04/21 追加
		// 部局担当者人数の追加数セット
		editInfo.setAddBukyokuNum(addBukyokuNum);
		
		try {
			PropertyUtils.copyProperties(editInfo, editForm);
		} catch (Exception e) {
			log.error(e);
			throw new SystemException("プロパティの設定に失敗しました。", e);
		}	
		
		//有効期限（String→Date)
		DateUtil dateUtil = new DateUtil();
		dateUtil.setCal(editForm.getYukoDateYear(),editForm.getYukoDateMonth(),editForm.getYukoDateDay());
		editInfo.setYukoDate(dateUtil.getCal().getTime());
		//-------▲

		//-----セッションに所属機関情報を登録する。
		container.setShozokuInfo(editInfo);

		//------修正確認フォームにセットする。
		updateFormBean(mapping,request,editForm);
		
		//-----画面遷移（定型処理）-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		
		//トークンをセッションに保存する。
		saveToken(request);
		
		return forwardSuccess(mapping);
	}

}
