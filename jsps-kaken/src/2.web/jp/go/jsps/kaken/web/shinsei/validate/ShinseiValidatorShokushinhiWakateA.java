/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : syuu
 *    Date        : 2006/02/14
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.shinsei.validate;

import javax.servlet.http.HttpServletRequest;

import jp.go.jsps.kaken.model.vo.ShinseiDataInfo;
import jp.go.jsps.kaken.model.vo.shinsei.KenkyuKeihiInfo;
import jp.go.jsps.kaken.model.vo.shinsei.KenkyuKeihiSoukeiInfo;
import jp.go.jsps.kaken.util.StringUtil;
import jp.go.jsps.kaken.web.struts.ActionMapping;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

/**
 * 形式チェッククラス（特別研究促進費（若手研究(A)相当））
 * ID RCSfile="$RCSfile: ShinseiValidatorShokushinhiWakateA.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:09 $"
 */
public class ShinseiValidatorShokushinhiWakateA extends DefaultShinseiValidator{
	
	/** 研究費を何年分入力するか */
	private int NENSU = 3;
	
	/** 研究経費システムMAX値 */
	private int MAX_KENKYUKEIHI = 9999999;
	
	/** 研究経費合計最低金額(円) */
	private int MIN_KENKYUKEIHI_GOKEI = 5000;
	
	/** 研究経費合計最高金額(円) */
	private int MAX_KENKYUKEIHI_GOKEI = 30000;
	
	/** 各年度の研究経費(万円) */
	private int MIN_KENKYUKEIHI = 100;

	/**
	 * コンストラクタ
	 * @param shinseiDataInfo
	 * @param page
	 */
	public ShinseiValidatorShokushinhiWakateA(ShinseiDataInfo shinseiDataInfo) {
		super(shinseiDataInfo);
	}

	/**
	 * 形式チェック（特別研究促進費（若手研究(A)相当））
	 * @see jp.go.jsps.kaken.web.shinsei.validate.IShinseiValidator#validate(jp.go.jsps.kaken.web.struts.ActionMapping, javax.servlet.http.HttpServletRequest)
	 */
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request, int page, ActionErrors errors) {

		//ActionErrors errors = new ActionErrors();

		//研究経費の合計を算出しつつ、最大値チェックを行う
		validateAndSetKeihiTotal(errors, page);
		
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
			name = "研究経費 " + rowIndex + "行目";
			//単年度合計の形式チェック
			if(MAX_KENKYUKEIHI < intKeihi){
				ActionError error = new ActionError(errKey,name,value);
				errors.add(property, error);
			}
			
			//チェックは一時保存時には行わない
			if(page >= 2){
// 2年目の合計は「100以上」を許可する
				if(i==1){
					if(MIN_KENKYUKEIHI > intKeihi
//2006/08/22　苗　削除ここから                            
							//新規の場合を条件に追加
//							&& IShinseiMaintenance.SHINSEI_NEW.equals(shinseiDataInfo.getShinseiKubun())
//2006/08/22　苗　削除ここまで                            
							){	
						ActionError error = new ActionError("errors.5031",name,String.valueOf(MIN_KENKYUKEIHI));
						errors.add(property, error);
					}
				}
				//研究経費は10万円以上でなければならない(3年目をのぞく)
				else if(i==2){
					if("0".equals(keihiInfo[i-1].getKeihi()) && intKeihi != 0){
						ActionError error = new ActionError("errors.5032",name + " 前年度","0円","");
						errors.add(property, error);
					}
					else if(MIN_KENKYUKEIHI > intKeihi && intKeihi!=0
//2006/08/22　苗　削除ここから                            
							//新規の場合を条件に追加
//							&& IShinseiMaintenance.SHINSEI_NEW.equals(shinseiDataInfo.getShinseiKubun())
//2006/08/22 苗　削除ここまで                            
							){	
						ActionError error = new ActionError("errors.5031",name,String.valueOf(MIN_KENKYUKEIHI));
						errors.add(property, error);
					}

				//1年目は新規と同じルール
				}else if(i == 0){
					if(MIN_KENKYUKEIHI > intKeihi){	
						ActionError error = new ActionError("errors.5031",name,String.valueOf(MIN_KENKYUKEIHI));
						errors.add(property, error);
					}

				}else{
					if(MIN_KENKYUKEIHI > intKeihi
//2006/08/22　苗　削除ここから                            
							//新規の場合を条件に追加
//							&& IShinseiMaintenance.SHINSEI_NEW.equals(shinseiDataInfo.getShinseiKubun())
//2006/08/22　苗　削除ここまで                            
							){	
						ActionError error = new ActionError("errors.5031",name,String.valueOf(MIN_KENKYUKEIHI));
						errors.add(property, error);
					}
				}
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
		
		//チェックは一時保存時には行わない
		if(page >= 2){           
			//申請区分が継続の場合は、3年目は０円でなければならない
//2006/08/22　苗　削除ここから            
//			if("2".equals(shinseiDataInfo.getShinseiKubun()) && !("0".equals(keihiInfo[2].getKeihi()))){
//				ActionError error = new ActionError("errors.5032","新規・継続区分","継続","研究経費 3行目");
//				errors.add(property, error);
//			}
//2006/08/22　苗　削除ここまで            
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
			if(MIN_KENKYUKEIHI_GOKEI > intKeihiTotal
//2006/08/22　苗　削除ここから
					//新規の場合を条件に追加
//					&& IShinseiMaintenance.SHINSEI_NEW.equals(shinseiDataInfo.getShinseiKubun())
//2006/08/22　苗　削除ここまで                    
					){
				name = "研究経費 総計";
				ActionError error = new ActionError("errors.5031",name,String.valueOf(MIN_KENKYUKEIHI_GOKEI));
				errors.add(property, error);
			}
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
}