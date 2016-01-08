<%@ page contentType="image/jpeg" import="java.awt.*,java.awt.image.*,java.util.*,javax.imageio.*,excellence.common.validator.RandomPic,java.io.*" %><%
response.setHeader("Pragma","No-cache");
response.setHeader("Cache-Control","no-cache");
response.setDateHeader("Expires", 0);
double rand=Math.random()*10000;
		RandomPic  rp=new RandomPic(Double.toString(rand));		
		rp.drawClient();
session.setAttribute("rand",rp.authenticationCode);
BufferedImage image=rp.image;
try{
	ImageIO.write(image, "JPEG", response.getOutputStream());
} catch (IOException e) {
			// TODO Auto-generated catch block			
			e.printStackTrace();			
		}
%>