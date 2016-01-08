/*======================================================================
 *    SYSTEM      : ���{�w�p�U����d�q�\���V�X�e���i�Ȋw������⏕���j
 *    Source name : RegistGaiyoApplicationAction
 *    Description : �̈�v�揑�ꎞ�ۑ�
 *
 *    Author      : �c
 *    Date        : 2006/06/28
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2006/06/28    v1.0        �c�c�@�@�@�@               �V�K�쐬�@�@�@�@�@�@ �@ 
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.shinsei;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import jp.go.jsps.kaken.model.ISystemServise;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.ValidationException;
import jp.go.jsps.kaken.model.vo.ErrorInfo;
import jp.go.jsps.kaken.model.vo.RyoikiKeikakushoInfo;
import jp.go.jsps.kaken.model.vo.RyoikiKeikakushoPk;
import jp.go.jsps.kaken.util.FileResource;
import jp.go.jsps.kaken.web.common.LabelValueBean;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

/**
 * �̈�v�揑�ꎞ�ۑ��A�N�V�����N���X�B
 * �̈�v�揑�����f�[�^�x�[�X�ɓo�^����B
 * ����������ɏI�������ꍇ�A�ꎞ�ۑ�������ʂ�Ԃ��B
 */
public class TransientSaveGaiyoApplicationAction extends BaseAction{

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
        
        //-----�̈�v�揑���̓t�H�[���̎擾
        RyoikiGaiyoForm ryoikiGaiyoForm = (RyoikiGaiyoForm)form;

        //-----���ۑ����\�b�h���Ăяo��
        try{
            transientSave(container, ryoikiGaiyoForm);
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

        // -----�ꎞ�ۑ�������ʂɂāASYSTEMNO���K�v�Ȃ��߃��N�G�X�g�ɃZ�b�g
        RyoikiKeikakushoPk ryoikikeikakushoPk = new RyoikiKeikakushoPk(ryoikiGaiyoForm
                .getRyoikikeikakushoInfo().getRyoikiSystemNo());
        // -----SYSTEMNO�����N�G�X�g�����ɃZ�b�g
        request.setAttribute("ryoikikeikakushoPk", ryoikikeikakushoPk);

        //------�g�[�N���̍폜 
        resetToken(request);
        
        //------�t�H�[�����̍폜
//        removeFormBean(mapping,request);        

        return forwardSuccess(mapping);
    }
    
    
    /**
     * �̈�v�揑�����ۑ�����B
     * @param container ���O�C���\���ҏ��
     * @param ryoikiGaiyoForms      ���̓t�H�[���f�[�^
     * @throws ValidationException  �f�[�^�`�F�b�N�G���[�����������ꍇ
     * @throws ApplicationException ���ۑ��Ɏ��s�����ꍇ
     */
    private void transientSave(UserContainer container, RyoikiGaiyoForm ryoikiGaiyoForm)
            throws ValidationException, ApplicationException {
        //�Y�t�t�@�C��
        FileResource annexFileRes = null;
        try{
            FormFile file = ryoikiGaiyoForm.getUploadFile();
            if(file != null &&
               file.getFileData() != null && 
               file.getFileData().length != 0)
            {
                annexFileRes = new FileResource();
                annexFileRes.setPath(file.getFileName());   //�t�@�C����
                annexFileRes.setBinary(file.getFileData()); //�t�@�C���T�C�Y
            }
        }catch(IOException e){
            throw new ApplicationException(
                "�Y�t�t�@�C���̎擾�Ɏ��s���܂����B",
                new ErrorInfo("errors.7001"),
                e);
        }
        
        //�̈�v�揑�i�T�v�j���̎擾
        RyoikiKeikakushoInfo ryoikikeikakushoInfo = ryoikiGaiyoForm.getRyoikikeikakushoInfo();
        
        //�����̕K�v���̒l�̃��X�g���擾����
        List kenkyuHitsuyouseiValues = ryoikiGaiyoForm.getValuesList();
        
        //�����̕K�v���������l�ݒ肷��
        ryoikikeikakushoInfo.setKenkyuHitsuyousei1("0");
        ryoikikeikakushoInfo.setKenkyuHitsuyousei2("0");
        ryoikikeikakushoInfo.setKenkyuHitsuyousei3("0");
        ryoikikeikakushoInfo.setKenkyuHitsuyousei4("0");
        ryoikikeikakushoInfo.setKenkyuHitsuyousei5("0");
        
        //�����̕K�v���F�`�F�b�N�ς݂̏ꍇ�́u1�v��ݒ肷��
        if (kenkyuHitsuyouseiValues.contains("1")) {
            ryoikikeikakushoInfo.setKenkyuHitsuyousei1("1");
        } 
        if (kenkyuHitsuyouseiValues.contains("2")) {
            ryoikikeikakushoInfo.setKenkyuHitsuyousei2("1");
        } 
        if (kenkyuHitsuyouseiValues.contains("3")) {
            ryoikikeikakushoInfo.setKenkyuHitsuyousei3("1");
        }
        if (kenkyuHitsuyouseiValues.contains("4")) {
            ryoikikeikakushoInfo.setKenkyuHitsuyousei4("1");
        }  
        if (kenkyuHitsuyouseiValues.contains("5")) {
            ryoikikeikakushoInfo.setKenkyuHitsuyousei5("1");
        }
        
        //�֘A����P�T���ށi���ށj���X�g
        List kanrenbunyaBunruiList = ryoikiGaiyoForm.getKanrenbunyaBunruiList();
        //�֘A����P�T���ށi�ԍ��j
        String kanrenbunyaBunruiNo = ryoikikeikakushoInfo.getKanrenbunyaBunruiNo();        
        
        //�֘A����15���ށi���ޖ��j��ݒ肷��
        for(int i = 0 ; i < kanrenbunyaBunruiList.size() ; i++){
            LabelValueBean kanrenbunyaBunrui = (LabelValueBean) kanrenbunyaBunruiList.get(i);
            if(kanrenbunyaBunruiNo.equals(kanrenbunyaBunrui.getValue())){
                ryoikikeikakushoInfo.setKanrenbunyaBunruiName(kanrenbunyaBunrui.getLabel());
            }
        }
        
        //�R����]����i�n���j���X�g
        List kiboubumonList = ryoikiGaiyoForm.getKiboubumonList();
        //�R����]����i�n���j�R�[�h
        String  kiboubumonNo = ryoikikeikakushoInfo.getKiboubumonCd();
        //�R����]����i�n���j����ݒ肷��
        for(int i = 0 ; i < kiboubumonList.size() ; i++){
            LabelValueBean kiboubumon = (LabelValueBean) kiboubumonList.get(i);
            if(kiboubumonNo.equals(kiboubumon.getValue())){
                ryoikikeikakushoInfo.setKiboubumonName(kiboubumon.getLabel());
            }
        }

        ryoikiGaiyoForm.outputFileInfo();

        //-----�V�K���X�V������i��t�ԍ����Z�b�g����Ă��邩�ǂ����j
        String uketukeNo = ryoikikeikakushoInfo.getUketukeNo();
        if(uketukeNo == null || uketukeNo.length() == 0){
            //�T�[�o�T�[�r�X�̌Ăяo���i�ꎞ�ۑ��j
            ISystemServise servise = getSystemServise(
                                IServiceName.SHINSEI_MAINTENANCE_SERVICE);
            RyoikiKeikakushoInfo newInfo = servise.transientGaiyoApplicationNew(
                                            container.getUserInfo(),
                                            ryoikikeikakushoInfo,
                                            annexFileRes);
            //�t�H�[���ɓo�^���ꂽ�\���f�[�^���Z�b�g����
            ryoikiGaiyoForm.setRyoikikeikakushoInfo(newInfo);
        } else {
            ISystemServise servise = getSystemServise(
                                IServiceName.SHINSEI_MAINTENANCE_SERVICE);
            servise.transientGaiyoApplicationUpdate(
                                            container.getUserInfo(),
                                            ryoikikeikakushoInfo,
                                            annexFileRes);
        }
    }
}
