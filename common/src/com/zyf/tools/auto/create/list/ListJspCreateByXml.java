/**
 * 沈阳卓越科技有限公司版权所有
 * 制作时间：Oct 31, 20072:24:09 PM
 * 文件名：ListJspCreateByXml.java
 * 制作者：zhaoyf
 * 
 */
package com.zyf.tools.auto.create.list;

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
public class ListJspCreateByXml extends AutoCreateFile {

	List ljms=new ArrayList();
	/* (non-Javadoc)
	 * @see com.zyf.tools.auto.create.AutoCreateFile#createJspf(java.lang.String)
	 */
	public void createJsp(String name) {
		// TODO Auto-generated method stub
		findFiles(name);
		readMetaData();
		for(int i=0,size=ljms.size();i<size;i++)
		{
			ListJspMeta ljm=(ListJspMeta)ljms.get(i);
			ljm.createFiles(this.realPath);
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
			br.registerBeanClass(ListJspCreateByXml.class);
			
		} catch (IntrospectionException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return;
		}
		
		//br.getBindingConfiguration().setMapIDs(false);
		
		
		try {
			ListJspCreateByXml auto=(ListJspCreateByXml)br.parse(new File(name));
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
			br.registerBeanClass(ListJspMeta.class);
			
		} catch (IntrospectionException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return;
		}
		
		//br.getBindingConfiguration().setMapIDs(false);
		
		
		try {
			for(int i=0,size=files.size();i<size;i++)
			{
				ListJspMeta ljm=(ListJspMeta)br.parse(new File(files.get(i).toString()));
				ljms.add(ljm);
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
