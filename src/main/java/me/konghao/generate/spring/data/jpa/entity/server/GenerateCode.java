package me.konghao.generate.spring.data.jpa.entity.server;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.Timestamp;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mysql.cj.jdbc.PreparedStatement;

import me.konghao.generate.spring.data.jpa.entity.component.FileHelper;
import me.konghao.generate.spring.data.jpa.entity.component.TypeHelper;
import me.konghao.generate.spring.data.jpa.entity.dbentity.Config;
import me.konghao.generate.spring.data.jpa.entity.dbentity.DBColumn;
import me.konghao.generate.spring.data.jpa.entity.dbentity.DBTable;

public class GenerateCode {

	public GenerateCode() {

	}

	public void buildCode() {
		// TODO 自动生成的方法存根
		DataBaseServer db = new DataBaseServer();
		List<DBTable> tabs = db.initTabData();
		for (int i = 0; i < tabs.size(); i++) {
			DBTable tab = tabs.get(i);
			buildCodeByTab(tab);
		}
	}

	public String UpFirstStr(String value) {
		return value.replaceFirst(value.substring(0, 1), value.substring(0, 1).toUpperCase());
	}

	void buildCodeByTab(DBTable tab) {
		StringBuilder sb = new StringBuilder();
		sb.append("package entity;\r\n");
		sb.append("import java.io.Serializable;\r\n");
		sb.append("import javax.persistence.Entity; import javax.persistence.*;\r\n");
		sb.append("@Entity\r\n");
		sb.append("@Table(name = \"" + tab.getName() + "\") \r\n");
		sb.append("public class " + UpFirstStr(tab.getNameIgnorePrefix()) + " implements Serializable {\r\n");
		/*
		 * sb.
		 * append("private static final long serialVersionUID = 2300044412175011558L;\r\n"
		 * );
		 */
		StringBuilder sbToString = new StringBuilder();
		StringBuilder propertyStr = new StringBuilder();
		StringBuilder methodStr = new StringBuilder();
		sbToString.append("@Override\r\n");
		sbToString.append("public String toString() {\r\n");
		sbToString.append("return \"" + UpFirstStr(tab.getNameIgnorePrefix()) + "{\"");
		for (int i = 0; i < tab.getColumns().size(); i++) {
			DBColumn col = tab.getColumns().get(i);
			buildCodeByTab_column(col, propertyStr, methodStr);

			sbToString.append(
					"+\"" + (i > 0 ? "," : "") + "'" + col.getColName() + "'='\"+" + col.getColName() + "+\"'\"");

		}

		sbToString.append("+'}';");
		sbToString.append("}\r\n");

		sb.append(propertyStr.toString());
		sb.append(methodStr.toString());

		sb.append(sbToString.toString());
		sb.append("}\r\n");
		FileHelper f = new FileHelper();
		f.textToFile(UpFirstStr(tab.getNameIgnorePrefix()) + ".java", sb.toString());
	}

	private void buildCodeByTab_column(DBColumn column, StringBuilder propertyCode, StringBuilder methodCode) {
		buildCodeByTab_column_property(column, propertyCode);

		buildCodeByTab_column_method(column, methodCode);
	}

	private void buildCodeByTab_column_method(DBColumn column, StringBuilder methodCode) {
		String methodName = UpFirstStr(column.getColName());

		methodCode.append("/**\r\n");
		methodCode.append(" * 获取 " + column.getDesc() + "\r\n");
		methodCode.append(" **/\r\n");

		methodCode.append(" public " + column.getColType() + " get" + methodName + "() { return " + column.getColName()
				+ "; }\r\n");
		
		methodCode.append("/**\r\n");
		methodCode.append(" * 设置 " + column.getDesc() + "\r\n");
		methodCode.append(" **/\r\n");
		methodCode.append(" public void set" + methodName + "(" + column.getColType() + " " + column.getColName()
				+ ") { this." + column.getColName() + " = " + column.getColName() + "; }\r\n");
	}

	private void buildCodeByTab_column_property(DBColumn column, StringBuilder propertyCode) {
		if (column.isPK()) {
			propertyCode.append("@Id\r\n");

		}
		if (column.isIdentity()) {
			propertyCode.append("@GeneratedValue(strategy = GenerationType.IDENTITY)\r\n");
		}
		propertyCode.append("@Column(name = \"" + column.getColName() + "\", nullable = false) \r\n");
		propertyCode.append("private " + column.getColType() + " " + column.getColName() + ";\r\n");
	}

}
