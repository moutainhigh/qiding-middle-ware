package com.qiding.poi.syswin;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class UserAttendanceHandler {

	public void readUserAttendanceData(String fileName, Integer sheetIndex, Integer begin, Integer end) throws IOException {
		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(fileName);
		XSSFWorkbook workbook = new XSSFWorkbook(inputStream);

		List<UserAttendance> infoList = new ArrayList<>();

		XSSFSheet sheet = workbook.getSheetAt(sheetIndex);
		for (int i = begin; i < end; i++) {
			XSSFRow row = sheet.getRow(i);
			String name = row.getCell(0).getStringCellValue();
			String userNo =   Double.valueOf(row.getCell(1).getNumericCellValue()).intValue()+"";
			String userAttendNo =  row.getCell(2).getStringCellValue();
			String userId = row.getCell(3).getStringCellValue();
			infoList.add(UserAttendance.builder()
				.userId(userId)
				.userName(name)
				.userNo(userNo)
				.attendanceNo(userAttendNo)
				.timeTimeStamp(Instant.now().toEpochMilli())
				.build());
		}

		StringBuffer stringBuffer = new StringBuffer();

		infoList.stream().forEach(info -> stringBuffer.append(info.toString()).append(","));
		System.out.println(stringBuffer);
		inputStream.close();
		workbook.close();
	}


	public static void main(String[] args) throws IOException {
		UserAttendanceHandler userAttendanceHandler=new UserAttendanceHandler();
		userAttendanceHandler.readUserAttendanceData("地产秘邮打卡问题明细汇总.xlsx",2,1,11);

	}

}
