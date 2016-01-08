/*
 * 创建日期 2004-9-10
 *
 * 
 */
package com.ll.smsbeans.test;

import java.net.InetAddress;

import com.ll.smsbeans.ConnectionAdapter;
import com.ll.smsbeans.ConnectionBean;
import com.ll.smsbeans.SyncMessageBean;
import com.ll.smsbeans.cmpp3.CmppCancelBuilder;
import com.ll.smsbeans.cmpp3.CmppQuery;
import com.ll.smsbeans.cmpp3.CmppQueryBuilder;
import com.ll.smsbeans.cmpp3.CmppQueryResp;
import com.ll.smsbeans.cmpp3.CmppSubmitBuilder;

/**
 * @author Administrator
 *
 * 
 */
public class ConnectBeanTest
{

	public static void main(String[] args)
	{
		InetAddress addr;
		ConnectionBean cb;
		cb = new ConnectionBean("919474","36957078");
		cb.addConnectionListener(new ConnectionAdapter());

		try
		{
			cb.connect(addr = InetAddress.getByName("127.0.0.1"));
		} catch (java.net.UnknownHostException e)
		{ //from InetAddress.getByName()
			java.lang.System.err.println("DNS error finding your server:");
			java.lang.System.err.println(e.toString());

			//TODO
			return;

		} catch (java.io.IOException e)
		{ //from connect
			java.lang.System.err.println(
				"IO error while attempting to connect to server:");
			java.lang.System.err.println(e.toString());

			//TODO 
			return;

		}
		try
		{

			Thread.sleep(1000L);
		} catch (Exception e)
		{
			// TODO: handle exception
		}
		
		CmppSubmitBuilder cm=new CmppSubmitBuilder();
		SyncMessageBean mb=new SyncMessageBean(cb);
		cm.setMsgId(001001);
		cm.setPkTotal(1);
		cm.setPkNumber(1);
		cm.setRegisterDelivery(0 );
		cm.setMsgLevel(1);
		cm.setServiceId("01850");
		cm.setFeeUserType(0);
		//cm.setFeeTermId("");
		cm.setLinkId("0001");
		cm.setFeeTermType(0 );
		cm.setTpPid(0 );
		cm.setTpUdhi(0);
		cm.setMsgFmt(15);
		cm.setMsgSrc("919474");
		cm.setFeeType("01");
		cm.setFeeCode("000000");
		cm.setAtTime("0000");
		cm.setValidTime("55555");
		cm.setSrcTermId("919474");
		cm.setDstTermType(0);
		cm.addDstTermId ("13803882229" );
		
		cm.setLinkId("");	
		
		mb.sendSubmit(cm,"这个是测试11111111111111111,2222222222222222222222aaaaaaaaaaa!!!!!这个是测试11111111111111111,2222222222222222222222aaaaaaaaaaa!!!!!这个是测试11111111111111111,2222222222222222222222aaaaaaaaaaa!!!!!这个是测试11111111111111111,2222222222222222222222aaaaaaaaaaa!!!!!这个是测试11111111111这个是测试11111111111111111,2222222222222222222222aaaaaaaaaa这个是测试11111111111111111,2222222222222222222222aaaaaaaaaa这个是测试11111111111111111,2222222222222222222222aaaaaaaaaa这个是测试11111111111111111,2222222222222222222222aaaaaaaaaa这个是测试11111111111111111,2222222222222222222222aaaaaaaaaa111111,2222222222222222222222aaaaaaaaaaa!!!!!这个是测试11111111111111111,2222222222222222222222aaaaaaaaaaa!!!!!这个是测试11111111111111111,2222222222222222222222aaaaaaaaaaa!!!!!这个是测试11111111111111111,2222222222222222222222aaaaaaaaaaa!!!!!这个是测试11111111111111111,2222222222222222222222aaaaaaaaaaa!!!!!这个是测试11111111111111111,2222222222222222222222aaaaaaaaaaa!!!!!这个是测试11111111111111111,2222222222222222222222aaaaaaaaaaa!!!!!这个是测试11111111111111111,2222222222222222222222aaaaaaaaaaa!!!!!" );
		
		cm.setMsgId(001001);
		mb.sendSubmit(cm,"这个是测试11111111111111111,2222222222222222222222aaaaaaaaaaa!!!!!这个是测试11111111111111111,2222222222222222222222aaaaaaaaaaa!!!!!这个是测试11111这个是测试11111111111111111,2222222222222222222222aaaaaaaaaa这个是测试11111111111111111,2222222222222222222222aaaaaaaaaa这个是测试11111111111111111,2222222222222222222222aaaaaaaaaa111111111111,2222222222222222222222aaaaaaaaaaa!!!!!这个是测试11111111111111111,2222222222222222222222aaaaaaaaaaa!!!!!这个是测试11111111111111111,2222222222222222222222aaaaaaaaaaa!!!!!这个是测试11111111111111111,2222222222222222222222aaaaaaaaaaa!!!!!这个是测试11111111111111111,2222222222222222222222aaaaaaaaaaa!!!!!这个是测试11111111111111111,2222222222222222222222aaaaaaaaaaa!!!!!这个是测试11111111111111111,2222222222222222222222aaaaaaaaaaa!!!!!这个是测试11111111111111111,2222222222222222222222aaaaaaaaaaa!!!!!这个是测试11111111111111111,2222222222222222222222aaaaaaaaaaa!!!!!这个是测试11111111111111111,2222222222222222222222aaaaaaaaaaa!!!!!" );
		
		CmppQueryBuilder cqb=new CmppQueryBuilder();
		cqb.setQueryCode("test" );
		cqb.setQueryType(CmppQuery.QUERY_TYPE_TOTAL);
		cqb.setReserve("");
		cqb.setTime("testTime");
		
		CmppQueryResp cqr;
		cqr=mb.sendQuery(cqb );
		
		
		System.out.println("CmppQueryResp=");
		System.out.println(cqr);
		
		
		CmppCancelBuilder ccb =new CmppCancelBuilder();
		ccb.setMsgId(0x0102030405060708L);
		 
		System.err.println("mb.sendCancel( ccb) =" +  mb.sendCancel( ccb));
		//cb.send( cm); 
		
		try
		{

			Thread.sleep(100000L);
		} catch (Exception e)
		{
			// TODO: handle exception
		}
		cb.disconnect();
	}
}
