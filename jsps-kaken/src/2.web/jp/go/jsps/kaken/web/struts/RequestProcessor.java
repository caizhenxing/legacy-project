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
 * �g���Ń��N�G�X�g�v���Z�b�T�N���X�B
 * ID RCSfile="$RCSfile: RequestProcessor.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:28 $"
 */
public class RequestProcessor extends org.apache.struts.action.RequestProcessor {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/**
	 * ���O�N���X�B 
	 */
	protected static final Log log = LogFactory.getLog(RequestProcessor.class);

    /**
     * �č����O�N���X�B 
     */
    protected static final Log auditLog = LogFactory.getLog("audit");


	/* (�� Javadoc)
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
		//�A�N�V�����������̃G���[���擾����悤�ɕύX
		//----------------------------------
		try {
			return super.processActionPerform(
				request,
				response,
				action,
				form,
				mapping);
		} catch (Error error) {
			log.error("�A�N�V����'" + mapping.getPath() + "'���s���ɃG���[���������܂����B", error);
			return processException(
				request,
				response,
				new Exception(error),
				form,
				mapping);
		}
	}

	/* (�� Javadoc)
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
			//�G���[������{�^����\�����邩�𔻒f����B(�Z�b�V�����G���[�ł�����{�^����L���ɂ��邽��)
			//----------------------------------
			if (actionMapping.isErrorClose()) {
				request.setAttribute("ERROR_CLOSE", Boolean.TRUE);
			}

			//----------------------------------
			//���O�I���`�F�b�N���s�������f���A���O�C���`�F�b�N����B
			//----------------------------------

			if (actionMapping.isLogonCheck()) {
				
				if (log.isDebugEnabled()){
					log.debug("���@Do Check Login !!!");
				}
								
				//----------------------------------
				//�G���[���̑J�ڐ�̌���
				//----------------------------------
				ActionForward forward = mapping.findForward("session");

				//----------------------------------
				//���[�U��񂪑��݂��Ȃ��ꍇ�̓Z�b�V�����G���[
				//----------------------------------
				UserContainer userContainer = (UserContainer) request.getSession().getAttribute(IConstants.USER_CONTAINER_KEY);
				if (userContainer == null || userContainer.getUserInfo() == null) {
				  	processForwardConfig(request, response, forward);
					return false;
				}

				//----------------------------------
				//���W���[�����ƃ��O�C�����[�U���[���̃`�F�b�N
				//----------------------------------
				ModuleConfig moduleConfig = mapping.getModuleConfig();
				//�\���ҁE�R�����Ƃ��������W���[�����擾�ł��Ȃ��ꍇ�̓`�F�b�N���Ȃ��B
				if (moduleConfig.getPrefix() != null) {
					//���W���[�������Y�����[�����̎擾
					UserRole role = null;
					//20005.11.07 iso �A���P�[�g�@�\�p�ɒǉ�
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
						//���[�U��񂪈�v���Ȃ��ꍇ�̓Z�b�V�����G���[
						processForwardConfig(request, response, forward);
                        return false;
					}
				}
                
                //----------------------------------
                //�č����O��������
                //----------------------------------
                UserInfo userInfo = userContainer.getUserInfo();
                StringBuffer buffer = new StringBuffer();
                buffer.append("Id::'").append(getUserId(userInfo)).append("',");
                buffer.append("Role::'").append(userInfo.getRole().getName()).append("',");
                buffer.append("Process::'").append(actionMapping.getDescription()).append("',");
                buffer.append("User-Agent::'").append(request.getHeader("User-Agent")).append("'");	//User-Agent���
                auditLog.info(buffer);
                
			} else {
				if (log.isDebugEnabled())
					log.debug("���@Do'nt Check Login !!!");
			}
		} else {
			log.debug("Do'nt LogonCheckActionMapping!!");
		}
		return super.processRoles(request, response, mapping);
	}
    
    /**
     * ���[�UID���擾����B
     * @param userInfo
     * @return
     */
    private String getUserId(UserInfo userInfo){
        if(userInfo.getRole().equals(UserRole.SHINSEISHA)){
            //�\���҂̂Ƃ�
            ShinseishaInfo info = userInfo.getShinseishaInfo();
            return info.getShinseishaId();
        }else if(userInfo.getRole().equals(UserRole.SHOZOKUTANTO)){
            //�����@�֒S���҂̂Ƃ�
            ShozokuInfo info = userInfo.getShozokuInfo();    
            return info.getShozokuTantoId();
            
/* 2005/03/25 �ǉ� ��������---------------------------------------
 * ���R ���ǒS���҂̒ǉ��ɂ�� */
            
        }else if(userInfo.getRole().equals(UserRole.BUKYOKUTANTO)){
            //���ǒS���҂̂Ƃ�
            BukyokutantoInfo info = userInfo.getBukyokutantoInfo();    
            return info.getBukyokutantoId();
            
/* �ǉ� �����܂�-------------------------------------------------- */
            
        }else if(userInfo.getRole().equals(UserRole.SHINSAIN)){
            //�R�����̂Ƃ�
            ShinsainInfo info = userInfo.getShinsainInfo();    
            return info.getShinsainId();
        }else if(userInfo.getRole().equals(UserRole.GYOMUTANTO)||userInfo.getRole().equals(UserRole.SYSTEM)){
            //�Ɩ��S���ҁE�V�X�e���Ǘ��҂̂Ƃ�
            GyomutantoInfo info = userInfo.getGyomutantoInfo();  
            return info.getGyomutantoId();
		}
		//2005.11.07 iso �ǉ�
		else if(userInfo.getRole().toString().indexOf("/question") > 0){
			//�A���P�[�g�̎�  
			return "";
        }else{
            throw new SystemException("���[�U�����ł��܂���B");
        }
    }
}
