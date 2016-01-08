/*
 * @(#)CallbackServiceImpl.java	 2008-04-01
 *
 * 版权所有 沈阳市卓越科技有限公司。
 */

package et.bo.callback.service.impl;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

import et.bo.callback.service.CallbackService;
import et.po.OperCallback;
import et.po.OperQuestion;
import excellence.common.key.KeyService;
import excellence.common.page.PageInfo;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

/**
 * <p>回访管理</p>
 * 
 * @version 2008-04-01
 * @author nie
 */

public class CallbackServiceImpl implements CallbackService {
	
	BaseDAO dao = null;
	private int num = 0;
	private String custId = null;
	public KeyService ks = null;
	
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
	
	/**
	 * 查询数据列表,返回记录的list。
	 * 取得查询问题列表数据。
	 * @param dto 数据传输对象
	 * @param pi 页面信息
	 * @return 数据的list
	 */
	public List callbackQuery(IBaseDTO dto, PageInfo pi) {
		
		List list = new ArrayList();
		CallbackHelp h = new CallbackHelp();
		
		Object[] result = (Object[]) dao.findEntity(h.custinfoQuery(dto, pi));
		num = dao.findEntitySize(h.custinfoQuery(dto, pi));
		
		for (int i = 0, size = result.length; i < size; i++) {
			OperCallback po = (OperCallback) result[i];
			list.add(custinfoToDynaBeanDTO(po));
		}
		return list;
	}
	
	/**
	 * 查询方法的 po 转 dto
	 * @param po
	 * @return dto
	 */
	private DynaBeanDTO custinfoToDynaBeanDTO(OperCallback po){
		
		DynaBeanDTO dto = new DynaBeanDTO();
		
		dto.set("id", po.getId());
		dto.set("callback_content", po.getCallbackContent());
		dto.set("remark", po.getRemark());
		String d = DateFormat.getDateInstance().format(po.getCallbackTime());
		dto.set("callback_time", d);
		
		return dto;
	}
	/**
	 * 查询数据列表的条数。
	 * 取得问题查询列表的条数。
	 * @return 得到list的条数
	 */
	public int getCallbackSize() {
		
		return num;
	
	}
	/**
	 * 根据ID取得一条数据的excellence.framework.base.dto.IBaseDTO对象
	 * 取得某条数据的详细信息。
	 * @param id 取得excellence.framework.base.dto.IBaseDTO的标识
	 * @return 包含数据信息的excellence.framework.base.dto.IBaseDTO对象
	 */
	public IBaseDTO getCallbackInfo(String id) {
		
		OperCallback po = (OperCallback)dao.loadEntity(OperCallback.class,id);
		IBaseDTO dto = new DynaBeanDTO();
		
		dto.set("id", po.getId());
		dto.set("callback_content", po.getCallbackContent());
		dto.set("is_callback_succ", po.getIsCallbackSucc());
		dto.set("remark", po.getRemark());
		String d = DateFormat.getDateInstance().format(po.getCallbackTime());
		dto.set("callback_time", d);
		
		return dto;
		
	}

	/**
	 * 修改数据。
	 * 修改某条记录的内容。
	 * @param dto 要更新的的excellence.framework.base.dto.IBaseDTO对象
	 * @return boolean
	 */
	public boolean updateCallback(IBaseDTO dto) {
		
		OperCallback po = (OperCallback)dao.loadEntity(OperCallback.class,dto.get("id").toString());//根据主键修改表
		
		po.setCallbackTime(TimeUtil.getTimeByStr(dto.get("callback_time").toString(),"yyyy-MM-dd"));		//时间
		po.setCallbackContent(dto.get("callback_content").toString());	//内容
		po.setRemark(dto.get("remark").toString());			//备注
		po.setIsCallbackSucc(dto.get("is_callback_succ").toString());
		
		try{
			dao.saveEntity(po);
			return true;
		}catch(Exception e){
			return false;
		}
	}
	/**
	 * 删除数据。
	 * 删除某条记录。
	 * @param id 要删除数据的标识
	 */
	public void delCallback(String id) {
		
		OperCallback cq = (OperCallback)dao.loadEntity(OperCallback.class,id);
		dao.removeEntity(cq);
		
	}
	/**
	 * 标记删除。
	 * 标记字段"IS_DELETE"，为"1"时为删除，为"0"时未删除。实际上这个方法执行的是修改"IS_DELETE"字段的操作。
	 * @param id 要标记删除数据的标识
	 */
	public boolean isDelete(String id) {
		
		OperCallback po = (OperCallback)dao.loadEntity(OperCallback.class,id);//根据主键修改表
		po.setIsDelete("1");	//"1"是已删除的意思
		
		try{
			dao.saveEntity(po);
			return true;
		}catch(Exception e){
			return false;
		}
		
	}
	/**
	 * 添加数据。
	 * 向数据库中添加一条记录。
	 * @param dto 新数据的excellence.framework.base.dto.IBaseDTO对象
	 */
	public void addCallback(IBaseDTO dto) {
		
		OperCallback po = new OperCallback();
		OperQuestion operQuestion = new OperQuestion();
		
		po.setId(ks.getNext("oper_callback"));	//生成ID
		po.setIsDelete("0");					//设置未删除
		
		String qid = dto.get("question_id").toString();
		if(qid != null && !qid.equals("")){
			operQuestion.setId(qid);
			po.setOperQuestion(operQuestion);		//问题id
		}
		
		po.setCallbackTime(TimeUtil.getTimeByStr(dto.get("callback_time").toString(),"yyyy-MM-dd"));		//时间
		po.setCallbackContent(dto.get("callback_content").toString());	//内容
		po.setRemark(dto.get("remark").toString());			//备注
		po.setIsCallbackSucc(dto.get("is_callback_succ").toString());	//是否成功
		
		dao.saveEntity(po);

	}

	/**
	 * 取得新客户记录的ID
	 * @return custID
	 */
	public String getCustId() {
		
		return custId;
		
	}
}
