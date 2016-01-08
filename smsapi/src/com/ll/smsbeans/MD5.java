package com.ll.smsbeans;

/**
 * @author lisong
 *
 * 
 */
import java.security.MessageDigest;

/*************************************************
md5 类实现了RSA Data Security, Inc.在提交给IETF
的RFC1321中的MD5 message-digest 算法。
*************************************************/

public class MD5
{

	public static String replaceString(String origin, String src, String dest)
	{
		if (origin == null)
			return null;
		StringBuffer sb = new StringBuffer(origin.length());

		int srcLength = src.length();
		int destLength = dest.length();

		int preOffset = 0;
		int offset = 0;
		while ((offset = origin.indexOf(src, preOffset)) != -1)
		{
			sb.append(origin.substring(preOffset, offset));
			sb.append(dest);
			preOffset = offset + srcLength;
		}
		sb.append(origin.substring(preOffset, origin.length()));

		return sb.toString();
	}

	public static String md5(String str)
	{
		try
		{
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes());

			byte[] b = md.digest();
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < b.length; i++)
			{
				int v = (int) b[i];
				v = v < 0 ? 0x100 + v : v;
				String cc = Integer.toHexString(v);
				if (cc.length() == 1)
					sb.append('0');
				sb.append(cc);
			}

			return sb.toString();
		} catch (Exception e)
		{
		}
		return "";
	}

	public static byte[] md5(byte[] ba)
	{
		byte[] b = new byte[16];

		try
		{
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(ba);
			b = md.digest();

			return b;
		} catch (Exception e)
		{
		}
		return b;

	}
	public static void main(String args[])
	{

		MD5 m = new MD5();
		if (args.length == 0)
		{ //如果没有参数，执行标准的Test Suite

			System.out.println("MD5 Test suite:");
			System.out.println("MD5(\"\"):" + m.md5(""));
			System.out.println("MD5(\"a\"):" + m.md5("a"));
			System.out.println("MD5(\"abc\"):" + m.md5("abc"));
			System.out.println(
				"MD5(\"message digest\"):" + m.md5("message digest"));
			System.out.println(
				"MD5(\"abcdefghijklmnopqrstuvwxyz\"):"
					+ m.md5("abcdefghijklmnopqrstuvwxyz"));
			System.out.println(
				"MD5(\"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789\"):"
					+ m.md5(
						"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"));
		} else
			System.out.println("MD5(" + args[0] + ")=" + m.md5(args[0]));

	}

}
