package excellence.framework.base.query;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;

import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;

/**
 * @author zhaoyifei
 * @version 2007-1-15
 * @see
 */
public interface MyQuery {

	static Logger log = Logger.getLogger(MyQuery.class);
	
	/**
	 * �����Ƿ���������
	 * @param order
	 */
	void setOrder(Order order);
	
	Order getOrder();

	/**
	 * �õ�dto�ڴ�ŵ�ֵ����Ϣ
	 * 
	 * @return IBaseDTO
	 */
	public abstract IBaseDTO getDto();

	/**
	 * ��dto������ֵ����Ϣ
	 * 
	 * @param dto
	 */
	public abstract void setDto(IBaseDTO dto);

	/**
	 * �õ���һ����¼
	 * 
	 * @return
	 */
	public abstract int getFirst();

	/**
	 * ���õڼ�����¼Ϊ��һ��
	 * 
	 * @param first
	 *            ������ʼ����
	 */
	public abstract void setFirst(int first);

	/**
	 * 
	 * @return
	 */
	public abstract int getFetch();

	public abstract void setFetch(int fetch);

	public abstract DetachedCriteria getDetachedCriteria();

	/**
	 * �õ�hql���
	 * 
	 * @return
	 */
	public abstract String getHql();

	/**
	 * �õ�sql���
	 * 
	 * @return
	 */
	public abstract String getSql();

	/**
	 * 
	 * @return
	 */
	public abstract Class getType();

	public abstract void setType(Class type);

	public abstract String getIdentifer();

	public abstract void setIdentifer(String identifer);

	public abstract void setDetachedCriteria(DetachedCriteria dc);

	public abstract void setHql(String hql);

	public abstract void setSql(String sql);

	public abstract void setParameter(int idx, Object para);

	public abstract void setParameter(String pname, Object para);

	public abstract Map getParameterM();

	public abstract List getParameterL();

	public abstract void setPageInfo(PageInfo pi);

	/**
	 * ��������˳��(����)
	 * 
	 * @param asc
	 */
	public abstract void setAsc(String asc);

	/**
	 * ��������˳��(����)
	 * 
	 * @param desc
	 */
	public abstract void setDesc(String desc);

	/**
	 * �õ�����˳��(����)
	 * 
	 * @return
	 */
	public abstract String getAsc();

	/**
	 * �õ�����˳��(����)
	 * 
	 * @return
	 */
	public abstract String getDesc();
}