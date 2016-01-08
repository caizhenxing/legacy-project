/*======================================================================
 *    SYSTEM      : ���{�w�p�U����d�q�\���V�X�e���i�Ȋw������⏕���j
 *    Source name : RyoikiGaiyoForm.java
 *    Description : �̈�v�揑�쐬�E�̈�v�揑��o�m�F�p�t�H�[��
 *
 *    Author      : �c�c
 *    Date        : 2006/06/19
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2006/06/19    v1.0        �c�c                        �V�K�쐬
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.shinsei;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import jp.go.jsps.kaken.model.common.ApplicationSettings;
import jp.go.jsps.kaken.model.common.ISettingKeys;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.ErrorInfo;
import jp.go.jsps.kaken.model.vo.JigyoKanriInfo;
import jp.go.jsps.kaken.model.vo.RyoikiKeikakushoInfo;
import jp.go.jsps.kaken.model.vo.ShinseiDataInfo;
import jp.go.jsps.kaken.util.StringUtil;
import jp.go.jsps.kaken.web.struts.BaseValidatorForm;

/**
 * �̈�v�揑�쐬�E�̈�v�揑��o�m�F�p�t�H�[��
 */
public class RyoikiGaiyoForm extends BaseValidatorForm{
    
    // ---------------------------------------------------------------------
    // Static data
    // ---------------------------------------------------------------------

    /** ���O */
    protected static Log log = LogFactory.getLog(RyoikiGaiyoForm.class);

    /** �Y�t�t�@�C�����M�����E�\���o�^�������������s�񐔁i1�b�Ԋu�j */
    private static int    TRY_COUNT_SYNCHRONIZE  = ApplicationSettings.getInt(ISettingKeys.TRY_COUNT_SYNCHRONIZE);
    
    // ---------------------------------------------------------------------
    // Instance data
    // ---------------------------------------------------------------------
//  <!-- ADD�@START 2007/07/19 BIS ���� -->
    /** �G���[���b�Z�W�[ */
    private List errorsList =  new ArrayList(); ;
//  <!-- ADD�@END�@ 2007/07/19 BIS ���� -->    
    
    /** �J�ډ�ʃt���O */
    private String screenFlg;

    /** �V�X�e����t�ԍ�(�̈�) */
    private String ryoikiSystemNo;

    /** �V�X�e����t�ԍ� */
    private String systemNo;

    /** ���̈�ԍ� */
    private String kariryoikiNo;
    
    /** ����ID */
    private String jigyoId;

    /** �̈�v�揑�i�T�v�j��� */
    private RyoikiKeikakushoInfo ryoikikeikakushoInfo = new RyoikiKeikakushoInfo();
    
    /** �\���f�[�^��� */
    private ShinseiDataInfo shinseiDataInfo = new ShinseiDataInfo();
    
    //2006/06/20 lwj add begin
    /** ���ƊǗ��f�[�^��� */
    private JigyoKanriInfo jigyoKanriInfo = new JigyoKanriInfo();
    //2006/06/20 lwj add end
    
    /** �R����]���僊�X�g */
    private List kiboubumonList = new ArrayList(); 
    
    /** ���O�������X�g */
    private List jizenchousaList = new ArrayList();
    
    /** �����̕K�v�����X�g */
    private List kenkyuHitsuyouseiList = new ArrayList();
    
    /** �����̕K�v���̒l�̃��X�g */
    private List values = new ArrayList();

// 2006/08/10 dyh delete start
//    /** �����̕K�v���̃`�F�b�N�̃��X�g */
//    private List checkedList = new ArrayList();
// 2006/08/10 dyh delete end

    /** 15���ރ��X�g */
    private List kanrenbunyaBunruiList = new ArrayList();
    
    /** �����̈�ŏI�N�x�O�N�x�̉���L�����X�g */
    private List zenNendoOuboFlgList = new ArrayList();
    
    /** �Y�t�t�@�[�����X�g */
    private List tenpuFileList = new ArrayList();
  
    /** �E�����X�g */
    private List shokushuList = new ArrayList();
    
    /** �A�b�v���[�h�t�@�C���A�N�V�����Ƃ̓������t���O */
    private boolean uploadActionEnd = false;
    
    /** �A�b�v���[�h�t�@�C�� */
    private FormFile uploadFile = null;

//  2006/06/24 �����@�ǉ���������
    /** �����g�D�o��v�l */
    private List keihi = new ArrayList();
    
    /** �����g�D�o��v�l */
    private List keihiTotal = new ArrayList();
    
    /** ���匤���o��v�l */
    private List kenkyuSyokeiTotal = new ArrayList();
//  2006/06/24 �����@�ǉ������܂�    
    
    // ---------------------------------------------------------------------
    // Constructors
    // ---------------------------------------------------------------------

    /**
     * �R���X�g���N�^�B
     */
    public RyoikiGaiyoForm() {
        super();
        init();
    }
    
    // ---------------------------------------------------------------------
    // Private methods
    // ---------------------------------------------------------------------
    /**
     * ����������
     */
    public void init() {
        values = new ArrayList();
// 2006/08/10 dyh delete start
//        for(int i = 0; i < 5 ; i++){
//            checkedList.add(i,"00");
//        }
// 2006/08/10 dyh delete end
    }

    /* 
     * �����������B
     * (�� Javadoc)
     * @see org.apache.struts.action.ActionForm#reset(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
     */
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
        //�E�E�E
    }

    /*
     * ���̓`�F�b�N�B (�� Javadoc)
     * 
     * @see org.apache.struts.action.ActionForm#validate(org.apache.struts.action.ActionMapping,javax.servlet.http.HttpServletRequest)
     */
    public ActionErrors validate(ActionMapping mapping,
            HttpServletRequest request) {
        
        // ActionMapping�p�����[�^��"synchronized"���w�肳��Ă����ꍇ�̂�
        if ("synchronized".equals(mapping.getParameter())) {
            // �A�b�v���[�h�t�@�C���A�N�V�����Ƃ̓�����
            if (!synchronizedUploadFileAction()) {
                log.error("UploadFileAction�Ƃ̓������ɂ����ă^�C���A�E�g���������܂����B");
                ActionErrors errors = new ActionErrors();
                errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
                        "errors.timeout"));
                return errors;
            }
        }

         // ��^����-----
        ActionErrors errors = super.validate(mapping, request);

//ADD�@START 2007-07-25 BIS ������
        //�u�̈�v�揑���́v�́u�����o��\�v��ʂŃG���[�ɂȂ������ڂ̃G���[���b�Z�[�W��ǉ�����
        {
	        HashMap errorMap = new HashMap();
        	String property = null;
	        for(int i=2;i<7;i++){
	        	property = "ryoikikeikakushoInfo.kenkyuSyokei" + i;
	        	if(errors.get(property).hasNext()){
	        		errorMap.put(property,"�����o��v " + i + "�N�� �Ⴂ�܂��B ");
	        	}
	        	property = "ryoikikeikakushoInfo.kenkyuUtiwake" + i;
	        	if(errors.get(property).hasNext()){
	        		errorMap.put(property,"�����o����� " + i + "�N�� �Ⴂ�܂��B ");
	        	}
	        }
	        ryoikikeikakushoInfo.setErrorsMap( errorMap);
        }
//ADD�@END 2007-07-25 BIS ������
        // ---------------------------------------------
        // ��{�I�ȃ`�F�b�N(�K�{�A�`�����j��Validator���g�p����B
        
//      <!-- ADD�@START 2007/07/26 BIS ���� -->
        //����̈於�S�p�����̃`�F�b�N
        String ryoikeName = ryoikikeikakushoInfo.getRyoikiName();
        if(!StringUtil.isBlank(ryoikeName)){
            ryoikeName = ryoikeName.replaceAll("[\r\n]", "");  //���s�͍폜����
            if(!StringUtil.isZenkaku(ryoikeName)){
                ActionError error = new ActionError("errors.mask_zenkaku","����̈於");
                errors.add("ryoikikeikakushoInfo.ryoikiName", error);
            }
        }
//      <!-- ADD�@END�@ 2007/07/26 BIS ���� -->

        
        //����̈於�i�p�󖼁j�̃`�F�b�N
        String ryoikeNameEigo = ryoikikeikakushoInfo.getRyoikiNameEigo();
        if(!StringUtil.isBlank(ryoikeNameEigo)){
            ryoikeNameEigo = ryoikeNameEigo.replaceAll("[\r\n]", "");  //���s�͍폜����
            if(ryoikeNameEigo.length() > 200){
                ActionError error = new ActionError("errors.maxlength","����̈於�i�p�󖼁j","200");
                errors.add("ryoikikeikakushoInfo.ryoikiNameEigo", error);
            }
        }
        
        //����̈�̌����T�v�̃`�F�b�N
        String kenkyuGaiyou = ryoikikeikakushoInfo.getKenkyuGaiyou();
        if(!StringUtil.isBlank(kenkyuGaiyou)){
            kenkyuGaiyou = kenkyuGaiyou.replaceAll("[\r\n]", "");  //���s�͍폜����
            if(kenkyuGaiyou.length() > 400){
                ActionError error = new ActionError("errors.maxlength","����̈�̌����T�v","400");
                errors.add("ryoikikeikakushoInfo.kenkyuGaiyou", error);
            }
        }

// 2006/08/07 dyh add start ���R�F�d�l�ύX
        //���������E���O�����̏󋵂̃`�F�b�N
        String jizenchousaFlg = ryoikikeikakushoInfo.getJizenchousaFlg();
        if(!StringUtil.isBlank(jizenchousaFlg) && !"3".equals(jizenchousaFlg)){
            if(!StringUtil.isBlank(ryoikikeikakushoInfo.getJizenchousaSonota())){
                errors.add("ryoikikeikakushoInfo.jizenchousaFlg",
                        new ActionError("errors.5068", new String[] {
                                "���������E���O�����̏�",
                                "�u���̑��v�ȊO",
                                "���������E���O�����̏󋵁i���̑��j"}));
            }
        }
// 2006/08/07 dyh add end

        //�ߋ��̓���̈�̉���󋵂̃`�F�b�N
        String kakoOubojyoukyou = ryoikikeikakushoInfo.getKakoOubojyoukyou();
        if(!StringUtil.isBlank(kakoOubojyoukyou)){
            kakoOubojyoukyou = kakoOubojyoukyou.replaceAll("[\r\n]", "");  //���s�͍폜����
            if(kakoOubojyoukyou.length() > 100){
                ActionError error = new ActionError("errors.maxlength","�ߋ��̓���̈�̉����","100");
                errors.add("ryoikikeikakushoInfo.kakoOubojyoukyou", error);
            }
        }

// 2006/08/07 dyh add start ���R�F�d�l�ύX
        //�����̈�ŏI�N�x�O�N�x�̉���̃`�F�b�N(�����̈�ŏI�N�x�O�N�x�̉��傪2:���̏ꍇ�A�󗓂łȂ���΃G���[)
        if("2".equals(ryoikikeikakushoInfo.getZennendoOuboFlg())){
            if(!StringUtil.isBlank(ryoikikeikakushoInfo.getZennendoOuboNo())){
                errors.add("ryoikikeikakushoInfo.zennendoOuboNo",
                        new ActionError("errors.5068", new String[] {
                                "�Y���̗L��",
                                "�u���v",
                                "�ŏI�N�x�O�N�x�ɂ�����̈�ԍ�"}));
            }
        }
// 2006/08/07 dyh add end

// 2006/08/10 dyh update start
        //�����̕K�v���̃`�F�b�N
        if(page == 2 && (values == null || values.size() == 0)){
            ActionError error = new ActionError("errors.2002", "�����̕K�v��");
            errors.add("ryoikikeikakushoInfo.kenkyuHitsuyousei", error);
        }
//        List checkedList = this.getCheckedList();
//        boolean isChecked = false;
//        for(int i = 0 ; i< checkedList.size(); i++){
//            String checked = StringUtils.right((String)checkedList.get(i),1);
//            if(checked.equals("1")){
//                isChecked = true;
//            }
//        } 
//        
//        if (!isChecked && page == 2) {
//            valuesList = new ArrayList();
//            for (int i = 0; i < checkedList.size(); i++) {
//                if (StringUtils.right((String)checkedList.get(i),1).equals("1")) {
//                    valuesList.add(String.valueOf(StringUtils.left((String)checkedList.get(i),1)));
//                }
//            }
//            ActionError error = new ActionError("errors.2002", "�����̕K�v��");
//            errors.add("ryoikikeikakushoInfo.kenkyuHitsuyousei", error);
//        } else {
//            valuesList = new ArrayList();
//            for (int i = 0; i < checkedList.size(); i++) {
//                if (StringUtils.right((String)checkedList.get(i),1).equals("1")) {
//                    valuesList.add(String.valueOf(StringUtils.left((String)checkedList.get(i),1)));
//                }
//            }
//        }
// 2006/08/10 dyh update end

        //�Y�t�t�@�C���L���̃`�F�b�N
        if("0".equals(ryoikikeikakushoInfo.getTenpuFileFlg())){
            if(uploadFile != null &&  uploadFile.getFileSize() != 0){
                ActionError error = new ActionError("errors.5063");
                errors.add("uploadFile", error); 
            }
        }
 
//		ADD�@START 2007-07-19 BIS ������
		//�g�ݍ��킹�̃G���[��ǉ�����
		addKumiaiError(errors);
//		ADD�@END 2007-07-19 BIS ������
        
        // ��{���̓`�F�b�N�����܂�
        if (!errors.isEmpty()) {
            return errors;
        }

        // ��^����-----

        // �ǉ�����-----

        // ---------------------------------------------
        // �g�ݍ��킹�`�F�b�N
        // ---------------------------------------------

        return errors;
    }
    
    /**
     * �A�b�v���[�h�t�@�C���A�N�V�����Ƃ̓��������s���B �A�b�v���[�h�t���O��true�ɂȂ�܂ŏ����𒆒f����B
     * �t���O��false�̏ꍇ��1�b��ɍēx�m�F���s���B TRY_COUNT_SYNCHRONIZE�Őݒ肳��Ă���񐔕����s���Ă�
     * �����ꍇ�̓^�C���A�E�g�Ƃ݂Ȃ��B
     * 
     * @return boolean �^�C���A�E�g�����������ꍇfalse
     */
    private boolean synchronizedUploadFileAction() {

        log.info("�t�@�C���A�b�v���[�h�������J�n");

        long sleepTime = 1000;
        try {
            for (int i = 0; i < TRY_COUNT_SYNCHRONIZE; i++) {
                if (isUploadActionEnd()) {
                    return true; // �A�b�v���[�h�A�N�V�������������Ă����ꍇ��return����B
                } else {
                    try {
                        Thread.sleep(sleepTime);
                    } catch (InterruptedException e) {
                    }
                }
            }
            return false;
        } finally {
            setUploadActionEnd(false); // �t���O�̏�����
        }
    }
    
    /**
     * �Y�t�t�@�C�����o�́B
     * @throws ApplicationException
     */
    public void outputFileInfo() throws ApplicationException {

        log.info("���[�UID=" + shinseiDataInfo.getShinseishaId());
        if (uploadFile == null) {
            log.info("�Y�t�t�@�C���Ȃ�");
        } else {
            try {
                log.info("�t�@�C����=" + uploadFile.getFileName());
                log.info("�t�@�C���T�C�Y=" + uploadFile.getFileData().length);
            } catch (IOException e) {
                throw new ApplicationException("�Y�t�t�@�C���̎擾�Ɏ��s���܂����B",
                        new ErrorInfo("errors.7000"), e);
            }
        }
    }

    /**
     * �̈�v�揑�i�T�v�j�����擾
     * @return RyoikiKeikakushoInfo �̈�v�揑�i�T�v�j����Ԃ�
     */
    public RyoikiKeikakushoInfo getRyoikikeikakushoInfo() {
        return ryoikikeikakushoInfo;
    }

    /**
     * �̈�v�揑�i�T�v�j����ݒ�
     * @param ryoikikeikakushoInfo �̈�v�揑�i�T�v�j�����Z�b�g����
     */
    public void setRyoikikeikakushoInfo(RyoikiKeikakushoInfo ryoikikeikakushoInfo) {
        this.ryoikikeikakushoInfo = ryoikikeikakushoInfo;
    }

    /**
     * �\���f�[�^�����擾
     * @return ShinseiDataInfo �\���f�[�^����Ԃ�
     */
    public ShinseiDataInfo getShinseiDataInfo() {
        return shinseiDataInfo;
    }

    /**
     * �\���f�[�^����ݒ�
     * @param shinseiDataInfo �\���f�[�^�����Z�b�g����
     */
    public void setShinseiDataInfo(ShinseiDataInfo shinseiDataInfo) {
        this.shinseiDataInfo = shinseiDataInfo;
    }

    /**
     * �������t���O���擾
     * @return boolean �A�b�v���[�h�t�@�C���A�N�V�����Ƃ̓������t���O��Ԃ�
     */
    public boolean isUploadActionEnd() {
        return uploadActionEnd;
    }

    /**
     * �������t���O��ݒ�
     * @param uploadActionEnd �A�b�v���[�h�t�@�C���A�N�V�����Ƃ̓������t���O���Z�b�g����
     */
    public void setUploadActionEnd(boolean uploadActionEnd) {
        this.uploadActionEnd = uploadActionEnd;
    }

    /**
     * �A�b�v���[�h�t�@�C�����擾
     * @return FormFile �A�b�v���[�h�t�@�C����Ԃ�
     */
    public FormFile getUploadFile() {
        return uploadFile;
    }

    /**
     * �A�b�v���[�h�t�@�C����ݒ�
     * @param uploadFile �A�b�v���[�h�t�@�C�����Z�b�g����
     */
    public void setUploadFile(FormFile uploadFile) {
        this.uploadFile = uploadFile;
    }

    /**
     * ���ƊǗ��f�[�^�����擾
     * @return JigyoKanriInfo ���ƊǗ��f�[�^���
     */
    public JigyoKanriInfo getJigyoKanriInfo() {
        return jigyoKanriInfo;
    }

    /**
     * ���ƊǗ��f�[�^����ݒ�
     * @param jigyoKanriInfo ���ƊǗ��f�[�^���
     */
    public void setJigyoKanriInfo(JigyoKanriInfo jigyoKanriInfo) {
        this.jigyoKanriInfo = jigyoKanriInfo;
    }

    /**
     * ���̈�ԍ����擾
     * @return String ���̈�ԍ�
     */
    public String getKariryoikiNo() {
        return kariryoikiNo;
    }

    /**
     * ���̈�ԍ���ݒ�
     * @param kariryoikiNo ���̈�ԍ�
     */
    public void setKariryoikiNo(String kariryoikiNo) {
        this.kariryoikiNo = kariryoikiNo;
    }

    /**
     * ���O�������X�g���擾
     * @return List ���O�������X�g
     */
    public List getJizenchousaList() {
        return jizenchousaList;
    }

    /**
     * ���O�������X�g��ݒ�
     * @param jizenchousaList ���O�������X�g
     */
    public void setJizenchousaList(List jizenchousaList) {
        this.jizenchousaList = jizenchousaList;
    }

    /**
     * 15���ރ��X�g���擾
     * @return List 15���ރ��X�g
     */
    public List getKanrenbunyaBunruiList() {
        return kanrenbunyaBunruiList;
    }

    /**
     * 15���ރ��X�g��ݒ�
     * @param kanrenbunyaBunruiList 15���ރ��X�g
     */
    public void setKanrenbunyaBunruiList(List kanrenbunyaBunruiList) {
        this.kanrenbunyaBunruiList = kanrenbunyaBunruiList;
    }

    /**
     * �����̕K�v�����X�g���擾
     * @return List �����̕K�v�����X�g
     */
    public List getKenkyuHitsuyouseiList() {
        return kenkyuHitsuyouseiList;
    }

    /**
     * �����̕K�v�����X�g��ݒ�
     * @param kenkyuHitsuyouseiList �����̕K�v�����X�g
     */
    public void setKenkyuHitsuyouseiList(List kenkyuHitsuyouseiList) {
        this.kenkyuHitsuyouseiList = kenkyuHitsuyouseiList;
    }

    /**
     * �R����]���僊�X�g���擾
     * @return List �R����]���僊�X�g
     */
    public List getKiboubumonList() {
        return kiboubumonList;
    }

    /**
     * �R����]���僊�X�g��ݒ�
     * @param kiboubumonList �R����]���僊�X�g
     */
    public void setKiboubumonList(List kiboubumonList) {
        this.kiboubumonList = kiboubumonList;
    }

    /**
     * �����̈�ŏI�N�x�O�N�x�̉���L�����X�g���擾
     * @return List �����̈�ŏI�N�x�O�N�x�̉���L�����X�g
     */
    public List getZenNendoOuboFlgList() {
        return zenNendoOuboFlgList;
    }

    /**
     * �����̈�ŏI�N�x�O�N�x�̉���L�����X�g��ݒ�
     * @param zenNendoOuboFlgList �����̈�ŏI�N�x�O�N�x�̉���L�����X�g
     */
    public void setZenNendoOuboFlgList(List zenNendoOuboFlgList) {
        this.zenNendoOuboFlgList = zenNendoOuboFlgList;
    }

    /**
     * �����̕K�v���̒l�̃��X�g���擾
     * @return List �����̕K�v���̒l�̃��X�g
     */
    public List getValuesList() {
        return values;
    }

    /**
     * �����̕K�v���̒l�̃��X�g��ݒ�
     * @param values �����̕K�v���̒l�̃��X�g
     */
    public void setValuesList(List values) {
        this.values = values;
    }

    /**
     * �����̕K�v���̒l�̃��X�g���擾
     * @param key
     * @return Object �����̕K�v���̒l�̃��X�g
     */
    public Object getValues(int key) {
        return values.get(key);
    }

    /**
     * �����̕K�v���̒l�̃��X�g��ݒ�
     * @param key
     * @param value
     */
    public void setValues(int key, Object value) {
        if(!values.contains(value)){
            values.add(value);
        }
    }
    
    /**
     * �E�����X�g���擾
     * @return List �E�����X�g
     */
    public List getShokushuList() {
        return shokushuList;
    }

    /**
     * �E�����X�g��ݒ�
     * @param shokushuList �E�����X�g
     */
    public void setShokushuList(List shokushuList) {
        this.shokushuList = shokushuList;
    }

    /**
     * �Y�t�t�@�[�����X�g���擾
     * @return List �Y�t�t�@�[�����X�g
     */
    public List getTenpuFileList() {
        return tenpuFileList;
    }

    /**
     * �Y�t�t�@�[�����X�g��ݒ�
     * @param tenpuFileList �Y�t�t�@�[�����X�g
     */
    public void setTenpuFileList(List tenpuFileList) {
        this.tenpuFileList = tenpuFileList;
    }

    /**
     * �V�X�e����t�ԍ����擾
     * @return String �V�X�e����t�ԍ�
     */
    public String getSystemNo() {
        return systemNo;
    }

    /**
     * �V�X�e����t�ԍ���ݒ�
     * @param systemNo �V�X�e����t�ԍ�
     */
    public void setSystemNo(String systemNo) {
        this.systemNo = systemNo;
    }
    
    /**
     * �V�X�e����t�ԍ�(�̈�)���擾
     * @return String �V�X�e����t�ԍ�(�̈�)
     */
    public String getRyoikiSystemNo() {
        return ryoikiSystemNo;
    }

    /**
     * �V�X�e����t�ԍ�(�̈�)��ݒ�
     * @param ryoikiSystemNo �V�X�e����t�ԍ�(�̈�)
     */
    public void setRyoikiSystemNo(String ryoikiSystemNo) {
        this.ryoikiSystemNo = ryoikiSystemNo;
    }

    /**
     * �����g�D�o��v�l���擾
     * @return List �����g�D�o��v�l
     */
    public List getKeihiTotal() {
        return keihiTotal;
    }

    /**
     * �����g�D�o��v�l��ݒ�
     * @param keihiTotal �����g�D�o��v�l
     */
    public void setKeihiTotal(List keihiTotal) {
        this.keihiTotal = keihiTotal;
    }
    
    /**
     * ���匤���o��v�l���擾
     * @return List ���匤���o��v�l
     */
    public List getKenkyuSyokeiTotal() {
        return kenkyuSyokeiTotal;
    }

    /**
     * ���匤���o��v�l��ݒ�
     * @param kenkyuSyokeiTotal ���匤���o��v�l
     */
    public void setKenkyuSyokeiTotal(List kenkyuSyokeiTotal) {
        this.kenkyuSyokeiTotal = kenkyuSyokeiTotal;
    }

    /**
     * �����g�D�o��v�l���擾
     * @return List �����g�D�o��v�l
     */
    public List getKeihi() {
        return keihi;
    }

    /**
     * �����g�D�o��v�l��ݒ�
     * @param keihi �����g�D�o��v�l
     */
    public void setKeihi(List keihi) {
        this.keihi = keihi;
    }
   
    /**
     * ����ID���擾
     * @return String ����ID
     */
    public String getJigyoId() {
        return jigyoId;
    }

    /**
     * ����ID��ݒ�
     * @param jigyoId ����ID
     */
    public void setJigyoId(String jigyoId) {
        this.jigyoId = jigyoId;
    }

    /**
     * �J�ډ�ʃt���O���擾
     * @return String �J�ډ�ʃt���O
     */
    public String getScreenFlg() {
        return screenFlg;
    }

    /**
     * �J�ډ�ʃt���O��ݒ�
     * @param screenFlg �J�ډ�ʃt���O
     */
    public void setScreenFlg(String screenFlg) {
        this.screenFlg = screenFlg;
    }

//  <!-- ADD�@START 2007/07/19 BIS ���� -->
	/**
	 * @return Returns the errorsList.
	 */
	public List getErrorsList() {
		return errorsList;
	}

	/**
	 * @param errorsList The errorsList to set.
	 */
	public void setErrorsList(List errorsList) {
		this.errorsList = errorsList;
	}
//  <!-- ADD�@END 2007/07/19 BIS ���� -->
// 2006/08/10 dyh delete start
//    /**
//     * �����̕K�v���̃`�F�b�N�̃��X�g���擾
//     * @return checkedList �����̕K�v���̃`�F�b�N�̃��X�g
//     */
//    public List getCheckedList() {
//        return checkedList;
//    }
//
//    /**
//     * �����̕K�v���̃`�F�b�N�̃��X�g��ݒ�
//     * @param checkedList �����̕K�v���̃`�F�b�N�̃��X�g
//     */
//    public void setCheckedList(List checkedList) {
//        this.checkedList = checkedList;
//    }
//
//    /**
//     * �����̕K�v���̃`�F�b�N�̃��X�g���擾
//     * @param key
//     * @return Object �����̕K�v���̒l�̃��X�g
//     */
//    public Object getCheckedList(int key) {
//        return checkedList.get(key);
//    }
//
//    /**
//     * �����̕K�v���̃`�F�b�N�̃��X�g��ݒ�
//     * @param key
//     * @param value
//     */
//    public void setCheckedList(int key, Object value) {
//        checkedList.set(key, value); 
//    }
// 2006/08/10 dyh delete end
	
//	ADD�@START 2007-07-19 BIS ������
	/**
	 * 
	 * @param errors
	 */
	private void addKumiaiError(ActionErrors errors){
		Iterator errorIterator = null;
		ActionError error = null;
		
		//���������E���O�����̏󋵁i���̑��j�͕K�{���ڂł�
		errorIterator = errors.get("ryoikikeikakushoInfo.jizenchousaSonota");
		while(errorIterator.hasNext()){
			error = (ActionError)(errorIterator.next());
			if(error.getKey().equalsIgnoreCase("errors.required")){
				errors.add("ryoikikeikakushoInfo.jizenchousaSonota.kumiai",new ActionError("errors.required"));
			}
		}
		//���������E���O�����̏󋵂Łu���̑��v�ȊO��I�������ꍇ�ɏ��������E���O�����̏󋵁i���̑��j�͓��͂ł��܂���B 
		errorIterator = errors.get("ryoikikeikakushoInfo.jizenchousaFlg");
		while(errorIterator.hasNext()){
			error = (ActionError)(errorIterator.next());
			if(error.getKey().equalsIgnoreCase("errors.5068")){
				errors.add("ryoikikeikakushoInfo.jizenchousaSonota.kumiai",new ActionError("errors.5068"));
			}
		}
		
		//�ŏI�N�x�O�N�x�ɂ�����̈�ԍ��͕K�{���ڂł�
		errorIterator = errors.get("ryoikikeikakushoInfo.zennendoOuboNo");
		while(errorIterator.hasNext()){
			error = (ActionError)(errorIterator.next());
			if(error.getKey().equalsIgnoreCase("errors.5068")){
				errors.add("ryoikikeikakushoInfo.zennendoOuboNo.kumiai",new ActionError("errors.5068"));
			}else if(error.getKey().equalsIgnoreCase("errors.required")){
				errors.add("ryoikikeikakushoInfo.zennendoOuboNo.kumiai",new ActionError("errors.required"));
			}
		}
		//�����S���ҐE���i�a���j�͕K�{���ڂł�
		errorIterator = errors.get("ryoikikeikakushoInfo.jimutantoShokushuNameKanji");
		while(errorIterator.hasNext()){
			error = (ActionError)(errorIterator.next());
			if(error.getKey().equalsIgnoreCase("errors.required")){
				errors.add("ryoikikeikakushoInfo.jimutantoShokushuNameKanji.kumiai",new ActionError("errors.required"));
			}
		}
	}
//	ADD�@END 2007-07-19 BIS ������
	
}