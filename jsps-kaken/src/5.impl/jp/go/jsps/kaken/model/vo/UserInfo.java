/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2003/07/16
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.model.vo;

import java.io.Serializable;

import jp.go.jsps.kaken.model.role.UserRole;

/**
 * ���O�I���������[�U����ێ�����N���X�B
 * 
 * ID RCSfile="$RCSfile: UserInfo.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:14 $"
 */
public class UserInfo implements Serializable{

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------
	
	//---------------------------------------------------------------------
	// static data
	//---------------------------------------------------------------------
	
	/** �K��l�̃��[�U��� */
	public static final UserInfo SYSTEM_USER = new UserInfo();

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------
	
	/** �Ɩ��S���ҏ�� */
	private GyomutantoInfo gyomutantoInfo;
	
	/** �R������� */
	private ShinsainInfo shinsainInfo;

	/** �����@�֒S������� */
	private ShozokuInfo shozokuInfo;
	
	/** �\���ҏ�� */
	private ShinseishaInfo shinseishaInfo;
	
	/** ���[����� */
	private UserRole role;
	
	/* 2005/03/24 �ǉ� ��������---------------------------
	 * ���R �u���ǒS���ҏ��v�ǉ��̂��� */
	
	/** ���ǒS���ҏ�� */
	private BukyokutantoInfo bukyokutantoInfo;
	
	/* �ǉ� �����܂�-------------------------------------- */
	
/* 2005/10/27 �ǉ� �������� */
    /* �A���P�[�g��� */
    private QuestionInfo questionInfo;
    
/* �ǉ� �����܂� */
	
	//...�Ȃ�
	
	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * �R���X�g���N�^�B
	 */
	public UserInfo() {
		super();
	}

	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------

	/**
	 * @return
	 */
	public GyomutantoInfo getGyomutantoInfo() {
		return gyomutantoInfo;
	}

	/**
	 * @param info
	 */
	public void setGyomutantoInfo(GyomutantoInfo info) {
		gyomutantoInfo = info;
	}

	/**
	 * @return
	 */
	public ShinsainInfo getShinsainInfo() {
		return shinsainInfo;
	}

	/**
	 * @param info
	 */
	public void setShinsainInfo(ShinsainInfo info) {
		shinsainInfo = info;
	}

	/**
	 * @return
	 */
	public ShozokuInfo getShozokuInfo() {
		return shozokuInfo;
	}

	/**
	 * @param info
	 */
	public void setShozokuInfo(ShozokuInfo info) {
		shozokuInfo = info;
	}

	/**
	 * @return
	 */
	public ShinseishaInfo getShinseishaInfo() {
		return shinseishaInfo;
	}

	/**
	 * @param info
	 */
	public void setShinseishaInfo(ShinseishaInfo info) {
		shinseishaInfo = info;
	}


	/**
	 * @return
	 */
	public UserRole getRole() {
		return role;
	}

	/**
	 * @param role
	 */
	public void setRole(UserRole role) {
		this.role = role;
	}

	/* 2005/03/24 �ǉ� ��������---------------------------
	 * ���R �u���ǒS���ҏ��v�ǉ��̂��� */
	
	/**
	 * @return bukyokutantoInfo ��߂��܂��B
	 */
	public BukyokutantoInfo getBukyokutantoInfo() {
		return bukyokutantoInfo;
	}
	/**
	 * @param bukyokutantoInfo bukyokutantoInfo ��ݒ�B
	 */
	public void setBukyokutantoInfo(BukyokutantoInfo bukyokutantoInfo) {
		this.bukyokutantoInfo = bukyokutantoInfo;
	}
	
	/* �ǉ� �����܂�-------------------------------------- */
	
	
	
	/**
	 * ���YRole�̃��O�C��ID��Ԃ��B
	 * Role�̎�ʂ����f�ł��Ȃ��ꍇ��null���Ԃ�B
	 * @return ���O�C��ID
	 */
	public String getId(){
		//���O�C�����[�U��ID���擾����B
		String id = null;
		if(UserRole.SHOZOKUTANTO.equals(getRole())){
			id = getShozokuInfo().getShozokuTantoId();
		}else if(UserRole.BUKYOKUTANTO.equals(getRole())){
			id = getBukyokutantoInfo().getBukyokutantoId();
		}else if(UserRole.GYOMUTANTO.equals(getRole())){
			id = getGyomutantoInfo().getGyomutantoId();
		}else if(UserRole.SYSTEM.equals(getRole())){
			id = getGyomutantoInfo().getGyomutantoId();
		}else if(UserRole.SHINSAIN.equals(getRole())){
			id = getShinsainInfo().getShinsainId();
		
		//2005.10.06 iso �\���҂̏ꍇ��ǉ�
		}else if(UserRole.SHINSEISHA.equals(getRole())){
			id = getShinseishaInfo().getShinseishaId();
		
		}else{
			id = null;
		}
		return id;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
