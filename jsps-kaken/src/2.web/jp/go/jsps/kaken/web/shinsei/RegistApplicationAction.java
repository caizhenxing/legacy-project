/*======================================================================
 *    SYSTEM      : ���{�w�p�U����d�q�\���V�X�e���i�Ȋw������⏕���j
 *    Source name : RegistApplicationAction.java
 *    Description : �\���������f�[�^�x�[�X�ɓo�^����
 *
 *    Author      : takano
 *    Date        : 2004/01/09
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2004/01/09    V1.0        takano         �V�K�쐬
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
import jp.go.jsps.kaken.util.*;
import jp.go.jsps.kaken.web.common.*;
import jp.go.jsps.kaken.web.struts.*;

import org.apache.struts.action.*;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.*;

/**
 * �\�����o�^�A�N�V�����N���X�B
 * �\���������f�[�^�x�[�X�ɓo�^����B
 * ����������ɏI�������ꍇ�A�t�@�C���ϊ�����ʂ�Ԃ��B
 * 
 * ID RCSfile="$RCSfile: RegistApplicationAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:00 $"
 */
public class RegistApplicationAction extends BaseAction {

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
// 2006/02/10�@�ǉ��@��������	
		String ouboShikaku = shinseiForm.getShinseiDataInfo().getOuboShikaku();
		String jigyoKubun = shinseiForm.getShinseiDataInfo().getKadaiInfo().getJigyoKubun();
		String year = "";
		String month = "";
		String day = "";
		DateUtil dateUtil = new DateUtil();
		if(jigyoKubun.equals(IJigyoKubun.JIGYO_KUBUN_WAKATESTART)){
			year = DateUtil.changeWareki4Seireki(shinseiForm.getSaiyoDateYear());
			month = StringUtil.fillLZero(shinseiForm.getSaiyoDateMonth(), 2);
			day = StringUtil.fillLZero(shinseiForm.getSaiyoDateDay(), 2);
			dateUtil.setCal(year, month, day);
			shinseiForm.getShinseiDataInfo().setSaiyoDate(dateUtil.getDateYYYYMMDD());
		} else if(jigyoKubun.equals(IJigyoKubun.JIGYO_KUBUN_SHOKUSHINHI)){
			if(ouboShikaku.equals("1")||ouboShikaku.equals("2")){
				year = DateUtil.changeWareki4Seireki(shinseiForm.getSikakuDateYear());
				month = StringUtil.fillLZero(shinseiForm.getSikakuDateMonth(), 2);
				day = StringUtil.fillLZero(shinseiForm.getSikakuDateDay(), 2);
				dateUtil.setCal(year, month, day);
				shinseiForm.getShinseiDataInfo().setSikakuDate(dateUtil.getDateYYYYMMDD());
			}
			if(ouboShikaku.equals("3")){
				year = DateUtil.changeWareki4Seireki(shinseiForm.getIkukyuStartDateYear());
				month = StringUtil.fillLZero(shinseiForm.getIkukyuStartDateMonth(), 2);
				day = StringUtil.fillLZero(shinseiForm.getIkukyuStartDateDay(), 2);
				dateUtil.setCal(year, month, day);
				shinseiForm.getShinseiDataInfo().setIkukyuStartDate(dateUtil.getDateYYYYMMDD());
				year = DateUtil.changeWareki4Seireki(shinseiForm.getIkukyuEndDateYear());
				month = StringUtil.fillLZero(shinseiForm.getIkukyuEndDateMonth(), 2);
				day = StringUtil.fillLZero(shinseiForm.getIkukyuEndDateDay(), 2);
				dateUtil.setCal(year, month, day);
				shinseiForm.getShinseiDataInfo().setIkukyuEndDate(dateUtil.getDateYYYYMMDD());
			}
		}
// �c�@�����܂�
//2007/02/08 �c�@�ǉ���������
        //�R����]����̖���ݒ�
        String shinsaRyoikiName = LabelValueManager.getlabelName(
                                            shinseiForm.getShinsaKiboRyoikiList(), 
                                            shinseiForm.getShinseiDataInfo().getShinsaRyoikiCd());
        shinseiForm.getShinseiDataInfo().setShinsaRyoikiName(shinsaRyoikiName);
//2007/02/08�@�c�@�ǉ������܂�        
		//-----�\���o�^���\�b�h���Ăяo��		
		try{
			registApplication(container, shinseiForm);
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

		//------�g�[�N���̍폜	
		resetToken(request);
		//------�t�H�[�����̍폜  ���t�@�C���ϊ��A�N�V�����ŗ��p���邽�ߍ폜���Ȃ��B
		//removeFormBean(mapping,request);

		return forwardSuccess(mapping);

	}
	
	
	/**
	 * �\������o�^����B
	 * @param container ���O�C���\���ҏ��
	 * @param form      �\�����̓t�H�[���f�[�^
	 * @throws ValidationException  �f�[�^�`�F�b�N�G���[�����������ꍇ
	 * @throws ApplicationException �\���o�^�Ɏ��s�����ꍇ
	 */
	private void registApplication(UserContainer container, ShinseiForm form)
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
				new ErrorInfo("errors.7000"),
				e);
		}
		
		//�����o����̎擾
		ShinseiDataInfo       shinseiDataInfo = form.getShinseiDataInfo();
 
//2007/03/15�@�c�@�ǉ���������        
        //�N�0�Ζ����܂���100�Έȏ�̏ꍇ�̓G���[
        //��ʂɂ��N��͕\�������Ȃ�
        if (!StringUtil.isBlank(shinseiDataInfo.getDaihyouInfo().getNenrei())){
            int nenrei = Integer.parseInt(shinseiDataInfo.getDaihyouInfo().getNenrei());
            if(nenrei < 0 || nenrei > 99){     
                List errors = new ArrayList();
                String property = "nenreiInvalid";
                errors.add(new ErrorInfo("errors.9037", null, property));
                String message = "�\�����f�[�^�`���`�F�b�N�Ō��؃G���[�ƂȂ�܂����B";
                throw new ValidationException(message, errors);
            }
        }
//2007/03/15�@�c�@�ǉ������܂�
        
		//2005.08.08 iso �t�@�C�����O�o��
		form.outputFileInfo();
		
		//-----�V�K���X�V������i��t�ԍ����Z�b�g����Ă��邩�ǂ����j
		String systemNo = shinseiDataInfo.getSystemNo();
		if(systemNo == null || systemNo.length() == 0){

//			2005/04/13 �ǉ� ��������----------
//			���R:�Œǉ��̂���

			//-----�ł�ݒ肷��i�V�K�o�^�j
//2006/06/21 �c�@�C����������            
//			shinseiDataInfo.getKadaiInfo().setEdition(1);
            shinseiDataInfo.getKadaiInfo().setEdition(0);
//2006/06/21�@�c�@�C�������܂�            
			
//			2005/04/13 �ǉ� �����܂�----------			
			
			//�T�[�o�T�[�r�X�̌Ăяo���i�V�K�o�^�j
			ISystemServise servise = getSystemServise(
							IServiceName.SHINSEI_MAINTENANCE_SERVICE);
			ShinseiDataInfo newInfo = servise.registApplicationNew(
										container.getUserInfo(),
										shinseiDataInfo,
										annexFileRes);
			//�t�H�[���ɓo�^���ꂽ�\���f�[�^���Z�b�g����
			form.setShinseiDataInfo(newInfo);
			
		}else{
			
//			2005/04/13 �ǉ� ��������----------
//			���R:�Œǉ��̂���

//2006/06/21 �c�@�폜��������            
//			
//            //2005/08/17 �\���󋵂��쐬��(01)�A�y�ѐ\�������m�F(02)�ȊO�̏ꍇ�A�ł��A�b�v����
//			String jokyoId = shinseiDataInfo.getJokyoId();
//			if ( !(jokyoId.equals(StatusCode.STATUS_SAKUSEITHU) 
//				|| jokyoId.equals(StatusCode.STATUS_SHINSEISHO_MIKAKUNIN)) ){
//				//-----�ł�ݒ肷��i�X�V�o�^�j
//				int edition = shinseiDataInfo.getKadaiInfo().getEdition();
//				edition++;
//				shinseiDataInfo.getKadaiInfo().setEdition(edition);
//			}
//			//2005/8/19 �ꎞ�ۑ���A���߂ēo�^�̏ꍇ�A�ł�ݒ肷��
//			else if (shinseiDataInfo.getKadaiInfo().getEdition() == 0){
//				//-----�ł�ݒ肷��i���߂ēo�^�j
//
//				shinseiDataInfo.getKadaiInfo().setEdition(1);            
//			}
//2006/06/21�@�c�@�폜�����܂� 
            
//			2005/04/13 �ǉ� �����܂�----------
			
			//�T�[�o�T�[�r�X�̌Ăяo���i�X�V�o�^�j
			ISystemServise servise = getSystemServise(
							IServiceName.SHINSEI_MAINTENANCE_SERVICE);
			ShinseiDataInfo newInfo = servise.registApplicationUpdate(
					container.getUserInfo(),
					shinseiDataInfo,
					annexFileRes);
			
//			2005/04/21 �ǉ� ��������----------
//			���R:�\�����ɕύX������V�K�o�^���s�����ꍇ�A�V�����\���f�[�^���Z�b�g����
			
			//�t�H�[���ɓo�^���ꂽ�\���f�[�^���Z�b�g����
			form.setShinseiDataInfo(newInfo);
		}		
		
//			2005/04/13 �ǉ� �����܂�----------
		
	}
	
}