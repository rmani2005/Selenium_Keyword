package keyword.framework;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;

@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class RunScripts {

	{
	 System.setProperty("atu.reporter.config", "atu.properties");  
	}
		
	@Test
	public void runScripts() throws IOException {

		CallWrappersUsingKeyword em=new CallWrappersUsingKeyword();
		
		try {
			FileInputStream fis = new FileInputStream(new File(System.getProperty("user.dir")+"\\Keywords\\KeywordDriver.xlsx"));
			XSSFWorkbook workbook = new XSSFWorkbook(fis);
			XSSFSheet sheet = workbook.getSheetAt(0);	
			
			// get the number of rows
			int rowCount = sheet.getLastRowNum();			
			
			// loop through the rows
			for(int i=1; i <rowCount+1; i++){
				try {
					XSSFRow row = sheet.getRow(i);
					
					System.out.println(row.getCell(1).getStringCellValue());
					System.out.println(row.getCell(3).getStringCellValue());
					if(row.getCell(3).getStringCellValue().toLowerCase().equals("yes"))
						em.getAndCallKeyword(System.getProperty("user.dir")+"\\keywords\\"+row.getCell(1).getStringCellValue()+".xlsx");

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			fis.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

				

	}

}
