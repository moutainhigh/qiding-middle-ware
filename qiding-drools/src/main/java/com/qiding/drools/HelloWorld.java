package com.qiding.drools;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class HelloWorld {
	private String name;
	private Integer age;
	private Boolean gone;
}
