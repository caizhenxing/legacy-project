/* 包名：    et.bo.output.service.impl
 * 文件名：  OutputFileImpl.java
 * 注释时间：2008-5-27 16:34:29
 * 版权所有：沈阳市卓越科技有限公司。
 */
package et.bo.output.service.impl;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.RowSet;

import jxl.Workbook;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

import et.bo.common.DbHtml2Text;
import et.bo.output.service.OutputService;
import et.po.OperBookMedicinfo;
import et.po.OperCaseinfo;
import et.po.OperCorpinfo;
import et.po.OperFocusinfo;
import et.po.OperInquiryCard;
import et.po.OperInquiryResult;
import et.po.OperInquiryinfo;
import et.po.OperMarkanainfo;
import et.po.OperMedicinfo;
import et.po.OperMessages;
import et.po.OperPriceinfo;
import et.po.OperSadinfo;
import et.po.SysUser;
import excellence.common.classtree.ClassTreeService;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.impl.DynaBeanDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;

/**
 * The Class OutputFileImpl.
 * 
 * @author NieYuan
 */
/**
 * @author zhangfeng
 * 
 */
public class OutputServiceImpl implements OutputService {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(OutputServiceImpl.class);

	private BaseDAO dao = null;

	public BaseDAO getDao() {
		return dao;
	}

	public void setDao(BaseDAO dao) {
		this.dao = dao;
	}

	private ClassTreeService cts = null;

	public ClassTreeService getCts() {
		return cts;
	}

	public void setCts(ClassTreeService cts) {
		this.cts = cts;
	}

	private ActiveXComponent msWordApp = null;

	private Dispatch document = null;

	private Dispatch selection = null;

	/**
	 * 删除“消息管理”中无用消息记录列表
	 * 
	 * @param ids
	 *            多个ID中间用逗号隔开的字符串
	 * @return list 包含数据和dto列表
	 */
	public void delMessagesList(String ids) {
		String[] id = ids.split(",");
		for (int i = 0; i < id.length; i++) {
			OperMessages po = (OperMessages) dao.loadEntity(OperMessages.class,
					id[i]);
			dao.removeEntity(po);
		}
	}

	/**
	 * 根据ID集取出案例库相关供求记录列表
	 * 
	 * @param ids
	 *            多个ID中间用逗号隔开的字符串
	 * @return list 包含数据和dto列表
	 */
	public List<OperSadinfo> getSadList(String ids) {

		List<OperSadinfo> list = new ArrayList<OperSadinfo>();
		String[] id = ids.split(",");
		for (int i = 0; i < id.length; i++) {
			OperSadinfo po = (OperSadinfo) dao.loadEntity(OperSadinfo.class,
					id[i]);
			list.add(po);
		}
		return list;
	}

	/**
	 * 根据ID集取出案例库相关供求记录列表
	 * 
	 * @param ids
	 *            多个ID中间用逗号隔开的字符串
	 * @return list 包含数据和dto列表
	 */
	public List<DynaBeanDTO> getSadList2(String ids) {

		List<DynaBeanDTO> list = new ArrayList<DynaBeanDTO>();
		String[] id = ids.split(",");
		for (int i = 0; i < id.length; i++) {
			OperSadinfo po = (OperSadinfo) dao.loadEntity(OperSadinfo.class,
					id[i]);
			DynaBeanDTO dto = new DynaBeanDTO();
			String begin = "";
			if (po.getDeployBegin() != null) {
				begin = po.getDeployBegin().toLocaleString();
				begin = begin.substring(0, begin.indexOf(" "));
			}
			String end = "";
			if (po.getDeployEnd() != null) {
				end = po.getDeployEnd().toLocaleString();
				end = end.substring(0, end.indexOf(" "));
			}

			dto.set("custName", po.getCustName());
			dto.set("dictSadType", cts.getLabelById(po.getDictSadType()));
			dto.set("deployEnd", end);
			dto.set("custTel", po.getCustTel());
			dto.set("productName", po.getProductName());
			dto.set("deployBegin", begin);
			dto.set("custAddr", po.getCustAddr());
			dto.set("productScale", po.getProductScale());
			dto.set("remark", po.getRemark());
			dto.set("post", po.getPost());
			dto.set("productCount", po.getProductCount());
			list.add(dto);
		}
		return list;
	}

	/**
	 * 根据ID集取出案例库相关记录列表
	 * 
	 * @param ids
	 *            多个ID中间用逗号隔开的字符串
	 * @return list 包含数据和dto列表
	 */
	public List<OperCaseinfo> getCaseList(String ids) {
		List<OperCaseinfo> list = new ArrayList<OperCaseinfo>();
		String[] id = ids.split(",");
		for (int i = 0; i < id.length; i++) {
			OperCaseinfo po = (OperCaseinfo) dao.loadEntity(OperCaseinfo.class,
					id[i]);
			list.add(po);
		}
		return list;
	}

	/**
	 * @author wwq 根据ID得到市场分析库列表
	 * @param ids
	 *            多个ID中间用逗号隔开的字符串
	 * @return list 包含数据和dto列表
	 */
	public List<OperMarkanainfo> getMarkanaList(String ids) {
		List<OperMarkanainfo> list = new ArrayList<OperMarkanainfo>();
		String[] id = ids.split(",");
		for (int i = 0; i < id.length; i++) {
			OperMarkanainfo po = (OperMarkanainfo) dao.loadEntity(
					OperMarkanainfo.class, id[i]);
			list.add(po);
		}
		return list;
	}

	/**
	 * @author wwq 根据ID得到焦点追踪库列表
	 * @param ids
	 *            多个ID中间用逗号隔开的字符串
	 * @return list 包含数据和dto列表
	 */
	public List<OperFocusinfo> getTraceList(String ids) {
		List<OperFocusinfo> list = new ArrayList<OperFocusinfo>();
		String[] id = ids.split(",");
		for (int i = 0; i < id.length; i++) {
			OperFocusinfo po = (OperFocusinfo) dao.loadEntity(
					OperFocusinfo.class, id[i]);
			list.add(po);
		}
		return list;
	}

	/**
	 * 根据ID集取出价格库中的相关记录列表
	 * 
	 * @param ids
	 *            多个ID中间用逗号隔开的字符串
	 * @return list 包含数据和dto列表
	 */
	public List<OperPriceinfo> getPriceList(String ids) {

		List<OperPriceinfo> list = new ArrayList<OperPriceinfo>();
		String[] id = ids.split(",");

		for (int i = 0; i < id.length; i++) {
			OperPriceinfo po = (OperPriceinfo) dao.loadEntity(
					OperPriceinfo.class, id[i]);
			if (po.getDictPriceType() != null)
				if (!"".equals(po.getDictPriceType())) {
					{
						if (!cts.getLabelById(po.getDictPriceType()).equals("")) {
							try {
								po.setDictPriceType(cts.getLabelById(po
										.getDictPriceType()));
							} catch (Exception e) {
								e.printStackTrace();
							}
							// dbd.set("dictPriceType",cts.getLabelById(opi.getDictPriceType()));
						}
					}
					list.add(po);
				}
		}
		return list;
	}

	/**
	 * 根据ID集取出调查信息问题库中的相关记录列表
	 * 
	 * @param ids
	 *            多个ID中间用逗号隔开的字符串
	 * @return list 包含数据和dto列表
	 */
	public List<OperInquiryinfo> getInquiryCardList(String ids) {
		List<OperInquiryinfo> list = new ArrayList<OperInquiryinfo>();
		String[] id = ids.split(",");
		for (int i = 0; i < id.length; i++) {
			OperInquiryinfo po = (OperInquiryinfo) dao.loadEntity(
					OperInquiryinfo.class, id[i]);
			list.add(po);
		}
		return list;
	}

	/**
	 * 根据po生成调查信息问题库的Excel文档
	 * 
	 * @param po
	 *            po对象
	 * @param row
	 *            行标记
	 * @return row 行标记
	 */
	private int writeExcelInquiryCard(WritableSheet ws, OperInquiryinfo po,
			int row, WritableCellFormat textFormat,
			WritableCellFormat titleFormat) {
		try {
			String beginTime = "";
			if (po.getBeginTime() != null) {
				beginTime = TimeUtil.getTheTimeStr(po.getBeginTime(),
						"yyyy-MM-dd");
			}
			String endTime = "";
			if (po.getEndTime() != null) {
				endTime = TimeUtil.getTheTimeStr(po.getEndTime(), "yyyy-MM-dd");
			}

			ws.setColumnView(1, 35);
			ws.setColumnView(3, 35);
			ws.setColumnView(5, 35);

			ws.mergeCells(0, row + 3, 1, row + 3);
			ws.mergeCells(2, row + 3, 3, row + 3);
			ws.mergeCells(4, row + 3, 5, row + 3);

			ws.mergeCells(0, row + 4, 1, row + 4);
			ws.mergeCells(2, row + 4, 3, row + 4);
			ws.mergeCells(4, row + 4, 5, row + 4);

			// 第一行
			Label label = new Label(0, row + 0, "调查类别", titleFormat);// 这个使用标题格式
			ws.addCell(label);
			if (po.getDictInquiryType().equals("SYS_TREE_0000000362")) {
				label = new Label(1, row + 0, "疫情灾害", textFormat);// 这个使用文本格式
				ws.addCell(label);
			}
			if (po.getDictInquiryType().equals("SYS_TREE_0000000363")) {
				label = new Label(1, row + 0, "涉农政策", textFormat);
				ws.addCell(label);
			}
			if (po.getDictInquiryType().equals("SYS_TREE_0000000364")) {
				label = new Label(1, row + 0, "市场经济", textFormat);
				ws.addCell(label);
			}
			if (po.getDictInquiryType().equals("SYS_TREE_0000000365")) {
				label = new Label(1, row + 0, "生产管理", textFormat);
				ws.addCell(label);
			}
			if (po.getDictInquiryType().equals("SYS_TREE_0000000366")) {
				label = new Label(1, row + 0, "医疗卫生", textFormat);
				ws.addCell(label);
			}
			if (po.getDictInquiryType().equals("SYS_TREE_0000000367")) {
				label = new Label(1, row + 0, "食品安全", textFormat);
				ws.addCell(label);
			}
			if (po.getDictInquiryType().equals("SYS_TREE_0000000368")) {
				label = new Label(1, row + 0, "文娱教育", textFormat);
				ws.addCell(label);
			}
			if (po.getDictInquiryType().equals("SYS_TREE_0000000369")) {
				label = new Label(1, row + 0, "热线服务", textFormat);
				ws.addCell(label);
			}

			label = new Label(2, row + 0, "开始时间", titleFormat);
			ws.addCell(label);
			label = new Label(3, row + 0, beginTime, textFormat);
			ws.addCell(label);

			label = new Label(4, row + 0, "发起机构", titleFormat);
			ws.addCell(label);
			label = new Label(5, row + 0, po.getOrganiztion(), textFormat);
			ws.addCell(label);

			// 第二行
			label = new Label(0, row + 1, "调查主题", titleFormat);
			ws.addCell(label);
			label = new Label(1, row + 1, po.getTopic(), textFormat);
			ws.addCell(label);

			label = new Label(2, row + 1, "结束时间", titleFormat);
			ws.addCell(label);
			label = new Label(3, row + 1, endTime, textFormat);
			ws.addCell(label);

			label = new Label(4, row + 1, "组 织 者", titleFormat);
			ws.addCell(label);
			label = new Label(5, row + 1, po.getOrganizers(), textFormat);
			ws.addCell(label);

			// 第三行
			label = new Label(0, row + 2, "调查目的", titleFormat);
			ws.addCell(label);
			label = new Label(1, row + 2, po.getAim(), textFormat);
			ws.addCell(label);

			label = new Label(2, row + 2, "审核状态", titleFormat);
			ws.addCell(label);
			label = new Label(3, row + 2, po.getState(), textFormat);
			ws.addCell(label);

			label = new Label(4, row + 2, "参与人员", titleFormat);
			ws.addCell(label);
			label = new Label(5, row + 2, po.getActors(), textFormat);
			ws.addCell(label);

			// 第四行
			label = new Label(0, row + 3, "调查类型", titleFormat);
			ws.addCell(label);

			label = new Label(2, row + 3, "调查问题", titleFormat);
			ws.addCell(label);

			label = new Label(4, row + 3, "调查答案备选项（不同备选项用;号隔开，双击自动添写）",
					titleFormat);
			ws.addCell(label);

			// 第五行
			MyQuery mq = new MyQueryImpl();
			StringBuffer hql = new StringBuffer();
			String id = po.getId();
			hql.append("from OperInquiryCard where operInquiryinfo = '" + id
					+ "'");
			mq.setHql(hql.toString());
			Object[] result = null;
			try {
				result = (Object[]) dao.findEntity(mq);
			} catch (Exception e) {
				e.printStackTrace();
			}
			for (int i = 0, size = result.length; i < size; i++) {

				ws.mergeCells(0, row + (4 + i), 1, row + (4 + i));
				ws.mergeCells(2, row + (4 + i), 3, row + (4 + i));
				ws.mergeCells(4, row + (4 + i), 5, row + (4 + i));

				OperInquiryCard oic = (OperInquiryCard) result[i];

				if (oic.getDictQuestionType().equals("001")) {
					label = new Label(0, row + (4 + i), "单选题", titleFormat);
					ws.addCell(label);
				}
				if (oic.getDictQuestionType().equals("002")) {
					label = new Label(0, row + (4 + i), "多选题", titleFormat);
					ws.addCell(label);
				}
				if (oic.getDictQuestionType().equals("003")) {
					label = new Label(0, row + (4 + i), "问答题", titleFormat);
					ws.addCell(label);
				}

				label = new Label(2, row + (4 + i), oic.getQuestion(),
						titleFormat);
				ws.addCell(label);

				label = new Label(4, row + (4 + i), oic.getAlternatives(),
						titleFormat);
				ws.addCell(label);

			}

			row = row + 6;

		} catch (Exception e) {
			System.err.println(e);
		}
		return row;
	}

	/**
	 * 根据ID集取出调查信息分析库中的调查类别记录列表
	 * 
	 * @param ids
	 *            多个ID中间用逗号隔开的字符串
	 * @return list 包含数据和dto列表
	 */
	public List getDictInquiryType(String ids) {
		List list = new ArrayList();
		String[] id = ids.split(",");
		for (int i = 0; i < id.length; i++) {
			OperInquiryinfo po = (OperInquiryinfo) dao.loadEntity(
					OperInquiryinfo.class, id[i]);
			String qid = po.getId().toString();
			list.add(qid);
		}
		return list;
	}

	/**
	 * 根据ID取出OperInquiryCard的实体
	 * 
	 * @param id
	 *            OperInquiryInfo的id
	 * @return oic 包含实体
	 */
	public List getCard(String ids) {
		List list = new ArrayList();
		String[] id = ids.split(",");
		for (int i = 0; i < id.length; i++) {
			OperInquiryinfo po = (OperInquiryinfo) dao.loadEntity(
					OperInquiryinfo.class, id[i]);
			list.add(getCardA(po));
		}
		return list;
	}

	private List getCardA(OperInquiryinfo po) {
		MyQuery mq = new MyQueryImpl();
		StringBuffer hql = new StringBuffer();
		String id = po.getId();
		hql.append("from OperInquiryCard where operInquiryinfo = '" + id + "'");
		mq.setHql(hql.toString());
		Object[] result = null;
		try {
			result = (Object[]) dao.findEntity(mq);
		} catch (Exception e) {
			e.printStackTrace();
		}
		List list = new ArrayList();
		for (int i = 0, size = result.length; i < size; i++) {
			OperInquiryCard oic = (OperInquiryCard) result[i];
			DynaBeanDTO dbd = new DynaBeanDTO();

			dbd.set("operInquiryinfo", oic.getOperInquiryinfo().getId());
			if (oic.getDictQuestionType().equals("001")) {
				dbd.set("dictQuestionType", "单选题");
			}
			if (oic.getDictQuestionType().equals("002")) {
				dbd.set("dictQuestionType", "多选题");
			}
			if (oic.getDictQuestionType().equals("003")) {
				dbd.set("dictQuestionType", "问答题");
			}
			dbd.set("question", oic.getQuestion());
			dbd.set("alternatives", oic.getAlternatives());
			list.add(dbd);
		}
		return list;
	}

	/**
	 * 根据ID集取出调查信息分析库中的相关记录列表
	 * 
	 * @param ids
	 *            多个ID中间用逗号隔开的字符串
	 * @return list 包含数据和dto列表
	 */
	public List getResultList(String ids) {
		List list = new ArrayList();
		String[] id = ids.split(",");
		for (int i = 0; i < id.length; i++) {
			OperInquiryinfo po = (OperInquiryinfo) dao.loadEntity(
					OperInquiryinfo.class, id[i]);
			DynaBeanDTO dto = new DynaBeanDTO();
			list.add(po);
		}
		return list;
	}

	public List getInquiryResultList(String ids) {
		List list = new ArrayList();
		String[] id = ids.split(",");
		for (int i = 0; i < id.length; i++) {
			OperInquiryinfo po = (OperInquiryinfo) dao.loadEntity(
					OperInquiryinfo.class, id[i]);
			OperInquiryinfo po2 = new OperInquiryinfo();

			String dictInquiryType = cts.getLabelById(po.getDictInquiryType());
			po2.setBeginTime(po.getBeginTime());
			po2.setOrganizers(po.getOrganizers());
			po2.setOrganiztion(po.getOrganiztion());
			po2.setTopic(po.getTopic());
			po2.setAim(po.getAim());
			po2.setState(po.getState());
			po2.setActors(po.getActors());
			po2.setEndTime(po.getEndTime());
			po2.setDictInquiryType(dictInquiryType);

			Map map = new HashMap();
			MyQuery mq = new MyQueryImpl();
			StringBuffer hql = new StringBuffer();
			String pid = po.getId();
			hql.append("from OperInquiryCard where operInquiryinfo = '" + pid
					+ "'");
			mq.setHql(hql.toString());
			Object[] result = null;
			try {
				result = (Object[]) dao.findEntity(mq);
			} catch (Exception e) {
				e.printStackTrace();
			}
			List list4 = new ArrayList();
			for (int j = 0, size = result.length; j < size; j++) {
				OperInquiryCard oic = (OperInquiryCard) result[j];
				DynaBeanDTO dbd = new DynaBeanDTO();
				dbd.set("operInquiryinfo", oic.getOperInquiryinfo().getId());

				if (oic.getDictQuestionType().equals("001")) {
					dbd.set("dictQuestionType", "单选题");
				}
				if (oic.getDictQuestionType().equals("002")) {
					dbd.set("dictQuestionType", "多选题");
				}
				if (oic.getDictQuestionType().equals("003")) {
					dbd.set("dictQuestionType", "问答题");
				}
				dbd.set("question", oic.getQuestion());
				dbd.set("alternatives", oic.getAlternatives());
				list4.add(dbd);
			}
			map.put("one", po2);
			map.put("two", list4);
			map.put("four", list4.size());
			list.add(map);
		}
		return list;
	}

	public List getInquiryResult2List(String ids) {
		List list = new ArrayList();
		String[] id = ids.split(",");
		for (int i = 0; i < id.length; i++) {
			OperInquiryinfo po = (OperInquiryinfo) dao.loadEntity(
					OperInquiryinfo.class, id[i]);

			DynaBeanDTO dto = new DynaBeanDTO();

			String begin = "";
			if (po.getBeginTime() != null) {
				begin = po.getBeginTime().toLocaleString();
				begin = begin.substring(0, begin.indexOf(" "));
			}
			String end = "";
			if (po.getBeginTime() != null) {
				end = po.getEndTime().toLocaleString();
				end = end.substring(0, end.indexOf(" "));
			}

			if (po.getDictInquiryType().equals("SYS_TREE_0000000362")) {
				dto.set("type", "疫情灾害");
			}
			if (po.getDictInquiryType().equals("SYS_TREE_0000000363")) {
				dto.set("type", "涉农政策");
			}
			if (po.getDictInquiryType().equals("SYS_TREE_0000000364")) {
				dto.set("type", "市场经济");
			}
			if (po.getDictInquiryType().equals("SYS_TREE_0000000365")) {
				dto.set("type", "生产管理");
			}
			if (po.getDictInquiryType().equals("SYS_TREE_0000000366")) {
				dto.set("type", "医疗卫生");
			}
			if (po.getDictInquiryType().equals("SYS_TREE_0000000367")) {
				dto.set("type", "食品安全");
			}
			if (po.getDictInquiryType().equals("SYS_TREE_0000000368")) {
				dto.set("type", "文娱教育");
			}
			if (po.getDictInquiryType().equals("SYS_TREE_0000000369")) {
				dto.set("type", "热线服务");
			}
			dto.set("reportTopic", po.getReportTopic());
			dto.set("reportTopic2", po.getReportTopic2());
			dto.set("topic", po.getTopic());
			dto.set("organizers", po.getOrganizers());
			dto.set("organiztion", po.getOrganiztion());
			dto.set("actors", po.getActors());
			dto.set("reportCopywriter", po.getReportCopywriter());
			dto.set("reportKeyword", po.getReportKeyword());
			dto.set("beginTime", begin);
			dto.set("aim", po.getAim());
			dto.set("state", po.getState());
			dto.set("endTime", end);
			dto.set("reportAbstract", po.getReportAbstract());
			dto.set("reportSwatch", po.getReportSwatch());
			dto.set("reportEfficiency", po.getReportEfficiency());
			dto.set("reportState", po.getReportState());
			dto.set("reportContent", po.getReportContent());
			dto.set("reportReview", po.getReportReview());
			dto.set("reportRemark", po.getReportRemark());
			dto.set("inquiryinfo", po.getId());

			list.add(dto);
		}
		return list;
	}

	/**
	 * 根据po生成调查信息分析库的Excel文档
	 * 
	 * @param po
	 *            po对象
	 * @param row
	 *            行标记
	 * @return row 行标记
	 */
	private int writeExcelInquiryinfo(WritableSheet ws, OperInquiryinfo po,
			int row, WritableCellFormat textFormat,
			WritableCellFormat titleFormat) {

		try {
			String beginTime = "";
			if (po.getBeginTime() != null) {
				beginTime = TimeUtil.getTheTimeStr(po.getBeginTime(),
						"yyyy-MM-dd");
			}
			String endTime = "";
			if (po.getEndTime() != null) {
				endTime = TimeUtil.getTheTimeStr(po.getEndTime(), "yyyy-MM-dd");
			}

			ws.setColumnView(1, 35);
			ws.setColumnView(3, 35);
			ws.setRowView(row + 6, 1500);
			ws.setRowView(row + 7, 1000);
			ws.setRowView(row + 8, 1000);

			ws.mergeCells(3, row + 0, 5, row + 0);
			ws.mergeCells(3, row + 1, 5, row + 0);
			ws.mergeCells(3, row + 2, 5, row + 0);
			ws.mergeCells(3, row + 3, 5, row + 0);
			ws.mergeCells(3, row + 4, 5, row + 0);
			ws.mergeCells(1, row + 6, 5, row + 6);
			ws.mergeCells(1, row + 7, 5, row + 7);
			ws.mergeCells(1, row + 8, 5, row + 8);

			// 第一行
			Label label = new Label(0, row + 0, "标  题", titleFormat);// 这个使用标题格式
			ws.addCell(label);
			label = new Label(1, row + 0, po.getReportTopic(), textFormat);// 这个使用文本格式
			ws.addCell(label);

			label = new Label(2, row + 0, "副 标 题", titleFormat);
			ws.addCell(label);
			label = new Label(3, row + 0, po.getReportTopic2(), textFormat);
			ws.addCell(label);

			// 第二行
			label = new Label(0, row + 1, "调查主题", titleFormat);
			ws.addCell(label);
			label = new Label(1, row + 1, po.getTopic(), textFormat);
			ws.addCell(label);

			label = new Label(2, row + 1, "组 织 者", titleFormat);
			ws.addCell(label);
			label = new Label(3, row + 1, po.getOrganizers(), textFormat);
			ws.addCell(label);

			// 第三行
			label = new Label(0, row + 2, "发起机构", titleFormat);
			ws.addCell(label);
			label = new Label(1, row + 2, po.getOrganiztion(), textFormat);
			ws.addCell(label);

			label = new Label(2, row + 2, "参与人员", titleFormat);
			ws.addCell(label);
			label = new Label(3, row + 2, po.getActors(), textFormat);
			ws.addCell(label);

			// 第四行
			label = new Label(0, row + 3, "撰 稿 人", titleFormat);
			ws.addCell(label);
			label = new Label(1, row + 3, po.getReportCopywriter(), textFormat);
			ws.addCell(label);

			label = new Label(2, row + 3, "关 键 字", titleFormat);
			ws.addCell(label);
			label = new Label(3, row + 3, po.getReportKeyword(), textFormat);
			ws.addCell(label);

			// 第五行
			label = new Label(0, row + 4, "调查时间", titleFormat);
			ws.addCell(label);
			label = new Label(1, row + 4, beginTime + "至" + endTime, textFormat);
			ws.addCell(label);

			label = new Label(2, row + 4, "摘    要", titleFormat);
			ws.addCell(label);
			label = new Label(3, row + 4, po.getReportAbstract(), textFormat);
			ws.addCell(label);

			// 第六行
			label = new Label(0, row + 5, "调查样本", titleFormat);
			ws.addCell(label);
			label = new Label(1, row + 5, po.getReportSwatch(), textFormat);
			ws.addCell(label);

			label = new Label(2, row + 5, "调查有效率", titleFormat);
			ws.addCell(label);
			label = new Label(3, row + 5, po.getReportEfficiency(), textFormat);
			ws.addCell(label);

			label = new Label(4, row + 5, "审核状态", titleFormat);
			ws.addCell(label);
			label = new Label(5, row + 5, po.getReportState(), textFormat);
			ws.addCell(label);

			// 第七行
			label = new Label(0, row + 6, "报告正文", titleFormat);
			ws.addCell(label);
			label = new Label(1, row + 6, po.getReportContent(), textFormat);
			ws.addCell(label);

			// 第八行
			label = new Label(0, row + 7, "报告评论", titleFormat);
			ws.addCell(label);
			label = new Label(1, row + 7, po.getReportReview(), textFormat);
			ws.addCell(label);

			// 第九行
			label = new Label(0, row + 8, "备    注", titleFormat);
			ws.addCell(label);
			label = new Label(1, row + 8, po.getReportRemark(), textFormat);
			ws.addCell(label);
			List<DynaBeanDTO> ls = getInquiryResultById(po.getId());
			
			if(ls.size()>0)
			{
				//受理时间  受理工号  用户姓名  问题类型  调查问题  调查答案  
				label = new Label(0,row + 9, "受理时间 ");
				ws.addCell(label);
				label = new Label(1,row+9, "受理工号 ");
				ws.addCell(label);
				label = new Label(2,row+9, "用户姓名 ");
				ws.addCell(label);
				label = new Label(3,row+9, "问题类型 ");
				ws.addCell(label);
				label = new Label(4,row+9, "调查问题 ");
				ws.addCell(label);
				label = new Label(5,row+9, "调查答案 ");
				ws.addCell(label);
			}
			int begin = 10;
			int count = 0;
			for(int i=0,len=ls.size(); i<len; i++)
			{
				label = new Label(0,row + begin,  (String)ls.get(i).get("time"));
				ws.addCell(label);
				label = new Label(1,row+begin, (String)ls.get(i).get("rname"));
				ws.addCell(label);
				label = new Label(2,row+begin, (String)ls.get(i).get("actor"));
				ws.addCell(label);
				label = new Label(3,row+begin, (String)ls.get(i).get("qType"));
				ws.addCell(label);
				label = new Label(4,row+begin, (String)ls.get(i).get("question"));
				ws.addCell(label);
				label = new Label(5,row+begin, (String)ls.get(i).get("answer"));
				ws.addCell(label);
				begin++;
				count++;
			}
			row = row + 3+count;
		} catch (Exception e) {
			System.err.println(e);
			e.printStackTrace();
		}

		return row;
	}

	private List<DynaBeanDTO> getInquiryResultById(String id)
	{
		List<DynaBeanDTO> ls = new ArrayList<DynaBeanDTO>();
		String sql = " select * from oper_inquiry_result this_ where this_.topic_id like '"+id+"'";
		RowSet rs = dao.getRowSetByJDBCsql(sql);
		try {
			rs.beforeFirst();
			while (rs.next()) {
				DynaBeanDTO r = new DynaBeanDTO();
				//受理时间  受理工号  用户姓名  问题类型  调查问题  调查答案  
				r.set("id",rs.getString("id"));
				r.set("time",TimeUtil.getTheTimeStr(rs.getDate("create_time"),
				"yyyy-MM-dd"));
				r.set("actor", rs.getString("actor"));// 这里需要将参与客户的ID转换成名字
				r.set("rname", rs.getString("rid"));// 这里需要将受理座席员ID转换为名称
				String questionType = rs.getString("question_type")==null?"":rs.getString("question_type");

				if("001".equals(questionType))
					questionType = "单选题";
				if("001".equals(questionType))
					questionType = "多选题";
				if("001".equals(questionType))
					questionType = "问答题";
				r.set("qType", questionType);
				r.set("question",rs.getString("question"));
				r.set("answer", rs.getString("answer"));
				
				//r.setTopicId();
				ls.add(r);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ls;
	}
	/**
	 * 导出案例信息到word文档.
	 * 
	 * @param cases
	 *            案例信息数组,每个案例信息有一个DynaBeanDTO代表
	 * @param fileName
	 *            文件路径和文件名
	 */
	public void outputWordFile(List<OperCaseinfo> pos, String fileName,
			String dbType) {
		// 打开word
		msWordApp = new ActiveXComponent("Word.Application");
		Dispatch.put(msWordApp, "Visible", new Variant(false));
		// 创建新文档
		Dispatch documents = Dispatch.get(msWordApp, "Documents").toDispatch();
		document = Dispatch.call(documents, "Add").toDispatch();

		for (int i = 0; i < pos.size(); i++) { // 把每条记录按格式写在文档里
			writeWord(pos.get(i), dbType, "" + (i + 1));
		}
		setPageInfo(); // 设置页首页尾
		Dispatch.call(document, "SaveAs", fileName);// 文件另存为
		// 关闭文档
		Dispatch.call(document, "Close", new Variant(0));// 0：没有存储改变；-1：有存储改变；-2：提示存储改变
		document = null;
		// 关闭word
		Dispatch.call(msWordApp, "Quit");
		msWordApp = null;

	}

	/**
	 * 导出案例信息到txt文档.
	 * 
	 * @param cases
	 *            案例信息数组,每个案例信息由一个DynaBeanDTO对象代表
	 * @param fileName
	 *            生成的文件路径和文件名
	 */
	public void outputTxtFile(List<OperCaseinfo> pos, String fileName,
			String dbType) {

		try {
			FileWriter fw = new FileWriter(fileName, true);
			BufferedWriter bw = new BufferedWriter(fw);

			if (dbType.equals("general") || dbType.equals("focus")) {// 如果是普通或焦点

				for (int i = 0; i < pos.size(); i++) { // 把每条记录按格式写在文档里
					OperCaseinfo po = pos.get(i);
					String CustAddr = (po.getCustAddr() == null) ? "" : po
							.getCustAddr();
					String CustName = (po.getCustName() == null) ? "" : po
							.getCustName();
					String CustTel = (po.getCustTel() == null) ? "" : po
							.getCustTel();
					String CaseTime = (po.getCaseTime() == null) ? "" : po
							.getCaseTime().toString();
					String CaseContent = (po.getCaseContent() == null) ? ""
							: po.getCaseContent();
					String CaseExpert = (po.getCaseExpert() == null) ? "" : po
							.getCaseExpert();
					bw.write((i + 1) + "、" + CustAddr + " " + CustName + " "
							+ CustTel + "(" + CaseTime + ")" + "来电咨询："
							+ CaseContent + "(" + CaseExpert + ")");
					bw.newLine();
					String CaseReply = (po.getCaseReply() == null) ? "" : po
							.getCaseReply();
					bw.write("【解答】 " + CaseReply);
					bw.newLine();
					String CaseReview = (po.getCaseReview() == null) ? "" : po
							.getCaseReview();
					bw.write("【案例点评】 " + CaseReview);
					bw.newLine();
					String Remark = (po.getRemark() == null) ? "" : po
							.getRemark();
					bw.write("【备注】 " + Remark);
					bw.newLine();
					bw.newLine();
					bw.newLine();
				}

			} else if (dbType.equals("hzinfo")) {// 会诊格式

				for (int i = 0; i < pos.size(); i++) { // 把每条记录按格式写在文档里
					OperCaseinfo po = pos.get(i);
					String CaseContent = (po.getCaseContent() == null) ? ""
							: po.getCaseContent();
					bw.write("【会诊主题】 " + CaseContent);
					bw.newLine();
					String CustName = (po.getCustName() == null) ? "" : po
							.getCustName();
					bw.write("【用户姓名】 " + CustName);
					bw.newLine();
					String CustTel = (po.getCustTel() == null) ? "" : po
							.getCustTel();
					bw.write("【用户电话】 " + CustTel);
					bw.newLine();
					String CustAddr = (po.getCustAddr() == null) ? "" : po
							.getCustAddr();
					bw.write("【用户地址】 " + CustAddr);
					bw.newLine();
					String CaseExpert = (po.getCaseExpert() == null) ? "" : po
							.getCaseExpert();
					bw.write("【受理专家】 " + CaseExpert);
					bw.newLine();
					String CaseJoins = (po.getCaseJoins() == null) ? "" : po
							.getCaseJoins();
					bw.write("【参诊人员】 " + CaseJoins);
					bw.newLine();
					String CaseTime = (po.getCaseTime() == null) ? "" : po
							.getCaseTime().toString();
					bw.write("【会诊时间】 " + CaseTime);
					bw.newLine();
					String CaseReview = (po.getCaseReview() == null) ? "" : po
							.getCaseReview();
					bw.write("【案例点评】 " + CaseReview);
					bw.newLine();
					String CaseReport = (po.getCaseReport() == null) ? "" : po
							.getCaseReport();
					bw.write("【相关报道】 " + CaseReport);
					bw.newLine();
					bw.newLine();
					bw.newLine();
				}

			} else if (dbType.equals("effect")) {// 效果格式

				for (int i = 0; i < pos.size(); i++) { // 把每条记录按格式写在文档里
					OperCaseinfo po = pos.get(i);
					String CaseTime = (po.getCaseTime() == null) ? "" : po
							.getCaseTime().toString();
					bw.write("【受理时间】 " + CaseTime);
					bw.newLine();
					String CustName = (po.getCustName() == null) ? "" : po
							.getCustName().toString();
					bw.write("【用户姓名】 " + CustName);
					bw.newLine();
					String CustTel = (po.getCustTel() == null) ? "" : po
							.getCustTel().toString();
					bw.write("【用户电话】 " + CustTel);
					bw.newLine();
					String CustAddr = (po.getCustAddr() == null) ? "" : po
							.getCustAddr().toString();
					bw.write("【用户地址】 " + CustAddr);
					bw.newLine();
					String CaseContent = (po.getCaseContent() == null) ? ""
							: po.getCaseContent().toString();
					bw.write("【具体情况】 " + CaseContent);
					bw.newLine();
					String CaseReply = (po.getCaseReply() == null) ? "" : po
							.getCaseReply().toString();
					bw.write("【案例解析】 " + CaseReply);
					bw.newLine();
					String CaseReport = (po.getCaseReport() == null) ? "" : po
							.getCaseReport().toString();
					bw.write("【相关报道】 " + CaseReport);

					bw.newLine();
					bw.newLine();
					bw.newLine();
				}

			}

			bw.close();
			fw.close();
		} catch (Exception e) {
			// System.out.println(e);
			e.printStackTrace();
		}
	}

	/**
	 * 导出案例信息到Excel文档.
	 * 
	 * @param cases
	 *            案例信息数组,每个案例信息由一个DynaBeanDTO对象代表
	 * @param fileName
	 *            生成的文件路径和文件名
	 */
	public void outputExcelFile(List pos, String fileName, String dbType) {
		System.out.println("--writeExcelInquiryCard----------inquirycard--");
		try {
			OutputStream os = new FileOutputStream(fileName);
			WritableWorkbook wwb = Workbook.createWorkbook(os);
			WritableSheet ws = wwb.createSheet("数据导出", 0);// 生成第一张表
			// 设置文字格式
			WritableFont wfText = new WritableFont(WritableFont
					.createFont("宋体"), 10, WritableFont.NO_BOLD);
			WritableCellFormat textFormat = new WritableCellFormat(wfText);
			textFormat.setBorder(Border.ALL, BorderLineStyle.THIN);// 加表格
			textFormat.setVerticalAlignment(VerticalAlignment.TOP);// 垂直对齐为顶端
			textFormat.setWrap(true);// 自动换行
			// 设置标题格式
			WritableFont wfTitle = new WritableFont(WritableFont
					.createFont("宋体"), 10, WritableFont.BOLD);
			WritableCellFormat titleFormat = new WritableCellFormat(wfTitle);
			titleFormat.setBorder(Border.ALL, BorderLineStyle.THIN);// 加表格
			titleFormat.setVerticalAlignment(VerticalAlignment.CENTRE);// 垂直居中对齐
			if(dbType.equals("question")){
				int row = 0;
				for (int i = 0; i < pos.size(); i++) { // 把每条记录按格式写在文档里
					List<String> lit = (List)pos.get(i);
					row = writeExcelQuestion(ws, lit, row, textFormat,
							titleFormat) + 4;// 空三行
				}
			}
			if (dbType.equals("general") || dbType.equals("focus")
					|| dbType.equals("hzinfo") || dbType.equals("effect")) {// 如果是案例库的某某某格式

				int row = 0;
				for (int i = 0; i < pos.size(); i++) { // 把每条记录按格式写在文档里
					OperCaseinfo po = (OperCaseinfo) pos.get(i);
					row = writeExcelCase(ws, po, row, dbType, textFormat,
							titleFormat) + 4;// 空三行
				}
			} else if (dbType.equals("price")) {

				writeExcelPrice(ws, pos, textFormat, titleFormat);// 空三行
			} else if (dbType.equals("sad")) {

				int row = 0;
				for (int i = 0; i < pos.size(); i++) { // 把每条记录按格式写在文档里
					OperSadinfo po = (OperSadinfo) pos.get(i);
					row = writeExcelSad(ws, po, row, textFormat, titleFormat) + 4;// 空三行
				}
			} else if (dbType.equals("trace")) {// 焦点追踪
				// writeExcelTrace(pos, fileName);
				int row = 0;
				for (int i = 0; i < pos.size(); i++) { // 把每条记录按格式写在文档里
					OperFocusinfo po = (OperFocusinfo) pos.get(i);
					row = writeExcelTrace(ws, po, row, textFormat, titleFormat) + 4;// 空三行
				}
			} else if (dbType.equals("mart")) {// 焦点追踪
				// writeExcelTrace(pos, fileName);
				int row = 0;
				for (int i = 0; i < pos.size(); i++) { // 把每条记录按格式写在文档里
					OperMarkanainfo po = (OperMarkanainfo) pos.get(i);
					row = writeExcelMart(ws, po, row, textFormat, titleFormat) + 4;// 空三行
				}
			} else if (dbType.equals("crop")) {
				int row = 0;
				for (int i = 0; i < pos.size(); i++) { // 把每条记录按格式写在文档里
					OperCorpinfo po = (OperCorpinfo) pos.get(i);
					row = writeExcelCrop(ws, po, row, textFormat, titleFormat) + 4;// 空三行
				}
			} else if (dbType.equals("medicinfo")) {
				int row = 0;
				for (int i = 0; i < pos.size(); i++) { // 把每条记录按格式写在文档里
					OperMedicinfo po = (OperMedicinfo) pos.get(i);
					row = writeExcelMedicinfo(ws, po, row, textFormat,
							titleFormat) + 4;// 空三行
				}
			} else if (dbType.equals("bookmedicinfo")) {
				int row = 0;
				for (int i = 0; i < pos.size(); i++) { // 把每条记录按格式写在文档里
					OperBookMedicinfo po = (OperBookMedicinfo) pos.get(i);
					row = writeExcelBookMedicinfo(ws, po, row, textFormat,
							titleFormat) + 4;// 空三行
				}
			} else if (dbType.equals("inquiryresult")) {
				int row = 0;
				for (int i = 0; i < pos.size(); i++) { // 把每条记录按格式写在文档里
					OperInquiryinfo po = (OperInquiryinfo) pos.get(i);
					row = writeExcelInquiryinfo(ws, po, row, textFormat,
							titleFormat) + 8;// 空三行
				}
			} else if (dbType.equals("inquirycard")) {
				int row = 0;
				for (int i = 0; i < pos.size(); i++) { // 把每条记录按格式写在文档里
					OperInquiryinfo po = (OperInquiryinfo) pos.get(i);
					row = writeExcelInquiryCard(ws, po, row, textFormat,
							titleFormat) + 4;// 空三行
				}
			}
			wwb.write();
			wwb.close();
			os.close();

		} catch (Exception e) {
			System.err.println(e);
		}
	}

	// //////////////////以上是具体操作部分////////////////////

	/**
	 * 开始一条一条的写文件
	 * 
	 * @param dto
	 *            the dto
	 */
	private void writeWord(OperCaseinfo po, String dbType, String order) {

		selection = Dispatch.get(msWordApp, "Selection").toDispatch();
		Dispatch font = Dispatch.get(selection, "Font").toDispatch();// 字型格式化需要的物件

		if (dbType.equals("general") || dbType.equals("focus")) {// 如果是普通或焦点

			Dispatch.put(selection, "Text",
					order + "、" + po.getCustAddr() == null ? "" : po
							.getCustAddr()
							+ " " + po.getCustName() == null ? "" : po
							.getCustName()
							+ " " + po.getCustTel() == null ? "" : po
							.getCustTel()
							+ "(" + po.getCaseTime() == null ? ""
							: po.getCaseTime() + ")" + " " + "来电咨询："
									+ po.getCaseContent() == null ? "" : po
									.getCaseContent()
									+ " (");
			Dispatch.put(font, "Bold", "1");
			Dispatch.call(selection, "MoveRight", new Variant(1),
					new Variant(1));
			Dispatch.put(selection, "Text", po.getCaseExpert());
			Dispatch.put(font, "Bold", "0");
			Dispatch.call(selection, "MoveRight", new Variant(1),
					new Variant(1));
			Dispatch.put(selection, "Text", ")");
			Dispatch.put(font, "Bold", "1");
			Dispatch.call(selection, "MoveDown");
			Dispatch.call(selection, "TypeParagraph");

			Dispatch.put(selection, "Text", "【解答】 ");
			Dispatch.put(font, "Bold", 1);
			Dispatch.call(selection, "MoveRight", new Variant(1),
					new Variant(1));
			Dispatch.put(selection, "Text", po.getCaseReply());
			Dispatch.put(font, "Bold", "0");
			Dispatch.call(selection, "MoveDown");
			Dispatch.call(selection, "TypeParagraph");

			Dispatch.put(selection, "Text", "【案例点评】 ");
			Dispatch.put(font, "Bold", 1);
			Dispatch.call(selection, "MoveRight", new Variant(1),
					new Variant(1));
			Dispatch.put(selection, "Text", po.getCaseReview());
			Dispatch.put(font, "Bold", "0");
			Dispatch.call(selection, "MoveDown");
			Dispatch.call(selection, "TypeParagraph");

			Dispatch.put(selection, "Text", "【备注】 ");
			Dispatch.put(font, "Bold", 1);
			Dispatch.call(selection, "MoveRight", new Variant(1),
					new Variant(1));
			Dispatch.put(selection, "Text", po.getRemark());
			Dispatch.put(font, "Bold", "0");
			Dispatch.call(selection, "MoveDown");
			Dispatch.call(selection, "TypeParagraph");// 空一行段落

		} else if (dbType.equals("hzinfo")) {// 会诊格式

			Dispatch.put(selection, "Text", "【会诊主题】 ");
			Dispatch.put(font, "Bold", 1);
			Dispatch.call(selection, "MoveRight", new Variant(1),
					new Variant(1));
			Dispatch.put(selection, "Text", po.getCaseContent());
			Dispatch.put(font, "Bold", "0");
			Dispatch.call(selection, "MoveDown");
			Dispatch.call(selection, "TypeParagraph");

			Dispatch.put(selection, "Text", "【用户姓名】 ");
			Dispatch.put(font, "Bold", 1);
			Dispatch.call(selection, "MoveRight", new Variant(1),
					new Variant(1));
			Dispatch.put(selection, "Text", po.getCustName());
			Dispatch.put(font, "Bold", "0");
			Dispatch.call(selection, "MoveDown");
			Dispatch.call(selection, "TypeParagraph");

			Dispatch.put(selection, "Text", "【用户电话】 ");
			Dispatch.put(font, "Bold", 1);
			Dispatch.call(selection, "MoveRight", new Variant(1),
					new Variant(1));
			Dispatch.put(selection, "Text", po.getCustTel());
			Dispatch.put(font, "Bold", "0");
			Dispatch.call(selection, "MoveDown");
			Dispatch.call(selection, "TypeParagraph");

			Dispatch.put(selection, "Text", "【用户地址】 ");
			Dispatch.put(font, "Bold", 1);
			Dispatch.call(selection, "MoveRight", new Variant(1),
					new Variant(1));
			Dispatch.put(selection, "Text", po.getCustAddr());
			Dispatch.put(font, "Bold", "0");
			Dispatch.call(selection, "MoveDown");
			Dispatch.call(selection, "TypeParagraph");

			Dispatch.put(selection, "Text", "【受理专家】 ");
			Dispatch.put(font, "Bold", 1);
			Dispatch.call(selection, "MoveRight", new Variant(1),
					new Variant(1));
			Dispatch.put(selection, "Text", po.getCaseExpert());
			Dispatch.put(font, "Bold", "0");
			Dispatch.call(selection, "MoveDown");
			Dispatch.call(selection, "TypeParagraph");

			Dispatch.put(selection, "Text", "【参诊人员】 ");
			Dispatch.put(font, "Bold", 1);
			Dispatch.call(selection, "MoveRight", new Variant(1),
					new Variant(1));
			Dispatch.put(selection, "Text", po.getCaseJoins());
			Dispatch.put(font, "Bold", "0");
			Dispatch.call(selection, "MoveDown");
			Dispatch.call(selection, "TypeParagraph");

			Dispatch.put(selection, "Text", "【会诊时间】 ");
			Dispatch.put(font, "Bold", 1);
			Dispatch.call(selection, "MoveRight", new Variant(1),
					new Variant(1));
			Dispatch.put(selection, "Text", po.getCaseTime());
			Dispatch.put(font, "Bold", "0");
			Dispatch.call(selection, "MoveDown");
			Dispatch.call(selection, "TypeParagraph");

			Dispatch.put(selection, "Text", "【案例点评】 ");
			Dispatch.put(font, "Bold", 1);
			Dispatch.call(selection, "MoveRight", new Variant(1),
					new Variant(1));
			Dispatch.put(selection, "Text", po.getCaseReview());
			Dispatch.put(font, "Bold", "0");
			Dispatch.call(selection, "MoveDown");
			Dispatch.call(selection, "TypeParagraph");

			Dispatch.put(selection, "Text", "【相关报道】 ");
			Dispatch.put(font, "Bold", 1);
			Dispatch.call(selection, "MoveRight", new Variant(1),
					new Variant(1));
			Dispatch.put(selection, "Text", po.getCaseReport());
			Dispatch.put(font, "Bold", "0");
			Dispatch.call(selection, "MoveDown");
			Dispatch.call(selection, "TypeParagraph");

		} else if (dbType.equals("effect")) {// 效果格式

			Dispatch.put(selection, "Text", "【受理时间】 ");
			Dispatch.put(font, "Bold", 1);
			Dispatch.call(selection, "MoveRight", new Variant(1),
					new Variant(1));
			Dispatch.put(selection, "Text", po.getCaseTime());
			Dispatch.put(font, "Bold", "0");
			Dispatch.call(selection, "MoveDown");
			Dispatch.call(selection, "TypeParagraph");

			Dispatch.put(selection, "Text", "【用户姓名】 ");
			Dispatch.put(font, "Bold", 1);
			Dispatch.call(selection, "MoveRight", new Variant(1),
					new Variant(1));
			Dispatch.put(selection, "Text", po.getCustName());
			Dispatch.put(font, "Bold", "0");
			Dispatch.call(selection, "MoveDown");
			Dispatch.call(selection, "TypeParagraph");

			Dispatch.put(selection, "Text", "【用户电话】 ");
			Dispatch.put(font, "Bold", 1);
			Dispatch.call(selection, "MoveRight", new Variant(1),
					new Variant(1));
			Dispatch.put(selection, "Text", po.getCustTel());
			Dispatch.put(font, "Bold", "0");
			Dispatch.call(selection, "MoveDown");
			Dispatch.call(selection, "TypeParagraph");

			Dispatch.put(selection, "Text", "【用户地址】 ");
			Dispatch.put(font, "Bold", 1);
			Dispatch.call(selection, "MoveRight", new Variant(1),
					new Variant(1));
			Dispatch.put(selection, "Text", po.getCustAddr());
			Dispatch.put(font, "Bold", "0");
			Dispatch.call(selection, "MoveDown");
			Dispatch.call(selection, "TypeParagraph");

			Dispatch.put(selection, "Text", "【具体情况】 ");
			Dispatch.put(font, "Bold", 1);
			Dispatch.call(selection, "MoveRight", new Variant(1),
					new Variant(1));
			Dispatch.put(selection, "Text", po.getCaseContent());
			Dispatch.put(font, "Bold", "0");
			Dispatch.call(selection, "MoveDown");
			Dispatch.call(selection, "TypeParagraph");

			Dispatch.put(selection, "Text", "【案例解析】 ");
			Dispatch.put(font, "Bold", 1);
			Dispatch.call(selection, "MoveRight", new Variant(1),
					new Variant(1));
			Dispatch.put(selection, "Text", po.getCaseReply());
			Dispatch.put(font, "Bold", "0");
			Dispatch.call(selection, "MoveDown");
			Dispatch.call(selection, "TypeParagraph");

			Dispatch.put(selection, "Text", "【相关报道】 ");
			Dispatch.put(font, "Bold", 1);
			Dispatch.call(selection, "MoveRight", new Variant(1),
					new Variant(1));
			Dispatch.put(selection, "Text", po.getCaseReport());
			Dispatch.put(font, "Bold", "0");
			Dispatch.call(selection, "MoveDown");
			Dispatch.call(selection, "TypeParagraph");

		}

		// 要求每个问题空三行
		Dispatch.call(selection, "TypeParagraph");// 空一行段落
		Dispatch.call(selection, "TypeParagraph");// 空一行段落
		Dispatch.call(selection, "TypeParagraph");// 空一行段落

	}

	/**
	 * 设置页首页尾
	 */
	private void setPageInfo() {
		Dispatch.call(selection, "MoveRight", new Variant(1), new Variant(1));
		Dispatch alignment = Dispatch.get(selection, "ParagraphFormat")
				.toDispatch();
		// 插入页首页尾
		// 取得活动窗体对象
		Dispatch activeWindow = msWordApp.getProperty("ActiveWindow")
				.toDispatch();
		// 取得活动窗格对象
		Dispatch activePane = Dispatch.get(activeWindow, "ActivePane")
				.toDispatch();
		// 取得视窗对象
		Dispatch view = Dispatch.get(activePane, "View").toDispatch();
		// 9是设置页首(游标所在处)
		Dispatch.put(view, "SeekView", "9");// 页首中的信息
		Dispatch.put(alignment, "Alignment", "2");
		Dispatch.put(selection, "Text", "金农热线典型案例");
		// 10是设置页尾(游标所在处)
		Dispatch.put(view, "SeekView", "10");// 页尾中的信息
		Dispatch.put(alignment, "Alignment", "1");
		Dispatch.put(selection, "Text", "金农热线  12316");
	}

	/**
	 * 根据po生成Excel案例文档
	 * 
	 * @param po
	 *            po对象
	 * @param row
	 *            行标记
	 * @return row 行标记
	 */
	private int writeExcelCase(WritableSheet ws, OperCaseinfo po, int row,
			String dbType, WritableCellFormat textFormat,
			WritableCellFormat titleFormat) {
		String date = "";
		if (po.getCaseTime() != null) {
			date = po.getCaseTime().toLocaleString();
			date = date.substring(0, date.indexOf(" "));
		}
		try {

			if (dbType.equals("general") || dbType.equals("focus")) {// 如果是普通或焦点

				ws.setColumnView(1, 15);// 设置列宽,第2列
				ws.setColumnView(3, 15);
				ws.setColumnView(5, 15);

				ws.setRowView(row + 3, 1000);// 设置行高，第4行
				ws.setRowView(row + 4, 1000);
				ws.setRowView(row + 5, 1000);

				ws.mergeCells(1, row + 3, 5, row + 3);// 合并单元格
				ws.mergeCells(1, row + 4, 5, row + 4);
				ws.mergeCells(1, row + 5, 5, row + 5);
				// 开始写标题
				Label label = new Label(0, row + 0, "所属大类", titleFormat);// 标题格式
				ws.addCell(label);
				label = new Label(2, row + 0, "受理专家", titleFormat);
				ws.addCell(label);
				label = new Label(4, row + 0, "用户姓名", titleFormat);
				ws.addCell(label);
				label = new Label(0, row + 1, "所属小类", titleFormat);
				ws.addCell(label);
				label = new Label(2, row + 1, "受理工号", titleFormat);
				ws.addCell(label);
				label = new Label(4, row + 1, "用户电话", titleFormat);
				ws.addCell(label);
				label = new Label(0, row + 2, "所属种类", titleFormat);
				ws.addCell(label);
				label = new Label(2, row + 2, "受理时间", titleFormat);
				ws.addCell(label);
				label = new Label(4, row + 2, "用户地址", titleFormat);
				ws.addCell(label);
				label = new Label(0, row + 3, "受理问题", titleFormat);
				ws.addCell(label);
				label = new Label(0, row + 4, "问题答案", titleFormat);
				ws.addCell(label);
				label = new Label(0, row + 5, "备注", titleFormat);
				ws.addCell(label);
				// 开始写文本了
				label = new Label(1, row + 0, po.getCaseAttr1(), textFormat);// 使用文本格式
				ws.addCell(label);
				label = new Label(3, row + 0, po.getCaseExpert(), textFormat);
				ws.addCell(label);
				label = new Label(5, row + 0, po.getCustName(), textFormat);
				ws.addCell(label);
				label = new Label(1, row + 1, po.getCaseAttr2(), textFormat);
				ws.addCell(label);
				label = new Label(3, row + 1, po.getCaseRid(), textFormat);
				ws.addCell(label);
				label = new Label(5, row + 1, po.getCustTel(), textFormat);
				ws.addCell(label);
				label = new Label(1, row + 2, po.getCaseAttr3(), textFormat);
				ws.addCell(label);
				label = new Label(3, row + 2, date, textFormat);
				ws.addCell(label);
				label = new Label(5, row + 2, po.getCustAddr(), textFormat);
				ws.addCell(label);
				label = new Label(1, row + 3, po.getCaseContent(), textFormat);
				ws.addCell(label);
				label = new Label(1, row + 4, po.getCaseReply(), textFormat);
				ws.addCell(label);
				label = new Label(1, row + 5, po.getRemark(), textFormat);
				ws.addCell(label);
				row = row + 5;// 移动到下一次的行数

			} else if (dbType.equals("hzinfo")) {// 会诊格式

				ws.mergeCells(1, row + 0, 5, row + 0); // 合并单元格
				ws.mergeCells(2, row + 3, 5, row + 3);
				ws.mergeCells(1, row + 4, 5, row + 4);
				ws.mergeCells(1, row + 5, 5, row + 5);
				ws.mergeCells(1, row + 6, 5, row + 6);

				ws.setColumnView(1, 15); // 设置列宽,第1列
				ws.setColumnView(3, 15);
				ws.setColumnView(5, 15);

				ws.setRowView(row + 4, 1000);
				ws.setRowView(row + 5, 1000);
				ws.setRowView(row + 6, 1000);

				Label label = new Label(0, row + 0, "会诊主题", titleFormat);// 使用格式了
				ws.addCell(label);
				label = new Label(1, row + 0, po.getCaseContent(), textFormat);// 使用格式了
				ws.addCell(label);

				label = new Label(0, row + 1, "用户姓名", titleFormat);// 这个使用标题格式
				ws.addCell(label);
				label = new Label(1, row + 1, po.getCustName(), textFormat);// 这个使用标题格式
				ws.addCell(label);

				label = new Label(2, row + 1, "用户电话", titleFormat);// 这个使用标题格式
				ws.addCell(label);
				label = new Label(3, row + 1, po.getCustTel(), textFormat);// 这个使用标题格式
				ws.addCell(label);

				label = new Label(4, row + 1, "用户地址", titleFormat);// 这个使用标题格式
				ws.addCell(label);
				label = new Label(5, row + 1, po.getCustAddr(), textFormat);// 这个使用标题格式
				ws.addCell(label);

				label = new Label(0, row + 2, "受理专家", titleFormat);// 这个使用标题格式
				ws.addCell(label);
				label = new Label(1, row + 2, po.getCaseExpert(), textFormat);// 这个使用标题格式
				ws.addCell(label);

				label = new Label(2, row + 2, "受理工号", titleFormat);// 这个使用标题格式
				ws.addCell(label);
				label = new Label(3, row + 2, po.getCaseRid(), textFormat);// 这个使用标题格式
				ws.addCell(label);

				label = new Label(4, row + 2, "受理时间", titleFormat);// 这个使用标题格式
				ws.addCell(label);
				label = new Label(5, row + 2, date, textFormat);// 这个使用标题格式
				ws.addCell(label);

				label = new Label(0, row + 3, "参诊单位及人员", titleFormat);// 这个使用标题格式
				ws.addCell(label);
				label = new Label(2, row + 3, po.getCaseJoins(), textFormat);// 这个使用标题格式
				ws.addCell(label);

				label = new Label(0, row + 4, "会诊过程", titleFormat);// 这个使用标题格式
				ws.addCell(label);
				label = new Label(1, row + 4, po.getCaseReply(), textFormat);// 这个使用标题格式
				ws.addCell(label);

				label = new Label(0, row + 5, "案例点评", titleFormat);// 这个使用标题格式
				ws.addCell(label);
				label = new Label(1, row + 5, po.getCaseReview(), textFormat);// 这个使用标题格式
				ws.addCell(label);

				label = new Label(0, row + 6, "相关报道", titleFormat);// 这个使用标题格式
				ws.addCell(label);
				label = new Label(1, row + 6, po.getCaseReport(), textFormat);// 这个使用标题格式
				ws.addCell(label);
				row = row + 6;// 移动到下一次的行数

			} else if (dbType.equals("effect")) {// 效果格式

				ws.setColumnView(1, 15);// 设置第2列的宽度为15个像素宽
				ws.setColumnView(3, 15);
				ws.setColumnView(5, 15);

				ws.setRowView(row + 2, 1000);// 设置第3行的高度为1000个像素
				ws.setRowView(row + 3, 1000);
				ws.setRowView(row + 4, 1000);

				ws.mergeCells(1, row + 2, 5, row + 2);// 合并单元格
				ws.mergeCells(1, row + 3, 5, row + 3);
				ws.mergeCells(1, row + 4, 5, row + 4);

				// 第一行头2个单元格
				Label label = new Label(0, row + 0, "受理时间", titleFormat);// 这个使用标题格式
				ws.addCell(label);
				label = new Label(1, row + 0, date, textFormat);// 这个使用文本格式
				ws.addCell(label);
				// 第一行中间2个单元格
				label = new Label(2, row + 0, "受理工号", titleFormat);
				ws.addCell(label);
				label = new Label(3, row + 0, po.getCaseRid(), textFormat);
				ws.addCell(label);
				// 第一行后2个单元格
				label = new Label(4, row + 0, "受理专家", titleFormat);
				ws.addCell(label);
				label = new Label(5, row + 0, po.getCaseExpert(), textFormat);
				ws.addCell(label);
				// 第二行头2个单元格
				label = new Label(0, row + 1, "用户姓名", titleFormat);
				ws.addCell(label);
				label = new Label(1, row + 1, po.getCustName(), textFormat);
				ws.addCell(label);
				// 第二行中间2个单元格
				label = new Label(2, row + 1, "用户电话", titleFormat);
				ws.addCell(label);
				label = new Label(3, row + 1, po.getCustTel(), textFormat);
				ws.addCell(label);
				// 第二行后2个单元格
				label = new Label(4, row + 1, "用户地址", titleFormat);
				ws.addCell(label);
				label = new Label(5, row + 1, po.getCustAddr(), textFormat);
				ws.addCell(label);
				// 第三行
				label = new Label(0, row + 2, "具体情况", titleFormat);
				ws.addCell(label);
				label = new Label(1, row + 2, po.getCaseContent(), textFormat);
				ws.addCell(label);
				// 第四行
				label = new Label(0, row + 3, "案例解析", titleFormat);
				ws.addCell(label);
				label = new Label(1, row + 3, po.getCaseReply(), textFormat);
				ws.addCell(label);
				// 第五行
				label = new Label(0, row + 4, "相关报道", titleFormat);
				ws.addCell(label);
				label = new Label(1, row + 4, po.getCaseReport(), textFormat);
				ws.addCell(label);
				row = row + 4;// 移动到下一次的行数
			}
		} catch (Exception e) {
			System.err.println(e);
		}

		return row;
	}

	/**
	 * 根据服务记录里含“024007009”查出的数据生成Excel案例文档
	 * 
	 * @param po
	 *            po list对象
	 * @param row
	 *            行标记
	 * @return row 行标记
	 */
	private int writeExcelQuestion(WritableSheet ws, List<String> po, int row,
			WritableCellFormat textFormat,WritableCellFormat titleFormat) {
		try {
//			System.out.println("3: "+po.get(4));
				ws.setColumnView(1, 15);// 设置列宽,第2列
				ws.setColumnView(3, 15);
				ws.setColumnView(5, 15);
				ws.setColumnView(7, 15);
				// 开始写标题
				Label label = new Label(0, row + 0, "用户电话", titleFormat);// 标题格式
				ws.addCell(label);
				label = new Label(2, row + 0, "用户姓名", titleFormat);
				ws.addCell(label);
				label = new Label(4, row + 0, "用户地址", titleFormat);
				ws.addCell(label);
				label = new Label(6, row + 0, "受理时间", titleFormat);
				ws.addCell(label);
				
				// 开始写文本了
				label = new Label(1, row + 0, po.get(0), textFormat);// 使用文本格式
				ws.addCell(label);
				label = new Label(3, row + 0, po.get(1), textFormat);
				ws.addCell(label);
				label = new Label(5, row + 0, po.get(2), textFormat);
				ws.addCell(label);
				label = new Label(7, row + 0, po.get(3), textFormat);
				ws.addCell(label);
			
				row = row - 2;// 移动到下一次的行数

		} catch (Exception e) {
			System.err.println(e);
		}
		return row;
	}
	
	/**
	 * 根据po生成Excel价格文档
	 */
	private void writeExcelPrice(WritableSheet ws, List pos,
			WritableCellFormat textFormat, WritableCellFormat titleFormat) {

		try {
			ws.setColumnView(0, 20);
			ws.setColumnView(1, 20);
			ws.setColumnView(2, 20);
			ws.setColumnView(3, 20);
			ws.setColumnView(4, 20);
			Label label = new Label(0, 0, "报价时间 ", titleFormat);// 这个使用标题格式
			ws.addCell(label);
			label = new Label(1, 0, "产品名称", titleFormat);
			ws.addCell(label);
			label = new Label(2, 0, "产地", titleFormat);
			ws.addCell(label);
			label = new Label(3, 0, "价格 ", titleFormat);
			ws.addCell(label);
			label = new Label(4, 0, "价格类型", titleFormat);
			ws.addCell(label);
			label = new Label(5, 0, "备注", titleFormat);
			ws.addCell(label);
			for (int i = 0; i < pos.size(); i++) { // 把每条记录按格式写在文档里
				OperPriceinfo po = (OperPriceinfo) pos.get(i);
				String date = "";
				if (po.getDeployTime() != null) {
					date = po.getOperTime().toLocaleString();
					date = date.substring(0, date.indexOf(" "));
				}
				label = new Label(0, i + 1, date, textFormat);
				ws.addCell(label);

				label = new Label(1, i + 1, po.getProductName(), textFormat);// 这个使用文本格式
				ws.addCell(label);
				label = new Label(2, i + 1, po.getCustAddr(), textFormat);
				ws.addCell(label);
				label = new Label(3, i + 1, po.getProductPrice(), textFormat);
				ws.addCell(label);
				label = new Label(4, i + 1, po.getDictPriceType(), textFormat);
				ws.addCell(label);
				label = new Label(5, i + 1, po.getRemark(), textFormat);
				ws.addCell(label);
			}
			// Label label = new Label(0, 0, "品种类别", titleFormat);// 这个使用标题格式
			// ws.addCell(label);
			// label = new Label(1, 0, "产品名称", titleFormat);
			// ws.addCell(label);
			// label = new Label(2, 0, "价格", titleFormat);
			// ws.addCell(label);
			// label = new Label(3, 0, "备注", titleFormat);
			// ws.addCell(label);
			// label = new Label(4, 0, "发布日期", titleFormat);
			// ws.addCell(label);
			//
			// for (int i = 0; i < pos.size(); i++) { // 把每条记录按格式写在文档里
			// OperPriceinfo po = (OperPriceinfo) pos.get(i);
			// String date = "";
			// if (po.getDeployTime() != null) {
			// date = po.getOperTime().toLocaleString();
			// date = date.substring(0, date.indexOf(" "));
			// }
			// label = new Label(0, i + 1, po.getDictProductType2(),
			// textFormat);// 这个使用文本格式
			// ws.addCell(label);
			// label = new Label(1, i + 1, po.getDictProductType1(),
			// textFormat);
			// ws.addCell(label);
			// label = new Label(2, i + 1, po.getProductPrice(), textFormat);
			// ws.addCell(label);
			// label = new Label(3, i + 1, po.getRemark(), textFormat);
			// ws.addCell(label);
			// label = new Label(4, i + 1, date, textFormat);
			// ws.addCell(label);
			// }

		} catch (Exception e) {
			System.err.println(e);
		}
	}

	/**
	 * 根据po生成Excel案例文档
	 * 
	 * @param po
	 *            po对象
	 * @param row
	 *            行标记
	 * @return row 行标记
	 */
	private int writeExcelSad(WritableSheet ws, OperSadinfo po, int row,
			WritableCellFormat textFormat, WritableCellFormat titleFormat) {

		try {
			String date = "";
			if (po.getDeployEnd() != null) {
				date = po.getDeployEnd().toLocaleString();
				date = date.substring(0, date.indexOf(" "));
			}
			String begin = "";
			if (po.getDeployBegin() != null) {
				begin = po.getDeployBegin().toLocaleString();
				begin = date.substring(0, begin.indexOf(" "));
			}

			ws.setColumnView(1, 25);
			ws.setColumnView(3, 25);
			ws.setColumnView(5, 25);

			ws.mergeCells(4, row + 2, 4, row + 3);// 合并单元格("备注"的标题)
			ws.mergeCells(5, row + 2, 5, row + 3);// 合并单元格("备注"的内容)

			// 第一行
			Label label = new Label(0, row + 0, "联 系 人", titleFormat);// 这个使用标题格式
			ws.addCell(label);
			label = new Label(1, row + 0, po.getCustName(), textFormat);// 这个使用文本格式
			ws.addCell(label);

			label = new Label(2, row + 0, "供求类型", titleFormat);
			ws.addCell(label);
			label = new Label(3, row + 0,
					cts.getLabelById(po.getDictSadType()), textFormat);
			ws.addCell(label);

			label = new Label(4, row + 0, "有效期至", titleFormat);
			ws.addCell(label);
			label = new Label(5, row + 0, date, textFormat);
			ws.addCell(label);

			// 第二行
			label = new Label(0, row + 1, "联系电话", titleFormat);
			ws.addCell(label);
			label = new Label(1, row + 1, po.getCustTel(), textFormat);
			ws.addCell(label);

			label = new Label(2, row + 1, "产品名称", titleFormat);
			ws.addCell(label);
			label = new Label(3, row + 1, po.getProductName(), textFormat);
			ws.addCell(label);

			label = new Label(4, row + 1, "发布日期", titleFormat);
			ws.addCell(label);
			label = new Label(5, row + 1, begin, textFormat);
			ws.addCell(label);

			// 第三行
			label = new Label(0, row + 2, "联系地址", titleFormat);
			ws.addCell(label);
			label = new Label(1, row + 2, po.getCustAddr(), textFormat);
			ws.addCell(label);

			label = new Label(2, row + 2, "产品规格", titleFormat);
			ws.addCell(label);
			label = new Label(3, row + 2, po.getProductScale(), textFormat);
			ws.addCell(label);

			label = new Label(4, row + 2, "备注", titleFormat);
			ws.addCell(label);
			label = new Label(5, row + 2, po.getRemark(), textFormat);
			ws.addCell(label);

			// 第四行
			label = new Label(0, row + 3, "邮编", titleFormat);
			ws.addCell(label);
			label = new Label(1, row + 3, po.getPost(), textFormat);
			ws.addCell(label);

			label = new Label(2, row + 3, "产品数量", titleFormat);
			ws.addCell(label);
			label = new Label(3, row + 3, po.getProductCount(), textFormat);
			ws.addCell(label);

			row = row + 3;

		} catch (Exception e) {
			System.err.println(e);
		}

		return row;
	}

	/**
	 * 得到焦点追踪库中信息
	 */
	public List<OperFocusinfo> getFocusList(String ids) {
		// TODO Auto-generated method stub
		List<OperFocusinfo> list = new ArrayList<OperFocusinfo>();
		String[] id = ids.split(",");
		for (int i = 0; i < id.length; i++) {
			OperFocusinfo po = (OperFocusinfo) dao.loadEntity(
					OperFocusinfo.class, id[i]);
			list.add(po);
		}
		return list;
	}

	/**
	 * 焦点案例信息列表excel
	 * 
	 * @param pos
	 * @return
	 */
	// private int writeExcelTrace(List pos, String fileName) throws Exception {
	// int i = 0;
	// String url = "E:\\gongneng.xls";
	// // System.out.println("读取文件成功......");
	// Workbook wb = Workbook.getWorkbook(new File(url));
	// ByteArrayOutputStream targetFile = new ByteArrayOutputStream();
	// WritableWorkbook wwb = Workbook.createWorkbook(targetFile, wb);
	// WritableSheet wws = wwb.getSheet("Sheet1");
	//		
	//		
	// // 设置文字格式
	// WritableFont wfText = new WritableFont(WritableFont
	// .createFont("宋体"), 10, WritableFont.NO_BOLD);
	// WritableCellFormat textFormat = new WritableCellFormat(wfText);
	// textFormat.setBorder(Border.ALL, BorderLineStyle.THIN);// 加表格
	// textFormat.setVerticalAlignment(VerticalAlignment.TOP);// 垂直对齐为顶端
	// textFormat.setWrap(true);// 自动换行
	// // 设置标题格式
	// WritableFont wfTitle = new WritableFont(WritableFont
	// .createFont("宋体"), 10, WritableFont.BOLD);
	// WritableCellFormat titleFormat = new WritableCellFormat(wfTitle);
	// titleFormat.setBorder(Border.ALL, BorderLineStyle.THIN);// 加表格
	// titleFormat.setVerticalAlignment(VerticalAlignment.CENTRE);// 垂直居中对齐
	//		
	// // System.out.println("label开始读取......");
	// Label tmp00 = (Label) wws.getWritableCell(0, 0);
	// Label tmp40 = (Label) wws.getWritableCell(4, 0);
	// Label tmp01 = (Label) wws.getWritableCell(0, 1);
	// Label tmp21 = (Label) wws.getWritableCell(2, 1);
	// Label tmp41 = (Label) wws.getWritableCell(4, 1);
	// Label tmp02 = (Label) wws.getWritableCell(0, 2);
	// Label tmp22 = (Label) wws.getWritableCell(2, 2);
	// Label tmp42 = (Label) wws.getWritableCell(4, 2);
	// Label tmp62 = (Label) wws.getWritableCell(6, 2);
	// // Label tmp14 = (Label) wws.getWritableCell(1, 4);
	// Label tmp03 = (Label) wws.getWritableCell(0, 3);
	// Label tmp23 = (Label) wws.getWritableCell(2, 3);
	// Label tmp43 = (Label) wws.getWritableCell(4, 3);
	// Label tmp04 = (Label) wws.getWritableCell(0, 4);
	// Label tmp24 = (Label) wws.getWritableCell(2, 4);
	// Label tmp44 = (Label) wws.getWritableCell(4, 4);
	// Label tmp05 = (Label) wws.getWritableCell(0, 5);
	// Label tmp45 = (Label) wws.getWritableCell(4, 5);
	// Label tmp06 = (Label) wws.getWritableCell(0, 6);
	// Label tmp08 = (Label) wws.getWritableCell(0, 8);
	// // System.out.println("完成label......");
	// int tmpk = 0;
	// for (int j = 0; j < pos.size(); j++) {
	// int k = tmpk++;
	// OperFocusinfo ofi = (OperFocusinfo) pos.get(j);
	// String date = "";
	// if (ofi.getCreateTime() != null) {
	// date = ofi.getCreateTime().toLocaleString();
	// date = date.substring(0, date.indexOf(" "));
	// }
	// // System.out.println(" k "+k);
	// // if (j > 0) {
	// // wws.addCell(tmp00.copyTo(0, 16 * k));
	// // wws.addCell(tmp40.copyTo(4, 16 * k));
	// // wws.addCell(tmp01.copyTo(0, 1 + 16 * k));
	// // wws.addCell(tmp21.copyTo(2, 1 + 16 * k));
	// // wws.addCell(tmp41.copyTo(4, 1 + 16 * k));
	// // wws.addCell(tmp02.copyTo(0, 2 + 16 * k));
	// // wws.addCell(tmp22.copyTo(2, 2 + 16 * k));
	// // wws.addCell(tmp42.copyTo(4, 2 + 16 * k));
	// // wws.addCell(tmp62.copyTo(6, 2 + 16 * k));
	// //// wws.addCell(tmp16.copyTo(1, 6 + 16 * k));
	// // wws.addCell(tmp03.copyTo(0, 3 + 16 * k));
	// // wws.addCell(tmp23.copyTo(2, 3 + 16 * k));
	// // wws.addCell(tmp43.copyTo(4, 3 + 16 * k));
	// // wws.addCell(tmp04.copyTo(0, 4 + 16 * k));
	// // wws.addCell(tmp24.copyTo(2, 4 + 16 * k));
	// // wws.addCell(tmp44.copyTo(4, 4 + 16 * k));
	// // wws.addCell(tmp05.copyTo(0, 5 + 16 * k));
	// // wws.addCell(tmp45.copyTo(4, 5 + 16 * k));
	// // wws.addCell(tmp06.copyTo(0, 6 + 16 * k));
	// // wws.addCell(tmp08.copyTo(0, 8 + 16 * k));
	// // }
	// // System.out.println(" k de zhi shi "+k);
	// wws.addCell(new Label(1, 0 + 16 * k, ofi.getFrontFor()));
	// wws.addCell(new Label(5, 0 + 16 * k, ofi.getUnderTake()));
	// wws.addCell(new Label(1, 1 + 16 * k, ofi.getSupportTel()));
	// wws.addCell(new Label(3, 1 + 16 * k, ofi.getSupportSite()));
	// wws.addCell(new Label(5, 1 + 16 * k, ofi.getContactMail()));
	// wws.addCell(new Label(1, 2 + 16 * k, ofi.getChiefEditor()));
	// wws.addCell(new Label(3, 2 + 16 * k, ofi.getSubEditor()));
	// wws.addCell(new Label(5, 2 + 16 * k, ofi.getFirstEditor()));
	// wws.addCell(new Label(7, 2 + 16 * k, ofi.getChargeEditor()));
	// wws.addCell(new Label(1, 3 + 16 * k, ofi.getPeriod()));
	// wws.addCell(new Label(3, 3 + 16 * k, date));
	// wws.addCell(new Label(5, 3 + 16 * k, ofi.getSendUnit()));
	// wws.addCell(new Label(1, 4 + 16 * k, ofi.getProductLabel()));
	// wws.addCell(new Label(3, 4 + 16 * k, ofi.getDictFocusType()));
	// wws.addCell(new Label(1, 5 + 16 * k, ofi.getChiefTitle()));
	// wws.addCell(new Label(5, 5 + 16 * k, ofi.getSubTitle()));
	// wws.addCell(new Label(1, 6 + 16 * k, ofi.getSummary()));
	// wws.addCell(new Label(1, 8 + 16 * k, ofi.getFucosContent()));
	// // System.out.println("完成sheet......");
	// }
	//
	// wwb.write();
	// wwb.close();
	// wb.close();
	// FileOutputStream fos = new FileOutputStream(fileName);
	// targetFile.writeTo(fos);
	// targetFile.close();
	// return i;
	// }
	private int writeExcelTrace(WritableSheet ws, OperFocusinfo po, int row,
			WritableCellFormat textFormat, WritableCellFormat titleFormat) {

		try {
			String date = "";
			if (po.getCreateTime() != null) {
				date = po.getCreateTime().toLocaleString();
				date = date.substring(0, date.indexOf(" "));
			}

			ws.setColumnView(1, 25);
			ws.setColumnView(3, 25);
			ws.setColumnView(5, 25);

			// ws.mergeCells(4, row + 2, 4, row + 3);// 合并单元格("备注"的标题)
			// ws.mergeCells(5, row + 2, 5, row + 3);// 合并单元格("备注"的内容)

			ws.mergeCells(1, row + 0, 3, row + 0);
			ws.mergeCells(1, row + 6, 7, row + 6);
			ws.mergeCells(1, row + 7, 7, row + 9);
			ws.mergeCells(0, row + 7, 0, row + 9);

			// 第一行
			Label label = new Label(0, row + 0, "主 办 方", titleFormat);// 这个使用标题格式
			ws.addCell(label);
			label = new Label(1, row + 0, po.getFrontFor(), textFormat);// 这个使用文本格式
			ws.addCell(label);

			// label = new Label(2, row + 0, "供求类型", titleFormat);
			// ws.addCell(label);
			// label = new Label(3, row + 0,
			// cts.getLabelById(po.getDictSadType()), textFormat);
			// ws.addCell(label);

			label = new Label(4, row + 0, "承 办 方", titleFormat);
			ws.addCell(label);
			label = new Label(5, row + 0, po.getUnderTake(), textFormat);
			ws.addCell(label);

			// 第二行
			label = new Label(0, row + 1, "支持热线", titleFormat);
			ws.addCell(label);
			label = new Label(1, row + 1, po.getSupportTel(), textFormat);
			ws.addCell(label);

			label = new Label(2, row + 1, "支持网站", titleFormat);
			ws.addCell(label);
			label = new Label(3, row + 1, po.getSupportSite(), textFormat);
			ws.addCell(label);

			label = new Label(4, row + 1, "联系邮箱", titleFormat);
			ws.addCell(label);
			label = new Label(5, row + 1, po.getContactMail(), textFormat);
			ws.addCell(label);

			// 第三行
			label = new Label(0, row + 2, "主  编", titleFormat);
			ws.addCell(label);
			label = new Label(1, row + 2, po.getChiefEditor(), textFormat);
			ws.addCell(label);

			label = new Label(2, row + 2, "副主编", titleFormat);
			ws.addCell(label);
			label = new Label(3, row + 2, po.getSubEditor(), textFormat);
			ws.addCell(label);

			label = new Label(4, row + 2, "首席编缉", titleFormat);
			ws.addCell(label);
			label = new Label(5, row + 2, po.getFirstEditor(), textFormat);
			ws.addCell(label);

			label = new Label(6, row + 2, "责任编缉", titleFormat);
			ws.addCell(label);
			label = new Label(7, row + 2, po.getChargeEditor(), textFormat);
			ws.addCell(label);

			// 第四行
			label = new Label(0, row + 3, "期  第", titleFormat);
			ws.addCell(label);
			label = new Label(1, row + 3, po.getPeriod(), textFormat);
			ws.addCell(label);

			label = new Label(2, row + 3, "出刊日期", titleFormat);
			ws.addCell(label);
			label = new Label(3, row + 3, date, textFormat);
			ws.addCell(label);

			label = new Label(4, row + 3, "报送单位", titleFormat);
			ws.addCell(label);
			label = new Label(5, row + 3, po.getSendUnit(), textFormat);
			ws.addCell(label);

			// 第五行
			label = new Label(0, row + 4, "品  种", titleFormat);
			ws.addCell(label);
			label = new Label(1, row + 4, po.getProductLabel(), textFormat);
			ws.addCell(label);

			label = new Label(2, row + 4, "版  别", titleFormat);
			ws.addCell(label);
			label = new Label(3, row + 4, po.getDictFocusType(), textFormat);
			ws.addCell(label);

			label = new Label(4, row + 4, "审核状态", titleFormat);
			ws.addCell(label);
			label = new Label(5, row + 4, "", textFormat);
			ws.addCell(label);

			// 第六行
			label = new Label(0, row + 5, "主标题", titleFormat);
			ws.addCell(label);
			label = new Label(1, row + 5, po.getChiefTitle(), textFormat);
			ws.addCell(label);

			label = new Label(4, row + 5, "副标题", titleFormat);
			ws.addCell(label);
			label = new Label(5, row + 5, po.getSubTitle(), textFormat);
			ws.addCell(label);

			// 第七行
			label = new Label(0, row + 6, "摘  要", titleFormat);
			ws.addCell(label);
			label = new Label(1, row + 6, po.getSummary(), textFormat);
			ws.addCell(label);

			// 第七行
			label = new Label(0, row + 7, "正  文", titleFormat);
			ws.addCell(label);
			label = new Label(1, row + 7, DbHtml2Text.Html2Text(po
					.getFucosContent()), textFormat);
			ws.addCell(label);

			row = row + 7;

		} catch (Exception e) {
			System.err.println(e);
		}

		return row;
	}

	private int writeExcelMart(WritableSheet ws, OperMarkanainfo po, int row,
			WritableCellFormat textFormat, WritableCellFormat titleFormat) {

		try {
			String date = "";
			if (po.getCreateTime() != null) {
				date = po.getCreateTime().toLocaleString();
				date = date.substring(0, date.indexOf(" "));
			}

			ws.setColumnView(1, 25);
			ws.setColumnView(3, 25);
			ws.setColumnView(5, 25);

			// ws.mergeCells(4, row + 2, 4, row + 3);// 合并单元格("备注"的标题)
			// ws.mergeCells(5, row + 2, 5, row + 3);// 合并单元格("备注"的内容)

			ws.mergeCells(1, row + 0, 3, row + 0);
			ws.mergeCells(1, row + 6, 7, row + 6);
			ws.mergeCells(1, row + 7, 7, row + 9);
			ws.mergeCells(0, row + 7, 0, row + 9);

			// 第一行
			Label label = new Label(0, row + 0, "主 办 方", titleFormat);// 这个使用标题格式
			ws.addCell(label);
			label = new Label(1, row + 0, po.getFrontFor(), textFormat);// 这个使用文本格式
			ws.addCell(label);

			// label = new Label(2, row + 0, "供求类型", titleFormat);
			// ws.addCell(label);
			// label = new Label(3, row + 0,
			// cts.getLabelById(po.getDictSadType()), textFormat);
			// ws.addCell(label);

			label = new Label(4, row + 0, "承 办 方", titleFormat);
			ws.addCell(label);
			label = new Label(5, row + 0, po.getUnderTake(), textFormat);
			ws.addCell(label);

			// 第二行
			label = new Label(0, row + 1, "支持热线", titleFormat);
			ws.addCell(label);
			label = new Label(1, row + 1, po.getSupportTel(), textFormat);
			ws.addCell(label);

			label = new Label(2, row + 1, "支持网站", titleFormat);
			ws.addCell(label);
			label = new Label(3, row + 1, po.getSupportSite(), textFormat);
			ws.addCell(label);

			label = new Label(4, row + 1, "联系邮箱", titleFormat);
			ws.addCell(label);
			label = new Label(5, row + 1, po.getContactMail(), textFormat);
			ws.addCell(label);

			// 第三行
			label = new Label(0, row + 2, "主  编", titleFormat);
			ws.addCell(label);
			label = new Label(1, row + 2, po.getChiefEditor(), textFormat);
			ws.addCell(label);

			label = new Label(2, row + 2, "副主编", titleFormat);
			ws.addCell(label);
			label = new Label(3, row + 2, po.getSubEditor(), textFormat);
			ws.addCell(label);

			label = new Label(4, row + 2, "首席编缉", titleFormat);
			ws.addCell(label);
			label = new Label(5, row + 2, po.getFirstEditor(), textFormat);
			ws.addCell(label);

			label = new Label(6, row + 2, "责任编缉", titleFormat);
			ws.addCell(label);
			label = new Label(7, row + 2, po.getChargeEditor(), textFormat);
			ws.addCell(label);

			// 第四行
			label = new Label(0, row + 3, "期  第", titleFormat);
			ws.addCell(label);
			label = new Label(1, row + 3, po.getPeriod(), textFormat);
			ws.addCell(label);

			label = new Label(2, row + 3, "出刊日期", titleFormat);
			ws.addCell(label);
			label = new Label(3, row + 3, date, textFormat);
			ws.addCell(label);

			label = new Label(4, row + 3, "报送单位", titleFormat);
			ws.addCell(label);
			label = new Label(5, row + 3, po.getSendUnit(), textFormat);
			ws.addCell(label);

			// 第五行
			label = new Label(0, row + 4, "品  种", titleFormat);
			ws.addCell(label);
			label = new Label(1, row + 4, po.getProductLabel(), textFormat);
			ws.addCell(label);

			label = new Label(2, row + 4, "版  别", titleFormat);
			ws.addCell(label);
			label = new Label(3, row + 4, po.getDictCommentType(), textFormat);
			ws.addCell(label);

			label = new Label(4, row + 4, "审核状态", titleFormat);
			ws.addCell(label);
			label = new Label(5, row + 4, "", textFormat);
			ws.addCell(label);

			// 第六行
			label = new Label(0, row + 5, "主标题", titleFormat);
			ws.addCell(label);
			label = new Label(1, row + 5, po.getChiefTitle(), textFormat);
			ws.addCell(label);

			label = new Label(4, row + 5, "副标题", titleFormat);
			ws.addCell(label);
			label = new Label(5, row + 5, po.getSubTitle(), textFormat);
			ws.addCell(label);

			// 第七行
			label = new Label(0, row + 6, "摘  要", titleFormat);
			ws.addCell(label);
			label = new Label(1, row + 6, po.getSummary(), textFormat);
			ws.addCell(label);

			// 第七行
			label = new Label(0, row + 7, "正  文", titleFormat);
			ws.addCell(label);
			label = new Label(1, row + 7, DbHtml2Text.Html2Text(po
					.getMarkanaContent()), textFormat);
			ws.addCell(label);

			row = row + 7;

		} catch (Exception e) {
			System.err.println(e);
		}

		return row;
	}

	public List<OperMarkanainfo> getMarkList(String ids) {
		// TODO Auto-generated method stub
		List<OperMarkanainfo> list = new ArrayList<OperMarkanainfo>();
		String[] id = ids.split(",");
		for (int i = 0; i < id.length; i++) {
			OperMarkanainfo po = (OperMarkanainfo) dao.loadEntity(
					OperMarkanainfo.class, id[i]);
			list.add(po);
		}
		return list;
	}

	public List<OperCorpinfo> getCrop(String ids) {
		// TODO Auto-generated method stub
		List<OperCorpinfo> list = new ArrayList<OperCorpinfo>();
		String[] id = ids.split(",");
		for (int i = 0; i < id.length; i++) {
			OperCorpinfo po = (OperCorpinfo) dao.loadEntity(OperCorpinfo.class,
					id[i]);
			list.add(po);
		}
		return list;
	}

	private int writeExcelCrop(WritableSheet ws, OperCorpinfo po, int row,
			WritableCellFormat textFormat, WritableCellFormat titleFormat) {

		try {
			String date = "";
			if (po.getCreatetime() != null) {
				date = po.getCreatetime().toLocaleString();
				date = date.substring(0, date.indexOf(" "));
			}

			// ws.mergeCells(4, row + 2, 4, row + 3);// 合并单元格("备注"的标题)
			// ws.mergeCells(5, row + 2, 5, row + 3);// 合并单元格("备注"的内容)
			ws.mergeCells(0, row + 4, 0, row + 6);
			ws.mergeCells(1, row + 4, 3, row + 6);
			ws.mergeCells(0, row + 7, 0, row + 9);
			ws.mergeCells(1, row + 7, 3, row + 9);

			// 第一行
			Label label = new Label(0, row + 0, "受理工号", titleFormat);// 这个使用标题格式
			ws.addCell(label);
			label = new Label(1, row + 0, po.getCorpRid(), textFormat);// 这个使用文本格式
			ws.addCell(label);

			label = new Label(2, row + 0, "受理专家", titleFormat);
			ws.addCell(label);
			label = new Label(3, row + 0, po.getExpert(), textFormat);
			ws.addCell(label);

			// 第二行
			label = new Label(0, row + 1, "用户姓名", titleFormat);
			ws.addCell(label);
			label = new Label(1, row + 1, po.getCustName(), textFormat);
			ws.addCell(label);

			label = new Label(2, row + 1, "用户电话", titleFormat);
			ws.addCell(label);
			label = new Label(3, row + 1, po.getCustTel(), textFormat);
			ws.addCell(label);

			// 第三行
			label = new Label(0, row + 2, "用户地址", titleFormat);
			ws.addCell(label);
			label = new Label(1, row + 2, po.getCustAddr(), textFormat);
			ws.addCell(label);

			label = new Label(2, row + 2, "审核状态", titleFormat);
			ws.addCell(label);
			label = new Label(3, row + 2, po.getState(), textFormat);
			ws.addCell(label);

			// 第四行
			label = new Label(0, row + 3, "服务类型", titleFormat);
			ws.addCell(label);
			label = new Label(1, row + 3, po.getDictServiceType(), textFormat);
			ws.addCell(label);

			label = new Label(2, row + 3, "受理时间", titleFormat);
			ws.addCell(label);
			label = new Label(3, row + 3, date, textFormat);
			ws.addCell(label);

			// 第五行
			label = new Label(0, row + 4, "咨询内容", titleFormat);
			ws.addCell(label);
			label = new Label(1, row + 4, po.getContents(), textFormat);
			ws.addCell(label);

			// 第六行
			label = new Label(0, row + 7, "热线答复", titleFormat);
			ws.addCell(label);
			label = new Label(1, row + 7, po.getReply(), textFormat);
			ws.addCell(label);

			row = row + 10;

		} catch (Exception e) {
			System.err.println(e);
		}

		return row;
	}

	public List<OperMedicinfo> getMedical(String ids) {
		// TODO Auto-generated method stub
		List<OperMedicinfo> list = new ArrayList<OperMedicinfo>();
		String[] id = ids.split(",");
		for (int i = 0; i < id.length; i++) {
			OperMedicinfo po = (OperMedicinfo) dao.loadEntity(
					OperMedicinfo.class, id[i]);
			list.add(po);
		}
		return list;
	}

	private int writeExcelMedicinfo(WritableSheet ws, OperMedicinfo po,
			int row, WritableCellFormat textFormat,
			WritableCellFormat titleFormat) {

		try {
			String date = "";
			if (po.getCreateTime() != null) {
				date = po.getCreateTime().toLocaleString();
				date = date.substring(0, date.indexOf(" "));
			}

			// ws.mergeCells(4, row + 2, 4, row + 3);// 合并单元格("备注"的标题)
			// ws.mergeCells(5, row + 2, 5, row + 3);// 合并单元格("备注"的内容)
			ws.mergeCells(1, row + 2, 3, row + 2);
			ws.mergeCells(0, row + 4, 0, row + 6);
			ws.mergeCells(1, row + 4, 5, row + 6);
			ws.mergeCells(0, row + 7, 0, row + 9);
			ws.mergeCells(1, row + 7, 5, row + 9);

			// 第一行
			Label label = new Label(0, row + 0, "受理工号", titleFormat);// 这个使用标题格式
			ws.addCell(label);
			label = new Label(1, row + 0, po.getMedicRid(), textFormat);// 这个使用文本格式
			ws.addCell(label);

			label = new Label(2, row + 0, "受理专家", titleFormat);
			ws.addCell(label);
			label = new Label(3, row + 0, po.getExpertName(), textFormat);
			ws.addCell(label);

			// 第二行
			label = new Label(0, row + 1, "用户姓名", titleFormat);
			ws.addCell(label);
			label = new Label(1, row + 1, po.getCustName(), textFormat);
			ws.addCell(label);

			label = new Label(2, row + 1, "用户性别", titleFormat);
			ws.addCell(label);
			label = new Label(3, row + 1, cts.getLabelById(po.getDictSex()),
					textFormat);
			ws.addCell(label);

			label = new Label(4, row + 1, "用户电话", titleFormat);
			ws.addCell(label);
			label = new Label(5, row + 1, po.getCustTel(), textFormat);
			ws.addCell(label);

			// 第三行
			label = new Label(0, row + 2, "用户地址", titleFormat);
			ws.addCell(label);
			label = new Label(1, row + 2, po.getCustAddr(), textFormat);
			ws.addCell(label);

			label = new Label(4, row + 2, "审核状态", titleFormat);
			ws.addCell(label);
			label = new Label(5, row + 2, po.getState(), textFormat);
			ws.addCell(label);

			// 第四行
			label = new Label(0, row + 3, "参加新农合", titleFormat);
			ws.addCell(label);
			label = new Label(1, row + 3, po.getIsParter(), textFormat);
			ws.addCell(label);

			label = new Label(2, row + 3, "添加日期", titleFormat);
			ws.addCell(label);
			label = new Label(3, row + 3, date, textFormat);
			ws.addCell(label);

			// 第五行
			label = new Label(0, row + 4, "咨询内容", titleFormat);
			ws.addCell(label);
			label = new Label(1, row + 4, po.getContents(), textFormat);
			ws.addCell(label);

			// 第六行
			label = new Label(0, row + 7, "热线答复", titleFormat);
			ws.addCell(label);
			label = new Label(1, row + 7, po.getReply(), textFormat);
			ws.addCell(label);

			row = row + 10;

		} catch (Exception e) {
			System.err.println(e);
		}

		return row;
	}

	public List<OperBookMedicinfo> getbookMedical(String ids) {
		// TODO Auto-generated method stub
		List<OperBookMedicinfo> list = new ArrayList<OperBookMedicinfo>();
		String[] id = ids.split(",");
		for (int i = 0; i < id.length; i++) {
			OperBookMedicinfo po = (OperBookMedicinfo) dao.loadEntity(
					OperBookMedicinfo.class, id[i]);
			list.add(po);
		}
		return list;
	}

	private int writeExcelBookMedicinfo(WritableSheet ws, OperBookMedicinfo po,
			int row, WritableCellFormat textFormat,
			WritableCellFormat titleFormat) {

		try {
			String date = "";
			if (po.getCreateTime() != null) {
				date = po.getCreateTime().toLocaleString();
				date = date.substring(0, date.indexOf(" "));
			}
			String beforeDate = "";
			if (po.getBookVisitTime() != null) {
				beforeDate = po.getBookVisitTime().toLocaleString();
				beforeDate = beforeDate.substring(0, date.indexOf(" "));
			}
			String visitDate = "";
			if (po.getVisitTime() != null) {
				visitDate = po.getBookVisitTime().toLocaleString();
				visitDate = visitDate.substring(0, date.indexOf(" "));
			}

			// ws.mergeCells(4, row + 2, 4, row + 3);// 合并单元格("备注"的标题)
			// ws.mergeCells(5, row + 2, 5, row + 3);// 合并单元格("备注"的内容)
			ws.mergeCells(1, row + 2, 3, row + 2);
			ws.mergeCells(0, row + 4, 0, row + 6);
			ws.mergeCells(1, row + 4, 5, row + 6);
			ws.mergeCells(0, row + 7, 0, row + 9);
			ws.mergeCells(1, row + 7, 5, row + 9);
			ws.mergeCells(3, row + 13, 5, row + 16);
			ws.mergeCells(1, row + 17, 5, row + 18);
			ws.mergeCells(1, row + 19, 5, row + 20);

			// 第一行
			Label label = new Label(0, row + 0, "受理工号", titleFormat);// 这个使用标题格式
			ws.addCell(label);
			label = new Label(1, row + 0, po.getBookRid(), textFormat);// 这个使用文本格式
			ws.addCell(label);

			label = new Label(2, row + 0, "受理专家", titleFormat);
			ws.addCell(label);
			label = new Label(3, row + 0, po.getExpert(), textFormat);
			ws.addCell(label);

			// 第二行
			label = new Label(0, row + 1, "用户姓名", titleFormat);
			ws.addCell(label);
			label = new Label(1, row + 1, po.getCustName(), textFormat);
			ws.addCell(label);

			label = new Label(2, row + 1, "用户性别", titleFormat);
			ws.addCell(label);
			label = new Label(3, row + 1, cts.getLabelById(po.getDictSex()),
					textFormat);
			ws.addCell(label);

			label = new Label(4, row + 1, "用户电话", titleFormat);
			ws.addCell(label);
			label = new Label(5, row + 1, po.getCustTel(), textFormat);
			ws.addCell(label);

			// 第三行
			label = new Label(0, row + 2, "用户地址", titleFormat);
			ws.addCell(label);
			label = new Label(1, row + 2, po.getCustAddr(), textFormat);
			ws.addCell(label);

			label = new Label(4, row + 2, "审核状态", titleFormat);
			ws.addCell(label);
			label = new Label(5, row + 2, po.getState(), textFormat);
			ws.addCell(label);

			// 第四行
			label = new Label(0, row + 3, "参加新农合", titleFormat);
			ws.addCell(label);
			label = new Label(1, row + 3, po.getIsParter(), textFormat);
			ws.addCell(label);

			label = new Label(2, row + 3, "添加日期", titleFormat);
			ws.addCell(label);
			label = new Label(3, row + 3, date, textFormat);
			ws.addCell(label);

			// 第五行
			label = new Label(0, row + 4, "咨询内容", titleFormat);
			ws.addCell(label);
			label = new Label(1, row + 4, po.getContents(), textFormat);
			ws.addCell(label);

			// 第六行
			label = new Label(0, row + 7, "热线答复", titleFormat);
			ws.addCell(label);
			label = new Label(1, row + 7, po.getReply(), textFormat);
			ws.addCell(label);

			// 第七行
			label = new Label(0, row + 10, "就诊类型", titleFormat);
			ws.addCell(label);
			label = new Label(1, row + 10, cts.getLabelById(po
					.getDictServiceType()), textFormat);
			ws.addCell(label);

			label = new Label(2, row + 10, "是事就诊", titleFormat);
			ws.addCell(label);
			label = new Label(3, row + 10, po.getIsVisit(), textFormat);
			ws.addCell(label);

			// 第八行
			label = new Label(0, row + 11, "预约就诊时间", titleFormat);
			ws.addCell(label);
			label = new Label(1, row + 11, beforeDate, textFormat);
			ws.addCell(label);

			label = new Label(2, row + 11, "实际就诊时间", titleFormat);
			ws.addCell(label);
			label = new Label(3, row + 11, visitDate, textFormat);
			ws.addCell(label);

			// 第九行
			label = new Label(0, row + 12, "医院审核意见", titleFormat);
			ws.addCell(label);
			label = new Label(1, row + 12, po.getHospitalAdvice(), textFormat);
			ws.addCell(label);

			// 第十行
			label = new Label(0, row + 13, "导诊员", titleFormat);
			ws.addCell(label);
			label = new Label(1, row + 13, po.getNavigator(), textFormat);
			ws.addCell(label);

			label = new Label(2, row + 13, "优惠政策", titleFormat);
			ws.addCell(label);
			label = new Label(3, row + 13, po.getFavours(), textFormat);
			ws.addCell(label);

			// 第十一行
			label = new Label(0, row + 14, "床位", titleFormat);
			ws.addCell(label);
			label = new Label(1, row + 14, po.getBed(), textFormat);
			ws.addCell(label);

			// 第十二行
			label = new Label(0, row + 15, "就诊项目", titleFormat);
			ws.addCell(label);
			label = new Label(1, row + 15, po.getItems(), textFormat);
			ws.addCell(label);

			// 第十三行
			label = new Label(0, row + 16, "总计费用", titleFormat);
			ws.addCell(label);
			label = new Label(1, row + 16, po.getCharge(), textFormat);
			ws.addCell(label);

			// 第十四行
			label = new Label(0, row + 17, "就医结果", titleFormat);
			ws.addCell(label);
			label = new Label(1, row + 17, po.getVisitResult(), textFormat);
			ws.addCell(label);

			// 第十五行
			label = new Label(0, row + 19, "跟踪服务", titleFormat);
			ws.addCell(label);
			label = new Label(1, row + 19, po.getVisitResult(), textFormat);
			ws.addCell(label);

			row = row + 22;

		} catch (Exception e) {
			System.err.println(e);
		}

		return row;
	}

}
