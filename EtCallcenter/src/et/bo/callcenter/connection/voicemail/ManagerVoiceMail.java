/**
 * 	@(#)ManagerVoiceMail.java   2007-3-2 ÉÏÎç10:39:00
 *	 ¡£ 
 *	 
 */
package et.bo.callcenter.connection.voicemail;

import java.beans.IntrospectionException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.betwixt.io.BeanReader;
import org.apache.commons.betwixt.io.BeanWriter;
import org.xml.sax.SAXException;

import excellence.common.util.file.FileBean;
import excellence.common.util.file.FileUtil;
import excellence.common.xmlparse.PoemBeana;

 /**
 * @author zhaoyifei
 * @version 2007-3-2
 * @see
 */
public class ManagerVoiceMail implements ManagerVoiceMailI {
	private String path="d:\\voicemail\\";
	private String clewVoice="";
	private Map<String,List<String>> lineVoice=new HashMap<String,List<String>>();
	private Map<String,Integer> lineBool=new HashMap<String,Integer>();
	public ManagerVoiceMail()
	{
		this.read();
	}
	/* (non-Javadoc)
	 * @see et.bo.callcenter.connection.voicemail.ManagerVoiceMailI#destroy()
	 */
	public void destroy()
	{
		this.write();
	}
	/* (non-Javadoc)
	 * @see et.bo.callcenter.connection.voicemail.ManagerVoiceMailI#getVoiceList(java.lang.String)
	 */
	public List<String> getVoiceList(String line)
	{
		lineBool.put(line,0);
		if(lineVoice.containsKey(line))
			return lineVoice.get(line);
		else
			return new ArrayList<String>();
	}
	
	/* (non-Javadoc)
	 * @see et.bo.callcenter.connection.voicemail.ManagerVoiceMailI#addVoice(java.lang.String, java.lang.String)
	 */
	public void addVoice(String line,String voice)
	{
		//System.out.println("ÓÐÐÂÂ¼Òô");
		if(lineVoice.containsKey(line))
			lineVoice.get(line).add(voice);
		else
		{
			List temp=new ArrayList<String>();
			temp.add(voice);
			lineVoice.put(line,temp);
		}
		Integer li=lineBool.get(line);
		
		lineBool.put(line,li.intValue()+1);
		this.write();
	}
	/* (non-Javadoc)
	 * @see et.bo.callcenter.connection.voicemail.ManagerVoiceMailI#delVoice(java.lang.String, java.lang.String)
	 */
	public void delVoice(String line,String voice)
	{
		if(lineVoice.containsKey(line))
			lineVoice.get(line).remove(voice);
		
	}
	private void read()
	{
		FileUtil fu=new FileUtil();
		List<FileBean> fbs=fu.getFolders(path);
		for(FileBean fb:fbs)
		{
			if(fb.isFolder())
				continue;
			BeanReader reader = new BeanReader();
	        try {
				reader.registerBeanClass(VoiceMail.class);
				VoiceMail bean = (VoiceMail) reader.parse( fb.getFile());
			    lineVoice.put(fb.getName(), bean.getVoices());
			    lineBool.put(fb.getName(), Integer.parseInt(bean.getHasNew()));
			} catch (IntrospectionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	private void write()
	{
		Iterator<String> i=lineVoice.keySet().iterator();
		while(i.hasNext())
		{
			String s=i.next();
			List l=lineVoice.get(s);
			VoiceMail vm=new VoiceMail();
			vm.setVoices(l);
			vm.setHasNew(lineBool.get(s).toString());
			try {
				Writer ow=new FileWriter(path+s);
				BeanWriter bw=new BeanWriter(ow);
				bw.setEndOfLine("\r\n");
				bw.setIndent("\t");
				bw.enablePrettyPrint();
				bw.setWriteEmptyElements(true);
				bw.write(vm);
				ow.close();
			} catch (IntrospectionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public static void main(String[] args) {
		ManagerVoiceMailI mvm=new ManagerVoiceMail();
		mvm.delVoice("1","one");
		mvm.delVoice("1","a");
		mvm.delVoice("2","b");
		mvm.delVoice("2","c");
		mvm.delVoice("3","d");
		mvm.destroy();
	}
	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}
	/**
	 * @param path the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}
	/**
	 * @return the clewVoice
	 */
	public String getClewVoice() {
		return clewVoice;
	}
	/**
	 * @param clewVoice the clewVoice to set
	 */
	public void setClewVoice(String clewVoice) {
		this.clewVoice = clewVoice;
	}
	public boolean hasNewVoice(String line) {
		// TODO Auto-generated method stub
		Integer i=lineBool.get(line);
		if(i==null)
			return false;
		return i>0;
	}
	public int getNewVoice(String line) {
		// TODO Auto-generated method stub
		return lineBool.get(line);
	}
	public void subVoiceSize(String line) {
		// TODO Auto-generated method stub
		lineBool.put(line,lineBool.get(line)-1);
	}
}
