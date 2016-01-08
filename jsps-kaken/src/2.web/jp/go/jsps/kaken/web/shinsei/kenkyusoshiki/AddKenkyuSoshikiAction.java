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
import jp.go.jsps.kaken.model.vo.shinsei.*;
import jp.go.jsps.kaken.web.common.*;
import jp.go.jsps.kaken.web.shinsei.*;
import jp.go.jsps.kaken.web.struts.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.*;
import org.apache.struts.action.ActionMapping;

/**
 * 研究組織表追加アクションクラス。
 * 研究組織リストに新規オブジェクトを追加する。 
 * ID RCSfile="$RCSfile: AddKenkyuSoshikiAction.java,v $"
 * Revision="$Revision: 1.4 $"
 * Date="$Date: 2007/07/21 08:39:46 $"
 */
public class AddKenkyuSoshikiAction extends BaseAction {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/** ログ */
	protected static Log log = LogFactory.getLog(AddKenkyuSoshikiAction.class);

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
		

		
		
		//-----研究組織管理リストに新規オブジェクトを追加
		ShinseiDataInfo shinseiDataInfo = shinseiForm.getShinseiDataInfo();
		List kenkyushaList = shinseiDataInfo.getKenkyuSoshikiInfoList();

//		2005/04/14 修正 ここから----------
//		理由:どちらかが最大数に達してしまうと、最大数でない方まで追加できなくなってしまう為
		
		//リストの最大数を超えていた場合
		//2005/03/30 修正 ---------------------------------------------ここから
		////理由 研究組織表に研究協力者が追加されたため
		//if(kenkyushaList.size() >= 99){
		//	String msg = "研究組織リストの最大数を超えました。";
		//	errors.add("addKenkyuSoshiki", new ActionError("errors.5016"));
		//}else{
		//	//新規オブジェクト
		//	KenkyuSoshikiKenkyushaInfo kenkyushaInfo = new KenkyuSoshikiKenkyushaInfo();
		//	kenkyushaInfo.setSystemNo(shinseiDataInfo.getSystemNo());
		//	kenkyushaInfo.setJigyoID(shinseiDataInfo.getJigyoId());
		//
		//	//リスト追加
		//	kenkyushaList.add(kenkyushaInfo);
		//}


//		//理由 研究組織表に研究協力者が追加されたため
//		if(shinseiDataInfo.getKenkyuNinzuInt() >= 99 || shinseiDataInfo.getKyoryokushaNinzuInt() >= 10){
//			String msg = "研究組織リストの最大数を超えました。";
//			errors.add("addKenkyuSoshiki", new ActionError("errors.5016"));
//		}else{
//			//新規オブジェクト
//			KenkyuSoshikiKenkyushaInfo kenkyushaInfo = new KenkyuSoshikiKenkyushaInfo();
//			kenkyushaInfo.setSystemNo(shinseiDataInfo.getSystemNo());
//			kenkyushaInfo.setJigyoID(shinseiDataInfo.getJigyoId());
//
//			kenkyushaInfo.setBuntanFlag(shinseiForm.getAddBuntanFlg());
//
//			if("2".equals(shinseiForm.getAddBuntanFlg())){
//				//分担者
//				shinseiDataInfo.setKenkyuNinzuInt(shinseiDataInfo.getKenkyuNinzuInt()+1);
//			} else {
//				//協力者
//				shinseiDataInfo.setKyoryokushaNinzuInt(shinseiDataInfo.getKyoryokushaNinzuInt()+1);
//			}
//			
//			//リスト追加
//			kenkyushaList.add(kenkyushaInfo);
//		}
		//2005/03/29 追加 ---------------------------------------------ここまで
		

		//TODO 2005.09.29 iso 分担フラグに空が入る対応で一時追加
		if(shinseiForm.getAddBuntanFlg() == null || "".equals(shinseiForm.getAddBuntanFlg())) {
			shinseiForm.setAddBuntanFlg("2");
			log.info("【発生】分担フラグ空：AddKenkyuSoshikiAction");
		}
//UPDATE　START 2007/07/10 BIS 金京浩 		//代表者分担者別に　add  （4：連携研究者）
		//if("2".equals(shinseiForm.getAddBuntanFlg())){
		if("2".equals(shinseiForm.getAddBuntanFlg())
				||"4".equals(shinseiForm.getAddBuntanFlg())
				||"5".equals(shinseiForm.getAddBuntanFlg())){
//UPDATE　END　 2007/07/10 BIS 金京浩 			
			

			//研究代表者及び研究分担者が99人の場合は追加できない
			if(shinseiDataInfo.getKenkyuNinzuInt() >= 99){
//UPDATE　START 2007/07/10 BIS 金京浩 		//代表者分担者別に　add  （4：連携研究者）				
				//errors.add("addKenkyuSoshiki", new ActionError("errors.5016","研究代表者及び研究分担者","99"));
				errors.add("addKenkyuSoshiki", new ActionError("errors.5016","研究代表者、研究分担者及び連携研究者","99"));
//UPDATE　END　 2007/07/10 BIS 金京浩 				
			}else{
				//分担者
				shinseiDataInfo.setKenkyuNinzuInt(shinseiDataInfo.getKenkyuNinzuInt()+1);
				
				KenkyuSoshikiKenkyushaInfo kenkyushaInfo = new KenkyuSoshikiKenkyushaInfo();
				kenkyushaInfo.setSystemNo(shinseiDataInfo.getSystemNo());
				kenkyushaInfo.setJigyoID(shinseiDataInfo.getJigyoId());

				kenkyushaInfo.setBuntanFlag(shinseiForm.getAddBuntanFlg());
				
				//リスト追加
				kenkyushaList.add(kenkyushaInfo);
			}
		}else{
			//研究協力者が10人の場合は追加できない
			if(shinseiDataInfo.getKyoryokushaNinzuInt() >= 10){
				errors.add("addKenkyuSoshiki", new ActionError("errors.5016","研究協力者","10"));
			}else{
				//協力者
				shinseiDataInfo.setKyoryokushaNinzuInt(shinseiDataInfo.getKyoryokushaNinzuInt()+1);
				
				KenkyuSoshikiKenkyushaInfo kenkyushaInfo = new KenkyuSoshikiKenkyushaInfo();
				kenkyushaInfo.setSystemNo(shinseiDataInfo.getSystemNo());
				kenkyushaInfo.setJigyoID(shinseiDataInfo.getJigyoId());

				kenkyushaInfo.setBuntanFlag(shinseiForm.getAddBuntanFlg());
				
				//リスト追加
				kenkyushaList.add(kenkyushaInfo);
			}
		}

		//2005/04/14 修正 ここまで----------

		
		//-----申請書入力フォームにセットする。
		
//		ADD　START 2007/07/11 BIS 金京浩   (研究組織表の中　：　ソート順　①代表者分担者別　昇順、②シーケンス番号（画面からの登録順）　昇順)
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
		
		
		
		updateFormBean(mapping, request, shinseiForm);

		//-----画面遷移（定型処理）-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		
		return forwardSuccess(mapping);
		
	}
	
	
	
}
