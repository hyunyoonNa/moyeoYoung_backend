package com.kosta.moyoung.youthspace.dto;
 
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class YouthSpaceDTO { 
	private Integer spaceId;
	private String title;
	private String titleImage;
	private String spaceType;
	private String place;
	private String useTime;
	private String telnum;
	private String openHours;
	private String inst;
	private String homepage;
	private String imgs;
	private String detail;
	
	// ,로 연결된 이미지url문자열을 잘라서 배열 저장 
	private String[] imgURLs; 
	
	//카드에 구까지만 출력
	private String placeLoc;
	 
	

}
