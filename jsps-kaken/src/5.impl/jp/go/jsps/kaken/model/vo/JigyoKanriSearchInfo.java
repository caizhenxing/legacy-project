/*======================================================================
 *    SYSTEM      : ���{�w�p�U����d�q�\���V�X�e���i�Ȋw������⏕���j
 *    Source name : JigyoKanriSearchInfo.java
 *    Description : ���ƊǗ���񌟍�������ێ�����N���X
 *
 *    Author      : Admin
 *    Date        : 2003/07/16
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2003/07/16    v1.0        Admin          �V�K�쐬
 *====================================================================== 
 */
package jp.go.jsps.kaken.model.vo;

/**
 * ���ƊǗ���񌟍�������ێ�����N���X�B
 * 
 * ID RCSfile="$RCSfile: JigyoKanriSearchInfo.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:13 $"
 */
public class JigyoKanriSearchInfo extends SearchInfo{

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------
	//static final long serialVersionUID = -5037219847097453179L;
	
	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/** ����ID */
	private JigyoKanriPk[]    jigyoPks;
	
	/** ���Ƌ敪 */
	private String[]    jigyoKubun;

    /** ���ƃR�[�h(�������A�J���}��؂�) */
    private String    jigyoCds;
	//�E�E�E�E�E�E�E�E�E�E

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

    /**
	 * �R���X�g���N�^�B
	 */
	public JigyoKanriSearchInfo() {
		super();
	}

	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------
	/**
     * ����ID���擾
	 * @return JigyoKanriPk[] ����ID
	 */
	public JigyoKanriPk[] getJigyoPks() {
		return jigyoPks;
	}

	/**
     * ��ݒ�
	 * @param pks
	 */
	public void setJigyoPks(JigyoKanriPk[] pks) {
		jigyoPks = pks;
	}

    /**
     * ���Ƌ敪���擾
     * @return String[] ���Ƌ敪
     */
	public String[] getJigyoKubun() {
		return jigyoKubun;
	}

    /**
     * ���Ƌ敪��ݒ�
     * @param jigyoKubun ���Ƌ敪
     */
	public void setJigyoKubun(String[] jigyoKubun) {
		this.jigyoKubun = jigyoKubun;
	}

    /**
     * ���ƃR�[�h(�������A�J���}��؂�)���擾
     * @return String ���ƃR�[�h(�������A�J���}��؂�)
     */
    public String getJigyoCds() {
        return jigyoCds;
    }

    /**
     * ���ƃR�[�h(�������A�J���}��؂�)��ݒ�
     * @param jigyoCds ���ƃR�[�h(�������A�J���}��؂�)
     */
    public void setJigyoCds(String jigyoCds) {
        this.jigyoCds = jigyoCds;
    }
}