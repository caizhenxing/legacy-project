package testIO.intro;
public class CastTest {

  public static void main(String[] args) {
    for (int i = -512; i <= 512; i++) {
      byte castByte = (byte) i;
      if (fakeCast(i) != castByte) {
        System.out.println(i + "\t" + castByte + "\t" + fakeCast(i));
      }
    }
  }
  
  static int fakeCast(int intValue) {
  
    int byteValue;
    int temp = intValue % 256;
    if ( intValue < 0) {
      byteValue =  temp < -128 ? 256 + temp : temp;        
    }
    else {
     byteValue =  temp > 127 ? temp - 256 : temp;
    }

    return byteValue;
  }

}