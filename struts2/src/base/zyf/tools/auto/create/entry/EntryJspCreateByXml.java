/**
 * 
 * 制作时间：Nov 1, 20072:15:48 PM
 * 文件名：EntryJspCreateByXml.java
 * 制作者：zhaoyf
 * 
 */
package base.zyf.tools.auto.create.entry;

import java.beans.IntrospectionException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.betwixt.io.BeanReader;
import org.xml.sax.SAXException;

import base.zyf.tools.auto.create.AutoCreateFile;

/**
 * @author zhaoyf
 *
 */
public class EntryJspCreateByXml extends AutoCreateFile {

	List metas=new ArrayList();
	/* (non-Javadoc)
	 * @see com.zyf.tools.auto.create.AutoCreateFile#createJsp(java.lang.String)
	 */
	public void createJsp(String name) {
		// TODO Auto-generated method stub
		findFiles(name);
		readMetaData();
		for(int i=0,size=metas.size();i<size;i++)
		{
			EntryJspMeta eim=(EntryJspMeta)metas.get(i);
			eim.createFiles(this.realPath);
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
			br.registerBeanClass(EntryJspCreateByXml.class);
			
		} catch (IntrospectionException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return;
		}
		
		//br.getBindingConfiguration().setMapIDs(false);
		
		
		try {
			EntryJspCreateByXml auto=(EntryJspCreateByXml)br.parse(new File(name));
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
			br.registerBeanClass(EntryJspMeta.class);
			
		} catch (IntrospectionException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return;
		}
		
		br.getBindingConfiguration().setMapIDs(false);
		
		
		try {
			for(int i=0,size=files.size();i<size;i++)
			{
				EntryJspMeta ejm=(EntryJspMeta)br.parse(new File(files.get(i).toString()));
				metas.add(ejm);
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
