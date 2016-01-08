package et.bo.sys.ccIvrTreeVoiceDetail.service.impl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.iflytek.Qtts;

import et.bo.sys.ccIvrTreeVoiceDetail.service.CcIvrTreeVDtlService;
import et.bo.sys.ivr.service.IvrClassTreeService;
import et.po.CcIvrVoiceinfo;
import excellence.common.classtree.ClassTreeService;
import excellence.common.key.KeyService;
import excellence.common.page.PageInfo;
import excellence.common.tools.LabelValueBean;
import excellence.common.util.Constants;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

public class CcIvrTreeVDtlServiceImpl implements CcIvrTreeVDtlService {

	private BaseDAO dao = null;

	private KeyService ks = null;

	private IvrClassTreeService classTreeService;

	private ClassTreeService comClassTreeService;

	private int num = 0;

	public static HashMap hashmap = new HashMap();

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

	private CcIvrVoiceinfo createOperCcIvrVoiceinfoText(IBaseDTO dto) {
		CcIvrVoiceinfo ivi = new CcIvrVoiceinfo();
		// ivi.setId(Integer.parseInt(dto.get("id").toString()));//自动增长
		ivi.setPath((String) dto.get("voicePath"));
		ivi.setRemark((String) dto.get("remark"));
		ivi.setVoicetype("tts");
		return ivi;
	}

	/**
	 * 生成指定的文件夹和音频全路径
	 * 
	 * @param ivrtype
	 * @return
	 */
	private String createVoicePath(String ivrtype) {
		StringBuffer sb = new StringBuffer();
		// 生成指定文件夹下的音频文件
		String ivrmorevoicepath = Constants.getProperty("ivrmorevoicepath");
		sb.append(ivrmorevoicepath);// 指定文件夹，需要提出去
		File f1 = new File(sb.toString());
		if (!f1.exists()) {
			f1.mkdir();
		}
		sb.append("\\");
		sb.append(ivrtype);// 根据IVR业务分类生成文件夹
		File f2 = new File(sb.toString());
		if (!f2.exists()) {
			f2.mkdir();
		}
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss_SSS");
		sb.append("\\");
		sb.append(sdf.format(date));
		sb.append(".wav");
		return sb.toString();
	}

	/**
	 * 生成TTS文件
	 * 
	 * @param path
	 * @param str
	 */
	private void createTTsFile(String path, String str) {
		String serverip = Constants.getProperty("tts_ip");
		Qtts qttsobj = new Qtts();
		TimeUtil tu = new TimeUtil();
		//System.out.println("path: " + path);
		//System.out.println("str: " + str);
		qttsobj.synthesize(str, false, path, serverip, "", "1=3");// ip为服务器ip
		System.out.println("正确生成语音文件......");
		//System.out.println("the end time "
		//		+ tu.getTheTimeStr(new Date(), "yyyyMMddHHmmss"));
	}

	public void addCcIvrTreeInfoText(IBaseDTO dto) {
		// TODO Auto-generated method stub
		String treeId = dto.get("treeId").toString();
		// SYS_TREE_0000000141
		CcIvrVoiceinfo voiceinfo = createOperCcIvrVoiceinfoText(dto);
		//System.out.println("getNickNameById: "
		//		+ comClassTreeService.getNickNameById(treeId));
		//System.out.println("getLabelById: "
		//		+ comClassTreeService.getLabelById(treeId));
		voiceinfo.setIvrType(comClassTreeService.getNickNameById(treeId));
		voiceinfo.setName(comClassTreeService.getLabelById(treeId));
		voiceinfo.setPath(createVoicePath(voiceinfo.getIvrType()));
		createTTsFile(voiceinfo.getPath(), dto.get("playcontent").toString());
		dao.saveEntity(voiceinfo);
	}

	private CcIvrVoiceinfo createOperCcIvrVoiceinfo(IBaseDTO dto) {
		CcIvrVoiceinfo ivi = new CcIvrVoiceinfo();
		ivi.setPath((String) dto.get("voicePath"));
		ivi.setRemark((String) dto.get("remark"));
		return ivi;
	}

	public void addCcIvrTreeInfo(IBaseDTO dto) {
		// TODO Auto-generated method stub
		String treeId = dto.get("treeId").toString();
		//System.out.println("addCcIvrTreeInfo: treeId: " + treeId);
		// SYS_TREE_0000000141
		CcIvrVoiceinfo voiceinfo = createOperCcIvrVoiceinfo(dto);
		//System.out.println("getNickNameById: "
		//		+ comClassTreeService.getNickNameById(treeId));
		//System.out.println("getLabelById: "
		//		+ comClassTreeService.getLabelById(treeId));
		voiceinfo.setIvrType(comClassTreeService.getNickNameById(treeId));
		voiceinfo.setName(comClassTreeService.getLabelById(treeId));
//		int len=voiceinfo.getPath().toString().length();
//		String str=voiceinfo.getPath().toString().substring(1);
//		String savepath="E"+str;
		String savepath=voiceinfo.getPath().toString();
		String sql = "insert into cc_ivr_voiceinfo (ivr_type, name, path, remark, voicetype) values ('"
				+ voiceinfo.getIvrType()
				+ "','"
				+ voiceinfo.getName()
				+ "','"
				+ savepath + "','" + voiceinfo.getRemark() + "','file')";
//		String sql = "insert into cc_ivr_voiceinfo (ivr_type, name, path, remark) values ('"
//			+ voiceinfo.getIvrType()
//			+ "','"
//			+ voiceinfo.getName()
//			+ "','"
//			+ voiceinfo.getPath() + "','" + voiceinfo.getRemark() + "')";
		dao.execute(sql);
	}

	/**
	 * 删除指定物理文件夹下的音频文件
	 * 
	 * @param path
	 */
	private void deleteVoice(String path) {
		File f1 = new File(path);
		if (f1.exists()) {
			f1.delete();
		}
	}

	public void delCcIvrTreeInfo(String id) {
		// TODO Auto-generated method stub
		IBaseDTO ibd = getCcIvrTreevoiceDetail(id);
		String sql = "delete from cc_ivr_voiceinfo where id=" + id;
		//System.out.println(sql + "*********");
		deleteVoice(ibd.get("voicePath").toString());
		dao.execute(sql);
	}

	public boolean updateCcIvrTreeInfo(IBaseDTO dto) {
		CcIvrVoiceinfo info = modifySad(dto);
		dao.updateEntity(info);
		return false;
	}

	private CcIvrVoiceinfo modifySad(IBaseDTO dto) {
		CcIvrVoiceinfo ivi = (CcIvrVoiceinfo) dao.loadEntity(
				CcIvrVoiceinfo.class, Integer
						.parseInt(dto.get("id").toString()));
		String treeId = dto.get("treeId")!=null?dto.get("treeId").toString():"";
		if(!"".equals(treeId.trim()))
		{
			ivi.setIvrType(comClassTreeService.getNickNameById(treeId));
			ivi.setName(comClassTreeService.getLabelById(treeId));
			//System.out.println(ivi.getIvrType()+"####"+ivi.getName());
		}
		String curPath = ivi.getPath()!=null?ivi.getPath():"";
		if(!curPath.equals((String) dto.get("voicePath")))
		{
			ivi.setPath((String)dto.get("voicePath"));
		}
		ivi.setRemark((String) dto.get("remark"));
		ivi.setVoicetype(dto.get("createType").toString());
		if("tts".equals(ivi.getVoicetype())&&!"".equals(ivi.getRemark())){
			String voicePath = dto.get("voicePath").toString();
			deleteVoice(voicePath);
			ivi.setPath(createVoicePath(ivi.getIvrType()));
			createTTsFile(ivi.getPath(), dto.get("remark").toString());
		}
		return ivi;
	}

	public IBaseDTO getCcIvrTreevoiceDetail(String id) {
		// TODO Auto-generated method stub
		//System.out.println("exception id :is " + id);
		CcIvrVoiceinfo osi = (CcIvrVoiceinfo) dao.loadEntity(
				CcIvrVoiceinfo.class, new Integer(id));
		IBaseDTO dto = new DynaBeanDTO();
		dto.set("id", osi.getId());
		dto.set("voicePath", osi.getPath());
		dto.set("ivrtype", osi.getIvrType());
		dto.set("name", osi.getName());
		dto.set("remark", osi.getRemark());
		dto.set("voicetype", osi.getVoicetype());
		return dto;
	}

	public int getCcIvrTreeInfoSize() {
		// TODO Auto-generated method stub
		return num;
	}

	/**
	 * 得到ivr id label List给视图用
	 * 
	 * @return List LabelValueBeanList
	 */
	public List<LabelValueBean> getIvrLVList() {
		// IVRMultiVoice
		return comClassTreeService.getLabelVaList("IVRMultiVoice", false);// ivrNodeRoot
																			// inquiryTypes
	}

	public List operCcIvrTreeInfoList(IBaseDTO dto, PageInfo pi) {
		// TODO Auto-generated method stub

		List list = new ArrayList();

		CcIvrTreeVDtlHelp sh = new CcIvrTreeVDtlHelp();

		Object[] result = (Object[]) dao.findEntity(sh.operCcIvrInfoQuery(dto,
				pi));
		try {
			num = dao.findEntitySize(sh.operCcIvrInfoSizeQuery(dto, pi));
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

		for (int i = 0, size = result.length; i < size; i++) {
			CcIvrVoiceinfo ivi = (CcIvrVoiceinfo) result[i];
			DynaBeanDTO dbd = new DynaBeanDTO();

			dbd.set("id", ivi.getId());
			dbd.set("ivrType", ivi.getIvrType());
			dbd.set("name", ivi.getName());
			dbd.set("path", ivi.getPath());
			dbd.set("remark", ivi.getRemark());
			if (ivi.getIvrType() != null) {
				try {
					dbd.set("treeId", comClassTreeService.getIdByNickname(ivi
							.getIvrType()));
				} catch (Exception e) {

				}
			}
			list.add(dbd);
		}
		return list;
	}

	public IvrClassTreeService getClassTreeService() {
		return classTreeService;
	}

	public void setClassTreeService(IvrClassTreeService classTreeService) {
		this.classTreeService = classTreeService;
	}

	public ClassTreeService getComClassTreeService() {
		return comClassTreeService;
	}

	public void setComClassTreeService(ClassTreeService comClassTreeService) {
		this.comClassTreeService = comClassTreeService;
	}

}
