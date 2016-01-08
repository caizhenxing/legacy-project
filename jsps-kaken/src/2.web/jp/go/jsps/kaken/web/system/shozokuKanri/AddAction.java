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

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.common.ITaishoId;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.ErrorInfo;
import jp.go.jsps.kaken.model.vo.KikanInfo;
import jp.go.jsps.kaken.model.vo.RuleInfo;
import jp.go.jsps.kaken.model.vo.RulePk;
import jp.go.jsps.kaken.model.vo.ShozokuInfo;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;
import jp.go.jsps.kaken.web.struts.BaseForm;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 所属機関情報登録前アクションクラス。
 * フォームに所属機関情報登録画面に必要なデータをセットする。
 * 所属機関情報新規登録画面を表示する。
 * 
 * ID RCSfile="$RCSfile: AddAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:03 $"
 */
public class AddAction extends BaseAction {

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

		//検索条件の取得
		ShozokuForm searchForm = (ShozokuForm)form;
		
		//------検索モード
		searchForm.setAction(BaseForm.ADD_ACTION);
		
		//所属機関担当者情報テーブルにすでに所属機関コードが
		//同じ所属機関担当者が登録されていないかどうかを確認
		int count =
			getSystemServise(
				IServiceName.SHOZOKU_MAINTENANCE_SERVICE).select(
				container.getUserInfo(),
				searchForm.getShozokuCd());	

		//すでに登録されている場合
		if(count > 0){
			String[] error = {"所属研究機関担当者"};
			throw new ApplicationException("すでに所属機関担当者が登録されています。検索キー：所属機関コード'" 
														+ searchForm.getShozokuCd() + "'", 	new ErrorInfo("errors.4007", error));			
		}
		
		KikanInfo kikanInfo = new KikanInfo();
		kikanInfo.setShozokuCd(searchForm.getShozokuCd());//所属機関コード
		
		//所属機関マスタから所属機関情報を取得
		KikanInfo result =
			getSystemServise(
				IServiceName.SHOZOKU_MAINTENANCE_SERVICE).select(
				container.getUserInfo(),
					kikanInfo);	

		//------有効期限初期値セット
		RulePk rulePk = new RulePk();
		rulePk.setTaishoId(ITaishoId.SHOZOKUTANTO);
		RuleInfo ruleInfo =
			getSystemServise(
				IServiceName.RULE_MAINTENANCE_SERVICE).select(
				container.getUserInfo(),
				rulePk);
		Calendar cal = Calendar.getInstance();
		cal.setTime(ruleInfo.getYukoDate());

		ShozokuForm addForm = new ShozokuForm();
		//------データ入力は2ページ目
		addForm.setPage(2);
	
		//フォームにデータをセットする。	
		addForm.setShozokuCd(result.getShozokuCd());							//所属機関名（コード）
		addForm.setShozokuNameEigo(result.getShozokuNameEigo());				//所属機関名（英語）
		addForm.setTantoTel(result.getShozokuTel());							//担当者部局所在地（電話番号）
		addForm.setTantoFax(result.getShozokuFax());							//担当者部局所在地（FAX番号）
		addForm.setTantoZip(result.getShozokuZip());							//担当者部局所在地（郵便番号）
		if(result.getShozokuAddress2() != null && result.getShozokuAddress2().length() != 0){
			addForm.setTantoAddress(result.getShozokuAddress1() + " " + result.getShozokuAddress2());	//担当者部局所在地（住所）
		}else{
			addForm.setTantoAddress(result.getShozokuAddress1());
		}
		addForm.setBiko(result.getBiko());										//備考
		addForm.setYukoDateYear(Integer.toString(cal.get(Calendar.YEAR)));		//有効期限（年）
		addForm.setYukoDateMonth(Integer.toString(cal.get(Calendar.MONTH)+1));	//有効期限（月）
		addForm.setYukoDateDay(Integer.toString(cal.get(Calendar.DATE)));		//有効期限（日）	

		//-------▼ VOにデータをセットする。
		ShozokuInfo addInfo = new ShozokuInfo();
		addInfo.setShozokuCd(result.getShozokuCd());				//所属機関名（コード）
		addInfo.setShubetuCd(result.getShubetuCd());				//機関種別		
		addInfo.setShozokuName(result.getShozokuNameKanji());		//所属機関名（日本語）
		addInfo.setShozokuRyakusho(result.getShozokuRyakusho());	//所属機関名（略称）
		addInfo.setShozokuNameEigo(result.getShozokuNameEigo());	//所属機関名（英語）
		//-------▲
				
		//-----セッションに所属機関情報を登録する。
		container.setShozokuInfo(addInfo);		

		//------登録フォームにセットする。
		updateFormBean(mapping,request,addForm);

		//-----画面遷移（定型処理）-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		return forwardSuccess(mapping);
	}

}
