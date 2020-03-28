package com.qiding.poi.data;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
public class AttenceData {
	List<Double> data=new ArrayList<>();
	void changeData(){
		if(data.get(0)<1d&&data.get(0)>0d){
			return;
		}




		for(int i=0;i<data.size()-1;i++){
		    //if(data.get(i)>1d||data.get(i)==0d){
				data.set(i+1,data.get(i)+data.get(i+1));
			//}
		}
	}
}
