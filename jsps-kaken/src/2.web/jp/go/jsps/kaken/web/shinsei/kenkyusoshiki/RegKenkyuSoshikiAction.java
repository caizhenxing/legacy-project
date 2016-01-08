/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : takano
 *    Date        : 2004/01/09
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.shinsei.kenkyusoshiki;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.*;

import jp.go.jsps.kaken.model.exceptions.*;
import jp.go.jsps.kaken.model.vo.shinsei.KenkyuSoshikiKenkyushaInfo;
import jp.go.jsps.kaken.web.common.*;
import jp.go.jsps.kaken.web.shinsei.ShinseiForm;
import jp.go.jsps.kaken.web.struts.*;

import org.apache.struts.action.*;
import org.apache.struts.action.ActionMapping;

/**
 * �����g�D�\�o�^�A�N�V�����N���X�B
 * ID RCSfile="$RCSfile: RegKenkyuSoshikiAction.java,v $"
 * Revision="$Revision: 1.2 $"
 * Date="$Date: 2007/07/23 10:30:16 $"
 */
public class RegKenkyuSoshikiAction extends BaseAction {

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

		//�����g�D�����Z�b�V�����Ɋi�[����݂̂Ȃ̂œ��ɉ������Ȃ��B

		//-----��ʑJ�ځi��^�����j-----
//ADD�@START 2007/07/11 BIS �����_   (�����g�D�\�̒��@�F�@�\�[�g���@�@��\�ҕ��S�ҕʁ@�����A�A�V�[�P���X�ԍ��i��ʂ���̓o�^���j�@����)
		ShinseiForm shinseiForm = (ShinseiForm)form;
		List kenkyusoshikiIfo = shinseiForm.getShinseiDataInfo().getKenkyuSoshikiInfoList();
		if(kenkyusoshikiIfo != null && kenkyusoshikiIfo.size() > 0){
			List outList = new ArrayList();
			List daihyoList = new ArrayList();    //������\�� : 1 
			List bunfanList = new ArrayList();    //�@���S��   : 2
			List kyouryokuList = new ArrayList(); //�������͎� : 3
			List renkeiList = new ArrayList();    //�A�g������ : 4
			List nullList = new ArrayList();      //    �� �@�@: 5
			
			for(int i = 0 ; i <kenkyusoshikiIfo.size() ; i ++ ){
				KenkyuSoshikiKenkyushaInfo kenkyuSoshikiKenkyushaInfo = (KenkyuSoshikiKenkyushaInfo) kenkyusoshikiIfo.get(i);
				String buntan = kenkyuSoshikiKenkyushaInfo.getBuntanFlag();
				if("1".equals(buntan)){
					daihyoList.add(kenkyuSoshikiKenkyushaInfo);
				}else if("2".equals(buntan)){
					bunfanList.add(kenkyuSoshikiKenkyushaInfo);
				}else if("3".equals(buntan)){
					kyouryokuList.add(kenkyuSoshikiKenkyushaInfo);
				}else if("4".equals(buntan)){
					renkeiList.add(kenkyuSoshikiKenkyushaInfo);
				}else if("5".equals(buntan)){
					nullList.add(kenkyuSoshikiKenkyushaInfo);
				}
				
			}
			if(daihyoList.size()>0){
				outList.addAll(daihyoList)	;
			}
			if(bunfanList.size()>0){
				outList.addAll(bunfanList)	;
			}
			if(kyouryokuList.size()>0){
				outList.addAll(kyouryokuList)	;
			}
			if(renkeiList.size()>0){
				outList.addAll(renkeiList)	;	
			}
			if(nullList.size()>0){
				outList.addAll(nullList)	;				}
			shinseiForm.getShinseiDataInfo().setKenkyuSoshikiInfoList(outList);			
		}	
//ADD�@END�@ 2007/07/11 BIS �����_	
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		
		return forwardSuccess(mapping);
		
	}
	
	
	
}
