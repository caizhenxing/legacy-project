/*======================================================================
 *    SYSTEM      : ���{�w�p�U����d�q�\���V�X�e���i�Ȋw������⏕���j
 *    Source name : UserContainer.java
 *    Description : �Z�b�V�����Ŏg�p��������i�[���邽�߂̃R���e�i�N���X
 *
 *    Author      : 
 *    Date        : 
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *                  V1.0                       �V�K�쐬
 *    2006/06/14    V1.1        DIS            �C��
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.common;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

import jp.go.jsps.kaken.model.vo.BukyokutantoInfo;
import jp.go.jsps.kaken.model.vo.CheckListInfo;
import jp.go.jsps.kaken.model.vo.CheckListSearchInfo;
import jp.go.jsps.kaken.model.vo.GyomutantoInfo;
import jp.go.jsps.kaken.model.vo.JigyoKanriInfo;
import jp.go.jsps.kaken.model.vo.KenkyushaInfo;
import jp.go.jsps.kaken.model.vo.PunchDataKanriInfo;
import jp.go.jsps.kaken.model.vo.QuestionInfo;
import jp.go.jsps.kaken.model.vo.RyoikiKeikakushoInfo;
import jp.go.jsps.kaken.model.vo.ShinsaJokyoInfo;
import jp.go.jsps.kaken.model.vo.ShinsaKekka2ndInfo;
import jp.go.jsps.kaken.model.vo.ShinsaKekkaInputInfo;
import jp.go.jsps.kaken.model.vo.ShinsaKekkaReferenceInfo;
import jp.go.jsps.kaken.model.vo.ShinsainInfo;
import jp.go.jsps.kaken.model.vo.ShinseiSearchInfo;
import jp.go.jsps.kaken.model.vo.ShinseishaInfo;
import jp.go.jsps.kaken.model.vo.ShinseishaSearchInfo;
import jp.go.jsps.kaken.model.vo.ShoruiKanriInfo;
import jp.go.jsps.kaken.model.vo.ShozokuInfo;
import jp.go.jsps.kaken.model.vo.SimpleShinseiDataInfo;
import jp.go.jsps.kaken.model.vo.TeishutsuShoruiSearchInfo;
import jp.go.jsps.kaken.model.vo.UserInfo;
import jp.go.jsps.kaken.model.vo.WarifuriInfo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * �Z�b�V�����Ŏg�p��������i�[���邽�߂̃R���e�i�N���X�B
 * 
 */
public class UserContainer implements HttpSessionBindingListener {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/** ���O�N���X�B */
	private static final Log log = LogFactory.getLog(UserContainer.class);

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------
	
	/** ���[�U��� */
	private UserInfo userInfo  = null;
	
	/** �o�^�X�V�p �\���ҏ�� */
	private ShinseishaInfo shinseishaInfo = null;
	
	/** �o�^�X�V�p �����@�֏�� */
	private ShozokuInfo shozokuInfo = null;
	
	/** �o�^�X�V�p ���ƊǗ���� */
	private JigyoKanriInfo jigyoKanriInfo = null;
	
	/** �o�^�p ���ފǗ���� */
	private ShoruiKanriInfo shoruiKanriInfo = null;
	
	/** �\���p ���ފǗ���񃊃X�g */
	private List shoruiKanriList = null;
	
	/** �o�^�X�V�p �Ɩ��S���ҏ�� */
	private GyomutantoInfo gyomutantoInfo = null;
	
	/** �o�^�X�V�p �R������� */
	private ShinsainInfo shinsainInfo = null;
	
	/** �ȈՐ\����� */
	private SimpleShinseiDataInfo simpleShinseiDataInfo = null;
	
	/** �o�^�X�V�p �R�����ʓ��͏�� */
	private ShinsaKekkaInputInfo shinsaKekkaInputInfo = null;

	/** �R��������U���� */
	private WarifuriInfo warifuriInfo = null;
	
	/** �\���p 1���R�����ʏ��i�Q�Ɨp�j */
	private ShinsaKekkaReferenceInfo shinsaKekkaReferenceInfo = null;

	/** �\���p 2���R�����ʏ��i2���R�����ʓ��͗p�j */
	private ShinsaKekka2ndInfo shinsaKekka2ndInfo = null;
	
	/** ���������ێ��p �\���f�[�^������� */
	private ShinseiSearchInfo shinseiSearchInfo = null;

	/** �p���`�f�[�^��� */
	private PunchDataKanriInfo punchDataKanriInfo = null;
		
	/** �\���p �����]�_��� */
	private Map sogoHyotenInfo = null;

	/** �R���󋵏�� */
	private ShinsaJokyoInfo shinsaJokyoInfo = null;
	
	// 2005/04/07 �ǉ���������----------------------------------------------
	// ���R ���ǒS���ҏ��ێ��̂���	
	
	/** ���ǒS���ҏ�� */
	private BukyokutantoInfo bukyokutantoInfo = null;
	
	// �ǉ� �����܂�--------------------------------------------------------
	
	// 2005/04/15 �ǉ� ��������---------------------------------------------
	// ���R �`�F�b�N���X�g���ێ��̂���
	
	/** �`�F�b�N���X�g��� */
	private CheckListInfo checkListInfo = null;

    /** �`�F�b�N���X�g������� */
	private CheckListSearchInfo checkListSearchInfo = null;
	
	// �ǉ� �����܂�--------------------------------------------------------
	
	//2005/04/15 �ǉ� ��������----------------------------------------------
	//���R �����ҏ��ێ��̂���
	
	/** �����ҏ�� */
	private KenkyushaInfo kenkyushaInfo = null;
	
	//�ǉ� �����܂�---------------------------------------------------------
	
	//2005/04/20 �ǉ� ��������----------------------------------------------
	//���R �\���Ҍ��������ێ��̂���
	
	/** �\���Ҍ������ */
	private ShinseishaSearchInfo shinseishaSearchInfo = null;
	
	//�ǉ� �����܂�---------------------------------------------------------
	
	/** �A���P�[�g��� */
	private QuestionInfo questionInfo = null;
			
	//2005/04/29 �ǉ� ----------------------------------------------��������
	//���R �ꊇ�Đݒ�p�̌�����ID�i�[�p�ɒǉ�
    /** ������ID���X�g */
	private String[] kenkyuNo = null;
	
	//2005/04/29 �ǉ� ----------------------------------------------�����܂�

    // 20060605 Wang Xiancheng add start
    /** �\����񃊃X�g */
    private SimpleShinseiDataInfo[] simpleShinseiDataInfos = null;
    // 20060605 Wang Xiancheng add start

	//2006/06/14 by jzx add start
	/** �̈�v�揑��� */
	private RyoikiKeikakushoInfo ryoikikeikakushoInfo = null;
    //2006/06/14 by jzx add end
    
    //2006/06/15 by mcj add start
    /** �̈�v�揑��� */
    private TeishutsuShoruiSearchInfo teishutsuShoruiSearchInfo = null;
    //2006/06/15 by mcj add end
    
	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * �R���X�g���N�^�B
	 */
	public UserContainer() {
		super();
	}

	//---------------------------------------------------------------------
	// Implementation of HttpSessionBindingListener interface
	//---------------------------------------------------------------------

	/* (�� Javadoc)
	 * @see javax.servlet.http.HttpSessionBindingListener#valueBound(javax.servlet.http.HttpSessionBindingEvent)
	 */
	public void valueBound(HttpSessionBindingEvent e) {
		
	}

	/* (�� Javadoc)
	 * @see javax.servlet.http.HttpSessionBindingListener#valueUnbound(javax.servlet.http.HttpSessionBindingEvent)
	 */
	public void valueUnbound(HttpSessionBindingEvent e) {
		
	}
	
	
	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------

	/**
	 * ���O�C�����[�U�����擾����B	
	 * @return	���O�C�����[�U���
	 */
	public UserInfo getUserInfo() {
		return userInfo;
	}

	/**
	 * ���O�C�����[�U�����Z�b�g����B
	 * @param info	���O�C�����[�U���
	 */
	public void setUserInfo(UserInfo info) {
		userInfo = info;
	}

	/**
	 * �o�^�X�V�\���ҏ����擾����B
	 * @return	�o�^�X�V�\���ҏ��
	 */
	public ShinseishaInfo getShinseishaInfo() {
		return shinseishaInfo;
	}

	/**
	 * �o�^�X�V�\���ҏ����Z�b�g����B
	 * @param info	�o�^�X�V�\���ҏ��
	 */
	public void setShinseishaInfo(ShinseishaInfo info) {
		shinseishaInfo = info;
	}

	/**
	 * �o�^�X�V�����@�֏����Z�b�g����B
	 * @param info	�o�^�X�V�����@�֏��
	 */
	public void setShozokuInfo(ShozokuInfo info) {
		shozokuInfo = info;
	}

	/**
	 * �o�^�X�V�����@�֏����擾����B
	 * @return	�o�^�X�V�����@�֏��
	 */
	public ShozokuInfo getShozokuInfo() {
		return shozokuInfo;
	}
	
	/**
	 * �o�^�X�V���ƊǗ������Z�b�g����B
	 * @param info	�o�^�X�V���ƊǗ����
	 */
	public void setJigyoKanriInfo(JigyoKanriInfo info) {
		jigyoKanriInfo = info;
	}

	/**
	 * �o�^�X�V���ƊǗ������擾����B
	 * @return	�o�^�X�V���ƊǗ����
	 */
	public JigyoKanriInfo getJigyoKanriInfo() {
		return jigyoKanriInfo;
	}

	/**
	 * �o�^���ފǗ������Z�b�g����B
	 * @param info	�o�^���ފǗ����
	 */
	public void setShoruiKanriInfo(ShoruiKanriInfo info) {
		shoruiKanriInfo = info;
	}

	/**
	 * �o�^���ފǗ������擾����B
	 * @return	�o�^���ފǗ����
	 */
	public ShoruiKanriInfo getShoruiKanriInfo() {
		return shoruiKanriInfo;
	}


	/**
	 * �\�����ފǗ���񃊃X�g���擾����B
	 * @return list	�o�^���ފǗ���񃊃X�g
	 */
	public List getShoruiKanriList() {
		return shoruiKanriList;
	}

	/**
	 * �\�����ފǗ���񃊃X�g���Z�b�g����B
	 * @param list �o�^���ފǗ���񃊃X�g
	 */
	public void setShoruiKanriList(List list) {
		shoruiKanriList = list;
	}

	/**
	 * �o�^�X�V�Ɩ��S���ҏ����擾����B
	 * @return �o�^�X�V�Ɩ��S���ҏ��
	 */
	public GyomutantoInfo getGyomutantoInfo() {
		return gyomutantoInfo;
	}

	/**
	 * �o�^�X�V�Ɩ��S���ҏ����Z�b�g����B
	 * @param info	�o�^�X�V�Ɩ��S���ҏ��
	 */
	public void setGyomutantoInfo(GyomutantoInfo info) {
		gyomutantoInfo = info;
	}

	/**
	 * �o�^�X�V�R���������擾����B
	 * @return	�o�^�X�V�R�������
	 */
	public ShinsainInfo getShinsainInfo() {
		return shinsainInfo;
	}

	/**
	 * �o�^�X�V�R���������Z�b�g����B
	 * @param info	�o�^�X�V�R�������
	 */
	public void setShinsainInfo(ShinsainInfo info) {
		shinsainInfo = info;
	}

	/**
	 * �ȈՐ\�������擾����B
	 * @return �ȈՐ\�����
	 */
	public SimpleShinseiDataInfo getSimpleShinseiDataInfo() {
		return simpleShinseiDataInfo;
	}

	/**
	 * �ȈՐ\�������Z�b�g����B
	 * @param info �ȈՐ\�����
	 */
	public void setSimpleShinseiDataInfo(SimpleShinseiDataInfo info) {
		simpleShinseiDataInfo = info;
	}

	/**
	 * �R�����ʓ��͏����擾����B
	 * @return
	 */
	public ShinsaKekkaInputInfo getShinsaKekkaInputInfo() {
		return shinsaKekkaInputInfo;
	}

	/**
	 * �R�����ʓ��͏����Z�b�g����B
	 * @param info �R�����ʓ��͏��
	 */
	public void setShinsaKekkaInputInfo(ShinsaKekkaInputInfo info) {
		shinsaKekkaInputInfo = info;
	}

	/**
	 * �R��������U������擾����B
	 * @return
	 */
	public WarifuriInfo getWarifuriInfo() {
		return warifuriInfo;
	}

	/**
	 * �R��������U������Z�b�g����B
	 * @param info �R��������U����
	 */
	public void setWarifuriInfo(WarifuriInfo info) {
		warifuriInfo = info;
	}

	/**
	 *  1���R�����ʏ��i�Q�Ɨp�j�I�u�W�F�N�g���擾����B
	 * @return
	 */
	public ShinsaKekkaReferenceInfo getShinsaKekkaReferenceInfo() {
		return shinsaKekkaReferenceInfo;
	}

	/**
	 *  1���R�����ʏ��i�Q�Ɨp�j�I�u�W�F�N�g���Z�b�g����B
	 * @param info 1���R�����ʏ��i�Q�Ɨp�j�I�u�W�F�N�g
	 */
	public void setShinsaKekkaReferenceInfo(ShinsaKekkaReferenceInfo info) {
		shinsaKekkaReferenceInfo = info;
	}

	/**
	 * 2���R�����ʏ��i2���R�����ʓ��͗p�j�I�u�W�F�N�g���擾����B
	 * @return
	 */
	public ShinsaKekka2ndInfo getShinsaKekka2ndInfo() {
		return shinsaKekka2ndInfo;
	}

	/**
	 * 2���R�����ʏ��i2���R�����ʓ��͗p�j�I�u�W�F�N�g���Z�b�g����B
	 * @param info 2���R�����ʏ��i2���R�����ʓ��͗p�j�I�u�W�F�N�g
	 */
	public void setShinsaKekka2ndInfo(ShinsaKekka2ndInfo info) {
		shinsaKekka2ndInfo = info;
	}

	/**
	 * �\���f�[�^�������i�����f�[�^�ێ��p�j�I�u�W�F�N�g���擾����B
	 * @return
	 */
	public ShinseiSearchInfo getShinseiSearchInfo() {
		return shinseiSearchInfo;
	}

	/**
	 * �\���f�[�^�������i�����f�[�^�ێ��p�j�I�u�W�F�N�g���Z�b�g����B
	 * @param info 2���R�����ʏ��i2���R�����ʓ��͗p�j�I�u�W�F�N�g
	 */
	public void setShinseiSearchInfo(ShinseiSearchInfo info) {
		shinseiSearchInfo = info;
	}
	
	/**
     * �p���`�f�[�^�����擾����B
	 * @return �p���`�f�[�^���
	 */
	public PunchDataKanriInfo getPunchDataKanriInfo() {
		return punchDataKanriInfo;
	}

	/**
     * �p���`�f�[�^�����Z�b�g����B
	 * @param info �p���`�f�[�^���
	 */
	public void setPunchDataKanriInfo(PunchDataKanriInfo info) {
		punchDataKanriInfo = info;
	}
	
	/**
	 * �����]�_���X�g���I�u�W�F�N�g���擾����B
	 * @return
	 */
	public Map getSogoHyotenInfo() {
		return sogoHyotenInfo;
	}

	/**
	 * �����]�_���X�g���I�u�W�F�N�g���Z�b�g����B
	 * @param map �����]�_���X�g���I�u�W�F�N�g
	 */
	public void setSogoHyotenInfo(Map map) {
		sogoHyotenInfo = map;
	}

	/**
	 * �R���󋵏����擾����B
	 * @return
	 */
	public ShinsaJokyoInfo getShinsaJokyoInfo() {
		return shinsaJokyoInfo;
	}

	/**
	 * �R���󋵏����Z�b�g����B
	 * @param info �R���󋵏��
	 */
	public void setShinsaJokyoInfo(ShinsaJokyoInfo info) {
		shinsaJokyoInfo = info;
	}

	// 2005/04/07 �ǉ���������---------------------------------------------
	// ���R ���ǒS���ҏ��ێ��̂���	
	
	/**
	 * ���ǒS���ҏ����擾����
	 * @return
	 */
	public BukyokutantoInfo getBukyokutantoInfo() {
		return bukyokutantoInfo;
	}

	/**
	 * ���ǒS���ҏ����Z�b�g����
	 * @param info
	 */
	public void setBukyokutantoInfo(BukyokutantoInfo info) {
		bukyokutantoInfo = info;
	}
	// �ǉ� �����܂�--------------------------------------------------------
	
	// 2005/04/15 �ǉ� ��������---------------------------------------------
	// ���R �`�F�b�N���X�g���ێ��̂���
		
	/**
     * �`�F�b�N���X�g�����擾����B
	 * @return �`�F�b�N���X�g���
	 */
	public CheckListInfo getCheckListInfo() {
		return checkListInfo;
	}

	/**
     * �`�F�b�N���X�g�����Z�b�g����B
	 * @param info �`�F�b�N���X�g���
	 */
	public void setCheckListInfo(CheckListInfo info) {
		checkListInfo = info;
	}
	
	/**
     * �`�F�b�N���X�g���������擾����B
	 * @return �`�F�b�N���X�g��������߂��܂��B
	 */
	public CheckListSearchInfo getCheckListSearchInfo() {
		return checkListSearchInfo;
	}

	/**
     * �`�F�b�N���X�g���������Z�b�g����B
	 * @param checkListSearchInfo �`�F�b�N���X�g��������ݒ�B
	 */
	public void setCheckListSearchInfo(CheckListSearchInfo checkListSearchInfo) {
		this.checkListSearchInfo = checkListSearchInfo;
	}
	//	�ǉ� �����܂�--------------------------------------------------------
	//	2005/04/15 �ǉ� ��������---------------------------------------------
	//���R �����ҏ��ێ��̂���
	
	/**
     * �����ҏ����擾����B
	 * @return �����ҏ��
	 */
	public KenkyushaInfo getKenkyushaInfo() {
		return kenkyushaInfo;
	}

	/**
     * �����ҏ����Z�b�g����B
	 * @param info �����ҏ��
	 */
	public void setKenkyushaInfo(KenkyushaInfo info) {
		kenkyushaInfo = info;
	}
	
	//�ǉ� �����܂�----------------------------------------------------------
	
	//2005/04/20 �ǉ� ��������----------------------------------------------
	//���R �\���Ҍ��������ێ��̂���	
	/**
     * �\���Ҍ��������擾����B
	 * @return �\���Ҍ������
	 */
	public ShinseishaSearchInfo getShinseishaSearchInfo() {
		return shinseishaSearchInfo;
	}

	/**
     * �\���Ҍ��������Z�b�g����B
	 * @param info �\���Ҍ������
	 */
	public void setShinseishaSearchInfo(ShinseishaSearchInfo info) {
		shinseishaSearchInfo = info;
	}
	//�ǉ� �����܂�----------------------------------------------------------

	
	//2005/04/29 �ǉ� ----------------------------------------------��������
	//���R �ꊇ�Đݒ�p��ID�i�[�p�ɒǉ�
	/**
     * ������ID���X�g���擾����B
	 * @return ������ID���X�g
	 */
	public String[] getKenkyuNo() {
		return kenkyuNo;
	}
	/**
     * ������ID���X�g���Z�b�g����B
	 * @param kenkyuNo ������ID���X�g
	 */
	public void setKenkyuNo(String[] kenkyuNo) {
		this.kenkyuNo = kenkyuNo;
	}
	//2005/04/29 �ǉ� ----------------------------------------------�����܂�

	/**
     * �A���P�[�g�����擾����B
	 * @return �A���P�[�g���
	 */
	public QuestionInfo getQuestionInfo() {
		return questionInfo;
	}

	/**
     * �A���P�[�g�����Z�b�g����B
	 * @param info �A���P�[�g���
	 */
	public void setQuestionInfo(QuestionInfo info) {
		questionInfo = info;
	}

	// 20060605 Wang Xiancheng add start
	/**
     * �\����񃊃X�g���擾����B
	 * @return �\����񃊃X�g
	 */
	public SimpleShinseiDataInfo[] getSimpleShinseiDataInfos() {
		return simpleShinseiDataInfos;
	}

	/**
     * �\����񃊃X�g���Z�b�g����B
	 * @param simpleShinseiDataInfos �\����񃊃X�g
	 */
	public void setSimpleShinseiDataInfos(
			SimpleShinseiDataInfo[] simpleShinseiDataInfos) {
		this.simpleShinseiDataInfos = simpleShinseiDataInfos;
	}
	// 20060605 Wang Xiancheng add end

    /**
     * �̈�v�揑�����擾����B
     * @return �̈�v�揑���
     */
    public RyoikiKeikakushoInfo getRyoikikeikakushoInfo() {
        return ryoikikeikakushoInfo;
    }

    /**
     * �̈�v�揑�����Z�b�g����B
     * @param ryoikikeikakushoInfo �̈�v�揑���
     */
    public void setRyoikikeikakushoInfo(RyoikiKeikakushoInfo ryoikikeikakushoInfo) {
        this.ryoikikeikakushoInfo = ryoikikeikakushoInfo;
    }

    /**
     * @return Returns the teishutsuShoruiSearchInfo.
     */
    public TeishutsuShoruiSearchInfo getTeishutsuShoruiSearchInfo() {
        return teishutsuShoruiSearchInfo;
    }

    /**
     * @param teishutsuShoruiSearchInfo The teishutsuShoruiSearchInfo to set.
     */
    public void setTeishutsuShoruiSearchInfo(TeishutsuShoruiSearchInfo teishutsuShoruiSearchInfo) {
        this.teishutsuShoruiSearchInfo = teishutsuShoruiSearchInfo;
    }
}