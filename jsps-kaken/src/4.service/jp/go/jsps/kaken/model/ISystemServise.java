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
 * �I�����C���\���V�X�e���̃r�W�l�X�C���^�[�t�F�[�X���`����C���^�[�t�F�[�X�B
 *�@�N���C�A���g����Ă΂�郁�\�b�h���`����B
 * 
 * ID RCSfile="$RCSfile: ISystemServise.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:50 $"
 */

// 2005/03/25 �ǉ� �uIBukyokutantoMaintenance�v�i���ǒS���ҏ��Ǘ��j
// 2005/03/28 �ǉ� �uIKenkyushaMaintenance�v�i�����ҏ��Ǘ��j
// 2005/03/31 �ǉ� �uICheckListMaintenane�v(�`�F�b�N���X�g�Ǘ�)
// 2005/05/23 �ǉ� �uIIkeninfoMaintenance�v(�ӌ����Ǘ�)
// 2005/10/27 �ǉ� �uIQuestionMaintenance�v(�A���P�[�g���Ǘ�)
// 2006/06/14 �ǉ� �uITeishutuShoruiMaintenance�v(�̈�v�揑�i�T�v�j���Ǘ�)
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
