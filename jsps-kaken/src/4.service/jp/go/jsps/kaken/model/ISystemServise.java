/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2003/11/12
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.model;




/**
 * オンライン申請システムのビジネスインターフェースを定義するインターフェース。
 *　クライアントから呼ばれるメソッドを定義する。
 * 
 * ID RCSfile="$RCSfile: ISystemServise.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:50 $"
 */

// 2005/03/25 追加 「IBukyokutantoMaintenance」（部局担当者情報管理）
// 2005/03/28 追加 「IKenkyushaMaintenance」（研究者情報管理）
// 2005/03/31 追加 「ICheckListMaintenane」(チェックリスト管理)
// 2005/05/23 追加 「IIkeninfoMaintenance」(意見情報管理)
// 2005/10/27 追加 「IQuestionMaintenance」(アンケート情報管理)
// 2006/06/14 追加 「ITeishutuShoruiMaintenance」(領域計画書（概要）情報管理)
public interface ISystemServise 
	extends IAuthentication, 
	         IShinseishaMaintenance,
	         IShozokuMaintenance,
			 IGyomutantoMaintenance,
	         IShinseiMaintenance,
	         IShinsainMaintenance,
	         IJigyoKanriMaintenance,
	         IConverter,
			 IRuleMaintenance,
			 IShinsainWarifuri,
			 IShinsaKekkaMaintenance,
			 IShinsaJokyoKakunin,
			 ISystemMaintenance,
			 IDataHokanMaintenance,
			 ILabelValueMaintenance,
			 ICodeMaintenance,
			 IKanrenBunyaMaintenance,
			 IPunchDataMaintenance,
			 IBukyokutantoMaintenance,
			 IKenkyushaMaintenance,
			 ICheckListMaintenance,
			 IIkeninfoMaintenance,
             IQuestionMaintenance,
             //2006/06/14 by jzx add start
             ITeishutuShoruiMaintenance
             //2006/06/14 by jzx add start
             

{

	

}
