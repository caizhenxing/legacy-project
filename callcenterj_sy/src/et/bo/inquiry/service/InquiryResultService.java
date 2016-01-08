/**
 * 	@(#) InquiryResultService.java 2008-4-9 下午02:05:49
 *	版权所有 沈阳市卓越科技有限公司。 
 *	卓越科技 保留一切权利
 */
package et.bo.inquiry.service;

import java.util.List;

import excellence.framework.base.dto.impl.DynaBeanDTO;

/**
 * 执行调查结果的保存、查询的数据库操作
 * 实现类et.bo.inquiry.service.impl.InquiryResultServiceImpl
 * @author 梁云锋
 *
 */
public interface InquiryResultService {
	/**
	 * 取得此次调查问卷名
	 * @param topic
	 */
	public String getTopic();
	/**
	 * 取得此次调查编号
	 * @param InquiryNum
	 */
	public String getInquiryNum();
	/**
	 * 保存用户参与调查的结果信息
	 * @param resultJSON 用户参与调查结果信息的JSON串
	 */
	public void save(List answerList);
	/**
	 * 获取指定ID的调查结果信息
	 * @param id 调查结果ID
	 * @return
	 */
	public List<DynaBeanDTO> getResultInfo(String id);
}
