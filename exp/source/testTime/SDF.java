/*
 * 创建日期 2004-12-24
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package testTime;

/**
 * @author Administrator
 *
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Logger;

public class SDF {

	public static Logger log = Logger.getLogger(SDF.class.getName());
	public SDF() {
		super();
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		SimpleDateFormat sdf =
			new SimpleDateFormat("M/dd/yyyy hh:mm:ss a", java.util.Locale.US);
		SimpleDateFormat formatter =
			new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat formatter1 =
			new SimpleDateFormat("yyyy-MM-dd HH:mm:ss G E D F w W a E F");
		SimpleDateFormat formatter2 =
			new SimpleDateFormat("EEE", java.util.Locale.US);
		SimpleDateFormat formatter3 =
			new SimpleDateFormat("MMM", java.util.Locale.US);
		Calendar cal = Calendar.getInstance();
		try {
			java.util.Date d = sdf.parse("5/13/2003 10:31:37 AM");
			log.info(d.toString());
			String mDateTime1 = formatter.format(d);
			log.info(mDateTime1);
			String mDateTime = formatter1.format(cal.getTime());
			log.info(mDateTime);
			String s2 = formatter2.format(cal.getTime());
			log.info(s2);
			String s3 = formatter3.format(cal.getTime());
			log.info(s3);
		} catch (ParseException pe) {
			pe.getMessage();
		}

	}
}

 
