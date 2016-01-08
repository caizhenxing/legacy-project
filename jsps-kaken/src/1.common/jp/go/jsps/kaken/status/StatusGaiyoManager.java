/*======================================================================
 *    SYSTEM      : ���{�w�p�U����d�q�\���V�X�e���i�Ȋw������⏕���j
 *    Source name : StatusGaiyoManager.java
 *    Description : �X�e�[�^�X�i�T�v�j�Ǘ�
 *
 *    Author      : DIS.dyh
 *    Date        : 2006/07/14
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2006/07/14    v1.0        DIS.dyh        �V�K�쐬
 *====================================================================== 
 */
package jp.go.jsps.kaken.status;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.go.jsps.kaken.model.dao.select.SelectUtil;
import jp.go.jsps.kaken.model.dao.util.DatabaseUtil;
import jp.go.jsps.kaken.model.role.UserRole;
import jp.go.jsps.kaken.model.vo.RyoikiKeikakushoInfo;
import jp.go.jsps.kaken.model.vo.UserInfo;
import jp.go.jsps.kaken.util.Page;

/**
 * �X�e�[�^�X�i�T�v�j�Ǘ�
 */
public class StatusGaiyoManager {

    //---------------------------------------------------------------------
    // Static data
    //---------------------------------------------------------------------
    
    /** 
     * �\���󋵃}�X�^Map<!--�B-->
     * �e���s���[�U���Ƃ̐\���󋵖���Map���i�[����Ă���B
     * ���ƌ��J�O���Map��List�Ŋi�[����Ă���B
     * List(0)�����J�O�AList(1)�����J���Map�B���J�O�ƌ��J�オ�����ꍇ������B
     * �L�[�l��'null'�̏ꍇ�͐\���󋵖��́i���́j��Map���Ԃ�B
     * �\���󋵖���Map�i���́j���~�����ꍇ�́A������"DEFAULT"���L�[�l�Ƃ��Ďw�肷��B
     * �L�[���ځF�e���[�U��UserRole
     */
    private static Map MASTER_RYOIKI_STATUS_MAP           = new HashMap();                                
    
    /**
     * �\���󋵖���Map�i���́j
     * �L�[�l�F�X�e�[�^�X�R�[�h(Integer)
     */
    private static Map RYOIKI_STATUS_MAP                  = new HashMap();
    
    /**
     * �\���󋵖���Map�i�̈��\�Ҍ����\�����j
     * �L�[�l�F�X�e�[�^�X�R�[�h(Integer)
     */
    private static Map RYOUIKIDAIHYOU_RYOIKI_STATUS_MAP    = new HashMap();

    /**
     * �\���󋵖���Map�i�����@�֌����\�����́j
     * �L�[�l�F�X�e�[�^�X�R�[�h(Integer)
     */
    private static Map SHOZOKU_RYOIKI_STATUS_MAP           = new HashMap();

    /**
     * �\���󋵖���Map�i�Ɩ��S���Ҍ����\�����́j
     * �L�[�l�F�X�e�[�^�X�R�[�h(Integer)
     */
    private static Map GENKA_RYOIKI_STATUS_MAP             = new HashMap();
    
    /**
     * �\���󋵖���Map�i�V�X�e���Ǘ��Ҍ����\�����́j
     * �L�[�l�F�X�e�[�^�X�R�[�h(Integer)
     */
    private static Map SYSTEM_RYOIKI_STATUS_MAP            = new HashMap();   
    
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

    //---------------------------------------------------------------------
    // Constructors
    //---------------------------------------------------------------------
    /**
     * �R���X�g���N�^
     * @param userInfo
     */
    public StatusGaiyoManager(UserInfo userInfo){
        this.userInfo = userInfo;
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
     * �eMap�ɑ΂��āA�\����ID���L�[�ɗ̈�v�揑�T�v�󋵃}�X�^����\���󋵖����Z�b�g���Ă����B
     * @throws ExceptionInInitializerError
     */
    private static void initAllMap()
        throws ExceptionInInitializerError {

        // SQL���̍쐬
        String select = "SELECT * FROM MASTER_RYOIKI_STATUS ORDER BY JOKYO_ID";    
            
        // DB���R�[�h�擾
        Connection connection = null;
        List resultList = null;
        try {
            connection= DatabaseUtil.getConnection();
            resultList = SelectUtil.select(connection, select);
        }catch(Exception e){
            throw new ExceptionInInitializerError("�̈�v�揑�T�v�󋵃}�X�^��������DB�G���[���������܂����B");
        }finally{
            DatabaseUtil.closeConnection(connection);
        }
        
        // ���X�g���DB���R�[�h���擾����Map�𐶐�����
        for (int i = 0; i < resultList.size(); i++) {
            Map recordMap = (Map)resultList.get(i);

            //�\���󋵂ƍĐ\���t���O�������������̂��L�[���ڂɂ���
            String jokyoId = (String)recordMap.get("JOKYO_ID");
            String kaijyoFlg = (String)recordMap.get("KAIJYO_FLG");
            String pKey = jokyoId + kaijyoFlg;

            // �e�S���̃X�e�[�^�X��������i�[���Ă���
            RYOIKI_STATUS_MAP.put(pKey, recordMap.get("JOKYO_NAME"));
            RYOUIKIDAIHYOU_RYOIKI_STATUS_MAP.put(pKey, recordMap.get("RYOIKIDAIHYOU_HYOJI"));
            SHOZOKU_RYOIKI_STATUS_MAP.put(pKey, recordMap.get("SHOZOKU_HYOJI"));
            GENKA_RYOIKI_STATUS_MAP.put(pKey, recordMap.get("GENKA_HYOJI"));
            SYSTEM_RYOIKI_STATUS_MAP.put(pKey, recordMap.get("SYSTEM_HYOJI"));// �V�X�e���Ǘ��Ҍ����\������
        }
    }

    /**
     * �\���󋵃}�X�^Map�̏������B
     */
    private static void initMasterStatusMap(){
        
        //���J�O���List
        List list = null;

        //����
        list = new ArrayList(1);
        list.add(RYOIKI_STATUS_MAP);
        MASTER_RYOIKI_STATUS_MAP.put(null, list); //���̂�null�ɕR�t����
                
        //�\����
        list = new ArrayList(1);
        list.add(RYOUIKIDAIHYOU_RYOIKI_STATUS_MAP);
        MASTER_RYOIKI_STATUS_MAP.put(UserRole.SHINSEISHA, list);
        
        //�����@�֒S����
        list = new ArrayList(1);
        list.add(SHOZOKU_RYOIKI_STATUS_MAP);       
        MASTER_RYOIKI_STATUS_MAP.put(UserRole.SHOZOKUTANTO, list);     
        
        //�Ɩ��S���ҁi���ہj
        list = new ArrayList(1);
        list.add(GENKA_RYOIKI_STATUS_MAP);     
        MASTER_RYOIKI_STATUS_MAP.put(UserRole.GYOMUTANTO, list);

        //�V�X�e���Ǘ���
        list = new ArrayList(1);
        list.add(SYSTEM_RYOIKI_STATUS_MAP);        
        MASTER_RYOIKI_STATUS_MAP.put(UserRole.SYSTEM, list);

        //���ǒS����(�����Ɠ��l)
        list = new ArrayList(1);
        list.add(SHOZOKU_RYOIKI_STATUS_MAP);       
        MASTER_RYOIKI_STATUS_MAP.put(UserRole.BUKYOKUTANTO, list);
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
            ret = null;             //obj��null�̏ꍇ��null
        }
        
        return ret;
    }

    //---------------------------------------------------------------------
    // Public Methods
    //---------------------------------------------------------------------
    /**
     * ���Y�\���f�[�^�̐\���󋵖��i�\���󋵂�\��������j���AryoikiInfo�ɃZ�b�g����B
     * �\���󋵖��́A���s���郆�[�U�A���Ƃ̌��J�O��A�\����ID�ɊY�����镶���񂪃Z�b�g�����B
     * @param ryoikiInfo
     */
    public void setRyoikiStatusName(RyoikiKeikakushoInfo ryoikiInfo) {
        String ryoikiJokyoName = getRyoikiJokyoName(
                ryoikiInfo.getRyoikiJokyoId(),
                ryoikiInfo.getCancelFlg());
        ryoikiInfo.setRyoikiJokyoName(ryoikiJokyoName);
    }
    
    
    /**
     * ���Y�\���f�[�^�̐\���󋵖��i�\���󋵂�\��������j���AryoikiInfos�ɃZ�b�g����B
     * �\���󋵖��́A���s���郆�[�U�A���Ƃ̌��J�O��A�\����ID�ɊY�����镶���񂪃Z�b�g�����B
     * @param ryoikiInfos
     */
    public void setRyoikiStatusName(RyoikiKeikakushoInfo[] ryoikiInfos){
        for(int i=0; i<ryoikiInfos.length; i++){
            setRyoikiStatusName(ryoikiInfos[i]);
        }
    }

    /**
     * ���Y�\���f�[�^�̐\���󋵖��i�\���󋵂�\��������j���Apage�I�u�W�F�N�g��
     * �i�[����Ă���List�ɃZ�b�g����B�\���󋵖��́A�L�[�l�uJOKYO_NAME�v�Ƃ���Map�ɒǉ������B
     * �\���󋵖��́A���s���郆�[�U�A���Ƃ̌��J�O��A�\����ID�ɊY�����镶���񂪃Z�b�g�����B
     * page�Ɋi�[����Ă���List�ɂ́A
     * <li>�\����ID["RYOIKI_JOKYO_ID"]</li>
     * <li>�����t���O["CANCEL_FLG"]</li>
     * ��Map�`���Ŋi�[����Ă��邱�ƁB
     * @param page
     */
    public void setRyoikiStatusName(Page page){
        //Page���̃��X�g���擾���A�e���R�[�h�}�b�v�ɑ΂��Đ\���󋵖����Z�b�g����B
        List infoList = page.getList();
        for(int i=0; i<infoList.size(); i++){
            Map info = (Map)infoList.get(i);
            String jokyoName = getRyoikiJokyoName(
                    object2String(info.get("RYOIKI_JOKYO_ID")),
                    object2String(info.get("CANCEL_FLG")));
            info.put("RYOIKI_JOKYO_NAME", jokyoName);
        }
    }

    /**
     * ���Y�\���f�[�^�̐\���󋵖��i�\���󋵂�\��������j���Apage�I�u�W�F�N�g��
     * �i�[����Ă���List�ɃZ�b�g����B�\���󋵖��́A�L�[�l�uJOKYO_NAME�v�Ƃ���Map�ɒǉ������B
     * �\���󋵖��́A���s���郆�[�U�A���Ƃ̌��J�O��A�\����ID�ɊY�����镶���񂪃Z�b�g�����B
     * page�Ɋi�[����Ă���List�ɂ́A
     * <li>�\����ID["RYOIKI_JOKYO_ID"]</li>
     * <li>�����t���O["CANCEL_FLG"]</li>
     * ��Map�`���Ŋi�[����Ă��邱�ƁB
     * @param infoList
     */
    public void setRyoikiStatusName(List infoList){
        // ���X�g���̊e���R�[�h�}�b�v�ɑ΂��Đ\���󋵖����Z�b�g����B
        for(int i=0; i<infoList.size(); i++){
            Map info = (Map)infoList.get(i);
            String jokyoName = getRyoikiJokyoName(
                    object2String(info.get("RYOIKI_JOKYO_ID")),
                    object2String(info.get("CANCEL_FLG")));
            info.put("RYOIKI_JOKYO_NAME", jokyoName);
        }
    }

    /**
     * ���s���郆�[�U�ɊY������\���󋵖��i�\���󋵂�\��������j��Ԃ��B
     * �������{KEKKA1}���܂܂�Ă����ꍇ�́A1���R������(ABC)��1���R������(�_��)��
     * �����񌋍��������̂ƒu���A{KEKKA2}���܂܂�Ă����ꍇ��2���R������(������)��
     * �u�������������Ԃ��B
     * @param jokyoId
     * @param kaijyoFlg �����t���O
     * @return String
     */
    private String getRyoikiJokyoName(String jokyoId,String kaijyoFlg) {

        // ���s���[�U�ɕR�Â����\����Map���擾����
        List list = (List)MASTER_RYOIKI_STATUS_MAP.get(userInfo.getRole());
        Map  map  = (Map)list.get(0);
        
        // �\����ID�{�Đ\���t���O�����ɐ\���󋵖��̂��擾
        String jokyoName = (String)map.get(jokyoId + kaijyoFlg);
    
        return jokyoName;
    }
}