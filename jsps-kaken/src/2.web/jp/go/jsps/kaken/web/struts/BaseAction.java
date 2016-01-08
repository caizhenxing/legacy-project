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
package jp.go.jsps.kaken.web.struts;

import java.util.*;

import javax.servlet.http.*;

import jp.go.jsps.kaken.log.*;
import jp.go.jsps.kaken.model.*;
import jp.go.jsps.kaken.model.common.*;
import jp.go.jsps.kaken.model.exceptions.*;
import jp.go.jsps.kaken.model.vo.*;
import jp.go.jsps.kaken.util.*;
import jp.go.jsps.kaken.web.common.*;

import org.apache.commons.logging.*;
import org.apache.struts.action.*;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.config.*;
import org.apache.struts.util.*;

/**
 * �A�N�V�����̊�{�ƂȂ�N���X�B
 * ���Ӂjperform���\�b�h���T�u�N���X�Ŏg�p���Ȃ��ł��������B
 * 
 * ID RCSfile="$RCSfile: BaseAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:28 $"
 */
public abstract class BaseAction extends Action {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/**
	 * ���O�N���X�B 
	 */
	protected static final Log log = LogFactory.getLog(BaseAction.class);

	//---------------------------------------------------------------------
	// Public Methods
	//---------------------------------------------------------------------

	/* (�� Javadoc)
	 * @see org.apache.struts.action.Action#execute(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public final ActionForward execute(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)
		throws Exception {

		//�t�H�[�����̊m�F
		if (log.isDebugEnabled()) {
			if (form != null) {
				log.debug(
					"ActionForm " + form.getClass().getName() + "\n" + form);
			}
		}

		//		//�f�o�b�N�p���b�Z�[�W�̕\��
		//		if (log.isDebugEnabled()) {
		//			HttpSession session = request.getSession();
		//			Enumeration se = session.getAttributeNames();
		//			StringBuffer buffer = new StringBuffer();
		//			buffer.append("\nsession Attribute\n");
		//			while (se.hasMoreElements()) {
		//				String attributeName = (String) se.nextElement();
		//				buffer.append(
		//					"\tName '"
		//						+ attributeName
		//						+ "' Value '"
		//						+ session.getAttribute(attributeName)
		//						+ "'\n");
		//			}
		//
		//			Enumeration re = request.getAttributeNames();
		//			buffer.append("request Attribute\n");
		//			while (re.hasMoreElements()) {
		//				String attributeName = (String) re.nextElement();
		//				buffer.append(
		//					"\tName '"
		//						+ attributeName
		//						+ "' Value '"
		//						+ request.getAttribute(attributeName)
		//						+ "'\n");
		//			}
		//
		//			Enumeration pe = request.getParameterNames();
		//			buffer.append("request parameter\n");
		//			while (pe.hasMoreElements()) {
		//				String parameterName = (String) pe.nextElement();
		//				buffer.append(
		//					"\tName '"
		//						+ parameterName
		//						+ "' Value '"
		//						+ Arrays.asList(
		//							request.getParameterValues(parameterName))
		//						+ "'\n");
		//			}
		//			log.debug(buffer.toString());
		//		}
		
		
		if (mapping instanceof jp.go.jsps.kaken.web.struts.ActionMapping) {
			jp.go.jsps.kaken.web.struts.ActionMapping actionMapping = 
					(jp.go.jsps.kaken.web.struts.ActionMapping) mapping;
			//----------------------------------
			//�������`�F�b�N���s�������f���A�󂫃������`�F�b�N���s�Ȃ��B
			//----------------------------------
			if (actionMapping.isMemoryCheck()) {
				int usedMemRate = PerformanceLogWriter.checkUsedMemRate();
				int maxMemRate  = ApplicationSettings.getInt(ISettingKeys.MAX_MEMORY_USED_RATE);
				if(usedMemRate >= maxMemRate){
					return forwardMemoryError(mapping);	//�ő�l�𒴂��Ă����ꍇ��Sorry�y�[�W�֑J�ڂ���
				}
			}
		}
		
		
		//���[�U���̎擾
		UserContainer container = getUserContainer(request);
		//�Z�b�V�������̎擾
		HttpSession session = request.getSession();
		
		synchronized (session.getId().intern()) {
			//�O����
			doPreProcessing(mapping, form, request, response);

			//�����p�t�H�[�}���X���O
			PerformanceLogWriter pw = null;
			if (log.isDebugEnabled()) {
				pw = new PerformanceLogWriter();
			}

			//���C������
			ActionForward forward =
				doMainProcessing(mapping, form, request, response, container);
			
			//�����p�t�H�[�}���X���O
			if(pw != null){
				if (mapping instanceof jp.go.jsps.kaken.web.struts.ActionMapping) {
					String s = ((jp.go.jsps.kaken.web.struts.ActionMapping)mapping).getDescription();
					pw.out("�������F"+s);
				}else{
					pw.out("�������͕s���ł��B");	
				}
			}

			//�㏈��
			doPostProcessing(mapping, form, request, response);
			
			return forward;
		}
	}

	/**
	 *Action�N���X�̎�v�ȋ@�\����������B
	 * �߂�l�Ƃ��āA���̑J�ڐ��ActionForward�^�ŕԂ���B
	 * 
	 * @param mapping ActonMapping���n����܂��B
	 * @param form ActionForm���n����܂��B
	 * @param req HttpServletRequest���n����܂��B
	 * @param res HttpServletResponse���n����܂��B
	 *
	 * @exception Exception Exception�������AThrow����܂��B
	 */
	public abstract ActionForward doMainProcessing(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response,
		UserContainer container)
		throws ApplicationException;

	/**
	 * ���㏈������������B���̎����͏ȗ��\�B
	 * 
	 * @param mapping ActonMapping���n����܂��B
	 * @param form ActionForm���n����܂��B
	 * @param req HttpServletRequest���n����܂��B
	 * @param res HttpServletResponse���n����܂��B
	 *
	 * @exception Exception Exception�������AThrow����܂��B
	 */
	public void doPostProcessing(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)
		throws Exception {
		
		//�u���E�U�ɃL���b�V����ۑ������Ȃ��B
		response.setHeader("Expires", new DateUtil(new Date()).getHTTPDate());
		response.setHeader("Pragma","no-cache");
		response.setHeader("Cache-Control","no-cache");
		
		return;
	}

	/**
	 * ���O��������������B���̎����͏ȗ��\�B
	 * 
	 * @param mapping ActonMapping���n����܂��B
	 * @param form ActionForm���n����܂��B
	 * @param req HttpServletRequest���n����܂��B
	 * @param res HttpServletResponse���n����܂��B
	 *
	 * @exception Exception Exception�������AThrow����܂��B
	 */
	public void doPreProcessing(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)
		throws Exception {

		return;
	}

	/**
	 * �������̃y�[�W�J�ڂ��s���B
	 * @param mapping	ActionMapping
	 * @return	ActionForward �y�[�W�J�ڐ���
	 */
	protected ActionForward forwardSuccess(ActionMapping mapping) {
		return mapping.findForward(IConstants.SUCCESS_KEY);
	}

	/**
	 * ���s���̃y�[�W�J�ڂ��s���B
	 * @param mapping	ActionMapping
	 * @return	ActionForward �y�[�W�J�ڐ���
	 */
	protected ActionForward forwardFailure(ActionMapping mapping) {
		return mapping.findForward(IConstants.FAILURE_KEY);
	}

	/**
	 * ���s���̃y�[�W�J�ڂ��s���B
	 * @param mapping	ActionMapping
	 * @return	ActionForward �y�[�W�J�ڐ���
	 */
	protected ActionForward forwardTokenError(ActionMapping mapping) {
		return mapping.findForward(IConstants.TOKEN_ERROR_KEY);
	}

	/**
	 * CANCEL���̃y�[�W�J�ڂ��s���B
	 * @param mapping	ActionMapping
	 * @return	ActionForward �y�[�W�J�ڐ���
	 */
	protected ActionForward forwardCancel(ActionMapping mapping) {
		return mapping.findForward(IConstants.CANCEL_KEY);
	}

	/**
	 * ���̓y�[�W�֑J�ڂ�����B
	 * @param mapping	ActionMapping
	 * @return	ActionForward �y�[�W�J�ڐ���
	 */
	protected ActionForward forwardInput(ActionMapping mapping) {
		return new ActionForward(mapping.getInput());
	}

	/**
	 * �������G���[�y�[�W�iSorry�y�[�W�j�֑J�ڂ�����B
	 * �����W���[�����΂ł͂Ȃ��A�R���e�L�X�g���΂ƂȂ�B
	 * @param mapping	ActionMapping
	 * @return	ActionForward �y�[�W�J�ڐ���
	 */
	protected ActionForward forwardMemoryError(ActionMapping mapping) {
		return new ActionForward("memSorryForward",
								  ApplicationSettings.getString(ISettingKeys.MAX_MEMORY_ERROR_PAGE),
								  false,
								  true);
	}

	/**
	 * ��`���ꂽ�A�N�V�����t�H�[�����擾����B
	 * @param mapping	�}�b�s���O���
	 * @param request	���N�G�X�g���
	 * @return			�A�N�V�����t�H�[��
	 */
	protected ActionForm getFormBean(
		ActionMapping mapping,
		HttpServletRequest request) {
		ActionForm actionForm = null;
		// Retrieve the form bean
		if (mapping.getAttribute() != null) {
			if ("request".equals(mapping.getScope())) {
				actionForm =
					(ActionForm) request.getAttribute(mapping.getAttribute());
			} else {
				HttpSession session = request.getSession();
				actionForm =
					(ActionForm) session.getAttribute(mapping.getAttribute());
			}
		}
		return actionForm;
	}

	/**
	 * ��`���ꂽ�A�N�V�����t�H�[�����X�V����B
	 * @param mapping	�}�b�s���O���
	 * @param request	���N�G�X�g���
	 * @param form		�A�N�V�����t�H�[��
	 */
	protected void updateFormBean(
		ActionMapping mapping,
		HttpServletRequest request,
		ActionForm form) {
		// Update the form bean
		if (mapping.getAttribute() != null) {
			if ("session".equals(mapping.getScope())) {
				HttpSession session = request.getSession();
				session.setAttribute(mapping.getAttribute(), form);
			} else {
				request.setAttribute(mapping.getAttribute(), form);
			}
		}
	}
	
	/**
	 * ��`���ꂽ�A�N�V�����t�H�[�����X�V����B
	 * @param mapping	�}�b�s���O���
	 * @param session	�Z�b�V�������
	 * @param form		�A�N�V�����t�H�[��
	 */
	protected void updateFormBean(
			ActionMapping mapping,
			HttpSession session,
			ActionForm form) {
			// Update the form bean
			if (mapping.getAttribute() != null) {
				session.setAttribute(mapping.getAttribute(), form);
			}
		}
	
	
	/**
	 * �p�X���ł��Ă����ꂽ�A�N�V�����t�H�[�����X�V����B
	 * @param path			�p�X���
	 * @param request		���N�G�X�g���
	 * @param form			�A�N�V�����t�H�[��
	 */
	protected void updateFormBean(
		String path,
		HttpServletRequest request,
		ActionForm form) {
		this.updateFormBean(findMapping(request, path), request, form);
	}

	/**
	 * �p�X���Ɉ�v����}�b�s���O�����擾����B
	 * @param request	���N�G�X�g���
	 * @param path		�}�b�s���O�����擾���邽�߂̃p�X
	 * @return			�A�N�V�����}�b�s���O
	 */
	protected ActionMapping findMapping(
		HttpServletRequest request,
		String path) {
		ModuleConfig moduleConfig =
			RequestUtils.getModuleConfig(
				request,
				getServlet().getServletContext());
		return (ActionMapping) moduleConfig.findActionConfig(path);
	}

	/**
	 * ��`���ꂽ�A�N�V�����t�H�[�����폜����B
	 * @param mapping		�}�b�s���O���
	 * @param request		���N�G�X�g���
	 */
	protected void removeFormBean(
		ActionMapping mapping,
		HttpServletRequest request) {
		// Remove the obsolete form bean
		if (mapping.getAttribute() != null) {
			if ("request".equals(mapping.getScope())) {
				request.removeAttribute(mapping.getAttribute());
			} else {
				HttpSession session = request.getSession();
				session.removeAttribute(mapping.getAttribute());
			}
		}
	}

	/**
	 * �V�X�e���T�[�r�X���擾����B
	 * @param serviceName	�T�[�r�X����
	 * @return				�V�X�e���T�[�r�X�����N���X�B
	 */
	protected final ISystemServise getSystemServise(String serviceName) {
		return SystemServiceFactory.getSystemService(serviceName);
	}

	/**
	 * ���[�U���R���e�i���擾����B
	 * @param request	���N�G�X�g���
	 * @return	���[�U���ێ��p�R���e�i
	 */
	protected UserContainer getUserContainer(HttpServletRequest request) {

		UserContainer userContainer =
			(UserContainer) request.getSession().getAttribute(
				IConstants.USER_CONTAINER_KEY);
		if (userContainer == null) {
			userContainer = new UserContainer();
			HttpSession session = request.getSession(true);
			session.setAttribute(IConstants.USER_CONTAINER_KEY, userContainer);
		}
		return userContainer;
	}

	/**
	 * ���[�U�����擾����B
	 * @param request	���N�G�X�g���
	 * @return	���O�C�����[�U���
	 */
	protected UserInfo getUserInfo(HttpServletRequest request) {
		UserContainer container = getUserContainer(request);
		return container.getUserInfo();
	}
	/**
	 * �T�[�o��ł̏������ʂ����N�G�X�g���ɕۑ�����B
	 * @param request	���N�G�X�g���
	 * @param errors	�A�N�V�����G���[���
	 * @param e			�T�[�o���ؗ�O�I�u�W�F�N�g
	 */
	protected void saveServerErrors(
		HttpServletRequest request,
		ActionErrors errors,
		ValidationException e) {
		for (Iterator iter = e.getErrors().iterator(); iter.hasNext();) {
			ErrorInfo element = (ErrorInfo) iter.next();
			errors.add(
				ActionErrors.GLOBAL_ERROR,
				new ActionError(
					element.getErrorCode(),
					element.getErrorArgs()));
		}
		saveErrors(request, errors);
	}

}
