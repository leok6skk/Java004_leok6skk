package _00_Util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import _00_Util.BeanDAO;
import _00_Util.BeanObject;
import _00_Util.DBUtils;

public class PlaceMain {

	public static void main(String[] args) {
		// 
		BeanDAO dao = new BeanDAO();

		PlaceBean Place = new PlaceBean();
        dao.createTables(Place);
        
		insertPlace("PlaceData.csv","BIG5");    
		PlaceBean pb = (PlaceBean)dao.findByPrimaryKey(Place, new Object[]{5});
		if (pb!= null){
			displayData(pb);
		} else {
			System.out.println("查無此筆資料");
		}
		
	}
	private static void displayData(PlaceBean pb) {
		String saveFolderImg = "D:\\images1221";
		File dirImg = new File(saveFolderImg);
		if (!dirImg.exists())  dirImg.mkdirs();
		String filenameImg = (String)pb.getFieldValue("Filename"); 
		File fileImg = new File(dirImg, filenameImg);
		
		
		String saveFolderTxt = "D:\\text1221";
		File dirTxt = new File(saveFolderTxt);
		if (!dirTxt.exists())  dirTxt.mkdirs();
		String filenameTxt = "Comment" + pb.getFieldValue("PlaceId"); 
		File fileTxt = new File(dirTxt, filenameTxt);
		
		System.out.println("Place Id :"  + pb.getFieldValue("PlaceId"));
		System.out.println("Type Id  :"  + pb.getFieldValue("TypeId"));
		System.out.println("Name     :"  + pb.getFieldValue("Name"));
		System.out.println("Phone    :"  + pb.getFieldValue("Phone"));
		System.out.println("Address  :"  + pb.getFieldValue("Address"));
		System.out.println("Longitude:"  + pb.getFieldValue("Longitude"));
		System.out.println("Latitude :"  + pb.getFieldValue("Latitude"));
		System.out.println("Link     :"  + pb.getFieldValue("Link"));
		DBUtils.saveBytesToFile((byte[])pb.getFieldValue("Picture"), fileImg);
		DBUtils.saveCharsToFile((char[])pb.getFieldValue("Comment"), fileTxt, "BIG5");
	}

	
	public static void insertPlace(String filename, String encoding){
//		PlaceDAO dao = new PlaceDAO();
		BeanDAO dao = new BeanDAO();
		try (
			FileInputStream fis = new FileInputStream(filename);
			InputStreamReader in = new InputStreamReader(fis, encoding);
			BufferedReader br = new BufferedReader(in);
		) {
			PlaceBean pb = new PlaceBean();
			String line = "";
			while ((line = br.readLine()) != null) {
			  String[] sa = line.split(",");
			  pb.setFieldValue("placeId", Integer.parseInt(sa[0].trim()));
			  pb.setFieldValue("typeId", Integer.parseInt(sa[1].trim()));
			  pb.setFieldValue("name", sa[2].trim());
			  pb.setFieldValue("phone", sa[3].trim());
			  pb.setFieldValue("address", sa[4].trim());
			  pb.setFieldValue("longitude", Double.parseDouble(sa[5].trim()));
			  pb.setFieldValue("latitude", Double.parseDouble(sa[6].trim()));
			  pb.setFieldValue("link", sa[7].trim());
			  pb.setFieldValue("filename", sa[8].trim());
			  pb.setFieldValue("picture", DBUtils.fileToBytes("images/"+sa[8].trim()));
			  pb.setFieldValue("comment", DBUtils.fileToChars("text/"+sa[9].trim(), encoding));
//			  PlaceBean pb = new PlaceBean(placeId, typeId, 
//				   name, phone, address, longitude, latitude, 
//				   link, picFilename, picture, comment);
			  dao.insert(pb);

			}
			System.out.println("檔案" + filename + "新增完畢");
		} catch (IOException ex) {
			System.out.println(ex.getMessage() + "==>" + filename);
			ex.printStackTrace();
		}
	}
	
	
	
}
