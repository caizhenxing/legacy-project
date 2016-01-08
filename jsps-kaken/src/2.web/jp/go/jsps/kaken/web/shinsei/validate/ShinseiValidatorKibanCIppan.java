/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : takano
 *    Date        : 2005/01/17
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.shinsei.validate;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import jp.go.jsps.kaken.model.IShinseiMaintenance;
import jp.go.jsps.kaken.model.vo.ShinseiDataInfo;
import jp.go.jsps.kaken.model.vo.shinsei.DaihyouInfo;
import jp.go.jsps.kaken.model.vo.shinsei.KenkyuKeihiInfo;
import jp.go.jsps.kaken.model.vo.shinsei.KenkyuKeihiSoukeiInfo;
import jp.go.jsps.kaken.model.vo.shinsei.KenkyuSoshikiKenkyushaInfo;
import jp.go.jsps.kaken.util.StringUtil;
import jp.go.jsps.kaken.web.struts.ActionMapping;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

/**
 * 形式チェッククラス（基盤C一般）
 * ID RCSfile="$RCSfile: ShinseiValidatorKibanCIppan.java,v $"
 * Revision="$Revision: 1.6 $"
 * Date="$Date: 2007/07/21 06:38:45 $"
 */
public class ShinseiValidatorKibanCIppan extends DefaultShinseiValidator {
	
	/** 研究費を何年分入力するか */
//	UPDATE　START 2007-07-10 BIS 劉多良
//	private int NENSU = 4;
	private int NENSU = 5;
//	UPDATE　END　 2007-07-10 BIS 劉多良	
	
	/** 研究経費システムMAX値 */
	private int MAX_KENKYUKEIHI = 9999999;
	
	/** 研究経費合計最高金額(千円) */
	private int MAX_KENKYUKEIHI_GOKEI = 5000;
	
	/** 各年度の研究経費千円) */
	private int MIN_KENKYUKEIHI = 100;

	/**
	 * コンストラクタ
	 * @param shinseiDataInfo
	 * @param page
	 */
	public ShinseiValidatorKibanCIppan(ShinseiDataInfo shinseiDataInfo) {
		super(shinseiDataInfo);
	}

	
	/**
	 * 形式チェック（基盤C一般）
	 * @see jp.go.jsps.kaken.web.shinsei.validate.IShinseiValidator#validate(jp.go.jsps.kaken.web.struts.ActionMapping, javax.servlet.http.HttpServletRequest)
	 */
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request, int page, ActionErrors errors) {

		//定型処理----- 
		//ActionErrors errors = new ActionErrors();

		//研究経費の合計を算出しつつ、最大値チェックを行う
		validateAndSetKeihiTotal(errors, page);

		//2005/06/02 追加 ここから----------------------------------------------
		//各ShinseiValidaterクラスでvalidate処理を行うためコメント解除
		//研究組織表の形式チェックを行う。
		validateKenkyuSoshiki(errors, page);
		
		countKenkyushaNinzu(errors, page);
		//追加 ここまで---------------------------------------------------------

// 20050808
//		//時限コードによるキーワードチェック ※基盤(C)のみ
//		if(page > 1){
//			String chSaimoku = shinseiDataInfo.getKadaiInfo().getBunkaSaimokuCd();		//分科細目コード
//			String chKey = shinseiDataInfo.getKeyName();								//キーワード
//
//			if(chSaimoku == null || StringUtil.isBlank(chSaimoku) || chSaimoku.length() <= 0){
////				ActionError error = new ActionError(
////						"errors.required",
////						"細目番号"
////						);
////				errors.add("shinseiDataInfo.kadaiInfo.bunkaSaimokuCd", error);
//			}
//			else{
//				if(Integer.parseInt(chSaimoku) >= 9000){
//					//時限付き分科細目コードの場合には任意入力
//				}
//				else{
//					//時限付きでは無い場合には必須入力
//					if(StringUtil.isBlank(chKey)){
//						ActionError error = new ActionError(
//								"errors.required",
//								"キーワード"
//								);
//						errors.add("shinseiDataInfo.keyName", error);
//					}
//				}
//			}
//		}
// Horikoshi

		return errors;
	}
	
	
	/**
	 * 研究経費の総計を算出して申請情報にセットする。
	 * @param errors 検証エラーがあった場合は、errorsに追加していく。
	 * @param page   ページ番号（＝検証レベル）
	 */
	private void validateAndSetKeihiTotal(ActionErrors errors, int page)
	{
		
		//エラーメッセージ
		String name         = null;												//項目名
		String value        = String.valueOf(MAX_KENKYUKEIHI);						//値
		String errKey       = "errors.maxValue";									//キー文字列
		String property     = "shinseidataInfo.kenkyuKeihiSoukeiInfo.keihiTotal";	//プロパティ名
//ADD　START 2007-07-12 BIS 劉多良
		//行目プロパティ
		String propertyRow = null;
//ADD　END 2007-07-12 BIS 劉多良

		
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
		for(int i=0; i<NENSU; i++){
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
//ADD　START 2007-07-12 BIS 劉多良
			//行目を設定する
			propertyRow = property + (rowIndex - 1);
//ADD　END 2007-07-12 BIS 劉多良
			name = "研究経費 " + rowIndex + "行目";
			//単年度合計の形式チェック
			if(MAX_KENKYUKEIHI < intKeihi){
				ActionError error = new ActionError(errKey,name,value);
				errors.add(property, error);
//ADD　START 2007-07-12 BIS 劉多良
				errors.add(propertyRow, error);
//ADD　END 2007-07-12 BIS 劉多良
			}

// 2006/08/22 dyh update start
//			//チェックは一時保存時には行わない
//			if(page >= 2){
//				//研究経費は10万円以上でなければならない(3年目と4年目をのぞく)
//				if(i==2 || i==3){
//					if(MIN_KENKYUKEIHI > intKeihi && intKeihi!=0
//							//新規の場合を条件に追加
//							&& IShinseiMaintenance.SHINSEI_NEW.equals(shinseiDataInfo.getShinseiKubun())
//							){	
//						ActionError error = new ActionError("errors.5031",name,String.valueOf(MIN_KENKYUKEIHI));
//						errors.add(property, error);
//					}
//
//				//1年目は新規と同じルール
//				}else if(i == 0){
//					if(MIN_KENKYUKEIHI > intKeihi){	
//						ActionError error = new ActionError("errors.5031",name,String.valueOf(MIN_KENKYUKEIHI));
//						errors.add(property, error);
//					}
//
//				}else{
//					if(MIN_KENKYUKEIHI > intKeihi
//							//新規の場合を条件に追加
//							&& IShinseiMaintenance.SHINSEI_NEW.equals(shinseiDataInfo.getShinseiKubun())
//							){	
//						ActionError error = new ActionError("errors.5031",name,String.valueOf(MIN_KENKYUKEIHI));
//						errors.add(property, error);
//					}
//				}
//			}

//DELETE　START 2007-07-21 BIS 劉多良
//            // チェックは一時保存時には行わない、本登録でのみチェックする
//            if(page >= 2){
//                //1年目は新規と同じルール
//                if(i == 0){
//                    if(MIN_KENKYUKEIHI > intKeihi){ 
//                        ActionError error = new ActionError("errors.5031",name,String.valueOf(MIN_KENKYUKEIHI));
//                        errors.add(property, error);
//                    }
//
//                //2年目
//                } else if(i==1){
//                    //「新規」の場合、10万円以上でなければならない
//                    if(MIN_KENKYUKEIHI > intKeihi
//                            && IShinseiMaintenance.SHINSEI_NEW.equals(shinseiDataInfo.getShinseiKubun())
//                            ){  
//                        ActionError error = new ActionError("errors.5031",name,String.valueOf(MIN_KENKYUKEIHI));
//                        errors.add(property, error);
//                    }
//                    //「継続」の場合、「0」又は10万円以上でなければならない
//                    if(MIN_KENKYUKEIHI > intKeihi && intKeihi != 0 
//                            && IShinseiMaintenance.SHINSEI_CONTINUE.equals(shinseiDataInfo.getShinseiKubun())
//                            ){
//                        ActionError error = new ActionError("errors.5065",name,String.valueOf(MIN_KENKYUKEIHI));
//                        errors.add(property, error);                            
//                    }
//
//                //3年目と4年目時
//                } else if(i==2 || i==3){
//                    //3年目
//                    if(i==2){
//                        //新規と継続の場合、研究経費は「0」又は10万円以上でなければならない
//                        if(MIN_KENKYUKEIHI > intKeihi && intKeihi != 0){
//                            ActionError error = new ActionError("errors.5065",name,String.valueOf(MIN_KENKYUKEIHI));
//                            errors.add(property, error);
//                        }
//                    }
//
//                    //4年目時
//                    if(i==3){
//                        //新規の場合、研究経費は「0」又は10万円以上でなければならない
//                        if(MIN_KENKYUKEIHI > intKeihi && intKeihi != 0
//                                && IShinseiMaintenance.SHINSEI_NEW.equals(shinseiDataInfo.getShinseiKubun())){
//                            ActionError error = new ActionError("errors.5065",name,String.valueOf(MIN_KENKYUKEIHI));
//                            errors.add(property, error);
//                        }
//                        //「継続」の場合、「0」でなければならない(申請区分が継続の場合は、4年目は０円でなければならない)
//                        if(IShinseiMaintenance.SHINSEI_CONTINUE.equals(shinseiDataInfo.getShinseiKubun()) && intKeihi != 0){
//                            ActionError error = new ActionError("errors.5032",name + " 新規・継続区分", "継続", "");
//                            errors.add(property, error);                            
//                        }
//                    }
//
//                    //-----研究経費の組み合わせチェック-----
//                    //前年目の研究経費が０円の場合、本年目も０円でなければならない
//                    if("0".equals(keihiInfo[i - 1].getKeihi()) && intKeihi != 0){
//                        ActionError error = new ActionError("errors.5032", name + " 前年度", "0円", "");
//                        errors.add(property, error);
//                    }
//                }
//            }
//DELETE　END 2007-07-21 BIS 劉多良
//ADD　START 2007-07-21 BIS 劉多良
            // チェックは一時保存時には行わない、本登録でのみチェックする
            if(page >= 2){
                //1年目は新規と同じルール
                if(i == 0){
                    if(MIN_KENKYUKEIHI > intKeihi){ 
                        ActionError error = new ActionError("errors.5031",name,String.valueOf(MIN_KENKYUKEIHI));
                        errors.add(property, error);
        				errors.add(propertyRow, error);
                    }

                //2年目と3年目時
                } else if(i==1 || i==2){
                    //「新規」の場合、10万円以上でなければならない
                    if(MIN_KENKYUKEIHI > intKeihi
                            && IShinseiMaintenance.SHINSEI_NEW.equals(shinseiDataInfo.getShinseiKubun())
                            ){  
                        ActionError error = new ActionError("errors.5031",name,String.valueOf(MIN_KENKYUKEIHI));
                        errors.add(property, error);
        				errors.add(propertyRow, error);
                    }
                    //「継続」の場合、「0」又は10万円以上でなければならない
                    if(MIN_KENKYUKEIHI > intKeihi && intKeihi != 0 
                            && IShinseiMaintenance.SHINSEI_CONTINUE.equals(shinseiDataInfo.getShinseiKubun())
                            ){
                        ActionError error = new ActionError("errors.5065",name,String.valueOf(MIN_KENKYUKEIHI));
                        errors.add(property, error);                            
        				errors.add(propertyRow, error);
                    }
                    //3年目,「新規・継続区分」が「継続」で、2年目が「0」の場合、「0」
                    if(i == 2 && "0".equals(keihiInfo[i - 1].getKeihi()) && intKeihi != 0
                            && IShinseiMaintenance.SHINSEI_CONTINUE.equals(shinseiDataInfo.getShinseiKubun())
                    	){
                        ActionError error = new ActionError("errors.5032", name + " 前年度", "0円", "");
	                    errors.add(property, error);
	    				errors.add(propertyRow, error);
                	}
                    //3年目,「継続」の場合10万円以上でなければならない
                    if(i == 2 && MIN_KENKYUKEIHI > intKeihi && !("0".equals(keihiInfo[i - 1].getKeihi()))
                            && IShinseiMaintenance.SHINSEI_CONTINUE.equals(shinseiDataInfo.getShinseiKubun())
                            ){
                        ActionError error = new ActionError("errors.5031",name,String.valueOf(MIN_KENKYUKEIHI));
                        errors.add(property, error);                            
        				errors.add(propertyRow, error);
                    }

                //4年目と5年目時
                } else if(i==3 || i==4){
                    //4年目
                    if(i==3){
                        //新規と継続の場合、研究経費は「0」又は10万円以上でなければならない
                        if(MIN_KENKYUKEIHI > intKeihi && intKeihi != 0){
                            ActionError error = new ActionError("errors.5065",name,String.valueOf(MIN_KENKYUKEIHI));
                            errors.add(property, error);
            				errors.add(propertyRow, error);
                        }
                    }

                    //5年目時
                    if(i==4){
                        //新規の場合、研究経費は「0」又は10万円以上でなければならない
                        if(MIN_KENKYUKEIHI > intKeihi && intKeihi != 0
                                && IShinseiMaintenance.SHINSEI_NEW.equals(shinseiDataInfo.getShinseiKubun())){
                            ActionError error = new ActionError("errors.5065",name,String.valueOf(MIN_KENKYUKEIHI));
                            errors.add(property, error);
            				errors.add(propertyRow, error);
                        }
                        //「継続」の場合、「0」でなければならない(申請区分が継続の場合は、5年目は０円でなければならない)
                        if(IShinseiMaintenance.SHINSEI_CONTINUE.equals(shinseiDataInfo.getShinseiKubun()) && intKeihi != 0){
                            ActionError error = new ActionError("errors.5032",name + " 新規・継続区分", "継続", "");
                            errors.add(property, error);                            
            				errors.add(propertyRow, error);
                        }
                    }

                    //-----研究経費の組み合わせチェック-----
                    //前年目の研究経費が０円の場合、本年目も０円でなければならない
                    if("0".equals(keihiInfo[i - 1].getKeihi()) && intKeihi != 0){
                        ActionError error = new ActionError("errors.5032", name + " 前年度", "0円", "");
                        errors.add(property, error);
        				errors.add(propertyRow, error);
                    }
                }
            }
//ADD　END 2007-07-21 BIS 劉多良
// 2006/08/22 dyh update end

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

// 2006/08/22 dyh delete start 原因：上へ移動しました。
//		//チェックは一時保存時には行わない
//		if(page >= 2){
//			//-----研究経費の組み合わせチェック-----
//			//3年目の研究経費が０円の場合、4年目も０円でなければならない
//			if("0".equals(keihiInfo[2].getKeihi()) && !("0".equals(keihiInfo[3].getKeihi()))){
//				ActionError error = new ActionError("errors.5032","研究経費 3行目","0円","研究経費 4行目");
//				errors.add(property, error);
//			}
//			//申請区分が継続の場合は、4年目は０円でなければならない
//			if("2".equals(shinseiDataInfo.getShinseiKubun()) && !("0".equals(keihiInfo[3].getKeihi()))){
//				ActionError error = new ActionError("errors.5032","新規・継続区分","継続","研究経費 4行目");
//				errors.add(property, error);
//			}
//		}
// 2006/08/22 dyh delete end

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
			if(MAX_KENKYUKEIHI_GOKEI < intKeihiTotal){
				name = "研究経費 総計";
				ActionError error = new ActionError("errors.5033",name,String.valueOf(MAX_KENKYUKEIHI_GOKEI));
				errors.add(property, error);
			}
		}
		
		if(MAX_KENKYUKEIHI < intKeihiTotal){
			name = "研究経費 総計";
			ActionError error = new ActionError(errKey,name,value);
			errors.add(property, error);
		}

		if(MAX_KENKYUKEIHI < intBihinhiTotal){
			name = "設備備品費 総計";
			ActionError error = new ActionError(errKey,name,value);
			errors.add(property, error);
		}
		if(MAX_KENKYUKEIHI < intShomohinhiTotal){
			name = "消耗品費 総計";
			ActionError error = new ActionError(errKey,name,value);
			errors.add(property, error);
		}
		if(MAX_KENKYUKEIHI < intKokunairyohiTotal){
			name = "国内旅費 総計";
			ActionError error = new ActionError(errKey,name,value);
			errors.add(property, error);
		}
		if(MAX_KENKYUKEIHI < intGaikokuryohiTotal){
			name = "外国旅費 総計";
			ActionError error = new ActionError(errKey,name,value);
			errors.add(property, error);
		}
		if(MAX_KENKYUKEIHI < intRyohiTotal){
			name = "旅費 総計";
			ActionError error = new ActionError(errKey,name,value);
			errors.add(property, error);
		}
		if(MAX_KENKYUKEIHI < intShakinTotal){
			name = "謝金等 総計";
			ActionError error = new ActionError(errKey,name,value);
			errors.add(property, error);
		}		
		if(MAX_KENKYUKEIHI < intSonotaTotal){
			name = "その他 総計";
			ActionError error = new ActionError(errKey,name,value);
			errors.add(property, error);
		}		
		
	}	
	
	//2005/06/02 追加 ここから----------------------------------------------
	//各ShinseiValidaterクラスでvalidate処理を行うためコメント解除
	
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
		
		Set       kenkyuNoSet  = new HashSet();	//研究者番号のセット（重複を許さないため）
		boolean  buntanEffort = false;			//分担者のエフォート有無フラグ
		
		//2005/03/29 追加 ---------------------------------------ここから
		//理由 研究組織に研究者が追加されたため
		boolean  kyoryokusha = false;			//協力者フラグ
		int       kyoryokushaCnt = 0;			//協力者数
		int       buntanshaCnt = 0;				//代表者＋分担者数
		int       cnt=0;						//エラーにて表示するラベルの番号
		String    namePrefix = null;			//エラーにて表示するラベルのプリフィックス	
		//2005/03/29 追加 ---------------------------------------ここまで
		
		//========== 研究組織表のリスト分繰り返し 始まり ==========
		List kenkyushaList = shinseiDataInfo.getKenkyuSoshikiInfoList();
		for(int i=1; i<=kenkyushaList.size(); i++){
			//研究者
			KenkyuSoshikiKenkyushaInfo kenkyushaInfo = 
						(KenkyuSoshikiKenkyushaInfo)kenkyushaList.get(i-1);
			
			//研究協力者かどうか判定
			kyoryokusha = ("3".equals(kenkyushaInfo.getBuntanFlag()));

			if(kyoryokusha){
				//2005/06/02 変更 ここから-----------------------------------
				//名称変更のため
				//namePrefix="研究組織(協力者)".intern();
				namePrefix="研究組織(研究協力者)".intern();
				//変更 ここまで----------------------------------------------
				kyoryokushaCnt++;
				cnt=kyoryokushaCnt;
			} else {
				//2005/06/02 変更 ここから-----------------------------------
				//名称変更のため
				//namePrefix="研究組織".intern();
				namePrefix="研究組織(研究代表者及び研究分担者)".intern();
				//変更 ここまで----------------------------------------------
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
							if(!"99999999".equals(value)){
								ActionError error = new ActionError("errors.5021",name);
								errors.add(property, error);
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
				//必須チェック（研究代表者のとき）
//				if("1".equals(kenkyushaInfo.getBuntanFlag())){
					if(StringUtil.isBlank(value)){
						ActionError error = new ActionError("errors.required",name);
						errors.add(property, error);
					}
//				}
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
		property = null;
		
		
	}
************************************************* 2005/9/1 */	
	
	
	/**
	 * 研究組織表より、全研究者数、他機関の研究者数を算出する。
	 * また、研究経費の合計が申請データの研究経費（1年目）と同じ値になるかをチェックする。
	 * 違っていた場合は ValidationException をスローする。
	 * @param errors 検証エラーがあった場合は、errorsに追加していく。
	 * @param page   ページ番号（＝検証レベル）
	 */
	private void countKenkyushaNinzu(ActionErrors errors, int page)
	{
		//研究組織表の研究経費合計
		int keihiTotal = 0;
		
		//研究組織表の代表者年齢を申請データにもセットする
		List kenkyuSoshikiList = shinseiDataInfo.getKenkyuSoshikiInfoList();
		
		//研究代表者の所属機関コードを取得する
		DaihyouInfo daihyouInfo = shinseiDataInfo.getDaihyouInfo();
		String daihyouKikanCd = daihyouInfo.getShozokuCd();
		
		//研究代表者の年齢、他機関の研究者数を取得する
		int kenkyuNinzu  = kenkyuSoshikiList.size();
		int takikanNinzu = 0;
		
		//2005/03/30 追加 -------------------------ここから
		//理由 研究協力者が研究組織表に追加されたため
		int kyoryokushaNinzu = 0;
		//2005/03/30 追加 -------------------------ここまで

		for(int i=0; i<kenkyuNinzu; i++){
			KenkyuSoshikiKenkyushaInfo kenkyushainfo = (KenkyuSoshikiKenkyushaInfo)kenkyuSoshikiList.get(i);

			//2005/03/30 修正 -------------------------ここから
			//理由 研究協力者が研究組織表に追加されたため
			////代表者（インデックス値が「0」のもの）
			//if(i==0){
			//	//代表者の年齢を申請データ側にセットする
			//	daihyouInfo.setNenrei(kenkyushainfo.getNenrei());
			////分担者
			//}else{
			//	//代表者の所属機関コードと違う場合はカウントプラス１
			//	if( daihyouKikanCd != null && 
			//	   !daihyouKikanCd.equals(kenkyushainfo.getShozokuCd()))
			//	{
			//		takikanNinzu++;
			//	}
			//}

			//代表者（インデックス値が「0」のもの）
			if(i==0){
				//代表者の年齢を申請データ側にセットする
				daihyouInfo.setNenrei(kenkyushainfo.getNenrei());
			//分担者
			}else if("2".equals(kenkyushainfo.getBuntanFlag())){
				//代表者の所属機関コードと違う場合はカウントプラス１
				if( daihyouKikanCd != null && 
				   !daihyouKikanCd.equals(kenkyushainfo.getShozokuCd()))
				{
					takikanNinzu++;
				}
			//協力者
			} else {
				kyoryokushaNinzu++;
			}
			//2005/03/30 修正 -------------------------ここまで

			keihiTotal = keihiTotal + StringUtil.parseInt((String)kenkyushainfo.getKeihi());
		}
		//2005/06/02 変更 ここから-----------------------------------
		//名称変更のため
		//shinseiDataInfo.setKenkyuNinzu(String.valueOf(kenkyuNinzu));	//研究者数
		shinseiDataInfo.setKenkyuNinzu(String.valueOf(kenkyuNinzu-kyoryokushaNinzu));	//研究者数
		//変更 ここまで----------------------------------------------
		shinseiDataInfo.setTakikanNinzu(String.valueOf(takikanNinzu));	//他機関の研究者数

		//2005/03/30 追加 -------------------------ここから
		//理由 研究協力者が研究組織表に追加されたため
		shinseiDataInfo.setKyoryokushaNinzu(String.valueOf(kyoryokushaNinzu));	//研究協力者数
		//2005/03/30 追加 -------------------------ここまで

		
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

	//追加 ここまで-------------------------------------------------------------------
}