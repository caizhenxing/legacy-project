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
package jp.go.jsps.kaken.web.shinsei;

import java.io.*;
import java.util.*;

import javax.servlet.http.*;

import jp.go.jsps.kaken.model.*;
import jp.go.jsps.kaken.model.common.*;
import jp.go.jsps.kaken.model.exceptions.*;
import jp.go.jsps.kaken.model.vo.*;
import jp.go.jsps.kaken.model.vo.shinsei.KenkyuSoshikiKenkyushaInfo;
import jp.go.jsps.kaken.util.*;
import jp.go.jsps.kaken.web.common.*;
import jp.go.jsps.kaken.web.struts.*;

import org.apache.struts.action.*;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.*;

/**
 * �\�����ꎞ�ۑ��A�N�V�����N���X�B
 * �\���������f�[�^�x�[�X�ɓo�^����B
 * ����������ɏI�������ꍇ�A�ꎞ�ۑ�������ʂ�Ԃ��B
 * 
 * ID RCSfile="$RCSfile: TransientSaveApplicationAction.java,v $"
 * Revision="$Revision: 1.4 $"
 * Date="$Date: 2007/07/20 08:00:35 $"
 */
public class TransientSaveApplicationAction extends BaseAction {


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

		//------�L�����Z����		
		if (isCancelled(request)) {
			return forwardCancel(mapping);
		}

		//-----�擾�����g�[�N���������ł���Ƃ�
		if (!isTokenValid(request)) {
			errors.add(ActionErrors.GLOBAL_ERROR,
					   new ActionError("error.transaction.token"));
			saveErrors(request, errors);
			return forwardTokenError(mapping);
		}
		
		//-----�\�������̓t�H�[���̎擾
		ShinseiForm shinseiForm = (ShinseiForm)form;

//2006/02/15�@�ǉ��@��������	
		String ouboShikaku = shinseiForm.getShinseiDataInfo().getOuboShikaku();
		String jigyoKubun = shinseiForm.getShinseiDataInfo().getKadaiInfo().getJigyoKubun();
		String year = "";
		String month = "";
		String day = "";
		DateUtil dateUtil = new DateUtil();
		if(jigyoKubun.equals(IJigyoKubun.JIGYO_KUBUN_WAKATESTART)){
			if(!StringUtil.isBlank(shinseiForm.getSaiyoDateYear()) && 
					!StringUtil.isBlank(shinseiForm.getSaiyoDateMonth()) && 
					!StringUtil.isBlank(shinseiForm.getSaiyoDateDay())){
				year = DateUtil.changeWareki4Seireki(shinseiForm.getSaiyoDateYear());
				month = StringUtil.fillLZero(shinseiForm.getSaiyoDateMonth(), 2);
				day = StringUtil.fillLZero(shinseiForm.getSaiyoDateDay(), 2);
				dateUtil.setCal(year, month, day);
				shinseiForm.getShinseiDataInfo().setSaiyoDate(dateUtil.getDateYYYYMMDD());
			}
		} else if(jigyoKubun.equals(IJigyoKubun.JIGYO_KUBUN_SHOKUSHINHI)){
			if(ouboShikaku.equals("1")||ouboShikaku.equals("2")){
				if(!StringUtil.isBlank(shinseiForm.getSikakuDateYear()) && 
						!StringUtil.isBlank(shinseiForm.getSikakuDateMonth()) && 
						!StringUtil.isBlank(shinseiForm.getSikakuDateDay())){
					year = DateUtil.changeWareki4Seireki(shinseiForm.getSikakuDateYear());
					month = StringUtil.fillLZero(shinseiForm.getSikakuDateMonth(), 2);
					day = StringUtil.fillLZero(shinseiForm.getSikakuDateDay(), 2);
					dateUtil.setCal(year, month, day);
					shinseiForm.getShinseiDataInfo().setSikakuDate(dateUtil.getDateYYYYMMDD());
				}
			}
			if(ouboShikaku.equals("3")){
				if(!StringUtil.isBlank(shinseiForm.getIkukyuStartDateYear()) && 
						!StringUtil.isBlank(shinseiForm.getIkukyuStartDateMonth()) && 
						!StringUtil.isBlank(shinseiForm.getIkukyuStartDateDay())){
					year = DateUtil.changeWareki4Seireki(shinseiForm.getIkukyuStartDateYear());
					month = StringUtil.fillLZero(shinseiForm.getIkukyuStartDateMonth(), 2);
					day = StringUtil.fillLZero(shinseiForm.getIkukyuStartDateDay(), 2);
					dateUtil.setCal(year, month, day);
					shinseiForm.getShinseiDataInfo().setIkukyuStartDate(dateUtil.getDateYYYYMMDD());
				}
				if(!StringUtil.isBlank(shinseiForm.getIkukyuEndDateYear()) && 
						!StringUtil.isBlank(shinseiForm.getIkukyuEndDateMonth()) && 
						!StringUtil.isBlank(shinseiForm.getIkukyuEndDateDay())){
					year = DateUtil.changeWareki4Seireki(shinseiForm.getIkukyuEndDateYear());
					month = StringUtil.fillLZero(shinseiForm.getIkukyuEndDateMonth(), 2);
					day = StringUtil.fillLZero(shinseiForm.getIkukyuEndDateDay(), 2);
					dateUtil.setCal(year, month, day);
					shinseiForm.getShinseiDataInfo().setIkukyuEndDate(dateUtil.getDateYYYYMMDD());
				}
			}
		}
// �c�@�����܂�
		
		//-----���ۑ����\�b�h���Ăяo��
		try{
			transientSave(container, shinseiForm);
		}catch(ValidationException e){
			List errorList = e.getErrors();
			for(int i=0; i<errorList.size(); i++){
				ErrorInfo errInfo = (ErrorInfo)errorList.get(i);
				errors.add(errInfo.getProperty(),
						   new ActionError(errInfo.getErrorCode(), errInfo.getErrorArgs()));
			}
		}

		//-----��ʑJ�ځi��^�����j-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}

// 20050706
		//-----�ꎞ�ۑ�������ʂɂāASYSTEMNO���K�v�Ȃ��߃��N�G�X�g�ɃZ�b�g
		ShinseiDataPk shinseiDataPk = new ShinseiDataPk();
		shinseiDataPk.setSystemNo(shinseiForm.getShinseiDataInfo().getSystemNo());
		//-----SYSTEMNO�����N�G�X�g�����ɃZ�b�g
		request.setAttribute("shinseiDataPk", shinseiDataPk);
// Horikoshi

		//------�g�[�N���̍폜	
		resetToken(request);
		//------�t�H�[�����̍폜
		removeFormBean(mapping,request);		

		return forwardSuccess(mapping);
	}
	
	
	
	/**
	 * �\���������ۑ�����B
	 * @param container ���O�C���\���ҏ��
	 * @param form      �\�����̓t�H�[���f�[�^
	 * @throws ValidationException  �f�[�^�`�F�b�N�G���[�����������ꍇ
	 * @throws ApplicationException ���ۑ��Ɏ��s�����ꍇ
	 */
	private void transientSave(UserContainer container, ShinseiForm form)
		throws ValidationException, ApplicationException
	{
		//�Y�t�t�@�C��
		FileResource annexFileRes = null;
		try{
			FormFile file = form.getUploadFile();
			if(file != null &&
			   file.getFileData() != null && 
			   file.getFileData().length != 0)
			{
				annexFileRes = new FileResource();
				annexFileRes.setPath(file.getFileName());	//�t�@�C����
				annexFileRes.setBinary(file.getFileData());	//�t�@�C���T�C�Y
			}
		}catch(IOException e){
			throw new ApplicationException(
				"�Y�t�t�@�C���̎擾�Ɏ��s���܂����B",
				new ErrorInfo("errors.7001"),
				e);
		}
		
		//�����o����̎擾
		ShinseiDataInfo       shinseiDataInfo = form.getShinseiDataInfo();
 
//2007/03/15 �c�@�ǉ���������        
        //�N�0�Ζ����܂���100�Έȏ�̏ꍇ��null���Z�b�g
        //��ʂɂ��N��͕\�������Ȃ�
        if (!StringUtil.isBlank(shinseiDataInfo.getDaihyouInfo().getNenrei())){
            int nenrei = Integer.parseInt(shinseiDataInfo.getDaihyouInfo().getNenrei());
            if(nenrei < 0 || nenrei > 99){
                //�N���null���Z�b�g���A��ʂɕ\�������Ȃ�
                shinseiDataInfo.getDaihyouInfo().setNenrei(null);
                //�����g�D���ɁA�N���null���Z�b�g���A��ʂɕ\�������Ȃ�
                if (shinseiDataInfo.getKenkyuSoshikiInfoList().size() >= 1) {
                    KenkyuSoshikiKenkyushaInfo kenkyushaInfo = 
                        (KenkyuSoshikiKenkyushaInfo) shinseiDataInfo.getKenkyuSoshikiInfoList().get(0);
                    kenkyushaInfo.setNenrei(null);
                }
            }
        }
//2007/03/15�@�c�@�ǉ������܂�
        
		//2005.08.08 iso �t�@�C�����O�o��
		form.outputFileInfo();
		
		//-----�V�K���X�V������i��t�ԍ����Z�b�g����Ă��邩�ǂ����j
		String systemNo = shinseiDataInfo.getSystemNo();
		if(systemNo == null || systemNo.length() == 0){
			//�T�[�o�T�[�r�X�̌Ăяo���i�V�K�ꎞ�ۑ��j
			ISystemServise servise = getSystemServise(
							IServiceName.SHINSEI_MAINTENANCE_SERVICE);
			ShinseiDataInfo newInfo = servise.transientSaveNew(
										container.getUserInfo(),
										shinseiDataInfo,
										annexFileRes);
			//�t�H�[���ɓo�^���ꂽ�\���f�[�^���Z�b�g����
			form.setShinseiDataInfo(newInfo);
			
		}else{
			//�T�[�o�T�[�r�X�̌Ăяo���i�X�V�ꎞ�ۑ��j
			ISystemServise servise = getSystemServise(
							IServiceName.SHINSEI_MAINTENANCE_SERVICE);
			servise.transientSaveUpdate(container.getUserInfo(),shinseiDataInfo, annexFileRes);
		}		
	}
	
}
