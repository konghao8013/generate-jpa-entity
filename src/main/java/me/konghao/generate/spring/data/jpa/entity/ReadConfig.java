package me.konghao.generate.spring.data.jpa.entity;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import me.konghao.generate.spring.data.jpa.entity.dbentity.Config;

@Component
public class ReadConfig {
	public Config Read() {
		Config result = new Config();
		String json = readConfigCurrent();
		JSONObject obj = new JSONObject(json);
		result.setUrl(obj.getString("url"));
		result.setPwd(obj.getString("pwd"));
		result.setName(obj.getString("name"));
		result.setDatabase(obj.getString("database"));

		return result;
	}

	private String getCurrentConfigPath() {

		return System.getProperty("user.dir") + "\\config.ini";

	}

	public String readConfigCurrent() {
		File file = new File(getCurrentConfigPath());
		return readFileString(file);
	}

	public String readFileString(File file) {
		StringBuilder result = new StringBuilder();
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));// 构造一个BufferedReader类来读取文件
			String s = null;
			while ((s = br.readLine()) != null) {// 使用readLine方法，一次读一行
				result.append(System.lineSeparator() + s);
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result.toString();
	}
}
