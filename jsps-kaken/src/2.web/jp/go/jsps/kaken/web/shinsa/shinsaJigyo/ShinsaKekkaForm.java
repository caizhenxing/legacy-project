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
package jp.go.jsps.kaken.web.shinsa.shinsaJigyo;

import java.util.*;

import javax.servlet.http.*;

import jp.go.jsps.kaken.model.common.*;
import jp.go.jsps.kaken.model.IShinsaKekkaMaintenance;
import jp.go.jsps.kaken.web.struts.*;
import jp.go.jsps.kaken.util.*;

import org.apache.struts.action.*;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.*;

/**
 * �R�����ʃt�H�[��
 * ID RCSfile="$RCSfile: ShinsaKekkaForm.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:59 $"
 */
public class ShinsaKekkaForm extends BaseValidatorForm {

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/**
     * <code>serialVersionUID</code> �̃R�����g
     */
    private static final long serialVersionUID = -8088977827710070187L;

	/** �V�X�e���ԍ� */
	private String systemNo;

	/** �R�����ԍ� */
	private String shinsainNo;

	/** ���Ƌ敪 */
	private String jigyoKubun;
		
	/** ����ID */
	private String jigyoId;

	/** ���ƃR�[�h */
	private String jigyoCd;
	
	/** �����]���iABC�j */
	private String kekkaAbc;
	
	/** �����]���i�_���j */
	private String kekkaTen;
	
	/** �����]���i�_���j �G�� */
	private String kekkaTenHoga;

	/** �R�����g1 */
	private String comment1;

	/** �R�����g2 */
	private String comment2;
	
	/** �R�����g3 */
	private String comment3;
	
	/** �R�����g4 */
	private String comment4;
	
	/** �R�����g5 */
	private String comment5;
	
	/** �R�����g6 */
	private String comment6;

	/** �������e */
	private String kenkyuNaiyo;

	/** �����v�� */
	private String kenkyuKeikaku;

	/** �K�ؐ�-�C�O */
	private String tekisetsuKaigai;

	/** �K�ؐ�-����(1) */
	private String tekisetsuKenkyu1;

	/** �K�ؐ� */
	private String tekisetsu;

	/** �Ó��� */
	private String dato;

	/** ������\�� */
	private String shinseisha;

	/** �������S�� */
	private String kenkyuBuntansha;

	/** �q�g�Q�m�� */
	private String hitogenomu;

	/** ������ */
	private String tokutei;

	/** �q�gES�זE */
	private String hitoEs;

	/** ��`�q�g�������� */
	private String kumikae;

	/** ��`�q���×Տ����� */
	private String chiryo;

	/** �u�w���� */
	private String ekigaku;

	/** �R�����g */
	private String comments;
	
	/** ���Q�֌W */
	private String rigai;
	
	/** ���iS�j�Ƃ��Ă̑Ó���	 */
	private String wakates;

	/** �w�p�I�d�v���E�Ó��� */
	private String juyosei;

	/** �Ƒn���E�v�V�� */
	private String dokusosei;

	/** �g�y���ʁE���Ր� */
	private String hakyukoka;

	/** ���s�\�́E���̓K�ؐ� */
	private String suikonoryoku;

	/** �l���̕ی�E�@�ߓ��̏��� */
	private String jinken;
	
	/** ���S�� */
	private String buntankin;

	/** ���̑��R�����g */
	private String otherComment;
	
	/** �Y�t�t�@�C�� */
	private String tenpuPath;

	/** �Y�t�t�@�C���t���O */
	private String tenpuFlg;
	
	/** �Y�t�t�@�C��(�A�b�v���[�h�t�@�C��) */
	private FormFile tenpuUploadFile;
	
	/** �]���p�t�@�C���t���O */	
	private String hyokaFileFlg;
	
	/** �����]���iABC�j�I�����X�g */
	private List kekkaAbcList = new ArrayList();

	/** �����]���i�_���j�I�����X�g */
	private List kekkaTenList = new ArrayList();
	
	/** �����]���i�_���j�G��I�����X�g */
	private List kekkaTenHogaList = new ArrayList();

	/** �������e�I�����X�g */
	private List kenkyuNaiyoList = new ArrayList();
	
	/** �����v��I�����X�g */
	private List kenkyuKeikakuList = new ArrayList();
	
	/** �K�ؐ�-�C�O�I�����X�g */
	private List tekisetsuKaigaiList = new ArrayList();
	
	/** �K�ؐ�-�����i1�j�I�����X�g */
	private List tekisetsuKenkyu1List = new ArrayList();

	/** �K�ؐ��I�����X�g */
	private List tekisetsuList = new ArrayList();
			
	/** �Ó����I�����X�g */
	private List datoList = new ArrayList();

	/** ������\�ґI�����X�g */
	private List shinseishaList = new ArrayList();
		
	/** �������S�ґI�����X�g */
	private List kenkyuBuntanshaList = new ArrayList();

	/** �q�g�Q�m���I�����X�g */
	private List hitogenomuList = new ArrayList();
		
	/** ������I�����X�g */
	private List tokuteiList = new ArrayList();

	/** �q�gES�זE�I�����X�g */
	private List hitoEsList = new ArrayList();

	/** ��`�q�g���������I�����X�g */
	private List kumikaeList = new ArrayList();
		
	/** ��`�q���×Տ������I�����X�g */
	private List chiryoList = new ArrayList();

	/** �u�w�����I�����X�g */
	private List ekigakuList = new ArrayList();
	
	/** ���Q�֌W���X�g */
	private List rigaiList = new ArrayList();
		
	/** �w�p�I�d�v���E�Ó������X�g */
	private List juyoseiList = new ArrayList();

	/** �Ƒn���E�v�V�����X�g */
	private List dokusoseiList = new ArrayList();
		
	/** �g�y���ʁE���Ր����X�g */
	private List hakyukokaList = new ArrayList();

	/** ���s�\�́E���̓K�ؐ����X�g */
	private List suikonoryokuList = new ArrayList();

	/** �l���̕ی�E�@�ߓ��̏��烊�X�g */
	private List jinkenList = new ArrayList();
		
	/** ���S�����X�g */
	private List buntankinList = new ArrayList();


	//�E�E�E�E�E�E�E�E�E�E

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * �R���X�g���N�^�B
	 */
	public ShinsaKekkaForm() {
		super();
		init();
	}

	//---------------------------------------------------------------------
	// Public methods
	//---------------------------------------------------------------------

	/* 
	 * �����������B
	 * (�� Javadoc)
	 * @see org.apache.struts.action.ActionForm#reset(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		//�E�E�E
	}

	/**
	 * ����������
	 */
	public void init() {
		systemNo = "";         // �V�X�e���ԍ�
        shinsainNo = "";
        jigyoId = "";
        jigyoCd = "";
        kekkaAbc = "0";        // �v���_�E�������l�ݒ�
        kekkaTen = "";         // ���W�I�{�^�������l�ݒ�
        comment1 = "";         // �R�����g1
        comment2 = "";         // �R�����g2
        comment3 = "";         // �R�����g3
        comment4 = "";         // �R�����g4
        comment5 = "";         // �R�����g5
        comment6 = "";         // �R�����g6
        kenkyuNaiyo = "";      // �������e���W�I�����l�ݒ�
        kenkyuKeikaku = "";    // �����v�惉�W�I�����l�ݒ�
        tekisetsuKaigai = "";  // �K�ؐ�-�C�O���W�I�����l�ݒ�
        tekisetsuKenkyu1 = ""; // �K�ؐ�-����(1)���W�I�����l�ݒ�
        tekisetsu = "";        // �K�ؐ����W�I�����l�ݒ�
        dato = "";             // �Ó������W�I�����l�ݒ�
        shinseisha = "";       // ������\�҃��W�I�����l�ݒ�
        kenkyuBuntansha = "";  // ���W�I�{�^�������l�ݒ�
        hitogenomu = "";       // �q�g�Q�m�����W�I�����l�ݒ�
        tokutei = "";          // �����󃉃W�I�����l�ݒ�
        hitoEs = "";           // �q�gES�זE���W�I�����l�ݒ�
        kumikae = "";          // ��`�q�g�����������W�I�����l�ݒ�
        chiryo = "";           // ���W�I�{�^�������l�ݒ�
        ekigaku = "";          // ���W�I�{�^�������l�ݒ�
        comments = "";
        rigai = "";
        juyosei = "";
        dokusosei = "";
        hakyukoka = "";
        suikonoryoku = "";
        jinken = "";
        buntankin = "";
        otherComment = "";
        tenpuPath = "";
        tenpuUploadFile = null;
        hyokaFileFlg = "";	
	}

	/* 
	 * ���̓`�F�b�N�B
	 * (�� Javadoc)
	 * @see org.apache.struts.action.ActionForm#validate(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
	 */
	public ActionErrors validate(
		ActionMapping mapping,
		HttpServletRequest request) {

		//��^����----- 
		ActionErrors errors = super.validate(mapping, request);

		//---------------------------------------------
		// ��{�I�ȃ`�F�b�N(�K�{�A�`�����j��Validator���g�p����B
		//---------------------------------------------
		//update2004/12/21 ���x�����Ή�

//		if(getShinseisha() != null && !"".equals(getShinseisha())){
//			
//			//������ڂ��u��茤��(A)�v�u��茤��(B)�v�u��Ռ���(C)��ʁv�u��Ռ���(C)���v�u�G�茤���v�̏ꍇ
//			if("00121".equals(getJigyoCd())
//				|| "00131".equals(getJigyoCd())
//				|| "00061".equals(getJigyoCd())
//				|| "00062".equals(getJigyoCd())
//				|| "00111".equals(getJigyoCd())){
//				
//					//�֌W�ғ����u1�F������\�ҁv�̏ꍇ�́A�u�����]�_�v�u�������e�v�u�����v��v�́u-�v��I������					
//					if("1".equals(getShinseisha())) {
//					
//						if(!"-".equals(getKekkaTen()) 
//							|| !"-".equals(getKenkyuNaiyo())
//							|| !"-".equals(getKenkyuKeikaku())
//						){
//							errors.add(
//								ActionErrors.GLOBAL_ERROR,
//								new ActionError("errors.5026"));	
//						}
//					}
//			
//			//��L�i���j�ȊO�̌�����ڂ̏ꍇ
//			}else{
//				//�֌W�ғ����u1�F������\�ҁv�u2�F�������S�ҁv�u3�F�֌W�ҁv�̏ꍇ�́A�u�����]�_�v�u�������e�v�u�����v��v�́u-�v��I������
//				if("1".equals(getShinseisha()) 
//					|| "2".equals(getShinseisha()) 
//					|| "3".equals(getShinseisha())){
//					
//					if(!"-".equals(getKekkaTen()) 
//						|| !"-".equals(getKenkyuNaiyo())
//						|| !"-".equals(getKenkyuKeikaku())
//					){
//						errors.add(
//							ActionErrors.GLOBAL_ERROR,
//							new ActionError("errors.5025"));	
//					}
//				}
//			}
//		}

		//update2004/12/21 ���x�����Ή�
		//�������S�ҁE�֌W�҂��u1:�������S�ҁv���́u2:�֌W�ҁv��I�������ꍇ�́A�u�����]�_�v�u�������e�v�u�����v��v�́u-�v��I������
		/*
		if(getKenkyuBuntansha() != null && !"".equals(getKenkyuBuntansha())){
			//�u��Ռ���(S)�v�u��Ռ���(A)��ʁv�u��Ռ���(B)��ʁv�u��Ռ���(A)�C�O�w�p�����v�u��Ռ���(B)�C�O�w�p�����v�̏ꍇ�̂ݑΏۂƂ���B
			if("00031".equals(getJigyoCd())
				|| "00041".equals(getJigyoCd())
				|| "00051".equals(getJigyoCd())				
				|| "00043".equals(getJigyoCd())				
				|| "00053".equals(getJigyoCd()))
			{	
				if("1".equals(getKenkyuBuntansha()) || "2".equals(getKenkyuBuntansha())){
					if(!"-".equals(getKekkaTen()) 
						|| !"-".equals(getKenkyuNaiyo())
						|| !"-".equals(getKenkyuKeikaku())
					){
						errors.add(
							ActionErrors.GLOBAL_ERROR,
							new ActionError("errors.5025"));	
					}
				}
			}

			//�������S�ҁE�֌W�҂��u2:�֌W�ҁv��I�������ꍇ�̓R�����g���͕K�{�B�S���ʁB
			if("2".equals(getKenkyuBuntansha())){
				//�R�����g�������͂̏ꍇ
				if(getComments() == null || "".equals(getComments()) 
				){
					errors.add(
						ActionErrors.GLOBAL_ERROR,
						new ActionError("errors.5026"));	
				}
			}
		}
*/

		//update2004/12/21 ���x�����Ή�
		//�u�����]�_�v�Łu5�v�u1�v�̂����ꂩ��I�������ꍇ�̓R�����g���͕K�{
/*
		if(getKekkaTen() != null && !"".equals(getKekkaTen())){
			//�u��Ռ���(A)�C�O�w�p�����v�u��Ռ���(B)�C�O�w�p�����v�ȊO��ΏۂƂ���
			if(!"00043".equals(getJigyoCd()) && !"00053".equals(getJigyoCd())
				&& ("5".equals(getKekkaTen()) || "1".equals(getKekkaTen())))
			{
				//�R�����g�������͂̏ꍇ
				if(getComments() == null || "".equals(getComments()) 
				){
					errors.add(
						ActionErrors.GLOBAL_ERROR,
						new ActionError("errors.5027"));	
				}
			}
		}
*/

//		//�u�q�g�Q�m���E��`�q��͌����v�u������v�u�q�gES�זE�v�u��`�q�g���������v�u��`�q���×Տ������v�u�u�w�����v��
//		//�u1:���Y�N�x�v�u2:���N�x�ȍ~�v�̂����ꂩ��I�������ꍇ�̓R�����g���͕K�{
//		if(getHitogenomu() != null && !"".equals(getHitogenomu())){
//			if("1".equals(getHitogenomu()) || "2".equals(getHitogenomu())
//				|| "1".equals(getTokutei()) || "2".equals(getTokutei())
//				|| "1".equals(getHitoEs()) || "2".equals(getHitoEs())
//				|| "1".equals(getKumikae()) || "2".equals(getKumikae())			
//				|| "1".equals(getChiryo()) || "2".equals(getChiryo())	
//				|| "1".equals(getEkigaku()) || "2".equals(getEkigaku())
//			){
//				//�R�����g�������͂̏ꍇ
//				if(getComments() == null || "".equals(getComments()) 
//				){
//					errors.add(
//						ActionErrors.GLOBAL_ERROR,
//						new ActionError("errors.5028"));	
//				}
//			}
//		}			
		
//2006/10/28 �c�@�C����������
//		if(IShinsaKekkaMaintenance.RIGAI_OFF.equals(getRigai()) && 
//			IShinsaKekkaMaintenance.SOGO_HYOTEN.equals(getKekkaTen())){
        if(IShinsaKekkaMaintenance.SOGO_HYOTEN.equals(getKekkaTen())){
//2006/10/28�@�c�@�C�������܂�            
				errors.add(
				ActionErrors.GLOBAL_ERROR,
				new ActionError("errors.2002", new String[]{"�����]�_"}));	
		}
		
		
		
//		//�u��撲���v�u��茤���v�ŕ��S���̑Ó����Ɂu�~�v��I�������ꍇ�G���[
		if(IJigyoCd.JIGYO_CD_KIBAN_C_KIKAKU.equals(getJigyoCd())){
				if(IShinsaKekkaMaintenance.RIGAI_OFF.equals(getRigai()) && 
					IShinsaKekkaMaintenance.TEKISETU_BUNTANKIN.equals(getBuntankin())){
					errors.add(
					ActionErrors.GLOBAL_ERROR,
					new ActionError("errors.notselect", new String[]{"��Ռ���(C)��撲��"}) );
				}
		}else{
			if(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_A.equals(getJigyoCd()) || 
				IJigyoCd.JIGYO_CD_KIBAN_WAKATE_B.equals(getJigyoCd())){
				if(IShinsaKekkaMaintenance.RIGAI_OFF.equals(getRigai()) && 
					IShinsaKekkaMaintenance.TEKISETU_BUNTANKIN.equals(getBuntankin())){
						errors.add(
						ActionErrors.GLOBAL_ERROR,
						new ActionError("errors.notselect", new String[]{"��茤��"}) );
				}
			}	
		}

		//���̑��̕]�����ڂŁu�~�v��I�������ꍇ�ŁA�u�R�����g�v���ɓ��͂��Ȃ��ꍇ�́A�G���[
		//��Ղ̂ݎ��s�B
		if(IJigyoCd.JIGYO_CD_KIBAN_C_KIKAKU.equals(getJigyoCd())
			|| IJigyoCd.JIGYO_CD_KIBAN_WAKATE_A.equals(getJigyoCd()) 
			|| IJigyoCd.JIGYO_CD_KIBAN_WAKATE_B.equals(getJigyoCd())
//2006/04/10 �ǉ���������
			|| IJigyoCd.JIGYO_CD_WAKATESTART.equals(getJigyoCd())
//�c�@�ǉ������܂�		
		
		){
//2006/10/28�@�c�@�C����������			
//            if(StringUtil.isBlank(getOtherComment()) && 
//				IShinsaKekkaMaintenance.RIGAI_OFF.equals(getRigai()) &&
//				(IShinsaKekkaMaintenance.TEKISETU_KEIHI.equals(getDato()) ||
//				 IShinsaKekkaMaintenance.TEKISETU_JINKEN.equals(getJinken()))){
            if(StringUtil.isBlank(getOtherComment()) && 
                    (IShinsaKekkaMaintenance.TEKISETU_KEIHI.equals(getDato()) ||
                     IShinsaKekkaMaintenance.TEKISETU_JINKEN.equals(getJinken()))){ 
//2006/10/28�@�c�@�C�������܂�                
					errors.add(
					ActionErrors.GLOBAL_ERROR,
					new ActionError("errors.noComments") );
			}
		}else{
//2006/10/28�@�c�@�C����������            
//			if(StringUtil.isBlank(getOtherComment()) && 
//				IShinsaKekkaMaintenance.RIGAI_OFF.equals(getRigai()) &&
//				(IShinsaKekkaMaintenance.TEKISETU_KEIHI.equals(getDato()) ||
//				 IShinsaKekkaMaintenance.TEKISETU_JINKEN.equals(getJinken())||
//				 IShinsaKekkaMaintenance.TEKISETU_BUNTANKIN.equals(getBuntankin()))){
            if(StringUtil.isBlank(getOtherComment()) &&
                    (IShinsaKekkaMaintenance.TEKISETU_KEIHI.equals(getDato()) ||
                     IShinsaKekkaMaintenance.TEKISETU_JINKEN.equals(getJinken())||
                     IShinsaKekkaMaintenance.TEKISETU_BUNTANKIN.equals(getBuntankin()))){
//2006/10/28�@�c�@�C�������܂�                
					errors.add(
					ActionErrors.GLOBAL_ERROR,
					new ActionError("errors.noComments") );		
			}
		}
	
//		//2005.11.01 kainuma
//		//���̑��̕]�����ڂŁu�~�v��I�������ꍇ�ŁA�u�R�����g�v���ɓ��͂��Ȃ��ꍇ�́A�G���[
//		//��Ղ̂ݎ��s�B
//		if(getDato() != null && !"".equals(getDato()) && 
//		   getJinken() != null && !"".equals(getJinken()) && 
//		   getBuntankin() != null && !"".equals(getBuntankin())){
//		
//			if("3".equals(getDato()) || 
//				"3".equals(getJinken()) || 
//				  "3".equals(getBuntankin()))
//		    {
//			if(otherComment == null || otherComment.equals(""))
//				{
//			 errors.add(
//			 ActionErrors.GLOBAL_ERROR,
//			 new ActionError("errors.noComments") );
//			   	 
//				}
//		    }	
//		 }
		
		//��{���̓`�F�b�N�����܂�
		if (!errors.isEmpty()) {
			//�]���p�t�@�C���t���O��1�̏ꍇ�́A�t�@�C���đI���G���[��\��
			//���Q�֌W�ȊO 2005/11/15
			//if("1".equals(getHyokaFileFlg())){
			if("1".equals(getHyokaFileFlg()) && !"1".equals(this.rigai)){
				errors.add(
					ActionErrors.GLOBAL_ERROR,
					new ActionError("errors.2009"));
			}
			return errors;
		}

		//��^����----- 

		//�ǉ�����----- 
		//---------------------------------------------
		//�g�ݍ��킹�`�F�b�N	
		//---------------------------------------------
				
		return errors;
	}

	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------
    
    /**
     * �V�X�e���ԍ����擾
     * @return �V�X�e���ԍ�
     */
    public String getSystemNo() {
        return systemNo;
    }
    
    /**
     * �V�X�e���ԍ���ݒ�
     * @param string �V�X�e���ԍ�
     */
    public void setSystemNo(String string) {
        systemNo = string;
    }

    /**
     * �R�����ԍ����擾
     * @return �R�����ԍ�
     */
    public String getShinsainNo() {
        return shinsainNo;
    }

    /**
     * �R�����ԍ���ݒ�
     * @param string �R�����ԍ�
     */
    public void setShinsainNo(String string) {
        shinsainNo = string;
    }

    /**
     * ���Ƌ敪���擾
     * @return ���Ƌ敪
     */
    public String getJigyoKubun() {
        return jigyoKubun;
    }

    /**
     * ���Ƌ敪��ݒ�
     * @param string ���Ƌ敪
     */
    public void setJigyoKubun(String string) {
        jigyoKubun = string;
    }

    /**
     * ����ID���擾
     * @return ����ID
     */
    public String getJigyoId() {
        return jigyoId;
    }

    /**
     * ����ID��ݒ�
     * @param string ����ID
     */
    public void setJigyoId(String string) {
        jigyoId = string;
    }

    /**
     * ���ƃR�[�h���擾
     * @return ���ƃR�[�h
     */
    public String getJigyoCd() {
        return jigyoCd;
    }

    /**
     * ���ƃR�[�h��ݒ�
     * @param string ���ƃR�[�h
     */
    public void setJigyoCd(String string) {
        jigyoCd = string;
    }

    /**
     * �����]���iABC�j���擾
     * @return �����]���iABC�j
     */
    public String getKekkaAbc() {
        return kekkaAbc;
    }

    /**
     * �����]���iABC�j��ݒ�
     * @param string �����]���iABC�j
     */
    public void setKekkaAbc(String string) {
        kekkaAbc = string;
    }

    /**
     * �����]���i�_���j���擾
     * @return �����]���i�_���j
     */
    public String getKekkaTen() {
        return kekkaTen;
    }

    /**
     * �����]���i�_���j��ݒ�
     * @param string �����]���i�_���j
     */
    public void setKekkaTen(String string) {
        kekkaTen = string;
    }

    /**
     * �����]���i�_���j �G����擾
     * @return �����]���i�_���j �G��
     */
    public String getKekkaTenHoga() {
        return kekkaTenHoga;
    }

    /**
     * �����]���i�_���j �G���ݒ�
     * @param string �����]���i�_���j �G��
     */
    public void setKekkaTenHoga(String string) {
        kekkaTenHoga = string;
    }

    /**
     * �R�����g1���擾
     * @return �R�����g1
     */
    public String getComment1() {
        return comment1;
    }

    /**
     * �R�����g1��ݒ�
     * @param string �R�����g1
     */
    public void setComment1(String string) {
        comment1 = string;
    }

    /**
     * �R�����g2���擾
     * @return �R�����g2
     */
    public String getComment2() {
        return comment2;
    }

    /**
     * �R�����g2��ݒ�
     * @param string �R�����g2
     */
    public void setComment2(String string) {
        comment2 = string;
    }

    /**
     * �R�����g3���擾
     * @return �R�����g3
     */
    public String getComment3() {
        return comment3;
    }

    /**
     * �R�����g3��ݒ�
     * @param string �R�����g3
     */
    public void setComment3(String string) {
        comment3 = string;
    }

    /**
     * �R�����g4���擾
     * @return �R�����g4
     */
    public String getComment4() {
        return comment4;
    }

    /**
     * �R�����g4��ݒ�
     * @param string �R�����g4
     */
    public void setComment4(String string) {
        comment4 = string;
    }

    /**
     * �R�����g5���擾
     * @return �R�����g5
     */
    public String getComment5() {
        return comment5;
    }

    /**
     * �R�����g5��ݒ�
     * @param string �R�����g5
     */
    public void setComment5(String string) {
        comment5 = string;
    }

    /**
     * �R�����g6���擾
     * @return �R�����g6
     */
    public String getComment6() {
        return comment6;
    }

    /**
     * �R�����g6��ݒ�
     * @param string �R�����g6
     */
    public void setComment6(String string) {
        comment6 = string;
    }

    /**
     * �������e���擾
     * @return �������e
     */
    public String getKenkyuNaiyo() {
        return kenkyuNaiyo;
    }

    /**
     * �������e��ݒ�
     * @param string �������e
     */
    public void setKenkyuNaiyo(String string) {
        kenkyuNaiyo = string;
    }

    /**
     * �����v����擾
     * @return �����v��
     */
    public String getKenkyuKeikaku() {
        return kenkyuKeikaku;
    }

    /**
     * �����v���ݒ�
     * @param string �����v��
     */
    public void setKenkyuKeikaku(String string) {
        kenkyuKeikaku = string;
    }

    /**
     * �K�ؐ�-�C�O���擾
     * @return �K�ؐ�-�C�O
     */
    public String getTekisetsuKaigai() {
        return tekisetsuKaigai;
    }

    /**
     * �K�ؐ�-�C�O��ݒ�
     * @param string �K�ؐ�-�C�O
     */
    public void setTekisetsuKaigai(String string) {
        tekisetsuKaigai = string;
    }

    /**
     * �K�ؐ�-����(1)���擾
     * @return �K�ؐ�-����(1)
     */
    public String getTekisetsuKenkyu1() {
        return tekisetsuKenkyu1;
    }

    /**
     * �K�ؐ�-����(1)��ݒ�
     * @param string �K�ؐ�-����(1)
     */
    public void setTekisetsuKenkyu1(String string) {
        tekisetsuKenkyu1 = string;
    }

    /**
     * �K�ؐ����擾
     * @return �K�ؐ�
     */
    public String getTekisetsu() {
        return tekisetsu;
    }

    /**
     * �K�ؐ���ݒ�
     * @param string �K�ؐ�
     */
    public void setTekisetsu(String string) {
        tekisetsu = string;
    }

    /**
     * �Ó������擾
     * @return �Ó���
     */
    public String getDato() {
        return dato;
    }

    /**
     * �Ó�����ݒ�
     * @param string �Ó���
     */
    public void setDato(String string) {
        dato = string;
    }

    /**
     * ������\�҂��擾
     * @return ������\��
     */
    public String getShinseisha() {
        return shinseisha;
    }

    /**
     * ������\�҂�ݒ�
     * @param string ������\��
     */
    public void setShinseisha(String string) {
        shinseisha = string;
    }

    /**
     * �������S�҂��擾
     * @return �������S��
     */
    public String getKenkyuBuntansha() {
        return kenkyuBuntansha;
    }

    /**
     * �������S�҂�ݒ�
     * @param string �������S��
     */
    public void setKenkyuBuntansha(String string) {
        kenkyuBuntansha = string;
    }

    /**
     * �q�g�Q�m�����擾
     * @return �q�g�Q�m��
     */
    public String getHitogenomu() {
        return hitogenomu;
    }

    /**
     * �q�g�Q�m����ݒ�
     * @param string �q�g�Q�m��
     */
    public void setHitogenomu(String string) {
        hitogenomu = string;
    }

    /**
     * ��������擾
     * @return ������
     */
    public String getTokutei() {
        return tokutei;
    }

    /**
     * �������ݒ�
     * @param string ������
     */
    public void setTokutei(String string) {
        tokutei = string;
    }

    /**
     * �q�gES�זE���擾
     * @return �q�gES�זE
     */
    public String getHitoEs() {
        return hitoEs;
    }

    /**
     * �q�gES�זE��ݒ�
     * @param string �q�gES�זE
     */
    public void setHitoEs(String string) {
        hitoEs = string;
    }

    /**
     * ��`�q�g�����������擾
     * @return ��`�q�g��������
     */
    public String getKumikae() {
        return kumikae;
    }

    /**
     * ��`�q�g����������ݒ�
     * @param string ��`�q�g��������
     */
    public void setKumikae(String string) {
        kumikae = string;
    }
    
    /**
     * ��`�q���×Տ��������擾
     * @return ��`�q���×Տ�����
     */
    public String getChiryo() {
        return chiryo;
    }

    /**
     * ��`�q���×Տ�������ݒ�
     * @param string ��`�q���×Տ�����
     */
    public void setChiryo(String string) {
        chiryo = string;
    }

    /**
     * �u�w�������擾
     * @return �u�w����
     */
    public String getEkigaku() {
        return ekigaku;
    }

    /**
     * �u�w������ݒ�
     * @param string �u�w����
     */
    public void setEkigaku(String string) {
        ekigaku = string;
    }

    /**
     * �R�����g���擾
     * @return �R�����g
     */
    public String getComments() {
        return comments;
    }

    /**
     * �R�����g��ݒ�
     * @param string �R�����g
     */
    public void setComments(String string) {
        comments = string;
    }

    /**
     * ���Q�֌W���擾
     * @return ���Q�֌W
     */
    public String getRigai() {
        return rigai;
    }

    /**
     * ���Q�֌W��ݒ�
     * @param string ���Q�֌W
     */
    public void setRigai(String string) {
        rigai = string;
    }

    /**
     * �w�p�I�d�v���E�Ó������擾
     * @return �w�p�I�d�v���E�Ó���
     */
    public String getJuyosei() {
        return juyosei;
    }

    /**
     * �w�p�I�d�v���E�Ó�����ݒ�
     * @param string �w�p�I�d�v���E�Ó���
     */
    public void setJuyosei(String string) {
        juyosei = string;
    }

    /**
     * �Ƒn���E�v�V�����擾
     * @return �Ƒn���E�v�V��
     */
    public String getDokusosei() {
        return dokusosei;
    }

    /**
     * �Ƒn���E�v�V����ݒ�
     * @param string �Ƒn���E�v�V��
     */
    public void setDokusosei(String string) {
        dokusosei = string;
    }

    /**
     * �g�y���ʁE���Ր����擾
     * @return �g�y���ʁE���Ր�
     */
    public String getHakyukoka() {
        return hakyukoka;
    }

    /**
     * �g�y���ʁE���Ր���ݒ�
     * @param string �g�y���ʁE���Ր�
     */
    public void setHakyukoka(String string) {
        hakyukoka = string;
    }

    /**
     * ���s�\�́E���̓K�ؐ����擾
     * @return ���s�\�́E���̓K�ؐ�
     */
    public String getSuikonoryoku() {
        return suikonoryoku;
    }

    /**
     * ���s�\�́E���̓K�ؐ���ݒ�
     * @param string ���s�\�́E���̓K�ؐ�
     */
    public void setSuikonoryoku(String string) {
        suikonoryoku = string;
    }

    /**
     * �l���̕ی�E�@�ߓ��̏�����擾
     * @return �l���̕ی�E�@�ߓ��̏���
     */
    public String getJinken() {
        return jinken;
    }

    /**
     * �l���̕ی�E�@�ߓ��̏����ݒ�
     * @param string �l���̕ی�E�@�ߓ��̏���
     */
    public void setJinken(String string) {
        jinken = string;
    }

    /**
     * ���S�����擾
     * @return ���S��
     */
    public String getBuntankin() {
        return buntankin;
    }

    /**
     * ���S����ݒ�
     * @param string ���S��
     */
    public void setBuntankin(String string) {
        buntankin = string;
    }

    /**
     * ���̑��R�����g���擾
     * @return ���̑��R�����g
     */
    public String getOtherComment() {
        return otherComment;
    }

    /**
     * ���̑��R�����g��ݒ�
     * @param string ���̑��R�����g
     */
    public void setOtherComment(String string) {
        otherComment = string;
    }

    /**
     * �Y�t�t�@�C�����擾
     * @return �Y�t�t�@�C��
     */
	public String getTenpuPath() {
		return tenpuPath;
	}

    /**
     * �Y�t�t�@�C����ݒ�
     * @param string �Y�t�t�@�C��
     */
    public void setTenpuPath(String string) {
        tenpuPath = string;
    }

    /**
     * �Y�t�t�@�C���t���O���擾
     * @return �Y�t�t�@�C���t���O
     */
    public String getTenpuFlg() {
        return tenpuFlg;
    }

    /**
     * �Y�t�t�@�C���t���O��ݒ�
     * @param string �Y�t�t�@�C���t���O
     */
    public void setTenpuFlg(String string) {
        tenpuFlg = string;
    }

    /**
     * �Y�t�t�@�C��(�A�b�v���[�h�t�@�C��)���擾
     * @return �Y�t�t�@�C��(�A�b�v���[�h�t�@�C��)
     */
	public FormFile getTenpuUploadFile() {
		return tenpuUploadFile;
	}

    /**
     * �Y�t�t�@�C��(�A�b�v���[�h�t�@�C��)��ݒ�
     * @param file �Y�t�t�@�C��(�A�b�v���[�h�t�@�C��)
     */
	public void setTenpuUploadFile(FormFile file) {
		tenpuUploadFile = file;
	}

    /**
     * �]���p�t�@�C���t���O���擾
     * @return �]���p�t�@�C���t���O
     */
    public String getHyokaFileFlg() {
        return hyokaFileFlg;
    }

    /**
     * �]���p�t�@�C���t���O��ݒ�
     * @param string �]���p�t�@�C���t���O
     */
    public void setHyokaFileFlg(String string) {
        hyokaFileFlg = string;
    }

    /**
     * �����]���iABC�j�I�����X�g���擾
     * @return �����]���iABC�j�I�����X�g
     */
	public List getKekkaAbcList() {
		return kekkaAbcList;
	}

    /**
     * �����]���iABC�j�I�����X�g��ݒ�
     * @param list �����]���iABC�j�I�����X�g
     */
	public void setKekkaAbcList(List list) {
		kekkaAbcList = list;
	}

    /**
     * �����]���i�_���j�I�����X�g���擾
     * @return �����]���i�_���j�I�����X�g
     */
    public List getKekkaTenList() {
        return kekkaTenList;
    }

    /**
     * �����]���i�_���j�I�����X�g��ݒ�
     * @param list �����]���i�_���j�I�����X�g
     */
    public void setKekkaTenList(List list) {
        kekkaTenList = list;
    }

    /**
     * �����]���i�_���j�G��I�����X�g���擾
     * @return �����]���i�_���j�G��I�����X�g
     */
    public List getKekkaTenHogaList() {
        return kekkaTenHogaList;
    }

    /**
     * �����]���i�_���j�G��I�����X�g��ݒ�
     * @param list �����]���i�_���j�G��I�����X�g
     */
    public void setKekkaTenHogaList(List list) {
        kekkaTenHogaList = list;
    }

    /**
     * �������e�I�����X�g���擾
     * @return �������e�I�����X�g
     */
    public List getKenkyuNaiyoList() {
        return kenkyuNaiyoList;
    }

    /**
     * �������e�I�����X�g��ݒ�
     * @param list �������e�I�����X�g
     */
    public void setKenkyuNaiyoList(List list) {
        kenkyuNaiyoList = list;
    }

    /**
     * �����v��I�����X�g���擾
     * @return �����v��I�����X�g
     */
    public List getKenkyuKeikakuList() {
        return kenkyuKeikakuList;
    }

    /**
     * �����v��I�����X�g��ݒ�
     * @param list �����v��I�����X�g
     */
    public void setKenkyuKeikakuList(List list) {
        kenkyuKeikakuList = list;
    }

    /**
     * �K�ؐ�-�C�O�I�����X�g���擾
     * @return �K�ؐ�-�C�O�I�����X�g
     */
    public List getTekisetsuKaigaiList() {
        return tekisetsuKaigaiList;
    }

    /**
     * �K�ؐ�-�C�O�I�����X�g��ݒ�
     * @param list �K�ؐ�-�C�O�I�����X�g
     */
    public void setTekisetsuKaigaiList(List list) {
        tekisetsuKaigaiList = list;
    }

    /**
     * �K�ؐ�-�����i1�j�I�����X�g���擾
     * @return �K�ؐ�-�����i1�j�I�����X�g
     */
    public List getTekisetsuKenkyu1List() {
        return tekisetsuKenkyu1List;
    }

    /**
     * �K�ؐ�-�����i1�j�I�����X�g��ݒ�
     * @param list �K�ؐ�-�����i1�j�I�����X�g
     */
    public void setTekisetsuKenkyu1List(List list) {
        tekisetsuKenkyu1List = list;
    }

    /**
     * �K�ؐ��I�����X�g���擾
     * @return �K�ؐ��I�����X�g
     */
    public List getTekisetsuList() {
        return tekisetsuList;
    }

    /**
     * �K�ؐ��I�����X�g��ݒ�
     * @param list �K�ؐ��I�����X�g
     */
    public void setTekisetsuList(List list) {
        tekisetsuList = list;
    }

    /**
     * �Ó����I�����X�g���擾
     * @return �Ó����I�����X�g
     */
    public List getDatoList() {
        return datoList;
    }

    /**
     * �Ó����I�����X�g��ݒ�
     * @param list �Ó����I�����X�g
     */
    public void setDatoList(List list) {
        datoList = list;
    }

    /**
     * ������\�ґI�����X�g���擾
     * @return ������\�ґI�����X�g
     */
    public List getShinseishaList() {
        return shinseishaList;
    }

    /**
     * ������\�ґI�����X�g��ݒ�
     * @param list ������\�ґI�����X�g
     */
    public void setShinseishaList(List list) {
        shinseishaList = list;
    }

    /**
     * �������S�ґI�����X�g���擾
     * @return �������S�ґI�����X�g
     */
    public List getKenkyuBuntanshaList() {
        return kenkyuBuntanshaList;
    }

    /**
     * �������S�ґI�����X�g��ݒ�
     * @param list �������S�ґI�����X�g
     */
    public void setKenkyuBuntanshaList(List list) {
        kenkyuBuntanshaList = list;
    }

    /**
     * �q�g�Q�m���I�����X�g���擾
     * @return �q�g�Q�m���I�����X�g
     */
    public List getHitogenomuList() {
        return hitogenomuList;
    }

    /**
     * �q�g�Q�m���I�����X�g��ݒ�
     * @param list �q�g�Q�m���I�����X�g
     */
    public void setHitogenomuList(List list) {
        hitogenomuList = list;
    }

    /**
     * ������I�����X�g���擾
     * @return ������I�����X�g
     */
    public List getTokuteiList() {
        return tokuteiList;
    }

    /**
     * ������I�����X�g��ݒ�
     * @param list ������I�����X�g
     */
    public void setTokuteiList(List list) {
        tokuteiList = list;
    }

    /**
     * �q�gES�זE�I�����X�g���擾
     * @return �q�gES�זE�I�����X�g
     */
    public List getHitoEsList() {
        return hitoEsList;
    }

    /**
     * �q�gES�זE�I�����X�g��ݒ�
     * @param list �q�gES�זE�I�����X�g
     */
    public void setHitoEsList(List list) {
        hitoEsList = list;
    }

    /**
     * ��`�q�g���������I�����X�g���擾
     * @return ��`�q�g���������I�����X�g
     */
    public List getKumikaeList() {
        return kumikaeList;
    }

    /**
     * ��`�q�g���������I�����X�g��ݒ�
     * @param list ��`�q�g���������I�����X�g
     */
    public void setKumikaeList(List list) {
        kumikaeList = list;
    }

    /**
     * ��`�q���×Տ������I�����X�g���擾
     * @return ��`�q���×Տ������I�����X�g
     */
	public List getChiryoList() {
		return chiryoList;
	}

    /**
     * ��`�q���×Տ������I�����X�g��ݒ�
     * @param list ��`�q���×Տ������I�����X�g
     */
    public void setChiryoList(List list) {
        chiryoList = list;
    }

    /**
     * �u�w�����I�����X�g���擾
     * @return �u�w�����I�����X�g
     */
	public List getEkigakuList() {
		return ekigakuList;
	}

    /**
     * �u�w�����I�����X�g��ݒ�
     * @param list �u�w�����I�����X�g
     */
	public void setEkigakuList(List list) {
		ekigakuList = list;
	}

    /**
     * ���Q�֌W���X�g���擾
     * @return ���Q�֌W���X�g
     */
    public List getRigaiList() {
        return rigaiList;
    }

    /**
     * ���Q�֌W���X�g��ݒ�
     * @param list ���Q�֌W���X�g
     */
    public void setRigaiList(List list) {
        rigaiList = list;
    }

    /**
     * �w�p�I�d�v���E�Ó������X�g���擾
     * @return �w�p�I�d�v���E�Ó������X�g
     */
    public List getJuyoseiList() {
        return juyoseiList;
    }

    /**
     * �w�p�I�d�v���E�Ó������X�g��ݒ�
     * @param list �w�p�I�d�v���E�Ó������X�g
     */
    public void setJuyoseiList(List list) {
        juyoseiList = list;
    }

    /**
     * �Ƒn���E�v�V�����X�g���擾
     * @return �Ƒn���E�v�V�����X�g
     */
    public List getDokusoseiList() {
        return dokusoseiList;
    }

    /**
     * �Ƒn���E�v�V�����X�g��ݒ�
     * @param list �Ƒn���E�v�V�����X�g
     */
    public void setDokusoseiList(List list) {
        dokusoseiList = list;
    }

    /**
     * �g�y���ʁE���Ր����X�g���擾
     * @return �g�y���ʁE���Ր����X�g
     */
    public List getHakyukokaList() {
        return hakyukokaList;
    }

    /**
     * �g�y���ʁE���Ր����X�g��ݒ�
     * @param list �g�y���ʁE���Ր����X�g
     */
    public void setHakyukokaList(List list) {
        hakyukokaList = list;
    }

    /**
     * ���s�\�́E���̓K�ؐ����X�g���擾
     * @return ���s�\�́E���̓K�ؐ����X�g
     */
    public List getSuikonoryokuList() {
        return suikonoryokuList;
    }

    /**
     * ���s�\�́E���̓K�ؐ����X�g��ݒ�
     * @param list ���s�\�́E���̓K�ؐ����X�g
     */
    public void setSuikonoryokuList(List list) {
        suikonoryokuList = list;
    }

    /**
     * �l���̕ی�E�@�ߓ��̏��烊�X�g���擾
     * @return �l���̕ی�E�@�ߓ��̏��烊�X�g
     */
    public List getJinkenList() {
        return jinkenList;
    }

    /**
     * �l���̕ی�E�@�ߓ��̏��烊�X�g��ݒ�
     * @param list �l���̕ی�E�@�ߓ��̏��烊�X�g
     */
    public void setJinkenList(List list) {
        jinkenList = list;
    }

    /**
     * ���S�����X�g���擾
     * @return ���S�����X�g
     */
	public List getBuntankinList() {
		return buntankinList;
	}

    /**
     * ���S�����X�g��ݒ�
     * @param list ���S�����X�g
     */
	public void setBuntankinList(List list) {
		buntankinList = list;
	}

	/**
     * ���S�Ƃ��Ă̑Ó������擾���܂��B
     * 
     * @return wakates
     */
    
    public String getWakates() {
    	return wakates;
    }

	/**
     * ���S�Ƃ��Ă̑Ó�����ݒ肵�܂��B
     * 
     * @param wakates wakates
     */
    
    public void setWakates(String wakates) {
    	this.wakates = wakates;
    }
}
