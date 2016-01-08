package et.test.callcenter;

import et.bo.sys.user.action.Password_encrypt;

public class Testm {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Password_encrypt pe = new Password_encrypt();
		System.out.println("pe "+pe.pw_encrypt(null));
	}

}
