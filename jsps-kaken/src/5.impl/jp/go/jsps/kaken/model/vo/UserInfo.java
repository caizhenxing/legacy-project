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
 * ログオンしたユーザ情報を保持するクラス。
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
	
	/** 規定値のユーザ情報 */
	public static final UserInfo SYSTEM_USER = new UserInfo();

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------
	
	/** 業務担当者情報 */
	private GyomutantoInfo gyomutantoInfo;
	
	/** 審査員情報 */
	private ShinsainInfo shinsainInfo;

	/** 所属機関担当書情報 */
	private ShozokuInfo shozokuInfo;
	
	/** 申請者情報 */
	private ShinseishaInfo shinseishaInfo;
	
	/** ロール情報 */
	private UserRole role;
	
	/* 2005/03/24 追加 ここから---------------------------
	 * 理由 「部局担当者情報」追加のため */
	
	/** 部局担当者情報 */
	private BukyokutantoInfo bukyokutantoInfo;
	
	/* 追加 ここまで-------------------------------------- */
	
/* 2005/10/27 追加 ここから */
    /* アンケート情報 */
    private QuestionInfo questionInfo;
    
/* 追加 ここまで */
	
	//...など
	
	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * コンストラクタ。
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

	/* 2005/03/24 追加 ここから---------------------------
	 * 理由 「部局担当者情報」追加のため */
	
	/**
	 * @return bukyokutantoInfo を戻します。
	 */
	public BukyokutantoInfo getBukyokutantoInfo() {
		return bukyokutantoInfo;
	}
	/**
	 * @param bukyokutantoInfo bukyokutantoInfo を設定。
	 */
	public void setBukyokutantoInfo(BukyokutantoInfo bukyokutantoInfo) {
		this.bukyokutantoInfo = bukyokutantoInfo;
	}
	
	/* 追加 ここまで-------------------------------------- */
	
	
	
	/**
	 * 当該RoleのログインIDを返す。
	 * Roleの種別が判断できない場合はnullが返る。
	 * @return ログインID
	 */
	public String getId(){
		//ログインユーザのIDを取得する。
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
		
		//2005.10.06 iso 申請者の場合を追加
		}else if(UserRole.SHINSEISHA.equals(getRole())){
			id = getShinseishaInfo().getShinseishaId();
		
		}else{
			id = null;
		}
		return id;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
