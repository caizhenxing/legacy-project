/**
 * @(#)PortCompareService.java 1.0 //
 * 
 * ��Ȩ���� ������׿Խ�Ƽ����޹�˾�� ׿Խ�Ƽ� ����һ��Ȩ��
 * 
 */
package et.bo.callcenter.portCompare.service;

import java.util.HashMap;
import java.util.List;

import excellence.framework.base.dto.IBaseDTO;

/**
 * @describe �˿ڶ��սӿ�
 * @author Ҷ����
 * @version 1.0, 2006-10-13//
 * @see
 */
public interface PortCompareService {
	/**
	 * �õ�ip���ݶ˿�
	 * 
	 * @param port
	 * @return
	 */
	public String getIpByPort(String port);

	/**
	 * @describe ȡ�ö˿���Ip�Ķ�ӦMap
	 * @param
	 * @return Map
	 */
	public HashMap getIpByPort();

	/**
	 * @describe ˢ��ȡ�ö˿���Ip�Ķ�ӦMap
	 * @param
	 * @return Map
	 */
	public HashMap flushGetIpByPort();

	/**
	 * @describe ���Ӷ��ռ�¼
	 * @param
	 * @return void
	 */
	public void addPortCompare(IBaseDTO dto);

	/**
	 * @describe �޸Ķ��ռ�¼
	 * @param
	 * @return void
	 */
	public boolean updatePortCompare(IBaseDTO dto);

	/**
	 * @describe ɾ�����ռ�¼
	 * @param
	 * @return void
	 */
	public void delPortCompare(String id);

	/**
	 * @describe ��ѯ�˿ڶ����б�
	 * @param
	 * @return List
	 */
	public List portCompareQuery();

	/**
	 * @describe ȡ�ò�ѯ����
	 * @param
	 * @return int
	 */
	public int getPortCompareSize();

	/**
	 * @describe ����Idȡ����Ϣ
	 * @param
	 * @return dto(PortCompare����)
	 */
	public IBaseDTO getPortCompareInfo(String id);

	/**
	 * @describe �ж��Ƿ�����ͬ��Port
	 * @param
	 * @return boolean trueΪ�д˶˿�
	 */
	public boolean isHaveSamePort(String port);

	/**
	 * @describe �ж��Ƿ�����ͬ��Ip
	 * @param
	 * @return boolean trueΪ�д�Ip
	 */
	public boolean isHaveSameIp(String ip);
	/**
	 * ͨ��ip�ö˿ں�
	 * @param ip
	 * @return port �˿�
	 */
	public String getPortByIp(String ip);
	/**
	 * �õ����߶˿ڵ�HashMap
	 * @return HashMap
	 */
	public HashMap getInnerPortMap();

}