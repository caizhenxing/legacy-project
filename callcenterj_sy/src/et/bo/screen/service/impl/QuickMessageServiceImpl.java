/*
 * @(#)CustinfoServiceImpl.java	 2008-03-19
 *
 * 版权所有 沈阳市卓越科技有限公司。
 */

package et.bo.screen.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import et.bo.screen.service.QuickMessageService;

import et.po.OperCustinfo;
import et.po.OperQuestion;
import et.po.ScreenQuickMessage;
import excellence.common.classtree.ClassTreeService;
import excellence.common.key.KeyService;
import excellence.common.page.PageInfo;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

/**
 * <p>
 * 每日快讯
 * </p>
 * 
 * @version 2008-03-19
 * @author wwq
 */

public class QuickMessageServiceImpl implements QuickMessageService {

	BaseDAO dao = null;

	private int num = 0;

	private List questionList = null;

	public KeyService ks = null;

	private ClassTreeService cts = null;

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

	public ClassTreeService getCts() {
		return cts;
	}

	public void setCts(ClassTreeService cts) {
		this.cts = cts;
	}

	/**
	 * 根据客户ID查询数据列表,返回该客户的“问题”的list。 取得查询问题列表数据。 返回的列表根本时间进行排序
	 * 
	 * @return 回访数据的list
	 */
	public List getQuestionList() {
		List<String> l = new ArrayList<String>();
		List qList = new ArrayList();
		for (int i = 0; i < questionList.size(); i++) {
			DynaBeanDTO dto = (DynaBeanDTO) questionList.get(i);
			l.add((String) dto.get("addtime"));
		}
		Collections.sort(l);
		for (int i = l.size() - 1; i >= 0; i--) {
			for (int j = 0; j < questionList.size(); j++) {
				DynaBeanDTO dto = (DynaBeanDTO) questionList.get(j);
				if (l.get(i).equals(dto.get("addtime"))) {
					qList.add(dto);
					break;
				}
			}
		}

		return qList;
	}

	/**
	 * 查询数据列表,返回记录的list。 取得查询问题列表数据。
	 * 
	 * @param dto
	 *            数据传输对象
	 * @param pi
	 *            页面信息
	 * @return 数据的list
	 */
	public List quickMessageQuery(IBaseDTO dto, PageInfo pi) {

		List list = new ArrayList();
		QuickMessageHelp h = new QuickMessageHelp();
		
		Object[] result = (Object[]) dao.findEntity(h.quickMessageQuery(dto, pi));
		num = dao.findEntitySize(h.quickMessageQuery(dto, pi));

		for (int i = 0, size = result.length; i < size; i++) {
			ScreenQuickMessage po = (ScreenQuickMessage) result[i];
			String msg = po.getMsgContent();
//			if(msg!=null&&msg.length()>25)
//			{
//				msg = msg.substring(0,25)+"....";
				po.setMsgContent(msg);
//			}
			list.add(this.quickMessageDynaBeanDTO(po));
		}
		return list;
	}

	/**
	 * 查询数据列表,返回记录(专)的list。 取得查询列表数据。
	 * 
	 * @param dto
	 *            数据传输对象
	 * @param pi
	 *            页面信息
	 * @return 数据的list
	 */
	public List quickMessageAllQuery() {
		List list = new ArrayList();
		QuickMessageHelp h = new QuickMessageHelp();
		try
		{
			Object[] result = (Object[]) dao.findEntity(h.quickMessageAllQuery());
			for (int i = 0, size = result.length; i < size; i++) {
				ScreenQuickMessage po = (ScreenQuickMessage) result[i];
				list.add(this.quickMessageDynaBeanDTO(po));
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 查询数据列表,返回全部记录的list。 取得查询问题列表数据。
	 * 
	 * @param dto
	 *            数据传输对象
	 * @param pi
	 *            页面信息
	 * @return 数据的list
	 */
	public List custinfoAllQuery() {

		List list = new ArrayList();
		QuickMessageHelp h = new QuickMessageHelp();

		Object[] result = (Object[]) dao.findEntity(h.allQuery());

		for (int i = 0, size = result.length; i < size; i++) {
			ScreenQuickMessage po = (ScreenQuickMessage) result[i];
			list.add(this.quickMessageDynaBeanDTO(po));
		}
		return list;
	}

	/**
	 * 查询方法的 po 转 dto
	 * 
	 * @param po
	 * @return dto
	 */
	private DynaBeanDTO quickMessageDynaBeanDTO(ScreenQuickMessage po) {

		DynaBeanDTO dto = new DynaBeanDTO();

		dto.set("id", po.getId());
		dto.set("inputMan", po.getInputMan());
		dto.set("msgContent", po.getMsgContent());
		dto.set("msgTitle", po.getMsgTitle());
		dto.set("remark", po.getRemark());
		dto.set("createDate", po.getCreateDate());
		return dto;
	}

	/**
	 * 查询数据列表的条数。 取得问题查询列表的条数。
	 * 
	 * @return 得到list的条数
	 */
	public int getQuickMessageSize() {

		return num;

	}

	/**
	 * 根据ID取得一条数据的excellence.framework.base.dto.IBaseDTO对象 取得某条数据的详细信息。
	 * 
	 * @param id
	 *            取得excellence.framework.base.dto.IBaseDTO的标识
	 * @return 包含数据信息的excellence.framework.base.dto.IBaseDTO对象
	 */
	public IBaseDTO getQuickMessageInfo(String id) {

		ScreenQuickMessage po = (ScreenQuickMessage) dao.loadEntity(ScreenQuickMessage.class, id);
		IBaseDTO dto = new DynaBeanDTO();

		dto.set("id", po.getId());
		dto.set("inputMan", po.getInputMan());
		dto.set("msgContent", po.getMsgContent());
		dto.set("msgTitle", po.getMsgTitle());
		dto.set("remark", po.getRemark());
		dto.set("createDate", po.getCreateDate());

		return dto;

	}

	/**
	 * 根据电话号码取得一条数据的excellence.framework.base.dto.IBaseDTO对象
	 * 根据电话号码查询用户宅电，办公电话，手机，取得某客户的详细信息。
	 * 
	 * @param id
	 *            取得excellence.framework.base.dto.IBaseDTO的标识
	 * @return 包含数据信息的excellence.framework.base.dto.IBaseDTO对象
	 */
	public IBaseDTO getopenwinInfo(String tel) {

		QuickMessageHelp h = new QuickMessageHelp();
		Object[] obj = (Object[]) dao.findEntity(h.openwinQuery(tel));

		OperCustinfo po = new OperCustinfo();
		if (obj.length > 0) { // 如果返回结果数组大于0
			po = (OperCustinfo) obj[0]; // 则取得第一个对象转成po
		}
		IBaseDTO dto = new DynaBeanDTO();

		dto.set("cust_id", po.getCustId());
		dto.set("cust_name", po.getCustName());
		dto.set("dict_sex", po.getDictSex());
		dto.set("cust_email", po.getCustEmail());
		dto.set("cust_addr", po.getCustAddr());
		dto.set("cust_pcode", po.getCustPcode());
		dto.set("cust_tel_home", po.getCustTelHome());
		dto.set("cust_tel_work", po.getCustTelWork());
		dto.set("cust_tel_mob", po.getCustTelMob());
		dto.set("cust_fax", po.getCustFax());
		dto.set("cust_voc", po.getDictCustVoc());
		dto.set("cust_scale", po.getDictCustScale());
		dto.set("cust_type", po.getDictCustType());
		dto.set("remark", po.getRemark());

		Set sets = po.getOperQuestions();
		Iterator it = sets.iterator();
		questionList = new ArrayList();
		while (it.hasNext()) {
			OperQuestion oq = (OperQuestion) it.next();
			DynaBeanDTO ddto = new DynaBeanDTO();

			ddto.set("id", oq.getId());
			ddto.set("dict_question_type1", oq.getDictQuestionType1());
			ddto.set("question_content", oq.getQuestionContent());

			String dict_is_answer_succeed = oq.getDictIsAnswerSucceed();
			if (dict_is_answer_succeed != null
					&& dict_is_answer_succeed.indexOf("SYS") != -1)
				dict_is_answer_succeed = cts
						.getLabelById(dict_is_answer_succeed);
			ddto.set("dict_is_answer_succeed", dict_is_answer_succeed);

			String d = new java.text.SimpleDateFormat("yy-MM-dd HH:mm")
					.format(oq.getAddtime());
			ddto.set("addtime", d);

			questionList.add(ddto);
		}

		return dto;
	}

	/**
	 * 修改数据。 修改某条记录的内容。
	 * 
	 * @param dto
	 *            要更新的的excellence.framework.base.dto.IBaseDTO对象
	 * @return boolean
	 */
	public boolean updateQuickMessage(IBaseDTO dto) {

		try {
			dao.saveEntity(modifycoll(dto));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 修改方法的 dto 转 po
	 * 
	 * @param dto
	 * @return po
	 */
	private ScreenQuickMessage modifycoll(IBaseDTO dto) {

		ScreenQuickMessage po = (ScreenQuickMessage) dao.loadEntity(ScreenQuickMessage.class, dto
				.get("id").toString());// 根据主键修改表
		
		po.setId((String)dto.get("id"));
		po.setMsgTitle((String)dto.get("msgTitle"));
		po.setInputMan((String)dto.get("inputMan"));
		po.setMsgContent((String)dto.get("msgContent"));
		po.setRemark((String)dto.get("remark"));

		return po;
	}

	/**
	 * 删除数据。 删除某条记录。
	 * 
	 * @param id
	 *            要删除数据的标识
	 */
	public void delQuickMessage(String id) {

		ScreenQuickMessage po = (ScreenQuickMessage) dao.loadEntity(ScreenQuickMessage.class, id);
		dao.removeEntity(po);

	}

	/**
	 * 标记删除。 标记字段"IS_DELETE"，为"1"时为删除，为"0"时未删除。实际上这个方法执行的是修改"IS_DELETE"字段的操作。
	 * 
	 * @param id
	 *            要标记删除数据的标识
	 */
	public boolean isDelete(String id) {

		OperCustinfo po = (OperCustinfo) dao.loadEntity(OperCustinfo.class, id);// 根据主键修改表
		po.setIsDelete("1"); // "1"是已删除的意思

		try {
			dao.saveEntity(po);
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	/**
	 * 添加数据。 向数据库中添加一条记录。
	 * 
	 * @param dto
	 *            新数据的excellence.framework.base.dto.IBaseDTO对象
	 */
	public void addQuickMessage(IBaseDTO dto) {

		dao.saveEntity(createQuickMessage(dto));

	}

	/**
	 * 添加方法的 dto 转 po
	 * 
	 * @param dto
	 * @return po
	 */
	private ScreenQuickMessage createQuickMessage(IBaseDTO dto) {

		ScreenQuickMessage po = new ScreenQuickMessage();

		// 生成ID
		//custId = ks.getNext("oper_custinfo");
		//po.setCustId(custId);
		// 标记删除字段为"0"
		po.setId(ks.getNext("Screen_QM"));
		po.setMsgTitle((String)dto.get("msgTitle"));
		po.setInputMan((String)dto.get("inputMan"));
		po.setMsgContent((String)dto.get("msgContent"));
		po.setRemark((String)dto.get("remark"));
		po.setCreateDate(new java.util.Date());

		return po;
	}

	/**
	 * 大屏幕显示
	 * @return
	 */
	public String screenOper(){			
		Object[] result = (Object[]) dao.findEntity(new QuickMessageHelp().quickMessageAllQuery());
		StringBuffer sb = new StringBuffer();
		for (int i = 0, size = result.length; i < size; i++) {
			ScreenQuickMessage sqm = (ScreenQuickMessage) result[i];
			sb.append(sqm.getMsgTitle());
			sb.append(" ：");
			sb.append(sqm.getMsgContent());
			sb.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");			
		}
		if(sb.length()>1){
			return sb.toString();
		}
		return null;
	}

	

}
