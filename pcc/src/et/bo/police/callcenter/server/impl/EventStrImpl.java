package et.bo.police.callcenter.server.impl;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import et.bo.police.callcenter.server.EventStr;

public class EventStrImpl implements EventStr{
	/*
	 * 接收的串
	 */
	private String receiveStr;
	/*
	 * 是否是合法的命令
	 */
	private boolean validate;
	/*
	 * 命令动词
	 */
	private String command;
	/*
	 * 命令参数
	 */
	private String[] args;
	/*
	 * 中间字符串
	 */
	private String handleStr;
	/*
	 * 处理结果
	 */
	private String evtResultAfterOper;
	/*
	 * 通讯归约命令标点符号常数
	 */
	private final String SEMICOLON=";";
	private final String COLON=":";
	private final String COMMA=",";
	/*
	 * 通讯归约的正则表达式,最后的分号替换成了逗号。
	 * 例如："EXRING:0,1000,4000,"
	 */
	private final String RULER_REGEX = "^\\s*[A-Z]{6}\\s*:(\\s*\\w*\\s,)*";
	public EventStrImpl() {
	}
	public EventStrImpl(String receiveStr) {
		this.receiveStr = receiveStr;
	}
	public void isValidate(){
//		1:首先判断是否有";",并且只有一个分号;
		if(this.getRegexCount(this.SEMICOLON,this.receiveStr)!=1){
			this.validate=false;
			return ;
		}
//		2：replace with",";
		this.handleStr = this.receiveStr.replaceFirst(this.SEMICOLON, this.COMMA);
//		3:验证是否是合理的命令，用正则表达式。
//		String s="EXRING: 0,1000,4000;";
//		然后不论如何记录一下命令，然后把处理成分号的东东传下去。
		this.validate=checkString(this.RULER_REGEX,this.handleStr);		
	}
	/*
	 * 取出字符串中包含的相关的正则表达式的个数。
	 */
	public int getRegexCount(String regex,String s){
		Pattern p = Pattern.compile(regex);
		Matcher m=p.matcher(s);
		int i=0;
		while(m.find()){
			i++;
		}
		return i;
	}
	/*
	 * 检查字符串中是否符合正则表达式。
	 */
	public boolean checkString(String regex,String str){
		Pattern p = Pattern.compile(regex);
		Matcher m=p.matcher(str);		
		return m.find();
	}
	public void parse(){
		//首先得到命令，然后察看是否有参数。
		String s[]=this.handleStr.split(this.COLON, 2);
		this.command = s[0];
		//然后解析参数，放入相应的属性中。
		if(s.length==2){
			args =s[1].split(this.COMMA);
		}
	}
	public void handle(String s){
		this.receiveStr =s;
		//验证
		this.isValidate();
		//解析
		this.parse();
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	public String[] getArgs() {
		return args;
	}
	public void setArgs(String[] args) {
		this.args = args;
	}
	public String getCommand() {
		return command;
	}
	public void setCommand(String command) {
		this.command = command;
	}
	public String getReceiveStr() {
		return receiveStr;
	}
	public void setReceiveStr(String receiveStr) {
		this.receiveStr = receiveStr;
	}
	public void setValidate(boolean validate) {
		this.validate = validate;
	}
	public String getEvtResultAfterOper() {
		return evtResultAfterOper;
	}

}
