/*======================================================================
 *	  SYSTEM	  :
 *	  Source name :
 *	  Description :
 *
 *	  Author	  : takano
 *	  Date		  : 2005/01/17
 *
 *	  Revision history
 *	  Date			Revision	Author		   Description
 *
 *======================================================================
 */
package jp.go.jsps.kaken.web.shinsei.validate;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.*;

import jp.go.jsps.kaken.model.IShinseiMaintenance;
import jp.go.jsps.kaken.model.common.IJigyoCd;
import jp.go.jsps.kaken.model.vo.*;
import jp.go.jsps.kaken.model.vo.shinsei.KenkyuSoshikiKenkyushaInfo;
import jp.go.jsps.kaken.util.StringUtil;
import jp.go.jsps.kaken.web.struts.ActionMapping;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.*;

/**
 * デフォルト申請形式チェッククラス
 * ID RCSfile="$RCSfile: DefaultShinseiValidator.java,v $"
 * Revision="$Revision: 1.5 $"
 * Date="$Date: 2007/07/24 02:27:53 $"
 */
public class DefaultShinseiValidator implements IShinseiValidator {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/** ログ */
	protected static Log log = LogFactory.getLog(DefaultShinseiValidator.class);

	/** 申請情報 */
	protected ShinseiDataInfo shinseiDataInfo = null;


	/**
	 * コンストラクタ
	 * @param shinseidataInfo
	 */
	public DefaultShinseiValidator(ShinseiDataInfo shinseiDataInfo){
		this.shinseiDataInfo = shinseiDataInfo;
	}


	/**
	 * 形式チェック（空メソッド。何も処理しない。）
	 * @see jp.go.jsps.kaken.web.shinsei.validate.IShinseiValidator#validate(jp.go.jsps.kaken.web.struts.ActionMapping, javax.servlet.http.HttpServletRequest)
	 */
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request, int page, ActionErrors errors) {
		//空メソッド。何も処理しない。
		return new ActionErrors();

	}

	/**
	 * 研究組織表オブジェクトに対して形式チェックをかける。
	 * pageが「0以上」の場合、形式チェックを行う。必須チェックは行わない。<br>
	 * pageが「2以上」の場合、必須チェックも行う。<br>
	 * <p>
	 * ex.<br>
	 * <li>pageが「1」のとき、形式チェックのみ行う。</li>
	 * <li>pageが「2」のとき、必須チェック＋形式チェックを行う。</li>
	 * </p>
	 * （※List型のオブジェクトにStrutsのValidatorをかけても、
	 * 要件が満たせ無さそうなので、独自に実装する。）
	 * @param errors 検証エラーがあった場合は、errorsに追加していく。
	 * @param page	 ページ番号（＝検証レベル）
	 */
	protected void validateKenkyuSoshiki(ActionErrors errors, int page)
	{
		String	  name		          = null;				//項目名
		String	  value 	          = null;				//値
		String	  property	          = null;				//プロパティ名
		Set 	  kenkyuNoSet         = new HashSet();		//研究者番号のセット（重複を許さないため）
		boolean  buntanEffort        = false;				//分担者のエフォート有無フラグ
		boolean  kyoryokusha         = false;				//協力者フラグ
		int 	  kyoryokushaCnt      = 0;					//協力者数
		int 	  buntanshaCnt        = 0;					//代表者＋分担者数
		int 	  cnt                 =0;					//エラーにて表示するラベルの番号
		String	  namePrefix          = null;				//エラーにて表示するラベルのプリフィックス
		boolean  kikanFlg	          = true;				//機関コードが正しいかフラグ
		String	  daihyoKikan         = null;				//代表機関
		long     diffKikanTotalKeihi  = 0;					//代表者と異なる機関の分担者経費の合計値
//		<!-- ADD　START 2007/07/09 BIS 張楠 -->
		String	  objKey	          = null;				//エラーメッセジーキー
		int 	  cntKey              =0;					//エラーメッセジの番号
//		<!-- ADD　END　 2007/07/09 BIS 張楠 -->		
		
		//========== 研究組織表のリスト分繰り返し 始まり ==========
		List kenkyushaList = shinseiDataInfo.getKenkyuSoshikiInfoList();
//		<!-- ADD　START 2007/07/09 BIS 張楠 -->		
		HashMap errMap = new HashMap();
		errMap.clear() ;
//		<!-- ADD　END　 2007/07/09 BIS 張楠 -->				
		for(int i=1; i<=kenkyushaList.size(); i++){
			//研究者
			KenkyuSoshikiKenkyushaInfo kenkyushaInfo =
						(KenkyuSoshikiKenkyushaInfo)kenkyushaList.get(i-1);

			//TODO 2005.09.29 iso 分担フラグに空が入る対応で一時追加
			if(StringUtil.isBlank(kenkyushaInfo.getBuntanFlag())) {
				kenkyushaInfo.setBuntanFlag("2");
				log.info("【発生】分担フラグ空：DefaultShinseiValidator");
			}

			//研究協力者かどうか判定
			kyoryokusha = ("3".equals(kenkyushaInfo.getBuntanFlag()));
			//機関コードが正しいかフラグを初期化
			kikanFlg	 = true;

			if(kyoryokusha){
				namePrefix="研究組織(研究協力者)".intern();
				kyoryokushaCnt++;
				cnt=kyoryokushaCnt;
			} else {
//UPDATE　START 2007/07/13 BIS 金京浩 				
				//namePrefix="研究組織(研究代表者及び研究分担者)".intern();
				namePrefix="研究組織(研究代表者、研究分担者及び連携研究者)".intern();
//UPDATE　END　 2007/07/13 BIS 金京浩 				
				buntanshaCnt++;
				cnt=buntanshaCnt;
			}
			
//			<!-- ADD　START 2007/07/09 BIS 張楠 -->				
			if(cnt > 0){
				cntKey = cnt -1;
			}else{
				cntKey = cnt;
			}
//			<!-- ADD　END　 2007/07/09 BIS 張楠 -->					
			if(!kyoryokusha){
//ADD　START 2007/07/24 BIS 金京浩 //空白の場合はエラーとする
				name	 = namePrefix+" 区分 "+cnt+"行目";
				value	 = StringUtil.toHankakuDigit(kenkyushaInfo.getKenkyuNo());
				property = "shinseiDataInfo.kenkyuSoshikiInfoList.buntanFlag";			
				objKey 	 = "shinseiDataInfo.kenkyuSoshikiInfoList["+cntKey+"].buntanFlag";
				if("5".equals(kenkyushaInfo.getBuntanFlag())){
					ActionError error = new ActionError("errors.required",name);
					errors.add(property, error);								
					errMap.put(objKey ,name);
				}
//ADD　END　 2007/07/24 BIS 金京浩
				//-----研究者番号-----
				name	 = namePrefix+" 研究者番号 "+cnt+"行目";
				value	 = StringUtil.toHankakuDigit(kenkyushaInfo.getKenkyuNo());
				property = "shinseiDataInfo.kenkyuSoshikiInfoList.kenkyuNo";
//				<!-- ADD　START 2007/07/09 BIS 張楠 -->					
				objKey 	 = "shinseiDataInfo.kenkyuSoshikiInfoList["+cntKey+"].kenkyuNo";
//				<!-- ADD　END　 2007/07/09 BIS 張楠 -->						
				//本登録

				if(page >= 2){
					//必須チェック
					//2005.09.08 iso 半角スペースのみも必須チェックではじくよう変更
//					if(StringUtil.isBlank(value)){
					if(StringUtil.isEscapeBlank(value)){
						ActionError error = new ActionError("errors.required",name);
						errors.add(property, error);
//						<!-- ADD　START 2007/07/09 BIS 張楠 -->									
						errMap.put(objKey ,name);
//						<!-- ADD　END　 2007/07/09 BIS 張楠 -->								
					}else{
						//重複チェック
						if(kenkyuNoSet.contains(value)){
							//その他「99999999」のときは重複エラーとしない
							if(!"99999999".equals(value)){
								ActionError error = new ActionError("errors.5021",name);
								errors.add(property, error);
//								<!-- ADD　START 2007/07/09 BIS 張楠 -->									
								errMap.put(objKey ,name);
//								<!-- ADD　END　 2007/07/09 BIS 張楠 -->		
							}
						}else{
							kenkyuNoSet.add(value);	//存在していなかった場合はセットに格納
						}
					}
				}
				//一時保存, 本登録
				if(page >=0){
					if(!StringUtil.isBlank(value)){
						//数値チェック
						if(!StringUtil.isDigit(value)){
							ActionError error = new ActionError("errors.numeric",name);
							errors.add(property, error);
//							<!-- ADD　START 2007/07/09 BIS 張楠 -->									
							errMap.put(objKey ,name);
//							<!-- ADD　END　 2007/07/09 BIS 張楠 -->		
						//文字数チェック
						}else if(value.length() != 8){
							ActionError error = new ActionError("errors.length",name,"8");
							errors.add(property, error);
//							<!-- ADD　START 2007/07/09 BIS 張楠 -->									
							errMap.put(objKey ,name);
//							<!-- ADD　END　 2007/07/09 BIS 張楠 -->		
						}
					}
				}
				kenkyushaInfo.setKenkyuNo(value);	//半角数値に変換した値をセット
				name	 = null;
				value	 = null;
				property = null;
//				<!-- ADD　START 2007/07/09 BIS 張楠 -->					
				objKey 	 = null;
//				<!-- ADD　END　 2007/07/09 BIS 張楠 -->						
			}


			//-----氏名（漢字-姓）-----
			name	 = namePrefix+" 氏名（漢字等）（姓） "+cnt+"行目";
			value	 = kenkyushaInfo.getNameKanjiSei();
			property = "shinseiDataInfo.kenkyuSoshikiInfoList.nameKanjiSei";
//			<!-- ADD　START 2007/07/09 BIS 張楠 -->	
			objKey 	 = "shinseiDataInfo.kenkyuSoshikiInfoList["+cntKey+"].nameKanjiSei";
//			<!-- ADD　END　 2007/07/09 BIS 張楠 -->	
			//本登録
			if(page >= 2){
				//必須チェック
				//2005.09.08 iso 半角スペースのみも必須チェックではじくよう変更
//				if(StringUtil.isBlank(value)){
				if(StringUtil.isEscapeBlank(value)){
					ActionError error = new ActionError("errors.required",name);
					errors.add(property, error);
//					<!-- ADD　START 2007/07/09 BIS 張楠 -->									
					errMap.put(objKey ,name);
//					<!-- ADD　END　 2007/07/09 BIS 張楠 -->	
				}
			}
			//一時保存, 本登録
			if(page >=0){
				if(!StringUtil.isBlank(value)){
					//最大文字数チェック
					if(value.length() > 16){
						ActionError error = new ActionError("errors.maxlength",name,"16");
						errors.add(property, error);
//						<!-- ADD　START 2007/07/09 BIS 張楠 -->									
						errMap.put(objKey ,name);
//						<!-- ADD　END　 2007/07/09 BIS 張楠 -->	
					}
				}
			}
			name	 = null;
			value	 = null;
			property = null;
//			<!-- ADD　START 2007/07/09 BIS 張楠 -->					
			objKey 	 = null;
//			<!-- ADD　END　 2007/07/09 BIS 張楠 -->			


			//-----氏名（漢字-名）-----
			name	 = namePrefix+" 氏名（漢字等）（名） "+cnt+"行目";
			value	 = kenkyushaInfo.getNameKanjiMei();
			property = "shinseiDataInfo.kenkyuSoshikiInfoList.nameKanjiMei";
//			<!-- ADD　START 2007/07/09 BIS 張楠 -->	
			objKey 	 = "shinseiDataInfo.kenkyuSoshikiInfoList["+cntKey+"].nameKanjiMei";
//			<!-- ADD　END　 2007/07/09 BIS 張楠 -->	
			//2005.10.18 iso 名が空の場合があるので、必須チェックは行わないよう変更
//			//本登録
//			if(page >= 2){
//				//必須チェック
//				//2005.09.08 iso 半角スペースのみも必須チェックではじくよう変更
////				if(StringUtil.isBlank(value)){
//				if(StringUtil.isEscapeBlank(value)){
//					ActionError error = new ActionError("errors.required",name);
//					errors.add(property, error);
//				}
//			}
			//一時保存, 本登録
			if(page >=0){
				if(!StringUtil.isBlank(value)){
					//最大文字数チェック
					if(value.length() > 16){
						ActionError error = new ActionError("errors.maxlength",name,"16");
						errors.add(property, error);
//						<!-- ADD　START 2007/07/09 BIS 張楠 -->									
						errMap.put(objKey ,name);
//						<!-- ADD　END　 2007/07/09 BIS 張楠 -->	
					}
				}
			}
			name	 = null;
			value	 = null;
			property = null;
//			<!-- ADD　START 2007/07/09 BIS 張楠 -->					
			objKey 	 = null;
//			<!-- ADD　END　 2007/07/09 BIS 張楠 -->			


			//-----氏名（フリガナ-姓）-----
			name	 = namePrefix+" 氏名（フリガナ）（姓） "+cnt+"行目";
			value	 = kenkyushaInfo.getNameKanaSei();
			property = "shinseiDataInfo.kenkyuSoshikiInfoList.nameKanaSei";
//			<!-- ADD　START 2007/07/09 BIS 張楠 -->	
			objKey	 = "shinseiDataInfo.kenkyuSoshikiInfoList["+cntKey+"].nameKanaSei";
//			<!-- ADD　END　 2007/07/09 BIS 張楠 -->	
			//本登録
			if(page >= 2){
				//必須チェック
				//2005.09.08 iso 半角スペースのみも必須チェックではじくよう変更
//				if(StringUtil.isBlank(value)){
				if(StringUtil.isEscapeBlank(value)){
					ActionError error = new ActionError("errors.required",name);
					errors.add(property, error);
//					<!-- ADD　START 2007/07/09 BIS 張楠 -->									
					errMap.put(objKey ,name);
//					<!-- ADD　END　 2007/07/09 BIS 張楠 -->	
				}
			}
			//一時保存, 本登録
			if(page >=0){
				if(!StringUtil.isBlank(value)){
					//全角チェック
					if(!StringUtil.isZenkaku(value)){
						ActionError error = new ActionError("errors.mask_zenkaku",name);
						errors.add(property, error);
//						<!-- ADD　START 2007/07/09 BIS 張楠 -->									
						errMap.put(objKey ,name);
//						<!-- ADD　END　 2007/07/09 BIS 張楠 -->	
					//最大文字数チェック
					}else if(value.length() > 16){
						ActionError error = new ActionError("errors.maxlength",name,"16");
						errors.add(property, error);
//						<!-- ADD　START 2007/07/09 BIS 張楠 -->									
						errMap.put(objKey ,name);
//						<!-- ADD　END　 2007/07/09 BIS 張楠 -->	
					}
				}
			}
			name	 = null;
			value	 = null;
			property = null;
//			<!-- ADD　START 2007/07/09 BIS 張楠 -->					
			objKey 	 = null;
//			<!-- ADD　END　 2007/07/09 BIS 張楠 -->			


			//-----氏名（フリガナ-名）-----
			name	 = namePrefix+" 氏名（フリガナ）（名） "+cnt+"行目";
			value	 = kenkyushaInfo.getNameKanaMei();
			property = "shinseiDataInfo.kenkyuSoshikiInfoList.nameKanaMei";
//			<!-- ADD　START 2007/07/09 BIS 張楠 -->	
			objKey	 = "shinseiDataInfo.kenkyuSoshikiInfoList["+cntKey+"].nameKanaMei";
//			<!-- ADD　END　 2007/07/09 BIS 張楠 -->	
			//2005.10.18 iso 名が空の場合があるので、必須チェックは行わないよう変更
//			//本登録
//			if(page >= 2){
//				//必須チェック
//				//2005.09.08 iso 半角スペースのみも必須チェックではじくよう変更
////				if(StringUtil.isBlank(value)){
//				if(StringUtil.isEscapeBlank(value)){
//					ActionError error = new ActionError("errors.required",name);
//					errors.add(property, error);
//				}
//			}
			//一時保存, 本登録
			if(page >=0){
				if(!StringUtil.isBlank(value)){
					//全角チェック
					if(!StringUtil.isZenkaku(value)){
						ActionError error = new ActionError("errors.mask_zenkaku",name);
						errors.add(property, error);
//						<!-- ADD　START 2007/07/09 BIS 張楠 -->									
						errMap.put(objKey ,name);
//						<!-- ADD　END　 2007/07/09 BIS 張楠 -->	
					//最大文字数チェック
					}else if(value.length() > 16){
						ActionError error = new ActionError("errors.maxlength",name,"16");
						errors.add(property, error);
//						<!-- ADD　START 2007/07/09 BIS 張楠 -->									
						errMap.put(objKey ,name);
//						<!-- ADD　END　 2007/07/09 BIS 張楠 -->	
					}
				}
			}
			name	 = null;
			value	 = null;
			property = null;
//			<!-- ADD　START 2007/07/09 BIS 張楠 -->					
			objKey 	 = null;
//			<!-- ADD　END　 2007/07/09 BIS 張楠 -->			


			//-----年齢-----
			name	 = namePrefix+" 年齢 "+cnt+"行目";
			value	 = StringUtil.toHankakuDigit(kenkyushaInfo.getNenrei());
			property = "shinseiDataInfo.kenkyuSoshikiInfoList.nenrei";
//			<!-- ADD　START 2007/07/09 BIS 張楠 -->	
			objKey	 = "shinseiDataInfo.kenkyuSoshikiInfoList["+cntKey+"].nenrei";
//			<!-- ADD　END　 2007/07/09 BIS 張楠 -->	
			//本登録
			if(page >= 2){
				//必須チェック
				//2005.09.08 iso 半角スペースのみも必須チェックではじくよう変更
//				if(StringUtil.isBlank(value)){
				if(StringUtil.isEscapeBlank(value)){
					ActionError error = new ActionError("errors.required",name);
					errors.add(property, error);
//					<!-- ADD　START 2007/07/09 BIS 張楠 -->									
					errMap.put(objKey ,name);
//					<!-- ADD　END　 2007/07/09 BIS 張楠 -->	
				}
			}
			//一時保存, 本登録
			if(page >=0){
				if(!StringUtil.isBlank(value)){
					//数値チェック
					if(!StringUtil.isDigit(value)){
						ActionError error = new ActionError("errors.numeric",name);
						errors.add(property, error);
//						<!-- ADD　START 2007/07/09 BIS 張楠 -->									
						errMap.put(objKey ,name);
//						<!-- ADD　END　 2007/07/09 BIS 張楠 -->	
					//最大文字数チェック
					}else if(value.length() > 2){
						ActionError error = new ActionError("errors.maxlength",name,"2");
						errors.add(property, error);
//						<!-- ADD　START 2007/07/09 BIS 張楠 -->									
						errMap.put(objKey ,name);
//						<!-- ADD　END　 2007/07/09 BIS 張楠 -->	
					}
				}
			}
			kenkyushaInfo.setNenrei(value);		//半角数値に変換した値をセット
			name	 = null;
			value	 = null;
			property = null;
//			<!-- ADD　START 2007/07/09 BIS 張楠 -->					
			objKey 	 = null;
//			<!-- ADD　END　 2007/07/09 BIS 張楠 -->			


			if(!kyoryokusha){
				//-----所属機関名（コード）-----
				name	 = namePrefix+" 所属研究機関（番号） "+cnt+"行目";
				value	 = StringUtil.toHankakuDigit(kenkyushaInfo.getShozokuCd());
				property = "shinseiDataInfo.kenkyuSoshikiInfoList.shozokuCd";
//				<!-- ADD　START 2007/07/09 BIS 張楠 -->	
				objKey	 = "shinseiDataInfo.kenkyuSoshikiInfoList["+cntKey+"].shozokuCd";
//				<!-- ADD　END　 2007/07/09 BIS 張楠 -->	
				//本登録
				if(page >= 2){
					//必須チェック
					//2005.09.08 iso 半角スペースのみも必須チェックではじくよう変更
//					if(StringUtil.isBlank(value)){
					if(StringUtil.isEscapeBlank(value)){
						ActionError error = new ActionError("errors.required",name);
						errors.add(property, error);
//						<!-- ADD　START 2007/07/09 BIS 張楠 -->									
						errMap.put(objKey ,name);
//						<!-- ADD　END　 2007/07/09 BIS 張楠 -->	
						kikanFlg = false;	//機関コードが不正とする
					}
				}
				//一時保存, 本登録
				if(page >=0){
					if(!StringUtil.isBlank(value)){
						//数値チェック
						if(!StringUtil.isDigit(value)){
							ActionError error = new ActionError("errors.numeric",name);
							errors.add(property, error);
//							<!-- ADD　START 2007/07/09 BIS 張楠 -->									
							errMap.put(objKey ,name);
//							<!-- ADD　END　 2007/07/09 BIS 張楠 -->	
							kikanFlg = false;	//機関コードが不正とする
						//文字数チェック
						}else if(value.length() != 5){
							ActionError error = new ActionError("errors.length",name,"5");
							errors.add(property, error);
//							<!-- ADD　START 2007/07/09 BIS 張楠 -->									
							errMap.put(objKey ,name);
//							<!-- ADD　END　 2007/07/09 BIS 張楠 -->	
							kikanFlg = false;	//機関コードが不正とする
						}
					}
				}
				kenkyushaInfo.setShozokuCd(value);		//半角数値に変換した値をセット
				//代表機関コードを退避する
				if (i == 1){
					daihyoKikan = value;
				}
				name	 = null;
				value	 = null;
				property = null;
//				<!-- ADD　START 2007/07/09 BIS 張楠 -->					
				objKey 	 = null;
//				<!-- ADD　END　 2007/07/09 BIS 張楠 -->			
				
				//-----部局名（コード）-----
				name	 = namePrefix+" 部局（番号） "+cnt+"行目";
				value	 = StringUtil.toHankakuDigit(kenkyushaInfo.getBukyokuCd());
				property = "shinseiDataInfo.kenkyuSoshikiInfoList.bukyokuCd";
//				<!-- ADD　START 2007/07/09 BIS 張楠 -->	
				objKey	 = "shinseiDataInfo.kenkyuSoshikiInfoList["+cntKey+"].bukyokuCd";
//				<!-- ADD　END　 2007/07/09 BIS 張楠 -->	
				//本登録
				if(page >= 2){
					//必須チェック
					//2005.09.08 iso 半角スペースのみも必須チェックではじくよう変更
//					if(StringUtil.isBlank(value)){
					if(StringUtil.isEscapeBlank(value)){
						ActionError error = new ActionError("errors.required",name);
						errors.add(property, error);
//						<!-- ADD　START 2007/07/09 BIS 張楠 -->									
						errMap.put(objKey ,name);
//						<!-- ADD　END　 2007/07/09 BIS 張楠 -->	
					}
				}
				//一時保存, 本登録
				if(page >=0){
					if(!StringUtil.isBlank(value)){
						//数値チェック
						if(!StringUtil.isDigit(value)){
							ActionError error = new ActionError("errors.numeric",name);
							errors.add(property, error);
//							<!-- ADD　START 2007/07/09 BIS 張楠 -->									
							errMap.put(objKey ,name);
//							<!-- ADD　END　 2007/07/09 BIS 張楠 -->	
						//文字数チェック
						}else if(value.length() != 3){
							ActionError error = new ActionError("errors.length",name,"3");
							errors.add(property, error);
//							<!-- ADD　START 2007/07/09 BIS 張楠 -->									
							errMap.put(objKey ,name);
//							<!-- ADD　END　 2007/07/09 BIS 張楠 -->	
						}
					}
				}
				kenkyushaInfo.setBukyokuCd(value);		//半角数値に変換した値をセット
				name	 = null;
				value	 = null;
				property = null;
//				<!-- ADD　START 2007/07/09 BIS 張楠 -->					
				objKey 	 = null;
//				<!-- ADD　END　 2007/07/09 BIS 張楠 -->			
			}



			//-----部局名（和文）-----
			name	 = namePrefix+" 部局（和文） "+cnt+"行目";
			value	 = kenkyushaInfo.getBukyokuName();
			property = "shinseiDataInfo.kenkyuSoshikiInfoList.bukyokuName";
//			<!-- ADD　START 2007/07/09 BIS 張楠 -->	
			objKey	 = "shinseiDataInfo.kenkyuSoshikiInfoList["+cntKey+"].bukyokuName";
//			<!-- ADD　END　 2007/07/09 BIS 張楠 -->	
			//一時保存, 本登録
			if(page >=0){
				if(!StringUtil.isBlank(value)){
					//最大文字数チェック
					if(value.length() > 50){
						ActionError error = new ActionError("errors.maxlength",name,"50");
						errors.add(property, error);
//						<!-- ADD　START 2007/07/09 BIS 張楠 -->									
						errMap.put(objKey ,name);
//						<!-- ADD　END　 2007/07/09 BIS 張楠 -->	
					}
				}
			}
			name	 = null;
			value	 = null;
			property = null;
//			<!-- ADD　START 2007/07/09 BIS 張楠 -->					
			objKey 	 = null;
//			<!-- ADD　END　 2007/07/09 BIS 張楠 -->			



			//-----職名コード-----
			name	 = namePrefix+" 職 "+cnt+"行目";
			value	 = kenkyushaInfo.getShokushuCd();
			property = "shinseiDataInfo.kenkyuSoshikiInfoList.shokushuCd";
//			<!-- ADD　START 2007/07/09 BIS 張楠 -->	
			objKey	 = "shinseiDataInfo.kenkyuSoshikiInfoList["+cntKey+"].shokushuCd";
//			<!-- ADD　END　 2007/07/09 BIS 張楠 -->	
			//本登録
			if(page >= 2){
				//必須チェック
				//2005.09.08 iso 半角スペースのみも必須チェックではじくよう変更
//				if(StringUtil.isBlank(value)){
				if(StringUtil.isEscapeBlank(value)){
					ActionError error = new ActionError("errors.required",name);
					errors.add(property, error);
//					<!-- ADD　START 2007/07/09 BIS 張楠 -->									
					errMap.put(objKey ,name);
//					<!-- ADD　END　 2007/07/09 BIS 張楠 -->	
				}
			}
			name	 = null;
			value	 = null;
			property = null;
//			<!-- ADD　START 2007/07/09 BIS 張楠 -->					
			objKey 	 = null;
//			<!-- ADD　END　 2007/07/09 BIS 張楠 -->			


			//-----職名（和文）-----
			name	 = namePrefix+" 職 "+cnt+"行目";
			value	 = kenkyushaInfo.getShokushuNameKanji();
			property = "shinseiDataInfo.kenkyuSoshikiInfoList.shokushuNameKanji";
//			<!-- ADD　START 2007/07/09 BIS 張楠 -->	
			objKey	 = "shinseiDataInfo.kenkyuSoshikiInfoList["+cntKey+"].shokushuNameKanji";
//			<!-- ADD　END　 2007/07/09 BIS 張楠 -->	
			//本登録
			if(page >= 2){
				//必須チェック（職種コードが「その他(25)」のとき）
				if("25".equals(kenkyushaInfo.getShokushuCd())){
					//2005.09.08 iso 半角スペースのみも必須チェックではじくよう変更
//					if(StringUtil.isBlank(value)){
					if(StringUtil.isEscapeBlank(value)){
						ActionError error = new ActionError("errors.required",name);
						errors.add(property, error);
//						<!-- ADD　START 2007/07/09 BIS 張楠 -->									
						errMap.put(objKey ,name);
//						<!-- ADD　END　 2007/07/09 BIS 張楠 -->	
					}
				}
			}
			//一時保存, 本登録
			if(page >=0){
				if(!StringUtil.isBlank(value)){
					//最大文字数チェック
					if(value.length() > 20){
						ActionError error = new ActionError("errors.maxlength",name,"20");
						errors.add(property, error);
//						<!-- ADD　START 2007/07/09 BIS 張楠 -->									
						errMap.put(objKey ,name);
//						<!-- ADD　END　 2007/07/09 BIS 張楠 -->	
					}
				}
			}
			name	 = null;
			value	 = null;
			property = null;
//			<!-- ADD　START 2007/07/09 BIS 張楠 -->					
			objKey 	 = null;
//			<!-- ADD　END　 2007/07/09 BIS 張楠 -->			

			//研究協力者は以下の入力確認を行わない
			if(kyoryokusha){
				continue;
			}

			//-----現在の専門-----
			name	 = namePrefix+" 現在の専門 "+cnt+"行目";
			value	 = kenkyushaInfo.getSenmon();
			property = "shinseiDataInfo.kenkyuSoshikiInfoList.senmon";
//			<!-- ADD　START 2007/07/09 BIS 張楠 -->	
			objKey	 = "shinseiDataInfo.kenkyuSoshikiInfoList["+cntKey+"].senmon";
//			<!-- ADD　END　 2007/07/09 BIS 張楠 -->	
			//本登録
			if(page >= 2){
				//必須チェック
				//2005.09.08 iso 半角スペースのみも必須チェックではじくよう変更
//				if(StringUtil.isBlank(value)){
				if(StringUtil.isEscapeBlank(value)){
					ActionError error = new ActionError("errors.required",name);
					errors.add(property, error);
//					<!-- ADD　START 2007/07/09 BIS 張楠 -->									
					errMap.put(objKey ,name);
//					<!-- ADD　END　 2007/07/09 BIS 張楠 -->	
				}
			}
			//一時保存, 本登録
			if(page >=0){
				if(!StringUtil.isBlank(value)){
					//最大文字数チェック
					if(value.length() > 20){
						ActionError error = new ActionError("errors.maxlength",name,"20");
						errors.add(property, error);
//						<!-- ADD　START 2007/07/09 BIS 張楠 -->									
						errMap.put(objKey ,name);
//						<!-- ADD　END　 2007/07/09 BIS 張楠 -->	
					}
				}
			}
			name	 = null;
			value	 = null;
			property = null;
//			<!-- ADD　START 2007/07/09 BIS 張楠 -->					
			objKey 	 = null;
//			<!-- ADD　END　 2007/07/09 BIS 張楠 -->			


			//-----学位-----
			name	 = namePrefix+" 学位 "+cnt+"行目";
			value	 = kenkyushaInfo.getGakui();
			property = "shinseiDataInfo.kenkyuSoshikiInfoList.gakui";
//			<!-- ADD　START 2007/07/09 BIS 張楠 -->	
			objKey	 = "shinseiDataInfo.kenkyuSoshikiInfoList["+cntKey+"].gakui";
//			<!-- ADD　END　 2007/07/09 BIS 張楠 -->	
			//本登録
			if(page >= 2){
				//必須チェック
				//2005.09.08 iso 半角スペースのみも必須チェックではじくよう変更
//				if(StringUtil.isBlank(value)){
				if(StringUtil.isEscapeBlank(value)){
					ActionError error = new ActionError("errors.required",name);
					errors.add(property, error);
//					<!-- ADD　START 2007/07/09 BIS 張楠 -->									
					errMap.put(objKey ,name);
//					<!-- ADD　END　 2007/07/09 BIS 張楠 -->	
				}
			}
			//一時保存, 本登録
			if(page >=0){
				if(!StringUtil.isBlank(value)){
					//最大文字数チェック
					if(value.length() > 20){
						ActionError error = new ActionError("errors.maxlength",name,"20");
						errors.add(property, error);
//						<!-- ADD　START 2007/07/09 BIS 張楠 -->									
						errMap.put(objKey ,name);
//						<!-- ADD　END　 2007/07/09 BIS 張楠 -->	
					}
				}
			}
			name	 = null;
			value	 = null;
			property = null;
//			<!-- ADD　START 2007/07/09 BIS 張楠 -->					
			objKey 	 = null;
//			<!-- ADD　END　 2007/07/09 BIS 張楠 -->			


			//-----役割分担-----
			name	 = namePrefix+" 役割分担 "+cnt+"行目";
			value	 = kenkyushaInfo.getBuntan();
			property = "shinseiDataInfo.kenkyuSoshikiInfoList.buntan";
//			<!-- ADD　START 2007/07/09 BIS 張楠 -->	
			objKey	 = "shinseiDataInfo.kenkyuSoshikiInfoList["+cntKey+"].buntan";
//			<!-- ADD　END　 2007/07/09 BIS 張楠 -->	
			//本登録
			if(page >= 2){
				//必須チェック
				//2005.09.08 iso 半角スペースのみも必須チェックではじくよう変更
//				if(StringUtil.isBlank(value)){
				if(StringUtil.isEscapeBlank(value)){
					ActionError error = new ActionError("errors.required",name);
					errors.add(property, error);
//					<!-- ADD　START 2007/07/09 BIS 張楠 -->									
					errMap.put(objKey ,name);
//					<!-- ADD　END　 2007/07/09 BIS 張楠 -->	
				}
			}
			//一時保存, 本登録
			if(page >=0){
				if(!StringUtil.isBlank(value)){
					//最大文字数チェック
					if(value.length() > 50){
						ActionError error = new ActionError("errors.maxlength",name,"50");
						errors.add(property, error);
//						<!-- ADD　START 2007/07/09 BIS 張楠 -->									
						errMap.put(objKey ,name);
//						<!-- ADD　END　 2007/07/09 BIS 張楠 -->	
					}
				}
			}
			name	 = null;
			value	 = null;
			property = null;
//			<!-- ADD　START 2007/07/09 BIS 張楠 -->					
			objKey 	 = null;
//			<!-- ADD　END　 2007/07/09 BIS 張楠 -->			


			//-----研究経費-----
			name	 = namePrefix+" 研究経費 "+cnt+"行目";
			value	 = StringUtil.toHankakuDigit(kenkyushaInfo.getKeihi());
			property = "shinseiDataInfo.kenkyuSoshikiInfoList.keihi";
//			<!-- ADD　START 2007/07/09 BIS 張楠 -->	
			objKey	 = "shinseiDataInfo.kenkyuSoshikiInfoList["+cntKey+"].keihi";
//			<!-- ADD　END　 2007/07/09 BIS 張楠 -->	
			//本登録
			if(page >= 2){
				//必須チェック
				//2005.09.08 iso 半角スペースのみも必須チェックではじくよう変更
//				if(StringUtil.isBlank(value)){
				if(StringUtil.isEscapeBlank(value)){
//UPDATE　START 2007/07/23 BIS 金京浩  //連携研究者：値が入っていない場合はチェックしない。
			/*					
					ActionError error = new ActionError("errors.required",name);
					errors.add(property, error);
//					<!-- ADD　START 2007/07/09 BIS 張楠 -->									
					errMap.put(objKey ,name);
//					<!-- ADD　END　 2007/07/09 BIS 張楠 -->	
			 */		
					if(!"4".equals(kenkyushaInfo.getBuntanFlag())){						
						ActionError error = new ActionError("errors.required",name);
						errors.add(property, error);
//						<!-- ADD　START 2007/07/09 BIS 張楠 -->									
						errMap.put(objKey ,name);
//						<!-- ADD　END　 2007/07/09 BIS 張楠 -->	
					}
//UPDATE　END　 2007/07/23 BIS 金京浩	
				}
				else{
					//研究代表者以外の場合のみチェック
//UPDATE　START 2007/07/23 BIS 金京浩  //連携研究者：値が入っていない場合はチェックしない。					
//					if(!"1".equals(kenkyushaInfo.getBuntanFlag())){
					if("4".equals(kenkyushaInfo.getBuntanFlag())){
						ActionError error = new ActionError("errors.5076",name);
						errors.add(property, error);								
						errMap.put(objKey ,name);
						
					}
					else if(!"1".equals(kenkyushaInfo.getBuntanFlag())){
//UPDATE　END　 2007/07/23 BIS 金京浩						
						//分担金がある場合
						if("1".equals(shinseiDataInfo.getBuntankinFlg())){
//							チェックルールが変更した為、コメントする 2005/9/1
//							if(StringUtil.parseInt(value) == 0){
//								ActionError error = new ActionError("errors.5049",name);
//								errors.add(property, error);
//							}

							//入力機関コードが正しい
							if (kikanFlg) {
								//機関コードを取得する
								String kikanCd = StringUtil.toHankakuDigit(kenkyushaInfo.getShozokuCd());
								
								//代表者機関と異なる場合
								if (!daihyoKikan.equals(kikanCd)){
									//経費の合計値を確保する
//									diffKikanTotalKeihi = diffKikanTotalKeihi + Long.parseLong(value);
									diffKikanTotalKeihi = diffKikanTotalKeihi + StringUtil.parseLong(value);
								}else{
//2007/02/07　劉長宇　追加　ここから
                                    //分担者の場合、所属機関コードが代表者の所属機関コードと同じ場合、「０」以外はエラー。
								    if(StringUtil.parseInt(value) != 0){
								        ActionError error = new ActionError("errors.9030",name);
                                        errors.add(property, error);
//                						<!-- ADD　START 2007/07/09 BIS 張楠 -->									
                						errMap.put(objKey ,name);
//                						<!-- ADD　END　 2007/07/09 BIS 張楠 -->	
								    }
//2007/02/07　劉長宇　追加　ここから
                                }
							}
							
						}
						else{
							//分担金がない場合
							if(StringUtil.parseInt(value) != 0){
								ActionError error = new ActionError("errors.5050",name);
								errors.add(property, error);
//								<!-- ADD　START 2007/07/09 BIS 張楠 -->									
								errMap.put(objKey ,name);
//								<!-- ADD　END　 2007/07/09 BIS 張楠 -->	
							}
						}
					}
				}
			}
			//一時保存, 本登録
			if(page >=0){
				if(!StringUtil.isBlank(value)){
					//数値チェック
					if(!StringUtil.isDigit(value)){
						ActionError error = new ActionError("errors.numeric",name);
						errors.add(property, error);
//						<!-- ADD　START 2007/07/09 BIS 張楠 -->									
						errMap.put(objKey ,name);
//						<!-- ADD　END　 2007/07/09 BIS 張楠 -->	
					//最大文字数チェック
					}else if(value.length() > 7){
						ActionError error = new ActionError("errors.maxlength",name,"7");
						errors.add(property, error);
//						<!-- ADD　START 2007/07/09 BIS 張楠 -->									
						errMap.put(objKey ,name);
//						<!-- ADD　END　 2007/07/09 BIS 張楠 -->	
					}
				}
			}
			kenkyushaInfo.setKeihi(value);	//半角数値に変換した値をセット
			name	 = null;
			value	 = null;
			property = null;
//			<!-- ADD　START 2007/07/09 BIS 張楠 -->					
			objKey 	 = null;
//			<!-- ADD　END　 2007/07/09 BIS 張楠 -->			


			//-----エフォート-----
			name	 = namePrefix+" エフォート "+cnt+"行目";
			value	 = StringUtil.toHankakuDigit(kenkyushaInfo.getEffort());
			property = "shinseiDataInfo.kenkyuSoshikiInfoList.effort";
//			<!-- ADD　START 2007/07/09 BIS 張楠 -->	
			objKey	 = "shinseiDataInfo.kenkyuSoshikiInfoList["+cntKey+"].effort";
//			<!-- ADD　END　 2007/07/09 BIS 張楠 -->	
            
//2006/08/15 苗　追加ここから
            String keihiNendo1 = shinseiDataInfo.getKenkyuKeihiSoukeiInfo().getKenkyuKeihi6()[0].getKeihi();
            String jigyoCd = shinseiDataInfo.getJigyoCd();
//2006/08/15　苗　追加ここまで            
			//本登録
			if(page >= 2){
				// 20050728 エフォートは全てにおいて必須であるため分担金の有無に関わらない
				//2005.09.08 iso 半角スペースのみも必須チェックではじくよう変更
//				if(StringUtil.isBlank(value)){
				if(StringUtil.isEscapeBlank(value)){
					ActionError error = new ActionError("errors.required",name);
					errors.add(property, error);
//					<!-- ADD　START 2007/07/09 BIS 張楠 -->									
					errMap.put(objKey ,name);
//					<!-- ADD　END　 2007/07/09 BIS 張楠 -->	
				}
			}
			//一時保存, 本登録
			if(page >=0){
				if(!StringUtil.isBlank(value)){
					//存在チェック（研究分担者のとき）
					if("2".equals(kenkyushaInfo.getBuntanFlag())){
						buntanEffort = true;
					}
					//数値チェック
					if(!StringUtil.isDigit(value)){
						ActionError error = new ActionError("errors.numeric",name);
						errors.add(property, error);
//						<!-- ADD　START 2007/07/09 BIS 張楠 -->									
						errMap.put(objKey ,name);
//						<!-- ADD　END　 2007/07/09 BIS 張楠 -->	
//2006/08/15 苗　修正ここから
					//範囲チェック（1～100）　20006/08/15　特定（新規）のみで、一年目の研究経費が0の場合、「0」のみ許可する。                       
                    }else if(StringUtil.parseInt(value)<=0 || StringUtil.parseInt(value)>100){
                        if (!IJigyoCd.JIGYO_CD_TOKUTEI_SINKI.equals(jigyoCd)) {
                            ActionError error = new ActionError("errors.range",
                                    name, IShinseiMaintenance.EFFORT_MIN,
                                    IShinseiMaintenance.EFFORT_MAX);
                            errors.add(property, error);
//    						<!-- ADD　START 2007/07/09 BIS 張楠 -->									
    						errMap.put(objKey ,name);
//    						<!-- ADD　END　 2007/07/09 BIS 張楠 -->	
                        } else if (StringUtil.parseInt(value) == 0 && 
                                StringUtil.parseInt(keihiNendo1) != 0) {
                            ActionError error = new ActionError("errors.range",
                                    name, IShinseiMaintenance.EFFORT_MIN,
                                    IShinseiMaintenance.EFFORT_MAX);
                            errors.add(property, error);
//    						<!-- ADD　START 2007/07/09 BIS 張楠 -->									
    						errMap.put(objKey ,name);
//    						<!-- ADD　END　 2007/07/09 BIS 張楠 -->	
                        } else if (StringUtil.parseInt(value)>100){
                            ActionError error = new ActionError("errors.range",
                                    name,
                                    IShinseiMaintenance.EFFORT_MIN_SINNKI,
                                    IShinseiMaintenance.EFFORT_MAX);
                            errors.add(property, error);
//    						<!-- ADD　START 2007/07/09 BIS 張楠 -->									
    						errMap.put(objKey ,name);
//    						<!-- ADD　END　 2007/07/09 BIS 張楠 -->	
                        }
                    //特定（新規）のみで、一年目の研究経費が「0」ではない場合、「0」許可しない。
					}else if (StringUtil.parseInt(value) != 0 && "0".equals(keihiNendo1)
                            && IJigyoCd.JIGYO_CD_TOKUTEI_SINKI.equals(jigyoCd)) {
                        ActionError error = new ActionError("errors.5069", Integer.toString(cnt));
                        errors.add(property, error);
//						<!-- ADD　START 2007/07/09 BIS 張楠 -->									
						errMap.put(objKey ,name);
//						<!-- ADD　END　 2007/07/09 BIS 張楠 -->	
                    }
// 2006/08/15 苗 修正ここまで
				}
			}
			kenkyushaInfo.setEffort(value);		//半角数値に変換した値をセット
			name	 = null;
			value	 = null;
			property = null;
//			<!-- ADD　START 2007/07/09 BIS 張楠 -->					
			objKey 	 = null;
//			<!-- ADD　END　 2007/07/09 BIS 張楠 -->			

		}
		//========== 研究組織表のリスト分繰り返し 終わり ==========

		//分担金の配分が有の場合
		//2005.09.12 iso 経費チェックを本登録時のみに修正
		if(page >= 2) {
			if("1".equals(shinseiDataInfo.getBuntankinFlg())){
				//代表者と異なる機関の分担者経費のトータルが０の場合はＮＧ
				if (diffKikanTotalKeihi == 0){
					name	 = namePrefix;
					property = "shinseiDataInfo.kenkyuSoshikiInfoList.keihi";
//					<!-- ADD　START 2007/07/09 BIS 張楠 -->	
					objKey	 = "shinseiDataInfo.kenkyuSoshikiInfoList.keihi";;
//					<!-- ADD　END　 2007/07/09 BIS 張楠 -->	
					ActionError error = new ActionError("errors.5061",name);
					errors.add(property, error);
//					<!-- ADD　START 2007/07/09 BIS 張楠 -->									
					errMap.put(objKey ,name);
//					<!-- ADD　END　 2007/07/09 BIS 張楠 -->	
				}
			}
		}
		property = null;
//		<!-- ADD　START 2007/07/09 BIS 張楠 -->					
		objKey 	 = null;
		shinseiDataInfo.setErrorsMap(errMap );
//		<!-- ADD　END　 2007/07/09 BIS 張楠 -->			
	}
}