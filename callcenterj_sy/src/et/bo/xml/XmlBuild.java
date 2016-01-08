/**
 * 
 */
package et.bo.xml;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.xmlbeans.XmlException;

import et.xmlbean.ChartDocument;
import et.xmlbean.ChartDocument.Chart;
import et.xmlbean.ChartDocument.Chart.Set;

/**
 * @author zhangfeng
 *
 */
public class XmlBuild {
	
	public void createCustomer(String fileName,HashMap map) {
		try {
			String url = this.getClass().getResource("/").getPath();
			System.out.println(url);
			int temI = url.indexOf("WEB-INF");
			url = url.substring(1, temI);
			url = url + "dataxml/"+fileName+".xml";
			
			System.out.println(url);
			//String url ="d:/MM.xml";
			// Create Document
			ChartDocument doc = ChartDocument.Factory.newInstance();
			//doc.getChart().setXAxisName("月份统计");
			Iterator it = map.keySet().iterator();
			while(it.hasNext()){
				String key = it.next().toString();
				String value = map.get(key).toString();
				// Add new customer
				Set st = doc.addNewChart().addNewSet();
				// set info
				st.setLabel(key);
				st.setValue((short)Integer.parseInt(value));
			}
			File xmlFile = new File(url);
			doc.save(xmlFile);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * @param fileName xml文件名（不带.xml扩展名）
	 * @param map 数据map
	 * @param caption 图表标题
	 * @param xAxis X轴
	 * @param yAxis Y轴
	 * @param num 节点总数
	 */
	public void modifyXML(String fileName,List list,String caption,String xAxis,String yAxis,int num) {
		String url = this.getClass().getResource("/").getPath();
//		System.out.println(url);
		int temI = url.indexOf("WEB-INF");
		url = url.substring(1, temI);
		url = url + "dataxml/"+fileName+".xml";
		File xmlFile = new File(url);
//		System.out.println("URL: "+url);
		try {
			ChartDocument doc = ChartDocument.Factory.parse(xmlFile);
			doc.getChart().setCaption(caption);
			doc.getChart().setXAxisName(xAxis);
			doc.getChart().setYAxisName(yAxis);
			Set[] st = doc.getChart().getSetArray();
			int i = 0;
			for (i = 0; i < list.size(); i++) {
				ArrayList al = (ArrayList)list.get(i);
				String key = al.get(0).toString();
				String value = al.get(1).toString();
				if(st.length<list.size()){
					Set nst = doc.getChart().addNewSet();
					nst.setLabel(key);
					nst.setValue((short)Integer.parseInt(value));
					continue;
				}
				st[i].setLabel(key);
				st[i].setValue((short)Integer.parseInt(value));
			}
			doc.save(xmlFile);
		} catch (XmlException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void deleteXml(String fileName){
//		String url = this.getClass().getResource("/").getPath();
//		int temI = url.indexOf("callcenterj_sy");
//		System.out.println("url:"+url);
//		url = url.substring(1, temI);
//		url = url + "WebRoot/dataxml/"+fileName+".xml";
		String url ="d:/MM.xml";
		File xmlFile = new File(url);
		xmlFile.delete();
	}
	
	/**
	 * 
	 * @param fileName xml文件名（不带.xml扩展名）
	 * @param map 数据map
	 * @param caption 图表标题
	 * @param xAxis X轴
	 * @param yAxis Y轴
	 * @param num 节点总数
	 */
	/*public void modifyXMLNotNum(String fileName,List list,String caption,String xAxis,String yAxis) {
		String url = this.getClass().getResource("/").getPath();
		int temI = url.indexOf("WEB-INF");
		url = url.substring(1, temI);
		url = url + "dataxml/"+fileName+".xml";
		File xmlFile = new File(url);
		try {
			ChartDocument doc = ChartDocument.Factory.parse(xmlFile);
			doc.getChart().setCaption(caption);
			doc.getChart().setXAxisName(xAxis);
			doc.getChart().setYAxisName(yAxis);
			Set[] st = doc.getChart().getSetArray();			
			for (int i = 0; i < list.size(); i++) {
				ArrayList al = (ArrayList)list.get(i);
				String key = al.get(0).toString();
				String value = al.get(1).toString();
				System.out.println("key:"+key);
				System.out.println("value:"+value);
				if(st.length<list.size()){
					Set nst = doc.getChart().addNewSet();
					nst.setLabel(key);
					nst.setValue((short)Integer.parseInt(value));
					continue;
				}
				st[i].setLabel(key);
				st[i].setValue((short)Integer.parseInt(value));
			}
			doc.save(xmlFile);
		} catch (XmlException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/

}
