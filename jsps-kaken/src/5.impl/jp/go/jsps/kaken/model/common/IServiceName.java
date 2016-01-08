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
package jp.go.jsps.kaken.model.common;

/**
 * �T�[�r�X����萔����B
 * 
 * ID RCSfile="$RCSfile: IServiceName.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:30 $"
 */
public interface IServiceName {
	
	/** �T�[�r�X�����Q�Ɩ� */
	public static final String APPLICATION_SERVICE = "APPLICATION.SERVICE";

	/** �Ɩ��S���҃��O�I���T�[�r�X */
	public static final String GYOMUTANTO_LOGON_SERVICE = "gyomutanto.logon.service";

	/** �\���҃��O�I���T�[�r�X */
	public static final String SHINSEISHA_LOGON_SERVICE = "shinseisha.logon.service";
 	
	/** �\���ҏ��Ǘ��T�[�r�X */
	public static final String SHINSEISHA_MAINTENANCE_SERVICE = "shinseisha.maintenance.service";
	
	/** �����@�֏��Ǘ��T�[�r�X */
	public static final String SHOZOKU_MAINTENANCE_SERVICE = "shozoku.maintenance.service";
	
	/** ���Ə��Ǘ��T�[�r�X */
	public static final String JIGYOKANRI_MAINTENANCE_SERVICE = "jigyoKanri.maintenance.service";

	/** �Ɩ��S���ҏ��Ǘ��T�[�r�X */
	public static final String GYOMUTANTO_MAINTENANCE_SERVICE = "gyomutanto.maintenance.service";

	/** PDF�ϊ��T�[�r�X */
	public static final String CONVERT_SERVICE = "convert.service";
	
	/** �\�������Ǘ��T�[�r�X */
	public static final String SHINSEI_MAINTENANCE_SERVICE = "shinsei.maintenance.service";

	/** �eID�E�p�X���[�h�̔��s���[���Ǘ��T�[�r�X */
	public static final String RULE_MAINTENANCE_SERVICE = "rule.maintenance.service";
	
	/** �R�������Ǘ��T�[�r�X */
	public static final String SHINSAIN_MAINTENANCE_SERVICE = "shinsain.maintenance.service";
	
	/** �R��������U��T�[�r�X */
	public static final String SHINSAIN_WARIFURI_SERVICE = "shinsain.warifuri.service";
	
	/** �R�����ʃT�[�r�X */
	public static final String SHINSAKEKKA_MAINTENANCE_SERVICE = "shinsakekka.maintenance.service";
	
	/** �R��������U�胁�C������ */
	public static final String WARIFURI_EXECUTE_SERVICE = "warifuri.execute.service";
	
	/** �V�X�e���Ǘ��҃T�[�r�X */
	public static final String SYSTEM_MAINTENANCE_SERVICE = "system.maintenance.service";

	/** �R���󋵊m�F�T�[�r�X */
	public static final String SHINSAJOKYO_KAKUNIN_SERVICE = "shinsajokyo.kakunin.service";
	
	/** �f�[�^�ۊǃT�[�r�X */
	public static final String DATA_HOKAN_MAINTENANCE_SERVICE = "data.hokan.maintenance.service";
	
	/** ���x���Ǘ��T�[�r�X */
	public static final String LABEL_VALUE_MAINTENANCE_SERVICE = "label.value.maintenance.service";

	/** �w�p�V�X�e�������Z���^���Ǘ��T�[�r�X */
	public static final String GAKUJUTU_MAINTENANCE_SERVICE = "gakujutu.maintenance.service";
	
	/** �R�[�h�\�\���T�[�r�X */
	public static final String CODE_MAINTENANCE_SERVICE = "code.maintenance.service";	
	
	/** �֘A���쌤���ҏ��Ǘ��T�[�r�X */
	public static final String KANRENBUNYA_MAINTENANCE_SERVICE = "kanrenbunya.maintenance.service";	
	
	/**�p���`�f�[�^�T�[�r�X */
	public static final String PUNCHDATA_MAINTENANCE_SERVICE = "punchdata.maintenance.service";
	
// 2005/03/25 �ǉ� ��������--------------------------------------------
// ���R ���ǒS���ҏ��̒ǉ��ɂ��
	
	/**���ǒS���ҏ��Ǘ��T�[�r�X */
	public static final String BUKYOKUTANTO_MAINTENANCE_SERVICE = "bukyokutanto.maintenance.service";
	
// �ǉ� �����܂�-------------------------------------------------------
	
// 2005/03/28 �ǉ� ��������--------------------------------------------
// ���R �����҃}�X�^���̒ǉ��ɂ��
	
	/**�����ҏ��Ǘ��T�[�r�X */
	public static final String KENKYUSHA_MAINTENANCE_SERVICE = "kenkyusha.maintenance.service";
	
// �ǉ� �����܂�-------------------------------------------------------

// 2005/03/30 �ǉ� ��������--------------------------------------------
// ���R �`�F�b�N���X�g���̒ǉ��ɂ��
   
   /**�`�F�b�N���X�g�Ǘ��T�[�r�X */
   public static final String CHECKLIST_MAINTENANCE_SERVICE = "checklist.maintenance.service";
	
//�ǉ� �����܂�-------------------------------------------------------

//2005/05/20 �ǉ�
	/** ���ӌ����Ǘ��T�[�r�X */
	public static final String IKEN_MAINTENANCE_SERVICE = "iken.maintenance.service";
	
//2005/10/26 �ǉ� ��������------------------------------------------------
    /** �A���P�[�g���Ǘ��T�[�r�X */
    public static final String QUESTION_MAINTENANCE_SERVICE = "question.maintenance.service";

//�ǉ� �����܂�-----------------------------------------------------------
//2006/06/14 by jzx add start   
    /** �̈�v�揑�i�T�v�j���Ǘ� */
    public static final String TEISHUTU_MAINTENANCE_SERVICE = "teishutu.maintenance.service";
//2006/06/14 by jzx add end  
}
