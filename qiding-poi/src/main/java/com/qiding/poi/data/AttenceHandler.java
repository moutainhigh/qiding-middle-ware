package com.qiding.poi.data;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.lang.reflect.AnnotatedArrayType;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class AttenceHandler {

	public void originData(Integer rowBegin,  Integer colBegin, Integer colEnd,File  filePath){
		try {
		InputStream inputStream =new FileInputStream(filePath);
		XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
		XSSFSheet sheet = workbook.getSheetAt(0);
		for (int i = rowBegin; i < sheet.getLastRowNum(); i++) {
			XSSFRow row = sheet.getRow(i);
			AttenceData data = new AttenceData();

			if(row.getCell(2)!=null&&"非执行层".equals(row.getCell(2).getStringCellValue())){
				break;
			}


			for (int j = colBegin; j < colEnd; j++) {
				CellType cellType = row.getCell(j).getCellType();
				switch (cellType) {
					case NUMERIC:
						data.getData().add(row.getCell(j).getNumericCellValue());
						break;
					default:
						data.getData().add(0.0);
						break;
				}
			}
			data.changeData();
			for (int m = 0, j = colBegin; j < colEnd; j++, m++) {
				Double cellValue = data.getData().get(m);
				row.getCell(j).setCellValue(cellValue);
//				if (date == 0d) {
//					row.getCell(j).setCellValue("-");
//				} else {
//					row.getCell(j).setCellValue(date);
//				}
			}
		}
		OutputStream outputStream = new FileOutputStream(this.getClass().getResource("/").getPath()+File.separator+"2-"+filePath.getName());
		workbook.write(outputStream);
		outputStream.flush();
		workbook.close();
		outputStream.close();
		inputStream.close();
			System.out.println("success===="+filePath.getName());
		}catch (Exception e){
          e.printStackTrace();
		}
	}




	public static void main(String[] args) throws IOException {
		AttenceHandler handler = new AttenceHandler();
	    String allPath=	handler.getClass().getResource("/").getPath();
	    File file=new File(allPath);
	    if(file.isDirectory()){
		  File[] files=	file.listFiles(new FilenameFilter() {
				@Override
				public boolean accept(File dir, String name) {
					return name.endsWith("xlsx")&&!name.startsWith("2-");
				}
			});


			Stream.of(files).forEach(path->handler.originData(4, 12, 24,path));
		}





	}


}
