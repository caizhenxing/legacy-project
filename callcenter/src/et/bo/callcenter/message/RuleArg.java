package et.bo.callcenter.message;

public class RuleArg {

	private String cmd;
	private String[] args;
	public RuleArg(String cmd,String[]args){
		this.cmd=cmd;
		this.args=args;
	}
	
	public String[] getArgs() {
		return args;
	}
	public void setArgs(String[] args) {
		this.args = args;
	}
	public String getCmd() {
		return cmd;
	}
	public void setCmd(String cmd) {
		this.cmd = cmd;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
