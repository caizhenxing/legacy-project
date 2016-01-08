/*
 * Created on 2004-8-6
 *
 */
package testXml;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import com.lc.ebs.Interface.beans.Column;
import com.lc.ebs.Interface.beans.Condition;
import com.lc.ebs.Interface.beans.Task;
import com.lc.ebs.Interface.beans.TaskList;
import com.lc.ebs.Interface.beans.Transform;
import com.loushang.util.log.LogUtils;
/**
 * @author whz
 *
 * 解析定义接口数据同步任务定义文件/WEB-INF/conf/tasks.xml
 * 将结果放入一个TaskList类中，用于形成同步更新任务
 * 
 */
public class TaskParser {
	
	public static final LogUtils Log = new LogUtils(TaskParser.class);
	
	private static String url = null;
	
	private  TaskList taskList = null;
	
	private static TaskParser taskParser= null;
	
	/**
	 * 初始化Taskpaser
	 * @param url
	 */
	private TaskParser(String url1){
		url=url1;
		taskList = new TaskList();
		Log.debug("Task.xml path : "+url);
		parseTasks();
		
	}
	/**
	 * 取得实例
	 * @param url
	 * @return TaskParser
	 */
	public static synchronized TaskParser getInstance(String url){
		if(TaskParser.taskParser==null){
			TaskParser.taskParser= new TaskParser(url);
		}
		return taskParser;
	}
	
	/**
	 * 刷新配置
	 * @param url
	 */
	public static TaskList refresh(){
		TaskParser.taskParser= new TaskParser(url);
		return taskParser.getTaskList();
	}
	
	/**
	 * 取得任务列表
	 * @return TaskList
	 */
	public TaskList getTaskList(){
		return taskList;
	}
	/**
	 * 解析任务
	 * @return TaskList
	 */
	private TaskList parseTasks()
	{

		Element taskDef = loadDocument(url);

		getTasks(taskDef);

		return taskList;
	}
	/**
	 * 得到任务并放入任务列表
	 * @param taskDef
	 */
	private void getTasks(Element taskDef)
	{
		
		NodeList tasks = taskDef.getChildNodes();
		int index = 0;
		for(int i=0;i<tasks.getLength();i++){
			
			if(tasks.item(i)==null||!tasks.item(i).getNodeName().equals("task"))continue;
			
			Task task=parseTask(tasks.item(i));
			
			if(task!=null){
				index++;
				String id = (index<10)?"0"+index:""+index;
				task.setTaskID("task"+id);
				taskList.add(task);
			}
		}
	}
	/**
	 * 解析任务,并返回结果
	 * @param taskNode
	 * @return Task
	 */
	private Task parseTask(Node taskNode)
	{
		Task task = new Task();
		
		//取得属性 from dest updateRate
		NamedNodeMap attrs = taskNode.getAttributes();
		
		if(attrs==null)return null;
		
		for(int i=0;i<attrs.getLength();i++){

			Node attr = attrs.item(i);
			if(attr==null||attr.getNodeName()==null)continue;
			
			String name = attr.getNodeName();
			String value = attr.getNodeValue();
			
			if(name.equals("from")){
				try{
					task.setFromTableName(value.trim());
				}catch(Exception e){
					Log.error("解析任务时出错",e);
					return null;
				}
			}else if(name.equals("dest")){
				task.setDestTableName(value.trim());
			}else if(name.equals("updateRate")){
				double ms = Double.parseDouble(value.trim())*60000;
				String sms = String.valueOf(ms);
				try{
					long lms = Long.parseLong(sms.substring(0,sms.indexOf(".")));
					task.setUpdateRate(lms);
				}catch(NumberFormatException e){
					return null;
				}
			}else if(name.equals("flag")){
				String str = value.trim();
				String tab = str.substring(0,str.lastIndexOf("."));
				String col = str.substring(str.lastIndexOf(".")+1,str.length());
				task.setFlagCol(col);
				task.setFlagTab(tab);
			}

		}
		
		NodeList col_cond = taskNode.getChildNodes();
		
		for(int i=0;i<col_cond.getLength();i++){

			Node node = col_cond.item(i);
			
			//取得列
			if(node.getNodeName().equals("columns")){
				parseColumns(node,task);
			}
			
			//取得条件
			else if(node.getNodeName().equals("conditions")){
				parseConditions(node,task);
			}
			
			//取得转换
			else if(node.getNodeName().equals("transforms")){
				parseTransforms(node,task);
			}
		}
		
		return task;
		
	}
	/**
	 * 解析columns,并将结果放入task
	 * @param columns
	 * @param task
	 */
	private void parseColumns(Node columns,Task task){
		NodeList column = columns.getChildNodes();
		for(int i=0;i<column.getLength();i++){
			
			Node col = column.item(i);
			if(col==null||!col.getNodeName().equals("column"))continue;
			
			Column newCol = new Column();

			NamedNodeMap attrs = col.getAttributes();
			
			if(attrs==null){
				continue;
			}
			
			StringBuffer log = new StringBuffer();
			log.append("column attributes : ");
				
			for(int j=0;j<attrs.getLength();j++){
				Node attr = attrs.item(j);
				
				if(attr==null||attr.getNodeName()==null)continue;
				
				String name = attr.getNodeName();
				String value = attr.getNodeValue();
				
				log.append("["+name+":"+value+"]");
				
				if(name.equals("name")){
					newCol.setName(value.trim());
				}else if(name.equals("from")){
					newCol.setFrom(value.trim());
				}else if(name.equals("dest")){
					newCol.setDest(value.trim());
				}else if(name.equals("fromType")){
					newCol.setFromType(value.trim());
				}else if(name.equals("destType")){
					newCol.setDestType(value.trim());
				}else if(name.equals("isPrimaryKey")){
					if(value.trim().equals("true"))
					{
						newCol.setPrimaryKey(true);
					}
					else
						newCol.setPrimaryKey(false);
				}
			}
			//Log.debug(log);
			if(newCol.isPrimaryKey())task.addKey(newCol.getName());
		
			task.addColumn(newCol);
		}
	}
	/**
	 * 解析conditions,并将结果放入task
	 * @param conditions
	 * @param task
	 */
	private void parseConditions(Node conditions,Task task){
		
		boolean sublogic = true;
		
		ArrayList conds = parseCondition(conditions , sublogic);
		
		task.addCondition(conds);
	}
	private ArrayList parseCondition(Node conditions,boolean sublogic){
		NodeList condition = conditions.getChildNodes();
		ArrayList condList = new ArrayList();
		
		for(int i=0;i<condition.getLength();i++){
			
			Node cond = condition.item(i);
			
			if(cond==null||!cond.getNodeName().equals("condition"))continue;
			
			Condition newCond = new Condition(sublogic);
			
			NodeList subs = cond.getChildNodes();
			if(subs!=null&&subs.getLength()>0){
				//取得子条件
				newCond.addSub(parseCondition(cond,!sublogic));
				condList.add(newCond);
				//不再取属性
				continue;
			}
			
			NamedNodeMap attrs = cond.getAttributes();
			
			if(attrs==null){
				continue;
			}
			
			StringBuffer log = new StringBuffer();
			log.append("condition attributes : ");
			for(int j=0;j<attrs.getLength();j++){
				Node attr = attrs.item(j);
				
				if(attr==null||attr.getNodeName()==null)continue;
				
				String name = attr.getNodeName();
				String value = attr.getNodeValue();
				
				log.append("["+name+":"+value+"]");
				
				if(name.equals("col")){
					newCond.setColumn(value.trim());
				}else if(name.equals("type")){
					newCond.setType(value.trim());
				}else if(name.equals("value")){
					newCond.setValue(value.trim());
				}else if(name.equals("operator")){
					newCond.setOperator(value.trim());
				}
			}
			//Log.debug(log);
			condList.add(newCond);
		}
		return condList;
	}
	/**
	 * 解析transforms,并将结果放入task
	 * @param transforms
	 * @param task
	 */
	private void parseTransforms(Node transforms,Task task){
		NodeList transform = transforms.getChildNodes();
		
		StringBuffer log = new StringBuffer();
		//log.append("transforms : ");
		
		Transform newTran = new Transform();
		
		for(int i=0;i<transform.getLength();i++){
			
			Node cond = transform.item(i);
			
			if(cond==null||!cond.getNodeName().equals("transform"))continue;
			
			NamedNodeMap attrs = cond.getAttributes();
			
			if(attrs==null){
				continue;
			}
			
			String col = null;
			String rul = null;
			String typ = null;
			for(int j=0;j<attrs.getLength();j++){
				Node attr = attrs.item(j);
				
				if(attr==null||attr.getNodeName()==null)continue;
				
				String name = attr.getNodeName();
				String value = attr.getNodeValue();
				if(name.equals("column")){
					col=value;
					log.append("["+value+"--");
				}else if(name.equals("rule")){
					rul = value;
					log.append(value+"]");
				}else if(name.equals("type")){
					typ=value;
				}
				
			}
			newTran.add(col,rul,typ);
		}
		//Log.debug(log);
		task.setTransforms(newTran);
	}
	/**
	 * 加载tasks.xml文件
	 * @param url 文件存放地址
	 * @return Element
	 */
    public static Element loadDocument(String url)
    {
        
        try
        {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            //factory.setIgnoringComments(true);
            //factory.setIgnoringElementContentWhitespace(true);
            
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(url);
            Element element = document.getDocumentElement();
            element.normalize();
            return element;
        }
        catch(SAXParseException saxparseexception)
        {
            Log.debug("TaskPaser** 读取配置文件错误, 行 " + saxparseexception.getLineNumber() + ", uri " + saxparseexception.getSystemId());
            Log.debug("TaskPasererror: " + saxparseexception.getMessage());
        }
        catch(SAXException saxexception)
        {
            Log.debug("TaskPasererror: " + saxexception);
        }
        catch(MalformedURLException malformedurlexception)
        {
            Log.debug("TaskPasererror: " + malformedurlexception);
        }
        catch(IOException ioexception)
        {
            Log.debug("TaskPasererror: " + ioexception);
        }
        catch(Exception exception)
        {
            Log.debug("TaskPasererror: " + exception);
        }
        return null;
    }
}
