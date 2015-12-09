package com.pvs.service.excel;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.bson.Document;

import com.pvs.service.excel.utils.ExcelUtil;
import com.pvs.service.utils.CommonUtils;
import com.pvs.service.write.ProductValidationSystemWriteService;

public class ExcelFileHandler {
	final static Logger log = Logger.getLogger(ExcelFileHandler.class);
	
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
	
	public int readExcelAndInsertRecord(InputStream excelInputStream, String productTemplateId, String productType, String companyId) throws IOException {
		XSSFWorkbook workbook = null;
		int rowIndex = 0;
		try {
		     
		     
		    //Get the workbook instance for XLS file 
			workbook = new XSSFWorkbook(excelInputStream);
		 
		    //Get first sheet from the workbook
			XSSFSheet sheet = workbook.getSheetAt(0);
		     
		    //Iterate through each rows from first sheet
		    Iterator<Row> rowIterator = sheet.iterator();
		    List<Document> productDocumentList = new ArrayList<Document>();
		    Map<Integer, String> columnNames = new HashMap<Integer, String>();
		    while(rowIterator.hasNext()) {
		        Row row = rowIterator.next();
		         
		        //For each row, iterate through each columns
		        Iterator<Cell> cellIterator = row.cellIterator();
		        int columnIndex = 0;
		        Document productDocument = new Document();
		        while(cellIterator.hasNext()) {
		        	  if(rowIndex == 0) {
		        		  Cell cell = cellIterator.next();
		        		  columnNames.put(columnIndex, cell.getStringCellValue());
		        		  columnIndex++;
				        }
		        	  else {
		        		  Cell cell = cellIterator.next();
		        		  switch (cell.getCellType()) {
		        		    case Cell.CELL_TYPE_STRING:
		        		    	productDocument.append(columnNames.get(columnIndex), cell.getStringCellValue());
		        		    	break;
		        		    	
		        		    case Cell.CELL_TYPE_NUMERIC:
		        		    	boolean isDate = DateUtil.isCellDateFormatted(cell);
		        		    	if(isDate) {
		        		    		Date date = cell.getDateCellValue();
		        		    		String dateString = new SimpleDateFormat("dd-MMM-yyyy").format(date);
		        		    		productDocument.append(columnNames.get(columnIndex), dateString);
		        		    	}
		        		    	else {
		        		    		productDocument.append(columnNames.get(columnIndex), cell.getNumericCellValue());
		        		    	}
		        		    	
		        		    	break;
		        		 
		        		    case Cell.CELL_TYPE_BOOLEAN:
		        		    	productDocument.append(columnNames.get(columnIndex), cell.getBooleanCellValue());
		        		    	break;
		        		    }
		        		  columnIndex++;
		        	  }
		        }
		        productDocument.append("productTemplateId", productTemplateId);
		        productDocument.append("companyId", companyId);
		        new CommonUtils().addHistoryFields(productDocument);
		        if(rowIndex != 0) {
		        	productDocumentList.add(productDocument);
		        }		        
		        rowIndex++;
		   }
		    new ProductValidationSystemWriteService().registerProduct(productDocumentList, productType, companyId, productTemplateId, rowIndex-2);
		     
		} catch (FileNotFoundException e) {
		    e.printStackTrace();
		} catch (IOException e) {
		    e.printStackTrace();
		}	
		finally {
			if(workbook != null) {
				workbook.close();
			}
			if(excelInputStream != null) {
				excelInputStream.close();
			}			
		}
		return rowIndex;
	}

}
