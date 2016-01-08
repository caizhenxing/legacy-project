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
package jp.go.jsps.kaken.web.gyomu.shinsainKanri;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.SystemException;
import jp.go.jsps.kaken.model.vo.ShinsainInfo;
import jp.go.jsps.kaken.model.vo.ShinsainPk;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;
import jp.go.jsps.kaken.web.struts.BaseForm;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 * 審査員情報更新前アクションクラス。
 * キーに一致する審査員情報を取得、初期値としてデータをセットする。
 * 審査員情報更新画面を表示する。
 * 
 * ID RCSfile="$RCSfile: EditAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:40 $"
 */
public class EditAction extends BaseAction {

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

		//## 更新不可のプロパティ(セッションに保持)　$!userContainer.shinsainInfo.プロパティ名
		//## 更新対象プロパティ 				$!shinsainForm.プロパティ名
		//##

		//-----ActionErrorsの宣言（定型処理）-----
		ActionErrors errors = new ActionErrors();

		//------更新対象申請者情報の取得
		ShinsainPk pkInfo = new ShinsainPk();
		//------キー情報
		String shinsainNo = ((ShinsainForm)form).getShinsainNo();
		String jigyoKubun = ((ShinsainForm)form).getJigyoKubun();
		pkInfo.setShinsainNo(shinsainNo);
		pkInfo.setJigyoKubun(jigyoKubun);
		
		//------キー情報を元に更新データ取得	
		ShinsainInfo editInfo = getSystemServise(IServiceName.SHINSAIN_MAINTENANCE_SERVICE).select(container.getUserInfo(),pkInfo);
	
		//------更新対象情報をセッションに登録。
		container.setShinsainInfo(editInfo);
		
		//------更新対象申請者情報より、更新登録フォーム情報の更新
		ShinsainForm editForm = new ShinsainForm();
		editForm.setAction(BaseForm.EDIT_ACTION);
			
//		//------ラジオボタンデータセット
//		//新規･継続
//		editForm.setSinkiKeizokuFlgList(LabelValueManager.getSinkiKeizokuFlgList());
//		//謝金
//		editForm.setShakinList(LabelValueManager.getShakinList());

		try {
//			BeanUtils.copyProperty(editForm,"shinsainId",editInfo.getShinsainId());
//			BeanUtils.copyProperty(editForm,"shinsainNo",editInfo.getShinsainNo());
			BeanUtils.copyProperty(editForm,"nameKanjiSei",editInfo.getNameKanjiSei());
			BeanUtils.copyProperty(editForm,"nameKanjiMei",editInfo.getNameKanjiMei());
			BeanUtils.copyProperty(editForm,"nameKanaSei",editInfo.getNameKanaSei());
			BeanUtils.copyProperty(editForm,"nameKanaMei",editInfo.getNameKanaMei());
			BeanUtils.copyProperty(editForm,"shozokuCd",editInfo.getShozokuCd());
			BeanUtils.copyProperty(editForm,"shozokuName",editInfo.getShozokuName());
//			BeanUtils.copyProperty(editForm,"bukyokuCd",editInfo.getBukyokuCd());
			BeanUtils.copyProperty(editForm,"bukyokuName",editInfo.getBukyokuName());
//			BeanUtils.copyProperty(editForm,"shokushuCd",editInfo.getShokushuCd());
			BeanUtils.copyProperty(editForm,"shokushuName",editInfo.getShokushuName());
//			BeanUtils.copyProperty(editForm,"keiCd",editInfo.getKeiCd());
//			BeanUtils.copyProperty(editForm,"levelA1",editInfo.getLevelA1());
//			BeanUtils.copyProperty(editForm,"levelB11",editInfo.getLevelB11());
//			BeanUtils.copyProperty(editForm,"levelB12",editInfo.getLevelB12());
//			BeanUtils.copyProperty(editForm,"levelB13",editInfo.getLevelB13());
//			BeanUtils.copyProperty(editForm,"levelB21",editInfo.getLevelB21());
//			BeanUtils.copyProperty(editForm,"levelB22",editInfo.getLevelB22());
//			BeanUtils.copyProperty(editForm,"levelB23",editInfo.getLevelB23());
//			BeanUtils.copyProperty(editForm,"shinsaKahi",editInfo.getShinsaKahi());
			BeanUtils.copyProperty(editForm,"sofuZip",editInfo.getSofuZip());
			BeanUtils.copyProperty(editForm,"sofuZipaddress",editInfo.getSofuZipaddress());
			BeanUtils.copyProperty(editForm,"sofuZipemail",editInfo.getSofuZipemail());
//			BeanUtils.copyProperty(editForm,"jitakuTel",editInfo.getJitakuTel());
			BeanUtils.copyProperty(editForm,"shozokuTel",editInfo.getShozokuTel());
//			BeanUtils.copyProperty(editForm,"sinkiKeizokuFlg",editInfo.getSinkiKeizokuFlg());
//			BeanUtils.copyProperty(editForm,"shakin",editInfo.getShakin());
//			BeanUtils.copyProperty(editForm,"key1",editInfo.getKey1());
//			BeanUtils.copyProperty(editForm,"key2",editInfo.getKey2());
//			BeanUtils.copyProperty(editForm,"key3",editInfo.getKey3());
//			BeanUtils.copyProperty(editForm,"key4",editInfo.getKey4());
//			BeanUtils.copyProperty(editForm,"key5",editInfo.getKey5());
//			BeanUtils.copyProperty(editForm,"key6",editInfo.getKey6());
//			BeanUtils.copyProperty(editForm,"key7",editInfo.getKey7());
			BeanUtils.copyProperty(editForm,"url",editInfo.getUrl());
			BeanUtils.copyProperty(editForm,"biko",editInfo.getBiko());
			BeanUtils.copyProperty(editForm,"jigyoKubun",editInfo.getJigyoKubun());
			BeanUtils.copyProperty(editForm,"shozokuFax",editInfo.getShozokuFax());
			BeanUtils.copyProperty(editForm,"senmon",editInfo.getSenmon());

//			2006/10/24    易旭	追加ここから
			BeanUtils.copyProperty(editForm,"downloadFlag",editInfo.getDownloadFlag());
//			2006/10/24    易旭	追加ここまで
			
			//日付の設定		
			Calendar calendar = Calendar.getInstance();
//			//委嘱開始日
//			if(editInfo.getKizokuStart() != null){
//				calendar.setTime(editInfo.getKizokuStart());
//				editForm.setKizokuStartYear("" + calendar.get(Calendar.YEAR));
//				editForm.setKizokuStartMonth("" + (calendar.get(Calendar.MONTH) + 1));
//				editForm.setKizokuStartDay("" + calendar.get(Calendar.DATE));
//			}
//			//委嘱終了日
//			if(editInfo.getKizokuEnd() != null){
//				calendar.setTime(editInfo.getKizokuEnd());
//				editForm.setKizokuEndYear("" + calendar.get(Calendar.YEAR));
//				editForm.setKizokuEndMonth("" + (calendar.get(Calendar.MONTH) + 1));
//				editForm.setKizokuEndDay("" + calendar.get(Calendar.DATE));
//			}
			//有効期限
			if(editInfo.getYukoDate() != null){
				calendar.setTime(editInfo.getYukoDate());
				editForm.setYukoDateYear("" + calendar.get(Calendar.YEAR));
				editForm.setYukoDateMonth("" + (calendar.get(Calendar.MONTH) + 1));
				editForm.setYukoDateDay("" + calendar.get(Calendar.DATE));
			}

		} catch (Exception e) {
			log.error(e);
			throw new SystemException("プロパティの設定に失敗しました。", e);
		}
		
		//------修正登録フォームにセットする。
		updateFormBean(mapping,request,editForm);

		//-----画面遷移（定型処理）-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		return forwardSuccess(mapping);
	}

}
