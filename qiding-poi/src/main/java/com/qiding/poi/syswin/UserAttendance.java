package com.qiding.poi.syswin;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Builder
@Data
public class UserAttendance {
	private String userId;
	private String attendanceNo;
	private String userNo;
	private Long timeTimeStamp= Instant.now().toEpochMilli();
	private String userName;

	@Override
	public String toString() {
	     String str="('%s','%s','%s',1,%d,%d,'%s','%s','')";
	     return String.format(str,userId,attendanceNo,userNo,timeTimeStamp,timeTimeStamp,userName,userName);
	}
}
