/*======================================================================
 *    SYSTEM      : ���{�w�p�U����d�q�\���V�X�e��(JSPS)
 *    Source name : CsvOutAction.java
 *    Description : �ӌ����CSV�o�̓A�N�V�����N���X�B
 *
 *    Author      : Admin
 *    Date        : 2005/05/25
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2005/05/25    1.0         Xiang Emin     �V�K
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.iken;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
//import jp.go.jsps.kaken.model.exceptions.SystemException;
import jp.go.jsps.kaken.model.vo.IkenSearchInfo;
import jp.go.jsps.kaken.util.DownloadFileUtil;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * CSV�o�̓A�N�V�����N���X�B
 * 
 * ID RCSfile="$RCSfile: CsvOutAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:18 $"
 */
public class CsvOutAction extends BaseAction {
	
	/**
	 * CSV�t�@�C�����̐ړ����B
	 */
	public static final String filename = "IKENINFO";

	/* (�� Javadoc)
	 * @see jp.go.jsps.kaken.web.struts.BaseAction#doMainProcessing(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, jp.go.jsps.kaken.web.common.UserContainer)
	 */
	public ActionForward doMainProcessing(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response,
		UserContainer container)
		 throws ApplicationException
		{
		
		if (log.isDebugEnabled()){
			log.debug("�ӌ����CSV�o�́I�I�I");
		}
		
		//-----ActionErrors�̐錾�i��^�����j-----
		ActionErrors errors = new ActionErrors();

		//���������̎擾
		IkenSearchForm searchForm = (IkenSearchForm)form;
			
		//-------�� VO�Ƀf�[�^���Z�b�g����B
		IkenSearchInfo searchInfo = new IkenSearchInfo();
			
		//�\���҃t���O
		searchInfo.setShinseisya( searchForm.getShinseisya() );
		
		//�����@�֒S���҃t���O
		searchInfo.setSyozoku( searchForm.getSyozoku() );

		//���ǒS���҃t���O
		searchInfo.setBukyoku( searchForm.getBukyoku() );
		
		//�R�����t���O
		searchInfo.setShinsyain( searchForm.getShinsyain() );
		
		//�쐬���i�J�n���j
		if(!searchForm.getSakuseiDateFromYear().equals("")){	
			searchInfo.setSakuseiDateFrom(								
							searchForm.getSakuseiDateFromYear() + "/" +
							searchForm.getSakuseiDateFromMonth() + "/" +
							searchForm.getSakuseiDateFromDay()
							);
		}
		//�쐬���i�I�����j
		if(!searchForm.getSakuseiDateToYear().equals("")){
			searchInfo.setSakuseiDateTo(									
							searchForm.getSakuseiDateToYear() + "/" +
							searchForm.getSakuseiDateToMonth()+ "/" +
							searchForm.getSakuseiDateToDay()
							);
		}
		
		//�\������
		searchInfo.setDispmode( searchForm.getDispmode() );
	
		//�������s
		List result =
			getSystemServise(
				IServiceName.IKEN_MAINTENANCE_SERVICE).searchCsvData(searchInfo);
		
		DownloadFileUtil.downloadCSV(request, response, result, filename);

		//-----��ʑJ�ځi��^�����j-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		return forwardSuccess(mapping);
	}

}
