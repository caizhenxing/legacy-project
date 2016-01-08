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
package jp.go.jsps.kaken.web.system.shozokuKanri;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.ISystemServise;
import jp.go.jsps.kaken.model.common.ApplicationSettings;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.common.ISettingKeys;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.ShozokuInfo;
import jp.go.jsps.kaken.util.DownloadFileUtil;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * �ؖ������s�pCSV�o�̓A�N�V�����N���X�B
 * 
 * ID RCSfile="$RCSfile: ShomeiCsvOutAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:02 $"
 */
public class ShomeiCsvOutAction extends BaseAction {
	
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
			
		//-----ActionErrors�̐錾�i��^�����j-----
		ActionErrors errors = new ActionErrors();
			

		ShozokuInfo csvhInfo = container.getShozokuInfo();
		
		//�ݒ�t�@�C������CSV�Œ�f�[�^���擾
		String profileName = ApplicationSettings.getString(ISettingKeys.PROFILE_NAME);			//profile name
		String subjectDn = ApplicationSettings.getString(ISettingKeys.SUBJECT_DN);				//subject DN
		String subjectAltName = ApplicationSettings.getString(ISettingKeys.SUBJECT_ALT_NAME);	//subjectAltName
		String pubkeyAlgo = ApplicationSettings.getString(ISettingKeys.PUBKEY_ALGO);			//pubkey algo
		String keyLength = ApplicationSettings.getString(ISettingKeys.KEY_LENGTH);				//key length
		String p12Flag = ApplicationSettings.getString(ISettingKeys.P12_FLAG);					//p12 flag
				
		//-------CSV�f�[�^�����X�g�Ɋi�[
		List line = new ArrayList();
		line.add("\"" +profileName + "\"");				//profile name
		//line.add(csvhInfo.getShozokuCd());				//�����@�֖��i�R�[�h�j
		line.add(csvhInfo.getShozokuTantoId().substring(0, 7));	////�����@�֖��i�R�[�h�j+ �A��
		line.add("\"" + subjectDn + csvhInfo.getShozokuNameEigo() + csvhInfo.getShozokuTantoId().substring(0, 7) + "\"");	//subject DN,�����@�֖��i�p���j
		line.add("\"" + subjectAltName + "\"");			//subjectAltName
		line.add(pubkeyAlgo);							//pubkey algo
		line.add(keyLength);							//key length	
		line.add(p12Flag);								//p12 flag
		line.add("\"" + csvhInfo.getPassword()+ "\"");	//�p�X���[�h
		
		//-------CSV�f�[�^���X�g��1�s�ǉ�
		List csvList = new ArrayList();
		//csvList.add(line);
		
		//2005/05/31�@�ǉ��@��������----------------------------
		//���R�@CSV�f�[�^�ɕ��ǒS���ҏ���ǉ�
		ISystemServise servise = getSystemServise(
								IServiceName.BUKYOKUTANTO_MAINTENANCE_SERVICE);
		csvList = servise.getShomeiCsvData(container.getUserInfo(),csvhInfo, line);
		
		//�ǉ��@�����܂�----------------------------------------
		
		//-------�t�@�C�������擾		
		String fileName = 	csvhInfo.getShozokuCd();	//�����@�֖��i�R�[�h�j	
		
		//-------�t�@�C�����_�E�����[�h�i�G���N�H�[�g���Ȃ��j
		DownloadFileUtil.downloadCSV(request, response, csvList, fileName, false, false);	

		//-----��ʑJ�ځi��^�����j-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		return forwardSuccess(mapping);
	}

}
