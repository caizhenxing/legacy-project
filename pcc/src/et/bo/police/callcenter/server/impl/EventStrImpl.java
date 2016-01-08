package et.bo.police.callcenter.server.impl;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import et.bo.police.callcenter.server.EventStr;

public class EventStrImpl implements EventStr{
	/*
	 * ���յĴ�
	 */
	private String receiveStr;
	/*
	 * �Ƿ��ǺϷ�������
	 */
	private boolean validate;
	/*
	 * �����
	 */
	private String command;
	/*
	 * �������
	 */
	private String[] args;
	/*
	 * �м��ַ���
	 */
	private String handleStr;
	/*
	 * ������
	 */
	private String evtResultAfterOper;
	/*
	 * ͨѶ��Լ��������ų���
	 */
	private final String SEMICOLON=";";
	private final String COLON=":";
	private final String COMMA=",";
	/*
	 * ͨѶ��Լ��������ʽ,���ķֺ��滻���˶��š�
	 * ���磺"EXRING:0,1000,4000,"
	 */
	private final String RULER_REGEX = "^\\s*[A-Z]{6}\\s*:(\\s*\\w*\\s,)*";
	public EventStrImpl() {
	}
	public EventStrImpl(String receiveStr) {
		this.receiveStr = receiveStr;
	}
	public void isValidate(){
//		1:�����ж��Ƿ���";",����ֻ��һ���ֺ�;
		if(this.getRegexCount(this.SEMICOLON,this.receiveStr)!=1){
			this.validate=false;
			return ;
		}
//		2��replace with",";
		this.handleStr = this.receiveStr.replaceFirst(this.SEMICOLON, this.COMMA);
//		3:��֤�Ƿ��Ǻ���������������ʽ��
//		String s="EXRING: 0,1000,4000;";
//		Ȼ������μ�¼һ�����Ȼ��Ѵ���ɷֺŵĶ�������ȥ��
		this.validate=checkString(this.RULER_REGEX,this.handleStr);		
	}
	/*
	 * ȡ���ַ����а�������ص�������ʽ�ĸ�����
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
	 * ����ַ������Ƿ����������ʽ��
	 */
	public boolean checkString(String regex,String str){
		Pattern p = Pattern.compile(regex);
		Matcher m=p.matcher(str);		
		return m.find();
	}
	public void parse(){
		//���ȵõ����Ȼ��쿴�Ƿ��в�����
		String s[]=this.handleStr.split(this.COLON, 2);
		this.command = s[0];
		//Ȼ�����������������Ӧ�������С�
		if(s.length==2){
			args =s[1].split(this.COMMA);
		}
	}
	public void handle(String s){
		this.receiveStr =s;
		//��֤
		this.isValidate();
		//����
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
