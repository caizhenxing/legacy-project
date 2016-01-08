/**
 * 	@(#)QuestionServiceImpl.java   Oct 11, 2006 1:29:27 PM
 *	 ¡£ 
 *	 
 */
package et.bo.pcc.assay.question.service.impl;

import java.util.ArrayList;
import java.util.List;

import et.bo.pcc.assay.question.service.QuestionService;
import et.po.PoliceCallinInfo;
import excellence.common.classtree.ClassTreeService;
import excellence.common.key.KeyService;
import excellence.common.page.PageInfo;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

/**
 * @author zhang
 * @version Oct 11, 2006
 * @see
 */
public class QuestionServiceImpl implements QuestionService {
	
    private BaseDAO dao=null;
    
    private KeyService ks = null;
    
    private ClassTreeService ctree = null;
    
    private int QUESTION_NUM = 0;

	/* (non-Javadoc)
	 * @see et.bo.pcc.assay.question.service.QuestionService#getQuestionSize()
	 */
	public int getQuestionSize() {
		// TODO Auto-generated method stub
		return QUESTION_NUM;
	}

	/* (non-Javadoc)
	 * @see et.bo.pcc.assay.question.service.QuestionService#questionIndex(excellence.framework.base.dto.IBaseDTO, excellence.common.page.PageInfo)
	 */
	public List questionIndex(IBaseDTO dto, PageInfo pageInfo) {
		// TODO Auto-generated method stub
		List l = new ArrayList();
		QuestionSearch qSearch = new QuestionSearch();
//		String fuzznum = (String)dto.get("fuzzno");
//		if (fuzznum!=null&&!fuzznum.equals("")) {
//	        Object[] result = (Object[]) dao.findEntity(qSearch.searchFuzzInfoById(fuzznum, pageInfo));
//	        int s = dao.findEntitySize(qSearch.searchFuzzInfoById(fuzznum, pageInfo));
//	        //QUESTION_NUM = s;
//	        for (int i = 0, size = result.length; i < size; i++) {
//	        	PoliceCallinInfo pc = (PoliceCallinInfo)result[i];
//	        	DynaBeanDTO dbd = new DynaBeanDTO();
//	        	dbd.set("id", pc.getId());
//	        	dbd.set("taginfo", ctree.getvaluebyId(pc.getTagInfo()));
//	        	dbd.set("quinfo", pc.getQuInfo());
//	        	String content = pc.getContent();
//	        	if (content.length()>30) {
//					content = content.substring(0,29)+"...";
//				}
//	        	dbd.set("content", content);
//	        	dbd.set("remark", pc.getRemark());
//	        	l.add(dbd);
//	        }
//			Object[] re=null;
//			re = dao.findEntity(qSearch.searchFuzzInfoById(fuzznum, pageInfo));
//			QUESTION_NUM=((Integer)re[0]).intValue();
//	        return l;
//		}
		Object[] result = null;
        try{
        	result = (Object[]) dao.findEntity(qSearch.searchQuestion(dto,pageInfo));
        }catch(Exception e){
        	e.printStackTrace();
        }
		
//        int s = dao.findEntitySize(qSearch.searchQuestion(dto,pageInfo));
//        QUESTION_NUM = s;
        for (int i = 0, size = result.length; i < size; i++) {
        	PoliceCallinInfo pc = (PoliceCallinInfo)result[i];
        	DynaBeanDTO dbd = new DynaBeanDTO();
        	dbd.set("id", pc.getId());
        	dbd.set("taginfo", ctree.getvaluebyId(pc.getTagInfo()));
        	dbd.set("quinfo", pc.getQuInfo());
        	String content = pc.getContent();
        	if (content.length()>30) {
				content = content.substring(0,29)+"...";
			}
        	dbd.set("content", content);
        	dbd.set("remark", pc.getRemark());
        	l.add(dbd);
        }
        try{
        	QUESTION_NUM= dao.findEntitySize(qSearch.searchQuestion(dto,pageInfo));
        }catch(Exception e){
        	e.printStackTrace();
        }
		//QUESTION_NUM=((Integer)re[0]).intValue();
		return l;
	}

	public BaseDAO getDao() {
		return dao;
	}

	public void setDao(BaseDAO dao) {
		this.dao = dao;
	}

	public KeyService getKs() {
		return ks;
	}

	public void setKs(KeyService ks) {
		this.ks = ks;
	}

	
	public IBaseDTO getQuestionInfo(String id) {
		// TODO Auto-generated method stub
		IBaseDTO dto = new DynaBeanDTO();
		PoliceCallinInfo pcii = (PoliceCallinInfo)dao.loadEntity(PoliceCallinInfo.class, id);
		dto.set("taginfo", pcii.getTagInfo());
		dto.set("quinfo", pcii.getQuInfo());
		dto.set("content", pcii.getContent());
		dto.set("remark", pcii.getRemark());
		return dto;
	}

	public ClassTreeService getCtree() {
		return ctree;
	}

	public void setCtree(ClassTreeService ctree) {
		this.ctree = ctree;
	}

}
