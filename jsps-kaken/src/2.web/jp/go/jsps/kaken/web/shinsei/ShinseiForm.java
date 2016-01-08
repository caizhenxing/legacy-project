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
package jp.go.jsps.kaken.web.shinsei;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import jp.go.jsps.kaken.model.common.ApplicationSettings;
import jp.go.jsps.kaken.model.common.IJigyoCd;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.common.ISettingKeys;
import jp.go.jsps.kaken.model.common.SystemServiceFactory;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.impl.ShinseishaMaintenance;
import jp.go.jsps.kaken.model.vo.ErrorInfo;
import jp.go.jsps.kaken.model.vo.ShinseiDataInfo;
import jp.go.jsps.kaken.model.vo.ShinseishaSearchInfo;
import jp.go.jsps.kaken.model.vo.shinsei.DaihyouInfo;
import jp.go.jsps.kaken.model.vo.shinsei.KenkyuSoshikiKenkyushaInfo;
import jp.go.jsps.kaken.util.Page;
import jp.go.jsps.kaken.util.StringUtil;
import jp.go.jsps.kaken.web.common.IConstants;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.shinsei.validate.DefaultShinseiValidator;
import jp.go.jsps.kaken.web.shinsei.validate.IShinseiValidator;
import jp.go.jsps.kaken.web.shinsei.validate.ShinseiValidatorFactory;
import jp.go.jsps.kaken.web.shinsei.validate.ShinseiValidatorGakusou;
import jp.go.jsps.kaken.web.shinsei.validate.ShinseiValidatorKibanAIppan;
import jp.go.jsps.kaken.web.shinsei.validate.ShinseiValidatorKibanAKaigai;
import jp.go.jsps.kaken.web.shinsei.validate.ShinseiValidatorKibanBIppan;
import jp.go.jsps.kaken.web.shinsei.validate.ShinseiValidatorKibanBKaigai;
import jp.go.jsps.kaken.web.shinsei.validate.ShinseiValidatorKibanCIppan;
import jp.go.jsps.kaken.web.shinsei.validate.ShinseiValidatorKibanCKikaku;
import jp.go.jsps.kaken.web.shinsei.validate.ShinseiValidatorKibanHoga;
import jp.go.jsps.kaken.web.shinsei.validate.ShinseiValidatorKibanS;
import jp.go.jsps.kaken.web.shinsei.validate.ShinseiValidatorKibanWakateA;
import jp.go.jsps.kaken.web.shinsei.validate.ShinseiValidatorKibanWakateB;
import jp.go.jsps.kaken.web.shinsei.validate.ShinseiValidatorKibanWakateS;
import jp.go.jsps.kaken.web.shinsei.validate.ShinseiValidatorShokushinhiKibanA;
import jp.go.jsps.kaken.web.shinsei.validate.ShinseiValidatorShokushinhiKibanB;
import jp.go.jsps.kaken.web.shinsei.validate.ShinseiValidatorShokushinhiKibanC;
import jp.go.jsps.kaken.web.shinsei.validate.ShinseiValidatorShokushinhiWakateA;
import jp.go.jsps.kaken.web.shinsei.validate.ShinseiValidatorShokushinhiWakateB;
import jp.go.jsps.kaken.web.shinsei.validate.ShinseiValidatorTokusui;
import jp.go.jsps.kaken.web.shinsei.validate.ShinseiValidatorTokutei;
import jp.go.jsps.kaken.web.shinsei.validate.ShinseiValidatorTokuteiSinki;
import jp.go.jsps.kaken.web.shinsei.validate.ShinseiValidatorWakatestart;
import jp.go.jsps.kaken.web.struts.BaseValidatorForm;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

/**
 * ID RCSfile="$RCSfile: ShinseiForm.java,v $" Revision="$Revision: 1.34 $"
 * Date="$Date: 2007/07/25 08:53:00 $"
 */
public class ShinseiForm extends BaseValidatorForm {

	// ---------------------------------------------------------------------
	// Static data
	// ---------------------------------------------------------------------

	/**
     * <code>serialVersionUID</code> �̃R�����g
     */
    private static final long serialVersionUID = -8594326959248568097L;

	/** ���O */
	protected static Log log = LogFactory.getLog(ShinseiForm.class);

	/** �Y�t�t�@�C�����M�����E�\���o�^�������������s�񐔁i1�b�Ԋu�j */
	private static int    TRY_COUNT_SYNCHRONIZE  = ApplicationSettings.getInt(ISettingKeys.TRY_COUNT_SYNCHRONIZE);

	// ---------------------------------------------------------------------
	// Instance data
	// ---------------------------------------------------------------------
	
    /** ���̈�ԍ� */
    private String kariryoikiNo;
	
	/** �\���f�[�^��� */
	private ShinseiDataInfo shinseiDataInfo = new ShinseiDataInfo();

	/** �n���̋敪���X�g */
	private List keitouList = new ArrayList();

	/** ���E�̊ϓ_���X�g */
	private List suisenList = new ArrayList();

	/** �E���X�g */
	private List shokushuList = new ArrayList();

	/** �V�K�E�p���敪���X�g */
	private List shinkiKeibetuList = new ArrayList();

	/** �O�N�x�̉��僊�X�g */
	private List zennendoList = new ArrayList();

	/** ���S���̗L�����X�g */
	private List buntankinList = new ArrayList();

	/** �J����]�̗L�����X�g */
	private List kaijiKiboList = new ArrayList();

	// 2005/04/08 �ǉ� ��������----------------------------
	/** �����Ώۂ̗ތ^ */
	private List kenkyuTaishoList = new ArrayList();

	/** �R����]���� */
	private List shinsaKiboBunyaList = new ArrayList();
	//2006/02/15
	/** �R����]�̈� */
	private List shinsaKiboRyoikiList = new ArrayList();

	// �ǉ� �����܂�--------------------------------------

	/** �A�b�v���[�h�t�@�C���A�N�V�����Ƃ̓������t���O */
	private boolean uploadActionEnd = false;

	/** �A�b�v���[�h�t�@�C�� */
	private FormFile uploadFile = null;

	/** �l����ʁi���ƃR�[�h��3,4�P�^�j */
	private String yoshikiShubetu = null;

	/** ���X�g�C���f�b�N�X */
	private int listIndex = -1;

	/** �ǉ����錤���g�D�̌����҂̕��S�t���O */
	private String addBuntanFlg = null;

	// �E�E�E�E�E�E�E�E�E�E

	// 20050526 Start
	/** �����̈惊�X�g */
	private List kenkyuKubunList = new ArrayList();

	// Horikoshi End

	// 2006/02/08 Start
	/** ���i�擾�N�����̔N */
	private String sikakuDateYear = null;

	/** ���i�擾�N�����̌� */
	private String sikakuDateMonth = null;

	/** ���i�擾�N�����̓� */
	private String sikakuDateDay = null;

	/** ���i�擾�O�@�֖� */
	private String syutokumaeKikan = null;

	/** ��x���J�n���̔N */
	private String ikukyuStartDateYear = null;

	/** ��x���J�n���̌� */
	private String ikukyuStartDateMonth = null;

	/** ��x���J�n���̓� */
	private String ikukyuStartDateDay = null;

	/** ��x���I�����̔N */
	private String ikukyuEndDateYear = null;

	/** ��x���I�����̌� */
	private String ikukyuEndDateMonth = null;

	/** ��x���I�����̓� */
	private String ikukyuEndDateDay = null;
	
	/** ���i�擾�N���X�g */
	private List sikakuDateList = new ArrayList();
	
	/** ��x���J�n�N���X�g */
	private List ikukyuStartDateList = new ArrayList();
	
	/** �̗p�N�����̔N */
    private String saiyoDateYear;
    
    /** �̗p�N�����̌� */
    private String saiyoDateMonth;
    
    /** �̗p�N�����̓� */
    private String saiyoDateDay;

	// Byou End
	// ---------------------------------------------------------------------
	// Constructors
	// ---------------------------------------------------------------------

	/**
	 * @return Returns the sikakuDateList.
	 */
	public List getSikakuDateList() {
		return sikakuDateList;
	}

	/**
	 * @param sikakuDateList The sikakuDateList to set.
	 */
	public void setSikakuDateList(List sikakuDateList) {
		this.sikakuDateList = sikakuDateList;
	}

	/**
	 * �R���X�g���N�^�B
	 */
	public ShinseiForm() {
		super();
		init();
	}

	// ---------------------------------------------------------------------
	// Private methods
	// ---------------------------------------------------------------------

	/**
	 * �A�b�v���[�h�t�@�C���A�N�V�����Ƃ̓��������s���B �A�b�v���[�h�t���O��true�ɂȂ�܂ŏ����𒆒f����B
	 * �t���O��false�̏ꍇ��1�b��ɍēx�m�F���s���B TRY_COUNT_SYNCHRONIZE�Őݒ肳��Ă���񐔕����s���Ă�
	 * �����ꍇ�̓^�C���A�E�g�Ƃ݂Ȃ��B
	 * 
	 * @return �^�C���A�E�g�����������ꍇfalse
	 */
	private boolean synchronizedUploadFileAction() {

		// 2005.08.08 iso ���������O�̒ǉ�
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

	/*
	 * �����������B (�� Javadoc)
	 * 
	 * @see org.apache.struts.action.ActionForm#reset(org.apache.struts.action.ActionMapping,
	 *      javax.servlet.http.HttpServletRequest)
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		// �E�E�E
	}

	/**
	 * ����������
	 */
	public void init() {
	}

	/*
	 * ���̓`�F�b�N�B (�� Javadoc)
	 * 
	 * @see org.apache.struts.action.ActionForm#validate(org.apache.struts.action.ActionMapping,
	 *      javax.servlet.http.HttpServletRequest)
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
		
// ADD START 2007-07-10 BIS ���u��
		if ((IJigyoCd.JIGYO_CD_TOKUSUI.equals(shinseiDataInfo.getJigyoCd())
			|| IJigyoCd.JIGYO_CD_TOKUTEI_KEIZOKU.equals(shinseiDataInfo.getJigyoCd())) 
			&& page == 2 && "2".equals(shinseiDataInfo.getShinseiKubun())
			&& (shinseiDataInfo.getKenkyuKeihiSoukeiInfo().getNaiyakuTotal() == null
				|| "".equals(shinseiDataInfo.getKenkyuKeihiSoukeiInfo().getNaiyakuTotal()) 
				|| "0".equals(shinseiDataInfo.getKenkyuKeihiSoukeiInfo().getNaiyakuTotal()))) {
			
			ActionErrors errors = new ActionErrors();
			// [�p���ۑ���擾]�{�^�����N���b�N�ŁA����z���m�F���Ă��������B
			errors.add("shinseiForm.confirmNaiyakugaku", new ActionError(
					"errors.9038"));
			return errors;
		}
// ADD END 2007-07-10 BIS ���u��
		
//ADD�@START 2007/07/11 BIS �����_  
        if ("kenkyusoshiki".equals(mapping.getParameter())) {
			ActionErrors errors = super.validate(mapping, request);
			//�����g�D�\���X�g���A�N�V�����t�H�[������擾����
			List kenkyuSoshikiIfo = this.getShinseiDataInfo().getKenkyuSoshikiInfoList();
			   
			boolean bunfanFlagCheck = false;
			boolean renkeiFlagCheck = false;
			
			for (int i = 1; kenkyuSoshikiIfo != null && i < kenkyuSoshikiIfo.size(); i++) {
				KenkyuSoshikiKenkyushaInfo kenkyuSoshikiKenkyushaInfo =
					            (KenkyuSoshikiKenkyushaInfo) kenkyuSoshikiIfo.get(i);

				String buntanFlag = kenkyuSoshikiKenkyushaInfo.getBuntanFlag() ;				
				
				if("5".equals(buntanFlag)){
					continue;
				}
				
				//�\���Ҍ��������I�u�W�F�N�g�̎��ቻ
				ShinseishaSearchInfo searchInfo = new ShinseishaSearchInfo();
				//�Ȍ������Ҕԍ��̐ݒ�
				searchInfo.setKenkyuNo(kenkyuSoshikiKenkyushaInfo.getKenkyuNo());
				//�Z�b�V��������g�p��������擾����
				UserContainer container = (UserContainer) request.getSession()
				                .getAttribute(IConstants.USER_CONTAINER_KEY);
				if (container == null) {
					container = new UserContainer();
					HttpSession session = request.getSession(true);
					session.setAttribute(IConstants.USER_CONTAINER_KEY, container);
				}

				try {
					if (searchInfo.getKenkyuNo() != null
							&& !"".equals(searchInfo.getKenkyuNo())) {
						Page result = new ShinseishaMaintenance().getKenkyushaInfoByKenkyuNo(container.getUserInfo(), searchInfo);
						Map mapTemp = (Map) result.getList().get(0);
						String nyuuryokuSei = StringUtil
								.toOomojiDigit(kenkyuSoshikiKenkyushaInfo.getNameKanaSei());
						String nyuuryokuMei = StringUtil
								.toOomojiDigit(kenkyuSoshikiKenkyushaInfo.getNameKanaMei());
						String mapSei = StringUtil
								.toOomojiDigit((String) mapTemp.get("NAME_KANA_SEI"));
						String mapMei = StringUtil
								.toOomojiDigit((String) mapTemp.get("NAME_KANA_MEI"));

						// �����҃}�X�^�̃t���K�i���E�t���K�i���ɁA�S�p�X�y�[�X���P�����o�^����Ă���ꍇ�́A���̍��ڂ̔�r�`�F�b�N�͍s��Ȃ��B
						// �t���K�i�������A�ǂ�����S�p�X�y�[�X�P�����Ƃ������Ƃ͂Ȃ��B
						// �S�p�X�y�[�X�P���������łȂ��ANULL�̂Ƃ������l�̓���Ƃ���B
						boolean flagTemp = false;
						if (mapSei == null || "".equals(mapSei) || mapSei.indexOf('�@') >= 0) {
							flagTemp = !flagTemp;
						}
						if (mapMei == null || "".equals(mapMei) || mapMei.indexOf('�@') >= 0) {
							flagTemp = !flagTemp;
						}
						if (flagTemp) {
							continue;
						}

						// �����Ҕԍ����L�[�Ƃ��āA�u��ʓ��͒l�̃t���K�i���v���u�����҃}�X�^�̃t���K�i���v�Ɋ܂܂�邩���`�F�b�N����B
						// �����Ҕԍ����L�[�Ƃ��āA�u�����҃}�X�^�̃t���K�i���v���u��ʓ��͒l�̃t���K�i���v�Ɋ܂܂�邩���`�F�b�N����B
						// (1),(2)�̔�r���ʂ̂����ꂩ�i�������͗����j���u���v�̂Ƃ��A�u�t���K�i���v�͈�v���Ă���Ɣ��肷��B
						if (nyuuryokuSei != null && mapSei != null && !"".equals(nyuuryokuSei)
								&& (mapSei.indexOf(nyuuryokuSei) >= 0 || nyuuryokuSei.indexOf(mapSei) >= 0)) {
							continue;
						}
						// �u�t���K�i���v�̔�r�x�Ɠ��l�̎d�g�݂ŁA�u�t���K�i���v�ɂ��Ă����肷��B
						if (nyuuryokuMei != null && mapMei != null && !"".equals(nyuuryokuMei)
								&& (mapMei.indexOf(nyuuryokuMei) >= 0 || nyuuryokuMei.indexOf(mapMei) >= 0)) {
							continue;
						}
						
						if ("2".equals(buntanFlag)){
							bunfanFlagCheck = true;
						}else if("4".equals(buntanFlag)){
							renkeiFlagCheck = true ; 
						}
						
						String property = "shinseiDataInfo.kenkyuSoshikiInfoList["+ i + "].";
						
						ActionError error = new ActionError("errors.5074");
						
						errors.add(property + "nameKanaSei", error);
						
						errors.add(property + "nameKanaMei", error);
						
						errors.add(property + "kenkyuNo", error);

					}
				} catch (ApplicationException e) {
					if ("2".equals(buntanFlag)){
						bunfanFlagCheck = true;
					}else if("4".equals(buntanFlag)){
						renkeiFlagCheck = true ; 
					}
					String property = "shinseiDataInfo.kenkyuSoshikiInfoList["+ i + "].";
					ActionError error = new ActionError("errors.5074");
					errors.add(property + "nameKanaSei", error);
					errors.add(property + "nameKanaMei", error);
					errors.add(property + "kenkyuNo", error);
				}
			}
			if (bunfanFlagCheck) {
				String property = "shinseiDataInfo.kenkyuKeihiSoukeiInfo.NameKanaSeiORNameKanaMei.Bunfan";
				ActionError error = new ActionError("errors.5074" , "���S��");
				errors.add(property, error);
			}
			if (renkeiFlagCheck) {
				String property = "shinseiDataInfo.kenkyuKeihiSoukeiInfo.NameKanaSeiORNameKanaMei.Renkei";
				ActionError error = new ActionError("errors.5074" , "�A�g������");
				errors.add(property, error);
			}
			return errors;

		}
// ADD END 2007/07/11 BIS �����_
		
		
        
// 2007/02/06 �����F �ǉ� ��������
        if(IJigyoCd.JIGYO_CD_WAKATESTART.equals(this.getShinseiDataInfo().getJigyoCd())){
            //���ʌ����������ۑ�ԍ�-�N�x��2������������0�l��.
            if (!StringUtil.isBlank(this.getShinseiDataInfo().getShoreihiNoNendo())) {
                this.getShinseiDataInfo().setShoreihiNoNendo(StringUtil.fillLZero(
                        StringUtil.toHankakuDigit(this.getShinseiDataInfo().getShoreihiNoNendo()), 2));
            }
            //���ʌ����������ۑ�ԍ��|�����ԍ���5������������0�l��.
            if (!StringUtil.isBlank(this.getShinseiDataInfo().getShoreihiNoSeiri())) {
                this.getShinseiDataInfo().setShoreihiNoSeiri(StringUtil.fillLZero(
                        StringUtil.toHankakuDigit(this.getShinseiDataInfo().getShoreihiNoSeiri()), 5));
            }
        }
//2007/02/06 �����F�@�ǉ��@�����܂�

		// 2005/8/29 �E���̂̑S�p/���p�󔒂���������
		String shokushu = this.getShinseiDataInfo().getDaihyouInfo()
				.getShokushuNameKanji();
		if (StringUtil.isSpaceString(shokushu)) {
			this.getShinseiDataInfo().getDaihyouInfo().setShokushuNameKanji("");
		}
		
		//2006/11/16 �����ԍ��Ɂu-�v����͂�����A�X�y�[�X�ɕύX
		if ("-".equals(this.getShinseiDataInfo().getKadaiInfo().getBunkatsuNo())){
			this.getShinseiDataInfo().getKadaiInfo().setBunkatsuNo("");
		}

		// ��^����-----
		ActionErrors errors = super.validate(mapping, request);

//		ADD�@START 2007-07-19 BIS ������
		//�g�ݍ��킹�̃G���[��ǉ�����
		addKumiaiError(errors);
//		ADD�@END 2007-07-19 BIS ������
		
		// ---------------------------------------------
		// ��{�I�ȃ`�F�b�N(�K�{�A�`�����j��Validator���g�p����B

		// ������̃`�F�b�N�ɂ��Ă͊eValidator�N���X���g�p����B
		// //�����o��̍��v���Z�o���A�ő�l�`�F�b�N���s��
		// validateAndSetKeihiTotal(errors, page);

		// 2005.04.08 hashimoto �l�����Ƃ�Validate�̓������
		IShinseiValidator shinseiValidator = ShinseiValidatorFactory
				.getShinseiValidator(shinseiDataInfo);
		errors = shinseiValidator.validate(
				(jp.go.jsps.kaken.web.struts.ActionMapping) mapping, request,
				page, errors);

//�S���Ƃɑ΂��āA��\�҂̔N����Z�b�g�ƂȂ� 2007/3/19
//// 2006/02/13 Update Start
//		// ���ʌ������i��Ɗ�Վ��͑g�D�[���������߃`�F�b�N���s��Ȃ�
//		if (!(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_A.equals(this.getShinseiDataInfo().getJigyoCd()) 
//			 || IJigyoCd.JIGYO_CD_KIBAN_WAKATE_B.equals(this.getShinseiDataInfo().getJigyoCd())
//			 || IJigyoCd.JIGYO_CD_SHOKUSHINHI_WAKATE_A.equals(this.getShinseiDataInfo().getJigyoCd())
//			 || IJigyoCd.JIGYO_CD_SHOKUSHINHI_WAKATE_B.equals(this.getShinseiDataInfo().getJigyoCd())
////2007/02/15 �c�@�ǉ���������
//             || IJigyoCd.JIGYO_CD_KIBAN_WAKATE_S.equals(this.getShinseiDataInfo().getJigyoCd())
////2007/02/15�@�c�@�ǉ������܂�
////2007/03/19 �c�@�ǉ���������
//             || IJigyoCd.JIGYO_CD_WAKATESTART.equals(this.getShinseiDataInfo().getJigyoCd())
////2007/03/19�@�c�@�ǉ������܂�             
//        )) {
//// Byou End
//
//			// 2005/06/02 �폜
//			// ��������----------------------------------------------------------
//			// �eShinseiValidater�N���X��validate�������s�����ߍ폜
//			// �����g�D�\�̌`���`�F�b�N���s���B
//			// validateKenkyuSoshiki(errors, page);
//			//
//			// countKenkyushaNinzu(errors, page);
//			// �폜
//			// �����܂�---------------------------------------------------------------------
//		} else {
			DaihyouInfo daihyo = this.getShinseiDataInfo().getDaihyouInfo();
			if (daihyo != null) {
				List soshiki = this.getShinseiDataInfo().getKenkyuSoshikiInfoList();
				if (soshiki != null && soshiki.size() > 0) {
					daihyo.setNenrei(((KenkyuSoshikiKenkyushaInfo) soshiki.get(0)).getNenrei());
				}
			}
//		}
		
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

	// 2005.08.08 iso �Y�t�t�@�C�����o�͒ǉ�
	/*
	 * �Y�t�t�@�C�����o�́B
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

	// 2005.10.25 iso �G���[���O�o�͋@�\
	/*
	 * �G���[���b�Z�[�W�̃��O�o�́B
	 */
	public void outputErrors(ActionErrors errors) {

		log.info("�\����ID=" + shinseiDataInfo.getShinseishaId());

		for (Iterator iter = errors.get(); iter.hasNext();) {
			ActionError value = (ActionError) iter.next();
			String out = value.getKey();
			Object[] values = value.getValues();
			for (int j = 0; j < values.length; j++) {
				out += " " + values[j].toString();
			}
			log.info(out);
		}
	}

	// ---------------------------------------------------------------------
	// Properties
	// ---------------------------------------------------------------------

	/**
	 * @return
	 */
	public List getKeitouList() {
		return keitouList;
	}

	/**
	 * @return
	 */
	public ShinseiDataInfo getShinseiDataInfo() {
		return shinseiDataInfo;
	}

	/**
	 * @return
	 */
	public List getShokushuList() {
		return shokushuList;
	}

	/**
	 * @return
	 */
	public List getSuisenList() {
		return suisenList;
	}

	/**
	 * @return
	 */
	public boolean isUploadActionEnd() {
		return uploadActionEnd;
	}

	/**
	 * @return
	 */
	public FormFile getUploadFile() {
		return uploadFile;
	}

	/**
	 * @return
	 */
	public String getYoshikiShubetu() {
		return yoshikiShubetu;
	}

	/**
	 * @param list
	 */
	public void setKeitouList(List list) {
		keitouList = list;
	}

	/**
	 * @param info
	 */
	public void setShinseiDataInfo(ShinseiDataInfo info) {
		shinseiDataInfo = info;
	}

	/**
	 * @param list
	 */
	public void setShokushuList(List list) {
		shokushuList = list;
	}

	/**
	 * @param list
	 */
	public void setSuisenList(List list) {
		suisenList = list;
	}

	/**
	 * @param b
	 */
	public void setUploadActionEnd(boolean b) {
		uploadActionEnd = b;
	}

	/**
	 * @param file
	 */
	public void setUploadFile(FormFile file) {
		uploadFile = file;
	}

	/**
	 * @param string
	 */
	public void setYoshikiShubetu(String string) {
		yoshikiShubetu = string;
	}

	/**
	 * @return
	 */
	public List getBuntankinList() {
		return buntankinList;
	}

	/**
	 * @return
	 */
	public List getShinkiKeibetuList() {
		return shinkiKeibetuList;
	}

	/**
	 * @return
	 */
	public List getZennendoList() {
		return zennendoList;
	}

	/**
	 * @param list
	 */
	public void setBuntankinList(List list) {
		buntankinList = list;
	}

	/**
	 * @param list
	 */
	public void setShinkiKeibetuList(List list) {
		shinkiKeibetuList = list;
	}

	/**
	 * @param list
	 */
	public void setZennendoList(List list) {
		zennendoList = list;
	}

	/**
	 * @return
	 */
	public int getListIndex() {
		return listIndex;
	}

	/**
	 * @param i
	 */
	public void setListIndex(int i) {
		listIndex = i;
	}

	public List getKaijiKiboList() {
		return kaijiKiboList;
	}

	public void setKaijiKiboList(List kaijiKiboList) {
		this.kaijiKiboList = kaijiKiboList;
	}

	/**
	 * @return Returns the addBuntanFlg.
	 */
	public String getAddBuntanFlg() {
		return addBuntanFlg;
	}

	/**
	 * @param addBuntanFlg
	 *            The addBuntanFlg to set.
	 */
	public void setAddBuntanFlg(String addBuntanFlg) {
		this.addBuntanFlg = addBuntanFlg;
	}

	/**
	 * @return kenkyuTaishoList ��߂��܂��B
	 */
	public List getKenkyuTaishoList() {
		return kenkyuTaishoList;
	}

	/**
	 * @param kenkyuTaishoList
	 *            kenkyuTaishoList ��ݒ�B
	 */
	public void setKenkyuTaishoList(List kenkyuTaishoList) {
		this.kenkyuTaishoList = kenkyuTaishoList;
	}

	/**
	 * @return shinsaKiboBunyaList ��߂��܂��B
	 */
	public List getShinsaKiboBunyaList() {
		return shinsaKiboBunyaList;
	}

	/**
	 * @param shinsaKiboBunyaList
	 *            shinsaKiboBunyaList ��ݒ�B
	 */
	public void setShinsaKiboBunyaList(List shinsaKiboBunyaList) {
		this.shinsaKiboBunyaList = shinsaKiboBunyaList;
	}

	// 20050526 Start
	public List getKenkyuKubunList() {
		return kenkyuKubunList;
	}

	public void setKenkyuKubunList(List List) {
		kenkyuKubunList = List;
	}

	// Horikoshi End

	/**
	 * @return Returns the ikukyuEndDateDay.
	 */
	public String getIkukyuEndDateDay() {
		return ikukyuEndDateDay;
	}

	/**
	 * @param ikukyuEndDateDay
	 *            The ikukyuEndDateDay to set.
	 */
	public void setIkukyuEndDateDay(String ikukyuEndDateDay) {
		this.ikukyuEndDateDay = ikukyuEndDateDay;
	}

	/**
	 * @return Returns the ikukyuEndDateMonth.
	 */
	public String getIkukyuEndDateMonth() {
		return ikukyuEndDateMonth;
	}

	/**
	 * @param ikukyuEndDateMonth
	 *            The ikukyuEndDateMonth to set.
	 */
	public void setIkukyuEndDateMonth(String ikukyuEndDateMonth) {
		this.ikukyuEndDateMonth = ikukyuEndDateMonth;
	}

	/**
	 * @return Returns the ikukyuEndDateYear.
	 */
	public String getIkukyuEndDateYear() {
		return ikukyuEndDateYear;
	}

	/**
	 * @param ikukyuEndDateYear
	 *            The ikukyuEndDateYear to set.
	 */
	public void setIkukyuEndDateYear(String ikukyuEndDateYear) {
		this.ikukyuEndDateYear = ikukyuEndDateYear;
	}

	/**
	 * @return Returns the ikukyuStartDateDay.
	 */
	public String getIkukyuStartDateDay() {
		return ikukyuStartDateDay;
	}

	/**
	 * @param ikukyuStartDateDay
	 *            The ikukyuStartDateDay to set.
	 */
	public void setIkukyuStartDateDay(String ikukyuStartDateDay) {
		this.ikukyuStartDateDay = ikukyuStartDateDay;
	}

	/**
	 * @return Returns the ikukyuStartDateMonth.
	 */
	public String getIkukyuStartDateMonth() {
		return ikukyuStartDateMonth;
	}

	/**
	 * @param ikukyuStartDateMonth
	 *            The ikukyuStartDateMonth to set.
	 */
	public void setIkukyuStartDateMonth(String ikukyuStartDateMonth) {
		this.ikukyuStartDateMonth = ikukyuStartDateMonth;
	}

	/**
	 * @return Returns the ikukyuStartDateYear.
	 */
	public String getIkukyuStartDateYear() {
		return ikukyuStartDateYear;
	}

	/**
	 * @param ikukyuStartDateYear
	 *            The ikukyuStartDateYear to set.
	 */
	public void setIkukyuStartDateYear(String ikukyuStartDateYear) {
		this.ikukyuStartDateYear = ikukyuStartDateYear;
	}

	/**
	 * @return Returns the sikakuDateDay.
	 */
	public String getSikakuDateDay() {
		return sikakuDateDay;
	}

	/**
	 * @param sikakuDateDay
	 *            The sikakuDateDay to set.
	 */
	public void setSikakuDateDay(String sikakuDateDay) {
		this.sikakuDateDay = sikakuDateDay;
	}

	/**
	 * @return Returns the sikakuDateMonth.
	 */
	public String getSikakuDateMonth() {
		return sikakuDateMonth;
	}

	/**
	 * @param sikakuDateMonth
	 *            The sikakuDateMonth to set.
	 */
	public void setSikakuDateMonth(String sikakuDateMonth) {
		this.sikakuDateMonth = sikakuDateMonth;
	}

	/**
	 * @return Returns the sikakuDateYear.
	 */
	public String getSikakuDateYear() {
		return sikakuDateYear;
	}

	/**
	 * @param sikakuDateYear The sikakuDateYear to set.
	 */
	public void setSikakuDateYear(String sikakuDateYear) {
		this.sikakuDateYear = sikakuDateYear;
	}

	/**
	 * @return Returns the syutokumaeKikan.
	 */
	public String getSyutokumaeKikan() {
		return syutokumaeKikan;
	}

	/**
	 * @param syutokumaeKikan The syutokumaeKikan to set.
	 */
	public void setSyutokumaeKikan(String syutokumaeKikan) {
		this.syutokumaeKikan = syutokumaeKikan;
	}

	/**
	 * @return Returns the ikukyuStartDateList.
	 */
	public List getIkukyuStartDateList() {
		return ikukyuStartDateList;
	}

	/**
	 * @param ikukyuStartDateList The ikukyuStartDateList to set.
	 */
	public void setIkukyuStartDateList(List ikukyuStartDateList) {
		this.ikukyuStartDateList = ikukyuStartDateList;
	}

	/**
	 * @return Returns the saiyoDateDay.
	 */
	public String getSaiyoDateDay() {
		return saiyoDateDay;
	}

	/**
	 * @param saiyoDateDay The saiyoDateDay to set.
	 */
	public void setSaiyoDateDay(String saiyoDateDay) {
		this.saiyoDateDay = saiyoDateDay;
	}

	/**
	 * @return Returns the saiyoDateMonth.
	 */
	public String getSaiyoDateMonth() {
		return saiyoDateMonth;
	}

	/**
	 * @param saiyoDateMonth The saiyoDateMonth to set.
	 */
	public void setSaiyoDateMonth(String saiyoDateMonth) {
		this.saiyoDateMonth = saiyoDateMonth;
	}

	/**
	 * @return Returns the saiyoDateYear.
	 */
	public String getSaiyoDateYear() {
		return saiyoDateYear;
	}

	/**
	 * @param saiyoDateYear The saiyoDateYear to set.
	 */
	public void setSaiyoDateYear(String saiyoDateYear) {
		this.saiyoDateYear = saiyoDateYear;
	}

	/**
	 * @return Returns the shinsaKiboRyoikiList.
	 */
	public List getShinsaKiboRyoikiList() {
		return shinsaKiboRyoikiList;
	}

	/**
	 * @param saiyoDateYear The shinsaKiboRyoikiList to set.
	 */
	public void setShinsaKiboRyoikiList(List shinsaKiboRyoikiList) {
		this.shinsaKiboRyoikiList = shinsaKiboRyoikiList;
	}

	/**
	 * @return Returns the kariryoikiNo.
	 */
	public String getKariryoikiNo() {
		return kariryoikiNo;
	}

	/**
	 * @param kariryoikiNo The kariryoikiNo to set.
	 */
	public void setKariryoikiNo(String kariryoikiNo) {
		this.kariryoikiNo = kariryoikiNo;
	}
	
//	ADD�@START 2007-07-19 BIS ������
	/**
	 * 
	 * @param errors
	 */
	private void addKumiaiError(ActionErrors errors){
		Iterator errorIterator = null;
		ActionError error = null;
		//�V�K�E�p���敪�ƌ����ۑ�ԍ��̑g�ݍ��킹��
		errorIterator = errors.get("shinseiDataInfo.kadaiNoKeizoku");
		while(errorIterator.hasNext()){
			error = (ActionError)(errorIterator.next());
			if(error.getKey().equalsIgnoreCase("errors.5019")){
				errors.add("shinseiDataInfo.kadaiNoKeizoku.kumiai",new ActionError("errors.5019"));
			}
		}
		//�O�N�x�̉���̑g�ݍ��킹��
		errorIterator = errors.get("shinseiDataInfo.kadaiNoSaisyu");
		while(errorIterator.hasNext()){
			error = (ActionError)(errorIterator.next());
			if(error.getKey().equalsIgnoreCase("errors.5020")){
				errors.add("shinseiDataInfo.kadaiNoSaisyu.kumiai",new ActionError("errors.5020"));
			}else if(error.getKey().equalsIgnoreCase("errors.5047")){
				errors.add("shinseiDataInfo.kadaiNoSaisyu.kumiai",new ActionError("errors.5047"));
			}
		}
		//�V�K�E�p���敪���p���̏ꍇ�ɂ͌����v��ŏI�N�x�O�N�x�̉���Ɂu�Y������v��I���ł��܂���B
		errorIterator = errors.get("shinseiDataInfo.shinseiFlgNo");
		while(errorIterator.hasNext()){
			error = (ActionError)(errorIterator.next());
			String errorKey=error.getKey();
			if(errorKey.equalsIgnoreCase("errors.5056")){
				errors.add("shinseiDataInfo.shinseiFlgNo.kumiai",new ActionError("errors.5056"));
			}else if(errorKey.equalsIgnoreCase("errors.5054")){
				errors.add("shinseiDataInfo.shinseiFlgNo.kumiai",new ActionError("errors.5054"));
			}
		}
	}
//	ADD�@END 2007-07-19 BIS ������
	
}