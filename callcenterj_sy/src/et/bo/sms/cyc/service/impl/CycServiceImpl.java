/**
 * ����׿Խ�Ƽ����޹�˾
 * 2008-4-22
 */
package et.bo.sms.cyc.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.criterion.DetachedCriteria;


import et.bo.custinfo.service.impl.CustinfoHelp;
import et.bo.sms.ModermSendServiceHelp;
import et.bo.sms.cyc.service.CycService;
import et.bo.sms.modermSend.service.SmsSendNewService;
import et.po.HandsetNoteInfo;
import et.po.HandsetNoteInfoAlready;
import et.po.HandsetNoteInfoNot;
import et.po.OperCustinfo;
import et.po.OperSadinfo;
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
 * ʵ����
 * ����ʱ��Ϣ����ѭ����(��Ҫ�Ǽ�¼���ݿ�)
 * �����ͨ������è�շ���������CycServiceImpl�������
 * @author ������
 * @version 1.0
 *
 */
public class CycServiceImpl implements CycService{	
	private KeyService ks = null;
	private BaseDAO dao = null;
	/*
	 * ��ѭ���ŷ���
	 */
	public void cycSend() {
		// TODO Auto-generated method stub
		String sendReg = null;//��Ϣ�Ƿ��ͳɹ�
		Date da = TimeUtil.getNowTime();//�õ�ϵͳ��ǰʱ��	
		ModermSendServiceHelp mssh = new ModermSendServiceHelp();//����Help��ķ��͹��ܽӿ�
		MyQuery mq = new MyQueryImpl();
		String hql = "select hsnin from HandsetNoteInfoNot hsnin where hsnin.id=hsnin.id" +
				" and ((hsnin.sendState='3' and hsnin.schedularTime <= '"+ TimeUtil.getTheTimeStr(da,"yyyy-MM-dd hh:mm:ss")+ "') or (hsnin.sendState='4' and hsnin.operCount is null)" +
				" or (hsnin.sendState='4' and hsnin.operCount<5))";
		mq.setHql(hql);
		Object[] results = dao.findEntity(mq);
		for(int i=0; i<results.length; i++) {
			HandsetNoteInfoNot hsnin = (HandsetNoteInfoNot)results[i];	
			String id= hsnin.getId();
			String content =  hsnin.getHandsetNoteInfo().getContent();
			String receiveNum = hsnin.getReceiveNum();
			sendReg = mssh.sendmsg(content,receiveNum);//����Help��ķ��͹��ܽӿ� �Է��ص�ֵ���ж϶����Ƿ��ͳɹ�
			if(sendReg.equals("succee"))
				addHandsetNoteInfoAlready(id);	
			else
				addHandsetNoteInfoNot(id);	
		}
	}
	private void addHandsetNoteInfoNot(String id)
	{
		HandsetNoteInfoNot hsnin = (HandsetNoteInfoNot)dao.loadEntity(HandsetNoteInfoNot.class,id);		
		if("".equals(hsnin.getOperCount()) || hsnin.getOperCount()==null || hsnin.getOperCount().equals(""))
		hsnin.setOperCount("1");
		else
		hsnin.setOperCount(String.valueOf(Integer.valueOf(hsnin.getOperCount())+1));	
		dao.updateEntity(hsnin);
	}
	private void addHandsetNoteInfoAlready(String id)
	{
		String idHsnia = ks.getNext("handsetNoteInfoAlready");//����ID
		HandsetNoteInfoNot hsnin = (HandsetNoteInfoNot)dao.loadEntity(HandsetNoteInfoNot.class,id);//����ID����δ���ͱ��������������	
		HandsetNoteInfo hni = (HandsetNoteInfo)dao.loadEntity(HandsetNoteInfo.class,hsnin.getHandsetNoteInfo().getId());//��������ID	
		HandsetNoteInfoAlready hsnia = new HandsetNoteInfoAlready();
		hsnia.setId(idHsnia);
		hsnia.setHandsetNoteInfo(hni);
		hsnia.setOperTime(TimeUtil.getNowTime());
		hsnia.setReceiveNum(hsnin.getReceiveNum());
		if(!hsnin.getSchedularTime().equals("") || hsnin.getSchedularTime()!=null)
		{
			hsnia.setSchedularTime(hsnin.getSchedularTime());
		}
		hsnia.setManagement(hsnin.getManagement());
		hsnia.setDelSign("Y");
		dao.saveEntity(hsnia);//���ŷ��ͳɹ���浽���ͳɹ�������		
		dao.removeEntity(hsnin);//���ŷ��ͳɹ���ͬʱ,��δ���ͳɹ������������������
	}
	
	public BaseDAO getDao() {
		return dao;
	}

	public void setDao( BaseDAO dao) {
		this.dao = dao;
	}

	public KeyService getKs() {
		return ks;
	}

	public void setKs(KeyService ks) {
		this.ks = ks;
	}
}
