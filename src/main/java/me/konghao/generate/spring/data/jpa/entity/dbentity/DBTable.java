package me.konghao.generate.spring.data.jpa.entity.dbentity;

import java.util.List;

public class DBTable {
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	List<DBColumn> columns;

	public List<DBColumn> getColumns() {
		return columns;
	}

	public void setColumns(List<DBColumn> columns) {
		this.columns = columns;
	}

	public String getNameIgnorePrefix() {
		String[] names = name.split("_");
		return names[names.length - 1];
	}
}
