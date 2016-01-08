/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2003/11/14
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.gyomu.shinsaJokyo;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.role.UserRole;
import jp.go.jsps.kaken.model.vo.GyomutantoInfo;
import jp.go.jsps.kaken.model.vo.ShinsaJokyoSearchInfo;
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
 * Date="$Date: 2007/06/28 02:07:16 $"
 */
public class CsvOutAction extends BaseAction {
	
	/**
	 * CSV�t�@�C�����̐ړ����B
	 */
	//2006.12.08 iso �����ꗗ�ƃt�@�C�����𕪂���悤�ύX
//	public static final String filename = "SHINSAJOKYO";

	/* (�� Javadoc)
	 * @see jp.go.jsps.kaken.web.struts.BaseAction#doMainProcessing(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, jp.go.jsps.kaken.web.common.UserContainer)
	 */
	public ActionForward doMainProcessing(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response,
			UserContainer container)
			throws ApplicationException {
			
		//-----ActionErrors�̐錾�i��^�����j-----
		ActionErrors errors = new ActionErrors();

		//���������̎擾
		ShinsaJokyoSearchForm searchForm = (ShinsaJokyoSearchForm)form;
			
		//-------�� VO�Ƀf�[�^���Z�b�g����B
		ShinsaJokyoSearchInfo searchInfo = new ShinsaJokyoSearchInfo();

//		searchInfo.setValues(searchForm.getValues());
		searchInfo.setShinsainNo(searchForm.getShinsainNo());
		searchInfo.setNameKanjiSei(searchForm.getNameKanjiSei());
		searchInfo.setNameKanjiMei(searchForm.getNameKanjiMei());
		searchInfo.setNameKanaSei(searchForm.getNameKanaSei());
		searchInfo.setNameKanaMei(searchForm.getNameKanaMei());
		searchInfo.setShozokuCd(searchForm.getShozokuCd());
		searchInfo.setNendo(searchForm.getNendo());
		searchInfo.setKaisu(searchForm.getKaisu());
		searchInfo.setKeiName(searchForm.getKeiName());
		searchInfo.setRigaiJokyo(searchForm.getRigaiJokyo());			//���Q�֌W���͊����� 2007/5/8
		searchInfo.setShinsaJokyo(searchForm.getShinsaJokyo());
		searchInfo.setLoginDate(searchForm.getLoginDate());				//�ŏI���O�C������ǉ�	2005/10/25
		searchInfo.setRigaiKankeisha(searchForm.getRigaiKankeisha());	//���Q�֌W�Ғǉ�		2005/10/25
		searchInfo.setSeiriNo(searchForm.getSeiriNo());					//�����ԍ���ǉ�		2005/11/2

		//2005.11.03 iso �\��������ǉ�
		searchInfo.setHyojiHoshikiShinsaJokyo(searchForm.getHyojiHoshikiShinsaJokyo());

		//2005.05.18 iso �A�N�Z�X�Ǘ������Ƌ敪������CD�ɕύX
		//�`�F�b�N�{�b�N�X�̎��ƃR�[�h���X�g���Z�b�g
		if(!searchForm.getValues().isEmpty()){
			searchInfo.setValues(searchForm.getValues());	
		}else{
			//����U��̌����ł́A�����ŐR���S���敪���Z�b�g���Ă��邪�A
			//�R���󋵊Ǘ��ł́AShinsaJokyoKakunin�N���X�ōs���Ă���B
			//���삪�Ⴄ�̂Œ��ӁB
			if(container.getUserInfo().getRole().equals(UserRole.GYOMUTANTO)){
				GyomutantoInfo gyomutantoInfo = container.getUserInfo().getGyomutantoInfo(); 
				searchInfo.setValues(new ArrayList(gyomutantoInfo.getTantoJigyoCd()));
			}
		}

		//�������s
		List result =
			getSystemServise(
				IServiceName.SHINSAJOKYO_KAKUNIN_SERVICE).searchCsvData(
				container.getUserInfo(),
				searchInfo);
		
		//2006.12.08 iso �����ꗗ�ƃt�@�C�����𕪂���悤�ύX
		String filename = "SHINSAJOKYO";	//�ꉞ�f�t�H���g�t�@�C������SHINSAJOKYO�ɂ��Ă���
		if("1".equals(searchForm.getHyojiHoshikiShinsaJokyo())) {
			filename = "SHINSAJOKYO";
		} else if("2".equals(searchForm.getHyojiHoshikiShinsaJokyo())) {
			filename = "SHINSAKENSU";
		}
		
		
		DownloadFileUtil.downloadCSV(request, response, result, filename);

		//-----��ʑJ�ځi��^�����j-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		return forwardSuccess(mapping);
	}

}
