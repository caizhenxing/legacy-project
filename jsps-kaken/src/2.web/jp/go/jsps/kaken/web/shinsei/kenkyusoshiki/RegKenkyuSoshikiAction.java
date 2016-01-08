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

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.*;

import jp.go.jsps.kaken.model.exceptions.*;
import jp.go.jsps.kaken.model.vo.shinsei.KenkyuSoshikiKenkyushaInfo;
import jp.go.jsps.kaken.web.common.*;
import jp.go.jsps.kaken.web.shinsei.ShinseiForm;
import jp.go.jsps.kaken.web.struts.*;

import org.apache.struts.action.*;
import org.apache.struts.action.ActionMapping;

/**
 * 研究組織表登録アクションクラス。
 * ID RCSfile="$RCSfile: RegKenkyuSoshikiAction.java,v $"
 * Revision="$Revision: 1.2 $"
 * Date="$Date: 2007/07/23 10:30:16 $"
 */
public class RegKenkyuSoshikiAction extends BaseAction {

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

		//研究組織情報をセッションに格納するのみなので特に何もしない。

		//-----画面遷移（定型処理）-----
//ADD　START 2007/07/11 BIS 金京浩   (研究組織表の中　：　ソート順　①代表者分担者別　昇順、②シーケンス番号（画面からの登録順）　昇順)
		ShinseiForm shinseiForm = (ShinseiForm)form;
		List kenkyusoshikiIfo = shinseiForm.getShinseiDataInfo().getKenkyuSoshikiInfoList();
		if(kenkyusoshikiIfo != null && kenkyusoshikiIfo.size() > 0){
			List outList = new ArrayList();
			List daihyoList = new ArrayList();    //研究代表者 : 1 
			List bunfanList = new ArrayList();    //　分担者   : 2
			List kyouryokuList = new ArrayList(); //研究協力者 : 3
			List renkeiList = new ArrayList();    //連携研究者 : 4
			List nullList = new ArrayList();      //    空 　　: 5
			
			for(int i = 0 ; i <kenkyusoshikiIfo.size() ; i ++ ){
				KenkyuSoshikiKenkyushaInfo kenkyuSoshikiKenkyushaInfo = (KenkyuSoshikiKenkyushaInfo) kenkyusoshikiIfo.get(i);
				String buntan = kenkyuSoshikiKenkyushaInfo.getBuntanFlag();
				if("1".equals(buntan)){
					daihyoList.add(kenkyuSoshikiKenkyushaInfo);
				}else if("2".equals(buntan)){
					bunfanList.add(kenkyuSoshikiKenkyushaInfo);
				}else if("3".equals(buntan)){
					kyouryokuList.add(kenkyuSoshikiKenkyushaInfo);
				}else if("4".equals(buntan)){
					renkeiList.add(kenkyuSoshikiKenkyushaInfo);
				}else if("5".equals(buntan)){
					nullList.add(kenkyuSoshikiKenkyushaInfo);
				}
				
			}
			if(daihyoList.size()>0){
				outList.addAll(daihyoList)	;
			}
			if(bunfanList.size()>0){
				outList.addAll(bunfanList)	;
			}
			if(kyouryokuList.size()>0){
				outList.addAll(kyouryokuList)	;
			}
			if(renkeiList.size()>0){
				outList.addAll(renkeiList)	;	
			}
			if(nullList.size()>0){
				outList.addAll(nullList)	;				}
			shinseiForm.getShinseiDataInfo().setKenkyuSoshikiInfoList(outList);			
		}	
//ADD　END　 2007/07/11 BIS 金京浩	
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		
		return forwardSuccess(mapping);
		
	}
	
	
	
}
