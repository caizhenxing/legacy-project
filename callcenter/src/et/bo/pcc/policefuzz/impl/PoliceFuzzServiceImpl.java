/**
 * 	@(#)PoliceFuzzServiceImpl.java   Oct 9, 2006 2:31:05 PM
 *	 。 
 *	 
 */
package et.bo.pcc.policefuzz.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jxl.Workbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import et.bo.pcc.policefuzz.PoliceFuzzService;
import et.bo.pcc.policeinfo.impl.InfoSearch;
import et.bo.sys.user.action.Password_encrypt;
import et.po.PoliceFuzzInfo;
import excellence.common.classtree.ClassTreeService;
import excellence.common.key.KeyService;
import excellence.common.page.PageInfo;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

/**
 * @author zhang
 * @version Oct 9, 2006
 * @see
 */
public class PoliceFuzzServiceImpl implements PoliceFuzzService {

	private BaseDAO dao = null;

	private KeyService ks = null;

	private int FUZZ_NUM = 0;

	private int COUNT_NUM = 0;

	private ClassTreeService depTree = null;

	private ClassTreeService ctree = null;

	private String TOMCAT_ADDRESS = "http://10.79.1.6:8089/callcenter/excel/";

	private String CREATE_FILE_PATH = "F:/tomcat5/webapps/callcenter/excel/";

	// 不删除
	private String NOT_DEL = "N";

	// 删除
	private String YES_DEL = "Y";

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

	// 添加
	private PoliceFuzzInfo createFuzzInfo(IBaseDTO dto) {
		PoliceFuzzInfo pf = new PoliceFuzzInfo();
		pf.setId(ks.getNext("police_fuzz_info"));
		pf.setFuzzNo((String) dto.get("fuzzNo"));
		pf.setName((String) dto.get("name"));
		pf.setSex((String) dto.get("sex"));
		pf.setBirthday((String) dto.get("birthday"));
		Password_encrypt pe = new Password_encrypt();
		pf.setPassword(pe.pw_encrypt((String) dto.get("password")));
		pf.setMobileTel((String) dto.get("mobileTel"));
		pf.setTagPoliceKind((String) dto.get("tagPoliceKind"));
		pf.setTagUnit((String) dto.get("tagUnit"));
		pf.setTagArea((String) dto.get("tagArea"));
		pf.setWorkTime(TimeUtil.getTimeByStr((String) dto.get("workontime"),
				"yyyy-MM-dd"));
		pf.setIdCard((String) dto.get("idcard"));
		pf.setPersonType((String) dto.get("personstate"));
		pf.setDuty((String) dto.get("duty"));
		// pf.setTagFreeze((String)dto.get("tagFreeze"));
		pf.setTagDel(NOT_DEL);
		return pf;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see et.bo.pcc.policefuzz.PoliceFuzzService#addFuzzInfo(excellence.framework.base.dto.IBaseDTO)
	 */
	public boolean addFuzzInfo(IBaseDTO dto) {
		// TODO Auto-generated method stub
		boolean flag = false;
		dao.saveEntity(createFuzzInfo(dto));
		flag = true;
		return flag;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see et.bo.pcc.policefuzz.PoliceFuzzService#bookIndex(excellence.framework.base.dto.IBaseDTO,
	 *      excellence.common.page.PageInfo)
	 */
	public List fuzzIndex(IBaseDTO dto, PageInfo pageInfo) {
		// TODO Auto-generated method stub
		List l = new ArrayList();
		FuzzSearch fuzzSearch = new FuzzSearch();
		Object[] result = (Object[]) dao.findEntity(fuzzSearch.searchFuzzList(
				dto, pageInfo));
		int s = dao.findEntitySize(fuzzSearch.searchFuzzList(dto, pageInfo));
		FUZZ_NUM = s;
		for (int i = 0, size = result.length; i < size; i++) {
			PoliceFuzzInfo policeFuzzInfo = (PoliceFuzzInfo) result[i];
			DynaBeanDTO dbd = new DynaBeanDTO();
			dbd.set("id", policeFuzzInfo.getId());
			dbd.set("fuzzno", policeFuzzInfo.getFuzzNo());
			dbd.set("name", policeFuzzInfo.getName());
			dbd.set("birthday", policeFuzzInfo.getBirthday());
			dbd.set("mobiletel", policeFuzzInfo.getMobileTel());
			dbd.set("duty", depTree.getvaluebyId(policeFuzzInfo.getTagUnit()));
			l.add(dbd);
		}
		return l;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see et.bo.pcc.policefuzz.PoliceFuzzService#delFuzzInfo(excellence.framework.base.dto.IBaseDTO)
	 */
	public boolean delFuzzInfo(IBaseDTO dto) {
		// TODO Auto-generated method stub
		boolean flag = false;
		PoliceFuzzInfo pf = (PoliceFuzzInfo) dao.loadEntity(
				PoliceFuzzInfo.class, dto.get("id").toString());
		pf.setTagDel(YES_DEL);
		// dao.removeEntity(pf);
		dao.updateEntity(pf);
		flag = true;
		return flag;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see et.bo.pcc.policefuzz.PoliceFuzzService#getBookSize()
	 */
	public int getFuzzSize() {
		// TODO Auto-generated method stub
		return FUZZ_NUM;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see et.bo.pcc.policefuzz.PoliceFuzzService#getFuzzInfo(java.lang.String)
	 */
	public IBaseDTO getFuzzInfo(String id) {
		// TODO Auto-generated method stub
		IBaseDTO dto = new DynaBeanDTO();
		PoliceFuzzInfo pf = (PoliceFuzzInfo) dao.loadEntity(
				PoliceFuzzInfo.class, id);
		dto.set("id", pf.getId());
		dto.set("fuzzNo", pf.getFuzzNo());
		dto.set("name", pf.getName());
		dto.set("sex", pf.getSex());
		dto.set("birthday", pf.getBirthday());
		Password_encrypt pe = new Password_encrypt();
		dto.set("password", pe.pw_encrypt(pf.getPassword()));
		dto.set("repassword", pe.pw_encrypt(pf.getPassword()));
		dto.set("mobileTel", pf.getMobileTel());
		dto.set("tagPoliceKind", pf.getTagPoliceKind());
		dto.set("tagUnit", pf.getTagUnit());
		dto.set("workontime", TimeUtil.getTimeByStr((String) dto
				.get("workontime")));
		dto.set("idcard", pf.getIdCard());
		dto.set("personstate", pf.getPersonType());
		dto.set("tagArea", pf.getTagArea());
		dto.set("duty", pf.getDuty());
		// dto.set("tagFreeze", pf.getTagFreeze());
		dto.set("tagDel", pf.getTagDel());
		return dto;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see et.bo.pcc.policeinfo.PoliceInfoService#getPoliceInfo(java.lang.String)
	 */
	public IBaseDTO getPoliceInfo(String policeNum) {
		// TODO Auto-generated method stub
		IBaseDTO dto = new DynaBeanDTO();
		InfoSearch infoSearch = new InfoSearch();
		Object[] result = (Object[]) dao.findEntity(infoSearch
				.searchFuzzInfo(policeNum));
		for (int i = 0, size = result.length; i < size; i++) {
			PoliceFuzzInfo pf = (PoliceFuzzInfo) result[i];
			dto.set("id", pf.getId());
			dto.set("fuzzNo", pf.getFuzzNo());
			dto.set("name", pf.getName());
			dto.set("sex", pf.getSex());
			dto.set("birthday", pf.getBirthday());
			dto.set("password", pf.getPassword());
			dto.set("repassword", pf.getPassword());
			dto.set("mobileTel", pf.getMobileTel());
			dto.set("tagPoliceKind", pf.getPersonType());
			dto.set("tagArea", pf.getTagArea());
			dto.set("duty", pf.getDuty());
			dto.set("tagFreeze", pf.getTagFreeze());
			dto.set("tagDel", pf.getTagDel());
		}
		return dto;
	}

	// 修改
	private PoliceFuzzInfo upFuzzInfo(IBaseDTO dto) {
		PoliceFuzzInfo pf = new PoliceFuzzInfo();
		pf.setId(dto.get("id").toString());
		pf.setFuzzNo(dto.get("fuzzNo").toString());
		pf.setName(dto.get("name").toString());
		pf.setSex(dto.get("sex").toString());
		pf.setBirthday(dto.get("birthday").toString());
		Password_encrypt pe = new Password_encrypt();
		pf.setPassword(pe.pw_encrypt(dto.get("password").toString()));
		pf.setMobileTel(dto.get("mobileTel").toString());
		pf.setTagPoliceKind(dto.get("tagPoliceKind").toString());
		pf.setTagUnit((String) dto.get("tagUnit"));
		pf.setTagArea((String) dto.get("tagArea"));
		pf.setWorkTime(TimeUtil.getTimeByStr((String) dto.get("workontime"),
				"yyyy-MM-dd"));
		pf.setIdCard((String) dto.get("idcard"));
		pf.setPersonType((String) dto.get("personstate"));
		pf.setDuty((String) dto.get("duty"));
		// pf.setTagFreeze(dto.get("tagFreeze").toString());
		pf.setTagDel(NOT_DEL);
		return pf;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see et.bo.pcc.policefuzz.PoliceFuzzService#updateFuzzInfo(excellence.framework.base.dto.IBaseDTO)
	 */
	public boolean updateFuzzInfo(IBaseDTO dto) {
		// TODO Auto-generated method stub
		boolean flag = false;
		dao.updateEntity(upFuzzInfo(dto));
		flag = true;
		return flag;
	}

	public boolean checkPoliceNum(String policeNum) {
		// TODO Auto-generated method stub
		boolean flag = false;
		FuzzSearch fuzzSearch = new FuzzSearch();
		Object[] result = (Object[]) dao.findEntity(fuzzSearch
				.searchFuzzList(policeNum));
		for (int i = 0, size = result.length; i < size; i++) {
			flag = true;
		}
		return flag;
	}

	public boolean updatePoliceNum(IBaseDTO dto) {
		// TODO Auto-generated method stub
		boolean flag = false;
		FuzzSearch fuzzSearch = new FuzzSearch();
		String policeNum = dto.get("fuzzNo").toString();
		Object[] result = (Object[]) dao.findEntity(fuzzSearch
				.searchFuzzInfo(policeNum));
		for (int i = 0, size = result.length; i < size; i++) {
			PoliceFuzzInfo pf = (PoliceFuzzInfo) result[i];
			String id = pf.getId();
			PoliceFuzzInfo pfinfo = (PoliceFuzzInfo) dao.loadEntity(
					PoliceFuzzInfo.class, id);
			Password_encrypt pe = new Password_encrypt();
			pfinfo.setPassword(pe.pw_encrypt(dto.get("password").toString()));
			dao.updateEntity(pfinfo);
			flag = true;
		}
		return flag;
	}

	public void addPoc(PoliceFuzzInfo pf) {
		// TODO Auto-generated method stub
		dao.saveEntity(pf);
	}

	public ClassTreeService getDepTree() {
		return depTree;
	}

	public void setDepTree(ClassTreeService depTree) {
		this.depTree = depTree;
	}

	public List countIndex(IBaseDTO dto, PageInfo pageInfo) {
		// TODO Auto-generated method stub
		List l = new ArrayList();
		FuzzSearch fuzzSearch = new FuzzSearch();
		Object[] result = (Object[]) dao.findEntity(fuzzSearch.searchCountList(
				dto, pageInfo));
		int s = dao.findEntitySize(fuzzSearch.searchCountList(dto, pageInfo));
		COUNT_NUM = s;
		for (int i = 0, size = result.length; i < size; i++) {
			PoliceFuzzInfo policeFuzzInfo = (PoliceFuzzInfo) result[i];
			DynaBeanDTO dbd = new DynaBeanDTO();
			dbd.set("id", policeFuzzInfo.getId());
			dbd.set("fuzzno", policeFuzzInfo.getFuzzNo());
			dbd.set("name", policeFuzzInfo.getName());
			dbd.set("birthday", policeFuzzInfo.getBirthday());
			dbd.set("mobiletel", policeFuzzInfo.getMobileTel());
			dbd.set("duty", depTree.getvaluebyId(policeFuzzInfo.getTagUnit()));
			l.add(dbd);
		}
		return l;
	}

	public int getCountSize() {
		// TODO Auto-generated method stub
		return COUNT_NUM;
	}

	public ClassTreeService getCtree() {
		return ctree;
	}

	public void setCtree(ClassTreeService ctree) {
		this.ctree = ctree;
	}

	public String getRemoateFile(IBaseDTO dto) throws FileNotFoundException,
			IOException, RowsExceededException, WriteException {
		// TODO Auto-generated method stub
		String finalUrl = "";
		
		String filename = 
		TimeUtil.getTheTimeStr(new Date(),"yyyyMMddMMHHSS") + ".xls";
		FileOutputStream os = new FileOutputStream(new File(CREATE_FILE_PATH+filename));
		jxl.write.WritableWorkbook wwb = Workbook.createWorkbook(os);
		jxl.write.WritableSheet ws = wwb.createSheet(TimeUtil.getTheTimeStr(new Date(),"yyyyMMddMMHHSS"), 0);
		jxl.write.Label labelC = new jxl.write.Label(0, 0, "警号");
		ws.addCell(labelC);
		labelC = new jxl.write.Label(1, 0, "姓名");
		ws.addCell(labelC);
		labelC = new jxl.write.Label(2, 0, "性别");
		ws.addCell(labelC);
		labelC = new jxl.write.Label(3, 0, "生日");
		ws.addCell(labelC);
		labelC = new jxl.write.Label(4, 0, "身份证");
		ws.addCell(labelC);
		labelC = new jxl.write.Label(5, 0, "单位");
		ws.addCell(labelC);

		FuzzSearch fuzzSearch = new FuzzSearch();
		Object[] result = (Object[]) dao.findEntity(fuzzSearch
				.searchCountList(dto));
		int countsize = dao.findEntitySize(fuzzSearch
				.searchCountList(dto));
		int k = 1;
		for (int i = 0; i < countsize; i++) {
			PoliceFuzzInfo policeFuzzInfo = (PoliceFuzzInfo) result[i];
			labelC = new jxl.write.Label(0, k, policeFuzzInfo.getFuzzNo());
			ws.addCell(labelC);
			labelC = new jxl.write.Label(1, k, policeFuzzInfo.getName());
			ws.addCell(labelC);
			labelC = new jxl.write.Label(2, k, policeFuzzInfo.getSex().equals("1") ? "男" : "女");
			ws.addCell(labelC);
			labelC = new jxl.write.Label(3, k, policeFuzzInfo.getBirthday());
			ws.addCell(labelC);
			labelC = new jxl.write.Label(4, k, policeFuzzInfo.getIdCard());
			ws.addCell(labelC);
			labelC = new jxl.write.Label(5, k, depTree.getvaluebyId(policeFuzzInfo
					.getTagUnit()));
			ws.addCell(labelC);
			k++;
		}

		wwb.write();
		wwb.close();
		finalUrl = TOMCAT_ADDRESS+filename;
		return finalUrl;
	}

}
