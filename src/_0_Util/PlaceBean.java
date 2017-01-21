package _0_Util;

import _0_Util.BeanObject;

public class PlaceBean extends BeanObject {
//	  int placeId; 	
//	  int typeId; 		
//	  String name; 		
//	  String phone; 		
//	  String address; 	
//	  double longitude; 	
//	  double latitude; 	
//	  String link; 		
//	  String filename;
//	  byte[] picture; 	
//	  char[] comment; 	
	
	
//    public PlaceBean(int placeId, int typeId, String name, String phone, String address, double longitude,
//			double latitude, String link, String filename, byte[] picture, char[] comment) {
////		super();
//		this.placeId = placeId;
//		this.typeId = typeId;
//		this.name = name;
//		this.phone = phone;
//		this.address = address;
//		this.longitude = longitude;
//		this.latitude = latitude;
//		this.link = link;
//		this.filename = filename;
//		this.picture = picture;
//		this.comment = comment;
//	}


	public PlaceBean(){
		super("place"
			,new String[]{"placeId","typeId","name"   ,"phone"  ,"address","longitude","latitude","link"   ,"filename","picture" ,"comment"}
			,new String[]{"int"    ,"int"   ,"varchar","varchar","varchar","double"   ,"double"  ,"varchar","varchar" ,"longblob","longtext"}
			,new int[]{11       ,11      ,255      ,255      ,255      ,0          ,0         ,255      ,255       ,0         ,0}
			,new Boolean[]{true     ,false  ,false    ,false   ,false    ,false     ,false     ,false    ,false     ,false    ,false}
			,new Boolean[]{true     ,false  ,false    ,false   ,false    ,false     ,false     ,false    ,false     ,false    ,false}
			,new String[]{null      ,"null"  ,"null"   ,"null"   ,"null"   ,"null"     ,"null"    ,"null"    ,"null"    ,null       ,null}
			,"placeId"
			);
//		super("place");
//		addField("placeId", "int", 11, true, true, "", true);
//		addField("typeId" , "int", 11, false, false, "", false);
//		addField("name", "int", 11, true, true, "", true);
    }


//	public String getFilename() {
//		return filename;
//	}
//
//
//	public void setFilename(String filename) {
//		this.filename = filename;
//	}
//
//
//	public int getPlaceId() {
//		return placeId;
//	}
//
//
//	public void setPlaceId(int placeId) {
//		this.placeId = placeId;
//	}
//
//
//	public int getTypeId() {
//		return typeId;
//	}
//
//
//	public void setTypeId(int typeId) {
//		this.typeId = typeId;
//	}
//
//
//	public String getName() {
//		return name;
//	}
//
//
//	public void setName(String name) {
//		this.name = name;
//	}
//
//
//	public String getPhone() {
//		return phone;
//	}
//
//
//	public void setPhone(String phone) {
//		this.phone = phone;
//	}
//
//
//	public String getAddress() {
//		return address;
//	}
//
//
//	public void setAddress(String address) {
//		this.address = address;
//	}
//
//
//	public double getLongitude() {
//		return longitude;
//	}
//
//
//	public void setLongitude(double longitude) {
//		this.longitude = longitude;
//	}
//
//
//	public double getLatitude() {
//		return latitude;
//	}
//
//
//	public void setLatitude(double latitude) {
//		this.latitude = latitude;
//	}
//
//
//	public String getLink() {
//		return link;
//	}
//
//
//	public void setLink(String link) {
//		this.link = link;
//	}
//
//
//	public byte[] getPicture() {
//		return picture;
//	}
//
//
//	public void setPicture(byte[] picture) {
//		this.picture = picture;
//	}
//
//
//	public char[] getComment() {
//		return comment;
//	}
//
//
//	public void setComment(char[] comment) {
//		this.comment = comment;
//	}
//	
	
}
