/*
 * @(#)CustinfoServiceImpl.java	 2008-03-19
 *
 * 版权所有 沈阳市卓越科技有限公司。
 */

package et.bo.custinfo.service.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.sql.RowSet;

import jxl.Cell;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import et.bo.custinfo.service.PhoneinfoService;
import et.po.OperCustinfo;
import et.po.SysUser;
import excellence.common.classtree.ClassTreeService;
import excellence.common.key.KeyService;
import excellence.common.page.PageInfo;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;

/**
 * <p>
 * 客户管理
 * </p>
 * 
 * @version 2008-03-19
 * @author nie
 */

public class PhoneinfoServiceImpl implements PhoneinfoService {

	private BaseDAO dao = null;

	private int num = 0;

	private int phonenum = 0;

	private List questionList = null;

	public KeyService ks = null;

	private ClassTreeService cts = null;

	/**
	 * 查询数据列表,返回记录的list。 取得查询问题列表数据。
	 * 
	 * @param dto
	 *            数据传输对象
	 * @param pi
	 *            页面信息
	 * @return 数据的list
	 */
	public List phoneinfoQuery(IBaseDTO dto, PageInfo pi) {

		List list = new ArrayList();
		PhoneinfoHelp ph = new PhoneinfoHelp();

		Object[] result = (Object[]) dao.findEntity(ph.custinfoQuery(dto, pi));
		// System.out.println(result.length+" ..... ");
		num = dao.findEntitySize(ph.custinfoQuery(dto, pi));

		for (int i = 0, size = result.length; i < size; i++) {
			OperCustinfo po = (OperCustinfo) result[i];
			DynaBeanDTO dbd = new DynaBeanDTO();

			dbd.set("id", po.getCustId());
			String custName = po.getCustName();
			if (custName.length()>=3) {
				custName = custName.substring(0, 3);
			}
			dbd.set("name", custName);
			String phone = "";
			if (po.getCustTelHome() != null) {
				if (!po.getCustTelHome().equals(""))
					phone = po.getCustTelHome();
			}
			if (po.getCustTelMob() != null) {
				if (!po.getCustTelMob().equals(""))
					phone = po.getCustTelMob();
			}
			if (po.getCustTelWork() != null) {
				if (!po.getCustTelWork().equals(""))
					phone = po.getCustTelWork();
			}
			dbd.set("phone", phone);
			dbd.set("cust_addr", po.getCustAddr());
			dbd.set("type", po.getCustName());
			if (po.getDictCustType() != null) {
				String custType = po.getDictCustType();
				try
				{
				String type = cts.getLabelById(custType);
				dbd.set("cust_type", type);
				}
				catch(Exception e)
				{
					System.out.println(e.getMessage());
					System.out.println("####ctsGetLabelById ERROR####:custType"+custType);
				}
				dbd.set("dict_cust_type", custType);
			}
			dbd.set("cust_rid", po.getCustRid());

			list.add(dbd);
		}
		return list;
	}

	/**
	 * 
	 */
	public List phoneinfoAllQuery() {

		List list = new ArrayList();
		PhoneinfoHelp h = new PhoneinfoHelp();

		Object[] result = (Object[]) dao.findEntity(h.allQuery());

		for (int i = 0, size = result.length; i < size; i++) {
			OperCustinfo po = (OperCustinfo) result[i];
			list.add(ToDynaBeanDTO(po));
		}
		return list;
	}

	/**
	 * 查询方法的 po 转 dto
	 * 
	 * @param po
	 * @return dto
	 */
	private DynaBeanDTO ToDynaBeanDTO(OperCustinfo po) {

		DynaBeanDTO dto = new DynaBeanDTO();

		dto.set("cust_id", po.getCustId());
		dto.set("cust_name", po.getCustName());
		dto.set("cust_unit", po.getCustUnit());
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
		dto.set("expert_type", po.getExpertType());
		String cust_type = po.getDictCustType();
		if (cust_type != null && cust_type.length() > 15) {
			cust_type = cts.getLabelById(cust_type);
		}
		dto.set("cust_type", cust_type);
		dto.set("remark", po.getRemark());

		return dto;
	}

	/**
	 * 根据ID取得一条数据的excellence.framework.base.dto.IBaseDTO对象 取得某条数据的详细信息。
	 * 
	 * @param id
	 *            取得excellence.framework.base.dto.IBaseDTO的标识
	 * @return 包含数据信息的excellence.framework.base.dto.IBaseDTO对象
	 */
	public IBaseDTO getPhoneinfo(String id) {
		OperCustinfo po = (OperCustinfo) dao.loadEntity(OperCustinfo.class, id);
		IBaseDTO dto = new DynaBeanDTO();

		dto.set("cust_id", po.getCustId());
		dto.set("dict_cust_type", po.getDictCustType());
		dto.set("cust_rid", po.getCustRid());
		dto.set("cust_server_name", po.getCustServerName());
		dto.set("cust_duty", po.getCustDuty());
		dto.set("cust_nature", po.getCustNature());
		dto.set("cust_pic_name", po.getCustPicName());
		dto.set("cust_pic_path", po.getCustPicPath());
		dto.set("cust_name", po.getCustName());
		dto.set("cust_unit", po.getCustUnit());
		dto.set("cust_age", po.getCustAge());
		dto.set("cust_degree", po.getCustDegree());
		dto.set("expert_type", po.getExpertType());
		dto.set("dict_cust_voc", po.getDictCustVoc());
		dto.set("cust_tel_home", po.getCustTelHome());
		dto.set("cust_tel_mob", po.getCustTelMob());
		dto.set("cust_tel_work", po.getCustTelWork());
		dto.set("cust_fax", po.getCustFax());
		dto.set("cust_addr", po.getCustAddr());
		dto.set("cust_pcode", po.getCustPcode());
		dto.set("cust_email", po.getCustEmail());
		dto.set("cust_homepage", po.getCustHomepage());
		String dictCustScale = po.getDictCustScale();
		if(dictCustScale.indexOf("SYS_TREE") != -1){
			dictCustScale = cts.getLabelById(dictCustScale);
		}
		dto.set("dict_cust_scale", dictCustScale);
		dto.set("work_item", po.getWorkItem());
		dto.set("enterprise_intru", po.getEnterpriseIntru());
		dto.set("cust_banner", po.getCustBanner());		
		dto.set("enterprise_net", po.getEnterpriseNet());
		dto.set("cust_way_by", po.getCustWayBy());
		dto.set("cust_develop_time", TimeUtil.getTheTimeStr(po.getCustDevelopTime(),"yyyy-MM-dd"));
		dto.set("cust_positive_time", TimeUtil.getTheTimeStr(po.getCustPositiveTime(),"yyyy-MM-dd"));
		dto.set("cust_job", po.getCustJob());
		dto.set("remark", po.getRemark());
		
		dto.set("custLinkmanName", po.getCustLinkmanName());
		dto.set("cust_intru", po.getCustIntru());
		
		dto.set("dict_sex", po.getDictSex());
		dto.set("cust_idcard", po.getCustIdentityCard());
		dto.set("is_eliminate", po.getIsEliminate());
		dto.set("eliminate_reason", po.getEliminateReason());
		dto.set("cust_work_way", po.getCustWorkWay());
		dto.set("cust_number", po.getCustNumber());
		return dto;
	}

	/**
	 * 修改数据。 修改某条记录的内容。
	 * 
	 * @param dto
	 *            要更新的的excellence.framework.base.dto.IBaseDTO对象
	 * @return boolean
	 */
	public boolean updatePhoneinfo(IBaseDTO dto) {
		try {
//			dao.saveEntity(modifycoll(dto));
			dao.updateEntity(modifycoll(dto));
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
	private OperCustinfo modifycoll(IBaseDTO dto) {

		OperCustinfo po = (OperCustinfo) dao.loadEntity(OperCustinfo.class, dto
				.get("cust_id").toString());// 根据主键修改表
System.out.println("cust_id=="+dto.get("cust_id").toString());
		// 标记删除字段为"0"
		po.setIsDelete("0");
		
		po.setDictCustType(dto.get("dict_cust_type").toString());
		po.setCustName(dto.get("cust_name").toString());
		po.setCustUnit(dto.get("cust_unit").toString());	
		po.setCustId(dto.get("cust_id").toString());
		po.setCustRid(dto.get("cust_rid").toString());
		po.setCustDuty(dto.get("cust_duty").toString());
		po.setCustPicName(dto.get("cust_pic_name").toString());
		
		if(dto.get("cust_pic_path")!=null)
			po.setCustPicPath(dto.get("cust_pic_path").toString());
		
		po.setCustDegree(dto.get("cust_degree").toString());
		po.setDictCustVoc(dto.get("dict_cust_voc").toString());
		po.setCustTelHome(dto.get("cust_tel_home").toString());
		po.setCustTelMob(dto.get("cust_tel_mob").toString());
		po.setCustTelWork(dto.get("cust_tel_work").toString());
		po.setCustFax(dto.get("cust_fax").toString());
		po.setExpertType(dto.get("expert_type").toString());
			
		po.setCustAddr(dto.get("cust_addr").toString());
		po.setCustPcode(dto.get("cust_pcode").toString());
		po.setCustEmail(dto.get("cust_email").toString());
		po.setCustHomepage(dto.get("cust_homepage").toString());
		po.setDictCustScale(dto.get("dict_cust_scale").toString());
		po.setWorkItem(dto.get("work_item").toString());
		
		if(dto.get("cust_banner")!=null)
			po.setCustBanner(dto.get("cust_banner").toString());
		
		po.setEnterpriseNet(dto.get("enterprise_net").toString());
		po.setCustWayBy(dto.get("cust_way_by").toString());
		// po.setCustDevelopTime(dto.get("cust_develop_time").toString());
		po.setCustJob(dto.get("cust_job").toString());
		po.setRemark(dto.get("remark").toString());
		
		po.setCustAge(dto.get("cust_age").toString());
		po.setEnterpriseIntru(dto.get("enterprise_intru").toString());
		po.setCustDevelopTime(TimeUtil.getTimeByStr(dto.get("cust_develop_time").toString(),"yyyy-MM-dd"));
		po.setCustPositiveTime(TimeUtil.getTimeByStr(dto.get("cust_positive_time").toString(),"yyyy-MM-dd"));
		
		po.setCustIntru(dto.get("cust_intru").toString());
		po.setCustLinkmanName(dto.get("custLinkmanName").toString());
		
		return po;
	}

	/**
	 * 删除数据。 删除某条记录。
	 * 
	 * @param id
	 *            要删除数据的标识
	 */
	public void delPhoneinfo(String id) {

		OperCustinfo cq = (OperCustinfo) dao.loadEntity(OperCustinfo.class, id);
		dao.removeEntity(cq);

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
	public void addPhoneinfo(IBaseDTO dto) {
		dao.saveEntity(createPhoneinfo(dto));
		System.out.println("phoneinfo add in to db .......");
	}

	/**
	 * 添加方法的 dto 转 po
	 * 
	 * @param dto
	 * @return po
	 */
	private OperCustinfo createPhoneinfo(IBaseDTO dto) {
		OperCustinfo oc = new OperCustinfo();

		// 生成ID
		String custId = ks.getNext("oper_custinfo");
		System.out.println("custid " + custId);
		oc.setCustId(custId);
		// oc.setCustId(custId);
		// 标记删除字段为"0"
		oc.setIsDelete("0");

		oc.setDictCustType(dto.get("dict_cust_type").toString());
		oc.setCustName(dto.get("cust_name").toString());
		oc.setCustRid(dto.get("cust_rid").toString());
		oc.setCustDuty(dto.get("cust_duty").toString());
		oc.setCustPicName(dto.get("cust_pic_name").toString());
		oc.setCustPicPath(dto.get("cust_pic_path").toString());
		oc.setCustAge(dto.get("cust_age").toString());
		oc.setCustDegree(dto.get("cust_degree").toString());
		oc.setDictCustVoc(dto.get("dict_cust_voc").toString());
		oc.setCustTelHome(dto.get("cust_tel_home").toString());
		oc.setCustTelMob(dto.get("cust_tel_mob").toString());
		oc.setCustTelWork(dto.get("cust_tel_work").toString());
		oc.setExpertType(dto.get("expert_type").toString());
		oc.setCustFax(dto.get("cust_fax").toString());
		oc.setCustAddr(dto.get("cust_addr").toString());
		oc.setCustPcode(dto.get("cust_pcode").toString());
		oc.setCustEmail(dto.get("cust_email").toString());
		oc.setCustHomepage(dto.get("cust_homepage").toString());
		oc.setDictCustScale(dto.get("dict_cust_scale").toString());
		oc.setWorkItem(dto.get("work_item").toString());
		oc.setCustBanner(dto.get("cust_banner").toString());
		oc.setEnterpriseNet(dto.get("enterprise_net").toString());
		oc.setCustWayBy(dto.get("cust_way_by").toString());
		// po.setCustDevelopTime(dto.get("cust_develop_time").toString());
		oc.setCustJob(dto.get("cust_job").toString());
		oc.setRemark(dto.get("remark").toString());		
		
		oc.setEnterpriseIntru(dto.get("enterprise_intru").toString());
		oc.setCustUnit(dto.get("cust_unit").toString());
		oc.setCustDevelopTime(TimeUtil.getTimeByStr(dto.get("cust_develop_time").toString(),"yyyy-MM-dd"));
		oc.setCustPositiveTime(TimeUtil.getTimeByStr(dto.get("cust_positive_time").toString(),"yyyy-MM-dd"));
		oc.setAddtime(TimeUtil.getNowTime());
		oc.setCustLinkmanName(dto.get("custLinkmanName").toString());
		oc.setCustIntru(dto.get("cust_intru").toString());
		
		return oc;
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

	public ClassTreeService getCts() {
		return cts;
	}

	public void setCts(ClassTreeService cts) {
		this.cts = cts;
	}

	public int phoneSize() {
		// TODO Auto-generated method stub
		return num;
	}
	
	/**
	 * 取得专家列表
	 */
	public String getExpertList(String expertType) {
		// TODO Auto-generated method stub
		StringBuilder expertLsit = new StringBuilder();
		MyQuery mq = new MyQueryImpl();
		DetachedCriteria dc = DetachedCriteria.forClass(OperCustinfo.class);
		dc.add(Restrictions.like("expertType", "%" + expertType + "%"));
		mq.setDetachedCriteria(dc);
		Object[] result = (Object[]) dao.findEntity(mq);
		for (int i = 0; i < result.length; i++) {
			OperCustinfo oc = (OperCustinfo)result[i];
			expertLsit.append(oc.getCustName());
			expertLsit.append(":");
		}
		return expertLsit.toString();
	}
	
	/**
	 * 联络员批量添加
	 */
	public boolean addCountPhoneInfo(String excelPath) {
		try {
			InputStream is = new FileInputStream(excelPath);// 写入到FileInputStream
			jxl.Workbook wb = Workbook.getWorkbook(is); // 得到工作薄
			jxl.Sheet st = wb.getSheet(0);// 得到工作薄中的第一个工作表
			int rows = st.getRows();
			int columns = st.getColumns();
			System.out.println(rows+"   "+columns);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			for (int i = 1; i < rows; i++) {
				OperCustinfo oc = new OperCustinfo();
				String flag = "true";
				for (int j = 0; j < columns; j++) {
					Cell cell = st.getCell(j, i);// 得到工作表的第一个单元格,即A1 0列0行
					String content = cell.getContents();// getContents()将Cell中的字符转为字符串
					switch (j) {
					case 0:
						oc.setCustRid(content.trim());  // 座席号
						break;
					case 1:
						oc.setCustName(content.trim());  // 联络员姓名
						break;
					case 2:
						if("男".equals(content.trim())) {
							oc.setDictSex("SYS_TREE_0000000663");
						} else if("女".equals(content.trim())) {
							oc.setDictSex("SYS_TREE_0000000664");
						} else {
							oc.setDictSex("");
						}
						break;	
					case 3:
						oc.setCustAddr(content.trim());
						break;
					case 4:
						oc.setCustIdentityCard(content.trim());
						break;
					case 5:
						oc.setCustPcode(content.trim());
						break;
					case 6:
						oc.setCustTelHome(content.trim());
						oc.setCustTelMob(content.trim());
						oc.setCustTelWork(content.trim());
						break;
					case 7:
						oc.setDictCustType("SYS_TREE_0000000683");
						break;
					case 8:
//					oc.setWorkItem(content.trim());
						oc.setWorkItem("SYS_TREE_0000002522");  // 从事行业 目前只有农业
						break;
					case 9:
						oc.setCustDevelopTime(sdf.parse("20"+content.trim()));  // 发展时间
//						oc.setCustDevelopTime(sdf.parse(sdf..format(content.trim())));
						System.out.println("date is "+sdf.format(content.trim()));
						break;
					case 10:
						oc.setRemark(content.trim());
						break;
					default:
						break;
					}
				}
				if(flag.equals("true")){
					oc.setCustId(ks.getNext("oper_custinfo"));
					System.out.println("id is "+oc.getCustId()+" rid is "+oc.getCustRid()+" name is "+oc.getCustName());
					dao.saveEntity(oc);
				}
				
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (BiffException e) {
			e.printStackTrace();
			return false;
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
		return true;
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
	
/*开始更改*/
	
	/**
	 * 查询数据列表,返回记录的list。 取得查询问题列表数据。
	 * 
	 * @param dto
	 *            数据传输对象
	 * @param pi
	 *            页面信息
	 * @return 数据的list
	 */
	public List phoneinfoQuery2(IBaseDTO dto, PageInfo pi) {

		List list = new ArrayList();
		PhoneinfoHelp ph = new PhoneinfoHelp();

		Object[] result = (Object[]) dao.findEntity(ph.custinfoQuery2(dto, pi));
		// System.out.println(result.length+" ..... ");
		num = dao.findEntitySize(ph.custinfoQuery2(dto, pi));

		for (int i = 0, size = result.length; i < size; i++) {
			OperCustinfo po = (OperCustinfo) result[i];
			DynaBeanDTO dbd = new DynaBeanDTO();

			dbd.set("id", po.getCustId());
			String custName = po.getCustName();
			if (custName.length()>=4) {
				custName = custName.substring(0, 4);
			}
			dbd.set("name", custName);
			String phone = "";
			if (po.getCustTelHome() != null) {
				if (!po.getCustTelHome().equals(""))
					phone = po.getCustTelHome();
			}
			if (po.getCustTelMob() != null) {
				if (!po.getCustTelMob().equals(""))
					phone = po.getCustTelMob();
			}
			if (po.getCustTelWork() != null) {
				if (!po.getCustTelWork().equals(""))
					phone = po.getCustTelWork();
			}
			dbd.set("phone", phone);
			dbd.set("cust_addr", po.getCustAddr());
			dbd.set("cust_develop_time",TimeUtil.getTheTimeStr(po.getCustDevelopTime()));
			if (po.getDictCustType() != null) {
				String custType = po.getDictCustType();
				try
				{
				String type = cts.getLabelById(custType);
				dbd.set("cust_type", type);
				}
				catch(Exception e)
				{
					System.out.println(e.getMessage());
					System.out.println("####ctsGetLabelById ERROR####:custType"+custType);
				}
				dbd.set("dict_cust_type", custType);
			}
			dbd.set("cust_rid", po.getCustRid());
			
			list.add(dbd);
		}
		return list;
	}
	
	/**
	 * 添加数据。 向数据库中添加一条记录。
	 * 
	 * @param dto
	 *            新数据的excellence.framework.base.dto.IBaseDTO对象
	 */
	public void addLinkManinfo(IBaseDTO dto) {
		OperCustinfo oc2 = createLinkManinfo(dto);
		dao.saveEntity(createLinkManinfo(dto));
		System.out.println("LinkManinfo add in to db .......");
	}

	/**
	 * 添加方法的 dto 转 po
	 * 
	 * @param dto
	 * @return po
	 */
	private OperCustinfo createLinkManinfo(IBaseDTO dto) {
		OperCustinfo oc = new OperCustinfo();

		// 生成ID
		String custId = ks.getNext("oper_custinfo");
		System.out.println("custid " + custId);
		oc.setCustId(custId);
		// oc.setCustId(custId);
		// 标记删除字段为"0"
		oc.setIsDelete("0");

		oc.setDictCustType("SYS_TREE_0000002108");
		oc.setCustName(dto.get("cust_name").toString());
		oc.setCustRid(dto.get("cust_rid").toString());
		oc.setCustDuty(dto.get("cust_duty").toString());
		oc.setCustPicName(dto.get("cust_pic_name").toString());
		oc.setCustPicPath(dto.get("cust_pic_path").toString());
		oc.setCustAge(dto.get("cust_age").toString());
		oc.setCustDegree(dto.get("cust_degree").toString());
		oc.setDictCustVoc(dto.get("dict_cust_voc").toString());
		oc.setCustTelHome(dto.get("cust_tel_home").toString());
		oc.setCustTelMob(dto.get("cust_tel_home").toString());
		oc.setCustTelWork(dto.get("cust_tel_home").toString());
		oc.setExpertType(dto.get("expert_type").toString());
		oc.setCustFax(dto.get("cust_fax").toString());
		oc.setCustAddr(dto.get("cust_addr").toString());
		oc.setCustPcode(dto.get("cust_pcode").toString());
		oc.setCustEmail(dto.get("cust_email").toString());
		oc.setCustHomepage(dto.get("cust_homepage").toString());
		oc.setDictCustScale(dto.get("dict_cust_scale").toString());
		oc.setWorkItem(dto.get("work_item").toString());
		oc.setCustBanner(dto.get("cust_banner").toString());
//		oc.setExpertType(dto.get("enterprise_net").toString());
		oc.setCustWayBy(dto.get("cust_way_by").toString());
		// po.setCustDevelopTime(dto.get("cust_develop_time").toString());
		oc.setCustJob(dto.get("cust_job").toString());
		oc.setRemark(dto.get("remark").toString());
		
		oc.setCustBanner(dto.get("cust_banner").toString());
		oc.setEnterpriseIntru(dto.get("enterprise_intru").toString());
		oc.setCustUnit(dto.get("cust_unit").toString());
		oc.setCustDevelopTime(TimeUtil.getTimeByStr(dto.get("cust_develop_time").toString(),"yyyy-MM-dd"));
		oc.setCustPositiveTime(TimeUtil.getTimeByStr(dto.get("cust_positive_time").toString(),"yyyy-MM-dd"));
		oc.setAddtime(TimeUtil.getNowTime());
		
		oc.setDictSex(dto.get("dict_sex").toString());
		oc.setCustIdentityCard(dto.get("cust_idcard").toString());
		oc.setIsEliminate(dto.get("is_eliminate").toString());
		oc.setEliminateReason(dto.get("eliminate_reason").toString());
		oc.setCustWorkWay(dto.get("cust_work_way").toString());
		oc.setCustNumber(dto.get("cust_number").toString());
		
		
		return oc;
	}
	/**
	 * 修改数据。 修改某条记录的内容。
	 * 
	 * @param dto
	 *            要更新的的excellence.framework.base.dto.IBaseDTO对象
	 * @return boolean
	 */
	public boolean updateLinkManinfo(IBaseDTO dto) {
		try {
//			dao.saveEntity(modifycoll(dto));
			dao.updateEntity(modifycoll2(dto));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}

	/**
	 * 修改方法的 dto 转 po
	 * 
	 * @param dto
	 * @return po
	 */
	private OperCustinfo modifycoll2(IBaseDTO dto) {
		OperCustinfo po = (OperCustinfo) dao.loadEntity(OperCustinfo.class, dto
				.get("cust_id").toString());// 根据主键修改表
		
		// 标记删除字段为"0"
		po.setIsDelete("0");
		po.setDictCustType("SYS_TREE_0000002108");
		po.setCustName(dto.get("cust_name").toString());
		po.setCustUnit(dto.get("cust_unit").toString());	
		po.setCustFax(dto.get("cust_fax").toString());
		po.setCustId(dto.get("cust_id").toString());
		po.setCustRid(dto.get("cust_rid").toString());
		po.setCustDuty(dto.get("cust_duty").toString());
//		po.setCustPicName(dto.get("cust_pic_name").toString());
//		po.setCustPicPath(dto.get("cust_pic_path").toString());
		po.setCustDegree(dto.get("cust_degree").toString());
		po.setDictCustVoc(dto.get("dict_cust_voc").toString());
		
		po.setCustFax(dto.get("cust_fax").toString());
		
		po.setExpertType(dto.get("expert_type").toString());
		
		
		po.setCustPcode(dto.get("cust_pcode").toString());
		po.setCustEmail(dto.get("cust_email").toString());
		po.setCustHomepage(dto.get("cust_homepage").toString());
		po.setDictCustScale(dto.get("dict_cust_scale").toString());
		po.setWorkItem(dto.get("work_item").toString());
		po.setCustBanner(dto.get("cust_banner").toString());
//		po.setExpertType(dto.get("enterprise_net").toString());
		po.setCustWayBy(dto.get("cust_way_by").toString());
		// po.setCustDevelopTime(dto.get("cust_develop_time").toString());
		po.setCustJob(dto.get("cust_job").toString());
		po.setRemark(dto.get("remark").toString());
		po.setCustAddr(dto.get("cust_addr").toString());
		po.setCustAge(dto.get("cust_age").toString());
		po.setCustTelHome(dto.get("cust_tel_home").toString());
		po.setCustTelMob(dto.get("cust_tel_home").toString());
		po.setCustTelWork(dto.get("cust_tel_home").toString());
		po.setCustBanner(dto.get("cust_banner").toString());
		po.setEnterpriseIntru(dto.get("enterprise_intru").toString());
		po.setCustDevelopTime(TimeUtil.getTimeByStr(dto.get("cust_develop_time").toString(),"yyyy-MM-dd"));
		po.setCustPositiveTime(TimeUtil.getTimeByStr(dto.get("cust_positive_time").toString(),"yyyy-MM-dd"));
		
		po.setDictSex(dto.get("dict_sex").toString());
		po.setCustIdentityCard(dto.get("cust_idcard").toString());
		po.setIsEliminate(dto.get("is_eliminate").toString());
		po.setEliminateReason(dto.get("eliminate_reason").toString());
		po.setCustWorkWay(dto.get("cust_work_way").toString());
		po.setCustNumber(dto.get("cust_number").toString());
		
		return po;
	}
/*开始更改*/
}
