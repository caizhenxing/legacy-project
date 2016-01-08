/*======================================================================
 *    SYSTEM      : ���{�w�p�U����d�q�\���V�X�e���i�Ȋw������⏕���j
 *    Source name : ShinseiDataInfoDao.java
 *    Description : �\�����f�[�^�A�N�Z�X�N���X
 *
 *    Author      : Admin
 *    Date        : 2003/12/08
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2003/12/08    V1.0        Admin          �V�K�쐬
 *====================================================================== 
 */
package jp.go.jsps.kaken.model.dao.impl;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import jp.go.jsps.kaken.jigyoKubun.JigyoKubunFilter;
import jp.go.jsps.kaken.model.IShinseiMaintenance;
import jp.go.jsps.kaken.model.common.IJigyoCd;
import jp.go.jsps.kaken.model.common.IJigyoKubun;
import jp.go.jsps.kaken.model.dao.exceptions.DataAccessException;
import jp.go.jsps.kaken.model.dao.exceptions.DuplicateKeyException;
import jp.go.jsps.kaken.model.dao.select.BaseCallbackHandler;
import jp.go.jsps.kaken.model.dao.select.JDBCReading;
import jp.go.jsps.kaken.model.dao.select.PreparedStatementCreator;
import jp.go.jsps.kaken.model.dao.select.RowCallbackHandler;
import jp.go.jsps.kaken.model.dao.select.SelectUtil;
import jp.go.jsps.kaken.model.dao.util.DatabaseUtil;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.exceptions.UserAuthorityException;
import jp.go.jsps.kaken.model.role.UserRole;
import jp.go.jsps.kaken.model.vo.ErrorInfo;
import jp.go.jsps.kaken.model.vo.HyokaSearchInfo;
import jp.go.jsps.kaken.model.vo.JigyoKanriPk;
import jp.go.jsps.kaken.model.vo.SearchInfo;
import jp.go.jsps.kaken.model.vo.ShinseiDataInfo;
import jp.go.jsps.kaken.model.vo.ShinseiDataPk;
import jp.go.jsps.kaken.model.vo.ShinseiSearchInfo;
import jp.go.jsps.kaken.model.vo.SimpleShinseiDataInfo;
import jp.go.jsps.kaken.model.vo.UserInfo;
import jp.go.jsps.kaken.model.vo.shinsei.DaihyouInfo;
import jp.go.jsps.kaken.model.vo.shinsei.KadaiInfo;
import jp.go.jsps.kaken.model.vo.shinsei.KanrenBunyaKenkyushaInfo;
import jp.go.jsps.kaken.model.vo.shinsei.KenkyuKeihiInfo;
import jp.go.jsps.kaken.model.vo.shinsei.KenkyuKeihiSoukeiInfo;
import jp.go.jsps.kaken.model.vo.shinsei.KenkyuSoshikiKenkyushaInfo;
import jp.go.jsps.kaken.status.StatusCode;
import jp.go.jsps.kaken.util.EscapeUtil;
import jp.go.jsps.kaken.util.Page;
import jp.go.jsps.kaken.util.SortUtil;
import jp.go.jsps.kaken.util.StringUtil;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * �\�����f�[�^�A�N�Z�X�N���X�B
 */
public class ShinseiDataInfoDao {

    //---------------------------------------------------------------------
    // Static data
    //---------------------------------------------------------------------
	
    /** ���O */
    protected static final Log log               = LogFactory.getLog(ShinseiDataInfoDao.class);
    
    /** CSV�J�������p���X�g */
    private static List csvColumnList             = null;
    
    /** CSV�J�������p���X�g�i�֘A����j*/
    private static List csvColumnList4Kanrenbunya = null;
    
//2005/08/16 takano ���ƃR�[�h��IJigyoCd����擾�����邽�ߍ폜 �������� -----
//  //2005/04/05 �ǉ� ----------------------------------------------�������� 
//  //���R ��d�o�^�̃`�F�b�N���s������
//  /** ���ƃR�[�h�F��Վ���(S) */
//  private static final String JIGYO_CD_KIBAN_S = "00031";
//  
//  /** ���ƃR�[�h�F��Վ���(A)�i��ʁj */
//  private static final String JIGYO_CD_KIBAN_A_IPPAN = "00041";
//  
//  /** ���ƃR�[�h�F��Վ���(A)�i�C�O�j */
//  private static final String JIGYO_CD_KIBAN_A_KAIGAI = "00043";
//  
//  /** ���ƃR�[�h�F��Վ���(B)�i��ʁj */
//  private static final String JIGYO_CD_KIBAN_B_IPPAN = "00051";
//  
//  /** ���ƃR�[�h�F��Վ���(B)�i�C�O�j */
//  private static final String JIGYO_CD_KIBAN_B_KAIGAI = "00053";
//  
//  /** ���ƃR�[�h�F��Վ���(C)�i��ʁj */
//  //2005/04/26 ��Վ���(C)�i��ʁj�̎��ƃR�[�h��00063����00061�ɕύX
//  private static final String JIGYO_CD_KIBAN_C_IPPAN = "00061";
//  
//  /** ���ƃR�[�h�F��Վ���(C)�i���j */
//  private static final String JIGYO_CD_KIBAN_C_KIKAKU = "00062";
//  
//  /** ���ƃR�[�h�F�G�茤�� */
//  private static final String JIGYO_CD_HOUGA = "00111";
//  
//  /** ���ƃR�[�h�F��茤��(A) */
//  private static final String JIGYO_CD_WAKATE_A = "00121";
//  
//  /** ���ƃR�[�h�F��茤��(B) */
//  private static final String JIGYO_CD_WAKATE_B = "00131";
//
//// 20050523 Start
//  private static final String JIGYO_CD_TOKUTEI    =   "00021";    /** ���ƃR�[�h�F����̈� */
//// Hoirkoshi End
//  
//2005/08/16 takano ���ƃR�[�h��IJigyoCd����擾�����邽�ߍ폜 �����܂� -----

    /** ��d�\���̃`�F�b�N�ΏۂƂȂ鎖��CD��LIST���e����CD���L�[�Ƃ��Ċi�[����Ă���Map */
    private static final Map dupliCheckJigyoCDMap = getDupliCheckJigyoCDMap();
//  //2005/04/05 �ǉ� ----------------------------------------------�����܂� 

    //---------------------------------------------------------------------
    // Instance data
    //---------------------------------------------------------------------

    /** ���s���郆�[�U��� */
    private UserInfo userInfo = null;

    /** DB�����N�� */
    private String   dbLink   = "";

    //---------------------------------------------------------------------
    // Constructors
    //---------------------------------------------------------------------
    /**
     * �R���X�g���N�^�B
     * @param userInfo  ���s���郆�[�U���
     */
    public ShinseiDataInfoDao(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
    
    //2005/04/05 �ǉ� ----------------------------------------------�������� 
    //���R ��d�o�^�̃`�F�b�N���s������
    /**
     * ��d�o�^�̃`�F�b�N���s��
     * @return Map
     */
    private static Map getDupliCheckJigyoCDMap() {
        HashMap jigyoCDMap = new HashMap();
        ArrayList dupliCheckJigyoCD = new ArrayList();

//2005/08/16 takano ���ƃR�[�h��IJigyoCd����擾�����邽�ߍ폜 �������� -----
//      //���S
//      dupliCheckJigyoCD.add(JIGYO_CD_KIBAN_S);
//      dupliCheckJigyoCD.add(JIGYO_CD_KIBAN_B_IPPAN);
//      dupliCheckJigyoCD.add(JIGYO_CD_KIBAN_B_KAIGAI);
//      dupliCheckJigyoCD.add(JIGYO_CD_KIBAN_C_IPPAN);
//      dupliCheckJigyoCD.add(JIGYO_CD_KIBAN_C_KIKAKU);
//      dupliCheckJigyoCD.add(JIGYO_CD_WAKATE_A);
//      dupliCheckJigyoCD.add(JIGYO_CD_WAKATE_B);
//      jigyoCDMap.put(JIGYO_CD_KIBAN_S,dupliCheckJigyoCD);
//
//      //���(A)�i��ʁj
//      dupliCheckJigyoCD=new ArrayList();
//      dupliCheckJigyoCD.add(JIGYO_CD_KIBAN_A_IPPAN);
//      dupliCheckJigyoCD.add(JIGYO_CD_KIBAN_B_IPPAN);
//      dupliCheckJigyoCD.add(JIGYO_CD_KIBAN_C_IPPAN);
//      dupliCheckJigyoCD.add(JIGYO_CD_WAKATE_A);
//      dupliCheckJigyoCD.add(JIGYO_CD_WAKATE_B);
//      jigyoCDMap.put(JIGYO_CD_KIBAN_A_IPPAN,dupliCheckJigyoCD);
//
//      //���(A)�i�C�O�j
//      dupliCheckJigyoCD=new ArrayList();
//      dupliCheckJigyoCD.add(JIGYO_CD_KIBAN_A_KAIGAI);
//      dupliCheckJigyoCD.add(JIGYO_CD_KIBAN_B_KAIGAI);
//      dupliCheckJigyoCD.add(JIGYO_CD_WAKATE_A);
//      dupliCheckJigyoCD.add(JIGYO_CD_WAKATE_B);
//      jigyoCDMap.put(JIGYO_CD_KIBAN_A_KAIGAI,dupliCheckJigyoCD);
//  
//      //���(B)�i��ʁj
//      dupliCheckJigyoCD=new ArrayList();
//      dupliCheckJigyoCD.add(JIGYO_CD_KIBAN_S);
//      dupliCheckJigyoCD.add(JIGYO_CD_KIBAN_A_IPPAN);
//      dupliCheckJigyoCD.add(JIGYO_CD_KIBAN_B_IPPAN);
//      dupliCheckJigyoCD.add(JIGYO_CD_KIBAN_C_IPPAN);
//      dupliCheckJigyoCD.add(JIGYO_CD_WAKATE_A);
//      dupliCheckJigyoCD.add(JIGYO_CD_WAKATE_B);
//      jigyoCDMap.put(JIGYO_CD_KIBAN_B_IPPAN,dupliCheckJigyoCD);
//
//      //���(B)�i�C�O�j
//      dupliCheckJigyoCD=new ArrayList();
//      dupliCheckJigyoCD.add(JIGYO_CD_KIBAN_S);
//      dupliCheckJigyoCD.add(JIGYO_CD_KIBAN_A_KAIGAI);
//      dupliCheckJigyoCD.add(JIGYO_CD_KIBAN_B_KAIGAI);
//      dupliCheckJigyoCD.add(JIGYO_CD_WAKATE_A);
//      dupliCheckJigyoCD.add(JIGYO_CD_WAKATE_B);
//      jigyoCDMap.put(JIGYO_CD_KIBAN_B_KAIGAI,dupliCheckJigyoCD);
//      
//      //���(C)�i��ʁj
//      dupliCheckJigyoCD=new ArrayList();
//      dupliCheckJigyoCD.add(JIGYO_CD_KIBAN_S);
//      dupliCheckJigyoCD.add(JIGYO_CD_KIBAN_A_IPPAN);
//      dupliCheckJigyoCD.add(JIGYO_CD_KIBAN_B_IPPAN);
//      dupliCheckJigyoCD.add(JIGYO_CD_KIBAN_C_IPPAN);
//      dupliCheckJigyoCD.add(JIGYO_CD_HOUGA);
//      dupliCheckJigyoCD.add(JIGYO_CD_WAKATE_A);
//      dupliCheckJigyoCD.add(JIGYO_CD_WAKATE_B);
//      jigyoCDMap.put(JIGYO_CD_KIBAN_C_IPPAN,dupliCheckJigyoCD);
//
//      //���(C)�i���j
//      dupliCheckJigyoCD=new ArrayList();
//      dupliCheckJigyoCD.add(JIGYO_CD_KIBAN_S);
//      dupliCheckJigyoCD.add(JIGYO_CD_KIBAN_C_KIKAKU);
//      jigyoCDMap.put(JIGYO_CD_KIBAN_C_KIKAKU,dupliCheckJigyoCD);
//
//      //�G��
//      dupliCheckJigyoCD=new ArrayList();
//      dupliCheckJigyoCD.add(JIGYO_CD_KIBAN_C_IPPAN);
//      dupliCheckJigyoCD.add(JIGYO_CD_HOUGA);
//      dupliCheckJigyoCD.add(JIGYO_CD_WAKATE_B);
//      jigyoCDMap.put(JIGYO_CD_HOUGA,dupliCheckJigyoCD);
//
//      //���(A)
//      dupliCheckJigyoCD=new ArrayList();
//      dupliCheckJigyoCD.add(JIGYO_CD_KIBAN_S);
//      dupliCheckJigyoCD.add(JIGYO_CD_KIBAN_A_IPPAN);
//      dupliCheckJigyoCD.add(JIGYO_CD_KIBAN_A_KAIGAI);
//      dupliCheckJigyoCD.add(JIGYO_CD_KIBAN_B_IPPAN);
//      dupliCheckJigyoCD.add(JIGYO_CD_KIBAN_B_KAIGAI);
//      dupliCheckJigyoCD.add(JIGYO_CD_KIBAN_C_IPPAN);
//      dupliCheckJigyoCD.add(JIGYO_CD_WAKATE_A);
//      dupliCheckJigyoCD.add(JIGYO_CD_WAKATE_B);
//      jigyoCDMap.put(JIGYO_CD_WAKATE_A,dupliCheckJigyoCD);
//
//      //���(B)
//      dupliCheckJigyoCD=new ArrayList();
//      dupliCheckJigyoCD.add(JIGYO_CD_KIBAN_S);
//      dupliCheckJigyoCD.add(JIGYO_CD_KIBAN_A_IPPAN);
//      dupliCheckJigyoCD.add(JIGYO_CD_KIBAN_A_KAIGAI);
//      dupliCheckJigyoCD.add(JIGYO_CD_KIBAN_B_IPPAN);
//      dupliCheckJigyoCD.add(JIGYO_CD_KIBAN_B_KAIGAI);
//      dupliCheckJigyoCD.add(JIGYO_CD_KIBAN_C_IPPAN);
//      dupliCheckJigyoCD.add(JIGYO_CD_HOUGA);
//      dupliCheckJigyoCD.add(JIGYO_CD_WAKATE_A);
//      dupliCheckJigyoCD.add(JIGYO_CD_WAKATE_B);
//      jigyoCDMap.put(JIGYO_CD_WAKATE_B,dupliCheckJigyoCD);
//2005/08/16 takano ���ƃR�[�h��IJigyoCd����擾�����邽�ߍ폜 �����܂� -----

//2005/08/16 takano ���ƃR�[�h��IJigyoCd����擾������ �������� -----
        // ���S
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_S);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_B_IPPAN);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_B_KAIGAI);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_C_IPPAN);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_C_KIKAKU);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_A);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_B);
        
        
        //ADD START 2007/07/19 BIS ����
        //���iS�j�̏d���`�F�b�N���C��
        //�E���iS)���\���ς݂̏ꍇ�ƁA�V���Ɏ��iS�j��\������ꍇ�ƂŁA�d���ΏۂƂȂ錤����ڂɂ��ꂪ���������ߏC���B
        //�E��X�^�A���i��͎��S�Əd���\�ł��邽�߁A�u���v�ɏC���B
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_S);//��茤��(S)
        //ADD END 2007/07/19 BIS ����
        
        jigyoCDMap.put(IJigyoCd.JIGYO_CD_KIBAN_S,dupliCheckJigyoCD);

        // ���(A)�i��ʁj
        dupliCheckJigyoCD=new ArrayList();
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_A_IPPAN);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_B_IPPAN);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_C_IPPAN);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_A);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_B);
        jigyoCDMap.put(IJigyoCd.JIGYO_CD_KIBAN_A_IPPAN,dupliCheckJigyoCD);

        // ���(A)�i�C�O�j
        dupliCheckJigyoCD=new ArrayList();
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_A_KAIGAI);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_B_KAIGAI);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_A);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_B);
        jigyoCDMap.put(IJigyoCd.JIGYO_CD_KIBAN_A_KAIGAI,dupliCheckJigyoCD);
    
        // ���(B)�i��ʁj
        dupliCheckJigyoCD=new ArrayList();
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_S);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_A_IPPAN);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_B_IPPAN);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_C_IPPAN);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_A);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_B);
        jigyoCDMap.put(IJigyoCd.JIGYO_CD_KIBAN_B_IPPAN,dupliCheckJigyoCD);

        // ���(B)�i�C�O�j
        dupliCheckJigyoCD=new ArrayList();
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_S);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_A_KAIGAI);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_B_KAIGAI);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_A);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_B);
        jigyoCDMap.put(IJigyoCd.JIGYO_CD_KIBAN_B_KAIGAI,dupliCheckJigyoCD);
        
        // ���(C)�i��ʁj
        dupliCheckJigyoCD=new ArrayList();
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_S);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_A_IPPAN);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_B_IPPAN);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_C_IPPAN);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_HOGA);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_A);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_B);
        jigyoCDMap.put(IJigyoCd.JIGYO_CD_KIBAN_C_IPPAN,dupliCheckJigyoCD);

        // ���(C)�i���j
        dupliCheckJigyoCD=new ArrayList();
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_S);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_C_KIKAKU);
        jigyoCDMap.put(IJigyoCd.JIGYO_CD_KIBAN_C_KIKAKU,dupliCheckJigyoCD);

        // �G��
        dupliCheckJigyoCD=new ArrayList();
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_C_IPPAN);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_HOGA);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_B);
        jigyoCDMap.put(IJigyoCd.JIGYO_CD_KIBAN_HOGA,dupliCheckJigyoCD);

        // ���(A)
        dupliCheckJigyoCD=new ArrayList();
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_S);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_A_IPPAN);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_A_KAIGAI);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_B_IPPAN);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_B_KAIGAI);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_C_IPPAN);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_A);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_B);
        jigyoCDMap.put(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_A,dupliCheckJigyoCD);

        // ���(B)
        dupliCheckJigyoCD=new ArrayList();
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_S);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_A_IPPAN);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_A_KAIGAI);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_B_IPPAN);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_B_KAIGAI);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_C_IPPAN);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_HOGA);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_A);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_B);
        jigyoCDMap.put(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_B,dupliCheckJigyoCD);
        
//2007/02/03 �c�@�ǉ���������
        // ���(S)
        
        // UPDATE START 2007/07/19 BIS ����
        //���iS�j�̏d���`�F�b�N���C��
        //�E���iS)���\���ς݂̏ꍇ�ƁA�V���Ɏ��iS�j��\������ꍇ�ƂŁA�d���ΏۂƂȂ錤����ڂɂ��ꂪ���������ߏC���B
        //�E��X�^�A���i��͎��S�Əd���\�ł��邽�߁A�u���v�ɏC���B
        dupliCheckJigyoCD=new ArrayList();
//        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_WAKATESTART);
//        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_A);
//        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_B);
//        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_C);
//        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_SHOKUSHINHI_WAKATE_A);
//        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_SHOKUSHINHI_WAKATE_B);
//        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_S);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_S);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_TOKUSUI);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_S);
        //UPDATE END 2007/07/19 BIS ����
        
        
        
        
        jigyoCDMap.put(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_S,dupliCheckJigyoCD);
//2007/02/03 �c�@�ǉ������܂�        
//2005/08/16 takano ���ƃR�[�h��IJigyoCd����擾������ �����܂� -----
        
//2005/08/16 takano �����Ƃɂ��Ă��ǉ����� �������� -----
        // �w�n�i����j
        dupliCheckJigyoCD=new ArrayList();
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_GAKUSOU_KOUBO);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_GAKUSOU_HIKOUBO);
        jigyoCDMap.put(IJigyoCd.JIGYO_CD_GAKUSOU_KOUBO,dupliCheckJigyoCD);
        
        // �w�n�i�����j
        dupliCheckJigyoCD=new ArrayList();
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_GAKUSOU_KOUBO);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_GAKUSOU_HIKOUBO);
        jigyoCDMap.put(IJigyoCd.JIGYO_CD_GAKUSOU_HIKOUBO,dupliCheckJigyoCD);
        
        // ����
        dupliCheckJigyoCD=new ArrayList();
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_TOKUSUI);
        
        
        //ADD START 2007/07/19 BIS ����
        //���iS�j�̏d���`�F�b�N���C��
        //�E���iS)���\���ς݂̏ꍇ�ƁA�V���Ɏ��iS�j��\������ꍇ�ƂŁA�d���ΏۂƂȂ錤����ڂɂ��ꂪ���������ߏC���B
        //�E��X�^�A���i��͎��S�Əd���\�ł��邽�߁A�u���v�ɏC���B
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_S);//��茤��(S)
        //ADD END 2007/07/19 BIS ����
        
        
        jigyoCDMap.put(IJigyoCd.JIGYO_CD_TOKUSUI,dupliCheckJigyoCD);
        
        // ����p��
        dupliCheckJigyoCD=new ArrayList();
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_TOKUTEI_KEIZOKU);
        jigyoCDMap.put(IJigyoCd.JIGYO_CD_TOKUTEI_KEIZOKU,dupliCheckJigyoCD);
        
//2006/07/27�@�c�@�ǉ���������
        // ����V�K
        dupliCheckJigyoCD=new ArrayList();
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_TOKUTEI_SINKI);
        jigyoCDMap.put(IJigyoCd.JIGYO_CD_TOKUTEI_SINKI,dupliCheckJigyoCD);
//2006/07/27�@�c�@�ǉ������܂�        
      
//2005/08/16 takano �����Ƃɂ��Ă��ǉ����� �����܂� -----
        
//      // ���X�^�[�g�A�b�v 
//      dupliCheckJigyoCD=new ArrayList();
//      dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_WAKATESTART);
//      jigyoCDMap.put(IJigyoCd.JIGYO_CD_WAKATESTART,dupliCheckJigyoCD);
        
// 2006/02/13 Start
        // ���ʌ������i��i��茤��(A)�����j
        dupliCheckJigyoCD=new ArrayList();
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_A);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_B);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_C);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_SHOKUSHINHI_WAKATE_B);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_SHOKUSHINHI_WAKATE_A);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_WAKATESTART);
//      DEL START 2007/07/19 BIS ����
        //���iS�j�̏d���`�F�b�N���C��
        //�E���iS)���\���ς݂̏ꍇ�ƁA�V���Ɏ��iS�j��\������ꍇ�ƂŁA�d���ΏۂƂȂ錤����ڂɂ��ꂪ���������ߏC���B
        //�E��X�^�A���i��͎��S�Əd���\�ł��邽�߁A�u���v�ɏC���B
//2007/02/13 �c�@�ǉ���������
        //dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_S);
//2007/02/13�@�c�@�ǉ������܂�        
        
//DEL END 2007/07/19 BIS ����
        jigyoCDMap.put(IJigyoCd.JIGYO_CD_SHOKUSHINHI_WAKATE_A,dupliCheckJigyoCD);
        
        // ���ʌ������i��i��茤��(B)�����j
        dupliCheckJigyoCD=new ArrayList();
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_A);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_B);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_C);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_SHOKUSHINHI_WAKATE_A);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_SHOKUSHINHI_WAKATE_B);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_WAKATESTART);
//      DEL START 2007/07/19 BIS ����
        //���iS�j�̏d���`�F�b�N���C��
        //�E���iS)���\���ς݂̏ꍇ�ƁA�V���Ɏ��iS�j��\������ꍇ�ƂŁA�d���ΏۂƂȂ錤����ڂɂ��ꂪ���������ߏC���B
        //�E��X�^�A���i��͎��S�Əd���\�ł��邽�߁A�u���v�ɏC���B
//2007/02/13 �c�@�ǉ���������
        //dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_S);
//2007/02/13�@�c�@�ǉ������܂�        
        
//DEL END 2007/07/19 BIS ����
        jigyoCDMap.put(IJigyoCd.JIGYO_CD_SHOKUSHINHI_WAKATE_B,dupliCheckJigyoCD);
//Nae End
        
        //2006/02/14
        // ���ʌ������i��i��Ռ���(A)�����j 
        dupliCheckJigyoCD=new ArrayList();
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_A);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_B);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_C);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_SHOKUSHINHI_WAKATE_B);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_SHOKUSHINHI_WAKATE_A);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_WAKATESTART);
//      DEL START 2007/07/19 BIS ����
        //���iS�j�̏d���`�F�b�N���C��
        //�E���iS)���\���ς݂̏ꍇ�ƁA�V���Ɏ��iS�j��\������ꍇ�ƂŁA�d���ΏۂƂȂ錤����ڂɂ��ꂪ���������ߏC���B
        //�E��X�^�A���i��͎��S�Əd���\�ł��邽�߁A�u���v�ɏC���B
//2007/02/13 �c�@�ǉ���������
        //dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_S);
//2007/02/13�@�c�@�ǉ������܂�        
        
//DEL END 2007/07/19 BIS ����
        jigyoCDMap.put(IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_A,dupliCheckJigyoCD);
        
        // ���ʌ������i��i��Ռ���(B)�����j
        dupliCheckJigyoCD=new ArrayList();
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_A);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_B);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_C);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_SHOKUSHINHI_WAKATE_B);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_SHOKUSHINHI_WAKATE_A);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_WAKATESTART);
//      DEL START 2007/07/19 BIS ����
        //���iS�j�̏d���`�F�b�N���C��
        //�E���iS)���\���ς݂̏ꍇ�ƁA�V���Ɏ��iS�j��\������ꍇ�ƂŁA�d���ΏۂƂȂ錤����ڂɂ��ꂪ���������ߏC���B
        //�E��X�^�A���i��͎��S�Əd���\�ł��邽�߁A�u���v�ɏC���B
//2007/02/13 �c�@�ǉ���������
        //dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_S);
//2007/02/13�@�c�@�ǉ������܂�        
        
//DEL END 2007/07/19 BIS ����
        jigyoCDMap.put(IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_B,dupliCheckJigyoCD);

        // ���ʌ������i��i��Ռ���(C)�����j
        dupliCheckJigyoCD=new ArrayList();
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_A);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_B);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_C);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_SHOKUSHINHI_WAKATE_B);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_SHOKUSHINHI_WAKATE_A);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_WAKATESTART);
//      DEL START 2007/07/19 BIS ����
        //���iS�j�̏d���`�F�b�N���C��
        //�E���iS)���\���ς݂̏ꍇ�ƁA�V���Ɏ��iS�j��\������ꍇ�ƂŁA�d���ΏۂƂȂ錤����ڂɂ��ꂪ���������ߏC���B
        //�E��X�^�A���i��͎��S�Əd���\�ł��邽�߁A�u���v�ɏC���B
//2007/02/13 �c�@�ǉ���������
        //dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_S);
//2007/02/13�@�c�@�ǉ������܂�        
        
//DEL END 2007/07/19 BIS ����
        jigyoCDMap.put(IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_C,dupliCheckJigyoCD);

        // ��茤���i�X�^�[�g�A�b�v�j
        dupliCheckJigyoCD=new ArrayList();
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_A);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_B);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_C);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_SHOKUSHINHI_WAKATE_B);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_SHOKUSHINHI_WAKATE_A);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_WAKATESTART);
//      DEL START 2007/07/19 BIS ����
        //���iS�j�̏d���`�F�b�N���C��
        //�E���iS)���\���ς݂̏ꍇ�ƁA�V���Ɏ��iS�j��\������ꍇ�ƂŁA�d���ΏۂƂȂ錤����ڂɂ��ꂪ���������ߏC���B
        //�E��X�^�A���i��͎��S�Əd���\�ł��邽�߁A�u���v�ɏC���B
//2007/02/13 �c�@�ǉ���������
        //dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_S);
//2007/02/13�@�c�@�ǉ������܂�        
        
//DEL END 2007/07/19 BIS ����
        
        jigyoCDMap.put(IJigyoCd.JIGYO_CD_WAKATESTART,dupliCheckJigyoCD);
// syuu �ǉ� ----------------------------------------------�����܂�   
        return jigyoCDMap;
    }
    //2005/04/05 �ǉ� ----------------------------------------------�����܂� 

    /**
     * �R���X�g���N�^�B
     * @param userInfo ���s���郆�[�U���
     * @param dbLink   DB�����N��
     */
    public ShinseiDataInfoDao(UserInfo userInfo, String dbLink){
        this.userInfo = userInfo;
        this.dbLink   = dbLink;
    }

    //---------------------------------------------------------------------
    // Public Methods
    //---------------------------------------------------------------------
    

    /**
     * �����ԍ���Ԃ��B
     * <li>�w�n�����W�敪(1��)�{������ID�A�������@�ւ��Ƃ̘A��(3��)</li>
     * <li>�������������ID�A�������@�ւ��Ƃ̘A��(4��)</li>
     * <li>��ե��������ID�A�������@�ւ��Ƃ̘A��(4��)�i�b��j</li>
     * �A�Ԃ͂P����n�܂�A�������ӂꂽ�ꍇ�͂O����ēx�A�Ԃ�U���Ă����B
     * @param connection
     * @param dataInfo
     * @return
     * @throws DataAccessException
     */
    public String getSeiriNumber(Connection connection, ShinseiDataInfo dataInfo)
        throws DataAccessException {

        String jigyoKubun = dataInfo.getKadaiInfo().getJigyoKubun();
        //�w�n�̏ꍇ
        if(IJigyoKubun.JIGYO_KUBUN_GAKUSOU_HIKOUBO.equals(jigyoKubun) ||
           IJigyoKubun.JIGYO_KUBUN_GAKUSOU_KOUBO.equals(jigyoKubun)) {
            return getSequenceNumber4Gakusou(connection, dataInfo);

        //�����̏ꍇ
        } else if (IJigyoKubun.JIGYO_KUBUN_TOKUSUI.equals(jigyoKubun)){
            return getSequenceNumber4Tokusui(connection, dataInfo);

        //��Ղ̏ꍇ
        } else if (IJigyoKubun.JIGYO_KUBUN_KIBAN.equals(jigyoKubun)){
            return getSequenceNumber4Kiban(connection, dataInfo);

// 20050523 Start
        //����̈�̏ꍇ
        } else if (IJigyoKubun.JIGYO_KUBUN_TOKUTEI.equals(jigyoKubun)){
            return getSequenceNumber4Tokutei(connection, dataInfo);
// Horikoshi End

//2006/03/07 �ǉ���������
        //���X�^�[�g�A�b�v�̏ꍇ  
        } else if (IJigyoKubun.JIGYO_KUBUN_WAKATESTART.equals(jigyoKubun)){
            return getSequenceNumber4Kiban(connection, dataInfo);

        //���ʌ������i��̏ꍇ    
        } else if (IJigyoKubun.JIGYO_KUBUN_SHOKUSHINHI.equals(jigyoKubun)){
            return getSequenceNumber4Kiban(connection, dataInfo);
//�c�@�ǉ������܂�

        //����ȊO�̏ꍇ�b��I�ɓ����Ɠ����̔ԃ��[���ɂ��Ă����j
        } else {
            return getSequenceNumber4Tokusui(connection, dataInfo);
        }
    }   

    /**
     * �����ԍ��i�w�n�p�j��Ԃ��B
     * @param connection
     * @param dataInfo
     * @return
     * @throws DataAccessException
     */
    private String getSequenceNumber4Gakusou(Connection connection, ShinseiDataInfo dataInfo)
        throws DataAccessException {

        //��t�ԍ���8���ڂ���3���������o�����l�̍ő�l�Ƀv���X1�B�j
        String select = "TO_CHAR(MAX(SUBSTR(A.UKETUKE_NO,8,3)) + 1, 'FM000') SEQ_NUM";
        
        String query = 
        "SELECT "
            + select
            + " FROM"
            + "  SHINSEIDATAKANRI"+dbLink+" A"
            + " WHERE"
            + "  A.UKETUKE_NO IS NOT NULL"
            + " AND"
            + "  A.SHOZOKU_CD = ?"
            + " AND"
            + "  A.JIGYO_ID = ?"
            ;
        
        //for debug
        if(log.isDebugEnabled()){
            log.debug("query:" + query);
        }
        
        //DB�ڑ�
        PreparedStatement preparedStatement = null;
        ResultSet recordSet = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            int i = 1;
            preparedStatement.setString(i++, dataInfo.getDaihyouInfo().getShozokuCd());
            preparedStatement.setString(i++, dataInfo.getJigyoId());
            recordSet = preparedStatement.executeQuery();
             
            String seqNumber = null;
            if (recordSet.next()) {
                seqNumber= recordSet.getString(1);
                if(seqNumber == null){
                    seqNumber = "001";
                }
            }
            
            //��납��3���ɕϊ�����i�����ӂ�΍�j
            seqNumber = StringUtils.right(seqNumber,3);
            String jigyoKubun = dataInfo.getKadaiInfo().getJigyoKubun();        
            if(IJigyoKubun.JIGYO_KUBUN_GAKUSOU_HIKOUBO.equals(jigyoKubun)){
                //�����̏ꍇ�͓��Ɂu0�v������
                seqNumber = "0" + seqNumber;
            }else{
                //����̏ꍇ�͓��ɉ񐔂�����
                seqNumber = dataInfo.getKaisu() + seqNumber;
            }
            return seqNumber;
             
        } catch (SQLException ex) {
            throw new DataAccessException("�\���f�[�^�e�[�u���������s���ɗ�O���������܂����B", ex);
        } finally {
            DatabaseUtil.closeResource(recordSet, preparedStatement);
        }
    }

    /**
     * �����ԍ��i�����p�j��Ԃ��B
     * @param connection
     * @param dataInfo
     * @return
     * @throws DataAccessException
     */
    private String getSequenceNumber4Tokusui(Connection connection, ShinseiDataInfo dataInfo)
        throws DataAccessException {

        //��t�ԍ���7���ڂ���4���������o�����l�̍ő�l�Ƀv���X1�B�j
        String select = "TO_CHAR(MAX(SUBSTR(A.UKETUKE_NO,7,4)) + 1, 'FM0000') SEQ_NUM";

        String query = 
        "SELECT "
            + select
            + " FROM"
            + "  SHINSEIDATAKANRI" + dbLink + " A"
            + " WHERE"
            + "  A.UKETUKE_NO IS NOT NULL"
            + " AND"
            + "  A.SHOZOKU_CD = ?"
            + " AND"
            + "  A.JIGYO_ID = ?"
            ;
        
        //for debug
        if(log.isDebugEnabled()){
            log.debug("query:" + query);
        }
        
        //DB�ڑ�
        PreparedStatement preparedStatement = null;
        ResultSet recordSet = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            int i = 1;
            preparedStatement.setString(i++, dataInfo.getDaihyouInfo().getShozokuCd());
            preparedStatement.setString(i++, dataInfo.getJigyoId());
            recordSet = preparedStatement.executeQuery();
             
            String seqNumber = null;
            if (recordSet.next()) {
                seqNumber= recordSet.getString(1);
                if(seqNumber == null){
                    seqNumber = "0001";
                }
            }
            return StringUtils.right(seqNumber,4);
             
        } catch (SQLException ex) {
            throw new DataAccessException("�\���f�[�^�e�[�u���������s���ɗ�O���������܂����B", ex);
        } finally {
            DatabaseUtil.closeResource(recordSet, preparedStatement);
        }
    }

// 20050523 Start
    /**
     * �����ԍ��i����̈�p�j��Ԃ��B
     * @param connection
     * @param dataInfo
     * @return
     * @throws DataAccessException
     */
    private String getSequenceNumber4Tokutei(Connection connection, ShinseiDataInfo dataInfo)
            throws DataAccessException {

        //��t�ԍ���7���ڂ���4���������o�����l�̍ő�l�Ƀv���X1�B�j
//2007/02/03 �c�@�C����������   conut����max�֕ύX 
//        String select = "TO_CHAR(count(*) + 1, 'FM0000') SEQ_NUM";
        String select = "TO_CHAR(MAX(SUBSTR(UKETUKE_NO,7,4)) + 1, 'FM0000') SEQ_NUM";
//2007/02/03�@�c�@�C�������܂�        
        String query = 
            "SELECT "
                + select
                + " FROM"
                + "  SHINSEIDATAKANRI" + dbLink + " A"
                + " WHERE"
                + "  A.UKETUKE_NO IS NOT NULL"
                + " AND"
                + "  A.SHOZOKU_CD = ?"
                + " AND"
                //2005.08.09 iso ��Ή�
//              + "  A.RYOIKI_NO = ?"
                + "  DECODE(A.RYOIKI_NO, NULL, '-', RYOIKI_NO) = ?"     //��̏ꍇ"-"�ɒu��������
                + " AND"
                //2005.08.09 iso ��Ή�
//              + "  A.KOMOKU_NO = ?"
                + "  DECODE(A.KOMOKU_NO, NULL, '-', KOMOKU_NO) = ?"     //��̏ꍇ"-"�ɒu��������
                //2005.08.08 iso �����ԍ������Ɏ���ID��ǉ�
                + " AND"
                + "  A.JIGYO_ID = ?"
                ;
        
        //for debug
        if(log.isDebugEnabled()){log.debug("query:" + query);}

        //DB�ڑ�
        PreparedStatement preparedStatement = null;
        ResultSet recordSet = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            int i = 1;
            preparedStatement.setString(i++, dataInfo.getDaihyouInfo().getShozokuCd());
            //2005.08.09 iso ��Ή�
            if(dataInfo.getRyouikiNo() == null || "".equals(dataInfo.getRyouikiNo())) {
                preparedStatement.setString(i++, "-");          //��̏ꍇ"-"�ɒu��������
            } else {
                preparedStatement.setString(i++, dataInfo.getRyouikiNo());
            }
            //2005.08.09 iso ��Ή�
            if(dataInfo.getRyouikiKoumokuNo() == null || "".equals(dataInfo.getRyouikiKoumokuNo())) {
                preparedStatement.setString(i++, "-");          //��̏ꍇ"-"�ɒu��������
            } else {
                preparedStatement.setString(i++, dataInfo.getRyouikiKoumokuNo());
            }
            //2005.08.08 iso �����ԍ������Ɏ���ID��ǉ�
            preparedStatement.setString(i++, dataInfo.getJigyoId());
            recordSet = preparedStatement.executeQuery();
            String seqNumber = null;
            if (recordSet.next()) {
                seqNumber= recordSet.getString(1);
                if(seqNumber == null){
                    seqNumber = "0001";
                }
            }
            return StringUtils.right(seqNumber,4);
             
        } catch (SQLException ex) {
            throw new DataAccessException("�\���f�[�^�e�[�u���������s���ɗ�O���������܂����B", ex);
        } finally {
            DatabaseUtil.closeResource(recordSet, preparedStatement);
        }
    }
// Horikoshi End

//  2005/04/12 �ǉ� ��������----------
//  ���R:��Ղ̐\��ID���[�������̂���
    /**
     * �����ԍ��i��՗p�j��Ԃ��B
     * @param connection
     * @param dataInfo
     * @return
     * @throws DataAccessException
     */
    private String getSequenceNumber4Kiban(Connection connection, ShinseiDataInfo dataInfo)
        throws DataAccessException {

        //�����@�ցE�V�K/�p���E�זڃR�[�h�E�����ԍ����ƂɘA�Ԃ��擾����
//        String select = "TO_CHAR(count(*) + 1, 'FM0000') SEQ_NUM";
//2006/11/21 ��t�ԍ��ԍ��d���̃o�O�͈ȉ��̏C���ŉ����ł���
        String select = "TO_CHAR(MAX(SUBSTR(UKETUKE_NO,7,4)) + 1, 'FM0000') SEQ_NUM";
        
        String query = 
        "SELECT "
            + select
            + " FROM"
            + "  SHINSEIDATAKANRI" + dbLink + " A"
            + " WHERE"
            + "  A.UKETUKE_NO IS NOT NULL"
            + " AND"
            + "  A.SHOZOKU_CD = ?"
            + " AND"
            //2005.08.09 iso ��Ή�
//          + "  A.SHINSEI_KUBUN = ?"
            //2005.11.17 iso �V�K�p���敪�͍̔ԃ��[�����珜��
//          + "  DECODE(A.SHINSEI_KUBUN, NULL, '-', SHINSEI_KUBUN) = ?"     //��̏ꍇ"-"�ɒu��������
//          + " AND"
            + "  A.BUNKASAIMOKU_CD = ?"
            + " AND"
            //2005.08.08 iso �����ԍ������Ɏ���ID��ǉ�
//          + "  A.BUNKATSU_NO = ?"
            + "  DECODE(A.BUNKATSU_NO, NULL, '-', BUNKATSU_NO) = ?"         //��̏ꍇ"-"�ɒu��������
            + " AND"
            + "  A.JIGYO_ID = ?"
            ;
        
        //for debug
        if(log.isDebugEnabled()){
            log.debug("query:" + query);
        }
        
        //DB�ڑ�
        PreparedStatement preparedStatement = null;
        ResultSet recordSet = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            int i = 1;
            preparedStatement.setString(i++, dataInfo.getDaihyouInfo().getShozokuCd());
            //2005.11.17 iso �V�K�p���敪�͍̔ԃ��[�����珜��
//          //2005.08.09 iso ��Ή�
//          if(dataInfo.getShinseiKubun() == null || "".equals(dataInfo.getShinseiKubun())) {
//              preparedStatement.setString(i++, "-");          //��̏ꍇ"-"�ɒu��������
//          } else {
//              preparedStatement.setString(i++, dataInfo.getShinseiKubun());
//          }
            preparedStatement.setString(i++, dataInfo.getKadaiInfo().getBunkaSaimokuCd());
            //2005.08.08 iso �����ԍ������Ɏ���ID��ǉ�
            if(dataInfo.getKadaiInfo().getBunkatsuNo() == null || "".equals(dataInfo.getKadaiInfo().getBunkatsuNo())) {
                preparedStatement.setString(i++, "-");          //��̏ꍇ"-"�ɒu��������
            } else {
                preparedStatement.setString(i++, dataInfo.getKadaiInfo().getBunkatsuNo());
            }
            preparedStatement.setString(i++, dataInfo.getJigyoId());
            recordSet = preparedStatement.executeQuery();
             
            String seqNumber = null;
            if (recordSet.next()) {
                seqNumber= recordSet.getString(1);
                if(seqNumber == null){
                    seqNumber = "0001";
                }
            }
            return StringUtils.right(seqNumber,4);

        } catch (SQLException ex) {
            throw new DataAccessException("�\���f�[�^�e�[�u���������s���ɗ�O���������܂����B", ex);
        } finally {
            DatabaseUtil.closeResource(recordSet, preparedStatement);
        }
    }
//  2005/04/12 �ǉ� �����܂�----------
    
    /**
     * ���Y�\�������Q�Ƃ��邱�Ƃ��ł��邩�`�F�b�N����B
     * �Q�Ɖ\�ɊY�����Ȃ��ꍇ�� NoDataAccessExceptioin �𔭐�������B
     * <p>
     * ���f��͈ȉ��̒ʂ�B
     * <li>�\���҂̏ꍇ��������̐\����ID�̂��̂��ǂ���
     * <li>�����@�֒S���҂̏ꍇ��������̏����@��ID�̂��̂��ǂ���
     * <li>�R�����̏ꍇ��������Ɋ���U��ꂽ�\�������ǂ���
     * <li>�Ɩ��S���ҥ���������S�����鎖�Ƃ̐\�������ǂ���
     * <li>���̑��̏ꍇ����Ȃ��i���ׂ�OK�j
     * </p>
     * @param connection
     * @param primaryKey
     * @throws DataAccessException
     * @throws NoDataFoundException
     */
    public void checkOwnShinseiData(Connection connection, ShinseiDataPk primaryKey)
        throws DataAccessException, NoDataFoundException {
        checkOwnShinseiData(connection, new ShinseiDataPk[]{primaryKey});
    }

    /**
     * ���Y�\�������Q�Ƃ��邱�Ƃ��ł��邩�`�F�b�N����B
     * �Q�Ɖ\�ɊY�����Ȃ��ꍇ�� NoDataAccessExceptioin �𔭐�������B
     * �w�萔�ƊY���f�[�^������v���Ȃ��ꍇ�� NoDataAccessExceptioin �𔭐�������B
     * <p>
     * ���f��͈ȉ��̒ʂ�B
     * <li>�\���҂̏ꍇ��������̐\����ID�̂��̂��ǂ���
     * <li>�����@�֒S���҂̏ꍇ��������̏����@��ID�̂��̂��ǂ���
     * <li>�R�����̏ꍇ��������Ɋ���U��ꂽ�\�������ǂ���
     * <li>�Ɩ��S���ҥ���������S�����鎖�Ƃ̐\�������ǂ���
     * <li>���̑��̏ꍇ����Ȃ��i���ׂ�OK�j
     * </p>
     * @param connection
     * @param primaryKeys
     * @throws DataAccessException
     * @throws NoDataFoundException
     */
    public void checkOwnShinseiData(Connection connection, ShinseiDataPk[] primaryKeys)
        throws DataAccessException, NoDataFoundException {

        String table = " SHINSEIDATAKANRI" + dbLink + " A";
        String query = null;
        
        //���O�C�����[�U�ɂ���ď������𕪊򂷂�
        //---�\����
        if(userInfo.getRole().equals(UserRole.SHINSEISHA)){
            query = " AND"
                    + " A.SHINSEISHA_ID = '"
                    + EscapeUtil.toSqlString(userInfo.getShinseishaInfo().getShinseishaId())
                    + "'";
                    
        //---�����@�֒S����
        }else if(userInfo.getRole().equals(UserRole.SHOZOKUTANTO)){
            query = " AND"
                    + " A.SHOZOKU_CD = '"
                    + EscapeUtil.toSqlString(userInfo.getShozokuInfo().getShozokuCd())
                    + "'";
                    
        //---�R����
        }else if(userInfo.getRole().equals(UserRole.SHINSAIN)){
            table = " SHINSEIDATAKANRI" + dbLink + " A,"
                    + " (SELECT SYSTEM_NO FROM SHINSAKEKKA"+dbLink
                    + "  WHERE"
                    + "    SHINSAIN_NO = '"
                    +      EscapeUtil.toSqlString(userInfo.getShinsainInfo().getShinsainNo())
                    + "') B";
                    
            query = " AND"
                    + " A.SYSTEM_NO = B.SYSTEM_NO"; 
        
        //---�Ɩ��S����
        }else if(userInfo.getRole().equals(UserRole.GYOMUTANTO)){
            table = " SHINSEIDATAKANRI" + dbLink + " A,"
                    + " (SELECT JIGYO_CD FROM ACCESSKANRI"
                    + "  WHERE"
                    + "    GYOMUTANTO_ID = '"
                    +      EscapeUtil.toSqlString(userInfo.getGyomutantoInfo().getGyomutantoId())
                    + "') B";
                    
            query = " AND"
            		//2007/3/23 �d�l�ύX�̈׏C������
                    //���ʌ������i��i�N�����񉞕�̎��s�j�̏ꍇ�͑��i��̑S���ƂŌ�������
            		//+ " B.JIGYO_CD = SUBSTR(A.JIGYO_ID,3,5)";   //���ƃR�[�h
            		+ " B.JIGYO_CD = DECODE(SUBSTR(A.JIGYO_ID,3,5),'00152','00154','00153','00154','00155','00154','00156','00154',SUBSTR(A.JIGYO_ID,3,5))";
        
        //---����ȊO
        }else{
            query = ""; 
        }
        
        //SQL�̍쐬
        String select = 
            "SELECT COUNT(A.SYSTEM_NO) FROM " + table
                + " WHERE"
                + " A.SYSTEM_NO IN (" + getQuestionMark(primaryKeys.length) + ")"
                + query
                ;

        if (log.isDebugEnabled()){
        	log.debug("�R�����ʎQ�ƁF"+select);
        }
        
        PreparedStatement preparedStatement = null;
        ResultSet recordSet = null;
        int count = 0;
        try {
            //�o�^
            preparedStatement = connection.prepareStatement(select);
            int index = 1;
            for(int i=0; i<primaryKeys.length; i++){
                DatabaseUtil.setParameter(preparedStatement, index++, primaryKeys[i].getSystemNo());
            }
            recordSet = preparedStatement.executeQuery();
            if (recordSet.next()) {
                count = recordSet.getInt(1);
            }
        } catch (SQLException ex) {
            throw new DataAccessException("�\���f�[�^�Ǘ��e�[�u���������s���ɗ�O���������܂����B ", ex);
        } finally {
            DatabaseUtil.closeResource(recordSet, preparedStatement);
        }       
        
        //�Y�������̃`�F�b�N
        if(count !=  primaryKeys.length){
            throw new UserAuthorityException("�Q�Ɖ\�Ȑ\���f�[�^�ł͂���܂���B");
        }
    }

    /**
     * �w�肳�ꂽ�V�X�e����t�ԍ��̊ȈՐ\�������擾����B
     * @param connection
     * @param primaryKeys
     * @param checkFlg
     * @return
     * @throws DataAccessException
     * @throws NoDataFoundException
     */
    public SimpleShinseiDataInfo selectSimpleShinseiDataInfo(
        Connection connection,
        ShinseiDataPk primaryKeys,
        boolean checkFlg)
            throws DataAccessException, NoDataFoundException {
        
        return selectSimpleShinseiDataInfos(
            connection,
            new ShinseiDataPk[]{primaryKeys},
            checkFlg)[0];
    }

    /**
     * �w�肳�ꂽ�V�X�e����t�ԍ��̊ȈՐ\�������擾����B
     * �����̃V�X�e����t�ԍ����w�肵���ꍇ�A�P���ł��Y�����郌�R�[�h�����݂����
     * NoDataFoundException�͔������Ȃ��B���ׂẴ��R�[�h���擾�ł������ǂ�����
     * ��ʑ��Ŕ��f���邱�ƁB�i�폜�ς݂̐\���f�[�^�͏��O�ƂȂ�B�j
     * @param connection
     * @param primaryKeys
     * @param checkFlg
     * @return
     * @throws DataAccessException
     * @throws NoDataFoundException �P�����Y�����郌�R�[�h�����݂��Ȃ������ꍇ
     */
    public SimpleShinseiDataInfo[] selectSimpleShinseiDataInfos(
            Connection connection,
            ShinseiDataPk[] primaryKeys,
            boolean checkFlg)
            throws DataAccessException, NoDataFoundException {

        //�V�X�e����t�ԍ����w�肳��Ă��Ȃ��ꍇ��null��Ԃ�
        if(primaryKeys == null || primaryKeys.length == 0){
            return null;
        }
                
        if(checkFlg){
            //�Q�Ɖ\�\���f�[�^���`�F�b�N
            checkOwnShinseiData(connection, primaryKeys);
        }
        
        String query =
        "SELECT "
            + " A.SYSTEM_NO,"                   //�V�X�e����t�ԍ�
            + " A.UKETUKE_NO,"                  //�\���ԍ�
            + " A.JIGYO_ID,"                    //����ID
            + " A.NENDO,"                       //�N�x
            + " A.KAISU,"                       //��
            + " A.JIGYO_NAME,"                  //���Ɩ�
            + " A.SHINSEISHA_ID,"               //�\����ID
            + " A.SAKUSEI_DATE,"                //�\�����쐬��
            + " A.SHONIN_DATE,"                 //�������ԏ��F��
            + " A.NAME_KANJI_SEI,"              //�\���Ҏ����i������-���j
            + " A.NAME_KANJI_MEI,"              //�\���Ҏ����i������-���j
            + " A.KENKYU_NO,"                   //�\���Ҍ����Ҕԍ�
            + " A.SHOZOKU_CD,"                  //�����@�փR�[�h
            + " A.SHOZOKU_NAME,"                //�����@�֖�
            + " A.SHOZOKU_NAME_RYAKU,"          //�����@�֖��i���́j
            + " A.BUKYOKU_NAME,"                //���ǖ�
            + " A.BUKYOKU_NAME_RYAKU,"          //���ǖ��i���́j
            + " A.SHOKUSHU_NAME_KANJI,"         //�E��
            + " A.SHOKUSHU_NAME_RYAKU,"         //�E���i���́j    
            + " A.KADAI_NAME_KANJI,"            //�����ۑ薼(�a���j
            + " A.JIGYO_KUBUN,"                 //���Ƌ敪
            + " A.SUISENSHO_PATH,"              //���E���p�X
            + " A.JURI_KEKKA,"                  //�󗝌���
            + " A.JURI_BIKO,"                   //�󗝔��l
//2005/07/25 �����ԍ��ǉ�
            + " A.JURI_SEIRI_NO,"               //�����ԍ�
//2005/07/25
            + " A.KEKKA1_ABC,"                  //1���R������(ABC)
            + " A.KEKKA1_TEN,"                  //1���R������(�_��)
            + " A.KEKKA1_TEN_SORTED,"           //1���R������(�_����)
            + " A.KEKKA2,"                      //2���R������
            + " A.JOKYO_ID,"                    //�\����ID
            + " A.SAISHINSEI_FLG,"              //�Đ\���t���O
//2006/06/16 �c�@�ǉ���������
            + " A.KOMOKU_NO,"                   //�������ڔԍ�
            + " A.CHOSEIHAN,"                   //������
            + " A.EDITION,"                     //��  
//2006/06/16 �c�@�ǉ������܂�            
            + " B.UKETUKEKIKAN_END,"             //�w�U��t�����i�I���j
            + " B.RYOIKI_KAKUTEIKIKAN_END"        //�̈��\�Ҋm����ؓ�
            + " FROM"
            + " SHINSEIDATAKANRI"+dbLink+" A,"  //�\���f�[�^�Ǘ��e�[�u��
            + " JIGYOKANRI"+dbLink+" B"         //���Ə��Ǘ��e�[�u��
            + " WHERE"
            + " A.SYSTEM_NO IN ("+ getQuestionMark(primaryKeys.length) +")"
            + " AND"
            + " A.DEL_FLG = 0"                  //�폜�t���O��[0]
            + " AND"
            + " A.JIGYO_ID = B.JIGYO_ID"        //����ID����������
            + " ORDER BY A.SYSTEM_NO";
        
        PreparedStatement preparedStatement = null;
        ResultSet recordSet = null;
        
        SimpleShinseiDataInfo[] simpleInfos = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            int index = 1;
            for(int i=0; i<primaryKeys.length; i++){
                DatabaseUtil.setParameter(preparedStatement, index++, primaryKeys[i].getSystemNo());
            }
            recordSet = preparedStatement.executeQuery();
            
            List resultList = new ArrayList();
            while(recordSet.next()){
                SimpleShinseiDataInfo result = new SimpleShinseiDataInfo();
                result.setSystemNo(recordSet.getString("SYSTEM_NO"));
                result.setUketukeNo(recordSet.getString("UKETUKE_NO"));
                result.setJigyoId(recordSet.getString("JIGYO_ID"));
                result.setNendo(recordSet.getString("NENDO"));
                result.setKaisu(recordSet.getString("KAISU"));
                result.setJigyoName(recordSet.getString("JIGYO_NAME"));
                result.setShinseishaId(recordSet.getString("SHINSEISHA_ID"));
                result.setSakuseiDate(recordSet.getDate("SAKUSEI_DATE"));
                result.setShoninDate(recordSet.getDate("SHONIN_DATE"));
                result.setShinseishaNameSei(recordSet.getString("NAME_KANJI_SEI"));             
                result.setShinseishaNameMei(recordSet.getString("NAME_KANJI_MEI"));
                result.setKenkyuNo(recordSet.getString("KENKYU_NO"));
                result.setShozokuCd(recordSet.getString("SHOZOKU_CD"));
                result.setShozokuName(recordSet.getString("SHOZOKU_NAME"));
                result.setShozokuNameRyaku(recordSet.getString("SHOZOKU_NAME_RYAKU"));
                result.setBukyokuName(recordSet.getString("BUKYOKU_NAME"));
                result.setBukyokuNameRyaku(recordSet.getString("BUKYOKU_NAME_RYAKU"));
                result.setShokushuNameKanji(recordSet.getString("SHOKUSHU_NAME_KANJI"));
                result.setShokushuNameRyaku(recordSet.getString("SHOKUSHU_NAME_RYAKU"));
                result.setKadaiName(recordSet.getString("KADAI_NAME_KANJI"));
                result.setSuisenshoPath(recordSet.getString("SUISENSHO_PATH"));
                result.setJuriKekka(recordSet.getString("JURI_KEKKA"));
                result.setJuriBiko(recordSet.getString("JURI_BIKO"));
//              2005/07/25 �����ԍ��ǉ�
                result.setSeiriNo(recordSet.getString("JURI_SEIRI_NO"));
                
                result.setKekka1Abc(recordSet.getString("KEKKA1_ABC"));             
                result.setKekka1Ten(recordSet.getString("KEKKA1_TEN"));
                result.setKekka1TenSorted(recordSet.getString("KEKKA1_TEN_SORTED"));
                result.setKekka2(recordSet.getString("KEKKA2"));                
                result.setJokyoId(recordSet.getString("JOKYO_ID"));
                result.setSaishinseiFlg(recordSet.getString("SAISHINSEI_FLG"));
//2006/06/16 �c�@�ǉ���������
                result.setKomokuNo(recordSet.getString("KOMOKU_NO"));
                result.setChoseihan(recordSet.getString("CHOSEIHAN"));
                result.setEdition(recordSet.getString("EDITION"));
//2006/06/16�@�c�@�ǉ������܂�                
                result.setUketukeKikanEnd(recordSet.getDate("UKETUKEKIKAN_END"));
                result.setRyoikiKakuteikikanEnd(recordSet.getDate("RYOIKI_KAKUTEIKIKAN_END"));
                resultList.add(result);             
            }
            
            //�߂�l
            simpleInfos = (SimpleShinseiDataInfo[])resultList.toArray(new SimpleShinseiDataInfo[0]);
            if(simpleInfos.length == 0){
                throw new NoDataFoundException(
                    "�\���f�[�^�Ǘ��e�[�u���ɊY������f�[�^��������܂���B�����L�[�F�V�X�e����t�ԍ�'"
                        + Arrays.asList(primaryKeys).toString()
                        + "'");
            }
        } catch (SQLException ex) {
            throw new DataAccessException("�\���f�[�^�Ǘ��e�[�u���������s���ɗ�O���������܂����B ", ex);
        } finally {
            DatabaseUtil.closeResource(recordSet, preparedStatement);
        }
        
        return simpleInfos;
    }
    
    /**
     * �\�������擾����B
     * �폜�t���O���u0�v�̏ꍇ�i�폜����Ă��Ȃ��ꍇ�j�̂݌�������B
     * @param connection            �R�l�N�V����
     * @param primaryKeys           ��L�[���
     * @param checkFlg              �Q�ƃ`�F�b�N�t���O
     * @return                      �����@�֏��
     * @throws DataAccessException  �f�[�^�擾���ɗ�O�����������ꍇ
     * @throws NoDataFoundException �Ώۃf�[�^��������Ȃ��ꍇ
     */
    public ShinseiDataInfo selectShinseiDataInfo(
            Connection connection,
            ShinseiDataPk primaryKeys,
            boolean checkFlg)
            throws DataAccessException, NoDataFoundException {

        return selectShinseiDataInfo(connection, primaryKeys, false, checkFlg);
    }

    /**
     * �\�������擾����B
     * �폜�t���O���u0�v�̏ꍇ�i�폜����Ă��Ȃ��ꍇ�j�̂݌�������B
     * �擾�������R�[�h�����b�N����B�icomit���s����܂ŁB�j
     * @param connection            �R�l�N�V����
     * @param primaryKeys           ��L�[���
     * @param checkFlg              �Q�ƃ`�F�b�N�t���O
     * @return                      �����@�֏��
     * @throws DataAccessException  �f�[�^�擾���ɗ�O�����������ꍇ
     * @throws NoDataFoundException �Ώۃf�[�^��������Ȃ��ꍇ
     */
    public ShinseiDataInfo selectShinseiDataInfoForLock(
            Connection connection,
            ShinseiDataPk primaryKeys,
            boolean checkFlg)
            throws DataAccessException, NoDataFoundException {

        return selectShinseiDataInfo(connection, primaryKeys, true, checkFlg);
    }

    /**
     * �\�������擾����B
     * �폜�t���O���u0�v�̏ꍇ�i�폜����Ă��Ȃ��ꍇ�j�̂݌�������B
     * @param connection            �R�l�N�V����
     * @param primaryKeys           ��L�[���
     * @param lock                 true�̏ꍇ�̓��R�[�h�����b�N����
     * @param checkFlg              �Q�ƃ`�F�b�N�t���O
     * @return                      �����@�֏��
     * @throws DataAccessException  �f�[�^�擾���ɗ�O�����������ꍇ
     * @throws NoDataFoundException �Ώۃf�[�^��������Ȃ��ꍇ
     */
    private ShinseiDataInfo selectShinseiDataInfo(
            Connection connection,
            ShinseiDataPk primaryKeys,
            boolean lock,
            boolean checkFlg)
            throws DataAccessException, NoDataFoundException {

        if(checkFlg){
            //�Q�Ɖ\�\���f�[�^���`�F�b�N
            checkOwnShinseiData(connection, primaryKeys);
        }
        
        String query =
            "SELECT "
                + " A.SYSTEM_NO"                //�V�X�e����t�ԍ�
                + ",A.UKETUKE_NO"               //�\���ԍ�
                + ",A.JIGYO_ID"                 //����ID
                + ",A.NENDO"                    //�N�x
                + ",A.KAISU"                    //��
                + ",A.JIGYO_NAME"               //���Ɩ�
                + ",A.SHINSEISHA_ID"            //�\����ID
                + ",A.SAKUSEI_DATE"             //�\�����쐬��
                + ",A.SHONIN_DATE"              //�����@�֏��F��
//2006/07/26 dyh add start ���R�FDB�ŗ̈��\�Ҋm�����ǉ�
                + ",A.RYOIKI_KAKUTEI_DATE"      //�̈��\�Ҋm���
//2006/07/26 dyh add end                
                + ",A.JYURI_DATE"               //�w�U�󗝓�
                + ",A.NAME_KANJI_SEI"           //�\���Ҏ����i������-���j
                + ",A.NAME_KANJI_MEI"           //�\���Ҏ����i������-���j
                + ",A.NAME_KANA_SEI"            //�\���Ҏ����i�t���K�i-���j
                + ",A.NAME_KANA_MEI"            //�\���Ҏ����i�t���K�i-���j
                + ",A.NAME_RO_SEI"              //�\���Ҏ����i���[�}��-���j
                + ",A.NAME_RO_MEI"              //�\���Ҏ����i���[�}��-���j
                + ",A.NENREI"                   //�N��
                + ",A.KENKYU_NO"                //�\���Ҍ����Ҕԍ�
                + ",A.SHOZOKU_CD"               //�����@�փR�[�h
                + ",A.SHOZOKU_NAME"             //�����@�֖�
                + ",A.SHOZOKU_NAME_RYAKU"       //�����@�֖��i���́j
                + ",A.BUKYOKU_CD"               //���ǃR�[�h
                + ",A.BUKYOKU_NAME"             //���ǖ�
                + ",A.BUKYOKU_NAME_RYAKU"       //���ǖ��i���́j
                + ",A.SHOKUSHU_CD"              //�E���R�[�h
                + ",A.SHOKUSHU_NAME_KANJI"      //�E���i�a���j
                + ",A.SHOKUSHU_NAME_RYAKU"      //�E���i���́j
                + ",A.ZIP"                      //�X�֔ԍ�
                + ",A.ADDRESS"                  //�Z��
                + ",A.TEL"                      //TEL
                + ",A.FAX"                      //FAX
                + ",A.EMAIL"                    //E-Mail
//2006/06/30 �c�@�ǉ���������
                + ",A.URL"                      //URL  
//2006/06/30�@�c�@�ǉ������܂�                
                + ",A.SENMON"                   //���݂̐��
                + ",A.GAKUI"                    //�w��
                + ",A.BUNTAN"                   //�������S
                + ",A.KADAI_NAME_KANJI"         //�����ۑ薼(�a���j
                + ",A.KADAI_NAME_EIGO"          //�����ۑ薼(�p���j
                + ",A.JIGYO_KUBUN"              //���Ƌ敪
                + ",A.SHINSA_KUBUN"             //�R���敪
                + ",A.SHINSA_KUBUN_MEISHO"      //�R���敪����
                + ",A.BUNKATSU_NO"              //�����ԍ�
                + ",A.BUNKATSU_NO_MEISHO"       //�����ԍ�����
                + ",A.KENKYU_TAISHO"            //�����Ώۂ̗ތ^
                + ",A.KEI_NAME_NO"              //�n���̋敪�ԍ�
                + ",A.KEI_NAME"                 //�n���̋敪
                + ",A.KEI_NAME_RYAKU"           //�n���̋敪����
                + ",A.BUNKASAIMOKU_CD"          //�זڔԍ�
                + ",A.BUNYA_NAME"               //����
                + ",A.BUNKA_NAME"               //����
                + ",A.SAIMOKU_NAME"             //�ז�
                + ",A.BUNKASAIMOKU_CD2"         //�זڔԍ�2
                + ",A.BUNYA_NAME2"              //����2
                + ",A.BUNKA_NAME2"              //����2
                + ",A.SAIMOKU_NAME2"            //�ז�2
                + ",A.KANTEN_NO"                //���E�̊ϓ_�ԍ�
                + ",A.KANTEN"                   //���E�̊ϓ_
                + ",A.KANTEN_RYAKU"             //���E�̊ϓ_����
// 20050803
                + ",A.KAIGIHI"                  //��c��
                + ",A.INSATSUHI"                //�����
// Horikoshi
                + ",A.KEIHI1"                   //1�N�ڌ����o��
                + ",A.BIHINHI1"                 //1�N�ڐݔ����i��
                + ",A.SHOMOHINHI1"              //1�N�ڏ��Օi��
                + ",A.KOKUNAIRYOHI1"            //1�N�ڍ�������
                + ",A.GAIKOKURYOHI1"            //1�N�ڊO������
                + ",A.RYOHI1"                   //1�N�ڗ���
                + ",A.SHAKIN1"                  //1�N�ڎӋ���
                + ",A.SONOTA1"                  //1�N�ڂ��̑�
                + ",A.KEIHI2"                   //2�N�ڌ����o��
                + ",A.BIHINHI2"                 //2�N�ڐݔ����i��
                + ",A.SHOMOHINHI2"              //2�N�ڏ��Օi��
                + ",A.KOKUNAIRYOHI2"            //2�N�ڍ�������
                + ",A.GAIKOKURYOHI2"            //2�N�ڊO������
                + ",A.RYOHI2"                   //2�N�ڗ���
                + ",A.SHAKIN2"                  //2�N�ڎӋ���
                + ",A.SONOTA2"                  //2�N�ڂ��̑�
                + ",A.KEIHI3"                   //3�N�ڌ����o��
                + ",A.BIHINHI3"                 //3�N�ڐݔ����i��
                + ",A.SHOMOHINHI3"              //3�N�ڏ��Օi��
                + ",A.KOKUNAIRYOHI3"            //3�N�ڍ�������
                + ",A.GAIKOKURYOHI3"            //3�N�ڊO������
                + ",A.RYOHI3"                   //3�N�ڗ���
                + ",A.SHAKIN3"                  //3�N�ڎӋ���
                + ",A.SONOTA3"                  //3�N�ڂ��̑�
                + ",A.KEIHI4"                   //4�N�ڌ����o��
                + ",A.BIHINHI4"                 //4�N�ڐݔ����i��
                + ",A.SHOMOHINHI4"              //4�N�ڏ��Օi��
                + ",A.KOKUNAIRYOHI4"            //4�N�ڍ�������
                + ",A.GAIKOKURYOHI4"            //4�N�ڊO������
                + ",A.RYOHI4"                   //4�N�ڗ���
                + ",A.SHAKIN4"                  //4�N�ڎӋ���
                + ",A.SONOTA4"                  //4�N�ڂ��̑�
                + ",A.KEIHI5"                   //5�N�ڌ����o��
                + ",A.BIHINHI5"                 //5�N�ڐݔ����i��
                + ",A.SHOMOHINHI5"              //5�N�ڏ��Օi��
                + ",A.KOKUNAIRYOHI5"            //5�N�ڍ�������
                + ",A.GAIKOKURYOHI5"            //5�N�ڊO������
                + ",A.RYOHI5"                   //5�N�ڗ���
                + ",A.SHAKIN5"                  //5�N�ڎӋ���
                + ",A.SONOTA5"                  //5�N�ڂ��̑�
//2006/06/15 �c�@�ǉ���������  6�N�ڂ̌����o���ǉ�����
                + ",KEIHI6"                    //6�N�ڌ����o��
                + ",BIHINHI6"                  //6�N�ڐݔ����i��
                + ",SHOMOHINHI6"               //6�N�ڏ��Օi��
                + ",KOKUNAIRYOHI6"             //6�N�ڍ�������
                + ",GAIKOKURYOHI6"             //6�N�ڊO������
                + ",RYOHI6"                    //6�N�ڗ���
                + ",SHAKIN6"                   //6�N�ڎӋ���
                + ",SONOTA6"                   //6�N�ڂ��̑�
//2006/06/15 �c�@�ǉ������܂�                  
                + ",A.KEIHI_TOTAL"              //���v-�����o��
                + ",A.BIHINHI_TOTAL"            //���v-�ݔ����i��
                + ",A.SHOMOHINHI_TOTAL"         //���v-���Օi��
                + ",A.KOKUNAIRYOHI_TOTAL"       //���v-��������
                + ",A.GAIKOKURYOHI_TOTAL"       //���v-�O������
                + ",A.RYOHI_TOTAL"              //���v-����
                + ",A.SHAKIN_TOTAL"             //���v-�Ӌ���
                + ",A.SONOTA_TOTAL"             //���v-���̑�
                + ",A.SOSHIKI_KEITAI_NO"        //�����g�D�̌`�Ԕԍ�
                + ",A.SOSHIKI_KEITAI"           //�����g�D�̌`��
                + ",A.BUNTANKIN_FLG"            //���S���̗L��
                + ",A.KOYOHI"                   //�����x���Ҍٗp�o��
                + ",A.KENKYU_NINZU"             //�����Ґ�
                + ",A.TAKIKAN_NINZU"            //���@�ւ̕��S�Ґ�
                //2005/03/31 �ǉ� ----------------------------------�������� 
                //���R �������͎ҏ����͗����ǉ����ꂽ����
                + ",A.KYORYOKUSHA_NINZU"        //���@�ւ̕��S�Ґ�
                //2005/03/31 �ǉ� ----------------------------------�����܂� 
                + ",A.SHINSEI_KUBUN"            //�V�K�p���敪
                + ",A.KADAI_NO_KEIZOKU"         //�p�����̌����ۑ�ԍ�
                + ",A.SHINSEI_FLG_NO"           //�p�����̌����ۑ�ԍ�
                + ",A.SHINSEI_FLG"              //�\���̗L��
                + ",A.KADAI_NO_SAISYU"          //�ŏI�N�x�ۑ�ԍ�
                + ",A.KAIJIKIBO_FLG_NO"         //�J����]�̗L���ԍ�
                + ",A.KAIJIKIBO_FLG"            //�J����]�̗L��
                + ",A.KAIGAIBUNYA_CD"           //�C�O����R�[�h
                + ",A.KAIGAIBUNYA_NAME"         //�C�O���얼��
                + ",A.KAIGAIBUNYA_NAME_RYAKU"   //�C�O���엪��
                + ",A.KANREN_SHIMEI1"           //�֘A����̌�����-����1
                + ",A.KANREN_KIKAN1"            //�֘A����̌�����-�����@��1
                + ",A.KANREN_BUKYOKU1"          //�֘A����̌�����-��������1
                + ",A.KANREN_SHOKU1"            //�֘A����̌�����-�E��1
                + ",A.KANREN_SENMON1"           //�֘A����̌�����-��啪��1
                + ",A.KANREN_TEL1"              //�֘A����̌�����-�Ζ���d�b�ԍ�1
                + ",A.KANREN_JITAKUTEL1"        //�֘A����̌�����-����d�b�ԍ�1
                + ",A.KANREN_MAIL1"             //�֘A����̌�����-E-mail1
                + ",A.KANREN_SHIMEI2"           //�֘A����̌�����-����2
                + ",A.KANREN_KIKAN2"            //�֘A����̌�����-�����@��2
                + ",A.KANREN_BUKYOKU2"          //�֘A����̌�����-��������2
                + ",A.KANREN_SHOKU2"            //�֘A����̌�����-�E��2
                + ",A.KANREN_SENMON2"           //�֘A����̌�����-��啪��2
                + ",A.KANREN_TEL2"              //�֘A����̌�����-�Ζ���d�b�ԍ�2
                + ",A.KANREN_JITAKUTEL2"        //�֘A����̌�����-����d�b�ԍ�2
                + ",A.KANREN_MAIL2"             //�֘A����̌�����-E-mail2
                + ",A.KANREN_SHIMEI3"           //�֘A����̌�����-����3
                + ",A.KANREN_KIKAN3"            //�֘A����̌�����-�����@��3
                + ",A.KANREN_BUKYOKU3"          //�֘A����̌�����-��������3
                + ",A.KANREN_SHOKU3"            //�֘A����̌�����-�E��3
                + ",A.KANREN_SENMON3"           //�֘A����̌�����-��啪��3
                + ",A.KANREN_TEL3"              //�֘A����̌�����-�Ζ���d�b�ԍ�3
                + ",A.KANREN_JITAKUTEL3"        //�֘A����̌�����-����d�b�ԍ�3
                + ",A.KANREN_MAIL3"             //�֘A����̌�����-E-mail3
                + ",A.XML_PATH"                 //XML�̊i�[�p�X
                + ",A.PDF_PATH"                 //PDF�̊i�[�p�X
                + ",A.JURI_KEKKA"               //�󗝌���
                + ",A.JURI_BIKO"                //�󗝌��ʔ��l
//2005/07/25 �����ԍ��ǉ�
                + ",A.JURI_SEIRI_NO"            //�����ԍ�
//2005/07/25
                + ",A.SUISENSHO_PATH"           //���E���̊i�[�p�X
                + ",A.KEKKA1_ABC"               //�P���R������(ABC)
                + ",A.KEKKA1_TEN"               //�P���R������(�_��)
                + ",A.KEKKA1_TEN_SORTED"        //�P���R������(�_����)
                + ",A.SHINSA1_BIKO"             //�P���R�����l
                + ",A.KEKKA2"                   //�Q���R������
                + ",A.SOU_KEHI"                 //���o��i�w�U���́j
                + ",A.SHONEN_KEHI"              //���N�x�o��i�w�U���́j
                + ",A.SHINSA2_BIKO"             //�Ɩ��S���ҋL����
                + ",A.JOKYO_ID"                 //�\����ID
                + ",A.SAISHINSEI_FLG"           //�Đ\���t���O
//              2005/04/13 �ǉ� ��������----------
//              ���R:�Œǉ��̂���

                + ",A.EDITION"                  //��
                
//              2005/04/13 �ǉ� �����܂�----------

// 20050530 Start ����̈�̏���ǉ���������
                + ",A.KENKYU_KUBUN"             //�����敪
                + ",A.OHABAHENKO"               //�啝�ȕύX
                + ",A.RYOIKI_NO"                //�̈�ԍ�
                + ",A.RYOIKI_RYAKU"             //�̈旪��
                + ",A.KOMOKU_NO"                //���ڔԍ�
                + ",A.CHOSEIHAN"                //������
// Horikoshi End

// 20050712 �L�[���[�h���̒ǉ�
                + ",A.KEYWORD_CD"
                + ",A.SAIMOKU_KEYWORD"
                + ",A.OTHER_KEYWORD"
// Horikoshi
//2006/02/10    Syu
                + " ,A.SAIYO_DATE"                //�̗p�N����
                + " ,A.KINMU_HOUR"                //�Ζ����Ԑ�
                + " ,A.NAIYAKUGAKU"               //���ʌ�������������z
//End
//2007/02/02 �c�@�ǉ���������
                + " ,A.SHOREIHI_NO_NENDO"         //���ʌ����������ۑ�ԍ�-�N�x
                + " ,A.SHOREIHI_NO_SEIRI"         //���ʌ����������ۑ�ԍ�-�����ԍ�
//2007/02/02�@�c�@�ǉ������܂�                
//2006/02/13 Start
                + " ,A.OUBO_SHIKAKU"             //���厑�i
                + " ,A.SIKAKU_DATE"              //���i�擾�N����
                + " ,A.SYUTOKUMAE_KIKAN"         //���i�擾�O�@�֖�
                + " ,A.IKUKYU_START_DATE"        //��x���J�n��
                + " ,A.IKUKYU_END_DATE"          //��x���I����
//2006/02/13 End    
//              2006/02/15
                + " ,A.SHINSARYOIKI_NAME"        //���얼
                + " ,A.SHINSARYOIKI_CD"          //����R�[�h
// syuu End 

                + ",SUBSTR(A.JIGYO_ID,3,5) JIGYO_CD"        //���ƃR�[�h

                + ",A.DEL_FLG"                  //�폜�t���O
// ADD START 2007-07-26 BIS ���u��
                + ",A.NAIYAKUGAKU1"
                + ",A.NAIYAKUGAKU2"
                + ",A.NAIYAKUGAKU3"
                + ",A.NAIYAKUGAKU4"
                + ",A.NAIYAKUGAKU5"
// ADD END 2007-07-26 BIS ���u��
                + " FROM SHINSEIDATAKANRI" + dbLink + " A"
                + " WHERE SYSTEM_NO = ?"
//update2004/10/26 �V�X�e���Ǘ��Ҍ����\����񌟍��Ή�
//              + " AND DEL_FLG = 0"                //�폜�t���O��[0]
                ;
                                
            //�r��������s���ꍇ
            if(lock){
                query = query + " FOR UPDATE";
            }
                
        PreparedStatement preparedStatement = null;
        ResultSet recordSet = null;
        try {
            ShinseiDataInfo result = new ShinseiDataInfo();
            preparedStatement = connection.prepareStatement(query);
            int index = 1;
            DatabaseUtil.setParameter(preparedStatement, index++, primaryKeys.getSystemNo());   //�V�X�e����t�ԍ�
            recordSet = preparedStatement.executeQuery();
            if (recordSet.next()) {
                //---��{���i�O���j
                result.setSystemNo(recordSet.getString("SYSTEM_NO"));
                result.setUketukeNo(recordSet.getString("UKETUKE_NO"));
                result.setJigyoId(recordSet.getString("JIGYO_ID"));
                result.setNendo(recordSet.getString("NENDO"));
                result.setKaisu(recordSet.getString("KAISU"));
                result.setJigyoName(recordSet.getString("JIGYO_NAME"));
                result.setShinseishaId(recordSet.getString("SHINSEISHA_ID"));
                result.setSakuseiDate(recordSet.getDate("SAKUSEI_DATE"));
                result.setShoninDate(recordSet.getDate("SHONIN_DATE"));
                result.setRyoikiKakuteiDate(recordSet.getDate("RYOIKI_KAKUTEI_DATE"));
                result.setJyuriDate(recordSet.getDate("JYURI_DATE"));
                result.setJigyoCd(recordSet.getString("JIGYO_CD"));
               
                //---�\���ҁi������\�ҁj
                DaihyouInfo daihyouInfo = result.getDaihyouInfo();
                daihyouInfo.setNameKanjiSei(recordSet.getString("NAME_KANJI_SEI"));
                daihyouInfo.setNameKanjiMei(recordSet.getString("NAME_KANJI_MEI"));
                daihyouInfo.setNameKanaSei(recordSet.getString("NAME_KANA_SEI"));
                daihyouInfo.setNameKanaMei(recordSet.getString("NAME_KANA_MEI"));
                daihyouInfo.setNameRoSei(recordSet.getString("NAME_RO_SEI"));
                daihyouInfo.setNameRoMei(recordSet.getString("NAME_RO_MEI"));
                daihyouInfo.setNenrei(recordSet.getString("NENREI"));
                daihyouInfo.setKenkyuNo(recordSet.getString("KENKYU_NO"));
                daihyouInfo.setShozokuCd(recordSet.getString("SHOZOKU_CD"));
                daihyouInfo.setShozokuName(recordSet.getString("SHOZOKU_NAME"));
                daihyouInfo.setShozokuNameRyaku(recordSet.getString("SHOZOKU_NAME_RYAKU"));
                daihyouInfo.setBukyokuCd(recordSet.getString("BUKYOKU_CD"));
                daihyouInfo.setBukyokuName(recordSet.getString("BUKYOKU_NAME"));
                daihyouInfo.setBukyokuNameRyaku(recordSet.getString("BUKYOKU_NAME_RYAKU"));
                daihyouInfo.setShokushuCd(recordSet.getString("SHOKUSHU_CD"));
                daihyouInfo.setShokushuNameKanji(recordSet.getString("SHOKUSHU_NAME_KANJI"));
                daihyouInfo.setShokushuNameRyaku(recordSet.getString("SHOKUSHU_NAME_RYAKU"));
                daihyouInfo.setZip(recordSet.getString("ZIP"));
                daihyouInfo.setAddress(recordSet.getString("ADDRESS"));
                daihyouInfo.setTel(recordSet.getString("TEL"));
                daihyouInfo.setFax(recordSet.getString("FAX"));
                daihyouInfo.setEmail(recordSet.getString("EMAIL"));
//2006/06/30 �c�@�ǉ���������
                daihyouInfo.setUrl(recordSet.getString("URL"));
//2006/06/30�@�c�@�ǉ������܂�                
                daihyouInfo.setSenmon(recordSet.getString("SENMON"));
                daihyouInfo.setGakui(recordSet.getString("GAKUI"));
                daihyouInfo.setBuntan(recordSet.getString("BUNTAN"));

                //---�����ۑ�
                KadaiInfo kadaiInfo = result.getKadaiInfo();
                kadaiInfo.setKadaiNameKanji(recordSet.getString("KADAI_NAME_KANJI"));
                kadaiInfo.setKadaiNameEigo(recordSet.getString("KADAI_NAME_EIGO"));
                kadaiInfo.setJigyoKubun(recordSet.getString("JIGYO_KUBUN"));
                kadaiInfo.setShinsaKubun(recordSet.getString("SHINSA_KUBUN"));
                kadaiInfo.setShinsaKubunMeisho(recordSet.getString("SHINSA_KUBUN_MEISHO"));
                kadaiInfo.setBunkatsuNo(recordSet.getString("BUNKATSU_NO"));
                kadaiInfo.setBunkatsuNoMeisho(recordSet.getString("BUNKATSU_NO_MEISHO"));
                kadaiInfo.setKenkyuTaisho(recordSet.getString("KENKYU_TAISHO"));
                kadaiInfo.setKeiNameNo(recordSet.getString("KEI_NAME_NO"));
                kadaiInfo.setKeiName(recordSet.getString("KEI_NAME"));
                kadaiInfo.setKeiNameRyaku(recordSet.getString("KEI_NAME_RYAKU"));
                kadaiInfo.setBunkaSaimokuCd(recordSet.getString("BUNKASAIMOKU_CD"));
                kadaiInfo.setBunya(recordSet.getString("BUNYA_NAME"));
                kadaiInfo.setBunka(recordSet.getString("BUNKA_NAME"));
                kadaiInfo.setSaimokuName(recordSet.getString("SAIMOKU_NAME"));
                kadaiInfo.setBunkaSaimokuCd2(recordSet.getString("BUNKASAIMOKU_CD2"));
                kadaiInfo.setBunya2(recordSet.getString("BUNYA_NAME2"));
                kadaiInfo.setBunka2(recordSet.getString("BUNKA_NAME2"));
                kadaiInfo.setSaimokuName2(recordSet.getString("SAIMOKU_NAME2"));
                kadaiInfo.setKantenNo(recordSet.getString("KANTEN_NO"));
                kadaiInfo.setKanten(recordSet.getString("KANTEN"));
                kadaiInfo.setKantenRyaku(recordSet.getString("KANTEN_RYAKU"));

// 2005/04/13 �ǉ� ��������----------
// ���R:�Œǉ��̂���
                kadaiInfo.setEdition(recordSet.getInt("EDITION"));
// 2005/04/13 �ǉ� �����܂�----------

                //---�����o��
                KenkyuKeihiSoukeiInfo soukeiInfo = result.getKenkyuKeihiSoukeiInfo();
//2006/07/03 �c�@�C����������                
//                KenkyuKeihiInfo[] keihiInfo = soukeiInfo.getKenkyuKeihi();
//                for(int j=0; j<keihiInfo.length; j++){
//                    int n = j+1;
//                    keihiInfo[j].setKeihi(recordSet.getString("KEIHI"+n));
//                    keihiInfo[j].setBihinhi(recordSet.getString("BIHINHI"+n));
//                    keihiInfo[j].setShomohinhi(recordSet.getString("SHOMOHINHI"+n));
//                    keihiInfo[j].setKokunairyohi(recordSet.getString("KOKUNAIRYOHI"+n));
//                    keihiInfo[j].setGaikokuryohi(recordSet.getString("GAIKOKURYOHI"+n));
//                    keihiInfo[j].setRyohi(recordSet.getString("RYOHI"+n));
//                    keihiInfo[j].setShakin(recordSet.getString("SHAKIN"+n));
//                    keihiInfo[j].setSonota(recordSet.getString("SONOTA"+n));
//                }
                if (IJigyoCd.JIGYO_CD_TOKUTEI_SINKI.equals(result.getJigyoCd())) {
                    KenkyuKeihiInfo[] keihiInfo = soukeiInfo.getKenkyuKeihi6();
                    for (int j = 0; j < keihiInfo.length; j++) {
                        int n = j + 1;
                        keihiInfo[j].setKeihi(recordSet.getString("KEIHI" + n));
                        keihiInfo[j].setBihinhi(recordSet.getString("BIHINHI" + n));
                        keihiInfo[j].setShomohinhi(recordSet.getString("SHOMOHINHI" + n));
                        keihiInfo[j].setKokunairyohi(recordSet.getString("KOKUNAIRYOHI" + n));
                        keihiInfo[j].setGaikokuryohi(recordSet.getString("GAIKOKURYOHI" + n));
                        keihiInfo[j].setRyohi(recordSet.getString("RYOHI" + n));
                        keihiInfo[j].setShakin(recordSet.getString("SHAKIN" + n));
                        keihiInfo[j].setSonota(recordSet.getString("SONOTA" + n));
                    }
                } else {
                    KenkyuKeihiInfo[] keihiInfo = soukeiInfo.getKenkyuKeihi();
                    for (int j = 0; j < keihiInfo.length; j++) {
                        int n = j + 1;
                        keihiInfo[j].setKeihi(recordSet.getString("KEIHI" + n));
                        keihiInfo[j].setBihinhi(recordSet.getString("BIHINHI" + n));
                        keihiInfo[j].setShomohinhi(recordSet.getString("SHOMOHINHI" + n));
                        keihiInfo[j].setKokunairyohi(recordSet.getString("KOKUNAIRYOHI" + n));
                        keihiInfo[j].setGaikokuryohi(recordSet.getString("GAIKOKURYOHI" + n));
                        keihiInfo[j].setRyohi(recordSet.getString("RYOHI" + n));
                        keihiInfo[j].setShakin(recordSet.getString("SHAKIN" + n));
                        keihiInfo[j].setSonota(recordSet.getString("SONOTA" + n));
                    }
                }
//2006/07/03�@�c�@�C�������܂�                
                soukeiInfo.setKeihiTotal(recordSet.getString("KEIHI_TOTAL"));
                soukeiInfo.setBihinhiTotal(recordSet.getString("BIHINHI_TOTAL"));
                soukeiInfo.setShomohinhiTotal(recordSet.getString("SHOMOHINHI_TOTAL"));
                soukeiInfo.setKokunairyohiTotal(recordSet.getString("KOKUNAIRYOHI_TOTAL"));
                soukeiInfo.setGaikokuryohiTotal(recordSet.getString("GAIKOKURYOHI_TOTAL"));
                soukeiInfo.setRyohiTotal(recordSet.getString("RYOHI_TOTAL"));
                soukeiInfo.setShakinTotal(recordSet.getString("SHAKIN_TOTAL"));
                soukeiInfo.setSonotaTotal(recordSet.getString("SONOTA_TOTAL"));
// 20050803
                soukeiInfo.setMeetingCost(recordSet.getString("KAIGIHI"));      //��c��
                soukeiInfo.setPrintingCost(recordSet.getString("INSATSUHI"));   //�����
// Horikoshi

                //---��{���i���Ձj
                result.setSoshikiKeitaiNo(recordSet.getString("SOSHIKI_KEITAI_NO"));
                result.setSoshikiKeitai(recordSet.getString("SOSHIKI_KEITAI"));
                result.setBuntankinFlg(recordSet.getString("BUNTANKIN_FLG"));
                result.setKoyohi(recordSet.getString("KOYOHI"));
                result.setKenkyuNinzu(recordSet.getString("KENKYU_NINZU"));
                result.setTakikanNinzu(recordSet.getString("TAKIKAN_NINZU"));
                //2005/03/31 �ǉ� ------------------------------------------------------��������
                //���R �������͎ғ��͗����ǉ����ꂽ����
                result.setKyoryokushaNinzu(recordSet.getString("KYORYOKUSHA_NINZU"));
                
                try {
                    result.setKyoryokushaNinzuInt(Integer.parseInt(result.getKyoryokushaNinzu()));
                } catch (NumberFormatException e) {
                    result.setKyoryokushaNinzuInt(0);
                }
                try {
                    result.setKenkyuNinzuInt(Integer.parseInt(result.getKenkyuNinzu()));
                } catch (NumberFormatException e) {
                    result.setKenkyuNinzuInt(1);
                }
                //2005/03/31 �ǉ� ------------------------------------------------------�����܂�
                result.setShinseiKubun(recordSet.getString("SHINSEI_KUBUN"));
                result.setKadaiNoKeizoku(recordSet.getString("KADAI_NO_KEIZOKU"));
                result.setShinseiFlgNo(recordSet.getString("SHINSEI_FLG_NO"));
                result.setShinseiFlg(recordSet.getString("SHINSEI_FLG"));
                result.setKadaiNoSaisyu(recordSet.getString("KADAI_NO_SAISYU"));
                result.setKaijikiboFlgNo(recordSet.getString("KAIJIKIBO_FLG_NO"));
                result.setKaijiKiboFlg(recordSet.getString("KAIJIKIBO_FLG"));
                result.setKaigaibunyaCd(recordSet.getString("KAIGAIBUNYA_CD"));
                result.setKaigaibunyaName(recordSet.getString("KAIGAIBUNYA_NAME"));
                result.setKaigaibunyaNameRyaku(recordSet.getString("KAIGAIBUNYA_NAME_RYAKU"));

                //---�֘A����̌�����
                KanrenBunyaKenkyushaInfo[] kanrenInfo = result.getKanrenBunyaKenkyushaInfo();
                for(int j=0; j<kanrenInfo.length; j++){
                    int n = j+1;
                    kanrenInfo[j].setKanrenShimei(recordSet.getString("KANREN_SHIMEI"+n));
                    kanrenInfo[j].setKanrenKikan(recordSet.getString("KANREN_KIKAN"+n));
                    kanrenInfo[j].setKanrenBukyoku(recordSet.getString("KANREN_BUKYOKU"+n));
                    kanrenInfo[j].setKanrenShoku(recordSet.getString("KANREN_SHOKU"+n));
                    kanrenInfo[j].setKanrenSenmon(recordSet.getString("KANREN_SENMON"+n));
                    kanrenInfo[j].setKanrenTel(recordSet.getString("KANREN_TEL"+n));
                    kanrenInfo[j].setKanrenJitakuTel(recordSet.getString("KANREN_JITAKUTEL"+n));
                    kanrenInfo[j].setKanrenMail(recordSet.getString("KANREN_MAIL"+n));
                }

                //---��{���i�㔼�j
                result.setXmlPath(recordSet.getString("XML_PATH"));
                result.setPdfPath(recordSet.getString("PDF_PATH"));
                result.setJuriKekka(recordSet.getString("JURI_KEKKA"));
                result.setJuriBiko(recordSet.getString("JURI_BIKO"));
//              2005/07/25 �����ԍ��ǉ�
                result.setSeiriNo(recordSet.getString("JURI_SEIRI_NO"));

                result.setSuisenshoPath(recordSet.getString("SUISENSHO_PATH"));
                result.setKekka1Abc(recordSet.getString("KEKKA1_ABC"));
                result.setKekka1Ten(recordSet.getString("KEKKA1_TEN"));
                result.setKekka1TenSorted(recordSet.getString("KEKKA1_TEN_SORTED"));
                result.setShinsa1Biko(recordSet.getString("SHINSA1_BIKO"));
                result.setKekka2(recordSet.getString("KEKKA2"));
                result.setSouKehi(recordSet.getString("SOU_KEHI"));
                result.setShonenKehi(recordSet.getString("SHONEN_KEHI"));
                result.setShinsa2Biko(recordSet.getString("SHINSA2_BIKO"));
                result.setJokyoId(recordSet.getString("JOKYO_ID"));
                result.setSaishinseiFlg(recordSet.getString("SAISHINSEI_FLG"));
                result.setDelFlg(recordSet.getString("DEL_FLG"));

// 20050530 Start ����̈�̂��ߒǉ�
                result.setKenkyuKubun(recordSet.getString("KENKYU_KUBUN"));                             // �����敪
                if(IShinseiMaintenance.TOKUTEI_HENKOU.equals(recordSet.getString("OHABAHENKO"))){       // �啝�ȕύX
                    result.setChangeFlg(IShinseiMaintenance.CHECK_ON);}                                 //�`�F�b�N�I��
                else{result.setChangeFlg(IShinseiMaintenance.CHECK_OFF);}                               //�`�F�b�N�I�t
                result.setRyouikiNo(recordSet.getString("RYOIKI_NO"));                                  // �̈�ԍ�
                result.setRyouikiRyakuName(recordSet.getString("RYOIKI_RYAKU"));                        // �̈旪��
                result.setRyouikiKoumokuNo(recordSet.getString("KOMOKU_NO"));                           // ���ڔԍ�
                if(IShinseiMaintenance.TOKUTEI_CHOUSEI.equals(recordSet.getString("CHOSEIHAN"))){       // ������
                    result.setChouseiFlg(IShinseiMaintenance.CHECK_ON);}                                //�`�F�b�N�I��
                else{result.setChouseiFlg(IShinseiMaintenance.CHECK_OFF);}                              //�`�F�b�N�I�t
// Horikoshi End

// 20050712 �L�[���[�h���̒ǉ�
                result.setKigou(recordSet.getString("KEYWORD_CD"));
                result.setKeyName(recordSet.getString("SAIMOKU_KEYWORD"));
                result.setKeyOtherName(recordSet.getString("OTHER_KEYWORD"));
// Horikoshi
                
//2006/02/10   syu
                result.setSaiyoDate(recordSet.getDate("SAIYO_DATE"));//�̗p�N����
                result.setKinmuHour(recordSet.getString("KINMU_HOUR")); //�Ζ����Ԑ�
                result.setNaiyakugaku(recordSet.getString("NAIYAKUGAKU"));//���ʌ�������������z
//End
//2007/02/02 �c�@�ǉ���������
                result.setShoreihiNoNendo(recordSet.getString("SHOREIHI_NO_NENDO"));//���ʌ����������ۑ�ԍ�-�N�x
                result.setShoreihiNoSeiri(recordSet.getString("SHOREIHI_NO_SEIRI"));//���ʌ����������ۑ�ԍ�-�����ԍ�
//2007/02/02�@�c�@�ǉ������܂� 
            
//2006/02/13 Start
                result.setOuboShikaku(recordSet.getString("OUBO_SHIKAKU")); //���厑�i
                result.setSikakuDate(recordSet.getDate("SIKAKU_DATE"));//���i�擾�N����
                result.setSyuTokumaeKikan(recordSet.getString("SYUTOKUMAE_KIKAN"));//���i�擾�O�@�֖�
                result.setIkukyuStartDate(recordSet.getDate("IKUKYU_START_DATE"));//��x���J�n��
                result.setIkukyuEndDate(recordSet.getDate("IKUKYU_END_DATE"));//��x���I����     
//Nae End
    
//2006/02/16 Start
                result.setShinsaRyoikiName(recordSet.getString("SHINSARYOIKI_NAME"));//���얼
                result.setShinsaRyoikiCd(recordSet.getString("SHINSARYOIKI_CD"));//����R�[�h            
//syuu End
// ADD START 2007-07-26 BIS ���u��
                String naiyakugaku1 = recordSet.getString("NAIYAKUGAKU1");
                String naiyakugaku2 = recordSet.getString("NAIYAKUGAKU2");
                String naiyakugaku3 = recordSet.getString("NAIYAKUGAKU3");
                String naiyakugaku4 = recordSet.getString("NAIYAKUGAKU4");
                String naiyakugaku5 = recordSet.getString("NAIYAKUGAKU5");
                
                if (naiyakugaku1 != null && !"".equals(naiyakugaku1)
            		&& naiyakugaku2 != null && !"".equals(naiyakugaku2)
            		&& naiyakugaku3 != null && !"".equals(naiyakugaku3)
            		&& naiyakugaku4 != null && !"".equals(naiyakugaku4)
            		&& naiyakugaku5 != null && !"".equals(naiyakugaku5))
                {
                	result.getKenkyuKeihiSoukeiInfo().getKenkyuKeihi()[0].setNaiyaku(naiyakugaku1);
                    result.getKenkyuKeihiSoukeiInfo().getKenkyuKeihi()[1].setNaiyaku(naiyakugaku2);
                    result.getKenkyuKeihiSoukeiInfo().getKenkyuKeihi()[2].setNaiyaku(naiyakugaku3);
                    result.getKenkyuKeihiSoukeiInfo().getKenkyuKeihi()[3].setNaiyaku(naiyakugaku4);
                    result.getKenkyuKeihiSoukeiInfo().getKenkyuKeihi()[4].setNaiyaku(naiyakugaku5);
                    
	                long totle = Long.parseLong(recordSet.getString("NAIYAKUGAKU1"));
	                totle += Long.parseLong(recordSet.getString("NAIYAKUGAKU2"));
	                totle += Long.parseLong(recordSet.getString("NAIYAKUGAKU3"));
	                totle += Long.parseLong(recordSet.getString("NAIYAKUGAKU4"));
	                totle += Long.parseLong(recordSet.getString("NAIYAKUGAKU5"));
	                result.getKenkyuKeihiSoukeiInfo().setNaiyakuTotal(String.valueOf(totle));
                } else {
                	result.getKenkyuKeihiSoukeiInfo().getKenkyuKeihi()[0].setNaiyaku("");
                    result.getKenkyuKeihiSoukeiInfo().getKenkyuKeihi()[1].setNaiyaku("");
                    result.getKenkyuKeihiSoukeiInfo().getKenkyuKeihi()[2].setNaiyaku("");
                    result.getKenkyuKeihiSoukeiInfo().getKenkyuKeihi()[3].setNaiyaku("");
                    result.getKenkyuKeihiSoukeiInfo().getKenkyuKeihi()[4].setNaiyaku("");
                	result.getKenkyuKeihiSoukeiInfo().setNaiyakuTotal("");
                }
// ADD END 2007-07-26 BIS ���u��
                return result;

            } else {
                throw new NoDataFoundException(
                    "�\���f�[�^�Ǘ��e�[�u���ɊY������f�[�^��������܂���B�����L�[�F�V�X�e����t�ԍ�'"
                        + primaryKeys.getSystemNo()
                        + "'");
            }
        } catch (SQLException ex) {
            throw new DataAccessException("�\���f�[�^�Ǘ��e�[�u���������s���ɗ�O���������܂����B ", ex);
        } finally {
            DatabaseUtil.closeResource(recordSet, preparedStatement);
        }
    }

//add start dyh 2006/06/02
    /**
     * �\�������擾����B
     * �폜�t���O���u0�v�̏ꍇ�i�폜����Ă��Ȃ��ꍇ�j�̂݌�������B
     * �擾�������R�[�h�����b�N����B�icomit���s����܂ŁB�j
     * @param connection            �R�l�N�V����
     * @param searchInfo            �������
     * @param systemNos             ��L�[���i�[
     * @return                      �����@�֏��
     * @throws DataAccessException  �f�[�^�擾���ɗ�O�����������ꍇ
     * @throws NoDataFoundException �Ώۃf�[�^��������Ȃ��ꍇ
     * @throws ApplicationException
     */
    public Page selectShinseiDataInfoList(
            Connection connection,
            ShinseiSearchInfo searchInfo,
            String[] systemNos)
            throws DataAccessException , NoDataFoundException, ApplicationException {

        String select =
            "SELECT "
                + " A.SYSTEM_NO"                //�V�X�e����t�ԍ�
                + ",A.UKETUKE_NO"               //�\���ԍ�
                + ",A.JIGYO_ID"                 //����ID
                + ",A.NENDO"                    //�N�x
                + ",A.KAISU"                    //��
                + ",A.JIGYO_NAME"               //���Ɩ�
                + ",A.SHINSEISHA_ID"            //�\����ID
                + ",A.SAKUSEI_DATE"             //�\�����쐬��
                + ",A.SHONIN_DATE"              //�����@�֏��F��
// 2006/07/26 dyh add start ���R�FDB�ŗ̈��\�Ҋm�����ǉ�
                + ",A.RYOIKI_KAKUTEI_DATE"      //�̈��\�Ҋm���
// 2006/07/26 dyh add end
                + ",A.JYURI_DATE"               //�w�U�󗝓�
                + ",A.NAME_KANJI_SEI"           //�\���Ҏ����i������-���j
                + ",A.NAME_KANJI_MEI"           //�\���Ҏ����i������-���j
                + ",A.NAME_KANA_SEI"            //�\���Ҏ����i�t���K�i-���j
                + ",A.NAME_KANA_MEI"            //�\���Ҏ����i�t���K�i-���j
                + ",A.NAME_RO_SEI"              //�\���Ҏ����i���[�}��-���j
                + ",A.NAME_RO_MEI"              //�\���Ҏ����i���[�}��-���j
                + ",A.NENREI"                   //�N��
                + ",A.KENKYU_NO"                //�\���Ҍ����Ҕԍ�
                + ",A.SHOZOKU_CD"               //�����@�փR�[�h
                + ",A.SHOZOKU_NAME"             //�����@�֖�
                + ",A.SHOZOKU_NAME_RYAKU"       //�����@�֖��i���́j
                + ",A.BUKYOKU_CD"               //���ǃR�[�h
                + ",A.BUKYOKU_NAME"             //���ǖ�
                + ",A.BUKYOKU_NAME_RYAKU"       //���ǖ��i���́j
                + ",A.SHOKUSHU_CD"              //�E���R�[�h
                + ",A.SHOKUSHU_NAME_KANJI"      //�E���i�a���j
                + ",A.SHOKUSHU_NAME_RYAKU"      //�E���i���́j
                + ",A.ZIP"                      //�X�֔ԍ�
                + ",A.ADDRESS"                  //�Z��
                + ",A.TEL"                      //TEL
                + ",A.FAX"                      //FAX
                + ",A.EMAIL"                    //E-Mail
//2006/06/30 �c�@�ǉ���������
                + ",A.URL"                      //URL  
//2006/06/30�@�c�@�ǉ������܂�    
                + ",A.SENMON"                   //���݂̐��
                + ",A.GAKUI"                    //�w��
                + ",A.BUNTAN"                   //�������S
                + ",A.KADAI_NAME_KANJI"         //�����ۑ薼(�a���j
                + ",A.KADAI_NAME_EIGO"          //�����ۑ薼(�p���j
                + ",A.JIGYO_KUBUN"              //���Ƌ敪
                + ",A.SHINSA_KUBUN"             //�R���敪
                + ",A.SHINSA_KUBUN_MEISHO"      //�R���敪����
                + ",A.BUNKATSU_NO"              //�����ԍ�
                + ",A.BUNKATSU_NO_MEISHO"       //�����ԍ�����
                + ",A.KENKYU_TAISHO"            //�����Ώۂ̗ތ^
                + ",A.KEI_NAME_NO"              //�n���̋敪�ԍ�
                + ",A.KEI_NAME"                 //�n���̋敪
                + ",A.KEI_NAME_RYAKU"           //�n���̋敪����
                + ",A.BUNKASAIMOKU_CD"          //�זڔԍ�
                + ",A.BUNYA_NAME"               //����
                + ",A.BUNKA_NAME"               //����
                + ",A.SAIMOKU_NAME"             //�ז�
                + ",A.BUNKASAIMOKU_CD2"         //�זڔԍ�2
                + ",A.BUNYA_NAME2"              //����2
                + ",A.BUNKA_NAME2"              //����2
                + ",A.SAIMOKU_NAME2"            //�ז�2
                + ",A.KANTEN_NO"                //���E�̊ϓ_�ԍ�
                + ",A.KANTEN"                   //���E�̊ϓ_
                + ",A.KANTEN_RYAKU"             //���E�̊ϓ_����
                + ",A.KAIGIHI"                  //��c��
                + ",A.INSATSUHI"                //�����
                + ",A.KEIHI1"                   //1�N�ڌ����o��
                + ",A.BIHINHI1"                 //1�N�ڐݔ����i��
                + ",A.SHOMOHINHI1"              //1�N�ڏ��Օi��
                + ",A.KOKUNAIRYOHI1"            //1�N�ڍ�������
                + ",A.GAIKOKURYOHI1"            //1�N�ڊO������
                + ",A.RYOHI1"                   //1�N�ڗ���
                + ",A.SHAKIN1"                  //1�N�ڎӋ���
                + ",A.SONOTA1"                  //1�N�ڂ��̑�
                + ",A.KEIHI2"                   //2�N�ڌ����o��
                + ",A.BIHINHI2"                 //2�N�ڐݔ����i��
                + ",A.SHOMOHINHI2"              //2�N�ڏ��Օi��
                + ",A.KOKUNAIRYOHI2"            //2�N�ڍ�������
                + ",A.GAIKOKURYOHI2"            //2�N�ڊO������
                + ",A.RYOHI2"                   //2�N�ڗ���
                + ",A.SHAKIN2"                  //2�N�ڎӋ���
                + ",A.SONOTA2"                  //2�N�ڂ��̑�
                + ",A.KEIHI3"                   //3�N�ڌ����o��
                + ",A.BIHINHI3"                 //3�N�ڐݔ����i��
                + ",A.SHOMOHINHI3"              //3�N�ڏ��Օi��
                + ",A.KOKUNAIRYOHI3"            //3�N�ڍ�������
                + ",A.GAIKOKURYOHI3"            //3�N�ڊO������
                + ",A.RYOHI3"                   //3�N�ڗ���
                + ",A.SHAKIN3"                  //3�N�ڎӋ���
                + ",A.SONOTA3"                  //3�N�ڂ��̑�
                + ",A.KEIHI4"                   //4�N�ڌ����o��
                + ",A.BIHINHI4"                 //4�N�ڐݔ����i��
                + ",A.SHOMOHINHI4"              //4�N�ڏ��Օi��
                + ",A.KOKUNAIRYOHI4"            //4�N�ڍ�������
                + ",A.GAIKOKURYOHI4"            //4�N�ڊO������
                + ",A.RYOHI4"                   //4�N�ڗ���
                + ",A.SHAKIN4"                  //4�N�ڎӋ���
                + ",A.SONOTA4"                  //4�N�ڂ��̑�
                + ",A.KEIHI5"                   //5�N�ڌ����o��
                + ",A.BIHINHI5"                 //5�N�ڐݔ����i��
                + ",A.SHOMOHINHI5"              //5�N�ڏ��Օi��
                + ",A.KOKUNAIRYOHI5"            //5�N�ڍ�������
                + ",A.GAIKOKURYOHI5"            //5�N�ڊO������
                + ",A.RYOHI5"                   //5�N�ڗ���
                + ",A.SHAKIN5"                  //5�N�ڎӋ���
                + ",A.SONOTA5"                  //5�N�ڂ��̑�
                + ",A.KEIHI_TOTAL"              //���v-�����o��
                + ",A.BIHINHI_TOTAL"            //���v-�ݔ����i��
                + ",A.SHOMOHINHI_TOTAL"         //���v-���Օi��
                + ",A.KOKUNAIRYOHI_TOTAL"       //���v-��������
                + ",A.GAIKOKURYOHI_TOTAL"       //���v-�O������
                + ",A.RYOHI_TOTAL"              //���v-����
                + ",A.SHAKIN_TOTAL"             //���v-�Ӌ���
                + ",A.SONOTA_TOTAL"             //���v-���̑�
                + ",A.SOSHIKI_KEITAI_NO"        //�����g�D�̌`�Ԕԍ�
                + ",A.SOSHIKI_KEITAI"           //�����g�D�̌`��
                + ",A.BUNTANKIN_FLG"            //���S���̗L��
                + ",A.KOYOHI"                   //�����x���Ҍٗp�o��
                + ",A.KENKYU_NINZU"             //�����Ґ�
                + ",A.TAKIKAN_NINZU"            //���@�ւ̕��S�Ґ�
                + ",A.KYORYOKUSHA_NINZU"        //���@�ւ̕��S�Ґ�
                + ",A.SHINSEI_KUBUN"            //�V�K�p���敪
                + ",A.KADAI_NO_KEIZOKU"         //�p�����̌����ۑ�ԍ�
                + ",A.SHINSEI_FLG_NO"           //�p�����̌����ۑ�ԍ�
                + ",A.SHINSEI_FLG"              //�\���̗L��
                + ",A.KADAI_NO_SAISYU"          //�ŏI�N�x�ۑ�ԍ�
                + ",A.KAIJIKIBO_FLG_NO"         //�J����]�̗L���ԍ�
                + ",A.KAIJIKIBO_FLG"            //�J����]�̗L��
                + ",A.KAIGAIBUNYA_CD"           //�C�O����R�[�h
                + ",A.KAIGAIBUNYA_NAME"         //�C�O���얼��
                + ",A.KAIGAIBUNYA_NAME_RYAKU"   //�C�O���엪��
                + ",A.KANREN_SHIMEI1"           //�֘A����̌�����-����1
                + ",A.KANREN_KIKAN1"            //�֘A����̌�����-�����@��1
                + ",A.KANREN_BUKYOKU1"          //�֘A����̌�����-��������1
                + ",A.KANREN_SHOKU1"            //�֘A����̌�����-�E��1
                + ",A.KANREN_SENMON1"           //�֘A����̌�����-��啪��1
                + ",A.KANREN_TEL1"              //�֘A����̌�����-�Ζ���d�b�ԍ�1
                + ",A.KANREN_JITAKUTEL1"        //�֘A����̌�����-����d�b�ԍ�1
                + ",A.KANREN_MAIL1"             //�֘A����̌�����-E-mail1
                + ",A.KANREN_SHIMEI2"           //�֘A����̌�����-����2
                + ",A.KANREN_KIKAN2"            //�֘A����̌�����-�����@��2
                + ",A.KANREN_BUKYOKU2"          //�֘A����̌�����-��������2
                + ",A.KANREN_SHOKU2"            //�֘A����̌�����-�E��2
                + ",A.KANREN_SENMON2"           //�֘A����̌�����-��啪��2
                + ",A.KANREN_TEL2"              //�֘A����̌�����-�Ζ���d�b�ԍ�2
                + ",A.KANREN_JITAKUTEL2"        //�֘A����̌�����-����d�b�ԍ�2
                + ",A.KANREN_MAIL2"             //�֘A����̌�����-E-mail2
                + ",A.KANREN_SHIMEI3"           //�֘A����̌�����-����3
                + ",A.KANREN_KIKAN3"            //�֘A����̌�����-�����@��3
                + ",A.KANREN_BUKYOKU3"          //�֘A����̌�����-��������3
                + ",A.KANREN_SHOKU3"            //�֘A����̌�����-�E��3
                + ",A.KANREN_SENMON3"           //�֘A����̌�����-��啪��3
                + ",A.KANREN_TEL3"              //�֘A����̌�����-�Ζ���d�b�ԍ�3
                + ",A.KANREN_JITAKUTEL3"        //�֘A����̌�����-����d�b�ԍ�3
                + ",A.KANREN_MAIL3"             //�֘A����̌�����-E-mail3
                + ",A.XML_PATH"                 //XML�̊i�[�p�X
                + ",A.PDF_PATH"                 //PDF�̊i�[�p�X
                + ",A.JURI_KEKKA"               //�󗝌���
                + ",A.JURI_BIKO"                //�󗝌��ʔ��l
                + ",A.JURI_SEIRI_NO"            //�����ԍ�
                + ",A.SUISENSHO_PATH"           //���E���̊i�[�p�X
                + ",A.KEKKA1_ABC"               //�P���R������(ABC)
                + ",A.KEKKA1_TEN"               //�P���R������(�_��)
                + ",A.KEKKA1_TEN_SORTED"        //�P���R������(�_����)
                + ",A.SHINSA1_BIKO"             //�P���R�����l
                + ",A.KEKKA2"                   //�Q���R������
                + ",A.SOU_KEHI"                 //���o��i�w�U���́j
                + ",A.SHONEN_KEHI"              //���N�x�o��i�w�U���́j
                + ",A.SHINSA2_BIKO"             //�Ɩ��S���ҋL����
                + ",A.JOKYO_ID"                 //�\����ID
                + ",A.SAISHINSEI_FLG"           //�Đ\���t���O
                + ",A.EDITION"                  //��
                + ",A.KENKYU_KUBUN"             //�����敪
                + ",A.OHABAHENKO"               //�啝�ȕύX
                + ",A.RYOIKI_NO"                //�̈�ԍ�
                + ",A.RYOIKI_RYAKU"             //�̈旪��
                + ",A.KOMOKU_NO"                //���ڔԍ�
                + ",A.CHOSEIHAN"                //������
                + ",A.KEYWORD_CD"
                + ",A.SAIMOKU_KEYWORD"
                + ",A.OTHER_KEYWORD"
                + ",A.SAIYO_DATE"               //�̗p�N����
                + ",A.KINMU_HOUR"               //�Ζ����Ԑ�
                + ",A.NAIYAKUGAKU"              //���ʌ�������������z
                + ",A.OUBO_SHIKAKU"             //���厑�i
                + ",A.SIKAKU_DATE"              //���i�擾�N����
                + ",A.SYUTOKUMAE_KIKAN"         //���i�擾�O�@�֖�
                + ",A.IKUKYU_START_DATE"        //��x���J�n��
                + ",A.IKUKYU_END_DATE"          //��x���I����
                + ",A.SHINSARYOIKI_NAME"        //���얼
                + ",A.SHINSARYOIKI_CD"          //����R�[�h
                + ",SUBSTR(A.JIGYO_ID,3,5) JIGYO_CD"        //���ƃR�[�h
                + ",A.DEL_FLG"                  //�폜�t���O
                + " FROM SHINSEIDATAKANRI" + dbLink + " A"
                + " WHERE SYSTEM_NO IN ("
                + StringUtil.changeArray2CSV(systemNos,true)
                + ")"
                ;
        
        //��������������SQL���𐶐�����B
        String query = getQueryString(select, searchInfo);
        
        //for debug
        if(log.isDebugEnabled()){
            log.debug("query:" + query);
        }
        try{
            // �y�[�W�擾
            return SelectUtil.selectPageInfo(connection, (SearchInfo)searchInfo, query);
        } finally {
            DatabaseUtil.closeConnection(connection);
        }
    }
//add end dyh 2006/06/02

    /**
     * �\������o�^����B
     * @param connection                �R�l�N�V����
     * @param addInfo                   �o�^����\�����
     * @throws DataAccessException      �o�^���ɗ�O�����������ꍇ�B
     * @throws DuplicateKeyException    �L�[�Ɉ�v����f�[�^�����ɑ��݂���ꍇ
     */
    public void insertShinseiDataInfo(
            Connection connection,
            ShinseiDataInfo addInfo)
            throws DataAccessException, DuplicateKeyException {

        //�L�[�d���`�F�b�N
        try {
            selectShinseiDataInfo(connection, addInfo, true);
            //NG
            throw new DuplicateKeyException(
                "'" + addInfo + "'�͊��ɓo�^����Ă��܂��B");
        } catch (NoDataFoundException e) {
            //OK
        }
        
        String query =
            "INSERT INTO SHINSEIDATAKANRI"+dbLink+" "
                + "("
                + "  SYSTEM_NO"                 //�V�X�e����t�ԍ�
                + " ,UKETUKE_NO"                //�\���ԍ�
                + " ,JIGYO_ID"                  //����ID
                + " ,NENDO"                     //�N�x
                + " ,KAISU"                     //��
                + " ,JIGYO_NAME"                //���Ɩ�
                + " ,SHINSEISHA_ID"             //�\����ID
                + " ,SAKUSEI_DATE"              //�\�����쐬��
                + " ,SHONIN_DATE"               //�����@�֏��F��
// 2006/07/26 dyh add start ���R�FDB�ŗ̈��\�Ҋm�����ǉ�
                + " ,RYOIKI_KAKUTEI_DATE"       //�̈��\�Ҋm���
// 2006/07/26 dyh add end
                + " ,JYURI_DATE"                //�w�U�󗝓�
                + " ,NAME_KANJI_SEI"            //�\���Ҏ����i������-���j
                + " ,NAME_KANJI_MEI"            //�\���Ҏ����i������-���j
                + " ,NAME_KANA_SEI"             //�\���Ҏ����i�t���K�i-���j
                + " ,NAME_KANA_MEI"             //�\���Ҏ����i�t���K�i-���j
                + " ,NAME_RO_SEI"               //�\���Ҏ����i���[�}��-���j
                + " ,NAME_RO_MEI"               //�\���Ҏ����i���[�}��-���j
                + " ,NENREI"                    //�N��
                + " ,KENKYU_NO"                 //�\���Ҍ����Ҕԍ�
                + " ,SHOZOKU_CD"                //�����@�փR�[�h
                + " ,SHOZOKU_NAME"              //�����@�֖�
                + " ,SHOZOKU_NAME_RYAKU"        //�����@�֖��i���́j
                + " ,BUKYOKU_CD"                //���ǃR�[�h
                + " ,BUKYOKU_NAME"              //���ǖ�
                + " ,BUKYOKU_NAME_RYAKU"        //���ǖ��i���́j
                + " ,SHOKUSHU_CD"               //�E���R�[�h
                + " ,SHOKUSHU_NAME_KANJI"       //�E���i�a���j
                + " ,SHOKUSHU_NAME_RYAKU"       //�E���i���́j
                + " ,ZIP"                       //�X�֔ԍ�
                + " ,ADDRESS"                   //�Z��
                + " ,TEL"                       //TEL
                + " ,FAX"                       //FAX
                + " ,EMAIL"                     //E-Mail
//2006/06/30 �c�@�ǉ���������
                + " ,URL"                       //URL  
//2006/06/30�@�c�@�ǉ������܂�                    
                + " ,SENMON"                    //���݂̐��
                + " ,GAKUI"                     //�w��
                + " ,BUNTAN"                    //�������S
                + " ,KADAI_NAME_KANJI"          //�����ۑ薼(�a���j
                + " ,KADAI_NAME_EIGO"           //�����ۑ薼(�p���j
                + " ,JIGYO_KUBUN"               //���Ƌ敪
                + " ,SHINSA_KUBUN"              //�R���敪
                + " ,SHINSA_KUBUN_MEISHO"       //�R���敪����
                + " ,BUNKATSU_NO"               //�����ԍ�
                + " ,BUNKATSU_NO_MEISHO"        //�����ԍ�����
                + " ,KENKYU_TAISHO"             //�����Ώۂ̗ތ^
                + " ,KEI_NAME_NO"               //�n���̋敪�ԍ�
                + " ,KEI_NAME"                  //�n���̋敪
                + " ,KEI_NAME_RYAKU"            //�n���̋敪����
                + " ,BUNKASAIMOKU_CD"           //�זڔԍ�
                + " ,BUNYA_NAME"                //����
                + " ,BUNKA_NAME"                //����
                + " ,SAIMOKU_NAME"              //�ז�
                + " ,BUNKASAIMOKU_CD2"          //�זڔԍ�2
                + " ,BUNYA_NAME2"               //����2
                + " ,BUNKA_NAME2"               //����2
                + " ,SAIMOKU_NAME2"             //�ז�2
                + " ,KANTEN_NO"                 //���E�̊ϓ_�ԍ�
                + " ,KANTEN"                    //���E�̊ϓ_
                + " ,KANTEN_RYAKU"              //���E�̊ϓ_����               
//              2005/04/13 �ǉ� ��������----------
//              ���R:�Œǉ��̂���

                + " ,EDITION"                   //��
                
//              2005/04/13 �ǉ� �����܂�----------
                + " ,KEIHI1"                    //1�N�ڌ����o��
                + " ,BIHINHI1"                  //1�N�ڐݔ����i��
                + " ,SHOMOHINHI1"               //1�N�ڏ��Օi��
                + " ,KOKUNAIRYOHI1"             //1�N�ڍ�������
                + " ,GAIKOKURYOHI1"             //1�N�ڊO������
                + " ,RYOHI1"                    //1�N�ڗ���
                + " ,SHAKIN1"                   //1�N�ڎӋ���
                + " ,SONOTA1"                   //1�N�ڂ��̑�
                + " ,KEIHI2"                    //2�N�ڌ����o��
                + " ,BIHINHI2"                  //2�N�ڐݔ����i��
                + " ,SHOMOHINHI2"               //2�N�ڏ��Օi��
                + " ,KOKUNAIRYOHI2"             //2�N�ڍ�������
                + " ,GAIKOKURYOHI2"             //2�N�ڊO������
                + " ,RYOHI2"                    //2�N�ڗ���
                + " ,SHAKIN2"                   //2�N�ڎӋ���
                + " ,SONOTA2"                   //2�N�ڂ��̑�
                + " ,KEIHI3"                    //3�N�ڌ����o��
                + " ,BIHINHI3"                  //3�N�ڐݔ����i��
                + " ,SHOMOHINHI3"               //3�N�ڏ��Օi��
                + " ,KOKUNAIRYOHI3"             //3�N�ڍ�������
                + " ,GAIKOKURYOHI3"             //3�N�ڊO������
                + " ,RYOHI3"                    //3�N�ڗ���
                + " ,SHAKIN3"                   //3�N�ڎӋ���
                + " ,SONOTA3"                   //3�N�ڂ��̑�
                + " ,KEIHI4"                    //4�N�ڌ����o��
                + " ,BIHINHI4"                  //4�N�ڐݔ����i��
                + " ,SHOMOHINHI4"               //4�N�ڏ��Օi��
                + " ,KOKUNAIRYOHI4"             //4�N�ڍ�������
                + " ,GAIKOKURYOHI4"             //4�N�ڊO������
                + " ,RYOHI4"                    //4�N�ڗ���
                + " ,SHAKIN4"                   //4�N�ڎӋ���
                + " ,SONOTA4"                   //4�N�ڂ��̑�
                + " ,KEIHI5"                    //5�N�ڌ����o��
                + " ,BIHINHI5"                  //5�N�ڐݔ����i��
                + " ,SHOMOHINHI5"               //5�N�ڏ��Օi��
                + " ,KOKUNAIRYOHI5"             //5�N�ڍ�������
                + " ,GAIKOKURYOHI5"             //5�N�ڊO������
                + " ,RYOHI5"                    //5�N�ڗ���
                + " ,SHAKIN5"                   //5�N�ڎӋ���
                + " ,SONOTA5"                   //5�N�ڂ��̑�
//2006/06/15 �c�@�ǉ���������  6�N�ڂ̌����o���ǉ�����
                + " ,KEIHI6"                    //6�N�ڌ����o��
                + " ,BIHINHI6"                  //6�N�ڐݔ����i��
                + " ,SHOMOHINHI6"               //6�N�ڏ��Օi��
                + " ,KOKUNAIRYOHI6"             //6�N�ڍ�������
                + " ,GAIKOKURYOHI6"             //6�N�ڊO������
                + " ,RYOHI6"                    //6�N�ڗ���
                + " ,SHAKIN6"                   //6�N�ڎӋ���
                + " ,SONOTA6"                   //6�N�ڂ��̑�
//2006/06/15 �c�@�ǉ������܂�                
                + " ,KEIHI_TOTAL"               //���v-�����o��
                + " ,BIHINHI_TOTAL"             //���v-�ݔ����i��
                + " ,SHOMOHINHI_TOTAL"          //���v-���Օi��
                + " ,KOKUNAIRYOHI_TOTAL"        //���v-��������
                + " ,GAIKOKURYOHI_TOTAL"        //���v-�O������
                + " ,RYOHI_TOTAL"               //���v-����
                + " ,SHAKIN_TOTAL"              //���v-�Ӌ���
                + " ,SONOTA_TOTAL"              //���v-���̑�
                + " ,SOSHIKI_KEITAI_NO"         //�����g�D�̌`�Ԕԍ�
                + " ,SOSHIKI_KEITAI"            //�����g�D�̌`��
                + " ,BUNTANKIN_FLG"             //���S���̗L��
                + " ,KOYOHI"                    //�����x���Ҍٗp�o��
                + " ,KENKYU_NINZU"              //�����Ґ�
                + " ,TAKIKAN_NINZU"             //���@�ւ̕��S�Ґ�
                //2005/03/31 �ǉ� ---------------------------------------��������
                //���R �������͎҂̓��͗����ǉ����ꂽ����
                + " ,KYORYOKUSHA_NINZU"         //�������͎Ґ�
                //2005/03/31 �ǉ� ---------------------------------------�����܂�
                + " ,SHINSEI_KUBUN"             //�V�K�p���敪
                + " ,KADAI_NO_KEIZOKU"          //�p�����̌����ۑ�ԍ�
                + " ,SHINSEI_FLG_NO"            //�����v��ŏI�N�x�O�N�x�̉���
                + " ,SHINSEI_FLG"               //�\���̗L��
                + " ,KADAI_NO_SAISYU"           //�ŏI�N�x�ۑ�ԍ�
                + " ,KAIJIKIBO_FLG_NO"          //�J����]�̗L���ԍ�
                + " ,KAIJIKIBO_FLG"             //�J����]�̗L��
                + " ,KAIGAIBUNYA_CD"            //�C�O����R�[�h
                + " ,KAIGAIBUNYA_NAME"          //�C�O���얼��
                + " ,KAIGAIBUNYA_NAME_RYAKU"    //�C�O���엪��
                + " ,KANREN_SHIMEI1"            //�֘A����̌�����-����1
                + " ,KANREN_KIKAN1"             //�֘A����̌�����-�����@��1
                + " ,KANREN_BUKYOKU1"           //�֘A����̌�����-��������1
                + " ,KANREN_SHOKU1"             //�֘A����̌�����-�E��1
                + " ,KANREN_SENMON1"            //�֘A����̌�����-��啪��1
                + " ,KANREN_TEL1"               //�֘A����̌�����-�Ζ���d�b�ԍ�1
                + " ,KANREN_JITAKUTEL1"         //�֘A����̌�����-����d�b�ԍ�1
                + " ,KANREN_MAIL1"              //�֘A����̌�����-E-mail1
                + " ,KANREN_SHIMEI2"            //�֘A����̌�����-����2
                + " ,KANREN_KIKAN2"             //�֘A����̌�����-�����@��2
                + " ,KANREN_BUKYOKU2"           //�֘A����̌�����-��������2
                + " ,KANREN_SHOKU2"             //�֘A����̌�����-�E��2
                + " ,KANREN_SENMON2"            //�֘A����̌�����-��啪��2
                + " ,KANREN_TEL2"               //�֘A����̌�����-�Ζ���d�b�ԍ�2
                + " ,KANREN_JITAKUTEL2"         //�֘A����̌�����-����d�b�ԍ�2
                + " ,KANREN_MAIL2"              //�֘A����̌�����-E-mail2
                + " ,KANREN_SHIMEI3"            //�֘A����̌�����-����3
                + " ,KANREN_KIKAN3"             //�֘A����̌�����-�����@��3
                + " ,KANREN_BUKYOKU3"           //�֘A����̌�����-��������3
                + " ,KANREN_SHOKU3"             //�֘A����̌�����-�E��3
                + " ,KANREN_SENMON3"            //�֘A����̌�����-��啪��3
                + " ,KANREN_TEL3"               //�֘A����̌�����-�Ζ���d�b�ԍ�3
                + " ,KANREN_JITAKUTEL3"         //�֘A����̌�����-����d�b�ԍ�3
                + " ,KANREN_MAIL3"              //�֘A����̌�����-E-mail3
                + " ,XML_PATH"                  //XML�̊i�[�p�X
                + " ,PDF_PATH"                  //PDF�̊i�[�p�X
                + " ,JURI_KEKKA"                //�󗝌���
                + " ,JURI_BIKO"                 //�󗝌��ʔ��l
//2005/07/25 �󗝔ԍ�
                + " ,JURI_SEIRI_NO"             //�󗝔ԍ�
                + " ,SUISENSHO_PATH"            //���E���̊i�[�p�X
                + " ,KEKKA1_ABC"                //�P���R������(ABC)
                + " ,KEKKA1_TEN"                //�P���R������(�_��)
                + " ,KEKKA1_TEN_SORTED"         //�P���R������(�_����)
                + " ,SHINSA1_BIKO"              //�P���R�����l
                + " ,KEKKA2"                    //�Q���R������
                + " ,SOU_KEHI"                  //���o��i�w�U���́j
                + " ,SHONEN_KEHI"               //���N�x�o��i�w�U���́j
                + " ,SHINSA2_BIKO"              //�Ɩ��S���ҋL����
                + " ,JOKYO_ID"                  //�\����ID
                + " ,SAISHINSEI_FLG"            //�Đ\���t���O
                + " ,DEL_FLG"                   //�폜�t���O
// 20050530 Start
                + ", KENKYU_KUBUN"              //�����敪
                + ", OHABAHENKO"                //�啝�ȕύX�t���O
                + ", RYOIKI_NO"                 //�̈�ԍ�
                + ", RYOIKI_RYAKU"              //�̈旪��
                + ", KOMOKU_NO"                 //���ڔԍ�
                + ", CHOSEIHAN"                 //�����ǃt���O
// Horikoshi End
                
// 20050712 �L�[���[�h���̒ǉ�
                + ", KEYWORD_CD"                //�L��
                + ", SAIMOKU_KEYWORD"           //�זڕ\�L�[���[�h
                + ", OTHER_KEYWORD "            //�זڕ\�ȊO�̃L�[���[�h
// Horikoshi

// 20050803
                + " ,KAIGIHI"                   //��c��
                + " ,INSATSUHI"                 //�����
// Horikoshi
//2006/02/09
                + " ,SAIYO_DATE"                //�̗p�N����
                + " ,KINMU_HOUR"                //�Ζ����Ԑ�
                + " ,NAIYAKUGAKU"               //���ʌ�������������z
//End
//2007/02/02 �c�@�ǉ���������
                + " ,SHOREIHI_NO_NENDO"               //���ʌ����������ۑ�ԍ�-�N�x
                + " ,SHOREIHI_NO_SEIRI"         //���ʌ����������ۑ�ԍ�-�����ԍ� 
//2007/02/02�@�c�@�ǉ������܂�                
//2006/02/13 Start
                + " ,OUBO_SHIKAKU"              //���厑�i
                + " ,SIKAKU_DATE"               //���i�擾�N����
                + " ,SYUTOKUMAE_KIKAN"          //���i�擾�O�@�֖�
                + " ,IKUKYU_START_DATE"         //��x���J�n��
                + " ,IKUKYU_END_DATE"           //��x���I����
//  Nae End 
//2006/02/15
                + " ,SHINSARYOIKI_CD"           //�R���̈�R�[�h
                + " ,SHINSARYOIKI_NAME"         //�R���̈於��
// syuu End         
// ADD START 2007-07-26 BIS ���u��
                + " ,NAIYAKUGAKU1"				//1�N�ړ���z
                + " ,NAIYAKUGAKU2"				//2�N�ړ���z
                + " ,NAIYAKUGAKU3"				//3�N�ړ���z
                + " ,NAIYAKUGAKU4"				//4�N�ړ���z
                + " ,NAIYAKUGAKU5"				//5�N�ړ���z
// ADD END 2007-07-26 BIS ���u��
                + ") "
                + "VALUES "
                + "("
                + "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,"  //25��
                + "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,"  //50��
                + "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,"  //75��
                + "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,"  //100��
                + "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,"  //125��
                + "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,"  //150��
// 20050530 Start ����̈�̒ǉ����ڕ���ǉ�
                + "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?"               //169��
// Horikoshi End

// 20050803 ��c��ƈ�����ǉ�
                + ",?,?"                                                //171��
// Horikoshi

// 20050712 �L�[���[�h���̒ǉ�
                + ",?,?,?,?"                                            //175��
// Horikoshi
//2006/02/09
                + ",?,?,?"                                              //178��
//End
//2006/02/13 Start  
                + ",?,?,?,?,?"                                          //183��
//Nae End   
                + ",?,?"                                                //185��
//2006/06/30 �c�@�ǉ���������@6�N�ڂ̌����o���ǉ�����
                + ",?,?,?,?,?,?,?,?,?,?"                                //195��
//2006/06/30�@�c�@�ǉ������܂�
//2007/02/02 �c�@�ǉ���������@���ʌ����������ۑ�ԍ���ǉ�����
                + ",?,?"                                                  //196��
//2007/02/02�@�c�@�ǉ������܂�     
// ADD START 2007-07-26 BIS ���u��
                + ",?,?,?,?,?"
// ADD END 2007-07-26 BIS ���u��
                + ")";
        PreparedStatement preparedStatement = null;
        try {
            //�o�^
            preparedStatement = connection.prepareStatement(query);
            this.setAllParameter(preparedStatement, addInfo);           
            DatabaseUtil.executeUpdate(preparedStatement);
        } catch (SQLException ex) {
            log.error("�\�����o�^���ɗ�O���������܂����B ", ex);
            //TODO �v���C�}���[�L�[�d���G���[�����̂��߁A�ꎞ�I�ɒǉ�
            log.info(addInfo);
            throw new DataAccessException("�\�����o�^���ɗ�O���������܂����B ", ex);
        } finally {
            DatabaseUtil.closeResource(null, preparedStatement);
        }
    }

    /**
     * �\�������X�V����B
     * @param connection                �R�l�N�V����
     * @param updateInfo                �X�V����\�����
     * @param chekcFlg                  �Q�Ɖ\�`�F�b�N�t���O
     * @throws DataAccessException      �X�V���ɗ�O�����������ꍇ
     * @throws NoDataFoundException     �Ώۃf�[�^��������Ȃ��ꍇ
     */
    public void updateShinseiDataInfo(
            Connection connection,
            ShinseiDataInfo updateInfo,
            boolean chekcFlg)
            throws DataAccessException, NoDataFoundException {
        
        if(chekcFlg){
            //�Q�Ɖ\�\���f�[�^���`�F�b�N
            checkOwnShinseiData(connection, updateInfo);
        }

        String query =
            //�����������̂��߃e�[�u���̃J�������Ƃ͕ύX���Ă���
            "UPDATE SHINSEIDATAKANRI"+dbLink    
                + " SET"
                + "  SYSTEM_NO = ?"                 //�V�X�e����t�ԍ�
                + " ,UKETUKE_NO = ?"                //�\���ԍ�
                + " ,JIGYO_ID = ?"                  //����ID
                + " ,NENDO = ?"                     //�N�x
                + " ,KAISU = ?"                     //��
                + " ,JIGYO_NAME = ?"                //���Ɩ�
                + " ,SHINSEISHA_ID = ?"             //�\����ID
                + " ,SAKUSEI_DATE = ?"              //�\�����쐬��
                + " ,SHONIN_DATE = ?"               //�����@�֏��F��
// 2006/07/26 dyh add start ���R�FDB�ŗ̈��\�Ҋm�����ǉ�
                + " ,RYOIKI_KAKUTEI_DATE = ?"       //�̈��\�Ҋm���
// 2006/07/26 dyh add end
                + " ,JYURI_DATE = ?"                //�w�U�󗝓�
                + " ,NAME_KANJI_SEI = ?"            //�\���Ҏ����i������-���j
                + " ,NAME_KANJI_MEI = ?"            //�\���Ҏ����i������-���j
                + " ,NAME_KANA_SEI = ?"             //�\���Ҏ����i�t���K�i-���j
                + " ,NAME_KANA_MEI = ?"             //�\���Ҏ����i�t���K�i-���j
                + " ,NAME_RO_SEI = ?"               //�\���Ҏ����i���[�}��-���j
                + " ,NAME_RO_MEI = ?"               //�\���Ҏ����i���[�}��-���j
                + " ,NENREI = ?"                    //�N��
                + " ,KENKYU_NO = ?"                 //�\���Ҍ����Ҕԍ�
                + " ,SHOZOKU_CD = ?"                //�����@�փR�[�h
                + " ,SHOZOKU_NAME = ?"              //�����@�֖�
                + " ,SHOZOKU_NAME_RYAKU = ?"        //�����@�֖��i���́j
                + " ,BUKYOKU_CD = ?"                //���ǃR�[�h
                + " ,BUKYOKU_NAME = ?"              //���ǖ�
                + " ,BUKYOKU_NAME_RYAKU = ?"        //���ǖ��i���́j
                + " ,SHOKUSHU_CD = ?"               //�E���R�[�h
                + " ,SHOKUSHU_NAME_KANJI = ?"       //�E���i�a���j
                + " ,SHOKUSHU_NAME_RYAKU = ?"       //�E���i���́j
                + " ,ZIP = ?"                       //�X�֔ԍ�
                + " ,ADDRESS = ?"                   //�Z��
                + " ,TEL = ?"                       //TEL
                + " ,FAX = ?"                       //FAX
                + " ,EMAIL = ?"                     //E-Mail
//2006/06/30 �c�@�ǉ���������
                + " ,URL = ?"                       //URL
//2006/06/30�@�c�@�ǉ������܂�                
                + " ,SENMON = ?"                    //���݂̐��
                + " ,GAKUI = ?"                     //�w��
                + " ,BUNTAN = ?"                    //�������S
                + " ,KADAI_NAME_KANJI = ?"          //�����ۑ薼(�a���j
                + " ,KADAI_NAME_EIGO = ?"           //�����ۑ薼(�p���j
                + " ,JIGYO_KUBUN = ?"               //���Ƌ敪
                + " ,SHINSA_KUBUN = ?"              //�R���敪
                + " ,SHINSA_KUBUN_MEISHO = ?"       //�R���敪����
                + " ,BUNKATSU_NO = ?"               //�����ԍ�
                + " ,BUNKATSU_NO_MEISHO = ?"        //�����ԍ�����
                + " ,KENKYU_TAISHO = ?"             //�����Ώۂ̗ތ^
                + " ,KEI_NAME_NO = ?"               //�n���̋敪�ԍ�
                + " ,KEI_NAME = ?"                  //�n���̋敪
                + " ,KEI_NAME_RYAKU = ?"            //�n���̋敪����
                + " ,BUNKASAIMOKU_CD = ?"           //�זڔԍ�
                + " ,BUNYA_NAME = ?"                //����
                + " ,BUNKA_NAME = ?"                //����
                + " ,SAIMOKU_NAME = ?"              //�ז�
                + " ,BUNKASAIMOKU_CD2 = ?"          //�זڔԍ�2
                + " ,BUNYA_NAME2 = ?"               //����2
                + " ,BUNKA_NAME2 = ?"               //����2
                + " ,SAIMOKU_NAME2 = ?"             //�ז�2
                + " ,KANTEN_NO = ?"                 //���E�̊ϓ_�ԍ�
                + " ,KANTEN = ?"                    //���E�̊ϓ_
                + " ,KANTEN_RYAKU = ?"              //���E�̊ϓ_����
//              2005/04/13 �ǉ� ��������----------
//              ���R:�����ԍ��ǉ��̂���

                + " ,EDITION = ?"                   //��
                
//              2005/04/13 �ǉ� �����܂�----------
                + " ,KEIHI1 = ?"                    //1�N�ڌ����o��
                + " ,BIHINHI1 = ?"                  //1�N�ڐݔ����i��
                + " ,SHOMOHINHI1 = ?"               //1�N�ڏ��Օi��
                + " ,KOKUNAIRYOHI1 = ?"             //1�N�ڍ�������
                + " ,GAIKOKURYOHI1 = ?"             //1�N�ڊO������
                + " ,RYOHI1 = ?"                    //1�N�ڗ���
                + " ,SHAKIN1 = ?"                   //1�N�ڎӋ���
                + " ,SONOTA1 = ?"                   //1�N�ڂ��̑�
                + " ,KEIHI2 = ?"                    //2�N�ڌ����o��
                + " ,BIHINHI2 = ?"                  //2�N�ڐݔ����i��
                + " ,SHOMOHINHI2 = ?"               //2�N�ڏ��Օi��
                + " ,KOKUNAIRYOHI2 = ?"             //2�N�ڍ�������
                + " ,GAIKOKURYOHI2 = ?"             //2�N�ڊO������
                + " ,RYOHI2 = ?"                    //2�N�ڗ���
                + " ,SHAKIN2 = ?"                   //2�N�ڎӋ���
                + " ,SONOTA2 = ?"                   //2�N�ڂ��̑�
                + " ,KEIHI3 = ?"                    //3�N�ڌ����o��
                + " ,BIHINHI3 = ?"                  //3�N�ڐݔ����i��
                + " ,SHOMOHINHI3 = ?"               //3�N�ڏ��Օi��
                + " ,KOKUNAIRYOHI3 = ?"             //3�N�ڍ�������
                + " ,GAIKOKURYOHI3 = ?"             //3�N�ڊO������
                + " ,RYOHI3 = ?"                    //3�N�ڗ���
                + " ,SHAKIN3 = ?"                   //3�N�ڎӋ���
                + " ,SONOTA3 = ?"                   //3�N�ڂ��̑�
                + " ,KEIHI4 = ?"                    //4�N�ڌ����o��
                + " ,BIHINHI4 = ?"                  //4�N�ڐݔ����i��
                + " ,SHOMOHINHI4 = ?"               //4�N�ڏ��Օi��
                + " ,KOKUNAIRYOHI4 = ?"             //4�N�ڍ�������
                + " ,GAIKOKURYOHI4 = ?"             //4�N�ڊO������
                + " ,RYOHI4 = ?"                    //4�N�ڗ���
                + " ,SHAKIN4 = ?"                   //4�N�ڎӋ���
                + " ,SONOTA4 = ?"                   //4�N�ڂ��̑�
                + " ,KEIHI5 = ?"                    //5�N�ڌ����o��
                + " ,BIHINHI5 = ?"                  //5�N�ڐݔ����i��
                + " ,SHOMOHINHI5 = ?"               //5�N�ڏ��Օi��
                + " ,KOKUNAIRYOHI5 = ?"             //5�N�ڍ�������
                + " ,GAIKOKURYOHI5 = ?"             //5�N�ڊO������
                + " ,RYOHI5 = ?"                    //5�N�ڗ���
                + " ,SHAKIN5 = ?"                   //5�N�ڎӋ���
                + " ,SONOTA5 = ?"                   //5�N�ڂ��̑�
//2006/06/15 �c�@�ǉ���������  6�N�ڂ̌����o���ǉ�����
                + " ,KEIHI6 = ?"                    //6�N�ڌ����o��
                + " ,BIHINHI6 = ?"                  //6�N�ڐݔ����i��
                + " ,SHOMOHINHI6 = ?"               //6�N�ڏ��Օi��
                + " ,KOKUNAIRYOHI6 = ?"             //6�N�ڍ�������
                + " ,GAIKOKURYOHI6 = ?"             //6�N�ڊO������
                + " ,RYOHI6 = ?"                    //6�N�ڗ���
                + " ,SHAKIN6 = ?"                   //6�N�ڎӋ���
                + " ,SONOTA6 = ?"                   //6�N�ڂ��̑�
//2006/06/15 �c�@�ǉ������܂�                     
                + " ,KEIHI_TOTAL = ?"               //���v-�����o��
                + " ,BIHINHI_TOTAL = ?"             //���v-�ݔ����i��
                + " ,SHOMOHINHI_TOTAL = ?"          //���v-���Օi��
                + " ,KOKUNAIRYOHI_TOTAL = ?"        //���v-��������
                + " ,GAIKOKURYOHI_TOTAL = ?"        //���v-�O������
                + " ,RYOHI_TOTAL = ?"               //���v-����
                + " ,SHAKIN_TOTAL = ?"              //���v-�Ӌ���
                + " ,SONOTA_TOTAL = ?"              //���v-���̑�
                + " ,SOSHIKI_KEITAI_NO = ?"         //�����g�D�̌`�Ԕԍ�
                + " ,SOSHIKI_KEITAI = ?"            //�����g�D�̌`��
                + " ,BUNTANKIN_FLG = ?"             //���S���̗L��
                + " ,KOYOHI = ?"                    //�����x���Ҍٗp�o��
                + " ,KENKYU_NINZU = ?"              //�����Ґ�
                + " ,TAKIKAN_NINZU = ?"             //���@�ւ̕��S�Ґ�
                //2005/03/31 �ǉ� ---------------------------------------��������
                //���R �������͎ғ��͗����ǉ����ꂽ����
                + " ,KYORYOKUSHA_NINZU = ?"         //�������͎Ґ�
                //2005/03/31 �ǉ� ---------------------------------------�����܂�
                + " ,SHINSEI_KUBUN = ?"             //�V�K�p���敪
                + " ,KADAI_NO_KEIZOKU = ?"          //�p�����̌����ۑ�ԍ�
                + " ,SHINSEI_FLG_NO = ?"            //�����v��ŏI�N�x�O�N�x�̉���
                + " ,SHINSEI_FLG = ?"               //�\���̗L��
                + " ,KADAI_NO_SAISYU = ?"           //�ŏI�N�x�ۑ�ԍ�
                + " ,KAIJIKIBO_FLG_NO = ?"          //�J����]�̗L���ԍ�
                + " ,KAIJIKIBO_FLG = ?"             //�J����]�̗L��
                + " ,KAIGAIBUNYA_CD = ?"            //�C�O����R�[�h
                + " ,KAIGAIBUNYA_NAME = ?"          //�C�O���얼��
                + " ,KAIGAIBUNYA_NAME_RYAKU = ?"    //�C�O���엪��
                + " ,KANREN_SHIMEI1 = ?"            //�֘A����̌�����-����1
                + " ,KANREN_KIKAN1 = ?"             //�֘A����̌�����-�����@��1
                + " ,KANREN_BUKYOKU1 = ?"           //�֘A����̌�����-��������1
                + " ,KANREN_SHOKU1 = ?"             //�֘A����̌�����-�E��1
                + " ,KANREN_SENMON1 = ?"            //�֘A����̌�����-��啪��1
                + " ,KANREN_TEL1 = ?"               //�֘A����̌�����-�Ζ���d�b�ԍ�1
                + " ,KANREN_JITAKUTEL1 = ?"         //�֘A����̌�����-����d�b�ԍ�1              
                + " ,KANREN_MAIL1 = ?"              //�֘A����̌�����-E-mail1
                + " ,KANREN_SHIMEI2 = ?"            //�֘A����̌�����-����2
                + " ,KANREN_KIKAN2 = ?"             //�֘A����̌�����-�����@��2
                + " ,KANREN_BUKYOKU2 = ?"           //�֘A����̌�����-��������2
                + " ,KANREN_SHOKU2 = ?"             //�֘A����̌�����-�E��2
                + " ,KANREN_SENMON2 = ?"            //�֘A����̌�����-��啪��2
                + " ,KANREN_TEL2 = ?"               //�֘A����̌�����-�Ζ���d�b�ԍ�2
                + " ,KANREN_JITAKUTEL2 = ?"         //�֘A����̌�����-����d�b�ԍ�2              
                + " ,KANREN_MAIL2 = ?"              //�֘A����̌�����-E-mail2
                + " ,KANREN_SHIMEI3 = ?"            //�֘A����̌�����-����3
                + " ,KANREN_KIKAN3 = ?"             //�֘A����̌�����-�����@��3
                + " ,KANREN_BUKYOKU3 = ?"           //�֘A����̌�����-��������3
                + " ,KANREN_SHOKU3 = ?"             //�֘A����̌�����-�E��3
                + " ,KANREN_SENMON3 = ?"            //�֘A����̌�����-��啪��3
                + " ,KANREN_TEL3 = ?"               //�֘A����̌�����-�Ζ���d�b�ԍ�3
                + " ,KANREN_JITAKUTEL3 = ?"         //�֘A����̌�����-����d�b�ԍ�3              
                + " ,KANREN_MAIL3 = ?"              //�֘A����̌�����-E-mail3
                + " ,XML_PATH = ?"                  //XML�̊i�[�p�X
                + " ,PDF_PATH = ?"                  //PDF�̊i�[�p�X
                + " ,JURI_KEKKA = ?"                //�󗝌���
                + " ,JURI_BIKO = ?"                 //�󗝌��ʔ��l
                //�����ԍ��ǉ�
                + " ,JURI_SEIRI_NO = ?"             //�󗝐����ԍ�
                + " ,SUISENSHO_PATH = ?"            //���E���̊i�[�p�X
                + " ,KEKKA1_ABC = ?"                //�P���R������(ABC)
                + " ,KEKKA1_TEN = ?"                //�P���R������(�_��)
                + " ,KEKKA1_TEN_SORTED = ?"         //�P���R������(�_����)
                + " ,SHINSA1_BIKO = ?"              //�P���R�����l
                + " ,KEKKA2 = ?"                    //�Q���R������
                + " ,SOU_KEHI = ?"                  //���o��i�w�U���́j
                + " ,SHONEN_KEHI = ?"               //���N�x�o��i�w�U���́j
                + " ,SHINSA2_BIKO = ?"              //�Ɩ��S���ҋL����
                + " ,JOKYO_ID = ?"                  //�\����ID
                + " ,SAISHINSEI_FLG = ?"            //�Đ\���t���O
                + " ,DEL_FLG = ?"                   //�폜�t���O
// 20050530 Start
                + " ,KENKYU_KUBUN = ?"              //�����敪
                + " ,OHABAHENKO = ?"                //�啝�ȕύX�t���O
                + " ,RYOIKI_NO = ?"                 //�̈�ԍ�
                + " ,RYOIKI_RYAKU = ?"              //�̈旪��
                + " ,KOMOKU_NO = ?"                 //���ڔԍ�
                + " ,CHOSEIHAN = ?"                 //�����ǃt���O
// Horikoshi End

// 20050712 �L�[���[�h���ǉ��̂���
                + ", KEYWORD_CD = ?"                //�L��
                + ", SAIMOKU_KEYWORD = ?"           //�זڕ\�L�[���[�h
                + ", OTHER_KEYWORD = ?"             //�זڕ\�ȊO�̃L�[���[�h
// Horikoshi

// 20050803 ��c��A�����@�ǉ��̂���
                + " ,KAIGIHI = ?"                   //��c��
                + " ,INSATSUHI = ?"                 //�����
// Horikoshi
                
//2006/02/09
                + " ,SAIYO_DATE = ?"                //�̗p�N����
                + " ,KINMU_HOUR = ?"                //�Ζ����Ԑ�
                + " ,NAIYAKUGAKU = ?"               //���ʌ�������������z
//2007/02/02 �c�@�ǉ���������
                + " ,SHOREIHI_NO_NENDO = ?"         //���ʌ����������ۑ�ԍ�-�N�x
                + " ,SHOREIHI_NO_SEIRI = ?"         //���ʌ����������ۑ�ԍ�-�����ԍ�
//2007/02/02�@�c�@�ǉ������܂�                
//2006/02/13 Start
                + " ,OUBO_SHIKAKU = ?"              //���厑�i
                + " ,SIKAKU_DATE = ?"               //���i�擾�N����
                + " ,SYUTOKUMAE_KIKAN = ?"          //���i�擾�O�@�֖�
                + " ,IKUKYU_START_DATE = ?"         //��x���J�n��
                + " ,IKUKYU_END_DATE = ?"           //��x���I����
//Nae End
                + " ,SHINSARYOIKI_CD = ?"           //�R���̈�R�[�h
                + " ,SHINSARYOIKI_NAME = ?"         //�R���̈於��
                
// ADD START 2007-07-26 BIS ���u��
                + " ,NAIYAKUGAKU1 = ?"				//1�N�ړ���z
                + " ,NAIYAKUGAKU2 = ?"				//2�N�ړ���z
                + " ,NAIYAKUGAKU3 = ?"				//3�N�ړ���z
                + " ,NAIYAKUGAKU4 = ?"				//4�N�ړ���z
                + " ,NAIYAKUGAKU5 = ?"				//5�N�ړ���z
// ADD END 2007-07-26 BIS ���u��

                + " WHERE"
                + " SYSTEM_NO = ?";                 //�V�X�e����t�ԍ�
        PreparedStatement preparedStatement = null;
        try {
            //�o�^
            preparedStatement = connection.prepareStatement(query);
            int index = this.setAllParameter(preparedStatement, updateInfo);
            DatabaseUtil.setParameter(preparedStatement,index++, updateInfo.getSystemNo()); //�V�X�e����t�ԍ�                  
            DatabaseUtil.executeUpdate(preparedStatement);
        } catch (SQLException ex) {
            log.error("�\�����X�V���ɗ�O���������܂����B ", ex);
            throw new DataAccessException("�\�����X�V���ɗ�O���������܂����B ", ex);
        } finally {
            DatabaseUtil.closeResource(null, preparedStatement);
        }
    }

    /**
     * �\����ID�݂̂��w�肳�ꂽ�X�e�[�^�X�֍X�V����B
     * @param connection
     * @param pkInfo
     * @param status
     * @throws DataAccessException
     * @throws NoDataFoundException
     */
    public void updateStatus(
            Connection connection,
            ShinseiDataPk pkInfo,
            String status)
            throws DataAccessException, NoDataFoundException {

        //�Q�Ɖ\�\���f�[�^���`�F�b�N
        checkOwnShinseiData(connection, pkInfo);
        
        String query =
            "UPDATE SHINSEIDATAKANRI"+dbLink
                + " SET"
                + " JOKYO_ID = ?"           //�\����ID                
                + " WHERE"
                + " SYSTEM_NO = ?";

        PreparedStatement preparedStatement = null;
        try {
            //�o�^
            preparedStatement = connection.prepareStatement(query);
            int index = 1;
            DatabaseUtil.setParameter(preparedStatement,index++,status);
            DatabaseUtil.setParameter(preparedStatement,index++,pkInfo.getSystemNo());  //�V�X�e����t�ԍ�
            DatabaseUtil.executeUpdate(preparedStatement);
        } catch (SQLException ex) {
            throw new DataAccessException("�\�����X�e�[�^�X�X�V���ɗ�O���������܂����B ", ex);
        } finally {
            DatabaseUtil.closeResource(null, preparedStatement);
        }
    }

    /**
     * �}�X�^��荞�ݗp�\���f�[�^�X�V���\�b�h�B
     * �\����ID�̃X�e�[�^�X�ւƏ㏑�������X�V����B
     * @param connection
     * @param updateInfo
     * @param status
     * @throws DataAccessException
     * @throws NoDataFoundException
     */
    public void updateTorikomiShinseiDataInfo(
            Connection connection,
            ShinseiDataInfo updateInfo,
            String status)
            throws DataAccessException, NoDataFoundException {

        //�Q�Ɖ\�\���f�[�^���`�F�b�N
        checkOwnShinseiData(connection, updateInfo);
        
        String query =
            "UPDATE SHINSEIDATAKANRI"+dbLink
                + " SET"
                + " JOKYO_ID = ?"                   //�\����ID
                + " ,KADAI_NAME_KANJI = ?"          //�����ۑ薼(�a���j
                + " ,NAME_KANJI_SEI = ?"            //�\���Ҏ����i������-���j
                + " ,NAME_KANJI_MEI = ?"            //�\���Ҏ����i������-���j
                + " ,BUKYOKU_CD = ?"                //���ǃR�[�h
                + " ,SHOKUSHU_CD = ?"               //�E���R�[�h
                + " ,KAIGAIBUNYA_CD = ?"            //�C�O����R�[�h
                + " ,SHINSEI_FLG_NO = ?"            //�����v��ŏI�N�x�O�N�x�̉���
                + " ,BUNTANKIN_FLG = ?"             //���S���̗L��
                + " WHERE"
                + " SYSTEM_NO = ?";

        PreparedStatement preparedStatement = null;
        try {
            //�o�^
            preparedStatement = connection.prepareStatement(query);
            int index = 1;
            DatabaseUtil.setParameter(preparedStatement,index++, status);
            DatabaseUtil.setParameter(preparedStatement,index++, updateInfo.getKadaiInfo().getKadaiNameKanji());
            DatabaseUtil.setParameter(preparedStatement,index++, updateInfo.getDaihyouInfo().getNameKanjiSei());
            DatabaseUtil.setParameter(preparedStatement,index++, updateInfo.getDaihyouInfo().getNameKanjiMei());
            DatabaseUtil.setParameter(preparedStatement,index++, updateInfo.getDaihyouInfo().getBukyokuCd());
            DatabaseUtil.setParameter(preparedStatement,index++, updateInfo.getDaihyouInfo().getShokushuCd());
            DatabaseUtil.setParameter(preparedStatement,index++, updateInfo.getKaigaibunyaCd());
            DatabaseUtil.setParameter(preparedStatement,index++, updateInfo.getShinseiFlgNo());
            DatabaseUtil.setParameter(preparedStatement,index++, updateInfo.getBuntankinFlg());
            DatabaseUtil.setParameter(preparedStatement,index++, updateInfo.getSystemNo()); //�V�X�e����t�ԍ�
            DatabaseUtil.executeUpdate(preparedStatement);
        } catch (SQLException ex) {
            throw new DataAccessException("�\�����X�e�[�^�X�X�V���ɗ�O���������܂����B ", ex);
        } finally {
            DatabaseUtil.closeResource(null, preparedStatement);
        }
    }

    /**
     * �\����ID�ƍĐ\���t���O���w�肳�ꂽ�l�֍X�V����B    
     * @param connection
     * @param pkInfo
     * @param status
     * @param saishinseiFlg
     * @throws DataAccessException
     * @throws NoDataFoundException
     */
    public void updateStatus(
            Connection connection,
            ShinseiDataPk pkInfo,
            String status,
            String saishinseiFlg)
            throws DataAccessException, NoDataFoundException {

        //�Q�Ɖ\�\���f�[�^���`�F�b�N
        checkOwnShinseiData(connection, pkInfo);
        
        String query =
            "UPDATE SHINSEIDATAKANRI"+dbLink
                + " SET"
                + " JOKYO_ID = ?"           //�\����ID                
                + ",SAISHINSEI_FLG = ?"     //�Đ\���t���O
                + " WHERE"
                + " SYSTEM_NO = ?";

        PreparedStatement preparedStatement = null;
        try {
            //�o�^
            preparedStatement = connection.prepareStatement(query);
            int index = 1;
            DatabaseUtil.setParameter(preparedStatement,index++,status);
            DatabaseUtil.setParameter(preparedStatement,index++,saishinseiFlg);
            DatabaseUtil.setParameter(preparedStatement,index++,pkInfo.getSystemNo());  //�V�X�e����t�ԍ�
            DatabaseUtil.executeUpdate(preparedStatement);
        } catch (SQLException ex) {
            throw new DataAccessException("�\�����X�e�[�^�X�X�V���ɗ�O���������܂����B ", ex);
        } finally {
            DatabaseUtil.closeResource(null, preparedStatement);
        }
    }   

    /**
     * �\�������폜����B(�폜�t���O) 
     * @param connection            �R�l�N�V����
     * @param pkInfo                �폜����\���f�[�^��L�[���
     * @throws DataAccessException  �폜���ɗ�O�����������ꍇ
     * @throws NoDataFoundException �Ώۃf�[�^��������Ȃ��ꍇ
     */
    public void deleteFlagShinseiDataInfo(
            Connection connection,
            ShinseiDataPk pkInfo)
            throws DataAccessException, NoDataFoundException {

        //�Q�Ɖ\�\���f�[�^���`�F�b�N
        checkOwnShinseiData(connection, pkInfo);
        
        String query =
            "UPDATE SHINSEIDATAKANRI"+dbLink
                + " SET"
                + " DEL_FLG = 1"    //�폜�t���O             
                + " WHERE"
                + " SYSTEM_NO = ?";

        PreparedStatement preparedStatement = null;
        try {
            //�o�^
            preparedStatement = connection.prepareStatement(query);
            int index = 1;
            DatabaseUtil.setParameter(preparedStatement,index++,pkInfo.getSystemNo());  //�V�X�e����t�ԍ�
            DatabaseUtil.executeUpdate(preparedStatement);
        } catch (SQLException ex) {
            throw new DataAccessException("�\�����폜���ɗ�O���������܂����B ", ex);
        } finally {
            DatabaseUtil.closeResource(null, preparedStatement);
        }
    }

    /**
     * �\�������폜����B(�폜�t���O) 
     * ����ID�ɕR�Â��\���f�[�^�S�Ăɑ΂��č폜�t���O�𗧂Ă�B
     * @param connection            �R�l�N�V����
     * @param pkInfo                �폜���鎖�ƃf�[�^��L�[���
     * @throws DataAccessException  �폜���ɗ�O�����������ꍇ
     */
    public void deleteFlagShinseiDataInfo(
            Connection connection,
            JigyoKanriPk pkInfo)
            throws DataAccessException {

        String query =
            "UPDATE SHINSEIDATAKANRI"+dbLink
                + " SET"
                + " DEL_FLG = 1"    //�폜�t���O             
                + " WHERE"
                + " JIGYO_ID = ?";

        PreparedStatement preparedStatement = null;
        try {
            //�o�^
            preparedStatement = connection.prepareStatement(query);
            int index = 1;
            DatabaseUtil.setParameter(preparedStatement,index++,pkInfo.getJigyoId());   //����ID
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new DataAccessException("�\�����폜���ɗ�O���������܂����B ", ex);
        } finally {
            DatabaseUtil.closeResource(null, preparedStatement);
        }
    }

    /**
     * �\�������폜����B(�����폜) 
     * ��{�I�Ɏg�p���Ȃ��B
     * @param connection            �R�l�N�V����
     * @param pkInfo                �폜����\���f�[�^��L�[���
     * @throws DataAccessException  �폜���ɗ�O�����������ꍇ
     * @throws NoDataFoundException �Ώۃf�[�^��������Ȃ��ꍇ
     */
    public void deleteShinseiDataInfo(
            Connection connection,
            ShinseiDataPk pkInfo)
            throws DataAccessException, NoDataFoundException {

        //�Q�Ɖ\�\���f�[�^���`�F�b�N
        checkOwnShinseiData(connection, pkInfo);
        
        String query =
            "DELETE FROM SHINSEIDATAKANRI"+dbLink
                + " WHERE"
                + " SYSTEM_NO = ?";

        PreparedStatement preparedStatement = null;
        try {
            //�o�^
            preparedStatement = connection.prepareStatement(query);
            int index = 1;
            DatabaseUtil.setParameter(preparedStatement,index++,pkInfo.getSystemNo());  //�V�X�e����t�ԍ�
            DatabaseUtil.executeUpdate(preparedStatement);
        } catch (SQLException ex) {
            throw new DataAccessException("�\�����폜���i�����폜�j�ɗ�O���������܂����B ", ex);
        } finally {
            DatabaseUtil.closeResource(null, preparedStatement);
        }
    }

    /**
     * �\�������폜����B(�����폜) 
     * ����ID�ɕR�Â��\���f�[�^��S�č폜����B
     * �Y���f�[�^�����݂��Ȃ������ꍇ�͉����������Ȃ��B
     * @param connection            �R�l�N�V����
     * @param jigyoId               ���������i����ID�j
     * @throws DataAccessException  �폜���ɗ�O�����������ꍇ
     */
    public void deleteShinseiDataInfo(
            Connection connection,
            String jigyoId)
            throws DataAccessException {

        String query =
            "DELETE FROM SHINSEIDATAKANRI"+dbLink
                + " WHERE"
                + " JIGYO_ID = ?";

        PreparedStatement preparedStatement = null;
        try {
            //�o�^
            preparedStatement = connection.prepareStatement(query);
            int index = 1;
            DatabaseUtil.setParameter(preparedStatement,index++,jigyoId);   //���������i����ID�j
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new DataAccessException("�\�����폜���i�����폜�j�ɗ�O���������܂����B ", ex);
        } finally {
            DatabaseUtil.closeResource(null, preparedStatement);
        }
    }   

//2005/08/16 takano �d���`�F�b�N�̃��W�b�N�œK���ɂ��폜 �������� ---
//������̓R�����g�A�E�g�ɂ��܂������A�ŏI�I�ɂ̓\�[�X�ォ��폜���Ă�������������������ėǂ��Ǝv���܂��B
//  /**
//   * �d���\�����R�[�h����Ԃ��B
//   * �����_�i2004/08/04�j�Ŗ������̏�ԁB
//   * 
//   * @param connection
//   * @param shinseiDataInfo
//   * @return
//   * @throws DataAccessException
//   * @throws ApplicationException
//   */
//  public int countDuplicateApplication(
//      Connection connection, 
//      ShinseiDataInfo shinseiDataInfo)
//      throws DataAccessException, ApplicationException
//  {
//
//      //2005/04/05 �ǉ� ------------------------------------��������
//      //���R �d���\���`�F�b�N�����̂���
//      //�d���`�F�b�N�͊�Ղ̏ꍇ�̂ݍs��
//      KadaiInfo kadaiInfo = shinseiDataInfo.getKadaiInfo();
//      if(kadaiInfo!=null&&IJigyoKubun.JIGYO_KUBUN_KIBAN.equals(kadaiInfo.getJigyoKubun())){
//          String jigyoCD=shinseiDataInfo.getJigyoCd();
//          
//          List dupliCheckJigyoCDList=(List)dupliCheckJigyoCDMap.get(jigyoCD);
//          if(dupliCheckJigyoCDList==null||dupliCheckJigyoCDList.size()==0){
//              return 0;
//          }
//          
//          String sql= "SELECT " +
//                      "   COUNT(*) CNT "+
//                      "FROM " +
//                      "   SHINSEIDATAKANRI SDK "+
//                      "       INNER JOIN JIGYOKANRI JK "+
//                      "       ON JK.JIGYO_ID= SDK.JIGYO_ID "+
//                      "       AND JK.NENDO = ";
//          
//          StringBuffer query=new StringBuffer(sql);
//          query.append(EscapeUtil.toSqlString(shinseiDataInfo.getNendo()))
//                      .append(" AND JK.JIGYO_KUBUN = 4 ")
//          //2005/04/26 �ǉ� ��������------------------------------------------------------
//          //�d���`�F�b�N�ɉ񐔂̍i���݂�ǉ�
//                      .append(" AND JK.KAISU = SDK.KAISU ")
//          //�ǉ� �����܂�-----------------------------------------------------------------
//                      .append("WHERE ")
//                      .append("SDK.SHINSEISHA_ID = '")
//                      .append(EscapeUtil.toSqlString(shinseiDataInfo.getShinseishaId()))
//                      .append("' AND SUBSTR(JK.JIGYO_ID,3,5) IN (")
//                      .append(changeIterator2CSV(dupliCheckJigyoCDList.iterator()))
//                      .append(")")
//          //2005/04/26 �ǉ� ��������------------------------------------------------------
//          //�d���`�F�b�N�ɍ폜�t���O�̃`�F�b�N�Ɖ񐔂̍i���݂�ǉ�
//                      .append(" AND SDK.DEL_FLG = 0")
//                      .append(" AND SDK.KAISU = ")
//                      .append(EscapeUtil.toSqlString(shinseiDataInfo.getKaisu()));
//          //�ǉ� �����܂�-----------------------------------------------------------------
//
//// 20050809 �d���`�F�b�N�̏����ɏ�ID��ǉ�
//          query.append(" AND SDK.JOKYO_ID >= '02'")
//              .append(" AND SDK.JOKYO_ID <> '05'")
//              ;
//// Horikoshi
//          //2005.08.10 iso �C�����Ɏ������g���d���`�F�b�N����͂����悤�ɕύX
//          if(shinseiDataInfo.getSystemNo() != null || shinseiDataInfo.getSystemNo().length() > 0) {
//          query.append(" AND SDK.SYSTEM_NO <> '")
//               .append(shinseiDataInfo.getSystemNo())
//               .append("'")
//               ;
//         }
//
//          //for debug
//          if(log.isDebugEnabled()){
//              log.debug("query:" + query);
//          }
//          
//          PreparedStatement preparedStatement = null;
//          ResultSet         resultSet         = null;
//          
//          int retValue=0;
//          try {
//              //�o�^
//              preparedStatement = connection.prepareStatement(query.toString());
//              resultSet = preparedStatement.executeQuery();
//              
//              if(resultSet.next()){
//                  Object obj=resultSet.getObject("CNT");
//                  if(obj!=null){
//                      retValue=Integer.parseInt(obj.toString());
//                  }
//              }
//          } catch (SQLException ex) {
//              throw new DataAccessException("�\�����r�����䒆�ɗ�O���������܂����B ", ex);
//          } finally {
//              DatabaseUtil.closeResource(resultSet, preparedStatement);
//          }
//          
//          return retValue;
//      }
//      //2005/04/05 �ǉ� ------------------------------------�����܂�
//
//// 20050713 20050617 ����̈�d���\���`�F�b�N��ǉ�
//      else if(IJigyoKubun.JIGYO_KUBUN_TOKUTEI.equals(kadaiInfo.getJigyoKubun()) ||
//              IJigyoKubun.JIGYO_KUBUN_GAKUSOU_HIKOUBO.equals(kadaiInfo.getJigyoKubun()) ||
//              IJigyoKubun.JIGYO_KUBUN_GAKUSOU_KOUBO.equals(kadaiInfo.getJigyoKubun()) ||
//              IJigyoKubun.JIGYO_KUBUN_TOKUSUI.equals(kadaiInfo.getJigyoKubun())
//              ){
//
//          String strQuery = "";
//          StringBuffer sbSQL= new StringBuffer(strQuery);
//          if(IJigyoKubun.JIGYO_KUBUN_TOKUTEI.equals(kadaiInfo.getJigyoKubun())){
//              sbSQL = selectDupTokutei(shinseiDataInfo);
//          }
//          else if(IJigyoKubun.JIGYO_KUBUN_GAKUSOU_HIKOUBO.equals(kadaiInfo.getJigyoKubun())){
//              sbSQL = selectDupGakuHikoubo(shinseiDataInfo);
//          }
//          else if(IJigyoKubun.JIGYO_KUBUN_GAKUSOU_KOUBO.equals(kadaiInfo.getJigyoKubun())){
//              sbSQL = selectDupGakuKoubo(shinseiDataInfo);
//          }
//          else if(IJigyoKubun.JIGYO_KUBUN_TOKUSUI.equals(kadaiInfo.getJigyoKubun())){
//              sbSQL = selectDupTokusui(shinseiDataInfo);
//          }
//
//          //�����̎��s
//          int retValue=0;
//          if(sbSQL != null){
//              if(log.isDebugEnabled()){log.debug("query:" + sbSQL.toString());}
//              PreparedStatement preparedStatement = null;
//              ResultSet         resultSet         = null;
//              try {
//                  //�o�^
//                  preparedStatement = connection.prepareStatement(sbSQL.toString());
//                  resultSet = preparedStatement.executeQuery();
//                  if(resultSet.next()){
//                      Object obj=resultSet.getObject(IShinseiMaintenance.STR_COUNT);
//                      if(obj!=null){
//                          retValue=Integer.parseInt(obj.toString());
//                      }
//                  }
//              } catch (SQLException ex) {
//                  throw new DataAccessException("�\�����r�����䒆�ɗ�O���������܂����B ", ex);
//              } finally {
//                  DatabaseUtil.closeResource(resultSet, preparedStatement);
//              }
//          }
//
//          return retValue;
//      }
//// Horikoshi
//
//      return 0;
//  }
//2005/08/16 takano �d���`�F�b�N�̃��W�b�N�œK���ɂ��폜 �����܂� ---

//2005/08/16 takano �d���`�F�b�N�̃��W�b�N�œK���ɂ��ǉ� �������� ---
    /**
     * �d���\�����R�[�h����Ԃ��B
     * 
     * @param connection
     * @param shinseiDataInfo
     * @return
     * @throws DataAccessException
     * @throws ApplicationException
     */
    public int countDuplicateApplication(
            Connection connection, 
            ShinseiDataInfo shinseiDataInfo)
            throws DataAccessException, ApplicationException {

        //���Y���ƃR�[�h�̏d���Ώێ��ƃR�[�h���X�g���擾����
        List dupliCheckJigyoCDList = (List)dupliCheckJigyoCDMap.get(shinseiDataInfo.getJigyoCd());
        if(dupliCheckJigyoCDList == null || dupliCheckJigyoCDList.size() == 0){
            //�d���Ώێ��ƃ��X�g�����݂��Ȃ��ꍇ�́A�d���`�F�b�N�����Ɣ��f����
            return 0;
        }

        //SQL�i���ʕ����j
        String sql = "SELECT COUNT(SYSTEM_NO) AS CNT FROM SHINSEIDATAKANRI "
                   + "WHERE "
                   + " KENKYU_NO = ? "                  //���������҂��i�ϐ��j
                   + "AND "
                   + " SUBSTR(JIGYO_ID,1,2) = ? "       //�����N�x�Łi�ϐ��j
                   + "AND "
                   + " SUBSTR(JIGYO_ID,3,5) IN ("       //�d���Ώێ��ƃR�[�h�Łi�ϐ��j
                   + StringUtil.getQuestionMark(dupliCheckJigyoCDList.size())
                   + ") "
                   + "AND "
                   + " SUBSTR(JIGYO_ID,8,1) = ? "       //�����񐔂Łi�ϐ��j
                   + "AND "
                   + " SYSTEM_NO <> ? "                 //�������g�i�����V�X�e����t�ԍ��j�ȊO�Łi�ϐ��j
                   + "AND "
                   + " JOKYO_ID >= '02' "               //�\�����m�F[02]�ȏ�Łi�Œ�j
                   + "AND "
                   + " JOKYO_ID <> '05' "               //�����@�֋p��[05]�ȊO�Łi�Œ�j
                   + "AND "
                   + " DEL_FLG = 0 "                    //�폜����Ă��Ȃ����́i�Œ�j
                   ;
        
        StringBuffer query = new StringBuffer(sql);
        
        //-----����̈�̏ꍇ�͌���������ǉ�-----
        if(IJigyoKubun.JIGYO_KUBUN_TOKUTEI.equals(shinseiDataInfo.getKadaiInfo().getJigyoKubun())){
            query.append(makeQuery4DupTokutei(shinseiDataInfo));
        }
        //2005.10.27 iso [�O�N�x���傠��E�V�K]��[�O�N�x����Ȃ��E�p��]�̏d�����������������ǉ�
        //�����̂ݏ����ǉ�
        else if(IJigyoKubun.JIGYO_KUBUN_TOKUSUI.equals(shinseiDataInfo.getKadaiInfo().getJigyoKubun())){
            query.append(makeQuery4DupZennenShinki(shinseiDataInfo));
        }
        
        if(log.isDebugEnabled()){
            log.debug("query:" + query);
        }
        
        int retValue=0;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement(query.toString());
            int index = 1;
            DatabaseUtil.setParameter(ps, index++, userInfo.getShinseishaInfo().getKenkyuNo());
            DatabaseUtil.setParameter(ps, index++, shinseiDataInfo.getJigyoId().substring(0,2));
            for(int i=0; i<dupliCheckJigyoCDList.size(); i++){
                DatabaseUtil.setParameter(ps, index++, (String)dupliCheckJigyoCDList.get(i));
            }
            DatabaseUtil.setParameter(ps, index++, shinseiDataInfo.getJigyoId().substring(7,8));
            DatabaseUtil.setParameter(ps, index++, shinseiDataInfo.getSystemNo());
            rs = ps.executeQuery();
            if(rs.next()){
                Object obj=rs.getObject("CNT");
                if(obj!=null){
                    retValue=Integer.parseInt(obj.toString());
                }
            }
        } catch (SQLException ex) {
            throw new DataAccessException("�\����񌟍����ɗ�O���������܂����B ", ex);
        } finally {
            DatabaseUtil.closeResource(rs, ps);
        }
        return retValue;
    }
//2005/08/16 takano �d���`�F�b�N�̃��W�b�N�œK���ɂ��ǉ� �����܂� ---

    /**
     * �󗝓o�^���̏d���\�����R�[�h����Ԃ��B
     * �����_�i2004/08/04�j�Ŗ������̏�ԁB
     * 
     * @param connection
     * @param shinseiDataInfo
     * @return
     * @throws DataAccessException
     * @throws ApplicationException
     */
    public int countDuplicateApplicationForJuri(
        Connection connection, 
        ShinseiDataInfo shinseiDataInfo)
        throws DataAccessException, ApplicationException {
        //TODO takano �������̏�ԁi�d���\���`�F�b�N�j�B
        return 0;
    }
    
    /**
     * �w�茟�������ɊY������\�����f�[�^���擾����B
     * @param connection
     * @param searchInfo
     * @return
     * @throws DataAccessException
     * @throws NoDataFoundException
     * @throws ApplicationException  �\���ҏ�񂪃Z�b�g����Ă��Ȃ������ꍇ
     */
    public Page searchApplication(
            Connection connection,
            ShinseiSearchInfo searchInfo)
            throws DataAccessException , NoDataFoundException, ApplicationException {

        String select =
        "SELECT "
            + " A.SYSTEM_NO,"                   //�V�X�e����t�ԍ�
            + " A.UKETUKE_NO,"                  //�\���ԍ�
            + " A.JIGYO_ID,"                    //����ID
            + " A.NENDO,"                       //�N�x
            + " A.KAISU,"                       //��
            + " A.JIGYO_NAME,"                  //���Ɩ�
            + " A.SHINSEISHA_ID,"               //�\����ID
            + " A.SAKUSEI_DATE,"                //�\�����쐬��
            + " A.SHONIN_DATE,"                 //�����@�֏��F��
            + " A.NAME_KANJI_SEI,"              //�\���Ҏ����i������-���j
            + " A.NAME_KANJI_MEI,"              //�\���Ҏ����i������-���j
            + " A.KENKYU_NO,"                   //�\���Ҍ����Ҕԍ�
            + " A.SHOZOKU_CD,"                  //�����@�փR�[�h
            + " A.SHOZOKU_NAME,"                //�����@�֖�
            + " A.SHOZOKU_NAME_RYAKU,"          //�����@�֖��i���́j
            + " A.BUKYOKU_NAME,"                //���ǖ�
            + " A.BUKYOKU_NAME_RYAKU,"          //���ǖ��i���́j
            + " A.SHOKUSHU_NAME_KANJI,"         //�E��
            + " A.SHOKUSHU_NAME_RYAKU,"         //�E���i���́j            
            + " A.KADAI_NAME_KANJI,"            //�����ۑ薼(�a���j
            + " A.JIGYO_KUBUN,"                 //���Ƌ敪
            + " A.KEKKA1_ABC,"                  //1���R������(ABC)
            + " A.KEKKA1_TEN,"                  //1���R������(�_��)
            + " A.KEKKA1_TEN_SORTED,"           //1���R������(�_����)
            + " A.KEKKA2,"                      //2���R������
            + " A.JOKYO_ID,"                    //�\����ID
            + " A.SAISHINSEI_FLG,"              //�Đ\���t���O
            + " A.KEI_NAME_RYAKU,"              //�n���̋敪�i���́j
            + " A.KANTEN_RYAKU,"                //���E�̊ϓ_�i���́j
            + " A.NENREI,"                      //�N��
//2007/2/25 �����F�@�ǉ��@��������
            + " A.DEL_FLG,"                     //�폜�t���O
//2007/2/25 �����F�@�ǉ��@�����܂�

// 20050622
            + " CHCKLIST.KAKUTEI_DATE,"         //�m���
// Horikoshi
// 20050711 �R�����g�m�F�@�\�ǉ��̂���
            + " A.JURI_BIKO,"                   //�󗝔��l
// Horikoshi
            + " A.JURI_SEIRI_NO,"               //�󗝐����ԍ� 2005/07/25

//2006/06/23 �����ύX ��������-------------------------------------------------------
        //2005/04/30 �ǉ� ��������-------------------------------------------------------
        //���R �\���󋵈ꗗ�Ŋ�Ղ̊w�U��t���ȍ~�̏ꍇ�͐\�������C���ł��Ȃ��悤�ɂ��邽��
            + "CASE WHEN CHCKLIST.SHOZOKU_CD IS NULL THEN 'TRUE' ELSE 'FALSE' END EDITABLE, "
        //    + "CASE WHEN CHCKLIST.JOKYO_ID = '03' THEN 'FALSE' ELSE 'TRUE' END EDITABLE, "
        //�ǉ� �����܂�------------------------------------------------------------------
//2006/06/23 �����ύX �����܂�------------------------------------------------------- 
            
//2006/06/23 �����ǉ� ��������-------------------------------------------------------
            + "CASE WHEN RYOIKI.RYOIKIKEIKAKUSHO_KAKUTEI_FLG = '1' THEN 'FALSE' ELSE 'TRUE' END RYOIKI_KAKUTEI_FLG, "
            + "RYOIKI.RYOIKIKEIKAKUSHO_KAKUTEI_FLG, "
//2006/06/23 �����ǉ� �����܂�-------------------------------------------------------
//2006/07/07 �����ǉ��@��������-------------------------------------------------------
            + " DECODE (SIGN(TO_DATE"
            + "( TO_CHAR(B.RYOIKI_KAKUTEIKIKAN_END,"
            + "'YYYY/MM/DD'), 'YYYY/MM/DD' )- "
            + "TO_DATE( TO_CHAR(SYSDATE ,'YYYY/MM/DD'), 'YYYY/MM/DD' )),0 ,"
            + "'TRUE',1,'TRUE',-1,'FALSE') RYOIKI_KAKUTEIKIKAN_FLAG ,"
            + " B.RYOIKI_KAKUTEIKIKAN_END ," //�̈��\�Ҋm����ؓ�  
//2006/07/07 �����ǉ� �����܂� -------------------------------------------------------          
            + " DECODE"
            + " ("
            + "  NVL(A.SUISENSHO_PATH,'null') "
            + "  ,'null','FALSE'"               //���E���p�X��NULL�̂Ƃ�
            + "  ,      'TRUE'"                 //���E���p�X��NULL�ȊO�̂Ƃ�
            + " ) SUISENSHO_FLG, "              //���E���o�^�t���O
            + " B.UKETUKEKIKAN_END,"            //�w�U��t�����i�I���j
            + " B.HOKAN_DATE,"                  //�f�[�^�ۊǓ�
            + " B.YUKO_DATE,"                   //�ۊǗL������
            + " DECODE"
            + " ("
            + "  SIGN( "
            + "       TO_DATE( TO_CHAR(B.UKETUKEKIKAN_END,'YYYY/MM/DD'), 'YYYY/MM/DD' ) "
            + "     - TO_DATE( TO_CHAR(SYSDATE           ,'YYYY/MM/DD'), 'YYYY/MM/DD' ) "
            + "      )"
            
            //2006.11.16 iso ���ؓ��������W�I�{�^�����o�Ȃ��o�O���C��
//            + "  ,0 , 'TRUE'"                   //���ݎ����Ɠ����ꍇ
            + "  ,0 , 'FALSE'"                   //���ݎ����Ɠ����ꍇ
            
            + "  ,-1 , 'TRUE'"                   //���ݎ����̕�����t�������O
            + "  ,1, 'FALSE'"                  //���ݎ����̕�����t��������
            + " ) UKETUKE_END_FLAG,"            //�w�U��t�����i�I���j���B�t���O
            + " DECODE"
            + " ("
            + "  NVL(A.PDF_PATH,'null') "
            + "  ,'null','FALSE'"               //PDF�̊i�[�p�X��NULL�̂Ƃ�
            + "  ,      'TRUE'"                 //PDF�̊i�[�p�X��NULL�ȊO�̂Ƃ�
            + " ) PDF_PATH_FLG, "               //PDF�̊i�[�p�X�t���O
            + " DECODE"
            + " ("
            + "  NVL(C.SYSTEM_NO,'null') "
            + "  ,'null','FALSE'"               //�Y�t�t�@�C����NULL�̂Ƃ�
            + "  ,      'TRUE'"                 //�Y�t�t�@�C����NULL�ȊO�̂Ƃ�
            + " ) TENPUFILE_FLG "               //�Y�t�t�@�C���t���O 
            + " FROM"
            + " SHINSEIDATAKANRI"+dbLink+" A"   //�\���f�[�^�Ǘ��e�[�u��
            
            //2005/04/08 �ǉ� ��������-------------------------------------------------------
            //���R ���ǒS���҂̏ꍇ�̏����ǉ��̂���
            + " INNER JOIN JIGYOKANRI"+dbLink+" B"
            +" ON A.JIGYO_ID = B.JIGYO_ID "
            + " LEFT JOIN TENPUFILEINFO"+dbLink+" C "
            +" ON A.SYSTEM_NO = C.SYSTEM_NO "
            +" AND C.TENPU_PATH IS NOT NULL ";
            //���ǒS���҂̒S�����ǊǗ��e�[�u���ɒl������ꍇ�ɏ�����ǉ�����
            if(userInfo.getBukyokutantoInfo() != null && userInfo.getBukyokutantoInfo().getTantoFlg()){
                select = select 
                        + " INNER JOIN TANTOBUKYOKUKANRI T"
                        +" ON T.SHOZOKU_CD = A.SHOZOKU_CD"
                        +" AND T.BUKYOKU_CD = A.BUKYOKU_CD"
                        //2005/04/20�@�ǉ� ��������------------------------------------------
                        //���R �����s���̂��� 
                        + " AND T.BUKYOKUTANTO_ID = '"+EscapeUtil.toSqlString(userInfo.getBukyokutantoInfo().getBukyokutantoId())+"' ";
                        //�ǉ� �����܂�------------------------------------------------------
            }
            //�ǉ� �����܂�------------------------------------------------------------------
            
            //2005/04/30 �ǉ� ��������-------------------------------------------------------
            //���R �\���󋵈ꗗ�Ŋ�Ղ̊w�U��t���ȍ~�̏ꍇ�͐\�������C���ł��Ȃ��悤�ɂ��邽��
            select = select + "LEFT JOIN CHCKLISTINFO CHCKLIST "
                        + "ON CHCKLIST.JIGYO_ID = A.JIGYO_ID "
                        + "AND CHCKLIST.SHOZOKU_CD = A.SHOZOKU_CD "                   
                        + "AND CHCKLIST.JOKYO_ID <> '03' "               
            //2006/06/21 �����@�ǉ� ��������-------------------------------------------------------
                        + "LEFT JOIN RYOIKIKEIKAKUSHOINFO RYOIKI "
                        + "ON RYOIKI.KARIRYOIKI_NO = A.RYOIKI_NO "
                        + "AND RYOIKI.DEL_FLG = 0 ";
            //2006/06/21 �����@�ǉ� �����܂�-------------------------------------------------------
            //�ǉ� �����܂�------------------------------------------------------------------
            
            //2005/04/08 �폜 ��������-------------------------------------------------------
            //���R ���ǒS���҂̏ꍇ�̏����ǉ��̂���   
            //+ " , JIGYOKANRI"+dbLink+" B,"        //���Ə��Ǘ��e�[�u��
            //+ " (SELECT DISTINCT SYSTEM_NO FROM TENPUFILEINFO "
            //+     " WHERE TENPU_PATH IS NOT NULL) C"  //�Y�t�t�@�C���e�[�u���i�Y�t�t�@�C���p�X��null�ł͂Ȃ��ꍇ�̂݁j 
            //�폜 �����܂�------------------------------------------------------------------
            select = select + " WHERE 1=1 ";
//update2004/10/26 �V�X�e���Ǘ��Ҍ����\����񌟍��ւ̑Ή�
//          + " A.DEL_FLG = 0"                  //�폜�t���O��[0]
//          + " AND"
//            + " A.JIGYO_ID = B.JIGYO_ID";       //����ID����������
            //2005/04/11 �폜 
//          + " AND"
//          + " A.SYSTEM_NO = C.SYSTEM_NO(+)"   //�e�[�u���̘A��(C�ɘA������f�[�^���Ȃ��ꍇ���\��)
            //�폜 �����܂�-----------------------------------------------------------
        
        //��������������SQL���𐶐�����B
        String query = getQueryString(select, searchInfo);
        
        //for debug
        if(log.isDebugEnabled()){
            log.debug("query:" + query);
        }
        
        // �y�[�W�擾
        return SelectUtil.selectPageInfo(connection, (SearchInfo)searchInfo, query);
    }

    /**
     * CSV�o�͂��郊�X�g��Ԃ��B
     * @param connection
     * @param searchInfo
     * @return
     * @throws DataAccessException
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public List searchCsvData(
            Connection connection,
            ShinseiSearchInfo searchInfo)
            throws DataAccessException, NoDataFoundException, ApplicationException {

        String select =
        "SELECT "
//          2005/04/22 �ǉ� ��������----------
//          ���R:�\����ID�ǉ��̂���
            + "  A.JOKYO_ID"                //�\����ID
//          2005/04/22 �ǉ� �����܂�----------
            + " ,A.SYSTEM_NO"               //�V�X�e����t�ԍ�
            + " ,A.UKETUKE_NO"              //�\���ԍ�
            + " ,A.JIGYO_ID"                //����ID
            + " ,A.NENDO"                   //�N�x
            + " ,A.KAISU"                   //��
            + " ,A.JIGYO_NAME"              //���Ɩ�
            + " ,A.SHINSEISHA_ID"           //�\����ID
            + " ,TO_CHAR(A.SAKUSEI_DATE, 'YYYY/MM/DD')"         //�\�����쐬��
            + " ,TO_CHAR(A.SHONIN_DATE, 'YYYY/MM/DD')"          //�����@�֏��F��
            //add start ly 2006/08/02
            + " ,TO_CHAR(A.RYOIKI_KAKUTEI_DATE, 'YYYY/MM/DD')"  //�̈��\�Ҋm���
            //add end ly 2006/08/02
            + " ,TO_CHAR(A.JYURI_DATE, 'YYYY/MM/DD')"           //�w�U�󗝓�
            + " ,A.NAME_KANJI_SEI"          //�\���Ҏ����i������-���j
            + " ,A.NAME_KANJI_MEI"          //�\���Ҏ����i������-���j
            + " ,A.NAME_KANA_SEI"           //�\���Ҏ����i�t���K�i-���j
            + " ,A.NAME_KANA_MEI"           //�\���Ҏ����i�t���K�i-���j
            + " ,A.NAME_RO_SEI"             //�\���Ҏ����i���[�}��-���j
            + " ,A.NAME_RO_MEI"             //�\���Ҏ����i���[�}��-���j
            + " ,A.NENREI"                  //�N��
            + " ,A.KENKYU_NO"               //�\���Ҍ����Ҕԍ�
            + " ,A.SHOZOKU_CD"              //�����@�փR�[�h
            + " ,A.SHOZOKU_NAME"            //�����@�֖�
            + " ,A.SHOZOKU_NAME_RYAKU"      //�����@�֖��i���́j
            + " ,A.BUKYOKU_CD"              //���ǃR�[�h
            + " ,A.BUKYOKU_NAME"            //���ǖ�
            + " ,A.BUKYOKU_NAME_RYAKU"      //���ǖ��i���́j
            + " ,A.SHOKUSHU_CD"             //�E���R�[�h
            + " ,A.SHOKUSHU_NAME_KANJI"     //�E���i�a���j
            + " ,A.SHOKUSHU_NAME_RYAKU"     //�E���i���́j
            + " ,A.ZIP"                     //�X�֔ԍ�
            + " ,A.ADDRESS"                 //�Z��
            + " ,A.TEL"                     //TEL
            + " ,A.FAX"                     //FAX
            + " ,A.EMAIL"                   //E-Mail
            + " ,A.KADAI_NAME_KANJI"        //�����ۑ薼(�a���j
            + " ,A.KADAI_NAME_EIGO"         //�����ۑ薼(�p���j
            + " ,A.KEI_NAME_NO"             //�n���̋敪�ԍ�
            + " ,A.KEI_NAME"                //�n���̋敪
            + " ,A.KEI_NAME_RYAKU"          //�n���̋敪����
            + " ,A.BUNKASAIMOKU_CD"         //�זڔԍ�
            + " ,A.BUNYA_NAME"              //����
            + " ,A.BUNKA_NAME"              //����
            + " ,A.SAIMOKU_NAME"            //�ז�
            + " ,A.BUNKASAIMOKU_CD2"        //�זڔԍ�2
            + " ,A.BUNYA_NAME2"             //����2
            + " ,A.BUNKA_NAME2"             //����2
            + " ,A.SAIMOKU_NAME2"           //�ז�2
            + " ,A.KANTEN_NO"               //���E�̊ϓ_�ԍ�
            + " ,A.KANTEN"                  //���E�̊ϓ_
            + " ,A.KANTEN_RYAKU"            //���E�̊ϓ_����
// 20050622
            + " ,A.KENKYU_KUBUN"            //�����敪
            + " ,A.OHABAHENKO"              //�啝�ȕύX
            + " ,A.RYOIKI_NO"               //�̈�ԍ�
            + " ,A.KOMOKU_NO"               //���ڔԍ�
            + " ,A.CHOSEIHAN"               //������
// Horikoshi

// 20050712
            + " ,A.SAIMOKU_KEYWORD"             //�זڕ\�L�[���[�h
            + " ,A.OTHER_KEYWORD"               //�זڕ\�ȊO�̃L�[���[�h
// Horikoshi

            + " ,A.KEIHI1"                  //1�N�ڌ����o��
            + " ,A.BIHINHI1"                //1�N�ڐݔ����i��
            + " ,A.SHOMOHINHI1"             //1�N�ڏ��Օi��
            + " ,A.RYOHI1"                  //1�N�ڗ���
            + " ,A.SHAKIN1"                 //1�N�ڎӋ���
            + " ,A.SONOTA1"                 //1�N�ڂ��̑�
            + " ,A.KEIHI2"                  //2�N�ڌ����o��
            + " ,A.BIHINHI2"                //2�N�ڐݔ����i��
            + " ,A.SHOMOHINHI2"             //2�N�ڏ��Օi��
            + " ,A.RYOHI2"                  //2�N�ڗ���
            + " ,A.SHAKIN2"                 //2�N�ڎӋ���
            + " ,A.SONOTA2"                 //2�N�ڂ��̑�
            + " ,A.KEIHI3"                  //3�N�ڌ����o��
            + " ,A.BIHINHI3"                //3�N�ڐݔ����i��
            + " ,A.SHOMOHINHI3"             //3�N�ڏ��Օi��
            + " ,A.RYOHI3"                  //3�N�ڗ���
            + " ,A.SHAKIN3"                 //3�N�ڎӋ���
            + " ,A.SONOTA3"                 //3�N�ڂ��̑�
            + " ,A.KEIHI4"                  //4�N�ڌ����o��
            + " ,A.BIHINHI4"                //4�N�ڐݔ����i��
            + " ,A.SHOMOHINHI4"             //4�N�ڏ��Օi��
            + " ,A.RYOHI4"                  //4�N�ڗ���
            + " ,A.SHAKIN4"                 //4�N�ڎӋ���
            + " ,A.SONOTA4"                 //4�N�ڂ��̑�
            + " ,A.KEIHI5"                  //5�N�ڌ����o��
            + " ,A.BIHINHI5"                //5�N�ڐݔ����i��
            + " ,A.SHOMOHINHI5"             //5�N�ڏ��Օi��
            + " ,A.RYOHI5"                  //5�N�ڗ���
            + " ,A.SHAKIN5"                 //5�N�ڎӋ���
            + " ,A.SONOTA5"                 //5�N�ڂ��̑�
            //add start ly 2006/08/02
            + " ,A.KEIHI6"                  //6�N�ڌ����o��
            + " ,A.BIHINHI6"                //6�N�ڐݔ����i��
            + " ,A.SHOMOHINHI6"             //6�N�ڏ��Օi��
            + " ,A.RYOHI6"                  //6�N�ڗ���
            + " ,A.SHAKIN6"                 //6�N�ڎӋ���
            + " ,A.SONOTA6"                 //6�N�ڂ��̑�
            //add end ly 2006/08/02
            + " ,A.KEIHI_TOTAL"             //���v-�����o��
            + " ,A.BIHINHI_TOTAL"           //���v-�ݔ����i��
            + " ,A.SHOMOHINHI_TOTAL"        //���v-���Օi��
            + " ,A.RYOHI_TOTAL"             //���v-����
            + " ,A.SHAKIN_TOTAL"            //���v-�Ӌ���
            + " ,A.SONOTA_TOTAL"            //���v-���̑�
            + " ,A.SOSHIKI_KEITAI_NO"       //�����g�D�̌`�Ԕԍ�
            + " ,A.SOSHIKI_KEITAI"          //�����g�D�̌`��
            + " ,A.BUNTANKIN_FLG"           //���S���̗L��
            + " ,A.KENKYU_NINZU"            //�����Ґ�
            + " ,A.TAKIKAN_NINZU"           //���@�ւ̕��S�Ґ�
//          2005/04/22 �폜 ��������----------
//          ���R:�d�l�ɑ��݂��Ȃ�����
            //2005/03/31 �ǉ� ------------------------------------��������
            //���R �������͎ғ��͗����ǉ����ꂽ����
//          + " ,A.TAKIKAN_NINZU"           //�������͎Ґ�
            //2005/03/31 �ǉ� ------------------------------------�����܂�
//          2005/04/22 �폜 �����܂�----------
            + " ,A.SHINSEI_KUBUN"           //�V�K�p���敪
            + " ,A.KADAI_NO_KEIZOKU"        //�p�����̌����ۑ�ԍ�
            + " ,A.SHINSEI_FLG_NO"          //�����v��ŏI�N�x�O�N�x�̉���
//          + " ,A.SHINSEI_FLG"             //�\���̗L��
            + " ,A.KADAI_NO_SAISYU"         //�ŏI�N�x�ۑ�ԍ�
            + " ,A.KAIGAIBUNYA_CD"          //�C�O����R�[�h
            + " ,A.KAIGAIBUNYA_NAME"        //�C�O���얼��
            + " ,A.KAIGAIBUNYA_NAME_RYAKU"  //�C�O���엪��
            + " ,A.KANREN_SHIMEI1"          //�֘A����̌�����-����1
            + " ,A.KANREN_KIKAN1"           //�֘A����̌�����-�����@��1
            + " ,A.KANREN_BUKYOKU1"         //�֘A����̌�����-��������1
            + " ,A.KANREN_SHOKU1"           //�֘A����̌�����-�E��1
            + " ,A.KANREN_SENMON1"          //�֘A����̌�����-��啪��1
            + " ,A.KANREN_TEL1"             //�֘A����̌�����-�Ζ���d�b�ԍ�1
            + " ,A.KANREN_JITAKUTEL1"       //�֘A����̌�����-����d�b�ԍ�1              
            + " ,A.KANREN_MAIL1"            //�֘A����̌�����-Email1               
            + " ,A.KANREN_SHIMEI2"          //�֘A����̌�����-����2
            + " ,A.KANREN_KIKAN2"           //�֘A����̌�����-�����@��2
            + " ,A.KANREN_BUKYOKU2"         //�֘A����̌�����-��������2
            + " ,A.KANREN_SHOKU2"           //�֘A����̌�����-�E��2
            + " ,A.KANREN_SENMON2"          //�֘A����̌�����-��啪��2
            + " ,A.KANREN_TEL2"             //�֘A����̌�����-�Ζ���d�b�ԍ�2
            + " ,A.KANREN_JITAKUTEL2"       //�֘A����̌�����-����d�b�ԍ�2              
            + " ,A.KANREN_MAIL2"            //�֘A����̌�����-Email2               
            + " ,A.KANREN_SHIMEI3"          //�֘A����̌�����-����3
            + " ,A.KANREN_KIKAN3"           //�֘A����̌�����-�����@��3
            + " ,A.KANREN_BUKYOKU3"         //�֘A����̌�����-��������3
            + " ,A.KANREN_SHOKU3"           //�֘A����̌�����-�E��3
            + " ,A.KANREN_SENMON3"          //�֘A����̌�����-��啪��3
            + " ,A.KANREN_TEL3"             //�֘A����̌�����-�Ζ���d�b�ԍ�3
            + " ,A.KANREN_JITAKUTEL3"       //�֘A����̌�����-����d�b�ԍ�3              
            + " ,A.KANREN_MAIL3"            //�֘A����̌�����-Email3   
// 2007/02/13  ���u�j�@�ǉ���������
            + " ,A.SHINSARYOIKI_CD"         //�R����]����R�[�h
            + " ,A.SHINSARYOIKI_NAME"       //�R����]���얼
// 2007/02/13�@���u�j�@�ǉ������܂�
//          2006/02/13 START
            + " ,TO_CHAR(A.SAIYO_DATE, 'YYYY/MM/DD')"             //�̗p�N����
            + " ,A.KINMU_HOUR"               //�Ζ����Ԑ�
            + " ,A.NAIYAKUGAKU"              //���ʌ�������������z
// 2007/02/13  ���u�j�@�ǉ���������
            + " ,A.SHOREIHI_NO_NENDO"        //���ʌ����������ۑ�ԍ�-�N�x
            + " ,A.SHOREIHI_NO_SEIRI"        //���ʌ����������ۑ�ԍ�-�����ԍ�
// 2007/02/13�@���u�j�@�ǉ������܂� 
            + " ,A.OUBO_SHIKAKU"              //���厑�i
            + " ,TO_CHAR(A.SIKAKU_DATE, 'YYYY/MM/DD')"            //���i�擾�N����
            + " ,A.SYUTOKUMAE_KIKAN"         //���i�擾�O�@�֖�
            + " ,TO_CHAR(A.IKUKYU_START_DATE, 'YYYY/MM/DD')"      //��x���J�n��
            + " ,TO_CHAR(A.IKUKYU_END_DATE, 'YYYY/MM/DD')"        //��x���I����
            //2006/02/13 END
            + " ,A.JURI_KEKKA"              //�󗝌���
            + " ,A.JURI_BIKO"               //�󗝌��ʔ��l
            + " ,A.JURI_SEIRI_NO"           //�����ԍ�  2005/07/25
            + " ,A.KEKKA1_ABC"              //�P���R������(ABC)
            + " ,A.KEKKA1_TEN"              //�P���R������(�_��)
//          + " ,A.KEKKA1_TEN_SORTED"       //�P���R������(�_����)
            + " ,A.SHINSA1_BIKO"            //�P���R�����l
            + " ,A.KEKKA2"                  //�Q���R������
            + " ,A.SHINSA2_BIKO"            //�Ɩ��S���ҋL����
            //2005/04/26 �폜�@-----------------------------------------------��������
            //���R ��ID��1���ږڂֈړ���������
            //+ " ,A.JOKYO_ID"              //�\����ID
            //2005/04/26 �폜�@-----------------------------------------------�����܂�
            + " ,A.SAISHINSEI_FLG"          //�Đ\���t���O
//          2005/04/22 �ǉ� ��������----------
//          ���R:�Œǉ��̂���
            + " ,A.EDITION"                 //��
//          2005/04/22 �ǉ� �����܂�----------
            + " ,TO_CHAR(B.HOKAN_DATE, 'YYYY/MM/DD')"       //�f�[�^�ۊǓ�
            + " ,TO_CHAR(B.YUKO_DATE, 'YYYY/MM/DD')"        //�ۊǗL������
            + " FROM"
            + " SHINSEIDATAKANRI" + dbLink + " A,"          //�\���f�[�^�Ǘ��e�[�u��
            + " JIGYOKANRI" + dbLink + " B"                 //���Ə��Ǘ��e�[�u��
            + " WHERE"
            + " A.DEL_FLG = 0"                              //�폜�t���O��[0]
            + " AND"
            + " A.JIGYO_ID = B.JIGYO_ID"                    //����ID����������
            ;
        
        //��������������SQL���𐶐�����B
        String query = getQueryString(select, searchInfo);
        
        //for debug
        if(log.isDebugEnabled()){
            log.debug("query:" + query);
        }
        
        //�J���������X�g�𐶐�����i�ŏ��̂P��̂݁j
        //���w�蕶�����SQL�̎��ʎq���𒴂��Ă��܂����ߕʂɃZ�b�g����B
        //2005.10.04 iso �\��������A�����@�ց����������@�ցA�R�[�h���ԍ��A�\�����������v�撲��
        if(csvColumnList == null){
            String[] columnArray = {
//                  2005/04/22 �ǉ� ��������----------
//                  ���R:�\����ID�ǉ��̂���
                                    "�����ID"
//                  2005/04/22 �ǉ� �����܂�----------
                                   ,"�V�X�e����t�ԍ�"
                                   ,"����ԍ�"
                                   ,"����ID"
                                   ,"�N�x"
                                   ,"��"
                                   ,"���Ɩ�"
                                   ,"�����ID"
                                   ,"�����v�撲���쐬��"
                                   ,"���������@�֏��F��"
                                   //add start ly 2006/08/02
                                   ,"�̈��\�Ҋm���"
                                   //add end ly 2006/08/02
                                   ,"�w�U�󗝓�"
                                   ,"����Ҏ����i������-���j"
                                   ,"����Ҏ����i������-���j"
                                   ,"����Ҏ����i�t���K�i-���j"
                                   ,"����Ҏ����i�t���K�i-���j"
                                   ,"����Ҏ����i���[�}��-���j"
                                   ,"����Ҏ����i���[�}��-���j"
                                   ,"�N��"
                                   ,"����Ҍ����Ҕԍ�"
                                   ,"���������@�֔ԍ�"
                                   ,"���������@�֖�"
                                   ,"���������@�֖��i���́j"
                                   ,"���ǔԍ�"
                                   ,"���ǖ�"
                                   ,"���ǖ��i���́j"
                                   ,"�E���ԍ�"
                                   ,"�E���i�a���j"
                                   ,"�E���i���́j"
                                   ,"�X�֔ԍ�"
                                   ,"�Z��"
                                   ,"TEL"
                                   ,"FAX"
                                   ,"E��ail"
                                   ,"�����ۑ薼(�a���j"
                                   ,"�����ۑ薼(�p���j"
                                   ,"�n���̋敪�ԍ�"
                                   ,"�n���̋敪"
                                   ,"�n���̋敪����"
                                   ,"�זڔԍ�"
                                   ,"����"
                                   ,"����"
                                   ,"�ז�"
                                   ,"�זڔԍ�2"
                                   ,"����2"
                                   ,"����2"
                                   ,"�ז�2"
                                   ,"���E�̊ϓ_�ԍ�"
                                   ,"���E�̊ϓ_"
                                   ,"���E�̊ϓ_����"
// 20050622
                                   ,"�����敪"
                                   ,"�啝�ȕύX�𔺂������ۑ�"
                                   ,"�̈�ԍ�"
                                   ,"�������ڔԍ�"
                                   ,"������"
// Horikoshi

// 20050712 �L�[���[�h���̒ǉ�
                                   ,"�זڕ\�L�[���[�h"
                                   ,"�זڕ\�ȊO�̃L�[���[�h"
// Horikoshi

                                   ,"1�N�ڌ����o��"
                                   ,"1�N�ڐݔ����i��"
                                   ,"1�N�ڏ��Օi��"
                                   ,"1�N�ڗ���"
                                   ,"1�N�ڎӋ���"
                                   ,"1�N�ڂ��̑�"
                                   ,"2�N�ڌ����o��"
                                   ,"2�N�ڐݔ����i��"
                                   ,"2�N�ڏ��Օi��"
                                   ,"2�N�ڗ���"
                                   ,"2�N�ڎӋ���"
                                   ,"2�N�ڂ��̑�"
                                   ,"3�N�ڌ����o��"
                                   ,"3�N�ڐݔ����i��"
                                   ,"3�N�ڏ��Օi��"
                                   ,"3�N�ڗ���"
                                   ,"3�N�ڎӋ���"
                                   ,"3�N�ڂ��̑�"
                                   ,"4�N�ڌ����o��"
                                   ,"4�N�ڐݔ����i��"
                                   ,"4�N�ڏ��Օi��"
                                   ,"4�N�ڗ���"
                                   ,"4�N�ڎӋ���"
                                   ,"4�N�ڂ��̑�"
                                   ,"5�N�ڌ����o��"
                                   ,"5�N�ڐݔ����i��"
                                   ,"5�N�ڏ��Օi��"
                                   ,"5�N�ڗ���"
                                   ,"5�N�ڎӋ���"
                                   ,"5�N�ڂ��̑�"
                                   //add start ly 2006/08/02
                                   ,"6�N�ڌ����o��"
                                   ,"6�N�ڐݔ����i��"
                                   ,"6�N�ڏ��Օi��"
                                   ,"6�N�ڗ���"
                                   ,"6�N�ڎӋ���"
                                   ,"6�N�ڂ��̑�"
                                   //add end ly 2006/08/02
                                   ,"���v-�����o��"
                                   ,"���v-�ݔ����i��"
                                   ,"���v-���Օi��"
                                   ,"���v-����"
                                   ,"���v-�Ӌ���"
                                   ,"���v-���̑�"
                                   ,"�����g�D�̌`�Ԕԍ�"
                                   ,"�����g�D�̌`��"
                                   ,"���S���̗L��"
                                   ,"�����Ґ�"
                                   ,"���@�ւ̕��S�Ґ�"
                                   ,"�V�K�p���敪"
                                   ,"�p�����̌����ۑ�ԍ�"
                                   ,"�����v��ŏI�N�x�O�N�x�̉���"
//                                 ,"�\���̗L��"
                                   ,"�ŏI�N�x�ۑ�ԍ�"
                                   ,"�C�O����ԍ�"
                                   ,"�C�O���얼��"
                                   ,"�C�O���엪��"
                                   ,"�֘A����̌�����-����1"
                                   ,"�֘A����̌�����-���������@��1"
                                   ,"�֘A����̌�����-��������1"
                                   ,"�֘A����̌�����-�E��1"
                                   ,"�֘A����̌�����-��啪��1"
                                   ,"�֘A����̌�����-�Ζ���d�b�ԍ�1"
                                   ,"�֘A����̌�����-����d�b�ԍ�1"
// 2007/03/01  ���u�j�@�C�� ��������
                                   //,"�֘A����̌�����-Email1"
                                   ,"�֘A����̌�����-E-mail1"
// 2007/03/01  ���u�j�@�C�� �����܂�
                                   ,"�֘A����̌�����-����2"
                                   ,"�֘A����̌�����-���������@��2"
                                   ,"�֘A����̌�����-��������2"
                                   ,"�֘A����̌�����-�E��2"
                                   ,"�֘A����̌�����-��啪��2"
                                   ,"�֘A����̌�����-�Ζ���d�b�ԍ�2"
                                   ,"�֘A����̌�����-����d�b�ԍ�2"
// 2007/03/01  ���u�j�@�C�� ��������
                                   //,"�֘A����̌�����-Email2"
                                   ,"�֘A����̌�����-E-mail2"
// 2007/03/01  ���u�j�@�C�� �����܂�
                                   ,"�֘A����̌�����-����3"
                                   ,"�֘A����̌�����-���������@��3"
                                   ,"�֘A����̌�����-��������3"
                                   ,"�֘A����̌�����-�E��3"
                                   ,"�֘A����̌�����-��啪��3"
                                   ,"�֘A����̌�����-�Ζ���d�b�ԍ�3"
                                   ,"�֘A����̌�����-����d�b�ԍ�3"
// 2007/03/01  ���u�j�@�C�� ��������
                                   //,"�֘A����̌�����-Email3"
                                   ,"�֘A����̌�����-E-mail3"
// 2007/03/01  ���u�j�@�C�� �����܂�
// 2007/02/13  ���u�j�@�ǉ���������
                                   ,"�R����]����R�[�h"
                                   ,"�R����]���얼"
// 2007/02/13�@���u�j�@�ǉ������܂�
//                               2006/02/13 START
                                   ,"�̗p�N����"
// 2007/02/13  ���u�j�@�C�� ��������
                                   //,"�Ζ����Ԑ�"
                                   ,"�T������̋Ζ����Ԑ�"
// 2007/02/13�@���u�j�@�C�� �����܂�                                   
                                   ,"���ʌ�������������z"
// 2007/02/13  ���u�j�@�ǉ� ��������
                                   ,"���ʌ����������ۑ�ԍ�-�N�x"
                                   ,"���ʌ����������ۑ�ԍ�-�����ԍ�"
// 2007/02/13�@���u�j�@�ǉ� �����܂�
                                   ,"���厑�i"
                                   ,"���i�擾�N����"
                                   ,"���i�擾�O�@�֖�"
                                   ,"��x���J�n��"
                                   ,"��x���I����"
                                   //2006/02/13 END
                                   ,"�󗝌���"
                                   ,"�󗝌��ʔ��l"
                                   ,"�����ԍ�(�w�n�p)"
                                   ,"�P���R������(ABC)"
                                   ,"�P���R������(�_��)"
//                                 ,"�P���R������(�_����)"                                  
                                   ,"�P���R�����l"
                                   ,"�Q���R������"
                                   ,"�Ɩ��S���ҋL����"
                                    //2005/04/26 �폜�@-----------------------------------------------��������
                                    //���R ��ID��1���ږڂֈړ���������
                                    //,"�\����ID"
                                    //2005/04/26 �폜�@-----------------------------------------------�����܂�
                                   ,"�ĉ���t���O"
//                      2005/04/22 �ǉ� ��������----------
//                                  ���R:�Œǉ��̂���
                                   ,"��"
//                      2005/04/22 �ǉ� �����܂�----------
                                   ,"�f�[�^�ۊǓ�"
                                   ,"�ۊǗL������"
                                    };
            //�O�̂��߃X���b�h�Z�[�t��
            csvColumnList = Collections.synchronizedList(Arrays.asList(columnArray));
        }
        
        //CSV���X�g�擾�i�J���������L�[���ږ��Ƃ��Ȃ��j
        List csvDataList = SelectUtil.selectCsvList(connection, query, false);
        
        //�ŏ��̗v�f�ɃJ���������X�g��}������
        csvDataList.add(0, csvColumnList);
        csvColumnList = null;
        return csvDataList;
    }

    //add start ly 2006/07/11
    /**
     * ������ꗗ�i�����@�ցjCSV�o�͂��郊�X�g��Ԃ��B
     * @param connection
     * @param searchInfo
     * @return
     * @throws DataAccessException
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public List searchShozokuCsvData(
            Connection connection,
            ShinseiSearchInfo searchInfo)
            throws DataAccessException, NoDataFoundException, ApplicationException {

        String select =
        "SELECT "
            + "  A.JIGYO_NAME"                              //���Ɩ�
            + " ,TO_CHAR(A.SAKUSEI_DATE, 'YYYY/MM/DD')"     //�����v�撲���쐬��
            + " ,M.SHOZOKU_HYOJI"                           //�����
            + " ,TO_CHAR(A.SHONIN_DATE, 'YYYY/MM/DD')"      //���������@�֏��F��
            + " ,A.NAME_KANJI_SEI"                          //�\���Ҏ����i������-���j
            + " ,A.NAME_KANJI_MEI"                          //�\���Ҏ����i������-���j
            + " ,A.NAME_KANA_SEI"                           //�\���Ҏ����i�t���K�i-���j
            + " ,A.NAME_KANA_MEI"                           //�\���Ҏ����i�t���K�i-���j
            + " ,A.NENREI"                                  //�N��
            + " ,A.KENKYU_NO"                               //�\���Ҍ����Ҕԍ�
            + " ,A.SHOZOKU_CD"                              //�����@�փR�[�h
            + " ,A.SHOZOKU_NAME"                            //�����@�֖�
            + " ,A.BUKYOKU_CD"                              //���ǃR�[�h
            + " ,A.BUKYOKU_NAME"                            //���ǖ�
            + " ,A.SHOKUSHU_CD"                             //�E���R�[�h
            + " ,A.SHOKUSHU_NAME_KANJI"                     //�E���i�a���j
            + " ,A.ZIP"                                     //�X�֔ԍ�
            + " ,A.ADDRESS"                                 //�Z��
            + " ,A.TEL"                                     //TEL
            + " ,A.FAX"                                     //FAX
            + " ,A.EMAIL"                                   //E-Mail
//            + " ,A.URL"                                     //URL
            + " ,A.KADAI_NAME_KANJI"                        //�����ۑ薼(�a���j
            + " ,A.KEI_NAME_NO"                             //�n���̋敪�ԍ�
            + " ,A.KEI_NAME"                                //�n���̋敪
            + " ,A.BUNKASAIMOKU_CD"                         //�זڔԍ�
            + " ,A.BUNKATSU_NO"                             //�����ԍ�
            + " ,A.BUNYA_NAME"                              //����
            + " ,A.BUNKA_NAME"                              //����
            + " ,A.SAIMOKU_NAME"                            //�ז�
            + " ,A.BUNKASAIMOKU_CD2"                        //�זڔԍ�2
            + " ,A.BUNYA_NAME2"                             //����2
            + " ,A.BUNKA_NAME2"                             //����2
            + " ,A.SAIMOKU_NAME2"                           //�ז�2
            + " ,A.KENKYU_KUBUN"                            //�����敪
            + " ,A.OHABAHENKO"                              //�啝�ȕύX�𔺂������ۑ�
            + " ,A.RYOIKI_NO"                               //�̈�ԍ�
            + " ,A.KOMOKU_NO"                               //���ڔԍ�
            + " ,A.CHOSEIHAN"                               //������
            + " ,A.RYOIKI_RYAKU"                            //�̈旪�̖�
            + " ,SUBSTR(A.UKETUKE_NO,7,10)as UKETUKE_NO"    //�����ԍ�
            + " ,A.EDITION"                                 //�Ő�
            + " ,A.SAIMOKU_KEYWORD"                         //�זڕ\�L�[���[�h
            + " ,A.OTHER_KEYWORD"                           //�זڕ\�ȊO�̃L�[���[�h
            + " ,A.KEIHI1"                                  //1�N�ڌ����o��   
            + " ,A.BIHINHI1"                                //1�N�ڐݔ����i�� 
            + " ,A.SHOMOHINHI1"                             //1�N�ڏ��Օi��   
            + " ,A.RYOHI1"                                  //1�N�ڗ���       
            + " ,A.SHAKIN1"                                 //1�N�ڎӋ���     
            + " ,A.SONOTA1"                                 //1�N�ڂ��̑�     
            + " ,A.KEIHI2"                                  //2�N�ڌ����o��   
            + " ,A.BIHINHI2"                                //2�N�ڐݔ����i�� 
            + " ,A.SHOMOHINHI2"                             //2�N�ڏ��Օi��   
            + " ,A.RYOHI2"                                  //2�N�ڗ���       
            + " ,A.SHAKIN2"                                 //2�N�ڎӋ���     
            + " ,A.SONOTA2"                                 //2�N�ڂ��̑�     
            + " ,A.KEIHI3"                                  //3�N�ڌ����o��   
            + " ,A.BIHINHI3"                                //3�N�ڐݔ����i�� 
            + " ,A.SHOMOHINHI3"                             //3�N�ڏ��Օi��   
            + " ,A.RYOHI3"                                  //3�N�ڗ���       
            + " ,A.SHAKIN3"                                 //3�N�ڎӋ���     
            + " ,A.SONOTA3"                                 //3�N�ڂ��̑�     
            + " ,A.KEIHI4"                                  //4�N�ڌ����o��   
            + " ,A.BIHINHI4"                                //4�N�ڐݔ����i�� 
            + " ,A.SHOMOHINHI4"                             //4�N�ڏ��Օi��   
            + " ,A.RYOHI4"                                  //4�N�ڗ���       
            + " ,A.SHAKIN4"                                 //4�N�ڎӋ���     
            + " ,A.SONOTA4"                                 //4�N�ڂ��̑�     
            + " ,A.KEIHI5"                                  //5�N�ڌ����o��   
            + " ,A.BIHINHI5"                                //5�N�ڐݔ����i�� 
            + " ,A.SHOMOHINHI5"                             //5�N�ڏ��Օi��   
            + " ,A.RYOHI5"                                  //5�N�ڗ���       
            + " ,A.SHAKIN5"                                 //5�N�ڎӋ���     
            + " ,A.SONOTA5"                                 //5�N�ڂ��̑�   
            + " ,A.KEIHI6"                                  //6�N�ڌ����o��   
            + " ,A.BIHINHI6"                                //6�N�ڐݔ����i�� 
            + " ,A.SHOMOHINHI6"                             //6�N�ڏ��Օi��   
            + " ,A.RYOHI6"                                  //6�N�ڗ���       
            + " ,A.SHAKIN6"                                 //6�N�ڎӋ���     
            + " ,A.SONOTA6"                                 //6�N�ڂ��̑�
            + " ,A.KEIHI_TOTAL"                             //���v-�����o��
            + " ,A.BIHINHI_TOTAL"                           //���v-�ݔ����i��
            + " ,A.SHOMOHINHI_TOTAL"                        //���v-���Օi��
            + " ,A.RYOHI_TOTAL"                             //���v-����
            + " ,A.SHAKIN_TOTAL"                            //���v-�Ӌ���
            + " ,A.SONOTA_TOTAL"                            //���v-���̑�
            + " ,A.BUNTANKIN_FLG"                           //���S���̗L��
            + " ,A.KAIJIKIBO_FLG_NO"                        //�J����]�̗L��
            + " ,A.KENKYU_NINZU"                            //�����Ґ�
            + " ,A.TAKIKAN_NINZU"                           //���@�ւ̕��S�Ґ� 
            + " ,A.SHINSEI_KUBUN"                           //�V�K�p���敪
            + " ,A.KADAI_NO_KEIZOKU"                        //�p�����̌����ۑ�ԍ�
            + " ,A.SHINSEI_FLG_NO"                          //�����v��ŏI�N�x�O�N�x�̉���
            + " ,A.KADAI_NO_SAISYU"                         //�ŏI�N�x�ۑ�ԍ�
            + " ,A.KAIGAIBUNYA_CD"                          //�C�O����R�[�h
            + " ,A.KAIGAIBUNYA_NAME"                        //�C�O���얼��
            + " ,A.KAIGAIBUNYA_NAME_RYAKU"                  //�C�O���엪��
// 2007/02/14  ���u�j�@�ǉ� ��������
            + " ,A.SHINSARYOIKI_CD"                         //�R����]����R�[�h
            + " ,A.SHINSARYOIKI_NAME"                       //�R����]���얼
// 2007/02/14�@���u�j�@�ǉ� �����܂�
            + " ,TO_CHAR(A.SAIYO_DATE, 'YYYY/MM/DD')"       //�̗p�N����
            + " ,A.KINMU_HOUR"                              //�Ζ����Ԑ�
            + " ,A.NAIYAKUGAKU"                             //���ʌ�������������z 
// 2007/02/14  ���u�j�@�ǉ� ��������
            + " ,A.SHOREIHI_NO_NENDO"                       //���ʌ����������ۑ�ԍ�-�N�x
            + " ,A.SHOREIHI_NO_SEIRI"                       //���ʌ����������ۑ�ԍ�-�����ԍ�
// 2007/02/14�@���u�j�@�ǉ� �����܂�
            + " ,A.OUBO_SHIKAKU"                            //���厑�i
            + " ,TO_CHAR(A.SIKAKU_DATE, 'YYYY/MM/DD')"      //���i�擾�N����
            + " ,A.SYUTOKUMAE_KIKAN"                        //���i�擾�O�@�֖�
            + " ,TO_CHAR(A.IKUKYU_START_DATE, 'YYYY/MM/DD')"//��x���J�n��
            + " ,TO_CHAR(A.IKUKYU_END_DATE, 'YYYY/MM/DD')"  //��x���I����
            + " FROM"
            + " SHINSEIDATAKANRI" + dbLink + " A"           //�\���f�[�^�Ǘ��e�[�u��
            + " LEFT JOIN MASTER_STATUS M" 
            + " ON A.JOKYO_ID = M.JOKYO_ID  "
            + " AND A.SAISHINSEI_FLG = M.SAISHINSEI_FLG"
//2007/3/27�@���Ə��Ǘ��e�[�u�����獀�ڂ��擾���ĂȂ��ׁA�R�����g����
//            + " ,JIGYOKANRI" + dbLink + " B"                //���Ə��Ǘ��e�[�u��
            + " WHERE"
            + " A.DEL_FLG = 0"                              //�폜�t���O��[0]
//            + " AND"
//            + " A.JIGYO_ID = B.JIGYO_ID"                    //����ID����������
            ;
        
        //��������������SQL���𐶐�����B
        String query = getQueryString(select, searchInfo);
        
        //for debug
        if(log.isDebugEnabled()){
            log.debug("query:" + query);
        }
        
        //�J���������X�g�𐶐�����i�ŏ��̂P��̂݁j
        if(csvColumnList == null){
            String[] columnArray = {
                     "���Ɩ�"
                    ,"�����v�撲���쐬��"
                    ,"�����"
                    ,"���������@�֏��F��"
                    ,"����Ҏ����i������-���j"
                    ,"����Ҏ����i������-���j"
                    ,"����Ҏ����i�t���K�i-���j"
                    ,"����Ҏ����i�t���K�i-���j"
                    ,"�N��"
                    ,"����Ҍ����Ҕԍ�"
                    ,"���������@�֔ԍ�"
                    ,"���������@�֖�"
                    ,"���ǔԍ�"
                    ,"���ǖ�"
                    ,"�E���ԍ�"
                    ,"�E���i�a���j"
                    ,"�X�֔ԍ�"
                    ,"�Z��"
                    ,"TEL"
                    ,"FAX"
                    ,"E��ail"
 //                   ,"URL"
                    ,"�����ۑ薼(�a���j"
                    ,"�n���̋敪�ԍ�"
                    ,"�n���̋敪"
                    ,"�זڔԍ�"
                    ,"�����ԍ�"
                    ,"����"
                    ,"����"
                    ,"�ז�"
                    ,"�זڔԍ�2"
                    ,"����2"
                    ,"����2"
                    ,"�ז�2"
                    ,"�����敪"
                    ,"�啝�ȕύX�𔺂������ۑ�"
                    ,"�̈�ԍ�"
                    ,"�������ڔԍ�"
                    ,"������"
                    ,"�̈旪�̖�"
                    ,"�����ԍ�"
                    ,"�Ő�"
                    ,"�זڕ\�L�[���[�h"
                    ,"�זڕ\�ȊO�̃L�[���[�h"
                    ,"1�N�ڌ����o��"
                    ,"1�N�ڐݔ����i��"
                    ,"1�N�ڏ��Օi��"
                    ,"1�N�ڗ���"
                    ,"1�N�ڎӋ���"
                    ,"1�N�ڂ��̑�"
                    ,"2�N�ڌ����o��"
                    ,"2�N�ڐݔ����i��"
                    ,"2�N�ڏ��Օi��"
                    ,"2�N�ڗ���"
                    ,"2�N�ڎӋ���"
                    ,"2�N�ڂ��̑�"
                    ,"3�N�ڌ����o��"
                    ,"3�N�ڐݔ����i��"
                    ,"3�N�ڏ��Օi��"
                    ,"3�N�ڗ���"
                    ,"3�N�ڎӋ���"
                    ,"3�N�ڂ��̑�"
                    ,"4�N�ڌ����o��"
                    ,"4�N�ڐݔ����i��"
                    ,"4�N�ڏ��Օi��"
                    ,"4�N�ڗ���"
                    ,"4�N�ڎӋ���"
                    ,"4�N�ڂ��̑�"
                    ,"5�N�ڌ����o��"
                    ,"5�N�ڐݔ����i��"
                    ,"5�N�ڏ��Օi��"
                    ,"5�N�ڗ���"
                    ,"5�N�ڎӋ��� "
                    ,"5�N�ڂ��̑�"
                    ,"6�N�ڌ����o��"
                    ,"6�N�ڐݔ����i��"
                    ,"6�N�ڏ��Օi��"
                    ,"6�N�ڗ���"
                    ,"6�N�ڎӋ���"
                    ,"6�N�ڂ��̑�"
                    ,"���v-�����o��"
                    ,"���v-�ݔ����i��"
                    ,"���v-���Օi��"
                    ,"���v-����"
                    ,"���v-�Ӌ���"
                    ,"���v-���̑�"
                    ,"���S���̗L��"
                    ,"�J����]�̗L��"
                    ,"�����Ґ�"
                    ,"���@�ւ̕��S�Ґ�"
                    ,"�V�K�p���敪"
                    ,"�p�����̌����ۑ�ԍ�"
                    ,"�����v��ŏI�N�x�O�N�x�̉���"
                    ,"�ŏI�N�x�ۑ�ԍ�"
                    ,"�C�O����ԍ�"
                    ,"�C�O���얼��"
                    ,"�C�O���엪��"
// 2007/02/14  ���u�j�@�ǉ� ��������
                    ,"�R����]����R�[�h"
                    ,"�R����]���얼"
// 2007/02/14�@���u�j�@�ǉ� �����܂�
                    ,"�̗p�N����"                  
// 2007/02/14  ���u�j�@�C�� ��������
                    //,"�Ζ����Ԑ�"
                    ,"�T������̋Ζ����Ԑ�"
// 2007/02/14�@���u�j�@�C�� �����܂�
                    ,"���ʌ�������������z"
// 2007/02/14  ���u�j�@�ǉ� ��������
                    ,"���ʌ����������ۑ�ԍ�-�N�x"
                    ,"���ʌ����������ۑ�ԍ�-�����ԍ�"
// 2007/02/14�@���u�j�@�ǉ� �����܂�
                    ,"���厑�i"
                    ,"���i�擾�N����"
                    ,"���i�擾�O�@�֖�"
                    ,"��x���J�n��"
                    ,"��x���I����"
                    };
            //�O�̂��߃X���b�h�Z�[�t��
            csvColumnList = Collections.synchronizedList(Arrays.asList(columnArray));
        }
        
        //CSV���X�g�擾�i�J���������L�[���ږ��Ƃ��Ȃ��j
        List csvDataList = SelectUtil.selectCsvList(connection, query, false);
        
        //�ŏ��̗v�f�ɃJ���������X�g��}������
        csvDataList.add(0, csvColumnList);
        csvColumnList = null;
        return csvDataList;
    }   
    //add end ly 2006/07/11
    
    /**
     * �����g�D�\CSV�o�͂��郊�X�g��Ԃ��B
     * @param connection
     * @param searchInfo
     * @return
     * @throws DataAccessException
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public List searchKenkyuSoshikiCsvData(
            Connection connection,
            ShinseiSearchInfo searchInfo)
            throws DataAccessException, NoDataFoundException, ApplicationException {

        String select = 
        "SELECT "
            + " B.SYSTEM_NO               \"�V�X�e����t�ԍ�\""
            + ",B.SEQ_NO                  \"�V�[�P���X�ԍ�\""
            + ",B.JIGYO_ID                \"����ID\""
            + ",B.BUNTAN_FLG              \"��\�ҕ��S�ҕ�\""
            + ",B.KENKYU_NO               \"�����Ҕԍ�\""
            + ",B.NAME_KANJI_SEI          \"�����i�����|���j\""
            + ",B.NAME_KANJI_MEI          \"�����i�����|���j\""
            + ",B.NAME_KANA_SEI           \"�����i�t���K�i�|���j\""
            + ",B.NAME_KANA_MEI           \"�����i�t���K�i�|���j\""
            + ",B.SHOZOKU_CD              \"���������@�֖��i�ԍ��j\""
            + ",B.SHOZOKU_NAME            \"���������@�֖��i�a���j\""
            + ",B.BUKYOKU_CD              \"���ǖ��i�ԍ��j\""
            + ",B.BUKYOKU_NAME            \"���ǖ��i�a���j\""
            + ",B.SHOKUSHU_CD             \"�E���i�ԍ��j\""
            + ",B.SHOKUSHU_NAME_KANJI     \"�E���i�a���j\""
            + ",B.SENMON                  \"���݂̐��\""
            + ",B.GAKUI                   \"�w��\""
            + ",B.BUNTAN                  \"�������S\""
            + ",B.KEIHI                   \"�����o��\""
            + ",B.EFFORT                  \"�G�t�H�[�g\""
            + ",B.NENREI                  \"�N��\""
            + ",A.KADAI_NAME_KANJI        \"�����ۑ薼�i�a���j\""        //2005.12.13 iso �ǉ�
            + ",A.JURI_SEIRI_NO           \"�����ԍ��i�w�n�p�j\""        //2005.12.13 iso �ǉ�
//          2006.2.10  �ǉ�//
//          +",A.OUBO_SHIKAKU"
            + " FROM"
            + "  SHINSEIDATAKANRI" + dbLink + " A"
            + " ,KENKYUSOSHIKIKANRI" + dbLink + " B"
            + " WHERE"
            + "  B.SYSTEM_NO = A.SYSTEM_NO"     //�V�X�e����t�ԍ�����������
            ;
            
        //��������������SQL���𐶐�����B
        String query = getQueryString(select, searchInfo);
            
        //for debug
        if(log.isDebugEnabled()){
            log.debug("query:" + query);
        }   
    
        //CSV���X�g�擾�i�J���������L�[���ڂƂ���j
        List csvDataList = SelectUtil.selectCsvList(connection, query, true);
        return csvDataList;
    }   

    /**
     * �w�茟�������ɊY������֘A�������Ԃ��B
     * @param connection
     * @param searchInfo
     * @return
     * @throws DataAccessException
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public Page searchKanrenbunyaList(
            Connection connection,
            ShinseiSearchInfo searchInfo)
            throws DataAccessException , NoDataFoundException, ApplicationException {

        String select =
        "SELECT "
            + " A.SYSTEM_NO,"                   //�V�X�e����t�ԍ�
            + " A.UKETUKE_NO,"                  //�\���ԍ�
            + " A.JIGYO_ID,"                    //����ID
            + " A.NENDO,"                       //�N�x
            + " A.KAISU,"                       //��
            + " A.JIGYO_NAME,"                  //���Ɩ�
            + " A.SHINSEISHA_ID,"               //�\����ID
            + " A.NAME_KANJI_SEI,"              //�\���Ҏ����i������-���j
            + " A.NAME_KANJI_MEI,"              //�\���Ҏ����i������-���j
            + " A.SHOZOKU_CD,"                  //�����@�փR�[�h
            + " A.SHOZOKU_NAME_RYAKU,"          //�����@�֖��i���́j
            + " A.BUKYOKU_NAME_RYAKU,"          //���ǖ��i���́j
            + " A.SHOKUSHU_NAME_RYAKU,"         //�E���i���́j
            + " A.KADAI_NAME_KANJI,"            //�����ۑ薼(�a���j
            + " A.KEI_NAME_RYAKU,"              //�n���̋敪�i���́j
            + " A.JURI_SEIRI_NO,"               //�����ԍ��i�w�n�p�j�@    2005�11�2�ǉ�
            + " A.KANREN_SHIMEI1,"              //�֘A����̌�����-����1
            + " A.KANREN_KIKAN1,"               //�֘A����̌�����-�����@��1
            + " A.KANREN_BUKYOKU1,"             //�֘A����̌�����-��������1
            + " A.KANREN_SHOKU1,"               //�֘A����̌�����-�E��1
            + " A.KANREN_SENMON1,"              //�֘A����̌�����-��啪��1
            + " A.KANREN_TEL1,"                 //�֘A����̌�����-�Ζ���d�b�ԍ�1
            + " A.KANREN_JITAKUTEL1,"           //�֘A����̌�����-����d�b�ԍ�1
            + " A.KANREN_MAIL1,"                //�֘A����̌�����-E-Mail1
            + " A.KANREN_SHIMEI2,"              //�֘A����̌�����-����2
            + " A.KANREN_KIKAN2,"               //�֘A����̌�����-�����@��2
            + " A.KANREN_BUKYOKU2,"             //�֘A����̌�����-��������2
            + " A.KANREN_SHOKU2,"               //�֘A����̌�����-�E��2
            + " A.KANREN_SENMON2,"              //�֘A����̌�����-��啪��2
            + " A.KANREN_TEL2,"                 //�֘A����̌�����-�Ζ���d�b�ԍ�2
            + " A.KANREN_JITAKUTEL2,"           //�֘A����̌�����-����d�b�ԍ�2
            + " A.KANREN_MAIL2,"                //�֘A����̌�����-E-Mail2
            + " A.KANREN_SHIMEI3,"              //�֘A����̌�����-����3
            + " A.KANREN_KIKAN3,"               //�֘A����̌�����-�����@��3
            + " A.KANREN_BUKYOKU3,"             //�֘A����̌�����-��������3
            + " A.KANREN_SHOKU3,"               //�֘A����̌�����-�E��3
            + " A.KANREN_SENMON3,"              //�֘A����̌�����-��啪��3
            + " A.KANREN_TEL3,"                 //�֘A����̌�����-�Ζ���d�b�ԍ�3
            + " A.KANREN_JITAKUTEL3,"           //�֘A����̌�����-����d�b�ԍ�3
            + " A.KANREN_MAIL3,"                //�֘A����̌�����-E-Mail3
            + " A.JOKYO_ID,"                    //�\����ID
            + " A.SAISHINSEI_FLG"               //�Đ\���t���O
            + " FROM"
            + " SHINSEIDATAKANRI" + dbLink + " A"//�\���f�[�^�Ǘ��e�[�u��
            + " WHERE"
            + " A.DEL_FLG = 0"                  //�폜�t���O��[0]
            ;
        
        //��������������SQL���𐶐�����B
        String query = getQueryString(select, searchInfo);
        
        //for debug
        if(log.isDebugEnabled()){
            log.debug("query:" + query);
        }
        
        //�y�[�W�擾
        return SelectUtil.selectPageInfo(connection, (SearchInfo)searchInfo, query);
    }

    /**
     * �w�茟�������ɊY������֘A������(CSV)��Ԃ��B
     * @param connection
     * @param searchInfo
     * @return
     * @throws DataAccessException
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public List searchKanrenbunyaListCSV(
            Connection connection,
            ShinseiSearchInfo searchInfo)
            throws DataAccessException , NoDataFoundException, ApplicationException {

        String select =
        "SELECT "
            + " A.NENDO,"                       //�N�x
            + " A.KAISU,"                       //��
            + " A.JIGYO_NAME,"                  //���Ɩ�
            + " A.UKETUKE_NO,"                  //�\���ԍ�
            + " A.KEI_NAME,"                    //�n���̋敪
            + " A.JURI_SEIRI_NO,"               //�����ԍ��i�w�n�p�j2005/11/2�ǉ�
            + " A.KADAI_NAME_KANJI,"            //�����ۑ薼(�a���j
            + " A.NAME_KANJI_SEI,"              //�\���Ҏ����i������-���j
            + " A.NAME_KANJI_MEI,"              //�\���Ҏ����i������-���j
            + " A.SHOZOKU_CD,"                  //�����@�փR�[�h
            + " A.SHOZOKU_NAME,"                //�����@�֖�     
            + " A.BUKYOKU_NAME,"                //���ǖ�
            + " A.SHOKUSHU_NAME_KANJI,"         //�E��
            + " A.KANREN_SHIMEI1,"              //�֘A����̌�����-����1
            + " A.KANREN_KIKAN1,"               //�֘A����̌�����-�����@��1
            + " A.KANREN_BUKYOKU1,"             //�֘A����̌�����-��������1
            + " A.KANREN_SHOKU1,"               //�֘A����̌�����-�E��1
            + " A.KANREN_SENMON1,"              //�֘A����̌�����-��啪��1
            + " A.KANREN_TEL1,"                 //�֘A����̌�����-�Ζ���d�b�ԍ�1
            + " A.KANREN_JITAKUTEL1,"           //�֘A����̌�����-����d�b�ԍ�1
            + " A.KANREN_MAIL1,"                //�֘A����̌�����-E-Mail1
            + " A.KANREN_SHIMEI2,"              //�֘A����̌�����-����2
            + " A.KANREN_KIKAN2,"               //�֘A����̌�����-�����@��2
            + " A.KANREN_BUKYOKU2,"             //�֘A����̌�����-��������2
            + " A.KANREN_SHOKU2,"               //�֘A����̌�����-�E��2
            + " A.KANREN_SENMON2,"              //�֘A����̌�����-��啪��2
            + " A.KANREN_TEL2,"                 //�֘A����̌�����-�Ζ���d�b�ԍ�2
            + " A.KANREN_JITAKUTEL2,"           //�֘A����̌�����-����d�b�ԍ�2
            + " A.KANREN_MAIL2,"                //�֘A����̌�����-E-Mail2
            + " A.KANREN_SHIMEI3,"              //�֘A����̌�����-����3
            + " A.KANREN_KIKAN3,"               //�֘A����̌�����-�����@��3
            + " A.KANREN_BUKYOKU3,"             //�֘A����̌�����-��������3
            + " A.KANREN_SHOKU3,"               //�֘A����̌�����-�E��3
            + " A.KANREN_SENMON3,"              //�֘A����̌�����-��啪��3
            + " A.KANREN_TEL3,"                 //�֘A����̌�����-�Ζ���d�b�ԍ�3
            + " A.KANREN_JITAKUTEL3,"           //�֘A����̌�����-����d�b�ԍ�3
            + " A.KANREN_MAIL3"                 //�֘A����̌�����-E-Mail3
            + " FROM"
            + " SHINSEIDATAKANRI" + dbLink + " A"//�\���f�[�^�Ǘ��e�[�u��
            + " WHERE"
            + " A.DEL_FLG = 0"                  //�폜�t���O��[0]
            ;
        
        //��������������SQL���𐶐�����B
        String query = getQueryString(select, searchInfo);
        
        //for debug
        if(log.isDebugEnabled()){
            log.debug("query:" + query);
        }
        
        //�f�[�^���X�g�擾
        List dataList = SelectUtil.select(connection, query);
        
        //CSV�̌`���ɕϊ�����
        List csvDataList = new ArrayList();
        for(int i=0; i<dataList.size(); i++){
            Map recordMap = (Map)dataList.get(i);
            //�֘A���쌤���ҁi�R�l�j
            for(int j=1; j<=3; j++){
                List record = new ArrayList();
                record.add(StringUtil.defaultString(recordMap.get("NENDO")));
                record.add(StringUtil.defaultString(recordMap.get("KAISU")));
                record.add(StringUtil.defaultString(recordMap.get("JIGYO_NAME")));
                record.add(StringUtil.defaultString(recordMap.get("UKETUKE_NO")));
                record.add(StringUtil.defaultString(recordMap.get("KEI_NAME")));
                record.add(StringUtil.defaultString(recordMap.get("JURI_SEIRI_NO")));
                record.add(StringUtil.defaultString(recordMap.get("KADAI_NAME_KANJI")));
                record.add(StringUtil.defaultString(recordMap.get("NAME_KANJI_SEI")));
                record.add(StringUtil.defaultString(recordMap.get("NAME_KANJI_MEI")));
                record.add(StringUtil.defaultString(recordMap.get("SHOZOKU_CD")));
                record.add(StringUtil.defaultString(recordMap.get("SHOZOKU_NAME")));
                record.add(StringUtil.defaultString(recordMap.get("BUKYOKU_NAME")));
                record.add(StringUtil.defaultString(recordMap.get("SHOKUSHU_NAME_KANJI")));
                record.add(StringUtil.defaultString(recordMap.get("KANREN_SHIMEI"+j)));
                record.add(StringUtil.defaultString(recordMap.get("KANREN_KIKAN"+j)));
                record.add(StringUtil.defaultString(recordMap.get("KANREN_BUKYOKU"+j)));
                record.add(StringUtil.defaultString(recordMap.get("KANREN_SHOKU"+j)));
                record.add(StringUtil.defaultString(recordMap.get("KANREN_SENMON"+j)));
                record.add(StringUtil.defaultString(recordMap.get("KANREN_TEL"+j)));
                record.add(StringUtil.defaultString(recordMap.get("KANREN_JITAKUTEL"+j)));
                record.add(StringUtil.defaultString(recordMap.get("KANREN_MAIL"+j)));
                csvDataList.add(record);
            }
        }
        
        //�J���������X�g�𐶐�����i�ŏ��̂P��̂݁j
        //���w�蕶�����SQL�̎��ʎq���𒴂��Ă��܂��\�������邽�ߕʂɃZ�b�g����B    
        //2005.10.04 iso �\��������A�����@�ց����������@�ցA�R�[�h���ԍ��A�\�����������v�撲��    
        if(csvColumnList4Kanrenbunya == null){
            String[] columnArray = {
                                    "�N�x",
                                    "��",
                                    "���Ɩ�",
                                    "����ԍ�",
                                    "�n���̋敪",
                                    "�����ԍ��i�w�n�p�j",
                                    "�����ۑ薼(�a���j",
                                    "����Ҏ����i������-���j",
                                    "����Ҏ����i������-���j",
                                    "���������@�֔ԍ�",
                                    "���������@�֖�",
                                    "���ǖ�",
                                    "�E���i�a���j",
                                    "�֘A����̌�����-����1�`3",
                                    "�֘A����̌�����-���������@��1�`3",
                                    "�֘A����̌�����-��������1�`3",
                                    "�֘A����̌�����-�E��1�`3",
                                    "�֘A����̌�����-��啪��1�`3",
                                    "�֘A����̌�����-�Ζ���d�b�ԍ�1�`3",
                                    "�֘A����̌�����-����d�b�ԍ�1�`3",
                                    "�֘A����̌�����-Email1�`3"
                                    };
            //�O�̂��߃X���b�h�Z�[�t��
            csvColumnList4Kanrenbunya = Collections.synchronizedList(Arrays.asList(columnArray));
        }
        
        //�ŏ��̗v�f�ɃJ���������X�g��}������
        csvDataList.add(0, csvColumnList4Kanrenbunya);
        return csvDataList;
    }

    /**
     * �R���˗����s�ʒm���̃X�e�[�^�X�X�V���\�b�h�B
     * �R��������U�菈����[08]�̐\���󋵂�R����[10]�ɍX�V����B
     * ���ɐR����[10]�̂��́A�܂��͐R��������U�菈����[08]�ȊO�̂���
     * �ɑ΂��Ă͍X�V�������s��Ȃ��B�i�\���󋵂͂��̂܂܂ƂȂ�B�j
     * @param connection
     * @param shinseiDataPk
     * @throws DataAccessException
     * @throws ApplicationException
     */
    public void updateStatusForShinsaIraiIssue(
            Connection connection,
            ShinseiDataPk[] shinseiDataPk)
            throws DataAccessException , ApplicationException {

        //�Q�Ɖ\�\���f�[�^���`�F�b�N
        checkOwnShinseiData(connection, shinseiDataPk);
        
        //-----�r������-----
        String select = 
            "SELECT * FROM SHINSEIDATAKANRI"+dbLink
                + " WHERE"
                + " SYSTEM_NO IN ("+ getQuestionMark(shinseiDataPk.length) +")"
                + " FOR UPDATE"
                ;

        PreparedStatement preparedStatement = null;
        ResultSet         resultSet         = null;
        try {
            //�o�^
            preparedStatement = connection.prepareStatement(select);
            int index = 1;
            for(int i=0; i<shinseiDataPk.length; i++){
                DatabaseUtil.setParameter(preparedStatement, index++, shinseiDataPk[i].getSystemNo());
            }
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException ex) {
            throw new DataAccessException("�\�����r�����䒆�ɗ�O���������܂����B ", ex);
        } finally {
            DatabaseUtil.closeResource(resultSet, preparedStatement);
        }
            
        //-----DB�X�V-----
        String query =
            "UPDATE SHINSEIDATAKANRI"+dbLink
                + " SET"
                + " JOKYO_ID = '" + StatusCode.STATUS_1st_SHINSATYU + "' "      //�\����ID                
                + " WHERE"
                + " SYSTEM_NO IN ("+ getQuestionMark(shinseiDataPk.length) +")"
                + " AND"
                + " JOKYO_ID = '" + StatusCode.STATUS_GAKUSIN_JYURI + "' "  //�\����ID[06]�̏ꍇ�̂�
                ;

        PreparedStatement preparedStatement2 = null;
        try {
            //�o�^
            preparedStatement2 = connection.prepareStatement(query);
            int index = 1;
            for(int i=0; i<shinseiDataPk.length; i++){
                DatabaseUtil.setParameter(preparedStatement2, index++, shinseiDataPk[i].getSystemNo());
            }
            preparedStatement2.executeUpdate();
        } catch (SQLException ex) {
            throw new DataAccessException("�\�����X�e�[�^�X�X�V���ɗ�O���������܂����B ", ex);
        } finally {
            DatabaseUtil.closeResource(null, preparedStatement2);
        }
    }

    /**
     * ���[�J���ɑ��݂���Y�����R�[�h�̓��e��DBLink��̃e�[�u���ɑ}������B
     * DBLink��ɓ������R�[�h������ꍇ�́A�\�ߍ폜���Ă������ƁB
     * DBLink���ݒ肳��Ă��Ȃ��ꍇ�̓G���[�ƂȂ�B
     * @param connection
     * @param jigyoId
     * @throws DataAccessException
     */
    public void copy2HokanDB(
            Connection connection,
            String     jigyoId)
            throws DataAccessException {

        //DBLink�����Z�b�g����Ă��Ȃ��ꍇ
        if(dbLink == null || dbLink.length() == 0){
            throw new DataAccessException("DB�����N�����ݒ肳��Ă��܂���BDBLink="+dbLink);
        }
        
        String query =
            "INSERT INTO SHINSEIDATAKANRI"+dbLink
                + " SELECT * FROM SHINSEIDATAKANRI WHERE JIGYO_ID = ?";
        PreparedStatement preparedStatement = null;
        try {
            //�o�^
            preparedStatement = connection.prepareStatement(query);
            int index = 1;
            DatabaseUtil.setParameter(preparedStatement, index++, jigyoId);     //���������i����ID�j
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new DataAccessException("�\���f�[�^�Ǘ��e�[�u���ۊǒ��ɗ�O���������܂����B ", ex);
        } finally {
            DatabaseUtil.closeResource(null, preparedStatement);
        }
    }

    /**
     * �w�莖�Ƃ̐\���f�[�^������Ԃ��B
     * @param connection
     * @param jigyoId
     * @return
     * @throws DataAccessException
     */
    public int countShinseiDataByJigyoID(
            Connection connection,
            String     jigyoId)
            throws DataAccessException {

        String query =
            "SELECT COUNT(SYSTEM_NO) FROM SHINSEIDATAKANRI"+dbLink
                + " WHERE"
                + " JIGYO_ID = ?"   //�w�莖��ID�̂���
                ;
        PreparedStatement preparedStatement = null;
        ResultSet recordSet = null;
        try {
            //����
            preparedStatement = connection.prepareStatement(query);
            int index = 1;
            DatabaseUtil.setParameter(preparedStatement,index++,jigyoId);
            recordSet = preparedStatement.executeQuery();
            int count = 0;
            if (recordSet.next()) {
                count = recordSet.getInt(1);
            }
            return count;
        } catch (SQLException ex) {
            throw new DataAccessException("�\���f�[�^�Ǘ��e�[�u���������s���ɗ�O���������܂����B ", ex);
        } finally {
            DatabaseUtil.closeResource(recordSet, preparedStatement);
        }
    }

    /**
     * ����ID�{�\���ԍ��i�@�֐����ԍ��j�̐\���f�[�^������Ԃ��B
     * ���Ȍ��ɂ������Ӄf�[�^�̌����i�{���Ȃ�΂P���ɂȂ�͂��j
     * @param connection
     * @param jigyoId
     * @param uketukeNo
     * @return
     * @throws DataAccessException
     */
    public int countShinseiData(
            Connection connection,
            String     jigyoId,
            String     uketukeNo)
            throws DataAccessException {

        String query =
            "SELECT COUNT(SYSTEM_NO) FROM SHINSEIDATAKANRI" + dbLink
                + " WHERE"
                + " JIGYO_ID = ?"   //�w�莖��ID�̂���
                + " AND"
                + " UKETUKE_NO = ?" //�w��\���ԍ��̂���
                ;
        PreparedStatement preparedStatement = null;
        ResultSet recordSet = null;
        try {
            //����
            preparedStatement = connection.prepareStatement(query);
            int index = 1;
            DatabaseUtil.setParameter(preparedStatement,index++,jigyoId);
            DatabaseUtil.setParameter(preparedStatement,index++,uketukeNo);
            recordSet = preparedStatement.executeQuery();
            int count = 0;
            if (recordSet.next()) {
                count = recordSet.getInt(1);
            }
            return count;
        } catch (SQLException ex) {
            throw new DataAccessException("�\���f�[�^�Ǘ��e�[�u���������s���ɗ�O���������܂����B ", ex);
        } finally {
            DatabaseUtil.closeResource(recordSet, preparedStatement);
        }
    }   

    //---------------------------------------------------------------------
    // Private Methods
    //--------------------------------------------------------------------- 
    /**
     * �w��PreparedStatement�ɐ\���f�[�^���ׂẴp�����[�^���Z�b�g����B
     * SQL�X�e�[�g�����g�ɂ͑S�Ẵp�����[�^���w��\�ɂ��Ă������ƁB
     * @param preparedStatement
     * @param dataInfo
     * @return index          ���̃p�����[�^�C���f�b�N�X
     * @throws SQLException
     */
    private int setAllParameter(PreparedStatement preparedStatement, 
                                   ShinseiDataInfo dataInfo)
            throws SQLException {

        int index = 1;
        //---��{���i�O���j
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getSystemNo());         // �V�X�e����t�ԍ�
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getUketukeNo());        // �\���ԍ�
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getJigyoId());          // ����ID
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getNendo());            // �N�x
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getKaisu());            // ��
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getJigyoName());        // ���Ɩ�
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getShinseishaId());     // �\����ID
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getSakuseiDate());      // �\�����쐬��
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getShoninDate());       // �������ԏ��F��
// 2006/07/26 dyh add start ���R�FDB�ŗ̈��\�Ҋm����ǉ�
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getRyoikiKakuteiDate());// �̈��\�Ҋm���
// 2006/07/26 dyh add end
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getJyuriDate());        // �w�U�󗝓�

        //---�\���ҁi������\�ҁj
        DaihyouInfo daihyouInfo = dataInfo.getDaihyouInfo();
        DatabaseUtil.setParameter(preparedStatement,index++, daihyouInfo.getNameKanjiSei());     // �\���Ҏ����i������-���j
        DatabaseUtil.setParameter(preparedStatement,index++, daihyouInfo.getNameKanjiMei());     // �\���Ҏ����i������-���j
        DatabaseUtil.setParameter(preparedStatement,index++, daihyouInfo.getNameKanaSei());      // �\���Ҏ����i�J�i-���j
        DatabaseUtil.setParameter(preparedStatement,index++, daihyouInfo.getNameKanaMei());      // �\���Ҏ����i�J�i-���j
        DatabaseUtil.setParameter(preparedStatement,index++, daihyouInfo.getNameRoSei());        // �\���Ҏ����i���[�}��-���j
        DatabaseUtil.setParameter(preparedStatement,index++, daihyouInfo.getNameRoMei());        // �\���Ҏ����i���[�}��-���j
        DatabaseUtil.setParameter(preparedStatement,index++, daihyouInfo.getNenrei());           // �N��
        DatabaseUtil.setParameter(preparedStatement,index++, daihyouInfo.getKenkyuNo());         // �\���Ҍ����Ҕԍ�
        DatabaseUtil.setParameter(preparedStatement,index++, daihyouInfo.getShozokuCd());        // �����@�փR�[�h
        DatabaseUtil.setParameter(preparedStatement,index++, daihyouInfo.getShozokuName());      // �����@�֖�
        DatabaseUtil.setParameter(preparedStatement,index++, daihyouInfo.getShozokuNameRyaku()); // �����@�֖��i���́j
        DatabaseUtil.setParameter(preparedStatement,index++, daihyouInfo.getBukyokuCd());        // ���ǃR�[�h
        DatabaseUtil.setParameter(preparedStatement,index++, daihyouInfo.getBukyokuName());      // ���ǖ�
        DatabaseUtil.setParameter(preparedStatement,index++, daihyouInfo.getBukyokuNameRyaku()); // ���ǖ��i���́j
        DatabaseUtil.setParameter(preparedStatement,index++, daihyouInfo.getShokushuCd());       // �E���R�[�h
        DatabaseUtil.setParameter(preparedStatement,index++, daihyouInfo.getShokushuNameKanji());// �E���i�a���j
        DatabaseUtil.setParameter(preparedStatement,index++, daihyouInfo.getShokushuNameRyaku());// �E���i���́j
        DatabaseUtil.setParameter(preparedStatement,index++, daihyouInfo.getZip());              // �X�֔ԍ�
        DatabaseUtil.setParameter(preparedStatement,index++, daihyouInfo.getAddress());          // �Z��
        DatabaseUtil.setParameter(preparedStatement,index++, daihyouInfo.getTel());              // TEL
        DatabaseUtil.setParameter(preparedStatement,index++, daihyouInfo.getFax());              // FAX
        DatabaseUtil.setParameter(preparedStatement,index++, daihyouInfo.getEmail());            // E-mail
//2006/06/30 �c�@�ǉ���������
        DatabaseUtil.setParameter(preparedStatement,index++, daihyouInfo.getUrl());              // URL
//2006/06/30�@�c�@�ǉ������܂�        
        DatabaseUtil.setParameter(preparedStatement,index++, daihyouInfo.getSenmon());           // ���݂̐��
        DatabaseUtil.setParameter(preparedStatement,index++, daihyouInfo.getGakui());            // �w��
        DatabaseUtil.setParameter(preparedStatement,index++, daihyouInfo.getBuntan());           // �������S

        //---�����ۑ�
        KadaiInfo kadaiInfo = dataInfo.getKadaiInfo();
        DatabaseUtil.setParameter(preparedStatement,index++, kadaiInfo.getKadaiNameKanji());     // �����ۑ薼(�a���j
        DatabaseUtil.setParameter(preparedStatement,index++, kadaiInfo.getKadaiNameEigo());      // �����ۑ薼(�p���j
        DatabaseUtil.setParameter(preparedStatement,index++, kadaiInfo.getJigyoKubun());         // ���Ƌ敪
        DatabaseUtil.setParameter(preparedStatement,index++, kadaiInfo.getShinsaKubun());        // �R���敪
        DatabaseUtil.setParameter(preparedStatement,index++, kadaiInfo.getShinsaKubunMeisho());  // �R���敪����
        DatabaseUtil.setParameter(preparedStatement,index++, kadaiInfo.getBunkatsuNo());         // �����ԍ�
        DatabaseUtil.setParameter(preparedStatement,index++, kadaiInfo.getBunkatsuNoMeisho());   // �����ԍ�����
        DatabaseUtil.setParameter(preparedStatement,index++, kadaiInfo.getKenkyuTaisho());       // �����Ώۂ̗ތ^
        DatabaseUtil.setParameter(preparedStatement,index++, kadaiInfo.getKeiNameNo());          // �n���̋敪�ԍ�
        DatabaseUtil.setParameter(preparedStatement,index++, kadaiInfo.getKeiName());            // �n���̋敪
        DatabaseUtil.setParameter(preparedStatement,index++, kadaiInfo.getKeiNameRyaku());       // �n���̋敪�i���́j
        DatabaseUtil.setParameter(preparedStatement,index++, kadaiInfo.getBunkaSaimokuCd());     // �זڔԍ�
        DatabaseUtil.setParameter(preparedStatement,index++, kadaiInfo.getBunya());              // ����
        DatabaseUtil.setParameter(preparedStatement,index++, kadaiInfo.getBunka());              // ����
        DatabaseUtil.setParameter(preparedStatement,index++, kadaiInfo.getSaimokuName());        // �ז�
        DatabaseUtil.setParameter(preparedStatement,index++, kadaiInfo.getBunkaSaimokuCd2());    // �זڔԍ�2
        DatabaseUtil.setParameter(preparedStatement,index++, kadaiInfo.getBunya2());             // ����2
        DatabaseUtil.setParameter(preparedStatement,index++, kadaiInfo.getBunka2());             // ����2
        DatabaseUtil.setParameter(preparedStatement,index++, kadaiInfo.getSaimokuName2());       // �ז�2
        DatabaseUtil.setParameter(preparedStatement,index++, kadaiInfo.getKantenNo());           // ���E�̊ϓ_�ԍ�
        DatabaseUtil.setParameter(preparedStatement,index++, kadaiInfo.getKanten());             // ���E�̊ϓ_
        DatabaseUtil.setParameter(preparedStatement,index++, kadaiInfo.getKantenRyaku());        // ���E�̊ϓ_����
        
//      2005/04/13 �ǉ� ��������----------
//      ���R:�����ԍ��ǉ��̂���

        DatabaseUtil.setParameter(preparedStatement,index++, kadaiInfo.getEdition());            // ��
        
//      2005/04/13 �ǉ� �����܂�----------
        
        //---�����o��
        KenkyuKeihiSoukeiInfo soukeiInfo = dataInfo.getKenkyuKeihiSoukeiInfo();
//2006/07/03 �c�@�C����������
//        KenkyuKeihiInfo[] keihiInfo = soukeiInfo.getKenkyuKeihi();
//        for(int j=0; j<keihiInfo.length; j++){
//            DatabaseUtil.setParameter(preparedStatement,index++, keihiInfo[j].getKeihi());
//            DatabaseUtil.setParameter(preparedStatement,index++, keihiInfo[j].getBihinhi());
//            DatabaseUtil.setParameter(preparedStatement,index++, keihiInfo[j].getShomohinhi());
//            DatabaseUtil.setParameter(preparedStatement,index++, keihiInfo[j].getKokunairyohi());
//            DatabaseUtil.setParameter(preparedStatement,index++, keihiInfo[j].getGaikokuryohi());
//            DatabaseUtil.setParameter(preparedStatement,index++, keihiInfo[j].getRyohi());
//            DatabaseUtil.setParameter(preparedStatement,index++, keihiInfo[j].getShakin());
//            DatabaseUtil.setParameter(preparedStatement,index++, keihiInfo[j].getSonota());
//        }
        if(IJigyoCd.JIGYO_CD_TOKUTEI_SINKI.equals(dataInfo.getJigyoCd())){
            KenkyuKeihiInfo[] keihiInfo6 = soukeiInfo.getKenkyuKeihi6();
            for (int j = 0; j < keihiInfo6.length; j++) {
                DatabaseUtil.setParameter(preparedStatement, index++, keihiInfo6[j].getKeihi());// �����o��
                DatabaseUtil.setParameter(preparedStatement, index++, keihiInfo6[j].getBihinhi());// �ݔ����i��
                DatabaseUtil.setParameter(preparedStatement, index++, keihiInfo6[j].getShomohinhi());// ���Օi��
                DatabaseUtil.setParameter(preparedStatement, index++, keihiInfo6[j].getKokunairyohi());// ��������
                DatabaseUtil.setParameter(preparedStatement, index++, keihiInfo6[j].getGaikokuryohi());// �O������
                DatabaseUtil.setParameter(preparedStatement, index++, keihiInfo6[j].getRyohi());// ����
                DatabaseUtil.setParameter(preparedStatement, index++, keihiInfo6[j].getShakin());// �Ӌ���
                DatabaseUtil.setParameter(preparedStatement, index++, keihiInfo6[j].getSonota());// ���̑�
          }
        } else {
            KenkyuKeihiInfo[] keihiInfo = soukeiInfo.getKenkyuKeihi();
            for (int j = 0; j < keihiInfo.length; j++) {
                DatabaseUtil.setParameter(preparedStatement, index++, keihiInfo[j].getKeihi());// �����o��
                DatabaseUtil.setParameter(preparedStatement, index++, keihiInfo[j].getBihinhi());// �ݔ����i��
                DatabaseUtil.setParameter(preparedStatement, index++, keihiInfo[j].getShomohinhi());// ���Օi��
                DatabaseUtil.setParameter(preparedStatement, index++, keihiInfo[j].getKokunairyohi());// ��������
                DatabaseUtil.setParameter(preparedStatement, index++, keihiInfo[j].getGaikokuryohi());// �O������
                DatabaseUtil.setParameter(preparedStatement, index++, keihiInfo[j].getRyohi());// ����
                DatabaseUtil.setParameter(preparedStatement, index++, keihiInfo[j].getShakin());// �Ӌ���
                DatabaseUtil.setParameter(preparedStatement, index++, keihiInfo[j].getSonota());// ���̑�
            }
            DatabaseUtil.setParameter(preparedStatement, index++, 0);// 
            DatabaseUtil.setParameter(preparedStatement, index++, 0);// 
            DatabaseUtil.setParameter(preparedStatement, index++, 0);// 
            DatabaseUtil.setParameter(preparedStatement, index++, 0);// 
            DatabaseUtil.setParameter(preparedStatement, index++, 0);// 
            DatabaseUtil.setParameter(preparedStatement, index++, 0);// 
            DatabaseUtil.setParameter(preparedStatement, index++, 0);// 
            DatabaseUtil.setParameter(preparedStatement, index++, 0);// 
        }
//2006/07/03�@�c�@�C�������܂�        
        DatabaseUtil.setParameter(preparedStatement,index++, soukeiInfo.getKeihiTotal());// ���v-�����o��
        DatabaseUtil.setParameter(preparedStatement,index++, soukeiInfo.getBihinhiTotal());// ���v-�ݔ����i��
        DatabaseUtil.setParameter(preparedStatement,index++, soukeiInfo.getShomohinhiTotal());// ���v-���Օi��
        DatabaseUtil.setParameter(preparedStatement,index++, soukeiInfo.getKokunairyohiTotal());// ���v-��������
        DatabaseUtil.setParameter(preparedStatement,index++, soukeiInfo.getGaikokuryohiTotal());// ���v-�O������
        DatabaseUtil.setParameter(preparedStatement,index++, soukeiInfo.getRyohiTotal());// ���v-����
        DatabaseUtil.setParameter(preparedStatement,index++, soukeiInfo.getShakinTotal());// ���v-�Ӌ���
        DatabaseUtil.setParameter(preparedStatement,index++, soukeiInfo.getSonotaTotal());// ���v-���̑�

        //---��{���i���Ձj
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getSoshikiKeitaiNo());     // �����g�D�̌`�Ԕԍ�
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getSoshikiKeitai());       // �����g�D�̌`��
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getBuntankinFlg());        // ���S���̗L��
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getKoyohi());              // �����x���Ҍٗp�o��
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getKenkyuNinzu());         // �����Ґ�
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getTakikanNinzu());        // ���@�ւ̕��S�Ґ�
        //2005/03/31 �ǉ� ----------------------------------------------------��������
        //���R �������͎ҏ��̓��͗���ǉ���������
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getKyoryokushaNinzu());    // �������͎Ґ�
        //2005/03/31 �ǉ� ----------------------------------------------------�����܂�
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getShinseiKubun());        // �V�K�p���敪
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getKadaiNoKeizoku());      // �p�����̌����ۑ�ԍ�
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getShinseiFlgNo());        // �����v��ŏI�N�x�O�N�x�̉���
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getShinseiFlg());          // �\���̗L��
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getKadaiNoSaisyu());       // �ŏI�N�x�ۑ�ԍ�
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getKaijikiboFlgNo());      // �J����]�̗L���ԍ�
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getKaijiKiboFlg());        // �J����]�̗L��
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getKaigaibunyaCd());       // �C�O����R�[�h
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getKaigaibunyaName());     // �C�O���얼��
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getKaigaibunyaNameRyaku());// �C�O���얼�̗���

        //---�֘A����̌�����
        KanrenBunyaKenkyushaInfo[] kanrenInfo = dataInfo.getKanrenBunyaKenkyushaInfo();
        for(int j=0; j<kanrenInfo.length; j++){
            DatabaseUtil.setParameter(preparedStatement,index++, kanrenInfo[j].getKanrenShimei());   // �֘A����̌�����-����
            DatabaseUtil.setParameter(preparedStatement,index++, kanrenInfo[j].getKanrenKikan());    // �֘A����̌�����-�����@��
            DatabaseUtil.setParameter(preparedStatement,index++, kanrenInfo[j].getKanrenBukyoku());  // �֘A����̌�����-��������
            DatabaseUtil.setParameter(preparedStatement,index++, kanrenInfo[j].getKanrenShoku());    // �֘A����̌�����-�E��
            DatabaseUtil.setParameter(preparedStatement,index++, kanrenInfo[j].getKanrenSenmon());   // �֘A����̌�����-��啪��
            DatabaseUtil.setParameter(preparedStatement,index++, kanrenInfo[j].getKanrenTel());      // �֘A����̌�����-�Ζ���d�b�ԍ�
            DatabaseUtil.setParameter(preparedStatement,index++, kanrenInfo[j].getKanrenJitakuTel());// �֘A����̌�����-����d�b�ԍ�
            DatabaseUtil.setParameter(preparedStatement,index++, kanrenInfo[j].getKanrenMail());     // �֘A����̌�����-Email
        }

        //---��{���i�㔼�j
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getXmlPath());        // XML�̊i�[�p�X
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getPdfPath());        // PDF�̊i�[�p�X
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getJuriKekka());      // �󗝌���
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getJuriBiko());       // �󗝌��ʔ��l
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getSeiriNo());        // �󗝐����ԍ�
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getSuisenshoPath());  // ���E���̊i�[�p�X
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getKekka1Abc());      // �P���R������(ABC)
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getKekka1Ten());      // �P���R������(�_��)
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getKekka1TenSorted());// �P���R�����ʁi�_�����j
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getShinsa1Biko());    // �P���R�����l
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getKekka2());         // �Q���R������
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getSouKehi());        // ���o��i�w�U���́j
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getShonenKehi());     // ���N�x�o��i�w�U���́j
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getShinsa2Biko());    // �Ɩ��S���ҋL����
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getJokyoId());        // �\����ID
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getSaishinseiFlg());  // �Đ\���t���O
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getDelFlg());         // �폜�t���O

// 20050530 Start ����̈�
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getKenkyuKubun());    // �v�挤���E���匤���E�I�������̈�敪
        if(IShinseiMaintenance.CHECK_ON.equals(dataInfo.getChangeFlg())){
            DatabaseUtil.setParameter(preparedStatement,index++, IShinseiMaintenance.CHECK_ON);
        }else{
            DatabaseUtil.setParameter(preparedStatement,index++, IShinseiMaintenance.CHECK_OFF);
        }
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getRyouikiNo());       // �̈�ԍ�
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getRyouikiRyakuName());// �̈旪�̖�
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getRyouikiKoumokuNo());// �������ڔԍ�
//2006/06/16 �c�@�C����������        
//      if(IShinseiMaintenance.CHECK_ON.equals(dataInfo.getChouseiFlg())){
//          DatabaseUtil.setParameter(preparedStatement,index++, IShinseiMaintenance.CHECK_ON);}
//      else{DatabaseUtil.setParameter(preparedStatement,index++, IShinseiMaintenance.CHECK_OFF);}
        if(IJigyoCd.JIGYO_CD_TOKUTEI_SINKI.equals(dataInfo.getJigyoCd())){
            if(StringUtil.isBlank(dataInfo.getChouseiFlg())){
                DatabaseUtil.setParameter(preparedStatement,index++, "0");// 
            } else if (IShinseiMaintenance.CHECK_ON.equals(dataInfo.getChouseiFlg())){
                DatabaseUtil.setParameter(preparedStatement,index++, IShinseiMaintenance.CHECK_ON);
            } else {
                DatabaseUtil.setParameter(preparedStatement,index++, IShinseiMaintenance.CHECK_OFF);
            }
        }else {
            if(IShinseiMaintenance.CHECK_ON.equals(dataInfo.getChouseiFlg())){
                DatabaseUtil.setParameter(preparedStatement,index++, IShinseiMaintenance.CHECK_ON);
            } else {
                DatabaseUtil.setParameter(preparedStatement,index++, IShinseiMaintenance.CHECK_OFF);
            }
        }
//2006/06/16�@�c�@�C�������܂�        
// Horikoshi End
        
// 20050713 �L�[���[�h�ǉ�
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getKigou());           // �L��
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getKeyName());         // �L�[���[�h(����)
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getKeyOtherName());    // �זڕ\�ȊO�̃L�[���[�h(����)
// Horikoshi

// 20050803 ��c��A�����̒ǉ�
        DatabaseUtil.setParameter(preparedStatement,index++, soukeiInfo.getMeetingCost());   // ��c��
        DatabaseUtil.setParameter(preparedStatement,index++, soukeiInfo.getPrintingCost());  // �����
// Horikoshi
        
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getSaiyoDate());       //�̗p�N����
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getKinmuHour());       //�Ζ����Ԑ�
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getNaiyakugaku());     //���ʌ�������������z
//2007/02/02 �c�@�ǉ���������
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getShoreihiNoNendo());  //���ʌ����������ۑ�ԍ�-�N�x
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getShoreihiNoSeiri());  //���ʌ����������ۑ�ԍ�-�����ԍ�
//2007/02/02�@�c�@�ǉ������܂�   
//2006/02/13 Start
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getOuboShikaku());     //���厑�i
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getSikakuDate());      //���i�擾�N����
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getSyuTokumaeKikan()); //���i�擾�O�@�֖�
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getIkukyuStartDate()); //��x���J�n��
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getIkukyuEndDate());   //��x���I����
// Nae End  

//2006/02/15
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getShinsaRyoikiCd());  //�R���̈�R�[�h
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getShinsaRyoikiName());//�R���̈於��
// syuu End
        
// ADD START 2007-07-26 BIS ���u��
        String naiyaku1 = dataInfo.getKenkyuKeihiSoukeiInfo().getKenkyuKeihi()[0].getNaiyaku();
        String naiyaku2 = dataInfo.getKenkyuKeihiSoukeiInfo().getKenkyuKeihi()[1].getNaiyaku();
        String naiyaku3 = dataInfo.getKenkyuKeihiSoukeiInfo().getKenkyuKeihi()[2].getNaiyaku();
        String naiyaku4 = dataInfo.getKenkyuKeihiSoukeiInfo().getKenkyuKeihi()[3].getNaiyaku();
        String naiyaku5 = dataInfo.getKenkyuKeihiSoukeiInfo().getKenkyuKeihi()[4].getNaiyaku();
        
        naiyaku1 = (naiyaku1 == null || "".equals(naiyaku1)) ? "0" : naiyaku1;
        naiyaku2 = (naiyaku2 == null || "".equals(naiyaku2)) ? "0" : naiyaku2;
        naiyaku3 = (naiyaku3 == null || "".equals(naiyaku3)) ? "0" : naiyaku3;
        naiyaku4 = (naiyaku4 == null || "".equals(naiyaku4)) ? "0" : naiyaku4;
        naiyaku5 = (naiyaku5 == null || "".equals(naiyaku5)) ? "0" : naiyaku5;
        
        DatabaseUtil.setParameter(preparedStatement,index++, naiyaku1);//1�N�ړ���z
        DatabaseUtil.setParameter(preparedStatement,index++, naiyaku2);//2�N�ړ���z
        DatabaseUtil.setParameter(preparedStatement,index++, naiyaku3);//3�N�ړ���z
        DatabaseUtil.setParameter(preparedStatement,index++, naiyaku4);//4�N�ړ���z
        DatabaseUtil.setParameter(preparedStatement,index++, naiyaku5);//5�N�ړ���z
// ADD END 2007-07-26 BIS ���u��
        return index;
    }

    /**
     * 
     * @param count
     * @return String
     */
    private String getQuestionMark(int count){
        StringBuffer buf = new StringBuffer("?");
        for(int i=1; i<count; i++){
            buf.append(",?");
        }
        return buf.toString();
    }

    /**
     * 
     * @param array
     * @return
     */
    private static String changeArray2CSV(String[] array){
        return StringUtil.changeArray2CSV(array, true);
    }

    /**
     * 
     * @param array
     * @return
     */
    private static String changeIterator2CSV(Iterator ite){
        return StringUtil.changeIterator2CSV(ite, true);
    }   

    /**
     * 
     * @param array
     * @return
     */
    private static String changeArray2CSV(int[] array){
        String[] strArray = new String[array.length];
        for(int i=0; i<strArray.length; i++){
            strArray[i] = String.valueOf(array[i]);
        }
        return changeArray2CSV(strArray);
    }

    /**
     * �\�����������I�u�W�F�N�g���猟���������擾��SQL�̖₢���킹�����𐶐�����B
     * ���������₢���킹�����͑������̕�����̌��Ɍ��������B
     * @param select      
     * @param searchInfo
     * @return
     */
    protected static String getQueryString(String select, ShinseiSearchInfo searchInfo) {

        //-----���������I�u�W�F�N�g�̓��e��SQL�Ɍ������Ă���-----
        StringBuffer query = new StringBuffer(select);
        //�V�X�e����t�ԍ�
        if(searchInfo.getSystemNo() != null && searchInfo.getSystemNo().length() != 0){
            query.append(" AND A.SYSTEM_NO = '")
                 .append(EscapeUtil.toSqlString(searchInfo.getSystemNo()))
                 .append("'");
        }
        //�\���ԍ�
        if(searchInfo.getUketukeNo() != null && searchInfo.getUketukeNo().length() != 0){
            query.append(" AND A.UKETUKE_NO = '")
                 .append(EscapeUtil.toSqlString(searchInfo.getUketukeNo()))         
                 .append("'");
        }
        //����ID
        if(searchInfo.getJigyoId() != null && searchInfo.getJigyoId().length() != 0){
            query.append(" AND A.JIGYO_ID = '")
                 .append(EscapeUtil.toSqlString(searchInfo.getJigyoId()))
                 .append("'");          
        }
        //����CD�i����ID��3�����ڂ���5�������j
        if(searchInfo.getJigyoCd() != null && searchInfo.getJigyoCd().length() != 0){
            query.append(" AND SUBSTR(A.JIGYO_ID, 3, 5) = '")
                 .append(EscapeUtil.toSqlString(searchInfo.getJigyoCd()))
                 .append("'");          
        }
        //�S������CD�i����ID��3�����ڂ���5�������j
        if(searchInfo.getTantoJigyoCd() != null && searchInfo.getTantoJigyoCd().size() != 0){
            query.append(" AND SUBSTR(A.JIGYO_ID, 3, 5) IN (")
                 .append(changeIterator2CSV(searchInfo.getTantoJigyoCd().iterator()))
                 .append(")");
        }
        //���Ɩ�
        if(searchInfo.getJigyoName() != null && searchInfo.getJigyoName().length() != 0){
            query.append(" AND A.JIGYO_NAME = '")
                 .append(EscapeUtil.toSqlString(searchInfo.getJigyoName()))
                 .append("'");          
        }
        //�N�x
        if(searchInfo.getNendo() != null && searchInfo.getNendo().length() != 0){
            query.append(" AND A.NENDO = '")
                 .append(EscapeUtil.toSqlString(searchInfo.getNendo()))
                 .append("'");          
        }       
        //��
        if(searchInfo.getKaisu() != null && searchInfo.getKaisu().length() != 0){
            query.append(" AND A.KAISU = '")
                 .append(EscapeUtil.toSqlString(searchInfo.getKaisu()))
                 .append("'");          
        }       
        //�\����ID
        if(searchInfo.getShinseishaId() != null && searchInfo.getShinseishaId().length() != 0){
            query.append(" AND A.SHINSEISHA_ID = '")
                 .append(EscapeUtil.toSqlString(searchInfo.getShinseishaId()))
                 .append("'");          
        }       
        //�\���Җ��i�����F���j�i������v�j
        if(searchInfo.getNameKanjiSei() != null && searchInfo.getNameKanjiSei().length() != 0){
            query.append(" AND A.NAME_KANJI_SEI like '%")
                 .append(EscapeUtil.toSqlString(searchInfo.getNameKanjiSei()))
                 .append("%'");         
        }               
        //�\���Җ��i�����F���j�i������v�j
        if(searchInfo.getNameKanjiMei() != null && searchInfo.getNameKanjiMei().length() != 0){
            query.append(" AND A.NAME_KANJI_MEI like '%")
                 .append(EscapeUtil.toSqlString(searchInfo.getNameKanjiMei()))
                 .append("%'");         
        }
        //�\���Җ��i�J�i�F���j�i������v�j
        if(searchInfo.getNameKanaSei() != null && searchInfo.getNameKanaSei().length() != 0){
            query.append(" AND A.NAME_KANA_SEI like '%")
                 .append(EscapeUtil.toSqlString(searchInfo.getNameKanaSei()))
                 .append("%'");         
        }                       
        //�\���Җ��i�J�i�F���j�i������v�j
        if(searchInfo.getNameKanaMei() != null && searchInfo.getNameKanaMei().length() != 0){
            query.append(" AND A.NAME_KANA_MEI like '%")
                 .append(EscapeUtil.toSqlString(searchInfo.getNameKanaMei()))
                 .append("%'");         
        }                       
        //�\���Җ��i���[�}���F���j�i������v�j
        if(searchInfo.getNameRoSei() != null && searchInfo.getNameRoSei().length() != 0){
            query.append(" AND UPPER(A.NAME_RO_SEI) like '%")
                 .append(EscapeUtil.toSqlString(searchInfo.getNameRoSei().toUpperCase()))
                 .append("%'");         
        }                       
        //�\���Җ��i���[�}���F���j�i������v�j
        if(searchInfo.getNameRoMei() != null && searchInfo.getNameRoMei().length() != 0){
            query.append(" AND UPPER(A.NAME_RO_MEI) like '%")
                 .append(EscapeUtil.toSqlString(searchInfo.getNameRoMei().toUpperCase()))
                 .append("%'");         
        }
        //�\���Ҍ����Ҕԍ�
        if(searchInfo.getKenkyuNo() != null && searchInfo.getKenkyuNo().length() != 0){
            query.append(" AND A.KENKYU_NO = '")
                 .append(EscapeUtil.toSqlString(searchInfo.getKenkyuNo()))
                 .append("'");          
        }
        //���Ƌ敪
        if(searchInfo.getJigyoKubun() != null && searchInfo.getJigyoKubun().size() != 0){
            query.append(" AND A.JIGYO_KUBUN IN (")
                 .append(changeIterator2CSV(searchInfo.getJigyoKubun().iterator()))
                 .append(")");
        }
        //�����@�փR�[�h
        if(searchInfo.getShozokuCd() != null && searchInfo.getShozokuCd().length() != 0){
            query.append(" AND A.SHOZOKU_CD = '")
                 .append(EscapeUtil.toSqlString(searchInfo.getShozokuCd()))
                 .append("'");          
        }
        //2006/06/02 jzx�@add start
        //���������@�֖�(����)
        if(searchInfo.getShozokNm() != null && searchInfo.getShozokNm().length() != 0){
            query.append("AND A.SHOZOKU_NAME_RYAKU like '%")
                 .append(EscapeUtil.toSqlString(searchInfo.getShozokNm()))
                 .append("%' ");            
        }   
        //2006/06/02 jzx�@add end
        //�n���̋敪�ԍ�
        if(searchInfo.getKeiNameNo() != null && searchInfo.getKeiNameNo().length() != 0){
            query.append(" AND A.KEI_NAME_NO = '")
                 .append(EscapeUtil.toSqlString(searchInfo.getKeiNameNo()))
                 .append("'");          
        }       
        //�n���̋敪�܂��͌n���̋敪�i���́j
        if(searchInfo.getKeiName() != null && searchInfo.getKeiName().length() != 0){
            String tmp = EscapeUtil.toSqlString(searchInfo.getKeiName());
            query.append(" AND (A.KEI_NAME like '%")
                 .append(tmp)
                 .append("%'")
                 .append(" OR A.KEI_NAME_RYAKU like '%")
                 .append(tmp)
                 .append("%')");            
        }
        //�זڔԍ�1�܂��͍זڔԍ�2
        if(searchInfo.getBunkasaimokuCd() != null && searchInfo.getBunkasaimokuCd().length() != 0){
            String tmp = EscapeUtil.toSqlString(searchInfo.getBunkasaimokuCd());
            query.append(" AND (A.BUNKASAIMOKU_CD = '")
                 .append(tmp)
                 .append("'")
                 .append(" OR A.BUNKASAIMOKU_CD2 = '")
                 .append(tmp)
                 .append("')");         
        }               
        //���E�̊ϓ_�ԍ�
        if(searchInfo.getKantenNo() != null && searchInfo.getKantenNo().length() != 0){
            query.append(" AND A.KANTEN_NO = '")
                 .append(EscapeUtil.toSqlString(searchInfo.getKantenNo()))
                 .append("'");          
        }               
        //���E�̊ϓ_
        if(searchInfo.getKanten() != null && searchInfo.getKanten().length() != 0){
            query.append(" AND A.KANTEN = '")
                 .append(EscapeUtil.toSqlString(searchInfo.getKanten()))
                 .append("'");          
        }                       
        //���E�̊ϓ_����
        if(searchInfo.getKantenRyaku() != null && searchInfo.getKantenRyaku().length() != 0){
            query.append(" AND A.KANTEN_RYAKU = '")
                 .append(EscapeUtil.toSqlString(searchInfo.getKantenRyaku()))
                 .append("'");          
        }                       
        //�֘A����̌����Ҏ���1�`3�̂����ꂩ�i������v�j
        if(searchInfo.getKanrenShimei() != null && searchInfo.getKanrenShimei().length() != 0){
            String tmp = EscapeUtil.toSqlString(searchInfo.getKanrenShimei());
            query.append(" AND (A.KANREN_SHIMEI1 like '%")
                 .append(tmp)
                 .append("%'")
                 .append(" OR A.KANREN_SHIMEI2 like '%")
                 .append(tmp)
                 .append("%'")
                 .append(" OR A.KANREN_SHIMEI3 like '%")
                 .append(tmp)
                 .append("%')");            
        }                       
        //�\����
        if(searchInfo.getJokyoId() != null && searchInfo.getJokyoId().length != 0){
            query.append(" AND A.JOKYO_ID IN (")
                 .append(changeArray2CSV(searchInfo.getJokyoId()))
                 .append(")");          
        }
        //�Đ\���t���O
        if(searchInfo.getSaishinseiFlg() != null && searchInfo.getSaishinseiFlg().length != 0){
            query.append(" AND A.SAISHINSEI_FLG IN (")
                 .append(changeArray2CSV(searchInfo.getSaishinseiFlg()))
                 .append(")");          
        }                       
        //2���R������
        if(searchInfo.getKekka2() != null && searchInfo.getKekka2().length != 0){
            query.append(" AND A.KEKKA2 IN (")
                 .append(changeArray2CSV(searchInfo.getKekka2()))
                 .append(")");          
        }
        //�쐬���iFrom�j
        if(searchInfo.getSakuseiDateFrom() != null && searchInfo.getSakuseiDateFrom().length() != 0){
            query.append(" AND A.SAKUSEI_DATE >= TO_DATE('")
                 .append(EscapeUtil.toSqlString(searchInfo.getSakuseiDateFrom()))
                 .append("', 'YYYY/MM/DD') ");          
        }               
        //�쐬���iTo�j
        if(searchInfo.getSakuseiDateTo() != null && searchInfo.getSakuseiDateTo().length() != 0){
            query.append(" AND A.SAKUSEI_DATE <= TO_DATE('")
                 .append(EscapeUtil.toSqlString(searchInfo.getSakuseiDateTo()))
                 .append("', 'YYYY/MM/DD') ");          
        }                       
        //�����@�֏��F���iFrom�j
        if(searchInfo.getShoninDateFrom() != null && searchInfo.getShoninDateFrom().length() != 0){
            query.append(" AND A.SHONIN_DATE >= TO_DATE('")
                 .append(EscapeUtil.toSqlString(searchInfo.getShoninDateFrom()))
                 .append("', 'YYYY/MM/DD') ");          
        }                       
        //�����@�֏��F���iTo�j
        if(searchInfo.getShoninDateTo() != null && searchInfo.getShoninDateTo().length() != 0){
            query.append(" AND A.SHONIN_DATE <= TO_DATE('")
                 .append(EscapeUtil.toSqlString(searchInfo.getShoninDateTo()))
                 .append("', 'YYYY/MM/DD') ");          
        }
        //���ǃR�[�h
        if(searchInfo.getBukyokuCd() != null && searchInfo.getBukyokuCd().length() != 0){
            query.append(" AND A.BUKYOKU_CD = '")
                 .append(EscapeUtil.toSqlString(searchInfo.getBukyokuCd()))
                 .append("'");          
        }           
        //�g�ݍ��킹�X�e�[�^�X��
        if(searchInfo.getStatusSearchInfo() != null && searchInfo.getStatusSearchInfo().hasQuery()){
            query.append(" AND")
                 .append(searchInfo.getStatusSearchInfo().getQuery());
        }
        //�폜�t���O
        if(searchInfo.getDelFlg() != null && searchInfo.getDelFlg().length != 0){
            query.append(" AND A.DEL_FLG IN (")
                 .append(changeArray2CSV(searchInfo.getDelFlg()))
                 .append(")");      
        }       

        //�����ԍ��@2005/07/25
        if(searchInfo.getSeiriNo() != null && searchInfo.getSeiriNo().length() != 0){
            query.append(" AND A.JURI_SEIRI_NO LIKE '%")
                 .append(EscapeUtil.toSqlString(searchInfo.getSeiriNo()))
                 .append("%'");         
        }           

        //2005/9/2 ���������ǉ�
        //�V�K�E�p��
        if (searchInfo.getShinseiKubun() != null && searchInfo.getShinseiKubun().length() != 0){
            if (!"3".equals(searchInfo.getShinseiKubun()) ){
                query.append(" AND A.SHINSEI_KUBUN = '")
                     .append(EscapeUtil.toSqlString(searchInfo.getShinseiKubun()))
                     .append("'");
            }
            //�啝�ȕύX�̏ꍇ
            else{
                query.append(" AND A.OHABAHENKO = 1");
            }
        }
        //�����ԍ��i��Փ��j
        if (searchInfo.getBunkatsuNo() != null && searchInfo.getBunkatsuNo().length() != 0){
            query.append(" AND A.BUNKATSU_NO = '")
                 .append(EscapeUtil.toSqlString(searchInfo.getBunkatsuNo()))
                 .append("'");
        }
        //�ŏI�N�x�O�N�x����
        if (searchInfo.getShinseiFlgNo() != null && searchInfo.getShinseiFlgNo().length() != 0){
            query.append(" AND A.SHINSEI_FLG_NO = ")
                 .append(EscapeUtil.toSqlString(searchInfo.getShinseiFlgNo()))
                 ;
        }
        //�v�挤���E���匤���E�I�������̈�敪�i����j
        if (searchInfo.getKenkyuKubun() != null && searchInfo.getKenkyuKubun().length() != 0){
            query.append(" AND A.KENKYU_KUBUN = ")
                 .append(EscapeUtil.toSqlString(searchInfo.getKenkyuKubun()))
                 ;
        }
        //�v�挤���̂��������ǁi����j
        if (searchInfo.getChouseiFlg() != null && searchInfo.getChouseiFlg().length() != 0){
            query.append(" AND A.CHOSEIHAN = '")
                 .append(EscapeUtil.toSqlString(searchInfo.getChouseiFlg()))
                 .append("'");
        }
        //�̈�ԍ��i����j
        if (searchInfo.getRyouikiNo() != null && searchInfo.getRyouikiNo().length() != 0){
            query.append(" AND A.RYOIKI_NO = '")
                 .append(EscapeUtil.toSqlString(searchInfo.getRyouikiNo()))
                 .append("'");
        }
        //�������ڔԍ��i����j
        if (searchInfo.getRyouikiKoumokuNo() != null && searchInfo.getRyouikiKoumokuNo().length() != 0){
            query.append(" AND A.KOMOKU_NO = '")
                 .append(EscapeUtil.toSqlString(searchInfo.getRyouikiKoumokuNo()))
                 .append("'");
        }
        //���S���̔z���L��
        if (searchInfo.getBuntankinFlg() != null && searchInfo.getBuntankinFlg().length() != 0){
            query.append(" AND A.BUNTANKIN_FLG = '")
                 .append(EscapeUtil.toSqlString(searchInfo.getBuntankinFlg()))
                 .append("'");
        }
        //�J����]�̗L��
        if (searchInfo.getKaijiKiboFlg() != null && searchInfo.getKaijiKiboFlg().length() != 0){
            query.append(" AND A.KAIJIKIBO_FLG_NO = '")
                 .append(EscapeUtil.toSqlString(searchInfo.getKaijiKiboFlg()))
                 .append("'");
        }
        
        //-----���񏇏����w�肷��-----
        if(searchInfo.getOrder() != null && searchInfo.getOrder().length() != 0){
            query.append(" ORDER BY ")
                 .append(searchInfo.getOrder());
        }
        
        return query.toString();
    }

    /**
     * �\���f�[�^�L�[�����ΏۂƂȂ�IOD�t�@�C�����擾����B
     * @param connection            �R�l�N�V����
     * @param pkInfo                �\���f�[�^�L�[���
     * @return List                 �ϊ�����IOF�t�@�C�����X�g
     * @throws DataAccessException
     * @throws NoDataFoundException
     */
    public List getIodFilesToMerge(Connection connection, final ShinseiDataPk pkInfo)
            throws NoDataFoundException, DataAccessException {
    
        //1.�t�@�C���擾�pSQL��
        final StringBuffer query = new StringBuffer();
        query.append("SELECT 0 SEQ_NUM,0 SEQ_TENPU,D.PDF_PATH IOD_FILE_PATH FROM SHINSEIDATAKANRI"+ dbLink + " D WHERE D.SYSTEM_NO = ? ");
        query.append("UNION ALL ");
        //���ϊ�����Ă��Ȃ��Y�t�t�@�C���𖳎�����Ƃ��B
        //query.append("SELECT 1 SEQ_NUM,A.SEQ_TENPU SEQ_TENPU,A.PDF_PATH IOD_FILE_PATH FROM TENPUFILEINFO" + dbLink + " A WHERE A.SYSTEM_NO = ? AND PDF_PATH IS NOT NULL ");
        query.append("SELECT 1 SEQ_NUM,A.SEQ_TENPU SEQ_TENPU,A.PDF_PATH IOD_FILE_PATH FROM TENPUFILEINFO" + dbLink + " A WHERE A.SYSTEM_NO = ? ");
        query.append("ORDER BY SEQ_NUM,SEQ_TENPU");

        if(log.isDebugEnabled()){
            log.debug("�\���f�[�^�L�[��� '" + pkInfo);
        }
        
        //2.�����pSQL�쐬�I�u�W�F�N�g�𐶐�����B        
        PreparedStatementCreator creator = new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection conn)
                throws SQLException {
                PreparedStatement statement = conn.prepareStatement(query.toString());
                int i = 1;
                DatabaseUtil.setParameter(statement,i++,pkInfo.getSystemNo());
                DatabaseUtil.setParameter(statement,i++,pkInfo.getSystemNo());
                //�V�X�e���ԍ�
                return statement;
            }
        };
        //3.�\���f�[�^���ԃt�@�C���E�Y�t�t�@�C�����ԃt�@�C�����擾����B  
        final List iodFile = new ArrayList();
        
        //4.���������s����B
        RowCallbackHandler handler = new BaseCallbackHandler() {
            protected void processRow(ResultSet rs, int rowNum)
                throws SQLException, NoDataFoundException {
                String iodFilePath = rs.getString("IOD_FILE_PATH");
                if (iodFilePath == null || "".equals(iodFilePath)) {
                    throw new NoDataFoundException("�\���f�[�^IOD�t�@�C����񂪌�����܂���ł����B�����L�[�F�V�X�e����t�ԍ�'"
                    + pkInfo.getSystemNo()
                    + "'");
                } else {
                    iodFile.add(new File(iodFilePath));
                }
            }
        };
        new JDBCReading().query(connection,creator, handler);
        
        if (log.isDebugEnabled()) {
            for (Iterator iter = iodFile.iterator(); iter.hasNext();) {
                log.debug("�����Ώۃt�@�C��:" + iter.next());
            }
        }
        
        //5.�����t�@�C�����`�F�b�N����B
        if(iodFile.isEmpty()){
            throw new NoDataFoundException("��������t�@�C����������܂���ł����B");
        }
        return iodFile;
    }

    /**
     * �\���f�[�^����PDF,XML�t�@�C���p�X���X�V����B
     * @param connection            �R�l�N�V����
     * @param pkInfo                ��L�[���
     * @param iodFile               PDF�t�@�C��
     * @param xmlFile               XML�t�@�C��
     * @throws DataAccessException  �X�V�������̗�O�B
     * @throws NoDataFoundException�@�����Ώۃf�[�^���݂���Ȃ��Ƃ��B
     */
    public void updateFilePath(
            Connection connection,
            final ShinseiDataPk pkInfo,
            File iodFile,
            File xmlFile)
            throws DataAccessException, NoDataFoundException {

        //�Q�Ɖ\�\���f�[�^���`�F�b�N
        checkOwnShinseiData(connection, pkInfo);
        
        String updateQuery =
                "UPDATE SHINSEIDATAKANRI"+ dbLink 
                    + " SET"
                    + " PDF_PATH = ?,XML_PATH = ?"
                    + " WHERE SYSTEM_NO = ?";

        PreparedStatement preparedStatement = null;
        try {
            //�o�^
            preparedStatement = connection.prepareStatement(updateQuery);
            int i = 1;
            //PDF�t�@�C���p�X�B
            DatabaseUtil.setParameter(preparedStatement,i++,iodFile.getAbsolutePath());
            //XML�t�@�C���p�X�B
            DatabaseUtil.setParameter(preparedStatement,i++,xmlFile.getAbsolutePath());
            //�V�X�e����t�ԍ�
            DatabaseUtil.setParameter(preparedStatement,i++,pkInfo.getSystemNo());
            DatabaseUtil.executeUpdate(preparedStatement);

        } catch (SQLException ex) {
            throw new DataAccessException(
                "�\�����PDF�t�@�C���p�X�X�V���ɗ�O���������܂����B �F�V�X�e����t�ԍ�'"
                    + pkInfo.getSystemNo()
                    + "'",
                ex);
        } finally {
            DatabaseUtil.closeResource(null, preparedStatement);
        }
    }   

    /**
     * �\���f�[�^���̐��E���t�@�C���p�X���X�V����B
     * @param connection
     * @param pkInfo
     * @param filePath
     * @throws DataAccessException
     * @throws NoDataFoundException
     */
    public void updateSuisenshoFilePath(
            Connection connection,
            final ShinseiDataPk pkInfo,
            String filePath)
            throws DataAccessException, NoDataFoundException {

        //�Q�Ɖ\�\���f�[�^���`�F�b�N
        checkOwnShinseiData(connection, pkInfo);
        
        String updateQuery =
                "UPDATE SHINSEIDATAKANRI"+ dbLink 
                    + " SET"
                    + " SUISENSHO_PATH  = ?"
                    + " WHERE SYSTEM_NO = ?"
                    ;
        PreparedStatement preparedStatement = null;
        try {
            //�X�V
            preparedStatement = connection.prepareStatement(updateQuery);
            int i = 1;
            DatabaseUtil.setParameter(preparedStatement,i++,filePath);            //���E���t�@�C���p�X
            DatabaseUtil.setParameter(preparedStatement,i++,pkInfo.getSystemNo());//�V�X�e����t�ԍ�
            DatabaseUtil.executeUpdate(preparedStatement);
        } catch (SQLException ex) {
            throw new DataAccessException(
                "�\����񐄑E���t�@�C���p�X�X�V���ɗ�O���������܂����B �F�V�X�e����t�ԍ�'"
                    + pkInfo.getSystemNo()
                    + "'",
                ex);
        } finally {
            DatabaseUtil.closeResource(null, preparedStatement);
        }
    }   
    
//  //---------------------------------------------------------------------
//  // --
//  //---------------------------------------------------------------------     
//  /**
//   * �w�茟�������ɊY������\���]���f�[�^���擾����B
//   * 
//   * @param connection
//   * @param searchInfo
//   * @return
//   * @throws DataAccessException
//   * @throws NoDataFoundException
//   * @throws ApplicationException  �\���]����񂪃Z�b�g����Ă��Ȃ������ꍇ
//   */
//  public Page searchCommentList(
//      Connection connection,
//      HyokaSearchInfo searchInfo)
//      throws DataAccessException , NoDataFoundException, ApplicationException
//  {
//      String select = "SELECT "
//                    + "A.SYSTEM_NO, "                                         //�V�X�e����t�ԍ�
//                    + "A.UKETUKE_NO, "                                        //�\���ԍ�
//                    + "A.KEI_NAME, "                                          //����n��
//                    + "A.NENDO, "                                             //�N�x
//                    + "A.KAISU, "                                             //��
//                    + "A.JIGYO_NAME, "                                        //���Ɩ�
//                    + "A.NAME_KANJI_SEI, "                                    //�\���Ҏ���(������-��)
//                    + "A.NAME_KANJI_MEI, "                                    //�\���Ҏ���(������-��)
//                    + "B.KEKKA_ABC, "                                         //�R������(ABC)
//                    + "B.SENMON_CHK, "                                        //���̈�`�F�b�N
//                    + "B.KEKKA_TEN, "                                         //�R������(�_��)
//                    + "B.COMMENTS, "                                          //�R�����g
//                    + "B.SHINSAIN_NO, "                                       //�R�����ԍ�
//                    + "B.SHINSAIN_NAME_KANJI_SEI, "                           //�R������(����-��)
//                    + "B.SHINSAIN_NAME_KANJI_MEI "                            //�R������(����-��)
//                    + "FROM SHINSEIDATAKANRI"+dbLink+" A, "
//                    + "SHINSAKEKKA"+dbLink+" B "
//                    + "WHERE A.SYSTEM_NO = B.SYSTEM_NO ";
//
//      
//      //-----���������I�u�W�F�N�g�̓��e��SQL�Ɍ������Ă���-----
//      StringBuffer query = new StringBuffer(select);
//      //���S��v��
//      //���Ɩ�(CD)
//      List jigyoList = searchInfo.getJigyoList();
//      if(jigyoList != null && jigyoList.size() != 0){
//          for(int i=0; i<jigyoList.size(); i++){
//              if(i == 0){
//                  query.append("AND (SUBSTR(A.JIGYO_ID,3,5) = '")
//                       .append(EscapeUtil.toSqlString((String)jigyoList.get(i)))
//                       .append("' ");
//              }else{
//                  query.append("OR SUBSTR(A.JIGYO_ID,3,5) = '")
//                       .append(EscapeUtil.toSqlString((String)jigyoList.get(i)))
//                       .append("' ");
//              }
//          }
//          query.append(") ");
//      }
//      //�N�x
//      if(searchInfo.getNendo() != null && searchInfo.getNendo().length() != 0){
//          query.append("AND A.NENDO = '")
//               .append(EscapeUtil.toSqlString(searchInfo.getNendo()))         
//               .append("' ");
//      }
//      //��
//      if(searchInfo.getKaisu() != null && searchInfo.getKaisu().length() != 0){
//          query.append("AND A.KAISU = '")
//               .append(EscapeUtil.toSqlString(searchInfo.getKaisu()))         
//               .append("' ");
//      }
//      //����E�n��
//      if(searchInfo.getBunya() != null && searchInfo.getBunya().length() != 0){
//          query.append("AND A.KEI_NAME = '")
//               .append(EscapeUtil.toSqlString(searchInfo.getBunya()))         
//               .append("' ");
//      }
//      //�\���ԍ�
//      if(searchInfo.getShinseiNo() != null && searchInfo.getShinseiNo().length() != 0){
//          query.append("AND A.UKETUKE_NO = '")
//               .append(EscapeUtil.toSqlString(searchInfo.getShinseiNo()))         
//               .append("' ");
//      }
//      //�]���iFrom�j�i�O����v�j
//      if(searchInfo.getHyokaFrom() != null && searchInfo.getHyokaFrom().length() != 0){
//          query.append("AND A.KEKKA1_ABC >= '")
//               .append(EscapeUtil.toSqlString(searchInfo.getHyokaFrom()))
//               .append("' ");         
//      }                       
//      //�]���iTo�j�i�O����v�j
//      if(searchInfo.getHyokaTo() != null && searchInfo.getHyokaTo().length() != 0){
//          String hyokaTo = searchInfo.getHyokaTo().toString();
//          //�w�肪3�������̏ꍇ�A3���ɂȂ�悤��F�ŕ⊮����
//          if(hyokaTo.length() == 1){
//              hyokaTo = hyokaTo + "FF";
//          }else if(hyokaTo.length() == 2){
//              hyokaTo = hyokaTo + "F";
//          }
//          query.append("AND A.KEKKA1_ABC <= '")
//               .append(EscapeUtil.toSqlString(hyokaTo))
//               .append("' ");         
//      }                       
//      //������v��
//      //�\���Җ��i�����F���j�i������v�j
//      if(searchInfo.getNameSei() != null && searchInfo.getNameSei().length() != 0){
//          query.append("AND A.NAME_KANJI_SEI like '%")
//               .append(EscapeUtil.toSqlString(searchInfo.getNameSei()))
//               .append("%' ");            
//      }               
//      //�\���Җ��i�����F���j�i������v�j
//      if(searchInfo.getNameMei() != null && searchInfo.getNameMei().length() != 0){
//          query.append("AND A.NAME_KANJI_MEI like '%")
//               .append(EscapeUtil.toSqlString(searchInfo.getNameMei()))
//               .append("%' ");            
//      }                       
//      //�\���Җ��i���[�}���F���j�i������v�j
//      if(searchInfo.getNameRoSei() != null && searchInfo.getNameRoSei().length() != 0){
//          query.append("AND UPPER(A.NAME_RO_SEI) like '%")
//               .append(EscapeUtil.toSqlString(searchInfo.getNameRoSei().toUpperCase()))
//               .append("%' ");            
//      }               
//      //�\���Җ��i���[�}���F���j�i������v�j
//      if(searchInfo.getNameRoMei() != null && searchInfo.getNameRoMei().length() != 0){
//          query.append("AND UPPER(A.NAME_RO_MEI) like '%")
//               .append(EscapeUtil.toSqlString(searchInfo.getNameRoMei().toUpperCase()))
//               .append("%' ");            
//      }                       
//      
//      query.append("ORDER BY ")
//           .append("LENGTH(NVL(A.KEKKA1_ABC,' ')) DESC,") //�\�����̐R������(ABC)�̐R�����ʐ��̍~��
//           .append("A.KEKKA1_ABC ASC, ")                  //�\�����̐R������(ABC)�̏���
//           .append("A.KEKKA1_TEN DESC, ")                 //�\�����̐R������(�_)�̍~��
//           .append("A.UKETUKE_NO ASC, ")                  //�\���ԍ��̏���
//           .append("B.KEKKA_ABC ASC, ")                   //�R�������Ƃ̐R������(ABC)�̏���
//           .append("B.KEKKA_TEN DESC")                    //�R�������Ƃ̐R������(�_)�̍~��
//           ;
//      
//      
//      //for debug
//      if(log.isDebugEnabled()){
//          log.debug("query:" + query);
//      }
//
//      // �y�[�W�擾
//      return SelectUtil.selectPageInfo(connection, (SearchInfo)searchInfo, query.toString());
//      
//  }   
//  
//  /**
//   * �w�茟�������ɊY������\���]���f�[�^���擾����B
//   * 
//   * @param connection
//   * @param searchInfo
//   * @return
//   * @throws DataAccessException
//   * @throws NoDataFoundException
//   * @throws ApplicationException  �\���]����񂪃Z�b�g����Ă��Ȃ������ꍇ
//   */
//  public Page searchHyokaList(
//      Connection connection,
//      HyokaSearchInfo searchInfo)
//      throws DataAccessException , NoDataFoundException, ApplicationException
//  {
//      String select = "SELECT "
//                    + "A.SYSTEM_NO, "                                         //�V�X�e���ԍ�
//                    + "A.KEKKA1_ABC, "                                        //�ꎞ�R������(ABC)
//                    + "A.KEKKA1_TEN, "                                        //�ꎞ�R������(�_��)
//                    + "A.BUNKASAIMOKU_CD, "                                   //���ȍזڃR�[�h
//                    + "A.BUNKA_NAME, "                                        //����
//                    + "A.SAIMOKU_NAME, "                                      //�ז�
//                    + "A.KEI_NAME, "                                          //����n��
//                    + "A.UKETUKE_NO, "                                        //�\���ԍ�
//                    + "A.NENDO, "                                             //�N�x
//                    + "A.KAISU, "                                             //��
//                    + "A.JIGYO_NAME, "                                        //���Ɩ�
//                    + "A.KADAI_NAME_KANJI, "                                  //�����ۑ薼���̓Z�~�i�[��
//                    + "A.NAME_KANJI_SEI, "                                    //�\���Ҏ���(������-��)
//                    + "A.NAME_KANJI_MEI, "                                    //�\���Ҏ���(������-��)
//                    + "A.SHOZOKU_NAME_RYAKU, "                                //�����@�֖�
//                    + "A.BUKYOKU_NAME, "                                      //���ǖ�
//                    + "A.SHOKUSHU_NAME_KANJI, "                               //�E�햼
//                    + "A.NINZU_KEI, "                                         //�Q���l�����v
//                    + "A.NINZU_NIHON, "                                       //���{�l��
//                    + "A.NAME_FAMILY_AITE, "                                  //���荑��\�Җ�(�t�@�~���[�l�[��)
//                    + "A.NAME_FIRST_AITE, "                                   //���荑��\�Җ�(�t�@�[�X�g�l�[��)
//                    + "A.SHOZOKU_NAME_KANJI_AITE, "                           //���荑��\�ҏ����@�֖�
//                    + "A.BUKYOKU_NAME_KANJI_AITE, "                           //���荑��\�ҕ��ǖ�
//                    + "A.SHOKUSHU_NAME_KANJI_AITE, "                          //���荑��\�ҐE��
//                    + "A.NINZU_AITE, "                                        //���荑�l��
//                    + "A.KIKAN_START1, "                                      //����1(�J�n)
//                    + "A.KIKAN_END1, "                                        //����1(�I��)
//                    + "A.JIGYO_ID, "                                          //����ID
//                    + "A.KIKAN_HI1, "                                         //����1(����)
//                    + "A.KIKAN_TUKI, "                                        //����1(����)
//                    + "A.KAISAICHI1, "                                        //�J�Òn1
//                    + "B.KEKKA_ABC, "                                         //�R������(ABC)
//                    + "B.SENMON_CHK, "                                        //���̈�`�F�b�N
//                    + "B.KEKKA_TEN, "                                         //�R������(�_��)
//                    + "B.SHINSAIN_NO, "                                       //�R�����ԍ�
//                    + "B.SHINSAIN_NAME_KANJI_SEI, "                           //�R������(����-��)
//                    + "B.SHINSAIN_NAME_KANJI_MEI "                            //�R������(����-��)
//                    + "FROM "
//                    + "SHINSEIDATAKANRI"+dbLink+" A, "
//                    + "SHINSAKEKKA"+dbLink+" B "
//                    + "WHERE A.SYSTEM_NO = B.SYSTEM_NO ";
//      
//      //-----���������I�u�W�F�N�g�̓��e��SQL�Ɍ������Ă���-----
//      StringBuffer query = new StringBuffer(select);
//      //���S��v��
//      //���Ɩ�(CD)
//      List jigyoList = searchInfo.getJigyoList();
//      if(jigyoList != null && jigyoList.size() != 0){
//          for(int i=0; i<jigyoList.size(); i++){
//              if(i == 0){
//                  query.append("AND (SUBSTR(A.JIGYO_ID,3,5) = '")
//                       .append(EscapeUtil.toSqlString((String)jigyoList.get(i)))
//                       .append("' ");
//              }else{
//                  query.append("OR SUBSTR(A.JIGYO_ID,3,5) = '")
//                       .append(EscapeUtil.toSqlString((String)jigyoList.get(i)))
//                       .append("' ");
//              }
//          }
//          query.append(") ");
//      }
//      //�N�x
//      if(searchInfo.getNendo() != null && searchInfo.getNendo().length() != 0){
//          query.append("AND A.NENDO = '")
//               .append(EscapeUtil.toSqlString(searchInfo.getNendo()))         
//               .append("' ");
//      }
//      //��
//      if(searchInfo.getKaisu() != null && searchInfo.getKaisu().length() != 0){
//          query.append("AND A.KAISU = '")
//               .append(EscapeUtil.toSqlString(searchInfo.getKaisu()))         
//               .append("' ");
//      }
//      //����E�n��
//      if(searchInfo.getBunya() != null && searchInfo.getBunya().length() != 0){
//          query.append("AND A.KEI_NAME = '")
//               .append(EscapeUtil.toSqlString(searchInfo.getBunya()))         
//               .append("' ");
//      }
//      //�\���ԍ�
//      if(searchInfo.getShinseiNo() != null && searchInfo.getShinseiNo().length() != 0){
//          query.append("AND A.UKETUKE_NO = '")
//               .append(EscapeUtil.toSqlString(searchInfo.getShinseiNo()))         
//               .append("' ");
//      }
//      //�]���iFrom�j�i�O����v�j
//      if(searchInfo.getHyokaFrom() != null && searchInfo.getHyokaFrom().length() != 0){
//          query.append("AND A.KEKKA1_ABC >= '")
//               .append(EscapeUtil.toSqlString(searchInfo.getHyokaFrom()))
//               .append("' ");         
//      }                       
//      //�]���iTo�j�i�O����v�j
//      if(searchInfo.getHyokaTo() != null && searchInfo.getHyokaTo().length() != 0){
//          String hyokaTo = searchInfo.getHyokaTo().toString();
//          //�w�肪3�������̏ꍇ�A3���ɂȂ�悤��F�ŕ⊮����
//          if(hyokaTo.length() == 1){
//              hyokaTo = hyokaTo + "FF";
//          }else if(hyokaTo.length() == 2){
//              hyokaTo = hyokaTo + "F";
//          }
//          query.append("AND A.KEKKA1_ABC <= '")
//               .append(EscapeUtil.toSqlString(hyokaTo))
//               .append("' ");         
//      }                       
//      //������v��
//      //�\���Җ��i�����F���j�i������v�j
//      if(searchInfo.getNameSei() != null && searchInfo.getNameSei().length() != 0){
//          query.append("AND A.NAME_KANJI_SEI like '%")
//               .append(EscapeUtil.toSqlString(searchInfo.getNameSei()))
//               .append("%' ");            
//      }               
//      //�\���Җ��i�����F���j�i������v�j
//      if(searchInfo.getNameMei() != null && searchInfo.getNameMei().length() != 0){
//          query.append("AND A.NAME_KANJI_MEI like '%")
//               .append(EscapeUtil.toSqlString(searchInfo.getNameMei()))
//               .append("%' ");            
//      }                       
//      //�\���Җ��i���[�}���F���j�i������v�j
//      if(searchInfo.getNameRoSei() != null && searchInfo.getNameRoSei().length() != 0){
//          query.append("AND UPPER(A.NAME_RO_SEI) like '%")
//               .append(EscapeUtil.toSqlString(searchInfo.getNameRoSei().toUpperCase()))
//               .append("%' ");            
//      }               
//      //�\���Җ��i���[�}���F���j�i������v�j
//      if(searchInfo.getNameRoMei() != null && searchInfo.getNameRoMei().length() != 0){
//          query.append("AND UPPER(A.NAME_RO_MEI) like '%")
//               .append(EscapeUtil.toSqlString(searchInfo.getNameRoMei().toUpperCase()))
//               .append("%' ");            
//      }                       
//
//      query.append("ORDER BY ")
//           .append("LENGTH(NVL(A.KEKKA1_ABC,' ')) DESC,") //�\�����̐R������(ABC)�̐R�����ʐ��̍~��
//           .append("A.KEKKA1_ABC ASC, ")                  //�\�����̐R������(ABC)�̏���
//           .append("A.KEKKA1_TEN DESC, ")                 //�\�����̐R������(�_)�̍~��
//           .append("A.UKETUKE_NO ASC, ")                  //�\���ԍ��̏���
//           .append("B.KEKKA_ABC ASC, ")                   //�R�������Ƃ̐R������(ABC)�̏���
//           .append("B.KEKKA_TEN DESC")                    //�R�������Ƃ̐R������(�_)�̍~��
//           ;
//      
//      
//      //for debug
//      if(log.isDebugEnabled()){
//          log.debug("query:" + query);
//      }
//
//      // �y�[�W�擾
//      return SelectUtil.selectPageInfo(connection, (SearchInfo)searchInfo, query.toString());
//      
//  }   

    /**
     * �w�茟�������ɊY������\���]���f�[�^���擾����(�Ȍ��p)�B
     * 
     * @param connection
     * @param searchInfo
     * @return
     * @throws DataAccessException
     * @throws NoDataFoundException
     * @throws ApplicationException  �\���]����񂪃Z�b�g����Ă��Ȃ������ꍇ
     */
    public Page searchHyokaList(
            Connection connection,
            HyokaSearchInfo searchInfo)
            throws DataAccessException, NoDataFoundException, ApplicationException {

        String select = "SELECT"
                      + " A.SYSTEM_NO"                  //�V�X�e���ԍ�
                      + ", A.NENDO"                     //�N�x
                      + ", A.KAISU"                     //��
                      + ", A.JIGYO_NAME"                //���Ɩ�
                      + ", A.KADAI_NAME_KANJI"          //�����ۑ薼
                      + ", A.UKETUKE_NO"                //�\���ԍ�
                      + ", A.NAME_KANJI_SEI"            //�\���Ҏ���(������-��)
                      + ", A.NAME_KANJI_MEI"            //�\���Ҏ���(������-��)
                      + ", A.SHOZOKU_NAME_RYAKU"        //�����@�֖�
                      + ", A.BUKYOKU_NAME_RYAKU"        //���ǖ�
                      + ", A.SHOKUSHU_NAME_RYAKU"       //�E�햼
                      + ", A.KEKKA1_ABC"                //�ꎞ�R������(ABC)
                      + ", A.KEKKA1_TEN"                //�ꎞ�R������(�_��)
                      + ", A.KANTEN_RYAKU"              //���E�̊ϓ_
                      + ", A.JIGYO_KUBUN"               //���Ƌ敪
                      + ", A.JURI_SEIRI_NO"             //�����ԍ��@2005/10/18�ǉ�
                      + " FROM"
                      + " SHINSEIDATAKANRI" + dbLink + " A"
                      
                      //�������A�R������1�l�����蓖�Ă��Ă��Ȃ��\���f�[�^��\�������Ȃ����߂̐���
                      //�R������1�l�ȏ㊄�蓖�Ă��Ă���SYSTEM_NO���������Ă���A����SYSTEM_NO�����������Ƃ���B
                      + " WHERE EXISTS"
                      + " (SELECT *"
                      + " FROM"
                      + " SHINSEIDATAKANRI" + dbLink + " A"
                      + ", SHINSAKEKKA" + dbLink + " B"
                      + " WHERE A.SYSTEM_NO = B.SYSTEM_NO"
                      + " AND A.JIGYO_KUBUN = B.JIGYO_KUBUN"
                      + " AND B.SHINSAIN_NO NOT LIKE '@%')"//�R������1�l�����蓖�Ă��Ă��Ȃ�(�S��@XXX)���ƈ���������Ȃ�

                      //2005.12.19 iso ����������Ɨ�
                      ;
//                    + " AND A.DEL_FLG = 0"
//                    + " AND A.JOKYO_ID IN ('08', '09', '10', '11', '12')";
//      
//      //-----���������I�u�W�F�N�g�̓��e��SQL�Ɍ������Ă���-----
//      StringBuffer query = new StringBuffer(select);
//      
//      //���S��v��
////        //���Ɩ�(CD)
////        LabelValueBean labelValueBean = new LabelValueBean();
////        List jigyoList = searchInfo.getJigyoList();
////        if(jigyoList != null && jigyoList.size() > 0) {
////            query.append(" AND SUBSTR(A.JIGYO_ID,3,5) IN (");
////            int i;
////            for(i = 0; i < jigyoList.size()-1; i++) {
////                labelValueBean = (LabelValueBean)jigyoList.get(i);
////                query.append("'")
////                     .append(EscapeUtil.toSqlString(labelValueBean.getValue()))
////                     .append("', ");
////            }
////            labelValueBean = (LabelValueBean)jigyoList.get(i);
////            query.append("'")
////                 .append(EscapeUtil.toSqlString(labelValueBean.getValue()))
////                 .append("')");
////        }
//      //�N�x
//      if(searchInfo.getNendo() != null && searchInfo.getNendo().length() != 0){
//          query.append(" AND A.NENDO = '")
//               .append(EscapeUtil.toSqlString(searchInfo.getNendo()))         
//               .append("'");
//      }
//      //��
//      if(searchInfo.getKaisu() != null && searchInfo.getKaisu().length() != 0){
//          query.append(" AND A.KAISU = '")
//               .append(EscapeUtil.toSqlString(searchInfo.getKaisu()))         
//               .append("'");
//      }
//      //�\���ԍ�
//      if(searchInfo.getUketukeNo() != null && searchInfo.getUketukeNo().length() != 0){
//          query.append(" AND A.UKETUKE_NO = '")
//               .append(EscapeUtil.toSqlString(searchInfo.getUketukeNo()))         
//               .append("'");
//      }
//      //�����@�փR�[�h
//      if(searchInfo.getShozokuCd() != null && searchInfo.getShozokuCd().length() != 0){
//          query.append(" AND A.SHOZOKU_CD = '")
//               .append(EscapeUtil.toSqlString(searchInfo.getShozokuCd()))         
//               .append("'");
//      }
//      //�זڔԍ�
//      if(searchInfo.getBunkasaimokuCd() != null && searchInfo.getBunkasaimokuCd().length() != 0){
//          query.append(" AND A.BUNKASAIMOKU_CD = '")
//               .append(EscapeUtil.toSqlString(searchInfo.getBunkasaimokuCd()))            
//               .append("'");
//      }
//      //������v��
//      //�n���̋敪�Ɨ��̗̂�������������
//      if(searchInfo.getKeiName() != null && searchInfo.getKeiName().length() != 0){
//          query.append(" AND (A.KEI_NAME LIKE '%" + EscapeUtil.toSqlString(searchInfo.getKeiName())
//              + "%' OR A.KEI_NAME_RYAKU LIKE '%" + EscapeUtil.toSqlString(searchInfo.getKeiName()) + "%')");
//      }
//      //�\���Җ��i�����F���j�i������v�j
//      if(searchInfo.getNameKanjiSei() != null && searchInfo.getNameKanjiSei().length() != 0){
//          query.append(" AND A.NAME_KANJI_SEI like '%")
//               .append(EscapeUtil.toSqlString(searchInfo.getNameKanjiSei()))
//               .append("%'");
//      }
//      //�\���Җ��i�����F���j�i������v�j
//      if(searchInfo.getNameKanjiMei() != null && searchInfo.getNameKanjiMei().length() != 0){
//          query.append(" AND A.NAME_KANJI_MEI like '%")
//               .append(EscapeUtil.toSqlString(searchInfo.getNameKanjiMei()))
//               .append("%'");
//      }
//      //�\���Җ��i�t���K�i�F���j�i������v�j
//      if(searchInfo.getNameKanaSei() != null && searchInfo.getNameKanaSei().length() != 0){
//          query.append(" AND A.NAME_KANA_SEI like '%")
//               .append(EscapeUtil.toSqlString(searchInfo.getNameKanaSei()))
//               .append("%'");
//      }
//      //�\���Җ��i�t���K�i�F���j�i������v�j
//      if(searchInfo.getNameKanaMei() != null && searchInfo.getNameKanaMei().length() != 0){
//          query.append(" AND A.NAME_KANA_MEI like '%")
//               .append(EscapeUtil.toSqlString(searchInfo.getNameKanaMei()))
//               .append("%'");
//      }
//      //�\���Җ��i���[�}���F���j�i������v�j
//      if(searchInfo.getNameRoSei() != null && searchInfo.getNameRoSei().length() != 0){
//          query.append(" AND UPPER(A.NAME_RO_SEI) like '%")
//               .append(EscapeUtil.toSqlString(searchInfo.getNameRoSei().toUpperCase()))
//               .append("%'");
//      }               
//      //�\���Җ��i���[�}���F���j�i������v�j
//      if(searchInfo.getNameRoMei() != null && searchInfo.getNameRoMei().length() != 0){
//          query.append(" AND UPPER(A.NAME_RO_MEI) like '%")
//               .append(EscapeUtil.toSqlString(searchInfo.getNameRoMei().toUpperCase()))
//               .append("%'");
//      }                       
//      //���Ƌ敪
//      if(searchInfo.getJigyoKubun() != null && searchInfo.getJigyoKubun().length() != 0){
//          query.append(" AND A.JIGYO_KUBUN = '")
//               .append(EscapeUtil.toSqlString(searchInfo.getJigyoKubun()))            
//               .append("'");
//      }
//// 2005/10/18�@�����ԍ��ǉ�
//      // �����ԍ��@
//       if(searchInfo.getSeiriNo() != null && searchInfo.getSeiriNo().length() != 0){
//           query.append(" AND A.JURI_SEIRI_NO LIKE '%")
//                .append(EscapeUtil.toSqlString(searchInfo.getSeiriNo()))
//                .append("%'");            
//       }
////        query.append(" ORDER BY")
////             .append(" LENGTH(NVL(REPLACE(A.KEKKA1_ABC, '-', ''),' ')) DESC");      //�\�����̐R������(ABC)�̐R�����ʐ��̍~��
//      
//      if(searchInfo.getHyojiHoshiki() != null && searchInfo.getHyojiHoshiki().equals("1")) {
//          query.append(" ORDER BY TO_NUMBER(NVL(A.KEKKA1_TEN, '-1')) DESC")                                                           //�\�����̐R������(�_)�̍~��
////                 .append(", LENGTH(NVL(REPLACE(REPLACE(REPLACE(REPLACE(KEKKA1_ABC, 'A-', ''), 'B-', ''), 'A', '00'), 'B', ''), ' ')) DESC")     //A�̑�����
////                 .append(", LENGTH(NVL(REPLACE(REPLACE(REPLACE(REPLACE(KEKKA1_ABC, 'A-', '00'), 'B-', ''), 'A', ''), 'B', ''), ' ')) DESC")     //A-�̑�����
////                 .append(", LENGTH(NVL(REPLACE(REPLACE(REPLACE(REPLACE(KEKKA1_ABC, 'A-', ''), 'B-', ''), 'A', ''), 'B', '00'), ' ')) DESC")     //B�̑�����
////                 .append(", LENGTH(NVL(REPLACE(REPLACE(REPLACE(REPLACE(KEKKA1_ABC, 'A-', ''), 'B-', '00'), 'A', ''), 'B', ''), ' ')) DESC");        //B-�̑�����
//               .append(", NVL(REPLACE(REPLACE(REPLACE(REPLACE(KEKKA1_ABC, 'A-', '3'), 'B-', '1'), 'A', '4'), 'B', '2'), '0') DESC");      //�\�����̐R������(ABC)�̏���
////        } else if(searchInfo.getHyojiHoshiki() != null && searchInfo.getHyojiHoshiki().equals("2")) {
//      } else {    //"1","2"�Ńq�b�g���Ȃ������ꍇ�G���[�ƂȂ�̂ŁAelse�Ƃ��Ă���
////            query.append(" ORDER BY LENGTH(NVL(REPLACE(REPLACE(REPLACE(REPLACE(KEKKA1_ABC, 'A-', ''), 'B-', ''), 'A', '00'), 'B', ''), ' ')) DESC")     //A�̑�����
////                 .append(", LENGTH(NVL(REPLACE(REPLACE(REPLACE(REPLACE(KEKKA1_ABC, 'A-', '00'), 'B-', ''), 'A', ''), 'B', ''), ' ')) DESC")     //A-�̑�����
////                 .append(", LENGTH(NVL(REPLACE(REPLACE(REPLACE(REPLACE(KEKKA1_ABC, 'A-', ''), 'B-', ''), 'A', ''), 'B', '00'), ' ')) DESC")     //B�̑�����
////                 .append(", LENGTH(NVL(REPLACE(REPLACE(REPLACE(REPLACE(KEKKA1_ABC, 'A-', ''), 'B-', '00'), 'A', ''), 'B', ''), ' ')) DESC")     //B-�̑�����
//          query.append(" ORDER BY NVL(REPLACE(REPLACE(REPLACE(REPLACE(KEKKA1_ABC, 'A-', '3'), 'B-', '1'), 'A', '4'), 'B', '2'), '0')DESC")        //�\�����̐R������(ABC)�̏���
//               .append(", TO_NUMBER(NVL(A.KEKKA1_TEN, '-1')) DESC");                                                          //�\�����̐R������(�_)�̍~��
//      }
//      query.append(", A.SYSTEM_NO ASC");                                          //�\���ԍ��̏���   

        StringBuffer query = makeQueryHyokaGakuso(new StringBuffer(select), searchInfo);
        
        //for debug
        if(log.isDebugEnabled()){
            log.debug("query:" + query);
        }

        // �y�[�W�擾
        return SelectUtil.selectPageInfo(connection, (SearchInfo)searchInfo, query.toString());
    }   

//  CSV�o�͋@�\��ǉ�      2005/10/28
    /**
     * �]�����ʈꗗ��CSV���X�g���o�͂���(�w�n�p)�B
     * @param connection                �R�l�N�V����
     * @param searchInfo
     * @return List
     * @throws DataAccessException      �X�V���ɗ�O�����������ꍇ
     * @throws NoDataFoundException �Ώۃf�[�^��������Ȃ��ꍇ
     */
    public List searchCsvData(Connection connection, HyokaSearchInfo searchInfo)
            throws DataAccessException, NoDataFoundException {

        //�����ڂ̕ύX���������ꍇ�A���̕��בւ��ɉe�����ł�\��������̂ŁA���ӁB
        //-----------------------
        // SQL���̍쐬
        //-----------------------
        StringBuffer query = new StringBuffer();
        query.append("SELECT")
             .append(" A.JIGYO_ID")
             .append(", A.NENDO")
             .append(", A.KAISU")
             .append(", A.JIGYO_NAME")
             .append(", A.UKETUKE_NO")
             .append(", A.JURI_SEIRI_NO")
             .append(", A.KADAI_NAME_KANJI")
             .append(", A.NAME_KANJI_SEI")
             .append(", A.NAME_KANJI_MEI")
             .append(", A.SHOZOKU_CD")
             .append(", A.SHOZOKU_NAME")
             .append(", A.SHOZOKU_NAME_RYAKU")
             .append(", A.BUKYOKU_CD")
             .append(", A.BUKYOKU_NAME")
             .append(", A.BUKYOKU_NAME_RYAKU")
             .append(", A.SHOKUSHU_CD")
             .append(", A.SHOKUSHU_NAME_KANJI")
             .append(", SUBSTR(REPLACE(REPLACE(A.KEKKA1_ABC, 'A', ',A'), 'B', ',B'), 2, 17)")       //�P���R������(ABC)"             
             .append(", A.KEKKA1_TEN")
             .append(", A.KANTEN")
             .append(", A.KANTEN_RYAKU")
             //2005.12.26 iso ���ёւ����s��Ȃ��Ȃ����̂ō폜
//           .append(", A.SYSTEM_NO")                           //���ёւ��p�Ɏg�p����B���ŏo�͂���̓J�b�g����̂ŁA��Ɉ�ԉ��ɂ��Ă������ƁB
             //2005.12.19 iso ����������Ɨ�
//           .append(" FROM SHINSAKEKKA B, SHINSEIDATAKANRI A")
//           .append(" WHERE B.SYSTEM_NO = A.SYSTEM_NO")
//           .append(" AND A.JIGYO_KUBUN = B.JIGYO_KUBUN")
//         
//           .append(" AND A.DEL_FLG = 0")
//           .append(" AND B.SHINSAIN_NO NOT LIKE '@%'")
//           .append(" AND A.JOKYO_ID IN ('08', '09', '10', '11', '12')")
//           ;
//
             .append(" FROM")
             .append(" SHINSEIDATAKANRI" + dbLink + " A")
             .append(" WHERE EXISTS")
             .append(" (SELECT *")
             .append(" FROM")
             .append(" SHINSEIDATAKANRI" + dbLink + " A")
             .append(", SHINSAKEKKA" + dbLink + " B")
             .append(" WHERE A.SYSTEM_NO = B.SYSTEM_NO")
             .append(" AND A.JIGYO_KUBUN = B.JIGYO_KUBUN")
             .append(" AND B.SHINSAIN_NO NOT LIKE '@%')")
             ;
//      //�N�x
//      if(searchInfo.getNendo() != null && searchInfo.getNendo().length() != 0){
//          query.append(" AND A.NENDO = '")
//              .append(EscapeUtil.toSqlString(searchInfo.getNendo()))          
//              .append("'");
//      }
//      //��
//      if(searchInfo.getKaisu() != null && searchInfo.getKaisu().length() != 0){
//          query.append(" AND A.KAISU = '")
//              .append(EscapeUtil.toSqlString(searchInfo.getKaisu()))          
//              .append("'");
//      }
//      //�\���ԍ�
//      if(searchInfo.getUketukeNo() != null && searchInfo.getUketukeNo().length() != 0){
//          query.append(" AND A.UKETUKE_NO = '")
//              .append(EscapeUtil.toSqlString(searchInfo.getUketukeNo()))          
//              .append("'");
//      }
//      //�����@�փR�[�h
//      if(searchInfo.getShozokuCd() != null && searchInfo.getShozokuCd().length() != 0){
//          query.append(" AND A.SHOZOKU_CD = '")
//              .append(EscapeUtil.toSqlString(searchInfo.getShozokuCd()))          
//              .append("'");
//      }
//      //�זڔԍ�
//      if(searchInfo.getBunkasaimokuCd() != null && searchInfo.getBunkasaimokuCd().length() != 0){
//          query.append(" AND A.BUNKASAIMOKU_CD = '")
//              .append(EscapeUtil.toSqlString(searchInfo.getBunkasaimokuCd()))         
//              .append("'");
//      }
//      //������v��
//      //�n���̋敪�Ɨ��̗̂�������������
//      if(searchInfo.getKeiName() != null && searchInfo.getKeiName().length() != 0){
//          query.append(" AND (A.KEI_NAME LIKE '%" + EscapeUtil.toSqlString(searchInfo.getKeiName())
//                  + "%' OR A.KEI_NAME_RYAKU LIKE '%" + EscapeUtil.toSqlString(searchInfo.getKeiName()) + "%')");
//      }
//      //�\���Җ��i�����F���j�i������v�j
//      if(searchInfo.getNameKanjiSei() != null && searchInfo.getNameKanjiSei().length() != 0){
//          query.append(" AND A.NAME_KANJI_SEI like '%")
//              .append(EscapeUtil.toSqlString(searchInfo.getNameKanjiSei()))
//              .append("%'");          
//      }
//      //�\���Җ��i�����F���j�i������v�j
//      if(searchInfo.getNameKanjiMei() != null && searchInfo.getNameKanjiMei().length() != 0){
//          query.append(" AND A.NAME_KANJI_MEI like '%")
//              .append(EscapeUtil.toSqlString(searchInfo.getNameKanjiMei()))
//              .append("%'");          
//      }
//      //�\���Җ��i�t���K�i�F���j�i������v�j
//      if(searchInfo.getNameKanaSei() != null && searchInfo.getNameKanaSei().length() != 0){
//          query.append(" AND A.NAME_KANA_SEI like '%")
//              .append(EscapeUtil.toSqlString(searchInfo.getNameKanaSei()))
//              .append("%'");          
//      }
//      //�\���Җ��i�t���K�i�F���j�i������v�j
//      if(searchInfo.getNameKanaMei() != null && searchInfo.getNameKanaMei().length() != 0){
//          query.append(" AND A.NAME_KANA_MEI like '%")
//              .append(EscapeUtil.toSqlString(searchInfo.getNameKanaMei()))
//              .append("%'");          
//      }
//      //�\���Җ��i���[�}���F���j�i������v�j
//      if(searchInfo.getNameRoSei() != null && searchInfo.getNameRoSei().length() != 0){
//          query.append(" AND UPPER(A.NAME_RO_SEI) like '%")
//              .append(EscapeUtil.toSqlString(searchInfo.getNameRoSei().toUpperCase()))
//              .append("%'");          
//      }               
//      //�\���Җ��i���[�}���F���j�i������v�j
//      if(searchInfo.getNameRoMei() != null && searchInfo.getNameRoMei().length() != 0){
//          query.append(" AND UPPER(A.NAME_RO_MEI) like '%")
//              .append(EscapeUtil.toSqlString(searchInfo.getNameRoMei().toUpperCase()))
//              .append("%'");          
//      }
//      //���Ƌ敪
//      if(searchInfo.getJigyoKubun() != null && searchInfo.getJigyoKubun().length() != 0){
//          query.append(" AND A.JIGYO_KUBUN = '")
//              .append(EscapeUtil.toSqlString(searchInfo.getJigyoKubun()))         
//              .append("'");
//      }
//      //�����ԍ�  2005/10/21 �ǉ��ibaba�j
//      if(searchInfo.getSeiriNo() != null && searchInfo.getSeiriNo().length() != 0){
//          query.append(" AND A.JURI_SEIRI_NO LIKE '%")
//              .append(EscapeUtil.toSqlString(searchInfo.getSeiriNo()))
//              .append("%'");          
//      }
//
//      if(searchInfo.getHyojiHoshiki() != null && searchInfo.getHyojiHoshiki().equals("1")) {
//          query.append(" ORDER BY TO_NUMBER(NVL(A.KEKKA1_TEN, '-1')) DESC")                                                           //�\�����̐R������(�_)�̍~��
////                 .append(", LENGTH(NVL(REPLACE(REPLACE(REPLACE(REPLACE(KEKKA1_ABC, 'A-', ''), 'B-', ''), 'A', '00'), 'B', ''), ' ')) DESC")     //A�̑�����
////                 .append(", LENGTH(NVL(REPLACE(REPLACE(REPLACE(REPLACE(KEKKA1_ABC, 'A-', '00'), 'B-', ''), 'A', ''), 'B', ''), ' ')) DESC")     //A-�̑�����
////                 .append(", LENGTH(NVL(REPLACE(REPLACE(REPLACE(REPLACE(KEKKA1_ABC, 'A-', ''), 'B-', ''), 'A', ''), 'B', '00'), ' ')) DESC")     //B�̑�����
////                 .append(", LENGTH(NVL(REPLACE(REPLACE(REPLACE(REPLACE(KEKKA1_ABC, 'A-', ''), 'B-', '00'), 'A', ''), 'B', ''), ' ')) DESC");        //B-�̑�����
//                   .append(", NVL(REPLACE(REPLACE(REPLACE(REPLACE(KEKKA1_ABC, 'A-', '3'), 'B-', '1'), 'A', '4'), 'B', '2'), '0') DESC");      //�\�����̐R������(ABC)�̏���
////        } else if(searchInfo.getHyojiHoshiki() != null && searchInfo.getHyojiHoshiki().equals("2")) {
//      } else {    //"1","2"�Ńq�b�g���Ȃ������ꍇ�G���[�ƂȂ�̂ŁAelse�Ƃ��Ă���
////                            query.append(" ORDER BY LENGTH(NVL(REPLACE(REPLACE(REPLACE(REPLACE(KEKKA1_ABC, 'A-', ''), 'B-', ''), 'A', '00'), 'B', ''), ' ')) DESC")     //A�̑�����
////                                 .append(", LENGTH(NVL(REPLACE(REPLACE(REPLACE(REPLACE(KEKKA1_ABC, 'A-', '00'), 'B-', ''), 'A', ''), 'B', ''), ' ')) DESC")     //A-�̑�����
////                                 .append(", LENGTH(NVL(REPLACE(REPLACE(REPLACE(REPLACE(KEKKA1_ABC, 'A-', ''), 'B-', ''), 'A', ''), 'B', '00'), ' ')) DESC")     //B�̑�����
////                                 .append(", LENGTH(NVL(REPLACE(REPLACE(REPLACE(REPLACE(KEKKA1_ABC, 'A-', ''), 'B-', '00'), 'A', ''), 'B', ''), ' ')) DESC")     //B-�̑�����
//          query.append(" ORDER BY NVL(REPLACE(REPLACE(REPLACE(REPLACE(KEKKA1_ABC, 'A-', '3'), 'B-', '1'), 'A', '4'), 'B', '2'), '0')DESC")        //�\�����̐R������(ABC)�̏���
//               .append(", TO_NUMBER(NVL(A.KEKKA1_TEN, '-1')) DESC");                                                          //�\�����̐R������(�_)�̍~��
//      }
//      query.append(", A.SYSTEM_NO ASC");                  //�\���ԍ��̏���
//      query.append(", A.JIGYO_ID ASC");                       //����ID�̏���
//      query.append(", B.KEKKA_ABC ASC");                  //�R������(ABC)�̏���
//      query.append(", B.KEKKA_TEN DESC");                 //�R������(�_��)�̍~��
//      query.append(", B.SHINSAIN_NO ASC");                    //�R�����ԍ��̏���
//      query.append(", B.JIGYO_KUBUN ASC");                    //���Ƌ敪�̏���
        query = makeQueryHyokaGakuso(query, searchInfo);
        
        
//for debug
        if(log.isDebugEnabled()){
            log.debug("query:" + query);
        }

        String[] columnArray = {"����ID"
                                 ,"�N�x"
                                 ,"��"
                                 ,"���Ɩ�"
                                 ,"����ԍ�"
                                 ,"�����ԍ��i�w�n�p�j"
                                 ,"�����ۑ薼�i�a���j"
                                 ,"����Ҏ����i������-���j"
                                 ,"����Ҏ����i������-���j"
                                 ,"���������@�֔ԍ�"
                                 ,"���������@�֖�"
                                 ,"���������@�֖��i���́j"
                                 ,"���ǔԍ�"
                                 ,"���ǖ�"
                                 ,"���ǖ��i���́j"
                                 ,"�E���ԍ�"
                                 ,"�E���i�a���j"
                                 ,"1���R�����ʁiABC�j"
                                 ,"1���R�����ʁi�_���j"
                                 ,"���E�̊ϓ_"
                                 ,"���E�̊ϓ_�i���́j"
                        
                                 };
    
        List csvDataList = SelectUtil.selectCsvList(connection, query.toString(), false);
        //2005.12.19 iso CSV�o�͕s��C��
        csvDataList.add(0, Arrays.asList(columnArray));
    
//      ArrayList newList = new ArrayList(Arrays.asList(columnArray));  //�V�������1�\�����̃��X�g(�VCSV1�s��)
//      ArrayList csvList = new ArrayList();                            //CSV�Ƃ��ďo�͂��郊�X�g
//      String beforeJigyoId = "0";                                     //1�O�̎���ID�F�����l0(���߂Ɉ�v���Ȃ���Ή��ł�����)
//      //2005.12.19 iso �\�������V�X�e���ԍ��ł܂Ƃ߂�悤�ύX
////        String beforeUketukeNo = "0";                                   //1�O�̐\���ԍ��F�����l0(���߂Ɉ�v���Ȃ���Ή��ł�����)
//      String beforeSystemNo = "0";                                    //1�O�̃V�X�e���ԍ��F�����l0(���߂Ɉ�v���Ȃ���Ή��ł�����)
//
//      for(int i = 0; i < csvDataList.size(); i++) {
//          ArrayList shinseiList = (ArrayList)csvDataList.get(i);          //���f�[�^��1�\�����̃��X�g(��CSV1�s��)
//          
//          //2005.12.19 iso CSV�o�͕s��C��
//          //����ԍ��̈ʒu�C���Ɓu����ԍ�+�����@�֔ԍ��v�ň�ӂƂȂ�悤�ύX
////                if(shinseiList.get(0).equals(beforeJigyoId) && shinseiList.get(1).equals(beforeUketukeNo)){}
//          String nowSystemNo = shinseiList.get(shinseiList.size()-1).toString();
//          shinseiList.remove(shinseiList.size()-1);                   //CSV�o�͂��Ȃ��V�X�e���ԍ�(��ɍŌ�)���J�b�g����B
//          
//          if(shinseiList.get(0).equals(beforeJigyoId) && nowSystemNo.equals(beforeSystemNo)){} 
//          else {
//              //1�O�ƈႤ�\���f�[�^�̏ꍇ�A�V���������1�s���̐\���f�[�^��VCSV���X�g�ɓo�^�B
//              //�o�^��AnewList��V�����\���f�[�^�ŏ������B     
//              csvList.add(newList);
//              newList = new ArrayList(shinseiList);
//              //���݂̎���ID�E�\���ԍ������̔��f�Ɏg�����߂ɃZ�b�g�B
//              beforeJigyoId = shinseiList.get(0).toString();
//              //2005.12.19 iso CSV�o�͕s��C��
////                    beforeUketukeNo = shinseiList.get(1).toString();
//              beforeSystemNo = nowSystemNo;
//          }
//          //�Ō�̃f�[�^�͏��else�Ɉ���������Ȃ��̂ŁA������csvList�Ɋi�[����B
//          if(i == csvDataList.size()-1) {
//              //�R�����ʂ̐���������Ƃ���f�[�^�̂ݏo�͂���B
//              csvList.add(newList);
//              
//          }
//      }
//      return csvList;
        return csvDataList;
    }

    /**
     * �]�����������I�u�W�F�N�g���猟���������擾��SQL�̖₢���킹�����𐶐�����B
     * @param query
     * @param searchInfo
     * @return
     */
    private StringBuffer makeQueryHyokaGakuso(StringBuffer query, HyokaSearchInfo searchInfo) {

        //�N�x
        if(searchInfo.getNendo() != null && searchInfo.getNendo().length() != 0){
            query.append(" AND A.NENDO = '")
                 .append(EscapeUtil.toSqlString(searchInfo.getNendo()))         
                 .append("'");
        }
        //��
        if(searchInfo.getKaisu() != null && searchInfo.getKaisu().length() != 0){
            query.append(" AND A.KAISU = '")
                 .append(EscapeUtil.toSqlString(searchInfo.getKaisu()))         
                 .append("'");
        }
        //�\���ԍ�
        if(searchInfo.getUketukeNo() != null && searchInfo.getUketukeNo().length() != 0){
            query.append(" AND A.UKETUKE_NO = '")
                 .append(EscapeUtil.toSqlString(searchInfo.getUketukeNo()))         
                 .append("'");
        }
        //�����@�փR�[�h
        if(searchInfo.getShozokuCd() != null && searchInfo.getShozokuCd().length() != 0){
            query.append(" AND A.SHOZOKU_CD = '")
                 .append(EscapeUtil.toSqlString(searchInfo.getShozokuCd()))         
                 .append("'");
        }
        //�זڔԍ�
        if(searchInfo.getBunkasaimokuCd() != null && searchInfo.getBunkasaimokuCd().length() != 0){
            query.append(" AND A.BUNKASAIMOKU_CD = '")
                 .append(EscapeUtil.toSqlString(searchInfo.getBunkasaimokuCd()))            
                 .append("'");
        }
        //������v��
        //�n���̋敪�Ɨ��̗̂�������������
        if(searchInfo.getKeiName() != null && searchInfo.getKeiName().length() != 0){
            query.append(" AND (A.KEI_NAME LIKE '%" + EscapeUtil.toSqlString(searchInfo.getKeiName())
                    + "%' OR A.KEI_NAME_RYAKU LIKE '%" + EscapeUtil.toSqlString(searchInfo.getKeiName()) + "%')");
        }
        //�\���Җ��i�����F���j�i������v�j
        if(searchInfo.getNameKanjiSei() != null && searchInfo.getNameKanjiSei().length() != 0){
            query.append(" AND A.NAME_KANJI_SEI like '%")
                 .append(EscapeUtil.toSqlString(searchInfo.getNameKanjiSei()))
                 .append("%'");         
        }
        //�\���Җ��i�����F���j�i������v�j
        if(searchInfo.getNameKanjiMei() != null && searchInfo.getNameKanjiMei().length() != 0){
            query.append(" AND A.NAME_KANJI_MEI like '%")
                 .append(EscapeUtil.toSqlString(searchInfo.getNameKanjiMei()))
                 .append("%'");         
        }
        //�\���Җ��i�t���K�i�F���j�i������v�j
        if(searchInfo.getNameKanaSei() != null && searchInfo.getNameKanaSei().length() != 0){
            query.append(" AND A.NAME_KANA_SEI like '%")
                 .append(EscapeUtil.toSqlString(searchInfo.getNameKanaSei()))
                 .append("%'");         
        }
        //�\���Җ��i�t���K�i�F���j�i������v�j
        if(searchInfo.getNameKanaMei() != null && searchInfo.getNameKanaMei().length() != 0){
            query.append(" AND A.NAME_KANA_MEI like '%")
                 .append(EscapeUtil.toSqlString(searchInfo.getNameKanaMei()))
                 .append("%'");         
        }
        //�\���Җ��i���[�}���F���j�i������v�j
        if(searchInfo.getNameRoSei() != null && searchInfo.getNameRoSei().length() != 0){
            query.append(" AND UPPER(A.NAME_RO_SEI) like '%")
                 .append(EscapeUtil.toSqlString(searchInfo.getNameRoSei().toUpperCase()))
                 .append("%'");         
        }               
        //�\���Җ��i���[�}���F���j�i������v�j
        if(searchInfo.getNameRoMei() != null && searchInfo.getNameRoMei().length() != 0){
            query.append(" AND UPPER(A.NAME_RO_MEI) like '%")
                 .append(EscapeUtil.toSqlString(searchInfo.getNameRoMei().toUpperCase()))
                 .append("%'");         
        }
        //���Ƌ敪
        if(searchInfo.getJigyoKubun() != null && searchInfo.getJigyoKubun().length() != 0){
            query.append(" AND A.JIGYO_KUBUN = '")
                 .append(EscapeUtil.toSqlString(searchInfo.getJigyoKubun()))            
                 .append("'");
        }
        //�����ԍ�  2005/10/21 �ǉ��ibaba�j
        if(searchInfo.getSeiriNo() != null && searchInfo.getSeiriNo().length() != 0){
            query.append(" AND A.JURI_SEIRI_NO LIKE '%")
                 .append(EscapeUtil.toSqlString(searchInfo.getSeiriNo()))
                 .append("%'");         
        }

        query.append(" AND A.DEL_FLG = 0")
             .append(" AND A.JOKYO_ID IN ('08', '09', '10', '11', '12')");
    
        if(searchInfo.getHyojiHoshiki() != null && searchInfo.getHyojiHoshiki().equals("1")) {
            query.append(" ORDER BY TO_NUMBER(NVL(A.KEKKA1_TEN, '-1')) DESC")                                   
                 .append(", NVL(REPLACE(REPLACE(REPLACE(REPLACE(KEKKA1_ABC, 'A-', '3'), 'B-', '1'), 'A', '4'), 'B', '2'), '0') DESC");      //�\�����̐R������(ABC)�̏���
        } else {    //"1","2"�Ńq�b�g���Ȃ������ꍇ�G���[�ƂȂ�̂ŁAelse�Ƃ��Ă���
            query.append(" ORDER BY NVL(REPLACE(REPLACE(REPLACE(REPLACE(KEKKA1_ABC, 'A-', '3'), 'B-', '1'), 'A', '4'), 'B', '2'), '0')DESC")        //�\�����̐R������(ABC)�̏���
                 .append(", TO_NUMBER(NVL(A.KEKKA1_TEN, '-1')) DESC");                                                          //�\�����̐R������(�_)�̍~��
        }
        query.append(", A.SYSTEM_NO ASC");                  //�\���ԍ��̏���
        query.append(", A.JIGYO_ID ASC");                   //����ID�̏���

        return query;
    }

    /**
     * �w�茟�������ɊY������\���]���f�[�^���擾����(��՗p)�B
     * 
     * @param connection
     * @param searchInfo
     * @return
     * @throws DataAccessException
     * @throws NoDataFoundException
     * @throws ApplicationException  �\���]����񂪃Z�b�g����Ă��Ȃ������ꍇ
     */
    public Page searchHyokaListKiban(
            Connection connection,
            HyokaSearchInfo searchInfo)
            throws DataAccessException, NoDataFoundException, ApplicationException {

        String select = "SELECT"
                      + " A.SYSTEM_NO"                      //�V�X�e���ԍ�
                      + ", A.NENDO"                         //�N�x
                      + ", A.KAISU"                         //��
                      + ", A.JIGYO_NAME"                    //���Ɩ�
                      + ", A.BUNKASAIMOKU_CD"               //�זڔԍ�
                      + ", A.SAIMOKU_NAME"                  //�זږ�
                      + ", A.KAIGAIBUNYA_NAME_RYAKU"        //�C�O���얼(����)
                      //add start ly 2006/04/26
                      + " ,A.KAIGAIBUNYA_NAME"              //�C�O���얼(��Ղ̏ꍇ)
                      + " ,A.SHINSARYOIKI_NAME"             //�C�O���얼
                      //add end ly 2006/04/26
                      + ", A.SHOZOKU_NAME_RYAKU"            //�����@�֖�
                      + ", A.BUKYOKU_NAME_RYAKU"            //���ǖ�
                      + ", A.SHOKUSHU_NAME_RYAKU"           //�E�햼
                      + ", A.NAME_KANJI_SEI"                //�\���Ҏ���(������-��)
                      + ", A.NAME_KANJI_MEI"                //�\���Ҏ���(������-��)
                      + ", A.UKETUKE_NO"                    //�\���ԍ�
                      + ", A.KEKKA1_TEN_SORTED"             //�ꎞ�R������(�����]�_)
                      + ", A.KEKKA1_TEN"                    //�ꎞ�R������(�v)
                      + ", A.JIGYO_KUBUN"                   //���Ƌ敪
                      + " FROM"
                      + " SHINSEIDATAKANRI"+dbLink+" A"
                      
                      //�������A�R������1�l�����蓖�Ă��Ă��Ȃ��\���f�[�^��\�������Ȃ����߂̐���
                      //�R������1�l�ȏ㊄�蓖�Ă��Ă���SYSTEM_NO���������Ă���A����SYSTEM_NO�����������Ƃ���B
                      + " WHERE EXISTS"
                      + " (SELECT *"
                      + " FROM"
                      + " SHINSEIDATAKANRI"+dbLink+" A"
                      + ", SHINSAKEKKA"+dbLink+" B"
                      + " WHERE A.SYSTEM_NO = B.SYSTEM_NO"
                      + " AND A.JIGYO_KUBUN = B.JIGYO_KUBUN"
                      + " AND B.SHINSAIN_NO NOT LIKE '@%')" //�R������1�l�����蓖�Ă��Ă��Ȃ�(�S��@XXX)���ƈ���������Ȃ�

                      + " AND A.DEL_FLG = 0"
                      + " AND A.JOKYO_ID IN ('08', '09', '10', '11', '12')";
        
        //-----���������I�u�W�F�N�g�̓��e��SQL�Ɍ������Ă���-----
        StringBuffer query = new StringBuffer(select);
        
//      //���Ɩ�(CD)
//      if(searchInfo.getJigyoCd() != null && searchInfo.getJigyoCd().length() != 0) {
//          query.append(" AND SUBSTR(A.JIGYO_ID,3,5) = '")
//               .append(EscapeUtil.toSqlString(searchInfo.getJigyoCd()))
//               .append("'");
//      }
        //2005.05.19 iso �A�N�Z�X�Ǘ��̎��Ƌ敪������CD�ύX�Ή�
        //���ƃR�[�h
        List jigyoCdValueList = searchInfo.getValues();
        if(jigyoCdValueList != null && jigyoCdValueList.size() != 0){
            query.append(" AND SUBSTR(A.JIGYO_ID, 3, 5) IN (")
                 .append(changeIterator2CSV(searchInfo.getValues().iterator()))
                 .append(")");
        }
//      LabelValueBean labelValueBean = new LabelValueBean();
//      List jigyoList = searchInfo.getJigyoList();
//      if(jigyoList != null && jigyoList.size() > 0) {
//          query.append(" AND SUBSTR(A.JIGYO_ID,3,5) IN (");
//          int i;
//          for(i = 0; i < jigyoList.size()-1; i++) {
//              labelValueBean = (LabelValueBean)jigyoList.get(i);
//              query.append("'")
//                   .append(EscapeUtil.toSqlString(labelValueBean.getValue()))
//                   .append("', ");
//          }
//          labelValueBean = (LabelValueBean)jigyoList.get(i);
//          query.append("'")
//               .append(EscapeUtil.toSqlString(labelValueBean.getValue()))
//               .append("')");
//      }
        //�N�x
        if(searchInfo.getNendo() != null && searchInfo.getNendo().length() != 0){
            query.append(" AND A.NENDO = '")
                 .append(EscapeUtil.toSqlString(searchInfo.getNendo()))         
                 .append("'");
        }
        //��
        if(searchInfo.getKaisu() != null && searchInfo.getKaisu().length() != 0){
            query.append(" AND A.KAISU = '")
                 .append(EscapeUtil.toSqlString(searchInfo.getKaisu()))         
                 .append("'");
        }
        //�\���Җ��i�����F���j�i������v�j
        if(searchInfo.getNameKanjiSei() != null && searchInfo.getNameKanjiSei().length() != 0){
            query.append(" AND A.NAME_KANJI_SEI like '%")
                 .append(EscapeUtil.toSqlString(searchInfo.getNameKanjiSei()))
                 .append("%'");
        }
        //�\���Җ��i�����F���j�i������v�j
        if(searchInfo.getNameKanjiMei() != null && searchInfo.getNameKanjiMei().length() != 0){
            query.append(" AND A.NAME_KANJI_MEI like '%")
                 .append(EscapeUtil.toSqlString(searchInfo.getNameKanjiMei()))
                 .append("%'");
        }
        //�\���Җ��i�t���K�i�F���j�i������v�j
        if(searchInfo.getNameKanaSei() != null && searchInfo.getNameKanaSei().length() != 0){
            query.append(" AND A.NAME_KANA_SEI like '%")
                 .append(EscapeUtil.toSqlString(searchInfo.getNameKanaSei()))
                 .append("%'");
        }
        //�\���Җ��i�t���K�i�F���j�i������v�j
        if(searchInfo.getNameKanaMei() != null && searchInfo.getNameKanaMei().length() != 0){
            query.append(" AND A.NAME_KANA_MEI like '%")
                 .append(EscapeUtil.toSqlString(searchInfo.getNameKanaMei()))
                 .append("%'");
        }
        //�\���Җ��i���[�}���F���j�i������v�j
        if(searchInfo.getNameRoSei() != null && searchInfo.getNameRoSei().length() != 0){
            query.append(" AND UPPER(A.NAME_RO_SEI) like '%")
                 .append(EscapeUtil.toSqlString(searchInfo.getNameRoSei().toUpperCase()))
                 .append("%'");
        }
        //�\���Җ��i���[�}���F���j�i������v�j
        if(searchInfo.getNameRoMei() != null && searchInfo.getNameRoMei().length() != 0){
            query.append(" AND UPPER(A.NAME_RO_MEI) like '%")
                 .append(EscapeUtil.toSqlString(searchInfo.getNameRoMei().toUpperCase()))
                 .append("%'");
        }
        //�����@�փR�[�h
        if(searchInfo.getShozokuCd() != null && searchInfo.getShozokuCd().length() != 0){
            query.append(" AND A.SHOZOKU_CD = '")
                 .append(EscapeUtil.toSqlString(searchInfo.getShozokuCd()))         
                 .append("'");
        }
        //�זڔԍ�
        if(searchInfo.getBunkasaimokuCd() != null && searchInfo.getBunkasaimokuCd().length() != 0){
            query.append(" AND A.BUNKASAIMOKU_CD = '")
                 .append(EscapeUtil.toSqlString(searchInfo.getBunkasaimokuCd()))            
                 .append("'");
        }
        //�C�O����(����)
        //update start ly 2006/04/26
//      if(searchInfo.getKaigaibunyaName()!= null && searchInfo.getKaigaibunyaName().length() != 0){
//          query.append(" AND (A.KAIGAIBUNYA_NAME LIKE '%")
//               .append(EscapeUtil.toSqlString(searchInfo.getKaigaibunyaName()))
//               .append("%' OR A.KAIGAIBUNYA_NAME_RYAKU LIKE '%")
//               .append(EscapeUtil.toSqlString(searchInfo.getKaigaibunyaName()))
//               .append("%')");
//      }
        if(searchInfo.getKaigaibunyaName()!= null && searchInfo.getKaigaibunyaName().length() != 0){
            query.append(" AND (A.KAIGAIBUNYA_NAME LIKE '%")
                 .append(EscapeUtil.toSqlString(searchInfo.getKaigaibunyaName()))
                 .append("%' OR A.KAIGAIBUNYA_NAME_RYAKU LIKE '%")
                 .append(EscapeUtil.toSqlString(searchInfo.getKaigaibunyaName()))
                 .append("%' OR A.SHINSARYOIKI_NAME LIKE '%")
                 .append(EscapeUtil.toSqlString(searchInfo.getKaigaibunyaName()))
                 .append("%')");
        }
        //update end ly 2006/04/26
        //�\���ԍ�
        if(searchInfo.getUketukeNo() != null && searchInfo.getUketukeNo().length() != 0){
            query.append(" AND A.UKETUKE_NO = '")
                 .append(EscapeUtil.toSqlString(searchInfo.getUketukeNo()))         
                 .append("'");
        }
        //�]���i�_�j�i���j
        if(searchInfo.getHyokaHigh() != null && searchInfo.getHyokaHigh().length() != 0){
            query.append(" AND NVL(A.KEKKA1_TEN, -1) <= '")
                 .append(EscapeUtil.toSqlString(searchInfo.getHyokaHigh()))         
                 .append("'");
        }
        //�]���i�_�j�i��j
        if(searchInfo.getHyokaLow() != null && searchInfo.getHyokaLow().length() != 0){
            query.append(" AND A.KEKKA1_TEN >= '")
                 .append(EscapeUtil.toSqlString(searchInfo.getHyokaLow()))          
                 .append("'");
        }
        //���Ƌ敪
 //update start ly 2006/04/26
//      if(searchInfo.getJigyoKubun() != null && searchInfo.getJigyoKubun().length() != 0){
//          query.append(" AND A.JIGYO_KUBUN = '")
//               .append(EscapeUtil.toSqlString(searchInfo.getJigyoKubun()))            
//               .append("'");
//      }
        if(searchInfo.getJigyoKubun() != null && searchInfo.getJigyoKubun().length() != 0){
//2006/05/09�@�ǉ���������            
//          query.append(" AND (A.JIGYO_KUBUN = '4' or A.JIGYO_KUBUN = '6' or A.JIGYO_KUBUN = '7') ");
            query.append(" AND A.JIGYO_KUBUN IN (");
            query.append(StringUtil.changeIterator2CSV(JigyoKubunFilter.Convert2ShinseishoJigyoKubun(IJigyoKubun.JIGYO_KUBUN_KIBAN).iterator(),true));
            query.append(")");
//�c�@�ǉ������܂�            
      }
 //update end ly 2006/04/26

        query.append(" ORDER BY")
             .append(" TO_NUMBER(NVL(A.KEKKA1_TEN, '-1')) DESC")//�ꎞ�R������(�v)�̍~��

             .append(", A.KEKKA1_TEN_SORTED DESC")              //�ꎞ�R������(�����]�_)�̍~��

             .append(", A.SYSTEM_NO ASC");                      //�\���ԍ��̏���   

        //for debug
        if(log.isDebugEnabled()){
            log.debug("query:" + query);
        }

        // �y�[�W�擾
        return SelectUtil.selectPageInfo(connection, (SearchInfo)searchInfo, query.toString());
    }   

    /**
     * �w�茟�������ɊY������]���R�����g�f�[�^���擾����(��՗p)�B
     * 
     * @param connection
     * @param searchInfo
     * @return
     * @throws DataAccessException
     * @throws NoDataFoundException
     * @throws ApplicationException  �\���]����񂪃Z�b�g����Ă��Ȃ������ꍇ
     */
    public Page searchCommentListKiban(
            Connection connection,
            HyokaSearchInfo searchInfo)
            throws DataAccessException, NoDataFoundException, ApplicationException {

        String select = "SELECT"
                      + " A.SYSTEM_NO"                       //�V�X�e���ԍ�
                      + ", A.NENDO"                          //�N�x
                      + ", A.KAISU"                          //��
                      + ", A.JIGYO_NAME"                     //���Ɩ�
                      + ", A.BUNKASAIMOKU_CD"                //�זڔԍ�
                      + ", A.SAIMOKU_NAME"                   //�זږ�
                      + ", A.KAIGAIBUNYA_NAME_RYAKU"         //�C�O���얼(����)
                      + ", A.SHOZOKU_NAME_RYAKU"             //�����@�֖�
                      + ", A.BUKYOKU_NAME_RYAKU"             //���ǖ�
                      + ", A.SHOKUSHU_NAME_RYAKU"            //�E�햼
                      + ", A.NAME_KANJI_SEI"                 //�\���Ҏ���(������-��)
                      + ", A.NAME_KANJI_MEI"                 //�\���Ҏ���(������-��)
                      + ", A.UKETUKE_NO"                     //�\���ԍ�
                      + ", A.KEKKA1_TEN_SORTED"              //�ꎞ�R������(�����]�_)
                      + ", A.KEKKA1_TEN"                     //�ꎞ�R������(�v)
                      + ", B.COMMENTS"                       //�R�����g
                      + ", B.SHINSAIN_NO"                    //�R��������(������-��)
                      + ", B.SHINSAIN_NAME_KANJI_SEI"        //�R��������(������-��)
                      + ", B.SHINSAIN_NAME_KANJI_MEI"        //�R��������(������-��)
                      + ", A.JIGYO_KUBUN"                    //���Ƌ敪
                      + " FROM"
                      + " SHINSEIDATAKANRI"+dbLink+" A"
                      + ", SHINSAKEKKA"+dbLink+" B"
                      
                      //�������A�R������1�l�����蓖�Ă��Ă��Ȃ��\���f�[�^��\�������Ȃ����߂̐���
                      //�R������1�l�ȏ㊄�蓖�Ă��Ă���SYSTEM_NO���������Ă���A����SYSTEM_NO�����������Ƃ���B
                      + " WHERE EXISTS"
                      + " (SELECT *"
                      + " FROM"
                      + " SHINSEIDATAKANRI" + dbLink + " A"
                      + ", SHINSAKEKKA" + dbLink + " B"
                      + " WHERE A.SYSTEM_NO = B.SYSTEM_NO"
                      //+ " AND A.JIGYO_KUBUN = B.JIGYO_KUBUN"
                      + " AND B.SHINSAIN_NO NOT LIKE '@%')"  //�R������1�l�����蓖�Ă��Ă��Ȃ�(�S��@XXX)���ƈ���������Ȃ�

                      + " AND A.DEL_FLG = 0"
                      + " AND A.JOKYO_ID IN ('08', '09', '10', '11', '12')"
                      + " AND A.SYSTEM_NO = B.SYSTEM_NO"
                      + " AND A.JIGYO_KUBUN = B.JIGYO_KUBUN";
        
        //-----���������I�u�W�F�N�g�̓��e��SQL�Ɍ������Ă���-----
        StringBuffer query = new StringBuffer(select);
        
//      //���Ɩ�(CD)
//      if(searchInfo.getJigyoCd() != null && searchInfo.getJigyoCd().length() != 0) {
//          query.append(" AND SUBSTR(A.JIGYO_ID,3,5) = '")
//               .append(EscapeUtil.toSqlString(searchInfo.getJigyoCd()))
//               .append("'");
//      }
        //2005.05.19 iso �A�N�Z�X�Ǘ��̎��Ƌ敪������CD�ύX�Ή�
        //���ƃR�[�h
        List jigyoCdValueList = searchInfo.getValues();
        if(jigyoCdValueList != null && jigyoCdValueList.size() != 0){
            query.append(" AND SUBSTR(A.JIGYO_ID, 3, 5) IN (")
                 .append(changeIterator2CSV(searchInfo.getValues().iterator()))
                 .append(")");
        }
//      LabelValueBean labelValueBean = new LabelValueBean();
//      List jigyoList = searchInfo.getJigyoList();
//      if(jigyoList != null && jigyoList.size() > 0) {
//          query.append(" AND SUBSTR(A.JIGYO_ID,3,5) IN (");
//          int i;
//          for(i = 0; i < jigyoList.size()-1; i++) {
//              labelValueBean = (LabelValueBean)jigyoList.get(i);
//              query.append("'")
//                   .append(EscapeUtil.toSqlString(labelValueBean.getValue()))
//                   .append("', ");
//          }
//          labelValueBean = (LabelValueBean)jigyoList.get(i);
//          query.append("'")
//               .append(EscapeUtil.toSqlString(labelValueBean.getValue()))
//               .append("')");
//      }
        //�N�x
        if(searchInfo.getNendo() != null && searchInfo.getNendo().length() != 0){
            query.append(" AND A.NENDO = '")
                 .append(EscapeUtil.toSqlString(searchInfo.getNendo()))         
                 .append("'");
        }
        //��
        if(searchInfo.getKaisu() != null && searchInfo.getKaisu().length() != 0){
            query.append(" AND A.KAISU = '")
                 .append(EscapeUtil.toSqlString(searchInfo.getKaisu()))         
                 .append("'");
        }
        //�\���Җ��i�����F���j�i������v�j
        if(searchInfo.getNameKanjiSei() != null && searchInfo.getNameKanjiSei().length() != 0){
            query.append(" AND A.NAME_KANJI_SEI like '%")
                 .append(EscapeUtil.toSqlString(searchInfo.getNameKanjiSei()))
                 .append("%'");
        }
        //�\���Җ��i�����F���j�i������v�j
        if(searchInfo.getNameKanjiMei() != null && searchInfo.getNameKanjiMei().length() != 0){
            query.append(" AND A.NAME_KANJI_MEI like '%")
                 .append(EscapeUtil.toSqlString(searchInfo.getNameKanjiMei()))
                 .append("%'");
        }
        //�\���Җ��i�t���K�i�F���j�i������v�j
        if(searchInfo.getNameKanaSei() != null && searchInfo.getNameKanaSei().length() != 0){
            query.append(" AND A.NAME_KANA_SEI like '%")
                 .append(EscapeUtil.toSqlString(searchInfo.getNameKanaSei()))
                 .append("%'");
        }
        //�\���Җ��i�t���K�i�F���j�i������v�j
        if(searchInfo.getNameKanaMei() != null && searchInfo.getNameKanaMei().length() != 0){
            query.append(" AND A.NAME_KANA_MEI like '%")
                 .append(EscapeUtil.toSqlString(searchInfo.getNameKanaMei()))
                 .append("%'");
        }
        //�\���Җ��i���[�}���F���j�i������v�j
        if(searchInfo.getNameRoSei() != null && searchInfo.getNameRoSei().length() != 0){
            query.append(" AND UPPER(A.NAME_RO_SEI) like '%")
                 .append(EscapeUtil.toSqlString(searchInfo.getNameRoSei().toUpperCase()))
                 .append("%'");
        }
        //�\���Җ��i���[�}���F���j�i������v�j
        if(searchInfo.getNameRoMei() != null && searchInfo.getNameRoMei().length() != 0){
            query.append(" AND UPPER(A.NAME_RO_MEI) like '%")
                 .append(EscapeUtil.toSqlString(searchInfo.getNameRoMei().toUpperCase()))
                 .append("%'");
        }
        //�����@�փR�[�h
        if(searchInfo.getShozokuCd() != null && searchInfo.getShozokuCd().length() != 0){
            query.append(" AND A.SHOZOKU_CD = '")
                 .append(EscapeUtil.toSqlString(searchInfo.getShozokuCd()))         
                 .append("'");
        }
        //�זڔԍ�
        if(searchInfo.getBunkasaimokuCd() != null && searchInfo.getBunkasaimokuCd().length() != 0){
            query.append(" AND A.BUNKASAIMOKU_CD = '")
                 .append(EscapeUtil.toSqlString(searchInfo.getBunkasaimokuCd()))            
                 .append("'");
        }
        //�C�O����(����)
        if(searchInfo.getKaigaibunyaName()!= null && searchInfo.getKaigaibunyaName().length() != 0){
            query.append(" AND (A.KAIGAIBUNYA_NAME LIKE '%")
                 .append(EscapeUtil.toSqlString(searchInfo.getKaigaibunyaName()))
                 .append("%' OR A.KAIGAIBUNYA_NAME_RYAKU LIKE '%")
                 .append(EscapeUtil.toSqlString(searchInfo.getKaigaibunyaName()))
                 .append("%')");
        }
        //�\���ԍ�
        if(searchInfo.getUketukeNo() != null && searchInfo.getUketukeNo().length() != 0){
            query.append(" AND A.UKETUKE_NO = '")
                 .append(EscapeUtil.toSqlString(searchInfo.getUketukeNo()))         
                 .append("'");
        }
        //�]���i�_�j�i���j
        if(searchInfo.getHyokaHigh() != null && searchInfo.getHyokaHigh().length() != 0){
            query.append(" AND A.KEKKA1_TEN <= '")
                 .append(EscapeUtil.toSqlString(searchInfo.getHyokaHigh()))         
                 .append("'");
        }
        //�]���i�_�j�i��j
        if(searchInfo.getHyokaLow() != null && searchInfo.getHyokaLow().length() != 0){
            query.append(" AND A.KEKKA1_TEN >= '")
                 .append(EscapeUtil.toSqlString(searchInfo.getHyokaLow()))          
                 .append("'");
        }
        //���Ƌ敪
        if(searchInfo.getJigyoKubun() != null && searchInfo.getJigyoKubun().length() != 0){
            query.append(" AND A.JIGYO_KUBUN = '")
                 .append(EscapeUtil.toSqlString(searchInfo.getJigyoKubun()))            
                 .append("'");
        }

        query.append(" ORDER BY")
             .append(" TO_NUMBER(NVL(A.KEKKA1_TEN, '-1')) DESC")    //�ꎞ�R������(�v)�̍~��
             .append(", A.KEKKA1_TEN_SORTED DESC")                  //�ꎞ�R������(�����]�_)�̍~��
             .append(", A.SYSTEM_NO ASC")                           //�\���ԍ��̏���   
             //2005/11/15 ���Q�֌W�u-�v���Z�b�g�����̂ŁA������Ń\�[�g����
             //.append(", TO_NUMBER(NVL(REPLACE(B.KEKKA_TEN, '-', '0'), '-1')) DESC")   //�R������(�_��)�̍~��
             .append(", NVL(B.KEKKA_TEN, ' ') DESC")    //�R������(�_��)�̍~��

             .append(", B.SHINSAIN_NO ASC");                        //�R�����ԍ��̏���  

        //for debug
        if(log.isDebugEnabled()){
            log.debug("query:" + query);
        }

        return SelectUtil.selectPageInfo(connection, (SearchInfo)searchInfo, query.toString());
    }   

    /**
     * �w�茟�������ɊY������\���]���f�[�^���擾����B
     * 
     * @param connection
     * @param searchInfo
     * @return
     * @throws DataAccessException
     * @throws NoDataFoundException
     * @throws ApplicationException  �\���]����񂪃Z�b�g����Ă��Ȃ������ꍇ
     */
    public List searchCsvHyokaList(
            Connection connection,
            HyokaSearchInfo searchInfo)
            throws DataAccessException, NoDataFoundException, ApplicationException {

        String select = "SELECT"
                        + " B.JIGYO_ID"                             //����ID
                        + ", B.UKETUKE_NO"                          //�\���ԍ�
                        + ", A.NENDO"                               //�N�x
                        + ", A.KAISU"                               //��
                        + ", A.JIGYO_NAME"                          //���Ɩ�
                        + ", A.JURI_SEIRI_NO"                       //�����ԍ�                  2005/10/21�@�ǉ��ibaba�j
                        + ", A.NAME_KANJI_SEI"                      //�\���Ҏ����i������-���j
                        + ", A.NAME_KANJI_MEI"                      //�\���Ҏ����i������-��)
                        + ", A.SHOZOKU_CD"                          //�����@�փR�[�h
                        + ", A.SHOZOKU_NAME"                        //�����@�֖�
                        + ", A.SHOZOKU_NAME_RYAKU"                  //�����@�֖��i���́j
                        + ", A.BUKYOKU_CD"                          //���ǃR�[�h
                        + ", A.BUKYOKU_NAME"                        //���ǖ�
                        + ", A.BUKYOKU_NAME_RYAKU"                  //���ǖ��i���́j
                        + ", A.SHOKUSHU_CD"                         //�E��R�[�h
                        + ", A.SHOKUSHU_NAME_KANJI"                 //�E���i�a���j
                        + ", A.SHOKUSHU_NAME_RYAKU"                 //�E���i���́j
                        + ", A.KADAI_NAME_KANJI"                    //�����ۑ薼(�a���j
                        + ", A.KANTEN"                              //���E�̊ϓ_
                        + ", A.KEI_NAME"                            //�n���̋敪
                        + ", A.KENKYU_NO"                           //�\���Ҍ����Ҕԍ�
                        + ", SUBSTR(REPLACE(REPLACE(A.KEKKA1_ABC, 'A', ',A'), 'B', ',B'), 2, 17)"       //�P���R������(ABC)
                        + ", B.SHINSAIN_NAME_KANJI_SEI"             //�R�������i������-���j
                        + ", B.SHINSAIN_NAME_KANJI_MEI"             //�R�������i������-��)
                        + ", B.SHOZOKU_NAME SHINSAIN_SHOZOKU_NAME"  //�R���������@�֖��@���@�\���҂Ƃ��Ԃ�̂ŔO�̂��߃��l�[��
                        + ", B.BUKYOKU_NAME SHINSAIN_BUKYOKU_NAME"  //�R�������ǖ��@���@�\���҂Ƃ��Ԃ�̂ŔO�̂��߃��l�[��
                        + ", B.KEKKA_ABC"                           //�R������
                        + ", B.COMMENT1"                            //�R�����g1
                        + ", B.COMMENT2"                            //�R�����g2
                        + ", B.COMMENT3"                            //�R�����g3
                        + ", B.COMMENT4"                            //�R�����g4
                        + ", B.COMMENT5"                            //�R�����g5
                        + ", B.COMMENT6"                            //�R�����g6
                        + " FROM"
                        + " SHINSEIDATAKANRI"+dbLink+" A"
                        + ", SHINSAKEKKA"+dbLink+" B"
                        
                        //�������A�R������1�l�����蓖�Ă��Ă��Ȃ��\���f�[�^��\�������Ȃ����߂̐���
                        //�R������1�l�ȏ㊄�蓖�Ă��Ă���SYSTEM_NO���������Ă���A����SYSTEM_NO�����������Ƃ���B
                        + " WHERE EXISTS"
                        + " (SELECT *"
                        + " FROM"
                        + " SHINSEIDATAKANRI" + dbLink + " A"
                        + ", SHINSAKEKKA" + dbLink + " B"
                        + " WHERE A.SYSTEM_NO = B.SYSTEM_NO"
                        + " AND A.JIGYO_KUBUN = B.JIGYO_KUBUN"
                        + " AND B.SHINSAIN_NO NOT LIKE '@%')"        //�R������1�l�����蓖�Ă��Ă��Ȃ�(�S��@XXX)���ƈ���������Ȃ�

                        + " AND A.DEL_FLG = 0"
                        + " AND A.SYSTEM_NO = B.SYSTEM_NO"
                        + " AND A.JOKYO_ID IN ('08', '09', '10', '11', '12')";
        
        //-----���������I�u�W�F�N�g�̓��e��SQL�Ɍ������Ă���-----
        StringBuffer query = new StringBuffer(select);

        //���S��v��
        //���Ɩ�(CD)
//      if(searchInfo.getJigyoId() != null && )
//      LabelValueBean labelValueBean = new LabelValueBean();
//      List jigyoList = searchInfo.getJigyoList();
//      if(jigyoList != null && jigyoList.size() > 0) {
//          query.append(" AND SUBSTR(A.JIGYO_ID,3,5) IN (");
//          int i;
//          for(i = 0; i < jigyoList.size()-1; i++) {
//              labelValueBean = (LabelValueBean)jigyoList.get(i);
//              query.append("'")
//                   .append(EscapeUtil.toSqlString(labelValueBean.getValue()))
//                   .append("', ");
//          }
//          labelValueBean = (LabelValueBean)jigyoList.get(i);
//          query.append("'")
//               .append(EscapeUtil.toSqlString(labelValueBean.getValue()))
//               .append("')");
//      }
        //�N�x
        if(searchInfo.getNendo() != null && searchInfo.getNendo().length() != 0){
            query.append(" AND A.NENDO = '")
                 .append(EscapeUtil.toSqlString(searchInfo.getNendo()))         
                 .append("'");
        }
        //��
        if(searchInfo.getKaisu() != null && searchInfo.getKaisu().length() != 0){
            query.append(" AND A.KAISU = '")
                 .append(EscapeUtil.toSqlString(searchInfo.getKaisu()))         
                 .append("'");
        }
        //�\���ԍ�
        if(searchInfo.getUketukeNo() != null && searchInfo.getUketukeNo().length() != 0){
            query.append(" AND A.UKETUKE_NO = '")
                 .append(EscapeUtil.toSqlString(searchInfo.getUketukeNo()))         
                 .append("'");
        }
        //�����@�փR�[�h
        if(searchInfo.getShozokuCd() != null && searchInfo.getShozokuCd().length() != 0){
            query.append(" AND A.SHOZOKU_CD = '")
                 .append(EscapeUtil.toSqlString(searchInfo.getShozokuCd()))         
                 .append("'");
        }
        //�זڔԍ�
        if(searchInfo.getBunkasaimokuCd() != null && searchInfo.getBunkasaimokuCd().length() != 0){
            query.append(" AND A.BUNKASAIMOKU_CD = '")
                 .append(EscapeUtil.toSqlString(searchInfo.getBunkasaimokuCd()))            
                 .append("'");
        }
        //������v��
        //�n���̋敪�Ɨ��̗̂�������������
        if(searchInfo.getKeiName() != null && searchInfo.getKeiName().length() != 0){
            query.append(" AND (A.KEI_NAME LIKE '%" + EscapeUtil.toSqlString(searchInfo.getKeiName())
                + "%' OR A.KEI_NAME_RYAKU LIKE '%" + EscapeUtil.toSqlString(searchInfo.getKeiName()) + "%')");
        }
        //�\���Җ��i�����F���j�i������v�j
        if(searchInfo.getNameKanjiSei() != null && searchInfo.getNameKanjiSei().length() != 0){
            query.append(" AND A.NAME_KANJI_SEI like '%")
                 .append(EscapeUtil.toSqlString(searchInfo.getNameKanjiSei()))
                 .append("%'");         
        }
        //�\���Җ��i�����F���j�i������v�j
        if(searchInfo.getNameKanjiMei() != null && searchInfo.getNameKanjiMei().length() != 0){
            query.append(" AND A.NAME_KANJI_MEI like '%")
                 .append(EscapeUtil.toSqlString(searchInfo.getNameKanjiMei()))
                 .append("%'");         
        }
        //�\���Җ��i�t���K�i�F���j�i������v�j
        if(searchInfo.getNameKanaSei() != null && searchInfo.getNameKanaSei().length() != 0){
            query.append(" AND A.NAME_KANA_SEI like '%")
                 .append(EscapeUtil.toSqlString(searchInfo.getNameKanaSei()))
                 .append("%'");         
        }
        //�\���Җ��i�t���K�i�F���j�i������v�j
        if(searchInfo.getNameKanaMei() != null && searchInfo.getNameKanaMei().length() != 0){
            query.append(" AND A.NAME_KANA_MEI like '%")
                 .append(EscapeUtil.toSqlString(searchInfo.getNameKanaMei()))
                 .append("%'");         
        }
        //�\���Җ��i���[�}���F���j�i������v�j
        if(searchInfo.getNameRoSei() != null && searchInfo.getNameRoSei().length() != 0){
            query.append(" AND UPPER(A.NAME_RO_SEI) like '%")
                 .append(EscapeUtil.toSqlString(searchInfo.getNameRoSei().toUpperCase()))
                 .append("%'");         
        }               
        //�\���Җ��i���[�}���F���j�i������v�j
        if(searchInfo.getNameRoMei() != null && searchInfo.getNameRoMei().length() != 0){
            query.append(" AND UPPER(A.NAME_RO_MEI) like '%")
                 .append(EscapeUtil.toSqlString(searchInfo.getNameRoMei().toUpperCase()))
                 .append("%'");         
        }
        //���Ƌ敪
        if(searchInfo.getJigyoKubun() != null && searchInfo.getJigyoKubun().length() != 0){
            query.append(" AND A.JIGYO_KUBUN = '")
                 .append(EscapeUtil.toSqlString(searchInfo.getJigyoKubun()))            
                 .append("'");
        }
        //�����ԍ�  2005/10/21 �ǉ��ibaba�j
        if(searchInfo.getSeiriNo() != null && searchInfo.getSeiriNo().length() != 0){
                  query.append(" AND A.JURI_SEIRI_NO LIKE '%")
                       .append(EscapeUtil.toSqlString(searchInfo.getSeiriNo()))
                       .append("%'");           
        }
//      query.append(" ORDER BY")
//           .append(" LENGTH(NVL(REPLACE(A.KEKKA1_ABC, '-', ''),' ')) DESC");  //�\�����̐R������(ABC)�̐R�����ʐ��̍~��

        if(searchInfo.getHyojiHoshiki() != null && searchInfo.getHyojiHoshiki().equals("1")) {
            query.append(" ORDER BY TO_NUMBER(NVL(A.KEKKA1_TEN, '-1')) DESC")                                                           //�\�����̐R������(�_)�̍~��
//               .append(", LENGTH(NVL(REPLACE(REPLACE(REPLACE(REPLACE(KEKKA1_ABC, 'A-', ''), 'B-', ''), 'A', '00'), 'B', ''), ' ')) DESC")     //A�̑�����
//               .append(", LENGTH(NVL(REPLACE(REPLACE(REPLACE(REPLACE(KEKKA1_ABC, 'A-', '00'), 'B-', ''), 'A', ''), 'B', ''), ' ')) DESC")     //A-�̑�����
//               .append(", LENGTH(NVL(REPLACE(REPLACE(REPLACE(REPLACE(KEKKA1_ABC, 'A-', ''), 'B-', ''), 'A', ''), 'B', '00'), ' ')) DESC")     //B�̑�����
//               .append(", LENGTH(NVL(REPLACE(REPLACE(REPLACE(REPLACE(KEKKA1_ABC, 'A-', ''), 'B-', '00'), 'A', ''), 'B', ''), ' ')) DESC");        //B-�̑�����
            .append(", NVL(REPLACE(REPLACE(REPLACE(REPLACE(KEKKA1_ABC, 'A-', '3'), 'B-', '1'), 'A', '4'), 'B', '2'), '0') DESC");       //�\�����̐R������(ABC)�̏���
//           } else if(searchInfo.getHyojiHoshiki() != null && searchInfo.getHyojiHoshiki().equals("2")) {
        } else {    //"1","2"�Ńq�b�g���Ȃ������ꍇ�G���[�ƂȂ�̂ŁAelse�Ƃ��Ă���
//          query.append(" ORDER BY LENGTH(NVL(REPLACE(REPLACE(REPLACE(REPLACE(KEKKA1_ABC, 'A-', ''), 'B-', ''), 'A', '00'), 'B', ''), ' ')) DESC")     //A�̑�����
//               .append(", LENGTH(NVL(REPLACE(REPLACE(REPLACE(REPLACE(KEKKA1_ABC, 'A-', '00'), 'B-', ''), 'A', ''), 'B', ''), ' ')) DESC")     //A-�̑�����
//               .append(", LENGTH(NVL(REPLACE(REPLACE(REPLACE(REPLACE(KEKKA1_ABC, 'A-', ''), 'B-', ''), 'A', ''), 'B', '00'), ' ')) DESC")     //B�̑�����
//               .append(", LENGTH(NVL(REPLACE(REPLACE(REPLACE(REPLACE(KEKKA1_ABC, 'A-', ''), 'B-', '00'), 'A', ''), 'B', ''), ' ')) DESC")     //B-�̑�����
            query.append(" ORDER BY NVL(REPLACE(REPLACE(REPLACE(REPLACE(KEKKA1_ABC, 'A-', '3'), 'B-', '1'), 'A', '4'), 'B', '2'), '0')DESC")        //�\�����̐R������(ABC)�̏���
                 .append(", TO_NUMBER(NVL(A.KEKKA1_TEN, '-1')) DESC");                                                          //�\�����̐R������(�_)�̍~��
        }
        query.append(", A.SYSTEM_NO ASC");                                          //�\���ԍ��̏���
        query.append(", A.JIGYO_ID ASC");                                           //����ID�̏���
        query.append(", B.KEKKA_ABC ASC");                                          //�R������(ABC)�̏���
        query.append(", B.KEKKA_TEN DESC");                                         //�R������(�_��)�̍~��
        query.append(", B.SHINSAIN_NO ASC");                                        //�R�����ԍ��̏���
        query.append(", B.JIGYO_KUBUN ASC");                                        //���Ƌ敪�̏���

        //for debug
        if(log.isDebugEnabled()){
            log.debug("query:" + query);
        }

        return SelectUtil.selectCsvList(connection, query.toString(), false);
    }   

    //2005/04/22 �ǉ� ��������--------------------------------------------
    //���R�@��荞�݃}�X�^�ōX�V����\���f�[�^�����邩�ǂ����̃`�F�b�N�pSQL��ǉ�
    /**
     * ��荞�݃}�X�^�̃f�[�^���\�����e�[�u���ɑ��݂��邩�`�F�b�N����B
     * 
     * @param connection
     * @param info
     * @return String
     * @throws DataAccessException
     * @throws NoDataFoundException
     */
    public String selectShinseiTorikomiData(Connection connection, ShinseiDataInfo info)
        throws DataAccessException, NoDataFoundException {
        
        String systemNo = null;
        
        String bunkatsuNo = info.getKadaiInfo().getBunkatsuNo();
        String saimokuCd = info.getKadaiInfo().getBunkaSaimokuCd();
        
        String query = "SELECT "
                     + " SYSTEM_NO"                  //�V�X�e����t�ԍ�
                     + " FROM" 
                     + " SHINSEIDATAKANRI"
                     + " WHERE"
                     + " UKETUKE_NO = ?"
                     + " AND JIGYO_ID = ?"
                     + " AND DEL_FLG = 0";               //�폜�t���O��[0]
        if(saimokuCd != null && !saimokuCd.equals("")){
            query +=  " AND BUNKASAIMOKU_CD = ?";
        }
        if(bunkatsuNo!= null && !bunkatsuNo.equals("-")){
            query += " AND BUNKATSU_NO = ?";
        }
//2006/05/09 �ǉ���������           
//        query = query + " AND (JIGYO_KUBUN = 4 or JIGYO_KUBUN = 6 )"
//      query = query + " AND ( JIGYO_KUBUN = "
//                      + IJigyoKubun.JIGYO_KUBUN_KIBAN 
//                      + "OR JIGYO_KUBUN = "
//                      + IJigyoKubun.JIGYO_KUBUN_WAKATESTART 
//                      + "OR JIGYO_KUBUN = "
//                      + IJigyoKubun.JIGYO_KUBUN_SHOKUSHINHI
//                      + ")"
        query = query   + " AND JIGYO_KUBUN IN ("
                        + StringUtil.changeIterator2CSV(
                                JigyoKubunFilter.Convert2ShinseishoJigyoKubun(
                                        IJigyoKubun.JIGYO_KUBUN_KIBAN).iterator(), true) + ")"
//�c�@�ǉ������܂�                      
                    + " FOR UPDATE"
                    ;
        PreparedStatement preparedStatement = null;
        ResultSet recordSet = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            int index = 1;
            DatabaseUtil.setParameter(preparedStatement,index++, info.getUketukeNo());
            DatabaseUtil.setParameter(preparedStatement,index++, info.getJigyoId());    //�V�X�e����t�ԍ�
            if(saimokuCd != null && !saimokuCd.equals("")){
                DatabaseUtil.setParameter(preparedStatement,index++, saimokuCd);
            }
            if(bunkatsuNo!= null && !bunkatsuNo.equals("-")){
                DatabaseUtil.setParameter(preparedStatement,index++, bunkatsuNo);
            }
            recordSet = preparedStatement.executeQuery();
            if (recordSet.next()) {
                systemNo =recordSet.getString("SYSTEM_NO");
            }else{
                throw new NoDataFoundException(
                    "�\���f�[�^�Ǘ��e�[�u���ɊY������f�[�^��������܂���B" +
                    "�����L�[�F�\���ԍ�'"+ info.getUketukeNo()+ "', "+
                    "����ID'"+info.getJigyoId()+"',"+
                    "�זڔԍ�'"+info.getKadaiInfo().getBunkaSaimokuCd()+"',"+
                    "�����ԍ�'"+info.getKadaiInfo().getBunkatsuNo()+"'");
            }
            
        } catch (SQLException ex) {
            throw new DataAccessException("�\���f�[�^�Ǘ��e�[�u���������s���ɗ�O���������܂����B ", ex);
        } finally {
            DatabaseUtil.closeResource(recordSet, preparedStatement);
        }
        return systemNo;
    }

    /**
     * �����҃}�X�^�Ɍ����ҏ�񂪑��݂��邩�`�F�b�N
     * @param connection
     * @param strSQL
     * @return
     * @throws DataAccessException
     * @throws NoDataFoundException
     */
    public String selectKenkyuushaCount(
            Connection connection,
            String strSQL
            )throws DataAccessException, NoDataFoundException {

        String              strCount            =   null;
        //���g�p�̈׃R�����g����
        //List              lstResult           =   new ArrayList();    //�������ʎ擾�p���X�g
        ResultSet           recordSet           =   null;
        PreparedStatement   preparedStatement   =   null;

        try{
            preparedStatement = connection.prepareStatement(strSQL);
            recordSet = preparedStatement.executeQuery();

            if(recordSet.next()){
                strCount =recordSet.getString(IShinseiMaintenance.STR_COUNT);
            }else{
                throw new NoDataFoundException("�����҃}�X�^�e�[�u���ɊY������f�[�^��������܂���B");
            }
        }
        catch (SQLException ex) {
            throw new DataAccessException("�����ҏ��̌������ɗ�O���������܂����B",   ex);}
//      catch(DataAccessException ex){
//          throw new DataAccessException("�����ҏ��̌������ɗ�O���������܂����B",   ex);}
        catch(NoDataFoundException ex){
            throw new NoDataFoundException("�Y�����錤���ҏ�񂪑��݂��܂���B",ex);}
        finally{
            //TODO:finally�ł̏���
        }
        return strCount;
    }
// Horikoshi End

//2005/08/18 takano �d���`�F�b�N�̃��W�b�N�œK���ɂ��ǉ� �������� ---
    /**
     * ����̈�p�d���`�F�b�N�p�̏���SQL�𐶐�����B
     * @param shinseiDataInfo
     * @throws IllegalArgumentException �����敪���z��O�̏ꍇ
     * @return
     */
    private String makeQuery4DupTokutei(ShinseiDataInfo shinseiDataInfo)
            throws IllegalArgumentException {

        //------------------------------------------------------
        // �d�����[���̎d�l������e�Ղɂ��邽�߁A�����񌋍��Ɋ�����
        // StringBuffer���g���Ă��Ȃ��L�q������B
        //------------------------------------------------------
        
        final String kenkyuKubun = StringUtils.defaultString(shinseiDataInfo.getKenkyuKubun());
        final String komokuNo    = StringUtils.defaultString(shinseiDataInfo.getRyouikiKoumokuNo());
        final String ryoikiNo    = EscapeUtil.toSqlString(StringUtils.defaultString(shinseiDataInfo.getRyouikiNo()));
        
        StringBuffer query = new StringBuffer();
        
        //===�u�v�挤���v�̂Ƃ� ===
        if(IShinseiMaintenance.KUBUN_KEIKAKU.equals(kenkyuKubun)){
            //�u�v�挤���v�Łu�����ǁv�̂Ƃ�
            if(IShinseiMaintenance.CHECK_ON.equals(shinseiDataInfo.getChouseiFlg())){
                query.append("AND ( ")
                     //--�����ňٗ̈�͏d���G���[
                     .append(" (KENKYU_KUBUN = '" + IShinseiMaintenance.KUBUN_KEIKAKU + "' ")
                     .append("  AND KOMOKU_NO ='" + IShinseiMaintenance.HAN_SOUKATU + "' ")
                     .append("  AND RYOIKI_NO <> '" + ryoikiNo + "') ")
                     //--�����ǂ͏d���G���[
                     .append("OR ")
                     .append(" (KENKYU_KUBUN = '" + IShinseiMaintenance.KUBUN_KEIKAKU + "' ")
                     .append("  AND CHOSEIHAN = '" + IShinseiMaintenance.CHECK_ON + "') ")
                     //--���̑��ňٗ̈�͏d���G���[
                     .append("OR ")
                     .append(" (KENKYU_KUBUN = '" + IShinseiMaintenance.KUBUN_KEIKAKU + "' ")
                     .append("  AND KOMOKU_NO <>'" + IShinseiMaintenance.HAN_SOUKATU + "' ")
                     .append("  AND KOMOKU_NO <>'" + IShinseiMaintenance.HAN_SHIEN + "' ")
                     .append("  AND CHOSEIHAN <> '" + IShinseiMaintenance.CHECK_ON + "' ")
                     .append("  AND RYOIKI_NO <> '" + ryoikiNo + "') ")
                     //--���匤���͏d���G���[
                     .append("OR ")
                     .append(" (KENKYU_KUBUN = '" + IShinseiMaintenance.KUBUN_KOUBO + "') ")
                     .append(") ")
                     ;
                
            //�u�v�挤���v�Łu�����v�̂Ƃ�
            }else if(komokuNo.equals(IShinseiMaintenance.HAN_SOUKATU)){
                query.append("AND ( ")
                     //--�����͏d���G���[
                     .append(" (KENKYU_KUBUN = '" + IShinseiMaintenance.KUBUN_KEIKAKU + "' ")
                     .append("  AND KOMOKU_NO ='" + IShinseiMaintenance.HAN_SOUKATU + "') ")
                     //--�����ǂňٗ̈�͏d���G���[
                     .append("OR ")
                     .append(" (KENKYU_KUBUN = '" + IShinseiMaintenance.KUBUN_KEIKAKU + "' ")
                     .append("  AND CHOSEIHAN = '" + IShinseiMaintenance.CHECK_ON + "' ")
                     .append("  AND RYOIKI_NO <> '" + ryoikiNo + "') ")
                     //--���̑��ňٗ̈�͏d���G���[
                     .append("OR ")
                     .append(" (KENKYU_KUBUN = '" + IShinseiMaintenance.KUBUN_KEIKAKU + "' ")
                     .append("  AND KOMOKU_NO <>'" + IShinseiMaintenance.HAN_SOUKATU + "' ")
                     .append("  AND KOMOKU_NO <>'" + IShinseiMaintenance.HAN_SHIEN + "' ")
                     .append("  AND CHOSEIHAN <> '" + IShinseiMaintenance.CHECK_ON + "' ")
                     .append("  AND RYOIKI_NO <> '" + ryoikiNo + "') ")
                     //--���匤���ňٗ̈�͏d���G���[
                     .append("OR ")
                     .append(" (KENKYU_KUBUN = '" + IShinseiMaintenance.KUBUN_KOUBO + "' ")
                     .append("  AND RYOIKI_NO <> '" + ryoikiNo + "') ")
                     .append(") ")
                     ;
                
            //�u�v�挤���v�Łu�x���v�̂Ƃ�
            }else if(komokuNo.equals(IShinseiMaintenance.HAN_SHIEN)){
                //�x���̂Ƃ��͏d���G���[����
                query.append("AND ( ")
                     .append(" 1=0 ")       //�K���q�b�g���Ȃ��悤�ɂ���
                     .append(") ")
                     ;
                
            //�u�v�挤���v�Łu���̑��v�̂Ƃ�
            }else{
                query.append("AND ( ")
                     //--�����ňٗ̈�͏d���G���[
                     .append(" (KENKYU_KUBUN = '" + IShinseiMaintenance.KUBUN_KEIKAKU + "' ")
                     .append("  AND KOMOKU_NO ='" + IShinseiMaintenance.HAN_SOUKATU + "' ")
                     .append("  AND RYOIKI_NO <> '" + ryoikiNo + "') ")
                     //--�����ǂňٗ̈�͏d���G���[
                     .append("OR ")
                     .append(" (KENKYU_KUBUN = '" + IShinseiMaintenance.KUBUN_KEIKAKU + "' ")
                     .append("  AND CHOSEIHAN = '" + IShinseiMaintenance.CHECK_ON + "' ")
                     .append("  AND RYOIKI_NO <> '" + ryoikiNo + "') ")
                     //--���̑��͏d���G���[
                     .append("OR ")
                     .append(" (KENKYU_KUBUN = '" + IShinseiMaintenance.KUBUN_KEIKAKU + "' ")
                     .append("  AND KOMOKU_NO <>'" + IShinseiMaintenance.HAN_SOUKATU + "' ")
                     .append("  AND KOMOKU_NO <>'" + IShinseiMaintenance.HAN_SHIEN + "' ")
                     .append("  AND CHOSEIHAN <> '" + IShinseiMaintenance.CHECK_ON + "') ")
                     //--���匤���͏d���G���[
                     .append("OR ")
                     .append(" (KENKYU_KUBUN = '" + IShinseiMaintenance.KUBUN_KOUBO+"') ")
                     .append(") ")
                     ;
                
            }
            
        //===�u���匤���v�̂Ƃ� ===/
        }else if(IShinseiMaintenance.KUBUN_KOUBO.equals(kenkyuKubun)){
            query.append("AND ( ")
                 //--�����ňٗ̈�͏d���G���[
                 .append(" (KENKYU_KUBUN = '" + IShinseiMaintenance.KUBUN_KEIKAKU + "' ")
                 .append("  AND KOMOKU_NO ='" + IShinseiMaintenance.HAN_SOUKATU + "' ")
                 .append("  AND RYOIKI_NO <> '" + ryoikiNo + "') ")
                 //--�����ǂ͏d���G���[
                 .append("OR ")
                 .append(" (KENKYU_KUBUN = '" + IShinseiMaintenance.KUBUN_KEIKAKU + "' ")
                 .append("  AND CHOSEIHAN = '" + IShinseiMaintenance.CHECK_ON + "') ")
                 //--���̑��͏d���G���[
                 .append("OR ")
                 .append(" (KENKYU_KUBUN = '" + IShinseiMaintenance.KUBUN_KEIKAKU + "' ")
                 .append("  AND KOMOKU_NO <>'" + IShinseiMaintenance.HAN_SOUKATU + "' ")
                 .append("  AND KOMOKU_NO <>'" + IShinseiMaintenance.HAN_SHIEN + "' ")
                 .append("  AND CHOSEIHAN <> '" + IShinseiMaintenance.CHECK_ON + "') ")
                 //--���匤���œ��̈�͏d���G���[
                 .append("OR ")
                 .append(" (KENKYU_KUBUN = '" + IShinseiMaintenance.KUBUN_KOUBO + "' ")
                 .append("  AND RYOIKI_NO = '" + ryoikiNo + "') ")
                 .append(") ")
                 ;
            
        //===�u�I�������v�̂Ƃ� ===/
        }else if(IShinseiMaintenance.KUBUN_SHUURYOU.equals(kenkyuKubun)){
            query.append("AND ( ")
                 //--�I�������œ��̈�͏d���G���[
                 .append(" (KENKYU_KUBUN = '" + IShinseiMaintenance.KUBUN_SHUURYOU + "' ")
                 .append("  AND RYOIKI_NO = '" + ryoikiNo + "') ")
                 .append(") ")
                 ;
            
        //=== �z��O�̌����敪�̏ꍇ�͗�O�𔭐�����
        }else{
            String msg = "����̈�̏d���`�F�b�N�ɂ����āA�����敪���z��O�ł��BkenkyuKubun="+kenkyuKubun;
            if(log.isDebugEnabled()){
                log.debug(msg);
            }
            throw new IllegalArgumentException(msg);
        }
        
        return query.toString();
    }
//2005/08/18 takano �d���`�F�b�N�̃��W�b�N�œK���ɂ��ǉ� �����܂� ---

    /**
     * [�O�N�x���傠��E�V�K]��[�O�N�x����Ȃ��E�p��]�̏d���������������SQL�𐶐�����B
     * @param   shinseiDataInfo
     * @return
     */
    private String makeQuery4DupZennenShinki(ShinseiDataInfo shinseiDataInfo) {
        
        StringBuffer query = new StringBuffer();
        
        //�V�K�ŁA�O�N�x����L��̏ꍇ
        if(IShinseiMaintenance.SHINSEI_NEW.equals(StringUtils.defaultString(shinseiDataInfo.getShinseiKubun()))
                && IShinseiMaintenance.ZENNEN_ON.equals(StringUtils.defaultString(shinseiDataInfo.getShinseiFlgNo()))) {
            //�O�N�x����Ȃ��ŁA�p���̉�����d���`�F�b�N���珜�O
            //���󕶎��͖��g�p��'0'�ɕϊ��B
            query.append("AND (NVL(SHINSEI_KUBUN, '0') <> '" + IShinseiMaintenance.SHINSEI_CONTINUE + "' ")
                 .append("OR NVL(SHINSEI_FLG_NO, '0') <> '" + IShinseiMaintenance.ZENNEN_OFF + "') ")
                 ;
        }
        //�p���ŁA�O�N�x����Ȃ��̏ꍇ
        else if(IShinseiMaintenance.SHINSEI_CONTINUE.equals(StringUtils.defaultString(shinseiDataInfo.getShinseiKubun()))
                && IShinseiMaintenance.ZENNEN_OFF.equals(StringUtils.defaultString(shinseiDataInfo.getShinseiFlgNo()))) {
            //�O�N�x����L��ŁA�V�K�̉�����d���`�F�b�N���珜�O
            query.append("AND (NVL(SHINSEI_KUBUN, '0') <> '" + IShinseiMaintenance.SHINSEI_NEW + "' ")
                 .append("OR NVL(SHINSEI_FLG_NO, '0') <> '" + IShinseiMaintenance.ZENNEN_ON + "') ")
                 ;
        }
        
        return query.toString();
    }

//2005/08/16 takano �d���`�F�b�N�̃��W�b�N�œK���ɂ��폜 �������� ---
// ������̓R�����g�A�E�g�ɂ��܂������A�ŏI�I�ɂ̓\�[�X�ォ��폜���Ă�������������������ėǂ��Ǝv���܂��B
//
//// 20050713 �d���`�F�b�N��SQL�쐬���\�b�h�@���e���Ƃŏd���`�F�b�N���s����ꍇ�̂���SQL�쐬���\�b�h
//  /**
//   * ����̈�@�d���`�F�b�NSQL�쐬
//   * @param shinseiDataInfo
//   * @return
//   */
//  private StringBuffer selectDupTokutei(
//          ShinseiDataInfo shinseiDataInfo
//          ){
//
//      String strQuery = "";
//      StringBuffer sbSQL= new StringBuffer(strQuery);
//      sbSQL.append("Select")
//              .append(" COUNT(SYSTEM_NO) " + IShinseiMaintenance.STR_COUNT + " ")
//              .append("FROM")
//              .append(" SHINSEIDATAKANRI ")
//              .append("WHERE")
//              .append(" JIGYO_ID = '"         + shinseiDataInfo.getJigyoId()  +   "' ");
//
//      //�V�X�e��NO�����݂���(�C���A�ĊJ)�ꍇ�ɂ͑Ώۂ���O��
//      if(shinseiDataInfo.getSystemNo() != null || shinseiDataInfo.getSystemNo().length() > 0){
//          sbSQL.append("AND")
//              .append(" SYSTEM_NO <> '"       + shinseiDataInfo.getSystemNo() +   "' ")
//              ;
//      }
//
//      // 20050627
//      if(userInfo.getShinseishaInfo().getShozokuCd() != null &&
//          userInfo.getShinseishaInfo().getShozokuCd() != ""){
//          sbSQL.append("AND")
//              .append(" SHOZOKU_CD = '" + userInfo.getShinseishaInfo().getShozokuCd() + "' ");
//      }
//      // End
//
//      if(userInfo.getShinseishaInfo().getShinseishaId() != null &&
//              userInfo.getShinseishaInfo().getShinseishaId() != ""){
//              sbSQL.append("AND")
//                  .append(" SHINSEISHA_ID = '" + userInfo.getShinseishaInfo().getShinseishaId() + "' ")
//                  ;
//          }
//
//      sbSQL.append("AND")
//              .append(" DEL_FLG = "           + IShinseiMaintenance.CHECK_OFF +   " ")
//              ;
//
//// 20050809 �d���`�F�b�N�̏����ɏ�ID��ǉ�
//      sbSQL.append(" AND JOKYO_ID >= '02' ")
//          .append(" AND JOKYO_ID <> '05' ")
//          ;
////Horikoshi
//
///** �v�挤�� **/
//      if(IShinseiMaintenance.KUBUN_KEIKAKU.equals(shinseiDataInfo.getKenkyuKubun())){
//          if(IShinseiMaintenance.CHECK_ON.equals(shinseiDataInfo.getChouseiFlg()) ||
//              shinseiDataInfo.getRyouikiKoumokuNo().indexOf(IShinseiMaintenance.HAN_SOUKATU) == 0){
//              //�����ǁA�܂��͑����ǂ������ꍇ
//              if(IShinseiMaintenance.SHINSEI_NEW.equals(shinseiDataInfo.getShinseiKubun())){
//                  //�V�K
//                  sbSQL.append("AND ("
//                      //�v�挤���̑Ώ�
//                      + "(KENKYU_KUBUN = " + IShinseiMaintenance.KUBUN_KEIKAKU + " AND RYOIKI_NO <> '" + shinseiDataInfo.getRyouikiNo() + "' AND (CHOSEIHAN = " + IShinseiMaintenance.CHECK_ON + " OR SUBSTR(KOMOKU_NO, 1, 1) <> '" + IShinseiMaintenance.HAN_SHIEN + "'))"
//                      //���匤���̑Ώ�
//                      + "OR (KENKYU_KUBUN = " + IShinseiMaintenance.KUBUN_KOUBO + " AND (CHOSEIHAN = " + IShinseiMaintenance.CHECK_ON + " OR SUBSTR(KOMOKU_NO, 1, 1) <> '" + IShinseiMaintenance.HAN_SHIEN + "')) "
//                      //�I�������̑Ώہi�V�K�̏ꍇ�ɂ̓`�F�b�N�����j
//                      + ")")
//                      ;
//              }
//              else {
//                  //�p��
//                  sbSQL.append("AND ("
//                      //�v�挤���̑Ώ�
//                      + "(KENKYU_KUBUN = " + IShinseiMaintenance.KUBUN_KEIKAKU + " AND RYOIKI_NO <> '" + shinseiDataInfo.getRyouikiNo() + "' AND (CHOSEIHAN = " + IShinseiMaintenance.CHECK_ON + " OR SUBSTR(KOMOKU_NO, 1, 1) <> '" + IShinseiMaintenance.HAN_SHIEN + "'))"
//                      //���匤���̑Ώ�
//                      + "OR (KENKYU_KUBUN = " + IShinseiMaintenance.KUBUN_KOUBO + " AND (CHOSEIHAN = " + IShinseiMaintenance.CHECK_ON + " OR SUBSTR(KOMOKU_NO, 1, 1) <> '" + IShinseiMaintenance.HAN_SHIEN + "')) "
//                      //�I�������̑Ώہi�V�K�̏ꍇ�ɂ͏I�������̑��݂��`�F�b�N�j
//                      + "OR (KENKYU_KUBUN = " + IShinseiMaintenance.KUBUN_SHUURYOU + "))")
//                      ;
//              }
//          }
//          else{
//              if(shinseiDataInfo.getRyouikiKoumokuNo().indexOf(IShinseiMaintenance.HAN_SHIEN) == 0){
//                  //�x���ǂ������ꍇ
//                  if(IShinseiMaintenance.SHINSEI_NEW.equals(shinseiDataInfo.getShinseiKubun())){
//                      //�V�K�i���ׂďd��OK�̂��߃`�F�b�N�����j
//                      return null;
//                  }
//                  else {
//                      //�p���i�I���������o�^����Ă����ꍇ�̂݃G���[�j
//                      sbSQL.append("AND (KENKYU_KUBUN = " + IShinseiMaintenance.KUBUN_SHUURYOU + ")")
//                      ;
//                  }
//              }
//          }
//      }
//
///** ���匤�� **/
//      else if(IShinseiMaintenance.KUBUN_KOUBO.equals(shinseiDataInfo.getKenkyuKubun())){
//          sbSQL.append("AND ((KENKYU_KUBUN = " + IShinseiMaintenance.KUBUN_KEIKAKU + " AND (CHOSEIHAN = " + IShinseiMaintenance.CHECK_ON + " OR SUBSTR(KOMOKU_NO, 1, 1) <> '" + IShinseiMaintenance.HAN_SHIEN + "')) "
//                  + "OR (KENKYU_KUBUN = " + IShinseiMaintenance.KUBUN_KOUBO + " AND RYOIKI_NO = '" + shinseiDataInfo.getRyouikiNo() + "') "
//                  + ")")
//                  ;
//      }
//
///** �I������ **/
//      else if(IShinseiMaintenance.KUBUN_SHUURYOU.equals(shinseiDataInfo.getKenkyuKubun())){
//          if(IShinseiMaintenance.SHINSEI_NEW.equals(shinseiDataInfo.getShinseiKubun())){
//              //�V�K
//              sbSQL.append("AND (KENKYU_KUBUN = " + IShinseiMaintenance.KUBUN_SHUURYOU + ")")
//              ;
//          }
//          else{
//              //�p��
//              sbSQL.append("AND ((KENKYU_KUBUN = " + IShinseiMaintenance.KUBUN_KEIKAKU + ") OR (KENKYU_KUBUN = " + IShinseiMaintenance.KUBUN_SHUURYOU + "))")
//              ;
//          }
//      }
//
//      return sbSQL;
//  }
//
//
//
//  /**
//   * �w�p�n��(����)�@�d���`�F�b�NSQL�쐬
//   * @param shinseiDataInfo
//   * @return
//   */
//  private StringBuffer selectDupGakuKoubo(
//          ShinseiDataInfo shinseiDataInfo
//          ){
//
//      String strQuery = "";
//      StringBuffer sbSQL= new StringBuffer(strQuery);
//      sbSQL.append("Select")
//          .append(" COUNT(SYSTEM_NO) " + IShinseiMaintenance.STR_COUNT + " ")
//          .append("FROM")
//          .append(" SHINSEIDATAKANRI ")
//          .append("WHERE")
//          .append(" JIGYO_ID = '"         + shinseiDataInfo.getJigyoId()  +   "' ")
//          ;
//
//      //�V�X�e��NO�����݂���(�C���A�ĊJ)�ꍇ�ɂ͑Ώۂ���O��
//      if(shinseiDataInfo.getSystemNo() != null || shinseiDataInfo.getSystemNo().length() > 0){
//          sbSQL.append("AND")
//              .append(" SYSTEM_NO <> '"       + shinseiDataInfo.getSystemNo() +   "' ")
//              ;
//      }
//
//      //���������@�ւ���
//      if(userInfo.getShinseishaInfo().getShozokuCd() != null &&
//          userInfo.getShinseishaInfo().getShozokuCd() != ""){
//          sbSQL.append("AND")
//              .append(" SHOZOKU_CD = '" + userInfo.getShinseishaInfo().getShozokuCd() + "' ")
//              ;
//      }
//
//// 20050720
//      //������(�����)����
////        if(userInfo.getShinseishaInfo().getKenkyuNo() != null &&
////                userInfo.getShinseishaInfo().getKenkyuNo() != ""){
////                sbSQL.append("AND")
////                    .append(" KENKYU_NO = '" + userInfo.getShinseishaInfo().getKenkyuNo() + "' ")
////                    ;
////            }
//      if(userInfo.getShinseishaInfo().getShinseishaId() != null &&
//              userInfo.getShinseishaInfo().getShinseishaId() != ""){
//              sbSQL.append("AND")
//                  .append(" SHINSEISHA_ID = '" + userInfo.getShinseishaInfo().getShinseishaId() + "' ")
//                  ;
//          }
//// ���������s�����Ă������ߒǉ�
//
//      //�폜����Ă��Ȃ�
//      sbSQL.append("AND")
//              .append(" DEL_FLG = "           + IShinseiMaintenance.CHECK_OFF +   " ")
//              ;
//
//// 20050809 �d���`�F�b�N�̏����ɏ�ID��ǉ�
//      sbSQL.append(" AND JOKYO_ID >= '02'")
//          .append(" AND JOKYO_ID <> '05'")
//          ;
////Horikoshi
//
//      return sbSQL;
//  }
//
//  /**
//   * �w�p�n��(�����)�@�d���`�F�b�NSQL�쐬
//   * @param shinseiDataInfo
//   * @return
//   */
//  private StringBuffer selectDupGakuHikoubo(
//          ShinseiDataInfo shinseiDataInfo
//          ){
//
//      String strQuery = "";
//      StringBuffer sbSQL= new StringBuffer(strQuery);
//      sbSQL.append("Select")
//          .append(" COUNT(SYSTEM_NO)" + IShinseiMaintenance.STR_COUNT + " ")
//          .append("FROM")
//          .append(" SHINSEIDATAKANRI ")
//          .append("WHERE")
//          .append(" JIGYO_ID = '"         + shinseiDataInfo.getJigyoId()  +   "' ")
//          ;
//
//      //�V�X�e��NO�����݂���(�C���A�ĊJ)�ꍇ�ɂ͑Ώۂ���O��
//      if(shinseiDataInfo.getSystemNo() != null || shinseiDataInfo.getSystemNo().length() > 0){
//          sbSQL.append("AND")
//              .append(" SYSTEM_NO <> '"       + shinseiDataInfo.getSystemNo() +   "' ")
//              ;
//      }
//
//      //���������@�ւ���
//      if(userInfo.getShinseishaInfo().getShozokuCd() != null &&
//          userInfo.getShinseishaInfo().getShozokuCd() != ""){
//          sbSQL.append("AND")
//              .append(" SHOZOKU_CD = '" + userInfo.getShinseishaInfo().getShozokuCd() + "' ")
//              ;
//      }
//
//// 20050720
//      //������(�����)����
////        if(userInfo.getShinseishaInfo().getKenkyuNo() != null &&
////                userInfo.getShinseishaInfo().getKenkyuNo() != ""){
////                sbSQL.append("AND")
////                    .append(" KENKYU_NO = '" + userInfo.getShinseishaInfo().getKenkyuNo() + "' ")
////                    ;
////            }
//      if(userInfo.getShinseishaInfo().getShinseishaId() != null &&
//              userInfo.getShinseishaInfo().getShinseishaId() != ""){
//              sbSQL.append("AND")
//                  .append(" SHINSEISHA_ID = '" + userInfo.getShinseishaInfo().getShinseishaId() + "' ")
//                  ;
//          }
//// ���������s�����Ă������ߒǉ�
//
//      //�폜����Ă��Ȃ�
//      sbSQL.append("AND")
//              .append(" DEL_FLG = "           + IShinseiMaintenance.CHECK_OFF +   " ")
//              ;
//
//// 20050809 �d���`�F�b�N�̏����ɏ�ID��ǉ�
//      sbSQL.append(" AND JOKYO_ID >= '02'")
//          .append(" AND JOKYO_ID <> '05'")
//          ;
////Horikoshi
//
//      return sbSQL;
//  }
//
//  /**
//   * ���ʐ��i�@�d���`�F�b�NSQL�쐬
//   * @param shinseiDataInfo
//   * @return
//   */
//  private StringBuffer selectDupTokusui(
//          ShinseiDataInfo shinseiDataInfo
//          ){
//
//      String strQuery = "";
//      StringBuffer sbSQL= new StringBuffer(strQuery);
//      sbSQL.append("Select")
//          .append(" COUNT(SYSTEM_NO) " + IShinseiMaintenance.STR_COUNT + " ")
//          .append("FROM")
//          .append(" SHINSEIDATAKANRI ")
//          .append("WHERE")
//          .append(" JIGYO_ID = '"         + shinseiDataInfo.getJigyoId()  +   "' ")
//          ;
//
//      //�V�X�e��NO�����݂���(�C���A�ĊJ)�ꍇ�ɂ͑Ώۂ���O��
//      if(shinseiDataInfo.getSystemNo() != null || shinseiDataInfo.getSystemNo().length() > 0){
//          sbSQL.append("AND")
//              .append(" SYSTEM_NO <> '"       + shinseiDataInfo.getSystemNo() +   "' ")
//              ;
//      }
//
//      //���������@�ւ���
//      if(userInfo.getShinseishaInfo().getShozokuCd() != null &&
//          userInfo.getShinseishaInfo().getShozokuCd() != ""){
//          sbSQL.append("AND")
//              .append(" SHOZOKU_CD = '" + userInfo.getShinseishaInfo().getShozokuCd() + "' ")
//              ;
//      }
//
//// 20050720
//      //������(�����)����
////        if(userInfo.getShinseishaInfo().getKenkyuNo() != null &&
////                userInfo.getShinseishaInfo().getKenkyuNo() != ""){
////                sbSQL.append("AND")
////                    .append(" KENKYU_NO = '" + userInfo.getShinseishaInfo().getKenkyuNo() + "' ")
////                    ;
////            }
//      if(userInfo.getShinseishaInfo().getShinseishaId() != null &&
//              userInfo.getShinseishaInfo().getShinseishaId() != ""){
//              sbSQL.append("AND")
//                  .append(" SHINSEISHA_ID = '" + userInfo.getShinseishaInfo().getShinseishaId() + "' ")
//                  ;
//          }
//// ���������s�����Ă������ߒǉ�
//
//      //�폜����Ă��Ȃ�
//      sbSQL.append("AND")
//              .append(" DEL_FLG = "           + IShinseiMaintenance.CHECK_OFF +   " ")
//              ;
//
//// 20050809 �d���`�F�b�N�̏����ɏ�ID��ǉ�
//      sbSQL.append(" AND JOKYO_ID >= '02'")
//          .append(" AND JOKYO_ID <> '05'")
//          ;
////Horikoshi
//
//      return sbSQL;
//  }
//
//// Horikoshi
//2005/08/16 takano �d���`�F�b�N�̃��W�b�N�œK���ɂ��폜 �����܂� ---


    //2005/11/02 �ǉ� ��������--------------------------------------------------------------------------------
    //���R�@��荞�݃}�X�^�ŐR�����ʃe�[�u���̃f�[�^�������擾����SQL����ǉ��i�󗝓o�^���_�~�[�f�[�^��o�^���錏�����ύX(6��12)�ɂȂ����ׁj
    /**
     * �V�X�e���ԍ��{���Ƌ敪�̐R�����ʃf�[�^������Ԃ��B
     * 
     * @param connection
     * @param systemNo �V�X�e���ԍ�
     * @param jigyoKubun ���Ƌ敪
     * @return int
     * @throws DataAccessException
     */
    public int countShinsaKekkaData(
            Connection connection,
            String     systemNo,
            String     jigyoKubun)
            throws DataAccessException {

        String query =
            "SELECT COUNT(*) FROM SHINSAKEKKA"+dbLink
                + " WHERE"
                + " SYSTEM_NO = ?"
                + " AND"
                + " JIGYO_KUBUN = ?"
                ;
        PreparedStatement preparedStatement = null;
        ResultSet recordSet = null;
        try {
            //����
            preparedStatement = connection.prepareStatement(query);
            int index = 1;
            DatabaseUtil.setParameter(preparedStatement,index++,systemNo);
            DatabaseUtil.setParameter(preparedStatement,index++,jigyoKubun);
            recordSet = preparedStatement.executeQuery();
            int count = 0;
            if (recordSet.next()) {
                count = recordSet.getInt(1);
            }
            return count;
        } catch (SQLException ex) {
            throw new DataAccessException("�R�����ʃe�[�u���������s���ɗ�O���������܂����B ", ex);
        } finally {
            DatabaseUtil.closeResource(recordSet, preparedStatement);
        }
    }
    //2005/11/02 �ǉ� �����܂�--------------------------------------------------------------------------------

//   2006/06/15 dyh add start
    /**
     * �����v�撲���ꗗ�f�[�^��Ԃ��B
     * 
     * @param connection
     * @param ryoikiNo �̈�ԍ�
     * @param jokyoIds �\����ID
     * @return List
     * @throws DataAccessException
     * @throws NoDataFoundException
     */
    public List selectKeikakuTyosyoList(
            Connection connection,
            String ryoikiNo,
            String[] jokyoIds)
            throws DataAccessException,NoDataFoundException {

        // ��������:�̈�ԍ�(RYOIKI_NO),�\����ID(JOKYO_ID),�폜�t���O(DEL_FLG)
        StringBuffer addQuery = new StringBuffer();
        if(!StringUtil.isBlank(ryoikiNo)){
            addQuery.append(" AND ");
            addQuery.append(" S.RYOIKI_NO = '");
            addQuery.append(EscapeUtil.toSqlString(ryoikiNo));
            addQuery.append("' ");
        }
        if(jokyoIds != null && jokyoIds.length > 0){
            addQuery.append(" AND ");
            addQuery.append(" S.JOKYO_ID IN(");
            addQuery.append(StringUtil.changeArray2CSV(jokyoIds,true));
            addQuery.append(") ");
        }
        StringBuffer query = new StringBuffer();
        query.append("SELECT ");
        query.append(" R.RYOIKI_NAME,");          // ����̈於
        query.append(" S.KOMOKU_NO,");            // �������ڔԍ�
        query.append(" S.SYSTEM_NO,");            // �V�X�e���ԍ�
        query.append(" S.KENKYU_KUBUN,");         // �����敪
        query.append(" S.CHOSEIHAN,");            // ������
        query.append(" S.NAME_KANJI_SEI,");       // �\���Ҏ����i������-���j
        query.append(" S.NAME_KANJI_MEI,");       // �\���Ҏ����i������-���j
        query.append(" S.SHOZOKU_NAME_RYAKU,");   // �����@�֖��i���́j
        query.append(" S.BUKYOKU_NAME_RYAKU,");   // ���ǖ��i���́j
        query.append(" S.SHOKUSHU_NAME_RYAKU,");  // �E���i���́j
        query.append(" S.KENKYU_NO,");            // �\���Ҍ����Ҕԍ�
        query.append(" S.KADAI_NAME_KANJI,");     // �����ۑ薼(�a���j
        query.append(" S.EDITION,");              // ��
        query.append(" S.SAKUSEI_DATE,");         // �\�����쐬��
        query.append(" S.PDF_PATH, ");            // PDF�̊i�[�p�X
        
        //2006.11.06 iso��t�ԍ��i ���������@�֔ԍ����܂ށj�Ń\�[�g����悤�ύX
//        query.append(" SUBSTR(S.UKETUKE_NO,7,4) AS UKETUKE_NO ");   // �����ԍ�
        query.append(" S.UKETUKE_NO ");			  // �����ԍ�
        
        query.append(" FROM SHINSEIDATAKANRI S ");// �\���f�[�^�Ǘ��e�[�u��
        query.append(dbLink);
        query.append(" INNER JOIN RYOIKIKEIKAKUSHOINFO R ");// �̈�v�揑�i�T�v�j���Ǘ��e�[�u��
        query.append(dbLink);
        query.append(" ON S.RYOIKI_NO =�@R.KARIRYOIKI_NO ");
        query.append(" WHERE");
        query.append(" S.DEL_FLG = 0");
        query.append(" AND R.DEL_FLG = 0");
        query.append(addQuery);
        query.append(" ORDER BY S.KOMOKU_NO ASC,S.CHOSEIHAN DESC,UKETUKE_NO ASC");

        // for debug
        if(log.isDebugEnabled()){
            log.debug("query:" + query);
        }

        // �f�[�^���X�g�擾
        List dataList = SelectUtil.select(connection, query.toString());
        return SortUtil.sortByKomokuNo(dataList);
    }

    /**
     * �����v�撲���ꗗ�f�[�^��Ԃ��B
     * 
     * @param connection
     * @param ryoikiNo �̈�ԍ�
     * @param jokyoIds �\����ID
     * @return List
     * @throws DataAccessException
     * @throws NoDataFoundException
     */
    public List selectKeikakuTyosyoList(
            Connection connection,
            String ryoikiNo)
            throws DataAccessException,NoDataFoundException {

        // ��������:
        // �̈�ԍ�(RYOIKI_NO=�n�����u���̈�ԍ��v)
        // �\����ID(JOKYO_ID=[04,06,07,08,09,10,11,12,21,22,23,24]
        //       or (JOKYO_ID=[02,03,05] and SAISHINSEI_FLG=[3]))
        // �폜�t���O(DEL_FLG=0)
        StringBuffer addQuery = new StringBuffer();
        if(!StringUtil.isBlank(ryoikiNo)){
            addQuery.append(" AND ");
            addQuery.append(" S.RYOIKI_NO = '");
            addQuery.append(EscapeUtil.toSqlString(ryoikiNo));
            addQuery.append("' ");
        }
        String[] jokyoIds1 = new String[]{
                StatusCode.STATUS_GAKUSIN_SHORITYU,          // �w�U��t��:04
                StatusCode.STATUS_GAKUSIN_JYURI,             // �w�U��:06
                StatusCode.STATUS_GAKUSIN_FUJYURI,           // �w�U�s��:07
                StatusCode.STATUS_SHINSAIN_WARIFURI_SHORIGO, // �R��������U�菈����:08
                StatusCode.STATUS_WARIFURI_CHECK_KANRYO,     // ����U��`�F�b�N����:09
                StatusCode.STATUS_1st_SHINSATYU,             // �ꎟ�R����:10
                StatusCode.STATUS_1st_SHINSA_KANRYO,         // �ꎟ�R���F����:11
                StatusCode.STATUS_2nd_SHINSA_KANRYO,         // �񎟐R������:12
                StatusCode.STATUS_RYOUIKIDAIHYOU_KAKUNIN,    // �̈��\�Ҋm�F��:21
                StatusCode.STATUS_RYOUIKIDAIHYOU_KYAKKA,     // �̈��\�ҋp��:22
                StatusCode.STATUS_RYOUIKIDAIHYOU_KAKUTEIZUMI,// �̈��\�Ҋm��ς�:23
                StatusCode.STATUS_RYOUIKISHOZOKU_UKETUKE     // �̈��\�ҏ��������@�֎�t��:24
        };
        String[] jokyoIds2 = new String[]{
                StatusCode.STATUS_SHINSEISHO_MIKAKUNIN,      // �\�������m�F:02
                StatusCode.STATUS_SHOZOKUKIKAN_UKETUKETYU,   // �����@�֎�t��:03
                StatusCode.STATUS_SHOZOKUKIKAN_KYAKKA,       // �����@�֋p��:05
        };
        addQuery.append(" AND ");
        addQuery.append(" (S.JOKYO_ID IN(");
        addQuery.append(StringUtil.changeArray2CSV(jokyoIds1,true));
        addQuery.append(") ");
        addQuery.append(" OR (");
        addQuery.append(" S.JOKYO_ID IN(");
        addQuery.append(StringUtil.changeArray2CSV(jokyoIds2,true));
        addQuery.append(") AND S.SAISHINSEI_FLG = '3'))");

        StringBuffer query = new StringBuffer();
        query.append("SELECT ");
        query.append(" S.SYSTEM_NO,");            // �V�X�e���ԍ�
        query.append(" S.KOMOKU_NO,");            // �������ڔԍ�
        query.append(" S.KENKYU_KUBUN,");         // �����敪
        query.append(" S.CHOSEIHAN,");            // ������
        query.append(" S.NAME_KANJI_SEI,");       // �\���Ҏ����i������-���j
        query.append(" S.NAME_KANJI_MEI,");       // �\���Ҏ����i������-���j
        query.append(" S.SHOZOKU_NAME_RYAKU,");   // �����@�֖��i���́j
        query.append(" S.BUKYOKU_NAME_RYAKU,");   // ���ǖ��i���́j
        query.append(" S.SHOKUSHU_NAME_RYAKU,");  // �E���i���́j
        query.append(" S.KENKYU_NO,");            // �\���Ҍ����Ҕԍ�
        query.append(" S.KADAI_NAME_KANJI,");     // �����ۑ薼(�a���j
        query.append(" S.EDITION,");              // ��
        query.append(" S.SAKUSEI_DATE,");         // �\�����쐬��
        query.append(" S.RYOIKI_KAKUTEI_DATE,");  // �̈��\�Ҋm���
        //query.append(" S.PDF_PATH, ");            // PDF�̊i�[�p�X
        query.append(" M.RYOIKIDAIHYOSHA_HYOJI, ");// �̈��\�Ҍ����\��
        query.append(" M.JOKYO_NAME, ");          // �\���󋵖��́i���́j
        query.append(" S.JOKYO_ID,");             // �\����ID(�󋵖��̗p)
        query.append(" S.JIGYO_ID,");             // ����ID(�󋵖��̗p)
        query.append(" S.SAISHINSEI_FLG,");       // �Đ\���t���O(�󋵖��̗p)
        query.append(" S.KEKKA1_ABC,");           // �P���R������(ABC)(�󋵖��̗p)
        query.append(" S.KEKKA1_TEN,");           // �P���R������(�_��)(�󋵖��̗p)
        query.append(" S.KEKKA2");                // �Q���R������(�󋵖��̗p)
        query.append(" FROM SHINSEIDATAKANRI S"); // �\���f�[�^�Ǘ��e�[�u��
        query.append(dbLink);
        query.append(" INNER JOIN RYOIKIKEIKAKUSHOINFO R");// �̈�v�揑�i�T�v�j���Ǘ��e�[�u��
        query.append(dbLink);
        query.append(" ON S.RYOIKI_NO =�@R.KARIRYOIKI_NO ");
        query.append(" INNER JOIN MASTER_STATUS M");// �\���󋵃}�X�^�e�[�u��
        query.append(dbLink);
        query.append(" ON S.JOKYO_ID =�@M.JOKYO_ID ");
        query.append(" AND S.SAISHINSEI_FLG =�@M.SAISHINSEI_FLG ");
        query.append(" WHERE");
        query.append(" S.DEL_FLG = 0");
        query.append(" AND R.DEL_FLG = 0");
        query.append(addQuery);

        //2006.11.06 iso �\�[�g������t�ԍ��֕ύX�iA.JURI_SEIRI_NO�͊w�n�p�j
//        query.append(" ORDER BY S.KOMOKU_NO ASC,S.CHOSEIHAN DESC,S.JURI_SEIRI_NO ASC");
//		<!-- UPDATE�@START 2007/07/10 BIS ���� -->  
//		�Â��R�[�h : query.append(" ORDER BY S.KOMOKU_NO ASC,S.CHOSEIHAN DESC,S.UKETUKE_NO ASC");        
        query.append(" ORDER BY S.KOMOKU_NO ASC,S.CHOSEIHAN DESC,S.HYOJIJUN,S.UKETUKE_NO ASC");
//		<!-- UPDATE�@END 2007/07/10 BIS ���� -->        
        // for debug
        if(log.isDebugEnabled()){
            log.debug("query:" + query);
        }

        // �f�[�^���X�g�擾
        List dataList = SelectUtil.select(connection, query.toString());
        return SortUtil.sortByKomokuNo(dataList);
    }
// 2006/06/15 dyh add end

    //2006/06/16 Wang Xiancheng add start
    /**
     * �w�茟�������ɊY������\�����f�[�^���擾����B
     * @param connection
     * @param searchInfo
     * @return
     * @throws DataAccessException
     * @throws NoDataFoundException
     * @throws ApplicationException  �\���ҏ�񂪃Z�b�g����Ă��Ȃ������ꍇ
     */
    public Page searchConfirmInfo(Connection connection,
            ShinseiSearchInfo searchInfo) throws DataAccessException,
            NoDataFoundException, ApplicationException {

        StringBuffer select = new StringBuffer();
        select.append("SELECT ");
        select.append(" A.SYSTEM_NO,");             // �V�X�e����t�ԍ�
        select.append(" A.UKETUKE_NO,");            // �\���ԍ�
        select.append(" A.JIGYO_ID,");              // ����ID
        select.append(" A.NENDO,");                 // �N�x
        select.append(" A.KAISU,");                 // ��
        select.append(" A.JIGYO_NAME,");            // ���Ɩ�
        select.append(" A.SHINSEISHA_ID,");         // �\����ID
        select.append(" A.SAKUSEI_DATE,");          // �\�����쐬��
        select.append(" A.SHONIN_DATE,");           // �����@�֏��F��
        select.append(" A.NAME_KANJI_SEI,");        // �\���Ҏ����i������-���j
        select.append(" A.NAME_KANJI_MEI,");        // �\���Ҏ����i������-���j
        select.append(" A.KENKYU_NO,");             // �\���Ҍ����Ҕԍ�
        select.append(" A.SHOZOKU_CD,");            // �����@�փR�[�h
        select.append(" A.SHOZOKU_NAME,");          // �����@�֖�
        select.append(" A.SHOZOKU_NAME_RYAKU,");    // �����@�֖��i���́j
        select.append(" A.BUKYOKU_NAME,");          // ���ǖ�
        select.append(" A.BUKYOKU_NAME_RYAKU,");    // ���ǖ��i���́j
        select.append(" A.SHOKUSHU_NAME_KANJI,");   // �E��
        select.append(" A.SHOKUSHU_NAME_RYAKU,");   // �E���i���́j
        select.append(" A.KADAI_NAME_KANJI,");      // �����ۑ薼(�a���j
        select.append(" A.JIGYO_KUBUN,");           // ���Ƌ敪
        select.append(" A.KEKKA1_ABC,");            // 1���R������(ABC)
        select.append(" A.KEKKA1_TEN,");            // 1���R������(�_��)
        select.append(" A.KEKKA1_TEN_SORTED,");     // 1���R������(�_����)
        select.append(" A.KEKKA2,");                // 2���R������
        select.append(" A.JOKYO_ID,");              // �\����ID
        select.append(" A.SAISHINSEI_FLG,");        // �Đ\���t���O
        select.append(" A.KEI_NAME_RYAKU,");        // �n���̋敪�i���́j
        select.append(" A.KANTEN_RYAKU,");          // ���E�̊ϓ_�i���́j
        select.append(" A.NENREI,");                // �N��
        select.append(" CHCKLIST.KAKUTEI_DATE,");   // �m���
        // �R�����g�m�F�@�\�ǉ��̂���
        select.append(" A.JURI_BIKO,");             // �󗝔��l
        select.append(" A.JURI_SEIRI_NO,");         // �󗝐����ԍ�
        // �\���󋵈ꗗ�Ŋ�Ղ̊w�U��t���ȍ~�̏ꍇ�͐\�������C���ł��Ȃ��悤�ɂ��邽��
        select.append("CASE WHEN CHCKLIST.SHOZOKU_CD IS NULL THEN 'TRUE' ELSE 'FALSE' END EDITABLE, ");
        select.append(" DECODE");
        select.append(" (");
        select.append("  NVL(A.SUISENSHO_PATH,'null') ");
        select.append("  ,'null','FALSE'");         // ���E���p�X��NULL�̂Ƃ�
        select.append("  ,      'TRUE'");           // ���E���p�X��NULL�ȊO�̂Ƃ�
        select.append(" ) SUISENSHO_FLG, ");        // ���E���o�^�t���O
        select.append(" B.UKETUKEKIKAN_END,");      // �w�U��t�����i�I���j
        //add start ly 2006/07/10
        select.append("B.RYOIKI_KAKUTEIKIKAN_END,");//�̈��\�Ҋm����ؓ�
        select.append(" DECODE");
        select.append(" (");
        select.append("  SIGN( ");
        select.append("       TO_DATE( TO_CHAR(B.RYOIKI_KAKUTEIKIKAN_END,'YYYY/MM/DD'), 'YYYY/MM/DD' ) ");
        select.append("     - TO_DATE( TO_CHAR(SYSDATE           ,'YYYY/MM/DD'), 'YYYY/MM/DD' ) ");
        select.append("      )"); select.append("  ,0 , '0'"); // ���ݎ����Ɠ����ꍇ
        select.append("  ,-1 , '1'");               // ���ݎ����̕�����t�������O
        select.append("  ,1, '0'");                 // ���ݎ����̕�����t��������
        select.append(" ) RYOIKI_KAKUTEIKIKAN_END_FLAG,"); // �̈��\�Ҋm����ؓ�
        //add end ly 2006/07/10
        select.append(" B.HOKAN_DATE,");            // �f�[�^�ۊǓ�
        select.append(" B.YUKO_DATE,");             // �ۊǗL������
        select.append(" DECODE");
        select.append(" (");
        select.append("  SIGN( ");
        select.append("       TO_DATE( TO_CHAR(B.UKETUKEKIKAN_END,'YYYY/MM/DD'), 'YYYY/MM/DD' ) ");
        select.append("     - TO_DATE( TO_CHAR(SYSDATE           ,'YYYY/MM/DD'), 'YYYY/MM/DD' ) ");

        //2006.11.16 iso ���ؓ��������W�I�{�^�����o�Ȃ��o�O���C��
//        select.append("      )"); select.append("  ,0 , 'TRUE'"); // ���ݎ����Ɠ����ꍇ
        select.append("      )"); select.append("  ,0 , 'FALSE'"); // ���ݎ����Ɠ����ꍇ
        
        select.append("  ,-1 , 'TRUE'");            // ���ݎ����̕�����t�������O
        select.append("  ,1, 'FALSE'");             // ���ݎ����̕�����t��������
        select.append(" ) UKETUKE_END_FLAG,");      // �w�U��t�����i�I���j���B�t���O
        select.append(" DECODE" ).append(" (").append("  NVL(A.PDF_PATH,'null') ");
        select.append("  ,'null','FALSE'");         // PDF�̊i�[�p�X��NULL�̂Ƃ�
        select.append("  ,      'TRUE'");           // PDF�̊i�[�p�X��NULL�ȊO�̂Ƃ�
        select.append(" ) PDF_PATH_FLG, ");         // PDF�̊i�[�p�X�t���O
        select.append(" DECODE").append(" (").append("  NVL(C.SYSTEM_NO,'null') ");
        select.append("  ,'null','FALSE'");         // �Y�t�t�@�C����NULL�̂Ƃ�
        select.append("  ,      'TRUE'");           // �Y�t�t�@�C����NULL�ȊO�̂Ƃ�
        select.append(" ) TENPUFILE_FLG, ");        // �Y�t�t�@�C���t���O
        //add start ly 2006/06/21
        select.append(" M.SHOZOKU_HYOJI,");         //�����
        //add end ly 2006/06/21
        select.append("RYOIKI.RYOIKIKEIKAKUSHO_KAKUTEI_FLG ");
        select.append(" FROM");
        select.append(" SHINSEIDATAKANRI").append(dbLink).append(" A"); // �\���f�[�^�Ǘ��e�[�u��

        // ���ǒS���҂̏ꍇ�̏����ǉ�
        select.append(" INNER JOIN JIGYOKANRI").append(dbLink).append(" B");
        select.append(" ON A.JIGYO_ID = B.JIGYO_ID ");
        select.append(" LEFT JOIN TENPUFILEINFO").append(dbLink).append(" C ");
        select.append(" ON A.SYSTEM_NO = C.SYSTEM_NO ");
        select.append(" AND C.TENPU_PATH IS NOT NULL ");

        // ���ǒS���҂̒S�����ǊǗ��e�[�u���ɒl������ꍇ�ɏ�����ǉ�����
        if (userInfo.getBukyokutantoInfo() != null
                && userInfo.getBukyokutantoInfo().getTantoFlg()) {
            select.append(" INNER JOIN TANTOBUKYOKUKANRI T");
            select.append(" ON T.SHOZOKU_CD = A.SHOZOKU_CD");
            select.append(" AND T.BUKYOKU_CD = A.BUKYOKU_CD");
            select.append(" AND T.BUKYOKUTANTO_ID = '");
            select.append(EscapeUtil.toSqlString(userInfo.getBukyokutantoInfo()
                    .getBukyokutantoId()));
            select.append("' ");
        }

        // �\���󋵈ꗗ�Ŋ�Ղ̊w�U��t���ȍ~�̏ꍇ�͐\�������C���ł��Ȃ��悤�ɂ��邽��
        select.append("LEFT JOIN CHCKLISTINFO CHCKLIST ");
        select.append("ON CHCKLIST.JIGYO_ID = A.JIGYO_ID ");
        select.append("AND CHCKLIST.SHOZOKU_CD = A.SHOZOKU_CD ");
        select.append("AND CHCKLIST.JOKYO_ID <> '03' ");

        // �̈�v�揑�m��ς�
        select.append("LEFT JOIN RYOIKIKEIKAKUSHOINFO RYOIKI ");
        select.append("ON RYOIKI.KARIRYOIKI_NO = A.RYOIKI_NO ");
        select.append("AND RYOIKI.DEL_FLG = 0 ");
        select.append("LEFT JOIN MASTER_STATUS M ");
        select.append("ON M.JOKYO_ID = A.JOKYO_ID ");
        select.append("AND M.SAISHINSEI_FLG = A.SAISHINSEI_FLG ");
        select.append("WHERE");
        select.append(" A.JIGYO_ID = B.JIGYO_ID");// ����ID����������

        // ��������������SQL���𐶐�����B
        String query = getQueryString(select.toString(), searchInfo);

        // for debug
        if (log.isDebugEnabled()) {
            log.debug("query:" + query);
        }
        // �y�[�W�擾
        return SelectUtil.selectPageInfo(connection, (SearchInfo) searchInfo,
                query);
    }
    //2006/06/16 Wang Xiancheng add end
    
    // 2006/06/20 liujia add start------------------------------
    /**
     * �����o��\�f�[�^��Ԃ��B
     * 
     * @param connection
     * @param keihiInfo �����o����
     * @return List
     * @throws DataAccessException
     * @throws NoDataFoundException
     */
    public List selectKenkyuKeihiList(Connection connection,
            KenkyuSoshikiKenkyushaInfo keihiInfo)
            throws DataAccessException, NoDataFoundException {

        List dataList = null;

        // ��������:�\����ID(JOKYO_ID),�폜�t���O(DEL_FLG)
        StringBuffer query = new StringBuffer();
        query.append("SELECT ");
        query.append("A.KOMOKU_NO, ");// �������ڔԍ�
        query.append("SUM(A.KEIHI1) AS KEIHI1, ");// 1�N�ڌ����o��
        query.append("SUM(A.KEIHI2) AS KEIHI2, ");// 2�N�ڌ����o��
        query.append("SUM(A.KEIHI3) AS KEIHI3, ");// 3�N�ڌ����o��
        query.append("SUM(A.KEIHI4) AS KEIHI4, ");// 4�N�ڌ����o��
        query.append("SUM(A.KEIHI5) AS KEIHI5, ");// 5�N�ڌ����o��
        query.append("SUM(A.KEIHI6) AS KEIHI6, ");// 6�N�ڌ����o��
        query.append("SUM(A.KEIHI_TOTAL) AS KEIHI_TOTAL, ");// ���v-�����o��
        query.append(" B.KENKYU_SYOKEI_1,");      // �����o��i1�N��)-���v
        query.append(" B.KENKYU_UTIWAKE_1,");     // �����o��i1�N��)-����
        query.append(" B.KENKYU_SYOKEI_2,");      // �����o��i2�N��)-���v
        query.append(" B.KENKYU_UTIWAKE_2,");     // �����o��i2�N��)-����
        query.append(" B.KENKYU_SYOKEI_3,");      // �����o��i3�N��)-���v
        query.append(" B.KENKYU_UTIWAKE_3,");     // �����o��i3�N��)-����
        query.append(" B.KENKYU_SYOKEI_4,");      // �����o��i4�N��)-���v
        query.append(" B.KENKYU_UTIWAKE_4,");     // �����o��i4�N��)-����
        query.append(" B.KENKYU_SYOKEI_5,");      // �����o��i5�N��)-���v
        query.append(" B.KENKYU_UTIWAKE_5,");     // �����o��i5�N��)-����
        query.append(" B.KENKYU_SYOKEI_6,");      // �����o��i6�N��)-���v
        query.append(" B.KENKYU_UTIWAKE_6 ");     // �����o��i6�N��)-����
        query.append("FROM SHINSEIDATAKANRI A ");
        query.append("INNER JOIN RYOIKIKEIKAKUSHOINFO B ");// �̈�v�揑�i�T�v�j���Ǘ��e�[�u��
        query.append("ON A.RYOIKI_NO =�@B.KARIRYOIKI_NO ");
        query.append("WHERE A.JOKYO_ID IN(");
        query.append(StringUtil.changeArray2CSV(keihiInfo.getJokyoId(), true));
        query.append(")");
        query.append(" AND A.DEL_FLG = 0");
        query.append(" AND B.DEL_FLG = 0");//2006/08/01 add liuJia
        if (!StringUtil.isBlank(keihiInfo.getKariryoikiNo())) {
            query.append(" AND B.KARIRYOIKI_NO = '");
            query.append(EscapeUtil.toSqlString(keihiInfo.getKariryoikiNo()));
            query.append("' ");
        }
        query.append(" GROUP BY A.KOMOKU_NO,");
        query.append(" B.KENKYU_SYOKEI_1,");
        query.append(" B.KENKYU_UTIWAKE_1,");
        query.append(" B.KENKYU_SYOKEI_2,");
        query.append(" B.KENKYU_UTIWAKE_2,");
        query.append(" B.KENKYU_SYOKEI_3,");
        query.append(" B.KENKYU_UTIWAKE_3,");
        query.append(" B.KENKYU_SYOKEI_4,");
        query.append(" B.KENKYU_UTIWAKE_4,");
        query.append(" B.KENKYU_SYOKEI_5,");
        query.append(" B.KENKYU_UTIWAKE_5,");
        query.append(" B.KENKYU_SYOKEI_6,");
        query.append(" B.KENKYU_UTIWAKE_6 ");
        query.append("ORDER BY A.KOMOKU_NO ASC ");

        // �f�[�^���X�g�擾
        dataList = SelectUtil.select(connection, query.toString());
        return SortUtil.sortByKomokuNo(dataList);
    }
    // 2006/06/20 liujia add end------------------------------------------

    // 2006/06/16 �ǉ� ���`�� ��������
    /**
     * �����g�D�\�f�[�^��Ԃ��B
     * 
     * @param connection
     * @param kenkyuSoshikiKenkyushaInfo
     * @return List
     * @throws DataAccessException
     * @throws NoDataFoundException
     */
    /**
    public List selectKenkyuSosiki(Connection connection,
            KenkyuSoshikiKenkyushaInfo kenkyuSoshikiKenkyushaInfo)
            throws DataAccessException, NoDataFoundException {

        // ��������:�\����ID(JOKYO_ID),�폜�t���O(DEL_FLG)
        StringBuffer query = new StringBuffer();
        query.append("SELECT");
        query.append(" A.KOMOKU_NO,");          // �������ڔԍ�
        query.append(" A.KENKYU_KUBUN,");       // �����敪
        query.append(" A.CHOSEIHAN,");          // ������
        query.append(" A.KADAI_NAME_KANJI,");   // �����ۑ薼(�a���j
        query.append(" A.NENDO,");              // ���ƔN�x
        query.append(" A.KEIHI1,");             // 1�N�ڌ����o��
        query.append(" A.KEIHI2,");             // 2�N�ڌ����o��
        query.append(" A.KEIHI3,");             // 3�N�ڌ����o��
        query.append(" A.KEIHI4,");             // 4�N�ڌ����o��
        query.append(" A.KEIHI5,");             // 5�N�ڌ����o��
        query.append(" A.KEIHI6,");             // 6�N�ڌ����o��
        query.append(" A.KENKYU_NO,");          // �\���Ҍ����Ҕԍ�
        query.append(" A.NAME_KANJI_SEI,");     // �\���Ҏ����i������-���j
        query.append(" A.NAME_KANJI_MEI,");     // �\���Ҏ����i������-���j
        query.append(" A.SHOZOKU_CD,");         // ���������@�֔ԍ�
        query.append(" A.SHOZOKU_NAME_RYAKU,"); // �����@�֖��i���́j
        query.append(" A.BUKYOKU_CD,");         // ���ǃR�[�h
        
        //2006.10.30 iso �g�D�\�ɂ͐������̂��o���悤�C��
        query.append(" A.BUKYOKU_NAME,"); // ���ǖ�
        
        query.append(" A.BUKYOKU_NAME_RYAKU,"); // ���ǖ��i���́j
        query.append(" A.SHOKUSHU_CD,");        // �E���R�[�h
        query.append(" A.SHOKUSHU_NAME_RYAKU,");// �E��

        //2006.11.06 iso �\�[�g������t�ԍ��֕ύX�iA.JURI_SEIRI_NO�͊w�n�p�j
//        query.append(" A.JURI_SEIRI_NO,");      // �����ԍ�
        
        query.append(" COUNT(*) COUNT ");       // �\���l��
        query.append(" FROM SHINSEIDATAKANRI A ");// �\���f�[�^�Ǘ��e�[�u��
        query.append(" INNER JOIN RYOIKIKEIKAKUSHOINFO B ");// �̈�v�揑�i�T�v�j���Ǘ��e�[�u��
        query.append(" ON A.RYOIKI_NO =�@B.KARIRYOIKI_NO AND B.DEL_FLG = 0"); // �̈�ԍ�
        query.append(" INNER JOIN KENKYUSOSHIKIKANRI C ");// �����g�D�\�Ǘ��e�[�u��
        query.append(" ON A.SYSTEM_NO =�@C.SYSTEM_NO ");
        query.append("WHERE");
        query.append(" A.RYOIKI_NO = '");
        query.append(EscapeUtil.toSqlString(kenkyuSoshikiKenkyushaInfo.getKariryoikiNo())); // ���̈�ԍ�
        query.append("' ");
        query.append(" AND JOKYO_ID IN(");
        query.append(StringUtil.changeArray2CSV(kenkyuSoshikiKenkyushaInfo.getJokyoId(), true));
        query.append(") ");
        query.append(" AND A.DEL_FLG = 0");
        query.append("GROUP BY");
        query.append(" A.KOMOKU_NO,A.KENKYU_KUBUN,A.CHOSEIHAN,A.KADAI_NAME_KANJI,A.NENDO,");
        query.append(" A.KEIHI1,A.KEIHI2, A.KEIHI3, A.KEIHI4, A.KEIHI5, A.KEIHI6,A.KENKYU_NO,");
        query.append(" A.NAME_KANJI_SEI, A.NAME_KANJI_MEI, A.SHOZOKU_CD, A.SHOZOKU_NAME_RYAKU, A.BUKYOKU_CD,");

        //2006.11.06 iso �\�[�g������t�ԍ��֕ύX�iA.JURI_SEIRI_NO�͊w�n�p�j
//        //2006.10.30 iso �g�D�\�ɂ͐������̂��o���悤�C��
////        query.append(" A.BUKYOKU_NAME_RYAKU, A.SHOKUSHU_CD, A.SHOKUSHU_NAME_RYAKU,A.JURI_SEIRI_NO ");
//        query.append(" A.BUKYOKU_NAME, A.BUKYOKU_NAME_RYAKU, A.SHOKUSHU_CD, A.SHOKUSHU_NAME_RYAKU,A.JURI_SEIRI_NO ");
        query.append(" A.BUKYOKU_NAME, A.BUKYOKU_NAME_RYAKU, A.SHOKUSHU_CD, A.SHOKUSHU_NAME_RYAKU, A.UKETUKE_NO ");
        
        query.append("ORDER BY");
        
        //2006.11.06 iso �\�[�g������t�ԍ��֕ύX�iA.JURI_SEIRI_NO�͊w�n�p�j
//        query.append(" A.KOMOKU_NO ASC,A.CHOSEIHAN DESC,A.JURI_SEIRI_NO ASC");
        query.append(" A.KOMOKU_NO ASC,A.CHOSEIHAN DESC,A.UKETUKE_NO ASC");

        // �f�[�^���X�g�擾
        List dataList = SelectUtil.select(connection, query.toString());
        return SortUtil.sortByKomokuNo(dataList);
    }
    // 2006/06/16 �ǉ� ���`�� �����܂�
    */
//	<!-- UPDATE�@START�@ 2007/07/16 BIS ���� -->  
    public List selectKenkyuSosiki(Connection connection,
            KenkyuSoshikiKenkyushaInfo kenkyuSoshikiKenkyushaInfo)
            throws DataAccessException, NoDataFoundException {
        // ��������:�\����ID(JOKYO_ID),�폜�t���O(DEL_FLG)
        StringBuffer query = new StringBuffer();
        query.append("SELECT");
        query.append(" A.SYSTEM_NO,");          // �V�X�e����t�ԍ�
        query.append(" A.RYOIKI_NO,");          // ���̈�ԍ�
        query.append(" A.HYOJIJUN,");           // �\����
        query.append(" A.KOMOKU_NO,");          // �������ڔԍ�
        query.append(" A.KENKYU_KUBUN,");       // �����敪
        query.append(" A.CHOSEIHAN,");          // ������
        query.append(" A.KADAI_NAME_KANJI,");   // �����ۑ薼(�a���j
        query.append(" A.NENDO,");              // ���ƔN�x
        query.append(" A.KEIHI1,");             // 1�N�ڌ����o��
        query.append(" A.KEIHI2,");             // 2�N�ڌ����o��
        query.append(" A.KEIHI3,");             // 3�N�ڌ����o��
        query.append(" A.KEIHI4,");             // 4�N�ڌ����o��
        query.append(" A.KEIHI5,");             // 5�N�ڌ����o��
        query.append(" A.KEIHI6,");             // 6�N�ڌ����o��
        query.append(" A.KENKYU_NO,");          // �\���Ҍ����Ҕԍ�
        query.append(" A.NAME_KANJI_SEI,");     // �\���Ҏ����i������-���j
        query.append(" A.NAME_KANJI_MEI,");     // �\���Ҏ����i������-���j
        query.append(" A.SHOZOKU_CD,");         // ���������@�֔ԍ�
        query.append(" A.SHOZOKU_NAME,"); 		// �����@�֖�
        query.append(" A.BUKYOKU_CD,");         // ���ǃR�[�h
        query.append(" A.BUKYOKU_NAME,"); // ���ǖ�
        query.append(" A.BUKYOKU_NAME_RYAKU,"); // ���ǖ��i���́j
        query.append(" A.SHOKUSHU_CD,");        // �E���R�[�h
        query.append(" A.SHOKUSHU_NAME_KANJI,");// �E��
        query.append(" COUNT(*) COUNT ");       // �\���l��
        query.append(" FROM SHINSEIDATAKANRI A ");// �\���f�[�^�Ǘ��e�[�u��
        query.append(" INNER JOIN RYOIKIKEIKAKUSHOINFO B ");// �̈�v�揑�i�T�v�j���Ǘ��e�[�u��
        query.append(" ON A.RYOIKI_NO =�@B.KARIRYOIKI_NO AND B.DEL_FLG = 0"); // �̈�ԍ�
        query.append(" INNER JOIN KENKYUSOSHIKIKANRI C ");// �����g�D�\�Ǘ��e�[�u��
        query.append(" ON A.SYSTEM_NO =�@C.SYSTEM_NO ");
        query.append("WHERE");
        query.append(" A.RYOIKI_NO = '");
        query.append(EscapeUtil.toSqlString(kenkyuSoshikiKenkyushaInfo.getKariryoikiNo())); // ���̈�ԍ�
        query.append("' AND");
        query.append(" JOKYO_ID IN(");
        query.append(StringUtil.changeArray2CSV(kenkyuSoshikiKenkyushaInfo.getJokyoId(), true));
        query.append(") ");
        query.append(" AND A.DEL_FLG = 0 ");
        query.append(" GROUP BY");
        query.append(" A.RYOIKI_NO,A.HYOJIJUN,A.KOMOKU_NO,A.KENKYU_KUBUN,A.CHOSEIHAN,A.KADAI_NAME_KANJI,A.NENDO,");
        query.append(" A.KEIHI1,A.KEIHI2, A.KEIHI3, A.KEIHI4, A.KEIHI5, A.KEIHI6,A.KENKYU_NO,");
        query.append(" A.NAME_KANJI_SEI, A.NAME_KANJI_MEI, A.SHOZOKU_CD, A.SHOZOKU_NAME, A.BUKYOKU_CD,");
        query.append(" A.BUKYOKU_NAME, A.BUKYOKU_NAME_RYAKU, A.SHOKUSHU_CD, A.SHOKUSHU_NAME_KANJI, A.UKETUKE_NO, A.SYSTEM_NO ");
        query.append("ORDER BY");
        query.append(" A.KOMOKU_NO ASC,A.CHOSEIHAN DESC,A.HYOJIJUN ASC,A.UKETUKE_NO ASC");
        
        List dataList = SelectUtil.select(connection, query.toString());
		return SortUtil.sortByKomokuNo(dataList);
    }
//  <!-- UPDATE�@END�@ 2007/07/16 BIS ���� --> 
    
    
    // 2006/06/16 �ǉ� mcj ��������
    /**
     * �\����ID�݂̂��w�肳�ꂽ�X�e�[�^�X�֍X�V����B
     * 
     * @param connection
     * @param pkInfo
     * @param status
     * @throws DataAccessException
     * @throws NoDataFoundException
     */
    public void updateSyoninCancel(
            Connection connection,
            ShinseiDataPk pkInfo,
            String status)
            throws DataAccessException, NoDataFoundException {

        //�Q�Ɖ\�\���f�[�^���`�F�b�N
        checkOwnShinseiData(connection, pkInfo);
        
        String query =
            "UPDATE SHINSEIDATAKANRI" + dbLink
                + " SET"
                + " JOKYO_ID = ?,"           // �\����ID       
                + " SHONIN_DATE = null"        // �����@�֏��F��              
                + " WHERE"
                + " SYSTEM_NO = ?";

        PreparedStatement preparedStatement = null;
        try {
            //�o�^
            preparedStatement = connection.prepareStatement(query);
            int index = 1;
            DatabaseUtil.setParameter(preparedStatement,index++,status);
            DatabaseUtil.setParameter(preparedStatement,index++,pkInfo.getSystemNo());  //�V�X�e����t�ԍ�
            DatabaseUtil.executeUpdate(preparedStatement);
        } catch (SQLException ex) {
            throw new DataAccessException("�\�����X�e�[�^�X�X�V���ɗ�O���������܂����B ", ex);
        } finally {
            DatabaseUtil.closeResource(null, preparedStatement);
        }
    }
    
    /**
     * �\����ID�݂̂��w�肳�ꂽ�X�e�[�^�X�֍X�V����B
     * @param connection
     * @param pkInfo �L�[���
     * @param status ��
     * @param jyuriDate �󗝓��t
     * @throws DataAccessException
     * @throws NoDataFoundException
     */
    public void updateIkkastu(
            Connection connection,
            ShinseiDataPk pkInfo,
            String status,
            Date jyuriDate)
            throws DataAccessException, NoDataFoundException {

        //�Q�Ɖ\�\���f�[�^���`�F�b�N
        checkOwnShinseiData(connection, pkInfo);
        
        String query =
            "UPDATE SHINSEIDATAKANRI" + dbLink
                + " SET"
                + " JOKYO_ID = ?,"           // �\����ID       
                + " JYURI_DATE = ?"        // �����@�֏��F��              
                + " WHERE"
                + " SYSTEM_NO = ?";

        PreparedStatement preparedStatement = null;
        try {
            //�o�^
            preparedStatement = connection.prepareStatement(query);
            int index = 1;
            DatabaseUtil.setParameter(preparedStatement,index++,status);
            DatabaseUtil.setParameter(preparedStatement,index++,jyuriDate);
            DatabaseUtil.setParameter(preparedStatement,index++,pkInfo.getSystemNo());  //�V�X�e����t�ԍ�
            DatabaseUtil.executeUpdate(preparedStatement);
        } catch (SQLException ex) {
            throw new DataAccessException("�\�����X�e�[�^�X�X�V���ɗ�O���������܂����B ", ex);
        } finally {
            DatabaseUtil.closeResource(null, preparedStatement);
        }
    }
    // 2006/06/16 �ǉ� mcj �����܂�

//  �{�@2006/06/21�@��������
    /**
     * �\����ID�݂̂��w�肳�ꂽ�X�e�[�^�X�֍X�V����B
     * @param connection
     * @param dataInfo �\���f�[�^���
     * @throws DataAccessException
     * @throws NoDataFoundException
     */
//2007/02/05 �c�@�C����������@�p�����[�^�������̂�
//    public void updateShinseiInfo(
//            Connection connection,
//            ShinseiDataPk pkInfo,
//            String status,
//            int edintion,
//            Date sakuseiDate,
//            File iodFile,
//            File xmlFile)
    public void updateShinseiInfo(Connection connection,
                                  ShinseiDataInfo dataInfo)
//2007/02/05�@�c�@�C�������܂�    
            throws DataAccessException, NoDataFoundException {

        //�Q�Ɖ\�\���f�[�^���`�F�b�N
        checkOwnShinseiData(connection, dataInfo);

//2006/07/21 �c�@�C����������
//        String query =
//                "UPDATE SHINSEIDATAKANRI"+dbLink
//                    + " SET"
//                    + " JOKYO_ID = ?,"          //�\����ID
//                    + " EDITION = ?,"
//                    + " SAKUSEI_DATE = SYSDATE"
//                    + " WHERE"
//                    + " SYSTEM_NO = ?";
        
        StringBuffer query = new StringBuffer();
          
        query.append("UPDATE SHINSEIDATAKANRI");
        query.append(dbLink);
        query.append(" SET");
        query.append(" JOKYO_ID = ?,");
        query.append(" EDITION = ?,");
        query.append(" SAKUSEI_DATE = ?,");
        query.append(" PDF_PATH = ?,");
        query.append(" XML_PATH = ?,");
//2007/02/05 �c�@�ǉ���������
        query.append(" UKETUKE_NO = ?");
//2007/02/25�@�c�@�ǉ������܂�        
        query.append(" WHERE SYSTEM_NO = ?");

        PreparedStatement preparedStatement = null;
        try {
            //�o�^
            preparedStatement = connection.prepareStatement(query.toString());
            int index = 1;
//2007/02/05 �c�@�C����������            
//            DatabaseUtil.setParameter(preparedStatement,index++,status);
//            DatabaseUtil.setParameter(preparedStatement,index++,edintion);
//            //2006/07/24 add start
//            DatabaseUtil.setParameter(preparedStatement,index++,sakuseiDate);
//            //2006/07/24 add end
//            //PDF�t�@�C���p�X�B
//            DatabaseUtil.setParameter(preparedStatement,index++,iodFile.getAbsolutePath());
//            //XML�t�@�C���p�X�B
//            DatabaseUtil.setParameter(preparedStatement,index++,xmlFile.getAbsolutePath());
//            DatabaseUtil.setParameter(preparedStatement,index++,pkInfo.getSystemNo());  //�V�X�e����t�ԍ�
            DatabaseUtil.setParameter(preparedStatement,index++,dataInfo.getJokyoId());
            DatabaseUtil.setParameter(preparedStatement,index++,dataInfo.getKadaiInfo().getEdition());
            //2006/07/24 add start
            DatabaseUtil.setParameter(preparedStatement,index++,dataInfo.getSakuseiDate());
            //2006/07/24 add end
            //PDF�t�@�C���p�X�B
            DatabaseUtil.setParameter(preparedStatement,index++,dataInfo.getPdfPath());
            //XML�t�@�C���p�X�B
            DatabaseUtil.setParameter(preparedStatement,index++,dataInfo.getXmlPath());
            DatabaseUtil.setParameter(preparedStatement,index++,dataInfo.getUketukeNo());
            DatabaseUtil.setParameter(preparedStatement,index++,dataInfo.getSystemNo());  //�V�X�e����t�ԍ�
//2007/02/05�@�c�@�C�������܂�            
//206/07/21 �c�@�C�������܂�
            DatabaseUtil.executeUpdate(preparedStatement);
        } catch (SQLException ex) {
            throw new DataAccessException("�\�����X�e�[�^�X�X�V���ɗ�O���������܂����B ", ex);
        } finally {
            DatabaseUtil.closeResource(null, preparedStatement);
        }
    }
//�{�@�����܂�

    //2006/06/23 by jzx add start
    /**
     * �̈�v�揑�m���ʌ`���`�F�b�N���X�V����B
     * @param  connection               �R�l�N�V����
     * @param  shinseiDataInfo          �`�F�b�N���X�g�������
     * @return int
     * @throws DataAccessException      �X�V���ɗ�O�����������ꍇ
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public void selectCheckKakuteiSaveInfo(Connection connection, ShinseiDataInfo shinseiDataInfo)
            throws DataAccessException, NoDataFoundException, ApplicationException {

        if(shinseiDataInfo == null){
            throw new NoDataFoundException("��O���������Z�b�g����Ă��܂���B");
        }
        String ryoikiNo = shinseiDataInfo.getRyouikiNo();
        if(ryoikiNo == null || ryoikiNo.equals("")){
            throw new NoDataFoundException("���̈�ԍ����Z�b�g����Ă��܂���B");
        }
        //userInfo���擾�Ȍ������Ҕԍ�
        String kenkyuNo = userInfo.getShinseishaInfo().getKenkyuNo();
        if(kenkyuNo == null || kenkyuNo.equals("")){
            throw new NoDataFoundException("�\���Ҍ����Ҕԍ����Z�b�g����Ă��܂���B");
        }
        StringBuffer query = new StringBuffer();
        query.append("SELECT ");
        query.append(" COUNT(*)");
        query.append(" FROM");
        query.append(" SHINSEIDATAKANRI A");
        query.append(" WHERE");
        query.append(" A.RYOIKI_NO = ?");
        query.append(" AND A.KENKYU_NO = ?");
        query.append(" AND A.KOMOKU_NO = ?");
        query.append(" AND A.JOKYO_ID = ?");
        query.append(" AND A.DEL_FLG = ?");
        
        //printf Debug:
        if (log.isDebugEnabled()) {
            log.debug("query:" + query.toString());
        }
        //�ύX�O�̏�ID���擾
        PreparedStatement preparedStatement = null;
        ResultSet recordSet = null;
        int count = 0;
        try{
            preparedStatement = connection.prepareStatement(query.toString());
            int i = 1;
            DatabaseUtil.setParameter(preparedStatement, i++, ryoikiNo);
            DatabaseUtil.setParameter(preparedStatement, i++, kenkyuNo);
            DatabaseUtil.setParameter(preparedStatement, i++, shinseiDataInfo.getRyouikiKoumokuNo());
            DatabaseUtil.setParameter(preparedStatement, i++, shinseiDataInfo.getJokyoId());
            DatabaseUtil.setParameter(preparedStatement, i++, shinseiDataInfo.getDelFlg());
            recordSet = preparedStatement.executeQuery();
            if (recordSet.next()) {
                count = recordSet.getInt(1);
            }
        }catch (SQLException ex) {
            throw new DataAccessException(ex);
        }
        finally {
            if(count != 1 ){
                throw new ApplicationException(
                        "�̈�������v�撲���ɁA�����ǁuX00�v�̌����v�撲��������܂���B",
                        new ErrorInfo("errors.9026"));
            }
            DatabaseUtil.closeResource(recordSet, preparedStatement);
        }
    }

    /**
     * �̈�v�揑�m���ʌ`���`�F�b�N���X�V����B
     * @param  connection               �R�l�N�V����
     * @param  shinseiDataInfo          �`�F�b�N���X�g�������
     * @return List
     * @throws DataAccessException      �X�V���ɗ�O�����������ꍇ
     * @throws NoDataFoundException
     */
    public List selectShinseiDataPkInfo(
            Connection connection,
            ShinseiDataInfo shinseiDataInfo)
            throws DataAccessException, NoDataFoundException{

        StringBuffer select = new StringBuffer();
        select.append("SELECT ");
        select.append(" A.SYSTEM_NO " );
        select.append("FROM ");
        select.append(" SHINSEIDATAKANRI").append(dbLink).append(" A ");
        select.append("WHERE A.DEL_FLG = " );
        select.append(EscapeUtil.toSqlString(shinseiDataInfo.getDelFlg()));
        select.append(" AND A.JOKYO_ID = '");
        select.append(EscapeUtil.toSqlString(shinseiDataInfo.getJokyoId()));
        select.append("' ");
        select.append(" AND A.RYOIKI_NO = '");
        select.append(EscapeUtil.toSqlString(shinseiDataInfo.getRyouikiNo()));
        select.append("' ");
        select.append(" GROUP BY A.SYSTEM_NO ");

        // for debug
        if (log.isDebugEnabled()){
              log.debug("query:" + select.toString());
        }
        return SelectUtil.select(connection, select.toString());
    }
    //2006/06/23 by jzx add end

    //ADD START LY 2006/06/27
    /**
     * ���Y�\�������Q�Ƃ��邱�Ƃ��ł��邩�`�F�b�N����B
     * �Q�Ɖ\�ɊY�����Ȃ��ꍇ�� NoDataAccessExceptioin �𔭐�������B
     * �w�萔�ƊY���f�[�^������v���Ȃ��ꍇ�� NoDataAccessExceptioin �𔭐�������B
     * <p>
     * ���f��͈ȉ��̒ʂ�B
     * <li>SHINSEIDATAKANRI�e�[�u����RYOII_NO�i�̈�ԍ��j���ARYOIKIKEIKAUSHOINFO��KARIRYOIKI_NO�Ɠ������f�[�^
     * <li>SHINSEIDAKATANRI�e�[�u����DEL_FLG�i�폜�t���O�j���u�O�v
     * <li>SHINSEIDATAKANRI�e�[�u����JOKYO_ID�i��ID�j��shinseiInfo.getJokyoIds()
     * </p>
     * @param connection
     * @param shinseiInfo
     * @throws DataAccessException
     * @throws NoDataFoundException
     */
    public void checkSystemNo(Connection connection, ShinseiDataInfo shinseiInfo)
            throws DataAccessException, NoDataFoundException {

        // SQL�̍쐬
        StringBuffer select = new StringBuffer();
        select.append("SELECT ");
        select.append(" SYSTEM_NO ");
        select.append("FROM SHINSEIDATAKANRI");
        select.append(" WHERE ");
        select.append(" RYOIKI_NO = '");
        select.append(EscapeUtil.toSqlString(shinseiInfo.getRyouikiNo()));
        select.append("' ");
        select.append(" AND JOKYO_ID IN (");
        select.append(StringUtil.changeArray2CSV(shinseiInfo.getJokyoIds(), true));
        select.append(")");
        select.append(" AND DEL_FLG = 0");
        
        // for debug
        if (log.isDebugEnabled()){
              log.debug("query:" + select.toString());
        }

        SelectUtil.select(connection, select.toString());
    }

    /**
     * �\����ID,�̈�ԍ��݂̂��w�肳�ꂽ�X�e�[�^�X�֍X�V����B
     * 
     * @param connection
     * @param shinseiInfo ShinseiDataInfo
     * @param status
     * @throws DataAccessException
     * @throws NoDataFoundException
     */
    public void updateShinseis(Connection connection,
            ShinseiDataInfo shinseiInfo, String status)
            throws DataAccessException, NoDataFoundException {
        
        checkSystemNo( connection,  shinseiInfo);
        
        String query = "UPDATE SHINSEIDATAKANRI" + dbLink + " SET"
                + " JOKYO_ID = ? ";// �\����ID
        // 2006/06/28 by jzx add start
        if (shinseiInfo.getSaishinseiFlg() != null) {
            query = query + " ,SAISHINSEI_FLG = ?";
        }
        if (shinseiInfo.getRyoikiKakuteiDate() != null
                ||(shinseiInfo.getRyoikiKakuteiDateFlg() != null
                && shinseiInfo.getRyoikiKakuteiDateFlg().equals("1"))) {
            query = query + " ,RYOIKI_KAKUTEI_DATE = ?";
        }
        if (shinseiInfo.getJyuriDate() != null
                ||(shinseiInfo.getJyuriDateFlg() != null 
                && shinseiInfo.getJyuriDateFlg().equals("1"))) {
            query = query + " ,JYURI_DATE = ?";
        }
        // 2006/06/28 by jzx add end
        if (shinseiInfo.getShoninDate() != null) {
            query = query + " ,SHONIN_DATE = ?";
        }
        query = query + " WHERE" + "  RYOIKI_NO = ?";
        if (shinseiInfo.getJokyoIds() != null
                && shinseiInfo.getJokyoIds().length != 0) {
            query = query
                    + " AND JOKYO_ID IN ("
                    + StringUtil.changeArray2CSV(shinseiInfo.getJokyoIds(),
                            true) + ") ";
        }
        query = query + " AND DEL_FLG = 0";

        // for debug
        if (log.isDebugEnabled()){
              log.debug("query:" + query);
        }

        PreparedStatement preparedStatement = null;
        try {
            // �o�^
            preparedStatement = connection.prepareStatement(query);
            int index = 1;
            DatabaseUtil.setParameter(preparedStatement, index++, status);
            // 2006/06/28 by jzx add start
            if (shinseiInfo.getSaishinseiFlg() != null) {
                DatabaseUtil.setParameter(preparedStatement, index++,
                        shinseiInfo.getSaishinseiFlg());
            }
            if (shinseiInfo.getRyoikiKakuteiDate() != null) {
                DatabaseUtil.setParameter(preparedStatement, index++,
                        shinseiInfo.getRyoikiKakuteiDate());
            }else if (shinseiInfo.getRyoikiKakuteiDateFlg() != null
                    && shinseiInfo.getRyoikiKakuteiDateFlg().equals("1")) {
                shinseiInfo.setRyoikiKakuteiDateFlg(null);
                DatabaseUtil.setParameter(preparedStatement, index++,
                        shinseiInfo.getRyoikiKakuteiDate());
            }
            if (shinseiInfo.getJyuriDate() != null) {
                DatabaseUtil.setParameter(preparedStatement, index++,
                        shinseiInfo.getJyuriDate()); 
            }else if(shinseiInfo.getJyuriDateFlg() != null 
                && shinseiInfo.getJyuriDateFlg().equals("1")){
                shinseiInfo.setJyuriDateFlg(null);
                DatabaseUtil.setParameter(preparedStatement, index++,
                        shinseiInfo.getJyuriDate());
            }
            // 2006/06/28 by jzx add end
            if (shinseiInfo.getShoninDate() != null
                    && shinseiInfo.getShoninDateMark().equals("1")) {
                shinseiInfo.setShoninDate(null);
                DatabaseUtil.setParameter(preparedStatement, index++,
                        shinseiInfo.getShoninDate()); //
            }
            else if (shinseiInfo.getShoninDate() != null
                    && StringUtil.isBlank(shinseiInfo.getShoninDateMark())) {
                DatabaseUtil.setParameter(preparedStatement, index++,
                        shinseiInfo.getShoninDate()); //   
            }
            DatabaseUtil.setParameter(preparedStatement, index++, shinseiInfo
                    .getRyouikiNo()); // �V�X�e����t�ԍ�

            preparedStatement.executeUpdate();
        }
        catch (SQLException ex) {
            throw new DataAccessException("�\�����X�e�[�^�X�X�V���ɗ�O���������܂����B ", ex);
        }
        finally {
            DatabaseUtil.closeResource(null, preparedStatement);
        }
    }
    //add end ly 2006/06/27  
    
    // 2006/07/25 zjp �ǉ���������
    /**
     * �����\���̏��F�����[�����M�B
     * @param  connection               �R�l�N�V����
     * @param  shinseiDataPks           
     * @return List
     * @throws DataAccessException      �X�V���ɗ�O�����������ꍇ
     * @throws NoDataFoundException
     */
    public List selectShinseiDataListForMail(
            Connection connection,
            ShinseiDataPk[] shinseiDataPks)
            throws DataAccessException, NoDataFoundException{

        String[] string  = new String[shinseiDataPks.length];
        
        for (int i = 0; i < string.length; i++) {
            string[i] = shinseiDataPks[i].getSystemNo();
        }
        
        StringBuffer select = new StringBuffer();
        select.append("SELECT ");
        select.append(" NENDO," );
        select.append(" KAISU," );
        select.append(" JIGYO_NAME," );
        select.append(" JIGYO_ID," );
        select.append(" SHONIN_DATE," );
        select.append(" COUNT(*) COUNT " );
        select.append(" FROM ");
        select.append(" SHINSEIDATAKANRI");
        select.append(" WHERE SYSTEM_NO IN (" );
        select.append(StringUtil.changeArray2CSV(string, true));
        select.append(") GROUP BY ");
        select.append(" NENDO,KAISU,JIGYO_NAME,JIGYO_ID,SHONIN_DATE ");

        // for debug
        if (log.isDebugEnabled()){
              log.debug("query:" + select.toString());
        }
        return SelectUtil.select(connection, select.toString());
    }
    // 2006/07/25 zjp �ǉ������܂�
    
//2006/07/26 �c�@�ǉ��������� 
    
    /**
     * ���łɓ���ۑ�ԍ�����������
     * 
     * @param connection
     * @param shinseiDataInfo
     * @return List
     * @throws DataAccessException
     * @throws NoDataFoundException
     */
    public List selectKadaiNoSaisyu(
            Connection connection,
            ShinseiDataInfo shinseiDataInfo)
            throws DataAccessException, NoDataFoundException {
        
        String[] jokyoIds = new String[]{StatusCode.STATUS_SAKUSEITHU,
                                         StatusCode.STATUS_SHOZOKUKIKAN_KYAKKA,
                                         StatusCode.STATUS_GAKUSIN_FUJYURI};
        StringBuffer query = new StringBuffer();
        
        query.append(" SELECT S.KADAI_NO_SAISYU FROM");
        query.append(" SHINSEIDATAKANRI S WHERE");
        
        //2006.08.31 iso �`�F�b�N�͎���ID�����Ȃ��悤�ύX
//        query.append(" S.JIGYO_ID = ");
//        query.append(EscapeUtil.toSqlString(shinseiDataInfo.getJigyoId()));
//        query.append(" AND S.KADAI_NO_SAISYU = ");
        query.append(" S.KADAI_NO_SAISYU = ");
        
        query.append(EscapeUtil.toSqlString(shinseiDataInfo.getKadaiNoSaisyu()));

        //2006.08.31 �قȂ�N�x�ł̓`�F�b�N�������̂ŁA�N�x�������ɉ�����B
        query.append(" AND S.NENDO = ");
        query.append(EscapeUtil.toSqlString(shinseiDataInfo.getNendo()));
        
        query.append(" AND S.JOKYO_ID NOT IN (");
        query.append(changeArray2CSV(jokyoIds));
        query.append(")");
        query.append(" AND S.DEL_FLG = 0");
//2006/08/21 �c�@�ǉ���������
        if(!StringUtil.isBlank(shinseiDataInfo.getSystemNo())){
            query.append(" AND S.SYSTEM_NO <> ");
            query.append(EscapeUtil.toSqlString(shinseiDataInfo.getSystemNo()));
        }
//2006/08/21�@�c�@�ǉ������܂�        
        
        return SelectUtil.select(connection, query.toString());
   }    
//2006/07/26�@�c�@�ǉ������܂�    
    
//2007/02/05 �c�@�ǉ���������
    /**
     * �����ԍ��̃J�E���g��Ԃ��B
     * @param connection
     * @param dataInfo
     * @return
     * @throws DataAccessException
     */
    public int countSeiriNumber(Connection connection, ShinseiDataInfo dataInfo)
        throws DataAccessException {

        String jigyoKubun = dataInfo.getKadaiInfo().getJigyoKubun();
        //�w�n�̏ꍇ
        if(IJigyoKubun.JIGYO_KUBUN_GAKUSOU_HIKOUBO.equals(jigyoKubun) ||
           IJigyoKubun.JIGYO_KUBUN_GAKUSOU_KOUBO.equals(jigyoKubun)) {
            return getSequenceNumber4GakusouCount(connection, dataInfo);

        //�����̏ꍇ
        } else if (IJigyoKubun.JIGYO_KUBUN_TOKUSUI.equals(jigyoKubun)){
            return getSequenceNumber4TokusuiCount(connection, dataInfo);

        //��Ղ̏ꍇ
        } else if (IJigyoKubun.JIGYO_KUBUN_KIBAN.equals(jigyoKubun)){
            return getSequenceNumber4KibanCount(connection, dataInfo);

        //����̈�̏ꍇ
        } else if (IJigyoKubun.JIGYO_KUBUN_TOKUTEI.equals(jigyoKubun)){
            return getSequenceNumber4TokuteiCount(connection, dataInfo);

        //���X�^�[�g�A�b�v�̏ꍇ  
        } else if (IJigyoKubun.JIGYO_KUBUN_WAKATESTART.equals(jigyoKubun)){
            return getSequenceNumber4KibanCount(connection, dataInfo);

        //���ʌ������i��̏ꍇ    
        } else if (IJigyoKubun.JIGYO_KUBUN_SHOKUSHINHI.equals(jigyoKubun)){
            return getSequenceNumber4KibanCount(connection, dataInfo);

        //����ȊO�̏ꍇ�b��I�ɓ����Ɠ����̔ԃ��[���ɂ��Ă����j
        } else {
            return getSequenceNumber4TokusuiCount(connection, dataInfo);
        }
    }
    
    /**
     * �����ԍ��̃J�E���g�i�w�n�p�j��Ԃ��B
     * @param connection
     * @param dataInfo
     * @return int
     * @throws DataAccessException
     */
    private int getSequenceNumber4GakusouCount(Connection connection, ShinseiDataInfo dataInfo)
        throws DataAccessException {

        String select = "COUNT(A.UKETUKE_NO)";
        
        String query = 
        "SELECT "
            + select
            + " FROM"
            + "  SHINSEIDATAKANRI"+dbLink+" A"
            + " WHERE"
            + "  A.UKETUKE_NO = ?"
            + " AND"
            + "  A.SHOZOKU_CD = ?"
            + " AND"
            + "  A.JIGYO_ID = ?"
            ;
        
        //for debug
        if(log.isDebugEnabled()){
            log.debug("query:" + query);
        }
        
        //DB�ڑ�
        PreparedStatement preparedStatement = null;
        ResultSet recordSet = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            int i = 1;
            DatabaseUtil.setParameter(preparedStatement, i++, dataInfo.getUketukeNo()); 
            DatabaseUtil.setParameter(preparedStatement, i++, dataInfo.getDaihyouInfo().getShozokuCd());
            DatabaseUtil.setParameter(preparedStatement, i++, dataInfo.getJigyoId());
            recordSet = preparedStatement.executeQuery();
             
            int count = 0;
            if (recordSet.next()){
                count =  recordSet.getInt(1);
            }
            
            return count;
             
        } catch (SQLException ex) {
            throw new DataAccessException("�\���f�[�^�e�[�u���������s���ɗ�O���������܂����B", ex);
        } finally {
            DatabaseUtil.closeResource(recordSet, preparedStatement);
        }
    }
    
    /**
     * �����ԍ��̃J�E���g�i�����p�j��Ԃ��B
     * @param connection
     * @param dataInfo
     * @return int
     * @throws DataAccessException
     */
    private int getSequenceNumber4TokusuiCount(Connection connection, ShinseiDataInfo dataInfo)
        throws DataAccessException {

        String select = "COUNT(A.UKETUKE_NO)";

        String query = 
        "SELECT "
            + select
            + " FROM"
            + "  SHINSEIDATAKANRI" + dbLink + " A"
            + " WHERE"
            + "  A.UKETUKE_NO = ?"
            + " AND"
            + "  A.SHOZOKU_CD = ?"
            + " AND"
            + "  A.JIGYO_ID = ?"
            ;
        
        //for debug
        if(log.isDebugEnabled()){
            log.debug("query:" + query);
        }
        
        //DB�ڑ�
        PreparedStatement preparedStatement = null;
        ResultSet recordSet = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            int i = 1;
            DatabaseUtil.setParameter(preparedStatement, i++, dataInfo.getUketukeNo());
            DatabaseUtil.setParameter(preparedStatement, i++, dataInfo.getDaihyouInfo().getShozokuCd());
            DatabaseUtil.setParameter(preparedStatement, i++, dataInfo.getJigyoId());
            recordSet = preparedStatement.executeQuery();
             
            int count = 0;
            if (recordSet.next()){
                count =  recordSet.getInt(1);
            }
            
            return count;
             
        } catch (SQLException ex) {
            throw new DataAccessException("�\���f�[�^�e�[�u���������s���ɗ�O���������܂����B", ex);
        } finally {
            DatabaseUtil.closeResource(recordSet, preparedStatement);
        }
    }

    /**
     * �����ԍ��̃J�E���g�i��՗p�j��Ԃ��B
     * @param connection
     * @param dataInfo
     * @return int
     * @throws DataAccessException
     */
    private int getSequenceNumber4KibanCount(Connection connection, ShinseiDataInfo dataInfo)
        throws DataAccessException {

        String select = "COUNT(A.UKETUKE_NO)";
        
        String query = 
        "SELECT "
            + select
            + " FROM"
            + "  SHINSEIDATAKANRI" + dbLink + " A"
            + " WHERE"
            + "  A.UKETUKE_NO = ?"
            + " AND"
            + "  A.SHOZOKU_CD = ?"
            + " AND"
            + "  A.BUNKASAIMOKU_CD = ?"
            + " AND"
            + "  DECODE(A.BUNKATSU_NO, NULL, '-', BUNKATSU_NO) = ?"         //��̏ꍇ"-"�ɒu��������
            + " AND"
            + "  A.JIGYO_ID = ?"
            ;
        
        //for debug
        if(log.isDebugEnabled()){
            log.debug("query:" + query);
        }
        
        //DB�ڑ�
        PreparedStatement preparedStatement = null;
        ResultSet recordSet = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            int i = 1;
            DatabaseUtil.setParameter(preparedStatement, i++, dataInfo.getUketukeNo());
            DatabaseUtil.setParameter(preparedStatement, i++, dataInfo.getDaihyouInfo().getShozokuCd());
            DatabaseUtil.setParameter(preparedStatement, i++, dataInfo.getKadaiInfo().getBunkaSaimokuCd());
            if(dataInfo.getKadaiInfo().getBunkatsuNo() == null || "".equals(dataInfo.getKadaiInfo().getBunkatsuNo())) {
                DatabaseUtil.setParameter(preparedStatement, i++, "-");          //��̏ꍇ"-"�ɒu��������
            } else {
                DatabaseUtil.setParameter(preparedStatement, i++, dataInfo.getKadaiInfo().getBunkatsuNo());
            }
            DatabaseUtil.setParameter(preparedStatement, i++, dataInfo.getJigyoId());
            recordSet = preparedStatement.executeQuery();
            
            int count = 0;
            if (recordSet.next()){
                count =  recordSet.getInt(1);
            }
            
            return count;

        } catch (SQLException ex) {
            throw new DataAccessException("�\���f�[�^�e�[�u���������s���ɗ�O���������܂����B", ex);
        } finally {
            DatabaseUtil.closeResource(recordSet, preparedStatement);
        }
    }
    
    /**
     * �����ԍ��̃J�E���g�i����̈�p�j��Ԃ��B
     * @param connection
     * @param dataInfo
     * @return
     * @throws DataAccessException
     */
    private int getSequenceNumber4TokuteiCount(Connection connection, ShinseiDataInfo dataInfo)
            throws DataAccessException {

        String select = "COUNT(UKETUKE_NO)";      
        
        String query = 
            "SELECT "
                + select
                + " FROM"
                + "  SHINSEIDATAKANRI" + dbLink + " A"
                + " WHERE"
                + "  A.UKETUKE_NO = ?"
                + " AND"
                + "  A.SHOZOKU_CD = ?"
                + " AND"
                + "  DECODE(A.RYOIKI_NO, NULL, '-', RYOIKI_NO) = ?"     //��̏ꍇ"-"�ɒu��������
                + " AND"
                + "  DECODE(A.KOMOKU_NO, NULL, '-', KOMOKU_NO) = ?"     //��̏ꍇ"-"�ɒu��������
                + " AND"
                + "  A.JIGYO_ID = ?"
                ;
        
        //for debug
        if(log.isDebugEnabled()){log.debug("query:" + query);}

        //DB�ڑ�
        PreparedStatement preparedStatement = null;
        ResultSet recordSet = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            int i = 1;
            DatabaseUtil.setParameter(preparedStatement, i++, dataInfo.getUketukeNo());
            DatabaseUtil.setParameter(preparedStatement, i++, dataInfo.getDaihyouInfo().getShozokuCd());
            if(dataInfo.getRyouikiNo() == null || "".equals(dataInfo.getRyouikiNo())) {
                DatabaseUtil.setParameter(preparedStatement, i++, "-");          //��̏ꍇ"-"�ɒu��������
            } else {
                DatabaseUtil.setParameter(preparedStatement, i++, dataInfo.getRyouikiNo());
            }
            if(dataInfo.getRyouikiKoumokuNo() == null || "".equals(dataInfo.getRyouikiKoumokuNo())) {
                DatabaseUtil.setParameter(preparedStatement, i++, "-");          //��̏ꍇ"-"�ɒu��������
            } else {
                DatabaseUtil.setParameter(preparedStatement, i++, dataInfo.getRyouikiKoumokuNo());
            }
            DatabaseUtil.setParameter(preparedStatement, i++, dataInfo.getJigyoId());
            recordSet = preparedStatement.executeQuery();
            
            int count = 0;
            if (recordSet.next()){
                count =  recordSet.getInt(1);
            }
            
            return count;
             
        } catch (SQLException ex) {
            throw new DataAccessException("�\���f�[�^�e�[�u���������s���ɗ�O���������܂����B", ex);
        } finally {
            DatabaseUtil.closeResource(recordSet, preparedStatement);
        }
    }
//2007/02/05�@�c�@�ǉ������܂�    
}