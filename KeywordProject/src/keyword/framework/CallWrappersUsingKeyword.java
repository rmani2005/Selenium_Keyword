package keyword.framework;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import wrapper.GenericWrapperMethods;

public class CallWrappersUsingKeyword {


	public void getAndCallKeyword(String fileName) throws Exception{
		FileInputStream file = new FileInputStream(new File(fileName));

		// Create Workbook instance holding reference to .xlsx file
		XSSFWorkbook workbook = new XSSFWorkbook(file);

		// Get first/desired sheet from the workbook
		XSSFSheet sh = workbook.getSheetAt(0);
		GenericWrapperMethods cw=new GenericWrapperMethods();
		for (int i = 1; i <= sh.getLastRowNum(); i++) {

			String keyword = "" ;
			String locator = "" ;
			String data = "" ;
			try {
				keyword = sh.getRow(i).getCell(0).getStringCellValue();
				locator = sh.getRow(i).getCell(1).getStringCellValue();
				data = sh.getRow(i).getCell(2).getStringCellValue();
			} catch (NullPointerException e) {
				// ignore
			}
			
			switch(keyword){
				case "launchApp" 		: cw.launchApp(data);
										  break;
				case "enterByID" 		: cw.enterById(locator, data);
									      break;					
				case "enterByName" 		: cw.enterByName(locator, data);
										  break;
				case "clickByClassName" : cw.clickByClassName(locator);
										  break;
				case "clickByName"      : cw.clickByName(locator);
				                          break;
				case "clickLink" 	    : cw.clickLink(locator);
				                          break;
				case "verifyTitle" 		: cw.verifyTitle(data);
										  break;
				case "close" 			: cw.quitBrowser();
				  						  break;
				  						  
				default:				  throw new Exception("There is no such method available; Look at your wrapper methods and mapping.");
			}
			
		}
		
		workbook.close();
	}
}
