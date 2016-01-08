/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : takano
 *    Date        : 2004/01/20
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.jigyoKubun;

import java.util.HashSet;
import java.util.Set;

import jp.go.jsps.kaken.model.common.IJigyoCd;
import jp.go.jsps.kaken.model.common.IJigyoKubun;
import jp.go.jsps.kaken.model.role.UserRole;
import jp.go.jsps.kaken.model.vo.GyomutantoInfo;
import jp.go.jsps.kaken.model.vo.UserInfo;

/**
 * 審査対象事業区分設定。
 * ID RCSfile="$RCSfile: JigyoKubunFilter.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:51 $"
 */
public class JigyoKubunFilter {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------
	/** 審査対象となる事業区分の配列 */
	private static String[] SHINSA_TAISHO_JIGYO_KUBUN = {IJigyoKubun.JIGYO_KUBUN_GAKUSOU_HIKOUBO		//学創（非公募）
															 ,IJigyoKubun.JIGYO_KUBUN_KIBAN				//基盤
//2006/03/09 新種目追加
															 ,IJigyoKubun.JIGYO_KUBUN_WAKATESTART		//若手研究スタートアップ
															 ,IJigyoKubun.JIGYO_KUBUN_SHOKUSHINHI		//特別研究促進費
// 20050705 特定領域は審査対象としないため削除
//															 ,IJigyoKubun.JIGYO_KUBUN_TOKUTEI			//特定領域	2005.06.09 iso 追加
// Horikoshi
															 };
    
//2006/05/09 追加ここから
    /** 基盤系の事業区分の配列 */
    public static String[] KIBAN_JIGYO_KUBUN         = {
            IJigyoKubun.JIGYO_KUBUN_KIBAN, 
            IJigyoKubun.JIGYO_KUBUN_WAKATESTART,
            IJigyoKubun.JIGYO_KUBUN_SHOKUSHINHI       };
//苗　追加ここまで   
	//---------------------------------------------------------------------
	// Static initialize
	//---------------------------------------------------------------------

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------


	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------
	/**
	 * コンストラクタ
	 */
	public JigyoKubunFilter(){

	}


	//---------------------------------------------------------------------
	// Private Methods
	//---------------------------------------------------------------------
	/**
	 * 担当事業区分のうち審査対象事業区分のみを返す。<br>
	 * 審査対象分の事業区分は以下のとおり。<br>
	 * <li>1：学術創成研究費（非公募）</li>
	 * <li>4：基盤研究</li>
	 * <li>5：特定領域</li>
	 * 担当事業区分に含まれているものの中に、審査対象の事業区分が無かった場合は、
	 * 空のSetオブジェクトが返る。<br>
	 * @param  userInfo
	 * @return 審査対象事業区分
	 */
	public static Set getShinsaTaishoJigyoKubun(UserInfo userInfo)
	{
		//審査対象事業区分のSetオブジェクトを新規に作成
		//※新規に作成しないと、元のSetオブジェクトを更新してしまうため注意。
		Set shinsaTaishoSet = new HashSet();

		//業務担当者ならば自分が担当する事業区分のみ
		if(userInfo.getRole().equals(UserRole.GYOMUTANTO)){
			GyomutantoInfo gyomutantoInfo = userInfo.getGyomutantoInfo(); 		
					
			//担当事業区分のうち、審査担当分の事業区分を返す
			for(int i = 0; i < SHINSA_TAISHO_JIGYO_KUBUN.length;i++){
				if(gyomutantoInfo.getTantoJigyoKubun().contains(SHINSA_TAISHO_JIGYO_KUBUN[i])){
					//審査対象事業区分のSetオブジェクトに追加
					shinsaTaishoSet.add(SHINSA_TAISHO_JIGYO_KUBUN[i]);			
				}			 
			}
		}else{
			//それ以外の担当者の場合は、審査対象事業区分をそのまま返す。		
			for(int i = 0; i < SHINSA_TAISHO_JIGYO_KUBUN.length;i++){
				//審査対象事業区分のSetオブジェクトに追加
				shinsaTaishoSet.add(SHINSA_TAISHO_JIGYO_KUBUN[i]);						 
			}		
		}
		
		return shinsaTaishoSet;
	}
    
    
//2006/04/24 追加ここから    
    /**
     * 審査員の事業区分から申請書の事業区分のみを返す。<br>
     * 審査員の事業区分の事業区分は以下のとおり。<br>
     * <li>1：学術創成研究費（非公募）</li>
     * <li>4：基盤研究、若手スタートアップ</li>
     * 審査対象事業区分に含まれているものの中に、申請書の事業区分が無かった場合は、
     * 空のSetオブジェクトが返る。<br>
     * @param  shinsainJigyoKubun
     * @return 申請書の事業区分
     */
    public static Set Convert2ShinseishoJigyoKubun(String shinsainJigyoKubun){
        
        //申請書の事業区分
        Set shinseishoJigyoKubunSet = new HashSet();
        
        //審査員の事業区分は：学術創成（非公募）の場合
        if(shinsainJigyoKubun.equals(IJigyoKubun.JIGYO_KUBUN_GAKUSOU_HIKOUBO)){
            //申請書の事業区分：学術創成（非公募）を設定する
            shinseishoJigyoKubunSet.add(IJigyoKubun.JIGYO_KUBUN_GAKUSOU_HIKOUBO);
        //審査員の事業区分は：基盤研究の場合
        } else if (shinsainJigyoKubun.equals(IJigyoKubun.JIGYO_KUBUN_KIBAN)){
            //申請書の事業区分：基盤研究を設定する
            shinseishoJigyoKubunSet.add(IJigyoKubun.JIGYO_KUBUN_KIBAN);
            //申請書の事業区分：若手スタートアップを設定する
            shinseishoJigyoKubunSet.add(IJigyoKubun.JIGYO_KUBUN_WAKATESTART);
            //申請書の事業区分：特別研究促進費を設定する
            shinseishoJigyoKubunSet.add(IJigyoKubun.JIGYO_KUBUN_SHOKUSHINHI);
        //その他の場合    
        } else {
            shinseishoJigyoKubunSet = null;
        }
        
        return shinseishoJigyoKubunSet;
    }
    
    
    /**
     * 申請書の事業区分から審査員の事業区分のみを返す。<br>
     * 申請書の事業区分の事業区分は以下のとおり。<br>
     * <li>1：学術創成研究費（非公募）</li>
     * <li>4：基盤研究</li>
     * <li>6：若手スタートアップ</li>
     * <li>7：特別研究促進費</li>
     * 審査対象事業区分に含まれているものの中に、申請書の事業区分が無かった場合は、
     * 空のSetオブジェクトが返る。<br>
     * @param  shinseishoJigyoKubun
     * @return 審査員の事業区分
     */
    public static Set Convert2ShinsainJigyoKubun(String shinseishoJigyoKubun) {
        
        //審査員の事業区分
        Set shinsainJigyoKubunSet = new HashSet();
        
        //申請書の事業区分は：学術創成（非公募）の場合
        if(shinseishoJigyoKubun.equals(IJigyoKubun.JIGYO_KUBUN_GAKUSOU_HIKOUBO)){
            //審査員の事業区分：学術創成（非公募）を設定する
            shinsainJigyoKubunSet.add(IJigyoKubun.JIGYO_KUBUN_GAKUSOU_HIKOUBO);
        //申請書の事業区分は：基盤研究の場合   
        } else if (shinseishoJigyoKubun.equals(IJigyoKubun.JIGYO_KUBUN_KIBAN)){
            //審査員の事業区分：基盤研究を設定する
            shinsainJigyoKubunSet.add(IJigyoKubun.JIGYO_KUBUN_KIBAN);
        //申請書の事業区分は：若手スタートアップの場合  
        } else if (shinseishoJigyoKubun.equals(IJigyoKubun.JIGYO_KUBUN_WAKATESTART)){
            //審査員の事業区分：基盤研究を設定する
            shinsainJigyoKubunSet.add(IJigyoKubun.JIGYO_KUBUN_KIBAN);
        //申請書の事業区分は：特別研究促進費の場合    
        } else if (shinseishoJigyoKubun.equals(IJigyoKubun.JIGYO_KUBUN_SHOKUSHINHI)){
            //審査員の事業区分：基盤研究を設定する
            shinsainJigyoKubunSet.add(IJigyoKubun.JIGYO_KUBUN_KIBAN);
        //その他の場合    
        } else {
            shinsainJigyoKubunSet = null;
        }
        
        return shinsainJigyoKubunSet;
    }
//苗　追加ここまで    
    
//2006/05/10 追加ここから
    /**
     * 承認タイプの事業区分を取得する。<br>
     * 承認タイプの事業区分は以下のとおり。<br>
     * <li>1：学術創成研究費（非公募）</li>
     * <li>2：学術創成（公募）</li>
     * <li>3：特別推進研究</li>
     * @return 承認タイプの事業区分格納
     */
    public static Set getShoninTaishoJigyoKubun(){
        //承認タイプの事業区分格納の宣言
        Set shoninTaishoSet = new HashSet();
        
        //事業区分:学術創成（非公募）
        shoninTaishoSet.add(IJigyoKubun.JIGYO_KUBUN_GAKUSOU_HIKOUBO);
        //事業区分:学術創成（公募）
        shoninTaishoSet.add(IJigyoKubun.JIGYO_KUBUN_GAKUSOU_KOUBO);
        //事業区分:特別推進研究
        shoninTaishoSet.add(IJigyoKubun.JIGYO_KUBUN_TOKUSUI);
        
        return shoninTaishoSet;
    }
    
    /**
     * チェックリストタイプの事業区分を取得する。<br>
     * チェックリストタイプの事業区分は以下のとおり。<br>
     * <li>4：基盤研究</li>
     * <li>5：特定領域研究</li>
     * <li>6：若手スタートアップ</li>
     * <li>7：特別研究促進費</li>
     * @return チェックリストタイプの事業区分格納
     */
    public static Set getCheckListTaishoJigyoKubun (){
        //チェックリストタイプの事業区分格納の宣言
        Set checklistTaishoSet = new HashSet();
        
//      2006/06/29 ZJP 削除ここから 
//        //事業区分:基盤研究
//        checklistTaishoSet.add(IJigyoKubun.JIGYO_KUBUN_KIBAN);
//        //事業区分:特定領域研究
//        checklistTaishoSet.add(IJigyoKubun.JIGYO_KUBUN_TOKUTEI);
//      ZJP　削除ここまで         
        
        //事業区分:若手スタートアップ
        checklistTaishoSet.add(IJigyoKubun.JIGYO_KUBUN_WAKATESTART);
        //事業区分:特別研究促進費
        checklistTaishoSet.add(IJigyoKubun.JIGYO_KUBUN_SHOKUSHINHI);
        
        return checklistTaishoSet;
    }
//苗　追加ここまで    
    
//  2006/06/29 ZJP 追加ここから 
    /**
     * 承認タイプの事業CDを取得する。<br>
     * 承認タイプの事業CDは以下のとおり。<br>
     * <li>00521：学術創成（非公募）</li>
     * <li>00522：学術創成（公募）</li>
     * <li>00011：特別推進研究</li>
     * <li>00031：基盤研究(S)</li>
     * <li>00041：基盤研究(A)(一般)</li>
     * <li>00051：基盤研究(B)(一般)</li>
     * <li>00043：基盤研究(A)(海外学術調査)</li>
     * <li>00053：基盤研究(B)(海外学術調査)</li>
     * @return 承認タイプの事業CD格納
     */
    public static Set getShoninTaishoJigyoCd(){
        //承認タイプの事業CD格納の宣言
        Set shoninTaishoSet = new HashSet();
        //学術創成（非公募）
        shoninTaishoSet.add(IJigyoCd.JIGYO_CD_GAKUSOU_HIKOUBO);
        //学術創成（公募）
        shoninTaishoSet.add(IJigyoCd.JIGYO_CD_GAKUSOU_KOUBO);
        //特別推進研究
        shoninTaishoSet.add(IJigyoCd.JIGYO_CD_TOKUSUI);
        //基盤研究(S)
        shoninTaishoSet.add(IJigyoCd.JIGYO_CD_KIBAN_S);
        //基盤研究(A)(一般)
        shoninTaishoSet.add(IJigyoCd.JIGYO_CD_KIBAN_A_IPPAN);
        //基盤研究(B)(一般)
        shoninTaishoSet.add(IJigyoCd.JIGYO_CD_KIBAN_B_IPPAN);
        //基盤研究(A)(海外学術調査)
        shoninTaishoSet.add(IJigyoCd.JIGYO_CD_KIBAN_A_KAIGAI);
        //基盤研究(B)(海外学術調査)
        shoninTaishoSet.add(IJigyoCd.JIGYO_CD_KIBAN_B_KAIGAI);
//2007/03/13 劉長宇　追加　ここから
        //若手研究(S)
        shoninTaishoSet.add(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_S);
        //若手研究(スタートアップ)
        shoninTaishoSet.add(IJigyoCd.JIGYO_CD_WAKATESTART);
        //特別研究促進費（基盤研究(A)相当）
        shoninTaishoSet.add(IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_A);
        //特別研究促進費（基盤研究(B)相当）
        shoninTaishoSet.add(IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_B);
        //特別研究促進費（基盤研究(C)相当）
        shoninTaishoSet.add(IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_C);
        //特別研究促進費（若手研究(A)相当）
        shoninTaishoSet.add(IJigyoCd.JIGYO_CD_SHOKUSHINHI_WAKATE_A);
        //特別研究促進費（若手研究(B)相当）
        shoninTaishoSet.add(IJigyoCd.JIGYO_CD_SHOKUSHINHI_WAKATE_B);
//2007/03/13 劉長宇　追加　ここまで
        
        return shoninTaishoSet;
    }
    
    /**
     * チェックリストタイプの事業CDを取得する。<br>
     * チェックリストタイプの事業CDは以下のとおり。<br>
     * <li>00061：基盤研究(C)(一般)</li>
     * <li>00062：基盤研究(C)(企画調査)</li>
     * <li>00021：特定領域（継続領域）</li>
     * <li>00111：萌芽研究</li>
     * <li>00121：若手研究(A)</li>
     * <li>00131：若手研究(B)</li>
     * <li>00120：若手研究(S)</li>
     * <li>00141：若手研究(スタートアップ)</li>
     * <li>00152：特別研究促進費（基盤研究(A)相当）</li>
     * <li>00153：特別研究促進費（基盤研究(B)相当）</li>
     * <li>00154：特別研究促進費（基盤研究(C)相当）</li>
     * <li>00155：特別研究促進費（若手研究(A)相当）</li>
     * <li>00156：特別研究促進費（若手研究(B)相当）</li>
     * @return チェックリストタイプの事業CD格納
     */
    public static Set getCheckListTaishoJigyoCd(){
        //承認タイプの事業CD格納の宣言
        Set shoninTaishoSet = new HashSet();
        //基盤研究(C)(一般)
        shoninTaishoSet.add(IJigyoCd.JIGYO_CD_KIBAN_C_IPPAN);
        //基盤研究(C)(企画調査)
        shoninTaishoSet.add(IJigyoCd.JIGYO_CD_KIBAN_C_KIKAKU);
        //特定領域（継続領域）
        shoninTaishoSet.add(IJigyoCd.JIGYO_CD_TOKUTEI_KEIZOKU);
        //萌芽研究
        shoninTaishoSet.add(IJigyoCd.JIGYO_CD_KIBAN_HOGA);
        //若手研究(A)
        shoninTaishoSet.add(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_A);
        //若手研究(B)
        shoninTaishoSet.add(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_B);
//        //若手研究(スタートアップ)
//        shoninTaishoSet.add(IJigyoCd.JIGYO_CD_WAKATESTART);
//        //特別研究促進費（基盤研究(A)相当）
//        shoninTaishoSet.add(IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_A);
//        //特別研究促進費（基盤研究(B)相当）
//        shoninTaishoSet.add(IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_B);
//        //特別研究促進費（基盤研究(C)相当）
//        shoninTaishoSet.add(IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_C);
//        //特別研究促進費（若手研究(A)相当）
//        shoninTaishoSet.add(IJigyoCd.JIGYO_CD_SHOKUSHINHI_WAKATE_A);
//        //特別研究促進費（若手研究(B)相当）
//        shoninTaishoSet.add(IJigyoCd.JIGYO_CD_SHOKUSHINHI_WAKATE_B);
       
        return shoninTaishoSet;
    }

    /**
     * チェックリストタイプの事業CDを取得する。<br>
     * チェックリストタイプの事業CDは以下のとおり。<br>
     * <li>00061：基盤研究(C)(一般)</li>
     * <li>00062：基盤研究(C)(企画調査)</li>
     * <li>00021：萌芽研究</li>
     * <li>00061：若手研究(A)</li>
     * <li>00061：若手研究(B)</li>
     * <li>00021：特定領域（継続領域）</li>
     * <li>00141：若手研究（スタートアップ）</li>
     * <li>00152：特別研究促進費（基盤研究(A)相当）</li>
     * <li>00153：特別研究促進費（基盤研究(B)相当）</li>
     * <li>00154：特別研究促進費（基盤研究(C)相当）</li>
     * <li>00155：特別研究促進費（若手研究(A)相当）</li>
     * <li>00156：特別研究促進費（若手研究(B)相当）</li>
     * @return チェックリストタイプの事業CD格納
     */
    public static Set getCheckListKakuteiJigyoCd(){
        // 確定タイプの事業CD格納の宣言
        Set kakuteiTypeSet = new HashSet();
        // 基盤研究(C)(一般)
        kakuteiTypeSet.add(IJigyoCd.JIGYO_CD_KIBAN_C_IPPAN);
        // 基盤研究(C)(企画調査)
        kakuteiTypeSet.add(IJigyoCd.JIGYO_CD_KIBAN_C_KIKAKU);
        // 萌芽研究
        kakuteiTypeSet.add(IJigyoCd.JIGYO_CD_KIBAN_HOGA);
        // 若手研究(A)
        kakuteiTypeSet.add(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_A);
        // 若手研究(B)
        kakuteiTypeSet.add(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_B);
        // 特定領域（継続領域）
        kakuteiTypeSet.add(IJigyoCd.JIGYO_CD_TOKUTEI_KEIZOKU);
// 2006/08/21 dyh update start
//        //特別推進研究
//        kakuteiTypeSet.add(IJigyoCd.JIGYO_CD_TOKUSUI);
//2007/03/13 劉長宇　削除　ここから
//        // 若手研究（スタートアップ）
//        kakuteiTypeSet.add(IJigyoCd.JIGYO_CD_WAKATESTART);
//        // 特別研究促進費（基盤研究(A)相当）
//        kakuteiTypeSet.add(IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_A);
//        // 特別研究促進費（基盤研究(B)相当）
//        kakuteiTypeSet.add(IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_B);
//        // 特別研究促進費（基盤研究(C)相当）
//        kakuteiTypeSet.add(IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_C);
//        // 特別研究促進費（若手研究(A)相当）
//        kakuteiTypeSet.add(IJigyoCd.JIGYO_CD_SHOKUSHINHI_WAKATE_A);
//        // 特別研究促進費（若手研究(B)相当）
//        kakuteiTypeSet.add(IJigyoCd.JIGYO_CD_SHOKUSHINHI_WAKATE_B);
//2007/03/13 劉長宇　削除　ここまで
// 2006/08/21 dyh update start

        return kakuteiTypeSet;
    }
//  ZJP　追加ここまで 
}