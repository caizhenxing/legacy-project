/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *====================================================================== 
 */

package jp.go.jsps.kaken.web.shinsei.validate;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import jp.go.jsps.kaken.model.vo.ShinseiDataInfo;
import jp.go.jsps.kaken.model.vo.shinsei.DaihyouInfo;
import jp.go.jsps.kaken.model.vo.shinsei.KenkyuKeihiInfo;
import jp.go.jsps.kaken.model.vo.shinsei.KenkyuKeihiSoukeiInfo;
import jp.go.jsps.kaken.model.vo.shinsei.KenkyuSoshikiKenkyushaInfo;
import jp.go.jsps.kaken.util.StringUtil;
import jp.go.jsps.kaken.web.struts.ActionMapping;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import jp.go.jsps.kaken.model.IShinseiMaintenance;

/**
 * 形式チェッククラス（特定領域用）
 * ID RCSfile="$RCSfile: ShinseiValidatorTokutei.java,v $"
 * Revision="$Revision: 1.4 $"
 * Date="$Date: 2007/07/16 06:28:39 $"
 */
public class ShinseiValidatorTokutei extends DefaultShinseiValidator {
 
    /**
	 * コンストラクタ
	 * @param shinseiDataInfo
	 * @param page
	 */
	public ShinseiValidatorTokutei(ShinseiDataInfo shinseiDataInfo) {
		super(shinseiDataInfo);
	}


// 20050617
	/**
	 * 形式チェック（特定領域用）
	 * @see jp.go.jsps.kaken.web.shinsei.validate.IShinseiValidator#validate(jp.go.jsps.kaken.web.struts.ActionMapping, javax.servlet.http.HttpServletRequest)
	 */
	public ActionErrors validate(
			ActionMapping mapping,
			HttpServletRequest request,
			int page,
			ActionErrors errors) {

		validateAndSetTokutei(errors, request, page);		//特定領域のチェックとチェックボックス処理
		validateAndSetKeihiTotal(errors, page);				//研究経費の合計を算出しつつ、最大値チェックを行う
		validateKenkyuSoshiki(errors, page);				//研究組織表の形式チェックを行う
		countKenkyushaNinzu(errors, page);					//研究者
//2006/08/24　苗　追加ここから
        validateKomokuNo(errors);                           //研究項目番号の正規表現チェック
//2006/08/24　苗　追加ここまで        

		return errors;
	}

	/**
	 * 特定領域情報の状態を管理する
	 * @param errors
	 * @param request
	 * @param page
	 */
	private void validateAndSetTokutei(ActionErrors errors, HttpServletRequest request, int page)
	{
		String	name			=	null;			//項目名
		String	value			=	null;			//値
		String	property		=	null;			//プロパティ名
		int		cnt				=	0;				//エラーにて表示するラベルの番号
		String	namePrefix		=	null;			//エラーにて表示するラベルのプリフィックス	

// チェックボックス制御
		if(IShinseiMaintenance.CHECK_ON.equals(request.getParameter("shinseiDataInfo.changeFlg"))){		//大幅な変更
			shinseiDataInfo.setChangeFlg(IShinseiMaintenance.CHECK_ON);}					//オン
		else{shinseiDataInfo.setChangeFlg(IShinseiMaintenance.CHECK_OFF);}					//オフ
		
		//2005.08.09 iso 公募研究なら調整班に「-」を自動セット
		//調整班
//		if(IShinseiMaintenance.CHECK_ON.equals(request.getParameter("shinseiDataInfo.chouseiFlg"))){		// 調整班
//			shinseiDataInfo.setChouseiFlg(IShinseiMaintenance.CHECK_ON);}					//オン
//		else{shinseiDataInfo.setChouseiFlg(IShinseiMaintenance.CHECK_OFF);}					//オフ
		//公募研究以外でチェックなしの場合
		if(!IShinseiMaintenance.KUBUN_KOUBO.equals(shinseiDataInfo.getKenkyuKubun())
				&& IShinseiMaintenance.CHECK_ON.equals(request.getParameter("shinseiDataInfo.chouseiFlg"))) {
			shinseiDataInfo.setChouseiFlg(IShinseiMaintenance.CHECK_ON);}					//オン
		else{shinseiDataInfo.setChouseiFlg(IShinseiMaintenance.CHECK_OFF);}				//オフ

		/** 研究区分 */
		/** 新規・継続区分 */
		if(IShinseiMaintenance.KUBUN_SHUURYOU.equals(shinseiDataInfo.getKenkyuKubun())){
			//研究区分で終了研究が選択されていた場合には申請区分に新規をセット
			shinseiDataInfo.setShinseiKubun(IShinseiMaintenance.SHINSEI_NEW);
		}

		/** 大幅な変更を伴う研究課題 */
		if (page >= IShinseiMaintenance.CHECK_TWO){
			if (IShinseiMaintenance.CHECK_ON.equals(shinseiDataInfo.getChangeFlg())
				&& IShinseiMaintenance.SHINSEI_NEW.equals(shinseiDataInfo.getShinseiKubun())
// 終了研究以外の場合を条件に追加
				&& !IShinseiMaintenance.KUBUN_SHUURYOU.equals(shinseiDataInfo.getKenkyuKubun())
				){
				// チェックされている場合、新規・継続区分で新規が選択されている場合にはエラー（.xml側で実行？）
				ActionError error = new ActionError(
						"errors.5038",
						IShinseiMaintenance.SHINSEI_KUBUN,
						IShinseiMaintenance.CHANGE_FLG
						);
				errors.add("shinseiDataInfo.changeFlg", error);
			}
		}

		/** 継続の場合の研究課題番号 */
		// 様式調査票に記述されている継続課題データとは？
//		//2005.08.09 iso 公募研究 OR 計画研究(新規)の場合、研究課題番号にブランクをセット
//		if(IShinseiMaintenance.KUBUN_KOUBO.equals(shinseiDataInfo.getKenkyuKubun())
//				|| (IShinseiMaintenance.KUBUN_KEIKAKU.equals(shinseiDataInfo.getKenkyuKubun())
//					&& IShinseiMaintenance.SHINSEI_NEW.equals(shinseiDataInfo.getShinseiKubun()))) {
//			shinseiDataInfo.setKadaiNoKeizoku("");
//		}

		/** 領域番号＆領域略称名 */
		if(IShinseiMaintenance.KUBUN_SHUURYOU.equals(shinseiDataInfo.getKenkyuKubun()) || 
			IShinseiMaintenance.RYOUIKI_SHUURYOU.equals(shinseiDataInfo.getRyouikiNo())){
			shinseiDataInfo.setRyouikiNo(IShinseiMaintenance.RYOUIKI_SHUURYOU);				// 研究区分で終了研究領域が選択されている場合には自動で「999」をセット
			shinseiDataInfo.setRyouikiRyakuName(IShinseiMaintenance.SHUURYOU_NAME);			// 研究区分で終了研究領域が選択されている場合には自動で「成果取りまとめ」をセット
		}
		else{
			// 領域マスタに存在しない番号略称名の場合(ShinseiMaintenanceにて実行)
		}

		/** 研究項目番号 */
// xmlに必須チェックを追加したため条件付き必須チェックは削除
//		if(
//			(IShinseiMaintenance.KUBUN_KEIKAKU.equals(shinseiDataInfo.getKenkyuKubun())) ||
//			(IShinseiMaintenance.KUBUN_KOUBO.equals(shinseiDataInfo.getKenkyuKubun()))
//			){
//			//計画研究、または公募研究の場合
//			if((shinseiDataInfo.getRyouikiKoumokuNo() == null || shinseiDataInfo.getRyouikiKoumokuNo().length() <= 0) &&
//				shinseiDataInfo.getChouseiFlg() == IShinseiMaintenance.CHECK_OFF
//				){
//				//研究項目番号が入力されておらず、調整班チェックボックスにもチェックされていない場合
//				ActionError error = new ActionError(
//						"errors.required",
//						IShinseiMaintenance.KENKYUU_NUM
//						);
//				errors.add("shinseiDataInfo.ryouikiKoumokuNo", error);
//			}
//		}
		if(IShinseiMaintenance.KUBUN_SHUURYOU.equals(shinseiDataInfo.getKenkyuKubun())){
			//研究区分が終了研究であった場合には研究項目番号をセット　※IShinseiMaintenanceの定数にて管理
			shinseiDataInfo.setRyouikiKoumokuNo(IShinseiMaintenance.HAN_CHAR + IShinseiMaintenance.HAN_NO);
		}

// 20050818 調整班についてのチェックを修正
		if (page >= IShinseiMaintenance.CHECK_TWO){
		if(shinseiDataInfo.getRyouikiKoumokuNo() != null && shinseiDataInfo.getRyouikiKoumokuNo().length() >= 1){
			String chHan = shinseiDataInfo.getRyouikiKoumokuNo();
			if(IShinseiMaintenance.CHECK_ON.equals(shinseiDataInfo.getChouseiFlg()) &&
				("X00".equals(chHan) || "Y00".equals(chHan))
				){
				ActionError error = new ActionError(
						"errors.5054",
						IShinseiMaintenance.KENKYUU_NUM + "に" + chHan,
						IShinseiMaintenance.CHOUSEI_FLG
						);
				errors.add("shinseiDataInfo.chouseiFlg", error);
			}
// 自動的にセットしないため削除
//			//研究項目番号に入力されていた場合に調整班を自動的にチェックオフ
//			shinseiDataInfo.setChouseiFlg(IShinseiMaintenance.CHECK_OFF);
		}
		}

// 旧形式のチェックを削除
//		if (page >= IShinseiMaintenance.CHECK_TWO){
//			if (shinseiDataInfo.getRyouikiKoumokuNo() != null &&
//				shinseiDataInfo.getRyouikiKoumokuNo().length() >= 1){
//
//				if (shinseiDataInfo.getRyouikiKoumokuNo().indexOf(IShinseiMaintenance.HAN_SOUKATU) 
//						!= IShinseiMaintenance.KOUMOKU_CHECK_NUM
//					&& shinseiDataInfo.getRyouikiKoumokuNo().indexOf(IShinseiMaintenance.HAN_SHIEN) 
//						!= IShinseiMaintenance.KOUMOKU_CHECK_NUM){
//					// 文字列の最初が「X」または「Y」以外の場合はエラー
//					ActionError error = new ActionError(
//							"errors.5039",
//							IShinseiMaintenance.KENKYUU_NUM,
//							IShinseiMaintenance.HAN_SOUKATU,
//							IShinseiMaintenance.HAN_SHIEN);
//					errors.add("shinseiDataInfo.ryouikiKoumokuNo", error);
//				}
//				else{
//					// 領域マスタに存在しない番号の場合(ShinseMaintenance.javaにて実行)
//				}
//			}
//		}

		/** 調整班 */
		if (page >= IShinseiMaintenance.CHECK_TWO){
			if(IShinseiMaintenance.CHECK_ON.equals(shinseiDataInfo.getChouseiFlg())
				&& !IShinseiMaintenance.KUBUN_KEIKAKU.equals(shinseiDataInfo.getKenkyuKubun())){
				// チェックされている場合、研究区分で計画研究以外が選択されている場合にはエラー
				ActionError error = new ActionError(
						"errors.5038",
						IShinseiMaintenance.KENKYUU_KUBUN,
						IShinseiMaintenance.CHOUSEI_FLG);
				errors.add("shinseiDataInfo.chouseiFlg", error);
			}
			else{
				// 同じ研究項目番号内で１つのみ存在可能（登録時には同研究項目番号内に調整班の存在を許さない）
				// 同研究項目番号内の調整班チェック(重複チェックに含まれるため実装なし)
			}
		}

		/** 開示希望の有無 **/
// 自動的にセットせずにエラーを表示することになったため
//		if(IShinseiMaintenance.KUBUN_KEIKAKU.equals(shinseiDataInfo.getKenkyuKubun())){
//			//研究区分で計画研究が選択されていた場合には開示希望の有無にブランクをセット
//			shinseiDataInfo.setKaijikiboFlgNo(IShinseiMaintenance.KAIJI_FLG_SET);
//		}

	}
// Horikoshi End


	
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
	 * @param page   ページ番号（＝検証レベル）
	 */
/* *********************************************** 2005/9/1 共通関数になったのでコメントする
	private void validateKenkyuSoshiki(ActionErrors errors, int page)
	{
		String    name         = null;				//項目名
		String    value        = null;				//値
		String    property     = null;				//プロパティ名
		Set       kenkyuNoSet  = new HashSet();		//研究者番号のセット（重複を許さないため）
		boolean  buntanEffort = false;				//分担者のエフォート有無フラグ
		boolean  kyoryokusha = false;				//協力者フラグ
		int       kyoryokushaCnt = 0;				//協力者数
		int       buntanshaCnt = 0;					//代表者＋分担者数
		int       cnt=0;							//エラーにて表示するラベルの番号
		String    namePrefix = null;				//エラーにて表示するラベルのプリフィックス	
		
		//========== 研究組織表のリスト分繰り返し 始まり ==========
		List kenkyushaList = shinseiDataInfo.getKenkyuSoshikiInfoList();
		for(int i=1; i<=kenkyushaList.size(); i++){
			//研究者
			KenkyuSoshikiKenkyushaInfo kenkyushaInfo = 
						(KenkyuSoshikiKenkyushaInfo)kenkyushaList.get(i-1);

			//研究協力者かどうか判定
			kyoryokusha = ("3".equals(kenkyushaInfo.getBuntanFlag()));

			if(kyoryokusha){
				namePrefix="研究組織(研究協力者)".intern();
				kyoryokushaCnt++;
				cnt=kyoryokushaCnt;
			} else {
				namePrefix="研究組織(研究代表者及び研究分担者)".intern();
				buntanshaCnt++;
				cnt=buntanshaCnt;
			}
			
			if(!kyoryokusha){
				//-----研究者番号-----
				name     = namePrefix+" 研究者番号 "+cnt+"行目";
				value    = StringUtil.toHankakuDigit(kenkyushaInfo.getKenkyuNo());
				property = "shinseiDataInfo.kenkyuSoshikiInfoList.kenkyuNo";

				//本登録
				if(page >= 2){
					//必須チェック
					if(StringUtil.isBlank(value)){
						ActionError error = new ActionError("errors.required",name);
						errors.add(property, error);
					}else{
						//重複チェック
						if(kenkyuNoSet.contains(value)){
							//その他「99999999」のときは重複エラーとしない

// 20050628 「99999999」もエラーとして扱うため重複時にはすべてエラーとなる　→　条件を削除
// ShinseiMaintenanceで「99999999」の場合にはエラーとするため、重複チェックで「99999999」を対象とする必要はない
// 各ShinseiValidatorで記述する必要はなし
//							if(!"99999999".equals(value)){
								ActionError error = new ActionError("errors.5021",name);
								errors.add(property, error);
//							}
// Horikoshi

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
						//文字数チェック
						}else if(value.length() != 8){
							ActionError error = new ActionError("errors.length",name,"8");
							errors.add(property, error);
						}
					}
				}
				kenkyushaInfo.setKenkyuNo(value);	//半角数値に変換した値をセット
				name     = null;
				value    = null;
				property = null;
			}
			
			
			//-----氏名（漢字-姓）-----
			name     = namePrefix+" 氏名（漢字等）（姓） "+cnt+"行目";
			value    = kenkyushaInfo.getNameKanjiSei();
			property = "shinseiDataInfo.kenkyuSoshikiInfoList.nameKanjiSei";
			//本登録
			if(page >= 2){
				//必須チェック
				if(StringUtil.isBlank(value)){
					ActionError error = new ActionError("errors.required",name);
					errors.add(property, error);
				}
			}
			//一時保存, 本登録
			if(page >=0){
				if(!StringUtil.isBlank(value)){
					//最大文字数チェック
					if(value.length() > 16){
						ActionError error = new ActionError("errors.maxlength",name,"16");
						errors.add(property, error);
					}
				}
			}
			name     = null;
			value    = null;
			property = null;
			
			
			//-----氏名（漢字-名）-----
			name     = namePrefix+" 氏名（漢字等）（名） "+cnt+"行目";
			value    = kenkyushaInfo.getNameKanjiMei();
			property = "shinseiDataInfo.kenkyuSoshikiInfoList.nameKanjiMei";
			//本登録
			if(page >= 2){
				//必須チェック
				if(StringUtil.isBlank(value)){
					ActionError error = new ActionError("errors.required",name);
					errors.add(property, error);
				}
			}
			//一時保存, 本登録
			if(page >=0){
				if(!StringUtil.isBlank(value)){
					//最大文字数チェック
					if(value.length() > 16){
						ActionError error = new ActionError("errors.maxlength",name,"16");
						errors.add(property, error);
					}
				}
			}
			name     = null;
			value    = null;
			property = null;
			
			
			//-----氏名（フリガナ-姓）-----
			name     = namePrefix+" 氏名（フリガナ）（姓） "+cnt+"行目";
			value    = kenkyushaInfo.getNameKanaSei();
			property = "shinseiDataInfo.kenkyuSoshikiInfoList.nameKanaSei";
			//本登録
			if(page >= 2){
				//必須チェック
				if(StringUtil.isBlank(value)){
					ActionError error = new ActionError("errors.required",name);
					errors.add(property, error);
				}
			}
			//一時保存, 本登録
			if(page >=0){
				if(!StringUtil.isBlank(value)){
					//全角チェック
					if(!StringUtil.isZenkaku(value)){
						ActionError error = new ActionError("errors.mask_zenkaku",name);
						errors.add(property, error);
					//最大文字数チェック
					}else if(value.length() > 16){
						ActionError error = new ActionError("errors.maxlength",name,"16");
						errors.add(property, error);
					}
				}
			}
			name     = null;
			value    = null;
			property = null;
			
			
			//-----氏名（フリガナ-名）-----
			name     = namePrefix+" 氏名（フリガナ）（名） "+cnt+"行目";
			value    = kenkyushaInfo.getNameKanaMei();
			property = "shinseiDataInfo.kenkyuSoshikiInfoList.nameKanaMei";
			//本登録
			if(page >= 2){
				//必須チェック
				if(StringUtil.isBlank(value)){
					ActionError error = new ActionError("errors.required",name);
					errors.add(property, error);
				}
			}
			//一時保存, 本登録
			if(page >=0){
				if(!StringUtil.isBlank(value)){
					//全角チェック
					if(!StringUtil.isZenkaku(value)){
						ActionError error = new ActionError("errors.mask_zenkaku",name);
						errors.add(property, error);
					//最大文字数チェック
					}else if(value.length() > 16){
						ActionError error = new ActionError("errors.maxlength",name,"16");
						errors.add(property, error);
					}
				}
			}
			name     = null;
			value    = null;
			property = null;
			
		
			//-----年齢-----
			name     = namePrefix+" 年齢 "+cnt+"行目";
			value    = StringUtil.toHankakuDigit(kenkyushaInfo.getNenrei());
			property = "shinseiDataInfo.kenkyuSoshikiInfoList.nenrei";
			//本登録
			if(page >= 2){
				//必須チェック
				if(StringUtil.isBlank(value)){
					ActionError error = new ActionError("errors.required",name);
					errors.add(property, error);
				}
			}
			//一時保存, 本登録
			if(page >=0){
				if(!StringUtil.isBlank(value)){
					//数値チェック
					if(!StringUtil.isDigit(value)){
						ActionError error = new ActionError("errors.numeric",name);
						errors.add(property, error);
					//最大文字数チェック
					}else if(value.length() > 2){
						ActionError error = new ActionError("errors.maxlength",name,"2");
						errors.add(property, error);
					}
				}
			}
			kenkyushaInfo.setNenrei(value);		//半角数値に変換した値をセット
			name     = null;
			value    = null;
			property = null;
		
		
			if(!kyoryokusha){
				//-----所属機関名（コード）-----
				name     = namePrefix+" 所属研究機関（コード） "+cnt+"行目";
				value    = StringUtil.toHankakuDigit(kenkyushaInfo.getShozokuCd());
				property = "shinseiDataInfo.kenkyuSoshikiInfoList.shozokuCd";
				//本登録
				if(page >= 2){
					//必須チェック
					if(StringUtil.isBlank(value)){
						ActionError error = new ActionError("errors.required",name);
						errors.add(property, error);
					}
				}
				//一時保存, 本登録
				if(page >=0){
					if(!StringUtil.isBlank(value)){
						//数値チェック
						if(!StringUtil.isDigit(value)){
							ActionError error = new ActionError("errors.numeric",name);
							errors.add(property, error);
						//文字数チェック
						}else if(value.length() != 5){
							ActionError error = new ActionError("errors.length",name,"5");
							errors.add(property, error);
						}
					}
				}
				kenkyushaInfo.setShozokuCd(value);		//半角数値に変換した値をセット
				name     = null;
				value    = null;
				property = null;
			
			
				//-----部局名（コード）-----
				name     = namePrefix+" 部局（コード） "+cnt+"行目";
				value    = StringUtil.toHankakuDigit(kenkyushaInfo.getBukyokuCd());
				property = "shinseiDataInfo.kenkyuSoshikiInfoList.bukyokuCd";
				//本登録
				if(page >= 2){
					//必須チェック
					if(StringUtil.isBlank(value)){
						ActionError error = new ActionError("errors.required",name);
						errors.add(property, error);
					}
				}
				//一時保存, 本登録
				if(page >=0){
					if(!StringUtil.isBlank(value)){
						//数値チェック
						if(!StringUtil.isDigit(value)){
							ActionError error = new ActionError("errors.numeric",name);
							errors.add(property, error);
						//文字数チェック
						}else if(value.length() != 3){
							ActionError error = new ActionError("errors.length",name,"3");
							errors.add(property, error);
						}
					}
				}
				kenkyushaInfo.setBukyokuCd(value);		//半角数値に変換した値をセット
				name     = null;
				value    = null;
				property = null;
			}
			
		
		
			//-----部局名（和文）-----
			name     = namePrefix+" 部局（和文） "+cnt+"行目";
			value    = kenkyushaInfo.getBukyokuName();
			property = "shinseiDataInfo.kenkyuSoshikiInfoList.bukyokuName";
			//一時保存, 本登録
			if(page >=0){
				if(!StringUtil.isBlank(value)){
					//最大文字数チェック
					if(value.length() > 50){
						ActionError error = new ActionError("errors.maxlength",name,"50");
						errors.add(property, error);
					}
				}
			}
			name     = null;
			value    = null;
			property = null;
		
		
		
			//-----職名コード-----
			name     = namePrefix+" 職 "+cnt+"行目";
			value    = kenkyushaInfo.getShokushuCd();
			property = "shinseiDataInfo.kenkyuSoshikiInfoList.shokushuCd";
			//本登録
			if(page >= 2){
				//必須チェック
				if(StringUtil.isBlank(value)){
					ActionError error = new ActionError("errors.required",name);
					errors.add(property, error);
				}
			}		
			name     = null;
			value    = null;
			property = null;
		
		
			//-----職名（和文）-----
			name     = namePrefix+" 職 "+cnt+"行目";
			value    = kenkyushaInfo.getShokushuNameKanji();
			property = "shinseiDataInfo.kenkyuSoshikiInfoList.shokushuNameKanji";
			//本登録
			if(page >= 2){
				//必須チェック（職種コードが「その他(25)」のとき）
				if("25".equals(kenkyushaInfo.getShokushuCd())){
					if(StringUtil.isBlank(value)){
						ActionError error = new ActionError("errors.required",name);
						errors.add(property, error);
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
					}
				}
			}
			name     = null;
			value    = null;
			property = null;
			
			//研究協力者は以下の入力確認を行わない
			if(kyoryokusha){
				continue;
			}
			
			//-----現在の専門-----
			name     = namePrefix+" 現在の専門 "+cnt+"行目";
			value    = kenkyushaInfo.getSenmon();
			property = "shinseiDataInfo.kenkyuSoshikiInfoList.senmon";
			//本登録
			if(page >= 2){
				//必須チェック
				if(StringUtil.isBlank(value)){
					ActionError error = new ActionError("errors.required",name);
					errors.add(property, error);
				}
			}
			//一時保存, 本登録
			if(page >=0){
				if(!StringUtil.isBlank(value)){
					//最大文字数チェック
					if(value.length() > 20){
						ActionError error = new ActionError("errors.maxlength",name,"20");
						errors.add(property, error);
					}
				}
			}
			name     = null;
			value    = null;
			property = null;
			
			
			//-----学位-----
			name     = namePrefix+" 学位 "+cnt+"行目";
			value    = kenkyushaInfo.getGakui();
			property = "shinseiDataInfo.kenkyuSoshikiInfoList.gakui";
			//本登録
			if(page >= 2){
				//必須チェック
				if(StringUtil.isBlank(value)){
					ActionError error = new ActionError("errors.required",name);
					errors.add(property, error);
				}
			}
			//一時保存, 本登録
			if(page >=0){
				if(!StringUtil.isBlank(value)){
					//最大文字数チェック
					if(value.length() > 20){
						ActionError error = new ActionError("errors.maxlength",name,"20");
						errors.add(property, error);
					}
				}
			}
			name     = null;
			value    = null;
			property = null;
		
		
			//-----役割分担-----
			name     = namePrefix+" 役割分担 "+cnt+"行目";
			value    = kenkyushaInfo.getBuntan();
			property = "shinseiDataInfo.kenkyuSoshikiInfoList.buntan";
			//本登録
			if(page >= 2){
				//必須チェック
				if(StringUtil.isBlank(value)){
					ActionError error = new ActionError("errors.required",name);
					errors.add(property, error);
				}
			}
			//一時保存, 本登録
			if(page >=0){
				if(!StringUtil.isBlank(value)){
					//最大文字数チェック
					if(value.length() > 50){
						ActionError error = new ActionError("errors.maxlength",name,"50");
						errors.add(property, error);
					}
				}
			}
			name     = null;
			value    = null;
			property = null;
		
		
			//-----研究経費-----
			name     = namePrefix+" 研究経費 "+cnt+"行目";
			value    = StringUtil.toHankakuDigit(kenkyushaInfo.getKeihi());
			property = "shinseiDataInfo.kenkyuSoshikiInfoList.keihi";
			//本登録
			if(page >= 2){
				//必須チェック
				if(StringUtil.isBlank(value)){
					ActionError error = new ActionError("errors.required",name);
					errors.add(property, error);
				}
// 20050721
				else{
					if(!"1".equals(kenkyushaInfo.getBuntanFlag())){
						//研究代表者以外の場合のみチェック
						if("1".equals(shinseiDataInfo.getBuntankinFlg())){
							//分担金がある場合
							if(StringUtil.parseInt(value) == 0){
								ActionError error = new ActionError("errors.5049",name);
								errors.add(property, error);
							}
						}
						else{
							//分担金がない場合
							if(StringUtil.parseInt(value) != 0){
								ActionError error = new ActionError("errors.5050",name);
								errors.add(property, error);
							}
						}
					}
				}
// Horikoshi
			}
			//一時保存, 本登録
			if(page >=0){
				if(!StringUtil.isBlank(value)){
					//数値チェック
					if(!StringUtil.isDigit(value)){
						ActionError error = new ActionError("errors.numeric",name);
						errors.add(property, error);
					//最大文字数チェック
					}else if(value.length() > 7){
						ActionError error = new ActionError("errors.maxlength",name,"7");
						errors.add(property, error);
					}
				}
			}
			kenkyushaInfo.setKeihi(value);	//半角数値に変換した値をセット
			name     = null;
			value    = null;
			property = null;
	

			//-----エフォート-----
			name     = namePrefix+" エフォート "+cnt+"行目";
			value    = StringUtil.toHankakuDigit(kenkyushaInfo.getEffort());
			property = "shinseiDataInfo.kenkyuSoshikiInfoList.effort";
			//本登録
			if(page >= 2){
				// 20050728 エフォートは全てにおいて必須であるため分担金の有無に関わらない
				if(StringUtil.isBlank(value)){
						ActionError error = new ActionError("errors.required",name);
						errors.add(property, error);
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
					//範囲チェック（1～100）
					// 20050712 エフォートは全て必須項目で０を許可しないため
					}else if(StringUtil.parseInt(value)<=0 || StringUtil.parseInt(value)>100){
						ActionError error = new ActionError("errors.range",name,
								IShinseiMaintenance.EFFORT_MIN,
								IShinseiMaintenance.EFFORT_MAX
								);
						errors.add(property, error);
					}
				}
			}
			kenkyushaInfo.setEffort(value);		//半角数値に変換した値をセット
			name     = null;
			value    = null;
			property = null;
	
		}
		//========== 研究組織表のリスト分繰り返し 終わり ==========
		

// 20050728 分担金の有無とエフォートを比較することはしない
//		//-----分担金の有無との組み合わせ形式チェック-----
//		property = "shinseiDataInfo.buntankinFlg";
//		//本登録
//		if(page >= 2){
//			//分担金「有」の場合（分担者エフォートが１つも入力されていない場合はエラー）
//			if("1".equals(shinseiDataInfo.getBuntankinFlg()) && !buntanEffort){
//				ActionError error = new ActionError("errors.5022");
//				errors.add(property, error);
//			//分担金「無」場合（分担者エフォートが１つでも入力されているとエラー）
//			}else if("2".equals(shinseiDataInfo.getBuntankinFlg()) && buntanEffort){
//				ActionError error = new ActionError("errors.5022");
//				errors.add(property, error);
//			}
//		}
// Horikoshi

		property = null;
	}
*********************************************************** 2005/9/1 */

	/**
	 * 研究組織表より、全研究者数、他機関の研究者数を算出する。
	 * また、研究経費の合計が申請データの研究経費（1年目）と同じ値になるかをチェックする。
	 * 違っていた場合は ValidationException をスローする。
	 * @param errors 検証エラーがあった場合は、errorsに追加していく。
	 * @param page   ページ番号（＝検証レベル）
	 */
	private void countKenkyushaNinzu(ActionErrors errors, int page)
	{
		int keihiTotal = 0;															//研究組織表の研究経費合計
		List kenkyuSoshikiList = shinseiDataInfo.getKenkyuSoshikiInfoList();		//研究組織表の代表者年齢を申請データにもセットする

		//研究代表者の所属機関コードを取得する
		DaihyouInfo daihyouInfo = shinseiDataInfo.getDaihyouInfo();
		String daihyouKikanCd = daihyouInfo.getShozokuCd();

		//研究代表者の年齢、他機関の研究者数を取得する
		int kenkyuNinzu  = kenkyuSoshikiList.size();
		int takikanNinzu = 0;
		int kyoryokushaNinzu = 0;

		for(int i=0; i<kenkyuNinzu; i++){
			KenkyuSoshikiKenkyushaInfo kenkyushainfo = (KenkyuSoshikiKenkyushaInfo)kenkyuSoshikiList.get(i);

			if(i==0){														//代表者（インデックス値が「0」のもの）
				daihyouInfo.setNenrei(kenkyushainfo.getNenrei());			//代表者の年齢を申請データ側にセットする
			}else if("2".equals(kenkyushainfo.getBuntanFlag())){			//分担者
				if( daihyouKikanCd != null && 								//代表者の所属機関コードと違う場合はカウントプラス１
				   !daihyouKikanCd.equals(kenkyushainfo.getShozokuCd()))
				{
					takikanNinzu++;
				}
			} else {														//協力者
				kyoryokushaNinzu++;
			}
			keihiTotal = keihiTotal + StringUtil.parseInt((String)kenkyushainfo.getKeihi());
		}
		shinseiDataInfo.setKenkyuNinzu(String.valueOf(kenkyuNinzu-kyoryokushaNinzu));	//研究者数
		shinseiDataInfo.setTakikanNinzu(String.valueOf(takikanNinzu));					//他機関の研究者数
		shinseiDataInfo.setKyoryokushaNinzu(String.valueOf(kyoryokushaNinzu));			//研究協力者数
		
		//本登録
		if(page >= 2){

// 20050809 分担金の有無と研究人数を比較 ※有の場合には研究人数が2以上 ★研究分担者が存在しない場合にチェックをすり抜けていた
			if(Integer.parseInt(shinseiDataInfo.getKenkyuNinzu()) == 1 &&
				"1".equals(shinseiDataInfo.getBuntankinFlg())
				){
				String property = "shinseiDataInfo.kenkyuSoshikiInfoList.keihi";
				ActionError error = new ActionError("errors.5055");
				errors.add(property, error);
//				<!-- ADD　START 2007/07/09 BIS 張楠 -->	
				shinseiDataInfo.getErrorsMap().put(property,"分担金の有無:「有」→「研究組織表」の「分担者」の記入があり、かつ、「研究経費」に入力があること（無ければエラ");
				shinseiDataInfo.setErrorsMap( shinseiDataInfo.getErrorsMap());
//				<!-- ADD　END　 2007/07/09 BIS 張楠 -->	
			}
// Horikoshi
			//研究経費の合計が申請データの1年目研究経費と同じになっているかチェックする
			if(keihiTotal != 
			   StringUtil.parseInt(shinseiDataInfo.getKenkyuKeihiSoukeiInfo().getKenkyuKeihi()[0].getKeihi()))
			{
				String property = "shinseiDataInfo.kenkyuKeihiSoukeiInfo.kenkyuKeihi";
				ActionError error = new ActionError("errors.5017");
				errors.add(property, error);
//				<!-- ADD　START 2007/07/09 BIS 張楠 -->	
				shinseiDataInfo.getErrorsMap().put(property,"研究経費(1年目)が研究組織表の研究経費合計と違います。 ");
				shinseiDataInfo.setErrorsMap( shinseiDataInfo.getErrorsMap());
//				<!-- ADD　END　 2007/07/09 BIS 張楠 -->	
			}
		}
	}
	
	
	
	/**
	 * 研究経費の総計を算出して申請情報にセットする。
	 * @param errors 検証エラーがあった場合は、errorsに追加していく。
	 * @param page   ページ番号（＝検証レベル）
	 */
	private void validateAndSetKeihiTotal(
			ActionErrors errors,
			int page)	{

		//エラーメッセージ
		String name         = null;													//項目名
		String value        = String.valueOf(IShinseiMaintenance.MAX_KENKYUKEIHI);	//値
		String errKey       = "errors.maxValue";									//キー文字列
		String property     = "shinseiDataInfo.kenkyuKeihiSoukeiInfo.keihiTotal";	//プロパティ名
//ADD　START 2007-07-16 BIS 劉多良
		//行目プロパティ
		String propertyRow = null;
//ADD　END 2007-07-16 BIS 劉多良

		KenkyuKeihiSoukeiInfo soukeiInfo = shinseiDataInfo.getKenkyuKeihiSoukeiInfo();
		KenkyuKeihiInfo[]     keihiInfo  = soukeiInfo.getKenkyuKeihi();

		//研究経費の総計を算出する
		int intKeihiTotal          = 0;
		int intBihinhiTotal        = 0;
		int intShomohinhiTotal     = 0;
		int intKokunairyohiTotal   = 0;
		int intGaikokuryohiTotal   = 0;
		int intRyohiTotal          = 0;
		int intShakinTotal         = 0;
		int intSonotaTotal         = 0;

		//年度ごとに繰り返す
		for(int i=0; i<IShinseiMaintenance.NENSU; i++){            
			//単年度
			int intBihinhi        = StringUtil.parseInt(keihiInfo[i].getBihinhi());
			int intShomohinhi     = StringUtil.parseInt(keihiInfo[i].getShomohinhi());
			int intKokunairyohi   = StringUtil.parseInt(keihiInfo[i].getKokunairyohi());
			int intGaikokuryohi   = StringUtil.parseInt(keihiInfo[i].getGaikokuryohi());
			int intRyohi          = StringUtil.parseInt(keihiInfo[i].getRyohi());
			int intShakin         = StringUtil.parseInt(keihiInfo[i].getShakin());
			int intSonota         = StringUtil.parseInt(keihiInfo[i].getSonota());

			//単年度合計
			int intKeihi          = intBihinhi
								   + intShomohinhi
								   + intKokunairyohi
								   + intGaikokuryohi
								   + intRyohi
								   + intShakin
								   + intSonota
								   ;

			//単年度合計をオブジェクトにセットする
			keihiInfo[i].setKeihi(String.valueOf(intKeihi)); 

			int rowIndex = i+1;
//ADD　START 2007-07-16 BIS 劉多良
			//行目を設定する
			propertyRow = property + (rowIndex - 1);
//ADD　END 2007-07-16 BIS 劉多良
			name = "研究経費 " + rowIndex + "行目";
			//単年度合計の形式チェック
			if(IShinseiMaintenance.MAX_KENKYUKEIHI < intKeihi){
				ActionError error = new ActionError(errKey,name,value);
				errors.add(property, error);
//ADD　START 2007-07-16 BIS 劉多良
				errors.add(propertyRow, error);
//ADD　END 2007-07-16 BIS 劉多良
			}

			//チェックは一時保存時には行わない
			if(page >= 2){

// 20050611 Start
				switch(i){
					case(0):			//１年目
//						if(IShinseiMaintenance.MIN_KENKYUKEIHI > intKeihi && intKeihi != 0){
						if(IShinseiMaintenance.MIN_KENKYUKEIHI > intKeihi){
							ActionError error = new ActionError(
									"errors.5031",
									name,
									String.valueOf(IShinseiMaintenance.MIN_KENKYUKEIHI)
									);
							errors.add(property, error);
//ADD　START 2007-07-16 BIS 劉多良
							errors.add(propertyRow, error);
//ADD　END 2007-07-16 BIS 劉多良
						}
						break;

					case(1):			//２年目
						//終了研究の場合には0円
						if(IShinseiMaintenance.KUBUN_SHUURYOU.equals(shinseiDataInfo.getKenkyuKubun()) && intKeihi != 0){
							ActionError error = new ActionError(
									"errors.5032",
									name + " " + IShinseiMaintenance.KENKYUU_KUBUN,
									IShinseiMaintenance.NAME_SHUURYOU,
									""
									);
							errors.add(property, error);
//ADD　START 2007-07-16 BIS 劉多良
							errors.add(propertyRow, error);
//ADD　END 2007-07-16 BIS 劉多良
						}
						//0円、または10万円以上
						else if(IShinseiMaintenance.MIN_KENKYUKEIHI > intKeihi && intKeihi != 0){
							ActionError error = new ActionError(
									"errors.5031",
									name,
									String.valueOf(IShinseiMaintenance.MIN_KENKYUKEIHI)
									);
							errors.add(property, error);
//ADD　START 2007-07-16 BIS 劉多良
							errors.add(propertyRow, error);
//ADD　END 2007-07-16 BIS 劉多良
						}
						break;

					case(2):			//３年目
						//公募研究の場合には0円
						if(IShinseiMaintenance.KUBUN_KOUBO.equals(shinseiDataInfo.getKenkyuKubun()) && intKeihi != 0){
							ActionError error = new ActionError(
									"errors.5032",
									name + " " + IShinseiMaintenance.KENKYUU_KUBUN,
									IShinseiMaintenance.NAME_KOUBO,
									""
									);
							errors.add(property, error);
//ADD　START 2007-07-16 BIS 劉多良
							errors.add(propertyRow, error);
//ADD　END 2007-07-16 BIS 劉多良
						}
						//終了研究の場合には0円
						else if(IShinseiMaintenance.KUBUN_SHUURYOU.equals(shinseiDataInfo.getKenkyuKubun()) && intKeihi != 0){
							ActionError error = new ActionError(
									"errors.5032",
									name + " " + IShinseiMaintenance.KENKYUU_KUBUN,
									IShinseiMaintenance.NAME_SHUURYOU,
									""
									);
							errors.add(property, error);
//ADD　START 2007-07-16 BIS 劉多良
							errors.add(propertyRow, error);
//ADD　END 2007-07-16 BIS 劉多良
						}
						//前年度が0円の場合には0円
						else if("0".equals(keihiInfo[i-1].getKeihi()) && intKeihi != 0){
							ActionError error = new ActionError(
									"errors.5032",
									name + " 前年度",
									"0円",
									""
									);
							errors.add(property, error);
//ADD　START 2007-07-16 BIS 劉多良
							errors.add(propertyRow, error);
//ADD　END 2007-07-16 BIS 劉多良
						}
						//0円、または10万円以上
						else if(IShinseiMaintenance.MIN_KENKYUKEIHI > intKeihi && intKeihi != 0){
							ActionError error = new ActionError(
									"errors.5031",
									name,
									String.valueOf(IShinseiMaintenance.MIN_KENKYUKEIHI)
									);
							errors.add(property, error);
//ADD　START 2007-07-16 BIS 劉多良
							errors.add(propertyRow, error);
//ADD　END 2007-07-16 BIS 劉多良
						}
						break;

					case(3):			//４年目
						//終了研究の場合には0円
						if(IShinseiMaintenance.KUBUN_SHUURYOU.equals(shinseiDataInfo.getKenkyuKubun()) && intKeihi != 0){
							ActionError error = new ActionError(
									"errors.5032",
									name + " " + IShinseiMaintenance.KENKYUU_KUBUN,
									IShinseiMaintenance.NAME_SHUURYOU,
									""
									);
							errors.add(property, error);
//ADD　START 2007-07-16 BIS 劉多良
							errors.add(propertyRow, error);
//ADD　END 2007-07-16 BIS 劉多良
						}
						//前年度が0円の場合には0円
						else if("0".equals(keihiInfo[i-1].getKeihi()) && intKeihi != 0){
							ActionError error = new ActionError(
									"errors.5032",
									name + " 前年度",
									"0円",
									""
									);
							errors.add(property, error);
//ADD　START 2007-07-16 BIS 劉多良
							errors.add(propertyRow, error);
//ADD　END 2007-07-16 BIS 劉多良
						}
						//0円、または10万円以上
						else if(IShinseiMaintenance.MIN_KENKYUKEIHI > intKeihi && intKeihi != 0){
							ActionError error = new ActionError(
									"errors.5031",
									name,
									String.valueOf(IShinseiMaintenance.MIN_KENKYUKEIHI)
									);
							errors.add(property, error);
//ADD　START 2007-07-16 BIS 劉多良
							errors.add(propertyRow, error);
//ADD　END 2007-07-16 BIS 劉多良
						}
						break;

					case(4):			//５年目
						//終了研究の場合には0円
						if(IShinseiMaintenance.KUBUN_SHUURYOU.equals(shinseiDataInfo.getKenkyuKubun()) && intKeihi != 0){
							ActionError error = new ActionError(
									"errors.5032",
									name + " " + IShinseiMaintenance.KENKYUU_KUBUN,
									IShinseiMaintenance.NAME_SHUURYOU,
									""
									);
							errors.add(property, error);
//ADD　START 2007-07-16 BIS 劉多良
							errors.add(propertyRow, error);
//ADD　END 2007-07-16 BIS 劉多良
						}
						//前年度が0円の場合には0円
						else if("0".equals(keihiInfo[i-1].getKeihi()) && intKeihi != 0){
							ActionError error = new ActionError(
									"errors.5032",
									name + " 前年度",
									"0円",
									""
									);
							errors.add(property, error);
//ADD　START 2007-07-16 BIS 劉多良
							errors.add(propertyRow, error);
//ADD　END 2007-07-16 BIS 劉多良
						}
						//0円、または10万円以上
						else if(IShinseiMaintenance.MIN_KENKYUKEIHI > intKeihi && intKeihi != 0){
							ActionError error = new ActionError(
									"errors.5031",
									name,
									String.valueOf(IShinseiMaintenance.MIN_KENKYUKEIHI)
									);
							errors.add(property, error);
//ADD　START 2007-07-16 BIS 劉多良
							errors.add(propertyRow, error);
//ADD　END 2007-07-16 BIS 劉多良
						}
						break;
				}
// Horikoshi End

			}

			//総計
			intKeihiTotal          = intKeihiTotal        + intKeihi;
			intBihinhiTotal        = intBihinhiTotal      + StringUtil.parseInt(keihiInfo[i].getBihinhi());
			intShomohinhiTotal     = intShomohinhiTotal   + StringUtil.parseInt(keihiInfo[i].getShomohinhi());
			intKokunairyohiTotal   = intKokunairyohiTotal + StringUtil.parseInt(keihiInfo[i].getKokunairyohi());
			intGaikokuryohiTotal   = intGaikokuryohiTotal + StringUtil.parseInt(keihiInfo[i].getGaikokuryohi());
			intRyohiTotal          = intRyohiTotal        + StringUtil.parseInt(keihiInfo[i].getRyohi());
			intShakinTotal         = intShakinTotal       + StringUtil.parseInt(keihiInfo[i].getShakin());
			intSonotaTotal         = intSonotaTotal       + StringUtil.parseInt(keihiInfo[i].getSonota());
		}

		//総計をセットする
		soukeiInfo.setKeihiTotal(String.valueOf(intKeihiTotal));
		soukeiInfo.setBihinhiTotal(String.valueOf(intBihinhiTotal));
		soukeiInfo.setShomohinhiTotal(String.valueOf(intShomohinhiTotal));
		soukeiInfo.setKokunairyohiTotal(String.valueOf(intKokunairyohiTotal));
		soukeiInfo.setGaikokuryohiTotal(String.valueOf(intGaikokuryohiTotal));
		soukeiInfo.setRyohiTotal(String.valueOf(intRyohiTotal));
		soukeiInfo.setShakinTotal(String.valueOf(intShakinTotal));
		soukeiInfo.setSonotaTotal(String.valueOf(intSonotaTotal));

		//-----総計の形式チェック-----

		//範囲のチェックは一時保存時には行わない
		if(page == 2){
			if(IShinseiMaintenance.MAX_KENKYUKEIHI_GOKEI < intKeihiTotal){
				name = "研究経費 総計";
				ActionError error = new ActionError(
						"errors.5033",
						name,
						String.valueOf(IShinseiMaintenance.MAX_KENKYUKEIHI_GOKEI)
						);
				errors.add(property, error);
			}
		}

		if(IShinseiMaintenance.MAX_KENKYUKEIHI < intKeihiTotal){
			name = "研究経費 総計";
			ActionError error = new ActionError(errKey,name,value);
			errors.add(property, error);
		}
		if(IShinseiMaintenance.MAX_KENKYUKEIHI < intBihinhiTotal){
			name = "設備備品費 総計";
			ActionError error = new ActionError(errKey,name,value);
			errors.add(property, error);
		}
		if(IShinseiMaintenance.MAX_KENKYUKEIHI < intShomohinhiTotal){
			name = "消耗品費 総計";
			ActionError error = new ActionError(errKey,name,value);
			errors.add(property, error);
		}
		if(IShinseiMaintenance.MAX_KENKYUKEIHI < intKokunairyohiTotal){
			name = "国内旅費 総計";
			ActionError error = new ActionError(errKey,name,value);
			errors.add(property, error);
		}
		if(IShinseiMaintenance.MAX_KENKYUKEIHI < intGaikokuryohiTotal){
			name = "外国旅費 総計";
			ActionError error = new ActionError(errKey,name,value);
			errors.add(property, error);
		}
		if(IShinseiMaintenance.MAX_KENKYUKEIHI < intRyohiTotal){
			name = "旅費 総計";
			ActionError error = new ActionError(errKey,name,value);
			errors.add(property, error);
		}
		if(IShinseiMaintenance.MAX_KENKYUKEIHI < intShakinTotal){
			name = "謝金等 総計";
			ActionError error = new ActionError(errKey,name,value);
			errors.add(property, error);
		}
		if(IShinseiMaintenance.MAX_KENKYUKEIHI < intSonotaTotal){
			name = "その他 総計";
			ActionError error = new ActionError(errKey,name,value);
			errors.add(property, error);
		}
	}
    
//2006/08/24　苗　追加ここから    
    /**
     * 研究項目番号の正規表現チェック
     * 
     * @param errors 検証エラーがあった場合は、errorsに追加していく。
     */
    private void validateKomokuNo(ActionErrors errors){
        
        //研究項目番号
        String komokuNo = shinseiDataInfo.getRyouikiKoumokuNo();
        
        //３桁文字のチェック
        if(komokuNo != null && komokuNo.length() == 3){
            char komokuNoLetter = komokuNo.charAt(0);
            String komokuNoDigit = komokuNo.substring(1);

            //アルファベット１文字（大文字）でなければならない
            if (!StringUtil.isCapitalLetter(komokuNoLetter)) {
                ActionError error = new ActionError("errors.2001", "研究項目番号");
                errors.add("shinseiDataInfo.ryouikiKoumokuNo", error);
                return;
            }

            //１桁目が「Ｘ」また「Ｙ」の場合，２～３桁目は，００のみ
            if (komokuNoLetter == 'X' || komokuNoLetter == 'Y') {
                if (!"00".equals(komokuNoDigit)) {
                    ActionError error = new ActionError("errors.2001", "研究項目番号");
                    errors.add("shinseiDataInfo.ryouikiKoumokuNo", error);
                    return;
                }

            //１桁目がそれ以外のアルファベットの場合，２～３桁目は，０１～９９のみ    
            } else {
                if (!StringUtil.isDigit(komokuNoDigit)
                        || StringUtil.parseInt(komokuNoDigit) < 1
                        || StringUtil.parseInt(komokuNoDigit) > 99) {
                    ActionError error = new ActionError("errors.2001", "研究項目番号");
                    errors.add("shinseiDataInfo.ryouikiKoumokuNo", error);
                    return;
                }
            }
        }
    }
//2006/08/24　苗　追加ここまで    
}