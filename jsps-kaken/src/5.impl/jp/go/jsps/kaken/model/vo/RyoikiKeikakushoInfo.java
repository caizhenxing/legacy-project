/*======================================================================
 *    SYSTEM      : ���{�w�p�U����d�q�\���V�X�e���i�Ȋw������⏕���j
 *    Source name : RyoikiKeikakushoInfo.java
 *    Description : �󗝓o�^��ʕ\���A�N�V�����N���X
 *
 *    Author      : DIS.jzx
 *    Date        : 2006/06/13
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2006/06/13    V1.0        DIS.jzx        �V�K�쐬
 *====================================================================== 
 */
package jp.go.jsps.kaken.model.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * �̈�v�揑�i�T�v�j����ێ�����N���X�B
 *
 * ID RCSfile="$RCSfile: RyoikiKeikakushoInfo.java,v $"
 * Revision="$Revision: 1.2 $"
 * Date="$Date: 2007/07/25 06:48:38 $"
 */
public class RyoikiKeikakushoInfo extends RyoikiKeikakushoPk {

    /** ���̈�ԍ� */
    private String kariryoikiNo;

    /** ��t�ԍ��i�����ԍ��j */
    private String uketukeNo;

    /** ����ID */
    private String jigyoId;
    
    /** ���ƃR�[�h */
    private String jigyoCd;

    /** �N�x */
    private String nendo;
    
    /** ���ƔN�x�i����Q�P�^�j*/
    private String nendoSeireki;

    /** �� */
    private String kaisu;

    /** ���Ɩ� */
    private String jigyoName;

    /** �\����ID */
    private String shinseishaId;

    /** �̈�v�揑�m��� */
    private Date kakuteiDate;

    /** �̈�v�揑�T�v�쐬�� */
    private Date sakuseiDate;

    /** �����@�֏��F�� */
    private Date shoninDate;

    /** �w�U�󗝓� */
    private Date jyuriDate;

    /** �̈��\�Ҏ����i������-���j */
    private String nameKanjiSei;

    /** �̈��\�Ҏ����i������-���j */
    private String nameKanjiMei;

    /** �̈��\�Ҏ����i�t���K�i-���j */
    private String nameKanaSei;

    /** �̈��\�Ҏ����i�t���K�i-���j */
    private String nameKanaMei;

    /** �N�� */
    private String nenrei;

    /** �\���Ҍ����Ҕԍ� */
    private String kenkyuNo;

    /** �����@�փR�[�h */
    private String shozokuCd;

    /** �����@�֖� */
    private String shozokuName;

    /** �����@�֖��i���́j */
    private String shozokuNameRyaku;

    /** ���ǃR�[�h */
    private String bukyokuCd;

    /** ���ǖ� */
    private String bukyokuName;

    /** ���ǖ��i���́j */
    private String bukyokuNameRyaku;

    /** �E���R�[�h */
    private String shokushuCd;

    /** �E���i�a���j */
    private String shokushuNameKanji;

    /** �E���i���́j */
    private String shokushuNameRyaku;

    /** �R����]����i�n���j�R�[�h */
    private String kiboubumonCd;

    /** �R����]����i�n���j���� */
    private String kiboubumonName;

    /** ����̈於 */
    private String ryoikiName;

    /** �p�� */
    private String ryoikiNameEigo;

    /** �̈旪�̖� */
    private String ryoikiNameRyaku;

    /** �����T�v */
    private String kenkyuGaiyou;

    /** ���O�����̏� */
    private String jizenchousaFlg;

    /** ���O�����̏󋵁i���̑��j */
    private String jizenchousaSonota;

    /** �ߋ��̉���� */
    private String kakoOubojyoukyou;

    /** �ŏI�N�x�O�N�x�̉���i�Y���̗L���j */
    private String zennendoOuboFlg;

    /** �ŏI�N�x�O�N�x�̌����̈�ԍ� */
    private String zennendoOuboNo;

    /** �ŏI�N�x�O�N�x�̗̈旪�̖� */
    private String zennendoOuboRyoikiRyaku;

    /** �ŏI�N�x�O�N�x�̐ݒ���� */
    private String zennendoOuboSettei;

    /** �֘A����i�זڔԍ��j1 */
    private String bunkasaimokuCd1;

    /** �֘A����i����j1 */
    private String bunyaName1;

    /** �֘A����i���ȁj1 */
    private String bunkaName1;

    /** �֘A����i�זځj1 */
    private String saimokuName1;

    /** �֘A����i�זڔԍ��j2 */
    private String bunkasaimokuCd2;

    /** �֘A����i����j2 */
    private String bunyaName2;

    /** �֘A����i���ȁj2 */
    private String bunkaName2;

    /** �֘A����i�זځj2 */
    private String saimokuName2;

    /** �֘A����15���ށi�ԍ��j */
    private String kanrenbunyaBunruiNo;
    
    /** �֘A����15���ށi���ޖ��j */
    private String kanrenbunyaBunruiName;

    /** �����̕K�v��1 */
    private String kenkyuHitsuyousei1;

    /** �����̕K�v��2 */
    private String kenkyuHitsuyousei2;

    /** �����̕K�v��3 */
    private String kenkyuHitsuyousei3;

    /** �����̕K�v��4 */
    private String kenkyuHitsuyousei4;

    /** �����̕K�v��5 */
    private String kenkyuHitsuyousei5;

// 2006/08/25 dyh delete start �����F�d�l�ύX
//    /** �����o��i1�N��)-���v */
//    private String kenkyuSyokei1;
//
//    /** �����o��i1�N��)-���� */
//    private String kenkyuUtiwake1;
// 2006/08/25 dyh delete end

    /** �����o��i2�N��)-���v */
    private String kenkyuSyokei2;

    /** �����o��i2�N��)-���� */
    private String kenkyuUtiwake2;

    /** �����o��i3�N��)-���v */
    private String kenkyuSyokei3;

    /** �����o��i3�N��)-���� */
    private String kenkyuUtiwake3;

    /** �����o��i4�N��)-���v */
    private String kenkyuSyokei4;

    /** �����o��i4�N��)-���� */
    private String kenkyuUtiwake4;

    /** �����o��i5�N��)-���v */
    private String kenkyuSyokei5;

    /** �����o��i5�N��)-���� */
    private String kenkyuUtiwake5;

    /** �����o��i6�N��)-���v */
    private String kenkyuSyokei6;

    /** �����o��i6�N��)-���� */
    private String kenkyuUtiwake6;
    
    /** �����o��i���v) */
    private String kenkyuTotal;

    /** �̈��\�ҁi�X�֔ԍ��j */
    private String daihyouZip;

    /** �̈��\�ҁi�Z���j */
    private String daihyouAddress;

    /** �̈��\�ҁi�d�b�j */
    private String daihyouTel;

    /** �̈��\�ҁiFAX�j */
    private String daihyouFax;

    /** �̈��\�ҁi���[���A�h���X�j */
    private String daihyouEmail;

    /** �����S���Ҏ����i������-���j */
    private String jimutantoNameKanjiSei;

    /** �����S���Ҏ����i������-���j */
    private String jimutantoNameKanjiMei;

    /** �����S���Ҏ����i�t���K�i-���j */
    private String jimutantoNameKanaSei;

    /** �����S���Ҏ����i�t���K�i-���j */
    private String jimutantoNameKanaMei;

    /** �����S���Ҍ����@�֔ԍ� */
    private String jimutantoShozokuCd;

    /** �����S���Ҍ����@�֖� */
    private String jimutantoShozokuName;

    /** �����S���ҕ��ǔԍ� */
    private String jimutantoBukyokuCd;

    /** �����S���ҕ��ǖ� */
    private String jimutantoBukyokuName;

    /** �����S���ҐE���ԍ� */
    private String jimutantoShokushuCd;

    /** �����S���ҐE���i�a���j */
    private String jimutantoShokushuNameKanji;

    /** �����S���ҁi�X�֔ԍ��j */
    private String jimutantoZip;

    /** �����S���ҁi�Z���j */
    private String jimutantoAddress;

    /** �����S���ҁi�d�b�j */
    private String jimutantoTel;

    /** �����S���ҁiFAX�j */
    private String jimutantoFax;

    /** �����S���ҁi���[���A�h���X�j */
    private String jimutantoEmail;

    /** �֘A����̌�����-����1 */
    private String kanrenShimei1;

    /** �֘A����̌�����-���������@��1 */
    private String kanrenKikan1;

    /** �֘A����̌�����-����1 */
    private String kanrenBukyoku1;

    /** �֘A����̌�����-�E��1 */
    private String kanrenShoku1;

    /** �֘A����̌�����-��啪��1 */
    private String kanrenSenmon1;

    /** �֘A����̌�����-�Ζ���d�b�ԍ�1 */
    private String kanrenTel1;

    /** �֘A����̌�����-����d�b�ԍ�1 */
    private String kanrenJitakutel1;

    /** �֘A����̌�����-����2 */
    private String kanrenShimei2;

    /** �֘A����̌�����-���������@��2 */
    private String kanrenKikan2;

    /** �֘A����̌�����-����2 */
    private String kanrenBukyoku2;

    /** �֘A����̌�����-�E��2 */
    private String kanrenShoku2;

    /** �֘A����̌�����-��啪��2 */
    private String kanrenSenmon2;

    /** �֘A����̌�����-�Ζ���d�b�ԍ�2 */
    private String kanrenTel2;

    /** �֘A����̌�����-����d�b�ԍ�2 */
    private String kanrenJitakutel2;

    /** �֘A����̌�����-����3 */
    private String kanrenShimei3;

    /** �֘A����̌�����-���������@��3 */
    private String kanrenKikan3;

    /** �֘A����̌�����-����3 */
    private String kanrenBukyoku3;

    /** �֘A����̌�����-�E��3 */
    private String kanrenShoku3;

    /** �֘A����̌�����-��啪��3 */
    private String kanrenSenmon3;

    /** �֘A����̌�����-�Ζ���d�b�ԍ�3 */
    private String kanrenTel3;

    /** �֘A����̌�����-����d�b�ԍ�3 */
    private String kanrenJitakutel3;

    /** �̈�v�揑�T�vPDF�̊i�[�p�X */
    private String pdfPath;

    /** �\��PDF�i�[�p�X */
    private String hyoshiPdfPath;

    /** �󗝌��� */
    private String juriKekka;

    /** �󗝌��ʔ��l */
    private String juriBiko;

    /** �̈�v�揑�i�T�v�j�\����ID */
    private String ryoikiJokyoId;
    
    /** �̈�v�揑�i�T�v�j����� */
    private String ryoikiJokyoName;
    
    /** �� */
    private String edition;

    /** �m��t���O */
    private String ryoikikeikakushoKakuteiFlg;

    /** �����t���O */
    private String cancelFlg;

    /** �폜�t���O */
    private String delFlg;
    
    /** ���匏�� */
    private String count;
    
    /** �\���󋵔z�� */
    private String[] jokyoIds;
    
    /** �̈�v�揑�i�T�v�j�\����ID�z�� */
    private String[] ryoikiJokyoIds;
    
    //2006.07.04 zhangt add start
    /** �����o��\ */
    private List kenkyuKeihiList = new ArrayList();

    /** �����g�D�\ */
    private List KenkyuSosikiList = new ArrayList();

    /** �Y�t�t�@�C����� */
    private TenpuFileInfo[] tenpuFileInfos;
    //2006.07.04 zhangt add start
    
    /** �Y�t�t�@�[�����X�g��I������̒l */
    private String tenpuFileFlg;

    //2006.09.15 iso �^�C�g���Ɂu�T�v�v������PDF�쐬�̂���
    /** �ϊ�PDF�̃^�C�g���ɕt�����镶���� */
    private String addTitele;
    
//ADD�@START 2007-07-25 BIS ������
//  ���͂��������g�D�ɃG���[������ꍇ�A�u�̈�v�揑���́v��ʂŃG���[���b�Z�[�W��\�����A�u�����o��\�v��ʂŃG���[�ɂȂ鍀�ڂ̔w�i�F��ύX����B	
    /** �G���[���b�Z�W�[ */
    private HashMap errorsMap;
//ADD�@END 2007-07-25 BIS ������

    //---------------------------------------------------------------------
    // Constructors
    //---------------------------------------------------------------------

    /**
     * �R���X�g���N�^�B
     */
    public RyoikiKeikakushoInfo() {
        super();
    }
        
    //---------------------------------------------------------------------
    // Methods
    //---------------------------------------------------------------------

    
    //---------------------------------------------------------------------
    // Properties
    //---------------------------------------------------------------------
    
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
     * ��t�ԍ��i�����ԍ��j���擾
     * @return String ��t�ԍ��i�����ԍ��j
     */
    public String getUketukeNo() {
        return uketukeNo;
    }

    /**
     * ��t�ԍ��i�����ԍ��j��ݒ�
     * @param uketukeNo ��t�ԍ��i�����ԍ��j
     */
    public void setUketukeNo(String uketukeNo) {
        this.uketukeNo = uketukeNo;
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
     * �N�x���擾
     * @return String �N�x
     */
    public String getNendo() {
        return nendo;
    }

    /**
     * �N�x��ݒ�
     * @param nendo �N�x
     */
    public void setNendo(String nendo) {
        this.nendo = nendo;
    }

    /**
     * �񐔂��擾
     * @return String ��
     */
    public String getKaisu() {
        return kaisu;
    }

    /**
     * �񐔂�ݒ�
     * @param kaisu ��
     */
    public void setKaisu(String kaisu) {
        this.kaisu = kaisu;
    }

    /**
     * ���Ɩ����擾
     * @return String ���Ɩ�
     */
    public String getJigyoName() {
        return jigyoName;
    }

    /**
     * ���Ɩ���ݒ�
     * @param jigyoName ���Ɩ�
     */
    public void setJigyoName(String jigyoName) {
        this.jigyoName = jigyoName;
    }

    /**
     * �\����ID���擾
     * @return String �\����ID
     */
    public String getShinseishaId() {
        return shinseishaId;
    }

    /**
     * �\����ID��ݒ�
     * @param shinseishaId �\����ID
     */
    public void setShinseishaId(String shinseishaId) {
        this.shinseishaId = shinseishaId;
    }

    /**
     * �̈�v�揑�m������擾
     * @return Date �̈�v�揑�m���
     */
    public Date getKakuteiDate() {
        return kakuteiDate;
    }

    /**
     * �̈�v�揑�m�����ݒ�
     * @param kakuteiDate �̈�v�揑�m���
     */
    public void setKakuteiDate(Date kakuteiDate) {
        this.kakuteiDate = kakuteiDate;
    }

    /**
     * �̈�v�揑�T�v�쐬�����擾
     * @return Date �̈�v�揑�T�v�쐬��
     */
    public Date getSakuseiDate() {
        return sakuseiDate;
    }

    /**
     * �̈�v�揑�T�v�쐬����ݒ�
     * @param sakuseiDate �̈�v�揑�T�v�쐬��
     */
    public void setSakuseiDate(Date sakuseiDate) {
        this.sakuseiDate = sakuseiDate;
    }

    /**
     * �����@�֏��F�����擾
     * @return Date �����@�֏��F��
     */
    public Date getShoninDate() {
        return shoninDate;
    }

    /**
     * �����@�֏��F����ݒ�
     * @param shoninDate �����@�֏��F��
     */
    public void setShoninDate(Date shoninDate) {
        this.shoninDate = shoninDate;
    }

    /**
     * �w�U�󗝓����擾
     * @return Date �w�U�󗝓�
     */
    public Date getJyuriDate() {
        return jyuriDate;
    }

    /**
     * �w�U�󗝓���ݒ�
     * @param jyuriDate �w�U�󗝓�
     */
    public void setJyuriDate(Date jyuriDate) {
        this.jyuriDate = jyuriDate;
    }

    /**
     * �̈��\�Ҏ����i������-���j���擾
     * @return String �̈��\�Ҏ����i������-���j
     */
    public String getNameKanjiSei() {
        return nameKanjiSei;
    }

    /**
     * �̈��\�Ҏ����i������-���j��ݒ�
     * @param nameKanjiSei �̈��\�Ҏ����i������-���j
     */
    public void setNameKanjiSei(String nameKanjiSei) {
        this.nameKanjiSei = nameKanjiSei;
    }

    /**
     * �̈��\�Ҏ����i������-���j���擾
     * @return String �̈��\�Ҏ����i������-���j
     */
    public String getNameKanjiMei() {
        return nameKanjiMei;
    }

    /**
     * �̈��\�Ҏ����i������-���j��ݒ�
     * @param nameKanjiMei �̈��\�Ҏ����i������-���j
     */
    public void setNameKanjiMei(String nameKanjiMei) {
        this.nameKanjiMei = nameKanjiMei;
    }

    /**
     * �̈��\�Ҏ����i�t���K�i-���j���擾
     * @return String �̈��\�Ҏ����i�t���K�i-���j
     */
    public String getNameKanaSei() {
        return nameKanaSei;
    }

    /**
     * �̈��\�Ҏ����i�t���K�i-���j��ݒ�
     * @param nameKanaSei �̈��\�Ҏ����i�t���K�i-���j
     */
    public void setNameKanaSei(String nameKanaSei) {
        this.nameKanaSei = nameKanaSei;
    }

    /**
     * �̈��\�Ҏ����i�t���K�i-���j���擾
     * @return String �̈��\�Ҏ����i�t���K�i-���j
     */
    public String getNameKanaMei() {
        return nameKanaMei;
    }

    /**
     * �̈��\�Ҏ����i�t���K�i-���j��ݒ�
     * @param nameKanaMei �̈��\�Ҏ����i�t���K�i-���j
     */
    public void setNameKanaMei(String nameKanaMei) {
        this.nameKanaMei = nameKanaMei;
    }

    /**
     * �N����擾
     * @return String �N��
     */
    public String getNenrei() {
        return nenrei;
    }

    /**
     * �N���ݒ�
     * @param nenrei �N��
     */
    public void setNenrei(String nenrei) {
        this.nenrei = nenrei;
    }

    /**
     * �\���Ҍ����Ҕԍ����擾
     * @return String �\���Ҍ����Ҕԍ�
     */
    public String getKenkyuNo() {
        return kenkyuNo;
    }

    /**
     * �\���Ҍ����Ҕԍ���ݒ�
     * @param kenkyuNo �\���Ҍ����Ҕԍ�
     */
    public void setKenkyuNo(String kenkyuNo) {
        this.kenkyuNo = kenkyuNo;
    }

    /**
     * �����@�փR�[�h���擾
     * @return String �����@�փR�[�h
     */
    public String getShozokuCd() {
        return shozokuCd;
    }

    /**
     * �����@�փR�[�h��ݒ�
     * @param shozokuCd �����@�փR�[�h
     */
    public void setShozokuCd(String shozokuCd) {
        this.shozokuCd = shozokuCd;
    }

    /**
     * �����@�֖����擾
     * @return String �����@�֖�
     */
    public String getShozokuName() {
        return shozokuName;
    }

    /**
     * �����@�֖���ݒ�
     * @param shozokuName �����@�֖�
     */
    public void setShozokuName(String shozokuName) {
        this.shozokuName = shozokuName;
    }

    /**
     * �����@�֖��i���́j���擾
     * @return String �����@�֖��i���́j
     */
    public String getShozokuNameRyaku() {
        return shozokuNameRyaku;
    }

    /**
     * �����@�֖��i���́j��ݒ�
     * @param shozokuNameRyaku �����@�֖��i���́j
     */
    public void setShozokuNameRyaku(String shozokuNameRyaku) {
        this.shozokuNameRyaku = shozokuNameRyaku;
    }

    /**
     * ���ǃR�[�h���擾
     * @return String ���ǃR�[�h
     */
    public String getBukyokuCd() {
        return bukyokuCd;
    }

    /**
     * ���ǃR�[�h��ݒ�
     * @param bukyokuCd ���ǃR�[�h
     */
    public void setBukyokuCd(String bukyokuCd) {
        this.bukyokuCd = bukyokuCd;
    }

    /**
     * ���ǖ����擾
     * @return String ���ǖ�
     */
    public String getBukyokuName() {
        return bukyokuName;
    }

    /**
     * ���ǖ���ݒ�
     * @param bukyokuName ���ǖ�
     */
    public void setBukyokuName(String bukyokuName) {
        this.bukyokuName = bukyokuName;
    }

    /**
     * ���ǖ��i���́j���擾
     * @return String ���ǖ��i���́j
     */
    public String getBukyokuNameRyaku() {
        return bukyokuNameRyaku;
    }

    /**
     * ���ǖ��i���́j��ݒ�
     * @param bukyokuNameRyaku ���ǖ��i���́j
     */
    public void setBukyokuNameRyaku(String bukyokuNameRyaku) {
        this.bukyokuNameRyaku = bukyokuNameRyaku;
    }

    /**
     * �E���R�[�h���擾
     * @return String �E���R�[�h
     */
    public String getShokushuCd() {
        return shokushuCd;
    }

    /**
     * �E���R�[�h��ݒ�
     * @param shokushuCd �E���R�[�h
     */
    public void setShokushuCd(String shokushuCd) {
        this.shokushuCd = shokushuCd;
    }

    /**
     * �E���i�a���j���擾
     * @return String �E���i�a���j
     */
    public String getShokushuNameKanji() {
        return shokushuNameKanji;
    }

    /**
     * �E���i�a���j��ݒ�
     * @param shokushuNameKanji �E���i�a���j
     */
    public void setShokushuNameKanji(String shokushuNameKanji) {
        this.shokushuNameKanji = shokushuNameKanji;
    }

    /**
     * �E���i���́j���擾
     * @return Stirng �E���i���́j
     */
    public String getShokushuNameRyaku() {
        return shokushuNameRyaku;
    }

    /**
     * �E���i���́j��ݒ�
     * @param shokushuNameRyaku �E���i���́j
     */
    public void setShokushuNameRyaku(String shokushuNameRyaku) {
        this.shokushuNameRyaku = shokushuNameRyaku;
    }

    /**
     * �R����]����i�n���j�R�[�h���擾
     * @return String �R����]����i�n���j�R�[�h
     */
    public String getKiboubumonCd() {
        return kiboubumonCd;
    }

    /**
     * �R����]����i�n���j�R�[�h��ݒ�
     * @param kiboubumonCd �R����]����i�n���j�R�[�h
     */
    public void setKiboubumonCd(String kiboubumonCd) {
        this.kiboubumonCd = kiboubumonCd;
    }

    /**
     * �R����]����i�n���j���̂��擾
     * @return String �R����]����i�n���j����
     */
    public String getKiboubumonName() {
        return kiboubumonName;
    }

    /**
     * �R����]����i�n���j���̂�ݒ�
     * @param kiboubumonName �R����]����i�n���j����
     */
    public void setKiboubumonName(String kiboubumonName) {
        this.kiboubumonName = kiboubumonName;
    }

    /**
     * ����̈於���擾
     * @return String ����̈於
     */
    public String getRyoikiName() {
        return ryoikiName;
    }

    /**
     * ����̈於��ݒ�
     * @param ryoikiName ����̈於
     */
    public void setRyoikiName(String ryoikiName) {
        this.ryoikiName = ryoikiName;
    }

    /**
     * �p�󖼂��擾
     * @return String �p��
     */
    public String getRyoikiNameEigo() {
        return ryoikiNameEigo;
    }

    /**
     * �p�󖼂�ݒ�
     * @param ryoikiNameEigo �p��
     */
    public void setRyoikiNameEigo(String ryoikiNameEigo) {
        this.ryoikiNameEigo = ryoikiNameEigo;
    }

    /**
     * �̈旪�̖����擾
     * @return String �̈旪�̖�
     */
    public String getRyoikiNameRyaku() {
        return ryoikiNameRyaku;
    }

    /**
     * �̈旪�̖���ݒ�
     * @param ryoikiNameRyaku �̈旪�̖�
     */
    public void setRyoikiNameRyaku(String ryoikiNameRyaku) {
        this.ryoikiNameRyaku = ryoikiNameRyaku;
    }

    /**
     * �����T�v���擾
     * @return String �����T�v
     */
    public String getKenkyuGaiyou() {
        return kenkyuGaiyou;
    }

    /**
     * �����T�v��ݒ�
     * @param kenkyuGaiyou �����T�v
     */
    public void setKenkyuGaiyou(String kenkyuGaiyou) {
        this.kenkyuGaiyou = kenkyuGaiyou;
    }

    /**
     * ���O�����̏󋵂��擾
     * @return String ���O�����̏�
     */
    public String getJizenchousaFlg() {
        return jizenchousaFlg;
    }

    /**
     * ���O�����̏󋵂�ݒ�
     * @param jizenchousaFlg ���O�����̏�
     */
    public void setJizenchousaFlg(String jizenchousaFlg) {
        this.jizenchousaFlg = jizenchousaFlg;
    }

    /**
     * ���O�����̏󋵁i���̑��j���擾
     * @return String ���O�����̏󋵁i���̑��j
     */
    public String getJizenchousaSonota() {
        return jizenchousaSonota;
    }

    /**
     * ���O�����̏󋵁i���̑��j��ݒ�
     * @param jizenchousaSonota ���O�����̏󋵁i���̑��j
     */
    public void setJizenchousaSonota(String jizenchousaSonota) {
        this.jizenchousaSonota = jizenchousaSonota;
    }

    /**
     * �ߋ��̉���󋵂��擾
     * @return String �ߋ��̉����
     */
    public String getKakoOubojyoukyou() {
        return kakoOubojyoukyou;
    }

    /**
     * �ߋ��̉���󋵂�ݒ�
     * @param kakoOubojyoukyou �ߋ��̉����
     */
    public void setKakoOubojyoukyou(String kakoOubojyoukyou) {
        this.kakoOubojyoukyou = kakoOubojyoukyou;
    }

    /**
     * �ŏI�N�x�O�N�x�̉���i�Y���̗L���j���擾
     * @return String �ŏI�N�x�O�N�x�̉���i�Y���̗L���j
     */
    public String getZennendoOuboFlg() {
        return zennendoOuboFlg;
    }

    /**
     * �ŏI�N�x�O�N�x�̉���i�Y���̗L���j��ݒ�
     * @param zennendoOuboFlg �ŏI�N�x�O�N�x�̉���i�Y���̗L���j
     */
    public void setZennendoOuboFlg(String zennendoOuboFlg) {
        this.zennendoOuboFlg = zennendoOuboFlg;
    }

    /**
     * �ŏI�N�x�O�N�x�̌����̈�ԍ����擾
     * @return String �ŏI�N�x�O�N�x�̌����̈�ԍ�
     */
    public String getZennendoOuboNo() {
        return zennendoOuboNo;
    }

    /**
     * �ŏI�N�x�O�N�x�̌����̈�ԍ���ݒ�
     * @param zennendoOuboNo �ŏI�N�x�O�N�x�̌����̈�ԍ�
     */
    public void setZennendoOuboNo(String zennendoOuboNo) {
        this.zennendoOuboNo = zennendoOuboNo;
    }

    /**
     * �ŏI�N�x�O�N�x�̗̈旪�̖����擾
     * @return String �ŏI�N�x�O�N�x�̗̈旪�̖�
     */
    public String getZennendoOuboRyoikiRyaku() {
        return zennendoOuboRyoikiRyaku;
    }

    /**
     * �ŏI�N�x�O�N�x�̗̈旪�̖���ݒ�
     * @param zennendoOuboRyoikiRyaku �ŏI�N�x�O�N�x�̗̈旪�̖�
     */
    public void setZennendoOuboRyoikiRyaku(String zennendoOuboRyoikiRyaku) {
        this.zennendoOuboRyoikiRyaku = zennendoOuboRyoikiRyaku;
    }

    /**
     * �ŏI�N�x�O�N�x�̐ݒ���Ԃ��擾
     * @return String �ŏI�N�x�O�N�x�̐ݒ����
     */
    public String getZennendoOuboSettei() {
        return zennendoOuboSettei;
    }

    /**
     * �ŏI�N�x�O�N�x�̐ݒ���Ԃ�ݒ�
     * @param zennendoOuboSettei �ŏI�N�x�O�N�x�̐ݒ����
     */
    public void setZennendoOuboSettei(String zennendoOuboSettei) {
        this.zennendoOuboSettei = zennendoOuboSettei;
    }

    /**
     * �֘A����i�זڔԍ��j1���擾
     * @return String �֘A����i�זڔԍ��j1
     */
    public String getBunkasaimokuCd1() {
        return bunkasaimokuCd1;
    }

    /**
     * �֘A����i�זڔԍ��j1��ݒ�
     * @param bunkasaimokuCd1 �֘A����i�זڔԍ��j1
     */
    public void setBunkasaimokuCd1(String bunkasaimokuCd1) {
        this.bunkasaimokuCd1 = bunkasaimokuCd1;
    }

    /**
     * �֘A����i����j1���擾
     * @return String �֘A����i����j1
     */
    public String getBunyaName1() {
        return bunyaName1;
    }

    /**
     * �֘A����i����j1��ݒ�
     * @param bunyaName1 �֘A����i����j1
     */
    public void setBunyaName1(String bunyaName1) {
        this.bunyaName1 = bunyaName1;
    }

    /**
     * �֘A����i���ȁj1���擾
     * @return String �֘A����i���ȁj1
     */
    public String getBunkaName1() {
        return bunkaName1;
    }

    /**
     * �֘A����i���ȁj1��ݒ�
     * @param bunkaName1 �֘A����i���ȁj1
     */
    public void setBunkaName1(String bunkaName1) {
        this.bunkaName1 = bunkaName1;
    }

    /**
     * �֘A����i�זځj1���擾
     * @return String �֘A����i�זځj1
     */
    public String getSaimokuName1() {
        return saimokuName1;
    }

    /**
     * �֘A����i�זځj1��ݒ�
     * @param saimokuName1 �֘A����i�זځj1
     */
    public void setSaimokuName1(String saimokuName1) {
        this.saimokuName1 = saimokuName1;
    }

    /**
     * �֘A����i�זڔԍ��j2���擾
     * @return String �֘A����i�זڔԍ��j2
     */
    public String getBunkasaimokuCd2() {
        return bunkasaimokuCd2;
    }

    /**
     * �֘A����i�זڔԍ��j2��ݒ�
     * @param bunkasaimokuCd2 �֘A����i�זڔԍ��j2
     */
    public void setBunkasaimokuCd2(String bunkasaimokuCd2) {
        this.bunkasaimokuCd2 = bunkasaimokuCd2;
    }

    /**
     * �֘A����i����j2���擾
     * @return String �֘A����i����j2
     */
    public String getBunyaName2() {
        return bunyaName2;
    }

    /**
     * �֘A����i����j2��ݒ�
     * @param bunyaName2 �֘A����i����j2
     */
    public void setBunyaName2(String bunyaName2) {
        this.bunyaName2 = bunyaName2;
    }

    /**
     * �֘A����i���ȁj2���擾
     * @return String �֘A����i���ȁj2
     */
    public String getBunkaName2() {
        return bunkaName2;
    }

    /**
     * �֘A����i���ȁj2��ݒ�
     * @param bunkaName2 �֘A����i���ȁj2
     */
    public void setBunkaName2(String bunkaName2) {
        this.bunkaName2 = bunkaName2;
    }

    /**
     * �֘A����i�זځj2���擾
     * @return String �֘A����i�זځj2
     */
    public String getSaimokuName2() {
        return saimokuName2;
    }

    /**
     * �֘A����i�זځj2��ݒ�
     * @param saimokuName2 �֘A����i�זځj2
     */
    public void setSaimokuName2(String saimokuName2) {
        this.saimokuName2 = saimokuName2;
    }

    /**
     * �֘A����15���ށi�ԍ��j���擾
     * @return String �֘A����15���ށi�ԍ��j
     */
    public String getKanrenbunyaBunruiNo() {
        return kanrenbunyaBunruiNo;
    }

    /**
     * �֘A����15���ށi�ԍ��j��ݒ�
     * @param kanrenbunyaBunruiNo �֘A����15���ށi�ԍ��j
     */
    public void setKanrenbunyaBunruiNo(String kanrenbunyaBunruiNo) {
        this.kanrenbunyaBunruiNo = kanrenbunyaBunruiNo;
    }

    /**
     * �֘A����15���ށi���ޖ��j���擾
     * @return String �֘A����15���ށi���ޖ��j
     */
    public String getKanrenbunyaBunruiName() {
        return kanrenbunyaBunruiName;
    }

    /**
     * �֘A����15���ށi���ޖ��j��ݒ�
     * @param kanrenbunyaBunruiName �֘A����15���ށi���ޖ��j
     */
    public void setKanrenbunyaBunruiName(String kanrenbunyaBunruiName) {
        this.kanrenbunyaBunruiName = kanrenbunyaBunruiName;
    }

    /**
     * �����̕K�v��1���擾
     * @return String �����̕K�v��1
     */
    public String getKenkyuHitsuyousei1() {
        return kenkyuHitsuyousei1;
    }

    /**
     * �����̕K�v��1��ݒ�
     * @param kenkyuHitsuyousei1 �����̕K�v��1
     */
    public void setKenkyuHitsuyousei1(String kenkyuHitsuyousei1) {
        this.kenkyuHitsuyousei1 = kenkyuHitsuyousei1;
    }

    /**
     * �����̕K�v��2���擾
     * @return String �����̕K�v��2
     */
    public String getKenkyuHitsuyousei2() {
        return kenkyuHitsuyousei2;
    }

    /**
     * �����̕K�v��2��ݒ�
     * @param kenkyuHitsuyousei2 �����̕K�v��2
     */
    public void setKenkyuHitsuyousei2(String kenkyuHitsuyousei2) {
        this.kenkyuHitsuyousei2 = kenkyuHitsuyousei2;
    }

    /**
     * �����̕K�v��3���擾
     * @return String �����̕K�v��3
     */
    public String getKenkyuHitsuyousei3() {
        return kenkyuHitsuyousei3;
    }

    /**
     * �����̕K�v��3��ݒ�
     * @param kenkyuHitsuyousei3 �����̕K�v��3
     */
    public void setKenkyuHitsuyousei3(String kenkyuHitsuyousei3) {
        this.kenkyuHitsuyousei3 = kenkyuHitsuyousei3;
    }

    /**
     * �����̕K�v��4���擾
     * @return String �����̕K�v��4
     */
    public String getKenkyuHitsuyousei4() {
        return kenkyuHitsuyousei4;
    }

    /**
     * �����̕K�v��4��ݒ�
     * @param kenkyuHitsuyousei4 �����̕K�v��4
     */
    public void setKenkyuHitsuyousei4(String kenkyuHitsuyousei4) {
        this.kenkyuHitsuyousei4 = kenkyuHitsuyousei4;
    }

    /**
     * �����̕K�v��5���擾
     * @return String �����̕K�v��5
     */
    public String getKenkyuHitsuyousei5() {
        return kenkyuHitsuyousei5;
    }

    /**
     * �����̕K�v��5��ݒ�
     * @param kenkyuHitsuyousei5 �����̕K�v��5
     */
    public void setKenkyuHitsuyousei5(String kenkyuHitsuyousei5) {
        this.kenkyuHitsuyousei5 = kenkyuHitsuyousei5;
    }

// 2006/08/25 dyh delete start �����F�d�l�ύX
//    /**
//     * �����o��i1�N��)-���v���擾
//     * @return Returns �����o��i1�N��)-���v
//     */
//    public String getKenkyuSyokei1() {
//        return kenkyuSyokei1;
//    }
//
//    /**
//     * �����o��i1�N��)-���v��ݒ�
//     * @param kenkyuSyokei1 �����o��i1�N��)-���v
//     */
//    public void setKenkyuSyokei1(String kenkyuSyokei1) {
//        this.kenkyuSyokei1 = kenkyuSyokei1;
//    }
//
//    /**
//     * �����o��i1�N��)-������擾
//     * @return String �����o��i1�N��)-����
//     */
//    public String getKenkyuUtiwake1() {
//        return kenkyuUtiwake1;
//    }
//
//    /**
//     * �����o��i1�N��)-�����ݒ�
//     * @param kenkyuUtiwake1 �����o��i1�N��)-����
//     */
//    public void setKenkyuUtiwake1(String kenkyuUtiwake1) {
//        this.kenkyuUtiwake1 = kenkyuUtiwake1;
//    }
// 2006/08/25 dyh delete end

    /**
     * �����o��i2�N��)-���v���擾
     * @return String �����o��i2�N��)-���v
     */
    public String getKenkyuSyokei2() {
        return kenkyuSyokei2;
    }

    /**
     * �����o��i2�N��)-���v��ݒ�
     * @param kenkyuSyokei2 �����o��i2�N��)-���v
     */
    public void setKenkyuSyokei2(String kenkyuSyokei2) {
        this.kenkyuSyokei2 = kenkyuSyokei2;
    }

    /**
     * �����o��i2�N��)-������擾
     * @return String �����o��i2�N��)-����
     */
    public String getKenkyuUtiwake2() {
        return kenkyuUtiwake2;
    }

    /**
     * �����o��i2�N��)-�����ݒ�
     * @param kenkyuUtiwake2 �����o��i2�N��)-����
     */
    public void setKenkyuUtiwake2(String kenkyuUtiwake2) {
        this.kenkyuUtiwake2 = kenkyuUtiwake2;
    }

    /**
     * �����o��i3�N��)-���v���擾
     * @return String �����o��i3�N��)-���v
     */
    public String getKenkyuSyokei3() {
        return kenkyuSyokei3;
    }

    /**
     * �����o��i3�N��)-���v��ݒ�
     * @param kenkyuSyokei3 �����o��i3�N��)-���v
     */
    public void setKenkyuSyokei3(String kenkyuSyokei3) {
        this.kenkyuSyokei3 = kenkyuSyokei3;
    }

    /**
     * �����o��i3�N��)-������擾
     * @return String �����o��i3�N��)-����
     */
    public String getKenkyuUtiwake3() {
        return kenkyuUtiwake3;
    }

    /**
     * �����o��i3�N��)-�����ݒ�
     * @param kenkyuUtiwake3 �����o��i3�N��)-����
     */
    public void setKenkyuUtiwake3(String kenkyuUtiwake3) {
        this.kenkyuUtiwake3 = kenkyuUtiwake3;
    }

    /**
     * �����o��i4�N��)-���v���擾
     * @return String �����o��i4�N��)-���v
     */
    public String getKenkyuSyokei4() {
        return kenkyuSyokei4;
    }

    /**
     * �����o��i4�N��)-���v��ݒ�
     * @param kenkyuSyokei4 �����o��i4�N��)-���v
     */
    public void setKenkyuSyokei4(String kenkyuSyokei4) {
        this.kenkyuSyokei4 = kenkyuSyokei4;
    }

    /**
     * �����o��i4�N��)-������擾
     * @return String �����o��i4�N��)-����
     */
    public String getKenkyuUtiwake4() {
        return kenkyuUtiwake4;
    }

    /**
     * �����o��i4�N��)-�����ݒ�
     * @param kenkyuUtiwake4 �����o��i4�N��)-����
     */
    public void setKenkyuUtiwake4(String kenkyuUtiwake4) {
        this.kenkyuUtiwake4 = kenkyuUtiwake4;
    }

    /**
     * �����o��i5�N��)-���v���擾
     * @return String �����o��i5�N��)-���v
     */
    public String getKenkyuSyokei5() {
        return kenkyuSyokei5;
    }

    /**
     * �����o��i5�N��)-���v��ݒ�
     * @param kenkyuSyokei5 �����o��i5�N��)-���v
     */
    public void setKenkyuSyokei5(String kenkyuSyokei5) {
        this.kenkyuSyokei5 = kenkyuSyokei5;
    }

    /**
     * �����o��i5�N��)-������擾
     * @return String �����o��i5�N��)-����
     */
    public String getKenkyuUtiwake5() {
        return kenkyuUtiwake5;
    }

    /**
     * �����o��i5�N��)-�����ݒ�
     * @param kenkyuUtiwake5 �����o��i5�N��)-����
     */
    public void setKenkyuUtiwake5(String kenkyuUtiwake5) {
        this.kenkyuUtiwake5 = kenkyuUtiwake5;
    }

    /**
     * �����o��i6�N��)-���v���擾
     * @return String �����o��i6�N��)-���v
     */
    public String getKenkyuSyokei6() {
        return kenkyuSyokei6;
    }

    /**
     * �����o��i6�N��)-���v��ݒ�
     * @param kenkyuSyokei6 �����o��i6�N��)-���v
     */
    public void setKenkyuSyokei6(String kenkyuSyokei6) {
        this.kenkyuSyokei6 = kenkyuSyokei6;
    }

    /**
     * �����o��i6�N��)-������擾
     * @return String �����o��i6�N��)-����
     */
    public String getKenkyuUtiwake6() {
        return kenkyuUtiwake6;
    }

    /**
     * �����o��i6�N��)-�����ݒ�
     * @param kenkyuUtiwake6 �����o��i6�N��)-����
     */
    public void setKenkyuUtiwake6(String kenkyuUtiwake6) {
        this.kenkyuUtiwake6 = kenkyuUtiwake6;
    }

    /**
     * �̈��\�ҁi�X�֔ԍ��j���擾
     * @return String �̈��\�ҁi�X�֔ԍ��j
     */
    public String getDaihyouZip() {
        return daihyouZip;
    }

    /**
     * �̈��\�ҁi�X�֔ԍ��j��ݒ�
     * @param daihyouZip �̈��\�ҁi�X�֔ԍ��j
     */
    public void setDaihyouZip(String daihyouZip) {
        this.daihyouZip = daihyouZip;
    }

    /**
     * �̈��\�ҁi�Z���j���擾
     * @return String �̈��\�ҁi�Z���j
     */
    public String getDaihyouAddress() {
        return daihyouAddress;
    }

    /**
     * �̈��\�ҁi�Z���j��ݒ�
     * @param daihyouAddress �̈��\�ҁi�Z���j
     */
    public void setDaihyouAddress(String daihyouAddress) {
        this.daihyouAddress = daihyouAddress;
    }

    /**
     * �̈��\�ҁi�d�b�j���擾
     * @return String �̈��\�ҁi�d�b�j
     */
    public String getDaihyouTel() {
        return daihyouTel;
    }

    /**
     * �̈��\�ҁi�d�b�j��ݒ�
     * @param daihyouTel �̈��\�ҁi�d�b�j
     */
    public void setDaihyouTel(String daihyouTel) {
        this.daihyouTel = daihyouTel;
    }

    /**
     * �̈��\�ҁiFAX�j���擾
     * @return String �̈��\�ҁiFAX�j
     */
    public String getDaihyouFax() {
        return daihyouFax;
    }

    /**
     * �̈��\�ҁiFAX�j��ݒ�
     * @param daihyouFax �̈��\�ҁiFAX�j
     */
    public void setDaihyouFax(String daihyouFax) {
        this.daihyouFax = daihyouFax;
    }

    /**
     * �̈��\�ҁi���[���A�h���X�j���擾
     * @return String �̈��\�ҁi���[���A�h���X�j
     */
    public String getDaihyouEmail() {
        return daihyouEmail;
    }

    /**
     * �̈��\�ҁi���[���A�h���X�j��ݒ�
     * @param daihyouEmail �̈��\�ҁi���[���A�h���X�j
     */
    public void setDaihyouEmail(String daihyouEmail) {
        this.daihyouEmail = daihyouEmail;
    }

    /**
     * �����S���Ҏ����i������-���j���擾
     * @return String �����S���Ҏ����i������-���j
     */
    public String getJimutantoNameKanjiSei() {
        return jimutantoNameKanjiSei;
    }

    /**
     * �����S���Ҏ����i������-���j��ݒ�
     * @param jimutantoNameKanjiSei �����S���Ҏ����i������-���j
     */
    public void setJimutantoNameKanjiSei(String jimutantoNameKanjiSei) {
        this.jimutantoNameKanjiSei = jimutantoNameKanjiSei;
    }

    /**
     * �����S���Ҏ����i������-���j���擾
     * @return String �����S���Ҏ����i������-���j
     */
    public String getJimutantoNameKanjiMei() {
        return jimutantoNameKanjiMei;
    }

    /**
     * �����S���Ҏ����i������-���j��ݒ�
     * @param jimutantoNameKanjiMei �����S���Ҏ����i������-���j
     */
    public void setJimutantoNameKanjiMei(String jimutantoNameKanjiMei) {
        this.jimutantoNameKanjiMei = jimutantoNameKanjiMei;
    }

    /**
     * �����S���Ҏ����i�t���K�i-���j���擾
     * @return String �����S���Ҏ����i�t���K�i-���j
     */
    public String getJimutantoNameKanaSei() {
        return jimutantoNameKanaSei;
    }

    /**
     * �����S���Ҏ����i�t���K�i-���j��ݒ�
     * @param jimutantoNameKanaSei �����S���Ҏ����i�t���K�i-���j
     */
    public void setJimutantoNameKanaSei(String jimutantoNameKanaSei) {
        this.jimutantoNameKanaSei = jimutantoNameKanaSei;
    }

    /**
     * �����S���Ҏ����i�t���K�i-���j���擾
     * @return String �����S���Ҏ����i�t���K�i-���j
     */
    public String getJimutantoNameKanaMei() {
        return jimutantoNameKanaMei;
    }

    /**
     * �����S���Ҏ����i�t���K�i-���j��ݒ�
     * @param jimutantoNameKanaMei �����S���Ҏ����i�t���K�i-���j
     */
    public void setJimutantoNameKanaMei(String jimutantoNameKanaMei) {
        this.jimutantoNameKanaMei = jimutantoNameKanaMei;
    }

    /**
     * �����S���Ҍ����@�֔ԍ����擾
     * @return String �����S���Ҍ����@�֔ԍ�
     */
    public String getJimutantoShozokuCd() {
        return jimutantoShozokuCd;
    }

    /**
     * �����S���Ҍ����@�֔ԍ���ݒ�
     * @param jimutantoShozokuCd �����S���Ҍ����@�֔ԍ�
     */
    public void setJimutantoShozokuCd(String jimutantoShozokuCd) {
        this.jimutantoShozokuCd = jimutantoShozokuCd;
    }

    /**
     * �����S���Ҍ����@�֖����擾
     * @return String �����S���Ҍ����@�֖�
     */
    public String getJimutantoShozokuName() {
        return jimutantoShozokuName;
    }

    /**
     * �����S���Ҍ����@�֖���ݒ�
     * @param jimutantoShozokuName �����S���Ҍ����@�֖�
     */
    public void setJimutantoShozokuName(String jimutantoShozokuName) {
        this.jimutantoShozokuName = jimutantoShozokuName;
    }

    /**
     * �����S���ҕ��ǔԍ����擾
     * @return String �����S���ҕ��ǔԍ�
     */
    public String getJimutantoBukyokuCd() {
        return jimutantoBukyokuCd;
    }

    /**
     * �����S���ҕ��ǔԍ���ݒ�
     * @param jimutantoBukyokuCd �����S���ҕ��ǔԍ�
     */
    public void setJimutantoBukyokuCd(String jimutantoBukyokuCd) {
        this.jimutantoBukyokuCd = jimutantoBukyokuCd;
    }

    /**
     * �����S���ҕ��ǖ����擾
     * @return String �����S���ҕ��ǖ�
     */
    public String getJimutantoBukyokuName() {
        return jimutantoBukyokuName;
    }

    /**
     * �����S���ҕ��ǖ���ݒ�
     * @param jimutantoBukyokuName �����S���ҕ��ǖ�
     */
    public void setJimutantoBukyokuName(String jimutantoBukyokuName) {
        this.jimutantoBukyokuName = jimutantoBukyokuName;
    }

    /**
     * �����S���ҐE���ԍ����擾
     * @return String �����S���ҐE���ԍ�
     */
    public String getJimutantoShokushuCd() {
        return jimutantoShokushuCd;
    }

    /**
     * �����S���ҐE���ԍ���ݒ�
     * @param jimutantoShokushuCd �����S���ҐE���ԍ�
     */
    public void setJimutantoShokushuCd(String jimutantoShokushuCd) {
        this.jimutantoShokushuCd = jimutantoShokushuCd;
    }

    /**
     * �����S���ҐE���i�a���j���擾
     * @return String �����S���ҐE���i�a���j
     */
    public String getJimutantoShokushuNameKanji() {
        return jimutantoShokushuNameKanji;
    }

    /**
     * �����S���ҐE���i�a���j��ݒ�
     * @param jimutantoShokushuNameKanji �����S���ҐE���i�a���j
     */
    public void setJimutantoShokushuNameKanji(String jimutantoShokushuNameKanji) {
        this.jimutantoShokushuNameKanji = jimutantoShokushuNameKanji;
    }

    /**
     * �����S���ҁi�X�֔ԍ��j���擾
     * @return String �����S���ҁi�X�֔ԍ��j
     */
    public String getJimutantoZip() {
        return jimutantoZip;
    }

    /**
     * �����S���ҁi�X�֔ԍ��j��ݒ�
     * @param jimutantoZip �����S���ҁi�X�֔ԍ��j
     */
    public void setJimutantoZip(String jimutantoZip) {
        this.jimutantoZip = jimutantoZip;
    }

    /**
     * �����S���ҁi�Z���j���擾
     * @return String �����S���ҁi�Z���j
     */
    public String getJimutantoAddress() {
        return jimutantoAddress;
    }

    /**
     * �����S���ҁi�Z���j��ݒ�
     * @param jimutantoAddress �����S���ҁi�Z���j
     */
    public void setJimutantoAddress(String jimutantoAddress) {
        this.jimutantoAddress = jimutantoAddress;
    }

    /**
     * �����S���ҁi�d�b�j���擾
     * @return String �����S���ҁi�d�b�j
     */
    public String getJimutantoTel() {
        return jimutantoTel;
    }

    /**
     * �����S���ҁi�d�b�j��ݒ�
     * @param jimutantoTel �����S���ҁi�d�b�j
     */
    public void setJimutantoTel(String jimutantoTel) {
        this.jimutantoTel = jimutantoTel;
    }

    /**
     * �����S���ҁiFAX�j���擾
     * @return String �����S���ҁiFAX�j
     */
    public String getJimutantoFax() {
        return jimutantoFax;
    }

    /**
     * �����S���ҁiFAX�j��ݒ�
     * @param jimutantoFax �����S���ҁiFAX�j
     */
    public void setJimutantoFax(String jimutantoFax) {
        this.jimutantoFax = jimutantoFax;
    }

    /**
     * �����S���ҁi���[���A�h���X�j���擾
     * @return String �����S���ҁi���[���A�h���X�j
     */
    public String getJimutantoEmail() {
        return jimutantoEmail;
    }

    /**
     * �����S���ҁi���[���A�h���X�j��ݒ�
     * @param jimutantoEmail �����S���ҁi���[���A�h���X�j
     */
    public void setJimutantoEmail(String jimutantoEmail) {
        this.jimutantoEmail = jimutantoEmail;
    }

    /**
     * �֘A����̌�����-����1���擾
     * @return String �֘A����̌�����-����1
     */
    public String getKanrenShimei1() {
        return kanrenShimei1;
    }

    /**
     * �֘A����̌�����-����1��ݒ�
     * @param kanrenShimei1 �֘A����̌�����-����1
     */
    public void setKanrenShimei1(String kanrenShimei1) {
        this.kanrenShimei1 = kanrenShimei1;
    }

    /**
     * �֘A����̌�����-���������@��1���擾
     * @return String �֘A����̌�����-���������@��1
     */
    public String getKanrenKikan1() {
        return kanrenKikan1;
    }

    /**
     * �֘A����̌�����-���������@��1��ݒ�
     * @param kanrenKikan1 �֘A����̌�����-���������@��1
     */
    public void setKanrenKikan1(String kanrenKikan1) {
        this.kanrenKikan1 = kanrenKikan1;
    }

    /**
     * �֘A����̌�����-����1���擾
     * @return String �֘A����̌�����-����1
     */
    public String getKanrenBukyoku1() {
        return kanrenBukyoku1;
    }

    /**
     * �֘A����̌�����-����1��ݒ�
     * @param kanrenBukyoku1 �֘A����̌�����-����1
     */
    public void setKanrenBukyoku1(String kanrenBukyoku1) {
        this.kanrenBukyoku1 = kanrenBukyoku1;
    }

    /**
     * �֘A����̌�����-�E��1���擾
     * @return String �֘A����̌�����-�E��1
     */
    public String getKanrenShoku1() {
        return kanrenShoku1;
    }

    /**
     * �֘A����̌�����-�E��1��ݒ�
     * @param kanrenShoku1 �֘A����̌�����-�E��1
     */
    public void setKanrenShoku1(String kanrenShoku1) {
        this.kanrenShoku1 = kanrenShoku1;
    }

    /**
     * �֘A����̌�����-��啪��1���擾
     * @return String �֘A����̌�����-��啪��1
     */
    public String getKanrenSenmon1() {
        return kanrenSenmon1;
    }

    /**
     * �֘A����̌�����-��啪��1��ݒ�
     * @param kanrenSenmon1 �֘A����̌�����-��啪��1
     */
    public void setKanrenSenmon1(String kanrenSenmon1) {
        this.kanrenSenmon1 = kanrenSenmon1;
    }

    /**
     * �֘A����̌�����-�Ζ���d�b�ԍ�1���擾
     * @return String �֘A����̌�����-�Ζ���d�b�ԍ�1
     */
    public String getKanrenTel1() {
        return kanrenTel1;
    }

    /**
     * �֘A����̌�����-�Ζ���d�b�ԍ�1��ݒ�
     * @param kanrenTel1 �֘A����̌�����-�Ζ���d�b�ԍ�1
     */
    public void setKanrenTel1(String kanrenTel1) {
        this.kanrenTel1 = kanrenTel1;
    }

    /**
     * �֘A����̌�����-����d�b�ԍ�1���擾
     * @return String �֘A����̌�����-����d�b�ԍ�1
     */
    public String getKanrenJitakutel1() {
        return kanrenJitakutel1;
    }

    /**
     * �֘A����̌�����-����d�b�ԍ�1��ݒ�
     * @param kanrenJitakutel1 �֘A����̌�����-����d�b�ԍ�1
     */
    public void setKanrenJitakutel1(String kanrenJitakutel1) {
        this.kanrenJitakutel1 = kanrenJitakutel1;
    }

    /**
     * �֘A����̌�����-����2���擾
     * @return String �֘A����̌�����-����2
     */
    public String getKanrenShimei2() {
        return kanrenShimei2;
    }

    /**
     * �֘A����̌�����-����2��ݒ�
     * @param kanrenShimei2 �֘A����̌�����-����2
     */
    public void setKanrenShimei2(String kanrenShimei2) {
        this.kanrenShimei2 = kanrenShimei2;
    }

    /**
     * �֘A����̌�����-���������@��2���擾
     * @return String �֘A����̌�����-���������@��2
     */
    public String getKanrenKikan2() {
        return kanrenKikan2;
    }

    /**
     * �֘A����̌�����-���������@��2��ݒ�
     * @param kanrenKikan2 �֘A����̌�����-���������@��2
     */
    public void setKanrenKikan2(String kanrenKikan2) {
        this.kanrenKikan2 = kanrenKikan2;
    }

    /**
     * �֘A����̌�����-����2���擾
     * @return String �֘A����̌�����-����2
     */
    public String getKanrenBukyoku2() {
        return kanrenBukyoku2;
    }

    /**
     * �֘A����̌�����-����2��ݒ�
     * @param kanrenBukyoku2 �֘A����̌�����-����2
     */
    public void setKanrenBukyoku2(String kanrenBukyoku2) {
        this.kanrenBukyoku2 = kanrenBukyoku2;
    }

    /**
     * �֘A����̌�����-�E��2���擾
     * @return String �֘A����̌�����-�E��2
     */
    public String getKanrenShoku2() {
        return kanrenShoku2;
    }

    /**
     * �֘A����̌�����-�E��2��ݒ�
     * @param kanrenShoku2 �֘A����̌�����-�E��2
     */
    public void setKanrenShoku2(String kanrenShoku2) {
        this.kanrenShoku2 = kanrenShoku2;
    }

    /**
     * �֘A����̌�����-��啪��2���擾
     * @return String �֘A����̌�����-��啪��2
     */
    public String getKanrenSenmon2() {
        return kanrenSenmon2;
    }

    /**
     * �֘A����̌�����-��啪��2��ݒ�
     * @param kanrenSenmon2 �֘A����̌�����-��啪��2
     */
    public void setKanrenSenmon2(String kanrenSenmon2) {
        this.kanrenSenmon2 = kanrenSenmon2;
    }

    /**
     * �֘A����̌�����-�Ζ���d�b�ԍ�2���擾
     * @return String �֘A����̌�����-�Ζ���d�b�ԍ�2
     */
    public String getKanrenTel2() {
        return kanrenTel2;
    }

    /**
     * �֘A����̌�����-�Ζ���d�b�ԍ�2��ݒ�
     * @param kanrenTel2 �֘A����̌�����-�Ζ���d�b�ԍ�2
     */
    public void setKanrenTel2(String kanrenTel2) {
        this.kanrenTel2 = kanrenTel2;
    }

    /**
     * �֘A����̌�����-����d�b�ԍ�2���擾
     * @return String �֘A����̌�����-����d�b�ԍ�2
     */
    public String getKanrenJitakutel2() {
        return kanrenJitakutel2;
    }

    /**
     * �֘A����̌�����-����d�b�ԍ�2��ݒ�
     * @param kanrenJitakutel2 �֘A����̌�����-����d�b�ԍ�2
     */
    public void setKanrenJitakutel2(String kanrenJitakutel2) {
        this.kanrenJitakutel2 = kanrenJitakutel2;
    }

    /**
     * �֘A����̌�����-����3���擾
     * @return String �֘A����̌�����-����3
     */
    public String getKanrenShimei3() {
        return kanrenShimei3;
    }

    /**
     * �֘A����̌�����-����3��ݒ�
     * @param kanrenShimei3 �֘A����̌�����-����3
     */
    public void setKanrenShimei3(String kanrenShimei3) {
        this.kanrenShimei3 = kanrenShimei3;
    }

    /**
     * �֘A����̌�����-���������@��3���擾
     * @return String �֘A����̌�����-���������@��3
     */
    public String getKanrenKikan3() {
        return kanrenKikan3;
    }

    /**
     * �֘A����̌�����-���������@��3��ݒ�
     * @param kanrenKikan3 �֘A����̌�����-���������@��3
     */
    public void setKanrenKikan3(String kanrenKikan3) {
        this.kanrenKikan3 = kanrenKikan3;
    }

    /**
     * �֘A����̌�����-����3���擾
     * @return String �֘A����̌�����-����3
     */
    public String getKanrenBukyoku3() {
        return kanrenBukyoku3;
    }

    /**
     * �֘A����̌�����-����3��ݒ�
     * @param kanrenBukyoku3 �֘A����̌�����-����3
     */
    public void setKanrenBukyoku3(String kanrenBukyoku3) {
        this.kanrenBukyoku3 = kanrenBukyoku3;
    }

    /**
     * �֘A����̌�����-�E��3���擾
     * @return String �֘A����̌�����-�E��3
     */
    public String getKanrenShoku3() {
        return kanrenShoku3;
    }

    /**
     * �֘A����̌�����-�E��3��ݒ�
     * @param kanrenShoku3 �֘A����̌�����-�E��3
     */
    public void setKanrenShoku3(String kanrenShoku3) {
        this.kanrenShoku3 = kanrenShoku3;
    }

    /**
     * �֘A����̌�����-��啪��3���擾
     * @return String �֘A����̌�����-��啪��3
     */
    public String getKanrenSenmon3() {
        return kanrenSenmon3;
    }

    /**
     * �֘A����̌�����-��啪��3��ݒ�
     * @param kanrenSenmon3 �֘A����̌�����-��啪��3
     */
    public void setKanrenSenmon3(String kanrenSenmon3) {
        this.kanrenSenmon3 = kanrenSenmon3;
    }

    /**
     * �֘A����̌�����-�Ζ���d�b�ԍ�3���擾
     * @return String �֘A����̌�����-�Ζ���d�b�ԍ�3
     */
    public String getKanrenTel3() {
        return kanrenTel3;
    }

    /**
     * �֘A����̌�����-�Ζ���d�b�ԍ�3��ݒ�
     * @param kanrenTel3 �֘A����̌�����-�Ζ���d�b�ԍ�3
     */
    public void setKanrenTel3(String kanrenTel3) {
        this.kanrenTel3 = kanrenTel3;
    }

    /**
     * �֘A����̌�����-����d�b�ԍ�3���擾
     * @return String �֘A����̌�����-����d�b�ԍ�3
     */
    public String getKanrenJitakutel3() {
        return kanrenJitakutel3;
    }

    /**
     * �֘A����̌�����-����d�b�ԍ�3��ݒ�
     * @param kanrenJitakutel3 �֘A����̌�����-����d�b�ԍ�3
     */
    public void setKanrenJitakutel3(String kanrenJitakutel3) {
        this.kanrenJitakutel3 = kanrenJitakutel3;
    }

    /**
     * �̈�v�揑�T�vPDF�̊i�[�p�X���擾
     * @return String �̈�v�揑�T�vPDF�̊i�[�p�X
     */
    public String getPdfPath() {
        return pdfPath;
    }

    /**
     * �̈�v�揑�T�vPDF�̊i�[�p�X��ݒ�
     * @param pdfPath �̈�v�揑�T�vPDF�̊i�[�p�X
     */
    public void setPdfPath(String pdfPath) {
        this.pdfPath = pdfPath;
    }

    /**
     * �\��PDF�i�[�p�X���擾
     * @return String �\��PDF�i�[�p�X
     */
    public String getHyoshiPdfPath() {
        return hyoshiPdfPath;
    }

    /**
     * �\��PDF�i�[�p�X��ݒ�
     * @param hyoshiPdfPath �\��PDF�i�[�p�X
     */
    public void setHyoshiPdfPath(String hyoshiPdfPath) {
        this.hyoshiPdfPath = hyoshiPdfPath;
    }

    /**
     * �󗝌��ʂ��擾
     * @return String �󗝌���
     */
    public String getJuriKekka() {
        return juriKekka;
    }

    /**
     * �󗝌��ʂ�ݒ�
     * @param juriKekka �󗝌���
     */
    public void setJuriKekka(String juriKekka) {
        this.juriKekka = juriKekka;
    }

    /**
     * �󗝌��ʔ��l���擾
     * @return String �󗝌��ʔ��l
     */
    public String getJuriBiko() {
        return juriBiko;
    }

    /**
     * �󗝌��ʔ��l��ݒ�
     * @param juriBiko �󗝌��ʔ��l
     */
    public void setJuriBiko(String juriBiko) {
        this.juriBiko = juriBiko;
    }

    /**
     * �̈�v�揑�i�T�v�j�\����ID���擾
     * @return String �̈�v�揑�i�T�v�j�\����ID
     */
    public String getRyoikiJokyoId() {
        return ryoikiJokyoId;
    }

    /**
     * �̈�v�揑�i�T�v�j�\����ID��ݒ�
     * @param ryoikiJokyoId �̈�v�揑�i�T�v�j�\����ID
     */
    public void setRyoikiJokyoId(String ryoikiJokyoId) {
        this.ryoikiJokyoId = ryoikiJokyoId;
    }

    /**
     * �ł��擾
     * @return String ��
     */
    public String getEdition() {
        return edition;
    }

    /**
     * �ł�ݒ�
     * @param edition ��
     */
    public void setEdition(String edition) {
        this.edition = edition;
    }

    /**
     * �m��t���O���擾
     * @return String �m��t���O
     */
    public String getRyoikikeikakushoKakuteiFlg() {
        return ryoikikeikakushoKakuteiFlg;
    }

    /**
     * �m��t���O��ݒ�
     * @param ryoikikeikakushoKakuteiFlg �m��t���O
     */
    public void setRyoikikeikakushoKakuteiFlg(String ryoikikeikakushoKakuteiFlg) {
        this.ryoikikeikakushoKakuteiFlg = ryoikikeikakushoKakuteiFlg;
    }

    /**
     * �����t���O���擾
     * @return String �����t���O
     */
    public String getCancelFlg() {
        return cancelFlg;
    }

    /**
     * �����t���O��ݒ�
     * @param cancelFlg �����t���O
     */
    public void setCancelFlg(String cancelFlg) {
        this.cancelFlg = cancelFlg;
    }

    /**
     * �폜�t���O���擾
     * @return String �폜�t���O
     */
    public String getDelFlg() {
        return delFlg;
    }

    /**
     * �폜�t���O��ݒ�
     * @param delFlg �폜�t���O
     */
    public void setDelFlg(String delFlg) {
        this.delFlg = delFlg;
    }

    /**
     * ���ƃR�[�h���擾
     * @return String ���ƃR�[�h
     */
    public String getJigyoCd() {
        return jigyoCd;
    }

    /**
     * ���ƃR�[�h��ݒ�
     * @param jigyoCd ���ƃR�[�h
     */
    public void setJigyoCd(String jigyoCd) {
        this.jigyoCd = jigyoCd;
    }

    /**
     * �\���󋵔z����擾
     * @return String[] �\���󋵔z��
     */
    public String[] getJokyoIds() {
        return jokyoIds;
    }

    /**
     * �\���󋵔z���ݒ�
     * @param jokyoIds �\���󋵔z��
     */
    public void setJokyoIds(String[] jokyoIds) {
        this.jokyoIds = jokyoIds;
    }

    /**
     * �̈�v�揑�i�T�v�j�\����ID�z����擾
     * @return String[] �̈�v�揑�i�T�v�j�\����ID�z��
     */
    public String[] getRyoikiJokyoIds() {
        return ryoikiJokyoIds;
    }

    /**
     * �̈�v�揑�i�T�v�j�\����ID�z���ݒ�
     * @param ryoikiJokyoIds �̈�v�揑�i�T�v�j�\����ID�z��
     */
    public void setRyoikiJokyoIds(String[] ryoikiJokyoIds) {
        this.ryoikiJokyoIds = ryoikiJokyoIds;
    }

    /**
     * ���匏�����擾
     * @return Returns the count.
     */
    public String getCount() {
        return count;
    }

    /**
     * ���匏����ݒ�
     * @param count The count to set.
     */
    public void setCount(String count) {
        this.count = count;
    }

    /**
     * ���ƔN�x�i����Q�P�^�j�j���擾
     * @return String ���ƔN�x�i����Q�P�^
     */
    public String getNendoSeireki() {
        return nendoSeireki;
    }

    /**
     * ���ƔN�x�i����Q�P�^�j��ݒ�
     * @param nendoSeireki ���ƔN�x�i����Q�P�^�j
     */
    public void setNendoSeireki(String nendoSeireki) {
        this.nendoSeireki = nendoSeireki;
    }

    /**
     * �����o��i���v)���擾.
     * @return String �����o��i���v)
     */
    public String getKenkyuTotal() {
        return kenkyuTotal;
    }

    /**
     * �����o��i���v)��ݒ�.
     * @param kenkyuTotal �����o��i���v)
     */
    public void setKenkyuTotal(String kenkyuTotal) {
        this.kenkyuTotal = kenkyuTotal;
    }

    /**
     * �̈�v�揑�i�T�v�j����󋵂��擾
     * @return String �̈�v�揑�i�T�v�j�����
     */
    public String getRyoikiJokyoName() {
        return ryoikiJokyoName;
    }

    /**
     * �̈�v�揑�i�T�v�j����󋵂�ݒ�
     * @param ryoikiJokyoName �̈�v�揑�i�T�v�j�����
     */
    public void setRyoikiJokyoName(String ryoikiJokyoName) {
        this.ryoikiJokyoName = ryoikiJokyoName;
    }

    /**
     * �����o��\���擾
     * @return List �����o��\
     */
    public List getKenkyuKeihiList() {
        return kenkyuKeihiList;
    }

    /**
     * �����o��\��ݒ�
     * @param kenkyuKeihiList �����o��\
     */
    public void setKenkyuKeihiList(List kenkyuKeihiList) {
        this.kenkyuKeihiList = kenkyuKeihiList;
    }

    /**
     * �����g�D�\���擾
     * @return List �����g�D�\
     */
    public List getKenkyuSosikiList() {
        return KenkyuSosikiList;
    }

    /**
     * �����g�D�\��ݒ�
     * @param kenkyuSosikiList �����g�D�\
     */
    public void setKenkyuSosikiList(List kenkyuSosikiList) {
        KenkyuSosikiList = kenkyuSosikiList;
    }

    /**
     * �Y�t�t�@�C�������擾
     * @return TenpuFileInfo[] �Y�t�t�@�C�����
     */
    public TenpuFileInfo[] getTenpuFileInfos() {
        return tenpuFileInfos;
    }

    /**
     * �Y�t�t�@�C������ݒ�
     * @param tenpuFileInfos �Y�t�t�@�C�����
     */
    public void setTenpuFileInfos(TenpuFileInfo[] tenpuFileInfos) {
        this.tenpuFileInfos = tenpuFileInfos;
    }

    /**
     * �Y�t�t�@�[�����X�g��I������̒l���擾
     * @return String �Y�t�t�@�[�����X�g��I������̒l
     */
    public String getTenpuFileFlg() {
        return tenpuFileFlg;
    }

    /**
     * �Y�t�t�@�[�����X�g��I������̒l��ݒ�
     * @param tenpuFileFlg �Y�t�t�@�[�����X�g��I������̒l
     */
    public void setTenpuFileFlg(String tenpuFileFlg) {
        this.tenpuFileFlg = tenpuFileFlg;
    }

	/**
	 * @return addTitele ��߂��܂��B
	 */
	public String getAddTitele() {
		return addTitele;
	}

	/**
	 * @param addTitele �ݒ肷�� addTitele�B
	 */
	public void setAddTitele(String addTitele) {
		this.addTitele = addTitele;
	}
//	ADD�@START 2007-07-25 BIS ������
//  ���͂��������g�D�ɃG���[������ꍇ�A�u�̈�v�揑���́v��ʂŃG���[���b�Z�[�W��\�����A�u�����o��\�v��ʂŃG���[�ɂȂ鍀�ڂ̔w�i�F��ύX����B	
	/**
	 * @return Returns the errorsMap.
	 */
	public HashMap getErrorsMap() {
		return errorsMap;
	}

	/**
	 * @param errorsMap The errorsMap to set.
	 */
	public void setErrorsMap(HashMap errorsMap) {
		this.errorsMap = errorsMap;
	}
//ADD�@END 2007-07-25 BIS ������

}