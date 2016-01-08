/*======================================================================
 *    SYSTEM      : ���{�w�p�U����d�q�\���V�X�e���i�Ȋw������⏕���j
 *    Source name : StatusGaiyoManager.java
 *    Description : �X�e�[�^�X�i�T�v�j�Ǘ�
 *
 *    Author      : takano
 *    Date        : 2004/01/20
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2004/01/20    v1.0        takano         �V�K�쐬
 *    2006/07/17    v1.1        DIS.dyh        �ύX
 *====================================================================== 
 */
package jp.go.jsps.kaken.status;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.go.jsps.kaken.model.dao.exceptions.DataAccessException;
import jp.go.jsps.kaken.model.dao.select.SelectUtil;
import jp.go.jsps.kaken.model.dao.util.DatabaseUtil;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.role.UserRole;
import jp.go.jsps.kaken.model.vo.SimpleShinseiDataInfo;
import jp.go.jsps.kaken.model.vo.UserInfo;
import jp.go.jsps.kaken.util.Page;

/**
 * �X�e�[�^�X�Ǘ�
 */
public class StatusManager {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------
	/** �u��������F1���R������ */
	private static final String SHINSA_KEKKA_1            = "\\{KEKKA1\\}";
	
	/** �u��������F2���R������ */
	private static final String SHINSA_KEKKA_2            = "\\{KEKKA2\\}";

    /** �\���҂Ɨ̈��\�҂̋敪(2:�̈��\��) */
	private int RYOIKI = 2;

    /** �\���҂Ɨ̈��\�҂̋敪(0:�\����) */
	private int NOT_RYOIKI = 0;

	/** 
	 * �\���󋵃}�X�^Map<!--�B-->
	 * �e���s���[�U���Ƃ̐\���󋵖���Map���i�[����Ă���B
	 * ���ƌ��J�O���Map��List�Ŋi�[����Ă���B
	 * List(0)�����J�O�AList(1)�����J���Map�B���J�O�ƌ��J�オ�����ꍇ������B
	 * �L�[�l��'null'�̏ꍇ�͐\���󋵖��́i���́j��Map���Ԃ�B
	 * �\���󋵖���Map�i���́j���~�����ꍇ�́A������"DEFAULT"���L�[�l�Ƃ��Ďw�肷��B
	 * �L�[���ځF�e���[�U��UserRole
	 */
	private static Map  MASTER_STATUS_MAP                  = new HashMap();                                
	
	/**
	 * �\���󋵖���Map�i���́j
	 * �L�[�l�F�X�e�[�^�X�R�[�h(Integer)
	 */
	private static Map  STATUS_MAP                         = new HashMap();
	
	/**
	 * �\���󋵖���Map�i�\���Ҍ����\�����́j
	 * �L�[�l�F�X�e�[�^�X�R�[�h(Integer)
	 */
	private static Map  SHINSEISHA_STATUS_MAP              = new HashMap();
	
	/**
	 * �\���󋵖���Map�i�\���Ҍ������J�O�\�����́j
	 * �L�[�l�F�X�e�[�^�X�R�[�h(Integer)
	 */
	private static Map  SHINSNEISHA_KOKAIMAE_STATUS_MAP    = new HashMap();
	
	/**
	 * �\���󋵖���Map�i�̈��\�Ҍ����\�����j
	 * �L�[�l�F�X�e�[�^�X�R�[�h(Integer)
	 */
	private static Map  RYOUIKIDAIHYOU_STATUS_MAP    = new HashMap();	
	/**
	 * �\���󋵖���Map�i�����@�֌����\�����́j
	 * �L�[�l�F�X�e�[�^�X�R�[�h(Integer)
	 */
	private static Map  SHOZOKU_STATUS_MAP                 = new HashMap();
	
	/**
	 * �\���󋵖���Map�i�����@�֌������J�O�\�����́j
	 * �L�[�l�F�X�e�[�^�X�R�[�h(Integer)
	 */
	private static Map  SHOZOKU_KOKAIMAE_STATUS_MAP        = new HashMap();

	/**
	 * �\���󋵖���Map�i���ی����\�����́j
	 * �L�[�l�F�X�e�[�^�X�R�[�h(Integer)
	 */
	private static Map  GENKA_STATUS_MAP                   = new HashMap();
	
	/**
	 * �\���󋵖���Map�i�R���������\�����́j
	 * �L�[�l�F�X�e�[�^�X�R�[�h(Integer)
	 */
	private static Map  SHINSAIN_STATUS_MAP                = new HashMap();
	
	/**
	 * �\���󋵖���Map�i�V�X�e���Ǘ��Ҍ����\�����́j
	 * �L�[�l�F�X�e�[�^�X�R�[�h(Integer)
	 */
	private static Map  SYSTEM_STATUS_MAP                = new HashMap();	
	
	//---------------------------------------------------------------------
	// Static initialize
	//---------------------------------------------------------------------
	static{
		initAllMap();
		initMasterStatusMap();
	}

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------
	/** ���s���郆�[�U��� */
	private UserInfo userInfo = null;

	/** DBLnk */
	private String dbLink = "";
	
	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------
	/**
	 * �R���X�g���N�^
	 * @param userInfo
	 */
	public StatusManager(UserInfo userInfo){
		this.userInfo = userInfo;
	}

	/**
	 * �R���X�g���N�^
	 * @param userInfo
	 */
	public StatusManager(UserInfo userInfo, String dbLink){
		this.userInfo = userInfo;
		this.dbLink = dbLink;
	}

	
	/**
	 * �X�e�[�^�X�X�V�p���\�b�h�B
	 */
	public void refreshStatus() {
		initAllMap();
		initMasterStatusMap();
	}

	//---------------------------------------------------------------------
	// Private Methods
	//---------------------------------------------------------------------
	/**
	 * �eMap�ɑ΂��āA�\����ID���L�[�ɐ\���󋵖����Z�b�g���Ă����B
	 * @throws ExceptionInInitializerError
	 */
	private static void initAllMap()
		throws ExceptionInInitializerError {

		// SQL���̍쐬
		String select = "SELECT * FROM MASTER_STATUS ORDER BY JOKYO_ID";	
			
		// DB���R�[�h�擾
		Connection connection = null;
		List resultList = null;
		try {
			connection= DatabaseUtil.getConnection();
			resultList = SelectUtil.select(connection, select);
		}catch(Exception e){
			throw new ExceptionInInitializerError("�\���󋵃}�X�^��������DB�G���[���������܂����B");
		}finally{
			DatabaseUtil.closeConnection(connection);
		}
		
		// ���X�g���DB���R�[�h���擾����Map�𐶐�����
		for(int i=0; i<resultList.size(); i++){
			Map recordMap = (Map)resultList.get(i);

			//�\���󋵂ƍĐ\���t���O�������������̂��L�[���ڂɂ���
			String jokyoId = (String)recordMap.get("JOKYO_ID");
			String saishinseiFlg = (String)recordMap.get("SAISHINSEI_FLG");
			String pKey = jokyoId + saishinseiFlg;

			//�e�S���̃X�e�[�^�X��������i�[���Ă���
			STATUS_MAP.put(pKey, recordMap.get("JOKYO_NAME"));
			SHINSEISHA_STATUS_MAP.put(pKey, recordMap.get("SHINSEISHA_HYOJI"));
			SHINSNEISHA_KOKAIMAE_STATUS_MAP.put(pKey, recordMap.get("SHINSEISHA_HYOJI_KOKAIMAE"));
			RYOUIKIDAIHYOU_STATUS_MAP.put(pKey, recordMap.get("RYOIKIDAIHYOSHA_HYOJI"));
			SHOZOKU_STATUS_MAP.put(pKey, recordMap.get("SHOZOKU_HYOJI"));
			SHOZOKU_KOKAIMAE_STATUS_MAP.put(pKey, recordMap.get("SHOZOKU_HYOJI_KOKAIMAE"));
			GENKA_STATUS_MAP.put(pKey, recordMap.get("GENKA_HYOJI"));
			SHINSAIN_STATUS_MAP.put(pKey, recordMap.get("SHINSAIN_HYOJI"));
			SYSTEM_STATUS_MAP.put(pKey, recordMap.get("SYSTEM_HYOJI"));
		}
	}

	/**
	 * �\���󋵃}�X�^Map�̏������B
	 */
	private static void initMasterStatusMap(){
		
		//���J�O���List
		List list = null;

		//����
		list = new ArrayList(3);
		list.add(STATUS_MAP);		//���J�O��œ������̂�����
		list.add(STATUS_MAP);
		list.add(STATUS_MAP);
		MASTER_STATUS_MAP.put(null, list); //���̂�null�ɕR�t����
				
		//�\����
		list = new ArrayList(3);
		list.add(SHINSNEISHA_KOKAIMAE_STATUS_MAP);
		list.add(SHINSEISHA_STATUS_MAP);
		list.add(RYOUIKIDAIHYOU_STATUS_MAP);
		MASTER_STATUS_MAP.put(UserRole.SHINSEISHA, list);
		
		//�����@�֒S����
		list = new ArrayList(3);
		list.add(SHOZOKU_KOKAIMAE_STATUS_MAP);
		list.add(SHOZOKU_STATUS_MAP);
		list.add(SHOZOKU_STATUS_MAP);		
		MASTER_STATUS_MAP.put(UserRole.SHOZOKUTANTO, list);		
		
		//���ہi�Ɩ��S���ҁj
		list = new ArrayList(3);
		list.add(GENKA_STATUS_MAP);		//���J�O��œ������̂�����
		list.add(GENKA_STATUS_MAP);
		list.add(GENKA_STATUS_MAP);		
		MASTER_STATUS_MAP.put(UserRole.GYOMUTANTO, list);
				
		//�R����
		list = new ArrayList(3);
		list.add(SHINSAIN_STATUS_MAP);	//���J�O��œ������̂�����
		list.add(SHINSAIN_STATUS_MAP);
		list.add(SHINSAIN_STATUS_MAP);		
		MASTER_STATUS_MAP.put(UserRole.SHINSAIN, list);

		//�V�X�e���Ǘ���
		list = new ArrayList(3);
		list.add(SYSTEM_STATUS_MAP);	//���J�O��œ������̂�����
		list.add(SYSTEM_STATUS_MAP);
		list.add(SYSTEM_STATUS_MAP);		
		MASTER_STATUS_MAP.put(UserRole.SYSTEM, list);
		
		//2005/4/11 �ǉ� ��������---------------------------------------
		//���R ���ǒS���҂̏ꍇ�̎w�肪����Ă��Ȃ�����
		//���ǒS����(�����Ɠ��l)
		list = new ArrayList(3);
		list.add(SHOZOKU_KOKAIMAE_STATUS_MAP);
		list.add(SHOZOKU_STATUS_MAP);
		list.add(SHOZOKU_STATUS_MAP);		
		MASTER_STATUS_MAP.put(UserRole.BUKYOKUTANTO, list);
		//�ǉ� �����܂�-------------------------------------------------
	}

	/**
	 * �I�u�W�F�N�g�̕�����\����Ԃ��B
	 * ������null�̏ꍇ��null��Ԃ��B
	 * @param obj
	 * @return
	 */
	private String object2String(Object obj){
		
		//�߂�l
		String ret = null;
		
		if(obj instanceof String ){
			ret = (String)obj;      //String�^�̏ꍇ�̓L���X�g
		}else if(obj != null){
			ret = obj.toString();   //Strin�^g�ȊO�̏ꍇ��toString()
		}else{
			ret = null;				//obj��null�̏ꍇ��null
		}
		
		return ret;
	}

	//---------------------------------------------------------------------
	// Public Methods
	//---------------------------------------------------------------------
	/**
	 * ���Y�\���f�[�^�̐\���󋵖��i�\���󋵂�\��������j���AsimpleInfo�ɃZ�b�g����B
	 * �\���󋵖��́A���s���郆�[�U�A���Ƃ̌��J�O��A�\����ID�ɊY�����镶���񂪃Z�b�g�����B
	 * @param connection
	 * @param simpleInfo
	 * @return
	 * @throws NoDataFoundException
	 * @throws ApplicationException
	 */
	public void setStatusName(Connection connection, SimpleShinseiDataInfo simpleInfo)
		    throws NoDataFoundException, ApplicationException {

//2006/07/17 dyh update start
//		String jokyoName = getJokyoName(connection,
//										simpleInfo.getJigyoId(),
//										simpleInfo.getJokyoId(),
//										simpleInfo.getSaishinseiFlg(),
//										simpleInfo.getKekka1Abc(),
//										simpleInfo.getKekka1Ten(),
//										simpleInfo.getKekka2());
        String jokyoName = getJokyoName(connection,
                simpleInfo.getJigyoId(),
                simpleInfo.getJokyoId(),
                simpleInfo.getSaishinseiFlg(),
                simpleInfo.getKekka1Abc(),
                simpleInfo.getKekka1Ten(),
                simpleInfo.getKekka2(),
                NOT_RYOIKI);
// 2006/07/17 dyh update end

		simpleInfo.setJokyoName(jokyoName);
	}
	
	
	/**
	 * ���Y�\���f�[�^�̐\���󋵖��i�\���󋵂�\��������j���AsimpleInfo�ɃZ�b�g����B
	 * �\���󋵖��́A���s���郆�[�U�A���Ƃ̌��J�O��A�\����ID�ɊY�����镶���񂪃Z�b�g�����B
	 * @param connection
	 * @param simpleInfos
	 * @return
	 * @throws NoDataFoundException
	 * @throws ApplicationException
	 */
	public void setStatusNames(Connection connection, SimpleShinseiDataInfo[] simpleInfos)
		throws NoDataFoundException, ApplicationException {
		for(int i=0; i<simpleInfos.length; i++){
			setStatusName(connection, simpleInfos[i]);
		}
	}

	/**
	 * ���Y�\���f�[�^�̐\���󋵖��i�\���󋵂�\��������j���Apage�I�u�W�F�N�g��
	 * �i�[����Ă���List�ɃZ�b�g����B�\���󋵖��́A�L�[�l�uJOKYO_NAME�v�Ƃ���Map�ɒǉ������B
	 * �\���󋵖��́A���s���郆�[�U�A���Ƃ̌��J�O��A�\����ID�ɊY�����镶���񂪃Z�b�g�����B
	 * page�Ɋi�[����Ă���List�ɂ́A
	 * <li>����ID["JIGYO_ID"]</li>
	 * <li>�\����ID["JOKYO_ID"]</li>
	 * <li>�Đ\���t���O["SAISHINSEI_FLG"]</li>
	 * <li>1���R������(ABC)["KEKKA1_ABC"]</li>
	 * <li>1���R������(�_��)["KEKKA1_TEN"]</li>
	 * <li>2���R������["KEKKA2"]</li>
	 * ��Map�`���Ŋi�[����Ă��邱�ƁB
	 * @param connection
 	 * @param page
	 * @return
	 * @throws NoDataFoundException
	 * @throws ApplicationException
	 */
	public void setStatusName(Connection connection, Page page)
		throws NoDataFoundException, ApplicationException {

		//Page���̃��X�g���擾���A�e���R�[�h�}�b�v�ɑ΂��Đ\���󋵖����Z�b�g����B
        List infoList = page.getList();
// 2006/07/17 dyh update start
//		for(int i=0; i<infoList.size(); i++){
//			Map info = (Map)infoList.get(i);
//			String jokyoName = getJokyoName(connection,
//											object2String(info.get("JIGYO_ID")),
//											object2String(info.get("JOKYO_ID")),
//											object2String(info.get("SAISHINSEI_FLG")),
//											object2String(info.get("KEKKA1_ABC")),
//											object2String(info.get("KEKKA1_TEN")),
//											object2String(info.get("KEKKA2")));
//			info.put("JOKYO_NAME", jokyoName);
//		}
        setStatusName(connection, infoList);
// 2006/07/17 dyh update end
	}

//2006/7/17 dyh add start
    /**
     * ���Y�\���f�[�^�̐\���󋵖��i�̈��\�Ҍ����\��������j���Apage�I�u�W�F�N�g��
     * �i�[����Ă���List�ɃZ�b�g����B�\���󋵖��́A�L�[�l�uRYOIKI_JOKYO_NAME�v�Ƃ���Map�ɒǉ������B
     * �\���󋵖��́A���s���郆�[�U�A���Ƃ̌��J�O��A�\����ID�ɊY�����镶���񂪃Z�b�g�����B
     * page�Ɋi�[����Ă���List�ɂ́A
     * <li>����ID["JIGYO_ID"]</li>
     * <li>�\����ID["JOKYO_ID"]</li>
     * <li>�Đ\���t���O["SAISHINSEI_FLG"]</li>
     * <li>1���R������(ABC)["KEKKA1_ABC"]</li>
     * <li>1���R������(�_��)["KEKKA1_TEN"]</li>
     * <li>2���R������["KEKKA2"]</li>
     * ��Map�`���Ŋi�[����Ă��邱�ƁB
     * @param connection
     * @param page
     * @return
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public void setRyoikiStatusName(Connection connection, Page page)
            throws NoDataFoundException, ApplicationException {

        // Page���̃��X�g���擾���A�e���R�[�h�}�b�v�ɑ΂��Đ\���󋵖����Z�b�g����B
        List infoList = page.getList();
        setRyoikiStatusName(connection, infoList);
    }

    /**
     * ���Y�\���f�[�^�̐\���󋵖��i�\���󋵂�\��������j���AList�I�u�W�F�N�g��
     * �i�[����Ă���List�ɃZ�b�g����B�\���󋵖��́A�L�[�l�uJOKYO_NAME�v�uRYOIKI_JOKYO_NAME�v�Ƃ���Map�ɒǉ������B
     * �\���󋵖��́A���s���郆�[�U�A���Ƃ̌��J�O��A�\����ID�ɊY�����镶���񂪃Z�b�g�����B
     * List�ɂ́A
     * <li>����ID["JIGYO_ID"]</li>
     * <li>�\����ID["JOKYO_ID"]</li>
     * <li>�Đ\���t���O["SAISHINSEI_FLG"]</li>
     * <li>1���R������(ABC)["KEKKA1_ABC"]</li>
     * <li>1���R������(�_��)["KEKKA1_TEN"]</li>
     * <li>2���R������["KEKKA2"]</li>
     * ��Map�`���Ŋi�[����Ă��邱�ƁB
     * @param connection
     * @param infoList
     * @return
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public void setStatusNames(Connection connection, List infoList)
            throws NoDataFoundException, ApplicationException {

        // ���X�g���̊e���R�[�h�}�b�v�ɑ΂��Đ\���󋵖����Z�b�g����B
        for (int i = 0; i < infoList.size(); i++) {
            Map info = (Map)infoList.get(i);

            // �\���󋵖�(�\���Ҍ����\��)��ݒ�
            String jokyoName = getJokyoName(
                    connection,
                    object2String(info.get("JIGYO_ID")),
                    object2String(info.get("JOKYO_ID")),
                    object2String(info.get("SAISHINSEI_FLG")),
                    object2String(info.get("KEKKA1_ABC")),
                    object2String(info.get("KEKKA1_TEN")),
                    object2String(info.get("KEKKA2")),
                    NOT_RYOIKI);
            info.put("JOKYO_NAME", jokyoName);
                
            // �\���󋵖�(�̈��\�Ҍ����\��)��ݒ�
            String ryoikiJokyoName = getJokyoName(
                    connection,
                    object2String(info.get("JIGYO_ID")),
                    object2String(info.get("JOKYO_ID")),
                    object2String(info.get("SAISHINSEI_FLG")),
                    object2String(info.get("KEKKA1_ABC")),
                    object2String(info.get("KEKKA1_TEN")),
                    object2String(info.get("KEKKA2")),
                    RYOIKI);
            info.put("RYOIKI_JOKYO_NAME", ryoikiJokyoName);
        }
    }

    /**
     * ���Y�\���f�[�^�̐\���󋵖��i�\���󋵂�\��������j���AList�I�u�W�F�N�g��
     * �i�[����Ă���List�ɃZ�b�g����B�\���󋵖��́A�L�[�l�uJOKYO_NAME�v�Ƃ���Map�ɒǉ������B
     * �\���󋵖��́A���s���郆�[�U�A���Ƃ̌��J�O��A�\����ID�ɊY�����镶���񂪃Z�b�g�����B
     * List�ɂ́A
     * <li>����ID["JIGYO_ID"]</li>
     * <li>�\����ID["JOKYO_ID"]</li>
     * <li>�Đ\���t���O["SAISHINSEI_FLG"]</li>
     * <li>1���R������(ABC)["KEKKA1_ABC"]</li>
     * <li>1���R������(�_��)["KEKKA1_TEN"]</li>
     * <li>2���R������["KEKKA2"]</li>
     * ��Map�`���Ŋi�[����Ă��邱�ƁB
     * @param connection
     * @param infoList
     * @return
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public void setStatusName(Connection connection, List infoList)
            throws NoDataFoundException, ApplicationException {

        // ���X�g���̊e���R�[�h�}�b�v�ɑ΂��Đ\���󋵖����Z�b�g����B
        for (int i = 0; i < infoList.size(); i++) {
            Map info = (Map)infoList.get(i);
            
            String ryoikiJokyoName = getJokyoName(connection,
                    object2String(info.get("JIGYO_ID")),
                    object2String(info.get("JOKYO_ID")),
                    object2String(info.get("SAISHINSEI_FLG")),
                    object2String(info.get("KEKKA1_ABC")),
                    object2String(info.get("KEKKA1_TEN")),
                    object2String(info.get("KEKKA2")),
                    NOT_RYOIKI);
            info.put("JOKYO_NAME", ryoikiJokyoName);
        }
    }

    /**
     * ���Y�\���f�[�^�̐\���󋵖��i�̈��\�Ҍ����\��������j���AList�I�u�W�F�N�g��
     * �i�[����Ă���List�ɃZ�b�g����B�\���󋵖��́A�L�[�l�uRYOIKI_JOKYO_NAME�v�Ƃ���Map�ɒǉ������B
     * �\���󋵖��́A���s���郆�[�U�A���Ƃ̌��J�O��A�\����ID�ɊY�����镶���񂪃Z�b�g�����B
     * List�ɂ́A
     * <li>����ID["JIGYO_ID"]</li>
     * <li>�\����ID["JOKYO_ID"]</li>
     * <li>�Đ\���t���O["SAISHINSEI_FLG"]</li>
     * <li>1���R������(ABC)["KEKKA1_ABC"]</li>
     * <li>1���R������(�_��)["KEKKA1_TEN"]</li>
     * <li>2���R������["KEKKA2"]</li>
     * ��Map�`���Ŋi�[����Ă��邱�ƁB
     * @param connection
     * @param infoList
     * @param ryoikiFlg �\���҂Ɨ̈��\�҂̋敪
     * @return
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public void setRyoikiStatusName(Connection connection, List infoList)
            throws NoDataFoundException, ApplicationException {

        // ���X�g���̊e���R�[�h�}�b�v�ɑ΂��Đ\���󋵖����Z�b�g����B
        for (int i = 0; i < infoList.size(); i++) {
            Map info = (Map)infoList.get(i);
            
            String ryoikiJokyoName = getJokyoName(connection,
                    object2String(info.get("JIGYO_ID")),
                    object2String(info.get("JOKYO_ID")),
                    object2String(info.get("SAISHINSEI_FLG")),
                    object2String(info.get("KEKKA1_ABC")),
                    object2String(info.get("KEKKA1_TEN")),
                    object2String(info.get("KEKKA2")),
                    RYOIKI);
            info.put("RYOIKI_JOKYO_NAME", ryoikiJokyoName);
        }
    }
// 2006/7/17 dyh add end

	/**
	 * ���Y�\���f�[�^�̐\���󋵖��i�\���󋵂�\��������j���Apage�I�u�W�F�N�g��
	 * �i�[����Ă���List�ɃZ�b�g����B�\���󋵖��́A�L�[�l�uJOKYO_NAME�v�Ƃ���Map�ɒǉ������B
	 * �\���󋵖��́A���s���郆�[�U�A���Ƃ̌��J�O��A�\����ID�ɊY�����镶���񂪃Z�b�g�����B
	 * page�Ɋi�[����Ă���List�ɂ́A
	 * <li>�\����ID["JOKYO_ID"]</li>
	 * ��Map�`���Ŋi�[����Ă��邱�ƁB
	 * @param connection
	 * @param page
	 * @return
	 * @throws NoDataFoundException
	 * @throws ApplicationException
	 */
	public void setJokyoName(Connection connection, Page page)
			throws NoDataFoundException, ApplicationException {
		
		//Page���̃��X�g���擾���A�e���R�[�h�}�b�v�ɑ΂��Đ\���󋵖����Z�b�g����B
		List infoList = page.getList();
		for(int i=0; i<infoList.size(); i++){
			Map info = (Map)infoList.get(i);
// 2006/07/17 dyh update start
//			String jokyoName = getJokyoName(connection,
//											object2String(info.get("JIGYO_ID")),
//											object2String(info.get("JOKYO_ID")),
//											StatusCode.SAISHINSEI_FLG_DEFAULT,
//											null,
//											null,
//											null
//											);
            String jokyoName = getJokyoName(
                    connection,
                    object2String(info.get("JIGYO_ID")),
                    object2String(info.get("JOKYO_ID")),
                    StatusCode.SAISHINSEI_FLG_DEFAULT,
                    null,
                    null,
                    null,
                    NOT_RYOIKI);
// 2006/07/17 dyh update end
			String ryoikijokyoName = getJokyoName(
                    connection,
                    object2String(info.get("JIGYO_ID")),
                    object2String(info.get("JOKYO_ID")),
                    StatusCode.SAISHINSEI_FLG_DEFAULT,
                    null,
                    null,
                    null,
                    RYOIKI);
			info.put("JOKYO_NAME", jokyoName);
			info.put("RYOIKI_JOKYO_NAME", ryoikijokyoName);
		}
	}

// 2006/07/17 dyh delete start �C����g�p���Ȃ�
//	/**
//	 * ���s���郆�[�U�ɊY������\���󋵖��i�\���󋵂�\��������j��Ԃ��B
//	 * �������{KEKKA1}���܂܂�Ă����ꍇ�́A1���R������(ABC)��1���R������(�_��)��
//	 * �����񌋍��������̂ƒu���A{KEKKA2}���܂܂�Ă����ꍇ��2���R������(������)��
//	 * �u�������������Ԃ��B
//	 * @param connection
//	 * @param jigyoId
//	 * @param jokyoId
//	 * @param saishinseiFlag
//	 * @param kekka1Abc
//	 * @param kekka1Ten
//	 * @param kekka2
//	 * @return String
//	 * @throws NoDataFoundException
//	 * @throws ApplicationException
//	 */
//	protected String getJokyoName(Connection connection,
//								  String jigyoId, 
//								  String jokyoId,
//								  String saishinseiFlag,
//								  String kekka1Abc,
//								  String kekka1Ten,
//								  String kekka2)
//		throws NoDataFoundException, ApplicationException {
//		//-----����ID�����Ɍ��J�t���O���擾����-----
//		// SQL���̍쐬
//		String select = "SELECT KOKAI_FLG FROM JIGYOKANRI WHERE JIGYO_ID = '" + jigyoId + "'";	
//			
//		//DB���R�[�h�擾
//		List resultList = null;
//		try {
//			resultList = SelectUtil.select(connection, select);
//		}catch(Exception e){
//			e.printStackTrace();
//			throw new ExceptionInInitializerError("���ƊǗ��f�[�^��������DB�G���[���������܂����B");
//		}
//		
//		//���J�t���O���擾����
//		Map resultMap = (Map)resultList.get(0);
//		Number kokaiFlgObj = (Number)resultMap.get("KOKAI_FLG");
//		int kokaiFlg = 0;
//		if(kokaiFlgObj != null){
//			kokaiFlg = kokaiFlgObj.intValue();
//		}
//		
//		//���s���[�U�ɕR�Â����\����Map���擾����
//		List list = (List)MASTER_STATUS_MAP.get(userInfo.getRole());
//		Map  map  = (Map)list.get(kokaiFlg);
//		
//		//�\����ID�{�Đ\���t���O�����ɐ\���󋵖��̂��擾
//		String jokyoName = (String)map.get(jokyoId + saishinseiFlag);
//		
//		//1���R�����ʕ�����̎擾
//		StringBuffer strKekka1 = new StringBuffer();
//		if(kekka1Abc != null){
//			strKekka1.append(kekka1Abc);
//		}else{
//			//1���R������ABC���Z�b�g����Ă��Ȃ��ꍇ�́A1���R�����ʓ_��\������
//			if(kekka1Ten != null){
//				strKekka1.append(kekka1Ten);
//			}
//		}
//		//2���R�����ʕ�����̎擾
//		StringBuffer strKekka2 = new StringBuffer();
//		if(kekka2 != null){
//			try{
//				strKekka2.append(StatusCode.getKekka2Name(kekka2));
//			}catch(NumberFormatException e){
//				System.out.println("2���R�����ʂ𐔒l�Ƃ��ĔF���ł��܂���ł����B");
//			}
//		}
//		
//		//�u���Ώە������u��
//		if(jokyoName != null){
//			jokyoName = jokyoName.replaceAll(SHINSA_KEKKA_1, strKekka1.toString());
//			jokyoName = jokyoName.replaceAll(SHINSA_KEKKA_2, strKekka2.toString());
//		}else{
//			System.out.println("�\�������󋵂�null�B�󕶎��ɒu�������܂��B " + userInfo.getRole());
//			jokyoName = "";
//		}
//
//		String jokyoName = getJokyoName(connection,
//				                        jigyoId,
//				                        jokyoId,
//				                        saishinseiFlag,
//				                        kekka1Abc,
//				                        kekka1Ten,
//				                        kekka2,
//				                        NOT_RYOIKI
//										);
//		return jokyoName;
//	}
// 2006/07/17 dyh delete end

	/**
	 * ���s���郆�[�U�ɊY������\���󋵖��i�\���󋵂�\��������j��Ԃ��B
	 * �������{KEKKA1}���܂܂�Ă����ꍇ�́A1���R������(ABC)��1���R������(�_��)��
	 * �����񌋍��������̂ƒu���A{KEKKA2}���܂܂�Ă����ꍇ��2���R������(������)��
	 * �u�������������Ԃ��B
	 * @param connection
	 * @param jigyoId
	 * @param jokyoId
	 * @param saishinseiFlag
	 * @param kekka1Abc
	 * @param kekka1Ten
	 * @param kekka2
     * @param ryoikiFlg �\���҂Ɨ̈��\�҂̋敪
	 * @return String
	 * @throws NoDataFoundException
	 * @throws ApplicationException
	 */
	protected String getJokyoName(Connection connection,
								  String jigyoId,
								  String jokyoId,
								  String saishinseiFlag,
								  String kekka1Abc,
								  String kekka1Ten,
								  String kekka2,
								  int ryoikiFlg)
		    throws NoDataFoundException, ApplicationException {
// 2006/07/17 dyh update start
//		//-----����ID�����Ɍ��J�t���O���擾����-----
//		// SQL���̍쐬
//		String select = "SELECT KOKAI_FLG FROM JIGYOKANRI WHERE JIGYO_ID = '" + jigyoId + "'";	
//			
//		//DB���R�[�h�擾
//		List resultList = null;
//		try {
//			resultList = SelectUtil.select(connection, select);
//		}catch(Exception e){
//			throw new ExceptionInInitializerError("���ƊǗ��f�[�^��������DB�G���[���������܂����B");
//		}
//		int jokyoNameFlg;
//		if(ryoikiFlg == NOT_RYOIKI) {
//			//���J�t���O���擾����
//			Map resultMap = (Map)resultList.get(0);
//			Number kokaiFlgObj = (Number)resultMap.get("KOKAI_FLG");
//			jokyoNameFlg = 0;
//			if(kokaiFlgObj != null){
//				jokyoNameFlg = kokaiFlgObj.intValue();
//			}
//		} else {
//			jokyoNameFlg = ryoikiFlg;
//		}
        int jokyoNameFlg = 0;
        if(ryoikiFlg == NOT_RYOIKI){
            // ���J�t���O���擾����
            jokyoNameFlg = getKokaiFlg(connection, jigyoId);
        }else{
            jokyoNameFlg = ryoikiFlg;
        }
// 2006/07/17 dyh update end

		//���s���[�U�ɕR�Â����\����Map���擾����
		List list = (List)MASTER_STATUS_MAP.get(userInfo.getRole());
		Map  map  = (Map)list.get(jokyoNameFlg);
		
		//�\����ID�{�Đ\���t���O�����ɐ\���󋵖��̂��擾
		String jokyoName = (String)map.get(jokyoId + saishinseiFlag);
		
		//1���R�����ʕ�����̎擾
		StringBuffer strKekka1 = new StringBuffer();
		if(kekka1Abc != null){
			strKekka1.append(kekka1Abc);
		}else{
			//1���R������ABC���Z�b�g����Ă��Ȃ��ꍇ�́A1���R�����ʓ_��\������
			if(kekka1Ten != null){
				strKekka1.append(kekka1Ten);
			}
		}
		//2���R�����ʕ�����̎擾
		StringBuffer strKekka2 = new StringBuffer();
		if(kekka2 != null){
			try{
				strKekka2.append(StatusCode.getKekka2Name(kekka2));
			}catch(NumberFormatException e){
				System.out.println("2���R�����ʂ𐔒l�Ƃ��ĔF���ł��܂���ł����B");
			}
		}
		
		//�u���Ώە������u��
		if(jokyoName != null){
			jokyoName = jokyoName.replaceAll(SHINSA_KEKKA_1, strKekka1.toString());
			jokyoName = jokyoName.replaceAll(SHINSA_KEKKA_2, strKekka2.toString());
		}else{
			System.out.println("�\�������󋵂�null�B�󕶎��ɒu�������܂��B " + userInfo.getRole());
			jokyoName = "";
		}
	
		return jokyoName;
	}

// 2006/07/17 dyh add start
    /**
     * ���J�t���O���擾����
     * @param connection �R�l�N�V����
     * @param jigyoId ����ID
     */
    private int getKokaiFlg(Connection connection,String jigyoId)
            throws NoDataFoundException, ApplicationException{

        //-----����ID�����Ɍ��J�t���O���擾����-----
        // SQL���̍쐬
        String select = "SELECT KOKAI_FLG FROM JIGYOKANRI " + dbLink + " WHERE JIGYO_ID = '" + jigyoId + "'";  
            
        //DB���R�[�h�擾
        List resultList = null;
        try {
            resultList = SelectUtil.select(connection, select);
        }catch(DataAccessException e){
            throw new ApplicationException("���ƊǗ��f�[�^��������DB�G���[���������܂����B");
        }
        // ���J�t���O���擾����
        Map resultMap = (Map)resultList.get(0);
        Number kokaiFlgObj = (Number)resultMap.get("KOKAI_FLG");
        if(kokaiFlgObj != null){
            return kokaiFlgObj.intValue();
        }
        return 0;
    }
// 2006/07/17 dyh add end
}