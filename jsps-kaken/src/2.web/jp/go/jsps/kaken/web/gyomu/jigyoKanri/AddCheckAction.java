/*======================================================================
 *    SYSTEM      : ���{�w�p�U����d�q�\���V�X�e���i�Ȋw������⏕���j
 *    Source name : AddCheckAction.java
 *    Description : �V�K�o�^���ꂽ���ƊǗ����̓��̓`�F�b�N���s��
 *
 *    Author      : Admin
 *    Date        : 2003/11/14
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2003/11/14    v1.0        Admin          �V�K�쐬
 *    2006/06/15    v1.1        DIS.liuYang    �ύX
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
import jp.go.jsps.kaken.web.common.LabelValueManager;
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
 * �V�K�o�^���ꂽ���ƊǗ����̓��̓`�F�b�N���s���B
 * ���ƊǗ��o�^���l�I�u�W�F�N�g���쐬����B
 * �o�^�m�F��ʂ�\������B
 * 
 * ID RCSfile="$RCSfile: AddCheckAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:04 $"
 */
public class AddCheckAction extends BaseAction {
	
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
		
		//------�V�K�o�^�t�H�[�����̎擾
		JigyoKanriForm addForm = (JigyoKanriForm) form;

		//-------�� VO�Ƀf�[�^���Z�b�g����B
		JigyoKanriInfo addInfo = new JigyoKanriInfo();			

		//2005/04/21 �ǉ� ��������------------------------------------------------------
		//���R �\�����e�t�@�C���̕K�{�`�F�b�N�̒ǉ�
		String url = addForm.getDlUrl();
		FormFile win = addForm.getTenpuWinUploadFile();
		if((win == null || win.getFileSize() == 0) && (url==null || url.equals(""))){
			errors.add(null, new ActionError("errors.2002", "������e�t�@�C��(WIN)���w�肵�Ȃ��ꍇ�AURL"));
			saveErrors(request, errors);
			return forwardInput(mapping);
		}
		//�ǉ� �����܂�-----------------------------------------------------------------

		//------���ƊǗ����ɏd���`�F�b�N�̃L�[���Z�b�g
		addInfo.setJigyoName(LabelValueManager.getJigyoNameList(addForm.getJigyoCd()));		//���Ɩ�
		addInfo.setNendo(addForm.getNendo());		//�N�x
		addInfo.setKaisu(addForm.getKaisu());		//��	
		
		//���ƃR�[�h�i���ƃ}�X�^�����p�E����ID�쐬�p�j���Z�b�g����B
		addInfo.setJigyoCd(addForm.getJigyoCd());	
		
		try {
			//�T�[�o���̓`�F�b�N
			addInfo =
				getSystemServise(
					IServiceName.JIGYOKANRI_MAINTENANCE_SERVICE).validate(
					container.getUserInfo(),
					addInfo,
					IMaintenanceName.ADD_MODE);
		} catch (ValidationException e) {
			//�T�[�o�[�G���[��ۑ��B
			saveServerErrors(request, errors, e);
			//�t�@�C���ēx�I���G���[��ǉ�
			errors.add(
				ActionErrors.GLOBAL_ERROR,
				new ActionError("errors.2009"));
			//---���͓��e�ɕs��������̂ōē���
			return forwardInput(mapping);
		}

		try {
			PropertyUtils.copyProperties(addInfo, addForm);
		} catch (Exception e) {
			log.error(e);
			throw new SystemException("�v���p�e�B�̐ݒ�Ɏ��s���܂����B", e);
		}
	
		//2005/04/21 �ǉ� ��������------------------------------------------------------
		//���R URL��info�Ɋi�[���邽��
		if((win == null || win.getFileSize() == 0) && url != null && !url.equals("")){
			addInfo.setDlUrl(url);
		}else{
			addInfo.setDlUrl(null);
		}
		
		String urlAddress = addForm.getUrlAddress();
		String title = addForm.getUrlTitle();
		if(urlAddress != null && !urlAddress.equals("")){
			addInfo.setUrlAddress(urlAddress);
		}
		if(title != null && !title.equals("")){
			addInfo.setUrlTitle(title);
		}
		//�ǉ� �����܂�-----------------------------------------------------------------

		//���ԁi���ԁj���Z�b�g����B�i�u�N�v*12+�u�����v�j
		//------String��Date�ϊ�
		DateUtil dateUtil = new DateUtil();
		//�w�U��t���ԁi�J�n�j�iString��Date)
		if(addForm.getUketukekikanStartYear().length() != 0){
			dateUtil.setCal(addForm.getUketukekikanStartYear(),
                    addForm.getUketukekikanStartMonth(),
                    addForm.getUketukekikanStartDay());
			addInfo.setUketukekikanStart(dateUtil.getCal().getTime());
		}
		//�w�U��t���ԁi�I���j�iString��Date)
		if(addForm.getUketukekikanEndYear().length() != 0){
			dateUtil = new DateUtil();
			dateUtil.setCal(addForm.getUketukekikanEndYear(),
                    addForm.getUketukekikanEndMonth(),
                    addForm.getUketukekikanEndDay());
			addInfo.setUketukekikanEnd(dateUtil.getCal().getTime());
		}	
		//�����Җ���o�^�ŏI���ؓ��iString��Date)�u�N�v��""�łȂ��ꍇ�̂݃Z�b�g
		if(addForm.getMeiboDateYear().length() != 0){
			dateUtil = new DateUtil();
			dateUtil.setCal(addForm.getMeiboDateYear(),
                    addForm.getMeiboDateMonth(),
                    addForm.getMeiboDateDay());
			addInfo.setMeiboDate(dateUtil.getCal().getTime());
		}
		// 2006/06/23 �ǉ�  �Ո��@��������
	    // ���Q�֌W���͒��ؓ��ǉ�
	    if(addForm.getRigaiEndDateYear().length() != 0){
	    	dateUtil = new DateUtil();
	    	dateUtil.setCal(addForm.getRigaiEndDateYear(),
	    			addForm.getRigaiEndDateMonth(),
	    			addForm.getRigaiEndDateDay());
	    	addInfo.setRigaiEndDate(dateUtil.getCal().getTime());
	    }
	    // 2006/06/23 �ǉ�  �Ո��@�����܂�


//2006/06/15 Add by liuYang start
		//���̈�ԍ����s���ؓ�
		if(addForm.getKariryoikiNoEndDateYear().length() != 0) {
			dateUtil.setCal(addForm.getKariryoikiNoEndDateYear(),
                            addForm.getKariryoikiNoEndDateMonth(),
                            addForm.getKariryoikiNoEndDateDay());
			addInfo.setKariryoikiNoEndDate(dateUtil.getCal().getTime());
		
			//���̈�ԍ����s���ؓ�
			boolean hasErrSaiyo = false;
			int iBefore = 0;
			int iAfter = 0;
			
			// �w�U��t���ԁi�J�n�j
            if (addForm.getJigyoCd().equals(IJigyoCd.JIGYO_CD_TOKUTEI_SINKI)) {
    			iBefore = addInfo.getKariryoikiNoEndDate()
                                 .compareTo(addInfo.getUketukekikanStart());
                if (iBefore < 0) {
                    hasErrSaiyo = true;
                }
                else {
                    // �w�U��t���ԁi�I���j
                    iAfter = addInfo.getKariryoikiNoEndDate()
                                    .compareTo(addInfo.getUketukekikanEnd());
                    if (hasErrSaiyo == false && iAfter > 0) {
                        hasErrSaiyo = true;
                    }
                }
                if (hasErrSaiyo) {
                    errors.add(ActionErrors.GLOBAL_ERROR,
                            new ActionError("errors.2007", "���̈�ԍ����s���ؓ�",
                                    "�w�U��t���ԁi�J�n�j", "�w�U��t���ԁi�I���j"));
                    saveErrors(request, errors);
                    return forwardInput(mapping);
                }
            }
        }
        else {
            addInfo.setKariryoikiNoEndDate(null);
        }
//2006/06/15 Add liuYang end
        
//�@2006/07/10�@�ǉ��@���`�؁@��������Ryoiki  �̈��\�Ҋm����ؓ�
        //�̈��\�Ҋm����ؓ�
        if(addForm.getRyoikiEndDateYear().length() != 0) {
            dateUtil.setCal(addForm.getRyoikiEndDateYear(),
                            addForm.getRyoikiEndDateMonth(),
                            addForm.getRyoikiEndDateDay());
            addInfo.setRyoikiEndDate(dateUtil.getCal().getTime());
        
            //���̈�ԍ����s���ؓ��ȑO�̂Ƃ��i�����܂܂Ȃ��j�G���[�B
            boolean hasErrSaiyo = false;
            int iBefore = 0;
            int iAfter = 0;
            
            // ���̈�ԍ����s���ؓ�
            if (addForm.getJigyoCd().equals(IJigyoCd.JIGYO_CD_TOKUTEI_SINKI)) {
                iBefore = addInfo.getRyoikiEndDate()
                                 .compareTo(addInfo.getKariryoikiNoEndDate());
                if (iBefore < 0) {
                    hasErrSaiyo = true;
                }
                else {
                    // �w�U��t���ԁi�I���j�Ȍ�̂Ƃ��i�����܂܂Ȃ��j�G���[�B
                    iAfter = addInfo.getRyoikiEndDate()
                                    .compareTo(addInfo.getUketukekikanEnd());
                    if (hasErrSaiyo == false && iAfter > 0) {
                        hasErrSaiyo = true;
                    }
                }
                if (hasErrSaiyo) {
                    errors.add(ActionErrors.GLOBAL_ERROR,
                            new ActionError("errors.2007", "�̈��\�Ҋm����ؓ�",
                                    "���̈�ԍ����s���ؓ�", "�w�U��t���ԁi�I���j"));
                    saveErrors(request, errors);
                    return forwardInput(mapping);
                }
            }
        }
        else {
            addInfo.setRyoikiEndDate(null);
        }
//�@2006/07/10�@�ǉ��@���`�؁@�����܂�
		
		
		//�R�������iString��Date)�u�N�v��""�łȂ��ꍇ�̂݃Z�b�g
		if(addForm.getShinsaKigenYear().length() != 0){
			dateUtil = new DateUtil();
			dateUtil.setCal(addForm.getShinsaKigenYear(),
                            addForm.getShinsaKigenMonth(),
                            addForm.getShinsaKigenDay());
			addInfo.setShinsaKigen(dateUtil.getCal().getTime());
		}

		//-------�A�b�v���[�h�t�@�C�����Z�b�g
		//---�Y�t�t�@�C���iWin�j
		FormFile tenpuWinFile = addForm.getTenpuWinUploadFile();		
		if(tenpuWinFile != null && tenpuWinFile.getFileSize() != 0){
			FileResource tenpuWinFileRes = createFileResorce(tenpuWinFile);	
			addInfo.setTenpuWinFileRes(tenpuWinFileRes);
		}else{
			addInfo.setTenpuWinFileRes(null);			
		}
		//---�Y�t�t�@�C���iMac�j
		FormFile tenpuMacFile = addForm.getTenpuMacUploadFile();
		//2005/04/21 �C�� ��������-----------------------------------------------
		//URL���w�肵���ꍇ��MAC�̃t�@�C���𖳎����邽�߁AURL���w�肵�����ǂ����̏�����ǉ�
		if(tenpuMacFile != null && tenpuMacFile.getFileSize() != 0
			    && (addInfo.getDlUrl() == null || addInfo.getDlUrl().equals(""))){
			FileResource tenpuMacFileRes = createFileResorce(tenpuMacFile);	
			addInfo.setTenpuMacFileRes(tenpuMacFileRes);				
		}else{
			addInfo.setTenpuMacFileRes(null);			
		}
		//�C�� �����܂�----------------------------------------------------------
		
		//---�]���p�t�@�C��
		FormFile hyokaFile = addForm.getHyokaUploadFile();	
		if(hyokaFile != null && hyokaFile.getFileSize() != 0){
			FileResource hyokaFileRes = createFileResorce(hyokaFile);	
			addInfo.setHyokaFileRes(hyokaFileRes);
		}else{
			addInfo.setHyokaFileRes(null);			
		}
		//-------��

		//-----��ʑJ�ځi��^�����j-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);	
			return forwardFailure(mapping);
		}

		//-----�Z�b�V�����ɐ\���ҏ���o�^����B
		container.setJigyoKanriInfo(addInfo);

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