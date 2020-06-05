package com.qiding.search.document;
import com.google.gson.Gson;
import lombok.Data;
import org.elasticsearch.common.geo.GeoJson;

@Data
public class DocumentParam {
	private String username;
	private Integer sex;
	private Integer age;

	public static String getInstance(){
		DocumentParam doc=new DocumentParam();
		doc.username="qiding10";
		doc.sex=2;
		doc.age=100;
		return new Gson().toJson(doc);
	}
}
