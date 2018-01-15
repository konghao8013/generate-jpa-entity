package me.konghao.generate.spring.data.jpa.entity.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.jdbc.PreparedStatement;

import me.konghao.generate.spring.data.jpa.entity.component.ReadConfig;
import me.konghao.generate.spring.data.jpa.entity.component.TypeHelper;
import me.konghao.generate.spring.data.jpa.entity.dbentity.Config;
import me.konghao.generate.spring.data.jpa.entity.dbentity.DBColumn;
import me.konghao.generate.spring.data.jpa.entity.dbentity.DBTable;

public class DataBaseServer {

	Config config = null;
	ReadConfig read = null;

	public DataBaseServer() {
		read = new ReadConfig();
		config = read.Read();

	}

	public List<DBTable> initTabData() {
		List<DBTable> tabs = getTables();
		tabs.forEach(a -> {
			List<DBColumn> cols = getColumns(a.getName());
			a.setColumns(cols);
		});
		return tabs;
	}

	private List<DBColumn> getColumns(String tabName) {
		String sql = "select column_Name,column_Type,COLUMN_COMMENT,(case when EXTRA='auto_increment' then 1 else 0 end) identity,(case when COLUMN_KEY='PRI' then 1 else 0 end) as IsPK from information_schema.columns where	 table_schema='"
				+ config.getDatabase() + "' and table_name='" + tabName
				+ "' order by   COLUMN_KEY='PRI' desc,EXTRA='auto_increment'  desc";
		List<DBColumn> columns = new ArrayList<DBColumn>();
		TypeHelper t = new TypeHelper();
		OpenDB(sql, new IResultSetOpen() {

			@Override
			public void Open(ResultSet rst) {
				try {
					while (rst.next()) {
						DBColumn column = new DBColumn();
						column.setColName(rst.getString(1));
						column.setColType(t.getJavaType(rst.getString(2)));
						column.setDesc(rst.getString(3));
						column.setIdentity(rst.getBoolean(4));
						column.setPK(rst.getBoolean(5));
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

	private List<DBTable> getTables() {
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
			String url = config.getUrl() + "/mysql?autoReconnect=true&useSSL=false&serverTimezone=UTC ";
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
