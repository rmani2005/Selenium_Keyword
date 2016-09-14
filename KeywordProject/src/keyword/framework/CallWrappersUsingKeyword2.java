package keyword.framework;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Locale;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import wrapper.GenericWrapperMethods;

public class CallWrappersUsingKeyword2 {


	public void getAndCallKeyword(String fileName) throws Exception{
		FileInputStream file = new FileInputStream(new File(fileName));

		// Create Workbook instance holding reference to .xlsx file
		XSSFWorkbook workbook = new XSSFWorkbook(file);

		// syntax for reference
		// Class<ClassName> obj = ClassName.class;
		// syntax to create object to the class
		// obj.newInstance();
		Class<GenericWrapperMethods> wrapper = GenericWrapperMethods.class;
	    Object wM = wrapper.newInstance();

	    
		// Get first/desired sheet from the workbook
		XSSFSheet sh = workbook.getSheetAt(0);
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
			
			
			Method[] methodName = wrapper..getDeclaredMethods();
			
			for (Method method : methodName) {
				
				if(method.getName().toLowerCase().equals(keyword.toLowerCase())){

					if(locator.equals("") && data.equals(""))
							method.invoke(wM);
					else if(locator.equals(""))
							method.invoke(wM,data);
					else if(data.equals(""))
						method.invoke(wM,locator);
					else
						method.invoke(wM,locator,data);
					
					// go out of for
					break;

				}
				
			}
			
			
		}
		
		workbook.close();
	}
}
