/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : 張楠
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
	/** 研究種目名 */
	private String     jigyoName;

	/** 仮領域番号 */
	private String     kariryoikiNo;
	
	/** 領域代表者氏名-姓 */
	private String     nameKanjiSei;
	
	/** 領域代表者氏名-名*/
	private String     nameKanjiMei;

	/** 所属研究機関名ー番号 */
	private String     shozokuCd;
	
	/** 所属研究機関名ー名称 */
	private String     shozokuName;
		
	/** 整理番号 */
	private String     uketukeNo;
	
	/** 領域名 */
	private String     ryoikiName;	
	
	/** 部局名 */
	private String     bukyokuName;	
	
	/** 職名 */
	private String     shokushuNameKanji;		
	
	/** 版数 */
	private String     edition;			
	
	/** 領域計画書確認 */
	private String     pdfPath;	
	
	/** 受理日 */
	private Date     jyuriDate;	
	
	/** 応募状況 */
	private String     ryoikiJokyoId;	
		
	/** 応募状況選択リスト */
	private List jokyoList = new ArrayList();

	/**
	 * コンストラクタ。
	 */
	public RyoikiGaiyoSearchForm() {
		super();
		init();
	}


	//---------------------------------------------------------------------
	// Public methods
	//---------------------------------------------------------------------

	/* 
	 * 初期化処理。
	 * (非 Javadoc)
	 * @see org.apache.struts.action.ActionForm#reset(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
	}
	
	/**
	 * 初期化処理
	 */
	public void init() {
		jigyoName= "";	//デフォルトは、「1:研究種目毎に表示」
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
	 * 入力チェック。
	 * (非 Javadoc)
	 * @see org.apache.struts.action.ActionForm#validate(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
	 */
	public ActionErrors validate(
		ActionMapping mapping,
		HttpServletRequest request) {

		//定型処理----- 
		ActionErrors errors = super.validate(mapping, request);

		//---------------------------------------------
		// 基本的なチェック(必須、形式等）はValidatorを使用する。
		//---------------------------------------------

		//基本入力チェックここまで
		if (!errors.isEmpty()) {
			return errors;
		}
		//定型処理----- 

		//追加処理----- 

		//---------------------------------------------
		//組み合わせチェック	
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