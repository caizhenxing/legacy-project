package excellence.common.validator;

/**
 * @author	 		:	辜晓峰
 * @project			:
 * @class describe	:生成随机码以及干扰图形。
 * 
 */

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.imageio.ImageIO;
//import javax.imageio.*;
public class RandomPic {
	private final int width=60;//背景（图片）的宽
	private final int height=20;//背景（图片）的高
	
	private final int interfereLineNum = 88;//干扰线个数
	private final int interferePointNum = 88;//干扰线个数
	private final int interfereLineLenMax = 12;//干扰线随机长度最大值
	private final int authenticationCodeNumber = 4;//认证码的位数
	//背景色的范围
	private final int backgroundColorBegin =200;
	private final int backgroundColorFinal =250;
	
	private final int interfereColorBegin =160;
	private final int interfereColorFinal =200;
	
	private final int radomNumberColorBegin =20;
	private final int radomNumberColorFinal =130;
	//字体
	private final String fontName="Times New Roman";
	private final int fontStyle = Font.PLAIN;
	private final int fontSize = 18;
	
	private final int stringWidth =13;//string宽度坐标
	private final int stringHight =16;//string高度坐标
	private final int stringClearance =6;//string间距
	//rgb范围
	private final int rgbValue = 255;
	//
	private Graphics graphics;//内存中虚拟的画布
	
	public String authenticationCode=new String();//取随机产生的认证码(4位数字),对外的接口
	public BufferedImage image;//图片的内存,对外的接口
	
	
	public static void main(String[] args) {
		RandomPic t=new RandomPic("1234");
		//ImageIO.write();
		//t.drawBasic();
	}
	
	//认证码由服务器生成
	public RandomPic(){
		init();
	}
	//认证码客户端生成,rn为客户端生成的认证码
	public RandomPic(String rn){
		init();
		authenticationCode = rn;
	}
	
	private void init(){
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		graphics = image.getGraphics();
		
	}
	public void drawServer(){
		//画基本框架
		drawBasic();
		//画干扰线或者干扰点
		drawInterfereLine();
		//drawInterferePoint();
		//画随机数
		drawRadomNumberServer();
		//图象生效
		graphics.dispose();
	}
	public void drawClient(){
		//画基本框架
		drawBasic();
		//画干扰线或者干扰点
		drawInterfereLine();
		//drawInterferePoint();
		//画随机数
		drawRadomNumberClient();
		//图象生效
		graphics.dispose();
	}
	//	给定范围获得随机颜色bc:begin color ,fc:finally color
	private Color getRandColor(int fc,int bc){
		Random random = new Random();
		if(fc>rgbValue) fc=rgbValue;
		if(bc>rgbValue) bc=rgbValue;
		int r=fc+random.nextInt(bc-fc);
		int g=fc+random.nextInt(bc-fc);
		int b=fc+random.nextInt(bc-fc);
		return new Color(r,g,b);
	}
	
	//设定图片基本信息
	private void drawBasic(){
		//设定背景色
		graphics.setColor(getRandColor(backgroundColorBegin,backgroundColorFinal));
		graphics.fillRect(0, 0, width, height);
		//设定字体
		graphics.setFont(new Font(fontName,fontStyle,fontSize));
		//画边框
		graphics.setColor(new Color(rgbValue,rgbValue,rgbValue));
		graphics.drawRect(0,0,width-1,height-1);
	}
	//随机产生interfereLine条干扰线，使图象中的认证码不易被其它程序探测到
	private void drawInterfereLine(){
		Random random = new Random();
		graphics.setColor(getRandColor(interfereColorBegin,interfereColorFinal));
		for (int i=0;i<interfereLineNum;i++){
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int xl = random.nextInt(interfereLineLenMax);
			int yl = random.nextInt(interfereLineLenMax);
			graphics.drawLine(x,y,x+xl,y+yl);
		}
	}
	//	随机产生interfereLine条干扰点，使图象中的认证码不易被其它程序探测到
	private void drawInterferePoint(){
		Random random = new Random();
		graphics.setColor(getRandColor(interfereColorBegin,interfereColorFinal));
		for (int i=0;i<interferePointNum;i++){
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			graphics.drawLine(x,y,x,y);
		}
	}
	// 取随机产生的认证码(4位数字),由服务器端生成
	private void drawRadomNumberServer(){
		Random random = new Random();
		for (int i=0;i<authenticationCodeNumber;i++){
			String rand=String.valueOf(random.nextInt(10));
			authenticationCode+=rand;
			graphics.setColor(getRandColor(radomNumberColorBegin,radomNumberColorFinal));
			//调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
			graphics.drawString(rand,stringWidth*i+stringClearance,stringHight);//后两个参数为坐标
		}
		
	}
	//取随机产生的认证码(4位数字),由客户端生成
	//<script>
	//document.write("<img border=0 src=image.jsp?rand="+Math.random()*10000+">");
	//</script>
	 private void drawRadomNumberClient(){
		authenticationCode = authenticationCode.substring(0,authenticationCode.indexOf("."));
		switch(authenticationCode.length()){
		   case 1: authenticationCode = "000"+authenticationCode; break;
		   case 2: authenticationCode = "00"+authenticationCode; break;
		   case 3: authenticationCode = "0"+authenticationCode; break;
		   default: authenticationCode = authenticationCode.substring(0,4); break;
		}
		graphics.setColor(getRandColor(radomNumberColorBegin,radomNumberColorFinal));
		graphics.drawString(authenticationCode,stringWidth,stringHight);
		
	 }
}

