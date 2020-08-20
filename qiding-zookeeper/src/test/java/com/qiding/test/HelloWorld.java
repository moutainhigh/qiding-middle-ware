package com.qiding.test;

import java.util.HashMap;
import java.util.Map;

public class HelloWorld {
	public static void main(String[] args) {
		int []numbers ={3,0,2,2,1};

		int length=numbers.length;

		Map<Integer,Integer> left=new HashMap<>(length);

		Map<Integer,Integer> right=new HashMap<>(length);

		for(int i=0;i<length;i++){
			left.put(i,left.getOrDefault(i-1,0)+numbers[i]);
			right.put(length-i-1,right.getOrDefault(length-i,0)+numbers[length-i-1]);
		}

		for(int i=0;i<length-2;i++){
			int lef=left.get(i);
			int ri=right.get(i+2);
			if(lef==ri){
				System.out.println(numbers[i]);
			}
		}
	}
}
