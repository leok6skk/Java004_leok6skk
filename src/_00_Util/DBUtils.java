package _00_Util;

import java.io.BufferedReader;
import java.io.CharArrayWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import _00_Util.PlaceBean;

public class DBUtils {
	public static char[] fileToChars(String filename, String encoding) {
		try (FileInputStream fis = new FileInputStream(filename);
				InputStreamReader in = new InputStreamReader(fis, encoding);
				CharArrayWriter caw = new CharArrayWriter();) {
			int len = 0;
			char[] ca = new char[8192];
			while ((len = in.read(ca)) != -1) {
				caw.write(ca, 0, len);
			}
			return caw.toCharArray();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public static byte[] fileToBytes(String filename) {
		File f = new File(filename);
		int len = (int) f.length();
		byte[] b = new byte[len];
		try (FileInputStream fis = new FileInputStream(f);) {
			fis.read(b);
		} catch (Exception ex) {
			ex.getMessage();
		}
		return b;
	}
	public static void saveCharsToFile(char[] comment, File file, String encoding) {
		try (
		   FileOutputStream fos = new FileOutputStream(file);
		   OutputStreamWriter osw = new OutputStreamWriter(fos, encoding);
		   PrintWriter pw = new PrintWriter(osw) ;		
		) {
			pw.println(new String(comment));
		} catch(IOException ex){
			ex.printStackTrace();
		}
	}
	public static void saveBytesToFile(byte[] picture, File file) {
		try (
		   FileOutputStream fos = new FileOutputStream(file);
		) {
			fos.write(picture);
		} catch(IOException ex){
			ex.printStackTrace();
		}
		
	}
//	public static void initPlace(String filename, String encoding){
//		PlaceDAO dao = new PlaceDAO();
//		try (
//			FileInputStream fis = new FileInputStream(filename);
//			InputStreamReader in = new InputStreamReader(fis, encoding);
//			BufferedReader br = new BufferedReader(in);
//		) {
//			
//			String line = "";
//			while ((line = br.readLine()) != null) {
//			  String[] sa = line.split(",");
//			  int placeId = Integer.parseInt(sa[0].trim());
//			  int typeId  = Integer.parseInt(sa[1].trim());
//			  String name = sa[2].trim();
//			  String phone = sa[3].trim();
//			  String address = sa[4].trim();
//			  double longitude = Double.parseDouble(sa[5].trim());
//			  double latitude = Double.parseDouble(sa[6].trim());
//			  String link = sa[7].trim();
//			  String picFilename  = sa[8].trim();
//			  byte[] picture = DBUtils.fileToBytes("images\\" + sa[8].trim());
//			  char[] comment = DBUtils.fileToChars("text\\" + sa[9].trim(), encoding);
//			  PlaceBean pb = new PlaceBean(placeId, typeId, 
//				   name, phone, address, longitude, latitude, 
//				   link, picFilename, picture, comment);
//			  dao.insert(pb);
//
//			}
//			System.out.println("檔案" + filename + "新增完畢");
//		} catch (IOException ex) {
//			System.out.println(ex.getMessage() + "==>" + filename);
//			ex.printStackTrace();
//		}
//	}

//	public static void displayData(PlaceBean pb) {
//		String saveFolderImg = "D:\\images1221";
//		File dirImg = new File(saveFolderImg);
//		if (!dirImg.exists())  dirImg.mkdirs();
//		String filenameImg = pb.getFilename(); 
//		File fileImg = new File(dirImg, filenameImg);
//		
//		
//		String saveFolderTxt = "D:\\text1221";
//		File dirTxt = new File(saveFolderTxt);
//		if (!dirTxt.exists())  dirTxt.mkdirs();
//		String filenameTxt = "Comment" + pb.getPlaceId() + ".txt"; 
//		File fileTxt = new File(dirTxt, filenameTxt);
//		
//		System.out.println("Place Id :"  + pb.getPlaceId());
//		System.out.println("Type Id  :"  + pb.getTypeId());
//		System.out.println("Name     :"  + pb.getName());
//		System.out.println("Phone    :"  + pb.getPhone());
//		System.out.println("Address  :"  + pb.getAddress());
//		System.out.println("Longitude:"  + pb.getLongitude());
//		System.out.println("Latitude :"  + pb.getLatitude());
//		System.out.println("Link     :"  + pb.getLink());
//		System.out.println("Filename :"  + pb.getFilename());
//		saveBytesToFile(pb.getPicture(), fileImg);
//		saveCharsToFile(pb.getComment(), fileTxt, "BIG5");
//	}
}
