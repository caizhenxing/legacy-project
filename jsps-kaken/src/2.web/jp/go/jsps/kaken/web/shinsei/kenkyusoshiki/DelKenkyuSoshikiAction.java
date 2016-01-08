/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : takano
 *    Date        : 2004/01/09
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.shinsei.kenkyusoshiki;

import java.util.*;

import javax.servlet.http.*;

import jp.go.jsps.kaken.model.exceptions.*;
import jp.go.jsps.kaken.model.vo.*;
import jp.go.jsps.kaken.model.vo.shinsei.KenkyuSoshikiKenkyushaInfo;
import jp.go.jsps.kaken.web.common.*;
import jp.go.jsps.kaken.web.shinsei.*;
import jp.go.jsps.kaken.web.struts.*;

import org.apache.struts.action.*;
import org.apache.struts.action.ActionMapping;

/**
 * 研究組織表削除アクションクラス。
 * 研究組織リストから指定インデックスのオブジェクトを削除する。 
 * ID RCSfile="$RCSfile: DelKenkyuSoshikiAction.java,v $"
 * Revision="$Revision: 1.2 $"
 * Date="$Date: 2007/07/27 02:12:06 $"
 */
public class DelKenkyuSoshikiAction extends BaseAction {

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

		//-----申請書入力フォームの取得
		ShinseiForm shinseiForm = (ShinseiForm)form;
		
		//-----研究組織管理リストから指定インデックスを削除
		int listIndex = shinseiForm.getListIndex();
		if(listIndex > 0){
			ShinseiDataInfo shinseiDataInfo = shinseiForm.getShinseiDataInfo();
			List kenkyushaList = shinseiDataInfo.getKenkyuSoshikiInfoList();
			try{
				KenkyuSoshikiKenkyushaInfo kenkyushaInfo=(KenkyuSoshikiKenkyushaInfo)kenkyushaList.remove(listIndex);
//UPDATE　START 2007/07/27 BIS 金京浩 // 研究分担者・連携研究者 . 空の値
				//if(kenkyushaInfo!=null&&"2".equals(kenkyushaInfo.getBuntanFlag())){
				if(kenkyushaInfo!=null&&(!"1".equals(kenkyushaInfo.getBuntanFlag()) && !"3".equals(kenkyushaInfo.getBuntanFlag()) )){
//UPDATE　END　 2007/07/27 BIS 金京浩 					
					shinseiDataInfo.setKenkyuNinzuInt(shinseiDataInfo.getKenkyuNinzuInt()-1);
				} else if(kenkyushaInfo!=null&&"3".equals(kenkyushaInfo.getBuntanFlag())){
					shinseiDataInfo.setKyoryokushaNinzuInt(shinseiDataInfo.getKyoryokushaNinzuInt()-1);
				}
			}catch(IndexOutOfBoundsException e){
				String msg = "研究組織表のリストインデックス値が不正です。";
				throw new ApplicationException(msg, new ErrorInfo("errors.5015"), e);			
			}
		}
		
		//-----申請書入力フォームにセットする。
		updateFormBean(mapping, request, shinseiForm);

		//-----画面遷移（定型処理）-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		
		return forwardSuccess(mapping);
		
	}
	
	
	
}
