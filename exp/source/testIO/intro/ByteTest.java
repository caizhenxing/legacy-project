package testIO.intro;
public class ByteTest {

  public static void main(String[] args) {
    
    for (int i = 0; i<256; i++) {
      byte signedByte = (byte) i;
      int unsignedByte = signedByte > 0 ? signedByte : 256 + signedByte;
      System.out.println(i + "\t" + unsignedByte + "\t" + signedByte);
    }

  }
  
}