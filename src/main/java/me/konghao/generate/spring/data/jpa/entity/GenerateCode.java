package me.konghao.generate.spring.data.jpa.entity;

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

import me.konghao.generate.spring.data.jpa.entity.dbentity.Config;
import me.konghao.generate.spring.data.jpa.entity.dbentity.DBColumn;
import me.konghao.generate.spring.data.jpa.entity.dbentity.DBTable;



public class GenerateCode {
	Config config = null;

	public GenerateCode() {
		read = new ReadConfig();
		config = this.read.Read();
	}

	ReadConfig read;

	public void buildCode() {
		// TODO 自动生成的方法存根
		List<DBTable> tabs = initTabData();
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
		sb.append("private static final long serialVersionUID = 2300044412175011558L;\r\n");
		StringBuilder sbToString = new StringBuilder();
		sbToString.append("@Override\r\n");
		sbToString.append("public String toString() {\r\n");
		sbToString.append("return \"" + UpFirstStr(tab.getNameIgnorePrefix()) + "{\"");
		for (int i = 0; i < tab.getColumns().size(); i++) {
			DBColumn col = tab.getColumns().get(i);
			if (col.isPK()) {
				sb.append("@Id\r\n");

			}
			if (col.isIdentity()) {
				sb.append("@GeneratedValue(strategy = GenerationType.IDENTITY)\r\n");
			}
			sb.append("@Column(name = \"" + col.getColName() + "\", nullable = false) \r\n");
			sb.append("private " + col.getColType() + " " + col.getColName() + ";\r\n");
			String methodName = UpFirstStr(col.getColName());
			sb.append(" public " + col.getColType() + " get" + methodName + "() { return " + col.getColName()
					+ "; }\r\n");
			sb.append(" public void set" + methodName + "(String " + col.getColName() + ") { this." + col.getColName()
					+ " = " + col.getColName() + "; }\r\n");

			sbToString.append("+\"" + (i > 0 ? "," : "") + "'" + col.getColName() + "'='\"+" + col.getColName() + "+\"'\"");

		}
		sbToString.append("+'}';");
		sbToString.append("}\r\n");
		sb.append(sbToString.toString());
		sb.append("}\r\n");
		TextToFile(UpFirstStr(tab.getNameIgnorePrefix()) + ".java", sb.toString());
	}

	public void TextToFile(final String name, final String strBuffer) {
		try {
			String fileDir = System.getProperty("user.dir") + "\\entity\\";
			dirExists(fileDir);
			String filePath = fileDir + name;
			fileExists(filePath);
			// 创建文件对象
			File fileText = new File(filePath);
			// 向文件写入对象写入信息
			FileWriter fileWriter = new FileWriter(fileText);

			// 写文件
			fileWriter.write(strBuffer);
			// 关闭
			fileWriter.close();
		} catch (IOException e) {
			//
			e.printStackTrace();
		}
	}

	private List<DBTable> initTabData() {
		List<DBTable> tabs = getTables();
		tabs.forEach(a -> {
			List<DBColumn> cols = getColumns(a.getName());
			a.setColumns(cols);
		});
		return tabs;
	}

	public List<DBColumn> getColumns(String tabName) {
		String sql = "select column_Name,column_Type,COLUMN_COMMENT,(case when EXTRA='auto_increment' then 1 else 0 end) identity,(case when COLUMN_KEY='PRI' then 1 else 0 end) as IsPK from information_schema.columns where	 table_schema='"
				+ config.getDatabase() + "' and table_name='" + tabName + "'";
		List<DBColumn> columns = new ArrayList<DBColumn>();
		OpenDB(sql, new IResultSetOpen() {

			@Override
			public void Open(ResultSet rst) {
				try {
					while (rst.next()) {
						DBColumn column = new DBColumn();
						column.setColName(rst.getString(1));
						column.setColType(getJavaType(rst.getString(2)));
						column.setDesc(rst.getString(3));
						column.setIdentity(rst.getBoolean(4));
						column.setIdentity(rst.getBoolean(5));
						columns.add(column);
					}
				} catch (SQLException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}

			}
		});
		return columns;
	}

	// 判断文件是否存在
	public void fileExists(String path) {
		File file = new File(path);
		if (file.exists()) {
			System.out.println("file exists");
		} else {
			System.out.println("file not exists, create it ...");
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	// 判断文件夹是否存在
	public void dirExists(String path) {
		File dir = new File(path);
		if (dir.exists()) {
			if (dir.isDirectory()) {
				System.out.println("dir exists");
			} else {
				System.out.println("the same name file exists, can not create dir");
			}
		} else {
			System.out.println("dir not exists, create it ...");
			dir.mkdir();
		}

	}

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

	public List<DBTable> getTables() {
		String sql = " select table_name from information_schema.tables where table_schema='" + config.getDatabase()
				+ "' and table_type='base table';";
		List<DBTable> tabs = new ArrayList<DBTable>();
		OpenDB(sql, new IResultSetOpen() {
			@Override
			public void Open(ResultSet rst) {
				try {
					while (rst.next()) {
						DBTable tab = new DBTable();
						tab.setName(rst.getString(1));
						tabs.add(tab);
					}
				} catch (SQLException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
			}
		});
		return tabs;
	}

	void OpenDB(String sql, IResultSetOpen action) {
		Config config = this.read.Read();
		Connection connection = null;
		try {
			String url = config.getUrl() + "/mysql?autoReconnect=true&useSSL=false";
			connection = DriverManager.getConnection(url, config.getName(), config.getPwd());
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		PreparedStatement pstmt = null;
		try {
			pstmt = (PreparedStatement) connection.prepareStatement(sql);
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		ResultSet rst = null;
		try {
			rst = pstmt.executeQuery(sql);
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		action.Open(rst);
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}

	}

}
