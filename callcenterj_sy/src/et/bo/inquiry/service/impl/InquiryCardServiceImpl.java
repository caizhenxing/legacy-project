/**
 * 	InquiryCardServiceImpl.java   2008-4-2 下午02:56:35
 *	版权所有 沈阳市卓越科技有限公司。 
 *	卓越科技 保留一切权利
 */
package et.bo.inquiry.service.impl;

import org.apache.log4j.Logger;

import et.bo.inquiry.service.InquiryCardService;
import et.po.OperInquiryCard;
import excellence.common.classtree.ClassTreeService;
import excellence.common.key.KeyService;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

/**
 * et.bo.inquiry.service.InquiryCardService的实现类
 * @author 梁云锋
 *
 */
public class InquiryCardServiceImpl implements InquiryCardService {
	static Logger log = Logger.getLogger(InquiryCardServiceImpl.class);
	private BaseDAO dao;
	private KeyService ks;
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
	public IBaseDTO getInquiryCardInfo(String id) {
		// TODO Auto-generated method stub
		OperInquiryCard po=(OperInquiryCard)dao.loadEntity(OperInquiryCard.class, id);
		IBaseDTO dto = new DynaBeanDTO();
		dto.set("questionType", po.getDictQuestionType());
		dto.set("question",po.getQuestion());
		dto.set("alternatives",po.getAlternatives());
		dto.set("id",po.getId());
		dto.set("createTime",TimeUtil.getTheTimeStr(po.getCreateTime(),"yyyy-MM-dd"));
		return dto;
	}

	public void delete(IBaseDTO dto) {
		// TODO Auto-generated method stub
		OperInquiryCard inquiryCard=(OperInquiryCard)dao.loadEntity(OperInquiryCard.class, dto.get("id").toString());
		dao.removeEntity(inquiryCard);
	}
	public void update(IBaseDTO dto) {
		// TODO Auto-generated method stub
		OperInquiryCard inquiryCard=(OperInquiryCard)dao.loadEntity(OperInquiryCard.class, dto.get("id").toString());
		inquiryCard.setAlternatives(dto.get("alternatives").toString());
		inquiryCard.setDictQuestionType(dto.get("questionType").toString());
		inquiryCard.setQuestion(dto.get("question").toString());
	}

}
