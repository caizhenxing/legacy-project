/*======================================================================
 *    SYSTEM      : ���{�w�p�U����d�q�\���V�X�e���i�Ȋw������⏕���j
 *    Source name : EditCheckAction.java
 *    Description : ���ƊǗ����X�V�O�A�N�V�����N���X
 *
 *    Author      : Admin
 *    Date        : 2003/11/14
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2006/06/13    V1.0        DIS.liyh       �C��
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.gyomu.jigyoKanri;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IJigyoCd;
import jp.go.jsps.kaken.model.common.IMaintenanceName;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.SystemException;
import jp.go.jsps.kaken.model.exceptions.ValidationException;
import jp.go.jsps.kaken.model.vo.ErrorInfo;
import jp.go.jsps.kaken.model.vo.JigyoKanriInfo;
import jp.go.jsps.kaken.util.DateUtil;
import jp.go.jsps.kaken.util.FileResource;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;


/**
 * �X�V���ꂽ���ƊǗ����̓��̓`�F�b�N���s���B
 * ���ƊǗ��C�����l�I�u�W�F�N�g���쐬����B
 * �C���m�F��ʂ�\������B 
 * 
 * ID RCSfile="$RCSfile: EditCheckAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:04 $"
 */
public class EditCheckAction extends BaseAction {

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

		//------�C���o�^�t�H�[�����̎擾
		JigyoKanriForm editForm = (JigyoKanriForm) form;
		
		//------�Z�b�V�������X�V�Ώۏ��̎擾
		JigyoKanriInfo editInfo = container.getJigyoKanriInfo();
		if("7".equals(editInfo.getJigyoKubun()))
		{
			if(editForm.getMeiboDateYear()==null||editForm.getMeiboDateYear().length()==0)
			{
				errors.add(null, new ActionError("errors.2002", "�����Җ���o�^�ŏI���ؓ�"));
				saveErrors(request, errors);
				return forwardInput(mapping);
			}

		}
		
		//2005/04/24 �ǉ� ��������------------------------------------------------------
		//���R �\�����e�t�@�C���̕K�{�`�F�b�N�̒ǉ�
		String url = editForm.getDlUrl();
		FormFile win = editForm.getTenpuWinUploadFile();
		
		if((win == null || win.getFileSize() == 0) && (url==null || url.equals(""))){
			errors.add(null, new ActionError("errors.2002", "������e�t�@�C��(WIN)���w�肵�Ȃ��ꍇ�AURL"));
			
			if(editInfo.getTenpuWin() != null && !editInfo.getTenpuWin().equals("")){
				errors.add(null, new ActionError("errors.5037"));
			}
			saveErrors(request, errors);
			return forwardInput(mapping);
		}
		//�ǉ� �����܂�-----------------------------------------------------------------
		
		//-------�� VO�Ƀf�[�^���Z�b�g����B
		try {
			PropertyUtils.copyProperties(editInfo, editForm);
		} catch (Exception e) {
			log.error(e);
			throw new SystemException("�v���p�e�B�̐ݒ�Ɏ��s���܂����B", e);
		}	
		
		try {
			//�T�[�o���̓`�F�b�N
			editInfo =
				getSystemServise(
					IServiceName.JIGYOKANRI_MAINTENANCE_SERVICE).validate(
					container.getUserInfo(),
					editInfo,
					IMaintenanceName.EDIT_MODE);
		} catch (ValidationException e) {
			//�T�[�o�[�G���[��ۑ��B
			saveServerErrors(request, errors, e);
			//---���͓��e�ɕs��������̂ōē���
			return forwardInput(mapping);
		}
		
		//2005/04/24 �ǉ� ��������------------------------------------------------------
		//���R URL��info�Ɋi�[���邽��
		String urlAddress = editForm.getUrlAddress();
		String title = editForm.getUrlTitle();
		if(urlAddress != null && !urlAddress.equals("")){
			editInfo.setUrlAddress(urlAddress);
		}
		if(title != null && !title.equals("")){
			editInfo.setUrlTitle(title);
		}
		//�ǉ� �����܂�-----------------------------------------------------------------

		
		//���ԁi���ԁj���Z�b�g����B�i�u�N�v*12+�u�����v�j
		//------�v���_�E���E���W�I�{�^���Z�b�g
		//�]���p�t�@�C���L�����Z�b�g����B
		editInfo.setHyokaFileFlg(editForm.getHyokaFileFlg());
		
		DateUtil dateUtil = new DateUtil();
		//�w�U��t���ԁi�J�n�j�iString��Date)
		if(editForm.getUketukekikanStartYear().length() != 0){
			dateUtil.setCal(editForm.getUketukekikanStartYear(),editForm.getUketukekikanStartMonth(),editForm.getUketukekikanStartDay());
			editInfo.setUketukekikanStart(dateUtil.getCal().getTime());
		}else{
			editInfo.setUketukekikanStart(null);			
		}
		//�w�U��t���ԁi�I���j�iString��Date)
		if(editForm.getUketukekikanEndYear().length() != 0){
			dateUtil = new DateUtil();
			dateUtil.setCal(editForm.getUketukekikanEndYear(),editForm.getUketukekikanEndMonth(),editForm.getUketukekikanEndDay());
			editInfo.setUketukekikanEnd(dateUtil.getCal().getTime());
		}else{
			editInfo.setUketukekikanEnd(null);			
		}
		//�����Җ���o�^�ŏI���ؓ��iString��Date)�u�N�v��""�łȂ��ꍇ�̂݃Z�b�g
		if(editForm.getMeiboDateYear().length() != 0){
			dateUtil = new DateUtil();
			dateUtil.setCal(editForm.getMeiboDateYear(),editForm.getMeiboDateMonth(),editForm.getMeiboDateDay());
			editInfo.setMeiboDate(dateUtil.getCal().getTime());
		}else{
			editInfo.setMeiboDate(null);
		}
		
		//�@2006/06/13�@�ǉ��@���`�؁@��������
		//���̈�ԍ����s���ؓ�
		if(editForm.getKariryoikiNoEndDateYear().length() != 0 ){
			dateUtil.setCal(editForm.getKariryoikiNoEndDateYear(),editForm.getKariryoikiNoEndDateMonth(),editForm.getKariryoikiNoEndDateDay());
			editInfo.setKariryoikiNoEndDate(dateUtil.getCal().getTime());
		
			//���̈�ԍ����s���ؓ��̃`�F�b�N
			boolean hasErrSaiyo = false;
			int intBefore = 0;
			int intAfter = 0;
			
			// �w�U��t���ԁi�J�n�j�ȑO�̂Ƃ��i�����܂܂Ȃ��j�G���[�B
            if (editForm.getJigyoCd().equals(IJigyoCd.JIGYO_CD_TOKUTEI_SINKI)) {
                intBefore = editInfo.getKariryoikiNoEndDate().compareTo(
                        editInfo.getUketukekikanStart());
                if (intBefore < 0) {
                    hasErrSaiyo = true;
                }
                else {
                    // �w�U��t���ԁi�I���j�Ȍ�̂Ƃ��i�����܂܂Ȃ��j�G���[�B
                    intAfter = editInfo.getKariryoikiNoEndDate().compareTo(
                            editInfo.getUketukekikanEnd());
                    if (hasErrSaiyo == false && intAfter > 0) {
                        hasErrSaiyo = true;
                    }
                }
                if (hasErrSaiyo) {
                    errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.2007",
                            "���̈�ԍ����s���ؓ�", "�w�U��t���ԁi�J�n�j", "�w�U��t���ԁi�I���j"));
                    saveErrors(request, errors);
                    return forwardInput(mapping);
                }
            }
		}else{
			editInfo.setKariryoikiNoEndDate(null);			
		}
		//�@2006/06/13�@�ǉ��@���`�؁@�����܂�
      
		//�@2006/10/23�@�ǉ��@�Ո��@��������
		//���Q�֌W���͒��ؓ�
		if(editForm.getRigaiEndDateYear().length() != 0 ){
			dateUtil = new DateUtil();
			dateUtil.setCal(editForm.getRigaiEndDateYear(),editForm.getRigaiEndDateMonth(),editForm.getRigaiEndDateDay());
			editInfo.setRigaiEndDate(dateUtil.getCal().getTime());
		}else{
			editInfo.setRigaiEndDate(null);
		}
		//�@2006/10/23�@�ǉ��@�Ո��@�����܂�
		
		//�@2006/07/10�@�ǉ��@���`�؁@��������
		//�̈��\�Ҋm����ؓ�
		if(editForm.getRyoikiEndDateYear().length() != 0){
		    dateUtil.setCal(editForm.getRyoikiEndDateYear(),editForm.getRyoikiEndDateMonth(),editForm.getRyoikiEndDateDay());
		    editInfo.setRyoikiEndDate(dateUtil.getCal().getTime());
		    
		    //�̈��\�Ҋm����ؓ��̃`�F�b�N
		    boolean hasErrSaiyo = false;
		    int intBefore = 0;
		    int intAfter = 0;
		    
		    // ���̈�ԍ����s���ؓ��ȑO�̂Ƃ��i�����܂܂Ȃ��j�G���[�B
            if (editForm.getJigyoCd().equals(IJigyoCd.JIGYO_CD_TOKUTEI_SINKI)) {
		        intBefore = editInfo.getRyoikiEndDate().compareTo(editInfo.getKariryoikiNoEndDate());
            
                if (intBefore < 0) {
                    hasErrSaiyo = true;
                }
                else {
                    // �w�U��t���ԁi�I���j�Ȍ�̂Ƃ��i�����܂܂Ȃ��j�G���[�B
                    intAfter = editInfo.getRyoikiEndDate().compareTo(editInfo.getUketukekikanEnd());
                    if (hasErrSaiyo == false && intAfter > 0) {
                        hasErrSaiyo = true;
                    }
                }
                if (hasErrSaiyo) {
                    errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.2007",
                            "�̈��\�Ҋm����ؓ�", "���̈�ԍ����s���ؓ�", "�w�U��t���ԁi�I���j"));
                    saveErrors(request, errors);
                    return forwardInput(mapping);
                }
            }
		}else{
		    editInfo.setRyoikiEndDate(null);			
		}
		//�@2006/07/10�@�ǉ��@���`�؁@�����܂�
		
		//�R�������iString��Date)
		if(editForm.getShinsaKigenYear().length() != 0){
			dateUtil = new DateUtil();
			dateUtil.setCal(editForm.getShinsaKigenYear(),editForm.getShinsaKigenMonth(),editForm.getShinsaKigenDay());
			editInfo.setShinsaKigen(dateUtil.getCal().getTime());
		}else{
			editInfo.setShinsaKigen(null);			
		}
		
		//2005/04/24 �ǉ� ��������------------------------------------------------------
		//���R �_�E�����[�hURL�̒ǉ�
		editInfo.setDlUrl(null);
		
		if((win == null || win.getFileSize() == 0) && url != null && !url.equals("")){	
			editInfo.setDlUrl(url);
		}
		//�ǉ� �����܂�----------------------------------------------------------
		
		//-------�A�b�v���[�h�t�@�C�����Z�b�g
		//---�Y�t�t�@�C���iWin�j
		FormFile tenpuWinFile = editForm.getTenpuWinUploadFile();		
		if(tenpuWinFile != null && tenpuWinFile.getFileSize() != 0){
			FileResource tenpuWinFileRes = createFileResorce(tenpuWinFile);	
			editInfo.setTenpuWinFileRes(tenpuWinFileRes);
		}else{
			// 2005/04/26 �ǉ� ��������----------------------------------------------
			// �V�����t�@�C�����w�肵�Ȃ��Ƃ��A�p�X����ɂ���
			if(editInfo.getTenpuWin() != null && !editInfo.getTenpuWin().equals("")){
				editInfo.setDelWinFileFlg(true);
			}
			// �ǉ� �����܂�---------------------------------------------------------
			editInfo.setTenpuWinFileRes(null);
		}
		//---�Y�t�t�@�C���iMac�j
		FormFile tenpuMacFile = editForm.getTenpuMacUploadFile();
		
		//2005/04/21 �C�� ��������-----------------------------------------------
		//URL���w�肵���ꍇ��MAC�̃t�@�C���𖳎����邽�߁AURL���w�肵�����ǂ����̏�����ǉ�
		if(tenpuMacFile != null && tenpuMacFile.getFileSize() != 0
			&& (editInfo.getDlUrl() == null || editInfo.getDlUrl().equals(""))){
			FileResource tenpuMacFileRes = createFileResorce(tenpuMacFile);	
			editInfo.setTenpuMacFileRes(tenpuMacFileRes);				
		}else{
			// 2005/04/26 �ǉ� ��������----------------------------------------------
			// �V�����t�@�C�����w�肵�Ȃ��Ƃ��A�p�X����ɂ���
			if(editInfo.getTenpuMac() != null && !editInfo.getTenpuMac().equals("")){
				editInfo.setDelMacFileFlg(true);
			}
			// �ǉ� �����܂�---------------------------------------------------------
			editInfo.setTenpuMacFileRes(null);			
		}
		//�C�� �����܂�----------------------------------------------------------

		
		//2005/04/24 �C�� ��������-----------------------------------------------
		//URL���w�肵���ꍇ��MAC�̃t�@�C���𖳎����邽�߁AURL���w�肵�����ǂ����̏�����ǉ�
//		if(tenpuWinFile != null && tenpuWinFile.getFileSize() != 0){
//			if(tenpuMacFile != null && tenpuMacFile.getFileSize() != 0){
//				FileResource tenpuMacFileRes = createFileResorce(tenpuMacFile);	
//				editInfo.setTenpuMacFileRes(tenpuMacFileRes);
//			}else{
//				editInfo.setTenpuMacFileRes(null);
//			}
//		}else{
//			
//			editInfo.setTenpuMacFileRes(null);
//		}
		//�C�� �����܂�----------------------------------------------------------
		
		//---�]���p�t�@�C��
		FormFile hyokaFile = editForm.getHyokaUploadFile();	
		if(hyokaFile != null && hyokaFile.getFileSize() != 0){
			FileResource hyokaFileRes = createFileResorce(hyokaFile);	
			editInfo.setHyokaFileRes(hyokaFileRes);
		}else{
			editInfo.setHyokaFileRes(null);				
		}
		//-------��
		
		//-----�Z�b�V�����Ɏ��ƊǗ�����o�^����B
		container.setJigyoKanriInfo(editInfo);

		//------�C���m�F�t�H�[���ɃZ�b�g����B
		updateFormBean(mapping,request,editForm);
		
		//-----��ʑJ�ځi��^�����j-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		
		//�g�[�N�����Z�b�V�����ɕۑ�����B
		saveToken(request);
		
		return forwardSuccess(mapping);
	}
	
	/**
	 * @param file �A�b�v���[�h�t�@�C��
	 * @return �t�@�C�����\�[�X
	 */
	private FileResource createFileResorce(FormFile file)
								 throws ApplicationException {
		FileResource fileRes = null;
		try{	
			if(file != null){
				fileRes = new FileResource();
				fileRes.setPath(file.getFileName());	//�t�@�C����
				fileRes.setBinary(file.getFileData());	//�t�@�C���T�C�Y
			}
			return fileRes;
		}catch(IOException e){
			throw new ApplicationException(
				"�Y�t�t�@�C���̎擾�Ɏ��s���܂����B",
				new ErrorInfo("errors.7000"),
				e);
		}		
	}
}
