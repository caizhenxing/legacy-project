/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : ����
 *    Date        : 2007/6/29
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.system.ryoikiKeikaku;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jp.go.jsps.kaken.web.struts.BaseSearchForm;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

/**
 * ID RCSfile="$RCSfile: RyoikiGaiyoSearchForm.java,v $"
 * Revision="$Revision: 1.2 $"
 * Date="$Date: 2007/07/03 07:24:04 $"
 */
public class RyoikiGaiyoSearchForm extends BaseSearchForm {
	

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------
	/** ������ږ� */
	private String     jigyoName;

	/** ���̈�ԍ� */
	private String     kariryoikiNo;
	
	/** �̈��\�Ҏ���-�� */
	private String     nameKanjiSei;
	
	/** �̈��\�Ҏ���-��*/
	private String     nameKanjiMei;

	/** ���������@�֖��[�ԍ� */
	private String     shozokuCd;
	
	/** ���������@�֖��[���� */
	private String     shozokuName;
		
	/** �����ԍ� */
	private String     uketukeNo;
	
	/** �̈於 */
	private String     ryoikiName;	
	
	/** ���ǖ� */
	private String     bukyokuName;	
	
	/** �E�� */
	private String     shokushuNameKanji;		
	
	/** �Ő� */
	private String     edition;			
	
	/** �̈�v�揑�m�F */
	private String     pdfPath;	
	
	/** �󗝓� */
	private Date     jyuriDate;	
	
	/** ����� */
	private String     ryoikiJokyoId;	
		
	/** ����󋵑I�����X�g */
	private List jokyoList = new ArrayList();

	/**
	 * �R���X�g���N�^�B
	 */
	public RyoikiGaiyoSearchForm() {
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
	}
	
	/**
	 * ����������
	 */
	public void init() {
		jigyoName= "";	//�f�t�H���g�́A�u1:������ږ��ɕ\���v
		kariryoikiNo= "";
		nameKanjiSei= "";
		nameKanjiMei= "";
		shozokuCd= "";
		shozokuName= "";
		ryoikiJokyoId= "";
		bukyokuName= "";
		shokushuNameKanji= "";
		edition= "";
		pdfPath= "";
		ryoikiJokyoId= "";
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

		//��{���̓`�F�b�N�����܂�
		if (!errors.isEmpty()) {
			return errors;
		}
		//��^����----- 

		//�ǉ�����----- 

		//---------------------------------------------
		//�g�ݍ��킹�`�F�b�N	
		//---------------------------------------------

		return errors;
	}
	
	/**
	 * @return Returns the jigyoName.
	 */
	public String getJigyoName() {
		return jigyoName;
	}

	/**
	 * @param jigyoName The jigyoName to set.
	 */
	public void setJigyoName(String jigyoName) {
		this.jigyoName = jigyoName;
	}

	/**
	 * @return Returns the jokyoList.
	 */
	public List getJokyoList() {
		return jokyoList;
	}

	/**
	 * @param jokyoList The jokyoList to set.
	 */
	public void setJokyoList(List jokyoList) {
		this.jokyoList = jokyoList;
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

	/**
	 * @return Returns the nameKanjiMei.
	 */
	public String getNameKanjiMei() {
		return nameKanjiMei;
	}

	/**
	 * @param nameKanjiMei The nameKanjiMei to set.
	 */
	public void setNameKanjiMei(String nameKanjiMei) {
		this.nameKanjiMei = nameKanjiMei;
	}

	/**
	 * @return Returns the nameKanjiSei.
	 */
	public String getNameKanjiSei() {
		return nameKanjiSei;
	}

	/**
	 * @param nameKanjiSei The nameKanjiSei to set.
	 */
	public void setNameKanjiSei(String nameKanjiSei) {
		this.nameKanjiSei = nameKanjiSei;
	}

	/**
	 * @return Returns the ryoikiJokyoId.
	 */
	public String getRyoikiJokyoId() {
		return ryoikiJokyoId;
	}

	/**
	 * @param ryoikiJokyoId The ryoikiJokyoId to set.
	 */
	public void setRyoikiJokyoId(String ryoikiJokyoId) {
		this.ryoikiJokyoId = ryoikiJokyoId;
	}

	/**
	 * @return Returns the shozokuCd.
	 */
	public String getShozokuCd() {
		return shozokuCd;
	}

	/**
	 * @param shozokuCd The shozokuCd to set.
	 */
	public void setShozokuCd(String shozokuCd) {
		this.shozokuCd = shozokuCd;
	}

	/**
	 * @return Returns the shozokuName.
	 */
	public String getShozokuName() {
		return shozokuName;
	}

	/**
	 * @param shozokuName The shozokuName to set.
	 */
	public void setShozokuName(String shozokuName) {
		this.shozokuName = shozokuName;
	}

	/**
	 * @return Returns the bukyokuName.
	 */
	public String getBukyokuName() {
		return bukyokuName;
	}

	/**
	 * @param bukyokuName The bukyokuName to set.
	 */
	public void setBukyokuName(String bukyokuName) {
		this.bukyokuName = bukyokuName;
	}

	/**
	 * @return Returns the edition.
	 */
	public String getEdition() {
		return edition;
	}

	/**
	 * @param edition The edition to set.
	 */
	public void setEdition(String edition) {
		this.edition = edition;
	}

	/**
	 * @return Returns the jyuriDate.
	 */
	public Date getJyuriDate() {
		return jyuriDate;
	}

	/**
	 * @param jyuriDate The jyuriDate to set.
	 */
	public void setJyuriDate(Date jyuriDate) {
		this.jyuriDate = jyuriDate;
	}

	/**
	 * @return Returns the pdfPath.
	 */
	public String getPdfPath() {
		return pdfPath;
	}

	/**
	 * @param pdfPath The pdfPath to set.
	 */
	public void setPdfPath(String pdfPath) {
		this.pdfPath = pdfPath;
	}

	/**
	 * @return Returns the ryoikiName.
	 */
	public String getRyoikiName() {
		return ryoikiName;
	}

	/**
	 * @param ryoikiName The ryoikiName to set.
	 */
	public void setRyoikiName(String ryoikiName) {
		this.ryoikiName = ryoikiName;
	}

	/**
	 * @return Returns the shokushuNameKanji.
	 */
	public String getShokushuNameKanji() {
		return shokushuNameKanji;
	}

	/**
	 * @param shokushuNameKanji The shokushuNameKanji to set.
	 */
	public void setShokushuNameKanji(String shokushuNameKanji) {
		this.shokushuNameKanji = shokushuNameKanji;
	}

	/**
	 * @return Returns the uketukeNo.
	 */
	public String getUketukeNo() {
		return uketukeNo;
	}

	/**
	 * @param uketukeNo The uketukeNo to set.
	 */
	public void setUketukeNo(String uketukeNo) {
		this.uketukeNo = uketukeNo;
	}
		

	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------

}