package _00_Util;

import java.io.Serializable;


public class BeanObject implements Serializable {
//public class BeanObject {
	String tableName = "";						// 資料表名稱
	String[] fieldsName = new String[0];		// 欄位名稱
	String[] fieldsType = new String[0];		// 欄位型別
	int[] fieldsLength = new int[0]; 			// 欄位長度
	Boolean[] autoIncrement = new Boolean[0];	// 自動增號
	Boolean[] notNulls = new Boolean[0];		// 不允許空值
	String[] defaultValues = new String[0];	// 欄位預設值
	String[] primaryKeys = new String[0];		// 主鍵欄位
	Object[] fieldsValue = new Object[0];		// 欄位值
	String[] insertFields = new String[0];		// 新增資料欄位(排除自動增號的欄位)
	int columnsCount = 0;						// 欄位數
	
	/**
	 * 建構子
	 * @param table	(String) 資料表名稱
	 */
	public BeanObject(String table){
		tableName = table;
	}

	/**
	 * 建構子
	 * @param table			(String) 	資料表名稱
	 * @param saField		(String[])	欄位名稱
	 * @param saType		(String[])	欄位型別
	 * @param iaLength		(int[])		欄位長度
	 * @param auto			(boolean)	自動增號
	 * @param notNulls		(boolean) 	不允許空值
	 * @param defaultValues(String[])	預設值
	 * @param pk			(String)	主建欄位
	 */
	public BeanObject(String table
			, String[] saField
			, String[] saType
			, int[] iaLength
			, Boolean[] auto
			, Boolean[] notNulls
			, String[] defaultValues
			, String pk){
		tableName = table;
		if(fieldsName.length > fieldsType.length ){
			System.err.println("!!! fields 的長度大於 types 的長度 !!!");
		}
		int iCount = Math.min(saField.length, saType.length);
		for(int i=0;i<iCount;i++){
			String name = saField[i] , value;
			String type = saType[i];
			int length = iaLength[i];
			Boolean autoIncr , notNull ,hasPK;
			
			if(i>=auto.length){
				autoIncr = false;
			}else{
				autoIncr = auto[i];
			}
			
			if(i>=notNulls.length){
				notNull = false;
			}else{
				notNull = notNulls[i];
			}
			
			if(i>=defaultValues.length){
				value = null;
			}else{
				value = defaultValues[i];
			}

			hasPK = (","+pk+",").contains(","+name+",");

			addField(name, type, length, autoIncr, notNull, value, hasPK);
		}
//		primaryKeys = pk;
	}
	
	public int addField(String field, String type, int length, Boolean auto, Boolean notNull, String value ,boolean pk){
		String[] names = fieldsName;
		String[] types = fieldsType;
		int[] aiLength = fieldsLength;
		Boolean[] autoIncr = autoIncrement;
		Boolean[] notnulls = notNulls;
		String[] saValues = defaultValues;
		Object[] oaValues = fieldsValue;

		fieldsName = new String[columnsCount+1];
		fieldsType = new String[columnsCount+1];
		fieldsLength = new int[columnsCount+1];
		autoIncrement = new Boolean[columnsCount+1];
		notNulls = new Boolean[columnsCount+1];
		defaultValues = new String[columnsCount+1];
		fieldsValue = new Object[columnsCount+1];
		for(int i=0;i<columnsCount;i++){
//			System.out.println("["+columnsCount+"]");
			fieldsName[i] = names[i];
			fieldsType[i] = types[i];
			fieldsLength[i] = aiLength[i];
			autoIncrement[i] = autoIncr[i];
			notNulls[i] = notnulls[i];
			defaultValues[i] = saValues[i];
			fieldsValue[i] = oaValues[i];
		}
		fieldsName[columnsCount] = field;
		fieldsType[columnsCount] = type;
		if (!type.toUpperCase().equals("BIGINT") && 
			!type.toUpperCase().equals("INTEGER") && 
			!type.toUpperCase().equals("SMALLINT") &&
			!type.toUpperCase().equals("INT")){
			auto = false;
		}
		fieldsLength[columnsCount] = length;
		autoIncrement[columnsCount] = auto;
		if(auto){
			notNulls[columnsCount] = true;
			defaultValues[columnsCount] = null;
		}else{
			notNulls[columnsCount] = notNull;
			String[] saInsert = insertFields;
			insertFields = new String[saInsert.length+1];
			System.arraycopy(saInsert, 0, insertFields, 0, saInsert.length);
			insertFields[saInsert.length] = field;
		}
		if(pk){
			defaultValues[columnsCount] = null;
			notNulls[columnsCount] = true;
			String[] saPK = primaryKeys;
			primaryKeys = new String[saPK.length+1];
			System.arraycopy(saPK, 0, primaryKeys, 0, saPK.length);
			primaryKeys[saPK.length] = field ;
		}
		
		defaultValues[columnsCount] = value;			
		return	columnsCount++;

	}

	public void setFieldValue(String field, Object value){
		int i = getFieldIndexOf(field);
		if(i>=0) fieldsValue[i] = value;
	}

	public Object getFieldValue(String field){
		int i = getFieldIndexOf(field);
		if(i>=0) return fieldsValue[i];
		return null;
	}

	public String getFieldType(String field){
		int i = getFieldIndexOf(field); 
		if(i>=0) return fieldsType[i];	
		return null;
	}
	
	private int getFieldIndexOf(String field){
		for(int i=0;i<columnsCount;i++){
			if(fieldsName[i].toUpperCase().equals(field.toUpperCase())){
				return i;
			}
		}
		return -1;		
	}
	
	public String getTableName(){
		return tableName;
	}

	public String[] getFieldsName(){
		return fieldsName;
	}

	public String[] getPrimaryKeys(){
		return primaryKeys;
	}
	
	public  String getPKConditionString(){
		String sCondition = "";
		for(int i=0;i<primaryKeys.length;i++){
			if(i>0) sCondition += "AND ";
			sCondition += (primaryKeys[i] + " = ? ");
		}
		return sCondition;
	}
	
	public  String getPKFieldValueString(){
		String sFVString = "";
		for(int i=0;i<primaryKeys.length;i++){
			if(i>0) sFVString += "AND ";
			sFVString += (primaryKeys[i] + " = " + getFieldValue(primaryKeys[i]) + " ");
		}
		return sFVString;
	}

	public String[] getPKFieldTypes(){
		String[] saTypes = new String[primaryKeys.length];
		for(int i=0;i<primaryKeys.length;i++){
			saTypes[i] = getFieldType(primaryKeys[i]);
		}
		return saTypes;
	}
	
	public Object[] getPKFieldValues(){
		Object[] oaValues = new Object[primaryKeys.length];
		for(int i=0;i<primaryKeys.length;i++){
			oaValues[i] = getFieldValue(primaryKeys[i]);
		}
		return oaValues;
	}
	
	public String[] getInsertFields(){
		return insertFields;
	}
	
	public String[] getInsertTypes(){
		String[] saTypes = new String[insertFields.length];
		for(int i=0;i<insertFields.length;i++){
			saTypes[i] = getFieldType(insertFields[i]);
		}
		return saTypes;
	}
	
	public Object[] getInsertValues(){
		Object[] oaValues = new Object[insertFields.length];
		for(int i=0;i<insertFields.length;i++){
			oaValues[i] = getFieldValue(insertFields[i]);
		}
		return oaValues;
	}
	
	public String getDropTableString(){
		return "DROP TABLE IF EXISTS `" + tableName + "`";
	}
	
	public String getCreateTableString(){
		String createString = "CREATE TABLE `" + tableName + "` (";
		for(int i=0;i<columnsCount;i++){
			if(i!=0){createString += ",";}
			createString += ("`" + fieldsName[i] + "` ");
			createString += fieldsType[i];

			if(fieldsLength[i] > 0){
				createString += ("(" + fieldsLength[i] + ")");
			}
			createString += " ";
			
			if(notNulls[i]){
				createString += " NOT NULL";
			}
			if(autoIncrement[i]){
				createString += " AUTO_INCREMENT";
			}
			if(defaultValues[i]!=null){
				createString += " DEFAULT " + defaultValues[i] + " ";
			}
		}
		if(primaryKeys.length>0){
			createString += ",PRIMARY KEY (";
			for(int i=0;i<primaryKeys.length;i++){
				if(i>0) createString += ",";
				createString += ("`" + primaryKeys[i] + "`");
			}
			createString += ")";
		}
		createString += ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_general_ci";
		return createString;
	}

	public String getInsertString(){
		String sql = "";
		sql = "INSERT INTO " + tableName + " VALUES(";
		for(int i=0;i<columnsCount;i++){
			if(i>0) sql += ",";
			if(autoIncrement[i]){
				sql += "null ";
			}else{
				sql += "? "; 
			}
		}
		sql += ")";
		
		return sql;
	}
	
	public String getUpdateString(){
		String sql = "";
		sql = "UPDATE " + tableName + " SET ";
		for(int i=0;i<columnsCount;i++){
			if(i>0) sql += ",";
			sql += (fieldsName[i] + " = ? ");
		}
		sql += ("WHERE " + getPKConditionString());
		return sql;
	}
	
	public String getDeleteString(){
		String sql = "";
		sql = "DELETE FROM " + tableName + " ";
		sql += ("WHERE " + getPKConditionString());
		return sql;
	}
	
	public String getSelectString(){
		String sql = "";
		sql = "SELECT * FROM " + tableName + " ";
		return sql;
	}
}
