<%@ page contentType="image/jpeg" import="java.awt.*,java.awt.image.*,java.util.*,javax.imageio.*" %>

<%@ page import="ocelet.common.validator.RandomPic" %>
<%@ page import="java.io.*" %>
<%
//����ҳ�治����
response.setHeader("Pragma","No-cache");
response.setHeader("Cache-Control","no-cache");
response.setDateHeader("Expires", 0);

double rand=Math.random()*10000;
		RandomPic  rp=new RandomPic(Double.toString(rand));
		
		rp.drawClient();
session.setAttribute("rand",rp.authenticationCode);
BufferedImage image=rp.image;
// ���ͼ��ҳ��
try
{
ImageIO.write(image, "JPEG", response.getOutputStream());

} catch (IOException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
			
		}
%>