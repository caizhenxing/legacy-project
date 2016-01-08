package et.test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import junit.framework.TestCase;
import jxl.Cell;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import et.bo.pcc.phonesearch.service.SearchService;
import et.po.PhoneSearch;
import excellence.framework.base.container.SpringContainer;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

public class TestInsert extends TestCase {
	
	public void testInsertData() throws BiffException, IOException{
		SpringContainer s=SpringContainer.getInstance();
		SearchService ss=(SearchService)s.getBean("SearchService");
		//KeyService ks = (KeyService)s.getBean("KeyService");
		//BaseDAO dao = (BaseDAO)s.getBean("BaseDao");
		String path = "c:\\num.xls";// Excel�ļ�URL
		InputStream is = new FileInputStream(path);// д�뵽FileInputStream
		jxl.Workbook wb = Workbook.getWorkbook(is); // �õ�������
		jxl.Sheet st = wb.getSheet(7);// �õ��������еĵ�һ��������
		int rows = st.getRows();
		int columns = st.getColumns();
		System.out.println(rows);
		System.out.println(columns);
		long stime = System.currentTimeMillis();
//		HashMap hashmap4 = new HashMap();
		for (int i = 0; i < rows; i++) {
			//PoliceFuzzInfo pfi = new PoliceFuzzInfo();
			PhoneSearch ps = new PhoneSearch();
			for (int j = 0; j < columns; j++) {
				Cell cell = st.getCell(j, i);// �õ�������ĵ�һ����Ԫ��,��A1 0��0��
				String content = cell.getContents();// getContents()��Cell�е��ַ�תΪ�ַ���
				switch (j) {
				case 0:
					//pfi.setId(content);
					ps.setNum(content);
					break;
				case 1:
					//pfi.setFuzzNo(content);
					ps.setUnit(content);
					break;
				case 2:
					//pfi.setName(content);
					//ps.setUnit(content);
					break;
//				case 3:
//					//pfi.setSex(content);
//					break;
//				case 4:
//					//pfi.setBirthday(content);
//					break;
//				case 5:
//					//pfi.setPassword(content);
//					break;
//				case 6:
//					pfi.setTagUnit(content);
//					break;
//				case 7:
//					pfi.setDuty(content);
//					break;
//				case 8:
//					pfi.setTagArea(content);
//					break;
//				case 9:
//					pfi.setDuty(content);
//					break;
//				case 10:
//					pfi.setTagFreeze(content);
//					break;
//				case 11:
//					pfi.setTagDel(content);
//					break;
				default:
					break;
				}
				
			}
			//pfi.setMobileTel("");
			//pfi.setTagPoliceKind("");
			//pfi.setTagArea("");
			//pfi.setTagDel("0");
			//pfi.setTagFreeze("0");
			//System.out.println(ps.getNum() + " " + ps.getDeprtment() + " " + ps.getUnit());
			IBaseDTO dto = new DynaBeanDTO();
			dto.set("num", ps.getNum());
			dto.set("deprtment", ps.getDeprtment());
			dto.set("unit", ps.getUnit());
			dto.set("remark", ps.getRemark());
			ss.addSearchInfo(dto);
			//dao.saveEntity(ps);
			System.out.println("��Ӽ�¼"+i);
			//fs.addPoc(pfi);
//			System.out.println();
//			System.out.println(user.getUserId() + "  " + user.getGameNum()
//					+ " " + user.getGameWin() + "  " + user.getMaxPoint()
//					+ "  " + user.getCurrentPoint());
//			hashmap4.put(user.getUserId(), user);
		}
		System.out.println(System.currentTimeMillis()-stime);
		wb.close();// �رչ�����
		is.close();// �ر�������
	}

}
