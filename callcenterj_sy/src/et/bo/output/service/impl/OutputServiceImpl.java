/* ������    et.bo.output.service.impl
 * �ļ�����  OutputFileImpl.java
 * ע��ʱ�䣺2008-5-27 16:34:29
 * ��Ȩ���У�������׿Խ�Ƽ����޹�˾��
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
	 * ɾ������Ϣ������������Ϣ��¼�б�
	 * 
	 * @param ids
	 *            ���ID�м��ö��Ÿ������ַ���
	 * @return list �������ݺ�dto�б�
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
	 * ����ID��ȡ����������ع����¼�б�
	 * 
	 * @param ids
	 *            ���ID�м��ö��Ÿ������ַ���
	 * @return list �������ݺ�dto�б�
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
	 * ����ID��ȡ����������ع����¼�б�
	 * 
	 * @param ids
	 *            ���ID�м��ö��Ÿ������ַ���
	 * @return list �������ݺ�dto�б�
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
	 * ����ID��ȡ����������ؼ�¼�б�
	 * 
	 * @param ids
	 *            ���ID�м��ö��Ÿ������ַ���
	 * @return list �������ݺ�dto�б�
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
	 * @author wwq ����ID�õ��г��������б�
	 * @param ids
	 *            ���ID�м��ö��Ÿ������ַ���
	 * @return list �������ݺ�dto�б�
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
	 * @author wwq ����ID�õ�����׷�ٿ��б�
	 * @param ids
	 *            ���ID�м��ö��Ÿ������ַ���
	 * @return list �������ݺ�dto�б�
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
	 * ����ID��ȡ���۸���е���ؼ�¼�б�
	 * 
	 * @param ids
	 *            ���ID�м��ö��Ÿ������ַ���
	 * @return list �������ݺ�dto�б�
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
	 * ����ID��ȡ��������Ϣ������е���ؼ�¼�б�
	 * 
	 * @param ids
	 *            ���ID�м��ö��Ÿ������ַ���
	 * @return list �������ݺ�dto�б�
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
	 * ����po���ɵ�����Ϣ������Excel�ĵ�
	 * 
	 * @param po
	 *            po����
	 * @param row
	 *            �б��
	 * @return row �б��
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

			// ��һ��
			Label label = new Label(0, row + 0, "�������", titleFormat);// ���ʹ�ñ����ʽ
			ws.addCell(label);
			if (po.getDictInquiryType().equals("SYS_TREE_0000000362")) {
				label = new Label(1, row + 0, "�����ֺ�", textFormat);// ���ʹ���ı���ʽ
				ws.addCell(label);
			}
			if (po.getDictInquiryType().equals("SYS_TREE_0000000363")) {
				label = new Label(1, row + 0, "��ũ����", textFormat);
				ws.addCell(label);
			}
			if (po.getDictInquiryType().equals("SYS_TREE_0000000364")) {
				label = new Label(1, row + 0, "�г�����", textFormat);
				ws.addCell(label);
			}
			if (po.getDictInquiryType().equals("SYS_TREE_0000000365")) {
				label = new Label(1, row + 0, "��������", textFormat);
				ws.addCell(label);
			}
			if (po.getDictInquiryType().equals("SYS_TREE_0000000366")) {
				label = new Label(1, row + 0, "ҽ������", textFormat);
				ws.addCell(label);
			}
			if (po.getDictInquiryType().equals("SYS_TREE_0000000367")) {
				label = new Label(1, row + 0, "ʳƷ��ȫ", textFormat);
				ws.addCell(label);
			}
			if (po.getDictInquiryType().equals("SYS_TREE_0000000368")) {
				label = new Label(1, row + 0, "�������", textFormat);
				ws.addCell(label);
			}
			if (po.getDictInquiryType().equals("SYS_TREE_0000000369")) {
				label = new Label(1, row + 0, "���߷���", textFormat);
				ws.addCell(label);
			}

			label = new Label(2, row + 0, "��ʼʱ��", titleFormat);
			ws.addCell(label);
			label = new Label(3, row + 0, beginTime, textFormat);
			ws.addCell(label);

			label = new Label(4, row + 0, "�������", titleFormat);
			ws.addCell(label);
			label = new Label(5, row + 0, po.getOrganiztion(), textFormat);
			ws.addCell(label);

			// �ڶ���
			label = new Label(0, row + 1, "��������", titleFormat);
			ws.addCell(label);
			label = new Label(1, row + 1, po.getTopic(), textFormat);
			ws.addCell(label);

			label = new Label(2, row + 1, "����ʱ��", titleFormat);
			ws.addCell(label);
			label = new Label(3, row + 1, endTime, textFormat);
			ws.addCell(label);

			label = new Label(4, row + 1, "�� ֯ ��", titleFormat);
			ws.addCell(label);
			label = new Label(5, row + 1, po.getOrganizers(), textFormat);
			ws.addCell(label);

			// ������
			label = new Label(0, row + 2, "����Ŀ��", titleFormat);
			ws.addCell(label);
			label = new Label(1, row + 2, po.getAim(), textFormat);
			ws.addCell(label);

			label = new Label(2, row + 2, "���״̬", titleFormat);
			ws.addCell(label);
			label = new Label(3, row + 2, po.getState(), textFormat);
			ws.addCell(label);

			label = new Label(4, row + 2, "������Ա", titleFormat);
			ws.addCell(label);
			label = new Label(5, row + 2, po.getActors(), textFormat);
			ws.addCell(label);

			// ������
			label = new Label(0, row + 3, "��������", titleFormat);
			ws.addCell(label);

			label = new Label(2, row + 3, "��������", titleFormat);
			ws.addCell(label);

			label = new Label(4, row + 3, "����𰸱�ѡ���ͬ��ѡ����;�Ÿ�����˫���Զ���д��",
					titleFormat);
			ws.addCell(label);

			// ������
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
					label = new Label(0, row + (4 + i), "��ѡ��", titleFormat);
					ws.addCell(label);
				}
				if (oic.getDictQuestionType().equals("002")) {
					label = new Label(0, row + (4 + i), "��ѡ��", titleFormat);
					ws.addCell(label);
				}
				if (oic.getDictQuestionType().equals("003")) {
					label = new Label(0, row + (4 + i), "�ʴ���", titleFormat);
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
	 * ����ID��ȡ��������Ϣ�������еĵ�������¼�б�
	 * 
	 * @param ids
	 *            ���ID�м��ö��Ÿ������ַ���
	 * @return list �������ݺ�dto�б�
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
	 * ����IDȡ��OperInquiryCard��ʵ��
	 * 
	 * @param id
	 *            OperInquiryInfo��id
	 * @return oic ����ʵ��
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
				dbd.set("dictQuestionType", "��ѡ��");
			}
			if (oic.getDictQuestionType().equals("002")) {
				dbd.set("dictQuestionType", "��ѡ��");
			}
			if (oic.getDictQuestionType().equals("003")) {
				dbd.set("dictQuestionType", "�ʴ���");
			}
			dbd.set("question", oic.getQuestion());
			dbd.set("alternatives", oic.getAlternatives());
			list.add(dbd);
		}
		return list;
	}

	/**
	 * ����ID��ȡ��������Ϣ�������е���ؼ�¼�б�
	 * 
	 * @param ids
	 *            ���ID�м��ö��Ÿ������ַ���
	 * @return list �������ݺ�dto�б�
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
					dbd.set("dictQuestionType", "��ѡ��");
				}
				if (oic.getDictQuestionType().equals("002")) {
					dbd.set("dictQuestionType", "��ѡ��");
				}
				if (oic.getDictQuestionType().equals("003")) {
					dbd.set("dictQuestionType", "�ʴ���");
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
				dto.set("type", "�����ֺ�");
			}
			if (po.getDictInquiryType().equals("SYS_TREE_0000000363")) {
				dto.set("type", "��ũ����");
			}
			if (po.getDictInquiryType().equals("SYS_TREE_0000000364")) {
				dto.set("type", "�г�����");
			}
			if (po.getDictInquiryType().equals("SYS_TREE_0000000365")) {
				dto.set("type", "��������");
			}
			if (po.getDictInquiryType().equals("SYS_TREE_0000000366")) {
				dto.set("type", "ҽ������");
			}
			if (po.getDictInquiryType().equals("SYS_TREE_0000000367")) {
				dto.set("type", "ʳƷ��ȫ");
			}
			if (po.getDictInquiryType().equals("SYS_TREE_0000000368")) {
				dto.set("type", "�������");
			}
			if (po.getDictInquiryType().equals("SYS_TREE_0000000369")) {
				dto.set("type", "���߷���");
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
	 * ����po���ɵ�����Ϣ�������Excel�ĵ�
	 * 
	 * @param po
	 *            po����
	 * @param row
	 *            �б��
	 * @return row �б��
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

			// ��һ��
			Label label = new Label(0, row + 0, "��  ��", titleFormat);// ���ʹ�ñ����ʽ
			ws.addCell(label);
			label = new Label(1, row + 0, po.getReportTopic(), textFormat);// ���ʹ���ı���ʽ
			ws.addCell(label);

			label = new Label(2, row + 0, "�� �� ��", titleFormat);
			ws.addCell(label);
			label = new Label(3, row + 0, po.getReportTopic2(), textFormat);
			ws.addCell(label);

			// �ڶ���
			label = new Label(0, row + 1, "��������", titleFormat);
			ws.addCell(label);
			label = new Label(1, row + 1, po.getTopic(), textFormat);
			ws.addCell(label);

			label = new Label(2, row + 1, "�� ֯ ��", titleFormat);
			ws.addCell(label);
			label = new Label(3, row + 1, po.getOrganizers(), textFormat);
			ws.addCell(label);

			// ������
			label = new Label(0, row + 2, "�������", titleFormat);
			ws.addCell(label);
			label = new Label(1, row + 2, po.getOrganiztion(), textFormat);
			ws.addCell(label);

			label = new Label(2, row + 2, "������Ա", titleFormat);
			ws.addCell(label);
			label = new Label(3, row + 2, po.getActors(), textFormat);
			ws.addCell(label);

			// ������
			label = new Label(0, row + 3, "׫ �� ��", titleFormat);
			ws.addCell(label);
			label = new Label(1, row + 3, po.getReportCopywriter(), textFormat);
			ws.addCell(label);

			label = new Label(2, row + 3, "�� �� ��", titleFormat);
			ws.addCell(label);
			label = new Label(3, row + 3, po.getReportKeyword(), textFormat);
			ws.addCell(label);

			// ������
			label = new Label(0, row + 4, "����ʱ��", titleFormat);
			ws.addCell(label);
			label = new Label(1, row + 4, beginTime + "��" + endTime, textFormat);
			ws.addCell(label);

			label = new Label(2, row + 4, "ժ    Ҫ", titleFormat);
			ws.addCell(label);
			label = new Label(3, row + 4, po.getReportAbstract(), textFormat);
			ws.addCell(label);

			// ������
			label = new Label(0, row + 5, "��������", titleFormat);
			ws.addCell(label);
			label = new Label(1, row + 5, po.getReportSwatch(), textFormat);
			ws.addCell(label);

			label = new Label(2, row + 5, "������Ч��", titleFormat);
			ws.addCell(label);
			label = new Label(3, row + 5, po.getReportEfficiency(), textFormat);
			ws.addCell(label);

			label = new Label(4, row + 5, "���״̬", titleFormat);
			ws.addCell(label);
			label = new Label(5, row + 5, po.getReportState(), textFormat);
			ws.addCell(label);

			// ������
			label = new Label(0, row + 6, "��������", titleFormat);
			ws.addCell(label);
			label = new Label(1, row + 6, po.getReportContent(), textFormat);
			ws.addCell(label);

			// �ڰ���
			label = new Label(0, row + 7, "��������", titleFormat);
			ws.addCell(label);
			label = new Label(1, row + 7, po.getReportReview(), textFormat);
			ws.addCell(label);

			// �ھ���
			label = new Label(0, row + 8, "��    ע", titleFormat);
			ws.addCell(label);
			label = new Label(1, row + 8, po.getReportRemark(), textFormat);
			ws.addCell(label);
			List<DynaBeanDTO> ls = getInquiryResultById(po.getId());
			
			if(ls.size()>0)
			{
				//����ʱ��  ������  �û�����  ��������  ��������  �����  
				label = new Label(0,row + 9, "����ʱ�� ");
				ws.addCell(label);
				label = new Label(1,row+9, "������ ");
				ws.addCell(label);
				label = new Label(2,row+9, "�û����� ");
				ws.addCell(label);
				label = new Label(3,row+9, "�������� ");
				ws.addCell(label);
				label = new Label(4,row+9, "�������� ");
				ws.addCell(label);
				label = new Label(5,row+9, "����� ");
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
				//����ʱ��  ������  �û�����  ��������  ��������  �����  
				r.set("id",rs.getString("id"));
				r.set("time",TimeUtil.getTheTimeStr(rs.getDate("create_time"),
				"yyyy-MM-dd"));
				r.set("actor", rs.getString("actor"));// ������Ҫ������ͻ���IDת��������
				r.set("rname", rs.getString("rid"));// ������Ҫ��������ϯԱIDת��Ϊ����
				String questionType = rs.getString("question_type")==null?"":rs.getString("question_type");

				if("001".equals(questionType))
					questionType = "��ѡ��";
				if("001".equals(questionType))
					questionType = "��ѡ��";
				if("001".equals(questionType))
					questionType = "�ʴ���";
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
	 * ����������Ϣ��word�ĵ�.
	 * 
	 * @param cases
	 *            ������Ϣ����,ÿ��������Ϣ��һ��DynaBeanDTO����
	 * @param fileName
	 *            �ļ�·�����ļ���
	 */
	public void outputWordFile(List<OperCaseinfo> pos, String fileName,
			String dbType) {
		// ��word
		msWordApp = new ActiveXComponent("Word.Application");
		Dispatch.put(msWordApp, "Visible", new Variant(false));
		// �������ĵ�
		Dispatch documents = Dispatch.get(msWordApp, "Documents").toDispatch();
		document = Dispatch.call(documents, "Add").toDispatch();

		for (int i = 0; i < pos.size(); i++) { // ��ÿ����¼����ʽд���ĵ���
			writeWord(pos.get(i), dbType, "" + (i + 1));
		}
		setPageInfo(); // ����ҳ��ҳβ
		Dispatch.call(document, "SaveAs", fileName);// �ļ����Ϊ
		// �ر��ĵ�
		Dispatch.call(document, "Close", new Variant(0));// 0��û�д洢�ı䣻-1���д洢�ı䣻-2����ʾ�洢�ı�
		document = null;
		// �ر�word
		Dispatch.call(msWordApp, "Quit");
		msWordApp = null;

	}

	/**
	 * ����������Ϣ��txt�ĵ�.
	 * 
	 * @param cases
	 *            ������Ϣ����,ÿ��������Ϣ��һ��DynaBeanDTO�������
	 * @param fileName
	 *            ���ɵ��ļ�·�����ļ���
	 */
	public void outputTxtFile(List<OperCaseinfo> pos, String fileName,
			String dbType) {

		try {
			FileWriter fw = new FileWriter(fileName, true);
			BufferedWriter bw = new BufferedWriter(fw);

			if (dbType.equals("general") || dbType.equals("focus")) {// �������ͨ�򽹵�

				for (int i = 0; i < pos.size(); i++) { // ��ÿ����¼����ʽд���ĵ���
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
					bw.write((i + 1) + "��" + CustAddr + " " + CustName + " "
							+ CustTel + "(" + CaseTime + ")" + "������ѯ��"
							+ CaseContent + "(" + CaseExpert + ")");
					bw.newLine();
					String CaseReply = (po.getCaseReply() == null) ? "" : po
							.getCaseReply();
					bw.write("����� " + CaseReply);
					bw.newLine();
					String CaseReview = (po.getCaseReview() == null) ? "" : po
							.getCaseReview();
					bw.write("������������ " + CaseReview);
					bw.newLine();
					String Remark = (po.getRemark() == null) ? "" : po
							.getRemark();
					bw.write("����ע�� " + Remark);
					bw.newLine();
					bw.newLine();
					bw.newLine();
				}

			} else if (dbType.equals("hzinfo")) {// �����ʽ

				for (int i = 0; i < pos.size(); i++) { // ��ÿ����¼����ʽд���ĵ���
					OperCaseinfo po = pos.get(i);
					String CaseContent = (po.getCaseContent() == null) ? ""
							: po.getCaseContent();
					bw.write("���������⡿ " + CaseContent);
					bw.newLine();
					String CustName = (po.getCustName() == null) ? "" : po
							.getCustName();
					bw.write("���û������� " + CustName);
					bw.newLine();
					String CustTel = (po.getCustTel() == null) ? "" : po
							.getCustTel();
					bw.write("���û��绰�� " + CustTel);
					bw.newLine();
					String CustAddr = (po.getCustAddr() == null) ? "" : po
							.getCustAddr();
					bw.write("���û���ַ�� " + CustAddr);
					bw.newLine();
					String CaseExpert = (po.getCaseExpert() == null) ? "" : po
							.getCaseExpert();
					bw.write("������ר�ҡ� " + CaseExpert);
					bw.newLine();
					String CaseJoins = (po.getCaseJoins() == null) ? "" : po
							.getCaseJoins();
					bw.write("��������Ա�� " + CaseJoins);
					bw.newLine();
					String CaseTime = (po.getCaseTime() == null) ? "" : po
							.getCaseTime().toString();
					bw.write("������ʱ�䡿 " + CaseTime);
					bw.newLine();
					String CaseReview = (po.getCaseReview() == null) ? "" : po
							.getCaseReview();
					bw.write("������������ " + CaseReview);
					bw.newLine();
					String CaseReport = (po.getCaseReport() == null) ? "" : po
							.getCaseReport();
					bw.write("����ر����� " + CaseReport);
					bw.newLine();
					bw.newLine();
					bw.newLine();
				}

			} else if (dbType.equals("effect")) {// Ч����ʽ

				for (int i = 0; i < pos.size(); i++) { // ��ÿ����¼����ʽд���ĵ���
					OperCaseinfo po = pos.get(i);
					String CaseTime = (po.getCaseTime() == null) ? "" : po
							.getCaseTime().toString();
					bw.write("������ʱ�䡿 " + CaseTime);
					bw.newLine();
					String CustName = (po.getCustName() == null) ? "" : po
							.getCustName().toString();
					bw.write("���û������� " + CustName);
					bw.newLine();
					String CustTel = (po.getCustTel() == null) ? "" : po
							.getCustTel().toString();
					bw.write("���û��绰�� " + CustTel);
					bw.newLine();
					String CustAddr = (po.getCustAddr() == null) ? "" : po
							.getCustAddr().toString();
					bw.write("���û���ַ�� " + CustAddr);
					bw.newLine();
					String CaseContent = (po.getCaseContent() == null) ? ""
							: po.getCaseContent().toString();
					bw.write("����������� " + CaseContent);
					bw.newLine();
					String CaseReply = (po.getCaseReply() == null) ? "" : po
							.getCaseReply().toString();
					bw.write("������������ " + CaseReply);
					bw.newLine();
					String CaseReport = (po.getCaseReport() == null) ? "" : po
							.getCaseReport().toString();
					bw.write("����ر����� " + CaseReport);

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
	 * ����������Ϣ��Excel�ĵ�.
	 * 
	 * @param cases
	 *            ������Ϣ����,ÿ��������Ϣ��һ��DynaBeanDTO�������
	 * @param fileName
	 *            ���ɵ��ļ�·�����ļ���
	 */
	public void outputExcelFile(List pos, String fileName, String dbType) {
		System.out.println("--writeExcelInquiryCard----------inquirycard--");
		try {
			OutputStream os = new FileOutputStream(fileName);
			WritableWorkbook wwb = Workbook.createWorkbook(os);
			WritableSheet ws = wwb.createSheet("���ݵ���", 0);// ���ɵ�һ�ű�
			// �������ָ�ʽ
			WritableFont wfText = new WritableFont(WritableFont
					.createFont("����"), 10, WritableFont.NO_BOLD);
			WritableCellFormat textFormat = new WritableCellFormat(wfText);
			textFormat.setBorder(Border.ALL, BorderLineStyle.THIN);// �ӱ��
			textFormat.setVerticalAlignment(VerticalAlignment.TOP);// ��ֱ����Ϊ����
			textFormat.setWrap(true);// �Զ�����
			// ���ñ����ʽ
			WritableFont wfTitle = new WritableFont(WritableFont
					.createFont("����"), 10, WritableFont.BOLD);
			WritableCellFormat titleFormat = new WritableCellFormat(wfTitle);
			titleFormat.setBorder(Border.ALL, BorderLineStyle.THIN);// �ӱ��
			titleFormat.setVerticalAlignment(VerticalAlignment.CENTRE);// ��ֱ���ж���
			if(dbType.equals("question")){
				int row = 0;
				for (int i = 0; i < pos.size(); i++) { // ��ÿ����¼����ʽд���ĵ���
					List<String> lit = (List)pos.get(i);
					row = writeExcelQuestion(ws, lit, row, textFormat,
							titleFormat) + 4;// ������
				}
			}
			if (dbType.equals("general") || dbType.equals("focus")
					|| dbType.equals("hzinfo") || dbType.equals("effect")) {// ����ǰ������ĳĳĳ��ʽ

				int row = 0;
				for (int i = 0; i < pos.size(); i++) { // ��ÿ����¼����ʽд���ĵ���
					OperCaseinfo po = (OperCaseinfo) pos.get(i);
					row = writeExcelCase(ws, po, row, dbType, textFormat,
							titleFormat) + 4;// ������
				}
			} else if (dbType.equals("price")) {

				writeExcelPrice(ws, pos, textFormat, titleFormat);// ������
			} else if (dbType.equals("sad")) {

				int row = 0;
				for (int i = 0; i < pos.size(); i++) { // ��ÿ����¼����ʽд���ĵ���
					OperSadinfo po = (OperSadinfo) pos.get(i);
					row = writeExcelSad(ws, po, row, textFormat, titleFormat) + 4;// ������
				}
			} else if (dbType.equals("trace")) {// ����׷��
				// writeExcelTrace(pos, fileName);
				int row = 0;
				for (int i = 0; i < pos.size(); i++) { // ��ÿ����¼����ʽд���ĵ���
					OperFocusinfo po = (OperFocusinfo) pos.get(i);
					row = writeExcelTrace(ws, po, row, textFormat, titleFormat) + 4;// ������
				}
			} else if (dbType.equals("mart")) {// ����׷��
				// writeExcelTrace(pos, fileName);
				int row = 0;
				for (int i = 0; i < pos.size(); i++) { // ��ÿ����¼����ʽд���ĵ���
					OperMarkanainfo po = (OperMarkanainfo) pos.get(i);
					row = writeExcelMart(ws, po, row, textFormat, titleFormat) + 4;// ������
				}
			} else if (dbType.equals("crop")) {
				int row = 0;
				for (int i = 0; i < pos.size(); i++) { // ��ÿ����¼����ʽд���ĵ���
					OperCorpinfo po = (OperCorpinfo) pos.get(i);
					row = writeExcelCrop(ws, po, row, textFormat, titleFormat) + 4;// ������
				}
			} else if (dbType.equals("medicinfo")) {
				int row = 0;
				for (int i = 0; i < pos.size(); i++) { // ��ÿ����¼����ʽд���ĵ���
					OperMedicinfo po = (OperMedicinfo) pos.get(i);
					row = writeExcelMedicinfo(ws, po, row, textFormat,
							titleFormat) + 4;// ������
				}
			} else if (dbType.equals("bookmedicinfo")) {
				int row = 0;
				for (int i = 0; i < pos.size(); i++) { // ��ÿ����¼����ʽд���ĵ���
					OperBookMedicinfo po = (OperBookMedicinfo) pos.get(i);
					row = writeExcelBookMedicinfo(ws, po, row, textFormat,
							titleFormat) + 4;// ������
				}
			} else if (dbType.equals("inquiryresult")) {
				int row = 0;
				for (int i = 0; i < pos.size(); i++) { // ��ÿ����¼����ʽд���ĵ���
					OperInquiryinfo po = (OperInquiryinfo) pos.get(i);
					row = writeExcelInquiryinfo(ws, po, row, textFormat,
							titleFormat) + 8;// ������
				}
			} else if (dbType.equals("inquirycard")) {
				int row = 0;
				for (int i = 0; i < pos.size(); i++) { // ��ÿ����¼����ʽд���ĵ���
					OperInquiryinfo po = (OperInquiryinfo) pos.get(i);
					row = writeExcelInquiryCard(ws, po, row, textFormat,
							titleFormat) + 4;// ������
				}
			}
			wwb.write();
			wwb.close();
			os.close();

		} catch (Exception e) {
			System.err.println(e);
		}
	}

	// //////////////////�����Ǿ����������////////////////////

	/**
	 * ��ʼһ��һ����д�ļ�
	 * 
	 * @param dto
	 *            the dto
	 */
	private void writeWord(OperCaseinfo po, String dbType, String order) {

		selection = Dispatch.get(msWordApp, "Selection").toDispatch();
		Dispatch font = Dispatch.get(selection, "Font").toDispatch();// ���͸�ʽ����Ҫ�����

		if (dbType.equals("general") || dbType.equals("focus")) {// �������ͨ�򽹵�

			Dispatch.put(selection, "Text",
					order + "��" + po.getCustAddr() == null ? "" : po
							.getCustAddr()
							+ " " + po.getCustName() == null ? "" : po
							.getCustName()
							+ " " + po.getCustTel() == null ? "" : po
							.getCustTel()
							+ "(" + po.getCaseTime() == null ? ""
							: po.getCaseTime() + ")" + " " + "������ѯ��"
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

			Dispatch.put(selection, "Text", "����� ");
			Dispatch.put(font, "Bold", 1);
			Dispatch.call(selection, "MoveRight", new Variant(1),
					new Variant(1));
			Dispatch.put(selection, "Text", po.getCaseReply());
			Dispatch.put(font, "Bold", "0");
			Dispatch.call(selection, "MoveDown");
			Dispatch.call(selection, "TypeParagraph");

			Dispatch.put(selection, "Text", "������������ ");
			Dispatch.put(font, "Bold", 1);
			Dispatch.call(selection, "MoveRight", new Variant(1),
					new Variant(1));
			Dispatch.put(selection, "Text", po.getCaseReview());
			Dispatch.put(font, "Bold", "0");
			Dispatch.call(selection, "MoveDown");
			Dispatch.call(selection, "TypeParagraph");

			Dispatch.put(selection, "Text", "����ע�� ");
			Dispatch.put(font, "Bold", 1);
			Dispatch.call(selection, "MoveRight", new Variant(1),
					new Variant(1));
			Dispatch.put(selection, "Text", po.getRemark());
			Dispatch.put(font, "Bold", "0");
			Dispatch.call(selection, "MoveDown");
			Dispatch.call(selection, "TypeParagraph");// ��һ�ж���

		} else if (dbType.equals("hzinfo")) {// �����ʽ

			Dispatch.put(selection, "Text", "���������⡿ ");
			Dispatch.put(font, "Bold", 1);
			Dispatch.call(selection, "MoveRight", new Variant(1),
					new Variant(1));
			Dispatch.put(selection, "Text", po.getCaseContent());
			Dispatch.put(font, "Bold", "0");
			Dispatch.call(selection, "MoveDown");
			Dispatch.call(selection, "TypeParagraph");

			Dispatch.put(selection, "Text", "���û������� ");
			Dispatch.put(font, "Bold", 1);
			Dispatch.call(selection, "MoveRight", new Variant(1),
					new Variant(1));
			Dispatch.put(selection, "Text", po.getCustName());
			Dispatch.put(font, "Bold", "0");
			Dispatch.call(selection, "MoveDown");
			Dispatch.call(selection, "TypeParagraph");

			Dispatch.put(selection, "Text", "���û��绰�� ");
			Dispatch.put(font, "Bold", 1);
			Dispatch.call(selection, "MoveRight", new Variant(1),
					new Variant(1));
			Dispatch.put(selection, "Text", po.getCustTel());
			Dispatch.put(font, "Bold", "0");
			Dispatch.call(selection, "MoveDown");
			Dispatch.call(selection, "TypeParagraph");

			Dispatch.put(selection, "Text", "���û���ַ�� ");
			Dispatch.put(font, "Bold", 1);
			Dispatch.call(selection, "MoveRight", new Variant(1),
					new Variant(1));
			Dispatch.put(selection, "Text", po.getCustAddr());
			Dispatch.put(font, "Bold", "0");
			Dispatch.call(selection, "MoveDown");
			Dispatch.call(selection, "TypeParagraph");

			Dispatch.put(selection, "Text", "������ר�ҡ� ");
			Dispatch.put(font, "Bold", 1);
			Dispatch.call(selection, "MoveRight", new Variant(1),
					new Variant(1));
			Dispatch.put(selection, "Text", po.getCaseExpert());
			Dispatch.put(font, "Bold", "0");
			Dispatch.call(selection, "MoveDown");
			Dispatch.call(selection, "TypeParagraph");

			Dispatch.put(selection, "Text", "��������Ա�� ");
			Dispatch.put(font, "Bold", 1);
			Dispatch.call(selection, "MoveRight", new Variant(1),
					new Variant(1));
			Dispatch.put(selection, "Text", po.getCaseJoins());
			Dispatch.put(font, "Bold", "0");
			Dispatch.call(selection, "MoveDown");
			Dispatch.call(selection, "TypeParagraph");

			Dispatch.put(selection, "Text", "������ʱ�䡿 ");
			Dispatch.put(font, "Bold", 1);
			Dispatch.call(selection, "MoveRight", new Variant(1),
					new Variant(1));
			Dispatch.put(selection, "Text", po.getCaseTime());
			Dispatch.put(font, "Bold", "0");
			Dispatch.call(selection, "MoveDown");
			Dispatch.call(selection, "TypeParagraph");

			Dispatch.put(selection, "Text", "������������ ");
			Dispatch.put(font, "Bold", 1);
			Dispatch.call(selection, "MoveRight", new Variant(1),
					new Variant(1));
			Dispatch.put(selection, "Text", po.getCaseReview());
			Dispatch.put(font, "Bold", "0");
			Dispatch.call(selection, "MoveDown");
			Dispatch.call(selection, "TypeParagraph");

			Dispatch.put(selection, "Text", "����ر����� ");
			Dispatch.put(font, "Bold", 1);
			Dispatch.call(selection, "MoveRight", new Variant(1),
					new Variant(1));
			Dispatch.put(selection, "Text", po.getCaseReport());
			Dispatch.put(font, "Bold", "0");
			Dispatch.call(selection, "MoveDown");
			Dispatch.call(selection, "TypeParagraph");

		} else if (dbType.equals("effect")) {// Ч����ʽ

			Dispatch.put(selection, "Text", "������ʱ�䡿 ");
			Dispatch.put(font, "Bold", 1);
			Dispatch.call(selection, "MoveRight", new Variant(1),
					new Variant(1));
			Dispatch.put(selection, "Text", po.getCaseTime());
			Dispatch.put(font, "Bold", "0");
			Dispatch.call(selection, "MoveDown");
			Dispatch.call(selection, "TypeParagraph");

			Dispatch.put(selection, "Text", "���û������� ");
			Dispatch.put(font, "Bold", 1);
			Dispatch.call(selection, "MoveRight", new Variant(1),
					new Variant(1));
			Dispatch.put(selection, "Text", po.getCustName());
			Dispatch.put(font, "Bold", "0");
			Dispatch.call(selection, "MoveDown");
			Dispatch.call(selection, "TypeParagraph");

			Dispatch.put(selection, "Text", "���û��绰�� ");
			Dispatch.put(font, "Bold", 1);
			Dispatch.call(selection, "MoveRight", new Variant(1),
					new Variant(1));
			Dispatch.put(selection, "Text", po.getCustTel());
			Dispatch.put(font, "Bold", "0");
			Dispatch.call(selection, "MoveDown");
			Dispatch.call(selection, "TypeParagraph");

			Dispatch.put(selection, "Text", "���û���ַ�� ");
			Dispatch.put(font, "Bold", 1);
			Dispatch.call(selection, "MoveRight", new Variant(1),
					new Variant(1));
			Dispatch.put(selection, "Text", po.getCustAddr());
			Dispatch.put(font, "Bold", "0");
			Dispatch.call(selection, "MoveDown");
			Dispatch.call(selection, "TypeParagraph");

			Dispatch.put(selection, "Text", "����������� ");
			Dispatch.put(font, "Bold", 1);
			Dispatch.call(selection, "MoveRight", new Variant(1),
					new Variant(1));
			Dispatch.put(selection, "Text", po.getCaseContent());
			Dispatch.put(font, "Bold", "0");
			Dispatch.call(selection, "MoveDown");
			Dispatch.call(selection, "TypeParagraph");

			Dispatch.put(selection, "Text", "������������ ");
			Dispatch.put(font, "Bold", 1);
			Dispatch.call(selection, "MoveRight", new Variant(1),
					new Variant(1));
			Dispatch.put(selection, "Text", po.getCaseReply());
			Dispatch.put(font, "Bold", "0");
			Dispatch.call(selection, "MoveDown");
			Dispatch.call(selection, "TypeParagraph");

			Dispatch.put(selection, "Text", "����ر����� ");
			Dispatch.put(font, "Bold", 1);
			Dispatch.call(selection, "MoveRight", new Variant(1),
					new Variant(1));
			Dispatch.put(selection, "Text", po.getCaseReport());
			Dispatch.put(font, "Bold", "0");
			Dispatch.call(selection, "MoveDown");
			Dispatch.call(selection, "TypeParagraph");

		}

		// Ҫ��ÿ�����������
		Dispatch.call(selection, "TypeParagraph");// ��һ�ж���
		Dispatch.call(selection, "TypeParagraph");// ��һ�ж���
		Dispatch.call(selection, "TypeParagraph");// ��һ�ж���

	}

	/**
	 * ����ҳ��ҳβ
	 */
	private void setPageInfo() {
		Dispatch.call(selection, "MoveRight", new Variant(1), new Variant(1));
		Dispatch alignment = Dispatch.get(selection, "ParagraphFormat")
				.toDispatch();
		// ����ҳ��ҳβ
		// ȡ�û�������
		Dispatch activeWindow = msWordApp.getProperty("ActiveWindow")
				.toDispatch();
		// ȡ�û�������
		Dispatch activePane = Dispatch.get(activeWindow, "ActivePane")
				.toDispatch();
		// ȡ���Ӵ�����
		Dispatch view = Dispatch.get(activePane, "View").toDispatch();
		// 9������ҳ��(�α����ڴ�)
		Dispatch.put(view, "SeekView", "9");// ҳ���е���Ϣ
		Dispatch.put(alignment, "Alignment", "2");
		Dispatch.put(selection, "Text", "��ũ���ߵ��Ͱ���");
		// 10������ҳβ(�α����ڴ�)
		Dispatch.put(view, "SeekView", "10");// ҳβ�е���Ϣ
		Dispatch.put(alignment, "Alignment", "1");
		Dispatch.put(selection, "Text", "��ũ����  12316");
	}

	/**
	 * ����po����Excel�����ĵ�
	 * 
	 * @param po
	 *            po����
	 * @param row
	 *            �б��
	 * @return row �б��
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

			if (dbType.equals("general") || dbType.equals("focus")) {// �������ͨ�򽹵�

				ws.setColumnView(1, 15);// �����п�,��2��
				ws.setColumnView(3, 15);
				ws.setColumnView(5, 15);

				ws.setRowView(row + 3, 1000);// �����иߣ���4��
				ws.setRowView(row + 4, 1000);
				ws.setRowView(row + 5, 1000);

				ws.mergeCells(1, row + 3, 5, row + 3);// �ϲ���Ԫ��
				ws.mergeCells(1, row + 4, 5, row + 4);
				ws.mergeCells(1, row + 5, 5, row + 5);
				// ��ʼд����
				Label label = new Label(0, row + 0, "��������", titleFormat);// �����ʽ
				ws.addCell(label);
				label = new Label(2, row + 0, "����ר��", titleFormat);
				ws.addCell(label);
				label = new Label(4, row + 0, "�û�����", titleFormat);
				ws.addCell(label);
				label = new Label(0, row + 1, "����С��", titleFormat);
				ws.addCell(label);
				label = new Label(2, row + 1, "������", titleFormat);
				ws.addCell(label);
				label = new Label(4, row + 1, "�û��绰", titleFormat);
				ws.addCell(label);
				label = new Label(0, row + 2, "��������", titleFormat);
				ws.addCell(label);
				label = new Label(2, row + 2, "����ʱ��", titleFormat);
				ws.addCell(label);
				label = new Label(4, row + 2, "�û���ַ", titleFormat);
				ws.addCell(label);
				label = new Label(0, row + 3, "��������", titleFormat);
				ws.addCell(label);
				label = new Label(0, row + 4, "�����", titleFormat);
				ws.addCell(label);
				label = new Label(0, row + 5, "��ע", titleFormat);
				ws.addCell(label);
				// ��ʼд�ı���
				label = new Label(1, row + 0, po.getCaseAttr1(), textFormat);// ʹ���ı���ʽ
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
				row = row + 5;// �ƶ�����һ�ε�����

			} else if (dbType.equals("hzinfo")) {// �����ʽ

				ws.mergeCells(1, row + 0, 5, row + 0); // �ϲ���Ԫ��
				ws.mergeCells(2, row + 3, 5, row + 3);
				ws.mergeCells(1, row + 4, 5, row + 4);
				ws.mergeCells(1, row + 5, 5, row + 5);
				ws.mergeCells(1, row + 6, 5, row + 6);

				ws.setColumnView(1, 15); // �����п�,��1��
				ws.setColumnView(3, 15);
				ws.setColumnView(5, 15);

				ws.setRowView(row + 4, 1000);
				ws.setRowView(row + 5, 1000);
				ws.setRowView(row + 6, 1000);

				Label label = new Label(0, row + 0, "��������", titleFormat);// ʹ�ø�ʽ��
				ws.addCell(label);
				label = new Label(1, row + 0, po.getCaseContent(), textFormat);// ʹ�ø�ʽ��
				ws.addCell(label);

				label = new Label(0, row + 1, "�û�����", titleFormat);// ���ʹ�ñ����ʽ
				ws.addCell(label);
				label = new Label(1, row + 1, po.getCustName(), textFormat);// ���ʹ�ñ����ʽ
				ws.addCell(label);

				label = new Label(2, row + 1, "�û��绰", titleFormat);// ���ʹ�ñ����ʽ
				ws.addCell(label);
				label = new Label(3, row + 1, po.getCustTel(), textFormat);// ���ʹ�ñ����ʽ
				ws.addCell(label);

				label = new Label(4, row + 1, "�û���ַ", titleFormat);// ���ʹ�ñ����ʽ
				ws.addCell(label);
				label = new Label(5, row + 1, po.getCustAddr(), textFormat);// ���ʹ�ñ����ʽ
				ws.addCell(label);

				label = new Label(0, row + 2, "����ר��", titleFormat);// ���ʹ�ñ����ʽ
				ws.addCell(label);
				label = new Label(1, row + 2, po.getCaseExpert(), textFormat);// ���ʹ�ñ����ʽ
				ws.addCell(label);

				label = new Label(2, row + 2, "������", titleFormat);// ���ʹ�ñ����ʽ
				ws.addCell(label);
				label = new Label(3, row + 2, po.getCaseRid(), textFormat);// ���ʹ�ñ����ʽ
				ws.addCell(label);

				label = new Label(4, row + 2, "����ʱ��", titleFormat);// ���ʹ�ñ����ʽ
				ws.addCell(label);
				label = new Label(5, row + 2, date, textFormat);// ���ʹ�ñ����ʽ
				ws.addCell(label);

				label = new Label(0, row + 3, "���ﵥλ����Ա", titleFormat);// ���ʹ�ñ����ʽ
				ws.addCell(label);
				label = new Label(2, row + 3, po.getCaseJoins(), textFormat);// ���ʹ�ñ����ʽ
				ws.addCell(label);

				label = new Label(0, row + 4, "�������", titleFormat);// ���ʹ�ñ����ʽ
				ws.addCell(label);
				label = new Label(1, row + 4, po.getCaseReply(), textFormat);// ���ʹ�ñ����ʽ
				ws.addCell(label);

				label = new Label(0, row + 5, "��������", titleFormat);// ���ʹ�ñ����ʽ
				ws.addCell(label);
				label = new Label(1, row + 5, po.getCaseReview(), textFormat);// ���ʹ�ñ����ʽ
				ws.addCell(label);

				label = new Label(0, row + 6, "��ر���", titleFormat);// ���ʹ�ñ����ʽ
				ws.addCell(label);
				label = new Label(1, row + 6, po.getCaseReport(), textFormat);// ���ʹ�ñ����ʽ
				ws.addCell(label);
				row = row + 6;// �ƶ�����һ�ε�����

			} else if (dbType.equals("effect")) {// Ч����ʽ

				ws.setColumnView(1, 15);// ���õ�2�еĿ��Ϊ15�����ؿ�
				ws.setColumnView(3, 15);
				ws.setColumnView(5, 15);

				ws.setRowView(row + 2, 1000);// ���õ�3�еĸ߶�Ϊ1000������
				ws.setRowView(row + 3, 1000);
				ws.setRowView(row + 4, 1000);

				ws.mergeCells(1, row + 2, 5, row + 2);// �ϲ���Ԫ��
				ws.mergeCells(1, row + 3, 5, row + 3);
				ws.mergeCells(1, row + 4, 5, row + 4);

				// ��һ��ͷ2����Ԫ��
				Label label = new Label(0, row + 0, "����ʱ��", titleFormat);// ���ʹ�ñ����ʽ
				ws.addCell(label);
				label = new Label(1, row + 0, date, textFormat);// ���ʹ���ı���ʽ
				ws.addCell(label);
				// ��һ���м�2����Ԫ��
				label = new Label(2, row + 0, "������", titleFormat);
				ws.addCell(label);
				label = new Label(3, row + 0, po.getCaseRid(), textFormat);
				ws.addCell(label);
				// ��һ�к�2����Ԫ��
				label = new Label(4, row + 0, "����ר��", titleFormat);
				ws.addCell(label);
				label = new Label(5, row + 0, po.getCaseExpert(), textFormat);
				ws.addCell(label);
				// �ڶ���ͷ2����Ԫ��
				label = new Label(0, row + 1, "�û�����", titleFormat);
				ws.addCell(label);
				label = new Label(1, row + 1, po.getCustName(), textFormat);
				ws.addCell(label);
				// �ڶ����м�2����Ԫ��
				label = new Label(2, row + 1, "�û��绰", titleFormat);
				ws.addCell(label);
				label = new Label(3, row + 1, po.getCustTel(), textFormat);
				ws.addCell(label);
				// �ڶ��к�2����Ԫ��
				label = new Label(4, row + 1, "�û���ַ", titleFormat);
				ws.addCell(label);
				label = new Label(5, row + 1, po.getCustAddr(), textFormat);
				ws.addCell(label);
				// ������
				label = new Label(0, row + 2, "�������", titleFormat);
				ws.addCell(label);
				label = new Label(1, row + 2, po.getCaseContent(), textFormat);
				ws.addCell(label);
				// ������
				label = new Label(0, row + 3, "��������", titleFormat);
				ws.addCell(label);
				label = new Label(1, row + 3, po.getCaseReply(), textFormat);
				ws.addCell(label);
				// ������
				label = new Label(0, row + 4, "��ر���", titleFormat);
				ws.addCell(label);
				label = new Label(1, row + 4, po.getCaseReport(), textFormat);
				ws.addCell(label);
				row = row + 4;// �ƶ�����һ�ε�����
			}
		} catch (Exception e) {
			System.err.println(e);
		}

		return row;
	}

	/**
	 * ���ݷ����¼�ﺬ��024007009���������������Excel�����ĵ�
	 * 
	 * @param po
	 *            po list����
	 * @param row
	 *            �б��
	 * @return row �б��
	 */
	private int writeExcelQuestion(WritableSheet ws, List<String> po, int row,
			WritableCellFormat textFormat,WritableCellFormat titleFormat) {
		try {
//			System.out.println("3: "+po.get(4));
				ws.setColumnView(1, 15);// �����п�,��2��
				ws.setColumnView(3, 15);
				ws.setColumnView(5, 15);
				ws.setColumnView(7, 15);
				// ��ʼд����
				Label label = new Label(0, row + 0, "�û��绰", titleFormat);// �����ʽ
				ws.addCell(label);
				label = new Label(2, row + 0, "�û�����", titleFormat);
				ws.addCell(label);
				label = new Label(4, row + 0, "�û���ַ", titleFormat);
				ws.addCell(label);
				label = new Label(6, row + 0, "����ʱ��", titleFormat);
				ws.addCell(label);
				
				// ��ʼд�ı���
				label = new Label(1, row + 0, po.get(0), textFormat);// ʹ���ı���ʽ
				ws.addCell(label);
				label = new Label(3, row + 0, po.get(1), textFormat);
				ws.addCell(label);
				label = new Label(5, row + 0, po.get(2), textFormat);
				ws.addCell(label);
				label = new Label(7, row + 0, po.get(3), textFormat);
				ws.addCell(label);
			
				row = row - 2;// �ƶ�����һ�ε�����

		} catch (Exception e) {
			System.err.println(e);
		}
		return row;
	}
	
	/**
	 * ����po����Excel�۸��ĵ�
	 */
	private void writeExcelPrice(WritableSheet ws, List pos,
			WritableCellFormat textFormat, WritableCellFormat titleFormat) {

		try {
			ws.setColumnView(0, 20);
			ws.setColumnView(1, 20);
			ws.setColumnView(2, 20);
			ws.setColumnView(3, 20);
			ws.setColumnView(4, 20);
			Label label = new Label(0, 0, "����ʱ�� ", titleFormat);// ���ʹ�ñ����ʽ
			ws.addCell(label);
			label = new Label(1, 0, "��Ʒ����", titleFormat);
			ws.addCell(label);
			label = new Label(2, 0, "����", titleFormat);
			ws.addCell(label);
			label = new Label(3, 0, "�۸� ", titleFormat);
			ws.addCell(label);
			label = new Label(4, 0, "�۸�����", titleFormat);
			ws.addCell(label);
			label = new Label(5, 0, "��ע", titleFormat);
			ws.addCell(label);
			for (int i = 0; i < pos.size(); i++) { // ��ÿ����¼����ʽд���ĵ���
				OperPriceinfo po = (OperPriceinfo) pos.get(i);
				String date = "";
				if (po.getDeployTime() != null) {
					date = po.getOperTime().toLocaleString();
					date = date.substring(0, date.indexOf(" "));
				}
				label = new Label(0, i + 1, date, textFormat);
				ws.addCell(label);

				label = new Label(1, i + 1, po.getProductName(), textFormat);// ���ʹ���ı���ʽ
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
			// Label label = new Label(0, 0, "Ʒ�����", titleFormat);// ���ʹ�ñ����ʽ
			// ws.addCell(label);
			// label = new Label(1, 0, "��Ʒ����", titleFormat);
			// ws.addCell(label);
			// label = new Label(2, 0, "�۸�", titleFormat);
			// ws.addCell(label);
			// label = new Label(3, 0, "��ע", titleFormat);
			// ws.addCell(label);
			// label = new Label(4, 0, "��������", titleFormat);
			// ws.addCell(label);
			//
			// for (int i = 0; i < pos.size(); i++) { // ��ÿ����¼����ʽд���ĵ���
			// OperPriceinfo po = (OperPriceinfo) pos.get(i);
			// String date = "";
			// if (po.getDeployTime() != null) {
			// date = po.getOperTime().toLocaleString();
			// date = date.substring(0, date.indexOf(" "));
			// }
			// label = new Label(0, i + 1, po.getDictProductType2(),
			// textFormat);// ���ʹ���ı���ʽ
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
	 * ����po����Excel�����ĵ�
	 * 
	 * @param po
	 *            po����
	 * @param row
	 *            �б��
	 * @return row �б��
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

			ws.mergeCells(4, row + 2, 4, row + 3);// �ϲ���Ԫ��("��ע"�ı���)
			ws.mergeCells(5, row + 2, 5, row + 3);// �ϲ���Ԫ��("��ע"������)

			// ��һ��
			Label label = new Label(0, row + 0, "�� ϵ ��", titleFormat);// ���ʹ�ñ����ʽ
			ws.addCell(label);
			label = new Label(1, row + 0, po.getCustName(), textFormat);// ���ʹ���ı���ʽ
			ws.addCell(label);

			label = new Label(2, row + 0, "��������", titleFormat);
			ws.addCell(label);
			label = new Label(3, row + 0,
					cts.getLabelById(po.getDictSadType()), textFormat);
			ws.addCell(label);

			label = new Label(4, row + 0, "��Ч����", titleFormat);
			ws.addCell(label);
			label = new Label(5, row + 0, date, textFormat);
			ws.addCell(label);

			// �ڶ���
			label = new Label(0, row + 1, "��ϵ�绰", titleFormat);
			ws.addCell(label);
			label = new Label(1, row + 1, po.getCustTel(), textFormat);
			ws.addCell(label);

			label = new Label(2, row + 1, "��Ʒ����", titleFormat);
			ws.addCell(label);
			label = new Label(3, row + 1, po.getProductName(), textFormat);
			ws.addCell(label);

			label = new Label(4, row + 1, "��������", titleFormat);
			ws.addCell(label);
			label = new Label(5, row + 1, begin, textFormat);
			ws.addCell(label);

			// ������
			label = new Label(0, row + 2, "��ϵ��ַ", titleFormat);
			ws.addCell(label);
			label = new Label(1, row + 2, po.getCustAddr(), textFormat);
			ws.addCell(label);

			label = new Label(2, row + 2, "��Ʒ���", titleFormat);
			ws.addCell(label);
			label = new Label(3, row + 2, po.getProductScale(), textFormat);
			ws.addCell(label);

			label = new Label(4, row + 2, "��ע", titleFormat);
			ws.addCell(label);
			label = new Label(5, row + 2, po.getRemark(), textFormat);
			ws.addCell(label);

			// ������
			label = new Label(0, row + 3, "�ʱ�", titleFormat);
			ws.addCell(label);
			label = new Label(1, row + 3, po.getPost(), textFormat);
			ws.addCell(label);

			label = new Label(2, row + 3, "��Ʒ����", titleFormat);
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
	 * �õ�����׷�ٿ�����Ϣ
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
	 * ���㰸����Ϣ�б�excel
	 * 
	 * @param pos
	 * @return
	 */
	// private int writeExcelTrace(List pos, String fileName) throws Exception {
	// int i = 0;
	// String url = "E:\\gongneng.xls";
	// // System.out.println("��ȡ�ļ��ɹ�......");
	// Workbook wb = Workbook.getWorkbook(new File(url));
	// ByteArrayOutputStream targetFile = new ByteArrayOutputStream();
	// WritableWorkbook wwb = Workbook.createWorkbook(targetFile, wb);
	// WritableSheet wws = wwb.getSheet("Sheet1");
	//		
	//		
	// // �������ָ�ʽ
	// WritableFont wfText = new WritableFont(WritableFont
	// .createFont("����"), 10, WritableFont.NO_BOLD);
	// WritableCellFormat textFormat = new WritableCellFormat(wfText);
	// textFormat.setBorder(Border.ALL, BorderLineStyle.THIN);// �ӱ��
	// textFormat.setVerticalAlignment(VerticalAlignment.TOP);// ��ֱ����Ϊ����
	// textFormat.setWrap(true);// �Զ�����
	// // ���ñ����ʽ
	// WritableFont wfTitle = new WritableFont(WritableFont
	// .createFont("����"), 10, WritableFont.BOLD);
	// WritableCellFormat titleFormat = new WritableCellFormat(wfTitle);
	// titleFormat.setBorder(Border.ALL, BorderLineStyle.THIN);// �ӱ��
	// titleFormat.setVerticalAlignment(VerticalAlignment.CENTRE);// ��ֱ���ж���
	//		
	// // System.out.println("label��ʼ��ȡ......");
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
	// // System.out.println("���label......");
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
	// // System.out.println("���sheet......");
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

			// ws.mergeCells(4, row + 2, 4, row + 3);// �ϲ���Ԫ��("��ע"�ı���)
			// ws.mergeCells(5, row + 2, 5, row + 3);// �ϲ���Ԫ��("��ע"������)

			ws.mergeCells(1, row + 0, 3, row + 0);
			ws.mergeCells(1, row + 6, 7, row + 6);
			ws.mergeCells(1, row + 7, 7, row + 9);
			ws.mergeCells(0, row + 7, 0, row + 9);

			// ��һ��
			Label label = new Label(0, row + 0, "�� �� ��", titleFormat);// ���ʹ�ñ����ʽ
			ws.addCell(label);
			label = new Label(1, row + 0, po.getFrontFor(), textFormat);// ���ʹ���ı���ʽ
			ws.addCell(label);

			// label = new Label(2, row + 0, "��������", titleFormat);
			// ws.addCell(label);
			// label = new Label(3, row + 0,
			// cts.getLabelById(po.getDictSadType()), textFormat);
			// ws.addCell(label);

			label = new Label(4, row + 0, "�� �� ��", titleFormat);
			ws.addCell(label);
			label = new Label(5, row + 0, po.getUnderTake(), textFormat);
			ws.addCell(label);

			// �ڶ���
			label = new Label(0, row + 1, "֧������", titleFormat);
			ws.addCell(label);
			label = new Label(1, row + 1, po.getSupportTel(), textFormat);
			ws.addCell(label);

			label = new Label(2, row + 1, "֧����վ", titleFormat);
			ws.addCell(label);
			label = new Label(3, row + 1, po.getSupportSite(), textFormat);
			ws.addCell(label);

			label = new Label(4, row + 1, "��ϵ����", titleFormat);
			ws.addCell(label);
			label = new Label(5, row + 1, po.getContactMail(), textFormat);
			ws.addCell(label);

			// ������
			label = new Label(0, row + 2, "��  ��", titleFormat);
			ws.addCell(label);
			label = new Label(1, row + 2, po.getChiefEditor(), textFormat);
			ws.addCell(label);

			label = new Label(2, row + 2, "������", titleFormat);
			ws.addCell(label);
			label = new Label(3, row + 2, po.getSubEditor(), textFormat);
			ws.addCell(label);

			label = new Label(4, row + 2, "��ϯ�༩", titleFormat);
			ws.addCell(label);
			label = new Label(5, row + 2, po.getFirstEditor(), textFormat);
			ws.addCell(label);

			label = new Label(6, row + 2, "���α༩", titleFormat);
			ws.addCell(label);
			label = new Label(7, row + 2, po.getChargeEditor(), textFormat);
			ws.addCell(label);

			// ������
			label = new Label(0, row + 3, "��  ��", titleFormat);
			ws.addCell(label);
			label = new Label(1, row + 3, po.getPeriod(), textFormat);
			ws.addCell(label);

			label = new Label(2, row + 3, "��������", titleFormat);
			ws.addCell(label);
			label = new Label(3, row + 3, date, textFormat);
			ws.addCell(label);

			label = new Label(4, row + 3, "���͵�λ", titleFormat);
			ws.addCell(label);
			label = new Label(5, row + 3, po.getSendUnit(), textFormat);
			ws.addCell(label);

			// ������
			label = new Label(0, row + 4, "Ʒ  ��", titleFormat);
			ws.addCell(label);
			label = new Label(1, row + 4, po.getProductLabel(), textFormat);
			ws.addCell(label);

			label = new Label(2, row + 4, "��  ��", titleFormat);
			ws.addCell(label);
			label = new Label(3, row + 4, po.getDictFocusType(), textFormat);
			ws.addCell(label);

			label = new Label(4, row + 4, "���״̬", titleFormat);
			ws.addCell(label);
			label = new Label(5, row + 4, "", textFormat);
			ws.addCell(label);

			// ������
			label = new Label(0, row + 5, "������", titleFormat);
			ws.addCell(label);
			label = new Label(1, row + 5, po.getChiefTitle(), textFormat);
			ws.addCell(label);

			label = new Label(4, row + 5, "������", titleFormat);
			ws.addCell(label);
			label = new Label(5, row + 5, po.getSubTitle(), textFormat);
			ws.addCell(label);

			// ������
			label = new Label(0, row + 6, "ժ  Ҫ", titleFormat);
			ws.addCell(label);
			label = new Label(1, row + 6, po.getSummary(), textFormat);
			ws.addCell(label);

			// ������
			label = new Label(0, row + 7, "��  ��", titleFormat);
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

			// ws.mergeCells(4, row + 2, 4, row + 3);// �ϲ���Ԫ��("��ע"�ı���)
			// ws.mergeCells(5, row + 2, 5, row + 3);// �ϲ���Ԫ��("��ע"������)

			ws.mergeCells(1, row + 0, 3, row + 0);
			ws.mergeCells(1, row + 6, 7, row + 6);
			ws.mergeCells(1, row + 7, 7, row + 9);
			ws.mergeCells(0, row + 7, 0, row + 9);

			// ��һ��
			Label label = new Label(0, row + 0, "�� �� ��", titleFormat);// ���ʹ�ñ����ʽ
			ws.addCell(label);
			label = new Label(1, row + 0, po.getFrontFor(), textFormat);// ���ʹ���ı���ʽ
			ws.addCell(label);

			// label = new Label(2, row + 0, "��������", titleFormat);
			// ws.addCell(label);
			// label = new Label(3, row + 0,
			// cts.getLabelById(po.getDictSadType()), textFormat);
			// ws.addCell(label);

			label = new Label(4, row + 0, "�� �� ��", titleFormat);
			ws.addCell(label);
			label = new Label(5, row + 0, po.getUnderTake(), textFormat);
			ws.addCell(label);

			// �ڶ���
			label = new Label(0, row + 1, "֧������", titleFormat);
			ws.addCell(label);
			label = new Label(1, row + 1, po.getSupportTel(), textFormat);
			ws.addCell(label);

			label = new Label(2, row + 1, "֧����վ", titleFormat);
			ws.addCell(label);
			label = new Label(3, row + 1, po.getSupportSite(), textFormat);
			ws.addCell(label);

			label = new Label(4, row + 1, "��ϵ����", titleFormat);
			ws.addCell(label);
			label = new Label(5, row + 1, po.getContactMail(), textFormat);
			ws.addCell(label);

			// ������
			label = new Label(0, row + 2, "��  ��", titleFormat);
			ws.addCell(label);
			label = new Label(1, row + 2, po.getChiefEditor(), textFormat);
			ws.addCell(label);

			label = new Label(2, row + 2, "������", titleFormat);
			ws.addCell(label);
			label = new Label(3, row + 2, po.getSubEditor(), textFormat);
			ws.addCell(label);

			label = new Label(4, row + 2, "��ϯ�༩", titleFormat);
			ws.addCell(label);
			label = new Label(5, row + 2, po.getFirstEditor(), textFormat);
			ws.addCell(label);

			label = new Label(6, row + 2, "���α༩", titleFormat);
			ws.addCell(label);
			label = new Label(7, row + 2, po.getChargeEditor(), textFormat);
			ws.addCell(label);

			// ������
			label = new Label(0, row + 3, "��  ��", titleFormat);
			ws.addCell(label);
			label = new Label(1, row + 3, po.getPeriod(), textFormat);
			ws.addCell(label);

			label = new Label(2, row + 3, "��������", titleFormat);
			ws.addCell(label);
			label = new Label(3, row + 3, date, textFormat);
			ws.addCell(label);

			label = new Label(4, row + 3, "���͵�λ", titleFormat);
			ws.addCell(label);
			label = new Label(5, row + 3, po.getSendUnit(), textFormat);
			ws.addCell(label);

			// ������
			label = new Label(0, row + 4, "Ʒ  ��", titleFormat);
			ws.addCell(label);
			label = new Label(1, row + 4, po.getProductLabel(), textFormat);
			ws.addCell(label);

			label = new Label(2, row + 4, "��  ��", titleFormat);
			ws.addCell(label);
			label = new Label(3, row + 4, po.getDictCommentType(), textFormat);
			ws.addCell(label);

			label = new Label(4, row + 4, "���״̬", titleFormat);
			ws.addCell(label);
			label = new Label(5, row + 4, "", textFormat);
			ws.addCell(label);

			// ������
			label = new Label(0, row + 5, "������", titleFormat);
			ws.addCell(label);
			label = new Label(1, row + 5, po.getChiefTitle(), textFormat);
			ws.addCell(label);

			label = new Label(4, row + 5, "������", titleFormat);
			ws.addCell(label);
			label = new Label(5, row + 5, po.getSubTitle(), textFormat);
			ws.addCell(label);

			// ������
			label = new Label(0, row + 6, "ժ  Ҫ", titleFormat);
			ws.addCell(label);
			label = new Label(1, row + 6, po.getSummary(), textFormat);
			ws.addCell(label);

			// ������
			label = new Label(0, row + 7, "��  ��", titleFormat);
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

			// ws.mergeCells(4, row + 2, 4, row + 3);// �ϲ���Ԫ��("��ע"�ı���)
			// ws.mergeCells(5, row + 2, 5, row + 3);// �ϲ���Ԫ��("��ע"������)
			ws.mergeCells(0, row + 4, 0, row + 6);
			ws.mergeCells(1, row + 4, 3, row + 6);
			ws.mergeCells(0, row + 7, 0, row + 9);
			ws.mergeCells(1, row + 7, 3, row + 9);

			// ��һ��
			Label label = new Label(0, row + 0, "������", titleFormat);// ���ʹ�ñ����ʽ
			ws.addCell(label);
			label = new Label(1, row + 0, po.getCorpRid(), textFormat);// ���ʹ���ı���ʽ
			ws.addCell(label);

			label = new Label(2, row + 0, "����ר��", titleFormat);
			ws.addCell(label);
			label = new Label(3, row + 0, po.getExpert(), textFormat);
			ws.addCell(label);

			// �ڶ���
			label = new Label(0, row + 1, "�û�����", titleFormat);
			ws.addCell(label);
			label = new Label(1, row + 1, po.getCustName(), textFormat);
			ws.addCell(label);

			label = new Label(2, row + 1, "�û��绰", titleFormat);
			ws.addCell(label);
			label = new Label(3, row + 1, po.getCustTel(), textFormat);
			ws.addCell(label);

			// ������
			label = new Label(0, row + 2, "�û���ַ", titleFormat);
			ws.addCell(label);
			label = new Label(1, row + 2, po.getCustAddr(), textFormat);
			ws.addCell(label);

			label = new Label(2, row + 2, "���״̬", titleFormat);
			ws.addCell(label);
			label = new Label(3, row + 2, po.getState(), textFormat);
			ws.addCell(label);

			// ������
			label = new Label(0, row + 3, "��������", titleFormat);
			ws.addCell(label);
			label = new Label(1, row + 3, po.getDictServiceType(), textFormat);
			ws.addCell(label);

			label = new Label(2, row + 3, "����ʱ��", titleFormat);
			ws.addCell(label);
			label = new Label(3, row + 3, date, textFormat);
			ws.addCell(label);

			// ������
			label = new Label(0, row + 4, "��ѯ����", titleFormat);
			ws.addCell(label);
			label = new Label(1, row + 4, po.getContents(), textFormat);
			ws.addCell(label);

			// ������
			label = new Label(0, row + 7, "���ߴ�", titleFormat);
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

			// ws.mergeCells(4, row + 2, 4, row + 3);// �ϲ���Ԫ��("��ע"�ı���)
			// ws.mergeCells(5, row + 2, 5, row + 3);// �ϲ���Ԫ��("��ע"������)
			ws.mergeCells(1, row + 2, 3, row + 2);
			ws.mergeCells(0, row + 4, 0, row + 6);
			ws.mergeCells(1, row + 4, 5, row + 6);
			ws.mergeCells(0, row + 7, 0, row + 9);
			ws.mergeCells(1, row + 7, 5, row + 9);

			// ��һ��
			Label label = new Label(0, row + 0, "������", titleFormat);// ���ʹ�ñ����ʽ
			ws.addCell(label);
			label = new Label(1, row + 0, po.getMedicRid(), textFormat);// ���ʹ���ı���ʽ
			ws.addCell(label);

			label = new Label(2, row + 0, "����ר��", titleFormat);
			ws.addCell(label);
			label = new Label(3, row + 0, po.getExpertName(), textFormat);
			ws.addCell(label);

			// �ڶ���
			label = new Label(0, row + 1, "�û�����", titleFormat);
			ws.addCell(label);
			label = new Label(1, row + 1, po.getCustName(), textFormat);
			ws.addCell(label);

			label = new Label(2, row + 1, "�û��Ա�", titleFormat);
			ws.addCell(label);
			label = new Label(3, row + 1, cts.getLabelById(po.getDictSex()),
					textFormat);
			ws.addCell(label);

			label = new Label(4, row + 1, "�û��绰", titleFormat);
			ws.addCell(label);
			label = new Label(5, row + 1, po.getCustTel(), textFormat);
			ws.addCell(label);

			// ������
			label = new Label(0, row + 2, "�û���ַ", titleFormat);
			ws.addCell(label);
			label = new Label(1, row + 2, po.getCustAddr(), textFormat);
			ws.addCell(label);

			label = new Label(4, row + 2, "���״̬", titleFormat);
			ws.addCell(label);
			label = new Label(5, row + 2, po.getState(), textFormat);
			ws.addCell(label);

			// ������
			label = new Label(0, row + 3, "�μ���ũ��", titleFormat);
			ws.addCell(label);
			label = new Label(1, row + 3, po.getIsParter(), textFormat);
			ws.addCell(label);

			label = new Label(2, row + 3, "�������", titleFormat);
			ws.addCell(label);
			label = new Label(3, row + 3, date, textFormat);
			ws.addCell(label);

			// ������
			label = new Label(0, row + 4, "��ѯ����", titleFormat);
			ws.addCell(label);
			label = new Label(1, row + 4, po.getContents(), textFormat);
			ws.addCell(label);

			// ������
			label = new Label(0, row + 7, "���ߴ�", titleFormat);
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

			// ws.mergeCells(4, row + 2, 4, row + 3);// �ϲ���Ԫ��("��ע"�ı���)
			// ws.mergeCells(5, row + 2, 5, row + 3);// �ϲ���Ԫ��("��ע"������)
			ws.mergeCells(1, row + 2, 3, row + 2);
			ws.mergeCells(0, row + 4, 0, row + 6);
			ws.mergeCells(1, row + 4, 5, row + 6);
			ws.mergeCells(0, row + 7, 0, row + 9);
			ws.mergeCells(1, row + 7, 5, row + 9);
			ws.mergeCells(3, row + 13, 5, row + 16);
			ws.mergeCells(1, row + 17, 5, row + 18);
			ws.mergeCells(1, row + 19, 5, row + 20);

			// ��һ��
			Label label = new Label(0, row + 0, "������", titleFormat);// ���ʹ�ñ����ʽ
			ws.addCell(label);
			label = new Label(1, row + 0, po.getBookRid(), textFormat);// ���ʹ���ı���ʽ
			ws.addCell(label);

			label = new Label(2, row + 0, "����ר��", titleFormat);
			ws.addCell(label);
			label = new Label(3, row + 0, po.getExpert(), textFormat);
			ws.addCell(label);

			// �ڶ���
			label = new Label(0, row + 1, "�û�����", titleFormat);
			ws.addCell(label);
			label = new Label(1, row + 1, po.getCustName(), textFormat);
			ws.addCell(label);

			label = new Label(2, row + 1, "�û��Ա�", titleFormat);
			ws.addCell(label);
			label = new Label(3, row + 1, cts.getLabelById(po.getDictSex()),
					textFormat);
			ws.addCell(label);

			label = new Label(4, row + 1, "�û��绰", titleFormat);
			ws.addCell(label);
			label = new Label(5, row + 1, po.getCustTel(), textFormat);
			ws.addCell(label);

			// ������
			label = new Label(0, row + 2, "�û���ַ", titleFormat);
			ws.addCell(label);
			label = new Label(1, row + 2, po.getCustAddr(), textFormat);
			ws.addCell(label);

			label = new Label(4, row + 2, "���״̬", titleFormat);
			ws.addCell(label);
			label = new Label(5, row + 2, po.getState(), textFormat);
			ws.addCell(label);

			// ������
			label = new Label(0, row + 3, "�μ���ũ��", titleFormat);
			ws.addCell(label);
			label = new Label(1, row + 3, po.getIsParter(), textFormat);
			ws.addCell(label);

			label = new Label(2, row + 3, "�������", titleFormat);
			ws.addCell(label);
			label = new Label(3, row + 3, date, textFormat);
			ws.addCell(label);

			// ������
			label = new Label(0, row + 4, "��ѯ����", titleFormat);
			ws.addCell(label);
			label = new Label(1, row + 4, po.getContents(), textFormat);
			ws.addCell(label);

			// ������
			label = new Label(0, row + 7, "���ߴ�", titleFormat);
			ws.addCell(label);
			label = new Label(1, row + 7, po.getReply(), textFormat);
			ws.addCell(label);

			// ������
			label = new Label(0, row + 10, "��������", titleFormat);
			ws.addCell(label);
			label = new Label(1, row + 10, cts.getLabelById(po
					.getDictServiceType()), textFormat);
			ws.addCell(label);

			label = new Label(2, row + 10, "���¾���", titleFormat);
			ws.addCell(label);
			label = new Label(3, row + 10, po.getIsVisit(), textFormat);
			ws.addCell(label);

			// �ڰ���
			label = new Label(0, row + 11, "ԤԼ����ʱ��", titleFormat);
			ws.addCell(label);
			label = new Label(1, row + 11, beforeDate, textFormat);
			ws.addCell(label);

			label = new Label(2, row + 11, "ʵ�ʾ���ʱ��", titleFormat);
			ws.addCell(label);
			label = new Label(3, row + 11, visitDate, textFormat);
			ws.addCell(label);

			// �ھ���
			label = new Label(0, row + 12, "ҽԺ������", titleFormat);
			ws.addCell(label);
			label = new Label(1, row + 12, po.getHospitalAdvice(), textFormat);
			ws.addCell(label);

			// ��ʮ��
			label = new Label(0, row + 13, "����Ա", titleFormat);
			ws.addCell(label);
			label = new Label(1, row + 13, po.getNavigator(), textFormat);
			ws.addCell(label);

			label = new Label(2, row + 13, "�Ż�����", titleFormat);
			ws.addCell(label);
			label = new Label(3, row + 13, po.getFavours(), textFormat);
			ws.addCell(label);

			// ��ʮһ��
			label = new Label(0, row + 14, "��λ", titleFormat);
			ws.addCell(label);
			label = new Label(1, row + 14, po.getBed(), textFormat);
			ws.addCell(label);

			// ��ʮ����
			label = new Label(0, row + 15, "������Ŀ", titleFormat);
			ws.addCell(label);
			label = new Label(1, row + 15, po.getItems(), textFormat);
			ws.addCell(label);

			// ��ʮ����
			label = new Label(0, row + 16, "�ܼƷ���", titleFormat);
			ws.addCell(label);
			label = new Label(1, row + 16, po.getCharge(), textFormat);
			ws.addCell(label);

			// ��ʮ����
			label = new Label(0, row + 17, "��ҽ���", titleFormat);
			ws.addCell(label);
			label = new Label(1, row + 17, po.getVisitResult(), textFormat);
			ws.addCell(label);

			// ��ʮ����
			label = new Label(0, row + 19, "���ٷ���", titleFormat);
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
