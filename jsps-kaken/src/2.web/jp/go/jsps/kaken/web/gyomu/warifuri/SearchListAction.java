/*======================================================================
 *    SYSTEM      : ���{�w�p�U����d�q�\���V�X�e���i�Ȋw������⏕���j
 *    Source name : SearchListAction.java
 *    Description : ����U�茋�ʏ��ꗗ��ʂ�\������
 *
 *    Author      : Admin
 *    Date        : 2003/11/14
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2003/11/14    V1.0        Admin          �V�K�쐬
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.gyomu.warifuri;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.jigyoKubun.JigyoKubunFilter;
import jp.go.jsps.kaken.model.*;
import jp.go.jsps.kaken.model.common.*;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.SystemException;
import jp.go.jsps.kaken.model.role.UserRole;
import jp.go.jsps.kaken.model.vo.GyomutantoInfo;
import jp.go.jsps.kaken.model.vo.WarifuriSearchInfo;
import jp.go.jsps.kaken.util.Page;
import jp.go.jsps.kaken.web.common.IConstants;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * ����U�茋�ʌ����A�N�V�����N���X�B
 * ����U�茋�ʏ��ꗗ��ʂ�\������B
 * 
 * ID RCSfile="$RCSfile: SearchListAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:21 $"
 */
public class SearchListAction extends BaseAction {

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

		//-----�L�����Z����		
		if (isCancelled(request)) {
			return forwardCancel(mapping);
		}

		//���������̎擾
		WarifuriSearchForm searchForm = (WarifuriSearchForm)form;
		
		//-------�� VO�Ƀf�[�^���Z�b�g����B
		WarifuriSearchInfo searchInfo = new WarifuriSearchInfo();
		try {
			PropertyUtils.copyProperties(searchInfo, searchForm);
		} catch (Exception e) {
			log.error(e);
			throw new SystemException("�v���p�e�B�̐ݒ�Ɏ��s���܂����B", e);
		}
		//�`�F�b�N�{�b�N�X�̎��ƃR�[�h���X�g���Z�b�g
		if(!searchForm.getValueList().isEmpty()){
			searchInfo.setJigyoCdValueList(searchForm.getValueList());	
		}else{
			//�w�肳��Ă��Ȃ��ꍇ�́A�i�Ɩ��S���҂Ȃ�΁j�������S�����鎖�Ƌ敪����R���Ώە��̎��Ƌ敪�̂ݎ擾	
			Set shinsaTaishoSet = JigyoKubunFilter.getShinsaTaishoJigyoKubun(container.getUserInfo());
			//�R���Ώە��̎��Ƌ敪�����������ɃZ�b�g
			searchInfo.setTantoJigyoKubun(shinsaTaishoSet);
			//2005.05.17 iso �A�N�Z�X�Ǘ������Ƌ敪������CD�ɂ�������̂Œǉ�
			if(container.getUserInfo().getRole().equals(UserRole.GYOMUTANTO)){
				GyomutantoInfo gyomutantoInfo = container.getUserInfo().getGyomutantoInfo(); 
				searchInfo.setJigyoCdValueList(new ArrayList(gyomutantoInfo.getTantoJigyoCd()));
			}
		}
		
		//�T�[�r�X�擾
		ISystemServise servise = getSystemServise(IServiceName.SHINSAIN_WARIFURI_SERVICE);
		
		//-----1�\���Ɋ���U����R������
		int shinsainCount = 0;
		//TODO 2�i�K�R���@���ɐR�����l���́A�w�n6?�Ɗ��12?�ŕ�����B(���ƃR�[�h����擾����)
		String jigyoKubun = servise.selectJigyoKubun(container.getUserInfo(),
													(String)searchForm.getValues(0));
		
		if(jigyoKubun.equals(IJigyoKubun.JIGYO_KUBUN_GAKUSOU_HIKOUBO)){
			//�w�p�n���̏ꍇ
			shinsainCount = IShinsainWarifuri.SHINSAIN_NINZU_GAKUSOU;
		}else{ // if(jigyoKubun.equals(IJigyoKubun.JIGYO_KUBUN_KIBAN)){
			//��ՂƎ��X�^�[�g�A�b�v�̏ꍇ 2006/04/10�C��
			shinsainCount = IShinsainWarifuri.SHINSAIN_NINZU_KIBAN;
		}

		//-----1�\���~����U����R������������DB���擾����
		searchInfo.setStartPosition(searchForm.getStartPosition() * shinsainCount);
		searchInfo.setPageSize(searchForm.getPageSize() * shinsainCount);
		searchInfo.setMaxSize(searchForm.getMaxSize() * shinsainCount);

		//�������s
		Page result =servise.search(container.getUserInfo(), searchInfo);

		int countShinsain = 0;							//�����Ɠ��ł̐R���������J�E���g����ϐ�
		int rowSize = 0;								//�_�~�[�R������������row�̃T�C�Y		2005.10.28 iso �ǉ�
		int rigaiSize = 0;								//�R���������A���Q�֌W����̐R������
		int dairiSize = 0;								//�㗝�R������
		List list = result.getList();
		List newList = new ArrayList();				//���Ƃ��ƂɕҐ����Ȃ���List
		List shinsainList = new ArrayList();			//�����Ƃ̐R�������(HashMap)���܂Ƃ߂�List
        
		for(int i = 0; i < result.getSize(); i++) {
			HashMap hashMap = (HashMap)list.get(i);
			String systemNo = hashMap.get("SYSTEM_NO").toString();
			
			String nextSystemNo;
			if(i == result.getSize()-1) {
				nextSystemNo = "Not match";
			} else {
				HashMap nextHashMap = (HashMap)list.get(i+1);
				nextSystemNo = nextHashMap.get("SYSTEM_NO").toString();
			}

			countShinsain++;
			
			//2005.10.28 iso 2������U��Ή�
			//��{�̓_�~�[�̐R�������o���Ȃ��悤
			if(!hashMap.get("SHINSAIN_NO").toString().startsWith("@")) {
				rowSize++;
			}
//2006/10/27 modify by liucy start            
            // 2�i�K�R�� ���Q�֌W�Ґl�����A�_�~�[�f�[�^�ɑ�Ńt���O�𗧂Ă�B
            // if(hashMap.get("RIGAI") != null &&
            // "1".equals(hashMap.get("RIGAI"))
            // && "1".equals(hashMap.get("SHINSA_JOKYO"))){
            // rigaiSize++;
            // }

            // TODO 2�i�K�R�� ���Q�֌W�Ґl�����A�_�~�[�f�[�^�ɑ�Ńt���O�𗧂Ă�B
            if (hashMap.get("RIGAI") != null
                    && "1".equals(hashMap.get("RIGAI"))
                    && "1".equals(hashMap.get("NYURYOKU_JOKYO"))) {
                rigaiSize++;
            }
//2006/10/27 modify by liucy end 
			//�㗝�R�����̃J�E���g
			if(hashMap.get("DAIRI") != null && "1".equals(hashMap.get("DAIRI"))){
				dairiSize++;
			}
			
			//�R�������͐R����HashMap�Ɉړ�
			HashMap shinsainMap = new HashMap();
			shinsainMap.put("ROWSHINSAIN", new Integer(countShinsain));						//�����Ɠ��ŉ��s�ڂ̐R�������H�@1�l�ځF1
			shinsainMap.put("SHINSAIN_NO", hashMap.get("SHINSAIN_NO"));							//�R�����ԍ�
			shinsainMap.put("JIGYO_KUBUN", hashMap.get("JIGYO_KUBUN"));							//���Ƌ敪�i�R�����ʁj
			shinsainMap.put("SHINSAIN_NAME_KANJI_SEI", hashMap.get("SHINSAIN_NAME_KANJI_SEI"));	//�R���������i����-���j
			shinsainMap.put("SHINSAIN_NAME_KANJI_MEI", hashMap.get("SHINSAIN_NAME_KANJI_MEI"));	//�R���������i����-���j
			shinsainMap.put("SHINSAIN_SHOZOKU_NAME", hashMap.get("SHINSAIN_SHOZOKU_NAME"));		//�R���������@�֖�
			shinsainMap.put("SHINSAIN_BUKYOKU_NAME", hashMap.get("SHINSAIN_BUKYOKU_NAME"));		//�R�������ǖ�
			shinsainMap.put("SHINSAIN_SHOKUSHU_NAME", hashMap.get("SHINSAIN_SHOKUSHU_NAME"));	//�R�����E��
			shinsainMap.put("SHINSA_JOKYO", hashMap.get("SHINSA_JOKYO"));						//�R����
			shinsainMap.put("KOSHIN_DATE", hashMap.get("KOSHIN_DATE"));							//����U��X�V��
//2006/11/14 �c�@�ǉ���������
            shinsainMap.put("RIGAI", hashMap.get("RIGAI"));                         			//���Q�֌W
            shinsainMap.put("NYURYOKU_JOKYO", hashMap.get("NYURYOKU_JOKYO"));                   //���Q�֌W���͏�
//2006/11/14 �c�@�ǉ������܂�            

			//���Q�֌W�Ґl���́A���Q�t���O�E�����t���O�̗����������Ă���l��
			shinsainList.add(shinsainMap);			//�����Ƃ̐R�������(HashMap)��shinsainList�ɂ܂Ƃ߂�

			//���̃f�[�^���Ⴄ���ƂȂ�A�����Ƃ̐R���������܂Ƃ߁A�V�����f�[�^���X�g�Ɋi�[
			if(systemNo != null && nextSystemNo != null && !systemNo.equals(nextSystemNo)) {
				
				//�i�\��������_�~�[�f�[�^�� = �R������&���Q�֌W����̐R������ - �㗝�R�������j
				int dairiCnt = rigaiSize - dairiSize;
				//�\��������_�~�[�f�[�^�ɑ㗝�t���O�𗧂Ă�
				for(int k = 0; k < shinsainList.size(); k++){
					if(dairiCnt > 0 && ((HashMap)shinsainList.get(k)).get("SHINSAIN_NO").toString().startsWith("@")){
						((HashMap)shinsainList.get(k)).put("DAIRI_FLG", new Integer(1));
						dairiCnt--;
					}else{
						((HashMap)shinsainList.get(k)).put("DAIRI_FLG", new Integer(0));
					}
				}
				
				//���Ɠ��R��������List�Ƃ��Ċi�[����̂ŁA�]���̐R�������͍폜
				List regShinsainList = new ArrayList(shinsainList);
				hashMap.put("SHINSAIN", regShinsainList);
				hashMap.remove("SHINSAIN_NO");				//�R�����ԍ�
//				hashMap.remove("JIGYO_KUBUN");				//���Ƌ敪�i�R�����ʁj
				hashMap.remove("SHINSAIN_NAME_KANJI_SEI");	//�R���������i����-���j
				hashMap.remove("SHINSAIN_NAME_KANJI_MEI");	//�R���������i����-���j
				hashMap.remove("SHINSAIN_SHOZOKU_NAME");	//�R���������@�֖�
				hashMap.remove("SHINSAIN_BUKYOKU_NAME");	//�R�������ǖ�
				hashMap.remove("SHINSAIN_SHOKUSHU_NAME");	//�R�����E��
				
				//rowSize���擾�����R�������𒴂���ꍇ�AROWSIZE = 12�Ƃ���
				//2005.12.06 iso
				//�R���㗝�l�����蓖�Ă���A���Q�֌W�������R�������R������蒼���u���Q�֌W�Ȃ��v�ɂ����
				//rigaiSize - dairiSize�����ɂȂ�A�������Z�ƂȂ�ROWSIZE���s������B�B
				//���ɂȂ�ꍇ�́A���炳�Ȃ��悤�C�� 
//				rowSize = rowSize + (rigaiSize - dairiSize);
				if(rigaiSize - dairiSize > 0) {
//2006/11/24�_�~�[�f�[�^���Ȃ��ꍇ�A�㗝�t���O�𗧂ĂȂ��ׁA
//					rowSize = rowSize + (rigaiSize - dairiSize);
					rowSize = rowSize + (rigaiSize - dairiSize - dairiCnt);
				}
				if(rowSize > 12){
					hashMap.put("ROWSIZE", new Integer(12));		//2005.10.28 iso �T�C�Y��ǉ�					
				}else{
					hashMap.put("ROWSIZE", new Integer(rowSize));	//2005.10.28 iso �T�C�Y��ǉ�
				}

				newList.add(hashMap);
				shinsainList.clear();
				countShinsain = 0;
				rowSize = 0;			//2005.10.18 iso �ǉ�
				rigaiSize = 0;			//2005.11.02 tanabe �ǉ�
				dairiSize = 0;			//2005.11.04 tanabe �ǉ�
			}
		}

		//-----�g�[�^�������͐\���������Z�b�g����i�\��������DB�̌������R�[�h���^�R�������j
		Page newResult = new Page(newList, 
								  searchForm.getStartPosition(), 
								  searchForm.getPageSize(),
								  result.getTotalSize() / shinsainCount);

		//�o�^���ʂ����N�G�X�g�����ɃZ�b�g
		request.setAttribute(IConstants.RESULT_INFO, newResult);
		
		//�g�[�N�����Z�b�V�����ɕۑ�����B�i�폜�̏ꍇ�ɁA�K�v�j
		saveToken(request);

		//-----��ʑJ�ځi��^�����j-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		return forwardSuccess(mapping);
	}
}