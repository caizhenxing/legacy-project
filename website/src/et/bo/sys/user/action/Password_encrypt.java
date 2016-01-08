package et.bo.sys.user.action;

public class Password_encrypt {

        public Password_encrypt() {
        }
        public static void main(String[] args) {
                Password_encrypt aa = new Password_encrypt();
                String sss=aa.pw_encrypt("guxf");
                System.out.println(sss);
        }
        public String pw_encrypt(String password){
                String s=new String();
                try{
                        //java.security.MessageDigest alga=java.security.MessageDigest.getInstance("SHA-1");
                        java.security.MessageDigest alga=java.security.MessageDigest.getInstance("MD5");
                        //alga.reset();
                        alga.update(password.getBytes());
                        byte[] digesta=alga.digest();
                        //String s=new String(digesta);
                        Password_encrypt sss=new Password_encrypt();
                        s=sss.byte2hex(digesta);
                        System.out.println("s length is:"+s.length());
                        alga.reset();
                }catch(Exception e){
                }finally{
                        return s;
                }
        }
        public String byte2hex(byte[] b) //二行制转字符串
   {
    String hs="";
    String stmp="";
    for (int n=0;n<b.length;n++)
     {
      stmp=(java.lang.Integer.toHexString(b[n] & 0XFF));
      if (stmp.length()==1) hs=hs+"0"+stmp;
      else hs=hs+stmp;
      //if (n<b.length-1)  hs=hs+":";
     }
    return hs.toUpperCase();
    }
}