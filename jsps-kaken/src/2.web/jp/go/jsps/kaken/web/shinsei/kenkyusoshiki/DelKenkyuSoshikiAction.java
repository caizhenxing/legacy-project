/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : takano
 *    Date        : 2004/01/09
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.shinsei.kenkyusoshiki;

import java.util.*;

import javax.servlet.http.*;

import jp.go.jsps.kaken.model.exceptions.*;
import jp.go.jsps.kaken.model.vo.*;
import jp.go.jsps.kaken.model.vo.shinsei.KenkyuSoshikiKenkyushaInfo;
import jp.go.jsps.kaken.web.common.*;
import jp.go.jsps.kaken.web.shinsei.*;
import jp.go.jsps.kaken.web.struts.*;

import org.apache.struts.action.*;
import org.apache.struts.action.ActionMapping;

/**
 * �����g�D�\�폜�A�N�V�����N���X�B
 * �����g�D���X�g����w��C���f�b�N�X�̃I�u�W�F�N�g���폜����B 
 * ID RCSfile="$RCSfile: DelKenkyuSoshikiAction.java,v $"
 * Revision="$Revision: 1.2 $"
 * Date="$Date: 2007/07/27 02:12:06 $"
 */
public class DelKenkyuSoshikiAction extends BaseAction {

	/* (�� Javadoc)
	 * @see jp.go.jsps.kaken.web.struts.BaseAction#doMainProcessing(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, jp.go.jsps.kaken.web.common.UserContainer)
	 */
	public ActionForward doMainProcessing(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response,
		UserContainer container)
		throws ApplicationException {
			
		//-----ActionErrors�̐錾�i��^�����j-----
		ActionErrors errors = new ActionErrors();

		//-----�\�������̓t�H�[���̎擾
		ShinseiForm shinseiForm = (ShinseiForm)form;
		
		//-----�����g�D�Ǘ����X�g����w��C���f�b�N�X���폜
		int listIndex = shinseiForm.getListIndex();
		if(listIndex > 0){
			ShinseiDataInfo shinseiDataInfo = shinseiForm.getShinseiDataInfo();
			List kenkyushaList = shinseiDataInfo.getKenkyuSoshikiInfoList();
			try{
				KenkyuSoshikiKenkyushaInfo kenkyushaInfo=(KenkyuSoshikiKenkyushaInfo)kenkyushaList.remove(listIndex);
//UPDATE�@START 2007/07/27 BIS �����_ // �������S�ҁE�A�g������ . ��̒l
				//if(kenkyushaInfo!=null&&"2".equals(kenkyushaInfo.getBuntanFlag())){
				if(kenkyushaInfo!=null&&(!"1".equals(kenkyushaInfo.getBuntanFlag()) && !"3".equals(kenkyushaInfo.getBuntanFlag()) )){
//UPDATE�@END�@ 2007/07/27 BIS �����_ 					
					shinseiDataInfo.setKenkyuNinzuInt(shinseiDataInfo.getKenkyuNinzuInt()-1);
				} else if(kenkyushaInfo!=null&&"3".equals(kenkyushaInfo.getBuntanFlag())){
					shinseiDataInfo.setKyoryokushaNinzuInt(shinseiDataInfo.getKyoryokushaNinzuInt()-1);
				}
			}catch(IndexOutOfBoundsException e){
				String msg = "�����g�D�\�̃��X�g�C���f�b�N�X�l���s���ł��B";
				throw new ApplicationException(msg, new ErrorInfo("errors.5015"), e);			
			}
		}
		
		//-----�\�������̓t�H�[���ɃZ�b�g����B
		updateFormBean(mapping, request, shinseiForm);

		//-----��ʑJ�ځi��^�����j-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		
		return forwardSuccess(mapping);
		
	}
	
	
	
}
