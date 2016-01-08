/**
 * 沈阳卓越科技有限公司版权所有
 * 制作时间：Nov 5, 20073:32:18 PM
 * 文件名：InfoJspCreateByXml.java
 * 制作者：zhaoyf
 * 
 */
package com.zyf.tools.auto.create.info;

import java.beans.IntrospectionException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.betwixt.io.BeanReader;
import org.xml.sax.SAXException;

import com.zyf.tools.auto.create.AutoCreateFile;


/**
 * @author zhaoyf
 *
 */
public class InfoJspCreateByXml extends AutoCreateFile {

	List infoJspDatas=new ArrayList();
	
	
	/* (non-Javadoc)
	 * @see com.zyf.tools.auto.create.AutoCreateFile#createJsp(java.lang.String)
	 */
	public void createJsp(String name)
	{
		findFiles(name);
		readMetaData();
		for(int i=0,size=infoJspDatas.size();i<size;i++)
		{
			InfoJspData ijd=(InfoJspData)infoJspDatas.get(i);
			ijd.createFiles(realPath);
//			String path=realPath+ijd.getAbsPath()+ijd.getFileName();
//			BufferedReader br=FileUtil.getFileReader(this.getClass().getResourceAsStream("info.jsp"));
//			PrintWriter pw=FileUtil.getFileWriter(path);
//			files.add(path);
//			String temp;
//			try {
//				while((temp=br.readLine())!=null)
//				pw.println(MessageFormat.format(temp,new Object[]{ijd.getInfoTag()}));
//				br.close();
//				pw.close();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//				try {
//					br.close();
//				} catch (IOException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//				pw.close();
//				FileUtil.removeFile(path);
//				break;
//			}
		}
	}

	/* (non-Javadoc)
	 * @see com.zyf.tools.auto.create.AutoCreateFile#findFiles(java.lang.String)
	 */
	protected void findFiles(String name) {
		// TODO Auto-generated method stub
		BeanReader br=new BeanReader();
		br.getXMLIntrospector().setWrapCollectionsInElement(false);
		try {
			br.registerBeanClass(this.getClass());
			
		} catch (IntrospectionException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return;
		}
		
		//br.getBindingConfiguration().setMapIDs(false);
		
		
		try {
			InfoJspCreateByXml auto=(InfoJspCreateByXml)br.parse(new File(name));
			this.files=auto.files;
			this.realPath=auto.realPath;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see com.zyf.tools.auto.create.AutoCreateFile#readMetaData()
	 */
	protected void readMetaData() {
		// TODO Auto-generated method stub
		BeanReader br=new BeanReader();
		br.getXMLIntrospector().setWrapCollectionsInElement(false);
		try {
			br.registerBeanClass(InfoJspData.class);
			
		} catch (IntrospectionException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return;
		}
		
		//br.getBindingConfiguration().setMapIDs(false);
		
		
		try {
			for(int i=0,size=files.size();i<size;i++)
			{
				InfoJspData ijd=(InfoJspData)br.parse(new File(files.get(i).toString()));
				infoJspDatas.add(ijd);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
