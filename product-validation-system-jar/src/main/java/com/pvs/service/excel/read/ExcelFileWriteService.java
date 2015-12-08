package com.pvs.service.excel.read;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.bson.Document;

import com.pvs.service.excel.utils.ExcelUtil;

public class ExcelFileWriteService {
	final static Logger log = Logger.getLogger(ExcelFileWriteService.class);
	
	public void writeExcel(List<Document> listDocument, String excelFilePath) throws IOException {
//		Workbook workbook = getWorkbook(excelFilePath);
//		Sheet sheet = workbook.createSheet();
//		
//		int rowCount = 0;
//		
//		for (Document document : listDocument) {
//			Row row = sheet.createRow(++rowCount);
//			writeBook(aBook, row);
//		}
//		
//		try {
//			FileOutputStream outputStream = new FileOutputStream(excelFilePath);
//			workbook.write(outputStream);
//		}
//		catch(Exception exp) {
//			
//		}
	}
	
	public Workbook writeSingleDocument(Document doc, String excelFileName, int rowCount ) {
		log.debug("Parameters : doc :+"+doc+"  excelFileName : "+excelFileName+" : "+rowCount);
		Workbook workbook = null;
		try {
			workbook = ExcelUtil.getWorkbook(excelFileName);
			log.debug("Workbook Created : "+workbook);
			Sheet sheet = workbook.createSheet();
			log.debug("Sheet Created : "+sheet);
			Row row = sheet.createRow(rowCount);
			log.debug("Row Created : "+row);
			writeBook(doc, row);			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return workbook;
	}
	
	private void writeBook(Document document, Row row) {		
		int fieldCount = 1;
		String fieldName = document.getString("field1");
		log.debug("field Name : "+fieldName);
		while(fieldName != null) {
			Cell cell = row.createCell(fieldCount-1);
			cell.setCellValue(fieldName);
			fieldCount++;
			log.debug("fieldCount : "+fieldCount);
			fieldName = document.getString("field"+fieldCount);
		}		
	}

}
