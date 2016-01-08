/*======================================================================
 *    SYSTEM      : ���{�w�p�U����d�q�\���V�X�e���i�Ȋw������⏕���j
 *    Source name : GetKeizokuKadaiInfoAction.java
 *    Description : �\���������f�[�^�x�[�X�ɓo�^����
 *
 *    Author      : ���u��
 *    Date        : 2007-07-10
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2007-07-10    V1.0		���u��			�V�K�쐬
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.shinsei;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import jp.go.jsps.kaken.model.ISystemServise;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.dao.exceptions.DataAccessException;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.vo.KeizokuInfo;
import jp.go.jsps.kaken.model.vo.KeizokuPk;
import jp.go.jsps.kaken.model.vo.ShinseiDataInfo;
import jp.go.jsps.kaken.model.vo.shinsei.KenkyuKeihiInfo;
import jp.go.jsps.kaken.model.vo.shinsei.KenkyuKeihiSoukeiInfo;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

/**
 * �p���ۑ���擾�A�N�V�����N���X�B
 * 
 * ID RCSfile="$RCSfile: GetKeizokuKadaiInfoAction.java,v $"
 * Revision="$Revision: 1.9 $"
 * Date="$Date: 2007/07/26 09:52:22 $"
 */
public class GetKeizokuKadaiInfoAction extends BaseAction {

	/* (non-Javadoc)
	 * @see jp.go.jsps.kaken.web.struts.BaseAction#doMainProcessing(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, jp.go.jsps.kaken.web.common.UserContainer)
	 */
	public ActionForward doMainProcessing(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response, UserContainer container)
			throws ApplicationException {
		
		//-----ActionErrors�̐錾�i��^�����j-----
		ActionErrors errors = new ActionErrors();

		//------�L�����Z����		
		if (isCancelled(request)) {
			return forwardCancel(mapping);
		}

		//-----�T�[�o�T�[�r�X�̌Ăяo���i�p���ۑ���擾�j-----
		ShinseiForm shinseiForm = (ShinseiForm)super.getFormBean(mapping, request);
		ShinseiDataInfo shinseiInfo = shinseiForm.getShinseiDataInfo();
		ISystemServise service = getSystemServise(
				IServiceName.SHINSEI_MAINTENANCE_SERVICE);
		//����ID�ƌp�����̌����ۑ�ԍ����擾
		KeizokuInfo keizokuInfo = null;
		try {
			keizokuInfo = service.getKenkyukadaiInfo(container.getUserInfo(),
					shinseiInfo.getDaihyouInfo().getKenkyuNo(), shinseiInfo
							.getKadaiNoKeizoku());
		} catch (NoDataFoundException e) {
    		errors.add("shinseiForm.kaidaiNoErr", new ActionError("errors.9039"));
        } catch (DataAccessException e) {
        	errors.add("shinseiForm.kaidaiNoErr", new ActionError("errors.9040"));
        } catch (Exception e) {
        	errors.add("shinseiForm.kaidaiNoErr", new ActionError("errors.9040"));
        }
		
		//-----Keep data to form
        long total = 0;
        KenkyuKeihiSoukeiInfo kenhiSoukeiInfo = shinseiInfo.getKenkyuKeihiSoukeiInfo();
		if (!errors.isEmpty() || keizokuInfo == null) {
			
			shinseiInfo.getKadaiInfo().setKadaiNameKanji("");
			KenkyuKeihiInfo[] kenhiInfoList = kenhiSoukeiInfo.getKenkyuKeihi();
			kenhiInfoList[0].setNaiyaku("");
			kenhiInfoList[1].setNaiyaku("");
			kenhiInfoList[2].setNaiyaku("");
			kenhiInfoList[3].setNaiyaku("");
			kenhiInfoList[4].setNaiyaku("");
			kenhiSoukeiInfo.setNaiyakuTotal("");
			kenhiSoukeiInfo.setKenkyuKeihi(kenhiInfoList);
			
		} else {
		
			shinseiInfo.getKadaiInfo().setKadaiNameKanji(keizokuInfo.getKadaiNameKanji());
			KenkyuKeihiInfo[] kenhiInfoList = kenhiSoukeiInfo.getKenkyuKeihi();
			kenhiInfoList[0].setNaiyaku(keizokuInfo.getNaiyakugaku1());
			total += Long.parseLong(keizokuInfo.getNaiyakugaku1());
			kenhiInfoList[1].setNaiyaku(keizokuInfo.getNaiyakugaku2());
			total += Long.parseLong(keizokuInfo.getNaiyakugaku2());
			kenhiInfoList[2].setNaiyaku(keizokuInfo.getNaiyakugaku3());
			total += Long.parseLong(keizokuInfo.getNaiyakugaku3());
			kenhiInfoList[3].setNaiyaku(keizokuInfo.getNaiyakugaku4());
			total += Long.parseLong(keizokuInfo.getNaiyakugaku4());
			kenhiInfoList[4].setNaiyaku(keizokuInfo.getNaiyakugaku5());
			total += Long.parseLong(keizokuInfo.getNaiyakugaku5());
			kenhiSoukeiInfo.setNaiyakuTotal(String.valueOf(total));
			kenhiSoukeiInfo.setKenkyuKeihi(kenhiInfoList);
		}
		shinseiInfo.setKenkyuKeihiSoukeiInfo(kenhiSoukeiInfo);
		shinseiForm.setShinseiDataInfo(shinseiInfo);
		
		//-----�\�������̓t�H�[���ɃZ�b�g����B
		updateFormBean(mapping, request, shinseiForm);
		
		//�g�[�N�����Z�b�V�����ɕۑ�����B
		saveToken(request);
		
		//----- ��ʑJ�� -----
		if (errors.isEmpty()) {
			return forwardSuccess(mapping);
		} else {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
	}
}
