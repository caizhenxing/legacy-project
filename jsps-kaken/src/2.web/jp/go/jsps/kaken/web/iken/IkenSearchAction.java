/*======================================================================
 *    SYSTEM      : ���{�w�p�U����d�q�\���V�X�e��(JSPS)
 *    Source name : IkenSearchAction.java
 *    Description : �ӌ���񌟍��A�N�V�����N���X�B
 *
 *    Author      : Admin
 *    Date        : 2005/05/23
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2005/05/23    1.0         Xiang Emin     �V�K
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.iken;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.IkenSearchInfo;
import jp.go.jsps.kaken.util.Page;
import jp.go.jsps.kaken.web.common.IConstants;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

/**
 * �ӌ���񌟍��A�N�V�����N���X�B
 * ID RCSfile="$RCSfile: IkenSearchAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:18 $"
 */
public class IkenSearchAction extends BaseAction {

	/* (Javadoc �Ȃ�)
	 * @see jp.go.jsps.kaken.web.struts.BaseAction#doMainProcessing(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, jp.go.jsps.kaken.web.common.UserContainer)
	 */
	public ActionForward doMainProcessing(
			ActionMapping mapping,
			ActionForm form, 
			HttpServletRequest request,
			HttpServletResponse response, 
			UserContainer container) throws ApplicationException {

		//-----ActionErrors�̐錾�i��^�����j-----
		ActionErrors errors = new ActionErrors();
		
		//���������t�H�[���̎擾
		IkenSearchForm searchForm = (IkenSearchForm)form;
		
		// VO�Ɍ����������Z�b�g����B
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
			//��ʂ�01����1�֕ύX������
			String month = searchForm.getSakuseiDateFromMonth();
			if (month.length() == 1){
				month = "0" + month;
			}
			String day = searchForm.getSakuseiDateFromDay();
			if (day.length() == 1){
				day = "0" + day;
			}
			
			searchInfo.setSakuseiDateFrom(								
							searchForm.getSakuseiDateFromYear() + "/" +
//							searchForm.getSakuseiDateFromMonth() + "/" +
//							searchForm.getSakuseiDateFromDay()
							month + "/" + day
							);
		}
		//�쐬���i�I�����j
		if(!searchForm.getSakuseiDateToYear().equals("")){
			//��ʂ�01����1�֕ύX������
			String month = searchForm.getSakuseiDateToMonth();
			if (month.length() == 1){
				month = "0" + month;
			}
			String day = searchForm.getSakuseiDateToDay();
			if (day.length() == 1){
				day = "0" + day;
			}

			searchInfo.setSakuseiDateTo(									
							searchForm.getSakuseiDateToYear() + "/" +
//							searchForm.getSakuseiDateToMonth()+ "/" +
//							searchForm.getSakuseiDateToDay()
							month + "/" + day
							);
		}
		
		//�\������
		searchInfo.setDispmode( searchForm.getDispmode() );

		//�y�[�W����
		searchInfo.setPageSize(searchForm.getPageSize());
		searchInfo.setMaxSize(searchForm.getMaxSize());
		searchInfo.setStartPosition(searchForm.getStartPosition());
	
		//�������s
		Page result =
				getSystemServise(
					IServiceName.IKEN_MAINTENANCE_SERVICE).searchIken(searchInfo);

		//�o�^���ʂ����N�G�X�g�����ɃZ�b�g
		request.setAttribute(IConstants.RESULT_INFO,result);

		//-----��ʑJ�ځi��^�����j-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}

		return forwardSuccess(mapping);
	}

}
