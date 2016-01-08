/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2003/12/22
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.struts;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.exceptions.SystemException;
import jp.go.jsps.kaken.model.role.UserRole;
import jp.go.jsps.kaken.model.vo.BukyokutantoInfo;
import jp.go.jsps.kaken.model.vo.GyomutantoInfo;
import jp.go.jsps.kaken.model.vo.ShinsainInfo;
import jp.go.jsps.kaken.model.vo.ShinseishaInfo;
import jp.go.jsps.kaken.model.vo.ShozokuInfo;
import jp.go.jsps.kaken.model.vo.UserInfo;
import jp.go.jsps.kaken.web.common.IConstants;
import jp.go.jsps.kaken.web.common.UserContainer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.config.ModuleConfig;

/**
 * 拡張版リクエストプロセッサクラス。
 * ID RCSfile="$RCSfile: RequestProcessor.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:28 $"
 */
public class RequestProcessor extends org.apache.struts.action.RequestProcessor {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/**
	 * ログクラス。 
	 */
	protected static final Log log = LogFactory.getLog(RequestProcessor.class);

    /**
     * 監査ログクラス。 
     */
    protected static final Log auditLog = LogFactory.getLog("audit");


	/* (非 Javadoc)
	 * @see org.apache.struts.action.RequestProcessor#processActionPerform(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.apache.struts.action.Action, org.apache.struts.action.ActionForm, org.apache.struts.action.ActionMapping)
	 */
	protected ActionForward processActionPerform(
		HttpServletRequest request,
		HttpServletResponse response,
		Action action,
		ActionForm form,
		org.apache.struts.action.ActionMapping mapping)
		throws IOException, ServletException {

		//---------------------------------	
		//アクション処理中のエラーも取得するように変更
		//----------------------------------
		try {
			return super.processActionPerform(
				request,
				response,
				action,
				form,
				mapping);
		} catch (Error error) {
			log.error("アクション'" + mapping.getPath() + "'実行中にエラーが発生しました。", error);
			return processException(
				request,
				response,
				new Exception(error),
				form,
				mapping);
		}
	}

	/* (非 Javadoc)
	 * @see org.apache.struts.action.RequestProcessor#processRoles(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.apache.struts.action.ActionMapping)
	 */
	protected boolean processRoles(
		HttpServletRequest request,
		HttpServletResponse response,
		org.apache.struts.action.ActionMapping mapping)
		throws IOException, ServletException {
		
        if (mapping instanceof ActionMapping) {
			ActionMapping actionMapping = (ActionMapping) mapping;

			//----------------------------------
			//エラー時閉じるボタンを表示するかを判断する。(セッションエラーでも閉じるボタンを有効にするため)
			//----------------------------------
			if (actionMapping.isErrorClose()) {
				request.setAttribute("ERROR_CLOSE", Boolean.TRUE);
			}

			//----------------------------------
			//ログオンチェックを行うか判断し、ログインチェックする。
			//----------------------------------

			if (actionMapping.isLogonCheck()) {
				
				if (log.isDebugEnabled()){
					log.debug("★　Do Check Login !!!");
				}
								
				//----------------------------------
				//エラー時の遷移先の決定
				//----------------------------------
				ActionForward forward = mapping.findForward("session");

				//----------------------------------
				//ユーザ情報が存在しない場合はセッションエラー
				//----------------------------------
				UserContainer userContainer = (UserContainer) request.getSession().getAttribute(IConstants.USER_CONTAINER_KEY);
				if (userContainer == null || userContainer.getUserInfo() == null) {
				  	processForwardConfig(request, response, forward);
					return false;
				}

				//----------------------------------
				//モジュール名とログインユーザロールのチェック
				//----------------------------------
				ModuleConfig moduleConfig = mapping.getModuleConfig();
				//申請者・審査員といったモジュールが取得できない場合はチェックしない。
				if (moduleConfig.getPrefix() != null) {
					//モジュール名より該当ロール情報の取得
					UserRole role = null;
					//20005.11.07 iso アンケート機能用に追加
					if(moduleConfig.getPrefix().indexOf("/question") == 0){
						role = UserRole.getEnum(moduleConfig.getPrefix());
					} else if(moduleConfig.getPrefix().indexOf("/shinsei") > 0){
						role = UserRole.getEnum("/shinsei");
					} else if (moduleConfig.getPrefix().indexOf("/shinsei-") == 0){
						role = UserRole.getEnum("/shinsei");
					} else{
						role = UserRole.getEnum(moduleConfig.getPrefix());
					}
					
					if (!role.equals(userContainer.getUserInfo().getRole())) {
						//ユーザ情報が一致しない場合はセッションエラー
						processForwardConfig(request, response, forward);
                        return false;
					}
				}
                
                //----------------------------------
                //監査ログ書き込み
                //----------------------------------
                UserInfo userInfo = userContainer.getUserInfo();
                StringBuffer buffer = new StringBuffer();
                buffer.append("Id::'").append(getUserId(userInfo)).append("',");
                buffer.append("Role::'").append(userInfo.getRole().getName()).append("',");
                buffer.append("Process::'").append(actionMapping.getDescription()).append("',");
                buffer.append("User-Agent::'").append(request.getHeader("User-Agent")).append("'");	//User-Agent情報
                auditLog.info(buffer);
                
			} else {
				if (log.isDebugEnabled())
					log.debug("★　Do'nt Check Login !!!");
			}
		} else {
			log.debug("Do'nt LogonCheckActionMapping!!");
		}
		return super.processRoles(request, response, mapping);
	}
    
    /**
     * ユーザIDを取得する。
     * @param userInfo
     * @return
     */
    private String getUserId(UserInfo userInfo){
        if(userInfo.getRole().equals(UserRole.SHINSEISHA)){
            //申請者のとき
            ShinseishaInfo info = userInfo.getShinseishaInfo();
            return info.getShinseishaId();
        }else if(userInfo.getRole().equals(UserRole.SHOZOKUTANTO)){
            //所属機関担当者のとき
            ShozokuInfo info = userInfo.getShozokuInfo();    
            return info.getShozokuTantoId();
            
/* 2005/03/25 追加 ここから---------------------------------------
 * 理由 部局担当者の追加による */
            
        }else if(userInfo.getRole().equals(UserRole.BUKYOKUTANTO)){
            //部局担当者のとき
            BukyokutantoInfo info = userInfo.getBukyokutantoInfo();    
            return info.getBukyokutantoId();
            
/* 追加 ここまで-------------------------------------------------- */
            
        }else if(userInfo.getRole().equals(UserRole.SHINSAIN)){
            //審査員のとき
            ShinsainInfo info = userInfo.getShinsainInfo();    
            return info.getShinsainId();
        }else if(userInfo.getRole().equals(UserRole.GYOMUTANTO)||userInfo.getRole().equals(UserRole.SYSTEM)){
            //業務担当者・システム管理者のとき
            GyomutantoInfo info = userInfo.getGyomutantoInfo();  
            return info.getGyomutantoId();
		}
		//2005.11.07 iso 追加
		else if(userInfo.getRole().toString().indexOf("/question") > 0){
			//アンケートの時  
			return "";
        }else{
            throw new SystemException("ユーザを特定できません。");
        }
    }
}
