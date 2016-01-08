/*======================================================================
 *    SYSTEM      : ���{�w�p�U����d�q�\���V�X�e���i�Ȋw������⏕���j
 *    Source name : RyoikiKeikakushoPk.java
 *    Description : �̈�v�揑�i�T�v�j���Ǘ��e�[�u����L�[
 *
 *    Author      : DIS.dyh
 *    Date        : 2006/06/14
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2006/06/14    V1.0        DIS.dyh        �V�K�쐬
 *====================================================================== 
 */
package jp.go.jsps.kaken.model.vo;

/**
 * �̈�v�揑�i�T�v�j���Ǘ��e�[�u����L�[
 * ID RCSfile="$RCSfile: RyoikiKeikakushoPk.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:11 $"
 */
public class RyoikiKeikakushoPk extends ValueObject{

    //---------------------------------------------------------------------
    // Static data
    //---------------------------------------------------------------------
    private static final long serialVersionUID = -4155370705945945554L;

    //---------------------------------------------------------------------
    // Instance data
    //---------------------------------------------------------------------

    /* �V�X�e����t�ԍ� */
    private String ryoikiSystemNo;

    //---------------------------------------------------------------------
    // Constructors
    //---------------------------------------------------------------------
    /**
     * �R���X�g���N�^�B
     */
    public RyoikiKeikakushoPk() {
        super();
    }

    /**
     * �R���X�g���N�^�B
     * @param ryoikiSystemNo �V�X�e����t�ԍ�
     */
    public RyoikiKeikakushoPk(String ryoikiSystemNo){
        super();
        this.ryoikiSystemNo = ryoikiSystemNo;
    }
    
    //--------------------------------------------------------------------
    // Properties
    //---------------------------------------------------------------------

    /**
     * �V�X�e����t�ԍ����擾
     * @return String �V�X�e����t�ԍ�
     */
    public String getRyoikiSystemNo() {
        return ryoikiSystemNo;
    }

    /**
     * �V�X�e����t�ԍ���ݒ�
     * @param ryoikiSystemNo �V�X�e����t�ԍ�
     */
    public void setRyoikiSystemNo(String ryoikiSystemNo) {
        this.ryoikiSystemNo = ryoikiSystemNo;
    }
}