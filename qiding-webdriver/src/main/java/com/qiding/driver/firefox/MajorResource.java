package com.qiding.driver.firefox;

import lombok.*;

@Builder
@ToString
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MajorResource {
	private String schoolName;
	private String schoolType;
	private String lowerScore;
	private String position;
	private String baseScore;
	private String major;
	private String year;
	private String level;
}
