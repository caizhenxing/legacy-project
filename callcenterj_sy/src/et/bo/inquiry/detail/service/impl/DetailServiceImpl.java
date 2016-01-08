package et.bo.inquiry.detail.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.RowSet;

import et.bo.inquiry.detail.service.DetailService;
import et.bo.inquiry.service.impl.InquiryHelp;
import et.po.OperInquiryResult;
import et.po.OperMessages;
import et.po.SysUser;
import excellence.common.key.KeyService;
import excellence.common.page.PageInfo;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

public class DetailServiceImpl implements DetailService {

	private int num = 0;
	private BaseDAO dao = null;
	private KeyService ks = null;
	
	public List query(IBaseDTO dto, PageInfo pi){
		
		List list = new ArrayList();
		DetailHelp h = new DetailHelp();
		Object[] result = (Object[]) dao.findEntity(h.detailQuery(dto, pi));
		num = dao.findEntitySize(h.detailQuery(dto, pi));

		for (int i = 0, size = result.length; i < size; i++) {
			OperInquiryResult po = (OperInquiryResult) result[i];
			list.add(po2dto(po));
		}
		return list;
	}
	
	public List userQuery(String sql) {
		RowSet rs=dao.getRowSetByJDBCsql(sql);
		List<SysUser> list=new ArrayList<SysUser>();
		try {
			rs.beforeFirst();
			while (rs.next()) {
				SysUser su=new SysUser();
				su.setUserId(rs.getString("user_id"));
				list.add(su);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	/**
	 * 查询方法的 po 转 dto
	 * 
	 * @param po
	 * @return dto
	 */
	private DynaBeanDTO po2dto(OperInquiryResult po) {

		DynaBeanDTO dto = new DynaBeanDTO();

		dto.set("id", po.getId());
		dto.set("topicId", po.getTopicId());
		dto.set("actor", po.getActor());
		dto.set("rid", po.getRid());
		dto.set("createTime", TimeUtil.getTheTimeStr(po.getCreateTime(), "yyyy-MM-dd"));
		dto.set("cardId", po.getCardId());
		dto.set("question_type", InquiryHelp.getLabelByValue(po.getQuestionType()));
		dto.set("question", po.getQuestion());
		dto.set("answer", po.getAnswer());
		
		return dto;
	}
	public int getSize(){
		return num;
	}
	/**
	 * @return dao
	 */
	public BaseDAO getDao() {
		return dao;
	}
	/**
	 * @param dao 要设置的 dao
	 */
	public void setDao(BaseDAO dao) {
		this.dao = dao;
	}
	/**
	 * @return ks
	 */
	public KeyService getKs() {
		return ks;
	}
	/**
	 * @param ks 要设置的 ks
	 */
	public void setKs(KeyService ks) {
		this.ks = ks;
	}

	
}
