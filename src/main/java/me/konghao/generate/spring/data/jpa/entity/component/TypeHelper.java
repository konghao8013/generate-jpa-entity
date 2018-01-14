package me.konghao.generate.spring.data.jpa.entity.component;

public class TypeHelper {
	public String getJavaType(String type) {
		type = type.toUpperCase();
		String result = null;
		switch (type) {
		case "VARCHAR":
			result = "String";
			break;
		case "CHAR":
			result = "String";
			break;
		case "BLOB":
			result = "byte[]";
			break;
		case "TEXT":
			result = "String";
			break;
		case "INTEGER":
			result = "long";
			break;
		case "TINYINT":
			result = "Integer";
			break;
		case "MEDIUMINT":
			result = "Integer";
			break;
		case "SMALLINT":
			result = "Integer";
			break;
		case "BIT":
			result = "boolean";
			break;
		case "BIGINT":
			result = "BigInteger";
			break;
		case "FLOAT":
			result = "float";
			break;
		case "DOUBLE":
			result = "double";
			break;
		case "DECIMAL":
			result = "BigDecimal";
			break;
		case "BOOLEAN":
			result = "Integer";
			break;
		case "ID":
			result = "long";
			break;
		case "DATE":
			result = "Date";
			break;
		case "TIME":
			result = "Time";
			break;
		case "DATETIME":
			result = "Timestamp";
			break;
		case "TIMESTAMP":
			result = "Timestamp";
			break;
		case "YEAR":
			result = "Date";
			break;
		default:
			result = "String";
			break;
		}
		return result;
	}

}
