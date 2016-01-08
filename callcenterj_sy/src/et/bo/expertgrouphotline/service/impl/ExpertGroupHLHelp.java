package et.bo.expertgrouphotline.service.impl;

import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;

public class ExpertGroupHLHelp {

	public MyQuery sqlStr(IBaseDTO dto,PageInfo pi) {
		MyQuery mq = new MyQueryImpl();
		StringBuffer sb = new StringBuffer("from ExpertgroupHotline where 1=1");
		// ��ר�ҳƺ� רҵ���� ֧���� ���� ��ϲ�ѯ����
		String s = dto.get("ehCallName").toString();
		if (s != null && !"".equals(s)) {
			sb.append(" and callName like '%" + s.trim() + "%'");
		}

		s = dto.get("ehExpertZone").toString();
		if (s != null && !"".equals(s)) {
			sb.append(" and expertZone like '%" + s.trim() + "%'");
		}

		s = dto.get("ehType").toString();
		if (s != null && !"".equals(s)) {
			sb.append(" and type like '%" + s.trim() + "%'");
		}

		//���뱣֤ǰ̨����������� 
		s = dto.get("ehAgreeLevel").toString();
		if (s != null && !"".equals(s)) {
			sb.append(" and agreelevel >= " + new Integer(s.trim()));
		}
//		sb.append(" order by surveyTime desc");
		mq.setHql(sb.toString());
		mq.setFirst(pi.getBegin());
		mq.setFetch(pi.getPageSize());
		return mq;
	}
}
