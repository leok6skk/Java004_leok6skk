package _00_Util;

import java.io.CharArrayWriter;
import java.io.Reader;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.serial.SerialClob;


public class BeanDAO {
	String dbURL = SystemConstant.URL + "?user=" + SystemConstant.USER + "&password=" + SystemConstant.PASSWORD
			+ "&useSSL=true&useUnicode=yes&characterEncoding=UTF-8";

	public BeanDAO() {
		
	}

	public BeanDAO(String url) {
		dbURL = url;
	}

	public void createTables(BeanObject bo) {
//		String dropStr = readSQLFile("dropPlace.sql");
		String dropStr = bo.getDropTableString();
//		String createStr = readSQLFile("CreatePlace.sql");
		String createStr = bo.getCreateTableString();
//		System.out.println("["+createStr+"]");
		try (Connection con = DriverManager.getConnection(dbURL); 
			Statement stmt = con.createStatement();) {
			stmt.executeUpdate(dropStr);
			stmt.executeUpdate(createStr);
			System.out.println("表格重置成功");
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

	}

//	private String readSQLFile(String filename) {
////		String filename = "CreatePlace.sql";
//		try (FileInputStream fis = new FileInputStream(filename);
//				InputStreamReader in = new InputStreamReader(fis, "BIG5");
//				BufferedReader br = new BufferedReader(in);) {
//			String line = "";
//			StringBuffer sb = new StringBuffer();
//			// 將放在檔案內的字串合併為一個長字串
//			while ((line = br.readLine()) != null) {
//				sb.append(line);
//			}
//			return sb.toString();
//		} catch (IOException ex) {
//			ex.printStackTrace();
//		}
//		return null;
//	}
	private char[] clobToCharArray(Clob clob) {
		try (
        Reader rd = clob.getCharacterStream();
        CharArrayWriter caw = new CharArrayWriter();
		) {
           char[] ca = new char[8192];
           int len = 0 ;
           while ((len=rd.read(ca))!=-1){
        	  caw.write(ca, 0, len);
           }
           char[] output = caw.toCharArray();
		   return output;
		} catch(Exception ex){
			ex.printStackTrace();
		}   
		return null;
	}


	private void pstmtSetValue(PreparedStatement pstmt, String[] saFields, String[] saTypes, Object[] oaValues) throws SQLException{
		for(int i=0;i<saFields.length;i++){
			switch(saTypes[i].toUpperCase()){
			case "INT": case "INTEGER":
				pstmt.setInt(i+1, (int)oaValues[i]);
				break;
			case "DOUBLE":
				pstmt.setDouble(i+1, (Double)oaValues[i]);
				break;
			case "LONG":
				pstmt.setLong(i+1, (Long)oaValues[i]);
				break;
			case "CHAR": case "VARCHAR": case "TEXT":
				pstmt.setString(i+1, (String)oaValues[i]);
				break;
			case "LONGBLOB": case "BLOB":
				pstmt.setBytes(i+1, (byte[])oaValues[i]);
				break;
			case "LONGCLOB": case "CLOB": case "LONGTEXT":
				pstmt.setClob(i+1, new SerialClob((char[])oaValues[i]));
				break;
			}				
		}
	}

	private void resultGetValue(ResultSet rs, BeanObject bo) throws SQLException{
		int iColumns = rs.getMetaData().getColumnCount();
		for(int i=1;i<=iColumns;i++){
			String columnName = rs.getMetaData().getColumnName(i);
			
			Object oValue;
			switch(bo.getFieldType(columnName).toUpperCase()){
			case "INT": case "INTEGER":
				oValue = rs.getInt(i);
				break;
			case "DOUBLE":
				oValue = rs.getDouble(i);
				break;
			case "LONG":
				oValue = rs.getLong(i);
				break;
			case "LONGBLOB": case "BLOB":
				oValue = rs.getBytes(i);
				break;
			case "LONGCLOB": case "CLOB": case "LONGTEXT":
				oValue = clobToCharArray(rs.getClob(i));
				break;
			default: 
				oValue = rs.getString(i);
				break;
			}				
			bo.setFieldValue(columnName, oValue);
		}
	}

	public int executeSQL(String sql, String[] saFields, String[] saTypes, Object[] oaValues, String pkfvString) {
		int n = 0;		
		try (
		  Connection con = DriverManager.getConnection(dbURL);
		  PreparedStatement pstmt = con.prepareStatement(sql);		  		
		) {
			pstmtSetValue(pstmt, saFields, saTypes, oaValues);

			n = pstmt.executeUpdate();

			return n;
		} catch (SQLException ex) {
			System.out.println(ex.getSQLState() + " " + pkfvString);
			ex.printStackTrace() ;
		}
		return n;
	}	
	
	public List<BeanObject> querySQL(BeanObject bo, String sql, Object[] oaValues){
		List <BeanObject> list = new ArrayList<>();
		try (
			Connection con = DriverManager.getConnection(dbURL);
			PreparedStatement pstmt = con.prepareStatement(sql);	  		
		) {
			if(oaValues.length>0){
				pstmtSetValue(pstmt, bo.primaryKeys, bo.getPKFieldTypes(), oaValues);
			}
			try (
			   ResultSet rs = pstmt.executeQuery();
		    ) {
			   while (rs.next()){
				   resultGetValue(rs, bo);
				   list.add(bo);
			   }	
			}
		} catch (SQLException ex) {
			System.out.println(ex.getSQLState() + " " + bo.getPKFieldValueString());
			ex.printStackTrace() ;
		}
				
		return list;
	}
	
//	public int insert(PlaceBean pb) {
	public int insert(BeanObject bo) {
		int n = executeSQL(
				bo.getInsertString()
				,bo.getInsertFields()
				,bo.getInsertTypes()
				,bo.getInsertValues()
				,bo.getPKFieldValueString()
			);
		if(n>0){
			String sMessage = "更新記錄成功,";
			sMessage += bo.getPKFieldValueString();
			System.out.println(sMessage);
		}
		return n;
	}
	
	public int update(BeanObject bo) {
		int n = executeSQL(
				bo.getUpdateString()
				,bo.fieldsName
				,bo.fieldsType
				,bo.fieldsValue
				,bo.getPKFieldValueString()
			);
		if(n>0){
			String sMessage = "更新記錄成功,";
			sMessage += bo.getPKFieldValueString();
			System.out.println(sMessage);
		}
		return n;
	}

	public int delete(BeanObject bo){
		int n = executeSQL(
				bo.getDeleteString()
				,bo.getPrimaryKeys()
				,bo.getPKFieldTypes()
				,bo.getPKFieldValues()
				,bo.getPKFieldValueString()
			);
		if(n>0){
			String sMessage = "記錄刪除成功,";
			sMessage += bo.getPKFieldValueString();
			System.out.println(sMessage);
		}
		return n;
	}

	public BeanObject findByPrimaryKey(BeanObject bo ,Object[] oaValues) {
		String sql = bo.getSelectString();
		if(oaValues.length>0){
			sql += ("WHERE " + bo.getPKConditionString());
		}
		List <BeanObject> list = querySQL(bo, sql, oaValues);
		if(list.size()>0){
			System.out.println("查詢記錄成功, " + bo.getPKFieldValueString());
			bo = list.get(0);
		}
		return bo;
	}

	public List<BeanObject> findAll(BeanObject bo) {
		String sql = bo.getSelectString();
		List <BeanObject> list = querySQL(bo, sql, new Object[0]);
		if(list.size()>0){
			System.out.println("查詢多筆記錄成功");
		}
		return list;
	}
	
}
